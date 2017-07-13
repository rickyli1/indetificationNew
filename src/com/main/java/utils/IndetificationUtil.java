package com.main.java.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.main.java.model.AdminUser;

public class IndetificationUtil {
	public static AdminUser getAdminUser() {
		return (AdminUser)SecurityContextHolder.getContext()  
			    .getAuthentication()  
			    .getPrincipal();  
	}
		 
 
}
