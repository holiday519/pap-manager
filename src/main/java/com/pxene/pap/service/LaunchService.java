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

import javax.annotation.PostConstruct;

import java.util.Set;

import org.apache.commons.io.FileUtils;
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
import com.pxene.pap.common.RedisHelper;
import com.pxene.pap.constant.CodeTableConstant;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.RedisKeyConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.RegionBean;
import com.pxene.pap.domain.models.AdvertiserAuditModel;
import com.pxene.pap.domain.models.AdvertiserAuditModelExample;
import com.pxene.pap.domain.models.AdvertiserModel;
import com.pxene.pap.domain.models.AdxModel;
import com.pxene.pap.domain.models.AdxTargetModel;
import com.pxene.pap.domain.models.AdxTargetModelExample;
import com.pxene.pap.domain.models.AppModel;
import com.pxene.pap.domain.models.AppModelExample;
import com.pxene.pap.domain.models.AppTargetDetailModel;
import com.pxene.pap.domain.models.AppTargetDetailModelExample;
import com.pxene.pap.domain.models.AppTargetModel;
import com.pxene.pap.domain.models.AppTargetModelExample;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignModelExample;
import com.pxene.pap.domain.models.CampaignTargetModel;
import com.pxene.pap.domain.models.CampaignTargetModelExample;
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
import com.pxene.pap.domain.models.RegionModel;
import com.pxene.pap.domain.models.RegionModelExample;
import com.pxene.pap.domain.models.TimeTargetModel;
import com.pxene.pap.domain.models.TimeTargetModelExample;

import com.pxene.pap.domain.models.view.CreativeImageModelExample;
import com.pxene.pap.domain.models.view.CreativeImageModelWithBLOBs;
import com.pxene.pap.domain.models.view.CreativeInfoflowModelExample;
import com.pxene.pap.domain.models.view.CreativeInfoflowModelWithBLOBs;
import com.pxene.pap.domain.models.view.CreativeVideoModelExample;
import com.pxene.pap.domain.models.view.CreativeVideoModelWithBLOBs;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.exception.ServerFailureException;
import com.pxene.pap.repository.basic.AdvertiserAuditDao;
import com.pxene.pap.repository.basic.AdvertiserDao;
import com.pxene.pap.repository.basic.AdxDao;
import com.pxene.pap.repository.basic.AdxTargetDao;
import com.pxene.pap.repository.basic.AppDao;
import com.pxene.pap.repository.basic.AppTargetDao;
import com.pxene.pap.repository.basic.AppTargetDetailDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CampaignTargetDao;
import com.pxene.pap.repository.basic.CreativeAuditDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.FrequencyDao;
import com.pxene.pap.repository.basic.ImageDao;
import com.pxene.pap.repository.basic.PopulationDao;
import com.pxene.pap.repository.basic.PopulationTargetDao;
import com.pxene.pap.repository.basic.ProjectDao;
import com.pxene.pap.repository.basic.QuantityDao;
import com.pxene.pap.repository.basic.RegionDao;
import com.pxene.pap.repository.basic.RegionTargetDao;
import com.pxene.pap.repository.basic.TimeTargetDao;

import com.pxene.pap.repository.basic.view.CreativeImageDao;
import com.pxene.pap.repository.basic.view.CreativeInfoflowDao;
import com.pxene.pap.repository.basic.view.CreativeVideoDao;
//import org.apache.log4j.Logger;  


import redis.clients.jedis.Jedis;


@Service
public class LaunchService extends BaseService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LaunchService.class);
	// 视图中的分隔符
	private static final String MONITOR_SEPARATOR = "\\|";
	
	private static String UPLOAD_MODE;
	private static String IMAGE_URL;
	private static String POPULATION_ROOT_PATH;
	
	@Autowired
	private RedisHelper redisHelper;
	
	@Autowired
	private RedisHelper redisHelper2;
	
	@Autowired
	public LaunchService(Environment env)
	{
		/**
		 * 获取图片上传路径
		 */
		UPLOAD_MODE = env.getProperty("pap.fileserver.mode", "local");
		if ("local".equals(UPLOAD_MODE))
        {
			IMAGE_URL = env.getProperty("pap.fileserver.local.url.prefix");
        }
        else
        {
        	IMAGE_URL = env.getProperty("pap.fileserver.remote.url.prefix");
        }
		POPULATION_ROOT_PATH = env.getProperty("pap.population.path");
	}
	

	@PostConstruct
	public void selectRedis()
	{
	    redisHelper.select("redis.primary.");
	    redisHelper2.select("redis.secondary.");
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
	
	@Autowired
	private RegionDao regionDao;
	
	@Autowired
	private AdxTargetDao adxTargetDao;
	
	@Autowired
	private AppTargetDetailDao appTargetDetailDao;
	
	@Autowired
	private AppService appService;
	
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
		// 有可投放的创意，则将创意id写入到mapids、将创意基本信息写到mapid中
		if (ishaveLaunchCreatives(campaignId)) {
			//写入活动下的创意基本信息   dsp_mapid_*
			writeCreativeInfo(campaignId);
			//写入活动下的创意ID dsp_group_mapids_*
			writeCreativeId(campaignId);
		}		
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
		//removeCampaignId(campaignId);
		//将不在满足条件的活动将其活动id从redis的groupids中删除--停止投放
		boolean removeResult = pauseCampaignRepeatable(campaignId);
		if (!removeResult) {
			throw new ServerFailureException(PhrasesConstant.REDIS_KEY_LOCK);
		}
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
		// 周日的零点
//		Calendar cal = Calendar.getInstance();
//		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;

		CampaignModelExample campaignExample = new CampaignModelExample();
		Date current = new Date();
		campaignExample.createCriteria().andStartDateLessThanOrEqualTo(current).andEndDateGreaterThanOrEqualTo(current);
		// 所有正在投放的活动：在投放时间的活动
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

			if (projectIds == null || projectIds.size() == 0) {
				throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
			}
			// 找出今天开始投放的活动，将投放的基本信息写入redis中
			campaignExample.clear();
			campaignExample.createCriteria().andProjectIdIn(projectIds)
					.andStatusEqualTo(StatusConstant.CAMPAIGN_PROCEED).andStartDateEqualTo(start);
			List<CampaignModel> addCampaigns = campaignDao.selectByExample(campaignExample);
			for (CampaignModel campaign : addCampaigns) {
				write4StartDate(campaign);
			}

			// 找出今天结束投放的活动，将基本信息和活动id等信息从redis中删除
			campaignExample.clear();
			campaignExample.createCriteria().andEndDateEqualTo(end);
			List<CampaignModel> delCampaigns = campaignDao.selectByExample(campaignExample);
			for (CampaignModel campaign : delCampaigns) {
				// 将开关关闭
				CampaignModel campaignModel = new CampaignModel();
				campaignModel.setId(campaign.getId());
				campaignModel.setStatus(StatusConstant.CAMPAIGN_PAUSE);
				campaignDao.updateByPrimaryKeySelective(campaignModel);
				// 删除redis的信息
				remove4EndDate(campaign);
			}

			// 预算重新写入（每天需要写入当天的日预算和日均展现）
			for (CampaignModel campaign : launchCampaigns) {
				String campaignId = campaign.getId();
				writeCampaignBudget(campaign);
				writeCampaignCounter(campaignId);
				//写入活动频次信息   dsp_groupid_frequencycapping_*
				writeCampaignFrequency(campaign);
			}

			// 每天晚上检查groupids中是否存在已经过期的campaignId，如果有则删除
			// 1.获取redis中的Groupids
			String strGroupids = redisHelper.getStr(RedisKeyConstant.CAMPAIGN_IDS);
			if (strGroupids == null || strGroupids.isEmpty()) {
				throw new ServerFailureException(PhrasesConstant.REDIS_GROUPIDS_NULL);
			}
			// 2.将gson字符串转成JsonObject对象
			JsonObject returnData = parser.parse(strGroupids).getAsJsonObject();
			// 3.将data节点下的内容转为JsonArray
			JsonArray jsonArray = returnData.getAsJsonArray("groupids");
			// 4.判断每个groupid是否过期
			for (int i = 0; i < jsonArray.size(); i++) {
				// 获取第i个数组元素
				JsonElement elemGroupid = jsonArray.get(i);
				String strGroupid = elemGroupid.getAsString();
				// 查询相关的活动信息
				CampaignModel oldCampaigns = campaignDao.selectByPrimaryKey(strGroupid);
				if(oldCampaigns !=null) {
					Date end_date = oldCampaigns.getEndDate(); // 活动的结束时间
					if (end_date.before(current)) {
						// 如果活动的结束时间在今天之前则将其活动id从redis的groupids中删除--停止投放
						boolean removeResult = pauseCampaignRepeatable(strGroupid);
						if (!removeResult) {
							throw new ServerFailureException(PhrasesConstant.REDIS_KEY_LOCK);
						}
					}
				}
			}
		}
		// 每个小时判断时间定向，将不在该时间内的活动移除
		for (CampaignModel campaign : launchCampaigns) {
			String campaignId = campaign.getId();
			// 投放需满足项目开启、活动开启、在活动时间范围里、当前时间在定向时间内，活动没有超出每天的日预算并且日均最大展现未达到上限，项目预算没有达到上限
			String projectId = campaign.getProjectId();
			ProjectModel project = projectDao.selectByPrimaryKey(projectId);
			if(project == null){
				continue;
			}
			if (campaignService.isOnTargetTime(campaignId)) {
				// 活动在定向时间
				if (StatusConstant.PROJECT_PROCEED.equals(project.getStatus())
						&& StatusConstant.CAMPAIGN_PROCEED.equals(campaign.getStatus()) 
						&& isWriteGroupIds(projectId,campaignId)) {
					boolean writeResult = launchCampaignRepeatable(campaignId);
					if (!writeResult) {
						throw new ServerFailureException(PhrasesConstant.REDIS_KEY_LOCK);
					}
				}				
			} else {
				// 将不在满足条件的活动将其活动id从redis的groupids中删除--停止投放
				boolean removeResult = pauseCampaignRepeatable(campaignId);
				if (!removeResult) {
					throw new ServerFailureException(PhrasesConstant.REDIS_KEY_LOCK);
				}
			}
		}

		// 每周日零点再删除一次redis中过期的投放信息，防止每天零点删除遗漏
//		if (week == 0 && "00".equals(currentHour)) { // 0代表周日，6代表周六
//			// 查询redis中活动基本信息的keys
//			String[] strCampaignInfo = redisHelper.getKeys(RedisKeyConstant.CAMPAIGN_INFO + "*");
//			if (strCampaignInfo == null) {
//				throw new ServerFailureException(PhrasesConstant.REDIS_CAMPAIGNINFO_NULL);
//			}
//			for (String strCampaign : strCampaignInfo) {
//				String campaignid = strCampaign.substring(17); // 截取前缀，获取36位UUID
//				// 通过该id到数据库中查询相关的活动信息
//				CampaignModel oldCampaigns = campaignDao.selectByPrimaryKey(campaignid);
//				Date end_date = oldCampaigns.getEndDate(); // 活动的结束时间
//				if (end_date.before(current)) {
//					// 如果活动的结束时间在今天之前则将其投放的基本信息从redis中删除
//					remove4EndDate(oldCampaigns);
//				}
//			}
//		}
	}
	
	/*******************************************************************************************************/
	/**
	 * 将活动ID 写入redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void writeCampaignId(String campaignId) throws Exception {
		String idStr = redisHelper.getStr(RedisKeyConstant.CAMPAIGN_IDS);
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
		redisHelper.set(RedisKeyConstant.CAMPAIGN_IDS, idObj.toString());
	}
	
	/**
     * 启动指定的活动（重复指定的次数）
     * @param campaignId   需要开启的活动ID
     * @return
     */
    public boolean launchCampaignRepeatable(String campaignId) throws Exception
    {
        int i = 1;
        int total = 10;
        Jedis jedis = null;
        
        while (i <= total)
        {
            jedis = redisHelper.getJedis();
            
            jedis.watch(RedisKeyConstant.CAMPAIGN_IDS);
            
            // 读取key为pap_groupids的value，即当前全部可投放的活动ID集合
            String idStr = jedis.get(RedisKeyConstant.CAMPAIGN_IDS);
            
            JsonObject idObj = null;
            if (idStr == null)
            {
                idObj = new JsonObject();
                JsonArray idArray = new JsonArray();
                idArray.add(campaignId);
                idObj.add("groupids", idArray);
            }
            else
            {
                idObj = gson.fromJson(idStr, new JsonObject().getClass());
                JsonArray idArray = idObj.get("groupids").getAsJsonArray();
                if (!idArray.contains(parser.parse(campaignId)))
                {
                    idArray.add(campaignId);
                    idObj.add("groupids", idArray);
                }
            }
            
            if (idObj != null)
            {
                boolean casFlag = redisHelper.doTransaction(jedis, RedisKeyConstant.CAMPAIGN_IDS, idObj.toString());
                
                if (casFlag)
                {
                    return true;
                }
            }
            
            i++;
        }
        
        return false;
    }

    /**
     * 暂停指定的活动（重复指定的次数）
     * @param campaignId   需要暂停的活动ID
     * @return
     */
	public boolean pauseCampaignRepeatable(String campaignId) {
		int i = 1;
		int total = 10;
		Jedis jedis = null;

		while (i <= total) {
			jedis = redisHelper.getJedis();

			jedis.watch(RedisKeyConstant.CAMPAIGN_IDS);

			// 读取key为pap_groupids的value，即当前全部可投放的活动ID集合
			String availableGroups = jedis.get(RedisKeyConstant.CAMPAIGN_IDS);
			// 从JSON字符串中删除指定的活动ID
			String operatedVal = removeCampaignId(availableGroups, campaignId);
			if (operatedVal != null) {
				boolean casFlag = redisHelper.doTransaction(jedis, RedisKeyConstant.CAMPAIGN_IDS, operatedVal);
				if (casFlag) {
					return true;
				}
			}
			i++;
		}

		return false;
	}

    /**
	 * 将活动ID 移除redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void removeCampaignId(String campaignId) throws Exception {
		String idStr = redisHelper.getStr(RedisKeyConstant.CAMPAIGN_IDS);
		if (idStr != null) {
			JsonObject idObj = gson.fromJson(idStr, new JsonObject().getClass());
			JsonArray idArray = idObj.get("groupids").getAsJsonArray();
			JsonElement id = parser.parse(campaignId);
			if (idArray.contains(id)) {
				idArray.remove(id);
			}
			idObj.add("groupids", idArray);
			redisHelper.set(RedisKeyConstant.CAMPAIGN_IDS, idObj.toString());
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
				if (CodeTableConstant.CREATIVE_TYPE_IMAGE.equals(creativeType)) {
					writeImgCreativeInfo(creative);
				// 视频创意
				} else if (CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(creativeType)) {
					writeVideoCreativeInfo(creative);
				// 信息流创意
				} else if (CodeTableConstant.CREATIVE_TYPE_INFOFLOW.equals(creativeType)) {
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
			    redisHelper.delete(RedisKeyConstant.CREATIVE_INFO + creative.getId());
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
				// 添加项目id字段
				creativeObj.addProperty("projectid", model.getProjectId());
				creativeObj.addProperty("mapid", model.getCreativeId());
				creativeObj.addProperty("groupid", model.getCampaignId());
				creativeObj.addProperty("type", 2);
				if (model.getFormat() != null) {
					creativeObj.addProperty("ftype", Integer.parseInt(model.getFormat()));
				}
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
	            creativeObj.addProperty("sourceurl", IMAGE_URL + model.getPath());
	            creativeObj.addProperty("cid", GlobalUtil.parseString(model.getProjectId(), ""));
				
	            redisHelper.set(RedisKeyConstant.CREATIVE_INFO + creativeId, creativeObj.toString());
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
				// 添加项目id字段
				creativeObj.addProperty("projectid", model.getProjectId());
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
	            creativeObj.addProperty("sourceurl", IMAGE_URL + model.getPath());
	            creativeObj.addProperty("cid", GlobalUtil.parseString(model.getProjectId(), ""));
	            
	            redisHelper.set(RedisKeyConstant.CREATIVE_INFO + creativeId, creativeObj.toString());
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
				// 添加项目id字段
				creativeObj.addProperty("projectid", model.getProjectId());
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
						icon.addProperty("sourceurl", IMAGE_URL + image.getPath());
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
					if(image!=null) {
						creativeObj.addProperty("w", GlobalUtil.parseInt(image.getWidth(), 0));
						creativeObj.addProperty("h", GlobalUtil.parseInt(image.getHeight(), 0));
						creativeObj.addProperty("ftype", image.getFormat());
						creativeObj.addProperty("sourceurl", IMAGE_URL + image.getPath());
					}
				} else if (imageIds.size() > 1) {
					JsonArray imageJsons = new JsonArray(); 
					for (String imageId : imageIds) {
						ImageModel image = imageDao.selectByPrimaryKey(imageId);
						if(image!=null) {
							JsonObject imageJson = new JsonObject();
							imageJson.addProperty("w", GlobalUtil.parseInt(image.getWidth(), 0));
							imageJson.addProperty("h", GlobalUtil.parseInt(image.getHeight(), 0));
							imageJson.addProperty("ftype", Integer.parseInt(image.getFormat()));
							imageJson.addProperty("sourceurl", IMAGE_URL + image.getPath());
							imageJsons.add(imageJson);
						}
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
	            redisHelper.set(RedisKeyConstant.CREATIVE_INFO + creativeId, creativeObj.toString());
			}
		}
	}
	
	/**
	 * 通过创意获取ADX信息
	 * @param creative
	 * @return
	 */
	public List<Map<String, String>> getAdxByCreative(CreativeModel creative) {		
		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
		String campaignId = creative.getCampaignId();
		String creativeId = creative.getId();
		CampaignModel campaign = campaignDao.selectByPrimaryKey(campaignId);
		List<Map<String, String>> adxes = getAdxByCampaign(campaign);
		// 一个活动只有一个ADX
		String adxId = adxes.get(0).get("adxId");
		String adxName = adxes.get(0).get("adxName");
		Map<String, String> result = new HashMap<String, String>();
		result.put("adxId", adxId);
		result.put("adxName", adxName);
		CreativeAuditModelExample example = new CreativeAuditModelExample();
		example.createCriteria().andAdxIdEqualTo(adxId).andCreativeIdEqualTo(creativeId);
		List<CreativeAuditModel> audits = creativeAuditDao.selectByExample(example);
		if (audits != null && !audits.isEmpty()) {
			CreativeAuditModel audit = audits.get(0);
			String auditValue = audit.getAuditValue();
			//判断审核值是否为空
			if(auditValue !=null && !auditValue.isEmpty()) {
				result.put("creativeAudit", auditValue);
			}
		}
		results.add(result);
		return results;
	}
	
	/**
	 * 通过活动获取ADX信息
	 * @param campaign
	 * @return
	 */
	private List<Map<String, String>> getAdxByCampaign(CampaignModel campaign) {
		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
		String campaignId = campaign.getId();
		String projectId = campaign.getProjectId();
		//查询广告主ID
		ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
		String advertiserId = projectModel.getAdvertiserId();
		
		AdxTargetModelExample adxTargetExample = new AdxTargetModelExample();
		adxTargetExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<AdxTargetModel> adxTargets = adxTargetDao.selectByExample(adxTargetExample);
		if (adxTargets != null && !adxTargets.isEmpty()) {
			// 一个活动对应一个ADX
			String adxId = adxTargets.get(0).getAdxId();
			AdxModel adx = adxDao.selectByPrimaryKey(adxId);
			String adxName = adx.getName();
			Map<String, String> result = new HashMap<String, String>();
			result.put("adxId", adxId);
			result.put("adxName", adxName);
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
		return results;
	}
	
	/**
	 * 将活动基本信息写入redis
	 * @param campaign
	 * @throws Exception
	 */
	public void writeCampaignInfo(CampaignModel campaign) throws Exception {
		String projectId = campaign.getProjectId();
		ProjectModel project = projectDao.selectByPrimaryKey(projectId);
		String advertiserId = project.getAdvertiserId();
		AdvertiserModel advertiser = advertiserDao.selectByPrimaryKey(advertiserId);
		String campaignId = campaign.getId();
		
		JsonObject campaignJson = new JsonObject();
		String industryId = advertiser.getIndustryId();
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
				String auditValue = adx.get("advertiserAudit");
				if(auditValue!=null && !auditValue.isEmpty()) {
					extJson.addProperty("advid", auditValue);
				}
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
		
		redisHelper.set(RedisKeyConstant.CAMPAIGN_INFO + campaignId, campaignJson.toString());
	}
	
	/**
	 * 将活动基本信息移除redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void removeCampaignInfo(String campaignId) throws Exception {
	    redisHelper.delete(RedisKeyConstant.CAMPAIGN_INFO + campaignId);
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
			JsonArray network = targetStr2Jarr(model.getNetwork());
			JsonArray os = targetStr2Jarr(model.getOs());
			JsonArray operator = targetStr2Jarr(model.getOperator());
			JsonArray device = targetStr2Jarr(model.getDevice());
			JsonArray brand = targetStr2Jarr(model.getBrandId());
			
			// 地域定向：redis中写的是市id，如果选项的是省，找到省下的市id写入
			JsonArray region = new JsonArray();
			String regionsModel = model.getRegionId();
			String[] regionIds = null;
			if (regionsModel != null && !regionsModel.isEmpty()) {
				regionIds = model.getRegionId().split(",");
			}			
			if (regionIds != null && regionIds.length > 0) {				
				// 查询所有市的id
				RegionModelExample regionExample = new RegionModelExample();
				List<RegionModel> regions = regionDao.selectByExample(regionExample);
				List<String> cityIds = new ArrayList<String>();
				for (RegionModel regionModel : regions) {
					String regionIdInDB = regionModel.getId();
					if(!"000000".equals(regionIdInDB)) {
						if (!regionIdInDB.endsWith("0000")) {
							// 不以“0000”结尾的市
							cityIds.add(regionIdInDB);
						}
					}
				}
				// 判断是省还是市
				for (String regionId : regionIds) {
					if(!"000000".equals(regionId)) {
						// 如果不是未知
						String provinceStr = regionId.substring(0,2);
						if (regionId.endsWith("0000")) {
							if (provinceStr.equals("11") || provinceStr.equals("12")
									|| provinceStr.equals("31") || provinceStr.equals("50")) {
								region.add(regionId);
							} else {
								// 以“0000”结尾的并且不是直辖市的是省，将省下的市id添加到region中
								for (String cityId : cityIds) {
									String pId = regionId.substring(0, 4) + "00";
									String cId = cityId.substring(0, 2) + "0000";
									if (pId.equals(cId)) {
										// 如果市属于定向的省，则将市添加
										region.add(cityId);
									}
								}
							}
						} else {
							// 除了省份就是城市
							region.add(regionId);
						}
					}
				}
			}
						
			if (region != null && region.size() > 0) {
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
			// 获取ADXId
			AdxTargetModelExample adxTargetEx = new AdxTargetModelExample();
			adxTargetEx.createCriteria().andCampaignIdEqualTo(campaignId);
			List<AdxTargetModel> adxTargets = adxTargetDao.selectByExample(adxTargetEx);
			AdxTargetModel adxTarget = adxTargets.get(0); // 一个活动对应一个ADX 
			String adx = adxTarget.getAdxId();
			if (adx == null || adx.isEmpty()) {
				throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
			}
			// 获取App定向id
			AppTargetModelExample appTargetEx = new AppTargetModelExample();
			appTargetEx.createCriteria().andCampaignIdEqualTo(campaignId);
			List<AppTargetModel> appTargets = appTargetDao.selectByExample(appTargetEx);
			AppTargetModel appTarget = appTargets.get(0); // 一个活动对应一条App定向信息
			// 获取App定向详情信息
			AppTargetDetailModelExample adxTargetDetailEx = new AppTargetDetailModelExample();
			adxTargetDetailEx.createCriteria().andApptargetIdEqualTo(appTarget.getId());			
			List<AppTargetDetailModel> appTargetDetails = appTargetDetailDao.selectByExample(adxTargetDetailEx);
			// 查询App定向信息
			List<AppModel> apps = appService.listAppsByAppTargetDetail(appTargetDetails, adx);
			JsonArray appJsons = new JsonArray();
			Map<String, JsonObject> adxMap = new HashMap<String, JsonObject>();
			JsonObject appObjects = new JsonObject();
			for (AppModel app : apps) {
				String adxId = app.getAdxId();
				if (adxMap.containsKey(adxId)) {
					JsonObject adxJson = adxMap.get(adxId);
					JsonArray appIdJsons = adxJson.get("wlist").getAsJsonArray();
					appIdJsons.add(app.getId());
					adxJson.add("wlist", appIdJsons);
					adxMap.put(adxId, adxJson);
				} else {
					JsonObject adxJson = new JsonObject();
					adxJson.addProperty("adx", Integer.parseInt(adxId));
					adxJson.addProperty("flag", 1);
					JsonArray appIdJsons = new JsonArray();
					appIdJsons.add(app.getId());
					adxJson.add("wlist", appIdJsons);
					adxMap.put(adxId, adxJson);
				}
			}
			for (Entry<String, JsonObject> entry : adxMap.entrySet()) {
				appJsons.add(entry.getValue());
			}
			appObjects.add("id", appJsons);
			targetJson.add("app", appObjects);
			
			redisHelper.set(RedisKeyConstant.CAMPAIGN_TARGET + campaignId, targetJson.toString());
		}
	}
	
	/**
	 * 活动定向移除redis
	 * @param campaignId
	 * @param adType 
	 * @throws Exception
	 */
	public void removeCampaignTarget(String campaignId) throws Exception {
	    redisHelper.delete(RedisKeyConstant.CAMPAIGN_TARGET + campaignId);
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
			// 如果adx信息不为空
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
					// 每小时投放的数量 = 每日最大展现数 / adx的个数 / 时间定向（有几个时间段）
					int hourImpression = 0;
					if(adxes.size() != 0 && timeTargets != null && timeTargets.size() != 0) {
						hourImpression = dailyImpression / adxes.size() / timeTargets.size();
					}
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
			// 是否匀速
			for (Map<String, String> adx : adxes) {
				JsonObject groupObject = new JsonObject();
				groupObject.addProperty("adx", Integer.parseInt(adx.get("adxId")));
				if (frequencyJsons == null) {
					// 不匀速
					groupObject.addProperty("type", 0);
					groupObject.addProperty("period", 3);
				} else {
					// 匀速
					groupObject.addProperty("type", 1);
					groupObject.addProperty("period", 2);
					groupObject.add("frequency", frequencyJsons);
				}
				groupJsons.add(groupObject);
			}
			resultJson.add("group", groupJsons);
		}
		//设置频次
		String frequencyId = campaign.getFrequencyId();
		if (!StringUtils.isEmpty(frequencyId)) {
			// 如果频次信息不为空，则添加频次信息
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
				cappingJson.addProperty("frequency", number);
				cappingJsons.add(cappingJson);
				userJson.add("capping", cappingJsons);
			}
			resultJson.add("user", userJson);
		}
		redisHelper.set(RedisKeyConstant.CAMPAIGN_FREQUENCY + campaignId, resultJson.toString());
	}
	
	/**
	 * 频次信息移除redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void removeCampaignFrequency(String campaignId) throws Exception {
	    redisHelper.delete(RedisKeyConstant.CAMPAIGN_FREQUENCY + campaignId);
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
		// 活动下创意ID写入redis
		JsonArray creativeIdJsons = new JsonArray();
		JsonObject resultJson = new JsonObject();
		if (creatives != null && !creatives.isEmpty()) {
			// 如果创意信息不为空
			for (CreativeModel creative : creatives) {
				String creativeId = creative.getId();
				// 查询创意的审核信息
				CreativeAuditModelExample creativeAuditExample = new CreativeAuditModelExample();
				creativeAuditExample.createCriteria().andCreativeIdEqualTo(creativeId);
				List<CreativeAuditModel> creativeAudits = creativeAuditDao.selectByExample(creativeAuditExample);
				// 获取创意的审核状态
				if (!creativeAudits.isEmpty()) {
					String status = creativeAudits.get(0).getStatus();
					// 创意开关是打开，并且创意审核通过
					if (StatusConstant.CREATIVE_IS_ENABLE.equals(creative.getEnable()) 
							&& StatusConstant.CREATIVE_AUDIT_SUCCESS.equals(status)) {
						creativeIdJsons.add(creativeId);
					}
				}
			}
		}
		resultJson.add("mapids", creativeIdJsons);
		redisHelper.set(RedisKeyConstant.CAMPAIGN_CREATIVEIDS + campaignId, resultJson.toString());
	}
	
	/**
	 * 活动下创意ID移除redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void removeCreativeId(String campaignId) throws Exception {
	    redisHelper.delete(RedisKeyConstant.CAMPAIGN_CREATIVEIDS + campaignId);
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
		if (quantities != null && !quantities.isEmpty()) {
			for (QuantityModel quan : quantities) {
				Date startDate = quan.getStartDate();
				Date endDate = quan.getEndDate();
				if (DateUtils.isBetweenDates(current, startDate, endDate)) {
					Integer impression = quan.getDailyImpression();
					if (impression != null) {
						redisHelper.set(key, (int)impression);
					}
					break;
				}
			}
		}
	}
	
	public void removeCampaignCounter(String campaignId) throws Exception {
		String key = RedisKeyConstant.CAMPAIGN_COUNTER + campaignId;
		if (redisHelper.exists(key)) {
		    redisHelper.delete(key);
		}
	}
	
	/**
	 * 活动预算写入redis
	 * @param campaign
	 * @throws Exception
	 */
	public void writeCampaignBudget(CampaignModel campaign) throws Exception {
		String campaignId = campaign.getId();
		Date current = new Date();
		String key = RedisKeyConstant.CAMPAIGN_BUDGET + campaignId;
		
		QuantityModelExample quantityExample = new QuantityModelExample();
		quantityExample.createCriteria().andCampaignIdEqualTo(campaignId)
			.andStartDateLessThanOrEqualTo(current)
			.andEndDateGreaterThanOrEqualTo(current);
		List<QuantityModel> quantities = quantityDao.selectByExample(quantityExample);
		if (quantities != null && !quantities.isEmpty()) {
			for (QuantityModel quan : quantities) {
				Date startDate = quan.getStartDate();
				Date endDate = quan.getEndDate();
				if (DateUtils.isBetweenDates(current, startDate, endDate)) {
					Integer budget = quan.getDailyBudget();
					if (budget != null) {
						redisHelper.set(key, (int)budget * 100);
					}
					break;
				}
			}
		}
	}
	
	public void removeCampaignBudget(String campaignId) throws Exception {
		String key = RedisKeyConstant.CAMPAIGN_BUDGET + campaignId;
		if (redisHelper.exists(key)) {
			redisHelper.delete(key);
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
			PopulationTargetModel populationTargetModel = populationTargetModels.get(0);
			String targetId = populationTargetModel.getId();
            String populationId = populationTargetModel.getPopulationId();
			String type = populationTargetModel.getType();
			
			PopulationModel populationModel = populationDao.selectByPrimaryKey(populationId);
			if (populationModel != null) {
//				String path = populationModel.getPath();
//				String type = populationModel.getType();
				File file = new File(POPULATION_ROOT_PATH + populationId + ".txt");
				if (!file.exists()) {					
					throw new ResourceNotFoundException(PhrasesConstant.WBLIST_FILE_NOT_FOUND);
				}
				List<String> lines = FileUtils.readLines(file);
				List<String> values = null;
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
						Integer dType = deviceIdType.get(key.toLowerCase());
						if (dType != null) {
							deviceIdJsons.add(dType);
						}
						values = new ArrayList<String>();
					} else {
						if (!StringUtils.isEmpty(line) && key != null && values != null) {
							values.add(line);
							keyValues.put(key, values);
						}
					}
				}
				
				JsonObject wblistJson = new JsonObject();
				wblistJson.addProperty("groupid", campaignId);
				wblistJson.addProperty("relationid", targetId);
				wblistJson.addProperty("ratio", 1);
				wblistJson.addProperty("mprice", 999);
				String keySuffix = null;
				if (CodeTableConstant.POPULATION_WHITE_LIST.equals(type)) {
					wblistJson.add("whitelist", deviceIdJsons);
					keySuffix = "_wl_" + targetId;
				}
				if (CodeTableConstant.POPULATION_BLACK_LIST.equals(type)) {
					wblistJson.add("blacklist", deviceIdJsons);
					keySuffix = "_bl_" + targetId;
				}
				
				if (keySuffix != null) {
					for (Entry<String, List<String>> entry : keyValues.entrySet()) {
					    redisHelper2.sddKey(entry.getKey() + keySuffix, entry.getValue());
					}
					redisHelper.set(RedisKeyConstant.CAMPAIGN_WBLIST + campaignId, wblistJson.toString());
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
		if (redisHelper.exists(wbKey)) {
			String wbStr = redisHelper.getStr(RedisKeyConstant.CAMPAIGN_WBLIST + campaignId);
			JsonObject wbObj = parser.parse(wbStr).getAsJsonObject();
			String relationId = wbObj.get("relationid").getAsString();
			redisHelper2.deleteByPattern("*" + relationId);
			redisHelper.delete(wbKey);
		}
	}
	
	/**
	 * 判断活动是不是已经投放了
	 * @param campaignId 活动id
	 * @return
	 * @throws Exception
	 */
	public Boolean isHaveLaunched(String campaignId) throws Exception {
		// return JedisUtils.exists(RedisKeyConstant.CAMPAIGN_MAPIDS + campaignId);
		// return redisHelper.exists(RedisKeyConstant.CAMPAIGN_MAPIDS + campaignId);		
		// 判断活动下创意是否在redis中
//		Boolean isHaveMapids = redisHelper.exists(RedisKeyConstant.CAMPAIGN_CREATIVEIDS + campaignId);
		// 判断活动基本信息是否在redis中
		Boolean isHaveInfo = redisHelper.exists(RedisKeyConstant.CAMPAIGN_INFO + campaignId);
		// 判断活动定向是否在redis中
		Boolean isHaveTarget = redisHelper.exists(RedisKeyConstant.CAMPAIGN_TARGET + campaignId);
		// 判断活动频次信息是否在redis中  
		Boolean isHaveFrequency = redisHelper.exists(RedisKeyConstant.CAMPAIGN_FREQUENCY + campaignId);		
		// 判断活动预算是否在redis中
//		Boolean isHaveBudget = redisHelper.exists(RedisKeyConstant.CAMPAIGN_BUDGET  + campaignId);
		// 判断日均展现是否在redis中
//		Boolean isHaveCounter = redisHelper.exists(RedisKeyConstant.CAMPAIGN_COUNTER   + campaignId);
		if (isHaveInfo && isHaveTarget && isHaveFrequency) {
			return true;	
		} else {
			return false;	
		}			
	}
	
	
	/**
     * 从正在投放的活动中删除指定的活动ID
     * @param redisValue    Redis中Key为dsp_groups的Value
     * @param campaignId    欲删除的活动ID
     * @return
     */
	private String removeCampaignId(String redisValue, String campaignId) {
		JsonObject tmpObj = parser.parse(redisValue).getAsJsonObject();
		if (tmpObj != null) {
			JsonArray groudids = tmpObj.get("groupids").getAsJsonArray();

			// 判断是否已经含有当前campaignID
			for (int i = 0; i < groudids.size(); i++) {
				if (campaignId.equals(groudids.get(i).getAsString())) {
					groudids.remove(i);
					break;
				}
			}

			return tmpObj.toString();
		}
		return null;
	}

	/**
	 * 判断活动是否超出每天的日预算
	 * 
	 * @param campaignId
	 *            活动id
	 * @throws Exception
	 */
	public Boolean notOverDailyBudget(String campaignId) {
		// 获取redis中日预算
		String redisKey = RedisKeyConstant.CAMPAIGN_BUDGET + campaignId;
		if (redisHelper.exists(redisKey)) {
			// 转换类型
			double dayJudge = redisHelper.getDouble(redisKey);
			// 判断是否超出日预算
			if (dayJudge > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}

	}

	/**
	 * 日均最大展现是否达到上限
	 * 
	 * @param campaignId
	 */
	public Boolean notOverDailyCounter(String campaignId) {

		// 获取redis中日均最大展现数
		String dailyCounter = redisHelper
				.getStr(RedisKeyConstant.CAMPAIGN_COUNTER + campaignId);
		if (!StringUtils.isEmpty(dailyCounter)) {
			// 转换类型
			int dayCounter = Integer.parseInt(dailyCounter);
			// 判断是否超出日均最大展现数
			if (dayCounter > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	/**
	 * 更新创意价格
	 * 
	 * @param creative
	 * @throws Exception
	 */
	public void updateCreativePrice(String id) throws Exception {
		// 查询创意
		CreativeModel creative = creativeDao.selectByPrimaryKey(id);
		String creativeId = creative.getId();
		String creativeMapid = redisHelper.getStr(RedisKeyConstant.CREATIVE_INFO + creativeId);
		if (creativeMapid != null && !creativeMapid.isEmpty()) {
			JsonObject returnData = parser.parse(creativeMapid).getAsJsonObject();
			JsonArray jsonArray = returnData.get("price_adx").getAsJsonArray();
			for (int i = 0; i < jsonArray.size(); i++) {
				JsonObject price = jsonArray.get(i).getAsJsonObject();
				price.addProperty("price", creative.getPrice());
			}
			redisHelper.set(RedisKeyConstant.CREATIVE_INFO + creativeId,returnData.toString());
		}		
	}

	/**
	 * 判断是否超出项目总预算
	 * 
	 * @param campaignId
	 * @return
	 */
	public Boolean notOverProjectBudget(String projectId) {
		// 获取redis中项目预算
		String redisKey = RedisKeyConstant.PROJECT_BUDGET + projectId;
		if (redisHelper.exists(redisKey)) {
			// 转换类型
			double projectBudget = redisHelper.getDouble(redisKey);
			if (projectBudget > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 从redis中删除活动下一个创意id
	 * 
	 * @param campaignId
	 * @throws Exception
	 */
	public void removeOneCreativeId(String campaignId, String creativeId) throws Exception {
		String mapids = redisHelper.getStr(RedisKeyConstant.CAMPAIGN_CREATIVEIDS + campaignId);
		if (mapids != null && !mapids.isEmpty()) {
			// 将gson字符串转成JsonObject对象
			JsonObject returnData = parser.parse(mapids).getAsJsonObject();
			// 将data节点下的内容转为JsonArray
			JsonArray jsonArray = returnData.getAsJsonArray("mapids");
			// 删除单个创意id
			// 转换格式
			JsonElement elementCreativeId = parser.parse(creativeId);
			if (jsonArray.contains(elementCreativeId)) {
				// 如果包含这个元素则将这个元素删除
				jsonArray.remove(elementCreativeId);
			}

			// 将删除后剩下的元素再放回redis中
			JsonObject resultJson = new JsonObject();
			resultJson.add("mapids", jsonArray);
			redisHelper.set(RedisKeyConstant.CAMPAIGN_CREATIVEIDS + campaignId, resultJson.toString());
		}
	}

	/**
	 * 向redis写入单个创意id（审核通过，则将创意id写入门到redis的mapids中）
	 * 
	 * @param campaignId 活动id
	 * @param creativeId 创意id
	 * @throws Exception
	 */
	public void writeOneCreativeId(String campaignId, String creativeId) throws Exception {
		String mapids = redisHelper.getStr(RedisKeyConstant.CAMPAIGN_CREATIVEIDS + campaignId);
		if (mapids != null && !mapids.isEmpty()) {
			// 判断redis的mapids中是否存在创意id，不存在则将其写入
			// 1.将gson字符串转换成JsonObject对象
			JsonObject mapidsObject = parser.parse(mapids).getAsJsonObject();
			// 2.将mapidsObject节点下的内容转为JsonArray
			JsonArray mapidsArray = mapidsObject.getAsJsonArray("mapids");
			// 3.添加单个创意id
			// 转换格式
			JsonElement elementCreativeId = parser.parse(creativeId);
			if (!mapidsArray.contains(elementCreativeId)) {
				// 如果不包含这个创意id，则将其添加
				mapidsArray.add(creativeId);
			}
			// 将添加新创意id后的所有元素放回redis中
			JsonObject resultJson = new JsonObject();
			resultJson.add("mapids", mapidsArray);
			redisHelper.set(RedisKeyConstant.CAMPAIGN_CREATIVEIDS + campaignId, resultJson.toString());
		} else {
			//写入活动下的创意ID dsp_group_mapids_*
			writeCreativeId(campaignId);
		}
	}

	/**
	 * 判断该创意ID是否存在回收数据
	 * 
	 * @param creativeId
	 * @return
	 * @throws Exception
	 */
	public Boolean isHaveCreativeInfo(String creativeId) throws Exception {
		return redisHelper.exists("creativeDataHour_" + creativeId) 
				|| redisHelper.exists("creativeDataDay_" + creativeId);
	}

	public void writeCampaignId4Project(String campaignId, String projectId) {
		redisHelper.sset(RedisKeyConstant.PROJECT_CAMPAIGNIDS + projectId,
				campaignId);
	}

	public void removeCampaignId4Project(String campaignId, String projectId) {
		redisHelper.sdelete(RedisKeyConstant.PROJECT_CAMPAIGNIDS + projectId,
				campaignId);
	}

	public void removeProjectKey(String projectId) {
		redisHelper.delete(RedisKeyConstant.PROJECT_CAMPAIGNIDS + projectId);
	}

	/**
	 * 判断是否有可投放创意
	 * @param campaignId
	 * @return
	 */
	public boolean ishaveLaunchCreatives(String campaignId) throws Exception {
		CreativeModelExample ex = new CreativeModelExample();
		ex.createCriteria().andCampaignIdEqualTo(campaignId).andEnableEqualTo(StatusConstant.CREATIVE_IS_ENABLE);
		List<CreativeModel> creatives = creativeDao.selectByExample(ex);
		List <CreativeAuditModel> creativeAudits = null;
		if (creatives != null && !creatives.isEmpty()) {
			for (CreativeModel creative : creatives) {
				String creativeId = creative.getId();
				CreativeAuditModelExample creativeAuditEx = new CreativeAuditModelExample();
				creativeAuditEx.createCriteria().andCreativeIdEqualTo(creativeId).andStatusEqualTo(StatusConstant.CREATIVE_AUDIT_SUCCESS);
				creativeAudits = creativeAuditDao.selectByExample(creativeAuditEx);
			}
			return creativeAudits.size() > 0;
		}
		return false;
	}
	
	public boolean isInLaunchPeriod(String campaignId) throws Exception {
		CampaignModel model = campaignDao.selectByPrimaryKey(campaignId);
		Date current = new Date();
		Date start = model.getStartDate();
		Date end = model.getEndDate();
		
		return current.before(end) && current.after(start);
	}
	
	/**
	 * 判断是否将活动id写入到redis中的groupids里
	 * @param project
	 * @param campaign
	 * @return
	 * @throws Exception
	 */
	public boolean isWriteGroupIds(String projectId, String campaignId) throws Exception {
		// 满足（项目打开、活动打开）、活动在定向时间、在投放时间、没有超项目预算、没有超日预算、没有超日展现
		return campaignService.isOnTargetTime(campaignId)
		&& campaignService.isOnLaunchDate(campaignId) 
		&& notOverProjectBudget(projectId)
		&& notOverDailyBudget(campaignId) 
		&& notOverDailyCounter(campaignId);
	
	}
	
	/**
	 * 判断redis中是否存在创意信息
	 * @param campaignId 活动id
	 * @return
	 * @throws Exception
	 */
	public boolean isHaveCreativeInfoInRedis (String creativeId) throws Exception {
		return redisHelper.exists(RedisKeyConstant.CREATIVE_INFO + creativeId);
	}
	
}
