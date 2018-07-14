package com.pxene.pap.domain.beans;

/**
 * 视频模版信息
 */
public class VideoTmplBean {

	private String id;

	private String formats;

	private Float maxVolume;

	private Integer maxTimelength;

	private Integer width;

	private Integer height;

	private ImageTmplBean imageTmplBeanInVideo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFormats() {
		return formats;
	}

	public void setFormats(String formats) {
		this.formats = formats;
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

	public ImageTmplBean getImageTmplBeanInVideo() {
		return imageTmplBeanInVideo;
	}

	public void setImageTmplBeanInVideo(ImageTmplBean imageTmplBeanInVideo) {
		this.imageTmplBeanInVideo = imageTmplBeanInVideo;
	}

	@Override
	public String toString() {
		return "VideoTmplBean [id=" + id + ", formats=" + formats
				+ ", maxVolume=" + maxVolume + ", maxTimelength="
				+ maxTimelength + ", width=" + width + ", height=" + height
				+ ", imageTmplBeanInVideo=" + imageTmplBeanInVideo + "]";
	}

}
