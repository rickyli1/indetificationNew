package com.main.java.repository;

import java.util.List;

import com.main.java.model.Repairer;

public interface RepairRepository {
    //导入修理厂商
	public void importRepairers(Repairer repairers); 
	//导出厂商
	public List<Repairer> findAllRepairersForExport();
	//按页查询厂商
	public List<Repairer> findAllRepairers(Repairer repairer);
	//获得厂商数量
	public int findAllRepairersCount();
	//删除厂商
	public void deleteAllRepairers();
}
