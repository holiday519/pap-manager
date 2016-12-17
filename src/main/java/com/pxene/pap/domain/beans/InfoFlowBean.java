package com.pxene.pap.domain.beans;
/**
 * 信息流创意
 */
public class InfoFlowBean {

	/**
	 * 信息流id
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;
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
	private String icon;
	/**
	 * 大图id
	 */
	private String image1;
	/**
	 * 大图id
	 */
	private String image2;
	/**
	 * 大图id
	 */
	private String image3;
	/**
	 * 大图id
	 */
	private String image4;
	/**
	 * 大图id
	 */
	private String image5;
	/**
	 * CTA描述
	 */
	private String ctaDescription;
	/**
	 * APP评分
	 */
	private String appStar;
	
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
	@Override
	public String toString() {
		return "InfoFlowBean [id=" + id + ", name=" + name + ", title="
				+ title + ", description=" + description + ", icon=" + icon
				+ ", image1=" + image1 + ", image2=" + image2 + ", image3="
				+ image3 + ", image4=" + image4 + ", image5=" + image5
				+ ", ctaDescription=" + ctaDescription + ", appStar=" + appStar
				+ "]";
	}
	
}
