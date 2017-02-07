package com.pxene.pap.domain.beans;

/**
 * 用于查询数据统计（小时、天）
 */
public class RegionDataBean {

	private String id;
	private String name;

	private Long bidAmount;
	private Long winAmount;
	private Float winRate;
	private Long impressionAmount;
	private Float impressionRate;
	private Long clickAmount;
	private Float clickRate;
	private Long arrivalAmount;
	private Float arrivalRate;
	private Long uniqueAmount;
	private Long jumpAmount;
	private Float jumpRate;

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

	public Long getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(Long bidAmount) {
		this.bidAmount = bidAmount;
	}

	public Long getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(Long winAmount) {
		this.winAmount = winAmount;
	}

	public Float getWinRate() {
		return winRate;
	}

	public void setWinRate(Float winRate) {
		this.winRate = winRate;
	}

	public Long getImpressionAmount() {
		return impressionAmount;
	}

	public void setImpressionAmount(Long impressionAmount) {
		this.impressionAmount = impressionAmount;
	}

	public Float getImpressionRate() {
		return impressionRate;
	}

	public void setImpressionRate(Float impressionRate) {
		this.impressionRate = impressionRate;
	}

	public Long getClickAmount() {
		return clickAmount;
	}

	public void setClickAmount(Long clickAmount) {
		this.clickAmount = clickAmount;
	}

	public Float getClickRate() {
		return clickRate;
	}

	public void setClickRate(Float clickRate) {
		this.clickRate = clickRate;
	}

	public Long getArrivalAmount() {
		return arrivalAmount;
	}

	public void setArrivalAmount(Long arrivalAmount) {
		this.arrivalAmount = arrivalAmount;
	}

	public Float getArrivalRate() {
		return arrivalRate;
	}

	public void setArrivalRate(Float arrivalRate) {
		this.arrivalRate = arrivalRate;
	}

	public Long getUniqueAmount() {
		return uniqueAmount;
	}

	public void setUniqueAmount(Long uniqueAmount) {
		this.uniqueAmount = uniqueAmount;
	}

	public Long getJumpAmount() {
		return jumpAmount;
	}

	public void setJumpAmount(Long jumpAmount) {
		this.jumpAmount = jumpAmount;
	}

	public Float getJumpRate() {
		return jumpRate;
	}

	public void setJumpRate(Float jumpRate) {
		this.jumpRate = jumpRate;
	}

	@Override
	public String toString() {
		return "RegionDataBean [Id=" + id + ", name=" + name + ", bidAmount="
				+ bidAmount + ", winAmount=" + winAmount + ", winRate="
				+ winRate + ", impressionAmount=" + impressionAmount
				+ ", impressionRate=" + impressionRate + ", clickAmount="
				+ clickAmount + ", clickRate=" + clickRate + ", arrivalAmount="
				+ arrivalAmount + ", arrivalRate=" + arrivalRate
				+ ", uniqueAmount=" + uniqueAmount + ", jumpAmount="
				+ jumpAmount + ", jumpRate=" + jumpRate + "]";
	}

}
