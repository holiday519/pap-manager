package com.pxene.pap.domain.model.view;

public class TmplVideoDetailModel {
    private String id;

    private Float maxVolume;

    private Integer maxTimelength;

    private String imageTmplId;

    private Integer width;

    private Integer height;

    private String sizeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Float getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(Float maxVolume) {
        this.maxVolume = maxVolume;
    }

    public Integer getMaxTimelength() {
        return maxTimelength;
    }

    public void setMaxTimelength(Integer maxTimelength) {
        this.maxTimelength = maxTimelength;
    }

    public String getImageTmplId() {
        return imageTmplId;
    }

    public void setImageTmplId(String imageTmplId) {
        this.imageTmplId = imageTmplId == null ? null : imageTmplId.trim();
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName == null ? null : sizeName.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", maxVolume=").append(maxVolume);
        sb.append(", maxTimelength=").append(maxTimelength);
        sb.append(", imageTmplId=").append(imageTmplId);
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", sizeName=").append(sizeName);
        sb.append("]");
        return sb.toString();
    }
}