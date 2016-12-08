package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class ImageTmplTypeModel {
    private String id;

    private String imagetmplid;

    private String imagetypeid;

    private Date createtime;

    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getImagetmplid() {
        return imagetmplid;
    }

    public void setImagetmplid(String imagetmplid) {
        this.imagetmplid = imagetmplid == null ? null : imagetmplid.trim();
    }

    public String getImagetypeid() {
        return imagetypeid;
    }

    public void setImagetypeid(String imagetypeid) {
        this.imagetypeid = imagetypeid == null ? null : imagetypeid.trim();
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