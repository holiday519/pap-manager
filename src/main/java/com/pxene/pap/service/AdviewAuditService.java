package com.pxene.pap.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

/**
 * 对接AdView相关广告主、创意的审核、同步服务。
 * @author ningyu
 */
@Service
public class AdviewAuditService extends AuditService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AdviewAuditService.class);
	
    private static final SimpleDateFormat DATATIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	
    /**
     * 创意审核信息的过期天数
     */
	private static final int CREATIVE_AUDIT_EXPIRE_DAYS = 120;
	
	/**
	 * 素材文件服务器的URL前缀
	 */
	private String urlPrefix;
	
	/**
	 * Gson JSON解析器
	 */
	private JsonParser parser = new JsonParser();
    
    private static Set<String> adType0Set = new HashSet<String>();
    
    private static Set<String> adType1Set = new HashSet<String>();
    
    static
    {
        String adType0 = "640x100;320x50;480x44;480x60;728x90;240x38;320x50;640x100;480x75;728x90;960x150";
        String adType1 = "300x300;600x600;1200x1200;300x300;300x250;600x600;600x500";
        
        String[] adType0Array = adType0.split(";");
        String[] adType1Array = adType1.split(";");
        
        for (String adType0Item : adType0Array)
        {
            adType0Set.add(adType0Item);
        }
        for (String adType1Item : adType1Array)
        {
            adType1Set.add(adType1Item);
        }
    }
    
    @Autowired
	public AdviewAuditService(Environment env) 
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
	}
	

	/**
	 * 提交广告主到ADX进行审核（更新广告主审核表数据）
	 */
	@Override
	@Transactional
    public void auditAdvertiser(String auditId) throws Exception
    {
        // 查询广告主审核信息
        AdvertiserAuditModel advertiserAudit = advertiserAuditDao.selectByPrimaryKey(auditId);
        if (advertiserAudit != null)
        {
            // 如果广告主审核信息不为空，则修改广告审核的状态：审核中
            advertiserAudit.setStatus(StatusConstant.ADVERTISER_AUDIT_WATING);
            
            // DSP 侧内部的广告主ID，需要保证DSP内部不重复，由于ADX要求是int值，因此不能用UUID。
            int auditValue = RandomUtils.nextInt();
            advertiserAudit.setAuditValue(String.valueOf(auditValue));
            
            // 更新数据库信息
            advertiserAuditDao.updateByPrimaryKeySelective(advertiserAudit);
        }
    }

	/**
	 * 查询广告主在ADX的审核状态（更新广告主审核表状态）
	 */
	@Override
	@Transactional
    public void synchronizeAdvertiser(String auditId) throws Exception
    {
        // 查询广告主审核信息
        AdvertiserAuditModel advertiserAudit = advertiserAuditDao.selectByPrimaryKey(auditId);
       
        if (advertiserAudit != null)
        {
            // 如果广告主审核信息不为空，则修改广告主审核表状态：审核通过
            advertiserAudit.setStatus(StatusConstant.ADVERTISER_AUDIT_SUCCESS);
            
            // 更新广告主审核表数据
            advertiserAuditDao.updateByPrimaryKey(advertiserAudit);
        }
    }

	/**
	 * 提交创意到AdView进行审核
	 */
	@Override
	@Transactional(dontRollbackOn = IllegalStatusException.class)
    public void auditCreative(String creativeId) throws Exception
    {
        // 根据创意ID查询创意信息
        CreativeModel creative = creativeDao.selectByPrimaryKey(creativeId);
        
        // 判断创意是否存在
        if (creative == null)
        {
            throw new ResourceNotFoundException(PhrasesConstant.CREATIVE_NOT_FOUND);
        }
        
        // AdView的ADX信息
        AdxModel adx = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_ADVIEW_VALUE);
        String uploadURL = adx.getCreativeAddUrl();                     // 创意审核URL
        String accessKey = adx.getSignKey();                            // AdView下发给合作伙伴授权使用该API的密钥。
        String userId = adx.getDspName();                               // 由合作伙伴在 AdView 平台注册，并且通过 AdView 审核的账号。（审核方式是线下审核。）
        Integer channel = Integer.valueOf(adx.getDspId());              // 渠道编号，由AdView分配，蓬景为75
        String token = getToken(userId, accessKey);                     // 验证信息
        
        // 查询出创意的相关信息
        CreativeRichBean richCreative = buildRelation(creativeId);
        
        // 查询出创意的审核信息
        String auditValue = null;
        CreativeAuditModelExample example = new CreativeAuditModelExample();
        example.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_ADVIEW_VALUE);
        List<CreativeAuditModel> creativeAuditList = creativeAuditDao.selectByExample(example);
        if (creativeAuditList != null && creativeAuditList.size() > 0)
        {
            auditValue = creativeAuditList.get(0).getAuditValue();
        }
        if (auditValue == null) {
        	auditValue = String.valueOf(RandomUtils.nextInt());
        }
        // 广告创意素材若为审核通过状态，创意信息不会改变。因此，当编辑完某创意之后，需要重新生成一个表示创意ID的originalityId
//        String originalityId = null;
//        if (auditValue != null)
//        {
//            originalityId = auditValue;
//        }
//        else
//        {
//            originalityId = UUIDGenerator.getUUID();
//        }
        
        // 广告主信息
        AdvertiserModel advertiserModel = richCreative.getAdvertiserInfo();
        String domainName = advertiserModel.getSiteUrl();                   // 广告主公司官网地址
        String industryId = advertiserModel.getIndustryId();                // 行业ID
        String businessType = getCreativeTradeId(industryId);               // 行业类型
        
        // 活动信息
//        CampaignModel campaignInfo = richCreative.getCampaignInfo();
//        String campaignId = campaignInfo.getId();       // 活动ID
//        String campaignName = campaignInfo.getName();   // 活动名称
        // 查询审核信息
        AdvertiserAuditModelExample advertiserAuditEx = new AdvertiserAuditModelExample();
        advertiserAuditEx.createCriteria().andAdvertiserIdEqualTo(advertiserModel.getId()).andAdxIdEqualTo(AdxKeyConstant.ADX_ADVIEW_VALUE);
        List<AdvertiserAuditModel> advertiserAudits = advertiserAuditDao.selectByExample(advertiserAuditEx);
        String aderAuditValue = null; 
        if (!advertiserAudits.isEmpty()) {
        	aderAuditValue = advertiserAudits.get(0).getAuditValue();
        } else {
        	throw new ThirdPartyAuditException();
        }
        
        // 创意类型
        String creativeType = creative.getType();
        
        // 创意素材ID
        String materialId = creative.getMaterialId();
        
        // 落地页信息
        LandpageModel landpageInfo = richCreative.getLandpageInfo();
        
        // 构建请求对象
        JsonObject request = new JsonObject();
        request.addProperty("userId", userId);
        request.addProperty("channel", channel);
        request.addProperty("token", token);
//        request.addProperty("adId", campaignId);
//        request.addProperty("adName", campaignName);
        request.addProperty("adId", aderAuditValue);
        request.addProperty("adName", advertiserModel.getName());
        request.addProperty("domainName", domainName);
        request.addProperty("system", -1);
        request.addProperty("businessType", businessType);
        request.addProperty("startDate", DATATIME_FORMATTER.format(new Date()));
        
        JsonArray orig = new JsonArray();
        
        JsonObject origObj = new JsonObject();
        origObj.addProperty("originalityId", auditValue);    // 创意 id，唯一标识该广告创意。
        origObj.addProperty("clickURL", landpageInfo.getUrl()); // 点击跳转落地页
        
        if (CodeTableConstant.CREATIVE_TYPE_IMAGE.equals(creativeType))         // 如果创意是图片，则读取图片素材表，取得图片ID，再根据图片ID，取得详细的图片信息
        {
            ImageMaterialModel imageMaterial = imageMaterialDao.selectByPrimaryKey(materialId);
            String imageId = imageMaterial.getImageId();
            
            // 根据图片ID取得图片素材信息
            ImageModel imageInfo = imageDao.selectByPrimaryKey(imageId);
            int width = imageInfo.getWidth();
            int height = imageInfo.getHeight();
            String concatedImageName = width + "x" + height;
            String imageURL = urlPrefix + imageInfo.getPath();
            
            JsonObject imageObj = new JsonObject();
            imageObj.addProperty(concatedImageName, imageURL);
            
            origObj.addProperty("originalityName", concatedImageName);
            origObj.addProperty("originalityDesc", "");
            origObj.addProperty("type", 1);
            origObj.addProperty("adType", getAdType(concatedImageName));
            origObj.add("images", imageObj);
        }
        else if (CodeTableConstant.CREATIVE_TYPE_INFOFLOW.equals(creativeType)) //如果创意是信息流，则读取信息流素材表，取得第一张图片ID
        {
            InfoflowMaterialModel infoflowMaterial = infoflowMaterialDao.selectByPrimaryKey(materialId);
            
            String title = infoflowMaterial.getTitle();
            String descp = infoflowMaterial.getDescription();
            String ctaDescription = infoflowMaterial.getCtaDescription();
            
            String iconId = infoflowMaterial.getIconId();
            String image1Id = infoflowMaterial.getImage1Id();
            String image2Id = infoflowMaterial.getImage2Id();
            String image3Id = infoflowMaterial.getImage3Id();
            String image4Id = infoflowMaterial.getImage4Id();
            String image5Id = infoflowMaterial.getImage5Id();
            
            String[] imageIds = new String[]{iconId, image1Id, image2Id, image3Id, image4Id, image5Id};
            JsonArray nativePic = buildNativePic(imageIds);
            
            JsonArray nativeData = buildNativeData(title, descp, ctaDescription);
            
            origObj.addProperty("originalityName", title);
            origObj.addProperty("originalityDesc", descp);
            origObj.addProperty("type", 4);
            origObj.add("nativePic", nativePic);
            origObj.add("nativeText", new JsonArray());
            origObj.add("nativeData", nativeData);
        }
        else
        {
            throw new IllegalArgumentException();
        }
        
        orig.add(origObj);
        request.add("orig", orig);
        
        // 发送HTTP POST请求
        LOGGER.debug("### PAP-Manager ### Audit creative [" + creativeId + "] to AdView： url = " + uploadURL + ", params = " + request);
        String jsonStrResponse = HttpClientUtil.getInstance().sendHttpPostJson(uploadURL, request.toString());
        LOGGER.debug("### PAP-Manager ### Synchronize creative [" + creativeId + "] state from AdView = " + jsonStrResponse);

        
        // 如果请求发送成功且应答响应成功，则将审核信息插入或更新至数据库中，否则提示审核失败的原因
        if (!StringUtils.isEmpty(jsonStrResponse))
        {
            // 检查status是否为0
            boolean respFlag = checkResponseStatus(jsonStrResponse);
            if (respFlag)
            {
                insertOrUpdateToDB(creativeId, auditValue);
            }
            else
            {
                throw new IllegalStatusException(parseResponseErrorInfo(jsonStrResponse));
            }
        }
        else 
        {
            throw new IllegalStatusException(AuditErrorConstant.ADVIEW_CREATIVE_AUDIT_ERROR_REASON + AuditErrorConstant.COMMON_REQUEST_SENT_FAIL);
        }
    }
	
	/**
     * 查询AdView对某创意的审核结果
     */
    @Override
    @Transactional(dontRollbackOn = IllegalStatusException.class)
    public void synchronizeCreative(String creativeId) throws Exception
    {
        // 根据创意ID查询创意信息
        CreativeModel creativeModel = creativeDao.selectByPrimaryKey(creativeId);
        
        // 判断创意是否存在
        if (creativeModel == null)
        {
            throw new ResourceNotFoundException();
        }
        
        // 判断“创意审核表”中是否存在该创意的审核信息
        CreativeAuditModelExample example = new CreativeAuditModelExample();
        example.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_ADVIEW_VALUE);
        List<CreativeAuditModel> modelList = creativeAuditDao.selectByExample(example);
        if (modelList == null || modelList.isEmpty())
        {
            throw new ResourceNotFoundException();
        }
        
        String auditValue = modelList.get(0).getAuditValue();
        
        // AdView的ADX信息
        AdxModel adx = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_ADVIEW_VALUE);
        String checkURL = adx.getCreativeQueryUrl();                    // 创意审核URL
        String accessKey = adx.getSignKey();                            // AdView下发给合作伙伴授权使用该API的密钥。
        String userId = adx.getDspName();                               // 由合作伙伴在 AdView 平台注册，并且通过 AdView 审核的账号。（审核方式是线下审核。）
        Integer channel = Integer.valueOf(adx.getDspId());              // 渠道编号，由AdView分配，蓬景为75
        String token = getToken(userId, accessKey);                     // 验证信息
        
        // 构建请求参数
        JsonObject request = new JsonObject();
        request.addProperty("channel", channel);
        request.addProperty("userId", userId);
        request.addProperty("token", token);
        request.addProperty("originalityId", auditValue);
    
        // 发送HTTP POST请求
        LOGGER.debug("### PAP-Manager ### Synchronize creative [" + creativeId + "] to AdView： url = " + checkURL + ", params = " + request);
        String jsonStrResponse = HttpClientUtil.getInstance().sendHttpPostJson(checkURL, request.toString());
        LOGGER.debug("### PAP-Manager ### Synchronize creative [" + creativeId + "] state from AdView = " + jsonStrResponse);

        // 如果请求发送成功且应答响应成功，则将审核结果更新至数据库中，否则不修改数据库中的审核状态，直接提示审核失败的原因
        if (!StringUtils.isEmpty(jsonStrResponse))
        {
            // 检查status是否为1
            boolean respFlag = checkResponseStatus(jsonStrResponse);
            if (respFlag)
            {
                Integer origStatus = getOrigStatus(jsonStrResponse, creativeId);
                if (origStatus != null)
                {
                    if (origStatus == 1)        // 未审核
                    {
                        changeCreativeAuditStatus(creativeId, StatusConstant.CREATIVE_AUDIT_WATING, "");
                    }
                    else if (origStatus == 2)   // 审核通过
                    {
                        changeCreativeAuditStatus(creativeId, StatusConstant.CREATIVE_AUDIT_SUCCESS, "");
                    }
                    else if (origStatus == 3)   // 审核未通过
                    {
                        changeCreativeAuditStatus(creativeId, StatusConstant.CREATIVE_AUDIT_FAILURE, "");
                    }
                }
            }
            else
            {
                throw new IllegalStatusException(parseResponseErrorInfo(jsonStrResponse));
            }
        }
        else 
        {
            throw new IllegalStatusException(AuditErrorConstant.ADVIEW_CREATIVE_SYNC_ERROR_REASON + AuditErrorConstant.COMMON_REQUEST_SENT_FAIL);
        }
    }
    
    /**
	 * 广告位类型：0横幅(条)，1插屏，4开屏
	 * @param imageWidthHeightStr
	 * @return
	 */
    private String getAdType(String imageWidthHeightStr)
    {
	    if (adType0Set.contains(imageWidthHeightStr))
	    {
	    	return "0";
	    }
	    else if (adType1Set.contains(imageWidthHeightStr))
        {
	    	return "1";
        }
	    else
	    {
	    	return "4";
	    }
    }
	
	/**
     * 解析创意的审核状态。<br>
     * 状态：1 --未审核，2 --审核通过，3 --审核未通过
     * @param respStr       接口的响应对址
     * @param creativeId    创意ID
     * @return 指定创意的审核状态
     */
    private Integer getOrigStatus(String respStr, String creativeId)
    {
        JsonObject respObj = parser.parse(respStr).getAsJsonObject();
        
        if (respObj != null && respObj.has("data") && respObj.get("data").isJsonArray())
        {
            JsonArray data = respObj.get("data").getAsJsonArray();
            for (JsonElement item : data)
            {
                JsonObject tmpObj = item.getAsJsonObject();
                int status = tmpObj.get("status").getAsInt();
                String originalityId = tmpObj.get("originalityId").getAsString();
                
                if (creativeId.equals(originalityId))
                {
                    return status;
                }
            }
        }
        return null;
    }

    private JsonArray buildNativePic(String[] imageIds)
	{
	    JsonArray result = new JsonArray();
	    if (imageIds != null && imageIds.length > 0)
        {
	        for (String imageId : imageIds)
            {
	            // 根据图片ID取得图片素材信息
	            ImageModel imageInfo = imageDao.selectByPrimaryKey(imageId);
	            if (imageInfo != null)
	            {
	                JsonObject obj = new JsonObject();
	                obj.addProperty("size", imageInfo.getWidth() + "x" + imageInfo.getHeight());
	                obj.addProperty("url", urlPrefix + imageInfo.getPath());    // 拼接上图片服务器前缀，获得完整的图片URL
	                
	                result.add(obj);
	            }
            }
	        return result;
        }
	    return null;
	}

    private JsonArray buildNativeData(String title, String descp, String ctaDescription)
    {
        JsonArray result = new JsonArray();
        
        if (title != null) {
        	JsonObject titleObj = new JsonObject();
            titleObj.addProperty("type", "2");
            titleObj.addProperty("data", title);
            result.add(titleObj);
        }
        
        if (descp != null) {
        	JsonObject descpObj = new JsonObject();
            descpObj.addProperty("type", "10");
            descpObj.addProperty("data", descp);
            result.add(descpObj);
        }
        
        if (ctaDescription != null) {
        	JsonObject ctaDescpObj = new JsonObject();
            ctaDescpObj.addProperty("type", "12");
            ctaDescpObj.addProperty("data", ctaDescription);
            result.add(ctaDescpObj);
        }
        
        return result;
    }

    /**
	 * 生成验证信息，MD5(userId+accessKey)。注：采用  MD5 32 位小写加密。
	 * @param userId       由合作伙伴在 AdView 平台注册，并且通过 AdView 审核的账号。
	 * @param accessKey    accessKey 为 AdView 下发给合作伙伴授权使用该 API 的密钥
	 * @return
	 */
	private String getToken(String userId, String accessKey)
    {
	    return GlobalUtil.MD5(userId + accessKey);
    }

	/**
     * 根据广告主的行业ID，取得行业编号。
     * @param industryId   行业ID
     * @return
     */
    private String getCreativeTradeId(String industryId)
    {
        IndustryAdxModelExample industryAdxModelExample = new IndustryAdxModelExample();
        industryAdxModelExample.createCriteria().andAdxIdEqualTo(AdxKeyConstant.ADX_ADVIEW_VALUE).andIndustryIdEqualTo(industryId);
        List<IndustryAdxModel> industryAdxList = industryAdxDao.selectByExample(industryAdxModelExample);
        
        if (industryAdxList != null && !industryAdxList.isEmpty())
        {
            return industryAdxList.get(0).getIndustryCode();
        }
        
        return null;
    }

    
    /**
     * 修改创意审核状态
     * @param creativeId    创意ID
     * @param status        欲修改为的创意审核状态
     */
    private void changeCreativeAuditStatus(String creativeId, String status, String message)
    {
        CreativeAuditModelExample example = new CreativeAuditModelExample();
        example.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_ADVIEW_VALUE);
        
        CreativeAuditModel model = new CreativeAuditModel();
        model.setStatus(status);
        model.setMessage(message);
        creativeAuditDao.updateByExampleSelective(model, example);
    }


    /**
	 * 解析响应请求中的错误信息
	 * @param respStr  请求返回的JSON字符串
	 * @return
	 */
	private String parseResponseErrorInfo(String respStr)
	{
	    JsonObject respObj = parser.parse(respStr).getAsJsonObject();
        if (respObj != null && respObj.has("msg") && !StringUtils.isEmpty(respObj.get("msg").getAsString()))
        {
            return respObj.get("msg").getAsString();
        }
        else
        {
            return AuditErrorConstant.COMMON_RESPONSE_PARSE_FAIL;
        }
	}
	
	/**
	 * 检查请求响应结果是否正确
	 * @param respStr 请求返回的JSON字符串
	 * @return
	 */
    private boolean checkResponseStatus(String respStr)
    {
        boolean respFlag = false;
        JsonObject respObj = parser.parse(respStr).getAsJsonObject();
        
        if (respObj != null && respObj.has("ret") && respObj.get("ret").getAsInt() == 0)
        {
            respFlag = true;
        }
        
        return respFlag;
    }
    
    /**
     * 新建一条创意审核信息或更新已有创意审核信息。
     * @param creativeId 创意ID
     * @param auditValue ADX返回的创意ID
     */
    private void insertOrUpdateToDB(String creativeId, String auditValue)
    {
        // 根据创意ID 和 ADX ID查询指定创意的审核信息（二者组合可以确定唯一一条创意审核信息）
        CreativeAuditModelExample example = new CreativeAuditModelExample();
        example.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_ADVIEW_VALUE);
        List<CreativeAuditModel> auditsInDB = creativeAuditDao.selectByExample(example);
        
        // 如果创意审核表信息已存在，更新数据库中原记录的状态为审核中
        if (auditsInDB != null && !auditsInDB.isEmpty())
        {
            for (CreativeAuditModel auditInDB : auditsInDB)
            {
                auditInDB.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
                auditInDB.setExpiryDate(new DateTime(new Date()).plusDays(CREATIVE_AUDIT_EXPIRE_DAYS).toDate());
                auditInDB.setAuditValue(auditValue);
                creativeAuditDao.updateByPrimaryKeySelective(auditInDB);
            }
        }
        else // 否则在数据库中添加一条审核记录，设置状态为未审核。
        {
            CreativeAuditModel model = new CreativeAuditModel();
            model.setId(UUIDGenerator.getUUID());
            model.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
            model.setExpiryDate(new DateTime(new Date()).plusDays(CREATIVE_AUDIT_EXPIRE_DAYS).toDate());
            model.setAdxId(AdxKeyConstant.ADX_ADVIEW_VALUE);
            model.setCreativeId(creativeId);
            model.setAuditValue(auditValue);
            creativeAuditDao.insertSelective(model);
        }
    }

	private CreativeRichBean buildRelation(String creativeId)
	{
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
        
        CreativeRichBean result = new CreativeRichBean(creativeId, landpageInfo, creativeInfo, campaignInfo, projectInfo, advertiserInfo);
        return result ;
	}
}
