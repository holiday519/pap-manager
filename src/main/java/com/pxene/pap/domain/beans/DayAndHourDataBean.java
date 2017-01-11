package com.pxene.pap.domain.beans;

/**
 * 用于查询数据统计（小时、天）
 */
public class DayAndHourDataBean {

	private String regionId;
	private String regionName;

	private String creativeId;
	private String creativeName;

	private String appId;
	private String realAppId;
	private String appName;
	private String adxId;
	private String appType;
	private String pkgName;

	private String campaignId;
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
	private Integer residentTime;

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCreativeId() {
		return creativeId;
	}

	public void setCreativeId(String creativeId) {
		this.creativeId = creativeId;
	}

	public String getCreativeName() {
		return creativeName;
	}

	public void setCreativeName(String creativeName) {
		this.creativeName = creativeName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getRealAppId() {
		return realAppId;
	}

	public void setRealAppId(String realAppId) {
		this.realAppId = realAppId;
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

	public String getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
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

	public Integer getResidentTime() {
		return residentTime;
	}

	public void setResidentTime(Integer residentTime) {
		this.residentTime = residentTime;
	}

	@Override
	public String toString() {
		return "DayAndHourDataBean [regionId=" + regionId + ", regionName="
				+ regionName + ", creativeId=" + creativeId + ", creativeName="
				+ creativeName + ", appId=" + appId + ", realAppId="
				+ realAppId + ", appName=" + appName + ", adxId=" + adxId
				+ ", appType=" + appType + ", pkgName=" + pkgName
				+ ", campaignId=" + campaignId + ", bidAmount=" + bidAmount
				+ ", winAmount=" + winAmount + ", winRate=" + winRate
				+ ", impressionAmount=" + impressionAmount
				+ ", impressionRate=" + impressionRate + ", clickAmount="
				+ clickAmount + ", clickRate=" + clickRate + ", arrivalAmount="
				+ arrivalAmount + ", arrivalRate=" + arrivalRate
				+ ", uniqueAmount=" + uniqueAmount + ", jumpAmount="
				+ jumpAmount + ", jumpRate=" + jumpRate + ", residentTime="
				+ residentTime + "]";
	}

}
