package com.pxene.pap.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.service.DataService;

@Controller
public class DataController
{
    @Autowired
    private DataService dataService;
    
    @RequestMapping(value = "/data/times", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listTimes(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, @RequestParam(required = false) String campaignId, 
    		@RequestParam(required = false) String creativeId, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate, HttpServletResponse response) throws Exception
    {
    	Page<Object> pager = null;
    	List<Map<String, Object>> Datas = dataService.getDataForTime(startDate, endDate, advertiserId, projectId, campaignId, creativeId);
    	PaginationBean result = new PaginationBean(Datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }

    @RequestMapping(value = "/data/regions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listRegions(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, @RequestParam(required = false) String campaignId,
            @RequestParam(required = false) String creativeId, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate, HttpServletResponse response) throws Exception
    {
    	Page<Object> pager = null;
    	List<Map<String, Object>> Datas = dataService.getDataForRegion(startDate, endDate, advertiserId, projectId, campaignId, creativeId);
    	PaginationBean result = new PaginationBean(Datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
    
    @RequestMapping(value = "/data/operators", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listOperators(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, @RequestParam(required = false) String campaignId,
            @RequestParam(required = false) String creativeId, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate, HttpServletResponse response) throws Exception
    {
    	Page<Object> pager = null;
    	List<Map<String, Object>> Datas = dataService.getDataForOperator(startDate, endDate, advertiserId, projectId, campaignId, creativeId);
    	PaginationBean result = new PaginationBean(Datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
    
    @RequestMapping(value = "/data/networks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listNetworks(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, @RequestParam(required = false) String campaignId,
            @RequestParam(required = false) String creativeId, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate, HttpServletResponse response) throws Exception
    {
    	Page<Object> pager = null;
    	List<Map<String, Object>> Datas = dataService.getDataForNetwork(startDate, endDate, advertiserId, projectId, campaignId, creativeId);
    	PaginationBean result = new PaginationBean(Datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
    
    @RequestMapping(value = "/data/systems", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listSystems(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, @RequestParam(required = false) String campaignId,
            @RequestParam(required = false) String creativeId, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate, HttpServletResponse response) throws Exception
    {
    	Page<Object> pager = null;
    	List<Map<String, Object>> Datas = dataService.getDataForSystem(startDate, endDate, advertiserId, projectId, campaignId, creativeId);
    	PaginationBean result = new PaginationBean(Datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
    
    @RequestMapping(value = "/data/advertisers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listAdvertisers(@RequestParam(required = true) String id, @RequestParam(required = true) Long startDate, 
    		@RequestParam(required = true) Long endDate, @RequestParam(required = false) Integer pageSize, 
    		@RequestParam(required = false) Integer pageNo, HttpServletResponse response) throws Exception
    {
    	Page<Object> pager = null;
    	List<Map<String, Object>> Datas = dataService.getAdvertiserData(startDate, endDate, id);
    	PaginationBean result = new PaginationBean(Datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
    
    @RequestMapping(value = "/data/projects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listProjects(@RequestParam(required = true) String id, @RequestParam(required = true) Long startDate, 
    		@RequestParam(required = true) Long endDate, @RequestParam(required = false) Integer pageSize, 
    		@RequestParam(required = false) Integer pageNo, HttpServletResponse response) throws Exception
    {
    	Page<Object> pager = null;
    	if (pageNo != null && pageSize != null) {
			pager = PageHelper.startPage(pageNo, pageSize);
		}
    	List<Map<String, Object>> Datas = dataService.getProjectData(startDate, endDate, id);
    	PaginationBean result = new PaginationBean(Datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
    
    @RequestMapping(value = "/data/campaigns", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listCampaigns(@RequestParam(required = true) String id, @RequestParam(required = true) Long startDate, 
    		@RequestParam(required = true) Long endDate, @RequestParam(required = false) Integer pageSize, 
    		@RequestParam(required = false) Integer pageNo, HttpServletResponse response) throws Exception
    {
    	Page<Object> pager = null;
    	List<Map<String, Object>> Datas = dataService.getCampaignData(startDate, endDate, id);
    	PaginationBean result = new PaginationBean(Datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
    
    @RequestMapping(value = "/data/creatives", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listCreatives(@RequestParam(required = true) String id, @RequestParam(required = true) Long startDate, 
    		@RequestParam(required = true) Long endDate, @RequestParam(required = false) Integer pageSize, 
    		@RequestParam(required = false) Integer pageNo, HttpServletResponse response) throws Exception
    {
    	Page<Object> pager = null;
    	List<Map<String, Object>> Datas = dataService.getCreativeData(startDate, endDate, id);
    	PaginationBean result = new PaginationBean(Datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
    
}