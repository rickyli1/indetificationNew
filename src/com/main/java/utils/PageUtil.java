package com.main.java.utils;

public class PageUtil {

	/**
	 * 获得翻页的起始值
	 */
	public static int getStartNo(int page, int pageSize) {
		return ((page == 0 ? 1 : page ) -1 ) * pageSize ;
	}

	/**
	 * 获得总页数
	 */	
	public static int getTotalPage(int totalCount, int pageSize) {
		int tempPage = totalCount / pageSize;
		
		if(totalCount % pageSize != 0) {
			tempPage ++;
		}
		
		return tempPage;
	}
}