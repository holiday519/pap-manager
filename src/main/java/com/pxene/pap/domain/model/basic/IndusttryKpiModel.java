package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class IndusttryKpiModel {
    private String id;

    private String industryid;

    private String kpiid;

    private Date createtime;

    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getIndustryid() {
        return industryid;
    }

    public void setIndustryid(String industryid) {
        this.industryid = industryid == null ? null : industryid.trim();
    }

    public String getKpiid() {
        return kpiid;
    }

    public void setKpiid(String kpiid) {
        this.kpiid = kpiid == null ? null : kpiid.trim();
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
}