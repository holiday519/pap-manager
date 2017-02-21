package com.pxene.pap.domain.beans;


/**
 * app下所有模版
 */
public class TmplBean {

	private ImageTmpl[] imageTmpls;

	public static class ImageTmpl {
		private String id;
		private Float maxVolume;
		private Integer width;
		private Integer height;
		private String formats;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Float getMaxVolume() {
			return maxVolume;
		}

		public void setMaxVolume(Float maxVolume) {
			this.maxVolume = maxVolume;
		}

		public Integer getWidth() {
			return width;
		}

		public void setWidth(Integer width) {
			this.width = width;
		}

		public Integer getHeight() {
			return height;
		}

		public void setHeight(Integer height) {
			this.height = height;
		}

		public String getFormats() {
			return formats;
		}

		public void setFormats(String formats) {
			this.formats = formats;
		}

		@Override
		public String toString() {
			return "ImageTmpl [id=" + id + ", maxVolume=" + maxVolume
					+ ", width=" + width + ", height=" + height + ", formats="
					+ formats + "]";
		}

	}

	private VideoTmpl[] videoTmpls;

	public static class VideoTmpl {
		private String id;
		private Float maxVolume;
		private Integer maxTimelength;
		private Integer width;
		private Integer height;
		private ImageTmpl imageTmpl;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Float getMaxVolume() {
			return maxVolume;
		}

		public void setMaxVolume(Float maxVolume) {
			this.maxVolume = maxVolume;
		}

		public Integer getMaxTimelength() {
			return maxTimelength;
		}

		public void setMaxTimelength(Integer maxTimelength) {
			this.maxTimelength = maxTimelength;
		}

		public Integer getWidth() {
			return width;
		}

		public void setWidth(Integer width) {
			this.width = width;
		}

		public Integer getHeight() {
			return height;
		}

		public void setHeight(Integer height) {
			this.height = height;
		}

		public ImageTmpl getImageTmpl() {
			return imageTmpl;
		}

		public void setImageTmpl(ImageTmpl imageTmpl) {
			this.imageTmpl = imageTmpl;
		}

		@Override
		public String toString() {
			return "VideoTmpl [id=" + id + ", maxVolume=" + maxVolume
					+ ", maxTimelength=" + maxTimelength + ", width=" + width
					+ ", height=" + height + ", imageTmpl=" + imageTmpl + "]";
		}

	}

	private InfoTmpl[] infoTmpls;

	public static class InfoTmpl {
		private String id;
		private Integer maxTitle;
		private Integer maxDescription;
		private Integer maxCtaDescription;
		private ImageTmpl icon;
		private ImageTmpl image1;
		private ImageTmpl image2;
		private ImageTmpl image3;
		private ImageTmpl image4;
		private ImageTmpl image5;
		private String isAppStar;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Integer getMaxTitle() {
			return maxTitle;
		}

		public void setMaxTitle(Integer maxTitle) {
			this.maxTitle = maxTitle;
		}

		public Integer getMaxDescription() {
			return maxDescription;
		}

		public void setMaxDescription(Integer maxDescription) {
			this.maxDescription = maxDescription;
		}

		public Integer getMaxCtaDescription() {
			return maxCtaDescription;
		}

		public void setMaxCtaDescription(Integer maxCtaDescription) {
			this.maxCtaDescription = maxCtaDescription;
		}

		public ImageTmpl getIcon() {
			return icon;
		}

		public void setIcon(ImageTmpl icon) {
			this.icon = icon;
		}

		public ImageTmpl getImage1() {
			return image1;
		}

		public void setImage1(ImageTmpl image1) {
			this.image1 = image1;
		}

		public ImageTmpl getImage2() {
			return image2;
		}

		public void setImage2(ImageTmpl image2) {
			this.image2 = image2;
		}

		public ImageTmpl getImage3() {
			return image3;
		}

		public void setImage3(ImageTmpl image3) {
			this.image3 = image3;
		}

		public ImageTmpl getImage4() {
			return image4;
		}

		public void setImage4(ImageTmpl image4) {
			this.image4 = image4;
		}

		public ImageTmpl getImage5() {
			return image5;
		}

		public void setImage5(ImageTmpl image5) {
			this.image5 = image5;
		}

		public String getIsAppStar() {
			return isAppStar;
		}

		public void setIsAppStar(String isAppStar) {
			this.isAppStar = isAppStar;
		}

		@Override
		public String toString() {
			return "InfoTmpl [id=" + id + ", maxTitle=" + maxTitle
					+ ", maxDescription=" + maxDescription
					+ ", maxCtaDescription=" + maxCtaDescription + ", icon="
					+ icon + ", image1=" + image1 + ", image2=" + image2
					+ ", image3=" + image3 + ", image4=" + image4 + ", image5="
					+ image5 + ", isAppStar=" + isAppStar + "]";
		}

	}

	public ImageTmpl[] getImageTmpls() {
		return imageTmpls;
	}

	public void setImageTmpls(ImageTmpl[] imageTmpls) {
		this.imageTmpls = imageTmpls;
	}

	public VideoTmpl[] getVideoTmpls() {
		return videoTmpls;
	}

	public void setVideoTmpls(VideoTmpl[] videoTmpls) {
		this.videoTmpls = videoTmpls;
	}

	public InfoTmpl[] getInfoTmpls() {
		return infoTmpls;
	}

	public void setInfoTmpls(InfoTmpl[] infoTmpls) {
		this.infoTmpls = infoTmpls;
	}

}
