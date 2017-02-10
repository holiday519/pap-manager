package com.pxene.pap.domain.beans;

import java.util.Arrays;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.pxene.pap.constant.PhrasesConstant;

/**
 * 添加创意bean
 */
public class CreativeAddBean {

	/**
	 * 创意id
	 */
	@Length(max = 36, message = PhrasesConstant.LENGTH_ERROR_ID)
	private String id;
	/**
	 * 活动id
	 */
	@Length(max = 36, message = PhrasesConstant.CREATIVE_LENGTH_ERROR_CAMPAIGNID)
	@NotNull(message = PhrasesConstant.CREATIVE_NOTNULL_CAMPAIGNID)
	private String campaignId;
	/**
	 * 备注
	 */
	@Length(max = 400, message = PhrasesConstant.LENGTH_ERROR_REMARK)
	private String remark;

	/**
	 * 图片创意信息
	 */
	private Image[] images;

	public static class Image {
		@Length(max = 36, message = PhrasesConstant.CREATIVE_LENGTH_ERROR_IMAGE_ID)
		@NotNull(message = PhrasesConstant.CREATIVE_NOTNULL_IMAGE_ID)
		private String id;
		@Length(max = 36, message = PhrasesConstant.CREATIVE_LENGTH_ERROR_IMAGE_TMPLID)
		@NotNull(message = PhrasesConstant.CREATIVE_NOTNULL_IMAGE_TMPLID)
		private String tmplId;
		@NotNull(message = PhrasesConstant.CREATIVE_NOTNULL_IMAGE_PRICE)
		private Float price;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTmplId() {
			return tmplId;
		}

		public void setTmplId(String tmplId) {
			this.tmplId = tmplId;
		}

		public Float getPrice() {
			return price;
		}

		public void setPrice(Float price) {
			this.price = price;
		}

		@Override
		public String toString() {
			return "Image [id=" + id + ", tmplId=" + tmplId + ", price="
					+ price + "]";
		}

	}

	/**
	 * 视频创意信息
	 */
	private Video[] videos;

	public static class Video {
		@Length(max = 36, message = PhrasesConstant.CREATIVE_LENGTH_ERROR_VIDEO_ID)
		@NotNull(message = PhrasesConstant.CREATIVE_NOTNULL_VIDEO_ID)
		private String id;
		@Length(max = 36, message = PhrasesConstant.CREATIVE_LENGTH_ERROR_VIDEO_IMAGEID)
		private String imageId;
		@Length(max = 36, message = PhrasesConstant.CREATIVE_LENGTH_ERROR_VIDEO_TMPLID)
		@NotNull(message = PhrasesConstant.CREATIVE_NOTNULL_VIDEO_TMPLID)
		private String tmplId;
		@NotNull(message = PhrasesConstant.CREATIVE_NOTNULL_VIDEO_PRICE)
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

		public String getTmplId() {
			return tmplId;
		}

		public void setTmplId(String tmplId) {
			this.tmplId = tmplId;
		}

		public Float getPrice() {
			return price;
		}

		public void setPrice(Float price) {
			this.price = price;
		}

		@Override
		public String toString() {
			return "Video [id=" + id + ", imageId=" + imageId + ", tmplId="
					+ tmplId + ", price=" + price + "]";
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
		@Length(max = 36, message = PhrasesConstant.CREATIVE_LENGTH_ERROR_INFO_ID)
		@NotNull(message = PhrasesConstant.CREATIVE_NOTNULL_INFO_ID)
		private String id;
		/**
		 * 价格
		 */
		@NotNull(message = PhrasesConstant.CREATIVE_NOTNULL_INFO_PRICE)
		private Float price;
		/**
		 * 标题
		 */
		@Length(max = 36, message = PhrasesConstant.CREATIVE_LENGTH_ERROR_INFO_TITLE)
		@NotNull(message = PhrasesConstant.CREATIVE_NOTNULL_INFO_TITLE)
		private String title;
		/**
		 * 描述
		 */
		@Length(max = 36, message = PhrasesConstant.CREATIVE_LENGTH_ERROR_INFO_DESCRIPTION)
		private String description;
		/**
		 * 小图id
		 */
		@Length(max = 36, message = PhrasesConstant.CREATIVE_LENGTH_ERROR_INFO_ICONID)
		private String iconId;
		/**
		 * 大图id
		 */
		@Length(max = 36, message = PhrasesConstant.CREATIVE_LENGTH_ERROR_INFO_IMAGEID1)
		private String image1Id;
		/**
		 * 大图id
		 */
		@Length(max = 36, message = PhrasesConstant.CREATIVE_LENGTH_ERROR_INFO_IMAGEID2)
		private String image2Id;
		/**
		 * 大图id
		 */
		@Length(max = 36, message = PhrasesConstant.CREATIVE_LENGTH_ERROR_INFO_IMAGEID3)
		private String image3Id;
		/**
		 * 大图id
		 */
		@Length(max = 36, message = PhrasesConstant.CREATIVE_LENGTH_ERROR_INFO_IMAGEID4)
		private String image4Id;
		/**
		 * 大图id
		 */
		@Length(max = 36, message = PhrasesConstant.CREATIVE_LENGTH_ERROR_INFO_IMAGEID5)
		private String image5Id;
		/**
		 * CTA描述
		 */
		@Length(max = 36, message = PhrasesConstant.CREATIVE_LENGTH_ERROR_INFO_CTADESCRIPTION)
		private String ctaDescription;
		/**
		 * APP评分
		 */
		private String appStar;
		/**
		 * 模版ID
		 */
		@Length(max = 36, message = PhrasesConstant.CREATIVE_LENGTH_ERROR_INFO_TMPLID)
		@NotNull(message = PhrasesConstant.CREATIVE_NOTNULL_INFO_TMPLID)
		private String tmplId;

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

		public String getTmplId() {
			return tmplId;
		}

		public void setTmplId(String tmplId) {
			this.tmplId = tmplId;
		}

		public Float getPrice() {
			return price;
		}

		public void setPrice(Float price) {
			this.price = price;
		}

		@Override
		public String toString() {
			return "Infoflow [id=" + id + ", price=" + price + ", title="
					+ title + ", description=" + description + ", iconId="
					+ iconId + ", image1Id=" + image1Id + ", image2Id="
					+ image2Id + ", image3Id=" + image3Id + ", image4Id="
					+ image4Id + ", image5Id=" + image5Id + ", ctaDescription="
					+ ctaDescription + ", appStar=" + appStar + ", tmplId="
					+ tmplId + "]";
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
				+ ", remark=" + remark + ", images=" + Arrays.toString(images)
				+ ", videos=" + Arrays.toString(videos) + ", infoflows="
				+ Arrays.toString(infoflows) + "]";
	}

}
