package com.pxene.pap.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.pxene.pap.domain.beans.AppFlowHourBean;
import com.pxene.pap.service.AppFlowHourService;

/**
 * APP流量-小时
 * @author ningyu
 */
@Controller
public class AppFlowHourController
{
    @Autowired
    private AppFlowHourService appFlowHourService;
    
    
    /**
     * 添加App流量。
     * @param appFlowHour 按小时计App流量对象    
     * @param response
     * @return
     */
    @RequestMapping(value = "/flowHour/app", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Void> addAppFlowHour(@Valid @RequestBody AppFlowHourBean appFlowHour, HttpServletResponse response) throws Exception
    {
        appFlowHourService.saveAppFlowHour(appFlowHour);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
    
    
    /**
     * 列出App流量。
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/flowHour/apps", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listAppFlowHours(@RequestParam(required = true) Date beginTime, @RequestParam(required = true) Date endTime, @RequestParam(required = true) int limitNum, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        List<Map<String, Object>> appFlowHourList = appFlowHourService.listAppFlowHour(beginTime, endTime, limitNum);
        
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), appFlowHourList, response);
    }
    
    @InitBinder(value = {"beginTime", "endTime"})
    protected void initDateTimeBind(WebDataBinder binder)
    {
        RequestUtils.inDateTimeBind(binder);
    }
}
