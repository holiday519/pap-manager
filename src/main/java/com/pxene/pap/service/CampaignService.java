package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.JsonArray;
import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.JedisUtils;
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
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
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

@Service
public class CampaignService extends LaunchService {
	
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
	private LaunchService launchService; 
	
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
		campaignModelExample = new CampaignModelExample();
		campaignModelExample.createCriteria().andProjectIdEqualTo(projectId);
		campaignModels = campaignDao.selectByExample(campaignModelExample);
		if (campaignModels != null && !campaignModels.isEmpty()) {
			for (CampaignModel model : campaignModels) {
				campaignBueget = campaignBueget + model.getTotalBudget();
			}
		}
		if (campaignBueget.compareTo(projectBudget) > 0) {
			throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_TOTAL_BUDGET_BIGGER_PROJECT);
		}
		
		String id = UUID.randomUUID().toString();
		bean.setId(id);
		CampaignModel campaignModel = modelMapper.map(bean, CampaignModel.class);
		
//		try {
//			
//		} catch (DuplicateKeyException exception) {
//        	throw new DuplicateEntityException();
//      }
		
		// 添加频次信息
		Frequency frequency = bean.getFrequency();
		if (frequency != null) {
			if (frequency.getControlObj() == null
					|| frequency.getNumber() == null
					|| StringUtils.isEmpty(frequency.getTimeType())) {
				throw new IllegalArgumentException("频次信息不全！");
			}
			FrequencyModel frequencyModel = modelMapper.map(frequency, FrequencyModel.class);
			String frequencyId = UUID.randomUUID().toString();
			frequencyModel.setId(frequencyId);
			frequencyDao.insertSelective(frequencyModel);
			campaignModel.setFrequencyId(frequencyId);
		}
		//添加点击、展现监测地址
		addCampaignMonitor(bean);
		campaignModel.setStatus(StatusConstant.CAMPAIGN_PAUSE);
		// 添加投放量控制策略
		addCampaignQuantity(bean);
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
			throw new ResourceNotFoundException();
		}
		bean.setId(id);//bean中放入ID，用于更新关联关系表中数据
		Integer campaignBueget = bean.getTotalBudget();//传值里的预算
		Integer dbBudget = campaignInDB.getTotalBudget();//数据库中预算

		//改变预算、展现时修改redis中的值
		changeRedisBudget(id, dbBudget, campaignBueget, bean.getQuantities());//改变日预算和总预算
		
		CampaignModel campaignModel = modelMapper.map(bean, CampaignModel.class);
		// 判断预算是否超出
		String projectId = bean.getProjectId();
		ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
		Integer projectBudget = projectModel.getTotalBudget();
		CampaignModelExample campaignModelExample = new CampaignModelExample();
		campaignModelExample.createCriteria().andProjectIdEqualTo(projectId).andIdNotEqualTo(bean.getId());
		List<CampaignModel> campaignModels = campaignDao.selectByExample(campaignModelExample);
		if (campaignModels != null && !campaignModels.isEmpty()) {
			for (CampaignModel model : campaignModels) {
				campaignBueget = campaignBueget + model.getTotalBudget();
			}
		}
		if (campaignBueget.compareTo(projectBudget) > 0) {
			throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_TOTAL_BUDGET_BIGGER_PROJECT);
		}
		
		// 编辑频次
		Frequency frequency = bean.getFrequency();
		String frequencyId = campaignInDB.getFrequencyId();
		if (frequency == null) {
			if (StringUtils.isEmpty(frequencyId)) { // 活动以前不存在频次，不动
				
			} else { // 活动以前存在频次，删除
				campaignModel.setFrequencyId("");
				frequencyDao.deleteByPrimaryKey(frequencyId);
			}
		} else {
			if (StringUtils.isEmpty(frequencyId)) { // 活动以前不存在频次，添加
				String fId = UUID.randomUUID().toString();
				frequency.setId(fId);
				FrequencyModel frequencyModel = modelMapper.map(frequency, FrequencyModel.class);
				frequencyDao.insertSelective(frequencyModel);
				campaignModel.setFrequencyId(fId);
			} else { // 活动以前存在频次，删除再添加
				frequencyDao.deleteByPrimaryKey(frequencyId);
				String fId = UUID.randomUUID().toString();
				frequency.setId(fId);
				FrequencyModel frequencyModel = modelMapper.map(frequency, FrequencyModel.class);
				frequencyDao.insertSelective(frequencyModel);
				campaignModel.setFrequencyId(fId);
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
		campaignDao.updateByPrimaryKeySelective(campaignModel);
		
	}
	
	/**
	 * 修改redis中活动总预算、日预算、日展现
	 * @param campaignId
	 * @param dbBudget
	 * @param newBueget
	 * @param quantities 
	 * @throws Exception
	 */
	public void changeRedisBudget (String campaignId, Integer dbBudget, Integer newBueget, Quantity[] quantities) throws Exception {
		String key = RedisKeyConstant.CAMPAIGN_BUDGET + campaignId;
		String countKey = RedisKeyConstant.CAMPAIGN_COUNTER + campaignId;
		if (JedisUtils.exists(key)) {
			Map<String, String> map = JedisUtils.hget(key);
			//修改redis中总预算值
			if (!StringUtils.isEmpty(map.get("total"))) {
				String total = map.get("total").toString();//redis里值
				Integer difVaue = (newBueget - dbBudget);//修改前后差值（新的减去旧的）
				if (difVaue < 0 && Math.abs(difVaue) > Float.parseFloat(total)) {//小于0时，并且redis中值不够扣除，抛出异常
						throw new IllegalArgumentException(PhrasesConstant.DIF_TOTAL_BIGGER_REDIS);
				}
				if (difVaue != 0) {
					JedisUtils.hincrbyFloat(key, "total", difVaue);
				}
			}
			//修改redis中的日预算值
			if (!StringUtils.isEmpty(map.get("daily")) && quantities != null) {
				String daily = map.get("daily").toString();//redis里值
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
				if (difVaue < 0 && Math.abs(difVaue) > Float.parseFloat(daily)) {//小于0时，并且redis中值不够扣除，抛出异常
						throw new IllegalArgumentException(PhrasesConstant.DIF_DAILY_BIGGER_REDIS);
				}
				if (difVaue != 0) {
					JedisUtils.hincrbyFloat(key, "total", difVaue);
				}
				//如果有日展现key
				if (JedisUtils.exists(countKey)) {
					Integer dayImpression  = JedisUtils.getInt(countKey);
					if (difImpVaue < 0 && Math.abs(difImpVaue) > dayImpression) {//小于0时，并且redis中值不够扣除，抛出异常
						throw new IllegalArgumentException(PhrasesConstant.DIF_IMPRESSION_BIGGER_REDIS);
					}
					if (difVaue != 0) {
						JedisUtils.incrybyInt(countKey, difImpVaue);
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
		if (StringUtils.isEmpty(map.get("action"))) {
			throw new IllegalArgumentException();
		}
		CampaignModel campaignModel = campaignDao.selectByPrimaryKey(id);
		if (campaignModel == null) {
			throw new ResourceNotFoundException();
		}
		
		String action = map.get("action").toString();
		if (StatusConstant.ACTION_TYPE_PAUSE.equals(action)) {
			pauseCampaign(id);
		} else if (StatusConstant.ACTION_TYPE_PROCEES.equals(action)) {
			launchCampaign(id);
		}else {
			throw new IllegalStatusException();
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
	}
	
	/**
	 * 添加活动定向
	 * @param bean
	 */
	@Transactional
	public void addCampaignTarget(CampaignTargetBean bean) throws Exception {
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
		String populationTarget = bean.getPopulationId();//人群
		if (regionTarget != null && regionTarget.length > 0) {
			RegionTargetModel region;
			for (String regionId : regionTarget) {
				region = new RegionTargetModel();
				region.setId(UUID.randomUUID().toString());
				region.setCampaignId(id);
				region.setRegionId(regionId);
				regionTargetDao.insertSelective(region);
			}
		}
		if (adTypeTarget != null && adTypeTarget.length > 0) {
			AdTypeTargetModel adType;
			for (String adTypeId : adTypeTarget) {
				adType = new AdTypeTargetModel();
				adType.setId(UUID.randomUUID().toString());
				adType.setCampaignId(id);
				adType.setAdType(adTypeId);
				adtypeTargetDao.insertSelective(adType);
			}
		}
		if (timeTarget != null && timeTarget.length > 0) {
			TimeTargetModel time;
			for (String timeId : timeTarget) {
				time = new TimeTargetModel();
				time.setId(UUID.randomUUID().toString());
				time.setCampaignId(id);
				time.setTime(timeId);
				timeTargetDao.insertSelective(time);
			}
		}
		if (networkTarget != null && networkTarget.length > 0) {
			NetworkTargetModel network;
			for (String networkid : networkTarget) {
				network = new NetworkTargetModel();
				network.setId(UUID.randomUUID().toString());
				network.setCampaignId(id);
				network.setNetwork(networkid);
				networkTargetDao.insertSelective(network);
			}
		}
		if (operatorTarget != null && operatorTarget.length > 0) {
			OperatorTargetModel operator;
			for (String operatorId : operatorTarget) {
				operator = new OperatorTargetModel();
				operator.setId(UUID.randomUUID().toString());
				operator.setCampaignId(id);
				operator.setOperator(operatorId);
				operatorTargetDao.insertSelective(operator);
			}
		}
		if (deviceTarget != null && deviceTarget.length > 0) {
			DeviceTargetModel device;
			for (String deviceId : deviceTarget) {
				device = new DeviceTargetModel();
				device.setId(UUID.randomUUID().toString());
				device.setCampaignId(id);
				device.setDevice(deviceId);
				deviceTargetDao.insertSelective(device);
			}
		}
		if (osTarget != null && osTarget.length > 0) {
			OsTargetModel os;
			for (String osId : osTarget) {
				os = new OsTargetModel();
				os.setId(UUID.randomUUID().toString());
				os.setCampaignId(id);
				os.setOs(osId);
				osTargetDao.insertSelective(os);
			}
		}
		if (brandTarget != null && brandTarget.length > 0) {
			BrandTargetModel brand;
			for (String brandId : brandTarget) {
				brand = new BrandTargetModel();
				brand.setId(UUID.randomUUID().toString());
				brand.setCampaignId(id);
				brand.setBrandId(brandId);
				brandTargetDao.insertSelective(brand);
			}
		}
		if (appTarget != null && appTarget.length > 0) {
			AppTargetModel app;
			for (String appId : appTarget) {
				app = new AppTargetModel();
				app.setId(UUID.randomUUID().toString());
				app.setCampaignId(id);
				app.setAppId(appId);
				appTargetDao.insertSelective(app);
			}
		}
		if (!StringUtils.isEmpty(populationTarget)) {
			PopulationTargetModel population = new PopulationTargetModel();
			population.setId(UUID.randomUUID().toString());
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
	public void deleteCampaignTarget(String campaignId)  throws Exception {
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
	public void addCampaignMonitor(CampaignBean bean) throws Exception {
		Monitor[] monitors = bean.getMonitors();
		if (monitors != null && monitors.length > 0) {
			String id = bean.getId();
			for (Monitor mnt : monitors) {
				String monitorId = UUID.randomUUID().toString();
				MonitorModel monitor = modelMapper.map(mnt, MonitorModel.class);
				monitor.setId(monitorId);
				monitor.setCampaignId(id);
				monitorDao.insertSelective(monitor);
			}
		}
	}
	
	/**
	 * 删除活动监测地址
	 * @param campaignId
	 */
	@Transactional
	public void deleteCampaignMonitor(String campaignId) throws Exception {
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
	public void addCampaignQuantity(CampaignBean bean) throws Exception {
		Quantity[] quantitys = bean.getQuantities();
		if (quantitys != null && quantitys.length > 0) {
			String id = bean.getId();
			Integer dailyBudget = null;
			Integer totalBudget = null;
			for (Quantity qt : quantitys) {
				dailyBudget = qt.getDailyBudget();
				totalBudget = bean.getTotalBudget();
				if (dailyBudget != null && totalBudget != null
						&& dailyBudget.compareTo(totalBudget) > 0) {
					throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_DAILY_BUDGET_BIGGER_TOTAL); 
				}
				QuantityModel model = modelMapper.map(qt, QuantityModel.class);
				model.setCampaignId(id);
				model.setId(UUID.randomUUID().toString());
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
	public void deleteCampaignQuantity(String campaignId) throws Exception {
		QuantityModelExample example = new QuantityModelExample();
		example.createCriteria().andCampaignIdEqualTo(campaignId);
		quantityDao.deleteByExample(example);
	}
	
	/**
	 * 删除频次信息
	 * @param campaignId
	 * @throws Exception
	 */
	@Transactional
	public void deletefrequency(String campaignId) throws Exception {
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
			throw new IllegalStatusException(PhrasesConstant.CAMPAIGN_HAS_CREATIVE);
		}
		//删除监测地址
		deleteCampaignMonitor(campaignId);
		//删除定向
		deleteCampaignTarget(campaignId);
		//删除频次信息
		deletefrequency(campaignId);
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
		if (campaignInDB ==null || campaignInDB.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		for (String campaignId : campaignIds) {
			//先查询出活动下创意
			CreativeModelExample creativeExample = new CreativeModelExample();
			creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
			List<CreativeModel> list = creativeDao.selectByExample(creativeExample);
			if (list != null && !list.isEmpty()) {
				throw new IllegalStatusException(PhrasesConstant.CAMPAIGN_HAS_CREATIVE);
			}
			//删除监测地址
			deleteCampaignMonitor(campaignId);
			//删除定向
			deleteCampaignTarget(campaignId);
			//删除频次信息
			deletefrequency(campaignId);
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
	public CampaignBean selectCampaign(String campaignId)  throws Exception {
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
	public List<CampaignBean> selectCampaigns(String name, String projectId, Long beginTime, Long endTime) throws Exception {
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
					target.setPopulationId(populationId);
				}
				/*if (!StringUtils.isEmpty(populationId)) {
					List<Population> PopulationList = new ArrayList<CampaignBean.Target.Population>();
					String[] popids = populationId.split(",");
					List<String> popIdList = Arrays.asList(popids);
					PopulationModelExample ex = new PopulationModelExample();
					ex.createCriteria().andIdIn(popIdList);
					List<PopulationModel> pops = populationDao.selectByExample(ex);
					if (pops != null && !pops.isEmpty()) {
						Population popu = null;
						for (PopulationModel p : pops) {
							popu = new Population();
							popu.setId(p.getId());
							popu.setPath(p.getPath());
							popu.setType(p.getType());
							PopulationList.add(popu);
						}
					}
					if (!PopulationList.isEmpty()) {
						Population[] ps = new Population[PopulationList.size()];
						for (int i=0;i<PopulationList.size();i++) {
							ps[i] = PopulationList.get(i);
						}
						target.setPopulation(ps);
					}
				}*/
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
	public void launchCampaign(String param) throws Exception {
		if (StringUtils.isEmpty(param)) {
			throw new ResourceNotFoundException();
		}
		String[] campaignIds = param.split(",");
		
		for (String campaignId : campaignIds) {
			CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
			//活动存在，并且可以投放
			if (campaignModel != null && checkCampaignCanLaunch(campaignId)) {
				campaignModel.setStatus(StatusConstant.CAMPAIGN_PROCEED);
				//投放
				String projectId = campaignModel.getProjectId();
				ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
				if (StatusConstant.PROJECT_PROCEED.equals(projectModel.getStatus())) {
					launch(campaignId);
				}
				//改变数据库状态
				campaignDao.updateByPrimaryKeySelective(campaignModel);
			}
		}
	}
	 
	/**
	 * 检查活动是否可以投放
	 * @param campaignId
	 * @return true：可投放；false：不可投放
	 * @throws Exception
	 */
	public boolean checkCampaignCanLaunch(String campaignId) throws Exception {
		// 检查是否有落地页
		CampaignModel model = campaignDao.selectByPrimaryKey(campaignId);
		if (model != null) {
			String landpageId = model.getLandpageId();
			if (StringUtils.isEmpty(landpageId)) {
				throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_NO_LANDPAGE);
			}
		} else {
			return false;
		}
		// 检查是否有创意
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> creatives = creativeDao.selectByExample(creativeExample);
		if (creatives == null || creatives.isEmpty()) {
			throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_NO_CREATIE);
		}
		//检查创意状态是否有审核通过的
		boolean flag = false;
		for (CreativeModel cm : creatives) {
			String cId = cm.getId();
			if (!StatusConstant.CREATIVE_AUDIT_SUCCESS.equals(creativeService.getCreativeAuditStatus(cId))) {
				flag = true;
				break;
			}
		}
		if (flag) {
			throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_NO_PASS_CREATIE);
		} else {
			return true;
		}
	}
	
	/**
	 * 按照活动暂停
	 * @param campaignIds
	 * @throws Exception
	 */
	@Transactional
	public void pauseCampaign(String param) throws Exception {
		if (StringUtils.isEmpty(param)) {
			throw new ResourceNotFoundException();
		}
		String[] campaignIds = param.split(",");
		
		for (String campaignId : campaignIds) {
			CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
			if (campaignModel != null) {
				campaignModel.setStatus(StatusConstant.CAMPAIGN_PAUSE);
				String projectId = campaignModel.getProjectId();
				ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
				if (StatusConstant.PROJECT_PROCEED.equals(projectModel.getStatus())) {
					//移除redis中key
					pause(campaignId);
				}
				//改变数据库状态
				campaignDao.updateByPrimaryKeySelective(campaignModel);
			}
		}
	}
	
}
