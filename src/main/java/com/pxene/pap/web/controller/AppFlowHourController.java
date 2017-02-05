package com.pxene.pap.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.pxene.pap.common.RequestUtils;
import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.AppFlowBean;
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.service.AppFlowService;

/**
 * APP流量-小时
 * @author ningyu
 */
@Controller
public class AppFlowHourController
{
    @Autowired
    private AppFlowService appFlowHourService;
    
    /**
     * 列出App流量。
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/flow/apps", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listAppFlowHours(@RequestParam(required = true) Date beginTime, @RequestParam(required = true) Date endTime, @RequestParam(required = true) int limitNum, HttpServletResponse response) throws Exception
    {
    	Page<Object> pager = null;
    	
        List<AppFlowBean> appFlowHourList = appFlowHourService.listAppFlow(beginTime, endTime, limitNum);
        
        PaginationBean result = new PaginationBean(appFlowHourList, pager);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
    
    @InitBinder(value = {"beginTime", "endTime"})
    protected void initDateTimeBind(WebDataBinder binder)
    {
        RequestUtils.inDateTimeBind(binder);
    }
}
