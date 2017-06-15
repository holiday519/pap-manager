package com.pxene.pap.domain.beans;

/**
 * 查询广告主、项目、活动、创意时公共继承类 类中主要有展现、点击等投放数据
 */
public class BasicDataBean {
	// 展现数
	private Long impressionAmount = 0l;
	// 点击数
	private Long clickAmount = 0l;
	// 二跳数
	private Long jumpAmount = 0l;
	// 点击率
	private Float clickRate = 0f;
	// 总花费
	private Double totalCost = 0d;
	// 展现成本
	private Float impressionCost = 0f;
	// 点击成本
	private Float clickCost = 0f;
	// 二跳成本
	private Float jumpCost = 0f;
	// 修正成本
	private Double adxCost = 0d;

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

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
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

	public Double getAdxCost() {
		return adxCost;
	}

	public void setAdxCost(Double adxCost) {
		this.adxCost = adxCost;
	}

	@Override
	public String toString() {
		return "CreativeBasicBean [impressionAmount=" + impressionAmount
				+ ", clickAmount=" + clickAmount + ", jumpAmount=" + jumpAmount
				+ ", clickRate=" + clickRate + ", totalCost=" + totalCost
				+ ", impressionCost=" + impressionCost + ", clickCost="
				+ clickCost + ", jumpCost=" + jumpCost + ",adxCost="+adxCost+ "]";
	}

}
