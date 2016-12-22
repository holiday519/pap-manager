package com.pxene.pap.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.pxene.pap.constant.HttpStatusCode;
import com.pxene.pap.domain.beans.CampaignBean;
import com.pxene.pap.domain.model.custom.PaginationResult;
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
		return ResponseUtils.sendReponse(HttpStatusCode.OK, "id", bean.getId(), response);
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
	public void updateCampaign(@PathVariable String id, @RequestBody CampaignBean bean, HttpServletResponse response) throws Exception {
		campaignService.updateCampaign(id, bean);
		response.setStatus(HttpStatusCode.NO_CONTENT);
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
		response.setStatus(HttpStatusCode.NO_CONTENT);
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
	public String selectProject(@PathVariable String id, HttpServletResponse response) throws Exception {
		CampaignBean bean = campaignService.selectCampaign(id);
		return ResponseUtils.sendReponse(HttpStatusCode.OK, bean, response);
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
	@RequestMapping(value = "/campaign", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectProject(@RequestParam(required = false) String name, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Page<Object> pager = null;
		if (pageNo != null && pageSize != null) {
			pager = PageHelper.startPage(pageNo, pageSize);
		}
        
		List<CampaignBean> selectCampaigns = campaignService.selectCampaigns(name);
		
		PaginationResult result = new PaginationResult(selectCampaigns, pager);
		return ResponseUtils.sendReponse(HttpStatusCode.OK, result, response);
	}
	
	/**
	 * 投放活动
	 * @param campaignIds
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/campaign_PutOn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void putOnByCampaign(@RequestBody List<String> campaignIds, HttpServletResponse response) throws Exception {
		campaignService.putOnCampaign(campaignIds);
		response.setStatus(HttpStatusCode.NO_CONTENT);
	}
}
