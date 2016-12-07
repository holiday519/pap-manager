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
import com.pxene.pap.domain.beans.ProjectBean;
import com.pxene.pap.service.ProjectService;

@Controller
public class ProjectController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SysUserController.class);
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value="/project",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String createProject(@RequestBody ProjectBean bean, HttpServletResponse response){
		String str = projectService.createProject(bean);
		System.out.println(str);
		return ResponseUtils.sendReponse(LOGGER, HttpStatusCode.OK, str, response);
	}
}
