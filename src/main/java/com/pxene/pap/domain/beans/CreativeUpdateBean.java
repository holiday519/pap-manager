package com.pxene.pap.domain.beans;

import java.util.Arrays;

/**
 * 创意修改bean
 */
public class CreativeUpdateBean {

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
	 * 图片信息
	 */
	private Image images;

	public static class Image {
		private Add[] add;
		private String[] delete;

		public static class Add {
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
				return "add [id=" + id + ", price=" + price + "]";
			}
		}

		public Add[] getAdd() {
			return add;
		}

		public void setAdd(Add[] add) {
			this.add = add;
		}

		public String[] getDelete() {
			return delete;
		}

		public void setDelete(String[] delete) {
			this.delete = delete;
		}

		@Override
		public String toString() {
			return "Image [add=" + Arrays.toString(add) + ", delete="
					+ Arrays.toString(delete) + "]";
		}
	}

	/**
	 * 视频信息
	 */
	private Video videos;

	public static class Video {
		private String[] delete;
		private Add[] add;

		public static class Add {
			private String id;
			private Float price;
			private String imageId;

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

			public String getImageId() {
				return imageId;
			}

			public void setImageId(String imageId) {
				this.imageId = imageId;
			}

			@Override
			public String toString() {
				return "Add [id=" + id + ", price=" + price + ", imageId="
						+ imageId + "]";
			}
		}

		public String[] getDelete() {
			return delete;
		}

		public void setDelete(String[] delete) {
			this.delete = delete;
		}

		public Add[] getAdd() {
			return add;
		}

		public void setAdd(Add[] add) {
			this.add = add;
		}

		@Override
		public String toString() {
			return "Video [delete=" + Arrays.toString(delete) + ", add="
					+ Arrays.toString(add) + "]";
		}
	}

	/**
	 * 信息信息
	 */
	private InfoFlow infoFlows;

	public static class InfoFlow {

		private Add[] add;
		private String[] delete;

		public static class Add {
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
				return "Add [id=" + id + ", title=" + title + ", description="
						+ description + ", iconId=" + iconId + ", image1Id="
						+ image1Id + ", image2Id=" + image2Id + ", image3Id="
						+ image3Id + ", image4Id=" + image4Id + ", image5Id="
						+ image5Id + ", ctaDescription=" + ctaDescription
						+ ", appStar=" + appStar + ", price=" + price + "]";
			}
		}

		public Add[] getAdd() {
			return add;
		}

		public void setAdd(Add[] add) {
			this.add = add;
		}

		public String[] getDelete() {
			return delete;
		}

		public void setDelete(String[] delete) {
			this.delete = delete;
		}

		@Override
		public String toString() {
			return "InfoFlow [add=" + Arrays.toString(add) + ", delete="
					+ Arrays.toString(delete) + "]";
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

	public Image getImages() {
		return images;
	}

	public void setImages(Image images) {
		this.images = images;
	}

	public Video getVideos() {
		return videos;
	}

	public void setVideos(Video videos) {
		this.videos = videos;
	}

	public InfoFlow getInfoFlows() {
		return infoFlows;
	}

	public void setInfoFlows(InfoFlow infoFlows) {
		this.infoFlows = infoFlows;
	}

	@Override
	public String toString() {
		return "CreativeUpdateBean [id=" + id + ", campaignId=" + campaignId
				+ ", name=" + name + ", remark=" + remark + ", images="
				+ images + ", videos=" + videos + ", infoFlows=" + infoFlows
				+ "]";
	}

}
