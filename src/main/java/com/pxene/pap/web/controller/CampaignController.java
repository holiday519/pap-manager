package com.pxene.pap.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.CampaignBean;
import com.pxene.pap.domain.beans.CampaignTargetBean;
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.service.CampaignService;

@Controller
public class CampaignController {
	
	@Autowired
	private CampaignService campaignService;
	
	/**
	 * 创建活动
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/campaign", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String createCampaign(@Valid @RequestBody CampaignBean bean, HttpServletResponse response) throws Exception {
		campaignService.createCampaign(bean);
		return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), "id", bean.getId(), response);
	}
	
	/**
	 * 设置活动定向（新增和修改）
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/campaign/target/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	@ResponseBody
//	public void createCampaignTarget(@PathVariable String id, @RequestBody CampaignTargetBean bean, HttpServletResponse response) throws Exception {
//		campaignService.createCampaignTarget(id, bean);
//		response.setStatus(HttpStatus.NO_CONTENT.value());
//	}
	
	/**
	 * 修改活动
	 * @param id
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/campaign/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void updateCampaign(@PathVariable String id, @RequestBody CampaignBean bean, HttpServletResponse response) throws Exception {
		campaignService.updateCampaign(id, bean);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	/**
	 * 修改活动状态
	 * @param id
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/campaign/status/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void updateCampaignStatus(@PathVariable String id, @RequestBody Map<String, String> map, HttpServletResponse response) throws Exception {
		campaignService.updateCampaignStatus(id, map);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	/**
	 * 删除活动
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/campaign/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteCampaign(@PathVariable String id, HttpServletResponse response) throws Exception {
		campaignService.deleteCampaign(id);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	/**
	 * 批量删除活动
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/campaigns", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteCampaigns(@RequestParam(required = true) String ids, HttpServletResponse response) throws Exception {
		campaignService.deleteCampaigns(ids.split(","));
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	/**
	 * 根据id查询活动
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/campaign/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getCampaign(@PathVariable String id, HttpServletResponse response) throws Exception {
		CampaignBean bean = campaignService.getCampaign(id);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), bean, response);
	}
	
	/**
	 * 查询活动列表
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception  
	 */
	@RequestMapping(value = "/campaigns", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String listCampaigns(@RequestParam(required = false) String name, @RequestParam(required = false) boolean calScore, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate, @RequestParam(required = false) String projectId,
								@RequestParam(required = false) String sortKey, @RequestParam(required = false) String sortType,@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletResponse response) throws Exception {
		Page<Object> pager = null;
		if (pageNo != null && pageSize != null) {
			pager = PageHelper.startPage(pageNo, pageSize);
		}
        
		List<CampaignBean> campaigns = campaignService.listCampaigns(name, projectId, startDate, endDate, sortKey, sortType, calScore);
		
		PaginationBean result = new PaginationBean(campaigns, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}
	
	/**
	 * 批量同步活动下创意
	 * @param ids
	 * @param response  
	 * @throws Exception   
	 */
	@RequestMapping(value = "/campaigns/synchronize", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void synchronizeCreatives(@RequestParam(required = true) String ids, HttpServletResponse response) throws Exception {
		campaignService.synchronizeCreatives(ids.split(","));
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	/**
	 * 修改活动开始结束日期
	 * @param id
	 * @param map
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/campaigns/date/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody		
	public void updateCampaignStartAndEndDate(@PathVariable String id, @RequestBody Map<String, String> map, HttpServletResponse response) throws Exception {	
		campaignService.updateCampaignStartAndEndDate(id, map);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	/**
	 * 批量修改活动下创意价格
	 * @param ids
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/campaigns/price", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void updateCampaignsPrices(@RequestParam(required = true) String ids, @RequestBody(required = true) Map<String, String> map, HttpServletResponse response) throws Exception {
		campaignService.updateCampaignsPrices(ids.split(","), map);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
}
