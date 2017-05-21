package com.pxene.pap.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

//import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
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
import com.pxene.pap.constant.StatusConstant;
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
import com.pxene.pap.domain.models.IndustryModel;
import com.pxene.pap.domain.models.InfoflowMaterialModel;
import com.pxene.pap.domain.models.LandpageModel;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.IndustryDao;

/**
 * 汽车之家的审核同步 
 * @author lizhuoling
 */
@Service
public class AutohomeAuditService extends AuditService
{
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
    

	public AutohomeAuditService(Environment env) 
	{
		super(env);
		
        String uploadMode = env.getProperty("pap.fileserver.mode");
        String urlPrefixTemplate = "pap.fileserver.{0}.url.prefix";
        urlPrefix = env.getProperty(MessageFormat.format(urlPrefixTemplate, uploadMode));
        
        clkURLPrefix = env.getProperty("pap.click.url.prefix");
        impURLPrefix = env.getProperty("pap.impression.url.prefix");
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
//            int auditValue = RandomUtils.nextInt();
            int auditValue = 0;
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
	 * 提交创意到汽车之家进行审核
	 */
	@Override
	@Transactional	
    public void auditCreative(String creativeId) throws Exception
    {
        // 根据创意ID查询创意信息
        CreativeModel creativeModel = creativeDao.selectByPrimaryKey(creativeId);
        
        // 判断创意是否存在
        if (creativeModel == null)
        {
            throw new ResourceNotFoundException();
        }
        
        // 获得创意所属的活动ID，检查活动是否存在
        String campaignId = creativeModel.getCampaignId();
        if (StringUtils.isEmpty(campaignId))
        {
            throw new ResourceNotFoundException();
        }
        
        // 汽车之家的ADX信息
        AdxModel adx = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_AUTOHOME_VALUE);
        int dspId = Integer.valueOf(adx.getDspId());                    // DSP ID
        String dspName = adx.getDspName();                              // DSP 名称
        String uploadURL = adx.getCreativeAuditUrl();                   // 创意审核URL
        String signKey = adx.getSignKey();                              // 汽车之家为DSP颁发的SignKey
        String iURL = adx.getIurl();                                    // 曝光监测地址
        String cURL = adx.getCurl();                                    // 点击监测地址
        
        // 查询出创意的相关信息
        CreativeRichBean richCreative = buildRelation(creativeId);
        
        // 广告主信息
        AdvertiserModel advertiserModel = richCreative.getAdvertiserInfo();
        
        // DSP端的广告主ID，采用包装类型是为了判断是否能成功取出
        Integer advertiserIdObj = getAdvertiserIDInDSP(advertiserModel.getId());   
        if (advertiserIdObj == null)
        {
            throw new ResourceNotFoundException();
        }
        
        int advertiserId = advertiserIdObj;                                 // DSP端的广告主ID
        String advertiserName = advertiserModel.getName();                  // 广告主名称
        String industryId = advertiserModel.getIndustryId();                // 行业ID
        String industryName = getIndustryName(industryId);                  // 行业名称
        
        getAdvertiserIDInDSP(advertiserModel.getId());
        

        // 创意类型
        String creativeType = creativeModel.getType();
        
        // 创意素材ID
        String materialId = creativeModel.getMaterialId();
        
        // 素材类型ID
        String creativeTypeId = getCreativeTypeId(creativeType);
        
        // 落地页信息
        LandpageModel landpageInfo = richCreative.getLandpageInfo();
        //String landpageURL = landpageInfo.getUrl().replaceAll("&", "%26"); // 落地页地址，即，创意的点击后跳转地址
        
        JsonObject contentObj = null;
        JsonArray contentArray = new JsonArray();
        
        // 如果创意是图片，则读取图片素材表，取得图片ID，再根据图片ID，取得详细的图片信息
        if ("01".equals(creativeType))
        {
            ImageMaterialModel imageMaterial = imageMaterialDao.selectByPrimaryKey(materialId);
            String imageId = imageMaterial.getImageId();

            contentObj = buildSnippetContentObj(imageId, "img");
            contentArray.add(contentObj);
        }
        else if ("03".equals(creativeType))//如果创意是信息流，则读取信息流素材表，取得第一张图片ID
        {
            InfoflowMaterialModel infoflowMaterial = infoflowMaterialDao.selectByPrimaryKey(materialId);
            
            String title = infoflowMaterial.getTitle();
            String descp = infoflowMaterial.getDescription();
            
            String iconId = infoflowMaterial.getIconId();
            String image1Id = infoflowMaterial.getImage1Id();
            String image2Id = infoflowMaterial.getImage2Id();
            String image3Id = infoflowMaterial.getImage3Id();
            String image4Id = infoflowMaterial.getImage4Id();
            String image5Id = infoflowMaterial.getImage5Id();
            
            // 小图类型
            if (!StringUtils.isEmpty(iconId))
            {
                contentObj = buildSnippetContentObj(iconId, "simg");
                contentArray.add(contentObj);
            }
            
            // 图片地址1
            if (!StringUtils.isEmpty(image1Id))
            {
                contentObj = buildSnippetContentObj(iconId, "img");
                contentArray.add(contentObj);
            }
            
            // 图片地址2
            if (!StringUtils.isEmpty(image2Id))
            {
                contentObj = buildSnippetContentObj(iconId, "img2");
                contentArray.add(contentObj);
            }
            
            // 图片地址3
            if (!StringUtils.isEmpty(image3Id))
            {
                contentObj = buildSnippetContentObj(iconId, "img3");
                contentArray.add(contentObj);
            }
            
            // 图片地址4
            if (!StringUtils.isEmpty(image4Id))
            {
                contentObj = buildSnippetContentObj(iconId, "img4");
                contentArray.add(contentObj);
            }
            
            // 图片地址5
            if (!StringUtils.isEmpty(image5Id))
            {
                contentObj = buildSnippetContentObj(iconId, "img5");
                contentArray.add(contentObj);
            }
            
            // 标题类型
            if (!StringUtils.isEmpty(title))
            {
                contentObj = buildSnippetContentObj(iconId, "text");
                contentArray.add(contentObj);
            }
            
            // 副标题类型
            if (!StringUtils.isEmpty(descp))
            {
                contentObj = buildSnippetContentObj(iconId, "stext");
                contentArray.add(contentObj);
            }
        }
        else
        {
            throw new UnsupportedOperationException();
        }
        
        // 拼接成点击地址（302跳转）
        String link = clkURLPrefix + cURL;
        String landpageURL = URLEncoder.encode(landpageInfo.getUrl(), StandardCharsets.UTF_8.displayName());
        link = link + "&curl=" + landpageURL;
        
        // 拼接成监测地址
        JsonArray pv = new JsonArray();
        pv.add(impURLPrefix + iURL);

        // 获取当前时间戳
        long timestamp = System.currentTimeMillis();  
        
        // -####- 构建广告内容对象
        JsonObject adSnippetObj = new JsonObject();
        adSnippetObj.add("content", contentArray);
        adSnippetObj.addProperty("link", link);
        adSnippetObj.add("pv", pv);
        
        // -###- 构建创意对象
        JsonObject creative = buildCreativeObj(advertiserId, advertiserName, industryId, industryName, creativeTypeId, null, adSnippetObj);                // 设置广告内容
        
        // -##- 构建创意对象数组
        JsonArray creatives = new JsonArray();
        creatives.add(creative);
        
        // -#- 构建审核请求对象
        JsonObject auditObj = buildAuditObj(dspId, dspName, timestamp, creatives);
        
        // 生成请求验证签名时，需要将Json Object序列化为Json String，字段按照字典序从小到大排序，数组内容不排序，排序前不包含sign字段，null值不显示，无缩进，key带双引号
        JsonObject sortedAuditObj = GlobalUtil.sortJsonObject(auditObj);
        
        // 加密获得验签串
        String sign = getSign4Json(sortedAuditObj, signKey);
        
        sortedAuditObj.addProperty("sign", sign);
        
        // 发送HTTP POST请求
        String respStr = HttpClientUtil.getInstance().sendHttpPostJson(uploadURL, sortedAuditObj.toString());
        
        // 如果请求发送成功且应答响应成功，则将审核信息插入或更新至数据库中，否则提示审核失败的原因
        if (!StringUtils.isEmpty(respStr))
        {
            // 检查status是否为1
            boolean respFlag = checkResponseStatus(respStr);
            if (respFlag)
            {
                int auditValue = checkUploadStatus(respStr);
                insertOrUpdateToDB(creativeId, auditValue);
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
     * 查询汽车之前针对某创意的审核结果
     */
    @Override
    @Transactional
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
        String checkURL = adx.getCreativeSyncUrl();                     // 创意审核状态查询URL
        String signKey = adx.getSignKey();                              // 汽车之家为DSP颁发的SignKey
     
        // 构建基础的请求URL参数
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("dspId", String.valueOf(dspId));
//        requestParams.put("creativeId", org.apache.commons.lang3.StringUtils.join(creativeIds, ","));
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
                String errorMsg = checkAuditStatus(respStr);
                if (StringUtils.isEmpty(errorMsg))
                {
                    changeCreativeAuditStatus(creativeId, StatusConstant.CREATIVE_AUDIT_SUCCESS);
                }
                else
                {
                    changeCreativeAuditStatus(creativeId, StatusConstant.CREATIVE_AUDIT_FAILURE);
                    
                    throw new IllegalStatusException(errorMsg);
                }
            }
            else
            {
                changeCreativeAuditStatus(creativeId, StatusConstant.CREATIVE_AUDIT_FAILURE);
                
                throw new IllegalStatusException(parseResponseErrorInfo(respStr));
            }
        }
        else 
        {
            throw new IllegalStatusException(AuditErrorConstant.AUTOHOME_CREATIVE_AUDIT_ERROR_REASON + AuditErrorConstant.COMMON_REQUEST_SENT_FAIL);
        }
    }

    /**
     * 根据广告主的UUID获得DSP端的广告主ID（广告主审核表中保存的由ADX返回的审核后的广告主ID）
     * @param id 广告主ID
     * @return
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
            advertiserID = Integer.valueOf(advertiserAuditModel.getAuditValue());
        }
        return advertiserID;
    }


    /**
     * 修改创意审核状态
     * @param creativeId    创意ID
     * @param status        欲修改为的创意审核状态
     */
    private void changeCreativeAuditStatus(String creativeId, String status)
    {
        CreativeAuditModelExample example = new CreativeAuditModelExample();
        example.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_AUTOHOME_VALUE);
        
        List<CreativeAuditModel> modelList = creativeAuditDao.selectByExample(example);
        
        for (CreativeAuditModel model : modelList)
        {
            model.setStatus(status);
            creativeAuditDao.updateByPrimaryKeySelective(model);
        }
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
	private String checkAuditStatus(String respStr)
    {
	    String result = null;
	    List<String> errorList = new ArrayList<String>();
        JsonObject respObj = parser.parse(respStr).getAsJsonObject();
        
        if (respObj != null && respObj.has("status") && respObj.get("status").getAsInt() == 1)
        {
            JsonObject dataObj = respObj.get("data").getAsJsonObject();
            if (dataObj.has("creative") && dataObj.get("creative").isJsonArray())
            {
                JsonArray array = dataObj.get("creative").getAsJsonArray();
                
                for (JsonElement jsonElement : array)
                {
                    JsonObject o = jsonElement.getAsJsonObject();
                    int id = o.get("id").getAsInt();
                    String auditComment = o.get("auditComment").getAsString();
                    
                    String msg = "ID: " + id + ", " + auditComment;
                    errorList.add(msg);
                    
                    result = errorList.toString();
                }
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


    private JsonObject buildSnippetContentObj(String imageId, String type)
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
//        String str = org.apache.commons.lang3.StringUtils.join(paramList, "&");
        String str = "";
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
            model.setStatus(StatusConstant.CREATIVE_AUDIT_NOCHECK);
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


class CreativeRichBean
{
    private String id;
    private LandpageModel landpageInfo;
    private CreativeModel creativeInfo; 
    private CampaignModel campaignInfo;
    private ProjectModel projectInfo;
    private AdvertiserModel advertiserInfo;
    
    
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public LandpageModel getLandpageInfo()
    {
        return landpageInfo;
    }
    public void setLandpageInfo(LandpageModel landpageInfo)
    {
        this.landpageInfo = landpageInfo;
    }
    public CreativeModel getCreativeInfo()
    {
        return creativeInfo;
    }
    public void setCreativeInfo(CreativeModel creativeInfo)
    {
        this.creativeInfo = creativeInfo;
    }
    public CampaignModel getCampaignInfo()
    {
        return campaignInfo;
    }
    public void setCampaignInfo(CampaignModel campaignInfo)
    {
        this.campaignInfo = campaignInfo;
    }
    public ProjectModel getProjectInfo()
    {
        return projectInfo;
    }
    public void setProjectInfo(ProjectModel projectInfo)
    {
        this.projectInfo = projectInfo;
    }
    public AdvertiserModel getAdvertiserInfo()
    {
        return advertiserInfo;
    }
    public void setAdvertiserInfo(AdvertiserModel advertiserInfo)
    {
        this.advertiserInfo = advertiserInfo;
    }
    
    
    public CreativeRichBean()
    {
        super();
    }
    public CreativeRichBean(String id, LandpageModel landpageInfo, CreativeModel creativeInfo, CampaignModel campaignInfo, ProjectModel projectInfo, AdvertiserModel advertiserInfo)
    {
        super();
        this.id = id;
        this.landpageInfo = landpageInfo;
        this.creativeInfo = creativeInfo;
        this.campaignInfo = campaignInfo;
        this.projectInfo = projectInfo;
        this.advertiserInfo = advertiserInfo;
    }
    
    
    @Override
    public String toString()
    {
        return "CreativeRichBean [id=" + id + ", landpageInfo=" + landpageInfo + ", creativeInfo=" + creativeInfo + ", campaignInfo=" + campaignInfo + ", projectInfo=" + projectInfo
                + ", advertiserInfo=" + advertiserInfo + "]";
    }
}