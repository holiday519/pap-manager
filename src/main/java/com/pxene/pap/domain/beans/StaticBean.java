package com.pxene.pap.domain.beans;

import com.pxene.pap.constant.PhrasesConstant;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 静态值
 * Created by wangshuai on 2017/6/8.
 */
public class StaticBean {

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
    private double value;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 创建时间
     */
    private Date createTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ProjectBean [id=" + id + ", name=" + name + ",value="+ value+",projectId="+projectId+",creativeTime="+createTime+"]";
    }
}
