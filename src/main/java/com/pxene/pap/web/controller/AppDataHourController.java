package com.pxene.pap.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.pxene.pap.domain.beans.AppDataHourBean;
import com.pxene.pap.domain.beans.DayAndHourDataBean;
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.service.AppDataHourService;

/**
 * APP数据-小时
 * @author ningyu
 */
@Controller
public class AppDataHourController
{
    @Autowired
    private AppDataHourService appDataHourService;
    
    
    /**
     * 添加App数据。
     * @param appDataHour 按小时计App数据对象    
     * @param response
     * @return
     */
    @RequestMapping(value = "/dataHour/app", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Void> addAppDataHour(@Valid @RequestBody AppDataHourBean appDataHour, HttpServletResponse response) throws Exception
    {
        appDataHourService.saveAppDataHour(appDataHour);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
    
    
    /**
     * 根据ID编辑指定的App数据（全部更新）。
     * @param id        App数据ID
     * @param response
     * @return
     */
    @RequestMapping(value = "/dataHour/app/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void updateAppDataHour(@PathVariable Integer id, @Valid @RequestBody AppDataHourBean appDataHour, HttpServletResponse response) throws Exception
    {
        appDataHourService.updateAppDataHour(id, appDataHour);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
    
    
    /**
     * 列出App数据。
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/dataHour/apps", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listAppDataHours(@RequestParam(required = true) String campaignId, @RequestParam(required = true) long beginTime, @RequestParam(required = true) long endTime, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Page<Object> pager = null;
        if (pageNo != null && pageSize != null)
        {
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        
        List<DayAndHourDataBean> appDataHourList = appDataHourService.listAppDataHours(campaignId, beginTime, endTime);
        
        PaginationBean result = new PaginationBean(appDataHourList, pager);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
}
