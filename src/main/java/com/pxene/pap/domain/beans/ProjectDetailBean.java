package com.pxene.pap.domain.beans;

import java.math.BigDecimal;

public class ProjectDetailBean extends CreativeBasicBean {
	/**
	 * id
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 总预算
	 */
	private Integer totalBudget;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 广告主Id
	 */
	private String advertiserId;
	/**
	 * 广告主名称
	 */
	private String advertiserName;
	/**
	 * 行业id
	 */
	private String industryId;
	/**
	 * 行业名称
	 */
	private String industryName;
	/**
	 * kpi指标ID
	 */
	private String kpiId;
	/**
	 * kpi指标名称
	 */
	private String kpiName;
	/**
	 * Kpi指标值
	 */
	private Integer kpiVal;
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

	public Integer getTotalBudget() {
		return totalBudget;
	}

	public void setTotalBudget(Integer totalBudget) {
		this.totalBudget = totalBudget;
	}

	public Integer getKpiVal() {
		return kpiVal;
	}

	public void setKpiVal(Integer kpiVal) {
		this.kpiVal = kpiVal;
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

	public String getAdvertiserId() {
		return advertiserId;
	}

	public void setAdvertiserId(String advertiserId) {
		this.advertiserId = advertiserId;
	}

	public String getAdvertiserName() {
		return advertiserName;
	}

	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getKpiId() {
		return kpiId;
	}

	public void setKpiId(String kpiId) {
		this.kpiId = kpiId;
	}

	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	@Override
	public String toString() {
		return "ProjectDetailBean [id=" + id + ", name=" + name
				+ ", totalBudget=" + totalBudget + ", kpiVal=" + kpiVal
				+ ", remark=" + remark + ", status=" + status
				+ ", advertiserId=" + advertiserId + ", advertiserName="
				+ advertiserName + ", industryId=" + industryId
				+ ", industryName=" + industryName + ", kpiId=" + kpiId
				+ ", kpiName=" + kpiName + "]";
	}

}
