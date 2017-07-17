package com.pxene.pap.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.pxene.pap.domain.beans.CampaignBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.AppBean;
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.service.AppService;

@Controller
public class AppController {

	@Autowired
	private AppService appService;
	
	@RequestMapping(value = "/apps/{campaignId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String listApps(@RequestParam(required = false) String name, @PathVariable(required = false) String campaignId, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletResponse response) throws Exception {
		Page<Object> pager = null;
        if (pageNo != null && pageSize != null){
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        
		List<AppBean> selectApps = appService.listApps(name, campaignId);
		
		PaginationBean result = new PaginationBean(selectApps, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}

	@RequestMapping(value = "/app", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String listApps(@Valid @RequestBody CampaignBean.Target target, HttpServletResponse response) throws Exception {
		int amount = appService.getAppNumsByQueryCondition(target);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), "amount", String.valueOf(amount), response);
	}

	@RequestMapping(value = "/app/synAppInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String synAppInfo(HttpServletResponse response) throws Exception {

		boolean result = appService.synAppInfo();

		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result,response);
	}
}
