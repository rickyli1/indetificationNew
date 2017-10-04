package com.main.java.utils;

public class Constants {
	//返回成功结果 
	public static final String   RESULT_SUCCESS = "success";
	//返回失败结果
	public static final String   RESULT_FAIL = "FAIL";
	/**
	 * 删除标识 false 未删除
	 */
	public static final String DELETE_FLAG_FALSE = "0";
	
	/**
	 * 删除标识 TRUE 已删除
	 */
	public static final String DELETE_FLAG_TRUE = "1";
	
	//返回失败结果
	public static final String RESULT_FLAG_FALSE = "0";
	
	//返回成功结果
	public static final String RESULT_FLAG_TRUE = "1";
	
	/**
	 * 导入文件同项目内多个值分割符
	 */
	public static String splitConstant = "、";
	
	/**
	 * 解析excel 開始行與列
	 */
	public static int EXCEL_START_ROW = 3;
	public static int EXCEL_START_COLUMN = 1;

	public static String FILE_NAME= "承修单位选择范围";
	
	/**
	 * 页面一页显示几条
	 */
	public static int PAGE_SIZE = 30;

	/**
	 * 批量插入时候，每次提交的条数
	 */	
	public static int  COMMIT_COUNT_EVERY_TIME = 500;
}
