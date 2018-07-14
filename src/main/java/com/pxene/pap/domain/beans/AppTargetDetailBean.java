package com.pxene.pap.domain.beans;

import java.util.Date;

/**
 * Created by wangshuai on 2017/7/13.
 */
public class AppTargetDetailBean {

    private String id;

    private String apptarget_id;

    private String word;

    private String searchType;

    private String filterType;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApptarget_id() {
        return apptarget_id;
    }

    public void setApptarget_id(String apptarget_id) {
        this.apptarget_id = apptarget_id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
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
        return "AppTargetDetailBean [id=" + id + ", apptarget_id=" + apptarget_id + ",word="+ word+",searchType="+searchType
                +",filterType="+filterType+",createTime="+createTime+",updateTime="+updateTime+"]";
    }
}
