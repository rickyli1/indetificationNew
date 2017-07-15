package com.main.java.repository;

import java.util.List;

import com.main.java.model.Application;

public interface ApplicationRepository {

	public void importApplications(Application application); 
	
	public List<Application> findAllApplicationsForExport();
	
	public List<Application> findAllApplications(Application application);
	
	public int findApplicationsCount();
	
}
