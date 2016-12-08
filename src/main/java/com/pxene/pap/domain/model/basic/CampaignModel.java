package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class CampaignModel {
    private String id;

    private String projectId;

    private String name;

    private String type;

    private Date startDate;

    private Date endDate;

    private Integer totalBudget;

    private Integer dailyBudget;

    private Integer dailyImpression;

    private Integer dailyClick;

    private String remark;

    private String status;

    private String frequencyId;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(Integer totalBudget) {
        this.totalBudget = totalBudget;
    }

    public Integer getDailyBudget() {
        return dailyBudget;
    }

    public void setDailyBudget(Integer dailyBudget) {
        this.dailyBudget = dailyBudget;
    }

    public Integer getDailyImpression() {
        return dailyImpression;
    }

    public void setDailyImpression(Integer dailyImpression) {
        this.dailyImpression = dailyImpression;
    }

    public Integer getDailyClick() {
        return dailyClick;
    }

    public void setDailyClick(Integer dailyClick) {
        this.dailyClick = dailyClick;
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

    public String getFrequencyId() {
        return frequencyId;
    }

    public void setFrequencyId(String frequencyId) {
        this.frequencyId = frequencyId == null ? null : frequencyId.trim();
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