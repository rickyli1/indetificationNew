package com.main.java.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.java.model.AdminUser;
import com.main.java.model.ResultFile;
import com.main.java.repository.ResultFileRepository;
import com.main.java.utils.IndetificationUtil;
import com.mongodb.gridfs.GridFSDBFile;

@Service
public class ResultFileService {
	
	@Autowired
	private ResultFileRepository resultFileRepository;
	
	@Autowired
	private MonoFileUploadRepository monoFileUploadRepository;
	
	public List<ResultFile> findResultFileList(ResultFile resultFile) {
		return resultFileRepository.findResultFileList(resultFile);
	}
		
	public int findReulstFileCount(ResultFile resultFile) {
		return resultFileRepository.findReulstFileCount(resultFile);
	}
	
	public int insertResultFile(ResultFile resultFile) {
		AdminUser user = IndetificationUtil.getAdminUser();
		resultFile.setCreateor(user.getUsername());
		resultFile.setUpdateor(user.getUserName());
		resultFile.setCreateId(user.getUpdateId());
		resultFile.setUpdateId(user.getUpdateId());
		return resultFileRepository.insertResultFile(resultFile);
	}
	
	public String inserMongoFile(InputStream inputStream, String contentType, String filename) {
		return monoFileUploadRepository.save(inputStream, contentType, filename);
	}

	public GridFSDBFile getMongoFile(String id) {
		return monoFileUploadRepository.get(id);

	}

}
