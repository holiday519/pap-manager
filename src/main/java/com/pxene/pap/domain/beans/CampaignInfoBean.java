package com.pxene.pap.domain.beans;

import java.util.Arrays;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.pxene.pap.constant.PhrasesConstant;

/**
 * 活动基本信息
 */
public class CampaignInfoBean {

	/**
	 * 活动id
	 */
	@Length(max = 36, message = PhrasesConstant.LENGTH_ERROR_ID)
	private String id;
	/**
	 * 项目id
	 */
	@NotNull(message = PhrasesConstant.CAMPAIGN_NOTNULL_PROJECTID)
	@Length(max = 36, message = PhrasesConstant.CAMPAIGN_LENGTH_ERROR_PROJECTID)
	private String projectId;
	/**
	 * 名称
	 */
	@NotNull(message = PhrasesConstant.NOTNULL_NAME)
	@Length(max = 100, message = PhrasesConstant.LENGTH_ERROR_NAME)
	private String name;
	/**
	 * 活动类型
	 */
	@NotNull(message = PhrasesConstant.CAMPAIGN_NOTNULL_TYPE)
	private String type;
	/**
	 * 总预算
	 */
	@NotNull(message = PhrasesConstant.CAMPAIGN_NOTNULL_TOTALBUDGET)
	private Integer totalBudget;
	/**
	 * 日预算
	 */
	@NotNull(message = PhrasesConstant.CAMPAIGN_NOTNULL_DAILYBUDGET)
	private Integer dailyBudget;
	/**
	 * 日展现
	 */
	@NotNull(message = PhrasesConstant.CAMPAIGN_NOTNULL_DAILYIMPRESSION)
	private Integer dailyImpression;
	/**
	 * 日点击
	 */
	@NotNull(message = PhrasesConstant.CAMPAIGN_NOTNULL_DAILYCLICK)
	private Integer dailyClick;
	/**
	 * 备注
	 */
    @Length(max = 400, message = PhrasesConstant.LENGTH_ERROR_REMARK)
	private String remark;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 开始时间
	 */
	@NotNull(message = PhrasesConstant.CAMPAIGN_NOTNULL_STARTDATE)
	private Date startDate;
	/**
	 * 结束时间
	 */
	@NotNull(message = PhrasesConstant.CAMPAIGN_NOTNULL_ENDDATE)
	private Date endDate;
	
	/**
	 * 频次Id
	 */
	private String frequencyId;
	
	/**
	 * 频次
	 */
	private Frequency frequency;
	
	public static class Frequency {
		
		/**
		 * 控制对象
		 */
		@NotNull(message = PhrasesConstant.CAMPAIGN_NOTNULL_FREQUENCY_CONTROLOBJ)
		private String controlObj;
		/**
		 * 时间类型
		 */
		@NotNull(message = PhrasesConstant.CAMPAIGN_NOTNULL_FREQUENCY_TIMETYPE)
		private String timeType;
		/**
		 * 频次
		 */
		@NotNull(message = PhrasesConstant.CAMPAIGN_NOTNULL_FREQUENCY_NUMBER)
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
	private Monitor[] monitors;
	
	public static class Monitor {
		private String impressionUrl;
		private String clickUrl;
		public String getImpressionUrl() {
			return impressionUrl;
		}
		public void setImpressionUrl(String impressionUrl) {
			this.impressionUrl = impressionUrl;
		}
		public String getClickUrl() {
			return clickUrl;
		}
		public void setClickUrl(String clickUrl) {
			this.clickUrl = clickUrl;
		}
		@Override
		public String toString() {
			return "Monitor [impressionUrl=" + impressionUrl + ", clickUrl="
					+ clickUrl + "]";
		}
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

	public Monitor[] getMonitors() {
		return monitors;
	}

	public void setMonitors(Monitor[] monitors) {
		this.monitors = monitors;
	}

	public Integer getDailyBudget() {
		return dailyBudget;
	}

	public void setDailyBudget(Integer dailyBudget) {
		this.dailyBudget = dailyBudget;
	}

	@Override
	public String toString() {
		return "CampaignInfoBean [id=" + id + ", projectId=" + projectId
				+ ", name=" + name + ", type=" + type + ", totalBudget="
				+ totalBudget + ", dailyBudget=" + dailyBudget
				+ ", dailyImpression=" + dailyImpression + ", dailyClick="
				+ dailyClick + ", remark=" + remark + ", status=" + status
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", frequencyId=" + frequencyId + ", frequency=" + frequency
				+ ", monitors=" + Arrays.toString(monitors) + "]";
	}

}

