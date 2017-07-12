package com.main.java.model;

public class Repairer extends BaseModel{
	private static final long serialVersionUID = -1058328333110067211L;
	private int repairerId;//修理单位编号，自增长
	private String repairerNo;//导入修理单位序号
	private String repairerName;//导入修理单位名称
	private String repairerLevel;//导入修理单位性质
	private String repairerArea;//导入修理单位辖区
	
	public int getRepairerId() {
		return repairerId;
	}
	public void setRepairerId(int repairerId) {
		this.repairerId = repairerId;
	}
	public String getRepairerNo() {
		return repairerNo;
	}
	public void setRepairerNo(String repairerNo) {
		this.repairerNo = repairerNo;
	}
	public String getRepairerName() {
		return repairerName;
	}
	public void setRepairerName(String repairerName) {
		this.repairerName = repairerName;
	}
	public String getRepairerLevel() {
		return repairerLevel;
	}
	public void setRepairerLevel(String repairerLevel) {
		this.repairerLevel = repairerLevel;
	}
	public String getRepairerArea() {
		return repairerArea;
	}
	public void setRepairerArea(String repairerArea) {
		this.repairerArea = repairerArea;
	}
}
