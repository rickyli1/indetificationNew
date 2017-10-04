package com.main.java.repository;

import java.util.List;

import com.main.java.model.ResultFile;

public interface ResultFileRepository {
	//按页查询结论文件
	public List<ResultFile> findResultFileList(ResultFile resultFile);
	//获取文件数量	
	public int findReulstFileCount(ResultFile resultFile);
	//插入结论文件
	public int insertResultFile(ResultFile resultFile);
	
}
