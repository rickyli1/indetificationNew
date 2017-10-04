package com.main.java.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.main.java.model.ResultFile;
import com.main.java.model.UploadFileBaseModel;
import com.main.java.service.ResultFileService;
import com.main.java.utils.Constants;
import com.main.java.utils.PageUtil;
import com.mongodb.gridfs.GridFSDBFile;

@Controller
@RequestMapping("/resultFile")
public class ResultFileController {
	@Autowired
	private ResultFileService resultFileService;
	
	//结论文件初始页面
	@RequestMapping("/init")
	public String resultFileInit(Model model) {
		ResultFile resultFile = new ResultFile();
		doSearch(1, model, resultFile);//查询第一页数据

		return "resultFile/search";
	}
	
	//数据添加页面
	@RequestMapping("/addInit")
	public String addInit() {
	    return "resultFile/add";
	}
	
	//查询按钮，翻页查询数据
	@RequestMapping("/search/{page}")
	public String searchRepairers(@PathVariable int page, Model model, @RequestBody ResultFile resultFile) {
		doSearch(page, model, resultFile);
		return "/resultFile/list";
	}
	
	//添加数据
	@RequestMapping(value ="/add")
	@ResponseBody
	public  Map<String, String> insertResultFile(Model model,@RequestBody ResultFile resultFile) {
		int result = resultFileService.insertResultFile(resultFile);
		Map<String, String> response = new HashMap<String, String>();
		
		 if(result > 0) {
			 response.put("msg", "success");
		 }else {
			 response.put("msg", "faile"); 
		 }
		 
		 
		return response;

	}
	
	//结论文件上传
	@ResponseBody
	@RequestMapping("/requestFileUpload")
	public Map<String, String> requestFileUpload(Model model, UploadFileBaseModel uploadFile, HttpServletResponse httpResponse) throws IOException {
		MultipartFile file = uploadFile.getFile();
		Map<String, String> response =  new HashMap<String, String>();
		
		//文件是否为空
		if(file.isEmpty()) {
			response.put("message", "文件为空！");
			return response;
		}	
		
		try(InputStream fileStream = file.getInputStream();){
			//文件上传mongo数据库
			String fileId = resultFileService.inserMongoFile(fileStream, file.getContentType(), file.getOriginalFilename());
			//返回数据
			response.put("fileName", file.getOriginalFilename());
			response.put("fileId", fileId);
			
			return response;
			
		 }catch(IOException e){
			 throw e;
		}
	}
	
	@RequestMapping("/fileDownload/{id}")
	public void init(@PathVariable String id, HttpServletResponse response) throws IOException {
		GridFSDBFile document = resultFileService.getMongoFile(id);
		 
		try(OutputStream os = response.getOutputStream()) {
		    response.reset();  
		    response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(document.getFilename().getBytes("utf-8"), "ISO8859-1") + "\"");  
		    response.addHeader("Content-Length", "" + document.getLength());  
		    response.setContentType("application/octet-stream;charset=UTF-8"); 

			 byte[] fileByte =	IOUtils.toByteArray(document.getInputStream());
			 os.write(fileByte);
			 os.flush();  
			 os.close();  
		}catch(IOException e){
			 throw e;
		}
	}
	
		
	private void  doSearch(int page, Model model, ResultFile resultFile) {
		resultFile.setStartNo(PageUtil.getStartNo(page, Constants.PAGE_SIZE));
		resultFile.setPageSize(Constants.PAGE_SIZE);
		
		int totalCount = resultFileService.findReulstFileCount(resultFile);
		List<ResultFile> resultFiles = resultFileService.findResultFileList(resultFile);
		model.addAttribute("totalPage", PageUtil.getTotalPage(totalCount, Constants.PAGE_SIZE));
		model.addAttribute("pageSize", Constants.PAGE_SIZE);
		model.addAttribute("page", page);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("resultFiles", resultFiles);
		
	}
}
