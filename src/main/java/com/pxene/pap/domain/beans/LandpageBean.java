package com.pxene.pap.domain.beans;

/**
 * 落地页
 */
public class LandpageBean {

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
	private String url;
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
	 * 第三方检测地址
	 */
	private String status;
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
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "LandpageBean [id=" + id + ", campaignId=" + campaignId
				+ ", name=" + name + ", url=" + url + ", anidDeepLink="
				+ anidDeepLink + ", iosDeepLink=" + iosDeepLink
				+ ", monitorUrl=" + monitorUrl + ", status=" + status
				+ ", remark=" + remark + "]";
	}
}
