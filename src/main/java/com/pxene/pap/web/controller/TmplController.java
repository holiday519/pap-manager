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
	@RequestMapping(value = "/appTmpl/image", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectappTmplImage(@RequestParam String appids, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		APPTmplBean bean = tmplService.selectAppTmplImage(appids);
		
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
	@RequestMapping(value = "/appTmpl/video", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectappTmplVideo(@RequestParam String appids, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		APPTmplBean bean = tmplService.selectAppTmplVideo(appids);
		
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
	@RequestMapping(value = "/appTmpl/infoFlow", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectappTmplInfoFlow(@RequestParam String appids, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		APPTmplBean bean = tmplService.selectAppTmplInfo(appids);
		
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), bean, response);
	}
}
