package com.pxene.pap.domain.beans;

/**
 * 活动目标
 */
public class PurposeBean {

	/**
	 * 活动目标ID
	 */
	private String id;
	/**
	 * 活动id
	 */
	private String campaignId;
	/**
	 * 活动目标名称
	 */
	private String name;
	/**
	 * 第三方检测跳转地址
	 */
	private String escrowUrl;
	/**
	 * 落地页id
	 */
	private String landpageId;
	/**
	 * 下载id
	 */
	private String downloadId;
	/**
	 * 落地页地址
	 */
	private String landpagePath;
	/**
	 * 安卓deekLink
	 */
	private String anidDeepLink;
	/**
	 * IOSdeepLink
	 */
	private String iosDeepLink;
	/**
	 * APP下载地址
	 */
	private String downloadPath;
	
	/**
	 * APP描述
	 */
	private String appDescription;
	/**
	 * APP系统
	 */
	private String appOs;
	/**
	 * APP名称
	 */
	private String appName;
	/**
	 * APPID
	 */
	private String appId;
	/**
	 * APP包名
	 */
	private String appPkgName;
	
	public String getAppDescription() {
		return appDescription;
	}
	public void setAppDescription(String appDescription) {
		this.appDescription = appDescription;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEscrowUrl() {
		return escrowUrl;
	}
	public void setEscrowUrl(String escrowUrl) {
		this.escrowUrl = escrowUrl;
	}
	public String getLandpageId() {
		return landpageId;
	}
	public void setLandpageId(String landpageId) {
		this.landpageId = landpageId;
	}
	public String getDownloadId() {
		return downloadId;
	}
	public void setDownloadId(String downloadId) {
		this.downloadId = downloadId;
	}
	public String getLandpagePath() {
		return landpagePath;
	}
	public void setLandpagePath(String landpagePath) {
		this.landpagePath = landpagePath;
	}
	public String getAnidDeepLink() {
		return anidDeepLink;
	}
	public void setAnidDeepLink(String anidDeepLink) {
		this.anidDeepLink = anidDeepLink;
	}
	public String getIosDeepLink() {
		return iosDeepLink;
	}
	public void setIosDeepLink(String iosDeepLink) {
		this.iosDeepLink = iosDeepLink;
	}
	public String getDownloadPath() {
		return downloadPath;
	}
	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}
	public String getAppOs() {
		return appOs;
	}
	public void setAppOs(String appOs) {
		this.appOs = appOs;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppPkgName() {
		return appPkgName;
	}
	public void setAppPkgName(String appPkgName) {
		this.appPkgName = appPkgName;
	}
	@Override
	public String toString() {
		return "PurposeBean [id=" + id + ", campaignId=" + campaignId
				+ ", name=" + name + ", escrowUrl=" + escrowUrl
				+ ", landpageId=" + landpageId + ", downloadId=" + downloadId
				+ ", landpagePath=" + landpagePath + ", anidDeepLink="
				+ anidDeepLink + ", iosDeepLink=" + iosDeepLink
				+ ", downloadPath=" + downloadPath + ", appDescription="
				+ appDescription + ", appOs=" + appOs + ", appName=" + appName
				+ ", appId=" + appId + ", appPkgName=" + appPkgName + "]";
	}
}
