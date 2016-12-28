package com.pxene.pap.domain.model.basic.view;

import java.math.BigDecimal;

public class ProjectDetailModel {
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
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
        this.remark = remark == null ? null : remark.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(String advertiserId) {
        this.advertiserId = advertiserId == null ? null : advertiserId.trim();
    }

    public String getAdvertiserName() {
        return advertiserName;
    }

    public void setAdvertiserName(String advertiserName) {
        this.advertiserName = advertiserName == null ? null : advertiserName.trim();
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId == null ? null : industryId.trim();
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName == null ? null : industryName.trim();
    }

    public String getKpiId() {
        return kpiId;
    }

    public void setKpiId(String kpiId) {
        this.kpiId = kpiId == null ? null : kpiId.trim();
    }

    public String getKpiName() {
        return kpiName;
    }

    public void setKpiName(String kpiName) {
        this.kpiName = kpiName == null ? null : kpiName.trim();
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
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", totalBudget=").append(totalBudget);
        sb.append(", kpiVal=").append(kpiVal);
        sb.append(", remark=").append(remark);
        sb.append(", status=").append(status);
        sb.append(", advertiserId=").append(advertiserId);
        sb.append(", advertiserName=").append(advertiserName);
        sb.append(", industryId=").append(industryId);
        sb.append(", industryName=").append(industryName);
        sb.append(", kpiId=").append(kpiId);
        sb.append(", kpiName=").append(kpiName);
        sb.append(", winAmount=").append(winAmount);
        sb.append(", impressionAmount=").append(impressionAmount);
        sb.append(", clickAmount=").append(clickAmount);
        sb.append(", clickRate=").append(clickRate);
        sb.append(", arrivalAmount=").append(arrivalAmount);
        sb.append(", uniqueAmount=").append(uniqueAmount);
        sb.append(", cost=").append(cost);
        sb.append("]");
        return sb.toString();
    }
}