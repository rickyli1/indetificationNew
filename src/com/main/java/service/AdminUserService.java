package com.main.java.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.main.java.model.AdminUser;
import com.main.java.repository.AdminUserRepository;

@Service
public class AdminUserService implements UserDetailsService{
	@Autowired
	private AdminUserRepository adminUserRepository;

	public List<AdminUser> getAdminUser(AdminUser adminUser) {
		return adminUserRepository.selectAdminUser(adminUser);
	}
	
	@Cacheable(value = "serviceCache")
	public List<AdminUser> getAdminUser() {
		//return adminUserDao.selectAdminUser();
		List<AdminUser> list = new ArrayList<AdminUser>();
		
		System.out.println("@@@@@@@@@@" + "from db.................");
		AdminUser admin1 = new AdminUser();
		admin1.setUserName("qqqq");

		AdminUser admin2 = new AdminUser();
		admin2.setUserName("qqqq1");

		list.add(admin1);
		list.add(admin2);

		return list;
	}	

	/**
	 * @param adminUser
	 */
	public int addAdminUser(AdminUser adminUser) {
		return adminUserRepository.insertAdminUser(adminUser);
	}

	/**
	 * @param adminUser
	 */
	public int modifyAdminUser(AdminUser adminUser) {
		return adminUserRepository.updateAdminUser(adminUser);
	}

	@Override
	public AdminUser loadUserByUsername(String username) throws UsernameNotFoundException {
		AdminUser adminUser = new AdminUser();
		adminUser.setUserName(username);
		return adminUserRepository.loginCheck(adminUser);
	}
}
