package com.pxene.pap.domain.beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LandpageDataBean {
    private Integer id;

    private String landpageId;

    private String campaignId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date datetime;

    private Long clickAmount;

    private Long arrivalAmount;
    
    private Float arrivalRate;

    private Long uniqueAmount;

    private Long residentTime;

    private Long jumpAmount;
    
    private Float jumpRate;
    
    public Float getArrivalRate() {
		return arrivalRate;
	}

	public void setArrivalRate(Float arrivalRate) {
		this.arrivalRate = arrivalRate;
	}

	public Float getJumpRate() {
		return jumpRate;
	}

	public void setJumpRate(Float jumpRate) {
		this.jumpRate = jumpRate;
	}

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

    public Long getResidentTime() {
        return residentTime;
    }

    public void setResidentTime(Long residentTime) {
        this.residentTime = residentTime;
    }

    public Long getJumpAmount() {
        return jumpAmount;
    }

    public void setJumpAmount(Long jumpAmount) {
        this.jumpAmount = jumpAmount;
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
        sb.append(", clickAmount=").append(clickAmount);
        sb.append(", arrivalAmount=").append(arrivalAmount);
        sb.append(", uniqueAmount=").append(uniqueAmount);
        sb.append(", residentTime=").append(residentTime);
        sb.append(", jumpAmount=").append(jumpAmount);
        sb.append("]");
        return sb.toString();
    }
}