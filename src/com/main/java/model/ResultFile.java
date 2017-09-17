package com.main.java.model;

public class ResultFile extends BaseModel{
	private static final long serialVersionUID = 1L;
	
	private int fileId; //文件ID
	private String mongoFileId;//mongo文件id
	private String mongoFileName;//文件名
	private String applicationDate;//申请日期
	private String repairerName;//导入修理单位名称
	private String equimentGroup;//申请装备专业
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getMongoFileId() {
		return mongoFileId;
	}
	public void setMongoFileId(String mongoFileId) {
		this.mongoFileId = mongoFileId;
	}
	public String getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getRepairerName() {
		return repairerName;
	}
	public void setRepairerName(String repairerName) {
		this.repairerName = repairerName;
	}
	public String getEquimentGroup() {
		return equimentGroup;
	}
	public void setEquimentGroup(String equimentGroup) {
		this.equimentGroup = equimentGroup;
	}
	public String getMongoFileName() {
		return mongoFileName;
	}
	public void setMongoFileName(String mongoFileName) {
		this.mongoFileName = mongoFileName;
	}
	
}
