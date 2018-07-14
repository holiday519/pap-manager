package com.pxene.pap.domain.models.view;

public class CreativeInfoflowModel {
    private String creativeId;

    private String campaignId;

    private String landpageUrl;

    private String monitorUrl;

    private String title;

    private String description;

    private Integer appStar;

    private String ctaDescription;

    private String iconId;

    private String image1Id;

    private String image2Id;

    private String image3Id;

    private String image4Id;

    private String image5Id;

    private String projectId;

    public String getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(String creativeId) {
        this.creativeId = creativeId == null ? null : creativeId.trim();
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId == null ? null : campaignId.trim();
    }

    public String getLandpageUrl() {
        return landpageUrl;
    }

    public void setLandpageUrl(String landpageUrl) {
        this.landpageUrl = landpageUrl == null ? null : landpageUrl.trim();
    }

    public String getMonitorUrl() {
        return monitorUrl;
    }

    public void setMonitorUrl(String monitorUrl) {
        this.monitorUrl = monitorUrl == null ? null : monitorUrl.trim();
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

    public Integer getAppStar() {
        return appStar;
    }

    public void setAppStar(Integer appStar) {
        this.appStar = appStar;
    }

    public String getCtaDescription() {
        return ctaDescription;
    }

    public void setCtaDescription(String ctaDescription) {
        this.ctaDescription = ctaDescription == null ? null : ctaDescription.trim();
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
        sb.append(", creativeId=").append(creativeId);
        sb.append(", campaignId=").append(campaignId);
        sb.append(", landpageUrl=").append(landpageUrl);
        sb.append(", monitorUrl=").append(monitorUrl);
        sb.append(", title=").append(title);
        sb.append(", description=").append(description);
        sb.append(", appStar=").append(appStar);
        sb.append(", ctaDescription=").append(ctaDescription);
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