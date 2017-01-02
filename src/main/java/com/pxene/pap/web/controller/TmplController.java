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
import com.pxene.pap.domain.beans.APPTmplBean;
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
	public String selectImageTmpls(@RequestParam String appIds, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		APPTmplBean bean = tmplService.selectAppTmplImage(appIds);
		
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
	public String selectVideoTmpls(@RequestParam String appIds, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		APPTmplBean bean = tmplService.selectAppTmplVideo(appIds);
		
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
	@RequestMapping(value = "/tmpl/infoFlows", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectInfoFlowTmpls(@RequestParam String appIds, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		APPTmplBean bean = tmplService.selectAppTmplInfo(appIds);
		
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), bean, response);
	}
}
