package com.main.java.model;

public class Equipment extends BaseModel{
	
	private static final long serialVersionUID = -1058328333110067211L;
	private int equipmentId;//设备id，自增长
	private String equipmentNo;//导入设备序号
	private String equipmentGroup;//导入设备专业
	private String equipmentSubGroup;//导入设备专业类别
	private String equipmentName;//导入设备名称
	private String equipmentLevel;//导入设备修别
	private String equipmentCompany;//导入设备维修单位
	private String equipmentLimit;//期限
	private String remark;//备注
	private String equipmentIds;//备注
	
	public String getEquipmentIds() {
		return equipmentIds;
	}
	public void setEquipmentIds(String equipmentIds) {
		this.equipmentIds = equipmentIds;
	}
	public int getEquipmentId() {
		return equipmentId;
	}
	public String getEquipmentIdToString() {
		return String.valueOf(equipmentId);
	}
	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}
	public String getEquipmentNo() {
		return equipmentNo;
	}
	public void setEquipmentNo(String equipmentNo) {
		this.equipmentNo = equipmentNo;
	}
	public String getEquipmentGroup() {
		return equipmentGroup;
	}
	public void setEquipmentGroup(String equipmentGroup) {
		this.equipmentGroup = equipmentGroup;
	}
	public String getEquipmentSubGroup() {
		return equipmentSubGroup;
	}
	public void setEquipmentSubGroup(String equipmentSubGroup) {
		this.equipmentSubGroup = equipmentSubGroup;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public String getEquipmentLevel() {
		return equipmentLevel;
	}
	public void setEquipmentLevel(String equipmentLevel) {
		this.equipmentLevel = equipmentLevel;
	}
	public String getEquipmentCompany() {
		return equipmentCompany;
	}
	public void setEquipmentCompany(String equipmentCompany) {
		this.equipmentCompany = equipmentCompany;
	}
	public String getEquipmentLimit() {
		return equipmentLimit;
	}
	public void setEquipmentLimit(String equipmentLimit) {
		this.equipmentLimit = equipmentLimit;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
