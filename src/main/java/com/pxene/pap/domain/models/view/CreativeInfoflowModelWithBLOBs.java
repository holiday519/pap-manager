package com.pxene.pap.domain.models.view;

public class CreativeInfoflowModelWithBLOBs extends CreativeInfoflowModel {
    private String impressionUrl;

    private String clickUrl;

    public String getImpressionUrl() {
        return impressionUrl;
    }

    public void setImpressionUrl(String impressionUrl) {
        this.impressionUrl = impressionUrl == null ? null : impressionUrl.trim();
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl == null ? null : clickUrl.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", impressionUrl=").append(impressionUrl);
        sb.append(", clickUrl=").append(clickUrl);
        sb.append("]");
        return sb.toString();
    }
}