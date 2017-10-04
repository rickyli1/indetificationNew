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

import org.apache.commons.lang3.StringUtils;
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

import com.main.java.model.AdminUser;
import com.main.java.model.Equipment;
import com.main.java.service.EquipmentService;
import com.main.java.utils.Constants;
import com.main.java.utils.ExcelUtil;
import com.main.java.utils.IndetificationUtil;
import com.main.java.utils.PageUtil;
import com.main.java.utils.TimeUtils;


@Controller
@RequestMapping("/equipment")
public class EquipmentController {
	@Autowired
	private EquipmentService equipmentService;
	
	//页面初始查询
	@RequestMapping("/init")
	public String repairerInit(Model model) {
		Equipment equipment = new Equipment();
		doSearch(1, model, equipment);//查看第一页

		return "equipment/search";
	}
	
	//根据条件查询数据
	@RequestMapping("/search/{page}")
	public String searchRepairers(@PathVariable int page, Model model,@RequestBody Equipment equipment) {
		doSearch(page, model, equipment);
		return "/equipment/list";
	}
	
	//导入设备
	@RequestMapping(value="/import",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> importQestion(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		Map<String, String> response = new HashMap<String, String>();;
		
		//判断文件是否为空
		if(file == null || file.isEmpty()) {
			response.put("message", "文件为空！");
			return response;
		}
		
		try(InputStream fileStream = file.getInputStream()) {
			Sheet sheet = ExcelUtil.createSheet(file.getInputStream(), file.getContentType());
			equipmentService.importEquipments(sheet);
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
	public String exportRepairers( Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String equipmentGroup = request.getParameter("equipmentGroup");
		String equipmentName = request.getParameter("equipmentName");
		String equipmentSubGroup = request.getParameter("equipmentSubGroup");
		String equipmentLevel = request.getParameter("equipmentLevel");
	
		
		Equipment equipment = new Equipment();
		//设备名
		if(!StringUtils.isEmpty(equipmentName)){
			equipmentName=new String(equipmentName.getBytes( "iso-8859-1" ), "UTF-8" );
			equipment.setEquipmentName("%"+equipmentName.trim()+"%");
		}
		//设备专业		
		if(!StringUtils.isEmpty(equipmentGroup)){
			equipmentGroup=new String(equipmentGroup.getBytes( "iso-8859-1" ), "UTF-8" );
			equipment.setEquipmentGroup("%"+equipmentGroup.trim()+"%");
		}
		//设备子专业				
		if(!StringUtils.isEmpty(equipmentSubGroup)){
			equipmentSubGroup=new String(equipmentSubGroup.getBytes( "iso-8859-1" ), "UTF-8" );
			equipment.setEquipmentSubGroup("%"+equipmentSubGroup.trim()+"%");
		}
		//设备级别				
		if(!StringUtils.isEmpty(equipmentLevel)){
			equipmentLevel=new String(equipmentLevel.getBytes( "iso-8859-1" ), "UTF-8" );
			equipment.setEquipmentLevel(equipmentLevel.trim());
		}
		OutputStream os = null;  
		Workbook wb = null;    //工作薄

		try {		
			//导出Excel文件数据
			Resource res2 = new ClassPathResource("resources/export/承修单位选择范围.xls"); 
			
			String sheetName="承修单位选择范围";  
			wb = equipmentService.writeNewExcel(res2.getFile(),equipment); 
			
			String fileName= sheetName + "_" +TimeUtils.getStringFromTime(new Date(), TimeUtils.FORMAT_DATE_NO) +".xls";
		    response.setContentType("application/vnd.ms-excel");
		    response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "utf-8"));
		    os = response.getOutputStream();
			wb.write(os);  
		    
		} catch (Exception e) {
			model.addAttribute("msg", "导人失败！");	
			model.addAttribute("url", "equipment/init"); 
			return "common/alert";
		} finally{
			os.flush();
			os.close();
			wb.close();
		} 
		model.addAttribute("msg", "导人成功！");
		model.addAttribute("url", "equipment/init"); 
	    return "common/alert";
   }
	
	//按条件查询
	private void  doSearch(int page, Model model, Equipment equipmentParam) {
		Equipment equipment = new Equipment();
		equipment.setStartNo(PageUtil.getStartNo(page, Constants.PAGE_SIZE));
		equipment.setPageSize(Constants.PAGE_SIZE);
		//设备名
		if(!StringUtils.isEmpty(equipmentParam.getEquipmentName())){
			equipment.setEquipmentName("%"+equipmentParam.getEquipmentName().trim()+"%");
		}
		//专业
		if(!StringUtils.isEmpty(equipmentParam.getEquipmentGroup())){
			equipment.setEquipmentGroup("%"+equipmentParam.getEquipmentGroup().trim()+"%");
		}
		//子专业				
		if(!StringUtils.isEmpty(equipmentParam.getEquipmentSubGroup())){
			equipment.setEquipmentSubGroup("%"+equipmentParam.getEquipmentSubGroup().trim()+"%");
		}
		//级别		
		if(!StringUtils.isEmpty(equipmentParam.getEquipmentLevel())){
			equipment.setEquipmentLevel(equipmentParam.getEquipmentLevel().trim());
		}
		int totalCount = equipmentService.findAllEquipmentsCount(equipment);
		List<Equipment> equipments = equipmentService.findAllEquipments(equipment);
		model.addAttribute("totalPage", PageUtil.getTotalPage(totalCount, Constants.PAGE_SIZE));
		model.addAttribute("pageSize", Constants.PAGE_SIZE);
		model.addAttribute("page", page);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("equipments", equipments);
		
	}
	
	@RequestMapping("/deleteEquipment")
	public String deleteEquipment(Model model, @RequestBody String equipmentId) {
		Equipment equipmentModel = new Equipment();
		AdminUser user = IndetificationUtil.getAdminUser();
		// 执行删除
		int result = -1;
		if(equipmentId != null && !"".equals(equipmentId)){
			//软删除数据
			equipmentModel.setDeleteFlag(Constants.DELETE_FLAG_TRUE);
			equipmentModel.setLastModifyBy(user.getUsername());
			equipmentModel.setEquipmentId(Integer.parseInt(equipmentId));
			result = equipmentService.updateEquipment(equipmentModel);
		}
		if (result > 0) {
			model.addAttribute("msg", "删除设备成功!");
		} else {
			model.addAttribute("msg", "删除设备失败!");
		}
		model.addAttribute("url", "equipment/init");
		return "common/alert";
	}
	
	//修改或插入设备信息
	@RequestMapping("/updateEquipment")
	public String updateEquipment(Model model, @RequestBody Equipment equipmentModel) {
		int result = -1;
		AdminUser user = IndetificationUtil.getAdminUser();
		//修改信息
		if(equipmentModel != null && !"".equals(equipmentModel.getEquipmentId())){
			equipmentModel.setLastModifyBy(user.getUsername());
			result = equipmentService.updateEquipment(equipmentModel);
		}
		if (result > 0) {
			model.addAttribute("msg", "更新设备成功!");
		} else {
			model.addAttribute("msg", "更新设备失败!");
		}
		model.addAttribute("url", "equipment/init");

		return "common/alert";
	}
	
	@ResponseBody
	@RequestMapping("/updateInit")
	public Equipment updateInit(Model model,@RequestBody Equipment select) {
		Equipment equipment = new Equipment();
		equipment = this.equipmentService.findEquipmentById(select);
		return equipment;
	}
}
