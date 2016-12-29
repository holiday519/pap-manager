package com.pxene.pap.domain.beans;

public class VideoTmplBean {

	private String id;

	private String sizeId;

	private Float maxVolume;

	private Integer maxTimelength;

	private String imageTmplId;

	private String remark;

	private String imageTypeId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSizeId() {
		return sizeId;
	}

	public void setSizeId(String sizeId) {
		this.sizeId = sizeId;
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
		this.imageTmplId = imageTmplId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getImageTypeId() {
		return imageTypeId;
	}

	public void setImageTypeId(String imageTypeId) {
		this.imageTypeId = imageTypeId;
	}

	@Override
	public String toString() {
		return "VideoTmplBean [id=" + id + ", sizeId=" + sizeId
				+ ", maxVolume=" + maxVolume + ", maxTimelength="
				+ maxTimelength + ", imageTmplId=" + imageTmplId + ", remark="
				+ remark + ", imageTypeId=" + imageTypeId + "]";
	}
}
