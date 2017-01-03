package com.pxene.pap.domain.beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CreativeDataHourBean {
    private Integer id;

    private String creativeId;

    private String campaignId;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date datetime;

    private Long winAmount;

    private Long impressionAmount;
    
    private Float impressionRate;

    private Long clickAmount;
    
    private Float clickRate;

    private Long arrivalAmount;
    
    private Float arrivalRate;

    private Long uniqueAmount;
    
    public Float getImpressionRate() {
		return impressionRate;
	}

	public void setImpressionRate(Float impressionRate) {
		this.impressionRate = impressionRate;
	}

	public Float getClickRate() {
		return clickRate;
	}

	public void setClickRate(Float clickRate) {
		this.clickRate = clickRate;
	}

	public Float getArrivalRate() {
		return arrivalRate;
	}

	public void setArrivalRate(Float arrivalRate) {
		this.arrivalRate = arrivalRate;
	}

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

    public Long getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(Long winAmount) {
        this.winAmount = winAmount;
    }

    public Long getImpressionAmount() {
        return impressionAmount;
    }

    public void setImpressionAmount(Long impressionAmount) {
        this.impressionAmount = impressionAmount;
    }

    public Long getClickAmount() {
        return clickAmount;
    }

    public void setClickAmount(Long clickAmount) {
        this.clickAmount = clickAmount;
    }

    public Long getArrivalAmount() {
        return arrivalAmount;
    }

    public void setArrivalAmount(Long arrivalAmount) {
        this.arrivalAmount = arrivalAmount;
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
        sb.append(", creativeId=").append(creativeId);
        sb.append(", campaignId=").append(campaignId);
        sb.append(", datetime=").append(datetime);
        sb.append(", winAmount=").append(winAmount);
        sb.append(", impressionAmount=").append(impressionAmount);
        sb.append(", clickAmount=").append(clickAmount);
        sb.append(", arrivalAmount=").append(arrivalAmount);
        sb.append(", uniqueAmount=").append(uniqueAmount);
        sb.append("]");
        return sb.toString();
    }
}