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
import com.pxene.pap.domain.beans.LandpageDataHourBean;
import com.pxene.pap.domain.beans.LandpageDataHourViewBean;
import com.pxene.pap.domain.model.custom.PaginationResult;
import com.pxene.pap.service.LandpageDataHourService;

/**
 * 落地页数据-小时
 * @author ningyu
 */
@Controller
public class LandpageDataHourController
{
    @Autowired
    private LandpageDataHourService landpageDataHourService;
    
    
    /**
     * 添加落地页数据。
     * @param landpageDataHourBean 按小时计落地页数据对象    
     * @param response
     * @return
     */
    @RequestMapping(value = "/landpageDataHour", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Void> addLandpageDataHour(@Valid @RequestBody LandpageDataHourBean landpageDataHourBean, HttpServletResponse response) throws Exception
    {
        landpageDataHourService.saveLandpageDataHour(landpageDataHourBean);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
    
    
    /**
     * 根据ID编辑指定的落地页数据（全部更新）。
     * @param id        落地页数据ID
     * @param response
     * @return
     */
    @RequestMapping(value = "/landpageDataHour/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void updateLandpageDataHour(@PathVariable Integer id, @Valid @RequestBody LandpageDataHourBean landpageDataHour, HttpServletResponse response) throws Exception
    {
        landpageDataHourService.updateLandpageDataHour(id, landpageDataHour);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
    
    
    /**
     * 列出落地页数据。
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/landpageDataHour", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listLandpageDataHour(@RequestParam(required = true) String campaignId, @RequestParam(required = false) long beginTime, @RequestParam(required = false) long endTime, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Page<Object> pager = null;
        if (pageNo != null && pageSize != null)
        {
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        
        List<LandpageDataHourViewBean> landpageDataHourList = landpageDataHourService.listLandpageDataHour(campaignId, beginTime, endTime);
        
        PaginationResult result = new PaginationResult(landpageDataHourList, pager);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
}
