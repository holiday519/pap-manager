package com.pxene.pap.domain.beans;

import java.util.Date;

public class PopulationBean {

	private String id;
	private String name;
	private String type;
	private String path;
	private Integer amount;
	private String remark;
	private Date updateTime;
	
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
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "PopulationBean [id=" + id + ", name=" + name + ", type=" + type
				+ ", path=" + path + ", amount=" + amount + ", remark="
				+ remark + ", updateTime=" + updateTime + "]";
	}
	
}
