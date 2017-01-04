package com.pxene.pap.domain.beans;

import java.util.Arrays;
/**
 * app下所有模版
 */
public class TmplBean {

	private ImageTmpl[] imageTmpls;

	public static class ImageTmpl {
		private String id;
		private App[] apps;
		private Float maxVolume;
		private Integer width;
		private Integer height;
		private String sizeName;
		private String typeId;
		private String typeCode;
		private String typeName;

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

		public String getSizeName() {
			return sizeName;
		}

		public void setSizeName(String sizeName) {
			this.sizeName = sizeName;
		}

		public String getTypeId() {
			return typeId;
		}

		public void setTypeId(String typeId) {
			this.typeId = typeId;
		}

		public String getTypeCode() {
			return typeCode;
		}

		public void setTypeCode(String typeCode) {
			this.typeCode = typeCode;
		}

		public String getTypeName() {
			return typeName;
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}

		public App[] getApps() {
			return apps;
		}

		public void setApps(App[] apps) {
			this.apps = apps;
		}

		@Override
		public String toString() {
			return "ImageTmpl [id=" + id + ", apps=" + Arrays.toString(apps)
					+ ", maxVolume=" + maxVolume + ", width=" + width
					+ ", height=" + height + ", sizeName=" + sizeName
					+ ", typeId=" + typeId + ", typeCode=" + typeCode
					+ ", typeName=" + typeName + "]";
		}

	}

	private VideoTmpl[] videoTmpls;

	public static class VideoTmpl {
		private String id;
		private App[] apps;
		private Float maxVolume;
		private Integer maxTimelength;
		private Integer width;
		private Integer height;
		private String sizeName;
		private String typeId;
		private String typeCode;
		private String typeName;
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
		public String getSizeName() {
			return sizeName;
		}
		public void setSizeName(String sizeName) {
			this.sizeName = sizeName;
		}
		public String getTypeId() {
			return typeId;
		}
		public void setTypeId(String typeId) {
			this.typeId = typeId;
		}
		public String getTypeCode() {
			return typeCode;
		}
		public void setTypeCode(String typeCode) {
			this.typeCode = typeCode;
		}
		public String getTypeName() {
			return typeName;
		}
		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
		public ImageTmpl getImageTmpl() {
			return imageTmpl;
		}
		public void setImageTmpl(ImageTmpl imageTmpl) {
			this.imageTmpl = imageTmpl;
		}
		public App[] getApps() {
			return apps;
		}
		public void setApps(App[] apps) {
			this.apps = apps;
		}
		@Override
		public String toString() {
			return "VideoTmpl [id=" + id + ", apps=" + Arrays.toString(apps)
					+ ", maxVolume=" + maxVolume + ", maxTimelength="
					+ maxTimelength + ", width=" + width + ", height=" + height
					+ ", sizeName=" + sizeName + ", typeId=" + typeId
					+ ", typeCode=" + typeCode + ", typeName=" + typeName
					+ ", imageTmpl=" + imageTmpl + "]";
		}

	}

	private InfoTmpl[] infoTmpls;

	public static class InfoTmpl {
		private String id;
		private App[] apps;
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

		public App[] getApps() {
			return apps;
		}

		public void setApps(App[] apps) {
			this.apps = apps;
		}

		@Override
		public String toString() {
			return "InfoTmpl [id=" + id + ", apps=" + Arrays.toString(apps)
					+ ", maxTitle=" + maxTitle + ", maxDescription="
					+ maxDescription + ", maxCtaDescription="
					+ maxCtaDescription + ", icon=" + icon + ", image1="
					+ image1 + ", image2=" + image2 + ", image3=" + image3
					+ ", image4=" + image4 + ", image5=" + image5
					+ ", isAppStar=" + isAppStar + "]";
		}

	}

	public static class App{
		private String id;

		private String appId;

		private String adxId;

		private String appName;

		private String appType;

		private String pkgName;

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

		public String getAdxId() {
			return adxId;
		}

		public void setAdxId(String adxId) {
			this.adxId = adxId;
		}

		public String getAppName() {
			return appName;
		}

		public void setAppName(String appName) {
			this.appName = appName;
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

		@Override
		public String toString() {
			return "App [id=" + id + ", appId=" + appId + ", adxId=" + adxId
					+ ", appName=" + appName + ", appType=" + appType
					+ ", pkgName=" + pkgName + "]";
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

	@Override
	public String toString() {
		return "TmplBean [imageTmpls=" + Arrays.toString(imageTmpls)
				+ ", videoTmpls=" + Arrays.toString(videoTmpls)
				+ ", infoTmpls=" + Arrays.toString(infoTmpls) + "]";
	}

}
