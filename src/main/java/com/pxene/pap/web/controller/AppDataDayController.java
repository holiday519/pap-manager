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
import com.pxene.pap.domain.beans.AppDataDayBean;
import com.pxene.pap.domain.model.custom.PaginationResult;
import com.pxene.pap.service.AppDataDayService;

/**
 * APP数据-天
 * @author ningyu
 */
@Controller
public class AppDataDayController
{
    @Autowired
    private AppDataDayService appDataDayService;
    
    
    /**
     * 添加App数据。
     * @param appDataDay 按天计App数据对象    
     * @param response
     * @return
     */
    @RequestMapping(value = "/appDataDay", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Void> addAppDataDay(@Valid @RequestBody AppDataDayBean appDataDay, HttpServletResponse response) throws Exception
    {
        appDataDayService.saveAppDataDay(appDataDay);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
    
    
    /**
     * 根据ID编辑指定的App数据（全部更新）。
     * @param id        App数据ID
     * @param response
     * @return
     */
    @RequestMapping(value = "/appDataDay/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void updateAppDataDay(@PathVariable Integer id, @Valid @RequestBody AppDataDayBean appDataDay, HttpServletResponse response) throws Exception
    {
        appDataDayService.updateAppDataDay(id, appDataDay);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
    
    
    /**
     * 根据ID查询指定的App数据。
     * @param id        App数据ID
     * @param response
     * @return
     */
    @RequestMapping(value = "/appDataDay/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getAppDataDay(@PathVariable Integer id, HttpServletResponse response) throws Exception
    {
        AppDataDayBean appDataDay = appDataDayService.findAppDataDayById(id);
        
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), appDataDay, response);
    }
    
    
    /**
     * 列出App数据。
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/appDataDay", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listAppDataDay(@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Page<Object> pager = null;
        if (pageNo != null && pageSize != null)
        {
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        
        List<AppDataDayBean> appDataDayList = appDataDayService.listAppDataDay();
        
        PaginationResult result = new PaginationResult(appDataDayList, pager);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
}
