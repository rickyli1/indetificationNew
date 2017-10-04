package com.main.java.repository;

import java.util.List;

import com.main.java.model.Equipment;

public interface EquipmentRepository {
    //设备导入
	public void importEquipments(Equipment equipments); 
	//设备导出
	public List<Equipment> findAllEquipmentsForExport(Equipment equipment);
	//按页查找设备
	public List<Equipment> findAllEquipments(Equipment equipment);
	//查询设备数量
	public int findAllEquipmentsCount(Equipment equipment);
	//删除设备
	public void deleteAllEquipments();
	//修改所有设备
	public void updateAllEquipment(Equipment equipment);
	//修改设备
	public int updateEquipment(Equipment equipment);
	//按id查找设备
	public Equipment findEquipmentById(Equipment select);
	
}
