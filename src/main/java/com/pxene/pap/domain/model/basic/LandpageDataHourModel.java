package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class LandpageDataHourModel {
    private Integer id;

    private String landpageId;

    private String campaignId;

    private Date datetime;

    private Long clickAmount;

    private Long arrivalAmount;

    private Long uniqueAmount;

    private Long residentTime;

    private Long jumpAmount;

    private Date createtime;

    private Date updatetime;

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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
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
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}