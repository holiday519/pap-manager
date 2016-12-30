package com.pxene.pap.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.common.RequestUtils;
import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.RegionFlowHourBean;
import com.pxene.pap.service.RegionFlowHourService;

/**
 * 地域流量-小时
 * @author ningyu
 */
@Controller
public class RegionFlowHourController
{
    @Autowired
    private RegionFlowHourService regionFlowHourService;
    
    
    /**
     * 添加地域流量。
     * @param regionFlowHour 按小时计地域流量对象    
     * @param response
     * @return
     */
    @RequestMapping(value = "/regionFlowHour", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Void> addRegionFlowHour(@Valid @RequestBody RegionFlowHourBean regionFlowHour, HttpServletResponse response) throws Exception
    {
        regionFlowHourService.saveRegionFlowHour(regionFlowHour);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
    
    
    /**
     * 列出地域流量。
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/regionFlowHour", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listRegionFlowHour(@RequestParam(required = true) Date beginTime, @RequestParam(required = true) Date endTime, @RequestParam(required = true) int limitNum, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        List<RegionFlowHourBean> regionFlowHourList = regionFlowHourService.listRegionFlowHour(beginTime, endTime, limitNum);
        
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), regionFlowHourList, response);
    }
    
    @InitBinder(value = {"beginTime", "endTime"})
    protected void initDateTimeBind(WebDataBinder binder)
    {
        RequestUtils.inDateTimeBind(binder);
    }
}
