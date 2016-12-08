package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class CampaignModel {
    private String id;

    private String projectid;

    private String name;

    private String type;

    private Date startdate;

    private Date enddate;

    private Integer totalbudget;

    private Integer dailybudget;

    private Integer dailyimpression;

    private Integer dailyclick;

    private String remark;

    private String status;

    private String frequencyid;

    private Date createtime;

    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid == null ? null : projectid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Integer getTotalbudget() {
        return totalbudget;
    }

    public void setTotalbudget(Integer totalbudget) {
        this.totalbudget = totalbudget;
    }

    public Integer getDailybudget() {
        return dailybudget;
    }

    public void setDailybudget(Integer dailybudget) {
        this.dailybudget = dailybudget;
    }

    public Integer getDailyimpression() {
        return dailyimpression;
    }

    public void setDailyimpression(Integer dailyimpression) {
        this.dailyimpression = dailyimpression;
    }

    public Integer getDailyclick() {
        return dailyclick;
    }

    public void setDailyclick(Integer dailyclick) {
        this.dailyclick = dailyclick;
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

    public String getFrequencyid() {
        return frequencyid;
    }

    public void setFrequencyid(String frequencyid) {
        this.frequencyid = frequencyid == null ? null : frequencyid.trim();
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