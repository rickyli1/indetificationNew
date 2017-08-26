package com.main.java.repository;

import java.util.List;

import com.main.java.model.Equipment;

public interface EquipmentRepository {

	public void importEquipments(Equipment equipments); 
	
	public List<Equipment> findAllEquipmentsForExport(Equipment equipment);
	
	public List<Equipment> findAllEquipments(Equipment equipment);
	
	public int findAllEquipmentsCount(Equipment equipment);
	
	public void deleteAllEquipments();

	public void updateAllEquipment(Equipment equipment);
	
	public int updateEquipment(Equipment equipment);

	public Equipment findEquipmentById(Equipment select);
	
}
