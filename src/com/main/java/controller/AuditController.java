package com.main.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.main.java.model.Application;
import com.main.java.service.AuditService;
import com.main.java.utils.Constants;
import com.main.java.utils.PageUtil;

@Controller
@RequestMapping("/audit")
public class AuditController {
	@Autowired
	private AuditService auditService;
	
	@RequestMapping("/init")
	public String applicationInit(Model model) {
		Application searchParams = new Application();
		doSearch(1, model, searchParams);

		return "audit/search";
	}
	
	
	private void  doSearch(int page, Model model, Application searchParams) {
		searchParams.setStartNo(PageUtil.getStartNo(page, Constants.PAGE_SIZE));
		searchParams.setPageSize(Constants.PAGE_SIZE);
		
		int totalCount = auditService.findApplicatiosCount(searchParams);
		List<Application> applications = auditService.findAllApplications(searchParams);
		model.addAttribute("totalPage", PageUtil.getTotalPage(totalCount, Constants.PAGE_SIZE));
		model.addAttribute("pageSize", Constants.PAGE_SIZE);
		model.addAttribute("page", page);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("applications", applications);
		
	}	

}
