package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pxene.pap.common.GlobalUtil;
import com.pxene.pap.common.RedisUtils;
import com.pxene.pap.constant.RedisKeyConstant;
import com.pxene.pap.domain.model.basic.AdvertiserAuditModel;
import com.pxene.pap.domain.model.basic.AdvertiserAuditModelExample;
import com.pxene.pap.domain.model.basic.AdvertiserModel;
import com.pxene.pap.domain.model.basic.AdxModel;
import com.pxene.pap.domain.model.basic.AdxModelExample;
import com.pxene.pap.domain.model.basic.CampaignModel;
import com.pxene.pap.domain.model.basic.CreativeAuditModel;
import com.pxene.pap.domain.model.basic.CreativeAuditModelExample;
import com.pxene.pap.domain.model.basic.CreativeMaterialModel;
import com.pxene.pap.domain.model.basic.CreativeMaterialModelExample;
import com.pxene.pap.domain.model.basic.CreativeModel;
import com.pxene.pap.domain.model.basic.CreativeModelExample;
import com.pxene.pap.domain.model.basic.ProjectModel;
import com.pxene.pap.domain.model.basic.view.CampaignTargetModel;
import com.pxene.pap.domain.model.basic.view.CampaignTargetModelExample;
import com.pxene.pap.domain.model.basic.view.CreativeImageModelExample;
import com.pxene.pap.domain.model.basic.view.CreativeImageModelWithBLOBs;
import com.pxene.pap.domain.model.basic.view.CreativeInfoflowModelExample;
import com.pxene.pap.domain.model.basic.view.CreativeInfoflowModelWithBLOBs;
import com.pxene.pap.domain.model.basic.view.CreativeVideoModelExample;
import com.pxene.pap.domain.model.basic.view.CreativeVideoModelWithBLOBs;
import com.pxene.pap.domain.model.basic.view.ImageSizeTypeModel;
import com.pxene.pap.domain.model.basic.view.ImageSizeTypeModelExample;
import com.pxene.pap.exception.NotFoundException;
import com.pxene.pap.repository.basic.AdvertiserAuditDao;
import com.pxene.pap.repository.basic.AdvertiserDao;
import com.pxene.pap.repository.basic.AdxDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeAuditDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.CreativeMaterialDao;
import com.pxene.pap.repository.basic.ProjectDao;
import com.pxene.pap.repository.basic.view.CampaignTargetDao;
import com.pxene.pap.repository.basic.view.CreativeImageDao;
import com.pxene.pap.repository.basic.view.CreativeInfoflowDao;
import com.pxene.pap.repository.basic.view.CreativeVideoDao;
import com.pxene.pap.repository.basic.view.ImageSizeTypeDao;

@Service
public class RedisService {

	@Autowired
	private AdvertiserDao advertiserDao;
	
	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private CreativeDao creativeDao;
	
	@Autowired
	private CreativeMaterialDao creativeMaterialDao;
	
	@Autowired
	private CreativeImageDao creativeImageDao;
	
	@Autowired
	private CreativeVideoDao creativeVideoDao;
	
	@Autowired
	private CreativeInfoflowDao creativeInfoflowDao;
	
	@Autowired
	private ImageSizeTypeDao imageSizeTypeDao;
	
	@Autowired
	private CampaignTargetDao campaignTargetDao;
	
	@Autowired
	private AdxDao adxDao;
	
	@Autowired
	private RedisUtils redisUtils;
	
	@Autowired
	private AdvertiserAuditDao advertiserAuditDao;
	
	@Autowired
	private CreativeAuditDao creativeAuditDao;
	
	@Transactional
	public void writeCreativeInfoToRedis(String campaignId) throws Exception {
		// 查询活动下创意
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> creatives = creativeDao.selectByExample(creativeExample);
		// 创意id数组
		List<String> creativeIds = new ArrayList<String>();
		//如果活动下无创意
		if(creatives == null || creatives.isEmpty()){
			throw new NotFoundException();
		}
		// 将查询出来的创意id放入创意id数组
		for (CreativeModel creative : creatives) {
			String creativeId = creative.getId();
			if (creativeId != null && !creativeId.isEmpty()) {
				creativeIds.add(creativeId);
			}
		}
		// 根据创意id数组查询创意所对应的关联关系表数据
		if (!creativeIds.isEmpty()) {
			CreativeMaterialModelExample mapExample = new CreativeMaterialModelExample();
			mapExample.createCriteria().andCreativeIdIn(creativeIds);
			List<CreativeMaterialModel> mapModels = creativeMaterialDao.selectByExample(mapExample);
			if (mapModels != null && !mapModels.isEmpty()) {
				for (CreativeMaterialModel mapModel : mapModels) {
					String mapId = mapModel.getId();
					String creativeType = mapModel.getCreativeType();
					// 图片创意
					if ("1".equals(creativeType)) {
						writeImgCreativeInfoToRedis(mapId);
					// 视频创意
					} else if ("2".equals(creativeType)) {
						writeVideoCreativeInfoToRedis(mapId);
					// 信息流创意
					} else if ("3".equals(creativeType)) {
						writeInfoCreativeInfoToRedis(mapId);
					}
				}
			}
		}
	}
	/**
	 * 图片创意信息写入redis
	 * @param mapId
	 * @throws Exception
	 */
	public void writeImgCreativeInfoToRedis(String mapId) throws Exception {
		CreativeImageModelExample imageExaple = new CreativeImageModelExample();
		imageExaple.createCriteria().andMapIdEqualTo(mapId);
		List<CreativeImageModelWithBLOBs> list = creativeImageDao.selectByExampleWithBLOBs(imageExaple);
		if (list == null || list.isEmpty()) {
			throw new Exception();
		}
		for (CreativeImageModelWithBLOBs model : list) {
			JsonObject creativeObj = new JsonObject();
			creativeObj.addProperty("mapid", model.getMapId());
			creativeObj.addProperty("groupid", model.getCampaignId());
			creativeObj.addProperty("type", Integer.parseInt(model.getType()));
			creativeObj.addProperty("ftype", Integer.parseInt(model.getFtype()));
			AdxModelExample adxEcample = new AdxModelExample();
			List<AdxModel> adxs = adxDao.selectByExample(adxEcample);
			JsonArray priceAdx = new JsonArray();
			for(AdxModel adx : adxs){
				JsonObject adxObj = new JsonObject();
				adxObj.addProperty("adx", Integer.parseInt(adx.getId()));
				adxObj.addProperty("price", model.getPrice());
				priceAdx.add(adxObj);
			}
			creativeObj.add("price_adx", priceAdx);
			creativeObj.addProperty("ctype", Integer.parseInt(model.getCtype()));
			if ("2".equals(model.getCtype())) {
				creativeObj.addProperty("bundle", GlobalUtil.parseString(model.getMapId(),""));
				creativeObj.addProperty("apkname", GlobalUtil.parseString(model.getApkName(),""));
			}
			creativeObj.addProperty("w", GlobalUtil.parseInt(model.getW(),0));
			creativeObj.addProperty("h", GlobalUtil.parseInt(model.getH(),0));
			creativeObj.addProperty("curl", GlobalUtil.parseString(model.getCurl(),""));
			creativeObj.addProperty("landingurl", GlobalUtil.parseString(model.getLandingUrl(),""));
			
			String[] tempImonitorUrl = GlobalUtil.parseString(model.getImonitorUrl(), "").split("##");//数据库查询时用"##"连接
            String[] tempCmonitorUrl = GlobalUtil.parseString(model.getCmonitorUrl(), "").split("##");
			JsonArray imoUrlStr = new JsonArray();
			JsonArray cmoUrlStr = new JsonArray();
            String monitorcode = "";
            for (int i = 0; i < tempImonitorUrl.length; i++) {
                imoUrlStr.add(tempImonitorUrl[i]);
                monitorcode += RedisKeyConstant.MONITOR_TEMPLATES.replace("{index}", "" + i).replace("{imonitorurl}", tempImonitorUrl[i]);
            }
            for (int j = 0; j < tempCmonitorUrl.length; j++) {
                cmoUrlStr.add(tempCmonitorUrl[j]);
            }
            creativeObj.add("imonitorurl", imoUrlStr);
            creativeObj.add("cmonitorurl", cmoUrlStr);
            creativeObj.addProperty("monitorcode", monitorcode);
            creativeObj.addProperty("sourceurl", model.getSourceUrl());//此处需要修改—————————————————图片地址路径需要加上
            creativeObj.addProperty("cid", GlobalUtil.parseString(model.getProjectId(),""));
            //获取Exts
    		JsonArray exts = getCreativeExts(mapId);
			creativeObj.add("exts", exts);
			redisUtils.set(RedisKeyConstant.CREATIVE_INFO + mapId, creativeObj.toString());
		}
	}
	
	/**
	 * 视频创意信息写入redis
	 * @param mapId
	 * @throws Exception
	 */
	public void writeVideoCreativeInfoToRedis(String mapId) throws Exception {
		CreativeVideoModelExample videoExaple = new CreativeVideoModelExample();
		videoExaple.createCriteria().andMapIdEqualTo(mapId);
		List<CreativeVideoModelWithBLOBs> list = creativeVideoDao.selectByExampleWithBLOBs(videoExaple);
		if (list == null || list.isEmpty()) {
			throw new Exception();
		}
		for (CreativeVideoModelWithBLOBs model : list) {
			JsonObject creativeObj = new JsonObject();
			creativeObj.addProperty("mapid", model.getMapId());
			creativeObj.addProperty("groupid", model.getCampaignId());
			creativeObj.addProperty("type", Integer.parseInt(model.getType()));
			creativeObj.addProperty("ftype", Integer.parseInt(model.getFtype()));
			AdxModelExample adxEcample = new AdxModelExample();
			List<AdxModel> adxs = adxDao.selectByExample(adxEcample);
			JsonArray priceAdx = new JsonArray();
			for(AdxModel adx : adxs){
				JsonObject adxObj = new JsonObject();
				adxObj.addProperty("adx", Integer.parseInt(adx.getId()));
				adxObj.addProperty("price", model.getPrice());
				priceAdx.add(adxObj);
			}
			creativeObj.add("price_adx", priceAdx);
			creativeObj.addProperty("ctype", Integer.parseInt(model.getCtype()));
			if ("2".equals(model.getCtype())) {
				creativeObj.addProperty("bundle", GlobalUtil.parseString(model.getMapId(),""));
				creativeObj.addProperty("apkname", GlobalUtil.parseString(model.getApkName(),""));
			}
			creativeObj.addProperty("w", GlobalUtil.parseInt(model.getW(),0));
			creativeObj.addProperty("h", GlobalUtil.parseInt(model.getH(),0));
			creativeObj.addProperty("curl", GlobalUtil.parseString(model.getCurl(),""));
			creativeObj.addProperty("landingurl", GlobalUtil.parseString(model.getLandingUrl(),""));
			
			String[] tempImonitorUrl = GlobalUtil.parseString(model.getImonitorUrl(), "").split("##");//数据库查询时用"##"连接
            String[] tempCmonitorUrl = GlobalUtil.parseString(model.getCmonitorUrl(), "").split("##");
			JsonArray imoUrlStr = new JsonArray();
			JsonArray cmoUrlStr = new JsonArray();
            String monitorcode = "";
            for (int i = 0; i < tempImonitorUrl.length; i++) {
                imoUrlStr.add(tempImonitorUrl[i]);
                monitorcode += RedisKeyConstant.MONITOR_TEMPLATES.replace("{index}", "" + i).replace("{imonitorurl}", tempImonitorUrl[i]);
            }
            for (int j = 0; j < tempCmonitorUrl.length; j++) {
                cmoUrlStr.add(tempCmonitorUrl[j]);
            }
            creativeObj.add("imonitorurl", imoUrlStr);
            creativeObj.add("cmonitorurl", cmoUrlStr);
            creativeObj.addProperty("monitorcode", monitorcode);
            creativeObj.addProperty("sourceurl", model.getSourceUrl());//此处需要修改——————————————————图片地址路径需要加上
            creativeObj.addProperty("cid", GlobalUtil.parseString(model.getProjectId(),""));
          //获取Exts
    		JsonArray exts = getCreativeExts(mapId);
			creativeObj.add("exts", exts);
			redisUtils.set(RedisKeyConstant.CREATIVE_INFO + mapId, exts.toString());
		}
	}
	
	/**
	 * 信息流创意信息写入redis
	 * @param mapId
	 * @throws Exception
	 */
	public void writeInfoCreativeInfoToRedis(String mapId) throws Exception {
		CreativeInfoflowModelExample infoExaple = new CreativeInfoflowModelExample();
		infoExaple.createCriteria().andMapIdEqualTo(mapId);
		List<CreativeInfoflowModelWithBLOBs> list = creativeInfoflowDao.selectByExampleWithBLOBs(infoExaple);
		if (list == null || list.isEmpty()) {
			throw new Exception();
		}
		for (CreativeInfoflowModelWithBLOBs model : list) {
			JsonObject creativeObj = new JsonObject();
			creativeObj.addProperty("mapid", model.getMapId());
			creativeObj.addProperty("groupid", model.getCampaignId());
			creativeObj.addProperty("type", Integer.parseInt(model.getType()));
			creativeObj.addProperty("ftype", Integer.parseInt(model.getFtype()));
			AdxModelExample adxEcample = new AdxModelExample();
			List<AdxModel> adxs = adxDao.selectByExample(adxEcample);
			JsonArray priceAdx = new JsonArray();
			for(AdxModel adx : adxs){
				JsonObject adxObj = new JsonObject();
				adxObj.addProperty("adx", Integer.parseInt(adx.getId()));
				adxObj.addProperty("price", model.getPrice());
				priceAdx.add(adxObj);
			}
			creativeObj.add("price_adx", priceAdx);
			creativeObj.addProperty("ctype", Integer.parseInt(model.getCtype()));
			if ("2".equals(model.getCtype())) {
				creativeObj.addProperty("bundle", GlobalUtil.parseString(model.getMapId(), ""));
				creativeObj.addProperty("apkname", GlobalUtil.parseString(model.getApkName(), ""));
			}
			String icon = model.getIcon();
			if (icon != null) {
				JsonObject iconJson = getImageJson(icon);
				creativeObj.add(icon, iconJson);
			}
			String image1 = model.getImage1();
			String image2 = model.getImage2();
			String image3 = model.getImage3();
			String image4 = model.getImage4();
			String image5 = model.getImage5();
			int imgs = 0;
			//记录图片id（多张信息流大图）
			String []someImage = new String[5];
			//记录哪一个图片不是NULL（如果只有一张大图时）
			String oneImag = "";
			//逐个判断，如果有多图增加“imgs”，如果一个大图增加“w”“h”“ftype”“sourceurl”,没有则不增加
			if (image1 != null && !"".equals(image1)) {
				imgs += 1;
				oneImag = image1;
				someImage[0] = image1;
			}
			if (image2 != null && !"".equals(image2)) {
				imgs += 1;
				oneImag = image2;
				someImage[1] = image2;
			}
			if (image3 != null && !"".equals(image3)) {
				imgs += 1;
				oneImag = image3;
				someImage[2] = image3;
			}
			if (image4 != null && !"".equals(image4)) {
				imgs += 1;
				oneImag = image4;
				someImage[3] = image4;
			}
			if (image5 != null && !"".equals(image5)) {
				imgs += 1;
				oneImag = image5;
				someImage[4] = image5;
			}
			if (imgs == 1) {
				ImageSizeTypeModel ist = selectImages(oneImag);
				creativeObj.addProperty("w", GlobalUtil.parseInt(ist.getWidth(),0));
				creativeObj.addProperty("h", GlobalUtil.parseInt(ist.getHeight(),0));
				creativeObj.addProperty("ftype", ist.getCode());
				creativeObj.addProperty("sourceurl", ist.getPath());//需要拼接图片服务器地址——————————————
			} else if (imgs > 1) {
				JsonArray bigImages = new JsonArray(); 
				for(String str : someImage){
					// sourceurl需要拼接图片服务器地址——————————————
					bigImages.add(selectImages(str).toString());
				}
				creativeObj.add("imgs", bigImages);
			}
			creativeObj.addProperty("curl", GlobalUtil.parseString(model.getCurl(),""));
			creativeObj.addProperty("landingurl", GlobalUtil.parseString(model.getLandingUrl(),""));
			
			String[] tempImonitorUrl = GlobalUtil.parseString(model.getImonitorUrl(), "").split("##");//数据库查询时用"##"连接
            String[] tempCmonitorUrl = GlobalUtil.parseString(model.getCmonitorUrl(), "").split("##");
			JsonArray imoUrlStr = new JsonArray();
			JsonArray cmoUrlStr = new JsonArray();
            String monitorcode = "";
            for (int i = 0; i < tempImonitorUrl.length; i++) {
                imoUrlStr.add(tempImonitorUrl[i]);
                monitorcode += RedisKeyConstant.MONITOR_TEMPLATES.replace("{index}", "" + i).replace("{imonitorurl}", tempImonitorUrl[i]);
            }
            for (int j = 0; j < tempCmonitorUrl.length; j++) {
                cmoUrlStr.add(tempCmonitorUrl[j]);
            }
            creativeObj.add("imonitorurl", imoUrlStr);
            creativeObj.add("cmonitorurl", cmoUrlStr);
            creativeObj.addProperty("monitorcode", monitorcode);
            creativeObj.addProperty("cid", GlobalUtil.parseString(model.getProjectId(),""));
            creativeObj.addProperty("title", GlobalUtil.parseString(model.getTitle(),""));
            creativeObj.addProperty("description", GlobalUtil.parseString(model.getDescription(),""));
            creativeObj.addProperty("rating", GlobalUtil.parseString(model.getRating(),""));
            creativeObj.addProperty("ctatext", GlobalUtil.parseString(model.getCtatext(),""));
            //获取Exts
    		JsonArray exts = getCreativeExts(mapId);
			creativeObj.add("exts", exts);
			redisUtils.set(RedisKeyConstant.CREATIVE_INFO + mapId, exts.toString());
		}
	}
	
	/**
	 * 获取图片信息json
	 * @param imageId
	 * @return
	 */
	private JsonObject getImageJson(String imageId) throws Exception {
		JsonObject json = new JsonObject();
		ImageSizeTypeModel model = selectImages(imageId);
		json.addProperty("w", GlobalUtil.parseInt(model.getWidth(),0));
		json.addProperty("h", GlobalUtil.parseInt(model.getHeight(),0));
		json.addProperty("ftype", Integer.parseInt(model.getCode()));
		json.addProperty("sourceurl", model.getPath());
		return json;
	}
	
	/**
	 * 查询图片的尺寸、类型信息
	 * @param imageId
	 * @return
	 */
	private ImageSizeTypeModel selectImages(String imageId) throws Exception {
		ImageSizeTypeModelExample example = new ImageSizeTypeModelExample();
		example.createCriteria().andIdEqualTo(imageId);
		List<ImageSizeTypeModel> list = imageSizeTypeDao.selectByExample(example);
		if (list == null || list.isEmpty()) {
			return null;
		}else{
			for (ImageSizeTypeModel model : list) {
				return model;
			}
		}
		return null;
	}
	
	/**
	 * 将活动基本信息写入redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void writeCampaignInfoToRedis(String campaignId) throws Exception {
		CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
		String ProjectId = campaignModel.getProjectId();
		ProjectModel projectModel = projectDao.selectByPrimaryKey(ProjectId);
		String advertiserId = projectModel.getAdvertiserId();
		AdvertiserModel advertiserModel = advertiserDao.selectByPrimaryKey(advertiserId);
		JsonObject campaignInfo = new JsonObject();
		JsonArray catArr = new JsonArray();
		JsonArray adxArr = new JsonArray();
		JsonArray auctiontypeArr = new JsonArray();
		int industryId = Integer.parseInt(advertiserModel.getIndustryId());
		catArr.add(industryId);
		campaignInfo.add("cat", catArr);
		campaignInfo.addProperty("advcat", industryId);
		String adomain = GlobalUtil.parseString(advertiserModel.getSiteUrl(),"");
		campaignInfo.addProperty("adomain", adomain.replace("http://www.", "").replace("www.", ""));
		AdxModelExample adxEcample = new AdxModelExample();
		List<AdxModel> adxs = adxDao.selectByExample(adxEcample);
		for (AdxModel adx : adxs) {
			//投放adx
			JsonObject adxObj = new JsonObject();
			adxArr.add(Integer.parseInt(adx.getId()));
			//adx竞价列表
			adxObj.addProperty("adx", Integer.parseInt(adx.getId()));
			adxObj.addProperty("at", 0);//竞价方式  ；0代表RTB
			auctiontypeArr.add(adxObj);
		}
		campaignInfo.add("adx", adxArr);
		campaignInfo.add("auctiontype", auctiontypeArr);
		campaignInfo.addProperty("redirect", 0);//不重定向创意的curl
		campaignInfo.addProperty("effectmonitor", 1);//需要效果监测
		//获取Exts
		JsonArray exts = getCampaignExts(campaignId);
		campaignInfo.add("exts", exts);
		redisUtils.set(RedisKeyConstant.CAMPAIGN_INFO + campaignId, campaignInfo.toString());
	}
	
	/**
	 * 活动定向写入redis
	 * @param campaignId
	 * @param adType 
	 * @throws Exception
	 */
	public void writeCampaignTargetToRedis(String campaignId) throws Exception {
		JsonObject targetJson = new JsonObject(); // 项目定向信息
		JsonObject deviceJson = new JsonObject(); // 设备信息定向
		CampaignTargetModelExample example = new CampaignTargetModelExample();
		example.createCriteria().andIdEqualTo(campaignId);
		List<CampaignTargetModel> targets = campaignTargetDao.selectByExampleWithBLOBs(example);
		if (targets != null && !targets.isEmpty()) {
			int flag = 0;
			CampaignTargetModel target = targets.get(0);
			targetJson.addProperty("groupid", campaignId);
			JsonArray region = targetStringToJsonArrayWithInt(target.getRegionId());
			JsonArray network = targetStringToJsonArrayWithInt(target.getNetwork());
			JsonArray os = targetStringToJsonArrayWithInt(target.getOs());
			JsonArray operator = targetStringToJsonArrayWithInt(target.getOperator());
			JsonArray device = targetStringToJsonArrayWithInt(target.getDevice());
			JsonArray brand = targetStringToJsonArrayWithInt(target.getBrandId());
			if (region.size() > 0) {
				flag = flag | RedisKeyConstant.TARGET_CODES.get("region")[1];
				deviceJson.add("regioncode", region);
			}
			if (network.size() > 0) {
				flag = flag | RedisKeyConstant.TARGET_CODES.get("network")[1];
				deviceJson.add("connectiontype", network);
			}
			if (os.size() > 0) {
				flag = flag | RedisKeyConstant.TARGET_CODES.get("os")[1];
				deviceJson.add("os", os);
			}
			if (operator.size() > 0) {
				flag = flag | RedisKeyConstant.TARGET_CODES.get("operator")[1];
				deviceJson.add("carrier", operator);
			}
			if (device.size() > 0) {
				flag = flag | RedisKeyConstant.TARGET_CODES.get("device")[1];
				deviceJson.add("devicetype", device);
			}
			if (brand.size() > 0) {
				flag = flag | RedisKeyConstant.TARGET_CODES.get("brand")[1];
				deviceJson.add("make", brand);
			}
			targetJson.add("device", deviceJson);
			// app定向
			JsonObject appJson = createAppTargetJson(target.getAppId());
			if (appJson != null) {
				targetJson.add("app", appJson);
			}
			redisUtils.set(RedisKeyConstant.CAMPAIGN_TARGET + campaignId, targetJson.toString());
		}
	}
	
	/**
	 * 创建app定向写入redis的json对象
	 * @param appTarget
	 * @return
	 * @throws Exception
	 */
	private JsonObject createAppTargetJson(String appTarget) throws Exception {
		if (appTarget == null || "".equals(appTarget)) {
			return null;
		}
		JsonObject appJson = new JsonObject();
		String[] apps = appTarget.split(",");
		AdxModelExample adxExample = new AdxModelExample();
		List<AdxModel> adxs = adxDao.selectByExample(adxExample);
		JsonArray idArr = new JsonArray();
		for (AdxModel adx : adxs) {
			JsonObject idObj = new JsonObject();
			idObj.addProperty("adx", Integer.parseInt(adx.getId()));
			JsonArray appids = new JsonArray();
			for (String app : apps) {
				if (app.split("##")[0] == adx.getId()) {
					appids.add(app.split("##")[1]);
				}
			}
			int flag = 0;
			if (appids.size() > 0) {
				flag = 1;
			}
			idObj.addProperty("flag", flag);
			idObj.add("wlist", appids);
			idArr.add(idObj);
		}
		appJson.add("id", idArr);
		return appJson;
	}
	
	/**
	 * 频次信息写入redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void writeCampaignFrequencyToRedis(String campaignId) throws Exception {
		
		
	}
	
	/**
	 * 推广组下创意ID写入redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void WriteMapidToRedis(String campaignId) throws Exception {
		//查询活动下创意
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> creatives = creativeDao.selectByExample(creativeExample);
		JsonArray mapidJson = new JsonArray();
		if (creatives != null && !creatives.isEmpty()) {
			// 查询创意对应的关联关系mapid
			for (CreativeModel creative : creatives) {
				String creativeId = creative.getId();
				CreativeMaterialModelExample cmExample = new CreativeMaterialModelExample();
				cmExample.createCriteria().andCreativeIdEqualTo(creativeId);
				List<CreativeMaterialModel> list = creativeMaterialDao.selectByExample(cmExample);
				if (list == null || list.isEmpty()) {
					continue;
				}
				for (CreativeMaterialModel cm : list) {
					String mapId = cm.getId();
					if (mapId != null && !"".endsWith(mapId)) {
						mapidJson.add(mapId);
					}
				}
			}
		}
		redisUtils.set(RedisKeyConstant.CAMPAIGN_MAPIDS + campaignId, mapidJson.toString());
	}
	
	/**
	 * 将字符串合格化成json数组（用于活动定向信息处理）
	 * @param targetString
	 * @return
	 */
	private JsonArray targetStringToJsonArrayWithInt(String targetString) throws Exception {
		if (targetString == null || "".equals(targetString)) {
			return new JsonArray();
		} else {
			String[] targets = targetString.split(",");
			JsonArray jsonArray = new JsonArray();
			for (String str : targets) {
				jsonArray.add(Integer.parseInt(str));
			}
			return jsonArray;
		}
	}
	
	/**
	 * 获取推广组对应的各个adx审核值（exts）
	 * @param campaignId
	 * @return
	 * @throws Exception
	 */
	private JsonArray getCampaignExts(String campaignId) throws Exception {
		JsonArray exts = new JsonArray();
		//查询项目ID
		CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
		String projectId = campaignModel.getProjectId();
		//查询广告主ID
		ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
		String advertiserId = projectModel.getAdvertiserId();
		//查询adx列表
		AdxModelExample adxExample = new AdxModelExample();
		List<AdxModel> adxs = adxDao.selectByExample(adxExample);
		//查询各个adx审核Value
		JsonObject ext = null;
		for (AdxModel adx : adxs) {
			ext = new JsonObject();
			String adxId = adx.getId();
			AdvertiserAuditModelExample example = new AdvertiserAuditModelExample();
			example.createCriteria().andAdvertiserIdEqualTo(advertiserId).andAdxIdEqualTo(adxId);
			List<AdvertiserAuditModel> list = advertiserAuditDao.selectByExample(example);
			if (list != null && !list.isEmpty()) {
				AdvertiserAuditModel model = list.get(0);
				String auditValue = model.getAuditValue();
				ext.addProperty("adx", Integer.parseInt(adxId));
				ext.addProperty("advid", auditValue);
				exts.add(ext);
			}
		}
		if (exts.size() > 0) {
			return exts;
		}
		return new JsonArray();
	}
	
	/**
	 * 根据mapId 查询创意的审核值
	 * @param mapId
	 * @return
	 * @throws Exception
	 */
	private JsonArray getCreativeExts(String mapId) throws Exception {
		JsonArray exts = new JsonArray();
		//查询adx列表
		AdxModelExample adxExample = new AdxModelExample();
		List<AdxModel> adxs = adxDao.selectByExample(adxExample);
		//查询各个adx审核Value
		JsonObject ext = null;
		for (AdxModel adx : adxs) {
			ext = new JsonObject();
			String adxId = adx.getId();
			CreativeAuditModelExample example = new CreativeAuditModelExample();
			example.createCriteria().andAdxIdEqualTo(adxId).andCreativeIdEqualTo(mapId);
			List<CreativeAuditModel> list = creativeAuditDao.selectByExample(example);
			if (list != null && !list.isEmpty()) {
				CreativeAuditModel model = list.get(0);
				String auditValue = model.getAuditValue();
				ext.addProperty("adx", Integer.parseInt(adxId));
				ext.addProperty("id", auditValue);
				exts.add(ext);
			}
		}
		if (exts.size() > 0) {
			return exts;
		}
		return new JsonArray();
	}
}
