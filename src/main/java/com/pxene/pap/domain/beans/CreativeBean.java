package com.pxene.pap.domain.beans;

import java.util.List;

public class CreativeBean {

	private String id;
	private String campaignId;
	private String name;
	private List<String> materialIds;
	private List<Float> price;
	
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
	@Override
	public String toString() {
		return "CreativeBean [id=" + id + ", campaignId=" + campaignId
				+ ", name=" + name + ", materialIds=" + materialIds
				+ ", price=" + price + "]";
	}
	
	
}
