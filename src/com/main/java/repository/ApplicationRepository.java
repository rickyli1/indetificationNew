package com.main.java.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.main.java.model.Application;

public interface ApplicationRepository {

	//导入申请
	public void importApplications(Application application); 
	//导出申请
	public List<Application> findAllApplicationsForExport(Application application);
	//导入审查
	public List<Application> findAllApplicationAuditForExport(Application bean);
	//按页查询
	public List<Application> findAllApplications(Application application);
	//申请数量
	public int findApplicationsCount(Application searchParams);
    //删除申请
	public void deleteRepairById(@Param(value="id")int id);
    //修改申请
	public void updateRepair(Application updateParams);


}
