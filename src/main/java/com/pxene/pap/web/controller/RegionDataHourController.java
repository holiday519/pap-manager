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
import com.pxene.pap.domain.beans.RegionDataHourBean;
import com.pxene.pap.domain.beans.RegionDataHourViewBean;
import com.pxene.pap.domain.model.custom.PaginationResult;
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
    @RequestMapping(value = "/regionDataHour", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Void> addRegionDataHour(@Valid @RequestBody RegionDataHourBean regionDataHour, HttpServletResponse response) throws Exception
    {
        regionDataHourService.saveRegionDataHour(regionDataHour);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
    
    
    /**
     * 根据ID编辑指定的地域数据（全部更新）。
     * @param id        地域数据ID
     * @param response
     * @return
     */
    @RequestMapping(value = "/regionDataHour/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void updateRegionDataHour(@PathVariable Integer id, @Valid @RequestBody RegionDataHourBean regionDataHour, HttpServletResponse response) throws Exception
    {
        regionDataHourService.updateRegionDataHour(id, regionDataHour);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
    
    
    /**
     * 列出地域数据。
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/regionDataHour", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listRegionDataHour(@RequestParam(required = true) String campaignId, @RequestParam(required = false) long beginTime, @RequestParam(required = false) long endTime, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Page<Object> pager = null;
        if (pageNo != null && pageSize != null)
        {
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        
        List<RegionDataHourViewBean> regionDataHourList = regionDataHourService.listRegionDataHour(campaignId, beginTime, endTime);
        
        PaginationResult result = new PaginationResult(regionDataHourList, pager);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
}