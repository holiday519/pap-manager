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

public class LandpageRuleController {

	@RequestMapping(value = "/rule/landpage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String addLandpageRule(@Valid @RequestBody RuleBean rule, HttpServletResponse response) throws Exception {
		return "";
	}
	
	@RequestMapping(value = "/rule/landpage/{id}", method = RequestMethod.DELETE)
    @ResponseBody
	public void deleteLandpageRule(@PathVariable String id, HttpServletResponse response) throws Exception {
		
	}
	
	@RequestMapping(value = "/rule/landpage/{id}", method = RequestMethod.PUT)
    @ResponseBody
	public void updateLandpageRule(@PathVariable String id, @Valid @RequestBody RuleBean rule, HttpServletResponse response) throws Exception {
		
	}
	
	@RequestMapping(value = "/rule/landpage/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String getLandpageRule(@PathVariable String id, HttpServletResponse response) throws Exception {
		return "";
	}
	
	@RequestMapping(value = "/rule/landpages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String listLandpageRules(@RequestParam(required = false) String name, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "";
	}
	
}
