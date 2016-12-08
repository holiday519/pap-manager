package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class AppTmplModel {
    private String id;

    private String creativeId;

    private String tmplId;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(String creativeId) {
        this.creativeId = creativeId == null ? null : creativeId.trim();
    }

    public String getTmplId() {
        return tmplId;
    }

    public void setTmplId(String tmplId) {
        this.tmplId = tmplId == null ? null : tmplId.trim();
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