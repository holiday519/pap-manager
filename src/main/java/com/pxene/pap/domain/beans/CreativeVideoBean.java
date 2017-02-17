package com.pxene.pap.domain.beans;

public class CreativeVideoBean extends CreativeBasicBean {

	private String videoId;
	private String videoPath;
	private String imageId;
	private String imagePath;

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

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

	public CreativeVideoBean() {
		super();
	}

	@Override
	public String toString() {
		return "CreativeVideoBean [videoId=" + videoId + ", videoPath="
				+ videoPath + ", imageId=" + imageId + ", imagePath="
				+ imagePath + "]";
	}

}
