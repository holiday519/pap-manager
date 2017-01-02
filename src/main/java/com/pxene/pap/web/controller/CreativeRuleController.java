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

public class CreativeRuleController {

	@RequestMapping(value = "/rule/creative", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String addCreativeRule(@Valid @RequestBody RuleBean rule, HttpServletResponse response) throws Exception {
		return "";
	}
	
	@RequestMapping(value = "/rule/creative/{id}", method = RequestMethod.DELETE)
    @ResponseBody
	public void deleteCreativeRule(@PathVariable String id, HttpServletResponse response) throws Exception {
		
	}
	
	@RequestMapping(value = "/rule/creative/{id}", method = RequestMethod.PUT)
    @ResponseBody
	public void updateCreativeRule(@PathVariable String id, @Valid @RequestBody RuleBean rule, HttpServletResponse response) throws Exception {
		
	}
	
	@RequestMapping(value = "/rule/creative/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String getCreativeRule(@PathVariable String id, HttpServletResponse response) throws Exception {
		return "";
	}
	
	@RequestMapping(value = "/rule/creatives", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String listCreativeRules(@RequestParam(required = false) String name, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "";
	}
	
}
