package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class InfoflowTmplModel {
    private String id;

    private Integer maxtitle;

    private Integer maxdescription;

    private String iconid;

    private String image1id;

    private String image2id;

    private String image3id;

    private String image4id;

    private String image5id;

    private String remark;

    private Date createtime;

    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getMaxtitle() {
        return maxtitle;
    }

    public void setMaxtitle(Integer maxtitle) {
        this.maxtitle = maxtitle;
    }

    public Integer getMaxdescription() {
        return maxdescription;
    }

    public void setMaxdescription(Integer maxdescription) {
        this.maxdescription = maxdescription;
    }

    public String getIconid() {
        return iconid;
    }

    public void setIconid(String iconid) {
        this.iconid = iconid == null ? null : iconid.trim();
    }

    public String getImage1id() {
        return image1id;
    }

    public void setImage1id(String image1id) {
        this.image1id = image1id == null ? null : image1id.trim();
    }

    public String getImage2id() {
        return image2id;
    }

    public void setImage2id(String image2id) {
        this.image2id = image2id == null ? null : image2id.trim();
    }

    public String getImage3id() {
        return image3id;
    }

    public void setImage3id(String image3id) {
        this.image3id = image3id == null ? null : image3id.trim();
    }

    public String getImage4id() {
        return image4id;
    }

    public void setImage4id(String image4id) {
        this.image4id = image4id == null ? null : image4id.trim();
    }

    public String getImage5id() {
        return image5id;
    }

    public void setImage5id(String image5id) {
        this.image5id = image5id == null ? null : image5id.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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