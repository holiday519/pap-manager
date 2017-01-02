package com.pxene.pap.domain.beans;

import java.util.Arrays;
/**
 * 添加创意bean
 */
public class CreativeAddBean {

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
	 * 备注
	 */
	private String remark;

	/**
	 * 图片创意信息
	 */
	private Image[] images;

	public static class Image {

		private String id;

		private Float price;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Float getPrice() {
			return price;
		}

		public void setPrice(Float price) {
			this.price = price;
		}

		@Override
		public String toString() {
			return "BaseImageBean [id=" + id + ", price=" + price + "]";
		}
	}

	/**
	 * 视频创意信息
	 */
	private Video[] videos;

	public static class Video {

		private String id;

		private String imageId;

		private Float price;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getImageId() {
			return imageId;
		}

		public void setImageId(String imageId) {
			this.imageId = imageId;
		}

		public Float getPrice() {
			return price;
		}

		public void setPrice(Float price) {
			this.price = price;
		}

		@Override
		public String toString() {
			return "BaseVideoBean [id=" + id + ", imageId=" + imageId
					+ ", price=" + price + "]";
		}
	}

	/**
	 * 信息流创意信息
	 */
	private Infoflow[] infoflows;

	public static class Infoflow {
		/**
		 * 信息流id
		 */
		private String id;
		/**
		 * 标题
		 */
		private String title;
		/**
		 * 描述
		 */
		private String description;
		/**
		 * 小图id
		 */
		private String iconId;
		/**
		 * 大图id
		 */
		private String image1Id;
		/**
		 * 大图id
		 */
		private String image2Id;
		/**
		 * 大图id
		 */
		private String image3Id;
		/**
		 * 大图id
		 */
		private String image4Id;
		/**
		 * 大图id
		 */
		private String image5Id;
		/**
		 * CTA描述
		 */
		private String ctaDescription;
		/**
		 * APP评分
		 */
		private String appStar;
		/**
		 * 出价
		 */
		private Float price;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getIconId() {
			return iconId;
		}

		public void setIconId(String iconId) {
			this.iconId = iconId;
		}

		public String getImage1Id() {
			return image1Id;
		}

		public void setImage1Id(String image1Id) {
			this.image1Id = image1Id;
		}

		public String getImage2Id() {
			return image2Id;
		}

		public void setImage2Id(String image2Id) {
			this.image2Id = image2Id;
		}

		public String getImage3Id() {
			return image3Id;
		}

		public void setImage3Id(String image3Id) {
			this.image3Id = image3Id;
		}

		public String getImage4Id() {
			return image4Id;
		}

		public void setImage4Id(String image4Id) {
			this.image4Id = image4Id;
		}

		public String getImage5Id() {
			return image5Id;
		}

		public void setImage5Id(String image5Id) {
			this.image5Id = image5Id;
		}

		public String getCtaDescription() {
			return ctaDescription;
		}

		public void setCtaDescription(String ctaDescription) {
			this.ctaDescription = ctaDescription;
		}

		public String getAppStar() {
			return appStar;
		}

		public void setAppStar(String appStar) {
			this.appStar = appStar;
		}

		public Float getPrice() {
			return price;
		}

		public void setPrice(Float price) {
			this.price = price;
		}

		@Override
		public String toString() {
			return "Infoflow [id=" + id + ", title=" + title
					+ ", description=" + description + ", iconId=" + iconId
					+ ", image1Id=" + image1Id + ", image2Id=" + image2Id
					+ ", image3Id=" + image3Id + ", image4Id=" + image4Id
					+ ", image5Id=" + image5Id + ", ctaDescription="
					+ ctaDescription + ", appStar=" + appStar + ", price="
					+ price + "]";
		}
	}

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Image[] getImages() {
		return images;
	}

	public void setImages(Image[] images) {
		this.images = images;
	}

	public Video[] getVideos() {
		return videos;
	}

	public void setVideos(Video[] videos) {
		this.videos = videos;
	}

	public Infoflow[] getInfoflows() {
		return infoflows;
	}

	public void setInfoflows(Infoflow[] infoflows) {
		this.infoflows = infoflows;
	}

	@Override
	public String toString() {
		return "CreativeAddBean [id=" + id + ", campaignId=" + campaignId
				+ ", name=" + name + ", remark=" + remark + ", images="
				+ Arrays.toString(images) + ", videos="
				+ Arrays.toString(videos) + ", infoflows="
				+ Arrays.toString(infoflows) + "]";
	}

}
