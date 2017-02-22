package com.pxene.pap.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pxene.pap.domain.beans.BasicDataBean;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.ServerFailureException;
import com.pxene.pap.service.DataService;


public class DataController
{
    @Autowired
    private DataService dataService;
    
    
    @RequestMapping(value = "/data/times", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listTimes(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, @RequestParam(required = false) String campaignId, @RequestParam(required = false) String creativeId, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate) throws Exception
    {
        List<String> creativeIdList = new ArrayList<String>();
        List<String> tmpList = null;
        
        if (!StringUtils.isEmpty(creativeId))
        {
            creativeIdList.add(creativeId);
        }
        else 
        {
            if (!StringUtils.isEmpty(campaignId))
            {
                // 查询属于指定活动的全部的创意
                tmpList = dataService.getCreativeIdListByCampaignId(campaignId);
                
                creativeIdList.addAll(tmpList);
            }
            else 
            {
                if (!StringUtils.isEmpty(projectId))
                {
                    // 查询属于指定项目的全部的活动
                    List<String> campaignIds = dataService.findCampaignIdsByProjectId(projectId);
                    
                    for (String id : campaignIds)
                    {
                        tmpList = dataService.getCreativeIdListByCampaignId(id);
                        creativeIdList.addAll(tmpList);
                    }
                }
                else 
                {
                    if (!StringUtils.isEmpty(advertiserId))
                    {
                        // 查询属于指定广告主的全部项目
                        List<String> projectIds = dataService.findProjectIdsByAdvertiserId(advertiserId);
                        
                        for (String id : projectIds)
                        {
                            tmpList = dataService.getCreativeIdListByProjectId(id);
                            creativeIdList.addAll(tmpList);
                        }
                    }
                    else
                    {
                        throw new IllegalArgumentException();
                    }
                }
            }
        }
        
        if (!creativeIdList.isEmpty())
        {
            List<BasicDataBean> list = dataService.get("", creativeIdList);
            
            ObjectMapper mapper = new ObjectMapper();
            
            return mapper.writeValueAsString(list);
        }
        else
        {
            throw new ServerFailureException();
        }
    }

    @RequestMapping(value = "/data/regions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listRegions(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, @RequestParam(required = false) String campaignId,
            @RequestParam(required = false) String creativeId, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate) throws Exception
    {
        return "";
    }
    
    @RequestMapping(value = "/data/operators", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listOperators(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, @RequestParam(required = false) String campaignId,
            @RequestParam(required = false) String creativeId, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate) throws Exception
    {
        return "";
    }
    
    @RequestMapping(value = "/data/networks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listNetworks(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, @RequestParam(required = false) String campaignId,
            @RequestParam(required = false) String creativeId, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate) throws Exception
    {
        return "";
    }
    
    @RequestMapping(value = "/data/systems", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listSystems(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, @RequestParam(required = false) String campaignId,
            @RequestParam(required = false) String creativeId, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate) throws Exception
    {
        return "";
    }
    
}