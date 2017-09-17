package com.main.java.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer  extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{RootConfig.class,MongoDBConfig.class}; 
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};  
	}

	@Override
	protected String[] getServletMappings() {
        return new String [] {"/"};  
	}

}