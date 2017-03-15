package com.pxene.pap.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pxene.pap.common.GlobalUtil;
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
import com.pxene.pap.domain.models.IndustryAdxModel;
import com.pxene.pap.domain.models.IndustryAdxModelExample;
import com.pxene.pap.domain.models.IndustryModel;
import com.pxene.pap.domain.models.InfoflowMaterialModel;
import com.pxene.pap.domain.models.LandpageModel;
import com.pxene.pap.domain.models.MonitorModel;
import com.pxene.pap.domain.models.MonitorModelExample;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.exception.ServerFailureException;
import com.pxene.pap.repository.basic.AdvertiserAuditDao;
import com.pxene.pap.repository.basic.AdvertiserDao;
import com.pxene.pap.repository.basic.AdxDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeAuditDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.ImageDao;
import com.pxene.pap.repository.basic.ImageMaterialDao;
import com.pxene.pap.repository.basic.IndustryAdxDao;
import com.pxene.pap.repository.basic.IndustryDao;
import com.pxene.pap.repository.basic.InfoflowMaterialDao;
import com.pxene.pap.repository.basic.LandpageDao;
import com.pxene.pap.repository.basic.MonitorDao;
import com.pxene.pap.repository.basic.ProjectDao;

@Service
public class AuditCreativeAutoHomeService {

	@Autowired
	private AdxDao adxDao;
	@Autowired
	private LandpageDao landpageDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private AdvertiserDao advertiserDao;
	@Autowired
	private CampaignDao campaignDao;
	@Autowired
	private ImageDao imageDao;
	@Autowired
	private ImageMaterialDao imageMaterialDao;
	@Autowired
	private InfoflowMaterialDao infoMaterialDao;
	@Autowired
	private CreativeDao creativeDao;
	@Autowired
	private InfoflowMaterialDao infoflowDao;
	@Autowired
	private IndustryAdxDao industryAdxDao;
	@Autowired
	private IndustryDao industryDao;
	@Autowired
	private CreativeAuditDao creativeAuditDao;
	@Autowired
	private AdvertiserAuditDao advertiserAuditDao;
	@Autowired
	private MonitorDao monitorDao;
	
	private static final String HTTP_ACCEPT = "accept";

	private static final String HTTP_ACCEPT_DEFAULT = "*/*";

	private static final String HTTP_USER_AGENT = "user-agent";

	private static final String HTTP_USER_AGENT_VAL = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)";

	private static final String HTTP_CONNECTION = "connection";

	private static final String HTTP_CONNECTION_VAL = "Keep-Alive";
	
	public static final String SOHU_ADVER_DELETE = "http://api.ad.sohu.com/exchange/material/delete";
	
	public static final String SOHU_ADVER_SIGN = "http://api.ad.sohu.com/test/apiauth";
	
	private static String image_url;
	private static String impClick_url;
	
	public AuditCreativeAutoHomeService(Environment env) {
		/**
		 * 获取图片上传路径
		 */
		image_url = env.getProperty("pap.fileserver.url.prefix");
		impClick_url = env.getProperty("pap.fileserver.url.impClick");
	}
	
	@Transactional
	public void audit(String creativeId) throws Exception {
		AdxModel adxModel = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_AUTOHOME_VALUE);
		String cexamineurl = adxModel.getCexamineUrl();
		CreativeModel mapModel = creativeDao.selectByPrimaryKey(creativeId);
		if (mapModel == null) {
			throw new ResourceNotFoundException();
		}
		String privateKey = adxModel.getPrivateKey();//取出adx的私密key
		Gson gson = new Gson();
    	JsonObject json = gson.fromJson(privateKey, new JsonObject().getClass());
    	if (json.get("dspId") == null || json.get("signKey") == null) {
			throw new ResourceNotFoundException("autohome : 缺少私密key");//缺少私密key("auto创意提交第三方审核错误！原因：私密key不存在")
		}
    	//发送POST方法的请求所需参数
    	String dspId = json.get("dspId").getAsString();
		String signKey = json.get("signKey").getAsString();
		String dspName = AdxKeyConstant.AUDIT_NAME_AUTOHOME;
    	
		//查询活动ID
		CreativeModel creativeModel = creativeDao.selectByPrimaryKey(creativeId);
		String campaignId = creativeModel.getCampaignId();//计划id
		//查询项目ID
		CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
		String projectId = campaignModel.getProjectId();
		//查询广告主ID
		ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
		String advertiserId = projectModel.getAdvertiserId();//广告主id
		//查询广告主
		AdvertiserModel advertiserModel = advertiserDao.selectByPrimaryKey(advertiserId);
		//查询落地页
		String landpageId = campaignModel.getLandpageId();
		LandpageModel landpageModel = landpageDao.selectByPrimaryKey(landpageId);
        
		//获取参数
        String company = advertiserModel.getCompany();//公司名称
        String type = creativeModel.getType();//创意素材类型
        int industryId = Integer.parseInt(getIndustry(advertiserModel.getIndustryId()));//广告主行业类型（奥丁平台）
        IndustryModel industryModel = industryDao.selectByPrimaryKey(advertiserModel.getIndustryId());
        String industryName = industryModel.getName();
        String clicklink = landpageModel.getUrl();//目标地址
        String cturl = "";
        if (!StringUtils.isEmpty(adxModel.getClickUrl())) {
        	cturl = impClick_url + adxModel.getClickUrl();
        }
    	
        cturl = cturl.replace("#BID#","1").replace("#DEVICEID#","1").replace("#DEVICEIDTYPE#","1").replace("#MAPID#",creativeId);
        String iurl = "";
        if (!StringUtils.isEmpty(adxModel.getImpressionUrl())) {
        	iurl = impClick_url + adxModel.getImpressionUrl();
        }
        long advertiserIdValue = getAdvertiserAuditValueByMapId(creativeId);//广告主数字id
        // 目标地址targetUrl
        String encodeUrl = URLEncoder.encode(clicklink, "UTF-8");
        //平台的点击地址 + 编码后的落地页
        String targetUrl = cturl + "&curl=" + encodeUrl;
    	JsonArray pvArray = new JsonArray();
    	pvArray.add(iurl);
    	MonitorModelExample mex = new MonitorModelExample();
    	mex.createCriteria().andCampaignIdEqualTo(campaignId);
    	List<MonitorModel> monitors = monitorDao.selectByExample(mex);
    	if (monitors != null && !monitors.isEmpty()) {
    		for (MonitorModel monitor : monitors) {
    			pvArray.add(monitor.getImpressionUrl());
    		}
    	}
    	JsonObject adsnippetJson = new JsonObject();//广告模板内容
    	JsonArray contentArray = new JsonArray();//广告内容
    	JsonObject contentObject;//具体内容
        int creativeTypeId = 1002;//素材类型id  1001-文字链， 1002-图片，1003-图文
        int width = 0;
    	int height = 0;
        if(type.equals(StatusConstant.CREATIVE_TYPE_IMAGE)) {//图片
            creativeTypeId = 1002;
            contentObject = new JsonObject();
            ImageMaterialModel materialModel = imageMaterialDao.selectByPrimaryKey(creativeModel.getMaterialId());
            if (materialModel != null) {
            	ImageModel imageModel = imageDao.selectByPrimaryKey(materialModel.getImageId());
            	if (imageModel != null) {
            		contentObject.addProperty("src", imageModel.getPath());
            		width = imageModel.getWidth();
            		height = imageModel.getHeight();
            	}
            }
            contentObject.addProperty("type", "img");
            contentArray.add(contentObject);
        } else if(type.equals(StatusConstant.CREATIVE_TYPE_VIDEO)){//视频
            throw new IllegalStatusException(AuditErrorConstant.AUTOHOME_CREATIVE_MATERIAL_NOT_SUPPORT);
        } else if(type.equals(StatusConstant.CREATIVE_TYPE_INFOFLOW)) {//信息流
        	creativeTypeId = 1003;
        	InfoflowMaterialModel materialModel = infoMaterialDao.selectByPrimaryKey(creativeModel.getMaterialId());
        	if (materialModel != null) {
        		String text = materialModel.getTitle();//广告标题
        		contentObject = new JsonObject();
        		contentObject.addProperty("src", text);
        		contentObject.addProperty("type", "text");
                contentArray.add(contentObject);
                if (!StringUtils.isEmpty(materialModel.getDescription())) {
                	String stext = materialModel.getDescription();
                	contentObject = new JsonObject();
            		contentObject.addProperty("src", stext);
            		contentObject.addProperty("type", "stext");
                    contentArray.add(contentObject);
                }
        	}
        	//大小图都有时
        	if (!StringUtils.isEmpty(materialModel.getImage1Id()) && !StringUtils.isEmpty(materialModel.getIconId())) {
        		contentObject = new JsonObject();
        		ImageModel imageModel = imageDao.selectByPrimaryKey(materialModel.getImage1Id());
                contentObject.addProperty("src", image_url + imageModel.getPath());
                contentObject.addProperty("type", "bimg");
                contentArray.add(contentObject);
                contentObject = new JsonObject();
                imageModel = imageDao.selectByPrimaryKey(materialModel.getIconId());
                contentObject.addProperty("src", image_url + imageModel.getPath());
                contentObject.addProperty("type", "simg");
                contentArray.add(contentObject);
        	} else {
        		if (StringUtils.isEmpty(materialModel.getImage1Id())) {
            		throw new IllegalStatusException(AuditErrorConstant.AUTOHOME_CREATIVE_NOT_SUPPORT_ONE_SMALL_IMAGE);
            	}
        		 contentObject = new JsonObject();
        		 ImageModel imageModel = imageDao.selectByPrimaryKey(materialModel.getImage1Id());
                 contentObject.addProperty("src", image_url + imageModel.getPath());
                 contentObject.addProperty("type", "img");
                 contentArray.add(contentObject);
        	}
        	ImageModel imageModel = imageDao.selectByPrimaryKey(materialModel.getImage1Id());
        	width = imageModel.getWidth();
        	height = imageModel.getHeight();
        }
        adsnippetJson.add("content", contentArray);
        adsnippetJson.addProperty("link", targetUrl);
        adsnippetJson.add("pv", pvArray);
        // 请求参数 -- 素材数组
        JsonArray creatives = new JsonArray();
        JsonObject creative = new JsonObject();
        
        creative.add("adsnippet", adsnippetJson);//广告内容
        creative.addProperty("advertiserId", advertiserIdValue);//广告主id
        creative.addProperty("advertiserName", company);//广告主名称
        creative.addProperty("creativeTypeId", creativeTypeId);//素材类型id
        creative.addProperty("height", height);//高
        creative.addProperty("industryId", industryId);//行业id
        creative.addProperty("industryName", industryName);//行业名称
        creative.addProperty("repeatedCode", "");//去重码 ，预留字段填充固定值空符串 ””
//        creative.put("templateId", "");//模板id -- 暂时为空
        creative.addProperty("width", width);//宽
        creatives.add(creative);
        
        long timestamp = System.currentTimeMillis();//获取当前时间时间戳
        // 最终请求参数
        JsonObject jsonObj = new JsonObject();
        jsonObj.add("creative", creatives);
        jsonObj.addProperty("dspId", Integer.valueOf(dspId));
        jsonObj.addProperty("dspName", dspName);
        jsonObj.addProperty("timestamp", timestamp);

        // 数据签名生成
        String sign = getJsonSign(json, signKey);
        jsonObj.addProperty("sign", sign);
        //发送POST方法的请求,返回结果  
        String result = GlobalUtil.sendPost(cexamineurl, jsonObj.toString());
        JsonObject jsonMap = gson.fromJson(result, new JsonObject().getClass());
        if (jsonMap.get("status").getAsInt() == 1) {
        	JsonArray array = jsonMap.get("statusInfo").getAsJsonArray();
        	CreativeAuditModelExample example = new CreativeAuditModelExample();
			example.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_AUTOHOME_VALUE);
			List<CreativeAuditModel> list = creativeAuditDao.selectByExample(example);
			if (list != null && !list.isEmpty()) {
				for (CreativeAuditModel mod : list) {
					mod.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
					creativeAuditDao.updateByPrimaryKeySelective(mod);
				}
			} else {
				CreativeAuditModel mod = new CreativeAuditModel();
				mod.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
				mod.setId(UUID.randomUUID().toString());
				mod.setAuditValue(array.get(0).getAsString());//adx生成审核key
				mod.setCreativeId(creativeId);
				mod.setAdxId(AdxKeyConstant.ADX_AUTOHOME_VALUE);
				creativeAuditDao.insertSelective(mod);
			}
        } else {
        	JsonObject statusInfo = jsonMap.get("statusInfo").getAsJsonObject();
        	throw new IllegalStatusException(AuditErrorConstant.AUTOHOME_CREATIVE_AUDIT_ERROR_REASON + statusInfo.get("global").getAsString());
        }
	}
	
	@Transactional
	public void synchronize(String creativeId) throws Exception {
		AdxModel adxModel = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_MOMO_VALUE);
		String cexamineResultUrl = adxModel.getCexamineResultUrl();
		CreativeModel mapModel = creativeDao.selectByPrimaryKey(creativeId);
		if (mapModel == null) {
			throw new ResourceNotFoundException();
		}
		String privateKey = adxModel.getPrivateKey();//取出adx的私密key
		Gson gson = new Gson();
    	JsonObject json = gson.fromJson(privateKey, new JsonObject().getClass());
    	if (json.get("dspId") == null || json.get("signKey") == null) {
			throw new ResourceNotFoundException("autohome : 缺少私密key");//缺少私密key("auto创意提交第三方审核错误！原因：私密key不存在")
		}
    	//发送POST方法的请求所需参数
    	String dspId = json.get("dspId").getAsString();
		String signKey = json.get("signKey").getAsString();
		// 发送POST方法的请求所需参数
		CreativeAuditModelExample ex = new CreativeAuditModelExample();
		ex.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_AUTOHOME_VALUE);
		List<CreativeAuditModel> list = creativeAuditDao.selectByExample(ex);
		String remarkid = null;
		if (list != null && !list.isEmpty()) {
			remarkid = list.get(0).getAuditValue();
		}
		long timestamp = System.currentTimeMillis();
		// GET 和 POST 请求进行验签算法  参数拼接
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("dspId", dspId);
        params.put("creativeId", remarkid);
        params.put("timestamp", timestamp+"");
        // 数据签名生成
        String sign = getSign(params, signKey);
        String paramStr = "dspId=" + dspId + "&creativeId=" + remarkid + "&sign=" + sign + "&timestamp=" + timestamp;
        //发送GET方法的请求,返回结果
        String result = sendGet(cexamineResultUrl, paramStr);
        JsonObject res = gson.fromJson(result, new JsonObject().getClass());
        
        if (res.get("status").getAsInt() == 1) {
        	JsonObject data = res.get("data").getAsJsonObject();
        	JsonArray status = data.get("status").getAsJsonArray();
        	JsonObject resjson = status.get(0).getAsJsonObject();
        	int auditStatus = resjson.get("auditStatus").getAsInt();//返回的创意审核状态
        	//创意第三方审核状态，默认“审核中”
        	String creativeAdxStatus;
        	String message = null;
        	CreativeAuditModelExample example = new CreativeAuditModelExample();
			example.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_AUTOHOME_VALUE);
			CreativeAuditModel model = new CreativeAuditModel();
        	if (auditStatus == 1) {//通过
                creativeAdxStatus = StatusConstant.CREATIVE_AUDIT_SUCCESS;
            } else if (auditStatus == 2) {//拒绝
                creativeAdxStatus = StatusConstant.CREATIVE_AUDIT_FAILURE;
                message = resjson.get("auditComment").getAsString();
                model.setMessage(message);
            } else {//审核中
                creativeAdxStatus = StatusConstant.CREATIVE_AUDIT_WATING;
            }
        	model.setStatus(creativeAdxStatus);
        	creativeAuditDao.updateByExampleSelective(model, example);
        } else {
        	JsonObject statusInfo = res.get("statusInfo").getAsJsonObject();
        	throw new IllegalStatusException(AuditErrorConstant.AUTOHOME_CREATIVE_SYCHRONIZE_ERROR_REASON + statusInfo.get("global").getAsString());
        }
	}
	
	
	
    public void editAudit(String creativeId) throws Exception {
        audit(creativeId);//调新建接口（汽车之家会当作不同的素材来对待）
    }
	
	/**
	 * 获取广告主对应的行业ID
	 * @param industryId
	 * @return
	 */
	private String getIndustry(Integer industryId) throws Exception {
		IndustryAdxModelExample example = new IndustryAdxModelExample();
		example.createCriteria().andAdxIdEqualTo(AdxKeyConstant.ADX_AUTOHOME_VALUE).andIndustryIdEqualTo(industryId);
		List<IndustryAdxModel> list = industryAdxDao.selectByExample(example);
		String code = null;
		if(list!=null && !list.isEmpty()){
			IndustryAdxModel model = list.get(0);
			code = model.getIndustryCode();
		}
		return code;
	}
	
	/**
	 * 获取审核Value
	 * @param advertiserId
	 * @return
	 */
	private long getAdvertiserAuditValueByMapId(String advertiserId) throws Exception {
		//创意审核key
		Long advertiserValue = 0L;
		AdvertiserAuditModelExample example = new AdvertiserAuditModelExample();
		example.createCriteria().andAdvertiserIdEqualTo(advertiserId).andAdxIdEqualTo(AdxKeyConstant.ADX_AUTOHOME_VALUE);
		
		List<AdvertiserAuditModel> list = advertiserAuditDao.selectByExample(example);
		if (list == null || list.isEmpty()) {
			advertiserValue = (long) Math.floor((new Random()).nextDouble() * 1000000000D);
			// 创意审核表中插入数据
			AdvertiserAuditModel advAuditModel = new AdvertiserAuditModel();
			advAuditModel.setId(UUID.randomUUID().toString());
			advAuditModel.setAdxId(AdxKeyConstant.ADX_AUTOHOME_VALUE);
			advAuditModel.setAuditValue(String.valueOf(advertiserValue));
			advAuditModel.setAdvertiserId(advertiserId);
			advAuditModel.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
			advertiserAuditDao.insertSelective(advAuditModel);
		} else {
			AdvertiserAuditModel model = list.get(0);
			if (!StringUtils.isEmpty(model.getAuditValue())) {
				advertiserValue = Long.parseLong(model.getAuditValue());
			}
		}
		return advertiserValue;
	}
	
    /**
     * GET 和 POST 请求进行验签算法  （MD5加密）
     * @param params
     * @param signKey
     * @return
     */
    private String getSign(Map<String, String> params, String signKey){
        Set<String> keySet = params.keySet();
        List<String> keyList = new ArrayList<>();
        keyList.addAll(keySet);
        Collections.sort(keyList);
        List<String> paramList = new ArrayList<>();
        for (String key : keyList) {
            paramList.add(key + "=" + params.get(key));
        }
        String parameterStr = join(paramList, "&");
        return GlobalUtil.MD5(parameterStr + signKey);
    }
	
    /**
     * POST json
     * @param obj
     * @param signKey
     * @return
     */
    private String getJsonSign(JsonObject obj, String signKey){
        String parameterStr = obj.toString();
        return GlobalUtil.MD5(parameterStr + signKey);
    }
    
    /**
     * 将字符串通过关联字符拼接到一起
     * @param paramList
     * @param string
     * @return
     */
    private static String join(List<String> paramList, String string) {
    	StringBuilder buf = new StringBuilder(256);
    	for (int i=0;i<paramList.size();i++) {
    		buf.append(paramList.get(i));
    		if (i < paramList.size()-1) {
    			buf.append(string);
    		}
    	}
    	return buf.toString();
    }
    
    /**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) throws Exception{
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty(HTTP_ACCEPT, HTTP_ACCEPT_DEFAULT);
			connection.setRequestProperty(HTTP_CONNECTION, HTTP_CONNECTION_VAL);
			connection.setRequestProperty(HTTP_USER_AGENT, HTTP_USER_AGENT_VAL);
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			throw new ServerFailureException();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				throw new ServerFailureException();
			}
		}
		return result;
	}

	
}
