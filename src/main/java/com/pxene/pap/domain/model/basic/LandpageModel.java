package com.pxene.pap.domain.model.basic;

public class LandpageModel {
    private String id;

    private String campaignId;

    private String name;

    private String url;

    private String anidDeepLink;

    private String iosDeepLink;

    private String monitorUrl;

    private String remark;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getAnidDeepLink() {
        return anidDeepLink;
    }

    public void setAnidDeepLink(String anidDeepLink) {
        this.anidDeepLink = anidDeepLink == null ? null : anidDeepLink.trim();
    }

    public String getIosDeepLink() {
        return iosDeepLink;
    }

    public void setIosDeepLink(String iosDeepLink) {
        this.iosDeepLink = iosDeepLink == null ? null : iosDeepLink.trim();
    }

    public String getMonitorUrl() {
        return monitorUrl;
    }

    public void setMonitorUrl(String monitorUrl) {
        this.monitorUrl = monitorUrl == null ? null : monitorUrl.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
        sb.append(", url=").append(url);
        sb.append(", anidDeepLink=").append(anidDeepLink);
        sb.append(", iosDeepLink=").append(iosDeepLink);
        sb.append(", monitorUrl=").append(monitorUrl);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }
}