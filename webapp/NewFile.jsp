返回主页
高因咖啡
本博客文章除了注明转载以外,其他均为原创,基于人兴趣与经验得出结论,如有错误,欢迎拍砖.
博客园
首页
新随笔
联系
订阅
管理
SpringBoot SpringSecurity4整合,灵活权限配置,弃用注解方式.

SpringSecurity 可以使用注解对方法进行细颗粒权限控制,但是很不灵活,必须在编码期间,就已经写死权限

其实关于SpringSecurity,大部分类都不需要重写,需要的只是妥善的配置.

每次修改权限以后,需要让MetaDataSource刷新 资源-权限 的MAP,这里应该需要做一些处理,或者优化.

这里实现,可以在后台随时开启关闭权限,不需要硬编码写死.而且资源的RequestMapping,可以是有多个地址

可以根据角色分配权限,也可以精确到为每一个用户分配权限,模块,或者方法.

这样比较灵活,但是UI会很复杂,用户也不好理解

 



 

 

 

资源注解:注解使用在控制器类,或者方法中.注解在类中,粗颗粒控制,注解在方法中细颗粒

复制代码
/**
 * Created by ZhenWeiLai on on 2016-10-16.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AclResc {
    int id();//ACLResource 因为特殊原因不使用 id 自动增长,所以必须自定义ID ,并且不能重复
    String code();
    String name();
    String homePage() default "";
    boolean isMenu() default true;
}
复制代码
注解在类:

@AclResc(id = 5000,code = "aclRescUser", name = AclRescUserController.MODULE_NAME,homePage = AclRescUserController.HOME_PAGE)
public class AclRescUserController extends BaseController<AclRescUser> 
注解在方法:

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @AclResc(id = 5001,code = "list",name = "用户资源列表")
    public ResultDataDto list(){

    }
系统完全启动后,更新资源信息:

复制代码
/**
 * Created by ZhenWeiLai on on 2016-10-16.
 * SpringBoot 启动完毕做些事情
 */
@Component
public class ApplicationStartup  implements CommandLineRunner {

    @Resource
    private AclResourceService aclResourceService;
    @Resource
    private AclAuthService aclAuthService;

    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Resource
    private MySecurityMetadataSource securityMetadataSource;



    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void run(String... strings) throws Exception {
        /**
         * 初始化资源,保存到数据库
         */
        initModule();
        /**
         * Spring Security 需要的资源-权限
         */
        securityMetadataSource.doLoadResourceDefine();
    }


    /**
     * 读取所有Controller包括以内的方法
     */
    private void initModule() {
        /**
         * 模块 - 方法map
         */
        Map<AclResource, List<AclResource>> resourcesMap = new HashMap<>();
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        for (RequestMappingInfo info : map.keySet()) {
            AclResc moduleAclResc = map.get(info).getBeanType().getAnnotation(AclResc.class);
            if (moduleAclResc != null) {
                if (StringUtils.isBlank(moduleAclResc.homePage()))
                    throw new RuntimeException("使用:" + AclResc.class.getName() + " 注解类时,请配置 homePage ");
                Class<?> aclResourceClass = map.get(info).getBeanType();
                RequestMapping moduleMapping = aclResourceClass.getAnnotation(RequestMapping.class);
                AclResource moduleResc = new AclResource(moduleAclResc.id(), moduleAclResc.code(), moduleAclResc.name(), Arrays.toString(moduleMapping.value()), AclResource.Type.MODULE.getCode(), moduleAclResc.homePage(), moduleAclResc.isMenu());
                if (moduleMapping != null) {
                    List<AclResource> resources;
                    AclResource methodResc;
                    Method method = map.get(info).getMethod();
                    AclResc methodAclResc = method.getAnnotation(AclResc.class);
                    if (methodAclResc != null) {
                        methodResc = new AclResource(methodAclResc.id(), methodAclResc.code(), methodAclResc.name(), info.getPatternsCondition().toString().replace("||", Delimiter.COMMA.getDelimiter()), AclResource.Type.METHOD.getCode(), null);
                        if (resourcesMap.get(moduleResc) == null) {
                            resources = new ArrayList<>();
                            resources.add(methodResc);
                            resourcesMap.put(moduleResc, resources);
                        } else {
                            resourcesMap.get(moduleResc).add(methodResc);
                        }
                    }
                }
            }
        }
        addModule(resourcesMap);
    }

    /**
     * 检查新模块,添加到数据库,并更新视图的模块ID
     *
     * @param resourcesMap
     */
    private void addModule(Map<AclResource, List<AclResource>> resourcesMap) {
        for (Map.Entry<AclResource, List<AclResource>> item : resourcesMap.entrySet()) {
            AclResource resultResc = aclResourceService.findEntityById(item.getKey().getId());
            //如果模块是新模块,那么新增到数据库
            if (resultResc == null) {
                aclResourceService.addEntity(item.getKey());
                List<AclResource> resources = item.getValue();
                for (AclResource resc : resources) {
                    resc.setModuleId(item.getKey().getId());
                }
            } else {
                //如果已存在模块,那么更新需要的字段
                aclResourceService.updateEntity(item.getKey());
                List<AclResource> resources = item.getValue();
                for (AclResource methodResc : resources) {
                    //方法模块CODE 根据 模块CODE + 方法CODE 生成
                    methodResc.setCode(item.getKey().getCode() + "_" + methodResc.getCode());
                    methodResc.setModuleId(resultResc.getId());
                    AclResource oringinalMethodResc = aclResourceService.findEntityById(methodResc.getId());
                    if (oringinalMethodResc != null) {
                        //RequestMapping可能被修改,所以这里要做一次更新
                        aclResourceService.updateEntity(methodResc);
                        //同时code也可能被更改,所以更新权限code
                        aclAuthService.updateCodeByRescId(methodResc.getCode(), methodResc.getId());
                    } else {
                        aclResourceService.addEntity(methodResc);
                    }
                }
            }
        }
    }

}
复制代码
 

构建权限菜单:

复制代码
/**
     * 根据用户权限构建菜单
     */
    @Override
    public Map<AclMenu, List<AclResource>> getAclUserMenus() {

        //创建完整的菜单,然后删除没有权限的菜单
        Map<AclMenu, List<AclResource>> userMenuModuleMap = findAclMenuModuleMap();
        //获取资源/权限集
        Map<String, Collection<ConfigAttribute>> moduleMap = securityMetadataSource.getModuleMap();
        for (String path : moduleMap.keySet()) {
            //如果没有权限
            if (!SecurityUtil.hastAnyAuth(moduleMap.get(path))) {
                Iterator<AclMenu> userMenuModuleMapKey = userMenuModuleMap.keySet().iterator();
                while (userMenuModuleMapKey.hasNext()) {
                    AclMenu key = userMenuModuleMapKey.next();
                    List<AclResource> modules = userMenuModuleMap.get(key);
                    if (modules.isEmpty()) {
                        userMenuModuleMapKey.remove();
                        continue;
                    }
                    Iterator<AclResource> aclResourceIterator = modules.iterator();
                    while (aclResourceIterator.hasNext()) {
                        String rescPath = aclResourceIterator.next().getPath();
                        String[] pathArr = rescPath.substring(1, rescPath.length() - 1).split(Delimiter.COMMA.getDelimiter());
                        for (String item : pathArr) {
                            if (item.equals(path)) {
                                //从菜单模块中删除
                                aclResourceIterator.remove();
                                //如果模块为空
                                if (modules.isEmpty()) {
                                    //删除菜单
                                    userMenuModuleMapKey.remove();
                                }
                            }
                        }

                    }
                }
            }
        }
        return userMenuModuleMap;
    }
复制代码
 

FilterInvocationSecurityMetadataSource:
复制代码
/**
 * Created by ZhenWeiLai on 2016-10-16.
 */
@Component("securityMetadataSource")
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static Map<String, Collection<ConfigAttribute>> moduleMap = null;

    private static Map<String, Collection<ConfigAttribute>> methodMap = null;

    @Resource
    private AclResourceService aclResourceService;

    @Resource
    private AclRescRoleService aclRescRoleService;

    @Resource
    private AclRoleService aclRoleService;

    @Resource
    private AclAuthService aclAuthService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        Collection<ConfigAttribute> collection;
        collection = getAttributesHandler(methodMap, object);
        if (collection != null)
            return collection;
        collection = getAttributesHandler(moduleMap, object);
        return collection;
    }

    /**
     * 处理方法
     *
     * @param map
     * @return
     */
    private Collection<ConfigAttribute> getAttributesHandler(Map<String, Collection<ConfigAttribute>> map, Object object) {
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        Iterator var3 = map.entrySet().iterator();
        Map.Entry entry;
        do {
            if (!var3.hasNext()) {
                return null;
            }
            entry = (Map.Entry) var3.next();

        } while (!(new AntPathRequestMatcher(entry.getKey().toString())).matches(request));
        return (Collection) entry.getValue();
    }


    //4
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet();
        Map<String, Collection<ConfigAttribute>> all = new HashMap<>(this.moduleMap);
        all.putAll(this.methodMap);
        Iterator var2 = all.entrySet().iterator();
        while (var2.hasNext()) {
            Map.Entry<String, Collection<ConfigAttribute>> entry = (Map.Entry) var2.next();
            allAttributes.addAll(entry.getValue());
        }

        return allAttributes;
    }

    //3
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }


    @Transactional(readOnly = true)
    private void loadResourceDefine() {
        loadModuleResources();
        loadMethodResources();
    }


    /**
     * 提供一个外部使用方法.获取module权限MAP;
     *
     * @return
     */
    public Map<String, Collection<ConfigAttribute>> getModuleMap() {
        Map<String, Collection<ConfigAttribute>> map = new HashMap<>(moduleMap);
        return map;
    }


    /**
     * 提供外部方法让Spring环境启动完成后调用
     */
    public void doLoadResourceDefine() {
        loadResourceDefine();
    }


    /**
     * 读取模块资源
     */
    private void loadModuleResources() {
        /**
         * 查询模块资源权限,配置模块权限验证
         */
        List<AclResource> aclResources = aclResourceService.findAllModule();

        //模块资源为KEY,角色为Value 的list
        moduleMap = new HashMap<>();
        for (AclResource module : aclResources) {
            /**
             * 加载所有模块资源
             */
            List<AclRescRole> aclRescRoles = aclRescRoleService.findByRescId(module.getId());


            /**
             * 无论如何超级管理员拥有所有权限
             */
            stuff(new SecurityConfig(SecurityUtil.ADMIN), moduleMap, module.getPath());

            for (AclRescRole aclRescRole : aclRescRoles) {
                Integer roleId = aclRescRole.getRoleId();//角色ID
                String roleCode = aclRoleService.findEntityById(roleId).getCode();//角色编码
                stuff(new SecurityConfig(roleCode.toUpperCase()), moduleMap, module.getPath());
            }
        }
    }


    /**
     * 读取精确方法权限资源
     */
    private void loadMethodResources() {
        /**
         * 因为只有权限控制的资源才需要被拦截验证,所以只加载有权限控制的资源
         */
        //方法资源为key,权限编码为
        methodMap = new HashMap<>();
        List<Map<String, String>> pathAuths = aclAuthService.findPathCode();
        for (Map pathAuth : pathAuths) {
            String path = pathAuth.get("path").toString();
            ConfigAttribute ca = new SecurityConfig(pathAuth.get("code").toString().toUpperCase());
            stuff(ca, methodMap, path);
        }
    }

    private void stuff(ConfigAttribute ca, Map<String, Collection<ConfigAttribute>> map, String path) {

        String[] pathArr = path.substring(1, path.length() - 1).split(Delimiter.COMMA.getDelimiter());
        for (String item : pathArr) {
            Collection<ConfigAttribute> collection = map.get(item + "/**");
            if (collection != null) {
                collection.add(ca);
            } else {
                collection = new ArrayList<>();
                collection.add(ca);
                String pattern = StringUtils.trimToEmpty(item) + "/**";
                map.put(pattern, collection);
            }
        }
    }
}
复制代码
 

最后:

复制代码
/**
 * Created by ZhenWeiLai on on 2016-10-16.
 * <p>
 * 三种方法级权限控制
 * <p>
 * 1.securedEnabled: Spring Security’s native annotation
 * 2.jsr250Enabled: standards-based and allow simple role-based constraints
 * 3.prePostEnabled: expression-based
 */
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private MySecurityMetadataSource securityMetadataSource;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/images/**");
        web.ignoring().antMatchers("/js/**");
//忽略登录界面
        web.ignoring().antMatchers("/login");

        //注册地址不拦截
//        web.ignoring().antMatchers("/reg");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //解决不允许显示在iframe的问题
        http.headers().frameOptions().disable();

        http.addFilterAt(usernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests().anyRequest().fullyAuthenticated();

        //自定义过滤器
        MyFilterSecurityInterceptor filterSecurityInterceptor = new MyFilterSecurityInterceptor(securityMetadataSource,accessDecisionManager(),authenticationManagerBean());
        //在适当的地方加入
        http.addFilterAt(filterSecurityInterceptor,FilterSecurityInterceptor.class);

        http.exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")).and().logout().logoutUrl("/logout").logoutSuccessUrl("/login").and().exceptionHandling().accessDeniedPage("/accessDenied");

        // 关闭csrf
        http.csrf().disable();

        //session管理
        //session失效后跳转
        http.sessionManagement().invalidSessionUrl("/login");
        //只允许一个用户登录,如果同一个账户两次登录,那么第一个账户将被踢下线,跳转到登录页面
        http.sessionManagement().maximumSessions(1).expiredUrl("/login");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        // 自定义UserDetailsService,设置加密算法
        auth.userDetailsService(userDetailsService);
//.passwordEncoder(passwordEncoder())
        //不删除凭据，以便记住用户
        auth.eraseCredentials(false);
    }

    UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() throws Exception {
        UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter();
        usernamePasswordAuthenticationFilter.setPostOnly(true);
        usernamePasswordAuthenticationFilter.setAuthenticationManager(this.authenticationManager());
        usernamePasswordAuthenticationFilter.setUsernameParameter("name_key");
        usernamePasswordAuthenticationFilter.setPasswordParameter("pwd_key");
        usernamePasswordAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/checkLogin", "POST"));
        usernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(simpleUrlAuthenticationFailureHandler());
        usernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        return usernamePasswordAuthenticationFilter;
    }

//    @Bean
//    public LoggerListener loggerListener() {
//        System.out.println("org.springframework.security.authentication.event.LoggerListener");
//        return new LoggerListener();
//    }
//
//    @Bean
//    public org.springframework.security.access.event.LoggerListener eventLoggerListener() {
//        System.out.println("org.springframework.security.access.event.LoggerListener");
//        return new org.springframework.security.access.event.LoggerListener();
//    }

    /**
     * 投票器
     */
    private AbstractAccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList();
        decisionVoters.add(new AuthenticatedVoter());
        decisionVoters.add(new RoleVoter());//角色投票器,默认前缀为ROLE_
        RoleVoter AuthVoter = new RoleVoter();
        AuthVoter.setRolePrefix("AUTH_");//特殊权限投票器,修改前缀为AUTH_
        decisionVoters.add(AuthVoter);
        AbstractAccessDecisionManager accessDecisionManager = new AffirmativeBased(decisionVoters);
        return accessDecisionManager;
    }

    @Override
    public AuthenticationManager authenticationManagerBean() {
        AuthenticationManager authenticationManager = null;
        try {
            authenticationManager = super.authenticationManagerBean();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authenticationManager;
    }


    /**
     * 验证异常处理器
     *
     * @return
     */
    private SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/getLoginError");
    }


//    /**
//     * 表达式控制器
//     *
//     * @return
//     */
//    private DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
//        DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
//        return webSecurityExpressionHandler;
//    }

//    /**
//     * 表达式投票器
//     *
//     * @return
//     */
//    private WebExpressionVoter webExpressionVoter() {
//        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
//        webExpressionVoter.setExpressionHandler(webSecurityExpressionHandler());
//        return webExpressionVoter;
//    }

    // Code5  官方推荐加密算法
//    @Bean("passwordEncoder")
//    public BCryptPasswordEncoder passwordEncoder() {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        return bCryptPasswordEncoder;
//    }

//    // Code3----------------------------------------------


    /**
     * 登录成功后跳转
     * 如果需要根据不同的角色做不同的跳转处理,那么继承AuthenticationSuccessHandler重写方法
     *
     * @return
     */
    private SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/loginSuccess");
    }



    /**
     * Created by ZhenWeiLai on on 2016-10-16.
     */
    public static class MyFilterSecurityInterceptor extends FilterSecurityInterceptor {

        public MyFilterSecurityInterceptor(FilterInvocationSecurityMetadataSource securityMetadataSource, AccessDecisionManager accessDecisionManager, AuthenticationManager authenticationManager){
            this.setSecurityMetadataSource(securityMetadataSource);
            this.setAccessDecisionManager(accessDecisionManager);
            this.setAuthenticationManager(authenticationManager);

        }
    }


}
复制代码
 

分类: JAVA,Spring
标签: 权限, SpringBoot, SpringSecurity, WebSecurityConfigurerAdapter
好文要顶 关注我 收藏该文    
181282945
关注 - 0
粉丝 - 0
+加关注
0 0
« 上一篇：mysql 导出每张表中的100条数据..............
» 下一篇：CentOS7 配置JDK1.8,开启8080端口
posted @ 2016-10-25 23:02 181282945 阅读(1634) 评论(0) 编辑 收藏
刷新评论刷新页面返回顶部
注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。
【推荐】50万行VC++源码: 大型组态工控、电力仿真CAD与GIS源码库
【免费】从零开始学编程，开发者专属实验平台免费实践！
阿里云B1
最新IT新闻:
· 个体化癌症疫苗取得突破
· 小米手机触底反弹，雷军做对了什么？
· 创业公司制定策略前，需要先回答这五个问题
· 苹果回应供应商Imagination 指责其声明失实且具误导性
· 899元买吗？一张图看懂华为畅享7 ：比别人的3GB更流畅
» 更多新闻...
极光推广_0701
最新知识库文章:
· 小printf的故事：什么是真正的程序员？
· 程序员的工作、学习与绩效
· 软件开发为什么很难
· 唱吧DevOps的落地，微服务CI/CD的范本技术解读
· 程序员，如何从平庸走向理想？
» 更多知识库文章...
公告
昵称：181282945
园龄：1年1个月
粉丝：0
关注：0
+加关注
<	2017年7月	>
日	一	二	三	四	五	六
25	26	27	28	29	30	1
2	3	4	5	6	7	8
9	10	11	12	13	14	15
16	17	18	19	20	21	22
23	24	25	26	27	28	29
30	31	1	2	3	4	5
搜索

 找找看

 谷歌搜索
我的标签
SpringBoot(7)
mysql(3)
MongoDb(3)
socket(3)
SpringSecurity(3)
serverSocket(2)
注解式事务(2)
只能登录一次(2)
声明式事务(2)
事务传播(1)
更多
随笔分类
Docker(1)
JAVA(13)
JVM(2)
Kubenetes(1)
MongoDB/NoSql(3)
MYSQL(3)
Spring(8)
并发(2)
多线程(4)
网络编程(2)
随笔档案
2017年6月 (8)
2017年5月 (2)
2017年4月 (1)
2017年3月 (7)
2017年2月 (6)
2017年1月 (1)
2016年12月 (1)
2016年11月 (2)
2016年10月 (1)
2016年9月 (4)
2016年8月 (1)
阅读排行榜
1. spring oauth2 ,spring security整合oauth2.0 JdbcTokenStore实现 解决url-pattern .do .action(2081)
2. SpringBoot SpringSecurity4整合,灵活权限配置,弃用注解方式.(1635)
3. MongoDB的DBREF 使用.(890)
4. 记录 serverSocket socket 输入,输出流,关闭顺序,阻塞,PrintWriter的一些问题.(740)
5. Centos7下,简单DOCKER 使用.映射SSH端口到宿主主机.(668)
Copyright ©2017 181282945