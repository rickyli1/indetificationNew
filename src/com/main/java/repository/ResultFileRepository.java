package com.main.java.repository;

import java.util.List;

import com.main.java.model.ResultFile;

public interface ResultFileRepository {
	
	public List<ResultFile> findResultFileList(ResultFile resultFile);
		
	public int findReulstFileCount(ResultFile resultFile);
	
	public int insertResultFile(ResultFile resultFile);
	
}
