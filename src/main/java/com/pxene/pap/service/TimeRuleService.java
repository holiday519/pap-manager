package com.pxene.pap.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pxene.pap.common.JedisUtils;
import com.pxene.pap.common.RuleLogBean;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.RedisKeyConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.RuleBean;
import com.pxene.pap.domain.beans.RuleBean.Condition;
import com.pxene.pap.domain.models.AppRuleModel;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignRuleModel;
import com.pxene.pap.domain.models.CampaignRuleModelExample;
import com.pxene.pap.domain.models.CampaignRuleModelExample.Criteria;
import com.pxene.pap.domain.models.CreativeRuleModel;
import com.pxene.pap.domain.models.LandpageRuleModel;
import com.pxene.pap.domain.models.RegionRuleModel;
import com.pxene.pap.domain.models.RuleConditionModel;
import com.pxene.pap.domain.models.RuleConditionModelExample;
import com.pxene.pap.domain.models.TimeRuleModel;
import com.pxene.pap.domain.models.TimeRuleModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AppRuleDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CampaignRuleDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.CreativeMaterialDao;
import com.pxene.pap.repository.basic.CreativeRuleDao;
import com.pxene.pap.repository.basic.LandpageRuleDao;
import com.pxene.pap.repository.basic.RegionRuleDao;
import com.pxene.pap.repository.basic.RuleConditionDao;
import com.pxene.pap.repository.basic.TimeRuleDao;

@Service
public class TimeRuleService extends BaseService {
	
	@Autowired
	private CreativeDao creativeDao;
	
	@Autowired
	private CreativeMaterialDao creativeMaterialDao;
	
	@Autowired
	private AppRuleDao appRuleDao;
	
	@Autowired
	private RegionRuleDao regionRuleDao;
	
	@Autowired
	private TimeRuleDao timeRuleDao;
	
	@Autowired
	private LandpageRuleDao landpageRuleDao;
	
	@Autowired
	private CreativeRuleDao creativeRuleDao;
	
	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired
	private RuleLogService ruleLogService;
	
	@Autowired
	private CampaignRuleDao campaignRuleDao;
	
	@Autowired
	private RuleConditionDao ruleConditionDao;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private CampaignService campaignService;
	
	public void saveTimeRule(RuleBean ruleBean) throws Exception {
		TimeRuleModel model = modelMapper.map(ruleBean, TimeRuleModel.class);
		String ruleId = UUID.randomUUID().toString();
		model.setId(ruleId);
		model.setStatus(StatusConstant.CAMPAIGN_RULE_STATUS_UNUSED);
		
		try {
			ruleBean.setId(ruleId);
			//添加关联关系
			addCampaignAndRule(ruleBean, StatusConstant.CAMPAIGN_RULE_TYPE_TIME);
			//添加规则——条件
			addRuleCondition(ruleBean);
			timeRuleDao.insertSelective(model);
        } catch (DuplicateKeyException exception) {
            // 违反数据库唯一约束时，向上抛出自定义异常，交给全局异常处理器处理
            throw new DuplicateEntityException();
        }
		
		BeanUtils.copyProperties(model, ruleBean);
	}
	
	public void deleteTimeRule(String id) throws Exception {
		TimeRuleModel modelInDB = timeRuleDao.selectByPrimaryKey(id);
		if (modelInDB == null) {
			throw new ResourceNotFoundException();
		}
		
		// 查看该规则下是否绑定了活动
		CampaignRuleModelExample example = new CampaignRuleModelExample();
		Criteria criteria = example.createCriteria();
		criteria.andRuleIdEqualTo(id);
		List<CampaignRuleModel> models = campaignRuleDao.selectByExample(example);
		
		if (models.size() > 0) {
			throw new IllegalStatusException(PhrasesConstant.RULE_HAVE_CAMPAIGN);
		} else {
			//删除规则条件
			deleteRuleConditionById(id);
			timeRuleDao.deleteByPrimaryKey(id);
		}
		
	}
	
	public void updateTimeRule(String id, RuleBean ruleBean) throws Exception {
		TimeRuleModel modelInDB = timeRuleDao.selectByPrimaryKey(id);
		if (modelInDB == null) {
			throw new ResourceNotFoundException();
		}
		
		TimeRuleModel ruleModel = modelMapper.map(ruleBean, TimeRuleModel.class);
		//删除所有关联关系
		deleteCampaignAndRule(id);
		//删除规则条件
		deleteRuleConditionById(id);
		//重新添加规则——条件
		addRuleCondition(ruleBean);
		//重新添加关联关系
		ruleBean.setId(id);
		addCampaignAndRule(ruleBean, StatusConstant.CAMPAIGN_RULE_TYPE_TIME);
		
		try {
			// 修改规则信息
			timeRuleDao.updateByPrimaryKeySelective(ruleModel);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
	}
	
	public RuleBean findTimeRuleById(String id) throws Exception {
		TimeRuleModel timeRuleModel = timeRuleDao.selectByPrimaryKey(id);
		if(timeRuleModel == null){
			throw new ResourceNotFoundException();
		}
		RuleBean ruleBean = modelMapper.map(timeRuleModel, RuleBean.class);
		RuleBean bean = getParamForRule(ruleBean);
		
		return bean;
	}
	
	public List<RuleBean> listTimeRule(String name) throws Exception {
		TimeRuleModelExample example = new TimeRuleModelExample();
		
		if (!StringUtils.isEmpty(name)) {
			example.createCriteria().andNameLike("%" + name + "%");
		}
		List<TimeRuleModel> rules = timeRuleDao.selectByExample(example);
		List<RuleBean> beans = new ArrayList<RuleBean>();
		
		if (rules == null || rules.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		for (TimeRuleModel model : rules) {
			RuleBean bean = modelMapper.map(model, RuleBean.class);
			beans.add(getParamForRule(bean));
		}
		
    	return beans;
		
	}
	
	/**
	 * 获取活动绑定的活动ID和名称
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public RuleBean getParamForRule(RuleBean bean) throws Exception {
		String ruleId = bean.getId();
		CampaignRuleModelExample example = new CampaignRuleModelExample();
		example.createCriteria().andRuleIdEqualTo(ruleId);
		List<CampaignRuleModel> list = campaignRuleDao.selectByExample(example);
		if (list == null) {
			return bean;
		}
		List<String> campaignIds = new ArrayList<String>();
		List<String> campaignNames = new ArrayList<String>();
		for (CampaignRuleModel model : list) {
			String campaignId = model.getCampaignId();
			if (!campaignIds.contains(campaignId)) {
				CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
				if (campaignModel != null) {
					campaignIds.add(campaignId);
					campaignNames.add(campaignModel.getName());
				}
			}
		}
		
		if (!campaignIds.isEmpty()) {
			String[] idArray = (String[]) campaignIds.toArray(new String[campaignIds.size()]);
			String[] nameArray = (String[]) campaignNames.toArray(new String[campaignNames.size()]);
			bean.setCampaignIds(idArray);
			bean.setCampaignNames(nameArray);
		}
		//查询出规则条件
		List<Condition> conditionList = new ArrayList<Condition>();
		
		RuleConditionModelExample rcExample = new RuleConditionModelExample();
		rcExample.createCriteria().andRuleIdEqualTo(ruleId);
		List<RuleConditionModel> conditions = ruleConditionDao.selectByExample(rcExample);
		if (conditions != null && !conditions.isEmpty()) {
			for (RuleConditionModel mod : conditions) {
				Condition model = modelMapper.map(mod, Condition.class);
				conditionList.add(model);
			}
		}
		if (!conditionList.isEmpty()) {
			Condition[] cds = new Condition[conditionList.size()];
			for (int i=0;i< conditionList.size();i++) {
				cds[i] = conditionList.get(i);
			}
			bean.setConditions(cds);
		}
		return bean;
	}
	
	/**
	 * 添加活动规则关联关系
	 * @param ruleBean
	 * @param type
	 */
	public void addCampaignAndRule(RuleBean ruleBean, String type) throws Exception {
		String[] campaignIds = ruleBean.getCampaignIds();
		if (campaignIds != null && campaignIds.length > 0) {
			for (String campaignId : campaignIds) {
				CampaignRuleModel model = new CampaignRuleModel();
				model.setId(UUID.randomUUID().toString());
				model.setCampaignId(campaignId);
				model.setRuleId(ruleBean.getId());
				model.setRuleType(type);
				campaignRuleDao.insertSelective(model);
			}
		}
	}
	
	/**
	 * 删除活动规则关联关系
	 * @param ruleId
	 * @throws Exception
	 */
	public void deleteCampaignAndRule(String ruleId) throws Exception {
		CampaignRuleModelExample example = new CampaignRuleModelExample();
		example.createCriteria().andRuleIdEqualTo(ruleId);
		//删除关联关系
		campaignRuleDao.deleteByExample(example);
	}
	
	/**
	 * 修改规则状态
	 * @param id
	 * @param map
	 * @throws Exception
	 */
	public void updateTimeRuleStatus(String id, Map<String, String> map) throws Exception {
		if (StringUtils.isEmpty(map.get("action"))) {
			throw new IllegalArgumentException();
		}
		TimeRuleModel ruleModel = timeRuleDao.selectByPrimaryKey(id);
		if (ruleModel == null) {
			throw new ResourceNotFoundException();
		}
		
		String action = map.get("action").toString();
		String status = null;
		if (StatusConstant.ACTION_TYPE_PAUSE.equals(action)) {
			status = StatusConstant.CAMPAIGN_RULE_STATUS_UNUSED;
			
		} else if (StatusConstant.ACTION_TYPE_PROCEES.equals(action)) {
			status = StatusConstant.CAMPAIGN_RULE_STATUS_USED;
			
		}else {
			throw new IllegalStatusException();
		}
		ruleModel.setStatus(status);
		timeRuleDao.updateByPrimaryKeySelective(ruleModel);
	}
	
	/**
	 * 向规则——条件表插入数据
	 * @param ruleBean
	 * @throws Exception
	 */
	public void addRuleCondition(RuleBean ruleBean) throws Exception {
		Condition[] conditions = ruleBean.getConditions();
		if (conditions != null && conditions.length > 0) {
			for (Condition condition : conditions) {
				RuleConditionModel ruleCondition = modelMapper.map(condition, RuleConditionModel.class);
				ruleCondition.setId(UUID.randomUUID().toString());
				ruleCondition.setRuleId(ruleBean.getId());
				ruleConditionDao.insertSelective(ruleCondition);
			}
		}
	}
	
	/**
	 * 删除规则——条件表数据
	 * @param ruleId
	 */
	public void deleteRuleConditionById(String ruleId) {
		if (!StringUtils.isEmpty(ruleId)) {
			RuleConditionModelExample example = new RuleConditionModelExample();
			example.createCriteria().andRuleIdEqualTo(ruleId);
			ruleConditionDao.deleteByExample(example );
		}
	}
	
	/**
	 * 关闭时间规则（只需修改时间规则状态，定时器会处理其他逻辑）
	 * @param campaignId
	 * @param ruleId
	 * @throws Exception
	 */
	@Transactional
	public void closeTimeRule(String campaignId, String ruleId) throws Exception {
		TimeRuleModel ruleModel = timeRuleDao.selectByPrimaryKey(ruleId);
		if (ruleModel == null) {
			throw new ResourceNotFoundException();
		}
		ruleModel.setStatus(StatusConstant.CAMPAIGN_RULE_STATUS_UNUSED);
		timeRuleDao.updateByPrimaryKey(ruleModel);
		
		//将活动重新投放
		campaignService.launch(campaignId);
		
		//插入日志信息
		RuleLogBean bean = new RuleLogBean();
		bean.setRuleId(ruleId);
		bean.setCampaignId(campaignId);
		bean.setRuleType(StatusConstant.CAMPAIGN_RULE_TYPE_TIME);
		bean.setActionType("02");//关闭
		ruleLogService.createRuleLog(bean);
	}
	
	/**
	 * 打开时间规则（修改时间崔则状态，改变redis中key由定时器处理）
	 * @param campaignId
	 * @param ruleId
	 * @throws Exception
	 */
	@Transactional
	public void openTimeRule(String campaignId, String ruleId) throws Exception {
		if (checkGroupHaveRule(campaignId)) {
			throw new IllegalStatusException("活动下已有开启的规则，无法再次开启规则");
		}
		
		TimeRuleModel ruleModel = timeRuleDao.selectByPrimaryKey(ruleId);
		if (ruleModel == null) {
			throw new ResourceNotFoundException();
		}
		ruleModel.setStatus(StatusConstant.CAMPAIGN_RULE_STATUS_USED);
		timeRuleDao.updateByPrimaryKey(ruleModel);
		//插入日志信息
		RuleLogBean bean = new RuleLogBean();
		bean.setRuleId(ruleId);
		bean.setCampaignId(campaignId);
		bean.setRuleType(StatusConstant.CAMPAIGN_RULE_TYPE_TIME);
		bean.setActionType("01");//打开
		ruleLogService.createRuleLog(bean);
	}
	
	/**
	 * 定时监测时段规则
	 * @throws Exception
	 */
//	@Scheduled(cron = "0 0 */1 * * ?")
//	@Transactional
//	public void changeTimePrice() throws Exception {
//		TimeRuleModelExample example = new TimeRuleModelExample();
//		example.createCriteria().andStatusEqualTo(StatusConstant.CAMPAIGN_RULE_STATUS_USED);
//		List<TimeRuleModel> rules = timeRuleDao.selectByExample(example);
//		if (rules == null || rules.isEmpty()) {
//			return;
//		}
//		Gson gson = new Gson();
//		DecimalFormat format = new DecimalFormat("##.##");
//		Date time = new Date();//当前时间
//		//根据时段设置开始时间、结束时间
//		for (TimeRuleModel ruleModel: rules) {
//			String ruleId = ruleModel.getId();
//			String historyData = ruleModel.getHistoryData();//时段
//			Long beginTime = getStartTimeByhistoryData(historyData, time);//开始时间
//			Long endTime = time.getTime();//结束时间
//			//如果时间为“前一天”时，结束时间需要特殊处理
//			if ("09".equals(historyData)) {
//				DateTimeFormatter dateformat = DateTimeFormat .forPattern("yyyy-MM-dd HH:mm:ss");
//				String day = new DateTime(time).minusDays(1).toString("yyyy-MM-dd");
//				endTime = DateTime.parse(day + " 23:59:59", dateformat).getMillis();
//			}	
//			Float fare = ruleModel.getFare();//提价比
//			Float sale = ruleModel.getSale();//降价比
//			CampaignRuleModelExample crExample = new CampaignRuleModelExample();
//			crExample.createCriteria().andRuleIdEqualTo(ruleId);
//			List<CampaignRuleModel> crRules = campaignRuleDao.selectByExample(crExample);
//			if (crRules == null || crRules.isEmpty()) {
//				continue;
//			}
//			//查询规则下的条件
//			RuleConditionModelExample conditionExample = new RuleConditionModelExample();
//			conditionExample.createCriteria().andRuleIdEqualTo(ruleId);
//			List<RuleConditionModel> conditions = ruleConditionDao.selectByExample(conditionExample);
//			
//			for (CampaignRuleModel crModel : crRules) {//此处不用判断活动是否有两个及以上规则在执行，打开时间规则时，已经判断
//				String campaignId = crModel.getCampaignId();
//				if (!StringUtils.isEmpty(campaignId)) {
//					List<Map<String, Object>> timeHourData = creativeDataHourStatsDao.selectTimeDataByCampaignId(campaignId, new Date(beginTime), new Date(endTime));
//					if (timeHourData != null && !timeHourData.isEmpty()) {
//						List<String> upList = new ArrayList<String>();//加价的时段
//						List<String> downList = new ArrayList<String>();//减价的时段
//						for (Map<String, Object> map : timeHourData) {
//							for (RuleConditionModel condition : conditions) {
//								double data = 0;
//								if ("02".equals(condition.getDataType())) {
//									data = StringUtils.isEmpty(map.get("winAmount"))?0:Double.parseDouble(map.get("winAmount").toString());
//								} else if ("04".equals(condition.getDataType())) {
//									data = StringUtils.isEmpty(map.get("impressionAmount"))?0:Double.parseDouble(map.get("impressionAmount").toString());
//								} else if ("05".equals(condition.getDataType())) {
//									data = StringUtils.isEmpty(map.get("impressionRate"))?0:Double.parseDouble(map.get("impressionRate").toString());
//								} else if ("06".equals(condition.getDataType())) {
//									data = StringUtils.isEmpty(map.get("clickAmount"))?0:Double.parseDouble(map.get("clickAmount").toString());
//								} else if ("07".equals(condition.getDataType())) {
//									data = StringUtils.isEmpty(map.get("clickRate"))?0:Double.parseDouble(map.get("clickRate").toString());
//								} else if ("08".equals(condition.getDataType())) {
//									data = StringUtils.isEmpty(map.get("arrivalAmount"))?0:Double.parseDouble(map.get("arrivalAmount").toString());
//								} else if ("09".equals(condition.getDataType())) {
//									data = StringUtils.isEmpty(map.get("arrivalRate"))?0:Double.parseDouble(map.get("arrivalRate").toString());
//								} else if ("10".equals(condition.getDataType())) {
//									data = StringUtils.isEmpty(map.get("uniqueAmount"))?0:Double.parseDouble(map.get("uniqueAmount").toString());
//								}
//								String timeStr = map.get("time").toString();
//								if ("01".equals(condition.getCompareType())) {//条件选择大于的时候，如果数据值不大于条件值，就放入降价id中
//									if (data < condition.getData()) {
//										if (!downList.contains(timeStr)) {
//											downList.add(timeStr);
//										}
//									}
//								} else {//条件选择小于
//									if (data >= condition.getData()) {
//										if (!downList.contains(timeStr)) {
//											downList.add(timeStr);
//										}
//									}
//								}
//							}
//						}
//						//查询数据中除去减钱的剩下的就是加钱的-----在定向里的id，却没有投放数据（根据判断代码逻辑，不符合降价，也不符合升价），此处会将这些id丢掉。--？
//						for (Map<String, Object> map : timeHourData) {
//							String timeStr = map.get("time").toString();
//							if (!downList.isEmpty() && !downList.contains(timeStr)) {
//								upList.add(timeStr);
//							}
//						}
//						//已经找出升价的time和降价time，进行调价
//						//获取当前小时
//						DateTime dateTime = new DateTime(time);
//						String hour = dateTime.toString("HH");
//						//找出该活动所有的mapid
//						String campaignMapId = JedisUtils.getStr(RedisKeyConstant.CAMPAIGN_MAPIDS + campaignId);
//						if (!StringUtils.isEmpty(campaignMapId)) {
//							JsonObject mapIdJson = gson.fromJson(campaignMapId, new JsonObject().getClass());
//							JsonArray mapids = mapIdJson.getAsJsonArray("mapids");
//							//根据当前时段是不是在上涨的list中，如果在就加价
//							for (int m = 0; m < mapids.size(); m++) {
//								String string = mapids.get(m).getAsString();
//								String mapid = JedisUtils.getStr(RedisKeyConstant.CREATIVE_INFO + string);
//								if (StringUtils.isEmpty(mapid)) {
//									continue;
//								}
//								JsonObject obj = gson.fromJson(mapid, new JsonObject().getClass());
//								JsonArray array = obj.getAsJsonArray("price_adx");
//								for (int n = 0; n < array.size(); n++) {// 加价
//									JsonObject object = array.get(n).getAsJsonObject();
//									BigDecimal price = redisService.getCreativePrice(mapid);
//									String newPriceStr = "0";
//									if (upList.contains(hour)) {
//										newPriceStr = format.format(price.multiply(new BigDecimal(1 + fare)));
//									} else if (downList.contains(hour)) {
//										newPriceStr = format.format(price.multiply(new BigDecimal(1 - sale)));
//									}
//									float newPrice = Float.parseFloat(newPriceStr);
//									object.addProperty("price", newPrice);
//								}
//								obj.add("price_adx", array);
//								JedisUtils.set(RedisKeyConstant.CREATIVE_INFO + mapid, obj.toString());
//							}
//						}
//						
//					}
//				}
//			}
//		}
//	}
	
	/**
	 * 获取时段
	 * @param historyData 时段参数
	 * @return
	 */
	public static Long getStartTimeByhistoryData(String historyData, Date endTime) {
		DateTime time = new DateTime(endTime);
		DateTimeFormatter format = DateTimeFormat .forPattern("yyyy-MM-dd HH:mm:ss");
		if ("06".equals(historyData)) {//过去24小时
			return time.minusHours(24).getMillis();
		} else if ("07".equals(historyData)) {//过去3天
			return time.minusDays(3).getMillis();
		} else if ("08".equals(historyData)) {//过去7天
			return time.minusDays(7).getMillis();
		} else if ("09".equals(historyData)) {//前一天:调用此处时，由于是前一天，此函数只对开始时间处理；*需要单独处理结束时间
			String day = time.minusDays(1).toString("yyyy-MM-dd");
			return DateTime.parse(day + " 00:00:00", format).getMillis();
		} else if ("10".equals(historyData)) {//当天
			String day = time.toString("yyyy-MM-dd");
			return DateTime.parse(day + " 00:00:00", format).getMillis();
		}
		return 0L;
	}
	
	/**
	 * 检查活动下是否已经有规则
	 * @param campaignId
	 * @param ruleId
	 * @return true：有；false：无
	 * @throws Exception
	 */
	public boolean checkGroupHaveRule(String campaignId) throws Exception {
		//如果活动已经有开启的规则，则提示错误；一个活动只能有一个规则限定
		CampaignRuleModelExample example = new CampaignRuleModelExample();
		example.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CampaignRuleModel> campaignRules = campaignRuleDao.selectByExample(example);
		int m = 0;
		if (campaignRules != null && !campaignRules.isEmpty()) {
			for (CampaignRuleModel model : campaignRules) {
				String id = model.getRuleId();
				String ruleType = model.getRuleType();
				if (StatusConstant.CAMPAIGN_RULE_TYPE_APP.equals(ruleType)) {
					AppRuleModel ruleModel = appRuleDao.selectByPrimaryKey(id);
					if (ruleModel != null && StatusConstant.CAMPAIGN_RULE_STATUS_USED.equals(ruleModel.getStatus())) {
						m = m + 1;
					}
				} else if (StatusConstant.CAMPAIGN_RULE_TYPE_REGION.equals(ruleType)) {
					RegionRuleModel ruleModel = regionRuleDao.selectByPrimaryKey(id);
					if (ruleModel != null && StatusConstant.CAMPAIGN_RULE_STATUS_USED.equals(ruleModel.getStatus())) {
						m = m + 1;
					}
				} else if (StatusConstant.CAMPAIGN_RULE_TYPE_TIME.equals(ruleType)) {
					TimeRuleModel ruleModel = timeRuleDao.selectByPrimaryKey(id);
					if (ruleModel != null && StatusConstant.CAMPAIGN_RULE_STATUS_USED.equals(ruleModel.getStatus())) {
						m = m + 1;
					}
				} else if (StatusConstant.CAMPAIGN_RULE_TYPE_LANDPAGE.equals(ruleType)) {
					LandpageRuleModel ruleModel = landpageRuleDao.selectByPrimaryKey(id);
					if (ruleModel != null && StatusConstant.CAMPAIGN_RULE_STATUS_USED.equals(ruleModel.getStatus())) {
						m = m + 1;
					}
				} else if (StatusConstant.CAMPAIGN_RULE_TYPE_CREATIVE.equals(ruleType)) {
					CreativeRuleModel ruleModel = creativeRuleDao.selectByPrimaryKey(id);
					if (ruleModel != null && StatusConstant.CAMPAIGN_RULE_STATUS_USED.equals(ruleModel.getStatus())) {
						m = m + 1;
					}
				}
				if (m > 0) {
					return true;
				}
			}
		} else {
			throw new ResourceNotFoundException("活动无绑定规则，无法开启");
		}
		return false;
	}
	
	
	
}
