package com.pxene.pap.domain.beans.baidu;

import java.util.List;

public class APIAdvertiserQualificationInfo
{
    /**
     * 广告主 id，
     * 必填，
     * 此id为dsp系统的广告主 id 
     */
    private Long advertiserId;
    
    /**
     * 广告主资质名称，
     * 必填
     */
    private String nickname;
    
    /**
     * 广告主网址，
     * 必填
     */
    private String website;
    
    /**
     * 资质审核状态，
     * 必填，
     * 0：审核通过；1：审核中；2：审核拒绝；3：缺省状态
     */
    private Integer auditState;
    
    /**
     * 主体资质，
     * 必填
     */
    private APIAdvertiserLicenceInfo mainLicence;
    /**
     * 可选资质，
     * 非必填 
     */
    private List<APIAdvertiserLicenceInfo> optionalLicences;
    
    
    public Long getAdvertiserId()
    {
        return advertiserId;
    }
    public void setAdvertiserId(Long advertiserId)
    {
        this.advertiserId = advertiserId;
    }
    public String getNickname()
    {
        return nickname;
    }
    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }
    public String getWebsite()
    {
        return website;
    }
    public void setWebsite(String website)
    {
        this.website = website;
    }
    public Integer getAuditState()
    {
        return auditState;
    }
    public void setAuditState(Integer auditState)
    {
        this.auditState = auditState;
    }
    public APIAdvertiserLicenceInfo getMainLicence()
    {
        return mainLicence;
    }
    public void setMainLicence(APIAdvertiserLicenceInfo mainLicence)
    {
        this.mainLicence = mainLicence;
    }
    public List<APIAdvertiserLicenceInfo> getOptionalLicences()
    {
        return optionalLicences;
    }
    public void setOptionalLicences(List<APIAdvertiserLicenceInfo> optionalLicences)
    {
        this.optionalLicences = optionalLicences;
    }
    
    
    public APIAdvertiserQualificationInfo()
    {
        super();
    }
    public APIAdvertiserQualificationInfo(Long advertiserId, String nickname, String website, Integer auditState, APIAdvertiserLicenceInfo mainLicence, List<APIAdvertiserLicenceInfo> optionalLicences)
    {
        super();
        this.advertiserId = advertiserId;
        this.nickname = nickname;
        this.website = website;
        this.auditState = auditState;
        this.mainLicence = mainLicence;
        this.optionalLicences = optionalLicences;
    }
    
    
    @Override
    public String toString()
    {
        return "APIAdvertiserQualificationInfo [advertiserId=" + advertiserId + ", nickname=" + nickname + ", website=" + website + ", auditState=" + auditState + ", mainLicence=" + mainLicence
                + ", optionalLicences=" + optionalLicences + "]";
    }
}
