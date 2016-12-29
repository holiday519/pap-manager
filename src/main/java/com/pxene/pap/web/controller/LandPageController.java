package com.pxene.pap.web.controller;

import java.util.List;

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
import com.pxene.pap.domain.beans.LandPageBean;
import com.pxene.pap.domain.model.custom.PaginationResult;
import com.pxene.pap.service.LandPageService;

@Controller
public class LandPageController {

	@Autowired
	private LandPageService landPageService;
	
	/**
	 * 添加落地页
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/landpage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String createProject(@Valid @RequestBody LandPageBean bean, HttpServletResponse response) throws Exception {
		landPageService.createLandPage(bean);
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
	public void updateProject(@PathVariable String id, @Valid @RequestBody LandPageBean bean, HttpServletResponse response) throws Exception {
		landPageService.updateLandPage(id, bean);
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
		landPageService.deleteLandPage(id);
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
	public String selectLandPage(@PathVariable String id, HttpServletResponse response) throws Exception {
		LandPageBean bean = landPageService.selectLandPage(id);
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
	@RequestMapping(value = "/landpage/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectLandPages(@RequestParam(required = false) String name, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Page<Object> pager = null;
        if (pageNo != null && pageSize != null){
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        
		List<LandPageBean> beans = landPageService.selectLandPages(name);
		
		PaginationResult result = new PaginationResult(beans, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}
}
