package com.pxene.pap.domain.models;

import java.util.Date;

public class AdxCostModel {
    private String id;

    private String adxId;

    private Float ratio;

    private Date fixDate;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAdxId() {
        return adxId;
    }

    public void setAdxId(String adxId) {
        this.adxId = adxId == null ? null : adxId.trim();
    }

    public Float getRatio() {
        return ratio;
    }

    public void setRatio(Float ratio) {
        this.ratio = ratio;
    }

    public Date getFixDate() {
        return fixDate;
    }

    public void setFixDate(Date fixDate) {
        this.fixDate = fixDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", adxId=").append(adxId);
        sb.append(", ratio=").append(ratio);
        sb.append(", fixDate=").append(fixDate);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}