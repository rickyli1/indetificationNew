package com.main.java.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.java.model.Application;
import com.main.java.model.Repairer;
import com.main.java.repository.AuditRepository;

@Service
public class AuditService {
	@Autowired
	private AuditRepository auditRepository;
	
	public List<Application> findAllApplications(Application application){
		List<Application> applicationList =  auditRepository.findAllApplications(application);
		
		setAreaRepairInfos(applicationList);
		  
		return applicationList;
	}
	
	public int findApplicatiosCount(Application searchParams) {
		return auditRepository.findApplicationsCount(searchParams);
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

}
