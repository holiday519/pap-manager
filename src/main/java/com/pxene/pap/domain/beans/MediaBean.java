package com.pxene.pap.domain.beans;


public class MediaBean
{
	/**
	 * id
	 */
    private String id;
    /**
	 * 名称
	 */
    private String name;
    /**
	 * 类型
	 */
    private String type;
    /**
	 * 路径
	 */
    private String path;
    /**
	 * 大小
	 */
    private Float volume;
    /**
	 * 尺寸
	 */
    private String size;
    
    
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
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public String getPath()
    {
        return path;
    }
    public void setPath(String path)
    {
        this.path = path;
    }
	public Float getVolume() {
		return volume;
	}
	public void setVolume(Float volume) {
		this.volume = volume;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return "MediaBean [id=" + id + ", name=" + name + ", type=" + type
				+ ", path=" + path + ", volume=" + volume + ", size=" + size
				+ "]";
	}
}
