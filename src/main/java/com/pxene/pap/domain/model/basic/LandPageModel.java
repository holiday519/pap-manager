package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class LandPageModel {
    private String id;

    private String path;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", path=").append(path);
        sb.append(", anidDeepLink=").append(anidDeepLink);
        sb.append(", iosDeepLink=").append(iosDeepLink);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}