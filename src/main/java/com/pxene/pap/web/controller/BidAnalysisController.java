package com.pxene.pap.web.controller;

import com.github.pagehelper.Page;
import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.BidAnalyseBean;
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.domain.beans.ResponseData;
import com.pxene.pap.service.BidAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 不出价
 * Created by wangshuai on 2017/7/27.
 */
@Controller
public class BidAnalysisController {

    @Autowired
    private BidAnalysisService nobidService;
    
    /**
     * dsp同步接口--根据活动id获取活动名称|项目id|项目名称|项目code
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/odin/campaign/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getCampaignsInfoById(@PathVariable String id, HttpServletResponse response) throws Exception {

        String campaignsInfo = nobidService.getCampaignsInfoById(id);

        return ResponseUtils.sendReponse(HttpStatus.OK.value(), "value", campaignsInfo, response);
    }

    /**
     * dsp同步接口--批量获取pap活动时间在当前时间段的活动id及其活动名称|项目id|项目名称|项目code
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/odin/campaigns", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listCampaignsInfos(HttpServletResponse response) throws Exception {
        Page<Object> pager = null;
        List<ResponseData> datas = nobidService.listCampaignsInfos();
        PaginationBean result = new PaginationBean(datas, pager);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }

    /**
     * dsp同步接口---根据创意id获取素材大小
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/odin/size/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getImageSizeByCreativeId(@PathVariable String id, HttpServletResponse response) throws Exception {

        String imageSize = nobidService.getImageSizeByCreativeId(id);

        return ResponseUtils.sendReponse(HttpStatus.OK.value(), "value", imageSize, response);
    }

    /**
     * dsp同步接口---获取所有投放时间在当前时刻的素材大小
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/odin/sizes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listImageSizes(HttpServletResponse response) throws Exception {

        Page<Object> pager = null;
        List<ResponseData> datas = nobidService.listImageSizes();
        PaginationBean result = new PaginationBean(datas, pager);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }

    /**
     * dsp同步接口--根据创意id获取对应的素材类型,并转化为dsp的广告类型
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/odin/material/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getMaterialByCreativeId(@PathVariable String id, HttpServletResponse response) throws Exception {

        String type = nobidService.getMaterialByCreativeId(id);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), "value", type, response);
    }

    /**
     * dsp同步接口--获取投放时间在当前时间的所有创意的素材类型
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/odin/materials", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listMaterials(HttpServletResponse response) throws Exception {

        Page<Object> pager = null;
        List<ResponseData> datas = nobidService.listMaterials();
        PaginationBean result = new PaginationBean(datas, pager);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }

    /**
     * 根据创意ID查询活动ID
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/odin/campaignId/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getCampaignIdByCreativeId(@PathVariable String id, HttpServletResponse response) throws Exception {

        String campaignsInfo = nobidService.getCampaignIdByCreativeId(id);

        return ResponseUtils.sendReponse(HttpStatus.OK.value(), "value", campaignsInfo, response);
    }


    /**
     * 批量查询活动ID
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/odin/campaignIds", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listCampaignIds(HttpServletResponse response) throws Exception {
        Page<Object> pager = null;
        List<ResponseData> datas = nobidService.listCampaignIds();
        PaginationBean result = new PaginationBean(datas, pager);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
    
    
    /**
     * 不出价原因列表
     * @param bidAnalyseBean
     * @param pageNo
     * @param pageSize
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/analysis/nobids", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String queryNobidReason(BidAnalyseBean bidAnalyseBean, @RequestParam(required = true) Integer pageNo, @RequestParam(required = true) Integer pageSize,HttpServletResponse response) throws Exception {
        Page<Object> pager = new Page<Object>();
        pager.setPageNum(pageNo);
        pager.setPageSize(pageSize);
        PaginationBean result = nobidService.queryNobidReason(bidAnalyseBean, pager);
        if (result == null) {
            pager.setTotal(0);
            result = new PaginationBean(new ArrayList<>(), pager);
        }
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }

}
