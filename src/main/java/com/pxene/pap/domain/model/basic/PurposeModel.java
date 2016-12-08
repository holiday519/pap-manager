package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class PurposeModel {
    private String id;

    private String campaignid;

    private String name;

    private String landpageid;

    private String downloadid;

    private String remark;

    private Date createtime;

    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCampaignid() {
        return campaignid;
    }

    public void setCampaignid(String campaignid) {
        this.campaignid = campaignid == null ? null : campaignid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLandpageid() {
        return landpageid;
    }

    public void setLandpageid(String landpageid) {
        this.landpageid = landpageid == null ? null : landpageid.trim();
    }

    public String getDownloadid() {
        return downloadid;
    }

    public void setDownloadid(String downloadid) {
        this.downloadid = downloadid == null ? null : downloadid.trim();
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