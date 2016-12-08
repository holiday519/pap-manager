package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class VideoTmplTypeModel {
    private String id;

    private String videoTmplId;

    private String videoTypeId;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getVideoTmplId() {
        return videoTmplId;
    }

    public void setVideoTmplId(String videoTmplId) {
        this.videoTmplId = videoTmplId == null ? null : videoTmplId.trim();
    }

    public String getVideoTypeId() {
        return videoTypeId;
    }

    public void setVideoTypeId(String videoTypeId) {
        this.videoTypeId = videoTypeId == null ? null : videoTypeId.trim();
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
}