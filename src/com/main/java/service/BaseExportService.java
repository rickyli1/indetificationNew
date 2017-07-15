package com.main.java.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.main.java.utils.ExcelUtil;

public abstract class BaseExportService {
	 
	 public Workbook writeNewExcel(File file, String sheetName) throws Exception {
			
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		       // 打开HSSFWorkbook
	        POIFSFileSystem fs = new POIFSFileSystem(in);
	        HSSFWorkbook wb = new HSSFWorkbook(fs);
			Sheet sheet = wb.getSheet(sheetName);

			// 循环插入数据
			CellStyle cs = ExcelUtil.setSimpleCellStyle(wb); // Excel单元格样式
			
			writeCells(wb, sheet, cs);
			
			return wb;
		}
	 
	 protected abstract void writeCells(HSSFWorkbook wb, Sheet sheet, CellStyle cs);


}
