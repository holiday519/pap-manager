package com.pxene.pap.domain.beans;

/**
 * 活动目标
 */
public class LandPageBean {

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
	 * 落地页地址
	 */
	private String Url;
	/**
	 * 安卓deekLink
	 */
	private String anidDeepLink;
	/**
	 * IOSdeepLink
	 */
	private String iosDeepLink;
	/**
	 * 第三方检测地址
	 */
	private String monitorUrl;
	/**
	 * 备注
	 */
	private String remark;
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
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
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
	public String getMonitorUrl() {
		return monitorUrl;
	}
	public void setMonitorUrl(String monitorUrl) {
		this.monitorUrl = monitorUrl;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "PurposeBean [id=" + id + ", campaignId=" + campaignId
				+ ", name=" + name + ", Url=" + Url + ", anidDeepLink="
				+ anidDeepLink + ", iosDeepLink=" + iosDeepLink
				+ ", monitorUrl=" + monitorUrl + ", remark=" + remark + "]";
	}
}
