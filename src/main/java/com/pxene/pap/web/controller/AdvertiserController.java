package com.pxene.pap.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.AdvertiserBean;
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.service.AdvertiserService;

@Controller
public class AdvertiserController
{
    @Autowired
    private AdvertiserService advertiserService;
    
    /**
     * 添加广告主
     * @param advertiser    广告主DTO
     * @param response
     * @return
     */
    @RequestMapping(value = "/advertiser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String createAdvertiser(@Valid @RequestBody AdvertiserBean advertiser, HttpServletResponse response) throws Exception
    {
        advertiserService.createAdvertiser(advertiser);
        return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), "id", advertiser.getId(), response);
    }
    
    /**
     * 删除广告主
     * @param id        广告主ID
     * @param response
     * @return
     */
    @RequestMapping(value = "/advertiser/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteAdvertiser(@PathVariable String id, HttpServletResponse response) throws Exception
    {
        advertiserService.deleteAdvertiser(id);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
    
    /**
     * 批量删除广告主
     * @param ids
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/advertisers", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteAdvertisers(@RequestParam(required = true) String ids, HttpServletResponse response) throws Exception
    {
        advertiserService.deleteAdvertisers(ids.split(","));
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
    
    /**
     * 根据ID编辑指定的广告主（全部更新）
     * @param id        广告主ID
     * @param response
     * @return
     */
    @RequestMapping(value = "/advertiser/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void updateAdvertiser(@PathVariable String id, @Valid @RequestBody AdvertiserBean advertiser, HttpServletResponse response) throws Exception
    {
        advertiserService.updateAdvertiser(id, advertiser);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
    
    
    /**
     * 根据ID编辑指定的广告主（部分更新）
     * @param id            广告主ID
     * @param advertiser    广告主DTO
     * @param response
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/advertiser/{id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ResponseBody
//    public String patchUpdateAdvertiser(@PathVariable String id, @RequestBody AdvertiserBean advertiser, HttpServletResponse response) throws Exception
//    {
//        advertiserService.patchUpdateAdvertiser(id, advertiser);
//        return ResponseUtils.sendReponse(HttpStatusCode.OK, advertiser, response);
//    }
    
    
    /**
     * 根据ID查询指定的广告主
     * @param id        广告主ID
     * @param response
     * @return
     */
    @RequestMapping(value = "/advertiser/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getAdvertiser(@PathVariable String id, HttpServletResponse response) throws Exception
    {
        AdvertiserBean advertiser = advertiserService.getAdvertiser(id);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), advertiser, response);
    }
    
    
    /**
     * 列出广告主
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/advertisers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listAdvertisers(@RequestParam(required = false) String name, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletResponse response) throws Exception
    {
        Page<Object> pager = null;
        if (pageNo != null && pageSize != null)
        {
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        
        List<AdvertiserBean> advertisers = advertiserService.listAdvertisers(name, startDate, endDate);
        
        PaginationBean result = new PaginationBean(advertisers, pager);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
    
    /**
     * 上传广告主资质图片
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/advertiser/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String uploadQualification(@RequestPart(value = "file", required = true) MultipartFile file, HttpServletResponse response) throws Exception {
    	String path = advertiserService.uploadQualification(file);
    	return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), "path", path, response);
    }
    
    /**
     * 上传广告主资质图片(logo)
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/advertiser/upload/logo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String uploadQualification4Logo(@RequestPart(value = "file", required = true) MultipartFile file, HttpServletResponse response) throws Exception {
    	String path = advertiserService.uploadQualification4Logo(file);
    	return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), "path", path, response);
    }
    
    /**
     * 广告主提交第三方审核
     * @param id 广告主ADX审核id
     * @param adxId ADX的id
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/advertiser/audit/{auditId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void auditAdvertiser(@PathVariable String auditId, HttpServletResponse response) throws Exception {
    	advertiserService.auditAdvertiser(auditId);
    	response.setStatus(HttpStatus.NO_CONTENT.value());
    }
    
    /**
     * 同步广告主第三方审核结果
     * @param id 广告主ADX审核id
     * @param adxId ADX的id
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/advertiser/synchronize/{auditId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void synchronizeAdvertiser(@PathVariable String auditId, HttpServletResponse response) throws Exception {
    	advertiserService.synchronizeAdvertiser(auditId);
    	response.setStatus(HttpStatus.NO_CONTENT.value());
    }
    
    /**
     * 广告主启用/禁用ADX
     * @param auditId 广告主ADX审核的id
     * @param adxId ADX的ID
     * @param map 广告主ADX的状态
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/advertisers/adx/{auditId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void updateAdvertiserAdxEnabled(@PathVariable String auditId, @RequestBody Map<String, String> map, HttpServletResponse response) throws Exception {
    	advertiserService.updateAdvertiserAdxEnabled(auditId, map);
    	response.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
