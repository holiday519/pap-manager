package com.pxene.pap.domain.beans;

public class ImageBean extends MediaBean
{
    private int width;
    private int height;
    
    
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
    
    
    public ImageBean()
    {
        super();
    }
    public ImageBean(int width, int height)
    {
        super();
        this.width = width;
        this.height = height;
    }
    
    
    @Override
    public String toString()
    {
        return "ImageBean [width=" + width + ", height=" + height + "]";
    }
}
