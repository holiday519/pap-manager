package com.pxene.pap.domain.beans;

/**
 * 用于查询数据统计（小时、天）
 */
public class AppDataBean {

	private String id;
	private String appId;
	private String appName;
	private String adxId;
	private String appType;
	private String pkgName;

	private Long bidAmount;
	private Long winAmount;
	private Float winRate;
	private Long impressionAmount;
	private Float impressionRate;
	private Long clickAmount;
	private Float clickRate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getAdxId() {
		return adxId;
	}

	public void setAdxId(String adxId) {
		this.adxId = adxId;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
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

	@Override
	public String toString() {
		return "AppDataBean [id=" + id + ", appId=" + appId + ", appName="
				+ appName + ", adxId=" + adxId + ", appType=" + appType
				+ ", pkgName=" + pkgName + ", bidAmount=" + bidAmount
				+ ", winAmount=" + winAmount + ", winRate=" + winRate
				+ ", impressionAmount=" + impressionAmount
				+ ", impressionRate=" + impressionRate + ", clickAmount="
				+ clickAmount + ", clickRate=" + clickRate + "]";
	}

}
