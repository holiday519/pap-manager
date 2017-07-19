package com.pxene.pap.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.exception.ThirdPartyAuditException;
import com.pxene.pap.repository.basic.IndustryDao;

/**
 * 广点通的相关审核、同步服务
 * @author zhengyi
 */
@Service
public class TencentAuditService extends AuditService
{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TencentAuditService.class);
    /**
     * 创意审核信息的过期天数
     */
	private static final int CREATIVE_AUDIT_EXPIRE_DAYS = 60;
	
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
	private IndustryDao industryDao;
    
    @Autowired
	public TencentAuditService(Environment env) 
	{
		super(env);
        String uploadMode = env.getProperty(ConfKeyConstant.FILESERVER_MODE);
        if ("local".equals(uploadMode)) {
        	urlPrefix = env.getProperty(ConfKeyConstant.FILESERVER_LOCAL_URL_PREFIX);
        } else {
        	urlPrefix = env.getProperty(ConfKeyConstant.FILESERVER_REMOTE_URL_PREFIX);
        }
        
        clkURLPrefix = env.getProperty(ConfKeyConstant.CLICK_URL_PREFIX);
        impURLPrefix = env.getProperty(ConfKeyConstant.IMPRESSION_URL_PREFIX);
	}
	
    public static class AUDIT_STATUS {
    	private static final String SUCCESS = "APPROVED";
    	private static final String FAILURE = "REJECTED";
    }
    
    private static Map<String, Integer> creativeImageSpecs = new HashMap<String, Integer>();
    static {
    	creativeImageSpecs.put("640x960", 79);
    	creativeImageSpecs.put("320x480", 80);
    	creativeImageSpecs.put("640x1136", 120);
    	creativeImageSpecs.put("300x250", 58);
    	creativeImageSpecs.put("600x500", 59);
    	creativeImageSpecs.put("640x960", 113);
    	creativeImageSpecs.put("320x480", 114);
    	creativeImageSpecs.put("640x100", 10);
    	creativeImageSpecs.put("240x38", 28);
    	creativeImageSpecs.put("480x75", 31);
    	creativeImageSpecs.put("320x50", 35);
    }
    
    private static Map<String, Integer> creativeInfoflowSpecs = new HashMap<String, Integer>();
    static {
    	creativeInfoflowSpecs.put("2+2|240x180", 184);
    	creativeInfoflowSpecs.put("2+2|640x288", 210);
    	creativeInfoflowSpecs.put("4+2|240x180", 285);
    	creativeInfoflowSpecs.put("1+2|640x1136", 207);
    	creativeInfoflowSpecs.put("1+2|640x960", 208);
    	creativeInfoflowSpecs.put("1+2|320x480", 212);
    	creativeInfoflowSpecs.put("2+2|1200x800", 147);
    	creativeInfoflowSpecs.put("2+2|1280x720", 148);
    	creativeInfoflowSpecs.put("2+2|1200x627", 149);
    	creativeInfoflowSpecs.put("2+2|800x1200", 150);
    	creativeInfoflowSpecs.put("1+2|72*72", 70);
    }
    

	@Override
	@Transactional
    public void auditAdvertiser(String auditId) {
        // 查询广告主审核信息
		AdvertiserAuditModel advertiserAudit = advertiserAuditDao.selectByPrimaryKey(auditId);
		if (advertiserAudit != null) {
			String advertiserId = advertiserAudit.getAdvertiserId();
			AdvertiserModel advertiser = advertiserDao.selectByPrimaryKey(advertiserId);
			String legalName = advertiser.getLegalName();
			String siteUrl = advertiser.getSiteUrl();
			String industryId = advertiser.getIndustryId();
			String licensePath = advertiser.getLicensePath();
			// 查询行业编码
			IndustryAdxModelExample industryAdxEx = new IndustryAdxModelExample();
			industryAdxEx.createCriteria().andAdxIdEqualTo(AdxKeyConstant.ADX_TENCENT_VALUE).andIndustryIdEqualTo(industryId);
			List<IndustryAdxModel> industryAdxs = industryAdxDao.selectByExample(industryAdxEx);
			if (industryAdxs.isEmpty()) {
				return;
			}
			String industryCode = industryAdxs.get(0).getIndustryCode();
			
			JsonObject requestBody = new JsonObject();
			JsonArray advertiserJsons = new JsonArray();
			JsonObject advertiserJson = new JsonObject();
			advertiserJson.addProperty("advertiser_id", advertiserId);
			advertiserJson.addProperty("name", legalName);
			advertiserJson.addProperty("homepage", siteUrl);
			advertiserJson.addProperty("industry_id", Integer.parseInt(industryCode));
			JsonArray qualificationJsons = new JsonArray();
			JsonObject qualificationJson = new JsonObject();
			qualificationJson.addProperty("file_url", urlPrefix + licensePath);
			qualificationJsons.add(qualificationJson);
			advertiserJson.add("qualifications", qualificationJsons);
			advertiserJsons.add(advertiserJson);
			requestBody.add("data", advertiserJsons);
			
			AdxModel adx = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_TENCENT_VALUE);
			String auditValue = advertiserAudit.getAuditValue();
			String respStr = null;
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Authorization", adx.getSignKey());
			if (auditValue == null) {
				// 调用创建接口
				String addUrl = adx.getAdvertiserAddUrl();
				respStr = HttpClientUtil.getInstance().sendHttpPostJson(addUrl, requestBody.getAsString(), headers);
			} else {
				// 调用修改接口
				String updateUrl = adx.getAdvertiserUpdateUrl();
				respStr = HttpClientUtil.getInstance().sendHttpPostJson(updateUrl, requestBody.getAsString(), headers);
			}
			
	        if (!StringUtils.isEmpty(respStr)) {
	            if (isResponseSuccess(respStr)) {
	            	// 成功
	            	advertiserAudit.setStatus(StatusConstant.ADVERTISER_AUDIT_WATING);
					advertiserAudit.setAuditValue(advertiserId);
					advertiserAuditDao.updateByPrimaryKey(advertiserAudit);
	            } else {
	            	// 失败
	                throw new ThirdPartyAuditException(parseResponseError(respStr));
	            }
	        } else {
	            throw new ThirdPartyAuditException(AuditErrorConstant.TENCENT_ADVERTISER_AUDIT_ERROR_REASON + AuditErrorConstant.COMMON_REQUEST_SENT_FAIL);
	        }
		}
    }


	@Override
	@Transactional
    public void synchronizeAdvertiser(String auditId) throws Exception {
		// 查询广告主审核信息
		AdvertiserAuditModel advertiserAudit = advertiserAuditDao.selectByPrimaryKey(auditId);
		if (advertiserAudit != null) {
			String advertiserId = advertiserAudit.getAdvertiserId();
			JsonObject requestBody = new JsonObject();
			JsonArray advertiserIds = new JsonArray();
			advertiserIds.add(advertiserId);
			requestBody.add("data", advertiserIds);
			
			AdxModel adx = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_TENCENT_VALUE);
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Authorization", adx.getSignKey());
			String respStr = HttpClientUtil.getInstance().sendHttpPostJson(adx.getQualificationQueryUrl(), requestBody.getAsString(), headers);
			if (!StringUtils.isEmpty(respStr)) {
	            if (isResponseSuccess(respStr)) {
	            	JsonObject respObj = parser.parse(respStr).getAsJsonObject();
	            	if (respObj != null && respObj.has("data") && respObj.get("data").isJsonArray()) {
	            		JsonArray dataJsons = respObj.get("data").getAsJsonArray();
	            		if (dataJsons.size() > 0) {
	            			JsonObject dataJson = dataJsons.get(0).getAsJsonObject();
	            			if (dataJson != null && dataJson.has("data") && dataJson.get("data").isJsonObject()) {
	            				JsonObject data = dataJson.get("data").getAsJsonObject();
	            				String status = data.get("review_status").getAsString();
	            				String msg = data.get("review_msg").getAsString();
	            				if (AUDIT_STATUS.SUCCESS.equals(status)) {
	            					advertiserAudit.setStatus(StatusConstant.ADVERTISER_AUDIT_SUCCESS);
	            				} else if (AUDIT_STATUS.FAILURE.equals(status)) {
	            					advertiserAudit.setStatus(StatusConstant.ADVERTISER_AUDIT_FAILURE);
	            					advertiserAudit.setMessage(msg);
	            				} else {
	            					advertiserAudit.setStatus(StatusConstant.ADVERTISER_AUDIT_WATING);
	            				}
	            				advertiserAuditDao.updateByPrimaryKey(advertiserAudit);
	            			}
	            		}
	            	}
	            } else {
	            	// 失败
	                throw new ThirdPartyAuditException(parseResponseError(respStr));
	            }
			} else {
				throw new ThirdPartyAuditException(AuditErrorConstant.TENCENT_ADVERTISER_SYNC_ERROR_REASON + AuditErrorConstant.COMMON_REQUEST_SENT_FAIL);
			}
		}
    }

	
	@Override
	@Transactional
    public void auditCreative(String creativeId) throws Exception {
        // 根据创意ID查询创意信息
        CreativeModel creativeModel = creativeDao.selectByPrimaryKey(creativeId);
        // 判断创意是否存在
        if (creativeModel == null) {
            throw new ResourceNotFoundException(PhrasesConstant.CREATIVE_NOT_FOUND);
        }
        
        JsonObject requestBody = new JsonObject();
		JsonArray creativeJsons = new JsonArray();
		JsonObject creativeJson = new JsonObject();
        
        // 汽车之家的ADX信息
        AdxModel adx = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_TENCENT_VALUE);
        String addUrl = adx.getCreativeAddUrl();
        String updateUrl = adx.getCreativeUpdateUrl();
        String signKey = adx.getSignKey();
        String iURL = adx.getIurl();
        String cURL = adx.getCurl();
        
        // 查询出创意的相关信息
        CreativeRichBean richCreative = buildRelation(creativeId);
        
        // 广告主信息
        AdvertiserModel advertiserModel = richCreative.getAdvertiserInfo();
        
        String advertiserId = advertiserModel.getId();
        creativeJson.addProperty("advertiser_id", advertiserId);
        
        // 创意ID
        creativeJson.addProperty("creative_id", creativeId);
        
        // 落地页信息
        LandpageModel landpageInfo = richCreative.getLandpageInfo();
        String landpageURL = URLEncoder.encode(landpageInfo.getUrl(), StandardCharsets.UTF_8.displayName());
        creativeJson.addProperty("landing_page", landpageURL);
        creativeJson.addProperty("target_url", clkURLPrefix + cURL + "&curl=" + landpageURL);
        creativeJson.addProperty("monitor_url1", impURLPrefix + iURL);
        
        // 创意类型
        String creativeType = creativeModel.getType();
        // 素材ID
        String materialId = creativeModel.getMaterialId();
        // 如果创意是图片，则读取图片素材表，取得图片ID，再根据图片ID，取得详细的图片信息
        if (CodeTableConstant.CREATIVE_TYPE_IMAGE.equals(creativeType)) {
            ImageMaterialModel imageMaterial = imageMaterialDao.selectByPrimaryKey(materialId);
            if (imageMaterial != null) {
            	 String imageId = imageMaterial.getImageId();
                 ImageModel image = imageDao.selectByPrimaryKey(imageId);
                 if (image != null) {
                	 int width = image.getWidth();
                     int height = image.getHeight();
                     int spec = creativeImageSpecs.get(width + "x" + height);
                     creativeJson.addProperty("creative_spec", spec);
                     creativeJson.addProperty("multimedia1_file_url", urlPrefix + image.getPath());
                 }
            }
           
        } else if (CodeTableConstant.CREATIVE_TYPE_INFOFLOW.equals(creativeType)) {
            InfoflowMaterialModel infoflowMaterial = infoflowMaterialDao.selectByPrimaryKey(materialId);
            // 文字数量
            int textNum = 0;
            // 图片数量
            int imgNum = 0;
            // 首图宽高
            String img1WH = "";
            
            String title = infoflowMaterial.getTitle();
            if (!StringUtils.isEmpty(title)) {
            	textNum ++;
            	creativeJson.addProperty("title", title);
            }
            String descp = infoflowMaterial.getDescription();
            if (!StringUtils.isEmpty(descp)) {
            	textNum ++;
            	creativeJson.addProperty("description", descp);
            }
            String iconId = infoflowMaterial.getIconId();
            if (!StringUtils.isEmpty(iconId)) {
            	imgNum ++;
            	ImageModel image = imageDao.selectByPrimaryKey(iconId);
            	if (image != null) {
            		creativeJson.addProperty("multimedia2", urlPrefix + image.getPath());
            	}
            }
            String image1Id = infoflowMaterial.getImage1Id();
            if (!StringUtils.isEmpty(image1Id)) {
            	imgNum ++;
            	ImageModel image = imageDao.selectByPrimaryKey(image1Id);
            	img1WH = image.getWidth() + "x" + image.getHeight();
            	if (image != null) {
            		creativeJson.addProperty("multimedia1", urlPrefix + image.getPath());
            	}
            }
            String image2Id = infoflowMaterial.getImage2Id();
            if (!StringUtils.isEmpty(image2Id)) {
            	imgNum ++;
            	ImageModel image = imageDao.selectByPrimaryKey(image2Id);
            	if (image != null) {
            		creativeJson.addProperty("multimedia3", urlPrefix + image.getPath());
            	}
            }
            String image3Id = infoflowMaterial.getImage3Id();
            if (!StringUtils.isEmpty(image3Id)) {
            	imgNum ++;
            	ImageModel image = imageDao.selectByPrimaryKey(image3Id);
            	if (image != null) {
            		creativeJson.addProperty("multimedia4", urlPrefix + image.getPath());
            	}
            }
            
            int spec = creativeInfoflowSpecs.get(imgNum + "x" + textNum + "|" + img1WH);
            creativeJson.addProperty("creative_spec", spec);
            
//            String image4Id = infoflowMaterial.getImage4Id();
//            if (!StringUtils.isEmpty(image4Id)) {
//            	imgNum ++;
//            }
//            String image5Id = infoflowMaterial.getImage5Id();
//            if (!StringUtils.isEmpty(image5Id)) {
//            	imgNum ++;
//            }
            
        } else {
            throw new IllegalArgumentException();
        }
        
        creativeJsons.add(creativeJson);
        requestBody.add("data", creativeJsons);
        LOGGER.info("<=PAP-Manager=> tencent audit info = " + requestBody.toString());
        
        Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", signKey);
		
		// 判断是否审核过
		CreativeAuditModelExample creativeAuditEx = new CreativeAuditModelExample();
		creativeAuditEx.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_TENCENT_VALUE);
		List<CreativeAuditModel> creativeAudits = creativeAuditDao.selectByExample(creativeAuditEx);
		if (creativeAudits.isEmpty()) {
			// 未审核过
			String respStr = HttpClientUtil.getInstance().sendHttpPostJson(addUrl, requestBody.toString(), headers);
			if (!StringUtils.isEmpty(respStr)) {
				if (isResponseSuccess(respStr)) {
					CreativeAuditModel creativeAudit = new CreativeAuditModel();
					creativeAudit.setId(UUIDGenerator.getUUID());
					creativeAudit.setAdxId(AdxKeyConstant.ADX_TENCENT_VALUE);
					creativeAudit.setAuditValue(creativeId);
					creativeAudit.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
					creativeAudit.setExpiryDate(new DateTime(new Date()).plusDays(CREATIVE_AUDIT_EXPIRE_DAYS).toDate());
					creativeAuditDao.insertSelective(creativeAudit);
				} else {
					throw new ThirdPartyAuditException(parseResponseError(respStr));
				}
			} else {
				throw new ThirdPartyAuditException(AuditErrorConstant.TENCENT_CREATIVE_AUDIT_ERROR_REASON + AuditErrorConstant.COMMON_REQUEST_SENT_FAIL);
			}
		} else {
			// 审核过
			String respStr = HttpClientUtil.getInstance().sendHttpPostJson(updateUrl, creativeJson.toString(), headers);
			if (!StringUtils.isEmpty(respStr)) {
				if (isResponseSuccess(respStr)) {
					CreativeAuditModel creativeAudit = creativeAudits.get(0);
					creativeAudit.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
					creativeAudit.setExpiryDate(new DateTime(new Date()).plusDays(CREATIVE_AUDIT_EXPIRE_DAYS).toDate());
					creativeAuditDao.updateByPrimaryKeySelective(creativeAudit);
				} else {
					throw new ThirdPartyAuditException(parseResponseError(respStr));
				}
			} else {
				throw new ThirdPartyAuditException(AuditErrorConstant.TENCENT_CREATIVE_AUDIT_ERROR_REASON + AuditErrorConstant.COMMON_REQUEST_SENT_FAIL);
			}
		}
    }

    @Override
    @Transactional
    public void synchronizeCreative(String creativeId) throws Exception {
        // 根据创意ID查询创意信息
        CreativeModel creativeModel = creativeDao.selectByPrimaryKey(creativeId);
        
        // 判断创意是否存在
        if (creativeModel == null) {
            throw new ResourceNotFoundException();
        }
        
        // 判断“创意审核表”中是否存在该创意的审核信息
        CreativeAuditModelExample creativeAuditEx = new CreativeAuditModelExample();
        creativeAuditEx.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_TENCENT_VALUE);
        List<CreativeAuditModel> creativeAudits = creativeAuditDao.selectByExample(creativeAuditEx);
        if (creativeAudits == null || creativeAudits.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        CreativeAuditModel creativeAudit = creativeAudits.get(0);
        
        JsonObject requestBody = new JsonObject();
        JsonArray creativeJsons = new JsonArray();
        JsonObject creativeJson = new JsonObject();
        
        // 查询出创意的相关信息
        CreativeRichBean richCreative = buildRelation(creativeId);
        // 广告主信息
        AdvertiserModel advertiserModel = richCreative.getAdvertiserInfo();
        creativeJson.addProperty("advertiser_id", advertiserModel.getId());
        creativeJson.addProperty("creative_id", creativeId);
        creativeJsons.add(creativeJson);
        requestBody.add("data", creativeJsons);
        LOGGER.info("<=PAP-Manager=> tencent sync info = " + requestBody.toString());
        // 汽车之家的ADX信息
        AdxModel adx = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_TENCENT_VALUE);
        Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", adx.getSignKey());
		String respStr = HttpClientUtil.getInstance().sendHttpPostJson(adx.getCreativeQueryUrl(), requestBody.toString(), headers);
		if (!StringUtils.isEmpty(respStr)) {
            if (isResponseSuccess(respStr)) {
            	JsonObject respObj = parser.parse(respStr).getAsJsonObject();
            	if (respObj != null && respObj.has("data") && respObj.get("data").isJsonArray()) {
            		JsonArray dataJsons = respObj.get("data").getAsJsonArray();
            		if (dataJsons.size() > 0) {
            			JsonObject dataJson = dataJsons.get(0).getAsJsonObject();
            			if (dataJson != null && dataJson.has("data") && dataJson.get("data").isJsonObject()) {
            				JsonObject data = dataJson.get("data").getAsJsonObject();
            				String status = data.get("review_status").getAsString();
            				String msg = data.get("review_msg").getAsString();
            				if (AUDIT_STATUS.SUCCESS.equals(status)) {
            					creativeAudit.setStatus(StatusConstant.CREATIVE_AUDIT_SUCCESS);
            				} else if (AUDIT_STATUS.FAILURE.equals(status)) {
            					creativeAudit.setStatus(StatusConstant.CREATIVE_AUDIT_FAILURE);
            					creativeAudit.setMessage(msg);
            				} else {
            					creativeAudit.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
            				}
            				creativeAuditDao.updateByPrimaryKey(creativeAudit);
            			}
            		}
            	}
            } else {
            	// 失败
                throw new ThirdPartyAuditException(parseResponseError(respStr));
            }
		} else {
			throw new ThirdPartyAuditException(AuditErrorConstant.TENCENT_CREATIVE_SYNC_ERROR_REASON + AuditErrorConstant.COMMON_REQUEST_SENT_FAIL);
		}
        
    }

    /**
	 * 解析响应请求中的错误信息
	 * @param respStr  请求返回的JSON字符串
	 * @return
	 */
	private String parseResponseError(String respStr)
	{
	    JsonObject respObj = parser.parse(respStr).getAsJsonObject();
        if (respObj != null && respObj.has("message"))
        {
            return respObj.get("message").getAsString();
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
    private boolean isResponseSuccess(String respStr)
    {
        boolean respFlag = false;
        JsonObject respObj = parser.parse(respStr).getAsJsonObject();
        
        if (respObj != null && respObj.has("code") && respObj.get("code").getAsInt() == 0)
        {
            respFlag = true;
        }
        
        return respFlag;
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