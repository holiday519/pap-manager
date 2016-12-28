package com.pxene.pap.domain.model.basic.view;

import java.math.BigDecimal;
import java.util.Date;

public class AppDataHourViewModel {
    private String appId;

    private String campaignId;

    private Date datetime;

    private BigDecimal bidAmountSum;

    private BigDecimal winAmountSum;

    private BigDecimal winRate;

    private BigDecimal impressionAmountSum;

    private BigDecimal impressionRate;

    private BigDecimal clickAmountSum;

    private BigDecimal clickRate;

    private BigDecimal arrivalAmountSum;

    private BigDecimal arrivalRate;

    private BigDecimal uniqueAmountSum;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
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

    public BigDecimal getBidAmountSum() {
        return bidAmountSum;
    }

    public void setBidAmountSum(BigDecimal bidAmountSum) {
        this.bidAmountSum = bidAmountSum;
    }

    public BigDecimal getWinAmountSum() {
        return winAmountSum;
    }

    public void setWinAmountSum(BigDecimal winAmountSum) {
        this.winAmountSum = winAmountSum;
    }

    public BigDecimal getWinRate() {
        return winRate;
    }

    public void setWinRate(BigDecimal winRate) {
        this.winRate = winRate;
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

    public BigDecimal getArrivalAmountSum() {
        return arrivalAmountSum;
    }

    public void setArrivalAmountSum(BigDecimal arrivalAmountSum) {
        this.arrivalAmountSum = arrivalAmountSum;
    }

    public BigDecimal getArrivalRate() {
        return arrivalRate;
    }

    public void setArrivalRate(BigDecimal arrivalRate) {
        this.arrivalRate = arrivalRate;
    }

    public BigDecimal getUniqueAmountSum() {
        return uniqueAmountSum;
    }

    public void setUniqueAmountSum(BigDecimal uniqueAmountSum) {
        this.uniqueAmountSum = uniqueAmountSum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", appId=").append(appId);
        sb.append(", campaignId=").append(campaignId);
        sb.append(", datetime=").append(datetime);
        sb.append(", bidAmountSum=").append(bidAmountSum);
        sb.append(", winAmountSum=").append(winAmountSum);
        sb.append(", winRate=").append(winRate);
        sb.append(", impressionAmountSum=").append(impressionAmountSum);
        sb.append(", impressionRate=").append(impressionRate);
        sb.append(", clickAmountSum=").append(clickAmountSum);
        sb.append(", clickRate=").append(clickRate);
        sb.append(", arrivalAmountSum=").append(arrivalAmountSum);
        sb.append(", arrivalRate=").append(arrivalRate);
        sb.append(", uniqueAmountSum=").append(uniqueAmountSum);
        sb.append("]");
        return sb.toString();
    }
}