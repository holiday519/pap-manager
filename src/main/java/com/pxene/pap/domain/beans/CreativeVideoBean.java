package com.pxene.pap.domain.beans;

public class CreativeVideoBean extends BasicDataBean {

	private String id;
	private String type;// 创意类型
	private String campaignId;// 活动ID
	private String name;// 名称
	private String status;// 创意审核状态
	private Float price;// 创意价格
	private String videoId;
	private String videoPath;
	private String imageId;
	private String imagePath;

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

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public String toString() {
		return "CreativeVideoBean [id=" + id + ", type=" + type
				+ ", campaignId=" + campaignId + ", name=" + name + ", status="
				+ status + ", price=" + price + ", videoId=" + videoId
				+ ", videoPath=" + videoPath + ", imageId=" + imageId
				+ ", imagePath=" + imagePath + "]";
	}

}
