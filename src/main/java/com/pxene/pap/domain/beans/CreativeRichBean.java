package com.pxene.pap.domain.beans;

import java.util.List;

import com.pxene.pap.domain.models.AdvertiserModel;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CreativeAuditModel;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.LandpageModel;
import com.pxene.pap.domain.models.ProjectModel;

public class CreativeRichBean
{
    private String id;
    private LandpageModel landpageInfo;
    private CreativeModel creativeInfo; 
    private CampaignModel campaignInfo;
    private ProjectModel projectInfo;
    private AdvertiserModel advertiserInfo;
    private List<CreativeAuditModel> creativeAuditInfoList;
    
    
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public LandpageModel getLandpageInfo()
    {
        return landpageInfo;
    }
    public void setLandpageInfo(LandpageModel landpageInfo)
    {
        this.landpageInfo = landpageInfo;
    }
    public CreativeModel getCreativeInfo()
    {
        return creativeInfo;
    }
    public void setCreativeInfo(CreativeModel creativeInfo)
    {
        this.creativeInfo = creativeInfo;
    }
    public CampaignModel getCampaignInfo()
    {
        return campaignInfo;
    }
    public void setCampaignInfo(CampaignModel campaignInfo)
    {
        this.campaignInfo = campaignInfo;
    }
    public ProjectModel getProjectInfo()
    {
        return projectInfo;
    }
    public void setProjectInfo(ProjectModel projectInfo)
    {
        this.projectInfo = projectInfo;
    }
    public AdvertiserModel getAdvertiserInfo()
    {
        return advertiserInfo;
    }
    public void setAdvertiserInfo(AdvertiserModel advertiserInfo)
    {
        this.advertiserInfo = advertiserInfo;
    }
    public List<CreativeAuditModel> getCreativeAuditInfoList()
    {
        return creativeAuditInfoList;
    }
    public void setCreativeAuditInfoList(List<CreativeAuditModel> creativeAuditInfoList)
    {
        this.creativeAuditInfoList = creativeAuditInfoList;
    }
    
    
    public CreativeRichBean()
    {
        super();
    }
    public CreativeRichBean(String id, LandpageModel landpageInfo, CreativeModel creativeInfo, CampaignModel campaignInfo, ProjectModel projectInfo, AdvertiserModel advertiserInfo)
    {
        super();
        this.id = id;
        this.landpageInfo = landpageInfo;
        this.creativeInfo = creativeInfo;
        this.campaignInfo = campaignInfo;
        this.projectInfo = projectInfo;
        this.advertiserInfo = advertiserInfo;
    }
    public CreativeRichBean(String id, LandpageModel landpageInfo, CreativeModel creativeInfo, CampaignModel campaignInfo, ProjectModel projectInfo, AdvertiserModel advertiserInfo,
            List<CreativeAuditModel> creativeAuditInfoList)
    {
        super();
        this.id = id;
        this.landpageInfo = landpageInfo;
        this.creativeInfo = creativeInfo;
        this.campaignInfo = campaignInfo;
        this.projectInfo = projectInfo;
        this.advertiserInfo = advertiserInfo;
        this.creativeAuditInfoList = creativeAuditInfoList;
    }
    
    
    @Override
    public String toString()
    {
        return "CreativeRichBean [id=" + id + ", landpageInfo=" + landpageInfo + ", creativeInfo=" + creativeInfo + ", campaignInfo=" + campaignInfo + ", projectInfo=" + projectInfo
                + ", advertiserInfo=" + advertiserInfo + ", creativeAuditInfoList=" + creativeAuditInfoList + "]";
    }
}
