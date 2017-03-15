package com.pxene.pap.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
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
import com.pxene.pap.common.JedisUtils;
import com.pxene.pap.constant.RedisKeyConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.models.AdvertiserAuditModel;
import com.pxene.pap.domain.models.AdvertiserAuditModelExample;
import com.pxene.pap.domain.models.AdvertiserModel;
import com.pxene.pap.domain.models.AdxModel;
import com.pxene.pap.domain.models.AdxModelExample;
import com.pxene.pap.domain.models.AppModel;
import com.pxene.pap.domain.models.AppModelExample;
import com.pxene.pap.domain.models.AppTargetModel;
import com.pxene.pap.domain.models.AppTargetModelExample;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CreativeAuditModel;
import com.pxene.pap.domain.models.CreativeAuditModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.CreativeModelExample;
import com.pxene.pap.domain.models.FrequencyModel;
import com.pxene.pap.domain.models.ImageMaterialModel;
import com.pxene.pap.domain.models.ImageModel;
import com.pxene.pap.domain.models.PopulationModel;
import com.pxene.pap.domain.models.PopulationTargetModel;
import com.pxene.pap.domain.models.PopulationTargetModelExample;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.domain.models.QuantityModel;
import com.pxene.pap.domain.models.QuantityModelExample;
import com.pxene.pap.domain.models.RegionTargetModel;
import com.pxene.pap.domain.models.RegionTargetModelExample;
import com.pxene.pap.domain.models.view.CampaignTargetModel;
import com.pxene.pap.domain.models.view.CampaignTargetModelExample;
import com.pxene.pap.domain.models.view.CreativeImageModelExample;
import com.pxene.pap.domain.models.view.CreativeImageModelWithBLOBs;
import com.pxene.pap.domain.models.view.CreativeInfoflowModelExample;
import com.pxene.pap.domain.models.view.CreativeInfoflowModelWithBLOBs;
import com.pxene.pap.domain.models.view.CreativeVideoModelExample;
import com.pxene.pap.domain.models.view.CreativeVideoModelWithBLOBs;
import com.pxene.pap.repository.basic.AdvertiserAuditDao;
import com.pxene.pap.repository.basic.AdvertiserDao;
import com.pxene.pap.repository.basic.AdxDao;
import com.pxene.pap.repository.basic.AppDao;
import com.pxene.pap.repository.basic.AppTargetDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeAuditDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.FrequencyDao;
import com.pxene.pap.repository.basic.ImageDao;
import com.pxene.pap.repository.basic.ImageMaterialDao;
import com.pxene.pap.repository.basic.PopulationDao;
import com.pxene.pap.repository.basic.PopulationTargetDao;
import com.pxene.pap.repository.basic.ProjectDao;
import com.pxene.pap.repository.basic.QuantityDao;
import com.pxene.pap.repository.basic.RegionTargetDao;
import com.pxene.pap.repository.basic.view.CampaignTargetDao;
import com.pxene.pap.repository.basic.view.CreativeImageDao;
import com.pxene.pap.repository.basic.view.CreativeInfoflowDao;
import com.pxene.pap.repository.basic.view.CreativeVideoDao;

@Service
public class RedisService {

	private static String image_url;
	
	@Autowired
	public RedisService(Environment env)
	{
		/**
		 * 获取图片上传路径
		 */
		image_url = env.getProperty("pap.fileserver.url.prefix");
	}
	
	@Autowired
	private AdvertiserDao advertiserDao;
	
	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private AppTargetDao appTargetDao;
	
	@Autowired
	private CreativeDao creativeDao;
	
	@Autowired
	private CreativeImageDao creativeImageDao;
	
	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private FrequencyDao frequencyDao;
	
	@Autowired
	private QuantityDao quantityDao;
	
	@Autowired
	private CreativeVideoDao creativeVideoDao;
	
	@Autowired
	private CreativeInfoflowDao creativeInfoflowDao;
	
	@Autowired
	private ImageMaterialDao imageMaterialDao;
	
	@Autowired
	private CampaignTargetDao campaignTargetDao;
	
	@Autowired
	private PopulationTargetDao populationTargetDao;
	
	@Autowired
	private RegionTargetDao regionTargetDao;
	
	@Autowired
	private PopulationDao populationDao;
	
	@Autowired
	private AdxDao adxDao;
	
	@Autowired
	private AppDao appDao;
	
	@Autowired
	private AdvertiserAuditDao advertiserAuditDao;
	
	@Autowired
	private CreativeAuditDao creativeAuditDao;

	/**
	 * 将活动ID 写入redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void writeCampaignIds(String campaignId) throws Exception {
		String ids = JedisUtils.getStr(RedisKeyConstant.CAMPAIGN_IDS);
		JsonObject idObj = new JsonObject();
		JsonArray idArray = new JsonArray();
		if (ids == null) {
			idArray.add(campaignId);
			idObj.add("groupids", idArray);
		} else {
			Gson gson = new Gson();
			idObj = gson.fromJson(ids, new JsonObject().getClass());
			idArray = idObj.get("groupids").getAsJsonArray();
			// 判断是否已经含有当前campaignID
			boolean flag = false;
			for (int i = 0; i < idArray.size(); i++) {
				if (campaignId.equals(idArray.get(i).getAsString())) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				idArray.add(campaignId);
				idObj.add("groupids", idArray);
			}
		}
		JedisUtils.set(RedisKeyConstant.CAMPAIGN_IDS, idObj.toString());
	}
	
	/**
	 * 创意基本信息写入redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void writeCreativeInfoToRedis(String campaignId) throws Exception {
		// 查询活动下创意
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> creatives = creativeDao.selectByExample(creativeExample);
		// 创意id数组
		List<String> creativeIds = new ArrayList<String>();
		//如果活动下无可投创意
		if(creatives == null || creatives.isEmpty()){
			return;
		}
		// 将查询出来的创意id放入创意id数组
		for (CreativeModel creative : creatives) {
			creativeIds.add(creative.getId());
		}
		// 根据创意id数组查询创意所对应的关联关系表数据
		if (!creativeIds.isEmpty()) {
			CreativeModelExample mapExample = new CreativeModelExample();
			mapExample.createCriteria().andIdIn(creativeIds);
			List<CreativeModel> mapModels = creativeDao.selectByExample(mapExample);
			if (mapModels != null && !mapModels.isEmpty()) {
				for (CreativeModel mapModel : mapModels) {
					String mapId = mapModel.getId();
					//审核通过的创意才可以投放
					if (getAuditSuccessMapId(mapId)) {
						String creativeType = mapModel.getType();
						// 图片创意
						if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(creativeType)) {
							writeImgCreativeInfoToRedis(mapId, campaignId);
							// 视频创意
						} else if (StatusConstant.CREATIVE_TYPE_VIDEO.equals(creativeType)) {
							writeVideoCreativeInfoToRedis(mapId, campaignId);
							// 信息流创意
						} else if (StatusConstant.CREATIVE_TYPE_INFOFLOW.equals(creativeType)) {
							writeInfoCreativeInfoToRedis(mapId, campaignId);
						}
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
	public void writeImgCreativeInfoToRedis(String mapId, String campaignId) throws Exception {
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
			List<AdxModel> adxs = getAdxForCampaign(campaignId);
			JsonArray priceAdx = new JsonArray();
			for(AdxModel adx : adxs){
				JsonObject adxObj = new JsonObject();
				adxObj.addProperty("adx", Integer.parseInt(adx.getId()));
				adxObj.addProperty("price", getCreativePrice(mapId));
				priceAdx.add(adxObj);
			}
			creativeObj.add("price_adx", priceAdx);
			creativeObj.addProperty("ctype", Integer.parseInt(model.getCtype()));
//			if ("2".equals(model.getCtype())) {
//				creativeObj.addProperty("bundle", GlobalUtil.parseString(model.getMapId(),""));
//				creativeObj.addProperty("apkname", GlobalUtil.parseString(model.getApkName(),""));
//			}
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
            creativeObj.addProperty("sourceurl", image_url + model.getSourceUrl());
            creativeObj.addProperty("cid", GlobalUtil.parseString(model.getProjectId(),""));
            //获取Exts
    		JsonArray exts = getCreativeExts(mapId, campaignId);
			creativeObj.add("exts", exts);
			JedisUtils.set(RedisKeyConstant.CREATIVE_INFO + mapId, creativeObj.toString());
			
			//添加权重key
//			String type = "image_";
//			String materialName = model.getSourceUrl().substring(0, model.getSourceUrl().indexOf("|"));
//			addRateForMaterial(campaignId, type, GlobalUtil.parseInt(model.getW(),0), GlobalUtil.parseInt(model.getH(),0), materialName);
		}
	}
	
	/**
	 * 视频创意信息写入redis
	 * @param mapId
	 * @throws Exception
	 */
	public void writeVideoCreativeInfoToRedis(String mapId, String campaignId) throws Exception {
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
			List<AdxModel> adxs = getAdxForCampaign(campaignId);
			JsonArray priceAdx = new JsonArray();
			for(AdxModel adx : adxs){
				JsonObject adxObj = new JsonObject();
				adxObj.addProperty("adx", Integer.parseInt(adx.getId()));
				adxObj.addProperty("price", getCreativePrice(mapId));
				priceAdx.add(adxObj);
			}
			creativeObj.add("price_adx", priceAdx);
			creativeObj.addProperty("ctype", Integer.parseInt(model.getCtype()));
//			if ("2".equals(model.getCtype())) {
//				creativeObj.addProperty("bundle", GlobalUtil.parseString(model.getMapId(),""));
//				creativeObj.addProperty("apkname", GlobalUtil.parseString(model.getApkName(),""));
//			}
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
            creativeObj.addProperty("sourceurl", image_url + model.getSourceUrl());
            creativeObj.addProperty("cid", GlobalUtil.parseString(model.getProjectId(),""));
          //获取Exts
    		JsonArray exts = getCreativeExts(mapId, campaignId);
			creativeObj.add("exts", exts);
			JedisUtils.set(RedisKeyConstant.CREATIVE_INFO + mapId, exts.toString());
			
			//添加权重key
			String type = "video_";
			String materialName = model.getSourceUrl().substring(0, model.getSourceUrl().indexOf("|"));
			addRateForMaterial(campaignId, type, GlobalUtil.parseInt(model.getW(),0), GlobalUtil.parseInt(model.getH(),0), materialName);
		}
	}
	
	/**
	 * 信息流创意信息写入redis
	 * @param mapId
	 * @throws Exception
	 */
	public void writeInfoCreativeInfoToRedis(String mapId, String campaignId) throws Exception {
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
//			creativeObj.addProperty("ftype", Integer.parseInt(model.getFtype()));
			List<AdxModel> adxs = getAdxForCampaign(campaignId);
			JsonArray priceAdx = new JsonArray();
			for(AdxModel adx : adxs){
				JsonObject adxObj = new JsonObject();
				adxObj.addProperty("adx", Integer.parseInt(adx.getId()));
				adxObj.addProperty("price", getCreativePrice(mapId));
				priceAdx.add(adxObj);
			}
			creativeObj.add("price_adx", priceAdx);
			creativeObj.addProperty("ctype", Integer.parseInt(model.getCtype()));
//			if ("2".equals(model.getCtype())) {
//				creativeObj.addProperty("bundle", GlobalUtil.parseString(model.getMapId(), ""));
//				creativeObj.addProperty("apkname", GlobalUtil.parseString(model.getApkName(), ""));
//			}
			String icon = model.getIconId();
			if (icon != null) {
				JsonObject iconJson = getImageJson(icon);
				creativeObj.add(icon, iconJson);
			}
			String image1 = model.getImage1Id();
			String image2 = model.getImage2Id();
			String image3 = model.getImage3Id();
			String image4 = model.getImage4Id();
			String image5 = model.getImage5Id();
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
				ImageMaterialModel materialModel = imageMaterialDao.selectByPrimaryKey(oneImag);
				ImageModel Imodel = imageDao.selectByPrimaryKey(materialModel.getImageId());
				creativeObj.addProperty("w", GlobalUtil.parseInt(Imodel.getWidth(),0));
				creativeObj.addProperty("h", GlobalUtil.parseInt(Imodel.getHeight(),0));
				creativeObj.addProperty("ftype", Imodel.getFormat());
				creativeObj.addProperty("sourceurl", image_url + Imodel.getPath());
			} else if (imgs > 1) {
				JsonArray bigImages = new JsonArray(); 
				for(String str : someImage){
					JsonObject bObj = getImageJson(str);
					bigImages.add(bObj);
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
    		JsonArray exts = getCreativeExts(mapId, campaignId);
			creativeObj.add("exts", exts);
			JedisUtils.set(RedisKeyConstant.CREATIVE_INFO + mapId, exts.toString());
		}
	}
	
	/**
	 * 获取图片信息json
	 * @param imageId
	 * @return
	 */
	private JsonObject getImageJson(String imageId) throws Exception {
		JsonObject json = new JsonObject();
		ImageMaterialModel materialModel = imageMaterialDao.selectByPrimaryKey(imageId);
		if (materialModel != null) {
			ImageModel model = imageDao.selectByPrimaryKey(materialModel.getImageId());
			if (model != null) {
				json.addProperty("w", GlobalUtil.parseInt(model.getWidth(),0));
				json.addProperty("h", GlobalUtil.parseInt(model.getHeight(),0));
				json.addProperty("ftype", Integer.parseInt(model.getFormat()));
				json.addProperty("sourceurl", image_url + model.getPath());
			}
		}
		return json;
	}
	
	/**
	 * 查询图片的尺寸、类型信息
	 * @param imageId
	 * @return
	 */
//	private ImageSizeTypeModel selectImages(String imageId) throws Exception {
//		ImageSizeTypeModelExample example = new ImageSizeTypeModelExample();
//		example.createCriteria().andIdEqualTo(imageId);
//		List<ImageSizeTypeModel> list = imageSizeTypeDao.selectByExample(example);
//		if (list == null || list.isEmpty()) {
//			return null;
//		}else{
//			for (ImageSizeTypeModel model : list) {
//				return model;
//			}
//		}
//		return null;
//	}
	
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
		int industryId = advertiserModel.getIndustryId();
		catArr.add(industryId);
		campaignInfo.add("cat", catArr);
		campaignInfo.addProperty("advcat", industryId);
		String adomain = GlobalUtil.parseString(advertiserModel.getSiteUrl(),"");
		campaignInfo.addProperty("adomain", adomain.replace("http://www.", "").replace("www.", ""));
		List<AdxModel> adxs = getAdxForCampaign(campaignId);
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
		JedisUtils.set(RedisKeyConstant.CAMPAIGN_INFO + campaignId, campaignInfo.toString());
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
//			JsonArray region = targetStringToJsonArrayWithInt(target.getRegionId());
			JsonArray region = new JsonArray();
			RegionTargetModelExample regionTargetModelExample = new RegionTargetModelExample();
			regionTargetModelExample.createCriteria().andCampaignIdEqualTo(campaignId);
			List<RegionTargetModel> regionTargetList = regionTargetDao.selectByExample(regionTargetModelExample);
			if (regionTargetList!=null && !regionTargetList.isEmpty()) {
				for (RegionTargetModel mol : regionTargetList) {
					region.add(Integer.parseInt(mol.getRegionId()));
				}
			}
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
			deviceJson.addProperty("flag", flag);
			targetJson.add("device", deviceJson);
			// app定向
			JsonObject appJson = createAppTargetJson(target.getAppId(), campaignId);
			if (appJson != null) {
				targetJson.add("app", appJson);
			}
			JedisUtils.set(RedisKeyConstant.CAMPAIGN_TARGET + campaignId, targetJson.toString());
		}
	}
	
	/**
	 * 创建app定向写入redis的json对象
	 * @param appTarget
	 * @return
	 * @throws Exception
	 */
	public JsonObject createAppTargetJson(String appTarget, String campaignId) throws Exception {
		if (appTarget == null || "".equals(appTarget)) {
			return null;
		}
		JsonObject appJson = new JsonObject();
		String[] apps = appTarget.split(",");
		List<AdxModel> adxs = getAdxForCampaign(campaignId);
		JsonArray idArr = new JsonArray();
		for (AdxModel adx : adxs) {
			JsonObject idObj = new JsonObject();
			idObj.addProperty("adx", Integer.parseInt(adx.getId()));
			JsonArray appids = new JsonArray();
			AppModel appModel;
			for (String app : apps) {
				appModel = appDao.selectByPrimaryKey(app);
				if (adx.getId().equals(appModel.getAdxId())) {
					appids.add(appModel.getAppId());
				}
			}
			int flag = 0;
			if (appids.size()>0) {
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
		if (!StringUtils.isEmpty(campaignId)) {
			String key = RedisKeyConstant.CAMPAIGN_FREQUENCY + campaignId;
			CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
			if (campaignModel != null) {
				JsonObject obj = new JsonObject();
				obj.addProperty("groupid", campaignId);
				JsonArray groupArray = new JsonArray();
				JsonObject groupObject = null;
				List<AdxModel> adxList = getAdxForCampaign(campaignId);
				if (adxList != null && !adxList.isEmpty()) {
					for (AdxModel adx : adxList) {
						groupObject = new JsonObject();
						groupObject.addProperty("adx", Integer.parseInt(adx.getId()));
						groupObject.addProperty("type", 0);
						groupObject.addProperty("period", 3);
						JsonArray fre = new JsonArray();
						QuantityModelExample qEx = new QuantityModelExample();
						qEx.createCriteria().andCampaignIdEqualTo(campaignId);
						List<QuantityModel> qList = quantityDao.selectByExample(qEx);
						if (qList != null && !qList.isEmpty()) {
							Integer dailyImpression = qList.get(0).getDailyImpression();
							fre.add(dailyImpression);
						}
						groupObject.add("frequency", fre);
						groupArray.add(groupObject);
					}
					obj.add("group", groupArray);
				}
				String frequencyId = campaignModel.getFrequencyId();
				if (!StringUtils.isEmpty(frequencyId)) {
					JsonObject userObj = new JsonObject();
					FrequencyModel frequencyModel = frequencyDao.selectByPrimaryKey(frequencyId);
					if (frequencyModel != null) {
						String controlObj = frequencyModel.getControlObj();
						Integer number = frequencyModel.getNumber();
						String timeType = frequencyModel.getTimeType();
						if ("02".equals(controlObj)) {
							userObj.addProperty("type", 2);
						} else if ("01".equals(controlObj)) {
							userObj.addProperty("type", 1);
						}
						if ("02".equals(timeType)) {
							userObj.addProperty("period", 2);
						} else if ("01".equals(timeType)) {
							userObj.addProperty("period", 3);
						}
						JsonArray capping = new JsonArray();
						JsonObject cap = new JsonObject();
						cap.addProperty("id", campaignModel.getId());
						capping.add(cap);
						userObj.add("capping", capping);
						userObj.addProperty("frequency", number);
					}
					obj.add("user", userObj);
				}
				JedisUtils.set(key, obj.toString());
			}
		}
	}
	
	/**
	 * 活动预算写入redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void writeCampaignBudgetToRedis(String campaignId) throws Exception {
		if (!StringUtils.isEmpty(campaignId)) {
			CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
			Integer totalBudget = campaignModel.getTotalBudget();
			String key = RedisKeyConstant.CAMPAIGN_BUDGET + campaignId;
			if (!JedisUtils.exists(key)) {
				Integer budget = 0;
				QuantityModelExample example = new QuantityModelExample();
				example.createCriteria().andCampaignIdEqualTo(campaignId);
				List<QuantityModel> list = quantityDao.selectByExample(example);
				if (list !=null && !list.isEmpty()) {
					for (QuantityModel quan : list) {
						Date startDate = quan.getStartDate();
						Date endDate = quan.getEndDate();
						String[] days = DateUtils.getDaysBetween(startDate, endDate);
						List<String> dayList = Arrays.asList(days);
						String time = new DateTime(new Date()).toString("yyyyMMdd");
						if (dayList.contains(time)) {
							budget = quan.getDailyBudget();
							break;
						}
					}
				}
				Map<String, String> value = new HashMap<String, String>();
				value.put("total", String.valueOf(totalBudget * 100));
				value.put("daily", String.valueOf(budget * 100));
				JedisUtils.hset(key, value);
			}
		}
	}
	
	/**
	 * 删除活动预算
	 * @param campaignId
	 * @throws Exception
	 */
	public void deleteCampaignBudgetFormRedis(String campaignId) throws Exception {
		if (!StringUtils.isEmpty(campaignId)) {//如果没有才新加，有，不操作
			String key = RedisKeyConstant.CAMPAIGN_BUDGET + campaignId;
			if (JedisUtils.exists(key)) {
				JedisUtils.delete(key);
			}
		}
	}
	
//	/**
//	 * 活动所属项目预算key写入redis
//	 * @param campaignId
//	 * @throws Exception
//	 */
//	public void writeProjectBudgetToRedis(String campaignId) throws Exception {
//		if (!StringUtils.isEmpty(campaignId)) {//如果没有才新加，有，不操作
//			CampaignModel model = campaignDao.selectByPrimaryKey(campaignId);
//			if (model != null) {
//				String projectId = model.getProjectId();
//				String key = RedisKeyConstant.PROJECT_BUDGET + projectId;
//				ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
//				if (projectModel != null) {
//					int totalBudget = projectModel.getTotalBudget();
//					JedisUtils.set(key, totalBudget * 100);
//				}
//			}
//		}
//	}
	
	/**
	 * 活动kpi上限写如redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void writeCampaignCounterToRedis(String campaignId) throws Exception {
		if (!StringUtils.isEmpty(campaignId)) {
			String key = RedisKeyConstant.CAMPAIGN_COUNTER + campaignId;
			if (!JedisUtils.exists(key)) {
				QuantityModelExample example = new QuantityModelExample();
				example.createCriteria().andCampaignIdEqualTo(campaignId);
				List<QuantityModel> list = quantityDao.selectByExample(example);
				if (list !=null && !list.isEmpty()) {
					for (QuantityModel quan : list) {
						Date startDate = quan.getStartDate();
						Date endDate = quan.getEndDate();
						String[] days = DateUtils.getDaysBetween(startDate, endDate);
						List<String> dayList = Arrays.asList(days);
						String time = new DateTime(new Date()).toString("yyyyMMdd");
						if (dayList.contains(time)) {
							int counter = quan.getDailyImpression();
							JedisUtils.set(key, counter);
							break;
						}
					}
				}
			}
		}
	}
	
//	/**
//	 * 删除活动kpi
//	 * @param campaignId
//	 * @throws Exception
//	 */
//	public void deleteCampaignCounterFromRedis(String campaignId) throws Exception {
//		if (!StringUtils.isEmpty(campaignId)) {
//			String key = RedisKeyConstant.CAMPAIGN_COUNTER + campaignId;
//			if (JedisUtils.exists(key)) {
//				JedisUtils.delete(key);
//			}
//		}
//	}
	
	/**
	 * 活动下创意ID写入redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void writeMapidToRedis(String campaignId) throws Exception {
		//查询活动下创意
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> creatives = creativeDao.selectByExample(creativeExample);
		JsonArray mapidJson = new JsonArray();
		JsonObject mapidObject = new JsonObject();
		if (creatives != null && !creatives.isEmpty()) {
			// 查询创意对应的关联关系mapid
			for (CreativeModel creative : creatives) {
				String creativeId = creative.getId();
				//审核通过的创意才可以投放
				if (getAuditSuccessMapId(creativeId)) {
					mapidJson.add(creativeId);
				}
			}
		}
		mapidObject.add("mapids", mapidJson);
		JedisUtils.set(RedisKeyConstant.CAMPAIGN_MAPIDS + campaignId, mapidObject.toString());
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
	 * 获取活动对应的各个adx审核值（exts）
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
		List<AdxModel> adxs = getAdxForCampaign(campaignId);
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
	private JsonArray getCreativeExts(String mapId, String campaignId) throws Exception {
		JsonArray exts = new JsonArray();
		//查询adx列表
		List<AdxModel> adxs = getAdxForCampaign(campaignId);
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
	
	/**
	 * 判断mapId是否通过了第三方审核
	 * @param mapId
	 * @return true：是；false：否
	 */
	private boolean getAuditSuccessMapId(String mapId) throws Exception {
		CreativeAuditModelExample example = new CreativeAuditModelExample();
		example.createCriteria().andCreativeIdEqualTo(mapId)
				.andStatusEqualTo(StatusConstant.CREATIVE_AUDIT_SUCCESS);
		List<CreativeAuditModel> list = creativeAuditDao.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 从投放中活动中删除当前活动
	 * @param campaignId
	 * @throws Exception
	 */
	public void deleteCampaignId(String campaignId) throws Exception {
		String ids = JedisUtils.getStr(RedisKeyConstant.CAMPAIGN_IDS);
		JsonObject idObj = new JsonObject();
		JsonArray idArray = new JsonArray();
		//如果 有 pap_campaignids Key 
		if (!StringUtils.isEmpty(ids)) {
			Gson gson = new Gson();
			idObj = gson.fromJson(ids, new JsonObject().getClass());
			idArray = idObj.get("groupids").getAsJsonArray();
			// 判断是否已经含有当前campaignID
			for (int i = 0; i < idArray.size(); i++) {
				if (campaignId.equals(idArray.get(i).getAsString())) {
					//删除key
					idArray.remove(i);
					break;
				}
			}
			JedisUtils.set(RedisKeyConstant.CAMPAIGN_IDS, idObj.toString());
		}
	}
	
	/**
	 * 根据mapId查询价格
	 * @param mapId
	 * @return
	 * @throws Exception
	 */
	public Float getCreativePrice(String mapId) throws Exception {
//		//查询关联表数据
//		CreativeMaterialModel materialModel = creativeMaterialDao.selectByPrimaryKey(mapId);
//		if (materialModel == null) {
//			throw new ResourceNotFoundException();
//		}
//		//查询创意表数据
//		String creativeId = materialModel.getCreativeId();
//		String tmplId = materialModel.getTmplId();//模版ID
//		CreativeModel creativeModel = creativeDao.selectByPrimaryKey(creativeId);
//		if (creativeModel == null) {
//			throw new ResourceNotFoundException();
//		}
//		//获取活动ID
//		String campaignId = creativeModel.getCampaignId();
//		//根据模版ID、活动ID查询价格
//		CampaignTmplPriceModelExample example = new CampaignTmplPriceModelExample();
//		example.createCriteria().andTmplIdEqualTo(tmplId).andCampaignIdEqualTo(campaignId);
//		List<CampaignTmplPriceModel> list = campaignTmplPriceDao.selectByExample(example);
//		if (list == null || list.isEmpty()) {
//			throw new ResourceNotFoundException();
//		}
//		CampaignTmplPriceModel campaignTmplPriceModel = list.get(0);
//		BigDecimal price = campaignTmplPriceModel.getPrice();
		
		CreativeModel model = creativeDao.selectByPrimaryKey(mapId);
		Float price = model.getPrice();
		
		
		return price;
	}
	
	/**
	 * 删除redis中活动对应key
	 * @param campaignId
	 * @throws Exception
	 */
	public void deleteKeyFromRedis(String campaignId) throws Exception {
		deleteCampaignId(campaignId);
		deleteCampaignMapidFromredis(campaignId);
		deleteCampaignInfoFromredis(campaignId);
		deleteCampaignTargetFromredis(campaignId);
		deleteCampaignFrequencyFromredis(campaignId);
		deleteMapidsFromRedis(campaignId);
		deleteCampaignWBListFromredis(campaignId);
		deleteCampaignBudgetFormRedis(campaignId);
//		deleteCampaignCounterFromRedis(campaignId);
	}
	/**
	 * 删除redis中key：dsp_groupid_mapids_活动id
	 * @param campaignId
	 * @throws Exception
	 */
	public void deleteCampaignMapidFromredis(String campaignId) throws Exception {
		String str = JedisUtils.getStr(RedisKeyConstant.CAMPAIGN_MAPIDS + campaignId);
		if (!StringUtils.isEmpty(str)) {
			JedisUtils.delete(RedisKeyConstant.CAMPAIGN_MAPIDS + campaignId);
		}
	}
	/**
	 * 删除redis中key：dsp_groupid_info_id
	 * @param campaignId
	 * @throws Exception
	 */
	public void deleteCampaignInfoFromredis(String campaignId) throws Exception {
		String str = JedisUtils.getStr(RedisKeyConstant.CAMPAIGN_INFO + campaignId);
		if (!StringUtils.isEmpty(str)) {
			JedisUtils.delete(RedisKeyConstant.CAMPAIGN_INFO + campaignId);
		}
	}
	/**
	 * 删除redis中key：dsp_groupid_target_活动id
	 * @param campaignId
	 * @throws Exception
	 */
	public void deleteCampaignTargetFromredis(String campaignId) throws Exception {
		String str = JedisUtils.getStr(RedisKeyConstant.CAMPAIGN_TARGET + campaignId);
		if (!StringUtils.isEmpty(str)) {
			JedisUtils.delete(RedisKeyConstant.CAMPAIGN_TARGET + campaignId);
		}
	}
	/**
	 * 删除redis中key：dsp_groupid_frequencycapping_"活动id
	 * @param campaignId
	 * @throws Exception
	 */
	public void deleteCampaignFrequencyFromredis(String campaignId) throws Exception {
		String str = JedisUtils.getStr(RedisKeyConstant.CAMPAIGN_FREQUENCY + campaignId);
		if (!StringUtils.isEmpty(str)) {
			JedisUtils.delete(RedisKeyConstant.CAMPAIGN_FREQUENCY + campaignId);
		}
	}
	/**
	 * 删除redis中key：dsp_groupid_wblist_"活动id
	 * @param campaignId
	 * @throws Exception
	 */
	public void deleteCampaignWBListFromredis(String campaignId) throws Exception {
		String str = JedisUtils.getStr(RedisKeyConstant.CAMPAIGN_WBLIST + campaignId);
		if (!StringUtils.isEmpty(str)) {
			JedisUtils.delete(RedisKeyConstant.CAMPAIGN_WBLIST + campaignId);
		}
	}
	/**
	 * 删除redis中key：dsp_mapid_关联id
	 * @param campaignId
	 * @throws Exception
	 */
	public void deleteMapidsFromRedis(String campaignId) throws Exception {
		//		// 查询活动下创意
//		CreativeModelExample creativeExample = new CreativeModelExample();
//		creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
//		List<CreativeModel> creatives = creativeDao.selectByExample(creativeExample);
//		// 创意id数组
//		List<String> creativeIds = new ArrayList<String>();
//		//如果活动下无可投创意
//		if(creatives == null || creatives.isEmpty()){
//			return;
//		}
//		// 将查询出来的创意id放入创意id数组
//		for (CreativeModel creative : creatives) {
//			creativeIds.add(creative.getId());
//		}
//		// 根据创意id数组查询创意所对应的关联关系表数据
//		if (!creativeIds.isEmpty()) {
//			CreativeMaterialModelExample mapExample = new CreativeMaterialModelExample();
//			mapExample.createCriteria().andCreativeIdIn(creativeIds);
//			List<CreativeMaterialModel> mapModels = creativeMaterialDao.selectByExample(mapExample);
//			if (mapModels != null && !mapModels.isEmpty()) {
//				for (CreativeMaterialModel mapModel : mapModels) {
//					String mapId = mapModel.getId();
//					String str = JedisUtils.getStr(RedisKeyConstant.CREATIVE_INFO + mapId);
//					if (!StringUtils.isEmpty(str)) {
//						JedisUtils.delete(RedisKeyConstant.CREATIVE_INFO + mapId);
//					}
//				}
//			}
//		}
		CreativeModelExample example = new CreativeModelExample();
		example.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> list = creativeDao.selectByExample(example);
		for (CreativeModel mapModel : list) {
			String mapId = mapModel.getId();
			String str = JedisUtils.getStr(RedisKeyConstant.CREATIVE_INFO + mapId);
			if (!StringUtils.isEmpty(str)) {
				JedisUtils.delete(RedisKeyConstant.CREATIVE_INFO + mapId);
			}
		}
	}
	
	/**
	 * 为活动下素材添加权重key
	 * @param model
	 * @param type
	 */
	public void addRateForMaterial(String campaignId, String type, int width, int hight, String materialName) throws Exception {
		Map<String, String> map = JedisUtils.hget(type + width + "X" + hight);
		if (!StringUtils.isEmpty(map.get(campaignId))) {
			String valueInReids = map.get(campaignId).toString();
			String[] values = valueInReids.split(",");
			//判断是否已经含有当前model名称
			boolean flag = false;
			for (int i = 0; i < values.length; i++) {
				String value = values[i].substring(0, values[i].indexOf("|"));
				if (value.equals(materialName)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				//如果没有就在合并放入redis
				materialName = valueInReids + "," + materialName;
				map.put(campaignId, materialName + "|1");
			}
			JedisUtils.hset(type + width + "X" + hight, map);
		} else {
			map.put(campaignId, materialName + "|1");
			JedisUtils.hset(type + width + "X" + hight, map);
		}
	}
	
	/**
	 * 将黑白名单写入redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void writeWhiteBlackToRedis(String campaignId) throws Exception {
		PopulationTargetModelExample pex = new PopulationTargetModelExample();
		pex.createCriteria().andCampaignIdEqualTo(campaignId);
		List<PopulationTargetModel> lists = populationTargetDao.selectByExample(pex);
		String populationId = null;
		if (lists != null && !lists.isEmpty()) {
			for (PopulationTargetModel populationTargetModel : lists) {
				populationId = populationTargetModel.getPopulationId();
			}
		}
		if (!StringUtils.isEmpty(populationId)) {
			PopulationModel population = populationDao.selectByPrimaryKey(populationId);
			if (population != null) {
				String path = population.getPath();
				String type = population.getType();
				readFile(campaignId, populationId, path, type);
			}
		}
	}
	
	/**
	 * 读取文件写入redis
	 * @param campaignId
	 * @param populationId
	 * @param path
	 * @param type
	 * @throws Exception
	 */
	public static void readFile(String campaignId,String populationId, String path, String type) throws Exception {
		String wlType = "_wl_";
		if ("02".equals(type)) {
			wlType = "_bl_";
		}
		String key = null;//文本中的key
		String name = null;//名称（拼接好的）
		boolean flag = true;//是不是第一次遇到key（第一次碰到符合“[***]”字样）
		File file = new File(path);
		if (file.exists()) {//文件是否存在
			List<String> list = FileUtils.readLines(file,"GBK");
			List<String> values = new ArrayList<String>();
			JsonArray redisArray = new JsonArray();//redis中key用到
			for (int i = 0; i < list.size(); i++) {
				String str = list.get(i);
				if (!StringUtils.isEmpty(str)) {
					int left = str.indexOf("[");
					int right = str.indexOf("]");
					if ( left > -1 && right > -1) {
						if (flag) {//如果是第一次遇到key不做操作
							flag = false;//变成false，证明以后的不是第一次遇到key
						} else {//不是第一次时候
							JedisUtils.sddKey(name, values);//将刚才的name、values写入redis
						}
						values = new ArrayList<String>();//将数组置空
						key = str.substring(left + 1, right);//不管是不是第一次，都让key等于当前这个符合“[***]”的字符串
						if ("imei".equals(key.toLowerCase())) {//根据不同类型，redisArray中放入不同值
							redisArray.add(16);
						} else if ("imei_sha1".equals(key.toLowerCase())) {
							redisArray.add(17);
						} else if ("imei_md5".equals(key.toLowerCase())) {
							redisArray.add(18);
						} else if ("mac".equals(key.toLowerCase())) {
							redisArray.add(32);
						} else if ("mac_sha1".equals(key.toLowerCase())) {
							redisArray.add(33);
						} else if ("mac_md5".equals(key.toLowerCase())) {
							redisArray.add(34);
						} else if ("android".equals(key.toLowerCase())) {
							redisArray.add(96);
						} else if ("android_sha1".equals(key.toLowerCase())) {
							redisArray.add(97);
						} else if ("android_md5".equals(key.toLowerCase())) {
							redisArray.add(98);
						} else if ("idfa".equals(key.toLowerCase())) {
							redisArray.add(112);
						} else if ("idfa_sha1".equals(key.toLowerCase())) {
							redisArray.add(113);
						} else if ("idfa_md5".equals(key.toLowerCase())) {
							redisArray.add(1153);
						}
						name = key + wlType +populationId;//不管是不是第一次yudaokey，都让name等于这个新名称
						//这样新key、新name、新value，下一次再遇到key，直接放入redis
					} else {
						values.add(str);//如果不是key那就让如list中
					}
					if (i == list.size()-1) {
						JedisUtils.sddKey(name, values);//最后一个key的值，循环完成后也要添加
					}
				}
			}
			if (!flag) {
				JsonObject obj = new JsonObject();
				obj.addProperty("groupid", campaignId);
				if ("02".equals(type)) {
					obj.add("blacklist", redisArray);
				} else if ("01".equals(type)) {
					obj.add("whitelist", redisArray);
				}
				obj.addProperty("retio", 0);
				obj.addProperty("mprice", 0);
				String redisKey = RedisKeyConstant.CAMPAIGN_WBLIST + campaignId;
				JedisUtils.set(redisKey, obj.toString());
			}
		}
	}
	
	/**
	 * 查询活动定向ADX
	 * @param campaignId
	 * @throws Exception
	 */
	public List<AdxModel> getAdxForCampaign(String campaignId) throws Exception {
		List<AdxModel> adxs = new ArrayList<AdxModel>();
		AppTargetModelExample appTargetEx = new AppTargetModelExample();
		appTargetEx.createCriteria().andCampaignIdEqualTo(campaignId);
		List<AppTargetModel> appTargets = appTargetDao.selectByExample(appTargetEx);
		List<String> appIds = new ArrayList<String>();
		if (appTargets != null) {
			for (AppTargetModel tar : appTargets) {
				String appId = tar.getAppId();
				appIds.add(appId);
			}
			List<String> adxIds = new ArrayList<String>();
			AppModelExample appEx = new AppModelExample();
			appEx.createCriteria().andIdIn(appIds);
			List<AppModel> apps = appDao.selectByExample(appEx);
			if (apps != null) {
				for (AppModel app : apps) {
					String adxId = app.getAdxId();
					adxIds.add(adxId);
				}
				AdxModelExample adxEx = new AdxModelExample();
				adxEx.createCriteria().andIdIn(adxIds);
				adxs = adxDao.selectByExample(adxEx);
			}
		}
		return adxs;
	}
	
	
}
