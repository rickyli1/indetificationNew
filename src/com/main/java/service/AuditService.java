package com.main.java.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.main.java.model.Application;
import com.main.java.model.Repairer;
import com.main.java.repository.ApplicationRepository;
import com.main.java.repository.AuditRepository;
import com.main.java.utils.ExcelUtil;

@Service
public class AuditService {
	@Autowired
	private AuditRepository auditRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	public List<Application> findAllApplications(Application application){
		List<Application> applicationList =  auditRepository.findAllApplications(application);
		
		setAreaRepairInfos(applicationList);
		  
		return applicationList;
	}
	
	public int findApplicatiosCount(Application searchParams) {
		return auditRepository.findApplicationsCount(searchParams);
	}
	
	
	//保存结论信息
	 @Transactional
	public void saveApplicationInfo(Application updateParams) {
		 auditRepository.saveApplicationInfo(updateParams);
	}

	
	//获得辖区内所有修理厂商和级别
	private void setAreaRepairInfos(List<Application> applicationList) {
		if(CollectionUtils.isNotEmpty(applicationList)) {
			applicationList.parallelStream().forEach(item -> {
				List<String> areaRepairInfos = new ArrayList<>();
				List<Repairer> infos = auditRepository.findAreaRepairInfos(item);
				Map<String, List<Repairer>> repairMap = infos.stream().collect(Collectors.groupingBy(Repairer :: getRepairerLevel));
				
				repairMap.forEach((key, values) -> {
					String repairNames = values.stream().map(Repairer::getRepairerName).collect(Collectors.joining(","));
					areaRepairInfos.add(key + ": " + repairNames);
				});
				
				item.setAreaRepairInfos(areaRepairInfos);
			
			});
		}
	}

	public Workbook writeNewExcel(File file, Application equipment) throws IOException {
		
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
	       // 打开HSSFWorkbook
        POIFSFileSystem fs = new POIFSFileSystem(in);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
		Sheet sheet = wb.getSheetAt(0);

		// 循环插入数据
		CellStyle cs = ExcelUtil.setSimpleCellStyle(wb); // Excel单元格样式
		
		writeCells(wb, sheet, cs, equipment);
		
		return wb;
	}
 
	protected void writeCells(HSSFWorkbook wb, Sheet sheet, CellStyle cs, Application bean) {
		//取数据
		List<Application> applications = applicationRepository.findAllApplicationAuditForExport(bean);

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
				
				//有没有认可任务
				cell = row.createCell(7);
				cell.setCellValue(application.getHaveSuccesWwork());
				cell.setCellStyle(cs);
				
				//辖区同型号修理能力总体评价结论
				cell = row.createCell(8);
				cell.setCellValue(application.getAreaHaveAbility());
				cell.setCellStyle(cs);
				
				//辖区同型号修理能力具体情况
				cell = row.createCell(9);
				cell.setCellValue(application.getAreaRepairInfo());
				cell.setCellStyle(cs);
				
				//机关批复
				cell = row.createCell(10);
				cell.setCellValue(application.getOrginizationResult());
				cell.setCellStyle(cs);
				
				//备注
				cell = row.createCell(11);
				cell.setCellValue(application.getRemark());
				cell.setCellStyle(cs);
			});
	    }
	}


}
