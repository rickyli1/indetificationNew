package com.main.java.model;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileBaseModel extends  BaseModel{
	private static final long serialVersionUID = 1L;
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
		
}
