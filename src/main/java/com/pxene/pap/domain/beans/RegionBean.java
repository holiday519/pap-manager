package com.pxene.pap.domain.beans;

import java.math.BigDecimal;
/**
 * 地域码表
 */
public class RegionBean {
	private String id;

    private String name;

    private BigDecimal longitude;

    private BigDecimal latitude;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "RegionBean [id=" + id + ", name=" + name + ", longitude="
				+ longitude + ", latitude=" + latitude + "]";
	}

}
