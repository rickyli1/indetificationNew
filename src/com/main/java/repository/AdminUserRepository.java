package com.main.java.repository;

import java.util.List;

import com.main.java.model.AdminUser;

public interface AdminUserRepository {
	public List<AdminUser> selectAdminUser(AdminUser adminUser);

	/**
	 * @return
	 */
	public int insertAdminUser(AdminUser adminUser);

	/**
	 * @return
	 */
	public int updateAdminUser(AdminUser adminUser);
	
	public AdminUser loginCheck(AdminUser adminUser);

}
