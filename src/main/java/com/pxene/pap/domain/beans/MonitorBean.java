package com.pxene.pap.domain.beans;

import java.util.List;

public class MonitorBean {

	private List<String> urls;

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	@Override
	public String toString() {
		return "MonitorBean [urls=" + urls + "]";
	}
	
}
