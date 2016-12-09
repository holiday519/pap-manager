package com.pxene.pap.domain.beans;

import java.util.Date;
import java.util.List;
/**
 * 活动
 */
public class CampaignBean {

	/**
	 * 活动id
	 */
	private String id;
	/**
	 * 项目id
	 */
	private String projectId;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 活动类型
	 */
	private String type;
	/**
	 * 总预算
	 */
	private int totalBudget;
	/**
	 * 日预算
	 */
	private int dailyBudget;
	/**
	 * 日展现
	 */
	private int dailyImpression;
	/**
	 * 日点击
	 */
	private int dailyClick;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 频次id
	 */
	private String frequencyId;
	/**
	 * 开始时间
	 */
	private Date startDate;
	/**
	 * 结束时间
	 */
	private Date endDate;
	/**
	 * 地域定向
	 */
	private List<String> regionTarget;
	/**
	 * 广告类型定向
	 */
	private List<String> adtypeTarget;
	/**
	 * 时间定向
	 */
	private List<String> timeTarget;
	/**
	 * 网络定向
	 */
	private List<String> networkTarget;
	/**
	 * 运营商定向
	 */
	private List<String> operatorTarget;
	/**
	 * 设备定向
	 */
	private List<String> deviceTarget;
	/**
	 * 系统定向
	 */
	private List<String> osTarget;
	/**
	 * 品牌定向
	 */
	private List<String> brandTarget;
	/**
	 * app定向
	 */
	private List<String> appTarget;
	/**
	 *控制对象 
	 */
	private String controlObj;
	/**
	 * 时间类型
	 */
	private String timeType;
	/**
	 * 频次
	 */
	private int frequency;
	
	List<MonitorBean> monitors;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTotalBudget() {
		return totalBudget;
	}

	public void setTotalBudget(int totalBudget) {
		this.totalBudget = totalBudget;
	}

	public int getDailyBudget() {
		return dailyBudget;
	}

	public void setDailyBudget(int dailyBudget) {
		this.dailyBudget = dailyBudget;
	}

	public int getDailyImpression() {
		return dailyImpression;
	}

	public void setDailyImpression(int dailyImpression) {
		this.dailyImpression = dailyImpression;
	}

	public int getDailyClick() {
		return dailyClick;
	}

	public void setDailyClick(int dailyClick) {
		this.dailyClick = dailyClick;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(String frequencyId) {
		this.frequencyId = frequencyId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<String> getRegionTarget() {
		return regionTarget;
	}

	public void setRegionTarget(List<String> regionTarget) {
		this.regionTarget = regionTarget;
	}

	public List<String> getAdtypeTarget() {
		return adtypeTarget;
	}

	public void setAdtypeTarget(List<String> adtypeTarget) {
		this.adtypeTarget = adtypeTarget;
	}

	public List<String> getTimeTarget() {
		return timeTarget;
	}

	public void setTimeTarget(List<String> timeTarget) {
		this.timeTarget = timeTarget;
	}

	public List<String> getNetworkTarget() {
		return networkTarget;
	}

	public void setNetworkTarget(List<String> networkTarget) {
		this.networkTarget = networkTarget;
	}

	public List<String> getOperatorTarget() {
		return operatorTarget;
	}

	public void setOperatorTarget(List<String> operatorTarget) {
		this.operatorTarget = operatorTarget;
	}

	public List<String> getDeviceTarget() {
		return deviceTarget;
	}

	public void setDeviceTarget(List<String> deviceTarget) {
		this.deviceTarget = deviceTarget;
	}

	public List<String> getOsTarget() {
		return osTarget;
	}

	public void setOsTarget(List<String> osTarget) {
		this.osTarget = osTarget;
	}

	public List<String> getBrandTarget() {
		return brandTarget;
	}

	public void setBrandTarget(List<String> brandTarget) {
		this.brandTarget = brandTarget;
	}

	public List<String> getAppTarget() {
		return appTarget;
	}

	public void setAppTarget(List<String> appTarget) {
		this.appTarget = appTarget;
	}

	public String getControlObj() {
		return controlObj;
	}

	public void setControlObj(String controlObj) {
		this.controlObj = controlObj;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public List<MonitorBean> getMonitors() {
		return monitors;
	}

	public void setMonitors(List<MonitorBean> monitors) {
		this.monitors = monitors;
	}

	@Override
	public String toString() {
		return "CampaignBean [id=" + id + ", projectId=" + projectId
				+ ", name=" + name + ", type=" + type + ", totalBudget="
				+ totalBudget + ", dailyBudget=" + dailyBudget
				+ ", dailyImpression=" + dailyImpression + ", dailyClick="
				+ dailyClick + ", remark=" + remark + ", status=" + status
				+ ", frequencyId=" + frequencyId + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", regionTarget=" + regionTarget
				+ ", adtypeTarget=" + adtypeTarget + ", timeTarget="
				+ timeTarget + ", networkTarget=" + networkTarget
				+ ", operatorTarget=" + operatorTarget + ", deviceTarget="
				+ deviceTarget + ", osTarget=" + osTarget + ", brandTarget="
				+ brandTarget + ", appTarget=" + appTarget + ", controlObj="
				+ controlObj + ", timeType=" + timeType + ", frequency="
				+ frequency + ", monitors=" + monitors + "]";
	}
	
}
