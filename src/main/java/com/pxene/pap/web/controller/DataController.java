package com.pxene.pap.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.pxene.pap.constant.CodeTableConstant;
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
import com.pxene.pap.domain.beans.AdvertiserBean;
import com.pxene.pap.domain.beans.AnalysisBean;
import com.pxene.pap.domain.beans.BasicDataBean;
import com.pxene.pap.domain.beans.CampaignBean;
import com.pxene.pap.domain.beans.CreativeBean;
import com.pxene.pap.domain.beans.EffectFileBean;
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.domain.beans.ProjectBean;
import com.pxene.pap.service.DataService;

// REVIEW ME: 1.controller中不出现业务逻辑   2.中文常量化

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
		List<AnalysisBean> Datas = dataService.listTimes(startDate, endDate, advertiserId, projectId, campaignId, creativeId);
		PaginationBean result = new PaginationBean(Datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}

	@RequestMapping(value = "/data/regions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String listRegions(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, @RequestParam(required = false) String campaignId,
							  @RequestParam(required = false) String creativeId, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate, HttpServletResponse response) throws Exception
	{
		Page<Object> pager = null;
		List<AnalysisBean> Datas = dataService.listRegions(startDate, endDate, advertiserId, projectId, campaignId, creativeId);
		PaginationBean result = new PaginationBean(Datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}

	@RequestMapping(value = "/data/operators", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String listOperators(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, @RequestParam(required = false) String campaignId,
								@RequestParam(required = false) String creativeId, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate, HttpServletResponse response) throws Exception
	{
		Page<Object> pager = null;
		List<AnalysisBean> Datas = dataService.listOperators(startDate, endDate, advertiserId, projectId, campaignId, creativeId);
		PaginationBean result = new PaginationBean(Datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}

	@RequestMapping(value = "/data/networks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String listNetworks(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, @RequestParam(required = false) String campaignId,
							   @RequestParam(required = false) String creativeId, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate, HttpServletResponse response) throws Exception
	{
		Page<Object> pager = null;
		List<AnalysisBean> Datas = dataService.listNetworks(startDate, endDate, advertiserId, projectId, campaignId, creativeId);
		PaginationBean result = new PaginationBean(Datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}

	@RequestMapping(value = "/data/systems", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String listSystems(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, @RequestParam(required = false) String campaignId,
							  @RequestParam(required = false) String creativeId, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate, HttpServletResponse response) throws Exception
	{
		Page<Object> pager = null;
		List<AnalysisBean> Datas = dataService.listSystems(startDate, endDate, advertiserId, projectId, campaignId, creativeId);
		PaginationBean result = new PaginationBean(Datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}

	@RequestMapping(value = "/data/advertisers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String listAdvertisers(@RequestParam(required = false) String advertiserId, @RequestParam(required = true) String type, @RequestParam(required = true) Long startDate,
								  @RequestParam(required = true) Long endDate, HttpServletResponse response) throws Exception
	{

		Page<Object> pager = null;
		List<AdvertiserBean> datas = dataService.listAdvertisers(advertiserId, type, startDate, endDate);
		PaginationBean result = new PaginationBean(datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}

	@RequestMapping(value = "/data/projects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String listProjects(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId,
							   @RequestParam(required = true) String type,@RequestParam(required = true) Long startDate,
							   @RequestParam(required = true) Long endDate,  HttpServletResponse response) throws Exception
	{

		Page<Object> pager = null;
//    	if (pageNo != null && pageSize != null) {
//			pager = PageHelper.startPage(pageNo, pageSize);
//		}
		List<ProjectBean> datas = dataService.listProjects(advertiserId, projectId, type,startDate,endDate);

		PaginationBean result = new PaginationBean(datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}

	@RequestMapping(value = "/data/campaigns", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String listCampaigns(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId,
								@RequestParam(required = false) String campaignId,@RequestParam(required = true) String type, @RequestParam(required = true) Long startDate,
								@RequestParam(required = true) Long endDate,  HttpServletResponse response) throws Exception
	{

		Page<Object> pager = null;
		List<CampaignBean> datas = dataService.listCampaigns(advertiserId, projectId, campaignId, type, startDate, endDate);

		PaginationBean result = new PaginationBean(datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}

	@RequestMapping(value = "/data/creatives", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String listCreatives(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId,
								@RequestParam(required = false) String campaignId, @RequestParam(required = false) String creativeId,
								@RequestParam(required = true) String type, @RequestParam(required = true) Long startDate,
								@RequestParam(required = true) Long endDate, HttpServletResponse response) throws Exception
	{

		Page<Object> pager = null;
		List<CreativeBean> datas = dataService.listCreatives(advertiserId, projectId, campaignId, creativeId, type, startDate, endDate);

		PaginationBean result = new PaginationBean(datas, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}

	@RequestMapping(value = "/data/effect/import", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void importEffect(@RequestPart(name = "file", required = true) MultipartFile file, @RequestPart(name = "projectId", required = true) String projectId, HttpServletResponse response) throws Exception
	{
		dataService.importEffect(file, projectId);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	@RequestMapping(value = "/data/effect/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String listEffects(@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletResponse response) throws Exception
	{
		Page<Object> pager = null;
		if (pageNo != null && pageSize != null) {
			pager = PageHelper.startPage(pageNo, pageSize);
		}
		List<EffectFileBean> effectFiles = dataService.listEffectFiles();
		PaginationBean result = new PaginationBean(effectFiles, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}

	@RequestMapping(value = "/data/export/advertisers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void exportAdvertisers(@RequestParam(required = false) String advertiserId, @RequestParam(required = true) String type, @RequestParam(required = true) Long startDate,
								  @RequestParam(required = true) Long endDate,HttpServletResponse response) throws Exception
	{

		List<AdvertiserBean> datas = dataService.listAdvertisers(advertiserId,type,startDate,endDate);
		//生成文件名
		String fileName = "advertisers-"+ dataService.renameDatasExcel(type, startDate, endDate);
		//下载Excel
		dataService.exportDataToExcel(type, datas, fileName, response);
	}

	@RequestMapping(value = "/data/export/projects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void exportProjects(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId,
							   @RequestParam(required = true) String type,@RequestParam(required = true) Long startDate,
							   @RequestParam(required = true) Long endDate,  HttpServletResponse response) throws Exception
	{

		List<ProjectBean> datas = dataService.listProjects(advertiserId, projectId, type,startDate,endDate);
		//生成文件名
		String fileName = "projects-"+ dataService.renameDatasExcel(type, startDate, endDate);
		//下载Excel
		dataService.exportDataToExcel(type, datas, fileName, response);
	}

	@RequestMapping(value = "/data/export/campaigns", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void exportCampaigns(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId,
								@RequestParam(required = false) String campaignId,@RequestParam(required = true) String type, @RequestParam(required = true) Long startDate,
								@RequestParam(required = true) Long endDate,  HttpServletResponse response) throws Exception
	{

		List<CampaignBean> datas = dataService.listCampaigns(advertiserId, projectId, campaignId, type, startDate, endDate);
		//生成文件名
		String fileName = "campaigns-"+ dataService.renameDatasExcel(type, startDate, endDate);
		//下载Excel
		dataService.exportDataToExcel(type, datas, fileName, response);
	}

	@RequestMapping(value = "/data/export/creatives", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void exportCreatives(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId,
								@RequestParam(required = false) String campaignId, @RequestParam(required = false) String creativeId, 
								@RequestParam(required = true) String type, @RequestParam(required = true) Long startDate,
								@RequestParam(required = true) Long endDate, HttpServletResponse response) throws Exception
	{

		List<CreativeBean> datas = dataService.listCreatives(advertiserId, projectId, campaignId, creativeId, type, startDate, endDate);
		//生成文件名
		String fileName = "creatives-"+ dataService.renameDatasExcel(type, startDate, endDate);
		//下载Excel
		dataService.exportDataToExcel(type, datas, fileName, response);
	}


}