package com.pxene.pap.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import com.pxene.pap.constant.HttpStatusCode;
import com.pxene.pap.domain.beans.AdvertiserBean;
import com.pxene.pap.domain.model.custom.PaginationResult;
import com.pxene.pap.service.AdvertiserService;

@Controller
public class AdvertiserController
{
    @Autowired
    private AdvertiserService advertiserService;
    
    
    /**
     * 添加广告主。
     * @param advertiser    广告主DTO
     * @param response
     * @return
     */
    @RequestMapping(value = "/advertiser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String addAdvertiser(@Valid @RequestBody AdvertiserBean advertiser, HttpServletResponse response) throws Exception
    {
        advertiserService.saveAdvertiser(advertiser);
        return ResponseUtils.sendReponse(HttpStatusCode.CREATED, "id", advertiser.getId(), response);
    }
    
    
    /**
     * 删除广告主。
     * @param id        广告主ID
     * @param response
     * @return
     */
    @RequestMapping(value = "/advertiser/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteAdvertiser(@PathVariable String id, HttpServletResponse response) throws Exception
    {
        advertiserService.deleteAdvertiser(id);
        response.setStatus(HttpStatusCode.NO_CONTENT);
    }
    
    
    /**
     * 根据ID编辑指定的广告主（全部更新）。
     * @param id        广告主ID
     * @param response
     * @return
     */
    @RequestMapping(value = "/advertiser/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String updateAdvertiser(@PathVariable String id, @Valid @RequestBody AdvertiserBean advertiser, HttpServletResponse response) throws Exception
    {
        advertiserService.updateAdvertiser(id, advertiser);
        
        return ResponseUtils.sendReponse(HttpStatusCode.OK, advertiser, response);
    }
    
    
    /**
     * 根据ID编辑指定的广告主（部分更新）。
     * @param id            广告主ID
     * @param advertiser    广告主DTO
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/advertiser/{id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String patchUpdateAdvertiser(@PathVariable String id, @RequestBody AdvertiserBean advertiser, HttpServletResponse response) throws Exception
    {
        advertiserService.patchUpdateAdvertiser(id, advertiser);
        
        return ResponseUtils.sendReponse(HttpStatusCode.OK, advertiser, response);
    }
    
    
    /**
     * 根据ID查询指定的广告主。
     * @param id        广告主ID
     * @param response
     * @return
     */
    @RequestMapping(value = "/advertiser/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getAdvertiser(@PathVariable String id, HttpServletResponse response) throws Exception
    {
        AdvertiserBean advertiser = advertiserService.findAdvertiserById(id);
        
        return ResponseUtils.sendReponse(HttpStatusCode.OK, advertiser, response);
    }
    
    
    /**
     * 列出广告主。
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/advertisers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listAdvertisers(@RequestParam(required = false) String name, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Page<Object> pager = null;
        if (pageNo != null && pageSize != null)
        {
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        
        List<AdvertiserBean> advertisers = advertiserService.listAdvertisers(name);
        
        PaginationResult result = new PaginationResult(advertisers, pager);
        return ResponseUtils.sendReponse(HttpStatusCode.OK, result, response);
    }
}
