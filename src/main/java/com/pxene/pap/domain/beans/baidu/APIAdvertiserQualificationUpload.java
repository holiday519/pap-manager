package com.pxene.pap.domain.beans.baidu;

import java.util.List;

/**
 * 上传广告主资质。
 * @author ningyu
 */
public class APIAdvertiserQualificationUpload extends BaseBean
{
    /**
     * 广告主id，
     * 必填，
     * 此id为 dsp 系统的广告主 id。
     */
    private Long advertiserId;
    
    /**
     * 主体资质，
     * 必填，
     * 必须提供一条主体资质。
     */
    private APIAdvertiserLicence mainLicence;
    
    /**
     * 可选资质列表，
     * 必填，
     * 可选资质最多不超过 10 个。
     */
    private List<APIAdvertiserLicence> optionalLicences;
    
    
    public Long getAdvertiserId()
    {
        return advertiserId;
    }
    public void setAdvertiserId(Long advertiserId)
    {
        this.advertiserId = advertiserId;
    }
    public APIAdvertiserLicence getMainLicence()
    {
        return mainLicence;
    }
    public void setMainLicence(APIAdvertiserLicence mainLicence)
    {
        this.mainLicence = mainLicence;
    }
    public List<APIAdvertiserLicence> getOptionalLicences()
    {
        return optionalLicences;
    }
    public void setOptionalLicences(List<APIAdvertiserLicence> optionalLicences)
    {
        this.optionalLicences = optionalLicences;
    }
    
    
    public APIAdvertiserQualificationUpload()
    {
        super();
    }
    public APIAdvertiserQualificationUpload(Long advertiserId, APIAdvertiserLicence mainLicence, List<APIAdvertiserLicence> optionalLicences)
    {
        super();
        this.advertiserId = advertiserId;
        this.mainLicence = mainLicence;
        this.optionalLicences = optionalLicences;
    }
    
    
    @Override
    public String toString()
    {
        return "APIAdvertiserQualificationUpload [advertiserId=" + advertiserId + ", mainLicence=" + mainLicence + ", optionalLicences=" + optionalLicences + "]";
    }
}
