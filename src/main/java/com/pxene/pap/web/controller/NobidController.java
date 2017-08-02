package com.pxene.pap.web.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.BidAnalyseBean;
import com.pxene.pap.domain.beans.NobidReasonBean;
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.domain.beans.ResponseData;
import com.pxene.pap.service.NobidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 不出价
 * Created by wangshuai on 2017/7/27.
 */
@Controller
public class NobidController {

    @Autowired
    private NobidService nobidService;

    /**
     * dsp同步接口--根据活动id获取活动名称|项目id|项目名称|项目code
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/nobid/campaign/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getDspCampaignsInfoById(@PathVariable String id, HttpServletResponse response) throws Exception {

        String campaignsInfo = nobidService.getCampaignAndValuesBycampaignId(id);

        return ResponseUtils.sendReponse(HttpStatus.OK.value(), "value", campaignsInfo, response);
    }

    /**
     * dsp同步接口--批量获取pap活动时间在当前时间段的活动id及其活动名称|项目id|项目名称|项目code
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/nobid/campaigns", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String synAllDspCampaignsInfo(HttpServletResponse response) throws Exception {
        Page<Object> pager = null;
        List<ResponseData> datas = nobidService.getAllCampaignAndValues();
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
    @RequestMapping(value = "/nobid/size/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getImageSizeByCreativeId(@PathVariable String id, HttpServletResponse response) throws Exception {

        String imageSize = nobidService.getCreativeImageSizeByCreativeId(id);

        return ResponseUtils.sendReponse(HttpStatus.OK.value(), "value", imageSize, response);
    }

    /**
     * dsp同步接口---获取所有投放时间在当前时刻的素材大小
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/nobid/sizes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getImageSizes(HttpServletResponse response) throws Exception {

        Page<Object> pager = null;
        List<ResponseData> datas = nobidService.getCreativeImageSizes();
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
    @RequestMapping(value = "/nobid/material/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getMaterialByCreativeId(@PathVariable String id, HttpServletResponse response) throws Exception {

        String type = nobidService.getMaterialTypeByCreativeId(id);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), "value", type, response);
    }

    /**
     * dsp同步接口--获取投放时间在当前时间的所有创意的素材类型
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/nobid/materials", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getMaterials(HttpServletResponse response) throws Exception {

        Page<Object> pager = null;
        List<ResponseData> datas = nobidService.getMaterialTypes();
        PaginationBean result = new PaginationBean(datas, pager);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }

    @RequestMapping(value = "/nobid/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
//    public void queryNobidReason(@RequestParam(required = false) long startDate, @RequestParam(required = false) long endDate,
//                                 @RequestParam(required = false) long date, @RequestParam(required = true) String projectId ,
//                                 @RequestParam(required = false) String campaignId ,@RequestParam(required = false) String adx ,
//                                 @RequestParam(required = false) String materialType ,@RequestParam(required = false) String materialSize ,
//                                 @RequestParam(required = false) String type ,@RequestParam(required = false) String sortType ,
//                                 HttpServletResponse response) throws Exception {

    public String queryNobidReason(BidAnalyseBean bidAnalyseBean,@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize,HttpServletResponse response) throws Exception {
        Page<Object> pager = null;
        if (pageNo != null && pageSize != null){
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        List<NobidReasonBean> datas = nobidService.queryNobidReason(bidAnalyseBean);
        PaginationBean result = new PaginationBean(datas, pager);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }


    @RequestMapping(value = "/nobid/imageSizes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listAllImageSizes(HttpServletResponse response) throws Exception {
        Page<Object> pager = null;

        List<Map<String,String>> datas = nobidService.listAllImageSizes();
        PaginationBean result = new PaginationBean(datas, pager);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
    }
}
