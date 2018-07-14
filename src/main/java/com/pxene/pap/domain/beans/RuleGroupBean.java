package com.pxene.pap.domain.beans;

import java.util.Date;
import java.util.List;

import com.pxene.pap.domain.models.RuleModel;

public class RuleGroupBean
{
    private String id;

    private String name;

    private String projectId;

    private List<RuleModel> rules;
    
    private Date createTime;
    
    private Date updateTime;
    
    
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getProjectId()
    {
        return projectId;
    }
    public void setProjectId(String projectId)
    {
        this.projectId = projectId;
    }
    public List<RuleModel> getRules()
    {
        return rules;
    }
    public void setRules(List<RuleModel> rules)
    {
        this.rules = rules;
    }
    public Date getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    public Date getUpdateTime()
    {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    
    @Override
    public String toString()
    {
        return "RuleGroupBean [id=" + id + ", name=" + name + ", projectId=" + projectId + ", rules=" + rules + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
    }
}
