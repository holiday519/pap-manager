package com.pxene.pap.domain.beans;

import java.util.Arrays;

/**
 * 列出所有素材
 */
public class MaterialListBean {

	private String id;
	private String name;
	private String type;
	private App[] apps;

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

	@Override
	public String toString() {
		return "MaterialListBean [id=" + id + ", name=" + name + ", type="
				+ type + ", apps=" + Arrays.toString(apps) + "]";
	}

}
