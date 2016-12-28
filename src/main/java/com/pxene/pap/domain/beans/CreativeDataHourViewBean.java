package com.pxene.pap.domain.beans;

import java.math.BigDecimal;
import java.util.Date;

public class CreativeDataHourViewBean {
    private Integer id;

    private String creativeId;

    private String campaignId;

    private Date datetime;

    private BigDecimal winAmountSum;

    private BigDecimal impressionAmountSum;

    private BigDecimal impressionRate;

    private BigDecimal clickAmountSum;

    private BigDecimal clickRate;

    private BigDecimal arrivalAmount;

    private BigDecimal arrivalRate;

    private BigDecimal uniqueAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(String creativeId) {
        this.creativeId = creativeId == null ? null : creativeId.trim();
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId == null ? null : campaignId.trim();
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public BigDecimal getWinAmountSum() {
        return winAmountSum;
    }

    public void setWinAmountSum(BigDecimal winAmountSum) {
        this.winAmountSum = winAmountSum;
    }

    public BigDecimal getImpressionAmountSum() {
        return impressionAmountSum;
    }

    public void setImpressionAmountSum(BigDecimal impressionAmountSum) {
        this.impressionAmountSum = impressionAmountSum;
    }

    public BigDecimal getImpressionRate() {
        return impressionRate;
    }

    public void setImpressionRate(BigDecimal impressionRate) {
        this.impressionRate = impressionRate;
    }

    public BigDecimal getClickAmountSum() {
        return clickAmountSum;
    }

    public void setClickAmountSum(BigDecimal clickAmountSum) {
        this.clickAmountSum = clickAmountSum;
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

    public BigDecimal getArrivalRate() {
        return arrivalRate;
    }

    public void setArrivalRate(BigDecimal arrivalRate) {
        this.arrivalRate = arrivalRate;
    }

    public BigDecimal getUniqueAmount() {
        return uniqueAmount;
    }

    public void setUniqueAmount(BigDecimal uniqueAmount) {
        this.uniqueAmount = uniqueAmount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", creativeId=").append(creativeId);
        sb.append(", campaignId=").append(campaignId);
        sb.append(", datetime=").append(datetime);
        sb.append(", winAmountSum=").append(winAmountSum);
        sb.append(", impressionAmountSum=").append(impressionAmountSum);
        sb.append(", impressionRate=").append(impressionRate);
        sb.append(", clickAmountSum=").append(clickAmountSum);
        sb.append(", clickRate=").append(clickRate);
        sb.append(", arrivalAmount=").append(arrivalAmount);
        sb.append(", arrivalRate=").append(arrivalRate);
        sb.append(", uniqueAmount=").append(uniqueAmount);
        sb.append("]");
        return sb.toString();
    }
}