package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class AppTmplModel {
    private String id;

    private String creativeid;

    private String tmplid;

    private Date createtime;

    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCreativeid() {
        return creativeid;
    }

    public void setCreativeid(String creativeid) {
        this.creativeid = creativeid == null ? null : creativeid.trim();
    }

    public String getTmplid() {
        return tmplid;
    }

    public void setTmplid(String tmplid) {
        this.tmplid = tmplid == null ? null : tmplid.trim();
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