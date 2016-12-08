package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class FrequencyModel {
    private String id;

    private String controlobj;

    private String timetype;

    private Integer frequency;

    private Date creaetetime;

    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getControlobj() {
        return controlobj;
    }

    public void setControlobj(String controlobj) {
        this.controlobj = controlobj == null ? null : controlobj.trim();
    }

    public String getTimetype() {
        return timetype;
    }

    public void setTimetype(String timetype) {
        this.timetype = timetype == null ? null : timetype.trim();
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Date getCreaetetime() {
        return creaetetime;
    }

    public void setCreaetetime(Date creaetetime) {
        this.creaetetime = creaetetime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}