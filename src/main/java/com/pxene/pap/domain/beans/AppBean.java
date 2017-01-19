package com.pxene.pap.domain.beans;


public class AppBean {
	
	private String id;

    private String appId;

    private String adxId;

    private String appName;

    private String appType;

    private String pkgName;

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

	public String getAdxId() {
		return adxId;
	}

	public void setAdxId(String adxId) {
		this.adxId = adxId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
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

	@Override
	public String toString() {
		return "AppBean [id=" + id + ", appId=" + appId + ", adxId=" + adxId
				+ ", appName=" + appName + ", appType=" + appType
				+ ", pkgName=" + pkgName + "]";
	}

}
