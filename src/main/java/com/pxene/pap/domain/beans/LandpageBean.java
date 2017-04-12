package com.pxene.pap.domain.beans;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.pxene.pap.constant.PhrasesConstant;

/**
 * 落地页
 */
public class LandpageBean {

	/**
	 * 活动目标ID
	 */
	@Length(max = 37, message = PhrasesConstant.LENGTH_ERROR_ID)
	private String id;
	/**
	 * 活动目标名称
	 */
	@NotNull(message = PhrasesConstant.NAME_NOT_NULL)
	@Length(max = 100, message = PhrasesConstant.LENGTH_ERROR_NAME)
	private String name;
	/**
	 * 落地页地址
	 */
	@NotNull(message = PhrasesConstant.LANDPAGE_NOTNULL_URL)
	@Length(max = 400, message = PhrasesConstant.LANDPAGE_LENGTH_ERROR_URL)
	private String url;
	/**
	 * 安卓deekLink
	 */
	@Length(max = 400, message = PhrasesConstant.LANDPAGE_LENGTH_ERROR_ANIDDEEP)
	private String anidDeepLink;
	/**
	 * IOSdeepLink
	 */
	@Length(max = 400, message = PhrasesConstant.LANDPAGE_LENGTH_ERROR_IOSDEEP)
	private String iosDeepLink;
	/**
	 * 第三方监测地址
	 */
	@Length(max = 400, message = PhrasesConstant.LANDPAGE_LENGTH_ERROR_MONITORURL)
	private String monitorUrl;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 备注
	 */
	@Length(max = 200, message = PhrasesConstant.LANDPAGE_LENGTH_ERROR_REMARK)
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		return "LandpageBean [id=" + id + ", name=" + name + ", url=" + url
				+ ", anidDeepLink=" + anidDeepLink + ", iosDeepLink="
				+ iosDeepLink + ", monitorUrl=" + monitorUrl + ", status="
				+ status + ", remark=" + remark + "]";
	}

}
