package com.pxene.pap.domain.beans;

import java.util.Arrays;
import java.util.Date;

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
	/**
	 * 指标类型
	 */
	private String dataType;
	/**
	 * 指标量
	 */
	private Double data;
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
	
    private Date createtime;

    private Date updatetime;

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

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Double getData() {
		return data;
	}

	public void setData(Double data) {
		this.data = data;
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

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	@Override
	public String toString() {
		return "RuleBean [id=" + id + ", campaignIds="
				+ Arrays.toString(campaignIds) + ", name=" + name
				+ ", historyData=" + historyData + ", execCycle=" + execCycle
				+ ", dataType=" + dataType + ", data=" + data + ", promote="
				+ promote + ", fare=" + fare + ", sale=" + sale
				+ ", createtime=" + createtime + ", updatetime=" + updatetime
				+ "]";
	}

}
