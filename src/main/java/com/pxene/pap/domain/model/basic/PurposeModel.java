package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class PurposeModel {
    private String id;

    private String campaignId;

    private String name;

    private String escrowUrl;

    private String landpageId;

    private String downloadId;

    private String remark;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId == null ? null : campaignId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEscrowUrl() {
        return escrowUrl;
    }

    public void setEscrowUrl(String escrowUrl) {
        this.escrowUrl = escrowUrl == null ? null : escrowUrl.trim();
    }

    public String getLandpageId() {
        return landpageId;
    }

    public void setLandpageId(String landpageId) {
        this.landpageId = landpageId == null ? null : landpageId.trim();
    }

    public String getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(String downloadId) {
        this.downloadId = downloadId == null ? null : downloadId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", campaignId=").append(campaignId);
        sb.append(", name=").append(name);
        sb.append(", escrowUrl=").append(escrowUrl);
        sb.append(", landpageId=").append(landpageId);
        sb.append(", downloadId=").append(downloadId);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}