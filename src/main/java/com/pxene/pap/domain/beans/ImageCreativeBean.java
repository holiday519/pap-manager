package com.pxene.pap.domain.beans;

public class ImageCreativeBean extends CreativeBean {

	private String imageId;
	private String imagePath;

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public String toString() {
		return "ImageCreativeBean [imageId=" + imageId + ", imagePath="
				+ imagePath + "]";
	}
	
}
