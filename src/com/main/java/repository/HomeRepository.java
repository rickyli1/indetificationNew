package com.main.java.repository;

import com.main.java.model.AdminUser;

public interface HomeRepository {
	int searchHome();
	
	void insertAdminUser(AdminUser user);
}
