package com.main.java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.main.java.model.AdminUser;
import com.main.java.repository.HomeRepository;


@Deprecated
@Service
public class HomeService {
	@Autowired
	private HomeRepository homeRepository;
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	public String testHome() {
		System.out.println(homeRepository.searchHome());
		return "home";
	}
	
	@Transactional
	public void addHome1() {

			AdminUser user = new AdminUser();
			user.setUserName("user name8");
			user.setRoleId("roleId1");
			user.setRoleName("name");
			user.setQuestionCategoryIds("1,2");
			user.setQuestionCategoryNames("1,2");
			user.setPassword("password");
			user.setCreateId(123);
			user.setUpdateId(123);
			user.setCreateor("createor");
			user.setUpdateor("updateor");
			homeRepository.insertAdminUser(user);

	}


	public void addHome() {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try{
			AdminUser user = new AdminUser();
			user.setUserName("user name6");
			user.setRoleId("roleId7");
			user.setRoleName("name");
			user.setQuestionCategoryIds("1,2");
			user.setQuestionCategoryNames("1,2");
			user.setPassword("password");
			user.setCreateId(123);
			user.setUpdateId(123);
			user.setCreateor("createor");
			user.setUpdateor("updateor");
			homeRepository.insertAdminUser(user);
			homeRepository.insertAdminUser(user);
		}catch(Exception e) {
			transactionManager.rollback(status);
            throw e;
		}
		transactionManager.commit(status);

	}
}
