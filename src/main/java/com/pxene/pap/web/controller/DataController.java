package com.pxene.pap.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.exception.IllegalArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
	public String listAdvertisers(@RequestParam(required = false) String advertiserId, @RequestParam(required = true) String type,@RequestParam(required = true) Long startDate,
								  @RequestParam(required = true) Long endDate,HttpServletResponse response) throws Exception
	{
		if(!type.equals(StatusConstant.SUMMARYWAY_TOTAL) && !type.equals(StatusConstant.SUMMARYWAY_DAY)){
			throw new IllegalArgumentException("参数不正确");
		}

		Page<Object> pager = null;
		List<Map<String, Object>> Datas;
		if(advertiserId!=null) {
			Datas = dataService.getAdvertiserDataByAdvertiserId(startDate, endDate, advertiserId, type);
		}else{
			//查询全部客户
			Datas = dataService.getAllAdvertiserData(startDate, endDate, type);
		}
		PaginationBean result = new PaginationBean(Datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}

	@RequestMapping(value = "/data/projects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String listProjects(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId,
							   @RequestParam(required = true) String type,@RequestParam(required = true) Long startDate,
							   @RequestParam(required = true) Long endDate,  HttpServletResponse response) throws Exception
	{
		if(!type.equals(StatusConstant.SUMMARYWAY_TOTAL) && !type.equals(StatusConstant.SUMMARYWAY_DAY)){
			throw new IllegalArgumentException("参数不正确");
		}

		Page<Object> pager = null;
//    	if (pageNo != null && pageSize != null) {
//			pager = PageHelper.startPage(pageNo, pageSize);
//		}
		List<Map<String, Object>> Datas;
		if(projectId!=null){
			//如果projectId不为null，根据projectId查询
			Datas = dataService.getProjectDataByProjectId(startDate, endDate, type, projectId);
		}else if(projectId == null && advertiserId!=null){
			//如果projectId为null,根据客户查询
			Datas = dataService.getProjectDataByAdvertiserId(startDate, endDate, type, advertiserId);
		}else {
			//查询全部客户的项目数据
			Datas = dataService.getAllProjectData(startDate, endDate, type);
		}

		PaginationBean result = new PaginationBean(Datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}

	@RequestMapping(value = "/data/campaigns", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String listCampaigns(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId,
								@RequestParam(required = false) String campaignId,@RequestParam(required = true) String type, @RequestParam(required = true) Long startDate,
								@RequestParam(required = true) Long endDate,  HttpServletResponse response) throws Exception
	{
		if(!type.equals(StatusConstant.SUMMARYWAY_TOTAL) && !type.equals(StatusConstant.SUMMARYWAY_DAY)){
			throw new IllegalArgumentException("参数不正确");
		}

		Page<Object> pager = null;
		List<Map<String, Object>> Datas;
		if(campaignId!=null) {
			//查询指定活动的数据
			Datas = dataService.getCampaignDataByCampaignId(startDate, endDate, type, campaignId);
		}else if(campaignId == null && projectId != null){
			//查询指定项目的数据
			Datas = dataService.getCampaignDataByProjectId(startDate, endDate, type, projectId);
		}else if(campaignId == null && projectId == null && advertiserId != null){
			//查询客户的数据
			Datas = dataService.getCampaignDataByAdvertiserId(startDate, endDate, type,advertiserId);
		}else{
			//查询所有客户的数据
			Datas = dataService.getAllCampaignData(startDate, endDate, type);
		}
		PaginationBean result = new PaginationBean(Datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}

	@RequestMapping(value = "/data/creatives", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String listCreatives(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId,
								@RequestParam(required = false) String campaignId,@RequestParam(required = true) String type, @RequestParam(required = true) Long startDate,
								@RequestParam(required = true) Long endDate, HttpServletResponse response) throws Exception
	{
		if(!type.equals(StatusConstant.SUMMARYWAY_TOTAL) && !type.equals(StatusConstant.SUMMARYWAY_DAY)){
			throw new IllegalArgumentException("参数不正确");
		}

		Page<Object> pager = null;
		List<Map<String, Object>> Datas;
		if(campaignId != null){
			//查询指定活动的数据
			Datas = dataService.getCreativeDataByCampaignId(startDate, endDate, type, campaignId);
		}else if(campaignId == null && projectId != null){
			//查询指定项目的数据
			Datas = dataService.getCreativeDataByProjectId(startDate, endDate, type, projectId);
		}else if(campaignId == null && projectId == null && advertiserId != null){
			//查询指定用户的数据
			Datas = dataService.getCreativeDataByAdvertiserId(startDate, endDate, type, advertiserId);
		}else{
			Datas = dataService.getAllCreativeData(startDate, endDate, type);
		}

		PaginationBean result = new PaginationBean(Datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}




	@RequestMapping(value = "/data/action/import", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void importEffect(@RequestPart(name = "file", required = true) MultipartFile file, @RequestPart(name = "projectId", required = true) String projectId, HttpServletResponse response) throws Exception
	{
		dataService.importEffect(file, projectId);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}

}