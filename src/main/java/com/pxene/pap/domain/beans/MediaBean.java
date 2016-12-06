package com.pxene.pap.domain.beans;

import java.util.UUID;

public class MediaBean
{
    private String uuid = UUID.randomUUID().toString();
    private String name;
    private String type;
    private String path;
    private String uploader;
    private long volume;
    
    
    public String getUuid()
    {
        return uuid;
    }
    public void setUuid(String uuid)
    {
        this.uuid = uuid;
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
    public long getVolume()
    {
        return volume;
    }
    public void setVolume(long volume)
    {
        this.volume = volume;
    }
    
    @Override
    public String toString()
    {
        return "MediaBean [uuid=" + uuid + ", name=" + name + ", type=" + type + ", path=" + path + ", uploader=" + uploader + ", volume=" + volume + "]";
    }
}
