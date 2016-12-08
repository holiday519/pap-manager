package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class VideoTmplModel {
    private String id;

    private String sizeid;

    private Float maxvolume;

    private Integer maxtimelength;

    private String imagetmplid;

    private String remark;

    private Date createtime;

    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSizeid() {
        return sizeid;
    }

    public void setSizeid(String sizeid) {
        this.sizeid = sizeid == null ? null : sizeid.trim();
    }

    public Float getMaxvolume() {
        return maxvolume;
    }

    public void setMaxvolume(Float maxvolume) {
        this.maxvolume = maxvolume;
    }

    public Integer getMaxtimelength() {
        return maxtimelength;
    }

    public void setMaxtimelength(Integer maxtimelength) {
        this.maxtimelength = maxtimelength;
    }

    public String getImagetmplid() {
        return imagetmplid;
    }

    public void setImagetmplid(String imagetmplid) {
        this.imagetmplid = imagetmplid == null ? null : imagetmplid.trim();
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