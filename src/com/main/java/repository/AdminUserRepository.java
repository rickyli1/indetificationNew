package com.main.java.repository;

import java.util.List;

import com.main.java.model.AdminUser;

public interface AdminUserRepository {
	public List<AdminUser> selectAdminUser(AdminUser adminUser);

	/**
	 * 插入用户信息
	 */
	public int insertAdminUser(AdminUser adminUser);

	/**
	 * 修改用户信息
	 */
	public int updateAdminUser(AdminUser adminUser);
	
	//验证用户信息
	public AdminUser loginCheck(AdminUser adminUser);

}
