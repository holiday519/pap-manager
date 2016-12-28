package com.pxene.pap.domain.beans;

import java.math.BigDecimal;

public class ProjectDetailBean {
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
	 * Kpi指标值
	 */
    private Integer kpiVal;
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
	 * 中标量
	 */
    private BigDecimal winAmount;
    /**
	 * 展现量
	 */
    private BigDecimal impressionAmount;
    /**
	 * 点击量
	 */
    private BigDecimal clickAmount;
    /**
	 * 点击率
	 */
    private BigDecimal clickRate;
    /**
	 * 到达量
	 */
    private BigDecimal arrivalAmount;
    /**
	 * 独立访客数
	 */
    private BigDecimal uniqueAmount;
    /**
	 * 花费
	 */
    private BigDecimal cost;

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

	public BigDecimal getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(BigDecimal winAmount) {
		this.winAmount = winAmount;
	}

	public BigDecimal getImpressionAmount() {
		return impressionAmount;
	}

	public void setImpressionAmount(BigDecimal impressionAmount) {
		this.impressionAmount = impressionAmount;
	}

	public BigDecimal getClickAmount() {
		return clickAmount;
	}

	public void setClickAmount(BigDecimal clickAmount) {
		this.clickAmount = clickAmount;
	}

	public BigDecimal getClickRate() {
		return clickRate;
	}

	public void setClickRate(BigDecimal clickRate) {
		this.clickRate = clickRate;
	}

	public BigDecimal getArrivalAmount() {
		return arrivalAmount;
	}

	public void setArrivalAmount(BigDecimal arrivalAmount) {
		this.arrivalAmount = arrivalAmount;
	}

	public BigDecimal getUniqueAmount() {
		return uniqueAmount;
	}

	public void setUniqueAmount(BigDecimal uniqueAmount) {
		this.uniqueAmount = uniqueAmount;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "ProjectDetailBean [id=" + id + ", name=" + name
				+ ", totalBudget=" + totalBudget + ", kpiVal=" + kpiVal
				+ ", remark=" + remark + ", status=" + status
				+ ", advertiserId=" + advertiserId + ", advertiserName="
				+ advertiserName + ", industryId=" + industryId
				+ ", industryName=" + industryName + ", kpiId=" + kpiId
				+ ", kpiName=" + kpiName + ", winAmount=" + winAmount
				+ ", impressionAmount=" + impressionAmount + ", clickAmount="
				+ clickAmount + ", clickRate=" + clickRate + ", arrivalAmount="
				+ arrivalAmount + ", uniqueAmount=" + uniqueAmount + ", cost="
				+ cost + "]";
	}
}
