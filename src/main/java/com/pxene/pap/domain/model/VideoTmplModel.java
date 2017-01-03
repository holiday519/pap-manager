package com.pxene.pap.domain.model;

import java.util.Date;

public class VideoTmplModel {
    private String id;

    private String sizeId;

    private Float maxVolume;

    private Integer maxTimelength;

    private String imageTmplId;

    private String remark;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId == null ? null : sizeId.trim();
    }

    public Float getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(Float maxVolume) {
        this.maxVolume = maxVolume;
    }

    public Integer getMaxTimelength() {
        return maxTimelength;
    }

    public void setMaxTimelength(Integer maxTimelength) {
        this.maxTimelength = maxTimelength;
    }

    public String getImageTmplId() {
        return imageTmplId;
    }

    public void setImageTmplId(String imageTmplId) {
        this.imageTmplId = imageTmplId == null ? null : imageTmplId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
        sb.append(", sizeId=").append(sizeId);
        sb.append(", maxVolume=").append(maxVolume);
        sb.append(", maxTimelength=").append(maxTimelength);
        sb.append(", imageTmplId=").append(imageTmplId);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}