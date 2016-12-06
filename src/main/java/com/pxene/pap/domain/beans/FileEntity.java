package com.pxene.pap.domain.beans;

import java.util.Date;

public class FileEntity
{
    private String id;
    private String name;
    private String path;
    private String typeid;
    private String sizeid;
    private int width;
    private int height;
    private float volume;
    private String remark;
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
    public String getPath()
    {
        return path;
    }
    public void setPath(String path)
    {
        this.path = path;
    }
    public String getTypeid()
    {
        return typeid;
    }
    public void setTypeid(String typeid)
    {
        this.typeid = typeid;
    }
    public String getSizeid()
    {
        return sizeid;
    }
    public void setSizeid(String sizeid)
    {
        this.sizeid = sizeid;
    }
    public int getWidth()
    {
        return width;
    }
    public void setWidth(int width)
    {
        this.width = width;
    }
    public int getHeight()
    {
        return height;
    }
    public void setHeight(int height)
    {
        this.height = height;
    }
    public float getVolume()
    {
        return volume;
    }
    public void setVolume(float volume)
    {
        this.volume = volume;
    }
    public String getRemark()
    {
        return remark;
    }
    public void setRemark(String remark)
    {
        this.remark = remark;
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
        return "FileEntity [id=" + id + ", name=" + name + ", path=" + path + ", typeid=" + typeid + ", sizeid=" + sizeid + ", width=" + width + ", height=" + height + ", volume=" + volume
                + ", remark=" + remark + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
    }
}
