package com.pxene.pap.web.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.InfoFlowBean;
import com.pxene.pap.service.InfoFlowService;

@Controller
public class InfoFlowController {
	
	@Autowired
	private InfoFlowService infoFlowService;
	
	@RequestMapping(value="/infoflow",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String createInfoFlow(@Valid @RequestBody InfoFlowBean bean, HttpServletResponse response) throws Exception {
		infoFlowService.createInfoFlow(bean);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), "id",bean.getId(), response);
	}
	
	@RequestMapping(value="/infoflow/{id}",method = RequestMethod.PATCH,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void updateInfoFlow(@PathVariable String id, @RequestBody InfoFlowBean bean, HttpServletResponse response) throws Exception {
		infoFlowService.updateInfoFlow(id, bean);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}

}
