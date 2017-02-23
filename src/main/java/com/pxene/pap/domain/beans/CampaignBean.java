package com.pxene.pap.domain.beans;

import java.util.Arrays;
import java.util.Date;

/**
 * 活动所有信息
 */
public class CampaignBean extends BasicDataBean {

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
	 * 日预算
	 */
	private Integer dailyBudget;
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

	private String uniform;

	/**
	 * 定向
	 *
	 */
	private Target target;

	public static class Target {

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

		/**
		 * 人群定向
		 */
		private Population[] population;

		public static class Population {
			private String id;
			private String type;
			private String path;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

			public String getPath() {
				return path;
			}

			public void setPath(String path) {
				this.path = path;
			}

			@Override
			public String toString() {
				return "Population [id=" + id + ", type=" + type + ", path="
						+ path + "]";
			}

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

		public Population[] getPopulation() {
			return population;
		}

		public void setPopulation(Population[] population) {
			this.population = population;
		}

		@Override
		public String toString() {
			return "Target [region=" + Arrays.toString(region) + ", adType="
					+ Arrays.toString(adType) + ", time="
					+ Arrays.toString(time) + ", network="
					+ Arrays.toString(network) + ", operator="
					+ Arrays.toString(operator) + ", device="
					+ Arrays.toString(device) + ", os=" + Arrays.toString(os)
					+ ", brand=" + Arrays.toString(brand) + ", app="
					+ Arrays.toString(app) + ", population="
					+ Arrays.toString(population) + "]";
		}

	}

	/**
	 * 频次
	 */
	private Frequency frequency;

	public static class Frequency {

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
	private Monitor[] monitors;

	public static class Monitor {
		/**
		 * 展现监测地址
		 */
		private String impressionUrl;
		/**
		 * 点击监测地址
		 */
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

	/**
	 * 投放量控制策略
	 */
	private Quantity[] quantities;

	public static class Quantity {
		private Date startDate;
		private Date endDate;
		private Integer dailyBudget;
		private Integer dailyImpression;

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

		public Integer getDailyBudget() {
			return dailyBudget;
		}

		public void setDailyBudget(Integer dailyBudget) {
			this.dailyBudget = dailyBudget;
		}

		public Integer getDailyImpression() {
			return dailyImpression;
		}

		public void setDailyImpression(Integer dailyImpression) {
			this.dailyImpression = dailyImpression;
		}

	}

	private String landpageId;
	private String landpageName;
	private String landpageUrl;

	public Quantity[] getQuantities() {
		return quantities;
	}

	public void setQuantities(Quantity[] quantities) {
		this.quantities = quantities;
	}

	public String getLandpageId() {
		return landpageId;
	}

	public void setLandpageId(String landpageId) {
		this.landpageId = landpageId;
	}

	public String getLandpageName() {
		return landpageName;
	}

	public void setLandpageName(String landpageName) {
		this.landpageName = landpageName;
	}

	public String getLandpageUrl() {
		return landpageUrl;
	}

	public void setLandpageUrl(String landpageUrl) {
		this.landpageUrl = landpageUrl;
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

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
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

	public String getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(String frequencyId) {
		this.frequencyId = frequencyId;
	}

	public Integer getDailyBudget() {
		return dailyBudget;
	}

	public void setDailyBudget(Integer dailyBudget) {
		this.dailyBudget = dailyBudget;
	}

	public String getUniform() {
		return uniform;
	}

	public void setUniform(String uniform) {
		this.uniform = uniform;
	}

	@Override
	public String toString() {
		return "CampaignBean [id=" + id + ", projectId=" + projectId
				+ ", name=" + name + ", type=" + type + ", totalBudget="
				+ totalBudget + ", dailyBudget=" + dailyBudget
				+ ", dailyImpression=" + dailyImpression + ", dailyClick="
				+ dailyClick + ", remark=" + remark + ", status=" + status
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", frequencyId=" + frequencyId + ", uniform=" + uniform
				+ ", target=" + target + ", frequency=" + frequency
				+ ", monitors=" + Arrays.toString(monitors) + ", quantity="
				+ Arrays.toString(quantities) + ", landpageId=" + landpageId
				+ ", landpageName=" + landpageName + ", landpageUrl="
				+ landpageUrl + "]";
	}

}
