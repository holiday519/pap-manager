package com.pxene.pap.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.pxene.pap.domain.beans.CampaignInfoBean;
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
	public String createCampaign(@Valid @RequestBody CampaignInfoBean bean, HttpServletResponse response) throws Exception {
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
	@RequestMapping(value = "/campaign/target/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void createCampaignTarget(@PathVariable String id, @RequestBody CampaignTargetBean bean, HttpServletResponse response) throws Exception {
		campaignService.createCampaignTarget(id, bean);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
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
	public void updateCampaign(@PathVariable String id, @RequestBody CampaignInfoBean bean, HttpServletResponse response) throws Exception {
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
	 * 根据id查询活动
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/campaign/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectCampaign(@PathVariable String id, HttpServletResponse response) throws Exception {
		CampaignBean bean = campaignService.selectCampaign(id);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), bean, response);
	}
	
	/**
	 * 根据活动id查询频次信息
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/campaign/frequency/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectProject(@PathVariable String id, HttpServletResponse response) throws Exception {
		CampaignBean bean = campaignService.selectFrequencyByCampaignId(id);
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
	public String selectCampaigns(@RequestParam(required = false) String name, @RequestParam(required = false) String projectId, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Page<Object> pager = null;
		if (pageNo != null && pageSize != null) {
			pager = PageHelper.startPage(pageNo, pageSize);
		}
        
		List<CampaignBean> selectCampaigns = campaignService.selectCampaigns(name, projectId);
		
		PaginationBean result = new PaginationBean(selectCampaigns, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}
	
	/**
	 * 投放活动
	 * @param campaignIds
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/campaign/launch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void putOnByCampaign(@RequestParam(required = true) String campaignIds, HttpServletResponse response) throws Exception {
		campaignService.launchCampaign(campaignIds);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	/**
	 * 暂停活动
	 * @param campaignIds
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/campaign/pause", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void pauseByCampaign(@RequestParam(required = true) String campaignIds, HttpServletResponse response) throws Exception {
		campaignService.pauseCampaign(campaignIds);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	/**
	 * 添加活动模版价格
	 * @param campaignId
	 * @param tmplIds
	 * @param creativeTypes
	 * @param prices
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/campaign/addTmplPrice", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void addCampaignTmplPrice(@RequestBody Map<String, Map<String, String>[]> param, HttpServletResponse response) throws Exception {
		campaignService.addCampaignTmplPrice(param);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
}
