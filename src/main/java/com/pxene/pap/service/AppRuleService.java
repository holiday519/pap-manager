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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pxene.pap.common.JedisUtils;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.RedisKeyConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.DayAndHourDataBean;
import com.pxene.pap.domain.beans.RuleBean;
import com.pxene.pap.domain.beans.RuleBean.Condition;
import com.pxene.pap.domain.models.AppRuleModel;
import com.pxene.pap.domain.models.AppRuleModelExample;
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
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AppRuleDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CampaignRuleDao;
import com.pxene.pap.repository.basic.CreativeRuleDao;
import com.pxene.pap.repository.basic.LandpageRuleDao;
import com.pxene.pap.repository.basic.RegionRuleDao;
import com.pxene.pap.repository.basic.RuleConditionDao;
import com.pxene.pap.repository.basic.TimeRuleDao;

@Service
public class AppRuleService extends BaseService {
	
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
	private RuleConditionDao ruleConditionDao;
	
	@Autowired
	private CampaignRuleDao campaignRuleDao;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private AppDataHourService appDataHourService;
	
	public void saveAppRule(RuleBean ruleBean) throws Exception {
		AppRuleModel model = modelMapper.map(ruleBean, AppRuleModel.class);
		String ruleId = UUID.randomUUID().toString();
		model.setId(ruleId);
		model.setStatus(StatusConstant.CAMPAIGN_RULE_STATUS_UNUSED);
		
		try {
			ruleBean.setId(ruleId);
			//添加关联关系
			addCampaignAndRule(ruleBean, StatusConstant.CAMPAIGN_RULE_TYPE_APP);
			//添加规则——条件
			addRuleCondition(ruleBean);
			appRuleDao.insertSelective(model);
        } catch (DuplicateKeyException exception) {
            // 违反数据库唯一约束时，向上抛出自定义异常，交给全局异常处理器处理
            throw new DuplicateEntityException();
        }
		
		BeanUtils.copyProperties(model, ruleBean);
	}
	
	public void deleteAppRule(String id) throws Exception {
		AppRuleModel modelInDB = appRuleDao.selectByPrimaryKey(id);
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
			appRuleDao.deleteByPrimaryKey(id);
		}
		
	}
	
	public void updateAppRule(String id, RuleBean ruleBean) throws Exception {
		AppRuleModel modelInDB = appRuleDao.selectByPrimaryKey(id);
		if (modelInDB == null) {
			throw new ResourceNotFoundException();
		}
		
		AppRuleModel ruleModel = modelMapper.map(ruleBean, AppRuleModel.class);
		//删除所有关联关系
		deleteCampaignAndRule(id);
		//删除规则条件
		deleteRuleConditionById(id);
		//重新添加规则——条件
		addRuleCondition(ruleBean);
		//重新添加关联关系
		ruleBean.setId(id);
		addCampaignAndRule(ruleBean, StatusConstant.CAMPAIGN_RULE_TYPE_APP);
		
		try {
			// 修改规则信息
			appRuleDao.updateByPrimaryKeySelective(ruleModel);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
	}
	
	public RuleBean findAppRuleById(String id) throws Exception {
		AppRuleModel appRuleModel = appRuleDao.selectByPrimaryKey(id);
		if(appRuleModel == null){
			throw new ResourceNotFoundException();
		}
		RuleBean ruleBean = modelMapper.map(appRuleModel, RuleBean.class);
		RuleBean bean = getParamForRule(ruleBean);
		
		return bean;
	}
	
	public List<RuleBean> listAppRule(String name) throws Exception {
		AppRuleModelExample example = new AppRuleModelExample();
		
		if (!StringUtils.isEmpty(name)) {
			example.createCriteria().andNameLike("%" + name + "%");
		}
		List<AppRuleModel> rules = appRuleDao.selectByExample(example);
		List<RuleBean> beans = new ArrayList<RuleBean>();
		
		if (rules == null || rules.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		for (AppRuleModel model : rules) {
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
	@Transactional
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
	@Transactional
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
	@Transactional
	public void updateAppRuleStatus(String id, Map<String, String> map) throws Exception {
		if (StringUtils.isEmpty(map.get("action"))) {
			throw new IllegalArgumentException();
		}
		AppRuleModel ruleModel = appRuleDao.selectByPrimaryKey(id);
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
		appRuleDao.updateByPrimaryKeySelective(ruleModel);
	}
	
	/**
	 * 向规则——条件表插入数据
	 * @param ruleBean
	 * @throws Exception
	 */
	@Transactional
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
	@Transactional
	public void deleteRuleConditionById(String ruleId) {
		if (!StringUtils.isEmpty(ruleId)) {
			RuleConditionModelExample example = new RuleConditionModelExample();
			example.createCriteria().andRuleIdEqualTo(ruleId);
			ruleConditionDao.deleteByExample(example );
		}
	}
	
	/**
	 * 打开App规则
	 * @param campaignId
	 * @param ruleId
	 * @throws Exception
	 */
	@Transactional
	public void openCampaignAppRule(String campaignId, String ruleId) throws Exception {

		if (checkGroupHaveRule(campaignId)) {
			throw new IllegalStatusException("活动下已有开启的规则，无法再次开启规则");
		}
		
		String Id1 = UUID.randomUUID().toString();
		String Id2 = UUID.randomUUID().toString();
		Date time = new Date();//当前时间
		//查询规则
		AppRuleModel ruleModel = appRuleDao.selectByPrimaryKey(ruleId);
		String historyData = ruleModel.getHistoryData();//时段
		Float fare = ruleModel.getFare();//提价比
		Float sale = ruleModel.getSale();//降价比
		//根据时段设置开始时间、结束时间
		Long beginTime = getStartTimeByhistoryData(historyData, time);
		Long endTime = time.getTime();
		//如果时间为“前一天”时，结束时间需要特殊处理
		if ("09".equals(historyData)) {
			DateTimeFormatter format = DateTimeFormat .forPattern("yyyy-MM-dd HH:mm:ss");
			String day = new DateTime(time).minusDays(1).toString("yyyy-MM-dd");
			endTime = DateTime.parse(day + " 23:59:59", format).getMillis();
		}
		//查询app数据
		List<DayAndHourDataBean> appDataHour = appDataHourService.listAppDataHour(campaignId, beginTime, endTime);
		if (appDataHour == null || appDataHour.isEmpty()) {
			throw new ResourceNotFoundException("当前活动无APP投放数据，不能开启app规则");
		}
		//查询规则下的条件
		RuleConditionModelExample conditionExample = new RuleConditionModelExample();
		conditionExample.createCriteria().andRuleIdEqualTo(ruleId);
		List<RuleConditionModel> conditions = ruleConditionDao.selectByExample(conditionExample);
		
		List<String> upList = new ArrayList<String>();//加价的活动id
		List<String> downList = new ArrayList<String>();//减价的活动id
		for (DayAndHourDataBean bean : appDataHour) {//--先找出需要减钱的appid
			//要求每一条都符合所有的条件才能加价，否则就减价
			for (RuleConditionModel condition : conditions) {//不符合其中任意一个条件的，就属于减价
				double data = 0;
				if ("01".equals(condition.getDataType())) {
					data = bean.getBidAmount();
				} else if ("02".equals(condition.getDataType())) {
					data = bean.getWinAmount();
				} else if ("03".equals(condition.getDataType())) {
					data = bean.getWinRate();
				} else if ("04".equals(condition.getDataType())) {
					data = bean.getImpressionAmount();
				} else if ("05".equals(condition.getDataType())) {
					data = bean.getImpressionRate();
				} else if ("06".equals(condition.getDataType())) {
					data = bean.getClickAmount();
				} else if ("07".equals(condition.getDataType())) {
					data = bean.getClickRate();
				} else if ("08".equals(condition.getDataType())) {
					data = bean.getArrivalAmount();
				} else if ("09".equals(condition.getDataType())) {
					data = bean.getArrivalRate();
				} else if ("10".equals(condition.getDataType())) {
					data = bean.getUniqueAmount();
				}
				String AppId = bean.getAppId();
				if ("01".equals(condition.getCompareType())) {//条件选择大于的时候，如果数据值不大于条件值，就放入降价id中
					if (data < condition.getData()) {
						if (!downList.contains(AppId)) {
							downList.add(AppId);
						}
					}
				} else {//条件选择小于
					if (data >= condition.getData()) {
						if (!downList.contains(AppId)) {
							downList.add(AppId);
						}
					}
				}
			}
		}
		//查询数据中除去减钱的剩下的就是加钱的-----在定向里的id，却没有投放数据（根据判断代码逻辑，不符合降价，也不符合升价），此处会将这些id丢掉。--？
		for (DayAndHourDataBean bean : appDataHour) {
			if (!downList.isEmpty() && !downList.contains(bean.getAppId())) {
				upList.add(bean.getAppId());
			}
		}
		
		//已经找出升价的appid和降价appid，进行拆分key
		//查询redis中活动定向
		String campaignTarget = JedisUtils.getStr(RedisKeyConstant.CAMPAIGN_TARGET + campaignId);
		
		Gson gson = new Gson();
		
		if(!StringUtils.isEmpty(campaignTarget)) {
			JsonObject targetObj1 = gson.fromJson(campaignTarget, new JsonObject().getClass());
			JsonObject targetObj2 = gson.fromJson(campaignTarget, new JsonObject().getClass());
			targetObj1.addProperty("groupid", Id1);
			targetObj2.addProperty("groupid", Id2);
			StringBuffer string1 = new StringBuffer("");
			for (int i = 0; 1 < upList.size(); i++) {
				string1.append(upList.get(i));
				if (i < upList.size() - 1) {
					string1.append(",");
				}
			}
			StringBuffer string2 = new StringBuffer("");
			for (int i = 0; 1 < downList.size(); i++) {
				string2.append(downList.get(i));
				if (i < downList.size() - 1) {
					string2.append(",");
				}
			}
			JsonObject appObj1 = redisService.createAppTargetJson(string1.toString());
			JsonObject appObj2 = redisService.createAppTargetJson(string2.toString());
			targetObj1.add("app", appObj1);
			targetObj2.add("app", appObj2);
			
			JedisUtils.set(RedisKeyConstant.CAMPAIGN_TARGET + Id1, targetObj1.toString());
			JedisUtils.set(RedisKeyConstant.CAMPAIGN_TARGET + Id2, targetObj2.toString());
		}
		
		//活动基本信息拆分
		String campaignInfo = JedisUtils.getStr(RedisKeyConstant.CAMPAIGN_INFO + campaignId);
		if (!StringUtils.isEmpty(campaignInfo)) {
			JedisUtils.set(RedisKeyConstant.CAMPAIGN_INFO + Id1, campaignInfo);
			JedisUtils.set(RedisKeyConstant.CAMPAIGN_INFO + Id2, campaignInfo);
		}
		
		//活动下mapIds
		String campaignMapId = JedisUtils.getStr(RedisKeyConstant.CAMPAIGN_MAPIDS + campaignId);
		//将mapid分成两份
		List<String> mapIdList1 = new ArrayList<String>();
		List<String> mapIdList2 = new ArrayList<String>();
		if (!StringUtils.isEmpty(campaignMapId)) {
			JsonObject mapIdJson = gson.fromJson(campaignMapId, new JsonObject().getClass());
			JsonArray mapids = mapIdJson.getAsJsonArray("mapids");
			for (int i = 0; i < mapids.size(); i++) {
				String mapid = JedisUtils.getStr(RedisKeyConstant.CREATIVE_INFO + mapids.get(i));
				if (StringUtils.isEmpty(mapid)) {
					continue;
				}
				String mapid1 = UUID.randomUUID().toString();
				String mapid2 = UUID.randomUUID().toString();
				mapIdList1.add(mapid1);
				mapIdList2.add(mapid2);
				JsonObject obj1 = gson.fromJson(mapid, new JsonObject().getClass());
				JsonObject obj2 = gson.fromJson(mapid, new JsonObject().getClass());
				obj1.addProperty("groupid", Id1);
				obj2.addProperty("groupid", Id2);
				JsonArray array1 = obj1.getAsJsonArray("price_adx");
				JsonArray array2 = obj2.getAsJsonArray("price_adx");
				DecimalFormat format = new DecimalFormat("##.##");
				for (int m = 0; m < array1.size(); m++) {//加价
					JsonObject object = array1.get(m).getAsJsonObject();
					Float price = object.get("price").getAsFloat();
					String newPriceStr = format.format(new BigDecimal(price).multiply(new BigDecimal(1 + fare)));
					float newPrice = Float.parseFloat(newPriceStr);
					object.addProperty("price", newPrice);
				}
				for (int m = 0; m < array2.size(); m++) {//降价
					JsonObject object = array2.get(m).getAsJsonObject();
					Float price = object.get("price").getAsFloat();
					String newPriceStr = format.format(new BigDecimal(price).multiply(new BigDecimal(1 - sale)));
					float newPrice = Float.parseFloat(newPriceStr);
					object.addProperty("price", newPrice);
				}
				obj1.add("price_adx", array1);
				obj2.add("price_adx", array2);
				JedisUtils.set("part_child_mapId_" + mapid1, mapid);
				JedisUtils.set("part_child_mapId_" + mapid2, mapid);
				String str = JedisUtils.getStr("part_parent_mapId_" + mapid);
				if (StringUtils.isEmpty(str)) {
					JedisUtils.set("part_parent_mapId_" + mapid, mapid1 + "," + mapid2);
				} else {
					JedisUtils.set("part_parent_mapId_" + mapid, str + "," + mapid1 + "," + mapid2);
				}
				JedisUtils.set(RedisKeyConstant.CREATIVE_INFO + mapid1, obj1.toString());
				JedisUtils.set(RedisKeyConstant.CREATIVE_INFO + mapid2, obj2.toString());
			}
		}
		
		//写入活动下的创意
		JsonArray mapIdArr1 = new JsonArray();
		JsonArray mapIdArr2 = new JsonArray();
		JsonObject mapIdObj1 = new JsonObject();
		JsonObject mapIdObj2 = new JsonObject();
		for (int i = 0; i < upList.size(); i++) {
			mapIdArr1.add(upList.get(i));
		}
		for (int i = 0; i < downList.size(); i++) {
			mapIdArr2.add(downList.get(i));
		}
		mapIdObj1.add("mapids", mapIdArr1);
		mapIdObj2.add("mapids", mapIdArr2);
		JedisUtils.set(RedisKeyConstant.CAMPAIGN_MAPIDS + Id1, mapIdObj1.toString());
		JedisUtils.set(RedisKeyConstant.CAMPAIGN_MAPIDS + Id2, mapIdObj2.toString());
		
		JedisUtils.set("part_child_campaignId_" + Id1, campaignId);
		JedisUtils.set("part_child_campaignId_" + Id2, campaignId);
		String str = JedisUtils.getStr("part_parent_campaignId_" + campaignId);
		if (StringUtils.isEmpty(str)) {
			JedisUtils.set("part_parent_campaignId_" + campaignId, Id1 + "," + Id2);
		} else {
			JedisUtils.set("part_parent_campaignId_" + campaignId, str + "," + Id1 + "," + Id2);
		}
		
		//活动投放（被拆分的移除，拆分出的加入）
		redisService.deleteCampaignId(campaignId);
		redisService.writeCampaignIds(Id1);
		redisService.writeCampaignIds(Id2);
		
		AppRuleModel model = appRuleDao.selectByPrimaryKey(ruleId);
		model.setStatus(StatusConstant.CAMPAIGN_RULE_STATUS_USED);
		appRuleDao.updateByPrimaryKey(model);
	}
	
	/**
	 * 获取时段
	 * @param historyData 时段参数
	 * @return
	 */
	public static Long getStartTimeByhistoryData(String historyData, Date endTime) {
		DateTime time = new DateTime(endTime);
		DateTimeFormatter format = DateTimeFormat .forPattern("yyyy-MM-dd HH:mm:ss");
		if ("01".equals(historyData)) {//过去1小时
			return time.minusHours(1).getMillis();
		} else if ("02".equals(historyData)) {//过去2小时
			return time.minusHours(2).getMillis();
		} else if ("03".equals(historyData)) {//过去3小时
			return time.minusHours(3).getMillis();
		} else if ("04".equals(historyData)) {//过去6小时
			return time.minusHours(6).getMillis();
		} else if ("05".equals(historyData)) {//过去12小时
			return time.minusHours(12).getMillis();
		} else if ("06".equals(historyData)) {//过去24小时
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
