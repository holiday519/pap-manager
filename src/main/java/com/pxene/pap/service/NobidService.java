package com.pxene.pap.service;

import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.EsUtils;
import com.pxene.pap.common.RedisHelper;
import com.pxene.pap.constant.CodeTableConstant;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.BidAnalyseBean;
import com.pxene.pap.domain.beans.EsQueryBean;
import com.pxene.pap.domain.beans.NobidReasonBean;
import com.pxene.pap.domain.beans.ResponseData;
import com.pxene.pap.domain.models.*;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.repository.basic.*;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 不出价
 * Created by wangshuai on 2017/7/27.
 */
@Service
public class NobidService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NobidService.class);
    @Autowired
    private CampaignDao campaignDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ImageTmplDao imageTmplDao;

    @Autowired
    private InfoflowTmplDao infoflowTmplDao;

    @Autowired
    private CreativeService creativeService;
    @Autowired
    private CampaignService campaignService;

    @Autowired
    private CreativeDao creativeDao;

    @Autowired
    private EsUtils esUtils;


    /**
     * 根据活动的id获取活动名称|项目id|项目名称|项目编号
     * @param campaignId
     * @return
     */
    public String getCampaignAndValuesBycampaignId(String campaignId){
        String result ="";
        if(campaignId != null && !campaignId.isEmpty()){//查询单个活动相关信息
            CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
            ProjectModel projectModel = projectDao.selectByPrimaryKey(campaignModel.getProjectId());
            result = campaignModel.getName()+"|"+projectModel.getId()+"|"+projectModel.getName()+"|"+projectModel.getCode();
        }
        return result;
    }

    /**
     * 获取活动的id->活动名称|项目id|项目名称|项目编号
     * @return
     */
    public List<ResponseData> getAllCampaignAndValues(){
        List<ResponseData> result = new ArrayList<>();
        //查询在投的所有活动信息
        Date currentDate = new Date();
        CampaignModelExample campaignModelExample = new CampaignModelExample();
        campaignModelExample.createCriteria().andStartDateLessThanOrEqualTo(currentDate)
                .andEndDateGreaterThanOrEqualTo(currentDate);
        List<CampaignModel> campaignModels = campaignDao.selectByExample(campaignModelExample);
        if(campaignModels != null && !campaignModels.isEmpty()) {
            ResponseData data;
            for (CampaignModel campaignModel_temp : campaignModels) {
                ProjectModel projectModel = projectDao.selectByPrimaryKey(campaignModel_temp.getProjectId());
                String value = campaignModel_temp.getName() + "|" + projectModel.getId() + "|" + projectModel.getName() + "|" + projectModel.getCode();
                data = new ResponseData();
                data.setId(campaignModel_temp.getId());
                data.setValue(value);
                result.add(data);
            }
        }
        return result;
    }

    /**
     * 根据创意id获取素材的宽和高
     * @param creativeId
     * @return
     */
    public String getCreativeImageSizeByCreativeId(String creativeId){
        String size = "";
        if(creativeId != null && !creativeId.isEmpty()){
            CreativeModel creativeModel = creativeDao.selectByPrimaryKey(creativeId);
            size = getCreativeImageSizeByCreativeObject(creativeModel);
        }
        return size;
    }

    /**
     * 根据创意获取素材的宽和高
     * @param creativeModel
     * @return
     */
    public String getCreativeImageSizeByCreativeObject(CreativeModel creativeModel){
        String size = "";
        if(creativeModel !=null){
            //类型
            String type =creativeModel.getType();
            //模板id
            String tmpId = creativeModel.getTmplId();
            //判断是图片还是信息流
            if(CodeTableConstant.CREATIVE_TYPE_IMAGE.equals(type)){
                ImageTmplModel imageTmplModel = imageTmplDao.selectByPrimaryKey(tmpId);
                size = imageTmplModel.getWidth()+"*"+imageTmplModel.getHeight();
                return size;
            }else if(CodeTableConstant.CREATIVE_TYPE_INFOFLOW.equals(type)){
                InfoflowTmplModel infoflowTmplModel = infoflowTmplDao.selectByPrimaryKey(tmpId);
                if(infoflowTmplModel != null){
                    String imgId = infoflowTmplModel.getImage1Id() !=null ? infoflowTmplModel.getImage1Id() : infoflowTmplModel.getIconId();

                    ImageTmplModel imageTmplModel = imageTmplDao.selectByPrimaryKey(imgId);
                    if(imageTmplModel != null) {
                        size = imageTmplModel.getWidth() + "*" + imageTmplModel.getHeight();
                    }
                    return size;
                }
            }
        }
        return size;
    }

    /**
     * 获取所有投放时间在当前时间段的活动下面的素材尺寸
     * @return
     */
    public List<ResponseData> getCreativeImageSizes(){
        List<ResponseData> result = new ArrayList<>();
        List<CreativeModel> creativeModels = creativeService.getCreativeOfCurrentPuttingTime();
        if(creativeModels != null && !creativeModels.isEmpty()) {
            ResponseData responseData;
            for (CreativeModel creativeModel : creativeModels) {
               String value = getCreativeImageSizeByCreativeId(creativeModel.getId());
                responseData = new ResponseData();
                responseData.setId(creativeModel.getId());
                responseData.setValue(value);
                result.add(responseData);
            }
        }

        return result;
    }

    /**
     * 获取创意的广告类型,并转为dsp的广告类型
     * @param creativeId
     * @return
     */
    public String getMaterialTypeByCreativeId(String creativeId){
        String marterialType = "";
        if(creativeId != null && !creativeId.isEmpty()) {
            CreativeModel creativeModel = creativeDao.selectByPrimaryKey(creativeId);
            if (creativeModel != null) {
                //类型
                String type = creativeModel.getType();
                marterialType = transformCreativeType2Dsp(type)+"";
            }
        }
        return marterialType;
    }

    /**
     * 将创意类型转化为dsp的广告创意类型
     * @param type
     * @return
     */
    private int transformCreativeType2Dsp(String type){
        if (CodeTableConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
            return  CodeTableConstant.ADVERTISE_CREATVIE_TYPE_IMAGE;
        } else if (CodeTableConstant.CREATIVE_TYPE_INFOFLOW.equals(type)) {
            return CodeTableConstant.ADVERTISE_CREATVIE_TYPE_INFOFLOW;
        }else if(CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(type)){
            return CodeTableConstant.ADVERTISE_CREATVIE_TYPE_VIDEO;
        }
        return 0;
    }

    /**
     * 获取投放时间在当前时间的创意的类型，并转换为DSP的格式
     * @return
     */
    public List<ResponseData> getMaterialTypes(){
        List<ResponseData> result = new ArrayList<>();
        //获取投放时间在当前时间内的创意
       List<CreativeModel> creativeModels = creativeService.getCreativeOfCurrentPuttingTime();
        if(creativeModels != null && !creativeModels.isEmpty()) {
            ResponseData responseData;
            for (CreativeModel creativeModel : creativeModels) {
                String value = transformCreativeType2Dsp(creativeModel.getType())+"";
                responseData = new ResponseData();
                responseData.setId(creativeModel.getId());
                responseData.setValue(value);
                result.add(responseData);
            }
        }
        return  result;
    }

    /**
     * 查询不出价原因
     * @param bidAnalyseBean
     * @return
     */
    public List<NobidReasonBean> queryNobidReason(BidAnalyseBean bidAnalyseBean){
        if(bidAnalyseBean == null){
            return null;
        }
        if((bidAnalyseBean.getType().equals(CodeTableConstant.TYPE_DAY) && bidAnalyseBean.getStartDate() == 0 || bidAnalyseBean.getEndDate() == 0) || (bidAnalyseBean.getType().equals(CodeTableConstant.TYPE_HOUR) && bidAnalyseBean.getDate() == 0)){
            throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
        }

        //查询
        BoolQueryBuilder boolQB = QueryBuilders.boolQuery();
        //查询条件--项目
        QueryBuilder projectBuilder = QueryBuilders.termQuery("campaign",bidAnalyseBean.getProjectId());
        boolQB.must(projectBuilder);

        //查询条件--活动
        if(bidAnalyseBean.getCampaignId() != null && !bidAnalyseBean.getCampaignId().isEmpty()){
            QueryBuilder camp = QueryBuilders.termQuery("groupid", bidAnalyseBean.getCampaignId());
            boolQB.must(camp);
        }
        //查询条件--adx
        if(bidAnalyseBean.getAdx() != null && !bidAnalyseBean.getAdx().isEmpty()){
            QueryBuilder adx = QueryBuilders.termQuery("adxid",bidAnalyseBean.getAdx());
            boolQB.must(adx);
        }
        //查询条件--素材类型
        if(bidAnalyseBean.getMaterialType() != null && !bidAnalyseBean.getMaterialType().isEmpty()){
            QueryBuilder material = QueryBuilders.termQuery("creativeid", bidAnalyseBean.getMaterialType());
            boolQB.must(material);
        }
        //查询条件--素材大小
        if(bidAnalyseBean.getMaterialSize() != null && !bidAnalyseBean.getMaterialSize().isEmpty()){
            QueryBuilder materialSize = QueryBuilders.termQuery("size", bidAnalyseBean.getMaterialSize());
            boolQB.must(materialSize);
        }

        //聚合
        TermsBuilder agg_nbrname = AggregationBuilders.terms("nbrname").field("nbrname").order(Terms.Order.compound(Terms.Order.aggregation("nbrname_sum",false))).size(1000);
        SumBuilder nbrname_sum = AggregationBuilders.sum("nbrname_sum").field("flow");
        agg_nbrname.subAggregation(nbrname_sum);

        List<NobidReasonBean> result = null;
        EsQueryBean esQueryBean = new EsQueryBean();
        esQueryBean.setType("data");
        esQueryBean.setSize(0);
        esQueryBean.setQueryBuilder(boolQB);

        if(bidAnalyseBean.getType().equals(CodeTableConstant.TYPE_DAY)){//按天汇总
            esQueryBean.setAggregation(agg_nbrname);
            result = listNoBidReasonGroupByDay(bidAnalyseBean,esQueryBean);
        }else if(bidAnalyseBean.getType().equals(CodeTableConstant.TYPE_HOUR)){//按小时汇总
            TermsBuilder agg_hour = AggregationBuilders.terms("hour").field("hour").size(1000).order(Terms.Order.term(false));
            agg_hour.subAggregation(agg_nbrname);
            esQueryBean.setAggregation(agg_hour);
            result = listNoBidReasonGroupByHour(bidAnalyseBean,esQueryBean);
        }

        return result;
    }

    /**
     * 按天汇总不出价原因
     * @param bidAnalyseBean
     * @param esQueryBean
     * @return
     */
    public List<NobidReasonBean> listNoBidReasonGroupByDay(BidAnalyseBean bidAnalyseBean,EsQueryBean esQueryBean){

        List<NobidReasonBean> result = new ArrayList<>();

        String[] days = DateUtils.getDaysBetween(new Date(bidAnalyseBean.getStartDate()), new Date(bidAnalyseBean.getEndDate()));

        if(bidAnalyseBean.getSortType() == null || bidAnalyseBean.getSortType().equals(StatusConstant.SORT_TYPE_DESC)){//逆序
            for (int i=days.length-1; i>=0 ;i--) {
                List<NobidReasonBean> nobidReasonBeenList =  queryNoBidReasonFromEs(days[i], esQueryBean);
                if(nobidReasonBeenList != null){
                    result.addAll(nobidReasonBeenList);
                }
            }

        }else {//正序
            for (String day : days) {
                List<NobidReasonBean> nobidReasonBeenList =  queryNoBidReasonFromEs(day, esQueryBean);
                if(nobidReasonBeenList != null){
                    result.addAll(nobidReasonBeenList);
                }
            }
        }
        return result;
    }

    /**
     * 从es查询不出价
     * @param day
     * @param esQueryBean
     * @return
     */
    public List<NobidReasonBean> queryNoBidReasonFromEs(String day,EsQueryBean esQueryBean){

        esQueryBean.setIndex("nbr_"+day);

        //判断索引和type是否存在
        if(!esUtils.isExistsIndex(esQueryBean.getIndex()) || !esUtils.isExistsType(esQueryBean.getIndex(),esQueryBean.getType())){
            return null;
        }
        List<NobidReasonBean> nobidReasonBeenList = new ArrayList<>();
        SearchResponse searchResponse = esUtils.query(esQueryBean);

        if (searchResponse != null && searchResponse.getAggregations() != null) {
            Map<String, Aggregation> aggMap = searchResponse.getAggregations().asMap();
            StringTerms nbrnameTerms = (StringTerms) aggMap.get("nbrname");
            Iterator<Terms.Bucket> nbrnameBucketIt = nbrnameTerms.getBuckets().iterator();
            while (nbrnameBucketIt.hasNext()) {
                Terms.Bucket nbrnameBuck = nbrnameBucketIt.next();
                InternalSum sumTerms = (InternalSum) nbrnameBuck.getAggregations().get("nbrname_sum");

                NobidReasonBean nobidReasonBean = new NobidReasonBean();
                nobidReasonBean.setDate(day);
                nobidReasonBean.setNbrName(nbrnameBuck.getKeyAsString());
                nobidReasonBean.setAmount(Double.valueOf(sumTerms.getValue()).intValue());

                nobidReasonBeenList.add(nobidReasonBean);
            }

        }
        return nobidReasonBeenList;
    }

    /**
     * 按小时汇总不出价原因
     * @param bidAnalyseBean
     * @param esQueryBean
     * @return
     */
    public List<NobidReasonBean> listNoBidReasonGroupByHour(BidAnalyseBean bidAnalyseBean,EsQueryBean esQueryBean){

        List<NobidReasonBean> nobidReasonBeenList = new ArrayList<>();

        Date date = new Date(bidAnalyseBean.getDate());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String day = sdf.format(date);

        esQueryBean.setIndex("nbr_"+day);
        //判断索引和type是否存在
        if(!esUtils.isExistsIndex(esQueryBean.getIndex()) || !esUtils.isExistsType(esQueryBean.getIndex(),esQueryBean.getType())){
            return nobidReasonBeenList;
        }
        SearchResponse searchResponse = esUtils.query(esQueryBean);

        if (searchResponse != null && searchResponse.getAggregations() != null) {
            Map<String, Aggregation> aggMap = searchResponse.getAggregations().asMap();
            StringTerms teamAgg = (StringTerms) aggMap.get("hour");
            SimpleDateFormat sdf_hour = new SimpleDateFormat("HH:mm");
            SimpleDateFormat sdf_sec = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Iterator<Terms.Bucket> teamBucketIt = teamAgg.getBuckets().iterator();
            while (teamBucketIt.hasNext()) {
                Terms.Bucket buck = teamBucketIt.next();

                String dateStr = (String) buck.getKey();
                String hourStr = null;
                try {
                    hourStr = sdf_hour.format(sdf_sec.parse(dateStr));
                } catch (ParseException e) {
                    continue;
                }
                StringTerms nbrnameTerms = (StringTerms) (StringTerms) buck.getAggregations().asMap().get("nbrname");
                Iterator<Terms.Bucket> nbrnameBucketIt = nbrnameTerms.getBuckets().iterator();
                while (nbrnameBucketIt.hasNext()) {
                    Terms.Bucket nbrnameBuck = nbrnameBucketIt.next();
                    InternalSum sumTerms = (InternalSum) nbrnameBuck.getAggregations().get("nbrname_sum");

                    NobidReasonBean nobidReasonBean = new NobidReasonBean();
                    nobidReasonBean.setDate(hourStr);
                    nobidReasonBean.setNbrName(nbrnameBuck.getKeyAsString());
                    nobidReasonBean.setAmount(Double.valueOf(sumTerms.getValue()).intValue());

                    nobidReasonBeenList.add(nobidReasonBean);
                }
            }
        }
        return nobidReasonBeenList;
    }


}
