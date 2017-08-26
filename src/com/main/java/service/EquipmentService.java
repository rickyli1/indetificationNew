package com.main.java.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.java.model.AdminUser;
import com.main.java.model.Equipment;
import com.main.java.repository.EquipmentRepository;
import com.main.java.utils.Constants;
import com.main.java.utils.ExcelUtil;
import com.main.java.utils.IndetificationUtil;

@Service
public class EquipmentService extends BaseImportService<EquipmentRepository, Equipment>{
	
	@Autowired
	private EquipmentRepository equipmentRepository;
	
	public int getEquipmentsCount() {
		Equipment equipment = new Equipment();
		return equipmentRepository.findAllEquipmentsCount(equipment);
	}
	
	
	public List<Equipment> findAllEquipmentsForExport(Equipment equipmentParam) {
		return equipmentRepository.findAllEquipmentsForExport(equipmentParam);
	}
	
	public List<Equipment> findAllEquipments(Equipment equipment){
		List<Equipment> equipments = equipmentRepository.findAllEquipments(equipment);
		List<Equipment> equipmentMerged = new ArrayList<>();
		String equipmentName = "";
    	for(int index = 0 ; index < equipments.size() ; index++){
    		Equipment equipmentIndex = equipments.get(index);
    		if(!equipmentName.equals(equipmentIndex.getEquipmentName())){
    			equipmentMerged.add(equipmentIndex);
    			equipmentName = equipmentIndex.getEquipmentName();
    		}else{
    			equipmentIndex.setEquipmentNo("");
    			equipmentIndex.setEquipmentGroup("");
    			equipmentIndex.setEquipmentSubGroup("");
    			equipmentIndex.setEquipmentName("");
    			equipmentMerged.add(equipmentIndex);
    		}
		}
		return  equipments;
	}
	
	public int findAllEquipmentsCount(Equipment equipment) {
		return equipmentRepository.findAllEquipmentsCount(equipment);
	}
	public String importEquipments(Sheet sheet) {
		List<Equipment> equipments = new ArrayList<>();
		HashMap<String,String> hm = new HashMap<>();
		AdminUser user = IndetificationUtil.getAdminUser();
		String equipmentName = "";
		String equipmentNo = "";
		String equipmentLevel = "";
		String equipmentCompany = "";
		String equipmentLimit = "";
		String equipmentRemark = "";
		String equipmentGroup = "";
		String equipmentSubGroup = "";
		for (Row row : sheet) {
			
			if (row.getRowNum() < 1) {
				continue;
			}else{
				if(row.getRowNum() > 2){
					Equipment equipment = new Equipment();
					
					for (Cell cell : row) {
			    		String cellValue = ExcelUtil.getCellValue(cell);
			    		if(!StringUtils.isEmpty(cellValue)){
				    		switch (cell.getColumnIndex()) {
				    		    //序号
					    		case 0:
					    			equipment.setEquipmentNo(cellValue);
					    			equipmentNo = cellValue;
					    		break;	
				                //辖区
					    		case 1:
					    			equipment.setEquipmentGroup(cellValue);
					    			equipmentGroup = cellValue;
					    		break;
				                //维修类别
					    		case 2:
					    			equipment.setEquipmentSubGroup(cellValue);
					    			equipmentSubGroup = cellValue;
					    		break;
				                //单位名称
					    		case 3:
					    			equipment.setEquipmentName(cellValue);
					    			equipmentName = cellValue;
					    		break;
					    		  //维修级别
					    		case 4:
					    			equipment.setEquipmentLevel(cellValue);
					    			equipmentLevel = cellValue;
					    		break;
					    		  //维修单位范围
					    		case 5:
					    			equipment.setEquipmentCompany(cellValue);
//					    			equipmentCompany = cellValue;
					    			if(cellValue.contains(",")){
					    				equipmentCompany = cellValue.replace(",", "、");
					    			}
					    			else{
					    				equipmentCompany = cellValue;
					    			}
					    		break;
					    		  //期限
					    		case 6:
					    			equipment.setEquipmentLimit(cellValue);
					    			equipmentLimit = cellValue;
					    		break;
					    		  //备注
					    		case 7:
					    			equipment.setRemark(cellValue);
					    			equipmentRemark = cellValue;
					    		break;
					    	}
			    		}
					}
					if(!StringUtils.isEmpty(equipmentNo) && !StringUtils.isEmpty(equipmentLevel) && equipmentNo.trim().matches("[0-9]*")){
						if(!hm.containsKey(equipmentNo)){
							equipment.setCreateBy(user.getUsername());
							equipment.setLastModifyBy(user.getUserName());
							equipment.setCreateId(user.getUpdateId());
							equipment.setUpdateId(user.getUpdateId());
							equipment.setDeleteFlag(Constants.DELETE_FLAG_FALSE); //未删除
							equipments.add(equipment);
						}else{
							Equipment equipment1 = new Equipment();
							equipment1.setEquipmentNo(equipmentNo);
							equipment1.setEquipmentCompany(equipmentCompany);
							equipment1.setEquipmentGroup(equipmentGroup);
							equipment1.setEquipmentLevel(equipmentLevel);
							equipment1.setEquipmentLimit(equipmentLimit);
							equipment1.setEquipmentSubGroup(equipmentSubGroup);
							equipment1.setEquipmentName(equipmentName);
							equipment1.setRemark(equipmentRemark);
							equipment1.setCreateBy(user.getUsername());
							equipment1.setLastModifyBy(user.getUserName());
							equipment1.setCreateId(user.getUpdateId());
							equipment1.setUpdateId(user.getUpdateId());
							equipment1.setDeleteFlag(Constants.DELETE_FLAG_FALSE); //未删除
							equipments.add(equipment1);
							equipmentLevel = "";
							
						}
					}
					hm.put(equipmentNo, equipmentNo);
//					else{
//						
//					}
				}
			}
		}
		
		return batchCommit(equipments, EquipmentRepository.class);
	}
	

	
	@Override
	protected void deleteAllData() {
		AdminUser user = IndetificationUtil.getAdminUser();
		List<Equipment> list = equipmentRepository.findAllEquipmentsForExport(null);
//		String equipmentIds = list.stream().map(x -> x.getEquipmentIdToString())
//				.collect(Collectors.joining(",", "", ""));
 //		equipmentRepository.deleteAllEquipments();
		for(Equipment equipmentL : list){
			Equipment equipment = new Equipment();
			equipment.setDeleteFlag(Constants.DELETE_FLAG_TRUE);
			equipment.setLastModifyBy(user.getUserName());
			equipment.setEquipmentId(equipmentL.getEquipmentId());
			equipmentRepository.updateAllEquipment(equipment);
		}
	}


	@Override
	protected void writeCells(HSSFWorkbook wb, Sheet sheet, CellStyle cs,Equipment equipmentParam) {
		//取数据
 		List<Equipment> equipments = findAllEquipmentsForExport(equipmentParam);

	    if(CollectionUtils.isNotEmpty(equipments)) {
	    	String equipmentName ="";
	    	int sameLine = 0;
	    	boolean flag = false;
	    	for(int index = 0 ; index < equipments.size() ; index++){
	    		Equipment equipment = equipments.get(index);
	    		System.out.println("INDEX:"+index +",equipments.getEquipmentName:"+equipment.getEquipmentName());
	    		Row row = sheet.createRow(index + 3);
	    		Cell cell = row.createCell(0);
	    		if(!equipmentName.equals(equipment.getEquipmentName())){
		    		//序号
					cell.setCellValue(equipment.getEquipmentNo());
					cell.setCellStyle(cs);
					
					//专业 
		    		cell = row.createCell(1);
					cell.setCellValue(equipment.getEquipmentGroup());
					cell.setCellStyle(cs);
					
					//专业类别
					cell = row.createCell(2);
					cell.setCellValue(equipment.getEquipmentSubGroup());
					cell.setCellStyle(cs);
									
					equipmentName =equipment.getEquipmentName();
					//设备名称
					cell = row.createCell(3);
					cell.setCellValue(equipment.getEquipmentName());
					cell.setCellStyle(cs);
					flag = true;
	    		}else{
	    			//序号
//		    		Cell cell = row.createCell(0);
					cell.setCellValue("");
					cell.setCellStyle(cs);
					
					//专业 
		    		cell = row.createCell(1);
					cell.setCellValue("");
					cell.setCellStyle(cs);
					
					//专业类别
					cell = row.createCell(2);
					cell.setCellValue("");
					cell.setCellStyle(cs);
									
					
					//设备名称
					cell = row.createCell(3);
					cell.setCellValue("");
					cell.setCellStyle(cs);
					++sameLine;
					flag = false;
					
	    		}
				
				//修别
				cell = row.createCell(4);
				cell.setCellValue(equipment.getEquipmentLevel());
				cell.setCellStyle(cs);
				
				//陈修单位选择范围
				cell = row.createCell(5);
				cell.setCellValue(equipment.getEquipmentCompany());
				cell.setCellStyle(cs);
				
				//有效期限
				cell = row.createCell(6);
				cell.setCellValue(equipment.getEquipmentLimit());
				cell.setCellStyle(cs);
				
				//备注
				cell = row.createCell(7);
				cell.setCellValue(equipment.getRemark());
				cell.setCellStyle(cs);
				
				if(sameLine != 0 && flag){
					 /* 
			         * 设定合并单元格区域范围 
			         *  firstRow  0-based 
			         *  lastRow   0-based 
			         *  firstCol  0-based 
			         *  lastCol   0-based 
			         */  
					CellRangeAddress cra = new CellRangeAddress(3 + index -(sameLine+1),
							3 + index + sameLine -(sameLine+1), 0, 0);
					// 在sheet里增加合并单元格
					sheet.addMergedRegion(cra);
					CellRangeAddress cra1 = new CellRangeAddress(3 + index -(sameLine+1),
							3 + index + sameLine -(sameLine+1), 1, 1);
					// 在sheet里增加合并单元格
					sheet.addMergedRegion(cra1);
					CellRangeAddress cra2 = new CellRangeAddress(3 + index -(sameLine+1),
							3 + index + sameLine -(sameLine+1), 2, 2);
					// 在sheet里增加合并单元格
					sheet.addMergedRegion(cra2);
					CellRangeAddress cra3 = new CellRangeAddress(3 + index -(sameLine+1),
							3 + index + sameLine -(sameLine+1), 3, 3);
					// 在sheet里增加合并单元格
			        sheet.addMergedRegion(cra3); 
			        sameLine = 0;
				}
				
				if(index == equipments.size()-1 && sameLine != 0){
					CellRangeAddress cra = new CellRangeAddress(3 + index -1,
							3 + index + sameLine -1, 0, 0);
					// 在sheet里增加合并单元格
					sheet.addMergedRegion(cra);
					CellRangeAddress cra1 = new CellRangeAddress(3 + index -1,
							3 + index + sameLine -1, 1, 1);
					// 在sheet里增加合并单元格
					sheet.addMergedRegion(cra1);
					CellRangeAddress cra2 = new CellRangeAddress(3 + index -1,
							3 + index + sameLine -1, 2, 2);
					// 在sheet里增加合并单元格
					sheet.addMergedRegion(cra2);
					CellRangeAddress cra3 = new CellRangeAddress(3 + index -1,
							3 + index + sameLine -1, 3, 3);
					// 在sheet里增加合并单元格
			        sheet.addMergedRegion(cra3); 
				}
	    	}
	    }
	}


	@Override
	protected void batchInsertInfo(EquipmentRepository mapper, Equipment equipment) {
		equipmentRepository.importEquipments(equipment);
	}


	public int updateEquipment(Equipment equipment) {
		return equipmentRepository.updateEquipment(equipment);
	}


	public Equipment findEquipmentById(Equipment select) {
		return equipmentRepository.findEquipmentById(select);
	}
}
