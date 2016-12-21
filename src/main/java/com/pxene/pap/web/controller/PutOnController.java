package com.pxene.pap.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.constant.HttpStatusCode;
import com.pxene.pap.service.PutOnService;

@Controller
public class PutOnController {

	@Autowired
	private PutOnService putOnService;
	
	/**
	 * 投放活动
	 * @param campaignIds
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/putOnCampaign", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void putOnByCampaign(@RequestBody List<String> campaignIds, HttpServletResponse response) throws Exception {
		putOnService.putOnCampaign(campaignIds);
		response.setStatus(HttpStatusCode.NO_CONTENT);
	}
	
	/**
	 * 投放项目
	 * @param projectIds
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/putOnProject", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void putOnProject(@RequestBody List<String> projectIds, HttpServletResponse response) throws Exception {
		putOnService.putOnProject(projectIds);
		response.setStatus(HttpStatusCode.NO_CONTENT);
	}
}
