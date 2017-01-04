package com.pxene.pap.web.controller;

import java.util.List;
import java.util.Map;

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
import com.pxene.pap.service.AppRuleService;
@Controller
public class AppRuleController {

	@Autowired
	private AppRuleService appRuleService;
	
	@RequestMapping(value = "/rule/app", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String addAppRule(@Valid @RequestBody RuleBean rule, HttpServletResponse response) throws Exception {
		appRuleService.saveAppRule(rule);
		return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), "id", rule.getId(), response);
	}
	
	@RequestMapping(value = "/rule/app/{id}", method = RequestMethod.DELETE)
    @ResponseBody
	public void deleteAppRule(@PathVariable String id, HttpServletResponse response) throws Exception {
		appRuleService.deleteAppRule(id);
		response.setStatus(HttpStatus.NO_CONTENT.value());	
	}
	
	@RequestMapping(value = "/rule/app/{id}", method = RequestMethod.PUT)
    @ResponseBody
	public void updateAppRule(@PathVariable String id, @Valid @RequestBody RuleBean rule, HttpServletResponse response) throws Exception {
		appRuleService.updateAppRule(id, rule);
		response.setStatus(HttpStatus.NO_CONTENT.value());	
	}
	
	@RequestMapping(value = "/rule/app/status/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public void updateAppRuleStatus(@PathVariable String id, @RequestBody Map<String, String> map, HttpServletResponse response) throws Exception {
		appRuleService.updateAppRuleStatus(id, map);
		response.setStatus(HttpStatus.NO_CONTENT.value());	
	}
	
	@RequestMapping(value = "/rule/app/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String getAppRule(@PathVariable String id, HttpServletResponse response) throws Exception {
		RuleBean ruleBean = appRuleService.findAppRuleById(id);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), ruleBean, response);
	}
	
	@RequestMapping(value = "/rule/apps", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String listAppRules(@RequestParam(required = false) String name, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Page<Object> pager = null;
        if (pageNo != null && pageSize != null){
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        
		List<RuleBean> beans = appRuleService.listAppRule(name);
		
		PaginationBean result = new PaginationBean(beans, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}
	
}
