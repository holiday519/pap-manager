package com.pxene.pap.domain.models;

import java.util.Date;

public class AdxModel {
    private String id;

    private String name;

    private String dspId;

    private String dspName;

    private String iurl;

    private String curl;

    private String advertiserAuditUrl;

    private String advertiserSyncUrl;

    private String creativeAuditUrl;

    private String creativeSyncUrl;

    private String signKey;

    private String remark;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDspId() {
        return dspId;
    }

    public void setDspId(String dspId) {
        this.dspId = dspId == null ? null : dspId.trim();
    }

    public String getDspName() {
        return dspName;
    }

    public void setDspName(String dspName) {
        this.dspName = dspName == null ? null : dspName.trim();
    }

    public String getIurl() {
        return iurl;
    }

    public void setIurl(String iurl) {
        this.iurl = iurl == null ? null : iurl.trim();
    }

    public String getCurl() {
        return curl;
    }

    public void setCurl(String curl) {
        this.curl = curl == null ? null : curl.trim();
    }

    public String getAdvertiserAuditUrl() {
        return advertiserAuditUrl;
    }

    public void setAdvertiserAuditUrl(String advertiserAuditUrl) {
        this.advertiserAuditUrl = advertiserAuditUrl == null ? null : advertiserAuditUrl.trim();
    }

    public String getAdvertiserSyncUrl() {
        return advertiserSyncUrl;
    }

    public void setAdvertiserSyncUrl(String advertiserSyncUrl) {
        this.advertiserSyncUrl = advertiserSyncUrl == null ? null : advertiserSyncUrl.trim();
    }

    public String getCreativeAuditUrl() {
        return creativeAuditUrl;
    }

    public void setCreativeAuditUrl(String creativeAuditUrl) {
        this.creativeAuditUrl = creativeAuditUrl == null ? null : creativeAuditUrl.trim();
    }

    public String getCreativeSyncUrl() {
        return creativeSyncUrl;
    }

    public void setCreativeSyncUrl(String creativeSyncUrl) {
        this.creativeSyncUrl = creativeSyncUrl == null ? null : creativeSyncUrl.trim();
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey == null ? null : signKey.trim();
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
        sb.append(", name=").append(name);
        sb.append(", dspId=").append(dspId);
        sb.append(", dspName=").append(dspName);
        sb.append(", iurl=").append(iurl);
        sb.append(", curl=").append(curl);
        sb.append(", advertiserAuditUrl=").append(advertiserAuditUrl);
        sb.append(", advertiserSyncUrl=").append(advertiserSyncUrl);
        sb.append(", creativeAuditUrl=").append(creativeAuditUrl);
        sb.append(", creativeSyncUrl=").append(creativeSyncUrl);
        sb.append(", signKey=").append(signKey);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}