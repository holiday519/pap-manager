package com.pxene.pap.domain.beans;

import java.util.Arrays;

public class RuleBean {

	/**
	 * ID
	 */
	private String id;
	/**
	 * 活动id数组
	 */
	private String[] campaignIds;
	/**
	 * 活动名称数组
	 */
	private String[] campaignNames;
	/**
	 * 规则名称
	 */
	private String name;
	/**
	 * 历史数据时段
	 */
	private String historyData;
	/**
	 * 规则执行周期
	 */
	private String execCycle;
	
	private Condition[] conditions;
	
	public static class Condition {
		/**
		 * 指标类型
		 */
		private String dataType;
		/**
		 * 比较类型
		 */
		private String compareType;
		/**
		 * 指标量
		 */
		private Double data;
		public String getDataType() {
			return dataType;
		}
		public void setDataType(String dataType) {
			this.dataType = dataType;
		}
		public String getCompareType() {
			return compareType;
		}
		public void setCompareType(String compareType) {
			this.compareType = compareType;
		}
		public Double getData() {
			return data;
		}
		public void setData(Double data) {
			this.data = data;
		}
		@Override
		public String toString() {
			return "ConditionBean [dataType=" + dataType + ", compareType="
					+ compareType + ", data=" + data + "]";
		}
	}
	/**
	 * 流量提升
	 */
	private Float promote;
	/**
	 * 提价比
	 */
	private Float fare;
	/**
	 * 降价比
	 */
	private Float sale;
	/**
	 * 状态
	 */
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[] getCampaignIds() {
		return campaignIds;
	}
	public void setCampaignIds(String[] campaignIds) {
		this.campaignIds = campaignIds;
	}
	public String[] getCampaignNames() {
		return campaignNames;
	}
	public void setCampaignNames(String[] campaignNames) {
		this.campaignNames = campaignNames;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHistoryData() {
		return historyData;
	}
	public void setHistoryData(String historyData) {
		this.historyData = historyData;
	}
	public String getExecCycle() {
		return execCycle;
	}
	public void setExecCycle(String execCycle) {
		this.execCycle = execCycle;
	}
	public Condition[] getConditions() {
		return conditions;
	}
	public void setConditions(Condition[] conditions) {
		this.conditions = conditions;
	}
	public Float getPromote() {
		return promote;
	}
	public void setPromote(Float promote) {
		this.promote = promote;
	}
	public Float getFare() {
		return fare;
	}
	public void setFare(Float fare) {
		this.fare = fare;
	}
	public Float getSale() {
		return sale;
	}
	public void setSale(Float sale) {
		this.sale = sale;
	}
	@Override
	public String toString() {
		return "RuleBean [id=" + id + ", campaignIds="
				+ Arrays.toString(campaignIds) + ", campaignNames="
				+ Arrays.toString(campaignNames) + ", name=" + name
				+ ", historyData=" + historyData + ", execCycle=" + execCycle
				+ ", conditions=" + Arrays.toString(conditions) + ", promote="
				+ promote + ", fare=" + fare + ", sale=" + sale + "]";
	}

}
