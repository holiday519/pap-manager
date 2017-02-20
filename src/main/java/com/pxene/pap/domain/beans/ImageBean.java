package com.pxene.pap.domain.beans;

/**
 * 图片
 */
public class ImageBean extends MediaBean {
	/**
	 * 宽
	 */
	private Integer width;
	/**
	 * 高
	 */
	private Integer height;

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

	@Override
	public String toString() {
		return "ImageBean [width=" + width + ", height=" + height + "]";
	}

}
