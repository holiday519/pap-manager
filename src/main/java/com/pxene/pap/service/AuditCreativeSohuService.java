package com.pxene.pap.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pxene.pap.common.GlobalUtil;
import com.pxene.pap.constant.AdxKeyConstant;
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
import com.pxene.pap.domain.models.InfoflowMaterialModel;
import com.pxene.pap.domain.models.LandpageModel;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.domain.models.VideoMaterialModel;
import com.pxene.pap.domain.models.VideoModel;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AdvertiserAuditDao;
import com.pxene.pap.repository.basic.AdvertiserDao;
import com.pxene.pap.repository.basic.AdxDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeAuditDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.ImageDao;
import com.pxene.pap.repository.basic.ImageMaterialDao;
import com.pxene.pap.repository.basic.IndustryAdxDao;
import com.pxene.pap.repository.basic.InfoflowMaterialDao;
import com.pxene.pap.repository.basic.LandpageDao;
import com.pxene.pap.repository.basic.ProjectDao;
import com.pxene.pap.repository.basic.VideoDao;
import com.pxene.pap.repository.basic.VideoMaterialDao;
import com.pxene.pap.repository.basic.view.CreativeImageDao;
import com.pxene.pap.repository.basic.view.CreativeInfoflowDao;
import com.pxene.pap.repository.basic.view.CreativeVideoDao;

@Service
public class AuditCreativeSohuService {
	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired
	private LandpageDao landpageDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private AdvertiserDao advertiserDao;
	
    @Autowired
    private AdvertiserAuditDao advertiserAuditDao;
    
    @Autowired
    private CreativeAuditDao creativeAuditDao;
	
	@Autowired
	private AuditAdvertiserBaiduService auditAdvertiserBaiduService;
	
	@Autowired
	private CreativeDao creativeDao;
	
	@Autowired
	private CreativeImageDao creativeImageDao;
	
	@Autowired
	private CreativeVideoDao creativeVideoDao;
	
	@Autowired
	private CreativeInfoflowDao creativeInfoflowDao;
	
	@Autowired
	private ImageMaterialDao imageMaterialDao;
	
	@Autowired
	private VideoMaterialDao videoMaterialDao;
	
	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private VideoDao videoDao;
	
	@Autowired
	private InfoflowMaterialDao infoDao;
	
	@Autowired
	private IndustryAdxDao industryAdxDao;
	
	@Autowired
	private AdxDao adxDao;
	
	private static String image_url;
	
	private static final String HTTP_ACCEPT = "accept";

	private static final String HTTP_ACCEPT_DEFAULT = "*/*";

	private static final String HTTP_USER_AGENT = "user-agent";

	private static final String HTTP_USER_AGENT_VAL = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)";

	private static final String HTTP_CONNECTION = "connection";

	private static final String HTTP_CONNECTION_VAL = "Keep-Alive";
	
	public static final String SOHU_ADVER_DELETE = "http://api.ad.sohu.com/exchange/material/delete";
	
	public static final String SOHU_ADVER_SIGN = "http://api.ad.sohu.com/test/apiauth";
	
	@Autowired
	public AuditCreativeSohuService(Environment env)
	{
		/**
		 * 获取图片上传路径
		 */
		image_url = env.getProperty("pap.fileserver.url.prefix");
	}
	
	@Transactional
	public void audit(String creativeId) throws Exception {
		AdxModel adxModel = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_SOHU_VALUE);
		String cexamineurl = adxModel.getCexamineUrl();
		CreativeModel mapModel = creativeDao.selectByPrimaryKey(creativeId);
		if (mapModel == null) {
			throw new ResourceNotFoundException();
		}
		String privateKey = adxModel.getPrivateKey();//取出adx的私密key
		Gson gson = new Gson();
    	JsonObject json = gson.fromJson(privateKey, new JsonObject().getClass());
    	if (json.get("auth_consumer_key") == null || json.get("secret") == null) {
			throw new ResourceNotFoundException("sohu : 缺少私密key");//缺少私密key("sohu创意提交第三方审核错误！原因：私密key不存在")
		}
    	String auth_consumer_key = json.get("auth_consumer_key").getAsString();
		String secret = json.get("secret").getAsString();
		AdvertiserAuditModel advertiserAuditModel = getAdvAuditByMapId(creativeId);
		String advStatue = StatusConstant.ADVERTISER_AUDIT_WATING; // 搜狐门户状态
		if (StringUtils.isEmpty(advStatue)) {
			throw new IllegalStatusException("搜狐创意提交第三方审核执行失败！原因：广告主审核状态异常" );
		}
		advStatue = advertiserAuditModel.getStatus();
		//发送POST方法的请求所需参数
		AdvertiserModel advertiser = getAdvertiserByMapId(creativeId);
		String campaignId = mapModel.getCampaignId();
		CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
		String landpageId = campaignModel.getLandpageId();
		LandpageModel landpageModel = landpageDao.selectByPrimaryKey(landpageId);
		String creativeurl = "";//素材源地址
		String type = mapModel.getType();
		String txttitle = " ";
		//广告类型  1：图片；2：Flash；3：视频；4：iframe；5：script；6：文字；7：特型广告
		int typeValue = 1;
		if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
			String imageMaterialId = mapModel.getMaterialId();
			ImageMaterialModel materialModel = imageMaterialDao.selectByPrimaryKey(imageMaterialId);
			ImageModel imageModel = imageDao.selectByPrimaryKey(materialModel.getImageId());
			creativeurl = image_url + imageModel.getPath();
			typeValue  = 1;
		} else if (StatusConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
			String videoId = mapModel.getMaterialId();
			VideoMaterialModel materialModel = videoMaterialDao.selectByPrimaryKey(videoId);
			VideoModel videoModel = videoDao.selectByPrimaryKey(materialModel.getVideoId());
			creativeurl = image_url + videoModel.getPath();
			typeValue = 3;
		} else if (StatusConstant.CREATIVE_TYPE_INFOFLOW.equals(type)) {
			InfoflowMaterialModel info = infoDao.selectByPrimaryKey(mapModel.getMaterialId());
			txttitle = info.getTitle();
			typeValue = 7;
			if (!StringUtils.isEmpty(info.getIconId())) {
				ImageModel imageModel = imageDao.selectByPrimaryKey(info.getIconId());
				creativeurl = image_url + imageModel.getPath();
			} else {
				ImageModel imageModel = imageDao.selectByPrimaryKey(info.getIconId());
				creativeurl = image_url + imageModel.getPath();
			}
		}
		String clicklink = landpageModel.getUrl();//落地页地址
		String auth_nonce = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 23);
		String auth_timestampStr = (System.currentTimeMillis() + "").substring(0,10);
		String click_monitor = "http://cl2.pxene.com" + landpageModel.getMonitorUrl();
		JsonArray impJson = new JsonArray();
		impJson.add(adxModel.getImpressionUrl());
		String imp = impJson.toString();
		String customer_key = advertiserAuditModel.getAuditValue();
		//投放方式。1：RTB；2：PDB；3：PMP；4：Preferred Deal
		int delivery_type = 1;
		
		String submit_to = "1";
		String sourceName = mapModel.getName();
		
		if (!StatusConstant.CREATIVE_AUDIT_SUCCESS.equals(advStatue)) {
			throw new IllegalStatusException("搜狐创意提交第三方审核执行失败！原因：广告主未在搜狐门户审核通过" );
		}
		//判断是先到自动过期时间（6个月）还是项目结束时间
		Date cEndDate = campaignModel.getEndDate();
		String endDate = String.valueOf((cEndDate.getTime()/1000) - (new Date().getTime()/1000));
		Date maxDate = new DateTime(new Date()).plusDays(180).toDate();
		if (cEndDate.getTime() > maxDate.getTime()) {
			endDate = String.valueOf((maxDate.getTime()/1000) - (new Date().getTime()/1000));
		}
		
		String advertisingType = getIndustry(advertiser.getIndustryId());
		
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("ad_source", "蓬景数字");//广告来源标识
		params.put("advertising_type", advertisingType);//素材所属的广告投放类型
		params.put("auth_consumer_key", auth_consumer_key);
		params.put("auth_nonce", auth_nonce);
		params.put("auth_signature_method", "HMAC-SHA1");
		params.put("auth_timestamp", auth_timestampStr);
		params.put("click_monitor", click_monitor);//点击监测地址
		params.put("customer_key", customer_key);
		params.put("delivery_type", String.valueOf(delivery_type));//指定素材将用于何种投放方式
		params.put("expire", endDate);//素材有效期（以s计，上限6个月，不传递默认30天）
		params.put("file_source", creativeurl);//素材源地址，不可重复
		params.put("gotourl", clicklink);//落地页地址
		params.put("imp", imp);//曝光监测地址
		if(typeValue == 7){//特型广告
			params.put("main_attr", "picture");//主素材属性 -- 特性广告
		}
		params.put("material_name", sourceName);//素材名称
		params.put("material_type",String.valueOf(typeValue));//素材类型
		if(typeValue == 7) {//特型广告
			params.put("slave", "[{\"source\":\"" + txttitle + "\",\"attr\":\"txt\"}]");//json格式的副素材信息 -- 特性广告
		}
		params.put("submit_to", submit_to);
		if(typeValue == 7) {//特型广告
			params.put("template", "picturetxt");//特型广告模板
		}
		String auth_signature = GlobalUtil.paramsToString(params, secret, cexamineurl, "POST");
		Map<String, String> pmap = new LinkedHashMap<String, String>();
		pmap.put("ad_source", "蓬景数字");//广告来源标识
		pmap.put("advertising_type", advertisingType);
		pmap.put("auth_consumer_key", auth_consumer_key);
		pmap.put("auth_nonce", auth_nonce);
		pmap.put("auth_signature", auth_signature);
		pmap.put("auth_signature_method", "HMAC-SHA1");
		pmap.put("auth_timestamp", auth_timestampStr);
		pmap.put("click_monitor", click_monitor);
		pmap.put("customer_key", customer_key);
		pmap.put("delivery_type", String.valueOf(delivery_type));//指定素材将用于何种投放方式
		pmap.put("expire", endDate);
		pmap.put("file_source", creativeurl);
		pmap.put("gotourl", clicklink);
		pmap.put("imp", imp);
		if(typeValue == 7) {//特型广告
			pmap.put("main_attr", "picture");
		}
		pmap.put("material_name", sourceName);
		pmap.put("material_type",String.valueOf(typeValue));//素材类型
		if(typeValue == 7) {//特型广告
			pmap.put("slave", "[{\"source\":\"" + txttitle + "\",\"attr\":\"txt\"}]");
		}
		pmap.put("submit_to", submit_to);
		if(typeValue == 7) {//特型广告
			pmap.put("template", "picturetxt");
		}
		String result = sendPost(cexamineurl, params);
        JsonObject jsonMap = gson.fromJson(result , new JsonObject().getClass());
        String status = jsonMap.get("status").getAsString();
        if(status.equals("true")){
        	CreativeAuditModelExample cex = new CreativeAuditModelExample();
			cex.createCriteria().andAdxIdEqualTo(AdxKeyConstant.ADX_SOHU_VALUE).andCreativeIdEqualTo(creativeId);
			CreativeAuditModel mod = new CreativeAuditModel();
			mod.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
			creativeAuditDao.updateByExampleSelective(mod, cex);
        } else {
        	throw new IllegalStatusException("搜狐创意提交第三方审核执行失败！原因：" + jsonMap.get("message").toString());
        }
	}
	
	@Transactional
	public void synchronize(String creativeId) throws Exception {
		AdxModel adxModel = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_SOHU_VALUE);
		String cexamineResultUrl = adxModel.getCexamineResultUrl();
		CreativeModel mapModel = creativeDao.selectByPrimaryKey(creativeId);
		if (mapModel == null) {
			throw new ResourceNotFoundException();
		}
		String privateKey = adxModel.getPrivateKey();//取出adx的私密key
		Gson gson = new Gson();
    	JsonObject json = gson.fromJson(privateKey, new JsonObject().getClass());
    	if (json.get("auth_consumer_key") == null || json.get("secret") == null) {
			throw new ResourceNotFoundException("sohu : 缺少私密key");//缺少私密key("sohu创意提交第三方审核错误！原因：私密key不存在")
		}
    	String auth_consumer_key = json.get("auth_consumer_key").getAsString();
		String secret = json.get("secret").getAsString();
		
		Map<String, Object> param = new HashMap<String, Object>();//查询所需参数
		param.put("adxid", AdxKeyConstant.ADX_SOHU_VALUE);
		param.put("creativeId", creativeId);
		
		String creativeurl = "";//素材源地址
		String type = mapModel.getType();
		if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
			String imageMaterialId = mapModel.getMaterialId();
			ImageMaterialModel materialModel = imageMaterialDao.selectByPrimaryKey(imageMaterialId);
			ImageModel imageModel = imageDao.selectByPrimaryKey(materialModel.getImageId());
			creativeurl = image_url + imageModel.getPath();
		} else if (StatusConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
			String videoId = mapModel.getMaterialId();
			VideoMaterialModel materialModel = videoMaterialDao.selectByPrimaryKey(videoId);
			VideoModel videoModel = videoDao.selectByPrimaryKey(materialModel.getVideoId());
			creativeurl = image_url + videoModel.getPath();
		} else if (StatusConstant.CREATIVE_TYPE_INFOFLOW.equals(type)) {
			InfoflowMaterialModel info = infoDao.selectByPrimaryKey(mapModel.getMaterialId());
			if (!StringUtils.isEmpty(info.getIconId())) {
				ImageModel imageModel = imageDao.selectByPrimaryKey(info.getIconId());
				creativeurl = image_url + imageModel.getPath();
			} else {
				ImageModel imageModel = imageDao.selectByPrimaryKey(info.getIconId());
				creativeurl = image_url + imageModel.getPath();
			}
		}
		String auth_nonce = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 23);
		String auth_signature_method = "HMAC-SHA1";
		String auth_timestampStr = (System.currentTimeMillis() + "").substring(0,10);
		AdvertiserAuditModel advertiserAuditModel = getAdvAuditByMapId(creativeId);
		String customer_key = advertiserAuditModel.getAuditValue();
		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
		paramsMap.put("auth_consumer_key", auth_consumer_key);
		paramsMap.put("auth_nonce", auth_nonce);
		paramsMap.put("auth_signature_method", auth_signature_method);
		paramsMap.put("auth_timestamp", auth_timestampStr);
		paramsMap.put("customer_key", customer_key);
		paramsMap.put("file_source", creativeurl);
		String auth_signature = GlobalUtil.paramsToString(paramsMap, secret, cexamineResultUrl, "POST");
		String paramStr = "auth_consumer_key=" + auth_consumer_key + "&auth_nonce=" + auth_nonce +
				"&auth_signature_method=" + auth_signature_method + "&auth_timestamp=" + auth_timestampStr +
				"&customer_key=" + customer_key + "&file_source=" + URLEncoder.encode(creativeurl, "UTF-8") + "&auth_signature=" + auth_signature;
		//发送GET请求，返回结果
        String result = sendGet(cexamineResultUrl, paramStr);
        //请求结果处理
        JsonObject jsonMap = gson.fromJson(result , new JsonObject().getClass());
        String status = jsonMap.get("status").getAsString();
        if ("true".equals(status)) {
        	String returnContent = jsonMap.get("content").getAsString();
        	JsonObject jsonContent = gson.fromJson(returnContent , new JsonObject().getClass());
        	JsonArray jsonArray = jsonContent.get("items").getAsJsonArray();
        	int state = 0; // 搜狐门户状态
			String audit_info = "";
            String message = "";
            if(jsonArray.size() != 0){
                for(int i=0;i<jsonArray.size();i++){
                    JsonObject obj = jsonArray.get(i).getAsJsonObject();
                    String _file_source = obj.get("file_source").getAsString();
                    if(creativeurl.equals(_file_source)){
                    	state = obj.get("status").getAsInt();
                		audit_info =obj.get("audit_info").getAsString();
                		break;
                    }
                }
            }
            String creativeAdxStatus = StatusConstant.CREATIVE_AUDIT_WATING;
            if (state == 0) {//未审核
				creativeAdxStatus = StatusConstant.CREATIVE_AUDIT_WATING;
			} else if(state == 1){//审核通过
				creativeAdxStatus = StatusConstant.CREATIVE_AUDIT_SUCCESS;
			} else if(state == 2){//拒绝
				message = "在搜狐门户审核失败原因：" + audit_info;
				creativeAdxStatus = StatusConstant.CREATIVE_AUDIT_FAILURE;
			}
            CreativeAuditModelExample example = new CreativeAuditModelExample();
			example.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_ADVIEW_VALUE);
			CreativeAuditModel model = new CreativeAuditModel();
			model.setStatus(creativeAdxStatus);
			if (!StringUtils.isEmpty(message)) {
				model.setMessage(message);
			}
			creativeAuditDao.updateByExampleSelective(model, example);
        } else {
        	throw new IllegalStatusException("搜狐创意同步第三方审核状态执行失败！原因：" + jsonMap.get("message").toString());
        }
	}
	

	public void editAudit(String creativeId) throws Exception {
		deleteAudit(creativeId);
		audit(creativeId);
	}
	
	public void deleteAudit(String creativeId) throws Exception {
		AdxModel adxModel = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_SOHU_VALUE);
		CreativeModel mapModel = creativeDao.selectByPrimaryKey(creativeId);
		if (mapModel == null) {
			throw new ResourceNotFoundException();
		}
		String privateKey = adxModel.getPrivateKey();//取出adx的私密key
		Gson gson = new Gson();
    	JsonObject json = gson.fromJson(privateKey, new JsonObject().getClass());
    	if (json.get("auth_consumer_key") == null || json.get("secret") == null) {
			throw new ResourceNotFoundException("sohu : 缺少私密key");//缺少私密key("sohu创意提交第三方审核错误！原因：私密key不存在")
		}
    	String auth_consumer_key = json.get("auth_consumer_key").getAsString();
		String secret = json.get("secret").getAsString();
		Map<String, Object> param = new HashMap<String, Object>();//查询所需参数
		param.put("adxid", AdxKeyConstant.ADX_SOHU_VALUE);
		param.put("creativeId", creativeId);
		String auth_nonce = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 23);
		String auth_signature_method = "HMAC-SHA1";
		String auth_timestampStr = (System.currentTimeMillis() + "").substring(0,10);
		AdvertiserAuditModel advertiserAuditModel = getAdvAuditByMapId(creativeId);
		String customer_key = advertiserAuditModel.getAuditValue();
		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
		paramsMap.put("auth_consumer_key", auth_consumer_key);
		paramsMap.put("auth_nonce", auth_nonce);
		paramsMap.put("auth_signature_method", auth_signature_method);
		paramsMap.put("auth_timestamp", auth_timestampStr);
		paramsMap.put("customer_key", customer_key);
		String creativeurl = "";//素材源地址
		String type = mapModel.getType();
		if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
			String imageMaterialId = mapModel.getMaterialId();
			ImageMaterialModel materialModel = imageMaterialDao.selectByPrimaryKey(imageMaterialId);
			ImageModel imageModel = imageDao.selectByPrimaryKey(materialModel.getImageId());
			creativeurl = image_url + imageModel.getPath();
		} else if (StatusConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
			String videoId = mapModel.getMaterialId();
			VideoMaterialModel materialModel = videoMaterialDao.selectByPrimaryKey(videoId);
			VideoModel videoModel = videoDao.selectByPrimaryKey(materialModel.getVideoId());
			creativeurl = image_url + videoModel.getPath();
		} else if (StatusConstant.CREATIVE_TYPE_INFOFLOW.equals(type)) {
			InfoflowMaterialModel info = infoDao.selectByPrimaryKey(mapModel.getMaterialId());
			if (!StringUtils.isEmpty(info.getIconId())) {
				ImageModel imageModel = imageDao.selectByPrimaryKey(info.getIconId());
				creativeurl = image_url + imageModel.getPath();
			} else {
				ImageModel imageModel = imageDao.selectByPrimaryKey(info.getIconId());
				creativeurl = image_url + imageModel.getPath();
			}
		}
		paramsMap.put("file_source", creativeurl);
		String auth_signature = GlobalUtil.paramsToString(paramsMap, secret, SOHU_ADVER_DELETE, "POST");//对SHA1结果进行base64
		Map<String, String> pmap = new LinkedHashMap<String, String>();
		pmap.put("auth_consumer_key", auth_consumer_key);
		pmap.put("auth_nonce", auth_nonce);
		pmap.put("auth_signature", auth_signature);
		pmap.put("auth_signature_method", auth_signature_method);
		pmap.put("auth_timestamp", auth_timestampStr);
		pmap.put("customer_key", customer_key);
		pmap.put("file_source", creativeurl);
		String result = sendPost(SOHU_ADVER_DELETE, pmap);
        JsonObject jsonMap = gson.fromJson(result , new JsonObject().getClass());
        String status = jsonMap.get("status").getAsString();
        if(!status.equals("true")){
			throw new IllegalStatusException("搜狐创意重新提交第三方审核执行失败！原因：删除旧素材失败，" + jsonMap.get("message").toString());
		}
	}
	
	/**
	 * 查询创意对应广告主的审核值
	 * @param mapId
	 * @return
	 * @throws Exception
	 */
	public AdvertiserAuditModel getAdvAuditByMapId (String mapId) throws Exception {
		//查询活动ID
		CreativeModel creativeModel = creativeDao.selectByPrimaryKey(mapId);
		String campaignId = creativeModel.getCampaignId();
		//查询项目ID
		CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
		String projectId = campaignModel.getProjectId();
		//查询广告主ID
		ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
		String advertiserId = projectModel.getAdvertiserId();
		//查询广告主审核Key
		AdvertiserAuditModelExample example = new AdvertiserAuditModelExample();
		example.createCriteria().andAdvertiserIdEqualTo(advertiserId).andAdxIdEqualTo(AdxKeyConstant.ADX_SOHU_VALUE);
		List<AdvertiserAuditModel> list = advertiserAuditDao.selectByExample(example);
		if (list == null || list.isEmpty()) {
			throw new IllegalAccessError("搜狐广告主未审核");
		}
		AdvertiserAuditModel value = null;
		for (AdvertiserAuditModel adv : list) {
			value = adv;
		}
		return value;
	}
	
	/**
	 * 查询创意对应广告主
	 * @param mapId
	 * @return
	 * @throws Exception
	 */
	public AdvertiserModel getAdvertiserByMapId (String mapId) throws Exception {
		//查询活动ID
		CreativeModel campaignCreativeModel = creativeDao.selectByPrimaryKey(mapId);
		String campaignId = campaignCreativeModel.getCampaignId();
		//查询项目ID
		CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
		String projectId = campaignModel.getProjectId();
		//查询广告主ID
		ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
		String advertiserId = projectModel.getAdvertiserId();
		//查询广告主
		AdvertiserModel advertiserModel = advertiserDao.selectByPrimaryKey(advertiserId);
		
		return advertiserModel;
	}
	
	/**
	 * 获取广告主对应的行业ID
	 * @param industryId
	 * @return
	 */
	private String getIndustry(Integer industryId) throws Exception {
		IndustryAdxModelExample example = new IndustryAdxModelExample();
		example.createCriteria().andAdxIdEqualTo(AdxKeyConstant.ADX_SOHU_VALUE)
			.andIndustryIdEqualTo(industryId);
		List<IndustryAdxModel> list = industryAdxDao.selectByExample(example);
		String code = null;
		if(list!=null && !list.isEmpty()){
			IndustryAdxModel model = list.get(0);
			code = model.getIndustryCode();
		}
		return code;
	}
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, Map<String, String> param) {
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			List<NameValuePair> formParams = new ArrayList<NameValuePair>(); //构建POST请求的表单参数 
	        for(Map.Entry<String,String> entry : param.entrySet()){ 
	        	formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue())); 
	        } 
			post.setEntity(new UrlEncodedFormEntity(formParams,Charset.forName("UTF-8")));
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
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
	public static String sendGet(String url, String param) {
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
			// 获取所有响应头字段
//			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
//			for (String key : map.keySet()) {
//				System.out.println(key + "--->" + map.get(key));
//			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
}
