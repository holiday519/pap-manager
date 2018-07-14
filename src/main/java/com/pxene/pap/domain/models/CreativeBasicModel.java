package com.pxene.pap.domain.models;

import java.util.Date;

public class CreativeBasicModel {
    private String adxId;

    private String projectId;

    private String campaignId;

    private String templateId;

    private String appId;

    private String appName;

    private String adxName;

    private String campaignName;

    private String landpageId;

    private Date campaignStartDate;

    private Date campaignEndDate;

    private String campaignStatus;

    private String projectName;

    private Integer projectTotalBudget;

    private String projectStatus;

    private String advertiserId;

    private String advertiserName;

    private String creativeIds;

    public String getAdxId() {
        return adxId;
    }

    public void setAdxId(String adxId) {
        this.adxId = adxId == null ? null : adxId.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId == null ? null : campaignId.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getAdxName() {
        return adxName;
    }

    public void setAdxName(String adxName) {
        this.adxName = adxName == null ? null : adxName.trim();
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName == null ? null : campaignName.trim();
    }

    public String getLandpageId() {
        return landpageId;
    }

    public void setLandpageId(String landpageId) {
        this.landpageId = landpageId == null ? null : landpageId.trim();
    }

    public Date getCampaignStartDate() {
        return campaignStartDate;
    }

    public void setCampaignStartDate(Date campaignStartDate) {
        this.campaignStartDate = campaignStartDate;
    }

    public Date getCampaignEndDate() {
        return campaignEndDate;
    }

    public void setCampaignEndDate(Date campaignEndDate) {
        this.campaignEndDate = campaignEndDate;
    }

    public String getCampaignStatus() {
        return campaignStatus;
    }

    public void setCampaignStatus(String campaignStatus) {
        this.campaignStatus = campaignStatus == null ? null : campaignStatus.trim();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public Integer getProjectTotalBudget() {
        return projectTotalBudget;
    }

    public void setProjectTotalBudget(Integer projectTotalBudget) {
        this.projectTotalBudget = projectTotalBudget;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus == null ? null : projectStatus.trim();
    }

    public String getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(String advertiserId) {
        this.advertiserId = advertiserId == null ? null : advertiserId.trim();
    }

    public String getAdvertiserName() {
        return advertiserName;
    }

    public void setAdvertiserName(String advertiserName) {
        this.advertiserName = advertiserName == null ? null : advertiserName.trim();
    }

    public String getCreativeIds() {
        return creativeIds;
    }

    public void setCreativeIds(String creativeIds) {
        this.creativeIds = creativeIds == null ? null : creativeIds.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", adxId=").append(adxId);
        sb.append(", projectId=").append(projectId);
        sb.append(", campaignId=").append(campaignId);
        sb.append(", templateId=").append(templateId);
        sb.append(", appId=").append(appId);
        sb.append(", appName=").append(appName);
        sb.append(", adxName=").append(adxName);
        sb.append(", campaignName=").append(campaignName);
        sb.append(", landpageId=").append(landpageId);
        sb.append(", campaignStartDate=").append(campaignStartDate);
        sb.append(", campaignEndDate=").append(campaignEndDate);
        sb.append(", campaignStatus=").append(campaignStatus);
        sb.append(", projectName=").append(projectName);
        sb.append(", projectTotalBudget=").append(projectTotalBudget);
        sb.append(", projectStatus=").append(projectStatus);
        sb.append(", advertiserId=").append(advertiserId);
        sb.append(", advertiserName=").append(advertiserName);
        sb.append(", creativeIds=").append(creativeIds);
        sb.append("]");
        return sb.toString();
    }
}