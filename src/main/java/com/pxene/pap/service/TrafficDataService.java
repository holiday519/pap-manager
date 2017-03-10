package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.domain.beans.TrafficData;
import com.pxene.pap.exception.IllegalArgumentException;


@Service
public class TrafficDataService
{
    private static final String SCOPE_PROJECT = "project";
    private static final String SCOPE_CREATIVE = "creative";
    private static final String SCOPE_CAMPAIGN = "campaign";
    @Autowired
    private DataService dataService;

    
    
    public List<TrafficData> listData(String advertiserId, String projectId, String campaignId, String creativeId, String scope, Long startDate, Long endDate) throws Exception
    {
        List<TrafficData> result = new ArrayList<TrafficData>();
        TrafficData td = null;
        List<Map<String, Object>> serviceResult = new ArrayList<Map<String, Object>>();;
        
        
        if (!StringUtils.isEmpty(creativeId))
        {
            serviceResult = dataService.getCreativeData(startDate, endDate, creativeId);
        }
        else
        {
            if (!StringUtils.isEmpty(campaignId))
            {
                // 不勾选Radio | 勾选Radio：“活动” | 勾选Radio：“项目”
                if (StringUtils.isEmpty(scope) || SCOPE_CAMPAIGN.equals(scope) || SCOPE_PROJECT.equals(scope))
                {
                    serviceResult = dataService.getCampaignData(startDate, endDate, campaignId);
                }
                
                // 勾选Radio：“创意”
                if (SCOPE_CREATIVE.equals(scope))
                {
                    //serviceResult = new ArrayList<Map<String, Object>>();
                    
                    List<String> creativeIdList = dataService.getCreativeIdListByCampaignId(campaignId);
                    
                    for (String id : creativeIdList)
                    {
                        List<Map<String, Object>> creativeData = dataService.getCreativeData(startDate, endDate, id);
                        serviceResult.addAll(creativeData);
                    }
                }
            }
            else
            {
                if (!StringUtils.isEmpty(projectId))
                {
                    // 不勾选Radio | 勾选Radio：“项目”
                    if (StringUtils.isEmpty(scope) || SCOPE_PROJECT.equals(scope))
                    {
                        serviceResult = dataService.getProjectData(startDate, endDate, projectId);
                    }
                    
                    // 勾选Radio：“活动”
                    if (SCOPE_CAMPAIGN.equals(scope))
                    {
                        //serviceResult = new ArrayList<Map<String, Object>>();
                        
                        List<String> projectIdList = dataService.findCampaignIdListByProjectId(projectId);
                        
                        for (String id : projectIdList)
                        {
                            List<Map<String, Object>> campaignData = dataService.getCampaignData(startDate, endDate, id);
                            serviceResult.addAll(campaignData);
                        }
                    }
                    
                    // 勾选Radio：“创意”
                    if (SCOPE_CREATIVE.equals(scope))
                    {
                        //serviceResult = new ArrayList<Map<String, Object>>();
                        
                        List<String> creativeIdList = dataService.getCreativeIdListByProjectId(projectId);
                        
                        for (String id : creativeIdList)
                        {
                            List<Map<String, Object>> creativeData = dataService.getCreativeData(startDate, endDate, id);
                            serviceResult.addAll(creativeData);
                        }
                    }
                }
                else
                {
                    if (!StringUtils.isEmpty(advertiserId))
                    {
                        // 不勾选Radio
                        if (StringUtils.isEmpty(scope))
                        {
                            serviceResult = dataService.getAdvertiserData(startDate, endDate, advertiserId);
                        }
                        
                        // 勾选Radio：“项目”
                        if (SCOPE_PROJECT.equals(scope))
                        {
                            List<String> projectIdList = dataService.getProjectIdListByAdvertiserId(advertiserId);
                            
                            for (String id : projectIdList)
                            {
                                List<Map<String, Object>> projectData = dataService.getProjectData(startDate, endDate, id);
                                serviceResult.addAll(projectData);
                            }
                        }
                        
                        // 勾选Radio：“活动”
                        if (SCOPE_CAMPAIGN.equals(scope))
                        {
                            List<String> campaignIdList = dataService.getCampaignIdListByAdvertiserId(advertiserId);
                            
                            for (String id : campaignIdList)
                            {
                                List<Map<String, Object>> campaignData = dataService.getCampaignData(startDate, endDate, id);
                                serviceResult.addAll(campaignData);
                            }
                        }
                        
                        // 勾选Radio：“创意”
                        if (SCOPE_CREATIVE.equals(scope))
                        {
                            List<String> creativeIdList = dataService.getCreativeIdListByAdvertiserId(advertiserId);
                            
                            for (String id : creativeIdList)
                            {
                                List<Map<String, Object>> creativeData = dataService.getCreativeData(startDate, endDate, id);
                                serviceResult.addAll(creativeData);
                            }
                        }
                    }
                    else
                    {
                        throw new IllegalArgumentException();
                    }
                }
            }
        }
        
        if (serviceResult != null && !serviceResult.isEmpty())
        {
            for (Map<String, Object> sr : serviceResult)
            {
                td = new TrafficData();
                td.setDate((String) sr.get("date"));
                td.setName((String) sr.get("name"));
                td.setImpressionAmount((Long) sr.get("impressionAmount"));
                td.setClickAmount((Long) sr.get("clickAmount"));
                td.setClickRate((Float) sr.get("clickRate"));
                td.setConversionAmount((Long) sr.get("jumpAmount"));
                td.setTotalCost((Float) sr.get("totalCost"));
                td.setImpressionCost((Float) sr.get("impressionCost"));
                td.setClickCost((Float) sr.get("clickCost"));
                td.setConversionCost((Float) sr.get("jumpCost"));
                
                result.add(td);
            }
        }
        
        return result;
    }
    
}