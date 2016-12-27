package com.pxene.pap.domain.beans;

import java.util.Arrays;
import java.util.Date;

/**
 * 活动
 */
public class CampaignInfoBean {

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
	private Integer totalBudget;
	/**
	 * 日展现
	 */
	private Integer dailyImpression;
	/**
	 * 日点击
	 */
	private Integer dailyClick;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 开始时间
	 */
	private Date startDate;
	/**
	 * 结束时间
	 */
	private Date endDate;
	
	/**
	 * 频次Id
	 */
	private String frequencyId;
	
	/**
	 * 频次
	 */
	private Frequency frequency;
	
	public class Frequency {
		
		/**
		 * 控制对象
		 */
		private String controlObj;
		/**
		 * 时间类型
		 */
		private String timeType;
		/**
		 * 频次
		 */
		private Integer number;
		
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
		public Integer getNumber() {
			return number;
		}
		public void setNumber(Integer number) {
			this.number = number;
		}
		@Override
		public String toString() {
			return "Frequency [controlObj=" + controlObj + ", timeType="
					+ timeType + ", number=" + number + "]";
		}
	}

	/**
	 * 监测地址
	 */
	private String[] monitors;

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

	public Integer getTotalBudget() {
		return totalBudget;
	}

	public void setTotalBudget(Integer totalBudget) {
		this.totalBudget = totalBudget;
	}

	public Integer getDailyImpression() {
		return dailyImpression;
	}

	public void setDailyImpression(Integer dailyImpression) {
		this.dailyImpression = dailyImpression;
	}

	public Integer getDailyClick() {
		return dailyClick;
	}

	public void setDailyClick(Integer dailyClick) {
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

	public String getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(String frequencyId) {
		this.frequencyId = frequencyId;
	}

	public Frequency getFrequency() {
		return frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	public String[] getMonitors() {
		return monitors;
	}

	public void setMonitors(String[] monitors) {
		this.monitors = monitors;
	}

	@Override
	public String toString() {
		return "CampaignBean [id=" + id + ", projectId=" + projectId
				+ ", name=" + name + ", type=" + type + ", totalBudget="
				+ totalBudget + ", dailyImpression=" + dailyImpression
				+ ", dailyClick=" + dailyClick + ", remark=" + remark
				+ ", status=" + status + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", frequencyId=" + frequencyId
				+ ", frequency=" + frequency + ", monitors="
				+ Arrays.toString(monitors) + "]";
	}
}

