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
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.domain.beans.SizeBean;
import com.pxene.pap.service.SizeService;

@Controller
public class SizeController {

	@Autowired
	private SizeService sizeService;
	
	/**
	 * 添加尺寸
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/size", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String createProject(@Valid @RequestBody SizeBean bean, HttpServletResponse response) throws Exception {
		sizeService.createsize(bean);
        return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), "id", bean.getId(), response);
	}
	/**
	 * 修改尺寸
	 * @param id
	 * @param bean
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/size/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void updateProject(@PathVariable String id, @Valid @RequestBody SizeBean bean, HttpServletResponse response) throws Exception {
		sizeService.updatesize(id, bean);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	/**
	 * 删除尺寸
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/size/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteProject(@PathVariable String id, HttpServletResponse response) throws Exception {
		sizeService.deletesize(id);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	/**
	 * 根据id查询尺寸
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/size/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectsize(@PathVariable String id, HttpServletResponse response) throws Exception {
		SizeBean bean = sizeService.selectsize(id);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), bean, response);
	}
	/**
	 * 查询尺寸列表
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sizes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectsizes(@RequestParam(required = false) String name, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletResponse response) throws Exception {
		Page<Object> pager = null;
        if (pageNo != null && pageSize != null){
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        
		List<SizeBean> beans = sizeService.selectsizes(name);
		
		PaginationBean result = new PaginationBean(beans, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}
}
