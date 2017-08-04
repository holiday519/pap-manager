package com.pxene.pap.service;

import com.github.pagehelper.Page;
import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.EsUtils;
import com.pxene.pap.common.RedisHelper;
import com.pxene.pap.constant.CodeTableConstant;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.*;
import com.pxene.pap.domain.models.*;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.repository.basic.*;
import com.pxene.pap.repository.custom.CustomCreativeDao;
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
    @Autowired
    private CustomCreativeDao customCreativeDao;


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
    public PaginationBean  queryNobidReason(BidAnalyseBean bidAnalyseBean, Page<Object> pager){
        if(bidAnalyseBean == null){
            return null;
        }
        if((bidAnalyseBean.getType().equals(CodeTableConstant.TYPE_DAY) && bidAnalyseBean.getStartDate() == 0 || bidAnalyseBean.getEndDate() == 0) || (bidAnalyseBean.getType().equals(CodeTableConstant.TYPE_HOUR) && bidAnalyseBean.getDate() == 0)
                || (bidAnalyseBean.getProjectId() == null && bidAnalyseBean.getCampaignId() == null)){
            throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
        }

        //查询
        BoolQueryBuilder boolQB = QueryBuilders.boolQuery();
        if(bidAnalyseBean.getType().equals(CodeTableConstant.TYPE_DAY)){//按天汇总
            String[] days = DateUtils.getDaysBetween(new Date(bidAnalyseBean.getStartDate()), new Date(bidAnalyseBean.getEndDate()),"yyyy-MM-dd");
            QueryBuilder date = QueryBuilders.termsQuery("date",days);
            boolQB.must(date);
        }
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

        PaginationBean result = null;
        EsQueryBean esQueryBean = new EsQueryBean();
        esQueryBean.setType("data");
        esQueryBean.setSize(0);
        esQueryBean.setQueryBuilder(boolQB);

        if(bidAnalyseBean.getType().equals(CodeTableConstant.TYPE_DAY)){//按天汇总
            esQueryBean.setIndex("nbr_*");//设置index
            TermsBuilder agg_day = AggregationBuilders.terms("date").field("date").size(1000);//按date聚合
            //按日期排序
            if(bidAnalyseBean.getSortType() == null || bidAnalyseBean.getSortType().equals(StatusConstant.SORT_TYPE_DESC)){//逆序
                agg_day.order(Terms.Order.term(false));
            }else{
                agg_day.order(Terms.Order.term(true));
            }
            agg_day.subAggregation(agg_nbrname);
            esQueryBean.setAggregation(agg_day);
            result =  queryNoBidReasonFromEs(esQueryBean,"date",pager);
        }else if(bidAnalyseBean.getType().equals(CodeTableConstant.TYPE_HOUR)){//按小时汇总
            TermsBuilder agg_hour = AggregationBuilders.terms("hour").field("hour").size(1000);//按小时聚合
            //按小时排序
            if(bidAnalyseBean.getSortType() == null || bidAnalyseBean.getSortType().equals(StatusConstant.SORT_TYPE_DESC)){//逆序
                agg_hour.order(Terms.Order.term(false));
            }else{
                agg_hour.order(Terms.Order.term(true));
            }
            agg_hour.subAggregation(agg_nbrname);
            esQueryBean.setAggregation(agg_hour);
            Date date = new Date(bidAnalyseBean.getDate());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String day = sdf.format(date);

            esQueryBean.setIndex("nbr_"+day);

            result = queryNoBidReasonFromEs(esQueryBean,"hour",pager);
        }

        return result;
    }

    /**
     * 从es查询不出价
     * @param esQueryBean
     * @return
     */
    public PaginationBean queryNoBidReasonFromEs(EsQueryBean esQueryBean,String field,Page<Object> pager){

        //判断索引和type是否存在
        if(!esUtils.isExistsIndex(esQueryBean.getIndex()) || !esUtils.isExistsType(esQueryBean.getIndex(),esQueryBean.getType())){
            return null;
        }
        //存放不出价原因结果集
        List<NobidReasonBean> nobidReasonBeenList = new ArrayList<>();
        //查询es,获取结果集
        SearchResponse searchResponse = esUtils.query(esQueryBean);
        //计数：查询总数
        long total = 0L;
        if (searchResponse != null && searchResponse.getAggregations() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf_hour = new SimpleDateFormat("HH:mm");
            SimpleDateFormat sdf_sec = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //分页准备

            int pageNo = pager.getPageNum();//页码
            int pageSize = pager.getPageSize();//每页大小
            int count = 0;//计数：指定分页显示的数
            int startNum = (pageNo-1)*pageSize+1;//从第几个开始
            int endNum = pageNo*pageSize;//从第几个数结束

            //获取第一层聚合结果
            Map<String, Aggregation> aggMap = searchResponse.getAggregations().asMap();
            Terms teamAgg = (Terms) aggMap.get(field);
            Iterator<Terms.Bucket> teamBucketIt = teamAgg.getBuckets().iterator();
            while (teamBucketIt.hasNext()) {
                Terms.Bucket buck = teamBucketIt.next();

                StringTerms nbrnameTerms = (StringTerms) buck.getAggregations().asMap().get("nbrname");
                total = total + nbrnameTerms.getBuckets().size();
                //判断是不是指定页要显示的数据
                if(total < startNum){//指定页之前的数据直接跳过
                    count = (int) total;
                    continue;
                }
                if(count > endNum){//指定页之后的数据直接跳过
                    continue;
                }

                String dateStr=null;//日期时间
                if(buck.getKey() instanceof Long){
                    Long key = (Long) buck.getKey();
                    dateStr = sdf.format(new Date(key));
                } if(buck.getKey() instanceof String){
                    dateStr = (String) buck.getKey();
                    if(field.equals("hour")) {
                        try {
                            dateStr = sdf_hour.format(sdf_sec.parse(dateStr));
                        } catch (ParseException e) {
                            continue;
                        }
                    }
                }

                Iterator<Terms.Bucket> nbrnameBucketIt = nbrnameTerms.getBuckets().iterator();
                while (nbrnameBucketIt.hasNext()) {
                    Terms.Bucket nbrnameBuck = nbrnameBucketIt.next();
                    count++;
                    if(count >= startNum && count<= endNum) {
                        InternalSum sumTerms = (InternalSum) nbrnameBuck.getAggregations().get("nbrname_sum");

                        NobidReasonBean nobidReasonBean = new NobidReasonBean();
                        nobidReasonBean.setDate(dateStr);
                        nobidReasonBean.setNbrName(nbrnameBuck.getKeyAsString());
                        nobidReasonBean.setAmount(Double.valueOf(sumTerms.getValue()).intValue());

                        nobidReasonBeenList.add(nobidReasonBean);
                    }else if(count>endNum){//后面的数不再需要
                        break;
                    }

                }
            }

        }
        //设置总数
        pager.setTotal(total);
        //封装成分页
        PaginationBean result = new PaginationBean(nobidReasonBeenList,pager);
        searchResponse = null;
        return result;
    }


    /**
     * 列出所有图片大小，去重
     *
     * @return
     */
    public  List<Map<String,String>> listAllImageSizes(){
        List<Map<String,String>> result = customCreativeDao.selectImageSizes();
        return result;
    }

}
