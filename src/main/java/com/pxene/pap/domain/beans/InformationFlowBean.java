package com.pxene.pap.domain.beans;

public class InformationFlowBean {

	private String id;
	private String name;
	private String title;
	private String description;
	private String icon;
	private String image1;
	private String image2;
	private String image3;
	private String image4;
	private String image5;
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	public String getImage3() {
		return image3;
	}
	public void setImage3(String image3) {
		this.image3 = image3;
	}
	public String getImage4() {
		return image4;
	}
	public void setImage4(String image4) {
		this.image4 = image4;
	}
	public String getImage5() {
		return image5;
	}
	public void setImage5(String image5) {
		this.image5 = image5;
	}
	@Override
	public String toString() {
		return "InformationFlowBean [id=" + id + ", name=" + name + ", title="
				+ title + ", description=" + description + ", icon=" + icon
				+ ", image1=" + image1 + ", image2=" + image2 + ", image3="
				+ image3 + ", image4=" + image4 + ", image5=" + image5 + "]";
	}
	
}
