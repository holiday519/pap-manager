package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.beans.BasicDataBean;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.CreativeModelExample;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.domain.models.ProjectModelExample;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.ProjectDao;

@Service
public class DataService
{
    @Autowired
    private CreativeDao creativeDao;
    
    @Autowired
    private CampaignDao campaignDao;
    
    @Autowired
    private ProjectDao projectDao;
    
    
    public List<BasicDataBean> get(String type, List<String> creativeIds)
    {
        return null;
    }

    public List<String> findCreativeIdsByCampaignId(String campaignId)
    {
        // TODO 自动生成的方法存根
        return null;
    }

    /**
     * 根据项目ID，查询该项目下的全部活动ID。
     * @param projectId 项目ID
     * @return
     */
    public List<String> findCampaignIdsByProjectId(String projectId)
    {
        List<String> result = new ArrayList<String>();
        
        CampaignModelExample example = new CampaignModelExample();
        example.createCriteria().andProjectIdEqualTo(projectId);
        List<CampaignModel> campaigns = campaignDao.selectByExample(example);
        
        if (campaigns != null && !campaigns.isEmpty())
        {
            for (CampaignModel campaign : campaigns)
            {
                result.add(campaign.getId());
            }
        }
        
        return result;
    }

    /**
     * 根据广告主ID查询其名下的全部项目。
     * @param advertiserId  广告主ID
     * @return
     */
    public List<String> findProjectIdsByAdvertiserId(String advertiserId)
    {
        List<String> result = new ArrayList<String>();
        
        ProjectModelExample example = new ProjectModelExample();
        example.createCriteria().andAdvertiserIdEqualTo(advertiserId);
        
        List<ProjectModel> projects = projectDao.selectByExample(example);
        
        if (projects != null && !projects.isEmpty())
        {
            for (ProjectModel project : projects)
            {
                result.add(project.getId());
            }
        }
        
        return result;
    }
    
    /**
     * 根据活动ID，查询出这个活动下的全部创意ID。
     * @param campaignId    活动ID
     * @return
     */
    public List<String> getCreativeIdListByCampaignId(String campaignId)
    {
        List<String> result = new ArrayList<String>();
        
        CreativeModelExample example = new CreativeModelExample();
        example.createCriteria().andCampaignIdEqualTo(campaignId);
        List<CreativeModel> creatives = creativeDao.selectByExample(example);
        
        if (creatives != null && !creatives.isEmpty())
        {
            for (CreativeModel creative : creatives)
            {
                result.add(creative.getId());
            }
        }
        
        return result;
    }
    
    /**
     * 根据项目ID，查询出这个项目下的全部创意ID。
     * @param projectId     项目ID
     * @return
     */
    public List<String> getCreativeIdListByProjectId(String projectId)
    {
        List<String> result = new ArrayList<String>();
        List<String> campaignIds = findCampaignIdsByProjectId(projectId);
        
        for (String campaignId : campaignIds)
        {
            result.addAll(getCreativeIdListByCampaignId(campaignId));
        }
        
        return result;
    }

}
