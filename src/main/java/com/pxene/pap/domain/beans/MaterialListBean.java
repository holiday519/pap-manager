package com.pxene.pap.domain.beans;


/**
 * 列出所有素材
 */
public class MaterialListBean {

	private String id;
	private String mapId;
	private String name;
	private String type;
	private App[] apps;
	
	private String path;
	private String iconPath;
	private String image1Path;
	private String image2Path;
	private String image3Path;
	private String image4Path;
	private String image5Path;

	public static class App {
		private String id;
		private String appId;
		private String appname;
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

		public String getAppname() {
			return appname;
		}

		public void setAppname(String appname) {
			this.appname = appname;
		}

		public String getPkgName() {
			return pkgName;
		}

		public void setPkgName(String pkgName) {
			this.pkgName = pkgName;
		}

		@Override
		public String toString() {
			return "App [id=" + id + ", appId=" + appId + ", appname="
					+ appname + ", pkgName=" + pkgName + "]";
		}

	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public App[] getApps() {
		return apps;
	}

	public void setApps(App[] apps) {
		this.apps = apps;
	}

	public String getMapId() {
		return mapId;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getImage1Path() {
		return image1Path;
	}

	public void setImage1Path(String image1Path) {
		this.image1Path = image1Path;
	}

	public String getImage2Path() {
		return image2Path;
	}

	public void setImage2Path(String image2Path) {
		this.image2Path = image2Path;
	}

	public String getImage3Path() {
		return image3Path;
	}

	public void setImage3Path(String image3Path) {
		this.image3Path = image3Path;
	}

	public String getImage4Path() {
		return image4Path;
	}

	public void setImage4Path(String image4Path) {
		this.image4Path = image4Path;
	}

	public String getImage5Path() {
		return image5Path;
	}

	public void setImage5Path(String image5Path) {
		this.image5Path = image5Path;
	}

}
