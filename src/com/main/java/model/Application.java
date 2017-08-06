package com.main.java.model;

import java.util.List;

public class Application extends BaseModel{
	private static final long serialVersionUID = 998250562196243906L;
	
	private int applicationId; //申请编号，自增长
	private String applicationDate; //申请日期
	private String applicationRepairer; //申请单位
	private String equipmentManager;//装备管理机关
	private String equimentGroup;//申请装备专业
	private String equimentName; //申请装备型号名称
	private String repairerLevel; //申请级别
	private String repairerHistory;//申请经历
	private String remark; //备注
	private String haveSuccesWwork; //有没有认可任务
	private String areaHaveAbility;//辖区同型号修理能力总体评价结论
	private String orginizationResult; //机关批复
	
	private List<String> areaRepairInfos; //辖区修理信息 (大修 ： ***厂，###厂)
	
	private String orderType; //排序
	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	public String getApplicationRepairer() {
		return applicationRepairer;
	}
	public void setApplicationRepairer(String applicationRepairer) {
		this.applicationRepairer = applicationRepairer;
	}
	public String getEquipmentManager() {
		return equipmentManager;
	}
	public void setEquipmentManager(String equipmentManager) {
		this.equipmentManager = equipmentManager;
	}
	public String getEquimentGroup() {
		return equimentGroup;
	}
	public void setEquimentGroup(String equimentGroup) {
		this.equimentGroup = equimentGroup;
	}
	public String getEquimentName() {
		return equimentName;
	}
	public void setEquimentName(String equimentName) {
		this.equimentName = equimentName;
	}
	public String getRepairerLevel() {
		return repairerLevel;
	}
	public void setRepairerLevel(String repairerLevel) {
		this.repairerLevel = repairerLevel;
	}
	public String getRepairerHistory() {
		return repairerHistory;
	}
	public void setRepairerHistory(String repairerHistory) {
		this.repairerHistory = repairerHistory;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getHaveSuccesWwork() {
		return haveSuccesWwork;
	}
	public void setHaveSuccesWwork(String haveSuccesWwork) {
		this.haveSuccesWwork = haveSuccesWwork;
	}
	public String getAreaHaveAbility() {
		return areaHaveAbility;
	}
	public void setAreaHaveAbility(String areaHaveAbility) {
		this.areaHaveAbility = areaHaveAbility;
	}
	public String getOrginizationResult() {
		return orginizationResult;
	}
	public void setOrginizationResult(String orginizationResult) {
		this.orginizationResult = orginizationResult;
	}
	public String getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	public List<String> getAreaRepairInfos() {
		return areaRepairInfos;
	}
	public void setAreaRepairInfos(List<String> areaRepairInfos) {
		this.areaRepairInfos = areaRepairInfos;
	}
	public String getApplicationKey() {
		StringBuilder sb = new StringBuilder();
		return sb.append(this.getApplicationDate())
		  .append("+")
		  .append(this.getApplicationRepairer())
		  .append("+")
		  .append(this.getEquimentName()).toString();
	}
	
}