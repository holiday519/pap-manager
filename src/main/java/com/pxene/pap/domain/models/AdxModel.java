package com.pxene.pap.domain.models;

import java.util.Date;

public class AdxModel {
    private String id;

    private String name;

    private String dspId;

    private String dspName;

    private String iurl;

    private String curl;

    private String advertiserAddUrl;

    private String advertiserUpdateUrl;

    private String advertiserQueryUrl;

    private String qualificationAddUrl;

    private String qualificationUpdateUrl;

    private String qualificationQueryUrl;

    private String creativeAddUrl;

    private String creativeUpdateUrl;

    private String creativeQueryUrl;

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

    public String getAdvertiserAddUrl() {
        return advertiserAddUrl;
    }

    public void setAdvertiserAddUrl(String advertiserAddUrl) {
        this.advertiserAddUrl = advertiserAddUrl == null ? null : advertiserAddUrl.trim();
    }

    public String getAdvertiserUpdateUrl() {
        return advertiserUpdateUrl;
    }

    public void setAdvertiserUpdateUrl(String advertiserUpdateUrl) {
        this.advertiserUpdateUrl = advertiserUpdateUrl == null ? null : advertiserUpdateUrl.trim();
    }

    public String getAdvertiserQueryUrl() {
        return advertiserQueryUrl;
    }

    public void setAdvertiserQueryUrl(String advertiserQueryUrl) {
        this.advertiserQueryUrl = advertiserQueryUrl == null ? null : advertiserQueryUrl.trim();
    }

    public String getQualificationAddUrl() {
        return qualificationAddUrl;
    }

    public void setQualificationAddUrl(String qualificationAddUrl) {
        this.qualificationAddUrl = qualificationAddUrl == null ? null : qualificationAddUrl.trim();
    }

    public String getQualificationUpdateUrl() {
        return qualificationUpdateUrl;
    }

    public void setQualificationUpdateUrl(String qualificationUpdateUrl) {
        this.qualificationUpdateUrl = qualificationUpdateUrl == null ? null : qualificationUpdateUrl.trim();
    }

    public String getQualificationQueryUrl() {
        return qualificationQueryUrl;
    }

    public void setQualificationQueryUrl(String qualificationQueryUrl) {
        this.qualificationQueryUrl = qualificationQueryUrl == null ? null : qualificationQueryUrl.trim();
    }

    public String getCreativeAddUrl() {
        return creativeAddUrl;
    }

    public void setCreativeAddUrl(String creativeAddUrl) {
        this.creativeAddUrl = creativeAddUrl == null ? null : creativeAddUrl.trim();
    }

    public String getCreativeUpdateUrl() {
        return creativeUpdateUrl;
    }

    public void setCreativeUpdateUrl(String creativeUpdateUrl) {
        this.creativeUpdateUrl = creativeUpdateUrl == null ? null : creativeUpdateUrl.trim();
    }

    public String getCreativeQueryUrl() {
        return creativeQueryUrl;
    }

    public void setCreativeQueryUrl(String creativeQueryUrl) {
        this.creativeQueryUrl = creativeQueryUrl == null ? null : creativeQueryUrl.trim();
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
        sb.append(", advertiserAddUrl=").append(advertiserAddUrl);
        sb.append(", advertiserUpdateUrl=").append(advertiserUpdateUrl);
        sb.append(", advertiserQueryUrl=").append(advertiserQueryUrl);
        sb.append(", qualificationAddUrl=").append(qualificationAddUrl);
        sb.append(", qualificationUpdateUrl=").append(qualificationUpdateUrl);
        sb.append(", qualificationQueryUrl=").append(qualificationQueryUrl);
        sb.append(", creativeAddUrl=").append(creativeAddUrl);
        sb.append(", creativeUpdateUrl=").append(creativeUpdateUrl);
        sb.append(", creativeQueryUrl=").append(creativeQueryUrl);
        sb.append(", signKey=").append(signKey);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}