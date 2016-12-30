package com.pxene.pap.domain.beans;

/**
 * 视频信息
 */
public class VideoBean extends MediaBean
{
	/**
	 * 宽
	 */
    private int width;
    /**
	 * 高
	 */
    private int height;
    /**
	 * 时长
	 */
    private int timelength;
    /**
	 * 图片Id
	 */
    private String imageId;
    
    
    public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
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
	public String toString() {
		return "VideoBean [width=" + width + ", height=" + height
				+ ", timelength=" + timelength + ", imageId=" + imageId + "]";
	}

}
