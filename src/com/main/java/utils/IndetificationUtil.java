package com.main.java.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.main.java.model.AdminUser;

/**
 * 获得登录用户的信息
 */
public class IndetificationUtil {
	public static AdminUser getAdminUser() {
		return (AdminUser)SecurityContextHolder.getContext()  
			    .getAuthentication()  
			    .getPrincipal();  
	}
		 
 
}
