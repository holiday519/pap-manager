package com.pxene.pap.domain.beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AppFlowHourBean {
    private Integer id;

    private String appId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date datetime;

    private Long requestAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Long getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(Long requestAmount) {
        this.requestAmount = requestAmount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", appId=").append(appId);
        sb.append(", datetime=").append(datetime);
        sb.append(", requestAmount=").append(requestAmount);
        sb.append("]");
        return sb.toString();
    }
}