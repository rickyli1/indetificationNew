package com.main.java.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.main.java.model.Application;
import com.main.java.service.AuditService;
import com.main.java.utils.Constants;
import com.main.java.utils.PageUtil;
import com.main.java.utils.TimeUtils;

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
	
	@RequestMapping("/search/{page}")
	public String searchRepairers(@PathVariable int page, Model model, @RequestBody Application searchParams) {
		doSearch(page, model, searchParams);
		return "audit/list";
	}
	

	@RequestMapping(value ="/save")
	public String saveRepair(Model model,@RequestBody Application updateParams) {
		auditService.saveApplicationInfo(updateParams);
		
		model.addAttribute("msg", "修改成功！");
		model.addAttribute("url", "audit/init"); 
		 
	   return "common/alert";

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
	

	/**
	 * 描述：通过 jquery.form.js 插件提供的ajax方式导出Excel
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value ="/export")
	public String exportRepairers( Model model, HttpServletRequest request,HttpServletResponse response, @RequestParam(value="applicationDate", required=false)String applicationDate
			, @RequestParam(value="applicationRepairer", required=false)String applicationRepairer, @RequestParam(value="equimentName", required=false)String equimentName) throws Exception {
		
		Application application = new Application(); 
		application.setApplicationDate(applicationDate);
		application.setEquimentName(equimentName);
		application.setApplicationRepairer(applicationRepairer);
		OutputStream os = null;  
		Workbook wb = null;    //工作薄

		try {		
			//导出Excel文件数据
			Resource res2 = new ClassPathResource("resources/export/认可申请审核表.xls"); 
			
			String sheetName="认可申请审核表";  
			wb = auditService.writeNewExcel(res2.getFile(),application); 
			
			String fileName= sheetName + "_" +TimeUtils.getStringFromTime(new Date(), TimeUtils.FORMAT_DATE_NO) +".xls";
		    response.setContentType("application/vnd.ms-excel");
		    response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "utf-8"));
		    os = response.getOutputStream();
			wb.write(os);  
		    
		} catch (Exception e) {
			model.addAttribute("msg", "导人失败！");	
			model.addAttribute("url", "audit/init"); 
			return "common/alert";
		} finally{
			os.flush();
			os.close();
			wb.close();
		} 
		model.addAttribute("msg", "导人成功！");
		model.addAttribute("url", "audit/init"); 
	    return "common/alert";
   }


}
