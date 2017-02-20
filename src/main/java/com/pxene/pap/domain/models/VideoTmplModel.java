package com.pxene.pap.domain.models;

import java.util.Date;

public class VideoTmplModel {
    private String id;

    private Integer width;

    private Integer height;

    private String formats;

    private Float maxVolume;

    private Integer maxTimelength;

    private String imagelId;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getFormats() {
        return formats;
    }

    public void setFormats(String formats) {
        this.formats = formats == null ? null : formats.trim();
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

    public String getImagelId() {
        return imagelId;
    }

    public void setImagelId(String imagelId) {
        this.imagelId = imagelId == null ? null : imagelId.trim();
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
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", formats=").append(formats);
        sb.append(", maxVolume=").append(maxVolume);
        sb.append(", maxTimelength=").append(maxTimelength);
        sb.append(", imagelId=").append(imagelId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}