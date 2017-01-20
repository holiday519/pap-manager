package com.pxene.pap.domain.beans;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * 地域码表
 */
/**
 * @author yuweichao
 *
 */
public class RegionBean {
	private String id;

	private String name;

	private BigDecimal longitude;

	private BigDecimal latitude;

	private City[] citys;

	public static class City {
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
			return "City [id=" + id + ", name=" + name + ", longitude="
					+ longitude + ", latitude=" + latitude + "]";
		}
	}

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

	public City[] getCitys() {
		return citys;
	}

	public void setCitys(City[] citys) {
		this.citys = citys;
	}

	@Override
	public String toString() {
		return "RegionBean [id=" + id + ", name=" + name + ", longitude="
				+ longitude + ", latitude=" + latitude + ", citys="
				+ Arrays.toString(citys) + "]";
	}
}
