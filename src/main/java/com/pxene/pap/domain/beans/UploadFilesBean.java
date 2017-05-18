package com.pxene.pap.domain.beans;

import java.util.Date;

public class UploadFilesBean {
	/**
	 * 文件ID
	 */
	private String id;
	/**
	 * 文件名称
	 */
	private String name;
	/**
	 * 项目ID
	 */
	private String projectId;
	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * 匹配数量
	 */
	private int amount;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
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
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public String toString(){
		return "UploadFilesBean [id=" + id + ",name=" + name + ",projectId=" + projectId
				+ ",projectName=" + projectName
				+ ",amount=" + amount + ",createTime=" + createTime + "]";
	}
}
