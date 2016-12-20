package com.pxene.pap.domain.beans;


public class MediaBean
{
    private String id;
    private String name;
    private String type;
    private String path;
    private String uploader;
    private Float volume;
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
    public String getUploader()
    {
        return uploader;
    }
    public void setUploader(String uploader)
    {
        this.uploader = uploader;
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
    public String toString()
    {
        return "MediaBean [id=" + id + ", name=" + name + ", type=" + type + ", path=" + path + ", uploader=" + uploader + ", volume=" + volume + ", size=" + size + "]";
    }
}
