package com.pxene.pap.web.controller;

import java.util.List;

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
import com.pxene.pap.domain.beans.LandpageBean;
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.service.LandpageService;

@Controller
public class LandpageController {

	@Autowired
	private LandpageService landpageService;
	
	/**
	 * 添加落地页
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/landpage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String createProject(@Valid @RequestBody LandpageBean bean, HttpServletResponse response) throws Exception {
		landpageService.createLandpage(bean);
        return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), "id", bean.getId(), response);
	}
	/**
	 * 修改落地页
	 * @param id
	 * @param bean
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/landpage/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void updateProject(@PathVariable String id, @Valid @RequestBody LandpageBean bean, HttpServletResponse response) throws Exception {
		landpageService.updateLandpage(id, bean);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	/**
	 * 删除落地页
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/landpage/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteProject(@PathVariable String id, HttpServletResponse response) throws Exception {
		landpageService.deleteLandpage(id);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	/**
	 * 批量删除落地页
	 * @param ids
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/landpages", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteProject(@RequestBody String[] ids, HttpServletResponse response) throws Exception {
		landpageService.deleteLandpages(ids);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	/**
	 * 根据id查询落地页
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/landpage/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectLandpage(@PathVariable String id, HttpServletResponse response) throws Exception {
		LandpageBean bean = landpageService.selectLandpage(id);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), bean, response);
	}
	/**
	 * 查询落地页列表
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/landpages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectLandpages(@RequestParam(required = false) String name, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletResponse response) throws Exception {
		Page<Object> pager = null;
        if (pageNo != null && pageSize != null){
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        
		List<LandpageBean> beans = landpageService.selectLandpages(name);
		
		PaginationBean result = new PaginationBean(beans, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}
	/**
	 * 检查落地页监测代码状态
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/landpage/check/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public void checkCode(@PathVariable String id, HttpServletResponse response) throws Exception {
		landpageService.checkCode(id);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
}
