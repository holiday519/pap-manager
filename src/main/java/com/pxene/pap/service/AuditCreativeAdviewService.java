package com.pxene.pap.service;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import com.pxene.pap.domain.models.VideoModel;
import com.pxene.pap.domain.models.view.CampaignTargetModel;
import com.pxene.pap.domain.models.view.CampaignTargetModelExample;
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
import com.pxene.pap.repository.basic.view.CampaignTargetDao;
import com.pxene.pap.repository.basic.view.CreativeImageDao;
import com.pxene.pap.repository.basic.view.CreativeInfoflowDao;
import com.pxene.pap.repository.basic.view.CreativeVideoDao;
@Service
public class AuditCreativeAdviewService {

	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired
	private LandpageDao landpageDao;
	
//	@Autowired
//	private SizeDao sizeDao;
	
	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private VideoDao videoDao;
	
	@Autowired
	private ImageMaterialDao imageMaterialDao;
	
	@Autowired
	private InfoflowMaterialDao InfoflowDao;
	
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
	private IndustryAdxDao industryAdxDao;
	
	@Autowired
	private CampaignTargetDao campaignTargetDao;
	
	@Autowired
	private AdxDao adxDao;
	
	private static String image_url;
	
	public AuditCreativeAdviewService(Environment env) {
		/**
		 * 获取图片上传路径
		 */
		image_url = env.getProperty("pap.fileserver.url.prefix");
	}
	
	@Transactional
	public void audit(String creativeId) throws Exception {
		AdxModel adxModel = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_ADVIEW_VALUE);
		String cexamineurl = adxModel.getCexamineUrl();
		CreativeModel mapModel = creativeDao.selectByPrimaryKey(creativeId);
		if (mapModel == null) {
			throw new ResourceNotFoundException();
		}
		String privateKey = adxModel.getPrivateKey();//取出adx的私密key
		Gson gson = new Gson();
    	JsonObject json = gson.fromJson(privateKey, new JsonObject().getClass());
    	if (json.get("dspId") == null || json.get("token") == null) {
			throw new ResourceNotFoundException("adview : 缺少私密key");//缺少私密key("adview创意提交第三方审核错误！原因：私密key不存在")
		}
    	//将私密key转成json格式
		String userId = json.get("userId").getAsString();
		String accessKey = json.get("accessKey").getAsString();
		String channel = json.get("channel").getAsString();
		
		AdvertiserModel advertiserModel = getAdvertiserByMapId(creativeId);
		String advertiserId = advertiserModel.getId();//广告主ID
		
		//查询广告主Value，没有就新增；（adview广告主不用审核）
		long num;
		AdvertiserAuditModelExample ex = new AdvertiserAuditModelExample();
		ex.createCriteria().andAdvertiserIdEqualTo(advertiserId).andAdxIdEqualTo(AdxKeyConstant.ADX_ADVIEW_VALUE);
		List<AdvertiserAuditModel> list = advertiserAuditDao.selectByExample(ex);
		AdvertiserAuditModel model = new AdvertiserAuditModel();
		model.setId(UUID.randomUUID().toString());
		model.setAdvertiserId(advertiserId);
		model.setStatus(StatusConstant.ADVERTISER_AUDIT_WATING);
		if (list == null || list.isEmpty()) {
			num = (long) Math.floor((new Random()).nextDouble() * 100000D);//————————————————————
			model.setAuditValue(String.valueOf(num));
			advertiserAuditDao.insertSelective(model);
		} else {
			num = Long.parseLong(list.get(0).getAuditValue());
		}
		//活动系统定向
		Integer system = -1;
		String campaignId = mapModel.getCampaignId();
		CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
		String landpageId = campaignModel.getLandpageId();
		LandpageModel landpageModel = landpageDao.selectByPrimaryKey(landpageId);
		CampaignTargetModelExample example = new CampaignTargetModelExample();
		example.createCriteria().andIdEqualTo(campaignId);
		List<CampaignTargetModel> targetList = campaignTargetDao.selectByExampleWithBLOBs(example);
		if (targetList != null && !targetList.isEmpty()) {
			CampaignTargetModel tmodel = targetList.get(0);
			String os = tmodel.getOs();
			if (os.indexOf("1") > 0 && os.indexOf("2") < 0) {
				system = 1;
			} else if (os.indexOf("2") > 0 && os.indexOf("1") < 0) {
				system = 0;
			}
		}
		//创意审核key
		Integer businessType = 0;//businessType（行业类型查询）
		long creativeIdvalue = getCrativeAuditValueByMapId(creativeId);
		Integer industryId = advertiserModel.getIndustryId();//行业id
		String industryCode  = getIndustry(industryId);// 行业idcode
		if (StringUtils.isEmpty(industryCode)) {
			businessType = Integer.parseInt(industryCode);
		}
		//json 里元素 orig(包含的创意素材列表)(json数组) ： origs(json数组里元素)
		JsonArray orig = new JsonArray();
		JsonObject origs = new JsonObject();
		origs.addProperty("originalityId", String.valueOf(creativeIdvalue));
//		origs.addProperty("originalityName", bean.getName());
		origs.addProperty("clickURL", landpageModel.getUrl());
		//广告位类型adType: 0 –横幅(条) 1 –插屏 4 –开屏
		String type = mapModel.getType();
		if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
			String imageMaterialId = mapModel.getMaterialId();
			ImageMaterialModel materialModel = imageMaterialDao.selectByPrimaryKey(imageMaterialId);
			ImageModel imageModel = imageDao.selectByPrimaryKey(materialModel.getId());
//			String sizeId = imageModel.getSizeId();
//			SizeModel sizeModel = sizeDao.selectByPrimaryKey(sizeId);
			String imageName = imageModel.getWidth() + "x" + imageModel.getHeight();//名称、尺寸
			String imageUrl = imageModel.getPath();
			origs.addProperty("originalityName", imageName);
			JsonObject imageObj = new JsonObject();
			imageObj.addProperty(imageName, image_url + imageUrl);
			origs.addProperty("images", imageObj.getAsString());
			origs.addProperty("type", 1);
//			String promotionId = sizeModel.getPromotionId();0
//			PromotionModel promotionModel = promotionDao.selectByPrimaryKey(promotionId);
//			Integer code = promotionModel.getCode();
//			String adType = "0";
//			if (code == 0) {
//				adType = "0";
//			} else if (code == 1) {
//				adType = "1";
//			} else if (code == 3) {
//				adType = "4";
//			}
//			origs.addProperty("adtype", adType);//--------------------需要图片广告位类型
			
		} else if (StatusConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
			String videoId = mapModel.getMaterialId();
			VideoModel videoModel = videoDao.selectByPrimaryKey(videoId);
//			String sizeId = videoModel.getSizeId();
//			SizeModel sizeModel = sizeDao.selectByPrimaryKey(sizeId);
			String imageMaterialId = mapModel.getMaterialId();
			ImageMaterialModel materialModel = imageMaterialDao.selectByPrimaryKey(imageMaterialId);
			ImageModel imageModel = imageDao.selectByPrimaryKey(materialModel.getId());
			String videoName = imageModel.getWidth() + "x" + imageModel.getHeight();//名称、尺寸
			String videoUrl = videoModel.getPath();
			origs.addProperty("jumpLinkVideo", videoUrl);
			origs.addProperty("resolutionVideo", videoName);
			origs.addProperty("type", 3);
			
		} else if (StatusConstant.CREATIVE_TYPE_INFOFLOW.equals(type)) {
			String infoId= mapModel.getMaterialId();
			InfoflowMaterialModel infoflowModel = InfoflowDao.selectByPrimaryKey(infoId);
			//原生图片信息
			JsonArray nativePic = new JsonArray();
			if (infoflowModel.getIconId() != null) {
				String imageId = infoflowModel.getIconId();
				addImageInfo(nativePic, imageId);
			}
			if (infoflowModel.getImage1Id() != null) {
				String imageId = infoflowModel.getImage1Id();
				addImageInfo(nativePic, imageId);
			}
			if (infoflowModel.getImage2Id() != null) {
				String imageId = infoflowModel.getImage2Id();
				addImageInfo(nativePic, imageId);
			}
			if (infoflowModel.getImage3Id() != null) {
				String imageId = infoflowModel.getImage3Id();
				addImageInfo(nativePic, imageId);
			}
			if (infoflowModel.getImage4Id() != null) {
				String imageId = infoflowModel.getImage4Id();
				addImageInfo(nativePic, imageId);
			}
			if (infoflowModel.getImage5Id() != null) {
				String imageId = infoflowModel.getImage5Id();
				addImageInfo(nativePic, imageId);
			}
			//原生文本信息
			JsonArray nativeText = new JsonArray();
			nativeText.add(infoflowModel.getTitle());
			//原生数据信息
			JsonArray nativeData = new JsonArray();
			if(infoflowModel.getDescription() != null){
				JsonObject nativeData1 = new JsonObject();
				nativeData1.addProperty("type","2");//广告描述文本
				nativeData1.addProperty("data",infoflowModel.getDescription());
				nativeData.add(nativeData1);
			}
			origs.add("nativePic", nativePic);//原生图片信息
			origs.add("nativeText", nativeText);//原生文本信息
			origs.add("nativeData", nativeData );//原生数据信息
			origs.addProperty("type", 4 );
		}
		orig.add(origs);
		DateTime stime = new DateTime(campaignModel.getStartDate());
		DateTime etime = new DateTime(campaignModel.getEndDate());
		String start = stime.toString("yyyy-MM-dd", Locale.CHINESE);
		String end= etime.toString("yyyy-MM-dd", Locale.CHINESE);
		JsonObject lastjson = new JsonObject();
		lastjson.addProperty("adId", String.valueOf(num));
		lastjson.addProperty("adName", advertiserModel.getName());
		lastjson.addProperty("businessType", businessType );
		lastjson.addProperty("system", system );
		lastjson.addProperty("startDate", start);
		lastjson.addProperty("endDate", end);
		lastjson.addProperty("channel", Integer.valueOf(channel));
		lastjson.addProperty("userId", userId );
		lastjson.addProperty("token", MD5(userId + accessKey));
		lastjson.add("orig", orig);
		
		String code = lastjson.getAsString();
		
		String result = GlobalUtil.sendPost(cexamineurl, code);
//		System.out.println("快友创意提交第三方审核，请求时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
//				+ "请求参数：" + code + "，请求地址：" + cexamineurl + "，返回结果：" + result);
		JsonObject jsonMap = gson.fromJson(result, new JsonObject().getClass());
		if (jsonMap.get("status").getAsInt() == 0) {
			CreativeAuditModelExample cex = new CreativeAuditModelExample();
			cex.createCriteria().andAdxIdEqualTo(AdxKeyConstant.ADX_ADVIEW_VALUE).andCreativeIdEqualTo(creativeId);
			CreativeAuditModel mod = new CreativeAuditModel();
			mod.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
			creativeAuditDao.updateByExampleSelective(mod, cex);
		} else {//失败
			String msg = jsonMap.get("msg").getAsString();
			CreativeAuditModelExample cex = new CreativeAuditModelExample();
			cex.createCriteria().andAdxIdEqualTo(AdxKeyConstant.ADX_ADVIEW_VALUE).andCreativeIdEqualTo(creativeId);
			CreativeAuditModel mod = new CreativeAuditModel();
			mod.setStatus(StatusConstant.CREATIVE_AUDIT_FAILURE);
			mod.setMessage(msg);
			creativeAuditDao.updateByExampleSelective(mod, cex);
		}
	}
	
	/**
	 * 同步结果
	 * @param creativeId
	 * @throws Exception
	 */
	public void synchronize(String creativeId) throws Exception {
		AdxModel adxModel = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_ADVIEW_VALUE);
		String cexamineResultUrl = adxModel.getCexamineResultUrl();
		String privateKey = adxModel.getPrivateKey();//取出adx的私密key
		Gson gson = new Gson();
    	JsonObject json = gson.fromJson(privateKey, new JsonObject().getClass());
    	if (json.get("dspId") == null || json.get("token") == null) {
			throw new ResourceNotFoundException("adview : 缺少私密key");//缺少私密key("adview创意提交第三方审核错误！原因：私密key不存在")
		}
    	String userId = json.get("userId").getAsString();
		String accessKey = json.get("accessKey").getAsString();
		String channel = json.get("channel").getAsString();
		Map<String, Object> param = new HashMap<String, Object>();//查询所需参数
		param.put("adxid", adxModel.getId());
		param.put("creativeId", creativeId);
		String token = MD5(userId + accessKey);//MD5加密处理
		
		CreativeAuditModelExample ex = new CreativeAuditModelExample();
		ex.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_BAIDU_VALUE);
		List<CreativeAuditModel> list = creativeAuditDao.selectByExample(ex);
		CreativeAuditModel auditModel = null;
		if (list != null && !list.isEmpty()) {
			for (CreativeAuditModel cre : list) {
				auditModel = cre;
			}
		} else {
			throw new ResourceNotFoundException();
		}
		String auditValue = auditModel.getAuditValue();
		
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("userId", userId );
		jsonObj.addProperty("channel", Integer.valueOf(channel) );
		jsonObj.addProperty("token", token);
		jsonObj.addProperty("originalityId", auditValue);
		
		String result = GlobalUtil.sendPost(cexamineResultUrl, json.toString());
		//返回结构中取出reslut
		JsonObject jsonMap = gson.fromJson(result, new JsonObject().getClass());
		JsonArray datas = jsonMap.get("data").getAsJsonArray();
		String msg = jsonMap.get("msg").getAsString();
		for (Object dataObj : datas) {
			JsonObject data = gson.fromJson(dataObj.toString(), new JsonObject().getClass());
			CreativeAuditModelExample example = new CreativeAuditModelExample();
			example.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_ADVIEW_VALUE);
			CreativeAuditModel model = new CreativeAuditModel();
			if (data.get("status").getAsInt() == 1) {// 未审核
				model.setStatus(StatusConstant.CREATIVE_AUDIT_NOCHECK);
			} else if (data.get("status").getAsInt() == 2) {// 审核通过
				model.setStatus(StatusConstant.CREATIVE_AUDIT_SUCCESS);
			} else if (data.get("status").getAsInt() == 3) {// 审核未通过
				model.setStatus(StatusConstant.CREATIVE_AUDIT_FAILURE);
			}
			if (!StringUtils.isEmpty(msg)) {
				model.setMessage(msg);
			}
			creativeAuditDao.updateByExampleSelective(model, example);
		}
	}
	
	/**
	 * 图片信息数组整合
	 * @param nativePic
	 * @param imageId
	 */
	public void addImageInfo(JsonArray nativePic, String imageId ) throws Exception {
		
		ImageModel imageModel = imageDao.selectByPrimaryKey(imageId);
//		if (imageModel != null) {
//			String sizeId = imageModel.getSizeId();
//			SizeModel sizeModel = sizeDao.selectByPrimaryKey(sizeId);
			if (imageModel != null) {
				String size = imageModel.getWidth() + "x" + imageModel.getHeight();//尺寸
				String url = imageModel.getPath();
				JsonObject imageObj = new JsonObject();
				imageObj.addProperty("size", size);
				imageObj.addProperty("url", url);
				nativePic.add(imageObj);
			}
//		}
	}
	
	/**
	 * 加密
	 * @param input
	 * @return
	 */
    public static String MD5(String input) {
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(input.getBytes());
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < md.length; i++) {
                String shaHex = Integer.toHexString(md[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
	
	/**
	 * 查询创意对应广告主的审核值
	 * @param mapId
	 * @return
	 * @throws Exception
	 */
	public Long getAdvAuditValueByMapId (String mapId) throws Exception {
		//查询活动ID
		CreativeModel campaignCreativeModel = creativeDao.selectByPrimaryKey(mapId);
		String campaignId = campaignCreativeModel.getCampaignId();
		//查询项目ID
		CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
		String projectId = campaignModel.getProjectId();
		//查询广告主ID
		ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
		String advertiserId = projectModel.getAdvertiserId();
		//查询广告主审核Key
		AdvertiserAuditModelExample example = new AdvertiserAuditModelExample();
		example.createCriteria().andAdvertiserIdEqualTo(advertiserId).andAdxIdEqualTo(AdxKeyConstant.ADX_BAIDU_VALUE);
		List<AdvertiserAuditModel> list = advertiserAuditDao.selectByExample(example);
		if (list == null || list.isEmpty()) {
			throw new IllegalAccessError("百度广告主未审核");
		}
		long value = -1L;
		for (AdvertiserAuditModel adv : list) {
			String auditValue= adv.getAuditValue();
			if(!StringUtils.isEmpty(auditValue)){
				value = Long.parseLong(auditValue);
			}
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
	 * 获取审核Value
	 * @param mapId
	 * @return
	 */
	private long getCrativeAuditValueByMapId(String mapId) throws Exception {
		//创意审核key
		Long creativeIdvalue = 0L;
		CreativeAuditModelExample example = new CreativeAuditModelExample();
		example.createCriteria().andCreativeIdEqualTo(mapId).andAdxIdEqualTo(AdxKeyConstant.ADX_ADVIEW_VALUE);
		List<CreativeAuditModel> list = creativeAuditDao.selectByExample(example);
		if (list == null || list.isEmpty()) {
			creativeIdvalue = (long) Math.floor((new Random()).nextDouble() * 1000000000D);
			// 创意审核表中插入数据
			CreativeAuditModel creativeAuditModel = new CreativeAuditModel();
			creativeAuditModel.setId(UUID.randomUUID().toString());
			creativeAuditModel.setAdxId(AdxKeyConstant.ADX_ADVIEW_VALUE);
			creativeAuditModel.setAuditValue(String.valueOf(creativeIdvalue));
			creativeAuditModel.setCreativeId(mapId);
			creativeAuditModel.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
			creativeAuditDao.insertSelective(creativeAuditModel);
		} else {
			CreativeAuditModel model = list.get(0);
			if (!StringUtils.isEmpty(model.getAuditValue())) {
				creativeIdvalue = Long.parseLong(model.getAuditValue());
			}
		}
		return creativeIdvalue;
	}
	/**
	 * 获取广告主对应的行业ID
	 * @param industryId
	 * @return
	 */
	private String getIndustry(Integer industryId) throws Exception {
		IndustryAdxModelExample example = new IndustryAdxModelExample();
		example.createCriteria().andAdxIdEqualTo(AdxKeyConstant.ADX_ADVIEW_VALUE).andIndustryIdEqualTo(industryId);
		List<IndustryAdxModel> list = industryAdxDao.selectByExample(example);
		String code = null;
		if(list!=null && !list.isEmpty()){
			IndustryAdxModel model = list.get(0);
			code = model.getIndustryCode();
		}
		return code;
	}
}
