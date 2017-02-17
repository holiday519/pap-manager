package com.pxene.pap.domain.beans;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.pxene.pap.constant.PhrasesConstant;

/**
 * 项目
 */
public class ProjectBean {

	/**
	 * 项目id
	 */
	private String id;
	/**
	 * 广告主id
	 */
	@NotNull(message = PhrasesConstant.PROJECT_NOTNULL_ADVERTISERID)
	@Length(max = 36, message = PhrasesConstant.PROJECT_LENGTH_ERROR_ADVERTISERID)
	private String advertiserId;
	/**
	 * 名称
	 */
	@NotNull(message = PhrasesConstant.NOTNULL_NAME)
	@Length(max = 100, message = PhrasesConstant.LENGTH_ERROR_NAME)
	private String name;
	/**
	 * 总预算
	 */
	@NotNull(message = PhrasesConstant.INVALID_TOTAL_BUDGET)
	private Integer totalBudget;
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
	 * KPI指标id
	 */
	@NotNull(message = PhrasesConstant.PROJECT_NOTNULL_KPI)
	@Length(max = 36, message = PhrasesConstant.PROJECT_LENGTH_ERROR_KPIID)
	private String kpiId;
	
	/**
	 * KPI指标value
	 */
	@NotNull(message = PhrasesConstant.PROJECT_NOTNULL_KPI_VALUE)
	private Integer kpiVal;
	
	public Integer getKpiVal() {
		return kpiVal;
	}
	public void setKpiVal(Integer kpiVal) {
		this.kpiVal = kpiVal;
	}
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

	public String getKpiId() {
		return kpiId;
	}
	public void setKpiId(String kpiId) {
		this.kpiId = kpiId;
	}
	@Override
	public String toString() {
		return "ProjectBean [id=" + id + ", advertiserId=" + advertiserId
				+ ", name=" + name + ", totalBudget=" + totalBudget
				+ ", remark=" + remark + ", status=" + status + ", kpiId="
				+ kpiId + ", kpiVal=" + kpiVal + "]";
	}
	
}
