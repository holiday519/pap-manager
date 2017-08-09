package com.pxene.pap.service;

import static com.pxene.pap.constant.StatusConstant.ADVERTISER_AUDIT_FAILURE;
import static com.pxene.pap.constant.StatusConstant.ADVERTISER_AUDIT_NOCHECK;
import static com.pxene.pap.constant.StatusConstant.ADVERTISER_AUDIT_SUCCESS;
import static com.pxene.pap.constant.StatusConstant.ADVERTISER_AUDIT_WATING;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomUtils;
import org.apache.tools.ant.util.DateUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pxene.pap.common.GlobalUtil;
import com.pxene.pap.common.HttpClientUtil;
import com.pxene.pap.common.UUIDGenerator;
import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.constant.AuditErrorConstant;
import com.pxene.pap.constant.CodeTableConstant;
import com.pxene.pap.constant.ConfKeyConstant;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.CreativeRichBean;
import com.pxene.pap.domain.beans.baidu.APIAdvertiserLicence;
import com.pxene.pap.domain.beans.baidu.APIAdvertiserQualificationStatus;
import com.pxene.pap.domain.beans.baidu.APIAdvertiserQualificationUpload;
import com.pxene.pap.domain.beans.baidu.APIUploadQualificationResult;
import com.pxene.pap.domain.beans.baidu.Advertiser;
import com.pxene.pap.domain.beans.baidu.BaiduRequest;
import com.pxene.pap.domain.beans.baidu.BaiduResponse;
import com.pxene.pap.domain.models.AdvertiserAuditModel;
import com.pxene.pap.domain.models.AdvertiserAuditModelExample;
import com.pxene.pap.domain.models.AdvertiserModel;
import com.pxene.pap.domain.models.AdxModel;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CreativeAuditModel;
import com.pxene.pap.domain.models.CreativeAuditModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.ImageMaterialModel;
import com.pxene.pap.domain.models.ImageModel;
import com.pxene.pap.domain.models.IndustryAdxModel;
import com.pxene.pap.domain.models.IndustryAdxModelExample;
import com.pxene.pap.domain.models.InfoflowMaterialModel;
import com.pxene.pap.domain.models.LandpageModel;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.exception.ThirdPartyAuditException;
import com.pxene.pap.repository.basic.IndustryAdxDao;

/**
 * 百度流量交易平台（BES）相关审核、同步服务。
 * @author ningyu
 */
@Service
public class BaiduAuditService extends AuditService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(BaiduAuditService.class);
	
	/**
     * 创意审核信息的过期天数
     */
    private static final int CREATIVE_AUDIT_EXPIRE_DAYS = 365;
	
	/**
	 * 素材文件服务器的URL前缀
	 */
	private String urlPrefix;
	
	/**
	 * 点击URL前缀
	 */
	private String clkURLPrefix;
	
	/**
	 * 展现URL前缀
	 */
	private String impURLPrefix;
	
	/**
	 * Gson JSON解析器
	 */
	private JsonParser parser = new JsonParser();
	
    @Autowired
    private IndustryAdxDao industryAdxDao;
    
    
    @Autowired
	public BaiduAuditService(Environment env) 
	{
		super(env);
		
        String uploadMode = env.getProperty(ConfKeyConstant.FILESERVER_MODE);
        
        if ("local".equals(uploadMode))
        {
            urlPrefix = env.getProperty(ConfKeyConstant.FILESERVER_LOCAL_URL_PREFIX);
        }
        else
        {
            urlPrefix = env.getProperty(ConfKeyConstant.FILESERVER_REMOTE_URL_PREFIX);
        }
        
        clkURLPrefix = env.getProperty(ConfKeyConstant.CLICK_URL_PREFIX);
        impURLPrefix = env.getProperty(ConfKeyConstant.IMPRESSION_URL_PREFIX);
	}
	
    
	/**
	 * 提交广告主到BES进行审核（创建广告主、上传广告主资质、更新广告主审核表数据）
	 */
	@Override
	@Transactional
    public void auditAdvertiser(String auditId) throws Exception
    {
	    // 查询广告主审核信息
        AdvertiserAuditModel advertiserAudit = advertiserAuditDao.selectByPrimaryKey(auditId);
        String auditValue = advertiserAudit.getAuditValue();
        String advertiserId = advertiserAudit.getAdvertiserId();
        String adxId = advertiserAudit.getAdxId();
        String auditStatus = advertiserAudit.getStatus();
        
        // “正在审核中”和“审核通过”的广告主不需要再提审
        if (ADVERTISER_AUDIT_WATING.equals(auditStatus))
        {
            throw new IllegalStatusException(AuditErrorConstant.COMMON_ADVERTISER_IS_AUDITING);
        }
        if (ADVERTISER_AUDIT_SUCCESS.equals(auditStatus))
        {
            throw new IllegalStatusException(AuditErrorConstant.COMMON_ADVERTISER_HAS_AUDITTED);
        }
        
        // 查询广告主信息
        AdvertiserModel advertiser = advertiserDao.selectByPrimaryKey(advertiserId);
        
        // 查询ADX信息
        AdxModel adx = adxDao.selectByPrimaryKey(adxId);
        
        // 如果是“未审核”状态，则需要新建广告主，否则修改广告主
        if (ADVERTISER_AUDIT_NOCHECK.equals(auditStatus))
        {
            Long dspSideAdvertiserId = RandomUtils.nextLong(); 
            
            boolean createResult = createAdvertiserInBES(dspSideAdvertiserId, adx, advertiser);
            
            // 广告主如果创建成功，则在DB中插入audit_value，状态修改为“审核未通过”
            if (createResult)
            {
                updateAdvertiserAuditInfo(auditId, String.valueOf(dspSideAdvertiserId), ADVERTISER_AUDIT_FAILURE, null);
                
                // 如果创建成功，继续上传资质，如果上传成功，则将状态修改为“审核中”
                int qulificationUploadStatus = uploadQulificationToBES(dspSideAdvertiserId, adx, advertiser);
                if (qulificationUploadStatus == 1 || qulificationUploadStatus == 2) // 0：提交失败， 1：已提交，2：免提交
                {
                    updateAdvertiserAuditInfo(auditId, null, ADVERTISER_AUDIT_WATING, null);
                }
            }
        }
        else // 如果是“审核未通过”状态，则编辑广告主
        {
            Long dspSideAdvertiserId = Long.valueOf(auditValue);
            
            // 编辑广告主信息
            boolean updateResult = updateAdvertiserInBES(dspSideAdvertiserId, adx, advertiser);
            if (updateResult)
            {
                // 修改广告主主体资质
                int qulificationUpdateStatus = updateQulificationInBES(dspSideAdvertiserId, adx, advertiser);
                
                // 0：提交失败， 1：已提交，2：免提交
                if (qulificationUpdateStatus == 1 || qulificationUpdateStatus == 2)
                {
                    updateAdvertiserAuditInfo(auditId, String.valueOf(dspSideAdvertiserId), ADVERTISER_AUDIT_WATING, null);
                }
            }
        }
    }

	/**
	 * 查询广告主在ADX的审核状态，通过调用百度查询广告主资质API实现。<br>
	 * （由于DSP已经在百度审核过广告主————上海银行的资质，因此这里直接从数据库中读取出审核值作为Baidu分配给蓬景的ADX_ID）
	 */
	@Override
	@Transactional
    public void synchronizeAdvertiser(String auditId) throws Exception
    {
	    if (StringUtils.isEmpty(auditId))
	    {
	        throw new IllegalArgumentException();
	    }
	    
	    // 查询广告主审核信息
	    AdvertiserAuditModel advertiserAudit = advertiserAuditDao.selectByPrimaryKey(auditId);
	    String advertiserId = advertiserAudit.getAuditValue();
	    String adxId = advertiserAudit.getAdxId();
	    
	    // 查询ADX信息
        AdxModel adx = adxDao.selectByPrimaryKey(adxId);
        String url = adx.getQualificationQueryUrl();   // 查询广告主资质API URL
        Long dspId = Long.valueOf(adx.getDspId());     // DSP ID
        String token = adx.getSignKey();               // 百度为DSP颁发的Token
        
        // -##- 构建请求头
        JsonObject requestHeader = new JsonObject();
        requestHeader.addProperty("dspId", dspId);
        requestHeader.addProperty("token", token);
        
        // -#- 构建请求参数
        JsonObject requestObj = new JsonObject();
        requestObj.add("authHeader", requestHeader);
        requestObj.addProperty("advertiserId", advertiserId);
        requestObj.addProperty("needLicenceImgUrl", true);
        
        // 构建审核请求对象
        String jsonStrRequest = requestObj.toString();
        
        // 发送HTTP POST请求
        LOGGER.info("### PAP-Manager ### Synchronize advertiser [" + advertiserId + "] state from Baidu： url = " + url + ", params = " + jsonStrRequest);
        String jsonStrResponse = HttpClientUtil.getInstance().sendHttpPostJson(url, jsonStrRequest);
        LOGGER.debug("### PAP-Manager ### Synchronize advertiser [" + advertiserId + "] state from Baidu：result = " + jsonStrResponse);
        
        
        // 如果请求发送成功且应答响应成功，则将审核信息插入或更新至数据库中，否则提示审核失败的原因
        if (!StringUtils.isEmpty(jsonStrResponse))
        {
            // 检查响应对象中的status属性的值是否是0和1
            boolean respStatus = checkResponseStatus(jsonStrResponse);
            
            if (respStatus)
            {
                JsonObject respObj = parser.parse(jsonStrResponse).getAsJsonObject();
                JsonObject qualificationObj = respObj.get("qualification").getAsJsonObject();
                if (qualificationObj.has("auditState"))
                {
                    int auditState = qualificationObj.get("auditState").getAsInt();
                    
                    if (auditState == 0) // 审核通过
                    {
                        advertiserAudit.setStatus(ADVERTISER_AUDIT_SUCCESS);
                    }
                    else if (auditState == 1) // 审核中
                    {
                        advertiserAudit.setStatus(ADVERTISER_AUDIT_WATING);
                    }
                    else if (auditState == 2) // 审核拒绝
                    {
                        advertiserAudit.setStatus(ADVERTISER_AUDIT_FAILURE);
                    }
                    
                    // 更新广告主的审核表状态
                    advertiserAuditDao.updateByPrimaryKey(advertiserAudit);
                }
            }
        }
    }

	/**
	 * 提交创意到百度进行审核
	 */
	@Override
	@Transactional(dontRollbackOn = IllegalStatusException.class)
    public void auditCreative(String creativeId) throws Exception
    {
	    // 根据创意ID，查询创意信息
        CreativeModel creativeModel = creativeDao.selectByPrimaryKey(creativeId);
        
        // 判断创意是否存在
        if (creativeModel == null)
        {
            throw new ResourceNotFoundException(PhrasesConstant.CREATIVE_NOT_FOUND);
        }
        
        // 创意类型
        String creativeType = creativeModel.getType();
        
        // 检查创意类型
        if (CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(creativeType))
        {
            throw new ThirdPartyAuditException(PhrasesConstant.CREATIVE_TYPE_NOT_SUPPORT);
        }
        
        // 百度的ADX信息
        AdxModel adx = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_BAIDU_VALUE);
        Long dspId = Long.valueOf(adx.getDspId());                      // DSP ID
        String url = adx.getCreativeAddUrl();                           // 创意审核URL
        String token = adx.getSignKey();                                // 百度为DSP颁发的Token
        String iURL = adx.getIurl();                                    // 曝光监测地址
        String cURL = adx.getCurl();                                    // 点击监测地址
        
        // 查询出创意的相关信息
        CreativeRichBean richCreative = buildRelation(creativeId);
        
        // 广告主信息
        AdvertiserModel advertiserModel = richCreative.getAdvertiserInfo();
        
        // 广告主所属行业ID
        String industryId = advertiserModel.getIndustryId();
        
        // DSP端的广告主ID
        Long advertiserId = getAdvertiserIDInDSP(advertiserModel.getId());   
        if (advertiserId == null)
        {
            throw new ResourceNotFoundException(PhrasesConstant.ADVERTISER_AUDIT_NOT_FOUND);
        }
        
        // DSP端的创意ID
        Long dspSideCreativeId = RandomUtils.nextLong();
        
        // 查询出创意所属广告行业
        String creativeTradeId = getCreativeTradeId(industryId);
        
        // 创意素材ID
        String materialId = creativeModel.getMaterialId();
        
        // 落地页信息
        LandpageModel landpageInfo = richCreative.getLandpageInfo();
        
        // 拼接成点击地址（302跳转）
        cURL = cURL.replace("#BID#", "1").replace("#DEVICEID#", "1").replace("#DEVICEIDTYPE#", "1").replace("#MAPID#", creativeId);
        String link = clkURLPrefix + cURL;
        String landpageURL = URLEncoder.encode(landpageInfo.getUrl(), StandardCharsets.UTF_8.displayName());
        link = link + "&curl=" + landpageURL;

        // -###- 构建广告监测链接数组
        JsonArray monitorUrlArray = new JsonArray();
        monitorUrlArray.add(impURLPrefix + iURL);
        
        // -##- 构建请求头
        JsonObject requestHeader = new JsonObject();
        requestHeader.addProperty("dspId", dspId);
        requestHeader.addProperty("token", token);
        
        // -##- 构建请求体
        JsonArray requestBody = new JsonArray();
        JsonObject item = new JsonObject();
        item.addProperty("creativeId", dspSideCreativeId);
        item.addProperty("adviewType", 2);   // 2：Mobile流量
        item.addProperty("targetUrl", link);
        item.addProperty("landingPage", landpageInfo.getUrl());
        item.add("monitorUrls", monitorUrlArray);
        item.addProperty("creativeTradeId", creativeTradeId);
        item.addProperty("advertiserId", advertiserId);
        item.addProperty("interactiveStyle", 0); // 互动样式（移动创意必填 ），0：无
        
        if (CodeTableConstant.CREATIVE_TYPE_IMAGE.equals(creativeType))         // 如果是图片创意，则读取图片素材表，取得图片ID，再根据图片ID，取得详细的图片信息
        {
            ImageMaterialModel imageMaterial = imageMaterialDao.selectByPrimaryKey(materialId);
            String imageId = imageMaterial.getImageId();
            
            if (StringUtils.isEmpty(imageId))
            {
                throw new IllegalArgumentException();
            }
            
            // 根据图片ID取得图片素材信息
            ImageModel imageInfo = imageDao.selectByPrimaryKey(imageId);
            
            // 获得图片的二进制数据的字符串表示（Base64编码）
            String binaryDataBase64Str = getBase64ImageStrByImageId(imageInfo);
            
            item.addProperty("type", 1); // 1：图片
            item.addProperty("binaryData", binaryDataBase64Str);
            item.addProperty("width", imageInfo.getWidth());
            item.addProperty("height", imageInfo.getHeight());
        }
        else if (CodeTableConstant.CREATIVE_TYPE_INFOFLOW.equals(creativeType)) // 如果是信息流创意，则读取信息流素材表，取得第一张图片ID
        {
            InfoflowMaterialModel infoflowMaterial = infoflowMaterialDao.selectByPrimaryKey(materialId);
            
            String title = infoflowMaterial.getTitle();
            String descp = infoflowMaterial.getDescription();
            String brandName = advertiserModel.getBrandName();
            
            String iconId = infoflowMaterial.getIconId();
            String image1Id = infoflowMaterial.getImage1Id();
            
            // 根据图片ID取得图片素材信息
            ImageModel logoImageInfo = imageDao.selectByPrimaryKey(iconId);
            ImageModel image1Info = imageDao.selectByPrimaryKey(image1Id);
            
            // 获得图片的二进制数据的字符串表示（Base64编码）
            String logoBinaryDataBase64Str = getBase64ImageStrByImageId(logoImageInfo);
            String image1BinaryDataBase64Str = getBase64ImageStrByImageId(image1Info);
            
            // -###- 图文创意二进制数据的数组
            JsonArray binaryDatas = new JsonArray();
            binaryDatas.add(logoBinaryDataBase64Str);
            binaryDatas.add(image1BinaryDataBase64Str);
            
            item.addProperty("type", 4);                // 4：图文
            item.addProperty("style", 5);               // 5：APP图文信息流，要求adviewType必须为2
            item.addProperty("title", title);           // 主标题
            item.addProperty("description", descp);     // 描述
            item.addProperty("brandName", brandName);   // 品牌名称 
            item.add("binaryDatas", binaryDatas);       // 图文创意二进制数据列表
        }
        
        requestBody.add(item);
        
        // -#- 构建请求参数
        JsonObject requestObj = new JsonObject();
        requestObj.add("authHeader", requestHeader);
        requestObj.add("request", requestBody);
        
        // 构建审核请求对象
        String jsonStrRequest = requestObj.toString();
        
        // 发送HTTP POST请求
        LOGGER.debug("### PAP-Manager ### Audit creative [" + creativeId + "] to Baidu： url = " + url + ", params = " + jsonStrRequest);
        String jsonStrResponse = HttpClientUtil.getInstance().sendHttpPostJson(url, jsonStrRequest);
        LOGGER.debug("### PAP-Manager ### Audit creative [" + creativeId + "] to Baidu：result = " + jsonStrResponse);
        
        // 如果请求发送成功且应答响应成功，则将审核信息插入或更新至数据库中，否则提示审核失败的原因
        if (!StringUtils.isEmpty(jsonStrResponse))
        {
            // 检查响应对象中的status属性的值是否是0和1
            boolean respStatus = checkResponseStatus(jsonStrResponse);
            
            if (respStatus)
            {
                insertOrUpdateToDB(creativeId, dspSideCreativeId);
            }
            else
            {
                throw new ThirdPartyAuditException(AuditErrorConstant.BAIDU_CREATIVE_AUDIT_ERROR_REASON + AuditErrorConstant.COMMON_RESPONSE_PARSE_FAIL);
            }
        }
        else 
        {
            throw new ThirdPartyAuditException(AuditErrorConstant.BAIDU_CREATIVE_AUDIT_ERROR_REASON + AuditErrorConstant.COMMON_REQUEST_SENT_FAIL);
        }
    }
	
	/**
     * 查询百度对某创意的审核结果
     */
    @Override
    @Transactional(dontRollbackOn = IllegalStatusException.class)
    public void synchronizeCreative(String creativeId) throws Exception
    {
        // 根据创意ID查询创意信息
        CreativeModel creative = creativeDao.selectByPrimaryKey(creativeId);
        
        // 判断创意是否存在
        if (creative == null)
        {
            throw new ResourceNotFoundException();
        }
        
        Long auditValue = null;
        
        // 判断“创意审核表(pap_t_creative_audit)”中是否存在该创意的审核信息
        CreativeAuditModelExample example = new CreativeAuditModelExample();
        example.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_BAIDU_VALUE);
        List<CreativeAuditModel> modelList = creativeAuditDao.selectByExample(example);
        if (modelList == null || modelList.isEmpty())
        {
            throw new ResourceNotFoundException();
        }
        else
        {
            auditValue = Long.valueOf(modelList.get(0).getAuditValue());
        }
        
        // Baidu的ADX信息
        AdxModel adx = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_BAIDU_VALUE);
        Long dspId = Long.valueOf(adx.getDspId());                      // DSP ID
        String token = adx.getSignKey();                                // 百度为DSP颁发的Token
        
        // 查询创意审核状态URL
        String url = adx.getCreativeQueryUrl();
        
        // ## 构建请求头
        JsonObject requestHeader = new JsonObject();
        requestHeader.addProperty("dspId", dspId);
        requestHeader.addProperty("token", token);
        
        // ## 构建请求体
        JsonArray requestBody = new JsonArray();
        requestBody.add(auditValue);
        
        // # 构建请求参数
        JsonObject requestObj = new JsonObject();
        requestObj.add("authHeader", requestHeader);
        requestObj.add("creativeIds", requestBody);
        
        // 构建审核请求对象
        String jsonStrRequest = requestObj.toString();
        
        // 发送HTTP POST请求
        LOGGER.info("### PAP-Manager ### Synchronize creative [" + creativeId + "] state from Baidu： url = " + url + ", params = " + jsonStrRequest);
        String jsonStrResponse = HttpClientUtil.getInstance().sendHttpPostJson(url, jsonStrRequest);
        LOGGER.debug("### PAP-Manager ### Synchronize creative [" + creativeId + "] state from Baidu：result = " + jsonStrResponse);
        
        if (!StringUtils.isEmpty(jsonStrResponse))
        {
            // 检查响应对象中的status属性的值是否是0和1
            boolean respStatus = checkResponseStatus(jsonStrResponse);
            
            if (respStatus)
            {
                Map<String, String> creativeAuditStatus = getCreativeAuditStatus(jsonStrResponse);
                if (creativeAuditStatus != null)
                {
                    String state = creativeAuditStatus.get("state");
                    String refuseReason = creativeAuditStatus.get("refuseReason");
                    
                    // 如果请求发送成功且应答响应成功，则将审核结果更新至数据库中，否则不修改数据库中的审核状态，直接提示审核失败的原因
                    if ("0".equals(state))
                    {
                        changeCreativeAuditStatus(creativeId, StatusConstant.CREATIVE_AUDIT_SUCCESS, "");
                    }
                    else if ("1".equals(state))
                    {
                        changeCreativeAuditStatus(creativeId, StatusConstant.CREATIVE_AUDIT_WATING, "");
                    }
                    else if ("2".equals(state))
                    {
                        changeCreativeAuditStatus(creativeId, StatusConstant.CREATIVE_AUDIT_FAILURE, org.apache.commons.lang3.StringUtils.join(refuseReason,  ", "));
                    }
                }
                else
                {
                    throw new IllegalStatusException(AuditErrorConstant.BAIDU_CREATIVE_SYNC_ERROR_REASON + AuditErrorConstant.COMMON_RESPONSE_PARSE_FAIL);
                }
            }
        }
        else 
        {
            throw new IllegalStatusException(AuditErrorConstant.BAIDU_CREATIVE_SYNC_ERROR_REASON + AuditErrorConstant.COMMON_REQUEST_SENT_FAIL);
        }
    }
    
    /**
     * 修改广告主的审核信息，包括审核值、审核状态、审核信息。
     * @param auditId  审核ID，主键
     * @param status   审核状态
     * @param auditValue   审核值
     * @param message  审核信息
     */
    private void updateAdvertiserAuditInfo(String auditId, String status, String auditValue, String message)
    {
        AdvertiserAuditModel tmpRecord = new AdvertiserAuditModel();
        
        tmpRecord.setId(auditId);
        if (!StringUtils.isEmpty(status))
        {
            tmpRecord.setStatus(status);
        }
        if (!StringUtils.isEmpty(auditValue))
        {
            tmpRecord.setAuditValue(auditValue);
        }
        if (!StringUtils.isEmpty(message))
        {
            tmpRecord.setMessage(message);
        }
        
        advertiserAuditDao.updateByPrimaryKeySelective(tmpRecord);
    }

    /**
     * 根据广告主的行业ID，取得行业编号。
     * @param industryId   行业ID
     * @return
     */
    private String getCreativeTradeId(String industryId)
    {
        IndustryAdxModelExample industryAdxModelExample = new IndustryAdxModelExample();
        industryAdxModelExample.createCriteria().andAdxIdEqualTo(AdxKeyConstant.ADX_BAIDU_VALUE).andIndustryIdEqualTo(industryId);
        List<IndustryAdxModel> industryAdxList = industryAdxDao.selectByExample(industryAdxModelExample);
        
        if (industryAdxList != null && !industryAdxList.isEmpty())
        {
            return industryAdxList.get(0).getIndustryCode();
        }
        
        return null;
    }

    /**
     * 构建请求认证信息
     * @param adx
     * @return
     */
    private Map<String, Object> getAuthHeader(AdxModel adx)
    {
        String dspId = adx.getDspId();
        String token = adx.getSignKey();
        
        Map<String, Object> authHeader = new HashMap<String, Object>();
        
        authHeader.put("dspId", dspId);
        authHeader.put("token", token);
        
        return authHeader;
    }

    /**
     * 新增广告主
     * @param adx               pap_t_adx表的记录对象
     * @param advertiserModel   pap_t_advertiser表的记录对象 
     * @throws IOException 
     */
    private boolean createAdvertiserInBES(Long advertiserId, AdxModel adx, AdvertiserModel advertiserModel) throws IOException
    {
        return createOrUpdateAdvertiserInBES("add", advertiserId, adx, advertiserModel);
    }

    /**
     * 修改广告主
     * @param adx               pap_t_adx表的记录对象
     * @param advertiserModel   pap_t_advertiser表的记录对象 
     * @throws IOException 
     */
    private boolean updateAdvertiserInBES(Long advertiserId, AdxModel adx, AdvertiserModel advertiserModel) throws IOException
    {
        return createOrUpdateAdvertiserInBES("update", advertiserId, adx, advertiserModel);
    }

    /**
     * 新增或修改广告主
     * @param type              操作类型，add：新增、update：修改
     * @param adx               pap_t_adx表的记录对象
     * @param advertiserModel   pap_t_advertiser表的记录对象 
     * @throws IOException 
     */
    private boolean createOrUpdateAdvertiserInBES(String type, Long advertiserId, AdxModel adx, AdvertiserModel advertiserModel) throws IOException
    {
        String url = "";
        if (type.equals("add"))
        {
            url = adx.getAdvertiserAddUrl();
        }
        else
        {
            url = adx.getAdvertiserUpdateUrl();
        }
    
        Advertiser advertiser = new Advertiser();
        advertiser.setAdvertiserId(advertiserId);
        advertiser.setAdvertiserLiteName(advertiserModel.getName());
        advertiser.setAdvertiserName(advertiserModel.getLegalName());
        advertiser.setSiteName(advertiserModel.getSiteName());
        advertiser.setSiteUrl(advertiserModel.getSiteUrl());
        
        Map<String, Object> authHeader = getAuthHeader(adx);
        
        Advertiser[] advertisers = new Advertiser[]{ advertiser };
        
        BaiduRequest request = new BaiduRequest();
        request.setAuthHeader(authHeader);
        request.setRequest(advertisers);
        
        String jsonStrRequest = GlobalUtil.writeObject2Json(request, false);
        
        LOGGER.info("### PAP-Manager ### Create or Update advertiser [" + advertiserId + "] state from Baidu： url = " + url + ", params = " + jsonStrRequest + ", type = " + type);
        String jsonStrResponse = HttpClientUtil.getInstance().sendHttpPostJson(url, jsonStrRequest);
        LOGGER.debug("### PAP-Manager ### Create or Update advertiser [" + advertiserId + "] state from Baidu：result = " + jsonStrResponse);
        
        if (!StringUtils.isEmpty(jsonStrResponse))
        {
            // 检查响应对象中的status属性的值是否是0和1
            boolean respStatus = checkResponseStatus(jsonStrResponse);
            
            // 请求全部成功或部分成功
            if (respStatus)
            {
                return true;
            }
            else
            {
                JsonObject respObj = parser.parse(jsonStrResponse).getAsJsonObject();
                if (respObj != null && respObj.has("errors"))
                {
                    JsonArray errorsArray = respObj.get("errors").getAsJsonArray();
                    if (errorsArray.size() > 0)
                    {
                        JsonObject errorObj = errorsArray.get(0).getAsJsonObject();
                        throw new ThirdPartyAuditException(AuditErrorConstant.BAIDU_ADVERTISER_AUDIT_ERROR_REASON + errorObj.get("message").getAsString());
                    }
                    else
                    {
                        throw new ThirdPartyAuditException(AuditErrorConstant.BAIDU_ADVERTISER_AUDIT_ERROR_REASON + AuditErrorConstant.COMMON_RESPONSE_PARSE_FAIL);
                    }
                }
            }
        }
        
        return false;
    }

    /**
     * 上传广告主资质
     * @param advertiserId      此id为dsp系统的广告主id
     * @param adx               pap_t_adx表的记录对象
     * @param advertiserModel   pap_t_advertiser表的记录对象 
     * @throws IOException
     */
    private int uploadQulificationToBES(Long advertiserId, AdxModel adx, AdvertiserModel advertiserModel) throws IOException
    {
        return uploadOrUpdateQulificationToBES("upload", advertiserId, adx, advertiserModel);
    }

    /**
     * 编辑广告主资质
     * @param advertiserId      此id为dsp系统的广告主id
     * @param adx               pap_t_adx表的记录对象
     * @param advertiserModel   pap_t_advertiser表的记录对象 
     * @return 是否编辑成功，true：是，false：否
     * @throws IOException
     */
    private int updateQulificationInBES(Long advertiserId, AdxModel adx, AdvertiserModel advertiserModel) throws IOException
    {
        return uploadOrUpdateQulificationToBES("update", advertiserId, adx, advertiserModel);
    }

    /**
     * 上传或编辑广告主资质
     * @param adx               pap_t_adx表的记录对象
     * @param advertiserModel   pap_t_advertiser表的记录对象 
     * @throws IOException 
     */
    private int uploadOrUpdateQulificationToBES(String type, Long advertiserId, AdxModel adx, AdvertiserModel advertiserModel) throws IOException
    {
        String url = "";
        if ("upload".equals(type))
        {
            url = adx.getQualificationAddUrl();
        }
        else
        {
            url = adx.getQualificationUpdateUrl();
        }
        
        String qualificationPath = advertiserModel.getQualificationPath();
        String qualificationURL = urlPrefix + qualificationPath;
        
        List<String> imgUrls = new ArrayList<String>();
        imgUrls.add(qualificationURL);
        
        APIAdvertiserLicence licence = new APIAdvertiserLicence();
        licence.setType(1);
        licence.setName(advertiserModel.getLegalName());
        licence.setNumber(advertiserModel.getQualificationNo());
        licence.setValidDate(DateUtils.format(advertiserModel.getValidDate(), "yyyy-MM-dd"));
        licence.setImgUrls(imgUrls);
    
        APIAdvertiserQualificationUpload request = new APIAdvertiserQualificationUpload(advertiserId, licence, null);
        
        String jsonStrRequest = GlobalUtil.writeObject2Json(request, false);
        
        
        LOGGER.info("### PAP-Manager ### Upload or Update qulification [" + advertiserId + "] to Baidu： url = " + url + ", params = " + jsonStrRequest + ", type = " + type);
        String jsonStrResponse = HttpClientUtil.getInstance().sendHttpPostJson(url, jsonStrRequest);
        LOGGER.debug("### PAP-Manager ### Upload or Update qulification [" + advertiserId + "] to Baidu：result = " + jsonStrResponse);
        
        
        
        if (jsonStrResponse != null)
        {
            ObjectMapper objectMapper = new ObjectMapper();
            APIUploadQualificationResult operateResult = objectMapper.readValue(jsonStrResponse, APIUploadQualificationResult.class);
            
            if (operateResult.getStatus() == 0)
            {
                List<APIAdvertiserQualificationStatus> qualificationStatuses = operateResult.getQualificationStatuses();
                if (qualificationStatuses != null && !qualificationStatuses.isEmpty())
                {
                    int status = qualificationStatuses.get(0).getStatus();
                    
                    return status;
                }
            }
        }
        
        return 0;
    }

    /**
     * 根据广告主的UUID获得DSP端的广告主ID（广告主审核表中保存的由ADX返回的审核后的广告主ID），采用包装类型是为了判断是否能成功取出。
     * @param id 广告主ID
     * @return  DSP颁发的广告主ID
     */
    private Long getAdvertiserIDInDSP(String id)
    {
        Long advertiserID = null;
        
        AdvertiserAuditModelExample advertiserAuditExample = new AdvertiserAuditModelExample();
        advertiserAuditExample.createCriteria().andAdvertiserIdEqualTo(id).andAdxIdEqualTo(AdxKeyConstant.ADX_BAIDU_VALUE);
    
        List<AdvertiserAuditModel> advertiserAudits = advertiserAuditDao.selectByExample(advertiserAuditExample);
        if (advertiserAudits != null && !advertiserAudits.isEmpty())
        {
            AdvertiserAuditModel advertiserAuditModel = advertiserAudits.get(0);
            String auditValue = advertiserAuditModel.getAuditValue();
            
            if (StringUtils.isEmpty(auditValue))
            {
                throw new IllegalArgumentException(PhrasesConstant.ADVERTISER_AUDIT_ERROR);
            }
            
            advertiserID = Long.valueOf(auditValue);
        }
        return advertiserID;
    }

    /**
     * 修改创意的审核状态。
     * @param creativeId    创意ID
     * @param status        欲修改为的创意审核状态
     * @param message       审核信息
     */
    private void changeCreativeAuditStatus(String creativeId, String status, String message)
    {
        CreativeAuditModelExample example = new CreativeAuditModelExample();
        example.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_BAIDU_VALUE);
        
        CreativeAuditModel model = new CreativeAuditModel();
        model.setStatus(status);
        model.setMessage(message);
        creativeAuditDao.updateByExampleSelective(model, example);
    }



	/**
	 * 判断接口调用是否正确。<br>
	 * 获得API调用结果的状态码，判断这个状态码是否是0（成功）和1（部分成功），如果是则返回true；否则返回false。
	 * @param respStr 请求返回的JSON字符串
	 * @return true：调用成功；false：调用失败
	 */
    private boolean checkResponseStatus(String responseStr)
    {
        Integer status = parseResponseStatus(responseStr);
        if (status != null)
        {
            if (status == 0 || status == 1)
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 获得接口的调用状态码。<br>
     * 将API调用请求返回的JSON字符串转化为一个JsonObject，返回其中的status属性的值。
     * @param responseStr 请求返回的JSON字符串
     * @return
     */
    private Integer parseResponseStatus(String responseStr)
    {
        JsonObject respObj = parser.parse(responseStr).getAsJsonObject();
        if (respObj != null && respObj.has("status"))
        {
            return respObj.get("status").getAsInt();
        }
        return null;
    }
    
    /**
     * 获得指定创意的审核状态。
     * <pre>
     * state码表如下：
     * 0：检查通过
     * 1：待检查（默认）
     * 2：检查未通过
     * </pre>
     * @param responseStr   请求返回的JSON字符串
     * @return  HashMap，其中键名state，对应的键值为审核状态；键名refuseReson，对应的键值为创意被拒绝的原因。
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    private Map<String, String> getCreativeAuditStatus(String responseStr) throws JsonParseException, JsonMappingException, IOException
    {
        Map<String, String> result = new HashMap<>();
        
        int state = 1;
        List<String> refuseReason = new ArrayList<String>();
        
        JsonObject respObj = parser.parse(responseStr).getAsJsonObject();
        
        if (respObj != null && respObj.has("response"))
        {
            JsonArray responseArray = respObj.get("response").getAsJsonArray();
            
            if (responseArray.size() > 0)
            {
                JsonObject tmpObj = responseArray.get(0).getAsJsonObject();
                
                if (tmpObj.has("state"))
                {
                    state = tmpObj.get("state").getAsInt();
                }
                if (tmpObj.has("refuseReason"))
                {
                    JsonArray tmpArray = tmpObj.get("refuseReason").getAsJsonArray();
                    for (JsonElement tmpElement : tmpArray)
                    {
                        refuseReason.add(tmpElement.getAsString());
                    }
                }
                
                result.put("state", String.valueOf(state));
                result.put("refuseReason", org.apache.commons.lang3.StringUtils.join(refuseReason, ", "));
                
                return result;
            }
        }
        
        return null;
    }


    /**
     * 根据图片的ID，获得图片的Base64编码的字符串。
     * @param imageInfo 图片信息对象
     * @return 图片的字符串表示
     */
    private String getBase64ImageStrByImageId(ImageModel imageInfo)
    {
        if (imageInfo != null)
        {
            // 拼接上图片服务器前缀，获得完整的图片URL
            String src = urlPrefix + imageInfo.getPath();
            
            byte[] binaryData;
            try
            {
                binaryData = GlobalUtil.recoverImageFromUrl(src);
                return Base64.encodeBase64String(binaryData);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 新建一条创意审核信息或更新已有创意审核信息。
     * @param creativeId 创意ID
     * @param auditValue ADX返回的创意ID
     */
    private void insertOrUpdateToDB(String creativeId, Long auditValue)
    {
        // 根据创意ID 和 ADX ID查询指定创意的审核信息（二者组合可以确定唯一一条创意审核信息）
        CreativeAuditModelExample example = new CreativeAuditModelExample();
        example.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_BAIDU_VALUE);
        List<CreativeAuditModel> auditsInDB = creativeAuditDao.selectByExample(example);
        
        // 如果创意审核表信息已存在，更新数据库中原记录的状态为审核中
        if (auditsInDB != null && !auditsInDB.isEmpty())
        {
            for (CreativeAuditModel auditInDB : auditsInDB)
            {
                auditInDB.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
                auditInDB.setExpiryDate(new DateTime(new Date()).plusDays(CREATIVE_AUDIT_EXPIRE_DAYS).toDate());
                auditInDB.setAuditValue(String.valueOf(auditValue));
                creativeAuditDao.updateByPrimaryKeySelective(auditInDB);
            }
        }
        else // 否则在数据库中添加一条审核记录，设置状态为未审核。
        {
            CreativeAuditModel model = new CreativeAuditModel();
            model.setId(UUIDGenerator.getUUID());
            model.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
            model.setExpiryDate(new DateTime(new Date()).plusDays(CREATIVE_AUDIT_EXPIRE_DAYS).toDate());
            model.setAdxId(AdxKeyConstant.ADX_BAIDU_VALUE);
            model.setCreativeId(creativeId);
            model.setAuditValue(String.valueOf(auditValue));
            creativeAuditDao.insertSelective(model);
        }
    }

    private CreativeRichBean buildRelation(String creativeId)
    {
        // 创意审核数据
        CreativeAuditModelExample example = new CreativeAuditModelExample();
        example.createCriteria().andCreativeIdEqualTo(creativeId);
        List<CreativeAuditModel> creativeAuditList = creativeAuditDao.selectByExample(example);
        
        // 根据创意ID查询创意信息
        CreativeModel creativeInfo = creativeDao.selectByPrimaryKey(creativeId);
        
        // 根据活动ID查询项目ID
        CampaignModel campaignInfo = campaignDao.selectByPrimaryKey(creativeInfo.getCampaignId());
        String projectId = campaignInfo.getProjectId();
        
        // 根据活动ID查询落地页信息
        LandpageModel landpageInfo = landpageDao.selectByPrimaryKey(campaignInfo.getLandpageId());
        
        // 根据项目ID查询广告主ID
        ProjectModel projectInfo = projectDao.selectByPrimaryKey(projectId);
        String advertiserId = projectInfo.getAdvertiserId();
        
        // 根据广告主ID查询广告主
        AdvertiserModel advertiserInfo = advertiserDao.selectByPrimaryKey(advertiserId);
        
        CreativeRichBean result = new CreativeRichBean(creativeId, landpageInfo, creativeInfo, campaignInfo, projectInfo, advertiserInfo, creativeAuditList);
        return result ;
    }
}
