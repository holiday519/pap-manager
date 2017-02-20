package com.pxene.pap.domain.beans;

/**
 * 媒体
 */
public class MediaBean {
	/**
	 * id
	 */
	private String id;
	/**
	 * 类型
	 */
	private String format;
	/**
	 * 路径
	 */
	private String path;
	/**
	 * 大小
	 */
	private Float volume;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Float getVolume() {
		return volume;
	}

	public void setVolume(Float volume) {
		this.volume = volume;
	}

	@Override
	public String toString() {
		return "MediaBean [id=" + id + ", format=" + format + ", path=" + path
				+ ", volume=" + volume + "]";
	}

}
