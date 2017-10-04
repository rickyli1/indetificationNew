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
	
	//按页获得结论文件数据
	public List<ResultFile> findResultFileList(ResultFile resultFile) {
		return resultFileRepository.findResultFileList(resultFile);
	}
	
	//获得结论文件数量
	public int findReulstFileCount(ResultFile resultFile) {
		return resultFileRepository.findReulstFileCount(resultFile);
	}
	
	//插入结论文件
	public int insertResultFile(ResultFile resultFile) {
		AdminUser user = IndetificationUtil.getAdminUser();
		resultFile.setCreateor(user.getUsername());
		resultFile.setUpdateor(user.getUserName());
		resultFile.setCreateId(user.getUpdateId());
		resultFile.setUpdateId(user.getUpdateId());
		return resultFileRepository.insertResultFile(resultFile);
	}
	
	//插入结论文件到mongo
	public String inserMongoFile(InputStream inputStream, String contentType, String filename) {
		return monoFileUploadRepository.save(inputStream, contentType, filename);
	}

	//获取文件
	public GridFSDBFile getMongoFile(String id) {
		return monoFileUploadRepository.get(id);

	}

}
