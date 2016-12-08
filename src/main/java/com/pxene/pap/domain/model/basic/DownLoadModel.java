package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class DownLoadModel {
    private String id;

    private String path;

    private String appOs;

    private String appName;

    private String appId;

    private String appPkgName;

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

    public String getAppOs() {
        return appOs;
    }

    public void setAppOs(String appOs) {
        this.appOs = appOs == null ? null : appOs.trim();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getAppPkgName() {
        return appPkgName;
    }

    public void setAppPkgName(String appPkgName) {
        this.appPkgName = appPkgName == null ? null : appPkgName.trim();
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