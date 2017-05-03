package com.pxene.pap.domain.beans;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.pxene.pap.constant.PhrasesConstant;

/**
 * 项目
 */
public class ProjectBean extends BasicDataBean {

	/**
	 * 项目id
	 */
	private String id;
	
	@NotNull(message = PhrasesConstant.NAME_NOT_NULL)
	@Length(max = 100, message = PhrasesConstant.LENGTH_ERROR_NAME)
	private String name;
	/**
	 * 广告主id
	 */
	@NotNull(message = PhrasesConstant.PROJECT_NOTNULL_ADVERTISERID)
	@Length(max = 37, message = PhrasesConstant.PROJECT_LENGTH_ERROR_ADVERTISERID)
	private String advertiserId;
	/**
	 * 名称
	 */
	private String advertiserName;
	/**
	 * 行业id
	 */
	private Integer industryId;
	/**
	 * 行业名称
	 */
	private String industryName;
	/**
	 * 总预算
	 */
	@NotNull(message = PhrasesConstant.INVALID_TOTAL_BUDGET)
	private Integer totalBudget;
	/**
	 * 备注
	 */
    @Length(max = 200, message = PhrasesConstant.LENGTH_ERROR_REMARK)
	private String remark;
	/**
	 * 状态
	 */
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
	public String getAdvertiserName() {
		return advertiserName;
	}
	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}
	public Integer getIndustryId() {
		return industryId;
	}
	public void setIndustryId(Integer industryId) {
		this.industryId = industryId;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	@Override
    public String toString()
    {
        return "ProjectBean [id=" + id + ", name=" + name + ", advertiserId=" + advertiserId + ", advertiserName=" + advertiserName + ", industryId=" + industryId + ", industryName=" + industryName
                + ", totalBudget=" + totalBudget + ", remark=" + remark + ", status=" + status + "]";
    }
	
}
