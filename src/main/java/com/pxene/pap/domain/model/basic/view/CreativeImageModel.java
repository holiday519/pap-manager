package com.pxene.pap.domain.model.basic.view;

import java.math.BigDecimal;

public class CreativeImageModel {
    private String mapId;

    private String campaignId;

    private BigDecimal price;

    private String type;

    private String ftype;

    private String ctype;

    private Integer w;

    private Integer h;

    private String curl;

    private String landingUrl;

    private String sourceUrl;

    private String projectId;

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId == null ? null : mapId.trim();
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId == null ? null : campaignId.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype == null ? null : ftype.trim();
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype == null ? null : ctype.trim();
    }

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }

    public String getCurl() {
        return curl;
    }

    public void setCurl(String curl) {
        this.curl = curl == null ? null : curl.trim();
    }

    public String getLandingUrl() {
        return landingUrl;
    }

    public void setLandingUrl(String landingUrl) {
        this.landingUrl = landingUrl == null ? null : landingUrl.trim();
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl == null ? null : sourceUrl.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", mapId=").append(mapId);
        sb.append(", campaignId=").append(campaignId);
        sb.append(", price=").append(price);
        sb.append(", type=").append(type);
        sb.append(", ftype=").append(ftype);
        sb.append(", ctype=").append(ctype);
        sb.append(", w=").append(w);
        sb.append(", h=").append(h);
        sb.append(", curl=").append(curl);
        sb.append(", landingUrl=").append(landingUrl);
        sb.append(", sourceUrl=").append(sourceUrl);
        sb.append(", projectId=").append(projectId);
        sb.append("]");
        return sb.toString();
    }
}