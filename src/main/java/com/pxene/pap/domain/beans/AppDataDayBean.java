package com.pxene.pap.domain.beans;

import java.util.Date;

public class AppDataDayBean {
    private Integer id;

    private String appId;

    private String campaignId;

    private Date date;

    private Long bidAmount;

    private Long winAmount;

    private Float winRate;

    private Long impressionAmount;

    private Float impressionRate;

    private Long clickAmount;

    private Float clickRate;

    private Long arrivalAmount;

    private Float arrivalRate;

    private Long uniqueAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(Long bidAmount) {
        this.bidAmount = bidAmount;
    }

    public Long getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(Long winAmount) {
        this.winAmount = winAmount;
    }

    public Float getWinRate() {
        return winRate;
    }

    public void setWinRate(Float winRate) {
        this.winRate = winRate;
    }

    public Long getImpressionAmount() {
        return impressionAmount;
    }

    public void setImpressionAmount(Long impressionAmount) {
        this.impressionAmount = impressionAmount;
    }

    public Float getImpressionRate() {
        return impressionRate;
    }

    public void setImpressionRate(Float impressionRate) {
        this.impressionRate = impressionRate;
    }

    public Long getClickAmount() {
        return clickAmount;
    }

    public void setClickAmount(Long clickAmount) {
        this.clickAmount = clickAmount;
    }

    public Float getClickRate() {
        return clickRate;
    }

    public void setClickRate(Float clickRate) {
        this.clickRate = clickRate;
    }

    public Long getArrivalAmount() {
        return arrivalAmount;
    }

    public void setArrivalAmount(Long arrivalAmount) {
        this.arrivalAmount = arrivalAmount;
    }

    public Float getArrivalRate() {
        return arrivalRate;
    }

    public void setArrivalRate(Float arrivalRate) {
        this.arrivalRate = arrivalRate;
    }

    public Long getUniqueAmount() {
        return uniqueAmount;
    }

    public void setUniqueAmount(Long uniqueAmount) {
        this.uniqueAmount = uniqueAmount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", appId=").append(appId);
        sb.append(", campaignId=").append(campaignId);
        sb.append(", date=").append(date);
        sb.append(", bidAmount=").append(bidAmount);
        sb.append(", winAmount=").append(winAmount);
        sb.append(", winRate=").append(winRate);
        sb.append(", impressionAmount=").append(impressionAmount);
        sb.append(", impressionRate=").append(impressionRate);
        sb.append(", clickAmount=").append(clickAmount);
        sb.append(", clickRate=").append(clickRate);
        sb.append(", arrivalAmount=").append(arrivalAmount);
        sb.append(", arrivalRate=").append(arrivalRate);
        sb.append(", uniqueAmount=").append(uniqueAmount);
        sb.append("]");
        return sb.toString();
    }
}