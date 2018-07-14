package com.pxene.pap.domain.beans.baidu;

/**
 * 上传广告主资质响应。
 * @author ningyu
 */
public class APIAdvertiserQualificationStatus extends BaseBean
{
    /**
     * 广告主 id，
     * 必填，
     * 此id为dsp系统的广告主id。
     */
    private Long advertiserId;
    
    /**
     * 资质上传状态，
     * 必填，
     * 0：提交失败；1：已提交；2：免提交。
     */
    private int status;
    
    /**
     * 提交失败 message，
     * 选填，
     * 提交失败信息。
     */
    private String message;
    
    
    public Long getAdvertiserId()
    {
        return advertiserId;
    }
    public void setAdvertiserId(Long advertiserId)
    {
        this.advertiserId = advertiserId;
    }
    public int getStatus()
    {
        return status;
    }
    public void setStatus(int status)
    {
        this.status = status;
    }
    public String getMessage()
    {
        return message;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    
    @Override
    public String toString()
    {
        return "APIAdvertiserQualificationStatus [advertiserId=" + advertiserId + ", status=" + status + ", message=" + message + "]";
    }
}
