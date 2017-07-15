package com.main.java.utils;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	
	private static final String EXCEL_TYPE_XLS = "application/vnd.ms-excel";
	private static final String EXCEL_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	private static DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	@SuppressWarnings("resource")
	public static Sheet createSheet(InputStream in, String fileType)
			throws Exception {
		Workbook wb = null;
		if (fileType == null) {
			fileType = "";
		}
		if (EXCEL_TYPE_XLS.equals(fileType.toLowerCase())) {
			wb = new HSSFWorkbook(in);
		} else if (EXCEL_TYPE_XLSX.equals(fileType.toLowerCase())) {
			wb = new XSSFWorkbook(in);
		}
		if (wb == null) {
			throw new Exception("[ERROR]Excel file is null.");
		}
		Sheet sheet = wb.getSheetAt(0);
		if (sheet.getFirstRowNum() == sheet.getLastRowNum()) {
			throw new Exception("[ERROR]there is no data in Excel file .");
		}
		return sheet;
	}
	
	@SuppressWarnings("deprecation")
	public static String getCellValue(Cell cell) {
		if (cell == null) {
			return StringUtils.EMPTY;
		}
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue();
			case Cell.CELL_TYPE_NUMERIC:
				DecimalFormat formatter = new DecimalFormat("######## ");
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					return DEFAULT_DATE_FORMAT.format(cell.getDateCellValue());
				} else {
					return formatter.format(cell.getNumericCellValue());
				}
			case Cell.CELL_TYPE_FORMULA:
				return cell.getCellFormula();
			case Cell.CELL_TYPE_BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			case Cell.CELL_TYPE_ERROR:
				return StringUtils.EMPTY;
			default:
				return StringUtils.EMPTY;
		}
	}
	
	
	/**
	 * 描述：设置简单的Cell样式
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static CellStyle setSimpleCellStyle(Workbook wb) {
		CellStyle cs = wb.createCellStyle();

		cs.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
		cs.setBorderLeft(CellStyle.BORDER_THIN);// 左边框
		cs.setBorderTop(CellStyle.BORDER_THIN);// 上边框
		cs.setBorderRight(CellStyle.BORDER_THIN);// 右边框

//		cs.setAlignment(CellStyle.ALIGN_CENTER); // 居中
		cs.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中  
		cs.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直  
		return cs;
	}

}
