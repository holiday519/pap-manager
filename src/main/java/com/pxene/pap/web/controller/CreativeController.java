package com.pxene.pap.web.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.constant.HttpStatusCode;
import com.pxene.pap.domain.beans.CreativeBean;
import com.pxene.pap.service.CreativeService;

@Controller
public class CreativeController {
	
	@Autowired
	private CreativeService creativeService;
	
	@RequestMapping(value = "/creative", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String createProject(@Valid @RequestBody CreativeBean bean, HttpServletResponse response) throws Exception {
		creativeService.createCreative(bean);
		return ResponseUtils.sendReponse(HttpStatusCode.OK, "id", bean.getId(), response);
	}
	
	@RequestMapping(value = "/creative/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String updateProject(@PathVariable String id, @RequestBody CreativeBean bean, HttpServletResponse response) throws Exception {
		creativeService.updateCreative(id, bean);
		return ResponseUtils.sendReponse(HttpStatusCode.OK, bean, response);
	}
	
	@RequestMapping(value = "/creative/{id}",method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteProject(@PathVariable String id, HttpServletResponse response) throws Exception {
		creativeService.deleteCreative(id);
		response.setStatus(HttpStatusCode.NO_CONTENT);
	}

}
