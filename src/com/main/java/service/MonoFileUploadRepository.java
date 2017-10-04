package com.main.java.service;

import java.io.InputStream;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

@Service
public class MonoFileUploadRepository {
	@Autowired
	private GridFsOperations gridFsTemplcate;  

	//插入图片
	public String save(InputStream inputStream, String contentType, String filename) {  
	  
	DBObject metaData = new BasicDBObject();  
	metaData.put("meta1", filename);  //文件名
	metaData.put("meta2", contentType); // 文件类型
	  
	GridFSFile file = gridFsTemplcate.store(inputStream, filename, metaData);  
	  
	return file.getId().toString(); //返回文件id
	}  
	 
	//根据ID读取文件
	public GridFSDBFile get(String id) {  
	  
	return gridFsTemplcate.findOne(new Query(Criteria.where("_id").is(new ObjectId(id))));  
	}  
	 
	//获取所以文件
	public List<GridFSDBFile> listFiles() {  
	  
	return gridFsTemplcate.find(null);  
	}  
	 
	//根据文件名取的文件
	public GridFSDBFile getByFilename(String filename) {  
	return gridFsTemplcate.findOne(new Query(Criteria.where("filename").is(filename)));  
	}  
	
}
