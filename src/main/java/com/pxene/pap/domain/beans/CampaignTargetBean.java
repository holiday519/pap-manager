package com.pxene.pap.domain.beans;

import java.util.Arrays;

/**
 * 活动定向
 */
public class CampaignTargetBean {

	/**
	 * 活动id
	 */
	private String id;

	/**
	 * 地域定向
	 */
	private String[] region;
	/**
	 * 广告类型定向
	 */
	private String[] adType;
	/**
	 * 时间定向
	 */
	private String[] time;
	/**
	 * 网络定向
	 */
	private String[] network;
	/**
	 * 运营商定向
	 */
	private String[] operator;
	/**
	 * 设备定向
	 */
	private String[] device;
	/**
	 * 系统定向
	 */
	private String[] os;
	/**
	 * 品牌定向
	 */
	private String[] brand;
	/**
	 * app定向
	 */
	private String[] app;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[] getRegion() {
		return region;
	}
	public void setRegion(String[] region) {
		this.region = region;
	}
	public String[] getAdType() {
		return adType;
	}
	public void setAdType(String[] adType) {
		this.adType = adType;
	}
	public String[] getTime() {
		return time;
	}
	public void setTime(String[] time) {
		this.time = time;
	}
	public String[] getNetwork() {
		return network;
	}
	public void setNetwork(String[] network) {
		this.network = network;
	}
	public String[] getOperator() {
		return operator;
	}
	public void setOperator(String[] operator) {
		this.operator = operator;
	}
	public String[] getDevice() {
		return device;
	}
	public void setDevice(String[] device) {
		this.device = device;
	}
	public String[] getOs() {
		return os;
	}
	public void setOs(String[] os) {
		this.os = os;
	}
	public String[] getBrand() {
		return brand;
	}
	public void setBrand(String[] brand) {
		this.brand = brand;
	}
	public String[] getApp() {
		return app;
	}
	public void setApp(String[] app) {
		this.app = app;
	}
	@Override
	public String toString() {
		return "CampaignTargetBean [id=" + id + ", region="
				+ Arrays.toString(region) + ", adType="
				+ Arrays.toString(adType) + ", time=" + Arrays.toString(time)
				+ ", network=" + Arrays.toString(network) + ", operator="
				+ Arrays.toString(operator) + ", device="
				+ Arrays.toString(device) + ", os=" + Arrays.toString(os)
				+ ", brand=" + Arrays.toString(brand) + ", app="
				+ Arrays.toString(app) + "]";
	}
}
