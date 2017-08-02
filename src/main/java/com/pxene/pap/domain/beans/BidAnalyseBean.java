package com.pxene.pap.domain.beans;

/**
 * Created by wangshuai on 2017/8/1.
 */
public class BidAnalyseBean {
    private long startDate;
    private long endDate;
    private long date;
    private String projectId;
    private String campaignId;
    private String adx;
    private String materialType;
    private String materialSize;
    private String type;
    private String sortType;

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getAdx() {
        return adx;
    }

    public void setAdx(String adx) {
        this.adx = adx;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getMaterialSize() {
        return materialSize;
    }

    public void setMaterialSize(String materialSize) {
        this.materialSize = materialSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }


    @Override
    public String toString() {
        return "BidAnalyseBean{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", date=" + date +
                ", projectId='" + projectId + '\'' +
                ", campaignId='" + campaignId + '\'' +
                ", adx='" + adx + '\'' +
                ", materialType='" + materialType + '\'' +
                ", materialSize='" + materialSize + '\'' +
                ", type='" + type + '\'' +
                ", sortType='" + sortType + '\'' +
                '}';
    }
}
