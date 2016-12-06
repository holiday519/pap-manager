package com.pxene.pap.domain.beans;

public class VideoBean extends MediaBean
{
    private int width;
    private int height;
    private int timelength;
    
    
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
    public int getTimelength()
    {
        return timelength;
    }
    public void setTimelength(int timelength)
    {
        this.timelength = timelength;
    }
    
    
    public VideoBean()
    {
        super();
    }
    public VideoBean(int width, int height, int timelength)
    {
        super();
        this.width = width;
        this.height = height;
        this.timelength = timelength;
    }
    
    
    @Override
    public String toString()
    {
        return "VideoBean [width=" + width + ", height=" + height + ", timelength=" + timelength + "]";
    }
}
