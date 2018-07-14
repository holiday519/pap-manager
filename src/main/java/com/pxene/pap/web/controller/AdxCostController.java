package com.pxene.pap.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.AdxCostBean;
import com.pxene.pap.domain.beans.AdxCostData;
import com.pxene.pap.service.AdxCostService;

@Controller
public class AdxCostController
{
    @Autowired
    private AdxCostService adxCostService;
    
    
    /**
     * 录入修正比。
     * @param adxCost   位于HTTP请求体中的请求参数，包含项开始日期、结束日期、AdxID、修正比。
     * @param response
     */
    @RequestMapping(value = "/adxcost", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void createAdxCost(@RequestBody AdxCostBean adxCost, HttpServletResponse response)
    {
        adxCostService.create(adxCost);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
    
    /**
     * 查询所有项目数据。
     * @param date  日期
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/adxcost/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listProjects(@PathVariable String date, HttpServletResponse response) throws Exception
    {
        List<AdxCostData> beans = adxCostService.listProjectsData(date);
        Map<String, List<AdxCostData>> o = new HashMap<>();
        o.put("items", beans);
        
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), o, response);
    }
    
    
    @RequestMapping(value = "/adxcost/project", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getProjectNames(@RequestParam String[] codes, HttpServletResponse response) throws Exception
    {
        List<Map<String, String>> projectNames = adxCostService.getProjectNames(codes);
        Map<String, List<Map<String, String>>> result = new HashMap<String, List<Map<String, String>>>();
        result.put("items", projectNames);
        
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
}
