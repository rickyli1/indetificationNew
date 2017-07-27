package com.main.java.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.main.java.model.Application;
import com.main.java.service.ApplicationService;
import com.main.java.utils.Constants;
import com.main.java.utils.ExcelUtil;
import com.main.java.utils.PageUtil;
import com.main.java.utils.TimeUtils;

@Controller
@RequestMapping("/application")
public class ApplicationController {
	@Autowired
	private ApplicationService applicationService;
	
	@RequestMapping("/init")
	public String applicationInit(Model model) {
		Application searchParams = new Application();
		doSearch(1, model, searchParams);

		return "application/search";
	}
	
	@RequestMapping("/search/{page}")
	public String searchRepairers(@PathVariable int page, Model model, @RequestBody Application searchParams) {
		doSearch(page, model, searchParams);
		return "/application/list";
	}

	
	
	@RequestMapping(value="/import",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> importQestion(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		Map<String, String> response = new HashMap<String, String>();;
		
		if(file == null || file.isEmpty()) {
			response.put("message", "文件为空！");
			return response;
		}
		
		try(InputStream fileStream = file.getInputStream()) {
			Sheet sheet = ExcelUtil.createSheet(file.getInputStream(), file.getContentType());
			applicationService.importRepairers(sheet);
			response.put("message", "导人成功！");
			return response;
		}catch(Exception e) {
			response.put("message", "导入失败！"+e.getMessage());
			return response;
		}
	}	

	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value ="/export")

	public String exportRepairers(Model model,HttpServletRequest request,HttpServletResponse response, @RequestParam(value="applicationDate", required=false)String applicationDate
			, @RequestParam(value="applicationRepairer", required=false)String applicationRepairer, @RequestParam(value="equimentName", required=false)String equimentName
			, @RequestParam(value="orderType", required=false)String orderType) throws Exception {
		OutputStream os = null;  
		Workbook wb = null;    //工作薄
		
		Application application = new Application(); 
		application.setApplicationDate(applicationDate);
		application.setEquimentName(equimentName);
		application.setApplicationRepairer(applicationRepairer);
		application.setOrderType(orderType);

		try {		
			//导出Excel文件数据
			Resource res2 = new ClassPathResource("resources/export/认可申请表.xls"); 
			
			String sheetName="认可申请表";  
			wb = applicationService.writeNewExcel(res2.getFile(), application); 
			
			String fileName= sheetName + "_" +TimeUtils.getStringFromTime(new Date(), TimeUtils.FORMAT_DATE_NO) +".xls";
		    response.setContentType("application/vnd.ms-excel");
		    response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "utf-8"));
		    os = response.getOutputStream();
			wb.write(os);  
		    
		} catch (Exception e) {
			model.addAttribute("msg", "导人成功！");	
		} finally{
			os.flush();
			os.close();
			wb.close();
		} 
		
		model.addAttribute("msg", "导人成功！");
		model.addAttribute("url", "application/init"); 
		 
	   return "common/alert";
		
   }
	
	@RequestMapping(value ="/delete/{id}")
	public String deleteRepairById(Model model, @PathVariable int id) {
		applicationService.deleteRepairById(id);
		
		model.addAttribute("msg", "删除成功！");
		model.addAttribute("url", "application/init"); 
		 
	   return "common/alert";

	}
	
	@RequestMapping(value ="/update")
	public String updateRepair(Model model,@RequestBody Application updateParams) {
		applicationService.updateRepair(updateParams);
		
		model.addAttribute("msg", "修改成功！");
		model.addAttribute("url", "application/init"); 
		 
	   return "common/alert";

	}

	
	private void  doSearch(int page, Model model, Application searchParams) {
		searchParams.setStartNo(PageUtil.getStartNo(page, Constants.PAGE_SIZE));
		searchParams.setPageSize(Constants.PAGE_SIZE);
		
		int totalCount = applicationService.findApplicatiosCount(searchParams);
		List<Application> applications = applicationService.findAllApplications(searchParams);
		model.addAttribute("totalPage", PageUtil.getTotalPage(totalCount, Constants.PAGE_SIZE));
		model.addAttribute("pageSize", Constants.PAGE_SIZE);
		model.addAttribute("page", page);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("applications", applications);
		
	}
}