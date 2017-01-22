package com.pxene.pap.web.controller;

import java.util.List;
import java.util.Map;

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
import com.pxene.pap.service.LandpageRuleService;
@Controller
public class LandpageRuleController {

	@Autowired
	private LandpageRuleService landpageRuleService;
	
	@RequestMapping(value = "/rule/landpage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String addLandpageRule(@Valid @RequestBody RuleBean rule, HttpServletResponse response) throws Exception {
		landpageRuleService.saveLandpageRule(rule);
		return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), "id", rule.getId(), response);
	}
	
	@RequestMapping(value = "/rule/landpage/{id}", method = RequestMethod.DELETE)
    @ResponseBody
	public void deleteLandpageRule(@PathVariable String id, HttpServletResponse response) throws Exception {
		landpageRuleService.deleteLandpageRule(id);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	@RequestMapping(value = "/rule/landpage/{id}", method = RequestMethod.PUT)
    @ResponseBody
	public void updateLandpageRule(@PathVariable String id, @Valid @RequestBody RuleBean rule, HttpServletResponse response) throws Exception {
		landpageRuleService.updateLandpageRule(id, rule);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	@RequestMapping(value = "/rule/landpage/status/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public void updateLandpageRuleStatus(@PathVariable String id, @RequestBody Map<String, String> map, HttpServletResponse response) throws Exception {
		landpageRuleService.updateLanpageRuleStatus(id, map);
		response.setStatus(HttpStatus.NO_CONTENT.value());	
	}
	
	@RequestMapping(value = "/rule/landpage/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String getLandpageRule(@PathVariable String id, HttpServletResponse response) throws Exception {
		RuleBean ruleBean = landpageRuleService.findLandpageRuleById(id);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), ruleBean, response);
	}
	
	@RequestMapping(value = "/rule/landpages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String listLandpageRules(@RequestParam(required = false) String name, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletResponse response) throws Exception {
		Page<Object> pager = null;
        if (pageNo != null && pageSize != null){
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        
		List<RuleBean> beans = landpageRuleService.listLandpageRule(name);
		
		PaginationBean result = new PaginationBean(beans, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}
	
}
