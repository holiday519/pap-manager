package com.pxene.pap.domain.beans;

import java.util.Date;

public class RuleBean {

	private String id;
	
	private String name;
	
	private String historyData;
	
	private String execCycle;
	
	private String dataType;
	
	private Double data;
	
	private Float promote;
	
	private Float fare;
	
	private Float sale;
	
    private Date createtime;

    private Date updatetime;

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
    
}
