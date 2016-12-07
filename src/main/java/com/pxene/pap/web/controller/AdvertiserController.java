package com.pxene.pap.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.constant.HttpStatusCode;
import com.pxene.pap.domain.beans.AdvertiserBean;
import com.pxene.pap.service.AdvertiserService;

@Controller
public class AdvertiserController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserController.class);
    
    @Autowired
    private AdvertiserService advertiserService;
    
    
    @RequestMapping(value = "/advertiser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String addAdvertiser(@RequestBody AdvertiserBean advertiser, HttpServletResponse response)
    {
        LOGGER.debug("Received body params Advertiser {}.", advertiser);
        
        int affectedRows = advertiserService.saveAdvertiser(advertiser);
        
        if (affectedRows > 0)
        {
            return ResponseUtils.sendReponse(LOGGER, HttpStatusCode.CREATED, advertiser, response);
        }
        
        return ResponseUtils.sendHttp500(LOGGER, response);
    }
}
