package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

/*import org.hibernate.annotations.common.util.impl.Log_.logger;*/
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.JsonArray;
import com.mysql.jdbc.log.LogFactory;
import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.RedisHelper;
import com.pxene.pap.common.UUIDGenerator;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.RedisKeyConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.BasicDataBean;
import com.pxene.pap.domain.beans.CampaignBean;
import com.pxene.pap.domain.beans.CampaignBean.Frequency;
import com.pxene.pap.domain.beans.CampaignBean.Monitor;
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
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.CreativeModelExample;
import com.pxene.pap.domain.models.DeviceTargetModel;
import com.pxene.pap.domain.models.DeviceTargetModelExample;
import com.pxene.pap.domain.models.FrequencyModel;
import com.pxene.pap.domain.models.LandpageModel;
import com.pxene.pap.domain.models.MonitorModel;
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
import com.pxene.pap.repository.basic.AdTypeTargetDao;
import com.pxene.pap.repository.basic.AppDao;
import com.pxene.pap.repository.basic.AppTargetDao;
import com.pxene.pap.repository.basic.BrandTargetDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.DeviceTargetDao;
import com.pxene.pap.repository.basic.FrequencyDao;
import com.pxene.pap.repository.basic.LandpageDao;
import com.pxene.pap.repository.basic.MonitorDao;
import com.pxene.pap.repository.basic.NetworkTargetDao;
import com.pxene.pap.repository.basic.OperatorTargetDao;
import com.pxene.pap.repository.basic.OsTargetDao;
import com.pxene.pap.repository.basic.PopulationDao;
import com.pxene.pap.repository.basic.PopulationTargetDao;
import com.pxene.pap.repository.basic.ProjectDao;
import com.pxene.pap.repository.basic.QuantityDao;
import com.pxene.pap.repository.basic.RegionDao;
import com.pxene.pap.repository.basic.RegionTargetDao;
import com.pxene.pap.repository.basic.TimeTargetDao;
import com.pxene.pap.repository.basic.view.CampaignTargetDao;

/*import org.apache.log4j.Logger;*/  
import redis.clients.jedis.Jedis;

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
	private PopulationDao populationDao;
	
	@Autowired
	private CampaignTargetDao campaignTargetDao;
	
	@Autowired
	private LandpageDao LandpageDao;
	
	@Autowired
	private QuantityDao quantityDao;
	
	@Autowired
	private LaunchService launchService;
	
	private RedisHelper redisHelper;
	
//	private static final String REDIS_KEY_GROUPIDS = "dsp_groupids";
//	
//	private static final String REDIS_KEY_GROUPINFO = "dsp_groupid_info_";
//	
//	private static final String JSON_KEY_GROUPIDS = "groupids";
	
	//Logger log = Logger.getLogger("CampaignService"); 
	
	
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
    	// 监测日期范围是否正确
		Date startDate = bean.getStartDate();
	    Date endDate = bean.getEndDate();
	    if (startDate != null && endDate != null && startDate.after(endDate)) {
            throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_DATE_ERROR);
	    }
		// 判断预算是否超出
		String projectId = bean.getProjectId();
		ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
		Integer projectBudget = projectModel.getTotalBudget();
		Integer campaignBueget = bean.getTotalBudget();
//		campaignModelExample = new CampaignModelExample();
		campaignModelExample.clear();
		campaignModelExample.createCriteria().andProjectIdEqualTo(projectId);
		campaignModels = campaignDao.selectByExample(campaignModelExample);
		if (campaignModels != null && !campaignModels.isEmpty()) {
			for (CampaignModel model : campaignModels) {
				campaignBueget = campaignBueget + model.getTotalBudget();
			}
		}
		if (campaignBueget.compareTo(projectBudget) > 0) {
			throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_ALL_BUDGET_OVER_PROJECT);
		}
		
		//String id = UUID.randomUUID().toString(); 
		String id = UUIDGenerator.getUUID();
		bean.setId(id);
		CampaignModel campaignModel = modelMapper.map(bean, CampaignModel.class);
		
		// 添加频次信息
		String frequencyId = addFrequency(bean);
		if (!StringUtils.isEmpty(frequencyId)) {
			campaignModel.setFrequencyId(frequencyId);
		}
		//添加点击、展现监测地址
		addCampaignMonitor(bean);
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
		
		// 监测日期范围是否正确
		Date startDate = bean.getStartDate();
		Date endDate = bean.getEndDate();
		if (startDate != null && endDate != null && startDate.after(endDate)) {
			throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_DATE_ERROR);
		}	
		
		// bean中放入ID，用于更新关联关系表中数据
		bean.setId(id);
		
		// 传值里的预算
		Integer campaignBudget = bean.getTotalBudget();
		
		// 数据库中预算
		Integer dbBudget = campaignInDB.getTotalBudget();
		
		// 判断预算是否超出
		String projectId = bean.getProjectId();

		// 获得数据库中该活动隶属项目的项目总预算
		ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
		Integer projectBudget = projectModel.getTotalBudget();
		
		// 获得当前项目除本活动之外的全部活动已占用多少预算
		int campaignBudgetOthers = 0;
		CampaignModelExample campaignExample = new CampaignModelExample();
		campaignExample.createCriteria().andProjectIdEqualTo(projectId).andIdNotEqualTo(bean.getId());
		List<CampaignModel> campaigns = campaignDao.selectByExample(campaignExample);
		
		if (campaigns != null && !campaigns.isEmpty()) 
		{
			for (CampaignModel campaign : campaigns) 
			{
			    campaignBudgetOthers += campaign.getTotalBudget();
			}
		}
		
		// 如果修改后的预算 + 其他活动预算大于总预算，则抛出异常
		if (campaignBudget + campaignBudgetOthers > projectBudget) 
		{
			throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_ALL_BUDGET_OVER_PROJECT);
		}
		
		// 改变预算、展现时修改redis中的值
		changeRedisBudget(id, dbBudget, campaignBudget, bean.getQuantities());//改变日预算和总预算
		
		CampaignModel campaign = modelMapper.map(bean, CampaignModel.class);
		// 编辑频次
		Frequency frequency = bean.getFrequency();
		String frequencyId = campaignInDB.getFrequencyId();
		if (frequency == null) {
			if (StringUtils.isEmpty(frequencyId)) { // 活动以前不存在频次，不动
				
			} else { // 活动以前存在频次，删除
				campaign.setFrequencyId("");
				frequencyDao.deleteByPrimaryKey(frequencyId);
			}
		} else {
			if (StringUtils.isEmpty(frequencyId)) { // 活动以前不存在频次，添加  
				//String fId = UUID.randomUUID().toString();
				String fId =UUIDGenerator.getUUID();
				frequency.setId(fId);
				FrequencyModel frequencyModel = modelMapper.map(frequency, FrequencyModel.class);
				frequencyDao.insertSelective(frequencyModel);
				campaign.setFrequencyId(fId);
			} else { // 活动以前存在频次，删除再添加
				frequencyDao.deleteByPrimaryKey(frequencyId);
				//String fId = UUID.randomUUID().toString();
				String fId =UUIDGenerator.getUUID();
				frequency.setId(fId);
				FrequencyModel frequencyModel = modelMapper.map(frequency, FrequencyModel.class);
				frequencyDao.insertSelective(frequencyModel);
				campaign.setFrequencyId(fId);
			}
		}
		
		//Date startDate = bean.getStartDate();
		//Date endDate = bean.getEndDate();
		Date current = new Date();
		if (current.after(endDate)) {
			//launchService.removeCampaignId(id);
			//将不在满足条件的活动将其活动id从redis的groupids中删除--停止投放
			boolean removeResult = launchService.pauseCampaignRepeatable(id);
			LOGGER.info("chaxunPauseTrue." + id);
			if (!removeResult) {	
				//如果尝试多次不能将不满足条件的活动id从redis的groupids中删除，则删除该活动在redis中的活动信息--停止投放
				/*pauseLaunchByDelCampaignInfo(id);*/
				LOGGER.info("chaxunPauseFalse." + id);
				throw new ServerFailureException(PhrasesConstant.REDIS_KEY_LOCK);
				
			}
		}
		
		//删除点击、展现监测地址
		deleteCampaignMonitor(id);
		//重新添加点击、展现监测地址
		addCampaignMonitor(bean);
		//删除投放量控制策略
		deleteCampaignQuantity(id);
		//添加投放量控制策略
		addCampaignQuantity(bean);
		//修改基本信息
		campaignDao.updateByPrimaryKeySelective(campaign);
		
	}
	
	/**
	 * 修改redis中活动总预算、日预算、日展现
	 * @param campaignId
	 * @param dbBudget
	 * @param newBueget
	 * @param quantities 
	 * @throws Exception
	 */
	private void changeRedisBudget(String campaignId, Integer dbBudget, Integer newBueget, Quantity[] quantities) throws Exception {
		String budgetKey = RedisKeyConstant.CAMPAIGN_BUDGET + campaignId;
		String countKey = RedisKeyConstant.CAMPAIGN_COUNTER + campaignId;
		if (redisHelper.exists(budgetKey)) {
			Map<String, String> map = redisHelper.hget(budgetKey);
			// 修改redis中总预算值
			if (!StringUtils.isEmpty(map.get("total"))) {
				// redis里值
				String total = map.get("total");
				Float totalFloat = Float.parseFloat(total) / 100; // Redis中保存的费用单位是分，MySQL中保存的费用是元
				Integer difVaue = (newBueget - dbBudget);//修改前后差值（新的减去旧的）
				if (difVaue < 0 && Math.abs(difVaue) > totalFloat) {//小于0时，并且redis中值不够扣除，抛出异常
					throw new IllegalArgumentException(PhrasesConstant.DIF_TOTAL_BIGGER_REDIS);
				}
				if (difVaue != 0) {
					redisHelper.hincrbyFloat(budgetKey, "total", difVaue * 100);
				}
			}
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
		String action = map.get("action");
		
		if (StringUtils.isEmpty(action)) {
			throw new IllegalArgumentException();
		}
		CampaignModel campaignModel = campaignDao.selectByPrimaryKey(id);
		if (campaignModel == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		
		if (StatusConstant.ACTION_TYPE_PAUSE.equals(action)) {
			pauseCampaign(campaignModel);
		} else if (StatusConstant.ACTION_TYPE_PROCEES.equals(action)) {
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
		//删除掉定向
		deleteCampaignTarget(id);
		//添加定向
		addCampaignTarget(bean);
		//修改定向时更新redis中活动定向信息
		if (launchService.isFirstLaunch(id)) {
			launchService.writeCampaignTarget(id);
			// 先移除以前的白名单
			launchService.removeWhiteBlack(id);
			// 在添加最新的白名单
			launchService.writeWhiteBlack(id);
		}
		//编辑定向时间可添加、删除redis中的对应的groupids
		CampaignModel campaignModel = campaignDao.selectByPrimaryKey(id);
		String projectId = campaignModel.getProjectId();
		ProjectModel project = projectDao.selectByPrimaryKey(projectId);
		/*if (StatusConstant.PROJECT_PROCEED.equals(project.getStatus()) && StatusConstant.CAMPAIGN_PROCEED.equals(campaignModel.getStatus()) &&
		isOnLaunchDate(id) && isOnTargetTime(id){*/
		if (StatusConstant.PROJECT_PROCEED.equals(project.getStatus()) && StatusConstant.CAMPAIGN_PROCEED.equals(campaignModel.getStatus()) &&
				isOnLaunchDate(id) && isOnTargetTime(id) && launchService.dailyBudgetJudge(id)
				&& launchService.dailyCounterJudge(id)) {
			//在项目开启、活动开启并且在投放的时间里，修改定向时间在定向时间里，将活动ID写入redis
			//活动没有超出每天的日预算并且日均最大展现未达到上限
			//launchService.writeCampaignId(id);
			boolean writeResult = launchService.launchCampaignRepeatable(id);
			LOGGER.info("chaxunLaunchTrue." + id);
			if(!writeResult){
				LOGGER.info("chaxunLaunchFalse." + id);
				throw new ServerFailureException(PhrasesConstant.REDIS_KEY_LOCK);				
			}
		}else{
			//否则将活动ID移除redis
			//launchService.removeCampaignId(id);
			//将不在满足条件的活动将其活动id从redis的groupids中删除--停止投放
			boolean removeResult = launchService.pauseCampaignRepeatable(id);
			LOGGER.info("chaxunPauseTrue." + id);
			if (!removeResult) {
				//如果尝试多次不能将不满足条件的活动id从redis的groupids中删除，则删除该活动在redis中的活动信息--停止投放
				//pauseLaunchByDelCampaignInfo(id);
				LOGGER.info("chaxunPauseFalse." + id);
				throw new ServerFailureException(PhrasesConstant.REDIS_KEY_LOCK);
			}
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
				//region.setId(UUID.randomUUID().toString());
				region.setId(UUIDGenerator.getUUID());
				region.setCampaignId(id);
				region.setRegionId(regionId);
				regionTargetDao.insertSelective(region);
			}
		}
		if (adTypeTarget != null && adTypeTarget.length > 0) {
			AdTypeTargetModel adType = new AdTypeTargetModel();
			for (String adTypeId : adTypeTarget) {
				//adType.setId(UUID.randomUUID().toString());
				adType.setId(UUIDGenerator.getUUID());
				adType.setCampaignId(id);
				adType.setAdType(adTypeId);
				adtypeTargetDao.insertSelective(adType);
			}
		}
		if (timeTarget != null && timeTarget.length > 0) {
			TimeTargetModel time = new TimeTargetModel();
			for (String timeId : timeTarget) {
				//time.setId(UUID.randomUUID().toString());
				time.setId(UUIDGenerator.getUUID());
				time.setCampaignId(id);
				time.setTime(timeId);
				timeTargetDao.insertSelective(time);
			}
		}
		if (networkTarget != null && networkTarget.length > 0) {
			NetworkTargetModel network = new NetworkTargetModel();
			for (String networkid : networkTarget) {
				//network.setId(UUID.randomUUID().toString());
				network.setId(UUIDGenerator.getUUID());
				network.setCampaignId(id);
				network.setNetwork(networkid);
				networkTargetDao.insertSelective(network);
			}
		}
		if (operatorTarget != null && operatorTarget.length > 0) {
			OperatorTargetModel operator = new OperatorTargetModel();
			for (String operatorId : operatorTarget) {
				//operator.setId(UUID.randomUUID().toString());
				operator.setId(UUIDGenerator.getUUID());
				operator.setCampaignId(id);
				operator.setOperator(operatorId);
				operatorTargetDao.insertSelective(operator);
			}
		}
		if (deviceTarget != null && deviceTarget.length > 0) {
			DeviceTargetModel device = new DeviceTargetModel();
			for (String deviceId : deviceTarget) {
				/*device.setId(UUID.randomUUID().toString());*/
				device.setId(UUIDGenerator.getUUID());
				device.setCampaignId(id);
				device.setDevice(deviceId);
				deviceTargetDao.insertSelective(device);
			}
		}
		if (osTarget != null && osTarget.length > 0) {
			OsTargetModel os = new OsTargetModel();
			for (String osId : osTarget) {
				/*os.setId(UUID.randomUUID().toString());*/
				os.setId(UUIDGenerator.getUUID());
				os.setCampaignId(id);
				os.setOs(osId);
				osTargetDao.insertSelective(os);
			}
		}
		if (brandTarget != null && brandTarget.length > 0) {
			BrandTargetModel brand = new BrandTargetModel();
			for (String brandId : brandTarget) {
				/*brand.setId(UUID.randomUUID().toString());*/
				brand.setId(UUIDGenerator.getUUID());
				brand.setCampaignId(id);
				brand.setBrandId(brandId);
				brandTargetDao.insertSelective(brand);
			}
		}
		if (appTarget != null && appTarget.length > 0) {
			AppTargetModel app = new AppTargetModel();
			for (String appId : appTarget) {
				/*app.setId(UUID.randomUUID().toString());*/
				app.setId(UUIDGenerator.getUUID());
				app.setCampaignId(id);
				app.setAppId(appId);
				appTargetDao.insertSelective(app);
			}
		}
		if (!StringUtils.isEmpty(populationTarget)) {
			PopulationTargetModel population = new PopulationTargetModel();
			/*population.setId(UUID.randomUUID().toString());*/
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
	private void deleteCampaignTarget(String campaignId)  throws Exception {
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
		OperatorTargetModelExample op = new OperatorTargetModelExample();
		op.createCriteria().andCampaignIdEqualTo(campaignId);
		operatorTargetDao.deleteByExample(op);
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
	@Transactional
	private void addCampaignMonitor(CampaignBean campaignBean) throws Exception {
		Monitor[] monitors = campaignBean.getMonitors();
		if (monitors != null && monitors.length > 0) {
			String id = campaignBean.getId();
			for (Monitor bean : monitors) {
				/*String monitorId = UUID.randomUUID().toString();*/
				String monitorId = UUIDGenerator.getUUID();
				MonitorModel model = modelMapper.map(bean, MonitorModel.class);
				model.setId(monitorId);
				model.setCampaignId(id);
				monitorDao.insertSelective(model);
			}
		}
	}
	
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
		Quantity[] quantitys = campaignBean.getQuantities();			
		if (quantitys != null && quantitys.length > 0) {
			String id = campaignBean.getId();
			for (Quantity bean : quantitys) {
				Integer dailyBudget = bean.getDailyBudget();
				Integer totalBudget = campaignBean.getTotalBudget();
				if (dailyBudget != null && totalBudget != null && dailyBudget.compareTo(totalBudget) > 0) {
					throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_DAILY_BUDGET_OVER_TOTAL); 
				}
				QuantityModel model = modelMapper.map(bean, QuantityModel.class);
				model.setCampaignId(id);
				/*model.setId(UUID.randomUUID().toString());*/
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
			/*String frequencyId = UUID.randomUUID().toString();*/
			String frequencyId = UUIDGenerator.getUUID();
			frequencyModel.setId(frequencyId);
			frequencyDao.insertSelective(frequencyModel);
			return frequencyId;
		}
		return "";
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
		deleteCampaignMonitor(campaignId);
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
			deleteCampaignMonitor(campaignId);
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
	public CampaignBean getCampaign(String campaignId)  throws Exception {
		CampaignModel model = campaignDao.selectByPrimaryKey(campaignId);
		if (model ==null || StringUtils.isEmpty(model.getId())) {
        	throw new ResourceNotFoundException();
        }
		CampaignBean map = modelMapper.map(model, CampaignBean.class);
		addParamToCampaign(map, model.getId(), model.getFrequencyId());
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
				//查询每个活动的投放信息
				getData(beginTime, endTime, map);
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
//				target.setRegion(formatTargetStringToArray(campaignTargetModel.getRegionId()));
				target.setAdType(formatTargetStringToArray(campaignTargetModel.getAdType()));
				target.setTime(formatTargetStringToArray(campaignTargetModel.getTimeId()));
				target.setNetwork(formatTargetStringToArray(campaignTargetModel.getNetwork()));
				target.setOperator(formatTargetStringToArray(campaignTargetModel.getOperator()));
				target.setDevice(formatTargetStringToArray(campaignTargetModel.getDevice()));
				target.setOs(formatTargetStringToArray(campaignTargetModel.getOs()));
				target.setBrand(formatTargetStringToArray(campaignTargetModel.getBrandId()));
//				target.setApp(formatTargetStringToArray(campaignTargetModel.getAppId()));
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
//				String[] regionArray = formatTargetStringToArray(campaignTargetModel.getRegionId());
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
		// 查询检测地址
		MonitorModelExample monitorModelExample = new MonitorModelExample();
		monitorModelExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<MonitorModel> monitorModels = monitorDao.selectByExample(monitorModelExample);
		// 如果没有查到点击展现地址数据，直接返回
		if (monitorModels != null && !monitorModels.isEmpty()) {
			Monitor[] monitors = new Monitor[monitorModels.size()];
			if (monitors != null) {
				for (int i=0; i<monitorModels.size(); i++) {
					MonitorModel model = monitorModels.get(i);
					Monitor monitor = modelMapper.map(model, Monitor.class);
					monitors[i] = monitor;
				}
				bean.setMonitors(monitors);
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
		//检查创意状态是否有审核通过的
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
			if (launchService.isFirstLaunch(campaignId)) {
				launchService.write4FirstTime(campaign);
			}
			/*if (isOnTargetTime(campaignId){*/
			if (isOnTargetTime(campaignId) && launchService.dailyBudgetJudge(campaignId)
					&& launchService.dailyCounterJudge(campaignId) ) {
				//在定向时间里、活动没有超出每天的日预算并且日均最大展现未达到上限
				//launchService.writeCampaignId(campaignId);
				boolean writeResult = launchService.launchCampaignRepeatable(campaignId);
				LOGGER.info("chaxunLaunchTrue." + campaignId);
				if(!writeResult){
					LOGGER.info("chaxunLaunchFalse." + campaignId);
					throw new ServerFailureException(PhrasesConstant.REDIS_KEY_LOCK);
				}
			}
		}		
		//改变数据库状态
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
			//移除redis中key
			//launchService.removeCampaignId(campaign.getId());
			//将不在满足条件的活动将其活动id从redis的groupids中删除--停止投放
			boolean removeResult = launchService.pauseCampaignRepeatable(campaign.getId());
			LOGGER.info("chaxunPauseTrue." + campaign.getId());
			if (!removeResult) {
				LOGGER.info("chaxunPauseFalse." + campaign.getId());
				throw new ServerFailureException(PhrasesConstant.REDIS_KEY_LOCK);
			}
		}
		//改变数据库状态
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
	
	/**
	 * 通过删除投放信息终止投放
	 * @param campaignId 活动id
	 * @return
	 */
	public boolean pauseLaunchByDelCampaignInfo(String campaignId)
	{		
		Jedis jedis = redisHelper.getJedis();
		boolean delCampaignInfo = delCampaignInfo (jedis, campaignId);			
		if(delCampaignInfo)
		{
			return true;
		}	
		
		return false;		
	}
	
	/**
     * 从Redis中删除Key为dsp_mapid_活动ID的键值对
     * @param jedis         Redis连接实例
     * @param campaignId    活动ID
     * @return
     */
    private static boolean delCampaignInfo(Jedis jedis, String campaignId)
    {
        Long delResult= jedis.del(RedisKeyConstant.CAMPAIGN_INFO + campaignId);
        
        if (delResult == 1)
        {
            return true;
        }
        
        return false;
    }

    
//    /**
//     * 从正在投放的活动中删除指定的活动ID。
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
