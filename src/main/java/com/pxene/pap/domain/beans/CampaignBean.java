package com.pxene.pap.domain.beans;

import java.util.Date;
import java.util.List;

public class CampaignBean {

	private String id;
	private String projectId;
	private String name;
	private String type;
	private int totalBudget;
	private int totalImpression;
	private int totalClick;
	private int dailyBudget;
	private int dailyImpression;
	private int dailyClick;
	private String remark;
	private String status;
	private String uniform;
	private String frequencyId;
	private Date startDate;
	private Date endDate;
	
	private List<String> regionTarget;
	private List<String> adtypeTarget;
	private List<String> timeTarget;
	private List<String> networkTarget;
	private List<String> operatorTarget;
	private List<String> deviceTarget;
	private List<String> osTarget;
	private List<String> brandTarget;
	private List<String> appTarget;
	
	private String controlObj;
	private String timeType;
	private int frequency;
	
	List<MonitorBean> monitors;
	
	public List<MonitorBean> getMonitors() {
		return monitors;
	}
	public void setMonitors(List<MonitorBean> monitors) {
		this.monitors = monitors;
	}
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
	public int getTotalImpression() {
		return totalImpression;
	}
	public void setTotalImpression(int totalImpression) {
		this.totalImpression = totalImpression;
	}
	public int getTotalClick() {
		return totalClick;
	}
	public void setTotalClick(int totalClick) {
		this.totalClick = totalClick;
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
	public String getUniform() {
		return uniform;
	}
	public void setUniform(String uniform) {
		this.uniform = uniform;
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
	public String getControlObj() {
		return controlObj;
	}
	public void setControlObj(String controlObj) {
		this.controlObj = controlObj;
	}
	@Override
	public String toString() {
		return "CampaignBean [id=" + id + ", projectId=" + projectId
				+ ", name=" + name + ", type=" + type + ", totalBudget="
				+ totalBudget + ", totalImpression=" + totalImpression
				+ ", totalClick=" + totalClick + ", dailyBudget=" + dailyBudget
				+ ", dailyImpression=" + dailyImpression + ", dailyClick="
				+ dailyClick + ", remark=" + remark + ", status=" + status
				+ ", uniform=" + uniform + ", frequencyId=" + frequencyId
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", regionTarget=" + regionTarget + ", adtypeTarget="
				+ adtypeTarget + ", timeTarget=" + timeTarget
				+ ", networkTarget=" + networkTarget + ", operatorTarget="
				+ operatorTarget + ", deviceTarget=" + deviceTarget
				+ ", osTarget=" + osTarget + ", brandTarget=" + brandTarget
				+ ", appTarget=" + appTarget + ", controlObj=" + controlObj
				+ ", timeType=" + timeType + ", frequency=" + frequency
				+ ", monitors=" + monitors + "]";
	}
	
}
