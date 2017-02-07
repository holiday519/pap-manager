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
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.domain.beans.RegionFlowBean;
import com.pxene.pap.service.RegionFlowService;

/**
 * 地域流量-小时
 * @author ningyu
 */
@Controller
public class RegionFlowController
{
    @Autowired
    private RegionFlowService regionFlowService;
    
    /**
     * 列出地域流量。
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/flow/regions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listRegionFlows(@RequestParam(required = true) Date beginTime, @RequestParam(required = true) Date endTime, @RequestParam(required = true) int limitNum, HttpServletResponse response) throws Exception
    {
    	Page<Object> pager = null;
    	
        List<RegionFlowBean> regionFlowHourList = regionFlowService.listRegionFlows(beginTime, endTime, limitNum);
        
        PaginationBean result = new PaginationBean(regionFlowHourList, pager);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
    
    @InitBinder(value = {"beginTime", "endTime"})
    protected void initDateTimeBind(WebDataBinder binder)
    {
        RequestUtils.inDateTimeBind(binder);
    }
}
