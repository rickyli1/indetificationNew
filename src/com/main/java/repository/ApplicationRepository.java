package com.main.java.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.main.java.model.Application;

public interface ApplicationRepository {

	public void importApplications(Application application); 
	
	public List<Application> findAllApplicationsForExport(Application application);
	
	public List<Application> findAllApplications(Application application);
	
	public int findApplicationsCount(Application searchParams);

	public void deleteRepairById(@Param(value="id")int id);

	public void updateRepair(Application updateParams);
	
}
