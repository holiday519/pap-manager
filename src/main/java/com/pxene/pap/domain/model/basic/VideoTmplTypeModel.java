package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class VideoTmplTypeModel {
    private String id;

    private String videotmplid;

    private String videotypeid;

    private Date createtime;

    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getVideotmplid() {
        return videotmplid;
    }

    public void setVideotmplid(String videotmplid) {
        this.videotmplid = videotmplid == null ? null : videotmplid.trim();
    }

    public String getVideotypeid() {
        return videotypeid;
    }

    public void setVideotypeid(String videotypeid) {
        this.videotypeid = videotypeid == null ? null : videotypeid.trim();
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