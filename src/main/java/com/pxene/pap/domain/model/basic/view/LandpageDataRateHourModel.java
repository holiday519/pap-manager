package com.pxene.pap.domain.model.basic.view;

import java.math.BigDecimal;
import java.util.Date;

public class LandpageDataRateHourModel {
    private Integer id;

    private String landpageId;

    private String campaignId;

    private Date datetime;

    private BigDecimal clickAmountSum;

    private BigDecimal arrivalAmountSum;

    private BigDecimal arrivalRate;

    private BigDecimal uniqueAmountSum;

    private BigDecimal residentTimeSum;

    private BigDecimal jumpAmountSum;

    private BigDecimal jumpRate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLandpageId() {
        return landpageId;
    }

    public void setLandpageId(String landpageId) {
        this.landpageId = landpageId == null ? null : landpageId.trim();
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

    public BigDecimal getClickAmountSum() {
        return clickAmountSum;
    }

    public void setClickAmountSum(BigDecimal clickAmountSum) {
        this.clickAmountSum = clickAmountSum;
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

    public BigDecimal getResidentTimeSum() {
        return residentTimeSum;
    }

    public void setResidentTimeSum(BigDecimal residentTimeSum) {
        this.residentTimeSum = residentTimeSum;
    }

    public BigDecimal getJumpAmountSum() {
        return jumpAmountSum;
    }

    public void setJumpAmountSum(BigDecimal jumpAmountSum) {
        this.jumpAmountSum = jumpAmountSum;
    }

    public BigDecimal getJumpRate() {
        return jumpRate;
    }

    public void setJumpRate(BigDecimal jumpRate) {
        this.jumpRate = jumpRate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", landpageId=").append(landpageId);
        sb.append(", campaignId=").append(campaignId);
        sb.append(", datetime=").append(datetime);
        sb.append(", clickAmountSum=").append(clickAmountSum);
        sb.append(", arrivalAmountSum=").append(arrivalAmountSum);
        sb.append(", arrivalRate=").append(arrivalRate);
        sb.append(", uniqueAmountSum=").append(uniqueAmountSum);
        sb.append(", residentTimeSum=").append(residentTimeSum);
        sb.append(", jumpAmountSum=").append(jumpAmountSum);
        sb.append(", jumpRate=").append(jumpRate);
        sb.append("]");
        return sb.toString();
    }
}