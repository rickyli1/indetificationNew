package com.main.java.service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.java.model.AdminUser;
import com.main.java.model.Repairer;
import com.main.java.repository.RepairRepository;
import com.main.java.utils.ExcelUtil;
import com.main.java.utils.IndetificationUtil;

@Service
public class RepairerService extends BaseImportService<RepairRepository, Repairer>{
	
	@Autowired
	private RepairRepository repairRepository;
	
	public int getRepairersCount() {
		return repairRepository.findAllRepairersCount();
	}
	
	
	public List<Repairer> findAllRepairersForExport() {
		return repairRepository.findAllRepairersForExport();
	}
	
	public List<Repairer> findAllRepairers(Repairer repairer){
		return  repairRepository.findAllRepairers(repairer);
	}
	
	public int findRepairersCount() {
		return repairRepository.findAllRepairersCount();
	}
	


	public String importRepairers(Sheet sheet) {
		List<Repairer> repairers = new ArrayList<>();
		
		AdminUser user = IndetificationUtil.getAdminUser();
		
		for (Row row : sheet) {
			if (row.getRowNum() < 1) {
				continue;
			}else{
				Repairer repairer = new Repairer();
				for (Cell cell : row) {
		    		String cellValue = ExcelUtil.getCellValue(cell);
		    		
		    		switch (cell.getColumnIndex()) {
		    		    //序号
			    		case 0:
			    			repairer.setRepairerNo(cellValue);
			    		break;	
			    			
		                //辖区
			    		case 1:
			    			repairer.setRepairerArea(cellValue);
			    		break;
			    		
		                //修理单位名称
			    		case 2:
			    			repairer.setRepairerName(cellValue);
			    		break;
			    		
		                //单位性质
			    		case 3:
			    			repairer.setRepairerLevel(cellValue);
			    		break;
			    	}
		    		
				}
				repairer.setCreateor(user.getUsername());
				repairer.setUpdateor(user.getUserName());
				repairer.setCreateId(user.getUpdateId());
				repairer.setUpdateId(user.getUpdateId());
				repairers.add(repairer);
			}
		}
		
		return batchCommit(repairers, RepairRepository.class);
	}
	

	@Override
	protected void batchInsertInfo(RepairRepository repository, Repairer bean) {
		repairRepository.importRepairers(bean);
	}
	
	@Override
	protected void deleteAllData() {
		repairRepository.deleteAllRepairers();
	}


	@Override
	protected void writeCells(HSSFWorkbook wb, Sheet sheet, CellStyle cs, Repairer bean) {
		//取数据
		List<Repairer> repairers = findAllRepairersForExport();

	    if(CollectionUtils.isNotEmpty(repairers)) {
	    	
	    	IntStream.range(0, repairers.size()).forEach(index -> {
	    		Repairer repairer = repairers.get(index);
	    		Row row = sheet.createRow(index + 1);
	    		
	    		//序号
	    		Cell cell = row.createCell(0);
				cell.setCellValue(repairer.getRepairerNo());
				cell.setCellStyle(cs);
				
				//辖区 
	    		cell = row.createCell(1);
				cell.setCellValue(repairer.getRepairerArea());
				cell.setCellStyle(cs);
				
				//修理单位 
				cell = row.createCell(2);
				cell.setCellValue(repairer.getRepairerName());
				cell.setCellStyle(cs);
								
				
				//单位性质
				cell = row.createCell(3);
				cell.setCellValue(repairer.getRepairerLevel());
				cell.setCellStyle(cs);
	    	});
	    }
	}
}
