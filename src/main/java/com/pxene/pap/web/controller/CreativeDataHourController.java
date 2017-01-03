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
import com.pxene.pap.domain.beans.CreativeDataHourBean;
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.service.CreativeDataHourService;

/**
 * 创意数据-小时
 * @author ningyu
 */
@Controller
public class CreativeDataHourController
{
    @Autowired
    private CreativeDataHourService creativeDataHourService;
    
    
    /**
     * 添加创意数据。
     * @param creativeDataHour 按小时计创意数据对象    
     * @param response
     * @return
     */
    @RequestMapping(value = "/dataHour/creative", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Void> addCreativeDataHour(@Valid @RequestBody CreativeDataHourBean creativeDataHour, HttpServletResponse response) throws Exception
    {
        creativeDataHourService.saveCreativeDataHour(creativeDataHour);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
    
    
    /**
     * 根据ID编辑指定的创意数据（全部更新）。
     * @param id        创意数据ID
     * @param response
     * @return
     */
    @RequestMapping(value = "/dataHour/creative/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void updateCreativeDataHour(@PathVariable Integer id, @Valid @RequestBody CreativeDataHourBean creativeDataHour, HttpServletResponse response) throws Exception
    {
        creativeDataHourService.updateCreativeDataHour(id, creativeDataHour);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
    
    
    /**
     * 列出创意数据。
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/dataHour/creatives", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listCreativeDataHours(@RequestParam(required = true) String campaignId, @RequestParam(required = false) long beginTime, @RequestParam(required = false) long endTime, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Page<Object> pager = null;
        if (pageNo != null && pageSize != null)
        {
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        
        List<CreativeDataHourBean> creativeDataHourList = creativeDataHourService.listCreativeDataHour(campaignId, beginTime, endTime);
        
        PaginationBean result = new PaginationBean(creativeDataHourList, pager);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
}
