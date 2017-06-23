package com.pxene.pap.domain.beans;

public class AnalysisBean {

	private String id;
	private String name;
	private String time;
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
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
	@Override
	public String toString() {
		return "AnalysisBean [id=" + id + ", name=" + name + ", time=" + time
				+ ", impressionAmount=" + impressionAmount + ", clickAmount="
				+ clickAmount + ", jumpAmount=" + jumpAmount + ", clickRate="
				+ clickRate + ", totalCost=" + totalCost + ", impressionCost="
				+ impressionCost + ", clickCost=" + clickCost + ", jumpCost="
				+ jumpCost + "]";
	}
	
}
