package com.pxene.pap.domain.beans;

public class ImageEntity extends FileEntity
{
    private String videotmplid;
    private int timelength;
    private String imageid;
    
    
    public String getVideotmplid()
    {
        return videotmplid;
    }
    public void setVideotmplid(String videotmplid)
    {
        this.videotmplid = videotmplid;
    }
    public int getTimelength()
    {
        return timelength;
    }
    public void setTimelength(int timelength)
    {
        this.timelength = timelength;
    }
    public String getImageid()
    {
        return imageid;
    }
    public void setImageid(String imageid)
    {
        this.imageid = imageid;
    }
    
    
    @Override
    public String toString()
    {
        return "VideoEntity [videotmplid=" + videotmplid + ", timelength=" + timelength + ", imageid=" + imageid + "]";
    }
}
