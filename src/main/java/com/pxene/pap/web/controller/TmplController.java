package com.pxene.pap.web.controller;

import java.util.List;

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
import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.domain.beans.TmplBean.ImageTmpl;
import com.pxene.pap.domain.beans.TmplBean.InfoTmpl;
import com.pxene.pap.domain.beans.TmplBean.VideoTmpl;
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
	public String selectImageTmpls(@RequestParam(required = false) String campaignId, @RequestParam(required = false) String status, HttpServletResponse response) throws Exception {
		
		Page<Object> pager = null;
		
		List<ImageTmpl> bean = tmplService.selectImageTmpls(campaignId, status);
		
		PaginationBean result = new PaginationBean(bean, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
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
	public String selectVideoTmpls(@RequestParam(required = false) String campaignId, @RequestParam(required = false) String status, HttpServletResponse response) throws Exception {

		Page<Object> pager = null;
		
		List<VideoTmpl> bean = tmplService.selectVideoTmpls(campaignId, status);
		
		PaginationBean result = new PaginationBean(bean, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
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
	public String selectInfoflowTmpls(@RequestParam(required = false) String campaignId, @RequestParam(required = false) String status, HttpServletResponse response) throws Exception {
		
		Page<Object> pager = null;
		
		List<InfoTmpl> bean = tmplService.selectInfoflowTmpls(campaignId, status);
		
		PaginationBean result = new PaginationBean(bean, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}
}
