package com.main.java.model;

import java.util.List;

public class ApplicationUpdateModel extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4503420649866046496L;
	private List<Application> appliationList;

	public List<Application> getAppliationList() {
		return appliationList;
	}

	public void setAppliationList(List<Application> appliationList) {
		this.appliationList = appliationList;
	}
	
	
}
