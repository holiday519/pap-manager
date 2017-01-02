package com.pxene.pap.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.domain.beans.RuleBean;

public class TimeRuleController {

	@RequestMapping(value = "/rule/time", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String addTimeRule(@Valid @RequestBody RuleBean rule, HttpServletResponse response) throws Exception {
		return "";
	}
	
	@RequestMapping(value = "/rule/time/{id}", method = RequestMethod.DELETE)
    @ResponseBody
	public void deleteTimeRule(@PathVariable String id, HttpServletResponse response) throws Exception {
		
	}
	
	@RequestMapping(value = "/rule/time/{id}", method = RequestMethod.PUT)
    @ResponseBody
	public void updateTimeRule(@PathVariable String id, @Valid @RequestBody RuleBean rule, HttpServletResponse response) throws Exception {
		
	}
	
	@RequestMapping(value = "/rule/time/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String getTimeRule(@PathVariable String id, HttpServletResponse response) throws Exception {
		return "";
	}
	
	@RequestMapping(value = "/rule/times", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String listTimeRules(@RequestParam(required = false) String name, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "";
	}
	
}
