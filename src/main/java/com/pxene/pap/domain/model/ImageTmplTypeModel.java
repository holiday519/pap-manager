package com.pxene.pap.domain.model;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", imageTmplId=").append(imageTmplId);
        sb.append(", imageTypeId=").append(imageTypeId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}