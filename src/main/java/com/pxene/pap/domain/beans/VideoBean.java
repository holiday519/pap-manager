package com.pxene.pap.domain.beans;

/**
 * 视频信息
 */
public class VideoBean extends MediaBean {
	/**
	 * 宽
	 */
	private Integer width;
	/**
	 * 高
	 */
	private Integer height;
	/**
	 * 时长
	 */
	private Integer timelength;
	/**
	 * 图片Id
	 */
	private String imageId;

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

	public Integer getTimelength() {
		return timelength;
	}

	public void setTimelength(Integer timelength) {
		this.timelength = timelength;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	@Override
	public String toString() {
		return "VideoBean [width=" + width + ", height=" + height
				+ ", timelength=" + timelength + ", imageId=" + imageId + "]";
	}

}
