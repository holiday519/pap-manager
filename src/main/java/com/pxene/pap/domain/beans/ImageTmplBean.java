package com.pxene.pap.domain.beans;

/**
 * 图片模版
 */
public class ImageTmplBean {

	private String id;

	private String formats;

	private Float maxVolume;

	private Integer height;

	private Integer width;

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

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	@Override
	public String toString() {
		return "ImageTmplBean [id=" + id + ", formats=" + formats
				+ ", maxVolume=" + maxVolume + ", height=" + height
				+ ", width=" + width + "]";
	}

}
