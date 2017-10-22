package com.main.java.service;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.main.java.model.AdminUser;
import com.main.java.model.Application;
import com.main.java.repository.ApplicationRepository;
import com.main.java.utils.ExcelUtil;
import com.main.java.utils.IndetificationUtil;

@Service
public class ApplicationService extends BaseImportService<ApplicationRepository, Application>{
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	//获取分页数据
	public List<Application> findAllApplications(Application application){
		return  applicationRepository.findAllApplications(application);
	}
	
	//获得数据总数
	public int findApplicatiosCount(Application searchParams) {
		return applicationRepository.findApplicationsCount(searchParams);
	}
	
	public String importRepairers(Sheet sheet) {
		List<Application> applications = new ArrayList<>();
		//获得用户信息
		AdminUser user = IndetificationUtil.getAdminUser();
		
		for (Row row : sheet) {
			//去掉头
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
				
				if(StringUtils.isNotBlank(application.getApplicationDate())) {
					applications.add(application);
				}
				
			}
		}
		//批量插入数据
		return batchCommit(applications, ApplicationRepository.class);
	}
	
	//根据主键删除数据
	 @Transactional
	 public void deleteRepairById(int id) {
		 applicationRepository.deleteRepairById(id);
	 }
	 
	 //更新申请信息
	 @Transactional
	 public void updateRepair(Application updateParams) {
		AdminUser user = IndetificationUtil.getAdminUser();
		updateParams.setUpdateor(user.getUserName());
		updateParams.setUpdateId(user.getUpdateId());

		 applicationRepository.updateRepair(updateParams);
	}
	 
	//批量插入申请信息
	@Override
	protected void batchInsertInfo(ApplicationRepository repository, Application bean) {
		repository.importApplications(bean);
	}
	
	//过滤掉重复的数据（申请时间+申请单位+设备名）
	@Override
	protected  List<Application> filterExistData(List<Application> list) {
		Application application = new Application();
		
		List<Application> applicationList = applicationRepository.findAllApplicationsForExport(application);
		
		List<String> applicationKeyList = applicationList.stream().map(Application :: getApplicationKey).collect(toList());
		
		//去掉excel重复数据
		List<Application> excelList = new ArrayList<>();
		for(int i = 0; i < list.size(); i++) {
			
			if(CollectionUtils.isEmpty(excelList)) {
				excelList.add(list.get(i));
			}else {
				boolean isExist = false;
				
				for(int j = 0; j < excelList.size(); j++) {
					if(StringUtils.equals(list.get(i).getApplicationKey(), excelList.get(j).getApplicationKey())) {
						isExist = true;
						break;
					}
				}
				
				if(!isExist) {
					excelList.add(list.get(i));
				}

			}
		}
				
		//申请时间+申请单位+设备名
		return excelList.stream().filter(item -> !applicationKeyList.contains(item.getApplicationKey())).collect(toList());
		 
	 }
	

	@Override
	protected void writeCells(HSSFWorkbook wb, Sheet sheet, CellStyle cs, Application bean) {
		//取数据
		List<Application> applications = applicationRepository.findAllApplicationsForExport(bean);

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