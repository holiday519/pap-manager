package com.pxene.pap.domain.model.basic.view;

import java.math.BigDecimal;

public class CreativeInfoflowModel {
    private String mapId;

    private String campaignId;

    private BigDecimal price;

    private String type;

    private String ftype;

    private String ctype;

    private String bundle;

    private String apkName;

    private String downloadUrl;

    private String curl;

    private String landingUrl;

    private String appDescription;

    private String title;

    private String description;

    private Integer rating;

    private String ctatext;

    private String icon;

    private String image1;

    private String image2;

    private String image3;

    private String image4;

    private String image5;

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

    public String getBundle() {
        return bundle;
    }

    public void setBundle(String bundle) {
        this.bundle = bundle == null ? null : bundle.trim();
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName == null ? null : apkName.trim();
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl == null ? null : downloadUrl.trim();
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

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription == null ? null : appDescription.trim();
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1 == null ? null : image1.trim();
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2 == null ? null : image2.trim();
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3 == null ? null : image3.trim();
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4 == null ? null : image4.trim();
    }

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5 == null ? null : image5.trim();
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
        sb.append(", bundle=").append(bundle);
        sb.append(", apkName=").append(apkName);
        sb.append(", downloadUrl=").append(downloadUrl);
        sb.append(", curl=").append(curl);
        sb.append(", landingUrl=").append(landingUrl);
        sb.append(", appDescription=").append(appDescription);
        sb.append(", title=").append(title);
        sb.append(", description=").append(description);
        sb.append(", rating=").append(rating);
        sb.append(", ctatext=").append(ctatext);
        sb.append(", icon=").append(icon);
        sb.append(", image1=").append(image1);
        sb.append(", image2=").append(image2);
        sb.append(", image3=").append(image3);
        sb.append(", image4=").append(image4);
        sb.append(", image5=").append(image5);
        sb.append(", projectId=").append(projectId);
        sb.append("]");
        return sb.toString();
    }
}