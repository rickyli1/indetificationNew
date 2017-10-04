package com.main.java.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.main.java.service.AdminUserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AdminUserService adminUserService;
	
	//不用验证文件路径
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/images/**");
        web.ignoring().antMatchers("/js/**");
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		//解决不允许显示在iframe的问题
        // http.headers().frameOptions().sameOrigin();

        //http.addFilterAt(usernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

       // http.authorizeRequests().anyRequest().fullyAuthenticated();
         http.authorizeRequests()
                .antMatchers("/resultFile/fileDownload/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated()
               .and()
               .formLogin()
                 .loginPage("/login")
                 .permitAll()
                 .defaultSuccessUrl("/equipment/init")
                 .usernameParameter("username")
                 .passwordParameter("password")
               .and()
               .csrf()
               .and().exceptionHandling().accessDeniedPage("/login");

        //自定义过滤器
       // MyFilterSecurityInterceptor filterSecurityInterceptor = new MyFilterSecurityInterceptor(securityMetadataSource,accessDecisionManager(),authenticationManagerBean());
        //在适当的地方加入
       // http.addFilterAt(filterSecurityInterceptor,FilterSecurityInterceptor.class);

       // http.exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")).and().logout().logoutUrl("/logout").logoutSuccessUrl("/login").and().exceptionHandling().accessDeniedPage("/accessDenied");

        // 关闭csrf
        http.csrf().disable();

        //session管理
        //session失效后跳转
        http.sessionManagement().invalidSessionUrl("/login");
        //只允许一个用户登录,如果同一个账户两次登录,那么第一个账户将被踢下线,跳转到登录页面
       // http.sessionManagement().maximumSessions(1).expiredUrl("/login");
    }

    
    
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(adminUserService);
		
	}
	
	private UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() throws Exception {
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
	
    /**
     * 验证异常处理器
     *
     * @return
     */
    private SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/getLoginError");
    }
    
    /**
     * 登录成功后跳转
     * 如果需要根据不同的角色做不同的跳转处理,那么继承AuthenticationSuccessHandler重写方法
     *
     * @return
     */
    private SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/loginSuccess");
    }



    public static class MyFilterSecurityInterceptor extends FilterSecurityInterceptor {

        public MyFilterSecurityInterceptor(FilterInvocationSecurityMetadataSource securityMetadataSource, AccessDecisionManager accessDecisionManager, AuthenticationManager authenticationManager){
            this.setSecurityMetadataSource(securityMetadataSource);
            this.setAccessDecisionManager(accessDecisionManager);
            this.setAuthenticationManager(authenticationManager);

        }
    }

}
