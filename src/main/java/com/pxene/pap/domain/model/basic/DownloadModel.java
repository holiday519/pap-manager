package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class DownloadModel {
    private String id;

    private String path;

    private String appos;

    private String appname;

    private String appid;

    private String apppkgname;

    private Date createtime;

    private Date updatetime;

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

    public String getAppos() {
        return appos;
    }

    public void setAppos(String appos) {
        this.appos = appos == null ? null : appos.trim();
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname == null ? null : appname.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getApppkgname() {
        return apppkgname;
    }

    public void setApppkgname(String apppkgname) {
        this.apppkgname = apppkgname == null ? null : apppkgname.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}