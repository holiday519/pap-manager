package com.pxene.pap.domain.beans;

public class BaiduAuditAdvertiserBean {

	/**
	 * 广告主ID
	 */
	private long advertiserId;
	/**
	 * 广告主公司名
	 */
	private String advertiserName;
	
	/**
	 * 广告主名称
	 */
	private String advertiserLiteName;
	/**
	 * 网站名称
	 */
	private String siteName;
	/**
	 * 网站
	 */
	private String siteUrl;
	/**
	 * 联系电话
	 */
	private String telephone;
	/**
	 * 通讯地址
	 */
	private String address;

	public long getAdvertiserId() {
		return advertiserId;
	}

	public void setAdvertiserId(long advertiserId) {
		this.advertiserId = advertiserId;
	}

	public String getAdvertiserName() {
		return advertiserName;
	}

	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}

	public String getAdvertiserLiteName() {
		return advertiserLiteName;
	}

	public void setAdvertiserLiteName(String advertiserLiteName) {
		this.advertiserLiteName = advertiserLiteName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "BaiduAuditAdvertiserBean [advertiserId=" + advertiserId
				+ ", advertiserName=" + advertiserName
				+ ", advertiserLiteName=" + advertiserLiteName + ", siteName="
				+ siteName + ", siteUrl=" + siteUrl + ", telephone="
				+ telephone + ", address=" + address + "]";
	}
	
}
