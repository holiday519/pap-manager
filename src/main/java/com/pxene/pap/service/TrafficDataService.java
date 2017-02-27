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
    @Autowired
    private DataService dataService;

    
    
    public List<TrafficData> listData(String advertiserId, String projectId, String campaignId, String creativeId, Long startDate, Long endDate) throws Exception
    {
        List<TrafficData> result = new ArrayList<TrafficData>();
        TrafficData td = null;
        List<Map<String, Object>> serviceResult = null;
        
        if (!StringUtils.isEmpty(creativeId))
        {
            serviceResult = dataService.getCreativeData(startDate, endDate, creativeId);
        }
        else
        {
            if (!StringUtils.isEmpty(campaignId))
            {
                serviceResult = dataService.getCampaignData(startDate, endDate, campaignId);
            }
            else
            {
                if (!StringUtils.isEmpty(projectId))
                {
                    serviceResult = dataService.getProjectData(startDate, endDate, projectId);
                }
                else
                {
                    if (!StringUtils.isEmpty(advertiserId))
                    {
                        serviceResult = dataService.getAdvertiserData(startDate, endDate, advertiserId);
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
