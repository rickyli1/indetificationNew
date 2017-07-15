package com.main.java.repository;

import java.util.List;

import com.main.java.model.Repairer;

public interface RepairRepository {

	public void importRepairers(Repairer repairers); 
	
	public List<Repairer> findAllRepairersForExport();
	
	public List<Repairer> findAllRepairers(Repairer repairer);
	
	public int findAllRepairersCount();
	
	public void deleteAllRepairers();
}
