package com.pxene.pap.domain.models;

import java.util.Date;

public class AdxModel {
    private String id;

    private String name;

    private String impressionUrl;

    private String clickUrl;

    private String aexamineUrl;

    private String aexamineresultUrl;

    private String aupdateUrl;

    private String qexamineUrl;

    private String qupdateUrl;

    private String cexamineUrl;

    private String cexamineResultUrl;

    private String cupdateUrl;

    private String aexamineFlag;

    private String cexamineFlag;

    private String privateKey;

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

    public String getImpressionUrl() {
        return impressionUrl;
    }

    public void setImpressionUrl(String impressionUrl) {
        this.impressionUrl = impressionUrl == null ? null : impressionUrl.trim();
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl == null ? null : clickUrl.trim();
    }

    public String getAexamineUrl() {
        return aexamineUrl;
    }

    public void setAexamineUrl(String aexamineUrl) {
        this.aexamineUrl = aexamineUrl == null ? null : aexamineUrl.trim();
    }

    public String getAexamineresultUrl() {
        return aexamineresultUrl;
    }

    public void setAexamineresultUrl(String aexamineresultUrl) {
        this.aexamineresultUrl = aexamineresultUrl == null ? null : aexamineresultUrl.trim();
    }

    public String getAupdateUrl() {
        return aupdateUrl;
    }

    public void setAupdateUrl(String aupdateUrl) {
        this.aupdateUrl = aupdateUrl == null ? null : aupdateUrl.trim();
    }

    public String getQexamineUrl() {
        return qexamineUrl;
    }

    public void setQexamineUrl(String qexamineUrl) {
        this.qexamineUrl = qexamineUrl == null ? null : qexamineUrl.trim();
    }

    public String getQupdateUrl() {
        return qupdateUrl;
    }

    public void setQupdateUrl(String qupdateUrl) {
        this.qupdateUrl = qupdateUrl == null ? null : qupdateUrl.trim();
    }

    public String getCexamineUrl() {
        return cexamineUrl;
    }

    public void setCexamineUrl(String cexamineUrl) {
        this.cexamineUrl = cexamineUrl == null ? null : cexamineUrl.trim();
    }

    public String getCexamineResultUrl() {
        return cexamineResultUrl;
    }

    public void setCexamineResultUrl(String cexamineResultUrl) {
        this.cexamineResultUrl = cexamineResultUrl == null ? null : cexamineResultUrl.trim();
    }

    public String getCupdateUrl() {
        return cupdateUrl;
    }

    public void setCupdateUrl(String cupdateUrl) {
        this.cupdateUrl = cupdateUrl == null ? null : cupdateUrl.trim();
    }

    public String getAexamineFlag() {
        return aexamineFlag;
    }

    public void setAexamineFlag(String aexamineFlag) {
        this.aexamineFlag = aexamineFlag == null ? null : aexamineFlag.trim();
    }

    public String getCexamineFlag() {
        return cexamineFlag;
    }

    public void setCexamineFlag(String cexamineFlag) {
        this.cexamineFlag = cexamineFlag == null ? null : cexamineFlag.trim();
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey == null ? null : privateKey.trim();
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
        sb.append(", impressionUrl=").append(impressionUrl);
        sb.append(", clickUrl=").append(clickUrl);
        sb.append(", aexamineUrl=").append(aexamineUrl);
        sb.append(", aexamineresultUrl=").append(aexamineresultUrl);
        sb.append(", aupdateUrl=").append(aupdateUrl);
        sb.append(", qexamineUrl=").append(qexamineUrl);
        sb.append(", qupdateUrl=").append(qupdateUrl);
        sb.append(", cexamineUrl=").append(cexamineUrl);
        sb.append(", cexamineResultUrl=").append(cexamineResultUrl);
        sb.append(", cupdateUrl=").append(cupdateUrl);
        sb.append(", aexamineFlag=").append(aexamineFlag);
        sb.append(", cexamineFlag=").append(cexamineFlag);
        sb.append(", privateKey=").append(privateKey);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}