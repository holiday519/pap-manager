package com.pxene.pap.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.RedisHelper;
import com.pxene.pap.common.UUIDGenerator;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.RedisKeyConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.CampaignBean;
import com.pxene.pap.domain.beans.CampaignBean.Frequency;
import com.pxene.pap.domain.beans.CampaignBean.Quantity;
import com.pxene.pap.domain.beans.CampaignBean.Target;
import com.pxene.pap.domain.beans.CampaignBean.Target.App;
import com.pxene.pap.domain.beans.CampaignBean.Target.Region;
import com.pxene.pap.domain.beans.CampaignScoreBean;
import com.pxene.pap.domain.beans.CampaignTargetBean;
import com.pxene.pap.domain.beans.CampaignTargetBean.Population;
import com.pxene.pap.domain.beans.PopulationTargetBean;
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
import com.pxene.pap.domain.models.LandpageCodeHistoryModel;
import com.pxene.pap.domain.models.LandpageCodeHistoryModelExample;
import com.pxene.pap.domain.models.LandpageCodeModel;
import com.pxene.pap.domain.models.LandpageCodeModelExample;
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
import com.pxene.pap.repository.basic.AdTypeTargetDao;
import com.pxene.pap.repository.basic.AppDao;
import com.pxene.pap.repository.basic.AppTargetDao;
import com.pxene.pap.repository.basic.BrandTargetDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeAuditDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.DeviceTargetDao;
import com.pxene.pap.repository.basic.FrequencyDao;
import com.pxene.pap.repository.basic.LandpageCodeDao;
import com.pxene.pap.repository.basic.LandpageCodeHistoryDao;
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CampaignService.class);
	
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
	
	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private LandpageCodeHistoryDao landpageCodeHistoryDao;
	
	@Autowired
	private LandpageCodeDao landpageCodeDao;
	
	@Autowired
	private DataService dataService;
	
	@Autowired
	private LandpageService landpageService;
	
	@Autowired
	private RedisHelper redisHelper;

	
	private static JsonParser parser = new JsonParser();
	
	@PostConstruct
    public void selectRedis()
    {
	    redisHelper.select("redis.primary.");
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
		CampaignModelExample campaignExample = new CampaignModelExample();
		campaignExample.createCriteria().andNameEqualTo(bean.getName());
		List<CampaignModel> campaignModels = campaignDao.selectByExample(campaignExample);
		if (campaignModels != null && !campaignModels.isEmpty()) {
			throw new IllegalArgumentException(PhrasesConstant.NAME_NOT_REPEAT);
		}
		// 判断项目信息是否为空，项目信息为空则不能创建活动
		ProjectModel projectModel = projectDao.selectByPrimaryKey(bean.getProjectId());
		if (projectModel == null) {
			throw new IllegalArgumentException(PhrasesConstant.PROJECT_INFO_NULL);
		}
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
	    
	    // 活动开始时间不能在今天之前
	 	checkStartDate(startDate);
		
		// 活动的日预算不能大于项目预算
		Integer projectBudget = projectModel.getTotalBudget();
		Integer dailyBudget;
		Quantity[] quantities = bean.getQuantities();
		if (quantities != null && quantities.length > 0) {
			for (Quantity quantitie : quantities) {
				// 日预算
				dailyBudget = quantitie.getBudget();
				if (dailyBudget != null && dailyBudget.compareTo(projectBudget) > 0) {
					// 如果日预算大于项目总预算
					throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_DAILY_BUDGET_OVER_PROJECT);
				}
			}

		}
		
		// 生成UUID
		String id = UUIDGenerator.getUUID();		
		
	    // 添加活动信息	    		
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
		
		
		// 活动写入redis
		launchService.writeCampaignId4Project(bean.getId(), bean.getProjectId());
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
				// 验证名称重复，排除自己使得同一活动可用字母大小写不同的同一名称
				CampaignModelExample campaignExample = new CampaignModelExample();
				campaignExample.createCriteria().andNameEqualTo(name).andIdNotEqualTo(id);
				List<CampaignModel> campaigns = campaignDao.selectByExample(campaignExample);
				if (campaigns != null && !campaigns.isEmpty()) {
					throw new DuplicateEntityException(PhrasesConstant.NAME_NOT_REPEAT);					  
				}				
			}
		}
		
		// 判断项目信息是否为空，项目信息为空则不能创建活动
		ProjectModel projectModel = projectDao.selectByPrimaryKey(bean.getProjectId());
		if (projectModel == null) {
			throw new IllegalArgumentException(PhrasesConstant.PROJECT_INFO_NULL);
		}
		
		// 判断落地页信息是否为空，落地页信息为空则不能创建活动
		String beanLandpageId = bean.getLandpageId(); // 页面出来的落地页id
		String dbLandpageId = campaignInDB.getLandpageId(); // 编辑活动更改数据库前数据库里的落地页Id
		LandpageModel landpageModel = landpageDao.selectByPrimaryKey(beanLandpageId);
		if (landpageModel == null) {
			throw new IllegalArgumentException(PhrasesConstant.LANDPAGE_INFO_NULL);
		}
		
		// 监测日期范围是否正确
		Date startDate = bean.getStartDate();
		Date endDate = bean.getEndDate();
		if (startDate != null && endDate != null && startDate.after(endDate)) {
			throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_DATE_ERROR);
		}
		
		// 判断活动的开始时间和结束时间是否在今天之前
		Date current = new Date();                        // 当前时间
		Date startDateInDB = campaignInDB.getStartDate(); // 修改时间之前的活动开始时间
		Date endDateInDB = campaignInDB.getEndDate();     // 修改时间之后的活动结束时间
		if (startDateInDB.after(current)) {
			// 如果活动未开始，判断欲修改的活动开始时间是否在今天之前
			checkStartDate(startDate);
		} else {
			// 如果活动在投放中或已完成，判断欲修改的活动开始时间是否在今天之前
			if (!startDate.equals(startDateInDB)) {
				// 如果活动的开始时间有改变
				checkStartDate(startDate);
			}			
			// 如果活动在投放中或已完成，判断欲修改的活动结束时间是否在今天之前
			if (!(endDateInDB.before(current) && endDate.equals(endDateInDB))) {
				// ！（活动已完成  && 结束时间不变）
				checkEndDate(endDate);
			}			
		}
		
		// bean中放入ID，用于更新关联关系表中数据
		bean.setId(id);					
		
		// 每天的日预算不能大于项目的总预算
		int dailyBudget = 0;
		Quantity[] quantities = bean.getQuantities();
		// 项目总预算
		int projectBudget = projectModel.getTotalBudget();
		if (quantities != null && quantities.length > 0) {
			for (Quantity quantitie : quantities) {
				// 传值里的日预算
				dailyBudget = quantitie.getBudget();
				if (dailyBudget - projectBudget > 0) {
					// 如果日预算大于项目总预算
					throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_DAILY_BUDGET_OVER_PROJECT);
				}
			}
		}
						
		// 判断落地页监测码是否被其他开启并且未结束的活动使用
		if (StatusConstant.CAMPAIGN_PROCEED.equals(campaignInDB.getStatus()) 
				&& (!(startDate.after(startDateInDB) && endDate.before(endDateInDB))
				|| isChangeCode(dbLandpageId, beanLandpageId))) {
			// 如果活动打开 && （新的活动时间范围不在旧的活动时间范围 || 监测码发生改变）
			LandpageCodeModelExample example = new LandpageCodeModelExample();
			example.createCriteria().andLandpageIdEqualTo(beanLandpageId);
			List<LandpageCodeModel> codes = landpageCodeDao.selectByExample(example);
			for (LandpageCodeModel code : codes) {
				// 判断监测码是否被占用
				String usedCodeCampaignId = checkUsedLandpageCode(code.getCode(), startDate, endDate, id);
				if (usedCodeCampaignId != null) {
					// 如果返回不为空，则说明监测码被占用
					String campaignName = getNameByCampaignId(usedCodeCampaignId);;
					throw new IllegalStatusException(campaignName + "，" + PhrasesConstant.LANDPAGE_CODE_USED);
				}
			}
		}
								
		// 编辑活动时判断是否已经投放过，即不是第一次投放修改redis中相关的信息
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
		
		
		// 更新监测码历史记录表
		if (StatusConstant.CAMPAIGN_PROCEED.equals(bean.getStatus()) 
				&& isChangeCode(dbLandpageId, beanLandpageId)
				&& (!startDateInDB.equals(startDate) || !endDateInDB.equals(endDate))) {
			// 如果活动打开 && 活动周期改变（活动的开始时间改变 || 活动的结束时间改变）  && code改变
			if (startDateInDB.after(current)) {
				// 活动未开始
				// 删除监测码历史记录表中原来的记录
				landpageService.deleteCodeHistoryInfo(id);
				// 重新插入一条记录
				landpageService.creativeCodeHistoryInfo(id, beanLandpageId, startDate, endDate);			
			} else if (startDateInDB.before(current) && endDateInDB.after(current)) {
				// 活动已开始并且未结束
				// 编辑监测码历史记录表中原记录的结束时间为当前时间
				landpageService.updateCodeHistoryEndTime(id);
				// 重新插入一条监测码使用记录
				landpageService.creativeCodeHistoryInfo(id, beanLandpageId, startDate, endDate);						
			} else {
				// 活动已结束，插入一条新的记录
				landpageService.creativeCodeHistoryInfo(id, beanLandpageId, startDate, endDate);				
			}
		} else if (StatusConstant.CAMPAIGN_PROCEED.equals(bean.getStatus()) 
				&& isChangeCode(dbLandpageId, beanLandpageId)) {
			// 如果活动打开  && 活动周期不变 （/code改变）
			if (startDateInDB.after(current)) {
				// 活动未开始
				// 删除监测码历史记录表中原来的记录
				landpageService.deleteCodeHistoryInfo(id);
				// 重新插入一条记录
				landpageService.creativeCodeHistoryInfo(id, beanLandpageId, startDate, endDate);				
			} else if (startDateInDB.before(current) && endDateInDB.after(current)) {
				// 活动已开始并且未结束
				// 编辑监测码历史记录表中原记录的结束时间为当前时间
				landpageService.updateCodeHistoryEndTime(id);
				// 重新插入一条监测码使用记录
				landpageService.creativeCodeHistoryInfo(id, beanLandpageId, startDate, endDate);
			}
		} else if (StatusConstant.CAMPAIGN_PROCEED.equals(bean.getStatus()) 
				&& (!startDateInDB.equals(startDate) || !endDateInDB.equals(endDate))) {
			// 如果活动打开  && 活动周期改变/code不变，其中活动周期改变--->（活动的开始时间改变  || 活动的结束时间改变）
			if (startDateInDB.after(current)) {
				// 删除监测码历史记录表中原来的记录
				landpageService.deleteCodeHistoryInfo(id);
				// 重新插入一条记录
				landpageService.creativeCodeHistoryInfo(id, beanLandpageId, startDate, endDate);				
			} else if (startDateInDB.before(current) && endDateInDB.after(current)) {
				// 活动在投放中---即已开始并未结束				
				landpageService.updateCodeCodeHistoryInfo(id, beanLandpageId, startDate, endDate);
			} else {
				// 活动已结束，重新插入一条记录
				landpageService.creativeCodeHistoryInfo(id, beanLandpageId, startDate, endDate);				
			}
		}
			
		
		if (!beanLandpageId.equals(dbLandpageId)) {
			// 如果两个落地页id不相同				
			// 1.停止活动下面的所有创意投放
			launchService.removeCreativeId(id);
			// 2.把活动下面的所有创意状态改为未审核
			creativeService.updateCreativeAuditStatusByCampaignId(id,StatusConstant.CREATIVE_AUDIT_NOCHECK);

//			// 3.停止活动下面的所有创意投放
//			launchService.removeCreativeId(id);
//			// 4.把活动下面的所有创意状态改为未审核
//			creativeService.updateCreativeAuditStatusByCampaignId(id,StatusConstant.CREATIVE_AUDIT_NOCHECK);
		}
		
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
		if (quantities != null && quantities.length > 0) {
			Date current = new Date();
			String budgetKey = RedisKeyConstant.CAMPAIGN_BUDGET + campaignId;
			String countKey = RedisKeyConstant.CAMPAIGN_COUNTER + campaignId;
			// 获取今天的数据
			Integer newBudget = null;
			Integer newImpression = null;
			for (Quantity quan : quantities) {
				Date startDate = quan.getStartDate();
				Date endDate = quan.getEndDate();
				if (DateUtils.isBetweenDates(current, startDate, endDate)) {
					// 今天的配置
					newBudget = quan.getBudget();
					newImpression = quan.getImpression();
					break;
				}
			}
			Integer oldBudget = null;
			Integer oldImpression = null;
			QuantityModelExample example = new QuantityModelExample();
			example.createCriteria().andCampaignIdEqualTo(campaignId);
			List<QuantityModel> models = quantityDao.selectByExample(example);
			if (models != null && !models.isEmpty()) {
				for (QuantityModel model : models) {
					Date startDate = model.getStartDate();
					Date endDate = model.getEndDate();
					if (DateUtils.isBetweenDates(current, startDate, endDate)) {
						// 今天的配置
						oldBudget = model.getDailyBudget();
						oldImpression = model.getDailyImpression();
						break;
					}
				}
			}
			
			if (newBudget == null) {
				if (oldBudget != null) {
					if (redisHelper.exists(budgetKey)) {
						redisHelper.delete(budgetKey);
					}
				}
			} else {
				if (oldBudget == null) {
					redisHelper.set(budgetKey, (int)newBudget);
				} else {
					if (redisHelper.exists(budgetKey)) {
						double redisBudget = redisHelper.getDouble(budgetKey) / 100;
						int difBudget = newBudget - oldBudget;
						if (difBudget < 0 && Math.abs(difBudget) > redisBudget) {
							throw new IllegalArgumentException(PhrasesConstant.DIF_DAILY_BIGGER_REDIS);
						}
						if (difBudget != 0) {
							redisHelper.incrybyDouble(budgetKey, difBudget * 100);
						}
					}
				}
			}
			
			if (newImpression == null) {
				if (oldImpression != null) {
					if (redisHelper.exists(countKey)) {
						redisHelper.delete(countKey);
					}
				}
			} else {
				if (oldImpression == null) {
					redisHelper.set(countKey, (int)newImpression);
				} else {
					if (redisHelper.exists(countKey)) {
						int redisImpression = redisHelper.getInt(countKey);
						int difImpression = newImpression - oldImpression;
						if (difImpression < 0 && Math.abs(difImpression) > redisImpression) {
							throw new IllegalArgumentException(PhrasesConstant.DIF_IMPRESSION_BIGGER_REDIS);
						}
						if (difImpression != 0) {
							redisHelper.incrybyInt(countKey, difImpression);
						}
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
		// 根据活动id查询活动
		CampaignModel campaignModel = campaignDao.selectByPrimaryKey(id);
		if (campaignModel == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		
		//活动的使用时间
		Date startDate = campaignModel.getStartDate();
		Date endDate = campaignModel.getEndDate();
		
		if (StatusConstant.CAMPAIGN_PAUSE.equals(status)) {
			// 当前时间
			Date current = new Date();
			// 根据监测码的使用情况，操作监测码历史记录表
			if (startDate.after(current)) {
				// 如果活动的开始时间在今天之后，删除监测码历史记录表中该活动的信息
				landpageService.deleteCodeHistoryInfo(id);
			} else if (startDate.before(current) && endDate.after(current)) {
				// 如果活动开始并且未结束，更新监测码历史记录表中该活动距离现在时间最近的一条记录的使用结束时间为当前时间
				landpageService.updateCodeHistoryEndTime(id);
			}			
			// 按照活动暂停
			pauseCampaign(campaignModel);
		} else if (StatusConstant.CAMPAIGN_PROCEED.equals(status)) {	
			// 落地页id
			String landpageId = campaignModel.getLandpageId();
			// 判断落地页监测码是否被其他开启并且未结束的活动使用				
			LandpageCodeModelExample example = new LandpageCodeModelExample();
			example.createCriteria().andLandpageIdEqualTo(landpageId);
			List<LandpageCodeModel> codes = landpageCodeDao.selectByExample(example);
			for (LandpageCodeModel code : codes) {
				// 判断监测码是否被占用
				String usedCodeCampaignId = checkUsedLandpageCode(code.getCode(), campaignModel.getStartDate(), campaignModel.getEndDate(),id);
				if (usedCodeCampaignId != null) {
					// 如果返回不为空，则说明监测码被占用
					String campaignName = getNameByCampaignId(usedCodeCampaignId);;
					throw new IllegalStatusException(campaignName + "，" + PhrasesConstant.LANDPAGE_CODE_USED);
				}
			}			
			// 记录监测码的使用情况——向监测码历史记录表中插入数据
			if (endDate.after(new Date())) {
				landpageService.creativeCodeHistoryInfo(id,landpageId,startDate,endDate);
			}						
			// 按照活动投放
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
				if (StatusConstant.PROJECT_PROCEED.equals(project.getStatus())
						&& StatusConstant.CAMPAIGN_PROCEED.equals(campaignModel.getStatus())
						&& isOnTargetTime(id) && launchService.notOverProjectBudget(projectId)
						&& launchService.notOverDailyBudget(id) && launchService.notOverDailyCounter(id)) {
					// 在项目开启、活动开启并且在投放的时间里，修改定向时间在定向时间里
					// 并且活动没有超出每天的日预算并且日均最大展现未达到上限，将活动ID写入redis
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
		Population populationTarget = bean.getPopulation(); // 人群
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
			population.setPopulationId(populationTarget.getId());
			population.setType(populationTarget.getType());
			populationTargetDao.insertSelective(population);
		}
	}
	
	/**
	 * 删除活动定向
	 * @param campaignId
	 */
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
	 * 删除活动监测地址
	 * @param campaignId
	 */
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
				Integer dailyBudget = bean.getBudget();
				if (dailyBudget != null && projectBudget != null && dailyBudget.compareTo(projectBudget) > 0) {
					throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_DAILY_BUDGET_OVER_TOTAL); 
				}
				QuantityModel model = modelMapper.map(bean, QuantityModel.class);
				model.setCampaignId(id);
				model.setId(UUIDGenerator.getUUID());
				model.setDailyBudget(dailyBudget);
				model.setDailyImpression(bean.getImpression());
				quantityDao.insertSelective(model);
			}
		}
	}
	
	/**
	 * 活动投放策略
	 * @param campaignId
	 * @throws Exception
	 */
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
		// 删除监测码历史记录表中该活动对应的监测码历史记录信息
		deleteCampaignCodeHistory(campaignId);
		// 删除活动
		campaignDao.deleteByPrimaryKey(campaignId);
		// 移除redis
		launchService.removeCampaignId4Project(campaignId, campaignInDB.getProjectId());
	}
	
	/**
	 * 批量删除活动
	 * @param campaignIds
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void deleteCampaigns(String[] campaignIds) throws Exception {
		if(campaignIds.length ==0){
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}

		List<String> asList = Arrays.asList(campaignIds);
		CampaignModelExample ex = new CampaignModelExample();
		ex.createCriteria().andIdIn(asList);
		
		List<CampaignModel> campaignInDBs = campaignDao.selectByExample(ex);
		if (campaignInDBs == null || campaignInDBs.size() < campaignIds.length) {
			throw new ResourceNotFoundException();
		}
		
		for (CampaignModel campaign : campaignInDBs) {
			String id = campaign.getId();
			//先查询出活动下创意
			CreativeModelExample creativeExample = new CreativeModelExample();
			creativeExample.createCriteria().andCampaignIdEqualTo(id);
			List<CreativeModel> creatives = creativeDao.selectByExample(creativeExample);
			if (creatives != null && !creatives.isEmpty()) {
				throw new IllegalStatusException(PhrasesConstant.CAMPAIGN_HAVE_CREATIVE);
			}
			//删除监测地址
			// deleteCampaignMonitor(campaignId);
			//删除定向
			deleteCampaignTarget(id);
			//删除频次信息
			deleteFrequency(id);
			//删除控制策略
			deleteCampaignMonitor(id);
			// 删除监测码历史记录表中该活动对应的监测码历史记录信息
			deleteCampaignCodeHistory(id);
			
			launchService.removeCampaignId4Project(id, campaign.getProjectId());
		}
		//删除活动
		campaignDao.deleteByExample(ex);
	}
	
	/**
	 * 根据id查询活动
	 * @param campaignId
	 * @return
	 */
	@Transactional
	public CampaignBean getCampaign(String campaignId) throws Exception {
		CampaignModel model = campaignDao.selectByPrimaryKey(campaignId);
		if (model == null || StringUtils.isEmpty(model.getId())) {
			throw new ResourceNotFoundException();
		}
		CampaignBean bean = modelMapper.map(model, CampaignBean.class);
		addParamToCampaign(bean, model.getId(), model.getFrequencyId());
		// 落地页信息
		LandpageCodeModelExample example = new LandpageCodeModelExample();
		example.createCriteria().andLandpageIdEqualTo(model.getLandpageId());
//		List<LandpageCodeModel> codes = landpageCodeDao.selectByExample(example);	
		// 查询活动下的创意信息
		CreativeModelExample creativeEx = new CreativeModelExample();
		creativeEx.createCriteria().andCampaignIdEqualTo(model.getId());
		List<CreativeModel> creatives = creativeDao.selectByExample(creativeEx);
		// 活动未正常投放原因
		if (launchService.isHaveLaunched(model.getId())) {
			// 是否已经投放过，投放过可能出现预算和展现数上限
			if (!launchService.notOverProjectBudget(model.getProjectId())) {
				// 项目总预算达到上限
				bean.setReason(StatusConstant.CAMPAIGN_PROJECTBUDGET_OVER);
			} else if (!launchService.notOverDailyBudget(model.getId())) {
				// 日预算达到上限
				bean.setReason(StatusConstant.CAMPAIGN_DAILYBUDGET_OVER);
			} else if (!launchService.notOverDailyCounter(model.getId())) {
				// 展现数达到上限
				bean.setReason(StatusConstant.CAMPAIGN_COUNTER_OVER);
			} else if (!isOnTargetTime(model.getId())) {
				// 不在定向时间段内（投放过的活动判断不正常投放的原因）
				bean.setReason(StatusConstant.CAMPAIGN_ISNOT_TARGETTIME);
			} else if (!isHaveLaunchCreative(model.getId())) {
				// 如果活动下没有可投放的创意（创意关闭/创意审核未通过）
				bean.setReason(StatusConstant.CAMPAIGN_NOTHAVE_CREATIVE);
			}
		} else if (!isHaveLaunchCreative(model.getId())) {
			// 如果活动下没有可投放的创意（活动下没有创意）
			bean.setReason(StatusConstant.CAMPAIGN_NOTHAVE_CREATIVE);
		}
		return bean;
	}
	
	/**
	 * 查询活动列表
	 * @param name
	 * @param beginTime 
	 * @param endTime 
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<CampaignBean> listCampaigns(String name, String projectId, Long startDate, Long endDate, String sortKey, String sortType, boolean calScore) throws Exception {
		CampaignModelExample example = new CampaignModelExample();
		

		//设置排序
		if(sortKey == null ||sortKey.isEmpty()) {
			// 按更新时间进行倒序排序
			example.setOrderByClause("create_time DESC");
		}else{
			if(!sortType.isEmpty() && sortType.equals(StatusConstant.SORT_TYPE_DESC)) {
				example.setOrderByClause(sortKey + " DESC");
			}else{
				example.setOrderByClause(sortKey+" ASC");
			}
		}
		
		if(!StringUtils.isEmpty(name) && StringUtils.isEmpty(projectId)){
			example.createCriteria().andNameLike("%" + name + "%");
		} else if(!StringUtils.isEmpty(projectId) && StringUtils.isEmpty(name)){
			example.createCriteria().andProjectIdEqualTo(projectId);
		}else if(!StringUtils.isEmpty(name) && !StringUtils.isEmpty(projectId)){
			example.createCriteria().andProjectIdEqualTo(projectId).andNameLike("%" + name + "%");
		}
		
		List<CampaignModel> models = campaignDao.selectByExample(example);
		List<CampaignBean> beans = new ArrayList<CampaignBean>();
		
		for (CampaignModel model : models) {
			CampaignBean bean = modelMapper.map(model, CampaignBean.class);
			
			if (!StringUtils.isEmpty(calScore) && calScore)
			{
			    CampaignScoreBean campaignScore = scoreService.getCampaignScore(projectId, model.getId(), startDate, endDate);
			    bean.setCampaignScore(campaignScore);
			}
			
			addParamToCampaign(bean, model.getId(), model.getFrequencyId());

			if (startDate != null && endDate != null) {
				// 查询每个活动的投放信息
//				getData(beginTime, endTime, map);
				CampaignBean data = dataService.getCampaignData(model.getId(), startDate, endDate);
				BeanUtils.copyProperties(data, bean, "id", "projectId", "projectName", "name", "remark", 
						"creativeAmount", "status", "reason", "startDate", "endDate", "uniform", "creativeNum", 
						"target", "frequency", "quantities", "landpageId", "landpageName", "landpageUrl", "campaignScore");
			}
			// 落地页信息
			CampaignModel campaign = campaignDao.selectByPrimaryKey(model.getId());		
			LandpageCodeModelExample landpageCodeEx = new LandpageCodeModelExample();
			landpageCodeEx.createCriteria().andLandpageIdEqualTo(campaign.getLandpageId());
//			List<LandpageCodeModel> codes = landpageCodeDao.selectByExample(landpageCodeEx);
			// 查询活动下的创意信息
//			CreativeModelExample creativeEx = new CreativeModelExample();
//			creativeEx.createCriteria().andCampaignIdEqualTo(model.getId());
//			List<CreativeModel> creatives = creativeDao.selectByExample(creativeEx);
			//设置创意数
			setCreativeAmountToCampaignBean(startDate, endDate,bean);

			// 活动未正常投放原因
			if (launchService.isHaveLaunched(model.getId())) {
				// 是否已经投放过，投放过可能出现预算和展现数上限
				if (!launchService.notOverProjectBudget(model.getProjectId())) {
					// 项目总预算达到上限
					bean.setReason(StatusConstant.CAMPAIGN_PROJECTBUDGET_OVER);
				} else if (!launchService.notOverDailyBudget(model.getId())) {
					// 日预算达到上限
					bean.setReason(StatusConstant.CAMPAIGN_DAILYBUDGET_OVER);
				} else if (!launchService.notOverDailyCounter(model.getId())) {
					// 展现数达到上限
					bean.setReason(StatusConstant.CAMPAIGN_COUNTER_OVER);
				} else if (!isOnTargetTime(model.getId())) {
					// 不在定向时间段内
					bean.setReason(StatusConstant.CAMPAIGN_ISNOT_TARGETTIME);
				} else if (!isHaveLaunchCreative(model.getId())) {
					// 如果活动下没有可投放的创意（创意关闭/创意审核未通过）
					bean.setReason(StatusConstant.CAMPAIGN_NOTHAVE_CREATIVE);
				}
			}else if (!isHaveLaunchCreative(model.getId())) {
				// 如果活动下没有可投放的创意（活动下没有创意）
				bean.setReason(StatusConstant.CAMPAIGN_NOTHAVE_CREATIVE);
			}
			beans.add(bean);
		}		
		return beans;
	}

	/**
	 * 给活动设置创意数
	 * @param beginTime
	 * @param endTime
	 * @param bean
     */
	public void setCreativeAmountToCampaignBean(Long beginTime, Long endTime, CampaignBean bean){
		//创意审核通过数
		int passNum=0;
		//审核不通过数
		int noPassNum=0;
		//总数
		int totalNum=0;
		CreativeModelExample cExample = new CreativeModelExample();
		cExample.createCriteria().andCampaignIdEqualTo(bean.getId());
		List<CreativeModel> list = creativeDao.selectByExample(cExample);
		List<String> creativeIds = new ArrayList<String>();
		if (list != null && !list.isEmpty()) {
			totalNum = list.size();
			for (CreativeModel model : list) {
				creativeIds.add(model.getId());
			}
		}

		//设置创意数
		if(creativeIds.size()>0){
			//根据创意id，批量查询创意审核信息
			CreativeAuditModelExample example = new CreativeAuditModelExample();
			example.createCriteria().andCreativeIdIn(creativeIds);
			List<CreativeAuditModel> creativeAuditModels = creativeAuditDao.selectByExample(example);
			if(creativeAuditModels != null && !creativeAuditModels.isEmpty()){
				for(CreativeAuditModel model : creativeAuditModels){
					if(model.getStatus().equals(StatusConstant.ADVERTISER_AUDIT_SUCCESS)){	//审查成功
						passNum++;
					}else if(model.getStatus().equals(StatusConstant.ADVERTISER_AUDIT_FAILURE)){ //失败
						noPassNum++;
					}
				}
			}
		}
		bean.setCreativeNum(passNum+"/"+noPassNum+"/"+totalNum);
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
				// 查询活动的人群定向信息
				String populationId = campaignTargetModel.getPopulationId();
				String populationType = campaignTargetModel.getPopulationType();
				PopulationTargetBean population = new PopulationTargetBean();
				population.setId(populationId);
				population.setType(populationType);
				if (!StringUtils.isEmpty(populationId)) {
					target.setPopulation(population);
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
				quanArray[i] = modelMapper.map(quantityModel, Quantity.class);   // copy时间	
				quanArray[i].setBudget(quantityModel.getDailyBudget());          // 日预算
				quanArray[i].setImpression(quantityModel.getDailyImpression());  // 日均最大展现
			}
			bean.setQuantities(quanArray);
		}
		
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
	private void proceedCampaign(CampaignModel campaign) throws Exception {
		String campaignId = campaign.getId();
		// 检查该活动是否可以投放
		// 检查是否有落地页
		String landpageId = campaign.getLandpageId();
		if (StringUtils.isEmpty(landpageId)) {
			throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_NO_LANDPAGE);
		}
		// 检查是否有创意
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> creatives = creativeDao.selectByExample(creativeExample);
		if (creatives != null && !creatives.isEmpty()) {
			// 活动下有创意
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
						String strCreativeId = redisHelper
								.getStr(RedisKeyConstant.CAMPAIGN_CREATIVEIDS + creative.getId());
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
		} else {
			// 活动下无创意，可以打开开关
			campaign.setStatus(StatusConstant.CAMPAIGN_PROCEED);
			// 改变数据库状态
			campaignDao.updateByPrimaryKeySelective(campaign);
		}
	}
	
	/**
	 * 按照活动暂停
	 * @param campaignIds
	 * @throws Exception
	 */
	private void pauseCampaign(CampaignModel campaign) throws Exception {
		campaign.setStatus(StatusConstant.CAMPAIGN_PAUSE);
		String projectId = campaign.getProjectId();
		ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
		if (StatusConstant.PROJECT_PROCEED.equals(projectModel.getStatus())) {
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
	
	/**
	 * 判断当前活动是否已经开始投放
	 * @param campaignId 活动id
	 * @return
	 * @throws Exception
	 */
	public Boolean isBeginLaunchDate(String campaignId) throws Exception {
		// 时间
		Date current = new Date();
		// 查询活动信息
		CampaignModelExample campaignExample = new CampaignModelExample();
		campaignExample.createCriteria().andIdEqualTo(campaignId).andStartDateLessThanOrEqualTo(current);
		List<CampaignModel> campaigns = campaignDao.selectByExample(campaignExample);
		return campaigns.size() > 0;
	}
	
    /**
     * 批量同步活动下创意
     * @param ids
     * @throws Exception
     */
    @Transactional
    public void synchronizeCreatives(String[] campaignIds) throws Exception { 
    	if (campaignIds.length == 0) {
    		throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
    	}
    	// 根据活动ID列表查询活动信息
		List<String> asList = Arrays.asList(campaignIds);
		CampaignModelExample ex = new CampaignModelExample();
		ex.createCriteria().andIdIn(asList);		
		List<CampaignModel> campaignInDB = campaignDao.selectByExample(ex);
		// 判断活动信息是否为空
		if (campaignInDB == null || campaignInDB.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		Set<String> setIds = new HashSet<String>();
		for (String campaignId : campaignIds) {
			//查询出活动下创意
			CreativeModelExample creativeExample = new CreativeModelExample();
			creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
			List<CreativeModel> creativeList = creativeDao.selectByExample(creativeExample);
			// 获取creativeIds
			for (CreativeModel creativeModel : creativeList) {				
				setIds.add(creativeModel.getId());
			}
		}
		String[] creativeIds = new String[setIds.size()];
		// 同步创意
		creativeService.synchronizeCreatives(setIds.toArray(creativeIds));	
    }
    
    /**
     * 修改活动开始结束日期
     * @param id 活动id
     * @param map 参数集合
     * @throws Exception
     */
    @Transactional
    public void updateCampaignStartAndEndDate(String id,Map<String,String> map) throws Exception{
    	// 获取开始时间和结束时间
    	String sDate = map.get("startDate");
    	String eDate = map.get("endDate");

    	// 转换类型
		Date startDate = new Date(Long.parseLong(sDate));
		Date endDate = new Date(Long.parseLong(eDate));

		// 编辑的活动在数据库中不存在
		CampaignModel campaignInDB = campaignDao.selectByPrimaryKey(id);
		if (campaignInDB == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		// 监测日期范围是否正确
		if (startDate != null && endDate != null && startDate.after(endDate)) {
			throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_DATE_ERROR);
		}
		
		// 判断活动的开始时间和结束时间是否在今天之前
		Date current = new Date();                        // 当前时间
		Date startDateInDB = campaignInDB.getStartDate(); // 修改时间之前的活动开始时间
		Date endDateInDB = campaignInDB.getEndDate();     // 修改时间之后的活动结束时间
		if (!startDate.equals(startDateInDB) || !endDate.equals(endDateInDB)) {
			// 如果时间发生改变，则判断其开始时间和结束时间是否在今天之前
			if (startDateInDB.after(current)) {
				// 如果活动未开始，判断欲修改的活动开始时间是否在今天之前
				checkStartDate(startDate);
			} else {
				// 如果活动在投放中或已完成，判断欲修改的活动开始时间是否在今天之前
				if (!startDate.equals(startDateInDB)) {
					// 如果活动的开始时间有改变
					checkStartDate(startDate);
				}			
				// 如果活动在投放中或已完成，判断欲修改的活动结束时间是否在今天之前
				checkEndDate(endDate);
			}
		}		
		
		// 落地页id
		String landpageId = campaignInDB.getLandpageId();
		
		// 判断落地页监测码是否被其他开启并且未结束的活动使用
		if (StatusConstant.CAMPAIGN_PROCEED.equals(campaignInDB.getStatus())) {
			// 如果活动开关打开
			LandpageCodeModelExample example = new LandpageCodeModelExample();
			example.createCriteria().andLandpageIdEqualTo(landpageId);
			List<LandpageCodeModel> codes = landpageCodeDao.selectByExample(example);
			for (LandpageCodeModel code : codes) {
				// 判断监测码是否被占用
				String usedCodeCampaignId = checkUsedLandpageCode(code.getCode(), startDate, endDate,id);
				if (usedCodeCampaignId != null) {
					// 如果返回不为空，则说明监测码被占用
					String campaignName = getNameByCampaignId(usedCodeCampaignId);;
					throw new IllegalStatusException(campaignName + "，" + PhrasesConstant.LANDPAGE_CODE_USED);
				}
			}	
		}							

		CampaignModel model = new CampaignModel();
		model.setStartDate(startDate);
		model.setEndDate(endDate);
		model.setId(id);
		//修改基本信息
		campaignDao.updateByPrimaryKeySelective(model);
		
		// 根据监测码的使用情况，操作监测码历史记录表	
		if (StatusConstant.CAMPAIGN_PROCEED.equals(campaignInDB.getStatus())) {
			// 活动开关打开
			if (startDateInDB.after(current)) {
				// 删除监测码历史记录表中原来的记录
				landpageService.deleteCodeHistoryInfo(id);
				// 重新插入一条记录
				landpageService.creativeCodeHistoryInfo(id,landpageId,startDate,endDate);				
			} else if (startDateInDB.before(current) && endDateInDB.after(current)) {
				// 活动在投放中---即已开始并未结束				
				landpageService.updateCodeCodeHistoryInfo(id,landpageId,startDate,endDate);
			} else {
				// 活动已结束，重新插入一条记录
				landpageService.creativeCodeHistoryInfo(id,landpageId,startDate,endDate);				
			}
		} 		
				
		// 获取修改开始时间和结束时间后的活动信息
		CampaignModel campaignModel = campaignDao.selectByPrimaryKey(id);
		// 修改redis中的信息
		//(原来的状态、现在的状态--->1.改了时间前后状态，投放还是投放、不投放还是不投放：不用处理；
		//2.修改时间前后状态改变：不投放到投放；3.修改时间前后状态改变：投放到不投放)
		String projectId = campaignModel.getProjectId();
		ProjectModel project = projectDao.selectByPrimaryKey(projectId);
		if (!launchService.isHaveLaunched(id) && isOnLaunchDate(id)  &&
				StatusConstant.PROJECT_PROCEED.equals(project.getStatus())
				&& StatusConstant.CAMPAIGN_PROCEED.equals(campaignModel.getStatus())) {
			// 没有投放过、修改时间后再投放时间内、项目开关开启、活动开关开启，则向redis中写入活动的基本信息
			launchService.write4FirstTime(campaignModel);
			// 如果在定向时间段内&&没有超出日预算和日均最大展现数，则可以投放，向redis的groupids写入信息
			if (isOnTargetTime(id) && launchService.notOverProjectBudget(projectId) && launchService.notOverDailyBudget(id) && launchService.notOverDailyCounter(id)) {
				boolean writeResult = launchService.launchCampaignRepeatable(id);
				if (!writeResult) {
					throw new ServerFailureException(PhrasesConstant.REDIS_KEY_LOCK);
				}
			}
		} else if (launchService.isHaveLaunched(id) && !isOnLaunchDate(id)) {
			// 如果之间投放过，并且修改时间后不在投放时间段内，则将其信息从redis中删除
			launchService.remove4EndDate(campaignModel);
		}				
    }        
	
	/**
	 * 批量修改创意价格
	 * @param ids
	 * @param map
	 * @throws Exception
	 */
	public void updateCampaignsPrices(String[] ids, Map<String, String> map) throws Exception {
		if (ids.length == 0) {
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		// 查询活动信息
		List<String> asList = Arrays.asList(ids); // 转换类型
		CampaignModelExample campaignModelEx = new CampaignModelExample();
		campaignModelEx.createCriteria().andIdIn(asList);
		// 判断活动信息是否为空
		List<CampaignModel> campaignModels = campaignDao.selectByExample(campaignModelEx);
		if (campaignModels == null || campaignModels.size() < ids.length) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		for (CampaignModel campaign : campaignModels) {
			// 创意id
			String campaignId = campaign.getId();
			// 查询改活动下的创意
			CreativeModelExample creativeEx = new CreativeModelExample();
			creativeEx.createCriteria().andCampaignIdEqualTo(campaignId);
			// 判断活动下的创意信息是否为空
			List<CreativeModel> creativeList = creativeDao.selectByExample(creativeEx);
//			if (creativeList == null || creativeList.isEmpty()) {
//				throw new ResourceNotFoundException();
//			}
			// 修改创意价格
			for (CreativeModel creative : creativeList) {
				creative.setPrice(Float.parseFloat(map.get("price")));
				creativeDao.updateByPrimaryKeySelective(creative);
			}
			launchService.writeCreativeInfo(campaignId);
		}
	}
	
    /**
     * 判断落地页监测码是否被使用
     * @param landpageId 落地页id
     * @throws Exception
     */
	private String checkUsedLandpageCode(String code, Date startDate, Date endDate,String campaignId) throws Exception {
		// 当前时间
		Date current = new Date();
		// 一天中最小时间
		Date todayStart = DateUtils.getSmallHourOfDay(current);
		// 查询监测码历史记录信息
		LandpageCodeHistoryModelExample codeHistoryEx = new LandpageCodeHistoryModelExample();
		// 查询监测码使用时间在今天及之后的监测码历史记录信息
		if (campaignId != null) {
			// 编辑时排除自己
			codeHistoryEx.createCriteria().andCampaignIdNotEqualTo(campaignId).andEndTimeGreaterThanOrEqualTo(todayStart);
		} else {
			codeHistoryEx.createCriteria().andEndTimeGreaterThanOrEqualTo(todayStart);
		}
		List<LandpageCodeHistoryModel> codeHistorys = landpageCodeHistoryDao.selectByExample(codeHistoryEx);
		Map<String,List<Map<String,Object>>> cache = new HashMap<String,List<Map<String,Object>>>();
		if (codeHistorys != null && !codeHistorys.isEmpty()) {
			// 如果活动对应的监测码历史记录信息不为空，判断是否存在今天开始使用的监测码
			for (LandpageCodeHistoryModel codeHistory : codeHistorys) {								
				// 监测码使用时间
				Date startTime = codeHistory.getStartTime();
				Date endTime = codeHistory.getEndTime();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				String strStart = format.format(startTime);
				String strEnd = format.format(endTime);
				// 活动id
				String codesCampaignId = codeHistory.getCampaignId();
				// 被使用过的监测码
				String[] usedCodes = codeHistory.getCodes().split(",");
				if (usedCodes != null && usedCodes.length > 0) {
					for (String usedCode : usedCodes) {
						if (cache.containsKey(usedCode)) {
							List<Map<String,Object>> dateGroups = cache.get(usedCode);
							Map<String,Object> group = new HashMap<String,Object>();
							group.put("startDate", strStart);
							group.put("endDate", strEnd);
							group.put("campaignId", codesCampaignId);
							dateGroups.add(group);
						} else {
							List<Map<String,Object>> dateGroups = new ArrayList<Map<String,Object>>();
							Map<String,Object> group = new HashMap<String,Object>();
							group.put("startDate", strStart);
							group.put("endDate", strEnd);
							group.put("campaignId", codesCampaignId);
							dateGroups.add(group);
							cache.put(usedCode, dateGroups);
						}
					}
				}
			}
		}		
		
		// 被使用的监测码cache包含传来的code，则比较两个监测码使用的时间是否交叉
		if (cache.containsKey(code)) {
			List<Map<String,Object>> dateGroups = cache.get(code);
			for (Map<String,Object> group : dateGroups) {
				// 监测码的使用时间	
				SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss"); 
				String strStart = String.valueOf(group.get("startDate"));
				String strEnd = String.valueOf(group.get("endDate"));
				Date start = format.parse(strStart);
				Date end = format.parse(strEnd);
				
				// 监测码对应的活动id
				String usedcodeCampaignId = group.get("campaignId").toString();
				
				// 使用时间是否有交叉
				if (!(start.after(endDate) || end.before(startDate))) {
					// 冲突返回冲突的活动id
					return usedcodeCampaignId;					
				}
			}
		}
		// 不冲突返回null
		return null;
	}
    
    /**
     * 判断活动下是否有可投放创意
     * @param campaignId 活动id
     * @return
     * @throws Exception
     */
	public Boolean isHaveLaunchCreative(String campaignId) throws Exception {
		// 查询创意信息
		CreativeModelExample creativeEx = new CreativeModelExample();
		creativeEx.createCriteria().andCampaignIdEqualTo(campaignId)
				.andEnableEqualTo(StatusConstant.CREATIVE_IS_ENABLE);
		List<CreativeModel> creatives = creativeDao.selectByExample(creativeEx);
		if (creatives != null && !creatives.isEmpty()) {
			// 如果创意信息不为空
			for (CreativeModel creative : creatives) {
				// 创意id
				String creativeId = creative.getId();
				// 查询创意审核信息
				CreativeAuditModelExample creativeAuditEx = new CreativeAuditModelExample();
				creativeAuditEx.createCriteria().andCreativeIdEqualTo(creativeId)
						.andStatusEqualTo(StatusConstant.CREATIVE_AUDIT_SUCCESS);
				List<CreativeAuditModel> creativeAudit = creativeAuditDao.selectByExample(creativeAuditEx);
				if (creativeAudit.size() > 0) {
					return true;
				} 
			}
		}
		return false;
	}			
    
	/**
	 * 通过活动id获取活动名称
	 * @param CampaignId
	 * @return
	 * @throws Exception
	 */
	private String getNameByCampaignId(String CampaignId) throws Exception {
		CampaignModel campaign = campaignDao.selectByPrimaryKey(CampaignId);
		return campaign.getName();
	}
	
	/**
	 * 判断活动开始时间是否在今天之前
	 * @param startDate 活动的开始时间
	 * @throws Exception
	 */
	private void checkStartDate(Date startDate) throws Exception {
		// 一天中的最小时间（确保活动开始时间可以是今天，因为开始时间是00:00:00）
		Date start = DateUtils.getSmallHourOfDay(new Date());
		if (startDate.before(start)) {
			// 如果活动的开始时间在今天之前 ，则抛异常
			throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_STARTDATE_BEFORE_TODAY);
		}
	}
	
	/**
	 * 判断活动结束时间是否在今天之前
	 * @param endDate 活动的结束时间
	 * @throws Exception
	 */
	private void checkEndDate(Date endDate) throws Exception {
		if (endDate.before(new Date())) {
			// 如果活动的结束时间在今天之前（结束时间是23:59:59），则抛异常
			throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_ENDDATE_BEFORE_TODAY);
		}
	}
	
	/**
	 * 更换落地页，判断code是否更换
	 * @param dbLandpageId
	 * @param beanLandpageId
	 * @throws Exception
	 */
	private boolean isChangeCode(String dbLandpageId, String beanLandpageId) throws Exception {
		// 更换落地页，判断监测码是否更换
		if (!dbLandpageId.equals(beanLandpageId)) {
			// bean中落地页id对应的监测码
			List<String> newCodes = new ArrayList<String>();
			LandpageCodeModelExample exampleBean = new LandpageCodeModelExample();
			exampleBean.createCriteria().andLandpageIdEqualTo(beanLandpageId);
			List<LandpageCodeModel> codesBean = landpageCodeDao.selectByExample(exampleBean);
			for (LandpageCodeModel codeBean : codesBean) {
				newCodes.add(codeBean.getCode());
			}
			// 数据库中原来落地页id对应的监测码
			List<String> oldCodes = new ArrayList<String>();
			LandpageCodeModelExample exampleDB = new LandpageCodeModelExample();
			exampleDB.createCriteria().andLandpageIdEqualTo(dbLandpageId);
			List<LandpageCodeModel> codesDB = landpageCodeDao.selectByExample(exampleDB);
			for (LandpageCodeModel codeDB : codesDB) {
				oldCodes.add(codeDB.getCode());
			}
			// 判断长度，长度不同的话肯定不同；长度相同的话比较code是否改变
			if (newCodes.size() == oldCodes.size()) {
				// 如果长度相同，比较code是否相同
				for (String newCode : newCodes) {
					if (!oldCodes.contains(newCode)) {
						// 如果原来的codes中没有/不包含新的code，说明code有改变，返回true
						return true;
					}
				}
			} else {
				// 否则长度不相等，说明code有改变，返回true
				return true;
			}
		}
		// 如果监测码没有改变，返回false
		return false;
	}
	
	/**
	 * 删除一个活动的监测码历史记录信息
	 * @param campaignId 活动id
	 * @throws Exception
	 */
	private void deleteCampaignCodeHistory(String campaignId) throws Exception {
		// 根据活动id查询监测码
    	LandpageCodeHistoryModelExample codeHistorys = new LandpageCodeHistoryModelExample();
    	codeHistorys.createCriteria().andCampaignIdEqualTo(campaignId);
    	// 删除
    	landpageCodeHistoryDao.deleteByExample(codeHistorys);
	}
	
}
