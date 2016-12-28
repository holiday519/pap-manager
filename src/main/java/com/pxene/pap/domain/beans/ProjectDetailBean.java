package com.pxene.pap.domain.beans;

import java.math.BigDecimal;

public class ProjectDetailBean {
	private String id;

    private String name;

    private Integer totalBudget;

    private Integer kpiVal;

    private String remark;

    private String status;

    private String advertiserId;

    private String advertiserName;

    private String industryId;

    private String industryName;

    private String kpiId;

    private String kpiName;

    private BigDecimal winAmount;

    private BigDecimal impressionAmount;

    private BigDecimal clickAmount;

    private BigDecimal clickRate;

    private BigDecimal arrivalAmount;

    private BigDecimal uniqueAmount;

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
