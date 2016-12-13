package com.pxene.pap.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.constant.HttpStatusCode;
import com.pxene.pap.domain.beans.PurposeBean;
import com.pxene.pap.service.PurposeService;

@Controller
public class PurposeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PurposeController.class);
	
	@Autowired
	private PurposeService purposeService;
	
	@RequestMapping(value = "/purpose", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String createProject(@RequestBody PurposeBean bean, HttpServletResponse response){
		String str;
		try {
			str = purposeService.createPurpose(bean);
			return ResponseUtils.sendReponse(LOGGER, HttpStatusCode.OK, "id", str, response);
		} catch (Exception e) {
			return ResponseUtils.sendHttp500(LOGGER, response);
		}
	}
	
	@RequestMapping(value = "/purpose", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String updateProject(@RequestBody PurposeBean bean, HttpServletResponse response){
		String str;
		try {
			str = purposeService.updatePurpose(bean);
			return ResponseUtils.sendReponse(LOGGER, HttpStatusCode.OK, "id", str, response);
		} catch (Exception e) {
			return ResponseUtils.sendHttp500(LOGGER, response);
		}
	}
	
	@RequestMapping(value="/purpose",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String deleteProject(@RequestBody PurposeBean bean, HttpServletResponse response){
		try {
			purposeService.deletePurpose(bean);
			return ResponseUtils.sendReponse(LOGGER, HttpStatusCode.OK, "执行完毕", response);
		} catch (Exception e) {
			return ResponseUtils.sendHttp500(LOGGER, response);
		}
	}
}
