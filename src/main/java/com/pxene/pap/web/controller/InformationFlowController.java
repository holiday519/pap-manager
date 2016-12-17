package com.pxene.pap.web.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.pxene.pap.domain.beans.InformationFlowBean;
import com.pxene.pap.service.InfomationFlowService;

@Controller
public class InformationFlowController {
	
	@Autowired
	private InfomationFlowService infomationFlowService;
	
	@RequestMapping(value = "/info", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String createInfoFlow(@Valid @RequestBody InformationFlowBean bean, HttpServletResponse response) throws Exception {
		infomationFlowService.createInformationFlow(bean);
		return ResponseUtils.sendReponse(HttpStatusCode.OK, "id",bean.getId(), response);
	}
	
	@RequestMapping(value = "/info/{id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String updateInfoFlow(@PathVariable String id, @RequestBody InformationFlowBean bean, HttpServletResponse response) throws Exception {
		infomationFlowService.updateInformationFlow(id, bean);
		return ResponseUtils.sendReponse(HttpStatusCode.OK, bean, response);
	}

}
