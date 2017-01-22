package com.pxene.pap.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.DayAndHourDataBean;
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.domain.beans.RegionDataHourBean;
import com.pxene.pap.service.RegionDataHourService;

/**
 * 地域数据-小时
 * @author ningyu
 */
@Controller
public class RegionDataHourController
{
    @Autowired
    private RegionDataHourService regionDataHourService;
    
    
    /**
     * 添加地域数据。
     * @param regionDataHour 按小时计地域数据对象    
     * @param response
     * @return
     */
    @RequestMapping(value = "/dataHour/region", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Void> addRegionDataHour(@Valid @RequestBody RegionDataHourBean regionDataHour, HttpServletResponse response) throws Exception
    {
        regionDataHourService.saveRegionDataHour(regionDataHour);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
    
    
    /**
     * 列出地域数据。
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/dataHour/regions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listRegionDataHours(@RequestParam(required = true) String campaignId, @RequestParam(required = false) long beginTime, @RequestParam(required = false) long endTime, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletResponse response) throws Exception
    {
        Page<Object> pager = null;
        if (pageNo != null && pageSize != null)
        {
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        
        List<DayAndHourDataBean> regionDataHourList = regionDataHourService.listRegionDataHours(campaignId, beginTime, endTime);
        
        PaginationBean result = new PaginationBean(regionDataHourList, pager);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
}
