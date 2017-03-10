package com.pxene.pap.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
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
import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.GlobalUtil;
import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.models.AdvertiserModel;
import com.pxene.pap.domain.models.AdxModel;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CreativeAuditModel;
import com.pxene.pap.domain.models.CreativeAuditModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.ImageModel;
import com.pxene.pap.domain.models.IndustryAdxModel;
import com.pxene.pap.domain.models.IndustryAdxModelExample;
import com.pxene.pap.domain.models.InfoflowMaterialModel;
import com.pxene.pap.domain.models.LandpageModel;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.repository.basic.AdvertiserDao;
import com.pxene.pap.repository.basic.AdxDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeAuditDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.ImageDao;
import com.pxene.pap.repository.basic.IndustryAdxDao;
import com.pxene.pap.repository.basic.InfoflowMaterialDao;
import com.pxene.pap.repository.basic.LandpageDao;
import com.pxene.pap.repository.basic.ProjectDao;

@Service
public class AuditCreativeMomoService {

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
	private CreativeDao creativeDao;
	@Autowired
	private InfoflowMaterialDao infoflowDao;
	@Autowired
	private IndustryAdxDao industryAdxDao;
	@Autowired
	private CreativeAuditDao creativeAuditDao;
	
	private static String image_url;
	
	public AuditCreativeMomoService(Environment env) {
		/**
		 * 获取图片上传路径
		 */
		image_url = env.getProperty("pap.fileserver.url.prefix");
	}
	@Transactional
	public void audit(String creativeId) throws Exception {
		AdxModel adxModel = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_MOMO_VALUE);
		String cexamineurl = adxModel.getCexamineUrl();
		//私密key：dspid 和 appkey 为一个值"pxene"
        String dspid = "pxene";
        //发送POST方法的请求所需参数
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
		
        String advertiserName = advertiserModel.getCompany();//广告主名称（公司名称）
//        String clicklink = landpageModel.getMonitorUrl();//落地页
        String landurl = landpageModel.getUrl();//真实落地页
//        String promotiontype = "b";//推广类型（a：应用推广（下载）  b：品牌推广  c：效果推广）
        boolean quality_level = true;//是否是高质量素材，默认是
        JsonObject native_creative = new JsonObject();
        String expiry_date = DateUtils.getDayOfChange(new Date(), 120);
        if (StatusConstant.CREATIVE_TYPE_INFOFLOW.equals(creativeModel.getType())) {
        	//判断是先到自动过期时间（4个月）还是项目结束时间
        	//获取截止时间
        	if (campaignModel.getEndDate().after(new DateTime(new Date()).plusDays(120).toDate())) {
        		expiry_date = new DateTime(campaignModel.getEndDate()).toString("yyyy-MM-dd", Locale.CHINESE);
        	}
        	expiry_date = expiry_date + " 23:59:59";
        	String puton_to;//广告样式 - 拼接使用（投放位置：附近动态、附近人、好友动态）
            String puton_type;//广告样式 - 拼接使用（投放类型：打开网页、下载（IOS、安卓））
            String puton_img;//广告样式 - 拼接使用（图片数量：无图、大图、三图）
            
            InfoflowMaterialModel infoflowModel = infoflowDao.selectByPrimaryKey(creativeModel.getMaterialId());
            String title = infoflowModel.getTitle();
            native_creative.addProperty("title", title);
            if (!StringUtils.isEmpty(infoflowModel.getDescription())) {
            	native_creative.addProperty("desc", infoflowModel.getDescription());//推广语
            }
            String iconId = infoflowModel.getIconId();
            if (StringUtils.isEmpty(iconId)) {
            	throw new IllegalArgumentException("陌陌创意提交第三方审核执行失败！原因：信息流广告必须上传logo！" );
            }
            native_creative.add("logo", getImageInfo(iconId));
            
            JsonArray image = new JsonArray();
            String image1 = infoflowModel.getImage1Id();
			String image2 = infoflowModel.getImage2Id();
			String image3 = infoflowModel.getImage3Id();
			String image4 = infoflowModel.getImage4Id();
			String image5 = infoflowModel.getImage5Id();
			if (!StringUtils.isEmpty(image1)) {
				image.add(getImageInfo(image1));
			}
			if (!StringUtils.isEmpty(image2)) {
				image.add(getImageInfo(image2));
			}
			if (!StringUtils.isEmpty(image3)) {
				image.add(getImageInfo(image3));
			}
			if (!StringUtils.isEmpty(image4)) {
				image.add(getImageInfo(image4));
			}
			if (!StringUtils.isEmpty(image5)) {
				image.add(getImageInfo(image5));
			}
			if (image.size() > 0) {
				if (image.size() == 1) {// 大图
					puton_img = "LARGE_IMG";
				} else {// 三图
					puton_img = "SMALL_IMG";
				}
				native_creative.add("image", image);//大图
				puton_to = "FEED";
			} else {
				// 无图片模板 -- 只支持普通素材（附近的人）
                puton_to = "NEARBY";
                puton_img = "NO_IMG";
                quality_level = false;
			}
			native_creative.addProperty("landingpage_url", landurl.replaceAll("&", "%26"));//落地页的点击地址，落地页广告必填
            puton_type = "LANDING_PAGE";
            native_creative.addProperty("native_format", puton_to + "_" + puton_type + "_" + puton_img);//广告样式
        } else {
        	throw new IllegalStatusException("陌陌创意提交第三方审核执行失败！原因：创意类型不支持！");
        }
        JsonArray cats = new JsonArray();//行业id
        cats.add(getIndustry(advertiserModel.getIndustryId()));
        
        JsonObject json = new JsonObject();
        json.addProperty("dspid", dspid);//DSP ID
        json.addProperty("cid", campaignId);//Campaign ID （cid、adid、crid可以填写同一值——现cid写campaignid，adid、crid写mapid）
        json.addProperty("adid", creativeId);//广告 ID
        json.addProperty("crid", creativeId);//创意 ID
        json.addProperty("advertiser_id", advertiserId);//广告主 ID
        json.addProperty("advertiser_name", advertiserName);//广告主名称（公司名称）
        if (quality_level) { //质量等级(1：优质，附近动态、好友动态投放，不填：普通，附近动态、附近人投放)
            json.addProperty("quality_level", 1);//质量等级，可选字段，投放好友动态的素材设置为1
        }
//        JsonArray arr = new JsonArray();
//        json.add("display_labels", arr);
        json.add("cat", cats);//行业类目
        json.add("native_creative", native_creative);//信息流广告内容
        long uptime = System.currentTimeMillis()/1000;
        json.addProperty("uptime", uptime);//提交时间，Unix时间戳，单位秒，数字类型
        json.addProperty("expiry_date", expiry_date);//素材过期时间，最大支持当前日期往后120天
        // 签名生成
        String signStr = "appkey" + dspid + "crid" + creativeId + "uptime" + uptime;
        
        String sign = GlobalUtil.MD5(signStr).toUpperCase();
        json.addProperty("sign", sign);
        String code = "data=" + json.toString();
        String result = getResult(cexamineurl, code);
        Gson gson = new Gson();
        JsonObject jsonMap = gson.fromJson(result, new JsonObject().getClass());
        if (jsonMap.get("ec").getAsInt() == 200) {
			CreativeAuditModel mod = new CreativeAuditModel();
			mod.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
			mod.setId(UUID.randomUUID().toString());
			mod.setAdxId(AdxKeyConstant.ADX_MOMO_VALUE);
			creativeAuditDao.insertSelective(mod);
        } else {
        	throw new IllegalStatusException("陌陌创意提交第三方审核执行失败！原因：" + jsonMap.get("em").getAsString());
        }
     } 
	
	@Transactional
	public void synchronize(String creativeId) throws Exception {
		AdxModel adxModel = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_MOMO_VALUE);
		String cexamineResultUrl = adxModel.getCexamineResultUrl();//创意审核结果
		//私密key：dspid 和 appkey 为一个值"pxene"
        String dspid = "pxene";
        //发送POST方法的请求所需参数
        JsonObject json = new JsonObject();
        JsonArray arr = new JsonArray();
        arr.add(creativeId);
        json.addProperty("dspid", dspid);
        json.add("crids", arr);
        String code = "data=" + json.toString();
        
        String result = getResult(cexamineResultUrl, code);
        
        Gson gson = new Gson();
        JsonObject jsonMap = gson.fromJson(result, new JsonObject().getClass());
        
        if ("200".equals(jsonMap.get("ec").getAsString())) {
        	JsonArray array = jsonMap.get("data").getAsJsonArray();
        	for (Object je : array) {
        		JsonObject object = gson.fromJson(je.toString(), new JsonObject().getClass());
        		CreativeAuditModelExample example = new CreativeAuditModelExample();
    			example.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_MOMO_VALUE);
    			CreativeAuditModel model = new CreativeAuditModel();
    			if (object.get("status").getAsInt() == 1) {// 未审核
    				model.setStatus(StatusConstant.CREATIVE_AUDIT_NOCHECK);
    			} else if (object.get("status").getAsInt() == 2) {// 审核通过
    				model.setStatus(StatusConstant.CREATIVE_AUDIT_SUCCESS);
    			} else if (object.get("status").getAsInt() == 3) {// 审核未通过
    				model.setStatus(StatusConstant.CREATIVE_AUDIT_FAILURE);
    			}
    			if (!StringUtils.isEmpty(object.get("reason").getAsString())) {
    				model.setMessage(object.get("reason").getAsString());
    			}
    			creativeAuditDao.updateByExampleSelective(model, example);
        	}
        } else {
        	throw new IllegalStatusException("陌陌创意同步第三方审核状态执行失败！原因：" + jsonMap.get("em").getAsString());
        }
	}
	
	@Transactional
    public void editAudit(String creativeId) throws Exception {
        audit(creativeId);//直接调新建接口（再次提交，无论原来状态是什么，均变为“待审核”）
    }
	
	/** 连接到TOP服务器并获取数据 */
	public static String getResult(String urlStr, String content) {
		URL url = null;
		HttpURLConnection connection = null;

		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.connect();

			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			out.write(content.getBytes("utf-8"));
			out.flush();
			out.close();
			

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return null;
	}
	
	/**
	 * 获取广告主对应的行业ID
	 * @param industryId
	 * @return
	 */
	private String getIndustry(Integer industryId) throws Exception {
		IndustryAdxModelExample example = new IndustryAdxModelExample();
		example.createCriteria().andAdxIdEqualTo(AdxKeyConstant.ADX_MOMO_VALUE)
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
	 * 获取图片信息
	 * @param imageId
	 * @return
	 * @throws Exception
	 */
	public JsonObject getImageInfo(String imageId) throws Exception {
		 ImageModel imageModel = imageDao.selectByPrimaryKey(imageId);
         JsonObject obj = new JsonObject();
         obj.addProperty("url", image_url +  imageModel.getPath());
//         obj.addProperty("url", "http://www.immomo.com/static/w5/img/website/map.jpg");
         obj.addProperty("width", imageModel.getWidth());
         obj.addProperty("height", imageModel.getHeight());
         return obj;
	}
	
}
