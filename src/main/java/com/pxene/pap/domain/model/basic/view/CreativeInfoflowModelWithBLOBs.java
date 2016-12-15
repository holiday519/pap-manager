package com.pxene.pap.domain.model.basic.view;

public class CreativeInfoflowModelWithBLOBs extends CreativeInfoflowModel {
    private String imonitorUrl;

    private String cmonitorUrl;

    public String getImonitorUrl() {
        return imonitorUrl;
    }

    public void setImonitorUrl(String imonitorUrl) {
        this.imonitorUrl = imonitorUrl == null ? null : imonitorUrl.trim();
    }

    public String getCmonitorUrl() {
        return cmonitorUrl;
    }

    public void setCmonitorUrl(String cmonitorUrl) {
        this.cmonitorUrl = cmonitorUrl == null ? null : cmonitorUrl.trim();
    }
}