package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.common.DateUtils;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignModelExample;
import com.pxene.pap.domain.models.CreativeAuditModel;
import com.pxene.pap.domain.models.CreativeAuditModelExample;
import com.pxene.pap.domain.models.view.CampaignTargetModel;
import com.pxene.pap.domain.models.view.CampaignTargetModelExample;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeAuditDao;
import com.pxene.pap.repository.basic.ProjectDao;
import com.pxene.pap.repository.basic.QuantityDao;
import com.pxene.pap.repository.basic.view.CampaignTargetDao;

@Service
public class LaunchService extends BaseService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LaunchService.class);
	
	@Autowired
	private RedisService redisService;
	
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
	
	/**
	 * 判断当前时间是否是活动的定向时间
	 * @param campaignId
	 * @return
	 * @throws Exception
	 */
	public boolean campaignIsInDateTarget(String campaignId) throws Exception {
		boolean Flag = false;
		//查询活动的时间定向ID
		CampaignModel campaign = campaignDao.selectByPrimaryKey(campaignId);
		if (campaign != null) {
			//如果开始时间或者结束时间为空，直接返回false
			if (campaign.getStartDate() == null || campaign.getEndDate() == null) {
				return Flag;
			}
			//获取出时间定向中的“天”（格式：“20170101”）数组，检查当前天是不是在数组中
			String[] days = DateUtils.getDaysBetween(campaign.getStartDate(), campaign.getEndDate());
			List<String> daysList = new ArrayList<String>(Arrays.asList(days));
			DateTime nowTime = new DateTime();
			String now = nowTime.toString("yyyyMMdd");
			if (daysList.contains(now)) {
				Flag = true;
				return Flag;
			}
		}
		return Flag;
	}
	/**
	 * 判断当前星期、时段是否是活动的定向星期、时段
	 * @param campaignId
	 * @return
	 * @throws Exception
	 */
	public boolean campaignIsInWeekAndTimeTarget(String campaignId) throws Exception {
		boolean Flag = false;
		String currentWeek = DateUtils.getCurrentWeekInNumber();//当前星期
		String currentHour = DateUtils.getCurrentHour();//当前小时
		//当前时间对应的时间ID
		String cId = "0" + currentWeek + currentHour;
		//查询活动的时间定向ID
		CampaignModel campaign = campaignDao.selectByPrimaryKey(campaignId);
		if (campaign != null) {
			String id = campaign.getId();
			//判断当前时间是不是在活动的开始时间和结束时间之间
			if (campaign.getStartDate() == null || campaign.getEndDate() == null) {
				return Flag;
			}
			//检查当前星期是不是在活动时间定向内
			CampaignTargetModelExample targetModelExample = new CampaignTargetModelExample();
			targetModelExample.createCriteria().andIdEqualTo(id);
			List<CampaignTargetModel> ctModels = campaignTargetDao.selectByExampleWithBLOBs(targetModelExample);
			if (ctModels != null && ctModels.size() > 0) {
				for (CampaignTargetModel ctModel : ctModels) {//此处虽然有循环，是因为查询视图生成xml中只能查询出多条；实际上每个活动只对应一条；因此下方直接返回flag；不会出错
					String timeId = ctModel.getTimeId();
					if (!StringUtils.isEmpty(timeId)) {
						String [] ids = timeId.split(",");
						//判断当前小时是否在投放时段中
						boolean flag = false;//默认不在
						for (String tId : ids) {
							if (cId.equals(tId)) {
								flag = true;
								break;
							}
						}
						return flag;
					}
				}
			}
		}
		return Flag;
	}
	
	// 手动投放时调用
	public void writeRedis(String campaignId) throws Exception {
		//写入活动下的创意基本信息   dsp_mapid_*
		redisService.writeCreativeInfo(campaignId);
		//写入活动下的创意ID dsp_group_mapids_*
		redisService.writeCreativeId(campaignId);
		//写入活动基本信息   dsp_group_info_*
		redisService.writeCampaignInfo(campaignId);
		//写入活动定向   dsp_group_target_*
		redisService.writeCampaignTarget(campaignId);
		//写入活动频次信息   dsp_groupid_frequencycapping_*
		redisService.writeCampaignFrequency(campaignId);
		//写入黑白名单信息
		redisService.writeWhiteBlack(campaignId);
		
//		//写入活动ID pap_groupids
//		redisService.writeCampaignIds(campaignId);
//		//写入活动预算
//		redisService.writeCampaignBudget(campaignId);
//		//写入活动展现
//		redisService.writeCampaignCounter(campaignId);
	}
	
	public void removeRedis(String campaignId) throws Exception {
		redisService.removeCreativeInfo(campaignId);
		redisService.removeCreativeId(campaignId);
		redisService.removeCampaignInfo(campaignId);
		redisService.removeCampaignTarget(campaignId);
		redisService.removeCampaignFrequency(campaignId);
		redisService.removeWhiteBlack(campaignId);
	}
	
/*	//定时器投放调用
	public void writeRedisByTime(String campaignId) throws Exception {
		//写入活动下的创意基本信息   dsp_mapid_*
		redisService.writeCreativeInfoToRedis(campaignId);
		//写入活动下的创意ID  dsp_group_mapids_*
		redisService.writeCreativeidToRedis(campaignId);
		//写入活动基本信息   dsp_group_info_*
		redisService.writeCampaignInfoToRedis(campaignId);
		//写入活动定向   dsp_group_target_*
		redisService.writeCampaignTargetToRedis(campaignId);
		//写入活动ID pap_groupids
		redisService.writeCampaignIds(campaignId);
		//写入活动频次信息   dsp_groupid_frequencycapping_*
		redisService.writeCampaignFrequencyToRedis(campaignId);
		//写入黑白名单信息
		redisService.writeWhiteBlackToRedis(campaignId);

		//活动预算、活动展现都由定时器方法中添加，在每天的00点时添加
	}*/
	
	/**
	 * 根据时间定向投放活动，结束到期活动————————————每小时投放项目定时器
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 */1 * * ?")
	public void launchByTime() throws Exception {
		// 当前小时
		String currentHour = DateUtils.getCurrentHour();
		// yyyy-MM-dd
		String currentDate = DateUtils.getCurrentDate();
		// 当前日期的整点时间
		Date start = DateUtils.strToDate(currentDate, "yyyy-MM-dd");
		// 当前日期退后一秒钟的时间
		Date end = DateUtils.changeDate(start, Calendar.SECOND, -1);
		
		CampaignModelExample campaignExammple = new CampaignModelExample();
		Date current = new Date();
		campaignExammple.createCriteria().andStartDateLessThanOrEqualTo(current).andEndDateGreaterThanOrEqualTo(current);
		// 所有正在投放的活动
		List<CampaignModel> launchCampaigns = campaignDao.selectByExample(campaignExammple);
		
		// 如果是0点，需要做如下事情：
		// 将今天开始投放的活动，所有数据写入redis
		// 将今天结束投放的活动，redis中的数据删除
		if ("00".equals(currentHour)) {
			// 找出今天开始投放的活动
			campaignExammple.clear();
			campaignExammple.createCriteria().andStartDateEqualTo(start);
			List<CampaignModel> addCampaigns = campaignDao.selectByExample(campaignExammple);
			for (CampaignModel campaign : addCampaigns) {
				String campaignId = campaign.getId();
				writeRedis(campaignId);
			}
			
			campaignExammple.clear();
			campaignExammple.createCriteria().andEndDateEqualTo(end);
			List<CampaignModel> delCampaigns = campaignDao.selectByExample(campaignExammple);
			for (CampaignModel campaign : delCampaigns) {
				String campaignId = campaign.getId();
				removeRedis(campaignId);
			}
			
			// 预算重新写入
			for (CampaignModel campaign : launchCampaigns) {
				String campaignId = campaign.getId();
				redisService.writeCampaignBudget(campaignId);
				redisService.writeCampaignCounter(campaignId);
			}
		}
		// 每个小时判断时间定向，将不在该时间内的活动移除
		for (CampaignModel campaign : launchCampaigns) {
			String campaignId = campaign.getId();
			// 如果当期时间在定向内
			if (campaignIsInWeekAndTimeTarget(campaignId)) {
				redisService.writeCampaignId(campaignId);
			} else {
				redisService.removeCampaignId(campaignId);
			}
		}
		
		
//		// 当前小时
//		String currentHour = DateUtils.getCurrentHour();
//		// 当前日期
//		String currentDate = DateUtils.getCurrentDate();
//		LOGGER.info(currentDate + " " + currentHour + ":00:00 定时器开始执行—————In LaunchService");
//		// 查询开启的项目
//		ProjectModelExample projectModelExample = new ProjectModelExample();
//		projectModelExample.createCriteria().andStatusEqualTo(StatusConstant.PROJECT_PROCEED);
//		List<ProjectModel> projects = projectDao.selectByExample(projectModelExample);
//		// 查询开启的活动
//		for (ProjectModel project : projects) {
//			String projectId = project.getId();
//			CampaignModelExample campaignModelExammple = new CampaignModelExample();
//			campaignModelExammple.createCriteria().andProjectIdEqualTo(projectId)
//					.andStatusEqualTo(StatusConstant.CAMPAIGN_PROCEED);
//			List<CampaignModel> campaigns = campaignDao.selectByExample(campaignModelExammple);
//			if (campaigns == null || campaigns.isEmpty()) {
//				continue;
//			}
//			// 查询活动的时间定向ID
//			for (CampaignModel campaign : campaigns) {
//				String campaignId = campaign.getId();
//				// 每天零点写入预算和展现key
//				if ("00".equals(currentHour)) {
//					//日展现上限
//					Integer totalBudget = campaign.getTotalBudget();
//					Integer budget = 0;
//					Integer counter = 0;
//					String count_key = RedisKeyConstant.CAMPAIGN_COUNTER + campaignId;
//					String budget_key = RedisKeyConstant.CAMPAIGN_BUDGET + campaignId;
//					QuantityModelExample example = new QuantityModelExample();
//					example.createCriteria().andCampaignIdEqualTo(campaignId);
//					List<QuantityModel> list = quantityDao.selectByExample(example);
//					if (list !=null && !list.isEmpty()) {
//						for (QuantityModel quan : list) {
//							Date startDate = quan.getStartDate();
//							Date endDate = quan.getEndDate();
//							String[] days = DateUtils.getDaysBetween(startDate, endDate);
//							List<String> dayList = Arrays.asList(days);
//							String time = new DateTime(new Date()).toString("yyyyMMdd");
//							if (dayList.contains(time)) {
//								counter = quan.getDailyImpression();
//								budget = quan.getDailyBudget();
//								break;
//							}
//						}
//					}
//					Map<String, String> value = new HashMap<String, String>();
//					value.put("total", String.valueOf(totalBudget * 100));
//					value.put("daily", String.valueOf(budget * 100));
//					
//					JedisUtils.set(count_key, String.valueOf(counter));//预算
//					JedisUtils.hset(budget_key, value);//展现上限
//				}
////				String status = campaign.getStatus();
//				//判断当前时间是不是在活动的开始时间和结束时间之间
//				Date start = campaign.getStartDate();
//				Date end = campaign.getEndDate();
//				Date now = new Date();
//				
//				if (now.after(end)) {//当前时间大于结束时间，状态变成已结束
//					campaign.setStatus(StatusConstant.CAMPAIGN_PAUSE);
//					redisService.deleteCampaignId(campaignId);
//				} else if (now.before(start)) {//当前时间小于开始时间时无操作
//				} else {//当前时间在开始时间和结束时间之间
//					if (campaignIsInWeekAndTimeTarget(campaignId)) {
//						writeRedisByTime(campaignId);
//						campaign.setStatus(StatusConstant.CAMPAIGN_PROCEED);
//					} else {
//						redisService.deleteCampaignId(campaignId);
//					}
//				}
//				campaignDao.updateByPrimaryKeySelective(campaign);
//			}
//		}
//		LOGGER.info(currentDate + " " + currentHour + ":00:00 定时器执行结束—————In LaunchService");
	}
	/**
	 * 修改已到过期时间的创意审核状态为“已过期”————————定时器
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void updateExpityDate() throws Exception {
		CreativeAuditModelExample example = new CreativeAuditModelExample();
		List<CreativeAuditModel> list = creativeAuditDao.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			for (CreativeAuditModel model : list) {
				Date expiryDate = model.getExpiryDate();
				if (expiryDate != null) {
					DateTime expiry = new DateTime(expiryDate);
					expiry.toString("yyyy-MM-dd");
					String now = new DateTime().toString("yyyy-MM-dd");
					if (expiry.equals(now)) {
						model.setStatus(StatusConstant.CREATIVE_AUDIT_EXPITY);
						creativeAuditDao.updateByPrimaryKeySelective(model);
					}
				}
			}
		}
	}
	
}
