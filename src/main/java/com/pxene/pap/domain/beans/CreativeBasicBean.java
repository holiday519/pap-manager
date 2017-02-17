package com.pxene.pap.domain.beans;

/**
 * 查询广告主、项目、活动、创意时公共继承类 类中主要有展现、点击等投放数据
 */
public class CreativeBasicBean {

	private String id;
	private String type;// 创意类型
	private String campaignId;// 活动ID
	private String name;// 名称
	private String status;// 创意审核状态
	private Float price;// 创意价格
	private Long impressionAmount;// 展现数
	private Long clickAmount;// 点击数
	private Long jumpAmount;// 二跳数
	private Float clickRate; // 点击率
	private Float totalCost; // 总花费
	private Float impressionCost;// 展现成本
	private Float clickCost;// 点击成本
	private Float jumpCost;// 二跳成本

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Long getImpressionAmount() {
		return impressionAmount;
	}

	public void setImpressionAmount(Long impressionAmount) {
		this.impressionAmount = impressionAmount;
	}

	public Long getClickAmount() {
		return clickAmount;
	}

	public void setClickAmount(Long clickAmount) {
		this.clickAmount = clickAmount;
	}

	public Long getJumpAmount() {
		return jumpAmount;
	}

	public void setJumpAmount(Long jumpAmount) {
		this.jumpAmount = jumpAmount;
	}

	public Float getClickRate() {
		return clickRate;
	}

	public void setClickRate(Float clickRate) {
		this.clickRate = clickRate;
	}

	public Float getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Float totalCost) {
		this.totalCost = totalCost;
	}

	public Float getImpressionCost() {
		return impressionCost;
	}

	public void setImpressionCost(Float impressionCost) {
		this.impressionCost = impressionCost;
	}

	public Float getClickCost() {
		return clickCost;
	}

	public void setClickCost(Float clickCost) {
		this.clickCost = clickCost;
	}

	public Float getJumpCost() {
		return jumpCost;
	}

	public void setJumpCost(Float jumpCost) {
		this.jumpCost = jumpCost;
	}

	@Override
	public String toString() {
		return "ListCreativeDataBean [id=" + id + ", type=" + type
				+ ", campaignId=" + campaignId + ", name=" + name + ", status="
				+ status + ", price=" + price + ", impressionAmount="
				+ impressionAmount + ", clickAmount=" + clickAmount
				+ ", jumpAmount=" + jumpAmount + ", clickRate=" + clickRate
				+ ", totalCost=" + totalCost + ", impressionCost="
				+ impressionCost + ", clickCost=" + clickCost + ", jumpCost="
				+ jumpCost + "]";
	}

}
