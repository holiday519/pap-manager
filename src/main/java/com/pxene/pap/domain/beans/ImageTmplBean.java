package com.pxene.pap.domain.beans;

public class ImageTmplBean {
	
	private String id;

	private String sizeId;

	private Float maxVolume;

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
		return "ImageTmplBean [id=" + id + ", sizeId=" + sizeId
				+ ", maxVolume=" + maxVolume + ", remark=" + remark
				+ ", imageTypeId=" + imageTypeId + "]";
	}
	
}
