package com.pxene.pap.domain.beans;

public class SizeBean {
	
    private String id;

    private String name;

    private Integer width;

    private Integer height;

    private String remark;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "SizeBean [id=" + id + ", name=" + name + ", width=" + width
				+ ", height=" + height + ", remark=" + remark + "]";
	}
    
}
