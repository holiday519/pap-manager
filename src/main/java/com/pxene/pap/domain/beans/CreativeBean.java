package com.pxene.pap.domain.beans;

import java.util.List;
/**
 * 创意
 */
public class CreativeBean {

	/**
	 * 创意id
	 */
	private String id;
	/**
	 * 活动id 
	 */
	private String campaignId;
	/**
	 * 创意名称
	 */
	private String name;
	/**
	 * 素材id数组
	 */
	private List<String> materialIds;
	/**
	 * 价格数组
	 */
	private List<Float> price;
	
	/**
	 * 创意类型数组
	 */
	private List<String> creativeType;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getMaterialIds() {
		return materialIds;
	}

	public void setMaterialIds(List<String> materialIds) {
		this.materialIds = materialIds;
	}

	public List<Float> getPrice() {
		return price;
	}

	public void setPrice(List<Float> price) {
		this.price = price;
	}

	public List<String> getCreativeType() {
		return creativeType;
	}

	public void setCreativeType(List<String> creativeType) {
		this.creativeType = creativeType;
	}

	@Override
	public String toString() {
		return "CreativeBean [id=" + id + ", campaignId=" + campaignId
				+ ", name=" + name + ", materialIds=" + materialIds
				+ ", price=" + price + ", creativeType=" + creativeType + "]";
	}

	
	
}
