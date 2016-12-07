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
import com.pxene.pap.domain.beans.CreativeBean;
import com.pxene.pap.service.CreativeService;

@Controller
public class CreativeController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SysUserController.class);
	
	@Autowired
	private CreativeService creativeService;
	
	@RequestMapping(value="/creative",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String createProject(@RequestBody CreativeBean bean, HttpServletResponse response){
		String str = creativeService.createCreative(bean);
		System.out.println(str);
		return ResponseUtils.sendReponse(LOGGER, HttpStatusCode.OK, str, response);
	}

}
