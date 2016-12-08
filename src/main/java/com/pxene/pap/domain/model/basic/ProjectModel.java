package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class ProjectModel {
    private String id;

    private String advertiserid;

    private String name;

    private Integer totalbudget;

    private String remark;

    private String status;

    private Date createtime;

    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAdvertiserid() {
        return advertiserid;
    }

    public void setAdvertiserid(String advertiserid) {
        this.advertiserid = advertiserid == null ? null : advertiserid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getTotalbudget() {
        return totalbudget;
    }

    public void setTotalbudget(Integer totalbudget) {
        this.totalbudget = totalbudget;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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