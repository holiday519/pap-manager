package com.pxene.pap.domain.models.view;

public class CampaignKpiModel {
    private String projectId;

    private String advertiserId;

    private String projectName;

    private Integer totalBudget;

    private String projectStatus;

    private Integer kpiValue;

    private String kpiId;

    private String kpiName;

    private String unit;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(String advertiserId) {
        this.advertiserId = advertiserId == null ? null : advertiserId.trim();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public Integer getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(Integer totalBudget) {
        this.totalBudget = totalBudget;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus == null ? null : projectStatus.trim();
    }

    public Integer getKpiValue() {
        return kpiValue;
    }

    public void setKpiValue(Integer kpiValue) {
        this.kpiValue = kpiValue;
    }

    public String getKpiId() {
        return kpiId;
    }

    public void setKpiId(String kpiId) {
        this.kpiId = kpiId == null ? null : kpiId.trim();
    }

    public String getKpiName() {
        return kpiName;
    }

    public void setKpiName(String kpiName) {
        this.kpiName = kpiName == null ? null : kpiName.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", projectId=").append(projectId);
        sb.append(", advertiserId=").append(advertiserId);
        sb.append(", projectName=").append(projectName);
        sb.append(", totalBudget=").append(totalBudget);
        sb.append(", projectStatus=").append(projectStatus);
        sb.append(", kpiValue=").append(kpiValue);
        sb.append(", kpiId=").append(kpiId);
        sb.append(", kpiName=").append(kpiName);
        sb.append(", unit=").append(unit);
        sb.append("]");
        return sb.toString();
    }
}