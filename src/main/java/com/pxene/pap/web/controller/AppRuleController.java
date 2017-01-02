package com.pxene.pap.web.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.domain.beans.RuleBean;

public class AppRuleController {

	@RequestMapping(value = "/appRule", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String addAppRule(@Valid @RequestBody RuleBean rule, HttpServletResponse response) throws Exception {
		return "";
	}
	
	@RequestMapping(value = "/appRule/{id}", method = RequestMethod.DELETE)
    @ResponseBody
	public void deleteAppRule(@PathVariable String id, HttpServletResponse response) throws Exception {
		
	}
	
	@RequestMapping(value = "/appRule/{id}", method = RequestMethod.PUT)
    @ResponseBody
	public void updateAppRule(@PathVariable String id, @Valid @RequestBody RuleBean rule, HttpServletResponse response) throws Exception {
		
	}
	
}
