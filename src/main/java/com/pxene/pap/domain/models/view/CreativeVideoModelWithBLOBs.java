package com.pxene.pap.domain.models.view;

public class CreativeVideoModelWithBLOBs extends CreativeVideoModel {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", imonitorUrl=").append(imonitorUrl);
        sb.append(", cmonitorUrl=").append(cmonitorUrl);
        sb.append("]");
        return sb.toString();
    }
}