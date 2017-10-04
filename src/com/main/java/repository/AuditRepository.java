package com.main.java.repository;

import java.util.List;

import com.main.java.model.Application;
import com.main.java.model.Repairer;

public interface AuditRepository {

    //按页查询审核信息
	public List<Application> findAllApplications(Application application);
	//审核信息数量
	public int findApplicationsCount(Application searchParams);
	//获取辖区信息
	public List<Repairer> findAreaRepairInfos(Application application);
    //保存审核信息
	public void saveApplicationInfo(Application updateParams);
    //审核信息导出
	public List<Application> findAllApplicationAuditForExport(Application bean);

}
