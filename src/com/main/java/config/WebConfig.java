package com.main.java.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration  
@EnableWebMvc  
@ComponentScan(basePackages={"com.main.java.controller"},excludeFilters={@Filter(type=FilterType.ANNOTATION,value=Service.class)})  
public class WebConfig {
    @Bean  
    public ViewResolver viewResolver(){  
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();//jsp视图解析器  
        resolver.setPrefix("/WEB-INF/views/");  //jsp文件位置
        resolver.setSuffix(".jsp");  
        resolver.setExposeContextBeansAsAttributes(true);  
        return resolver;  
    }  
    
    @Bean
    public MessageSource messageSource() {
    	ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    	messageSource.setBasename("classpath:resources/messages");//国际化文件配置
    	return messageSource;
    }
    
    @Bean
    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
    	return new MappingJackson2HttpMessageConverter();//json格式解析
    }
    
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
    	RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
    	requestMappingHandlerAdapter.getMessageConverters().add(mappingJacksonHttpMessageConverter());
    	return requestMappingHandlerAdapter;
    }
    
    //文件上传
    @Bean
    public CommonsMultipartResolver multipartResolver() {
    	CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
    	multipartResolver.setMaxUploadSize(5242880 * 4);//5MB *4
    	multipartResolver.setDefaultEncoding("UTF-8");
    	return multipartResolver;
    }
      
    /** 
     * 配置静态资源的处理 
     * 将请求交由Servlet处理,不经过DispatchServlet 
     */  
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){  
        configurer.enable();  
    }  
}
