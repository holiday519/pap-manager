package com.pxene.pap.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.pxene.pap.domain.model.basic.AdvertiserModel;
import com.pxene.pap.domain.model.custom.PaginationResult;
import com.pxene.pap.service.AdvertiserService;

@Controller
public class AdvertiserController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserController.class);
    
    @Autowired
    private AdvertiserService advertiserService;
    
    
    /**
     * 添加广告主。
     * @param advertiser
     * @param response
     * @return
     */
    @RequestMapping(value = "/advertisers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String addAdvertiser(@RequestBody AdvertiserModel advertiser, HttpServletResponse response)
    {
        LOGGER.debug("Received body params Advertiser {}.", advertiser);
        
        int affectedRows = advertiserService.saveAdvertiser(advertiser);
        
        if (affectedRows > 0)
        {
            return ResponseUtils.sendReponse(LOGGER, HttpStatusCode.CREATED, "id", advertiser.getId(), response);
        }
        
        return ResponseUtils.sendHttp500(LOGGER, response);
    }
    
    
    /**
     * 删除广告主。
     * @param id
     * @param response
     * @return
     */
    @RequestMapping(value = "/advertisers/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteAdvertiser(@PathVariable String id, HttpServletResponse response)
    {
        LOGGER.debug("Received path params id {}.", id);
        
        int affectedRows;
        try
        {
            affectedRows = advertiserService.deleteAdvertiser(id);
            if (affectedRows > 0)
            {
                response.setStatus(HttpStatusCode.NO_CONTENT);
                return;
            }
            else
            {
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.getWriter().write(ResponseUtils.sendReponse(LOGGER, HttpStatusCode.INTERNAL_SERVER_ERROR, "删除失败", response));
            }
        }
        catch (Exception ex)
        {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            try
            {
                response.getWriter().write(ResponseUtils.sendReponse(LOGGER, HttpStatusCode.INTERNAL_SERVER_ERROR, ex.getMessage(), response));
            }
            catch (IOException e)
            {
                LOGGER.error(e.toString());
                e.printStackTrace();
            }
        }
        
    }
    
    
    /**
     * 根据ID编辑指定的广告主。
     * @param id
     * @param response
     * @return
     */
    @RequestMapping(value = "/advertisers/{id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String updateAdvertiser(@PathVariable String id, HttpServletResponse response)
    {
        LOGGER.debug("Received path params id {}.", id);
        
        return null;
    }
    
    
    /**
     * 根据ID查询指定的广告主。
     * @param id
     * @param response
     * @return
     */
    @RequestMapping(value = "/advertisers/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getAdvertiser(@PathVariable String id, HttpServletResponse response)
    {
        LOGGER.debug("Received path params id {}.", id);
        
        AdvertiserModel advertiser = advertiserService.findAdvertiserById(id);
        
        if (advertiser != null)
        {
            return ResponseUtils.sendReponse(LOGGER, HttpStatusCode.OK, advertiser, response);
        }
        else
        {
            return ResponseUtils.sendReponse(LOGGER, HttpStatusCode.NOT_FOUND, "找不到指定的广告主", response);
        }
    }
    
    
    /**
     * 列出广告主。
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/advertisers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listAdvertisers(@RequestParam(required = false) String name, @RequestParam(required = false) Integer pageNO, @RequestParam(required = false) Integer pageSize, HttpServletRequest request, HttpServletResponse response)
    {
        LOGGER.debug("Received path params pageNO {}.", pageNO);
        LOGGER.debug("Received path params pageSize {}.", pageSize);
        
        Page<Object> pager = null;
        if (pageNO != null && pageSize != null)
        {
            pager = PageHelper.startPage(pageNO, pageSize);
        }
        
        List<AdvertiserModel> advertisers = advertiserService.listAdvertisers(name);
        
        if (advertisers != null && advertisers.size() > 0)
        {
            PaginationResult result = new PaginationResult(advertisers, pager);
            return ResponseUtils.sendReponse(LOGGER, HttpStatusCode.OK, result, response);
        }
        else
        {
            return ResponseUtils.sendReponse(LOGGER, HttpStatusCode.NOT_FOUND, "找不到指定的广告主", response);
        }
    }
}
