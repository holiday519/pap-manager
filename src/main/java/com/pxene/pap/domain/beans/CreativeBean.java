package com.pxene.pap.domain.beans;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.pxene.pap.constant.PhrasesConstant;

/**
 * 添加创意bean
 */
public class CreativeBean extends BasicDataBean {

	/**
	 * 创意id
	 */
	@Length(max = 36, message = PhrasesConstant.LENGTH_ERROR_ID)
	private String id;

	/**
	 * 创意类型（图片、视频、信息流）
	 */
	private String type;
	
	private String status;

	/**
	 * 审核失败信息
     */
	private String message;
	/**
	 * 活动id
	 */
	@Length(max = 36, message = PhrasesConstant.CREATIVE_LENGTH_ERROR_CAMPAIGNID)
	@NotNull(message = PhrasesConstant.CREATIVE_NOTNULL_CAMPAIGNID)
	private String campaignId;
	/**
	 * 活动id
	 */
	private String campaignName;
	/**
	 * 价格
	 */
	@NotNull(message = PhrasesConstant.CREATIVE_NOTNULL_IMAGE_PRICE)
	private Float price;
	/**
	 * 模版ID
	 */
	@Length(max = 36, message = PhrasesConstant.CREATIVE_LENGTH_ERROR_CAMPAIGNID)
	@NotNull(message = PhrasesConstant.CREATIVE_NOTNULL_TMPLID)
	private String tmplId;
	
	private String materialId;
	
	@Length(max = 400, message = PhrasesConstant.LENGTH_ERROR_REMARK)
	private String remark;
	
	/**
	 * APPID
	 */
	private String appId;
	/**
	 * APP名称
	 */
	private String appName;
	/**
	 * 创意的状态
	 */
	private String enable;
	
	private Date startDate;
	
	private Date endDate;
	
	private String materialPaths[];
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getTmplId() {
		return tmplId;
	}

	public void setTmplId(String tmplId) {
		this.tmplId = tmplId;
	}
	
	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}
	
	public String[] getMaterialPaths() {
		return materialPaths;
	}

	public void setMaterialPaths(String[] materialPaths) {
		this.materialPaths = materialPaths;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "CreativeBean [id=" + id + ", type=" + type
				+ ", status=" + status + ", campaignId=" + campaignId
				+ ", campaignName=" + campaignName + ", price=" + price + ",tmplId=" + tmplId
				+ ", remark=" + remark + ", materialId=" + materialId  
				+ ", appId=" + appId + ", appName=" + appName + ", enable=" + enable +", message=" + message+"]";
	}
	
}
