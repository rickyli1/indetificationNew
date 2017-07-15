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
import com.main.java.model.Application;
import com.main.java.repository.ApplicationRepository;
import com.main.java.utils.ExcelUtil;
import com.main.java.utils.IndetificationUtil;

@Service
public class ApplicationService extends BaseImportService<ApplicationRepository, Application>{
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	public List<Application> findAllApplicationsForExport() {
		return applicationRepository.findAllApplicationsForExport();
	}
	
	public List<Application> findAllApplications(Application application){
		return  applicationRepository.findAllApplications(application);
	}
	
	public int findApplicatiosCount() {
		return applicationRepository.findApplicationsCount();
	}
	
	public String importRepairers(Sheet sheet) {
		List<Application> applications = new ArrayList<>();
		
		AdminUser user = IndetificationUtil.getAdminUser();
		
		for (Row row : sheet) {
			if (row.getRowNum() < 1) {
				continue;
			}else{
				Application application = new Application();
				for (Cell cell : row) {
		    		String cellValue = ExcelUtil.getCellValue(cell);
		    		
		    		switch (cell.getColumnIndex()) {
		    		    //申请日期
			    		case 0:
			    			application.setApplicationDate(cellValue);
			    		break;	
			    			
		                //申请单位
			    		case 1:
			    			application.setApplicationRepairer(cellValue);
			    		break;
			    		
		                //装备管理机关
			    		case 2:
			    			application.setEquipmentManager(cellValue);
			    		break;
			    		
		                //申请装备专业
			    		case 3:
			    			application.setEquimentGroup(cellValue);
			    		break;
			    		
			           //申请装备型号名称
				       case 4:
				           application.setEquimentName(cellValue);
                       break;
                       
				       //申请级别
					   case 5:
					       application.setRepairerLevel(cellValue);
			    		break;
			    		
			    	   //申请经历
					   case 6:
					       application.setRepairerHistory(cellValue);
					   break;
					   
					   //备注
					   case 7:
					       application.setRemark(cellValue);
					   break;				    	
			    	}
		    		
				}
				application.setCreateor(user.getUsername());
				application.setUpdateor(user.getUserName());
				application.setCreateId(user.getUpdateId());
				application.setUpdateId(user.getUpdateId());
				applications.add(application);
			}
		}
		
		return batchCommit(applications, ApplicationRepository.class);
	}

	
	
	@Override
	protected void batchInsertInfo(ApplicationRepository repository, Application bean) {
		repository.importApplications(bean);
	}
	

	@Override
	protected void writeCells(HSSFWorkbook wb, Sheet sheet, CellStyle cs) {
		//取数据
		List<Application> applications = this.findAllApplicationsForExport();

	    if(CollectionUtils.isNotEmpty(applications)) {
	    	
	    	IntStream.range(0, applications.size()).forEach(index -> {
	    		Application application = applications.get(index);
	    		Row row = sheet.createRow(index + 1);
	    		
	    		//申请日期
	    		Cell cell = row.createCell(0);
				cell.setCellValue(application.getApplicationDate());
				cell.setCellStyle(cs);
				
				//申请单位
	    		cell = row.createCell(1);
				cell.setCellValue(application.getApplicationRepairer());
				cell.setCellStyle(cs);
				
				//装备管理机关
				cell = row.createCell(2);
				cell.setCellValue(application.getEquipmentManager());
				cell.setCellStyle(cs);
				
				//申请装备专业
				cell = row.createCell(3);
				cell.setCellValue(application.getEquimentGroup());
				cell.setCellStyle(cs);
				
				
				//申请装备型号名称
				cell = row.createCell(4);
				cell.setCellValue(application.getEquimentName());
				cell.setCellStyle(cs);
				
				
				//申请级别
				cell = row.createCell(5);
				cell.setCellValue(application.getRepairerLevel());
				cell.setCellStyle(cs);
				
				
				
				//申请经历
				cell = row.createCell(6);
				cell.setCellValue(application.getRepairerHistory());
				cell.setCellStyle(cs);
				
				
				//备注
				cell = row.createCell(7);
				cell.setCellValue(application.getRemark());
				cell.setCellStyle(cs);
			});
	    }
	}
}