package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.RedisHelper;
import com.pxene.pap.common.UUIDGenerator;
import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.RedisKeyConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.BasicDataBean;
import com.pxene.pap.domain.beans.CampaignBean;
import com.pxene.pap.domain.beans.CampaignBean.Frequency;
import com.pxene.pap.domain.beans.CampaignBean.Quantity;
import com.pxene.pap.domain.beans.CampaignBean.Target;
import com.pxene.pap.domain.beans.CampaignBean.Target.App;
import com.pxene.pap.domain.beans.CampaignBean.Target.Region;
import com.pxene.pap.domain.beans.CampaignTargetBean;
import com.pxene.pap.domain.models.AdTypeTargetModel;
import com.pxene.pap.domain.models.AdTypeTargetModelExample;
import com.pxene.pap.domain.models.AppModel;
import com.pxene.pap.domain.models.AppTargetModel;
import com.pxene.pap.domain.models.AppTargetModelExample;
import com.pxene.pap.domain.models.BrandTargetModel;
import com.pxene.pap.domain.models.BrandTargetModelExample;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignModelExample;
import com.pxene.pap.domain.models.CreativeAuditModel;
import com.pxene.pap.domain.models.CreativeAuditModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.CreativeModelExample;
import com.pxene.pap.domain.models.DeviceTargetModel;
import com.pxene.pap.domain.models.DeviceTargetModelExample;
import com.pxene.pap.domain.models.FrequencyModel;
import com.pxene.pap.domain.models.LandpageModel;
import com.pxene.pap.domain.models.MonitorModelExample;
import com.pxene.pap.domain.models.NetworkTargetModel;
import com.pxene.pap.domain.models.NetworkTargetModelExample;
import com.pxene.pap.domain.models.OperatorTargetModel;
import com.pxene.pap.domain.models.OperatorTargetModelExample;
import com.pxene.pap.domain.models.OsTargetModel;
import com.pxene.pap.domain.models.OsTargetModelExample;
import com.pxene.pap.domain.models.PopulationTargetModel;
import com.pxene.pap.domain.models.PopulationTargetModelExample;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.domain.models.QuantityModel;
import com.pxene.pap.domain.models.QuantityModelExample;
import com.pxene.pap.domain.models.RegionModel;
import com.pxene.pap.domain.models.RegionTargetModel;
import com.pxene.pap.domain.models.RegionTargetModelExample;
import com.pxene.pap.domain.models.TimeTargetModel;
import com.pxene.pap.domain.models.TimeTargetModelExample;
import com.pxene.pap.domain.models.view.CampaignTargetModel;
import com.pxene.pap.domain.models.view.CampaignTargetModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.exception.ServerFailureException;
import com.pxene.pap.exception.ThirdPartyAuditException;
import com.pxene.pap.repository.basic.AdTypeTargetDao;
import com.pxene.pap.repository.basic.AppDao;
import com.pxene.pap.repository.basic.AppTargetDao;
import com.pxene.pap.repository.basic.BrandTargetDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeAuditDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.DeviceTargetDao;
import com.pxene.pap.repository.basic.FrequencyDao;
import com.pxene.pap.repository.basic.LandpageDao;
import com.pxene.pap.repository.basic.MonitorDao;
import com.pxene.pap.repository.basic.NetworkTargetDao;
import com.pxene.pap.repository.basic.OperatorTargetDao;
import com.pxene.pap.repository.basic.OsTargetDao;
import com.pxene.pap.repository.basic.PopulationTargetDao;
import com.pxene.pap.repository.basic.ProjectDao;
import com.pxene.pap.repository.basic.QuantityDao;
import com.pxene.pap.repository.basic.RegionDao;
import com.pxene.pap.repository.basic.RegionTargetDao;
import com.pxene.pap.repository.basic.TimeTargetDao;
import com.pxene.pap.repository.basic.view.CampaignTargetDao;

@Service
public class CampaignService extends BaseService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LaunchService.class);
	
	@Autowired
	private CampaignDao campaignDao; 
	
	@Autowired
	private ProjectDao projectDao; 
	
	@Autowired
	private RegionDao regionDao;
	
	@Autowired
	private AppDao appDao;
	
	@Autowired
	private CreativeService creativeService; 
	
	@Autowired
	private CreativeDao creativeDao; 
	
	@Autowired
	private MonitorDao monitorDao;
	
	@Autowired
	private FrequencyDao frequencyDao;
	
	@Autowired
	private RegionTargetDao regionTargetDao;
	
	@Autowired
	private AdTypeTargetDao adtypeTargetDao;
	
	@Autowired
	private TimeTargetDao timeTargetDao;
	
	@Autowired
	private NetworkTargetDao networkTargetDao;
	
	@Autowired
	private OperatorTargetDao operatorTargetDao;
	
	@Autowired
	private DeviceTargetDao deviceTargetDao;
	
	@Autowired
	private OsTargetDao osTargetDao;
	
	@Autowired
	private BrandTargetDao brandTargetDao;
	
	@Autowired
	private AppTargetDao appTargetDao;
	
	@Autowired
	private PopulationTargetDao populationTargetDao;	
	
	@Autowired
	private CampaignTargetDao campaignTargetDao;
	
	@Autowired
	private LandpageDao LandpageDao;
	
	@Autowired
	private QuantityDao quantityDao;
	
	@Autowired
	private LaunchService launchService;
	
	@Autowired
	private LandpageDao landpageDao;
	
	@Autowired
	private MomoAuditService momoAuditService;
	
	@Autowired
	private InmobiAuditService inmobiAuditService;
	
	@Autowired
	private CreativeAuditDao creativeAuditDao;
	
	private RedisHelper redisHelper;
	
	private static JsonParser parser = new JsonParser();
	
//	private static final String REDIS_KEY_GROUPIDS = "dsp_groupids";
//	
//	private static final String REDIS_KEY_GROUPINFO = "dsp_groupid_info_";
//	
//	private static final String JSON_KEY_GROUPIDS = "groupids";
	
	public CampaignService()
    {
	    redisHelper = RedisHelper.open("redis.primary.");
    }
	
	/**
	 * 创建活动
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void createCampaign(CampaignBean bean) throws Exception {
		// 验证名称重复
		CampaignModelExample campaignModelExample = new CampaignModelExample();
		campaignModelExample.createCriteria().andNameEqualTo(bean.getName());
		List<CampaignModel> campaignModels = campaignDao.selectByExample(campaignModelExample);
		if (campaignModels != null && !campaignModels.isEmpty()) {
			throw new IllegalArgumentException(PhrasesConstant.NAME_NOT_REPEAT);
		}
		// FIXME 判断项目是否为空		
		// 判断落地页信息是否为空，落地页信息为空则不能创建活动
		LandpageModel landpageModel = landpageDao.selectByPrimaryKey(bean.getLandpageId());
		if (landpageModel == null) {
			throw new IllegalArgumentException(PhrasesConstant.LANDPAGE_INFO_NULL);
		}
    	// 监测日期范围是否正确
		Date startDate = bean.getStartDate();
	    Date endDate = bean.getEndDate();
	    if (startDate != null && endDate != null && startDate.after(endDate)) {
            throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_DATE_ERROR);
	    }		  
		
	    // 活动的日预算之和不能大于项目预算
	    String projectId = bean.getProjectId();
		ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
		int projectBudget = projectModel.getTotalBudget();

		// FIXME ：活动 日预算不能超过项目总预算
		// FIXME : 日预算不传如何处理
		// 分段设置预算，分段预算总和小于或等于项目总预算
		int dailyBudget = 0;
		Quantity[] quantities = bean.getQuantities();
		for (Quantity quantitie : quantities) {
			// 分段设置预算总和
			dailyBudget = dailyBudget + quantitie.getDailyBudget();
		}

		campaignModelExample.clear();
		campaignModelExample.createCriteria().andProjectIdEqualTo(projectId);
		campaignModels = campaignDao.selectByExample(campaignModelExample);
		if (campaignModels != null && !campaignModels.isEmpty()) {
			for (CampaignModel model : campaignModels) {
				QuantityModelExample quantityExample = new QuantityModelExample();
				quantityExample.createCriteria().andCampaignIdEqualTo(model.getId());
				List<QuantityModel> quantitys = quantityDao.selectByExample(quantityExample);
				for (QuantityModel quantity : quantitys) {
					// 日预算总和
					dailyBudget = dailyBudget + quantity.getDailyBudget();
				}
			}
		}
		if (dailyBudget-projectBudget > 0) {
			throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_ALL_BUDGET_OVER_PROJECT);
		}	 
	    // 添加活动信息	    
		String id = UUIDGenerator.getUUID();
		bean.setId(id);
		CampaignModel campaignModel = modelMapper.map(bean, CampaignModel.class);
		
		// 添加频次信息
		String frequencyId = addFrequency(bean);
		if (!StringUtils.isEmpty(frequencyId)) {
			campaignModel.setFrequencyId(frequencyId);
		}

		// 添加投放量控制策略
		addCampaignQuantity(bean);
		
		campaignModel.setStatus(StatusConstant.CAMPAIGN_PAUSE);
		campaignDao.insertSelective(campaignModel);
	}

	/**
	 * 编辑活动
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void updateCampaign(String id, CampaignBean bean) throws Exception {
		// 编辑的活动在数据库中不存在		  
		CampaignModel campaignInDB = campaignDao.selectByPrimaryKey(id);
		if (campaignInDB == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		} else {
			String nameInDB = campaignInDB.getName();
			String name = bean.getName();
			if (!nameInDB.equals(name)) {
				CampaignModelExample campaignExample = new CampaignModelExample();
				campaignExample.createCriteria().andNameEqualTo(name);
				List<CampaignModel> campaigns = campaignDao.selectByExample(campaignExample);
				if (campaigns != null && !campaigns.isEmpty()) {
					throw new DuplicateEntityException(PhrasesConstant.NAME_NOT_REPEAT);					  
				}				
			}
		}
		
		// FIXME : 落地页信息是否存在、项目信息是否存在
		// 监测日期范围是否正确
		Date startDate = bean.getStartDate();
		Date endDate = bean.getEndDate();
		if (startDate != null && endDate != null && startDate.after(endDate)) {
			throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_DATE_ERROR);
		}	
		
		// bean中放入ID，用于更新关联关系表中数据
		bean.setId(id);		
		
		// 传值里的日预算
		Integer dailyBudget = 0;
		Quantity[] quantities = bean.getQuantities();
		for (Quantity quantitie : quantities) {
			// 分段设置的预算总和
			dailyBudget = dailyBudget + quantitie.getDailyBudget();
		}
		
		
		// 判断预算是否超出
		String projectId = bean.getProjectId();
		// 获得数据库中该活动隶属项目的项目总预算
		ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
		Integer projectBudget = projectModel.getTotalBudget();				
		
		// 获得当前项目除本活动之外的全部活动已占用多少日预算
		int dailyBudgetOthers = 0;
		CampaignModelExample campaignExample = new CampaignModelExample();
		campaignExample.createCriteria().andProjectIdEqualTo(projectId).andIdNotEqualTo(bean.getId());
		List<CampaignModel> campaigns = campaignDao.selectByExample(campaignExample);
		if (campaigns != null && !campaigns.isEmpty()) {
			for (CampaignModel campaign : campaigns) {
				// 当前项目其他活动，数据库中的日预算
				QuantityModelExample quantityExample = new QuantityModelExample();
				quantityExample.createCriteria().andCampaignIdEqualTo(campaign.getId());
				List<QuantityModel> quantityList = quantityDao.selectByExample(quantityExample);
				if (quantityList != null && !quantityList.isEmpty()) {
					for (QuantityModel quantity : quantityList) {
						// 当前项目其他活动分段设置日预算总和
						dailyBudget = dailyBudget + quantity.getDailyBudget();
					}
					
				}
			}
		}
		// 如果修改后的日预算 + 其他活动日预算大于总日预算，则抛出异常
		if (dailyBudget + dailyBudgetOthers > projectBudget) {
			throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_ALL_BUDGET_OVER_PROJECT);
		}
		// 改变预算、展现时修改redis中的值
		// changeBudgetAndCounter(id, dbBudget, campaignBudget, bean.getQuantities());//改变日预算和总预算
		// 编辑活动时判断是否已经投放过，及不是第一次投放修改redis中相关的信息
		if (launchService.isHaveLaunched(id)) {			
			// 改变预算(日均预算、总预算)、展现时修改redis中的值
			changeBudgetAndCounter(id, bean.getQuantities());
		}
		
		CampaignModel campaign = modelMapper.map(bean, CampaignModel.class);
		// 编辑频次
		Frequency frequency = bean.getFrequency();
		String frequencyId = campaignInDB.getFrequencyId();
		if (frequency == null) {
			if (StringUtils.isEmpty(frequencyId)) { // 活动以前不存在频次，不动
				
			} else { // 活动以前存在频次，删除
				campaign.setFrequencyId(null);
				frequencyDao.deleteByPrimaryKey(frequencyId);				
			}
		} else {
			if (StringUtils.isEmpty(frequencyId)) { // 活动以前不存在频次，添加  
				String fId = UUIDGenerator.getUUID();
				frequency.setId(fId);
				FrequencyModel frequencyModel = modelMapper.map(frequency, FrequencyModel.class);
				frequencyDao.insertSelective(frequencyModel);
				campaign.setFrequencyId(fId);				
			} else { // 活动以前存在频次，删除再添加
				frequencyDao.deleteByPrimaryKey(frequencyId);
				String fId = UUIDGenerator.getUUID();
				frequency.setId(fId);
				FrequencyModel frequencyModel = modelMapper.map(frequency, FrequencyModel.class);
				frequencyDao.insertSelective(frequencyModel);
				campaign.setFrequencyId(fId);				
			}
		}
			
		Date current = new Date();
		if (current.after(endDate)) {
			boolean removeResult = launchService.pauseCampaignRepeatable(id);
			if (!removeResult) {
				throw new ServerFailureException(PhrasesConstant.REDIS_KEY_LOCK);
			}
		}
		
		//删除点击、展现监测地址
		deleteCampaignMonitor(id);
		//删除投放量控制策略
		deleteCampaignQuantity(id);
		//添加投放量控制策略
		addCampaignQuantity(bean);				
		// 修改基本信息
		campaignDao.updateByPrimaryKeySelective(campaign);
		// 编辑活动时判断是否已经投放过，及不是第一次投放修改redis中相关的信息
		if (launchService.isHaveLaunched(id)) {
			// 写入活动频次信息 dsp_groupid_frequencycapping_*，修改频次信息/是否匀速等相关信息
			launchService.writeCampaignFrequency(campaign);
			// 写入活动下的创意基本信息 dsp_mapid_*，修改落地页等相关信息
			launchService.writeCreativeInfo(id);
		}
	}		
	
	/**
	 * 修改redis中活动日预算、日展现 
	 * @param campaignId
	 * @param quantities
	 * @throws Exception
	 */
	private void changeBudgetAndCounter(String campaignId, Quantity[] quantities) throws Exception {
		String budgetKey = RedisKeyConstant.CAMPAIGN_BUDGET + campaignId;
		String countKey = RedisKeyConstant.CAMPAIGN_COUNTER + campaignId;
		if (redisHelper.exists(budgetKey)) {
			Map<String, String> map = redisHelper.hget(budgetKey);			
			// 修改redis中的日预算值
			if (!StringUtils.isEmpty(map.get("daily")) && quantities != null) {
				String daily = map.get("daily");//redis里值
				Float dailyFloat = Float.parseFloat(daily) / 100;// Redis中保存的费用单位是分，MySQL中保存的费用是元
				Integer budget = 0;//数据库里值
				Integer impression = 0;//数据库里值
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
							impression = quan.getDailyImpression();
							break;
						}
					}
				}
				Integer newDayBudget = 0;//修改后的值
				Integer newImpression = 0;//修改后的值
				for (Quantity qt : quantities) {
					String[] days = DateUtils.getDaysBetween(qt.getStartDate(), qt.getEndDate());
					List<String> dayList = Arrays.asList(days);
					String time = new DateTime(new Date()).toString("yyyyMMdd");
					if (dayList.contains(time)) {
						newDayBudget = qt.getDailyBudget();
						newImpression = qt.getDailyImpression();
						break;
					}
				}
				Integer difVaue = (newDayBudget - budget);//修改前后差值（新的减去旧的）
				Integer difImpVaue = (newImpression - impression);//修改前后差值（新的减去旧的）
				if (difVaue < 0 && Math.abs(difVaue) > dailyFloat) {//小于0时，并且redis中值不够扣除，抛出异常
					throw new IllegalArgumentException(PhrasesConstant.DIF_DAILY_BIGGER_REDIS);
				}
				if (difVaue != 0) {
					redisHelper.hincrbyFloat(budgetKey, "daily", difVaue * 100);
				}
				//如果有日展现key
				if (redisHelper.exists(countKey)) {
					Integer dayImpression  = redisHelper.getInt(countKey);
					if (difImpVaue < 0 && Math.abs(difImpVaue) > dayImpression) {//小于0时，并且redis中值不够扣除，抛出异常
						throw new IllegalArgumentException(PhrasesConstant.DIF_IMPRESSION_BIGGER_REDIS);
					}
					if (difImpVaue != 0) {
					    redisHelper.incrybyInt(countKey, difImpVaue);
					}
				}
			}
		}
	}
	
	/**
	 * 修改活动状态
	 * @param id
	 * @param action
	 * @throws Exception
	 */
	@Transactional
	public void updateCampaignStatus(String id, Map<String, String> map) throws Exception {
		String status = map.get("status");
		
		if (StringUtils.isEmpty(status)) {
			throw new IllegalArgumentException();
		}
		CampaignModel campaignModel = campaignDao.selectByPrimaryKey(id);
		if (campaignModel == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		
		if (StatusConstant.CAMPAIGN_PAUSE.equals(status)) {
			pauseCampaign(campaignModel);
		} else if (StatusConstant.CAMPAIGN_PROCEED.equals(status)) {
			proceedCampaign(campaignModel);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 设置活动定向，新增和修改
	 * @param id
	 * @param bean
	 * @throws Exception
	 */
	@Transactional
	public void createCampaignTarget(String id, CampaignTargetBean bean) throws Exception {
		bean.setId(id);
		// 删除掉定向
		deleteCampaignTarget(id);
		// 添加定向
		addCampaignTarget(bean);
		// 修改定向时更新redis中活动定向信息（是否已经投放过，投放过再修改）
		if (launchService.isHaveLaunched(id)) {
			// 活动定向写入redis
			launchService.writeCampaignTarget(id);
			// 先移除以前的白名单
			launchService.removeWhiteBlack(id);
			// 在添加最新的白名单
			launchService.writeWhiteBlack(id);

			// 查询项目信息
			CampaignModel campaignModel = campaignDao.selectByPrimaryKey(id);
			String projectId = campaignModel.getProjectId();
			ProjectModel project = projectDao.selectByPrimaryKey(projectId);

			// 编辑定向时间可添加、删除redis中的对应的groupids
			// 1.投放过，判断该id是否在groupids中
			String groupids = redisHelper.getStr(RedisKeyConstant.CAMPAIGN_IDS);
			if (groupids == null || groupids.isEmpty()) {
				throw new ServerFailureException(PhrasesConstant.REDIS_GROUPIDS_NULL);
			}
			// 将gson字符串转成JsonObject对象
			JsonObject returnData = parser.parse(groupids).getAsJsonObject();
			// 将data节点下的内容转为JsonArray
			JsonArray jsonArray = returnData.getAsJsonArray("groupids");
			// 判断该id是否在groupids中	
			if (jsonArray.contains(parser.parse(id))) {
				// 2.在groupids中，判断是否在定向时间
				if (!isOnTargetTime(id)) {
					// 不在定向时间则删除
					boolean removeResult = launchService.pauseCampaignRepeatable(id);
					if (!removeResult) {
						throw new ServerFailureException(PhrasesConstant.REDIS_KEY_LOCK);
					}
				}
			} else {
				// 3.不在groupids中，满足项目开启、活动开启、定向时间、日预算、最大展现向groupids添加活动id
				// FIXME : 判断项目的总预算，不用判断是否在投放周期中
				if (StatusConstant.PROJECT_PROCEED.equals(project.getStatus())
						&& StatusConstant.CAMPAIGN_PROCEED.equals(campaignModel.getStatus()) && isOnLaunchDate(id)
						&& isOnTargetTime(id) && launchService.notOverDailyBudget(id)
						&& launchService.notOverDailyCounter(id)) {
					// 在项目开启、活动开启并且在投放的时间里，修改定向时间在定向时间里，将活动ID写入redis
					// 活动没有超出每天的日预算并且日均最大展现未达到上限
					boolean writeResult = launchService.launchCampaignRepeatable(id);
					if (!writeResult) {
						throw new ServerFailureException(PhrasesConstant.REDIS_KEY_LOCK);
					}
				}
			}
			
			// 修改定向时更新redis中的活动基本信息和频次基本信息
			// 1.写入活动基本信息 dsp_group_info_*
			launchService.writeCampaignInfo(campaignModel);
			// 2.写入活动频次信息 dsp_groupid_frequencycapping_*
			launchService.writeCampaignFrequency(campaignModel);
		}				
	}
	
	/**
	 * 添加活动定向
	 * @param bean
	 */
	@Transactional
	private void addCampaignTarget(CampaignTargetBean bean) throws Exception {
		String id = bean.getId();
		String[] regionTarget = bean.getRegion();//地域
		String[] adTypeTarget = bean.getAdType();//广告类型
		String[] timeTarget = bean.getTime();//时间
		String[] networkTarget = bean.getNetwork();//网络
		String[] operatorTarget = bean.getOperator();//运营商
		String[] deviceTarget = bean.getDevice();//设备
		String[] osTarget = bean.getOs();//系统
		String[] brandTarget = bean.getBrand();//品牌
		String[] appTarget = bean.getApp();//app
		String populationTarget = bean.getPopulation();//人群
		if (regionTarget != null && regionTarget.length > 0) {
			RegionTargetModel region = new RegionTargetModel();
			for (String regionId : regionTarget) {
				region.setId(UUIDGenerator.getUUID());
				region.setCampaignId(id);
				region.setRegionId(regionId);
				regionTargetDao.insertSelective(region);
			}
		}
		if (adTypeTarget != null && adTypeTarget.length > 0) {
			AdTypeTargetModel adType = new AdTypeTargetModel();
			for (String adTypeId : adTypeTarget) {
				adType.setId(UUIDGenerator.getUUID());
				adType.setCampaignId(id);
				adType.setAdType(adTypeId);
				adtypeTargetDao.insertSelective(adType);
			}
		}
		if (timeTarget != null && timeTarget.length > 0) {
			TimeTargetModel time = new TimeTargetModel();
			for (String timeId : timeTarget) {
				time.setId(UUIDGenerator.getUUID());
				time.setCampaignId(id);
				time.setTime(timeId);
				timeTargetDao.insertSelective(time);
			}
		}
		if (networkTarget != null && networkTarget.length > 0) {
			NetworkTargetModel network = new NetworkTargetModel();
			for (String networkid : networkTarget) {
				network.setId(UUIDGenerator.getUUID());
				network.setCampaignId(id);
				network.setNetwork(networkid);
				networkTargetDao.insertSelective(network);
			}
		}
		if (operatorTarget != null && operatorTarget.length > 0) {
			OperatorTargetModel operator = new OperatorTargetModel();
			for (String operatorId : operatorTarget) {
				operator.setId(UUIDGenerator.getUUID());
				operator.setCampaignId(id);
				operator.setOperator(operatorId);
				operatorTargetDao.insertSelective(operator);
			}
		}
		if (deviceTarget != null && deviceTarget.length > 0) {
			DeviceTargetModel device = new DeviceTargetModel();
			for (String deviceId : deviceTarget) {
				device.setId(UUIDGenerator.getUUID());
				device.setCampaignId(id);
				device.setDevice(deviceId);
				deviceTargetDao.insertSelective(device);
			}
		}
		if (osTarget != null && osTarget.length > 0) {
			OsTargetModel os = new OsTargetModel();
			for (String osId : osTarget) {
				os.setId(UUIDGenerator.getUUID());
				os.setCampaignId(id);
				os.setOs(osId);
				osTargetDao.insertSelective(os);
			}
		}
		if (brandTarget != null && brandTarget.length > 0) {
			BrandTargetModel brand = new BrandTargetModel();
			for (String brandId : brandTarget) {
				brand.setId(UUIDGenerator.getUUID());
				brand.setCampaignId(id);
				brand.setBrandId(brandId);
				brandTargetDao.insertSelective(brand);
			}
		}
		if (appTarget != null && appTarget.length > 0) {
			AppTargetModel app = new AppTargetModel();
			for (String appId : appTarget) {
				app.setId(UUIDGenerator.getUUID());
				app.setCampaignId(id);
				app.setAppId(appId);
				appTargetDao.insertSelective(app);
			}
		}
		if (!StringUtils.isEmpty(populationTarget)) {
			PopulationTargetModel population = new PopulationTargetModel();
			population.setId(UUIDGenerator.getUUID());
			population.setCampaignId(id);
			population.setPopulationId(populationTarget);
			populationTargetDao.insertSelective(population);
		}
	}
	
	/**
	 * 删除活动定向
	 * @param campaignId
	 */
	@Transactional
	private void deleteCampaignTarget(String campaignId) throws Exception {
		//删除地域定向
		RegionTargetModelExample region = new RegionTargetModelExample();
		region.createCriteria().andCampaignIdEqualTo(campaignId);
		regionTargetDao.deleteByExample(region);
		//删除广告类型定向
		AdTypeTargetModelExample adType = new AdTypeTargetModelExample();
		adType.createCriteria().andCampaignIdEqualTo(campaignId);
		adtypeTargetDao.deleteByExample(adType);
		//删除时间定向
		TimeTargetModelExample time = new TimeTargetModelExample();
		time.createCriteria().andCampaignIdEqualTo(campaignId);
		timeTargetDao.deleteByExample(time);
		//删除网络定向
		NetworkTargetModelExample network = new NetworkTargetModelExample();
		network.createCriteria().andCampaignIdEqualTo(campaignId);
		networkTargetDao.deleteByExample(network);
		//删除运营商定向
		OperatorTargetModelExample operator = new OperatorTargetModelExample();
		operator.createCriteria().andCampaignIdEqualTo(campaignId);
		operatorTargetDao.deleteByExample(operator);
		//删除设备定向
		DeviceTargetModelExample device = new DeviceTargetModelExample();
		device.createCriteria().andCampaignIdEqualTo(campaignId);
		deviceTargetDao.deleteByExample(device);
		//删除系统定向
		OsTargetModelExample os = new OsTargetModelExample();
		os.createCriteria().andCampaignIdEqualTo(campaignId);
		osTargetDao.deleteByExample(os);
		//删除品牌定向
		BrandTargetModelExample brand = new BrandTargetModelExample();
		brand.createCriteria().andCampaignIdEqualTo(campaignId);
		brandTargetDao.deleteByExample(brand);
		//删除APP定向
		AppTargetModelExample app = new AppTargetModelExample();
		app.createCriteria().andCampaignIdEqualTo(campaignId);
		appTargetDao.deleteByExample(app);
		//删除人群定向
		PopulationTargetModelExample pop = new PopulationTargetModelExample();
		pop.createCriteria().andCampaignIdEqualTo(campaignId);
		populationTargetDao.deleteByExample(pop);
	}
	
	/**
	 * 添加活动监测地址
	 * @param bean
	 */
	/*@Transactional
	private void addCampaignMonitor(CampaignBean campaignBean) throws Exception {
		Monitor[] monitors = campaignBean.getMonitors();
		if (monitors != null && monitors.length > 0) {
			String id = campaignBean.getId();
			for (Monitor bean : monitors) {
				String monitorId = UUIDGenerator.getUUID();
				MonitorModel model = modelMapper.map(bean, MonitorModel.class);
				model.setId(monitorId);
				model.setCampaignId(id);
				monitorDao.insertSelective(model);
			}
		}
	}*/
	
	/**
	 * 删除活动监测地址
	 * @param campaignId
	 */
	@Transactional
	private void deleteCampaignMonitor(String campaignId) throws Exception {
		MonitorModelExample example = new MonitorModelExample();
		example.createCriteria().andCampaignIdEqualTo(campaignId);
		monitorDao.deleteByExample(example);
	}
	
	/**
	 * 添加活动投放策略
	 * @param bean
	 * @throws Exception
	 */
	@Transactional
	private void addCampaignQuantity(CampaignBean campaignBean) throws Exception {
		// 监测日期范围是否正确
		Date startDate = campaignBean.getStartDate();
		Date endDate = campaignBean.getEndDate();
		if (startDate != null && endDate != null && startDate.after(endDate)) {
			throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_DATE_ERROR);
		}	
		// 项目总预算
		ProjectModel projectModel = projectDao.selectByPrimaryKey(campaignBean.getProjectId());
		Integer projectBudget = projectModel.getTotalBudget();
		// 策略
		Quantity[] quantitys = campaignBean.getQuantities();			
		if (quantitys != null && quantitys.length > 0) {
			String id = campaignBean.getId();
			for (Quantity bean : quantitys) {	
				Integer dailyBudget = bean.getDailyBudget();
				if (dailyBudget != null && projectBudget != null && dailyBudget.compareTo(projectBudget) > 0) {
					throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_DAILY_BUDGET_OVER_TOTAL); 
				}
				QuantityModel model = modelMapper.map(bean, QuantityModel.class);
				model.setCampaignId(id);
				model.setId(UUIDGenerator.getUUID());
				quantityDao.insertSelective(model);
			}
		}
	}
	
	/**
	 * 活动投放策略
	 * @param campaignId
	 * @throws Exception
	 */
	@Transactional
	private void deleteCampaignQuantity(String campaignId) throws Exception {
		QuantityModelExample example = new QuantityModelExample();
		example.createCriteria().andCampaignIdEqualTo(campaignId);
		quantityDao.deleteByExample(example);
	}
	
	private String addFrequency(CampaignBean campaignBean) throws Exception {
		Frequency frequency = campaignBean.getFrequency();
		if (frequency != null) {
			if (StringUtils.isEmpty(frequency.getControlObj()) || frequency.getNumber() == null
					|| StringUtils.isEmpty(frequency.getTimeType())) {
				throw new IllegalArgumentException(PhrasesConstant.FREQUENCY_NOT_COMPLETE);
			}
			FrequencyModel frequencyModel = modelMapper.map(frequency, FrequencyModel.class);
			String frequencyId = UUIDGenerator.getUUID();
			frequencyModel.setId(frequencyId);
			frequencyDao.insertSelective(frequencyModel);
			return frequencyId;
		}
		return null;
	}
	
	/**
	 * 删除频次信息
	 * @param campaignId
	 * @throws Exception
	 */
	@Transactional
	private void deleteFrequency(String campaignId) throws Exception {
		CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
		frequencyDao.deleteByPrimaryKey(campaignModel.getFrequencyId());
	}
	
	/**
	 * 删除活动
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void deleteCampaign(String campaignId) throws Exception {
		CampaignModel campaignInDB = campaignDao.selectByPrimaryKey(campaignId);
		if (campaignInDB ==null || StringUtils.isEmpty(campaignInDB.getId())) {
			throw new ResourceNotFoundException();
		}
		
		//先查询出活动下创意
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> list = creativeDao.selectByExample(creativeExample);
		if (list != null && !list.isEmpty()) {
			throw new IllegalStatusException(PhrasesConstant.CAMPAIGN_HAVE_CREATIVE);
		}
		//删除监测地址
		// deleteCampaignMonitor(campaignId);
		//删除定向
		deleteCampaignTarget(campaignId);
		//删除频次信息
		deleteFrequency(campaignId);
		//删除控制策略
		deleteCampaignMonitor(campaignId);
		//删除活动
		campaignDao.deleteByPrimaryKey(campaignId);
	}
	
	/**
	 * 批量删除活动
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void deleteCampaigns(String[] campaignIds) throws Exception {
		
		List<String> asList = Arrays.asList(campaignIds);
		CampaignModelExample ex = new CampaignModelExample();
		ex.createCriteria().andIdIn(asList);
		
		List<CampaignModel> campaignInDB = campaignDao.selectByExample(ex);
		if (campaignInDB == null || campaignInDB.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		for (String campaignId : campaignIds) {
			//先查询出活动下创意
			CreativeModelExample creativeExample = new CreativeModelExample();
			creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
			List<CreativeModel> list = creativeDao.selectByExample(creativeExample);
			if (list != null && !list.isEmpty()) {
				throw new IllegalStatusException(PhrasesConstant.CAMPAIGN_HAVE_CREATIVE);
			}
			//删除监测地址
			// deleteCampaignMonitor(campaignId);
			//删除定向
			deleteCampaignTarget(campaignId);
			//删除频次信息
			deleteFrequency(campaignId);
			//删除控制策略
			deleteCampaignMonitor(campaignId);
		}
		//删除活动
		campaignDao.deleteByExample(ex);
	}
	
	/**
	 * 根据id查询活动
	 * @param campaignId
	 * @return
	 */
	public CampaignBean getCampaign(String campaignId) throws Exception {
		CampaignModel model = campaignDao.selectByPrimaryKey(campaignId);
		if (model == null || StringUtils.isEmpty(model.getId())) {
			throw new ResourceNotFoundException();
		}
		CampaignBean map = modelMapper.map(model, CampaignBean.class);
		addParamToCampaign(map, model.getId(), model.getFrequencyId());
		// 活动未正常投放原因
		if (launchService.isHaveLaunched(model.getId())) {
			// 是否已经投放过，投放过可能出现预算和展现数上限
			// FIXME : 分清是哪个预算超出范围
			if (!launchService.notOverDailyBudget(model.getId()) || !launchService.notOverProjectBudget(model.getId())) {
				// 预算达到上限
				map.setReason(StatusConstant.CAMPAIGN_BUDGET_OVER);
			} else if (!launchService.notOverDailyCounter(model.getId())) {
				// 展现数达到上限
				map.setReason(StatusConstant.CAMPAIGN_COUNTER_OVER);
			}
		} else if (!isOnTargetTime(model.getId())) {
			// 不在定向时间段内
			// FIXME ： 这个else应该在哪？
			map.setReason(StatusConstant.CAMPAIGN_ISNOT_TARGETTIME);
		}
		return map;
	}
	
	/**
	 * 查询活动列表
	 * @param name
	 * @param beginTime 
	 * @param endTime 
	 * @return
	 * @throws Exception
	 */
	public List<CampaignBean> listCampaigns(String name, String projectId, Long beginTime, Long endTime) throws Exception {
		CampaignModelExample example = new CampaignModelExample();
		
		// 按更新时间进行倒序排序
        example.setOrderByClause("create_time DESC");
		
		if(!StringUtils.isEmpty(name) && StringUtils.isEmpty(projectId)){
			example.createCriteria().andNameLike("%" + name + "%");
		} else if(!StringUtils.isEmpty(projectId) && StringUtils.isEmpty(name)){
			example.createCriteria().andProjectIdEqualTo(projectId);
		}else if(!StringUtils.isEmpty(name) && !StringUtils.isEmpty(projectId)){
			example.createCriteria().andProjectIdEqualTo(projectId).andNameLike("%" + name + "%");
		}
		
		List<CampaignModel> models = campaignDao.selectByExample(example);
		List<CampaignBean> beans = new ArrayList<CampaignBean>();
		
		if (models == null || models.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		for (CampaignModel model : models) {
			CampaignBean map = modelMapper.map(model, CampaignBean.class);
			addParamToCampaign(map, model.getId(), model.getFrequencyId());

			if (beginTime != null && endTime != null) {
				// 查询每个活动的投放信息
				getData(beginTime, endTime, map);
			}
			// 活动未正常投放原因
			if (launchService.isHaveLaunched(model.getId())) {
				// 是否已经投放过，投放过可能出现预算和展现数上限  
				if (!launchService.notOverDailyBudget(model.getId())
						|| !launchService.notOverProjectBudget(model.getId())) {
					// 预算达到上限
					map.setReason(StatusConstant.CAMPAIGN_BUDGET_OVER);
				} else if (!launchService.notOverDailyCounter(model.getId())) {
					// 展现数达到上限
					map.setReason(StatusConstant.CAMPAIGN_COUNTER_OVER);
				}
			} else if (!isOnTargetTime(model.getId())) {
				// 不在定向时间段内
				map.setReason(StatusConstant.CAMPAIGN_ISNOT_TARGETTIME);
			}

			beans.add(map);
		}
		
		return beans;
	}
	
	/**
	 * 查询投放数据放入结果中
	 * @param campaignId
	 * @param beginTime
	 * @param endTime
	 * @param bean
	 * @throws Exception 
	 */
	private void getData(Long beginTime, Long endTime, CampaignBean bean) throws Exception {
		CreativeModelExample cExample = new CreativeModelExample();
		cExample.createCriteria().andCampaignIdEqualTo(bean.getId());
		List<CreativeModel> list = creativeDao.selectByExample(cExample);
		List<String> creativeIds = new ArrayList<String>();
		if (list != null && !list.isEmpty()) {
			for (CreativeModel model : list) {
				creativeIds.add(model.getId());
			}
		}
		BasicDataBean dataBean = creativeService.getCreativeDatas(creativeIds, beginTime, endTime);
		if (dataBean != null) {
			bean.setImpressionAmount(dataBean.getImpressionAmount());
			bean.setClickAmount(dataBean.getClickAmount());
			bean.setTotalCost(dataBean.getTotalCost());
			bean.setJumpAmount(dataBean.getJumpAmount());
			bean.setImpressionCost(dataBean.getImpressionCost());
			bean.setClickCost(dataBean.getClickCost());
			bean.setClickRate(dataBean.getClickRate());
			bean.setJumpCost(dataBean.getJumpCost());
		}
	}
	
	
	/**
	 * 查询活动相关属性
	 * @param bean
	 * @param campaignId
	 * @return
	 */
	private void addParamToCampaign(CampaignBean bean, String campaignId, String frequencyId) throws Exception{
		CampaignTargetModelExample campaignTargetModelExample = new CampaignTargetModelExample();
		campaignTargetModelExample.createCriteria().andIdEqualTo(campaignId);
		// 查询定向信息
		List<CampaignTargetModel> list = campaignTargetDao.selectByExampleWithBLOBs(campaignTargetModelExample);
		if (list != null && !list.isEmpty()) {
			CampaignTargetModel campaignTargetModel = list.get(0);
			if (campaignTargetModel != null) {
				Target target = new Target();
				target.setAdType(formatTargetStringToArray(campaignTargetModel.getAdType()));
				target.setTime(formatTargetStringToArray(campaignTargetModel.getTimeId()));
				target.setNetwork(formatTargetStringToArray(campaignTargetModel.getNetwork()));
				target.setOperator(formatTargetStringToArray(campaignTargetModel.getOperator()));
				target.setDevice(formatTargetStringToArray(campaignTargetModel.getDevice()));
				target.setOs(formatTargetStringToArray(campaignTargetModel.getOs()));
				target.setBrand(formatTargetStringToArray(campaignTargetModel.getBrandId()));
				JsonArray regionTargets = new JsonArray();//地域定向数据长度太长需要单独查询
				RegionTargetModelExample regionTargetModelExample = new RegionTargetModelExample();
				regionTargetModelExample.createCriteria().andCampaignIdEqualTo(campaignId);
				List<RegionTargetModel> regionTargetList = regionTargetDao.selectByExample(regionTargetModelExample);
				if (regionTargetList!=null && !regionTargetList.isEmpty()) {
					for (RegionTargetModel mol : regionTargetList) {
						regionTargets.add(Integer.parseInt(mol.getRegionId()));
					}
				}
				//地域定向信息返回名称和id
				String[] regionArray = new String[regionTargets.size()];
				if (regionTargets.size() > 0) {
					for (int i = 0; i < regionTargets.size(); i++) {
						regionArray[i] = regionTargets.get(i).getAsString();
					}
				}
				if (regionArray != null) {
					RegionModel regionModel = null;
					Region region = null;
					List<Region> regionList = new ArrayList<CampaignBean.Target.Region>();
					for (String re : regionArray) {
						regionModel = regionDao.selectByPrimaryKey(re);
						if (regionModel != null) {
							region = new Region();
							region.setName(regionModel.getName());
							region.setId(regionModel.getId());
							regionList.add(region);
						}
					}
					if (!regionList.isEmpty()) {
						Region[] regions = new Region[regionList.size()];
						for (int i = 0; i < regionList.size(); i++) {
							regions[i] = regionList.get(i);
						}
						target.setRegions(regions);
					}
				}
				//app定向信息返回名称和id
				String[] AppArray = formatTargetStringToArray(campaignTargetModel.getAppId());
				if (AppArray != null) {
					AppModel appModel = null;
					App app = null;
					List<App> appList = new ArrayList<CampaignBean.Target.App>();
					for (String ap : AppArray) {
						appModel = appDao.selectByPrimaryKey(ap);
						if (appModel != null) {
							app = new App();
							app.setName(appModel.getAppName());
							app.setId(appModel.getId());
							appList.add(app);
						}
					}
					if (!appList.isEmpty()) {
						App[] apps = new App[appList.size()];
						for (int i = 0; i < appList.size(); i++) {
							apps[i] = appList.get(i);
						}
						target.setApps(apps);
					}
				}
				String populationId = campaignTargetModel.getPopulationId();//查询活动的人群定向信息
				if (!StringUtils.isEmpty(populationId)) {
					target.setPopulation(populationId);
				}
				bean.setTarget(target);
			}
		}
		// 查询频次信息 
		
		if (!StringUtils.isEmpty(frequencyId)) {
			FrequencyModel frequencyModel = frequencyDao.selectByPrimaryKey(frequencyId);
			if (frequencyModel != null) {
				Frequency frequency = new Frequency();
				frequency.setId(frequencyId);
				frequency.setControlObj(frequencyModel.getControlObj());
				frequency.setNumber(frequencyModel.getNumber());
				frequency.setTimeType(frequencyModel.getTimeType());
				bean.setFrequency(frequency);
			}
		}				
		
		//落地页信息
		String landpageId = bean.getLandpageId();
		LandpageModel landpageModel = LandpageDao.selectByPrimaryKey(landpageId);
		if (landpageModel != null) {
			String url = landpageModel.getUrl();
			String name = landpageModel.getName();
			bean.setLandpageName(name);
			bean.setLandpageUrl(url);
		}
		//投放量控制策略
		QuantityModelExample quantityModelExample = new QuantityModelExample();
		quantityModelExample.setOrderByClause("start_date");
		quantityModelExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<QuantityModel> quantityModels = quantityDao.selectByExample(quantityModelExample);
		if (quantityModels != null && !quantityModels.isEmpty()) {
			Quantity[] quanArray = new Quantity[quantityModels.size()] ;
			for (int i = 0; i < quantityModels.size(); i++) {
				QuantityModel quantityModel = quantityModels.get(i);
				quanArray[i] = modelMapper.map(quantityModel, Quantity.class);
			}
			bean.setQuantities(quanArray);
		}
		//查询创意数
		CreativeModelExample creativeModelExample = new CreativeModelExample();
		creativeModelExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> creatives = creativeDao.selectByExample(creativeModelExample);
		if (creatives == null) {
			bean.setCreativeNum(0);
		} else {
			bean.setCreativeNum(creatives.size());
		}
		//查询项目名称
		
	}
	
	/**
	 * 将字符串形式定向转成数组("1,2,3"==》[1,2,3])
	 * @param target
	 * @return
	 */
	private static String[] formatTargetStringToArray(String target){
		if(!StringUtils.isEmpty(target)){
			String [] targets = target.split(",");
			return targets;
		}
		return null;//空字符串返回null
	}
	
	/**
	 * 按照活动投放
	 * @param campaignIds
	 * @throws Exception
	 */
	@Transactional
	private void proceedCampaign(CampaignModel campaign) throws Exception {
		String campaignId = campaign.getId();
		// 检查该活动是否可以投放
		// 检查是否有创意
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> creatives = creativeDao.selectByExample(creativeExample);
		if (creatives == null || creatives.isEmpty()) {
			throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_NO_CREATIE);
		}
		// 检查创意是否审核通过
		// FIXME : 点击打开不会有报错信息（项目相同）
		// 检查创意状态是否有审核通过的
		for (CreativeModel creative : creatives) {
			String creativeId = creative.getId();
			if (!StatusConstant.CREATIVE_AUDIT_SUCCESS.equals(creativeService.getCreativeAuditStatus(creativeId))) {
				throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_NO_PASS_CREATIE);
			}
		}
		// 检查是否有落地页
		String landpageId = campaign.getLandpageId();
		if (StringUtils.isEmpty(landpageId)) {
			throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_NO_LANDPAGE);
		}
		campaign.setStatus(StatusConstant.CAMPAIGN_PROCEED);
		// 投放
		String projectId = campaign.getProjectId();
		ProjectModel project = projectDao.selectByPrimaryKey(projectId);
		if (StatusConstant.PROJECT_PROCEED.equals(project.getStatus()) && isOnLaunchDate(campaignId)) {
			if (!launchService.isHaveLaunched(campaignId)) {
				launchService.write4FirstTime(campaign);
			}
			// 添加创意时如果活动投放的基本信息已经写入redis则将新添加的创意信息写入redis
			// 1.查询该活动在redis中的活动信息
			String strCampaingInfo = redisHelper.getStr(RedisKeyConstant.CAMPAIGN_INFO + campaignId);
			if (strCampaingInfo != null && !strCampaingInfo.isEmpty()) {
				// 2.如果活动信息不为空说明已经投放的基本信息写入redis，则看创意信息是否都写入redis
				for (CreativeModel creative : creatives) {
					// 3.从redis中获取创意信息
					String strCreativeId = redisHelper.getStr(RedisKeyConstant.CAMPAIGN_MAPIDS + creative.getId());
					if (strCreativeId == null || strCreativeId.isEmpty()) {
						// 4.如果创意的mapids为空的话说明该创意信息不在redis中，则将其写入
						// 写入活动下的创意基本信息 dsp_mapid_*
						launchService.writeCreativeInfo(campaignId);
						// 写入活动下的创意ID dsp_group_mapids_*
						launchService.writeCreativeId(campaignId);
					}
				}
			}
			if (isOnTargetTime(campaignId) && launchService.notOverDailyBudget(campaignId)
					&& launchService.notOverDailyCounter(campaignId)) {
				// 在定向时间里、活动没有超出每天的日预算并且日均最大展现未达到上限
				boolean writeResult = launchService.launchCampaignRepeatable(campaignId);
				if (!writeResult) {
					throw new ServerFailureException(PhrasesConstant.REDIS_KEY_LOCK);
				}
			}
		}
		// 改变数据库状态
		campaignDao.updateByPrimaryKeySelective(campaign);
	}
	
	/**
	 * 按照活动暂停
	 * @param campaignIds
	 * @throws Exception
	 */
	@Transactional
	private void pauseCampaign(CampaignModel campaign) throws Exception {
		campaign.setStatus(StatusConstant.CAMPAIGN_PAUSE);
		String projectId = campaign.getProjectId();
		ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
		if (StatusConstant.PROJECT_PROCEED.equals(projectModel.getStatus())) {
			// 移除redis中key
			//launchService.removeCampaignId(campaign.getId());
			// 将不在满足条件的活动将其活动id从redis的groupids中删除--停止投放
			boolean removeResult = launchService.pauseCampaignRepeatable(campaign.getId());
			if (!removeResult) {
				throw new ServerFailureException(PhrasesConstant.REDIS_KEY_LOCK);
			}
		}
		// 改变数据库状态
		campaignDao.updateByPrimaryKeySelective(campaign);
	}
	
	/**
	 * 判断当前活动是否在投放定向时间内
	 * @return
	 * @throws Exception
	 */
	public Boolean isOnTargetTime(String campaignId) throws Exception {
		String currentWeek = DateUtils.getCurrentWeekInNumber();
		String currentHour = DateUtils.getCurrentHour();
		String current = "0" + currentWeek + currentHour;
		TimeTargetModelExample targetExample = new TimeTargetModelExample();
		targetExample.createCriteria().andCampaignIdEqualTo(campaignId).andTimeEqualTo(current);
		List<TimeTargetModel> targets = timeTargetDao.selectByExample(targetExample);
		
		return targets.size() > 0;
	}
	
	/**
	 * 判断当前活动是否在投放的日期内
	 * @param campaignId 活动id
	 * @return
	 * @throws Exception
	 */
	public Boolean isOnLaunchDate(String campaignId) throws Exception {
		Date current = new Date();
		CampaignModelExample campaignExample = new CampaignModelExample();
		campaignExample.createCriteria().andStartDateLessThanOrEqualTo(current)
			.andEndDateGreaterThanOrEqualTo(current)
			.andIdEqualTo(campaignId);
		List<CampaignModel> campaigns = campaignDao.selectByExample(campaignExample);
		return campaigns.size() > 0;				
	}
	
//	/**
//	 * 暂停指定的活动（重复指定的次数）
//	 * @param campaignId   需要暂停的活动ID
//	 * @return
//	 */
//	public boolean pauseCampaignRepeatable(String campaignId)
//    {
//        int i = 1;
//        int total = 10;
//        
//        while (i <= total)
//        {
//            // 读取key为dsp_groupids的value，即当前全部可投放的活动ID集合
//            String availableGroups = redisHelper.getStr(REDIS_KEY_GROUPIDS);
//
//            // 从JSON字符串中删除指定的活动ID
//            String operatedVal = removeCampaignId(availableGroups, campaignId);
//            if (operatedVal != null)
//            {
//                boolean casFlag = redisHelper.checkAndSet(REDIS_KEY_GROUPIDS, operatedVal);
//                if (casFlag)
//                {
//                    return true;
//                }
//            }
//            
//            i++;
//        }
//        
//        return false;
//    }
	
//	/**
//	 * 通过删除投放信息终止投放
//	 * @param campaignId 活动id
//	 * @return
//	 */
//	public boolean pauseLaunchByDelCampaignInfo(String campaignId)
//	{		
//		Jedis jedis = redisHelper.getJedis();
//		boolean delCampaignInfo = delCampaignInfo(jedis, campaignId);			
//		if(delCampaignInfo)
//		{
//			return true;
//		}	
//		
//		return false;		
//	}
	
//	/**
//     * 从Redis中删除Key为dsp_mapid_活动ID的键值对
//     * @param jedis         Redis连接实例
//     * @param campaignId    活动ID
//     * @return
//     */
//    private static boolean delCampaignInfo(Jedis jedis, String campaignId)
//    {
//        Long delResult= jedis.del(RedisKeyConstant.CAMPAIGN_INFO + campaignId);
//        
//        if (delResult == 1)
//        {
//            return true;
//        }
//        
//        return false;
//    }

    /**
     * 批量同步活动下创意
     * @param ids
     * @throws Exception
     */
    @Transactional
    public void synchronizeCreatives(String[] campaignIds) throws Exception {
    	// FIXME : 调用批量同步创意的方法
    	// 根据活动ID列表查询活动信息
		List<String> asList = Arrays.asList(campaignIds);
		CampaignModelExample ex = new CampaignModelExample();
		ex.createCriteria().andIdIn(asList);		
		List<CampaignModel> campaignInDB = campaignDao.selectByExample(ex);
		// 判断活动信息是否为空
		if (campaignInDB == null || campaignInDB.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		for (String campaignId : campaignIds) {
			//查询出活动下创意
			CreativeModelExample creativeExample = new CreativeModelExample();
			creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
			List<CreativeModel> creativeList = creativeDao.selectByExample(creativeExample);			
			for (CreativeModel creativeModel : creativeList) {
				String creativeId = creativeModel.getId();
				CreativeModel creative = creativeDao.selectByPrimaryKey(creativeId);
				if (creative == null) {
					throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
				}
				// 查询adx列表
				List<Map<String, String>> adxes = launchService.getAdxByCreative(creative);
				//同步结果
				for (Map<String, String> adx : adxes) {
					CreativeAuditModelExample creativeAuditExample = new CreativeAuditModelExample();
					creativeAuditExample.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(adx.get("adxId"));
					List<CreativeAuditModel> audits = creativeAuditDao.selectByExample(creativeAuditExample);
					if (audits == null || audits.isEmpty()) {
						throw new ThirdPartyAuditException();
					} else {
						String adxId = adx.get("adxId");
						if (AdxKeyConstant.ADX_MOMO_VALUE.equals(adxId)) {
							momoAuditService.synchronizeCreative(creativeId);
						}
						if (AdxKeyConstant.ADX_INMOBI_VALUE.equals(adxId)) {
							inmobiAuditService.synchronizeCreative(creativeId);
						}
					}
				}
			}
		}						
    }
    
    /**
     * 修改活动开始结束日期
     * @param id 活动id
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @throws Exception
     */
    @Transactional
    public void updateCampaignStartAndEndDate(String id,Date startDate,Date endDate) throws Exception{
		// 编辑的活动在数据库中不存在
		CampaignModel campaignInDB = campaignDao.selectByPrimaryKey(id);
		if (campaignInDB == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		// 监测日期范围是否正确
		if (startDate != null && endDate != null && startDate.after(endDate)) {
			throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_DATE_ERROR);
		}
		CampaignModel model = new CampaignModel();
		model.setStartDate(startDate);
		model.setEndDate(endDate);
		model.setId(id);
		//修改基本信息
		campaignDao.updateByPrimaryKeySelective(model);
		
		// FIXME : 重新查询campaignInDB
		// 修改redis中的信息
		String projectId = campaignInDB.getProjectId();
		ProjectModel project = projectDao.selectByPrimaryKey(projectId);
		if (!launchService.isHaveLaunched(id) && isOnLaunchDate(id)  &&
				StatusConstant.PROJECT_PROCEED.equals(project.getStatus())
				&& StatusConstant.CAMPAIGN_PROCEED.equals(campaignInDB.getStatus())) {
			launchService.write4FirstTime(campaignInDB);
			// FIXME : 在定向时间可以投放
			// 原来的状态、现在的状态---1.改了时间前后状态
		} else if (launchService.isHaveLaunched(id) && !isOnLaunchDate(id)) {
			launchService.remove4EndDate(campaignInDB);
		}
				
    }
    
//    /**
//     * 从正在投放的活动中删除指定的活动ID
//     * @param redisValue    Redis中Key为dsp_groups的Value
//     * @param campaignId    欲删除的活动ID
//     * @return
//     */
//    private static String removeCampaignId(String redisValue, String campaignId)
//    {
//        JsonParser jsonParser = new JsonParser();
//        JsonObject tmpObj = jsonParser.parse(redisValue).getAsJsonObject();
//        
//        if (tmpObj != null)
//        {
//            JsonArray groudids = tmpObj.get(JSON_KEY_GROUPIDS).getAsJsonArray();
//            
//            // 判断是否已经含有当前campaignID
//            for (int i = 0; i < groudids.size(); i++)
//            {
//                if (campaignId.equals(groudids.get(i).getAsString()))
//                {
//                    groudids.remove(i);
//                    break;
//                }
//            }
//            
//            return tmpObj.toString();
//        }
//        
//        return null;
//    }
    
}
