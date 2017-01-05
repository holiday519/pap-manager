package com.pxene.pap.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.TmplBean;
import com.pxene.pap.service.TmplService;

@Controller
public class TmplController {

	@Autowired
	private TmplService tmplService;
	
	/**
	 * 查询图片模版信息
	 * @param appids
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tmpl/images", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectImageTmpls(@RequestParam(required = false) String campaignId, @RequestParam(required = false) String creativeId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		TmplBean bean = tmplService.selectImageTmpls(campaignId, creativeId);
		
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), bean, response);
	}
	
	/**
	 * 查询视频模版信息
	 * @param appids
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tmpl/videos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectVideoTmpls(@RequestParam(required = false) String campaignId, @RequestParam(required = false) String creativeId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		TmplBean bean = tmplService.selectVideoTmpls(campaignId, creativeId);
		
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), bean, response);
	}
	
	/**
	 * 查询信息流模版信息
	 * @param appids
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tmpl/infoflows", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectInfoflowTmpls(@RequestParam(required = false) String campaignId, @RequestParam(required = false) String creativeId, HttpServletResponse response) throws Exception {
		
		TmplBean bean = tmplService.selectInfoflowTmpls(campaignId, creativeId);
		
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), bean, response);
	}
}
