package com.pxene.pap.domain.beans;

public class CreativeImageBean extends CreativeBasicBean {

	private String imageId;
	private String imagePath;

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public CreativeImageBean() {
		super();
	}

	@Override
	public String toString() {
		return "CreativeImageBean [imageId=" + imageId + ", imagePath="
				+ imagePath + "]";
	}

}
