package com.pxene.pap.domain.beans;

public class AppFlowBean {

	private String id;
	private String appId;
	private String appName;
	private String adxId;
	private String appType;
	private String pkgName;
	private Long requestAmount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAdxId() {
		return adxId;
	}
	public void setAdxId(String adxId) {
		this.adxId = adxId;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getPkgName() {
		return pkgName;
	}
	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}
	public Long getRequestAmount() {
		return requestAmount;
	}
	public void setRequestAmount(Long requestAmount) {
		this.requestAmount = requestAmount;
	}
	
}
