package com.pxene.pap.domain.beans;


public class ProjectBean {

	private String id;
	private String advertiserId;
	private String name;
	private Integer totalBudget;
	private String remark;
	private String status;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAdvertiserId() {
		return advertiserId;
	}
	public void setAdvertiserId(String advertiserId) {
		this.advertiserId = advertiserId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getTotalBudget() {
		return totalBudget;
	}
	public void setTotalBudget(Integer totalBudget) {
		this.totalBudget = totalBudget;
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
	@Override
	public String toString() {
		return "ProjectBean [id=" + id + ", advertiserId=" + advertiserId
				+ ", name=" + name + ", totalBudget=" + totalBudget
				+ ", remark=" + remark + ", status=" + status + "]";
	}
	
	
}
