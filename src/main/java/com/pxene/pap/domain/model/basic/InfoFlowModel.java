package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class InfoFlowModel {
    private String id;

    private String name;

    private String title;

    private String description;

    private String ctaDescription;

    private String icon;

    private String image1;

    private String image2;

    private String image3;

    private String image4;

    private String image5;

    private Integer appStar;

    private String remark;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getCtaDescription() {
        return ctaDescription;
    }

    public void setCtaDescription(String ctaDescription) {
        this.ctaDescription = ctaDescription == null ? null : ctaDescription.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1 == null ? null : image1.trim();
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2 == null ? null : image2.trim();
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3 == null ? null : image3.trim();
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4 == null ? null : image4.trim();
    }

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5 == null ? null : image5.trim();
    }

    public Integer getAppStar() {
        return appStar;
    }

    public void setAppStar(Integer appStar) {
        this.appStar = appStar;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", title=").append(title);
        sb.append(", description=").append(description);
        sb.append(", ctaDescription=").append(ctaDescription);
        sb.append(", icon=").append(icon);
        sb.append(", image1=").append(image1);
        sb.append(", image2=").append(image2);
        sb.append(", image3=").append(image3);
        sb.append(", image4=").append(image4);
        sb.append(", image5=").append(image5);
        sb.append(", appStar=").append(appStar);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}