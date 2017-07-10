package com.pxene.pap.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.pxene.pap.domain.beans.baidu.APIAdvertiserQualificationInfo;
import com.pxene.pap.domain.beans.baidu.Advertiser;
import com.pxene.pap.domain.beans.baidu.BaiduRequest;
import com.pxene.pap.domain.models.AdvertiserAuditModel;
import com.pxene.pap.domain.models.AdvertiserAuditModelExample;
import com.pxene.pap.domain.models.AdvertiserModel;
import com.pxene.pap.domain.models.AdxModel;
import com.pxene.pap.domain.models.AdxModelExample;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CreativeAuditModel;
import com.pxene.pap.domain.models.CreativeAuditModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.ImageMaterialModel;
import com.pxene.pap.domain.models.ImageModel;
import com.pxene.pap.domain.models.IndustryModel;
import com.pxene.pap.domain.models.InfoflowMaterialModel;
import com.pxene.pap.domain.models.LandpageModel;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.IndustryDao;

/**
 * 百度流量交易平台（BES）相关审核、同步服务
 * @author ningyu
 */
@Service
public class BaiduAuditService extends AuditService
{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BaiduAuditService.class);
    /**
     * 创意审核信息的过期天数
     */
	private static final int CREATIVE_AUDIT_EXPIRE_DAYS = 120;
	
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
	 * 查询广告主在ADX的审核状态（由于DSP已经审核过BES查询广告主资质）
	 */
	@Override
	@Transactional
    public void synchronizeAdvertiser(String auditId) throws Exception
    {
	    // 查询广告主审核信息
	    AdvertiserAuditModel advertiserAudit = advertiserAuditDao.selectByPrimaryKey(auditId);
	    String advertiserId = advertiserAudit.getAuditValue();
	    String adxId = advertiserAudit.getAdxId();
	    
	    AdvertiserModel advertiser = advertiserDao.selectByPrimaryKey(advertiserAudit.getAdvertiserId());
	    
	    // 查询ADX信息
	    AdxModelExample adxExample = new AdxModelExample();
	    adxExample.createCriteria().andIdEqualTo(adxId);
	    
	    List<AdxModel> adxList = adxDao.selectByExample(adxExample);
	    if (adxList != null && adxList.isEmpty())
	    {
	        AdxModel adx = adxList.get(0);
	        String dspId = adx.getDspId();
	        String token = adx.getSignKey();
	        
	        Map<String, String> authHeaderMap = getAuthHeaderMap(dspId, token);
	        
	        APIAdvertiserQualificationInfo info = new APIAdvertiserQualificationInfo();
	        info.setAdvertiserId(Long.valueOf(advertiserId));
	        info.setNickname(advertiser.getName());
	    }
	    
       
        if (advertiserAudit != null)
        {
            // 如果广告主审核信息不为空，则修改广告主审核表状态：审核通过
            advertiserAudit.setStatus(StatusConstant.ADVERTISER_AUDIT_SUCCESS);
            
            // 更新广告主审核表数据
            advertiserAuditDao.updateByPrimaryKey(advertiserAudit);
        }
    }

	/**
	 * 提交创意到汽车之家进行审核
	 */
	@Override
	@Transactional(dontRollbackOn = IllegalStatusException.class)
    public void auditCreative(String creativeId) throws Exception
    {
    }

	/**
     * 查询汽车之前针对某创意的审核结果
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
        example.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_AUTOHOME_VALUE);
        List<CreativeAuditModel> modelList = creativeAuditDao.selectByExample(example);
        if (modelList == null || modelList.isEmpty())
        {
            throw new ResourceNotFoundException();
        }
        
        // 汽车之家在提交审核后返回的ADX端的创意ID
        ArrayList<String> creativeIds = new ArrayList<String>();
        for (CreativeAuditModel model : modelList)
        {
            creativeIds.add(model.getAuditValue());
        }
        
        // 汽车之家的ADX信息
        AdxModel adx = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_AUTOHOME_VALUE);
        int dspId = Integer.valueOf(adx.getDspId());                    // DSP ID
        String checkURL = adx.getCreativeAuditStateQueryUrl();                     // 创意审核状态查询URL
        String signKey = adx.getSignKey();                              // 汽车之家为DSP颁发的SignKey
     
        // 构建基础的请求URL参数
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("dspId", String.valueOf(dspId));
        requestParams.put("creativeId", org.apache.commons.lang3.StringUtils.join(creativeIds, ","));
        requestParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
        
        // 构建请求验证签名
        String sign = getSign4PlainText(requestParams, signKey);
        
        requestParams.put("sign", sign);
        
        // 发送HTTP GET请求
        String httpUrl = checkURL + "?" + buildRequestParams(requestParams);
        String respStr = HttpClientUtil.getInstance().sendHttpGet(httpUrl);
        
        // 如果请求发送成功且应答响应成功，则将审核结果更新至数据库中，否则不修改数据库中的审核状态，直接提示审核失败的原因
        if (!StringUtils.isEmpty(respStr))
        {
            // 检查status是否为1
            boolean respFlag = checkResponseStatus(respStr);
            if (respFlag)
            {
                Map<String, String> auditResultMap = checkAuditStatus(respStr);
                String code = auditResultMap.get("code");
                String msg = auditResultMap.get("msg");
                
                if ("1".equals(code))
                {
                    changeCreativeAuditStatus(creativeId, StatusConstant.CREATIVE_AUDIT_SUCCESS, "");
                }
                else if ("2".equals(code))
                {
                    changeCreativeAuditStatus(creativeId, StatusConstant.CREATIVE_AUDIT_FAILURE, msg);
                }
                else if ("0".equals(code))
                {
                    changeCreativeAuditStatus(creativeId, StatusConstant.CREATIVE_AUDIT_WATING, msg);
                }
            }
            else
            {
                throw new IllegalStatusException(parseResponseErrorInfo(respStr));
            }
        }
        else 
        {
            throw new IllegalStatusException(AuditErrorConstant.AUTOHOME_CREATIVE_AUDIT_ERROR_REASON + AuditErrorConstant.COMMON_REQUEST_SENT_FAIL);
        }
    }
    
    /**
     * 构建调用请求的验证信息。
     * @param dspId ADX分配给DSP的ID
     * @param token ADX分配给DSP的认证Token
     * @return Key为dspId和token的Map
     */
    private static Map<String, String> getAuthHeaderMap(String dspId, String token)
    {
        Map<String, String> authHeader = new HashMap<String, String>();
        
        authHeader.put("dspId", dspId);
        authHeader.put("token", token);
        
        return authHeader;
    }

    /**
     * 根据广告主的UUID获得DSP端的广告主ID（广告主审核表中保存的由ADX返回的审核后的广告主ID）
     * @param id 广告主ID
     * @return  DSP颁发的广告主ID
     */
    private Integer getAdvertiserIDInDSP(String id)
    {
        Integer advertiserID = null;
        
        AdvertiserAuditModelExample advertiserAuditExample = new AdvertiserAuditModelExample();
        advertiserAuditExample.createCriteria().andAdvertiserIdEqualTo(id).andAdxIdEqualTo(AdxKeyConstant.ADX_AUTOHOME_VALUE);
    
        List<AdvertiserAuditModel> advertiserAudits = advertiserAuditDao.selectByExample(advertiserAuditExample);
        if (advertiserAudits != null && !advertiserAudits.isEmpty())
        {
            AdvertiserAuditModel advertiserAuditModel = advertiserAudits.get(0);
            String auditValue = advertiserAuditModel.getAuditValue();
            
            if (StringUtils.isEmpty(auditValue))
            {
                throw new IllegalArgumentException(PhrasesConstant.ADVERTISER_AUDIT_ERROR);
            }
            
            advertiserID = Integer.valueOf(auditValue);
        }
        return advertiserID;
    }


    /**
     * 修改创意审核状态
     * @param creativeId    创意ID
     * @param status        欲修改为的创意审核状态
     */
    private void changeCreativeAuditStatus(String creativeId, String status, String message)
    {
        CreativeAuditModelExample example = new CreativeAuditModelExample();
        example.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_AUTOHOME_VALUE);
        
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
        if (respObj != null && respObj.has("statusInfo") && respObj.get("statusInfo").isJsonObject())
        {
            JsonObject statusInfo = respObj.get("statusInfo").getAsJsonObject();
            String errMsg = statusInfo.get("global").getAsString();
            return errMsg;
        }
        else
        {
            return AuditErrorConstant.COMMON_RESPONSE_PARSE_FAIL;
        }
	}
	
	/**
	 * 检查提交的创意审核是否返回了正确的ADX端的创意ID
	 * @param respStr
	 * @return
	 */
	private int checkUploadStatus(String respStr)
    {
        JsonObject respObj = parser.parse(respStr).getAsJsonObject();
        
        if (respObj != null && respObj.has("data") && respObj.get("data").isJsonObject())
        {
            JsonObject dataObj = respObj.get("data").getAsJsonObject();
            if (dataObj.has("creativeIds") && dataObj.get("creativeIds").isJsonArray())
            {
                JsonArray array = dataObj.get("creativeIds").getAsJsonArray();
                if (array != null && array.size() > 0)
                {
                    return array.get(0).getAsInt();
                }
            }
        }
        
        return -1;
    }
	
	/**
	 * 检查创意的审核状态，如果审核成功则返回null表示成功；否则返回值为ADX返回的审核错误原因
	 * @param respStr
	 * @return
	 */
    private Map<String, String> checkAuditStatus(String respStr)
    {
        Map<String, String> result = new HashMap<String, String>();
        
        JsonObject respObj = parser.parse(respStr).getAsJsonObject();
        
        JsonObject dataObj = respObj.get("data").getAsJsonObject();
        if (dataObj.has("creative") && dataObj.get("creative").isJsonArray())
        {
            JsonArray array = dataObj.get("creative").getAsJsonArray();
            
            for (JsonElement jsonElement : array)
            {
                JsonObject o = jsonElement.getAsJsonObject();
                int id = o.get("id").getAsInt();
                
                JsonElement tmpElement = o.get("auditStatus");
                int auditStatus = 2; //拒绝
                if (!tmpElement.isJsonNull())
                {
                    auditStatus= tmpElement.getAsInt();
                }
                result.put("code", String.valueOf(auditStatus));
                
                JsonElement element = o.get("auditComment");
                String msg = "";
                if (!element.isJsonNull())
                {
                    String auditComment = element.getAsString();
                    msg = "ID: " + id + ", " + auditComment;
                }
                result.put("msg", msg);
            }
        }
        
        return result;
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
        
        if (respObj != null && respObj.has("status") && respObj.get("status").getAsInt() == 1)
        {
            respFlag = true;
        }
        
        return respFlag;
    }


    private JsonObject buildSnippetContentImg(String imageId, String type)
    {
        JsonObject content = null;
        if (!StringUtils.isEmpty(imageId))
        {
            // 根据图片ID取得图片素材信息
            ImageModel imageInfo = imageDao.selectByPrimaryKey(imageId);
            if (imageInfo != null)
            {
                // 拼接上图片服务器前缀，获得完整的图片URL
                String src = urlPrefix + imageInfo.getPath();
                
                content = new JsonObject();
                content.addProperty("src", src);
                content.addProperty("type", type);
            }
        }
        return content;
    }
    
    private JsonObject buildSnippetContentText(String text, String type) {
    	JsonObject content = null;
        if (!StringUtils.isEmpty(text)) {
        	content = new JsonObject();
            content.addProperty("src", text);
            content.addProperty("type", type);
        }
        return content;
    }

    /**
     * 生成GET和POST请求验证签名
     * @param params
     * @param signKey
     * @return
     */
    private String getSign4PlainText(Map<String, String> params, String signKey)
    {
        String str = buildRequestParams(params);
        
        String sign = GlobalUtil.MD5(str + signKey);
        return sign;
       }


    private String buildRequestParams(Map<String, String> params)
    {
        // 获得URL参数中所有的参数名（key）
        Set<String> keySet = params.keySet();

        // 将所有参数名按字典序从小到大排序
        List<String> keyList = new ArrayList<String>();
        keyList.addAll(keySet);
        Collections.sort(keyList);
        
        // 按新的顺序构造一个新的包含参数名值对的ArrayList
        List<String> paramList = new ArrayList<String>();
        for (String key: keyList)
        {
            paramList.add(key + "=" + params.get(key));
        }
        
        // 在参数最后添加一个时间戳字段
        //paramList.add("timestamp=" + System.currentTimeMillis());
        
        // 将参数名和参数值用=和&连接
        String str = org.apache.commons.lang3.StringUtils.join(paramList, "&");
        return str;
    }

    /**
     * 生成POST json 请求验证签名
     * @param obj
     * @param signKey
     * @return
     */
    private String getSign4Json(JsonObject obj, String signKey)
    {
        obj.addProperty("timestamp", System.currentTimeMillis());
        String str = obj.toString();
        String sign = GlobalUtil.MD5(str + signKey);
        return sign;
    }

    private JsonObject buildAuditObj(int dspId, String dspName, long timestamp, JsonArray creatives)
    {
        JsonObject auditRequest = new JsonObject();
        auditRequest.addProperty("dspId", dspId);       // 设置所属 DSP ID
        auditRequest.addProperty("dspName", dspName);   // 设置所属 DSP 名称
        auditRequest.add("creative", creatives);        // 创意list
        auditRequest.addProperty("timestamp", Double.valueOf(timestamp)); // 设置验签时间戳（1970 年 1 月 1 日至今毫秒值）
        return auditRequest;
    }


    private JsonObject buildCreativeObj(int advertiserId, String advertiserName, String industryId, String industryName, String creativeTypeId, String templateId, JsonObject adSnippetObj)
    {
        JsonObject creative = new JsonObject();
        
        creative.addProperty("advertiserId", advertiserId);     // 设置所属广告主 ID
        creative.addProperty("advertiserName", advertiserName); // 设置所属广告主名称，需要填写全称,与注册名称一致
        creative.addProperty("industryId", industryId);         // 设置所属行业 ID
        creative.addProperty("industryName", industryName);     // 设置所属行业名称
        creative.addProperty("creativeTypeId", creativeTypeId); // 设置素材类型 ID：1001-文字链，1002-图片，1003-图文
        creative.addProperty("width", 0);                       // 设置广告位宽度，如果不知道宽度则填充 0
        creative.addProperty("height", 0);                      // 设置广告位高度，如果不知道高度则填充 0
        creative.addProperty("repeatedCode", "");               // 设置去重码，预留字段，填充固定值空字符串””
        creative.add("adsnippet", adSnippetObj);
        creative.addProperty("platform", 2);                    // 广告形式：PC/M=1，App=2
        if (StringUtils.isEmpty(templateId))
        {
            creative.addProperty("templateId", templateId);     // 设置模板 ID（参考 2.1.5 广告形式说明）
        }
        
        return creative;
    }

	/**
	 * 新建一条创意审核信息或更新已有创意审核信息。
	 * @param creativeId 创意ID
	 * @param auditValue ADX返回的创意ID
	 */
    private void insertOrUpdateToDB(String creativeId, int auditValue)
    {
        // 根据创意ID 和 ADX ID查询指定创意的审核信息（二者组合可以确定唯一一条创意审核信息）
        CreativeAuditModelExample example = new CreativeAuditModelExample();
        example.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_AUTOHOME_VALUE);
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
            model.setAdxId(AdxKeyConstant.ADX_AUTOHOME_VALUE);
            model.setCreativeId(creativeId);
            model.setAuditValue(String.valueOf(auditValue));
            creativeAuditDao.insertSelective(model);
        }
    }

	/**
	 * 将PAP业务中的创意类型（图片01，视频02，信息流03）转换成汽车之家的素材类型ID。
	 * <ul>
	 *     <li>图片：1002</li>
     *     <li>信息流：1003</li> 
     *     <li>不支持文字链</li>
     * </ul>
	 * @param creativeType PAP业务中的创意类型
	 * @return
	 */
	private String getCreativeTypeId(String creativeType)
    {
        if ("01".equals(creativeType))
        {
            return "1002";
        }
        if ("03".equals(creativeType))
        {
            return "1003";
        }
        return null;
    }

    /**
	 * 根据行业ID获得行业名称。
	 * @param industryId   行业ID
	 * @return
	 */
    private String getIndustryName(String industryId)
    {
        IndustryModel industryModel = industryDao.selectByPrimaryKey(industryId);
        if (industryModel != null)
        {
            return industryModel.getName();
        }
        return null;
    }



    /**
     * 提交广告主到ADX进行审核（更新广告主审核表数据）
     */
    @Transactional
    public void auditAdvertiser1(String auditId) throws Exception
    {
        // 查询广告主审核信息
        AdvertiserAuditModel advertiserAudit = advertiserAuditDao.selectByPrimaryKey(auditId);
        if (advertiserAudit != null)
        {
            // DSP 侧内部的广告主ID，需要保证DSP内部不重复，由于ADX要求是int值，因此不能用UUID。
            Long auditValue = RandomUtils.nextLong();

            AdvertiserModel advertiser = advertiserDao.selectByPrimaryKey(advertiserAudit.getAdvertiserId());
            AdxModel adx = adxDao.selectByPrimaryKey("8");
            
            String advertiserLiteName = advertiser.getName();
            String advertiserName = "";//TODO;
            String siteName = advertiser.getSiteName();
            String siteUrl = advertiser.getSiteUrl();
            String telephone = advertiser.getPhone();
            String address = advertiser.getAddress();
            
            Advertiser baiduAdvertiser = new Advertiser();
            baiduAdvertiser.setAdvertiserId(auditValue);
            baiduAdvertiser.setAdvertiserLiteName(advertiserLiteName);
            baiduAdvertiser.setAdvertiserName(advertiserName);
            baiduAdvertiser.setSiteName(siteName);
            baiduAdvertiser.setSiteUrl(siteUrl);
            baiduAdvertiser.setTelephone(telephone);
            baiduAdvertiser.setAddress(address);
            baiduAdvertiser.setIsWhiteUser(0);
            
            Advertiser[] advertisers = new Advertiser[]{baiduAdvertiser};
            
            batchAddAdvertiser(advertisers, adx);
            
            
            // 修改广告审核的状态：审核中
            advertiserAudit.setStatus(StatusConstant.ADVERTISER_AUDIT_WATING);
            
            // 将生成的Long类型的广告主ID，插入到审核表中
            advertiserAudit.setAuditValue(String.valueOf(auditValue));
            
            // 更新数据库信息
            advertiserAuditDao.updateByPrimaryKeySelective(advertiserAudit);
        }
    }

    /**
     * 批量新增广告主
     * @param advertisers 广告主
     * @param adx
     */
    private void batchAddAdvertiser(Advertiser[] advertisers, AdxModel adx)
    {
        Map<String, Object> authHeader = new HashMap<String, Object>();
        authHeader.put("dspId", adx.getDspId());
        authHeader.put("token", adx.getSignKey());
        
        BaiduRequest req = new BaiduRequest(authHeader, advertisers);
        String requestBody = req.toJsonString();
        
        String url = "https://api.es.baidu.com/v1/advertiser/add##";
        
        // 发送HTTP POST请求
        String respStr = HttpClientUtil.getInstance().sendHttpPostJson(url, requestBody);
        
    }
}
