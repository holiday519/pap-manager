package com.pxene.pap.domain.beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RegionDataHourBean {
    private Integer id;

    private String regionId;

    private String campaignId;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date datetime;

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

    private Long residentTime;

    private Long jumpAmount;
    
    private Float jumpRate;
    
    public Float getWinRate() {
		return winRate;
	}

	public void setWinRate(Float winRate) {
		this.winRate = winRate;
	}

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

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId == null ? null : regionId.trim();
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
        sb.append(", regionId=").append(regionId);
        sb.append(", campaignId=").append(campaignId);
        sb.append(", datetime=").append(datetime);
        sb.append(", bidAmount=").append(bidAmount);
        sb.append(", winAmount=").append(winAmount);
        sb.append(", impressionAmount=").append(impressionAmount);
        sb.append(", clickAmount=").append(clickAmount);
        sb.append(", arrivalAmount=").append(arrivalAmount);
        sb.append(", uniqueAmount=").append(uniqueAmount);
        sb.append(", residentTime=").append(residentTime);
        sb.append(", jumpAmount=").append(jumpAmount);
        sb.append("]");
        return sb.toString();
    }
}