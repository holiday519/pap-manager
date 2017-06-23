package com.pxene.pap.domain.beans;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.pxene.pap.constant.PhrasesConstant;

/**
 * 静态值
 * Created by wangshuai on 2017/6/8.
 */
public class StaticvalBean {
	/**
     * 静态值id
     */
    private String id;

    /**
     * 静态值名称
     */
    @NotNull(message = PhrasesConstant.NAME_NOT_NULL)
    @Length(max = 100, message = PhrasesConstant.LENGTH_ERROR_NAME)
    private String name;

    /**
     * 静态值
     */
    @NotNull(message = PhrasesConstant.STATICS_NAME_IS_NULL)
    private double value;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 更新日期
     */
    private Date updateDate;

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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "StaticvalBean [id=" + id + ", name=" + name + ",value="+ value+",projectId="+projectId+",creativeTime="+createDate+"]";
    }
}
