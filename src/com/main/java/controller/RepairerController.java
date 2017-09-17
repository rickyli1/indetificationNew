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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.main.java.model.Repairer;
import com.main.java.service.RepairerService;
import com.main.java.utils.Constants;
import com.main.java.utils.ExcelUtil;
import com.main.java.utils.PageUtil;
import com.main.java.utils.TimeUtils;


@Controller
@RequestMapping("/repairer")
public class RepairerController {
	@Autowired
	private RepairerService repairerService;
	
	@RequestMapping("/init")
	public String repairerInit(Model model) {
		doSearch(1, model);

		return "repairer/search";
	}
	
	@RequestMapping("/search/{page}")
	public String searchRepairers(@PathVariable int page, Model model) {
		doSearch(page, model);
		return "/repairer/list";
	}

	
	
	@RequestMapping(value="/import",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> importQestion(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		Map<String, String> response = new HashMap<String, String>();
		
		if(file == null || file.isEmpty()) {
			response.put("message", "文件为空！");
			return response;
		}
		
		try(InputStream fileStream = file.getInputStream()) {
			Sheet sheet = ExcelUtil.createSheet(file.getInputStream(), file.getContentType());
			repairerService.importRepairers(sheet);
			response.put("message", "导人成功！");
			return response;
		}catch(Exception e) {
			response.put("message", "导入失败！"+e.getMessage());
			return response;
		}
	}	

	/**
	 * 描述：通过 jquery.form.js 插件提供的ajax方式导出Excel
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value ="/export")
	public String exportRepairers(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		OutputStream os = null;  
		Workbook wb = null;    //工作薄

		try {		
			//导出Excel文件数据
			Resource res2 = new ClassPathResource("resources/export/辖区单位.xls"); 
			
			String sheetName="辖区修理单位对应表";  
			wb = repairerService.writeNewExcel(res2.getFile(), null); 
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
		model.addAttribute("url", "repairer/init"); 
		 
	   return "common/alert";
		
   }
	
	private void  doSearch(int page, Model model) {
		Repairer repairer = new Repairer();
		repairer.setStartNo(PageUtil.getStartNo(page, Constants.PAGE_SIZE));
		repairer.setPageSize(Constants.PAGE_SIZE);
		
		int totalCount = repairerService.findRepairersCount();
		List<Repairer> repairers = repairerService.findAllRepairers(repairer);
		model.addAttribute("totalPage", PageUtil.getTotalPage(totalCount, Constants.PAGE_SIZE));
		model.addAttribute("pageSize", Constants.PAGE_SIZE);
		model.addAttribute("page", page);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("repairers", repairers);
		
	}
}
