package com.pxene.pap.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.domain.beans.TmplBean.ImageTmpl;
import com.pxene.pap.domain.beans.TmplBean.InfoflowTmpl;
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
	public String listImageTmpls(@RequestParam(required = false) String adxId, HttpServletResponse response) throws Exception {
		
		Page<Object> pager = null;
		
		List<ImageTmpl> bean = tmplService.listImageTmpls(adxId);
		
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
	public String listVideoTmpls(@RequestParam(required = false) String adxId, HttpServletResponse response) throws Exception {

		Page<Object> pager = null;
		
		List<VideoTmpl> bean = tmplService.listVideoTmpls(adxId);
		
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
	public String listInfoflowTmpls(@RequestParam(required = false) String adxId, HttpServletResponse response) throws Exception {
		
		Page<Object> pager = null;
		
		List<InfoflowTmpl> bean = tmplService.listInfoflowTmpls(adxId);
		
		PaginationBean result = new PaginationBean(bean, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}
	
	/**
	 * 查询图片模版
	 * @param id
	 * @param response
	 * @throws Exception
	 */
    @RequestMapping(value = "/tmpl/image/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getImageTmpl(@PathVariable String id, HttpServletResponse response) throws Exception
    {
        ImageTmpl imageTmpl = tmplService.getImageTmpl(id);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), imageTmpl, response);
    }
    
    /**
     * 查询视频模版
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/tmpl/video/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getVideoTmpl(@PathVariable String id, HttpServletResponse response) throws Exception
    {
    	VideoTmpl videoTmpl = tmplService.getVideoTmpl(id);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), videoTmpl, response);
    }
    
    /**
     * 查询信息流模版
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/tmpl/infoflow/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getInfoflowTmpl(@PathVariable String id, HttpServletResponse response) throws Exception
    {
        InfoflowTmpl infoflowTmpl = tmplService.getInfoflowTmpl(id);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), infoflowTmpl, response);
    }
}
