package com.main.java.controller;

import java.io.InputStream;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.main.java.service.RepairerService;
import com.main.java.utils.ExcelUtil;


@Controller
@RequestMapping("/repairer")
public class RepairerController {
	@Autowired
	private RepairerService repairerService;
	
	@RequestMapping("/init")
	public String repairerInit() {
		return "/repairer/search";
	}
	
	@RequestMapping("/search/{page}")
	public String searchRepairers(@PathParam(value = "page") int page) {
		
		return "/repairer/list";
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
			repairerService.importRepairers(sheet);
			response.put("message", "导人成功！");
			return response;
		}catch(Exception e) {
			response.put("message", "导入失败！"+e.getMessage());
			return response;
		}
	}	

	
	
}
