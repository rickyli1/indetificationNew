package com.main.java.repository;

import java.util.List;

import com.main.java.model.Application;
import com.main.java.model.Repairer;

public interface AuditRepository {


	public List<Application> findAllApplications(Application application);
	
	public int findApplicationsCount(Application searchParams);
	
	public List<Repairer> findAreaRepairInfos(Application application);

	public void saveApplicationInfo(Application updateParams);

}
