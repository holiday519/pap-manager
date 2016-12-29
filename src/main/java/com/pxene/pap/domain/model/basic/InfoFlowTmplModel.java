package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class InfoFlowTmplModel {
    private String id;

    private Integer maxTitle;

    private Integer maxDescription;

    private Integer maxCtaDescription;

    private String iconId;

    private String image1Id;

    private String image2Id;

    private String image3Id;

    private String image4Id;

    private String image5Id;

    private String isAppStar;

    private String remark;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId == null ? null : iconId.trim();
    }

    public String getImage1Id() {
        return image1Id;
    }

    public void setImage1Id(String image1Id) {
        this.image1Id = image1Id == null ? null : image1Id.trim();
    }

    public String getImage2Id() {
        return image2Id;
    }

    public void setImage2Id(String image2Id) {
        this.image2Id = image2Id == null ? null : image2Id.trim();
    }

    public String getImage3Id() {
        return image3Id;
    }

    public void setImage3Id(String image3Id) {
        this.image3Id = image3Id == null ? null : image3Id.trim();
    }

    public String getImage4Id() {
        return image4Id;
    }

    public void setImage4Id(String image4Id) {
        this.image4Id = image4Id == null ? null : image4Id.trim();
    }

    public String getImage5Id() {
        return image5Id;
    }

    public void setImage5Id(String image5Id) {
        this.image5Id = image5Id == null ? null : image5Id.trim();
    }

    public String getIsAppStar() {
        return isAppStar;
    }

    public void setIsAppStar(String isAppStar) {
        this.isAppStar = isAppStar == null ? null : isAppStar.trim();
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
        sb.append(", maxTitle=").append(maxTitle);
        sb.append(", maxDescription=").append(maxDescription);
        sb.append(", maxCtaDescription=").append(maxCtaDescription);
        sb.append(", iconId=").append(iconId);
        sb.append(", image1Id=").append(image1Id);
        sb.append(", image2Id=").append(image2Id);
        sb.append(", image3Id=").append(image3Id);
        sb.append(", image4Id=").append(image4Id);
        sb.append(", image5Id=").append(image5Id);
        sb.append(", isAppStar=").append(isAppStar);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}