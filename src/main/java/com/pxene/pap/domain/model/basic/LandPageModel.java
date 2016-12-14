package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class LandPageModel {
    private String id;

    private String path;

    private String landingUrl;

    private String anidDeepLink;

    private String iosDeepLink;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getLandingUrl() {
        return landingUrl;
    }

    public void setLandingUrl(String landingUrl) {
        this.landingUrl = landingUrl == null ? null : landingUrl.trim();
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
}