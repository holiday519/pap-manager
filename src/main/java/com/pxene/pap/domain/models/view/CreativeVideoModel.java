package com.pxene.pap.domain.models.view;

public class CreativeVideoModel {
    private String creativeId;

    private String campaignId;

    private String format;

    private Integer width;

    private Integer height;

    private String landpageUrl;

    private String monitorUrl;

    private String path;

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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format == null ? null : format.trim();
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
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
        sb.append(", format=").append(format);
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", landpageUrl=").append(landpageUrl);
        sb.append(", monitorUrl=").append(monitorUrl);
        sb.append(", path=").append(path);
        sb.append(", projectId=").append(projectId);
        sb.append("]");
        return sb.toString();
    }
}