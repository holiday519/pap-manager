package com.pxene.pap.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.GlobalUtil;
import com.pxene.pap.common.JedisUtils;
import com.pxene.pap.constant.CodeTableConstant;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.RedisKeyConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.models.AdvertiserAuditModel;
import com.pxene.pap.domain.models.AdvertiserAuditModelExample;
import com.pxene.pap.domain.models.AdvertiserModel;
import com.pxene.pap.domain.models.AppModel;
import com.pxene.pap.domain.models.AppModelExample;
import com.pxene.pap.domain.models.AppTargetModel;
import com.pxene.pap.domain.models.AppTargetModelExample;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignModelExample;
import com.pxene.pap.domain.models.CreativeAuditModel;
import com.pxene.pap.domain.models.CreativeAuditModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.CreativeModelExample;
import com.pxene.pap.domain.models.FrequencyModel;
import com.pxene.pap.domain.models.ImageModel;
import com.pxene.pap.domain.models.PopulationModel;
import com.pxene.pap.domain.models.PopulationTargetModel;
import com.pxene.pap.domain.models.PopulationTargetModelExample;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.domain.models.ProjectModelExample;
import com.pxene.pap.domain.models.QuantityModel;
import com.pxene.pap.domain.models.QuantityModelExample;
import com.pxene.pap.domain.models.TimeTargetModel;
import com.pxene.pap.domain.models.TimeTargetModelExample;
import com.pxene.pap.domain.models.view.CampaignTargetModel;
import com.pxene.pap.domain.models.view.CampaignTargetModelExample;
import com.pxene.pap.domain.models.view.CreativeImageModelExample;
import com.pxene.pap.domain.models.view.CreativeImageModelWithBLOBs;
import com.pxene.pap.domain.models.view.CreativeInfoflowModelExample;
import com.pxene.pap.domain.models.view.CreativeInfoflowModelWithBLOBs;
import com.pxene.pap.domain.models.view.CreativeVideoModelExample;
import com.pxene.pap.domain.models.view.CreativeVideoModelWithBLOBs;
import com.pxene.pap.exception.ResourceNotFoundException;
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
import com.pxene.pap.repository.basic.PopulationDao;
import com.pxene.pap.repository.basic.PopulationTargetDao;
import com.pxene.pap.repository.basic.ProjectDao;
import com.pxene.pap.repository.basic.QuantityDao;
import com.pxene.pap.repository.basic.RegionTargetDao;
import com.pxene.pap.repository.basic.TimeTargetDao;
import com.pxene.pap.repository.basic.view.CampaignTargetDao;
import com.pxene.pap.repository.basic.view.CreativeImageDao;
import com.pxene.pap.repository.basic.view.CreativeInfoflowDao;
import com.pxene.pap.repository.basic.view.CreativeVideoDao;

@Service
public class LaunchService extends BaseService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LaunchService.class);
	// 视图中的分隔符
	private static final String MONITOR_SEPARATOR = "\\|";
	
	private static String image_url;
	
	@Autowired
	public LaunchService(Environment env)
	{
		/**
		 * 获取图片上传路径
		 */
		image_url = env.getProperty("pap.fileserver.url.prefix");
	}
	
	@Autowired
	private CampaignService campaignService;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private QuantityDao quantityDao;
	
	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired
	private CampaignTargetDao campaignTargetDao;
	
	@Autowired
	private CreativeAuditDao creativeAuditDao;
	
	@Autowired
	private CreativeDao creativeDao;
	
	@Autowired
	private CreativeImageDao creativeImageDao;
	
	@Autowired
	private CreativeVideoDao creativeVideoDao;
	
	@Autowired
	private CreativeInfoflowDao creativeInfoflowDao;
	
	@Autowired
	private AppTargetDao appTargetDao;
	
	@Autowired
	private AppDao appDao;
	
	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private AdvertiserDao advertiserDao;
	
	@Autowired
	private AdxDao adxDao;
	
	@Autowired
	private AdvertiserAuditDao advertiserAuditDao;
	
	@Autowired
	private RegionTargetDao regionTargetDao;
	
	@Autowired
	private TimeTargetDao timeTargetDao;
	
	@Autowired
	private FrequencyDao frequencyDao;
	
	@Autowired
	private PopulationDao populationDao;
	
	@Autowired
	private PopulationTargetDao populationTargetDao;
	
	private static final String POPULATION_ROOT_PATH = "/data/population/";
	
	private static Gson gson = new Gson();
	private static JsonParser parser = new JsonParser();
	
	private static Map<String, Integer> deviceIdType = new HashMap<String, Integer>();
	static {
		deviceIdType.put("imei", 16);
		deviceIdType.put("imei_sha1", 17);
		deviceIdType.put("imei_md5", 18);
		deviceIdType.put("mac", 32);
		deviceIdType.put("mac_sha1", 33);
		deviceIdType.put("mac_md5", 34);
		deviceIdType.put("android", 96);
		deviceIdType.put("android_sha1", 97);
		deviceIdType.put("android_md5", 98);
		deviceIdType.put("idfa", 112);
		deviceIdType.put("idfa_sha1", 113);
		deviceIdType.put("idfa_md5", 114);
	}
	
	/**
	 * 到开始日期的任务调用该方法，将基本信息写入redis（预算、counter、活动ID会在随后的逻辑中写入）
	 * @param campaign
	 * @throws Exception
	 */
	public void write4StartDate(CampaignModel campaign) throws Exception {
		String campaignId = campaign.getId();
		//写入活动下的创意基本信息   dsp_mapid_*
		writeCreativeInfo(campaignId);
		//写入活动下的创意ID dsp_group_mapids_*
		writeCreativeId(campaignId);
		//写入活动基本信息   dsp_group_info_*
		writeCampaignInfo(campaign);
		//写入活动定向   dsp_group_target_*
		writeCampaignTarget(campaignId);
		//写入活动频次信息   dsp_groupid_frequencycapping_*
		writeCampaignFrequency(campaign);
		//写入黑白名单信息
		writeWhiteBlack(campaignId);
	}
	
	/**
	 * 第一次手动开启活动时调用该方法
	 * @param campaign
	 * @throws Exception
	 */
	public void write4FirstTime(CampaignModel campaign) throws Exception {
		String campaignId = campaign.getId();
		write4StartDate(campaign);
		writeCampaignBudget(campaign);
		writeCampaignCounter(campaignId);
	}
	
	/**
	 * 到结束日期的任务调用该方法，将redis内所有相关内容全部清除
	 * @param campaign
	 * @throws Exception
	 */
	public void remove4EndDate(CampaignModel campaign) throws Exception {
		String campaignId = campaign.getId();
		removeCreativeInfo(campaignId);
		removeCreativeId(campaignId);
		removeCampaignInfo(campaignId);
		removeCampaignTarget(campaignId);
		removeCampaignFrequency(campaignId);
		removeWhiteBlack(campaignId);
		removeCampaignBudget(campaignId);
		removeCampaignCounter(campaignId);
		removeCampaignId(campaignId);
	}
	
	/**
	 * 根据时间定向投放活动，结束到期活动————————————每小时投放项目定时器
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 */1 * * ?")
	public void launchOnEveryHour() throws Exception {
		// 当前小时
		String currentHour = DateUtils.getCurrentHour();
		// yyyy-MM-dd
		String currentDate = DateUtils.getCurrentDate();
		// 当前日期的整点时间
		Date start = DateUtils.strToDate(currentDate, "yyyy-MM-dd");
		// 当前日期退后一秒钟的时间
		Date end = DateUtils.changeDate(start, Calendar.SECOND, -1);
		
		CampaignModelExample campaignExample = new CampaignModelExample();
		Date current = new Date();
		campaignExample.createCriteria().andStartDateLessThanOrEqualTo(current).andEndDateGreaterThanOrEqualTo(current);
		// 所有正在投放的活动
		List<CampaignModel> launchCampaigns = campaignDao.selectByExample(campaignExample);
		
		// 如果是0点，需要做如下事情：
		// 将今天开始投放的活动，所有数据写入redis
		// 将今天结束投放的活动，redis中的数据删除
		if ("00".equals(currentHour)) {
			// 找出开启状态的项目
			ProjectModelExample projectExample = new ProjectModelExample();
			projectExample.createCriteria().andStatusEqualTo(StatusConstant.PROJECT_PROCEED);
			List<ProjectModel> projects = projectDao.selectByExample(projectExample);
			List<String> projectIds = new ArrayList<String>();
			for (ProjectModel project : projects) {
				projectIds.add(project.getId());
			}
			// 找出今天开始投放的活动
			campaignExample.clear();
			campaignExample.createCriteria().andProjectIdIn(projectIds).andStatusEqualTo(StatusConstant.CAMPAIGN_PROCEED).andStartDateEqualTo(start);
			List<CampaignModel> addCampaigns = campaignDao.selectByExample(campaignExample);
			for (CampaignModel campaign : addCampaigns) {
				write4StartDate(campaign);
			}
			
			campaignExample.clear();
			campaignExample.createCriteria().andEndDateEqualTo(end);
			List<CampaignModel> delCampaigns = campaignDao.selectByExample(campaignExample);
			for (CampaignModel campaign : delCampaigns) {
				remove4EndDate(campaign);
			}
			
			// 预算重新写入
			for (CampaignModel campaign : launchCampaigns) {
				String campaignId = campaign.getId();
				writeCampaignBudget(campaign);
				writeCampaignCounter(campaignId);
			}
		}
		// 每个小时判断时间定向，将不在该时间内的活动移除
		for (CampaignModel campaign : launchCampaigns) {
			String campaignId = campaign.getId();
			// 如果当期时间在定向内
			if (campaignService.isOnTargetTime(campaignId)) {
				writeCampaignId(campaignId);
			} else {
				removeCampaignId(campaignId);
			}
		}
		
	}
	
	/*******************************************************************************************************/
	/**
	 * 将活动ID 写入redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void writeCampaignId(String campaignId) throws Exception {
		String idStr = JedisUtils.getStr(RedisKeyConstant.CAMPAIGN_IDS);
		JsonObject idObj = null;
		if (idStr == null) {
			idObj = new JsonObject();
			JsonArray idArray = new JsonArray();
			idArray.add(campaignId);
			idObj.add("groupids", idArray);
		} else {
			idObj = gson.fromJson(idStr, new JsonObject().getClass());
			JsonArray idArray = idObj.get("groupids").getAsJsonArray();
			if (!idArray.contains(parser.parse(campaignId))) {
				idArray.add(campaignId);
				idObj.add("groupids", idArray);
			}
		}
		JedisUtils.set(RedisKeyConstant.CAMPAIGN_IDS, idObj.toString());
	}
	/**
	 * 将活动ID 移除redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void removeCampaignId(String campaignId) throws Exception {
		String idStr = JedisUtils.getStr(RedisKeyConstant.CAMPAIGN_IDS);
		if (idStr != null) {
			JsonObject idObj = gson.fromJson(idStr, new JsonObject().getClass());
			JsonArray idArray = idObj.get("groupids").getAsJsonArray();
			JsonElement id = parser.parse(campaignId);
			if (idArray.contains(id)) {
				idArray.remove(id);
			}
			idObj.add("groupids", idArray);
			JedisUtils.set(RedisKeyConstant.CAMPAIGN_IDS, idObj.toString());
		}
	}
	
	/**
	 * 创意基本信息写入redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void writeCreativeInfo(String campaignId) throws Exception {
		// 查询活动下创意
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> creatives = creativeDao.selectByExample(creativeExample);
		// 如果活动下无可投创意
		if (creatives != null && !creatives.isEmpty()) {
			for (CreativeModel creative : creatives) {
				String creativeType = creative.getType();
				// 图片创意
				if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(creativeType)) {
					writeImgCreativeInfo(creative);
				// 视频创意
				} else if (StatusConstant.CREATIVE_TYPE_VIDEO.equals(creativeType)) {
					writeVideoCreativeInfo(creative);
				// 信息流创意
				} else if (StatusConstant.CREATIVE_TYPE_INFOFLOW.equals(creativeType)) {
					writeInfoflowCreativeInfo(creative);
				}
			}
		}
	}
	
	/**
	 * 创意基本信息移除redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void removeCreativeInfo(String campaignId) throws Exception {
		// 查询活动下创意
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> creatives = creativeDao.selectByExample(creativeExample);
		// 如果活动下无可投创意
		if (creatives != null && !creatives.isEmpty()) {
			for (CreativeModel creative : creatives) {
				JedisUtils.delete(RedisKeyConstant.CREATIVE_INFO + creative.getId());
			}
		}
	}
	
	/**
	 * 图片创意信息写入redis
	 * @param mapId
	 * @throws Exception
	 */
	public void writeImgCreativeInfo(CreativeModel creative) throws Exception {
		CreativeImageModelExample creativeImageExample = new CreativeImageModelExample();
		String creativeId = creative.getId();
		creativeImageExample.createCriteria().andCreativeIdEqualTo(creativeId);
		List<CreativeImageModelWithBLOBs> models = creativeImageDao.selectByExampleWithBLOBs(creativeImageExample);
		if (models != null && !models.isEmpty()) {
			for (CreativeImageModelWithBLOBs model : models) {
				JsonObject creativeObj = new JsonObject();
				creativeObj.addProperty("mapid", model.getCreativeId());
				creativeObj.addProperty("groupid", model.getCampaignId());
				creativeObj.addProperty("type", 2);
				creativeObj.addProperty("ftype", Integer.parseInt(model.getFormat()));
				List<Map<String, String>> adxes = getAdxByCreative(creative);
				JsonArray prices = new JsonArray();
				JsonArray exts = new JsonArray();
				for (Map<String, String> adx : adxes) {
					JsonObject price = new JsonObject();
					price.addProperty("adx", Integer.parseInt(adx.get("adxId")));
					price.addProperty("price", creative.getPrice());
					prices.add(price);
					if (adx.containsKey("creativeAudit")) {
						JsonObject ext = new JsonObject();
						ext.addProperty("adx", Integer.parseInt(adx.get("adxId")));
						ext.addProperty("id", adx.get("creativeAudit"));
						exts.add(ext);
					}
				}
				creativeObj.add("price_adx", prices);
				if (exts.size() > 0) {
					creativeObj.add("exts", exts);
				}
				creativeObj.addProperty("ctype", 1);
				creativeObj.addProperty("w", GlobalUtil.parseInt(model.getWidth(), 0));
				creativeObj.addProperty("h", GlobalUtil.parseInt(model.getHeight(), 0));
				creativeObj.addProperty("curl", GlobalUtil.parseString(model.getMonitorUrl(), model.getLandpageUrl()));
				creativeObj.addProperty("landingurl", GlobalUtil.parseString(model.getLandpageUrl(), ""));
				
				String[] tempImonitorUrl = GlobalUtil.parseString(model.getImpressionUrl(), "").split(MONITOR_SEPARATOR);
	            String[] tempCmonitorUrl = GlobalUtil.parseString(model.getClickUrl(), "").split(MONITOR_SEPARATOR);
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
	            creativeObj.addProperty("sourceurl", image_url + model.getPath());
	            creativeObj.addProperty("cid", GlobalUtil.parseString(model.getProjectId(), ""));
				
				JedisUtils.set(RedisKeyConstant.CREATIVE_INFO + creativeId, creativeObj.toString());
			}
		}
	}
	
	/**
	 * 视频创意信息写入redis
	 * @param mapId
	 * @throws Exception
	 */
	public void writeVideoCreativeInfo(CreativeModel creative) throws Exception {
		CreativeVideoModelExample creativeVideoExample = new CreativeVideoModelExample();
		String creativeId = creative.getId();
		creativeVideoExample.createCriteria().andCreativeIdEqualTo(creativeId);
		List<CreativeVideoModelWithBLOBs> models = creativeVideoDao.selectByExampleWithBLOBs(creativeVideoExample);
		if (models != null && !models.isEmpty()) {
			for (CreativeVideoModelWithBLOBs model : models) {
				JsonObject creativeObj = new JsonObject();
				creativeObj.addProperty("mapid", model.getCreativeId());
				creativeObj.addProperty("groupid", model.getCampaignId());
				creativeObj.addProperty("type", 6);
				creativeObj.addProperty("ftype", model.getFormat());
				List<Map<String, String>> adxes = getAdxByCreative(creative);
				JsonArray prices = new JsonArray();
				JsonArray exts = new JsonArray();
				for (Map<String, String> adx : adxes) {
					JsonObject price = new JsonObject();
					price.addProperty("adx", Integer.parseInt(adx.get("adxId")));
					price.addProperty("price", creative.getPrice());
					prices.add(price);
					if (adx.containsKey("creativeAudit")) {
						JsonObject ext = new JsonObject();
						ext.addProperty("adx", Integer.parseInt(adx.get("adxId")));
						ext.addProperty("id", adx.get("creativeAudit"));
						exts.add(ext);
					}
				}
				creativeObj.add("price_adx", prices);
				if (exts.size() > 0) {
					creativeObj.add("exts", exts);
				}
				creativeObj.addProperty("ctype", 1);
				creativeObj.addProperty("w", GlobalUtil.parseInt(model.getWidth(), 0));
				creativeObj.addProperty("h", GlobalUtil.parseInt(model.getHeight(), 0));
				creativeObj.addProperty("curl", GlobalUtil.parseString(model.getMonitorUrl(), model.getLandpageUrl()));
				creativeObj.addProperty("landingurl", GlobalUtil.parseString(model.getLandpageUrl(), ""));
				
				String[] tempImonitorUrl = GlobalUtil.parseString(model.getImpressionUrl(), "").split(MONITOR_SEPARATOR);
	            String[] tempCmonitorUrl = GlobalUtil.parseString(model.getClickUrl(), "").split(MONITOR_SEPARATOR);
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
	            creativeObj.addProperty("sourceurl", image_url + model.getPath());
	            creativeObj.addProperty("cid", GlobalUtil.parseString(model.getProjectId(), ""));
	            
				JedisUtils.set(RedisKeyConstant.CREATIVE_INFO + creativeId, creativeObj.toString());
			}
		}
	}
	
	/**
	 * 信息流创意信息写入redis
	 * @param mapId
	 * @throws Exception
	 */
	public void writeInfoflowCreativeInfo(CreativeModel creative) throws Exception {
		CreativeInfoflowModelExample creativeInfoflowExample = new CreativeInfoflowModelExample();
		String creativeId = creative.getId();
		creativeInfoflowExample.createCriteria().andCreativeIdEqualTo(creativeId);
		List<CreativeInfoflowModelWithBLOBs> models = creativeInfoflowDao.selectByExampleWithBLOBs(creativeInfoflowExample);
		if (models != null && !models.isEmpty()) {
			for (CreativeInfoflowModelWithBLOBs model : models) {
				JsonObject creativeObj = new JsonObject();
				creativeObj.addProperty("mapid", model.getCreativeId());
				creativeObj.addProperty("groupid", model.getCampaignId());
				creativeObj.addProperty("type", 9);
				List<Map<String, String>> adxes = getAdxByCreative(creative);
				JsonArray prices = new JsonArray();
				JsonArray exts = new JsonArray();
				for (Map<String, String> adx : adxes) {
					JsonObject price = new JsonObject();
					price.addProperty("adx", Integer.parseInt(adx.get("adxId")));
					price.addProperty("price", creative.getPrice());
					prices.add(price);
					if (adx.containsKey("creativeAudit")) {
						JsonObject ext = new JsonObject();
						ext.addProperty("adx", Integer.parseInt(adx.get("adxId")));
						ext.addProperty("id", adx.get("creativeAudit"));
						exts.add(ext);
					}
				}
				creativeObj.add("price_adx", prices);
				if (exts.size() > 0) {
					creativeObj.add("exts", exts);
				}
				creativeObj.addProperty("ctype", 1);

				String iconId = model.getIconId();
				if (iconId != null) {
					ImageModel image = imageDao.selectByPrimaryKey(iconId);
					if (model != null) {
						JsonObject icon = new JsonObject();
						icon.addProperty("w", GlobalUtil.parseInt(image.getWidth(), 0));
						icon.addProperty("h", GlobalUtil.parseInt(image.getHeight(), 0));
						icon.addProperty("ftype", Integer.parseInt(image.getFormat()));
						icon.addProperty("sourceurl", image_url + image.getPath());
						creativeObj.add("icon", icon);
					}
				}
				String image1Id = model.getImage1Id();
				String image2Id = model.getImage2Id();
				String image3Id = model.getImage3Id();
				String image4Id = model.getImage4Id();
				String image5Id = model.getImage5Id();
				List<String> imageIds = new ArrayList<String>();
				if (!StringUtils.isEmpty(image1Id)) {
					imageIds.add(image1Id);
				}
				if (!StringUtils.isEmpty(image2Id)) {
					imageIds.add(image2Id);
				}
				if (!StringUtils.isEmpty(image3Id)) {
					imageIds.add(image3Id);
				}
				if (!StringUtils.isEmpty(image4Id)) {
					imageIds.add(image4Id);
				}
				if (!StringUtils.isEmpty(image5Id)) {
					imageIds.add(image5Id);
				}
				
				if (imageIds.size() == 1) {
					ImageModel image = imageDao.selectByPrimaryKey(imageIds.get(0));
					creativeObj.addProperty("w", GlobalUtil.parseInt(image.getWidth(), 0));
					creativeObj.addProperty("h", GlobalUtil.parseInt(image.getHeight(), 0));
					creativeObj.addProperty("ftype", image.getFormat());
					creativeObj.addProperty("sourceurl", image_url + image.getPath());
				} else if (imageIds.size() > 1) {
					JsonArray imageJsons = new JsonArray(); 
					for (String imageId : imageIds) {
						ImageModel image = imageDao.selectByPrimaryKey(imageId);
						JsonObject imageJson = new JsonObject();
						imageJson.addProperty("w", GlobalUtil.parseInt(image.getWidth(), 0));
						imageJson.addProperty("h", GlobalUtil.parseInt(image.getHeight(), 0));
						imageJson.addProperty("ftype", Integer.parseInt(image.getFormat()));
						imageJson.addProperty("sourceurl", image_url + image.getPath());
						imageJsons.add(imageJson);
					}
					creativeObj.add("imgs", imageJsons);
				}
				creativeObj.addProperty("curl", GlobalUtil.parseString(model.getMonitorUrl(), model.getLandpageUrl()));
				creativeObj.addProperty("landingurl", GlobalUtil.parseString(model.getLandpageUrl(), ""));
				
				String[] tempImonitorUrl = GlobalUtil.parseString(model.getImpressionUrl(), "").split(MONITOR_SEPARATOR);
	            String[] tempCmonitorUrl = GlobalUtil.parseString(model.getClickUrl(), "").split(MONITOR_SEPARATOR);
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
	            creativeObj.addProperty("cid", GlobalUtil.parseString(model.getProjectId(), ""));
	            creativeObj.addProperty("title", GlobalUtil.parseString(model.getTitle(), ""));
	            creativeObj.addProperty("description", GlobalUtil.parseString(model.getDescription(), ""));
	            creativeObj.addProperty("rating", GlobalUtil.parseString(model.getAppStar(), ""));
	            creativeObj.addProperty("ctatext", GlobalUtil.parseString(model.getCtaDescription(), ""));
	            
				JedisUtils.set(RedisKeyConstant.CREATIVE_INFO + creativeId, creativeObj.toString());
			}
		}
	}
	
	
	private List<Map<String, String>> getAdxByCreative(CreativeModel creative) throws Exception {
		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
		String campaignId = creative.getCampaignId();
		String creativeId = creative.getId();
		CampaignModel campaign = campaignDao.selectByPrimaryKey(campaignId);
		
		List<Map<String, String>> adxes = getAdxByCampaign(campaign);
		for (Map<String, String> adx : adxes) {
			String adxId = adx.get("adxId");
			Map<String, String> result = new HashMap<String, String>();
			result.put("adxId", adxId);
			CreativeAuditModelExample example = new CreativeAuditModelExample();
			example.createCriteria().andAdxIdEqualTo(adxId).andCreativeIdEqualTo(creativeId);
			List<CreativeAuditModel> audits = creativeAuditDao.selectByExample(example);
			if (audits != null && !audits.isEmpty()) {
				CreativeAuditModel audit = audits.get(0);
				String auditValue = audit.getAuditValue();
				result.put("creativeAudit", auditValue);
				results.add(result);
			}
		}
		return results;
	}
	
	private List<Map<String, String>> getAdxByCampaign(CampaignModel campaign) throws Exception {
		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
		String campaignId = campaign.getId();
		String projectId = campaign.getProjectId();
		//查询广告主ID
		ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
		String advertiserId = projectModel.getAdvertiserId();
		
		AppTargetModelExample appTargetExample = new AppTargetModelExample();
		appTargetExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<AppTargetModel> appTargets = appTargetDao.selectByExample(appTargetExample);
		
		if (appTargets != null && !appTargets.isEmpty()) {
			List<String> appIds = new ArrayList<String>();
			for (AppTargetModel target : appTargets) {
				String appId = target.getAppId();
				appIds.add(appId);
			}
			AppModelExample appExample = new AppModelExample();
			appExample.createCriteria().andIdIn(appIds);
			List<AppModel> apps = appDao.selectByExample(appExample);
			if (apps != null && !apps.isEmpty()) {
				Map<String, String> result = new HashMap<String, String>();
				// 去重
				Set<String> adxIds = new HashSet<String>();
				for (AppModel app : apps) {
					String adxId = app.getAdxId();
					adxIds.add(adxId);
				}
				for (String adxId : adxIds) {
					result.put("adxId", adxId);
					AdvertiserAuditModelExample example = new AdvertiserAuditModelExample();
					example.createCriteria().andAdvertiserIdEqualTo(advertiserId).andAdxIdEqualTo(adxId);
					List<AdvertiserAuditModel> audits = advertiserAuditDao.selectByExample(example);
					if (audits != null && !audits.isEmpty()) {
						AdvertiserAuditModel audit = audits.get(0);
						String auditValue = audit.getAuditValue();
						result.put("advertiserAudit", auditValue);
					}
					results.add(result);
				}
			}
		}
		return results;
	}
	
	/**
	 * 将活动基本信息写入redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void writeCampaignInfo(CampaignModel campaign) throws Exception {
		String projectId = campaign.getProjectId();
		ProjectModel project = projectDao.selectByPrimaryKey(projectId);
		String advertiserId = project.getAdvertiserId();
		AdvertiserModel advertiser = advertiserDao.selectByPrimaryKey(advertiserId);
		String campaignId = campaign.getId();
		
		JsonObject campaignJson = new JsonObject();
		int industryId = advertiser.getIndustryId();
		JsonArray catJsons = new JsonArray();
		JsonArray adxJsons = new JsonArray();
		JsonArray extJsons = new JsonArray();
		JsonArray auctiontypeJsons = new JsonArray();
		
		catJsons.add(industryId);
		campaignJson.add("cat", catJsons);
		campaignJson.addProperty("advcat", industryId);
		String adomain = GlobalUtil.parseString(advertiser.getSiteUrl(), "").replace("http://www.", "").replace("www.", "");
		campaignJson.addProperty("adomain", adomain);
		
		List<Map<String, String>> adxes = getAdxByCampaign(campaign);
		for (Map<String, String> adx : adxes) {
			String adxId = adx.get("adxId");
			adxJsons.add(Integer.parseInt(adxId));
			
			JsonObject auctiontypeJson = new JsonObject();
			auctiontypeJson.addProperty("adx", Integer.parseInt(adx.get("adxId")));
			auctiontypeJson.addProperty("at", 0);
			auctiontypeJsons.add(auctiontypeJson);
			
			if (adx.containsKey("advertiserAudit")) {
				JsonObject extJson = new JsonObject();
				extJson.addProperty("adx", Integer.parseInt(adxId));
				extJson.addProperty("advid", adx.get("advertiserAudit"));
				extJsons.add(extJson);
			}
		}
		campaignJson.add("adx", adxJsons);
		campaignJson.add("auctiontype", auctiontypeJsons);
		campaignJson.add("exts", extJsons);
		// 不重定向创意的curl
		campaignJson.addProperty("redirect", 0);
		// 需要效果监测
		campaignJson.addProperty("effectmonitor", 0);
		
		JedisUtils.set(RedisKeyConstant.CAMPAIGN_INFO + campaignId, campaignJson.toString());
	}
	
	/**
	 * 将活动基本信息移除redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void removeCampaignInfo(String campaignId) throws Exception {
		JedisUtils.delete(RedisKeyConstant.CAMPAIGN_INFO + campaignId);
	}
	
	/**
	 * 活动定向写入redis
	 * @param campaignId
	 * @param adType 
	 * @throws Exception
	 */
	public void writeCampaignTarget(String campaignId) throws Exception {
		JsonObject targetJson = new JsonObject();
		JsonObject deviceJson = new JsonObject();
		CampaignTargetModelExample campaignTargetExample = new CampaignTargetModelExample();
		campaignTargetExample.createCriteria().andIdEqualTo(campaignId);
		List<CampaignTargetModel> models = campaignTargetDao.selectByExampleWithBLOBs(campaignTargetExample);
		if (models != null && !models.isEmpty()) {
			int flag = 0;
			CampaignTargetModel model = models.get(0);
			targetJson.addProperty("groupid", campaignId);
			JsonArray region = targetStr2Jarr(model.getRegionId());
			JsonArray network = targetStr2Jarr(model.getNetwork());
			JsonArray os = targetStr2Jarr(model.getOs());
			JsonArray operator = targetStr2Jarr(model.getOperator());
			JsonArray device = targetStr2Jarr(model.getDevice());
			JsonArray brand = targetStr2Jarr(model.getBrandId());
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
			String[] appIds = model.getAppId().split(",");
			AppModelExample appExample = new AppModelExample();
			appExample.createCriteria().andIdIn(Arrays.asList(appIds));
			List<AppModel> apps = appDao.selectByExample(appExample);
			JsonArray appJsons = new JsonArray();
			Map<String, JsonObject> adxMap = new HashMap<String, JsonObject>();
			for (AppModel app : apps) {
				String adxId = app.getAdxId();
				if (adxMap.containsKey(adxId)) {
					JsonObject adxJson = adxMap.get(adxId);
					JsonArray appIdJsons = adxJson.get("wlist").getAsJsonArray();
					appIdJsons.add(app.getAppId());
					adxJson.add("wlist", appIdJsons);
					adxMap.put(adxId, adxJson);
				} else {
					JsonObject adxJson = new JsonObject();
					adxJson.addProperty("adx", Integer.parseInt(adxId));
					adxJson.addProperty("flag", 1);
					JsonArray appIdJsons = new JsonArray();
					appIdJsons.add(app.getAppId());
					adxJson.add("wlist", appIdJsons);
					adxMap.put(adxId, adxJson);
				}
			}
			for (Entry<String, JsonObject> entry : adxMap.entrySet()) {
				appJsons.add(entry.getValue());
			}
			targetJson.add("id", appJsons);
			
			JedisUtils.set(RedisKeyConstant.CAMPAIGN_TARGET + campaignId, targetJson.toString());
		}
	}
	
	/**
	 * 活动定向移除redis
	 * @param campaignId
	 * @param adType 
	 * @throws Exception
	 */
	public void removeCampaignTarget(String campaignId) throws Exception {
		JedisUtils.delete(RedisKeyConstant.CAMPAIGN_TARGET + campaignId);
	}
	
	/**
	 * 将字符串合格化成json数组（用于活动定向信息处理）
	 * @param targetString
	 * @return
	 */
	private JsonArray targetStr2Jarr(String targetString) throws Exception {
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
	 * 频次信息写入redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void writeCampaignFrequency(CampaignModel campaign) throws Exception {
		String campaignId = campaign.getId();
		JsonObject resultJson = new JsonObject();
		resultJson.addProperty("groupid", campaignId);
		JsonArray groupJsons = new JsonArray();
		List<Map<String, String>> adxes = getAdxByCampaign(campaign);
		if (adxes != null && !adxes.isEmpty()) {
			String uniform = campaign.getUniform();
			JsonArray frequencyJsons = null;
			
			// 匀速需要设置一天每小时的频次
			if (CodeTableConstant.CAMPAIGN_UNIFORM.equals(uniform)) {
				frequencyJsons = new JsonArray();
				// 获取今天总的展现次数
				QuantityModelExample quantityExample = new QuantityModelExample();
				Date current = new Date();
				quantityExample.createCriteria().andCampaignIdEqualTo(campaignId)
					.andStartDateLessThanOrEqualTo(current)
					.andEndDateGreaterThanOrEqualTo(current);
				List<QuantityModel> quantities = quantityDao.selectByExample(quantityExample);
				if (quantities != null && !quantities.isEmpty()) {
					int dailyImpression = quantities.get(0).getDailyImpression();
					// 获取今天的时间定向
					String week = "0" + DateUtils.getCurrentWeekInNumber();
					TimeTargetModelExample timeTargetExample = new TimeTargetModelExample();
					timeTargetExample.createCriteria().andCampaignIdEqualTo(campaignId)
						.andTimeLike(week + "%");
					List<TimeTargetModel> timeTargets = timeTargetDao.selectByExample(timeTargetExample);
					// 获取存在的日期定向
					List<String> weekHours = new ArrayList<String>();
					for (TimeTargetModel timeTarget : timeTargets) {
						weekHours.add(timeTarget.getTime());
					}
					
					int hourImpression = dailyImpression / adxes.size() / timeTargets.size();
					for (int i=0; i<24; i++) {
						String weekHour = week + String.format("%02d", i);
						if (weekHours.contains(weekHour)) {
							frequencyJsons.add(hourImpression);
						} else {
							frequencyJsons.add(0);
						}
					}
				}
			}
			
			for (Map<String, String> adx : adxes) {
				JsonObject groupObject = new JsonObject();
				groupObject.addProperty("adx", Integer.parseInt(adx.get("adxId")));
				if (frequencyJsons == null) {
					groupObject.addProperty("type", 0);
					groupObject.addProperty("period", 3);
				} else {
					groupObject.addProperty("type", 1);
					groupObject.addProperty("period", 2);
					groupObject.add("frequency", frequencyJsons);
				}
				groupJsons.add(groupObject);
			}
			resultJson.add("group", groupJsons);
		}
		String frequencyId = campaign.getFrequencyId();
		if (!StringUtils.isEmpty(frequencyId)) {
			JsonObject userJson = new JsonObject();
			FrequencyModel frequencyModel = frequencyDao.selectByPrimaryKey(frequencyId);
			if (frequencyModel != null) {
				String controlObj = frequencyModel.getControlObj();
				int number = frequencyModel.getNumber();
				String timeType = frequencyModel.getTimeType();
				if (CodeTableConstant.FREQUENCY_CONTROLOBJ_CREATIVE.equals(controlObj)) {
					userJson.addProperty("type", 2);
				} else if (CodeTableConstant.FREQUENCY_CONTROLOBJ_CAMPAIGN.equals(controlObj)) {
					userJson.addProperty("type", 1);
				}
				if (CodeTableConstant.FREQUENCY_TIMETYPE_HOUR.equals(timeType)) {
					userJson.addProperty("period", 2);
				} else if (CodeTableConstant.FREQUENCY_TIMETYPE_DAY.equals(timeType)) {
					userJson.addProperty("period", 3);
				}
				JsonArray cappingJsons = new JsonArray();
				JsonObject cappingJson = new JsonObject();
				cappingJson.addProperty("id", campaign.getId());
				cappingJsons.add(cappingJson);
				userJson.add("capping", cappingJsons);
				userJson.addProperty("frequency", number);
			}
			resultJson.add("user", userJson);
		}
		JedisUtils.set(RedisKeyConstant.CAMPAIGN_FREQUENCY + campaignId, resultJson.toString());
	}
	
	/**
	 * 频次信息移除redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void removeCampaignFrequency(String campaignId) throws Exception {
		JedisUtils.delete(RedisKeyConstant.CAMPAIGN_FREQUENCY + campaignId);
	}
	
	/**
	 * 活动下创意ID写入redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void writeCreativeId(String campaignId) throws Exception {
		//查询活动下创意
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> creatives = creativeDao.selectByExample(creativeExample);
		JsonArray creativeIdJsons = new JsonArray();
		JsonObject resultJson = new JsonObject();
		if (creatives != null && !creatives.isEmpty()) {
			for (CreativeModel creative : creatives) {
				String creativeId = creative.getId();
				creativeIdJsons.add(creativeId);
			}
		}
		resultJson.add("mapids", resultJson);
		JedisUtils.set(RedisKeyConstant.CAMPAIGN_MAPIDS + campaignId, resultJson.toString());
	}
	
	/**
	 * 活动下创意ID移除redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void removeCreativeId(String campaignId) throws Exception {
		JedisUtils.delete(RedisKeyConstant.CAMPAIGN_MAPIDS + campaignId);
	}
	
	
	/**
	 * 活动kpi上限写如redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void writeCampaignCounter(String campaignId) throws Exception {
		String key = RedisKeyConstant.CAMPAIGN_COUNTER + campaignId;
		Date current = new Date();
		QuantityModelExample example = new QuantityModelExample();
		example.createCriteria().andCampaignIdEqualTo(campaignId)
			.andStartDateLessThanOrEqualTo(current)
			.andEndDateGreaterThanOrEqualTo(current);
		List<QuantityModel> quantities = quantityDao.selectByExample(example);
		if (quantities !=null && !quantities.isEmpty()) {
			int dailyImpression = quantities.get(0).getDailyImpression();
			JedisUtils.set(key, dailyImpression);
		}
	}
	
	public void removeCampaignCounter(String campaignId) throws Exception {
		String key = RedisKeyConstant.CAMPAIGN_COUNTER + campaignId;
		if (JedisUtils.exists(key)) {
			JedisUtils.delete(key);
		}
	}
	
	/**
	 * 活动预算写入redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void writeCampaignBudget(CampaignModel campaign) throws Exception {
		String campaignId = campaign.getId();
		Date current = new Date();
		int totalBudget = campaign.getTotalBudget();
		String key = RedisKeyConstant.CAMPAIGN_BUDGET + campaignId;
		
		QuantityModelExample quantityExample = new QuantityModelExample();
		quantityExample.createCriteria().andCampaignIdEqualTo(campaignId)
			.andStartDateLessThanOrEqualTo(current)
			.andEndDateGreaterThanOrEqualTo(current);
		List<QuantityModel> quantities = quantityDao.selectByExample(quantityExample);
		if (quantities !=null && !quantities.isEmpty()) {
			int budget = quantities.get(0).getDailyBudget();
			Map<String, String> value = new HashMap<String, String>();
			value.put("total", String.valueOf(totalBudget * 100));
			value.put("daily", String.valueOf(budget * 100));
			JedisUtils.hset(key, value);
		}
	}
	
	public void removeCampaignBudget(String campaignId) throws Exception {
		String key = RedisKeyConstant.CAMPAIGN_BUDGET + campaignId;
		if (JedisUtils.exists(key)) {
			JedisUtils.delete(key);
		}
	}
	
	/**
	 * 将黑白名单写入redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void writeWhiteBlack(String campaignId) throws Exception {
		PopulationTargetModelExample populationTargetExample = new PopulationTargetModelExample();
		populationTargetExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<PopulationTargetModel> populationTargetModels = populationTargetDao.selectByExample(populationTargetExample);
		
		if (populationTargetModels != null && !populationTargetModels.isEmpty()) {
			String populationId = populationTargetModels.get(0).getPopulationId();
			PopulationModel populationModel = populationDao.selectByPrimaryKey(populationId);
			if (populationModel != null) {
				String path = populationModel.getPath();
				String type = populationModel.getType();
				File file = new File(POPULATION_ROOT_PATH + path);
				if (!file.exists()) {
					throw new ResourceNotFoundException(PhrasesConstant.WBLIST_FILE_NOT_FOUND);
				}
				List<String> lines = FileUtils.readLines(file);
				List<String> values = new ArrayList<String>();
				Map<String, List<String>> keyValues = new HashMap<String, List<String>>();
				JsonArray deviceIdJsons = new JsonArray();
				String key = null;
				for (int i = 0; i < lines.size(); i++) {
					String line = lines.get(i).trim();
					int left = line.indexOf("[");
					int right = line.indexOf("]");
					// 当前行内容为ID标识
					if (left > -1 && right > -1) {
						key = line.substring(left + 1, right);
						deviceIdJsons.add(deviceIdType.get(key.toLowerCase()));
						values.clear();
					} else {
						if (!StringUtils.isEmpty(line) && key != null) {
							values.add(line);
							keyValues.put(key, values);
						}
					}
				}
				
				JsonObject wblistJson = new JsonObject();
				wblistJson.addProperty("groupid", campaignId);
				wblistJson.addProperty("relationid", populationId);
				wblistJson.addProperty("ratio", 1);
				wblistJson.addProperty("mprice", 999);
				String keySuffix = null;
				if (CodeTableConstant.POPULATION_WHITE_LIST.equals(type)) {
					wblistJson.add("whitelist", deviceIdJsons);
					keySuffix = "_wl_" + populationId;
				}
				if (CodeTableConstant.POPULATION_BLACK_LIST.equals(type)) {
					wblistJson.add("blacklist", deviceIdJsons);
					keySuffix = "_bl_" + populationId;
				}
				
				if (keySuffix != null) {
					for (Entry<String, List<String>> entry : keyValues.entrySet()) {
						JedisUtils.sddKey(entry.getKey() + keySuffix, entry.getValue());
					}
					JedisUtils.set(RedisKeyConstant.CAMPAIGN_WBLIST + campaignId, wblistJson.toString());
				}
			}
		}
	}
	
	/**
	 * 将黑白名单移除redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void removeWhiteBlack(String campaignId) throws Exception {
		// 先删除以前的黑白名单
		String wbKey = RedisKeyConstant.CAMPAIGN_WBLIST + campaignId;
		if (JedisUtils.exists(wbKey)) {
			String wbStr = JedisUtils.getStr(RedisKeyConstant.CAMPAIGN_WBLIST + campaignId);
			JsonObject wbObj = (new JsonParser()).parse(wbStr).getAsJsonObject();
			String populationId = wbObj.get("relationid").getAsString();
			JedisUtils.deleteByPattern("*" + populationId);
			JedisUtils.delete(wbKey);
		}
	}
	
	public Boolean isFirstLaunch(String campaignId) throws Exception {
		return JedisUtils.exists(RedisKeyConstant.CAMPAIGN_MAPIDS + campaignId);
	}
}
