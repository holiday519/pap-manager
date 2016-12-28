package com.pxene.pap.domain.model.basic.view;

import java.math.BigDecimal;

public class CreativeInfoflowModel {
    private String mapId;

    private String campaignId;

    private BigDecimal price;

    private String type;

    private String ftype;

    private String ctype;

    private String curl;

    private String landingUrl;

    private String title;

    private String description;

    private Integer rating;

    private String ctatext;

    private String iconId;

    private String image1Id;

    private String image2Id;

    private String image3Id;

    private String image4Id;

    private String image5Id;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getCtatext() {
        return ctatext;
    }

    public void setCtatext(String ctatext) {
        this.ctatext = ctatext == null ? null : ctatext.trim();
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId == null ? null : iconId.trim();
    }

    public String getImage1Id() {
        return image1Id;
    }

    public void setImage1Id(String image1Id) {
        this.image1Id = image1Id == null ? null : image1Id.trim();
    }

    public String getImage2Id() {
        return image2Id;
    }

    public void setImage2Id(String image2Id) {
        this.image2Id = image2Id == null ? null : image2Id.trim();
    }

    public String getImage3Id() {
        return image3Id;
    }

    public void setImage3Id(String image3Id) {
        this.image3Id = image3Id == null ? null : image3Id.trim();
    }

    public String getImage4Id() {
        return image4Id;
    }

    public void setImage4Id(String image4Id) {
        this.image4Id = image4Id == null ? null : image4Id.trim();
    }

    public String getImage5Id() {
        return image5Id;
    }

    public void setImage5Id(String image5Id) {
        this.image5Id = image5Id == null ? null : image5Id.trim();
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
        sb.append(", curl=").append(curl);
        sb.append(", landingUrl=").append(landingUrl);
        sb.append(", title=").append(title);
        sb.append(", description=").append(description);
        sb.append(", rating=").append(rating);
        sb.append(", ctatext=").append(ctatext);
        sb.append(", iconId=").append(iconId);
        sb.append(", image1Id=").append(image1Id);
        sb.append(", image2Id=").append(image2Id);
        sb.append(", image3Id=").append(image3Id);
        sb.append(", image4Id=").append(image4Id);
        sb.append(", image5Id=").append(image5Id);
        sb.append(", projectId=").append(projectId);
        sb.append("]");
        return sb.toString();
    }
}