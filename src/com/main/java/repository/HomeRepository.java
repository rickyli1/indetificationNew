package com.main.java.repository;

import com.main.java.model.AdminUser;

@Deprecated
public interface HomeRepository {
	int searchHome();
	
	void insertAdminUser(AdminUser user);
}
