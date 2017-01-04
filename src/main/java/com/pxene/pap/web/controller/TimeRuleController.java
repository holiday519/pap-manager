package com.pxene.pap.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.domain.beans.RuleBean;
import com.pxene.pap.service.TimeRuleService;
@Controller
public class TimeRuleController {

	@Autowired
	private TimeRuleService timeRuleService;
	
	@RequestMapping(value = "/rule/time", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String addTimeRule(@Valid @RequestBody RuleBean rule, HttpServletResponse response) throws Exception {
		timeRuleService.saveTimeRule(rule);
		return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), "id", rule.getId(), response);
	}
	
	@RequestMapping(value = "/rule/time/{id}", method = RequestMethod.DELETE)
    @ResponseBody
	public void deleteTimeRule(@PathVariable String id, HttpServletResponse response) throws Exception {
		timeRuleService.deleteTimeRule(id);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	@RequestMapping(value = "/rule/time/{id}", method = RequestMethod.PUT)
    @ResponseBody
	public void updateTimeRule(@PathVariable String id, @Valid @RequestBody RuleBean rule, HttpServletResponse response) throws Exception {
		timeRuleService.updateTimeRule(id, rule);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	@RequestMapping(value = "/rule/time/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String getTimeRule(@PathVariable String id, HttpServletResponse response) throws Exception {
		RuleBean ruleBean = timeRuleService.findTimeRuleById(id);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), ruleBean, response);
	}
	
	@RequestMapping(value = "/rule/times", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String listTimeRules(@RequestParam(required = false) String name, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Page<Object> pager = null;
        if (pageNo != null && pageSize != null){
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        
		List<RuleBean> beans = timeRuleService.listTimeRule(name);
		
		PaginationBean result = new PaginationBean(beans, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}
	
}