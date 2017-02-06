package com.pxene.pap.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.LandpageDataBean;
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.service.LandpageDataService;

/**
 * 落地页数据-小时
 * @author ningyu
 */
@Controller
public class LandpageDataController
{
    @Autowired
    private LandpageDataService landpageDataHourService;
    
    /**
     * 列出落地页数据。
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/data/landpages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listLandpageDatas(@RequestParam(required = true) String campaignId, @RequestParam(required = false) long beginTime, @RequestParam(required = false) long endTime, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletResponse response) throws Exception
    {
        Page<Object> pager = null;
        if (pageNo != null && pageSize != null)
        {
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        
        List<LandpageDataBean> landpageDataHourList = landpageDataHourService.listLandpageDatas(campaignId, beginTime, endTime);
        
        PaginationBean result = new PaginationBean(landpageDataHourList, pager);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
}
