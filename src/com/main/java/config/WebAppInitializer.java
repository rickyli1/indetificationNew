package com.main.java.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer  extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{RootConfig.class,MongoDBConfig.class}; //加载基础配置
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class}; //加载请求配置
	}

	@Override
	protected String[] getServletMappings() {
        return new String [] {"/"};  //匹配路径
	}

}