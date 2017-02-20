package com.pxene.pap.web.controller;

import java.util.List;

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
import com.pxene.pap.service.AuditAdvertiserBaiduService;

@Controller
public class AdvertiserController
{
    @Autowired
    private AdvertiserService advertiserService;
    
    @Autowired
    private AuditAdvertiserBaiduService auditAdvertiserBaiduService;
    
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
        return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), "id", advertiser.getId(), response);
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
    public void deleteAdvertisers(@RequestBody String[] ids, HttpServletResponse response) throws Exception
    {
        advertiserService.deleteAdvertisers(ids);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
    
    /**
     * 根据ID编辑指定的广告主（全部更新）。
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
     * 根据ID编辑指定的广告主（部分更新）。
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
//        
//        return ResponseUtils.sendReponse(HttpStatusCode.OK, advertiser, response);
//    }
    
    
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
        
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), advertiser, response);
    }
    
    
    /**
     * 列出广告主。
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/advertisers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listAdvertisers(@RequestParam(required = false) String name, @RequestParam(required = false) Long beginTime, @RequestParam(required = false) Long endTime, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletResponse response) throws Exception
    {
        Page<Object> pager = null;
        if (pageNo != null && pageSize != null)
        {
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        
        List<AdvertiserBean> advertisers = advertiserService.listAdvertisers(name, beginTime, endTime);
        
        PaginationBean result = new PaginationBean(advertisers, pager);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
    
    /**
     * 上传广告主资质图片。
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
     * 广告主提交第三方审核
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/advertiser/audit/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void auditAdvertiser(@PathVariable String id, HttpServletResponse response) throws Exception {
    	advertiserService.auditAdvertiser(id);
    	response.setStatus(HttpStatus.NO_CONTENT.value());
    }
    
    /**
     * 同步广告主第三方审核结果
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/advertiser/synchronize/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void synchronizeAdvertiser(@PathVariable String id, HttpServletResponse response) throws Exception {
    	advertiserService.synchronize(id);
    	response.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
