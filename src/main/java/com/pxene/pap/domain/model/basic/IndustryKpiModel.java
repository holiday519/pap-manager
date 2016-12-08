package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class IndustryKpiModel {
    private String id;

    private String industryId;

    private String kpiId;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId == null ? null : industryId.trim();
    }

    public String getKpiId() {
        return kpiId;
    }

    public void setKpiId(String kpiId) {
        this.kpiId = kpiId == null ? null : kpiId.trim();
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