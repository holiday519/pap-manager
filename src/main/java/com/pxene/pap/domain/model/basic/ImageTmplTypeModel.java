package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class ImageTmplTypeModel {
    private String id;

    private String imageTmplId;

    private String imageTypeId;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getImageTmplId() {
        return imageTmplId;
    }

    public void setImageTmplId(String imageTmplId) {
        this.imageTmplId = imageTmplId == null ? null : imageTmplId.trim();
    }

    public String getImageTypeId() {
        return imageTypeId;
    }

    public void setImageTypeId(String imageTypeId) {
        this.imageTypeId = imageTypeId == null ? null : imageTypeId.trim();
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