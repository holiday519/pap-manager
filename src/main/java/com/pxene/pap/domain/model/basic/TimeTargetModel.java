package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class TimeTargetModel {
    private String id;

    private String campaignid;

    private String timeid;

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

    public String getTimeid() {
        return timeid;
    }

    public void setTimeid(String timeid) {
        this.timeid = timeid == null ? null : timeid.trim();
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