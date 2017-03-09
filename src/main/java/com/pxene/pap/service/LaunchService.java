package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.domain.models.ProjectModelExample;
import com.pxene.pap.domain.models.view.CampaignTargetModel;
import com.pxene.pap.domain.models.view.CampaignTargetModelExample;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.ProjectDao;
import com.pxene.pap.repository.basic.view.CampaignTargetDao;

@Service
public class LaunchService extends BaseService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LaunchService.class);
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired
	private CampaignTargetDao campaignTargetDao;
	
	/**
	 * 投放
	 * @param campaignIds
	 * @throws Exception
	 */
	public void launch(String campaignId) throws Exception {
		if (campaignIsInTimeTarget(campaignId)) {
			writeRedis(campaignId);
		}
	}
	
	/**
	 * 判断当前时间是否是活动的定向时间
	 * @param campaignId
	 * @return
	 * @throws Exception
	 */
	public boolean campaignIsInTimeTarget(String campaignId) throws Exception {
		boolean Flag = false;
		String currentWeek = DateUtils.getCurrentWeekInNumber();//当前星期
		String currentHour = DateUtils.getCurrentHour();//当前小时
		//当前时间对应的时间ID
		String cId = "0" + currentWeek + currentHour;
		//查询活动的时间定向ID
		CampaignModel campaign = campaignDao.selectByPrimaryKey(campaignId);
		if (campaign != null) {
			String id = campaign.getId();
//			String status = campaign.getStatus();
//			if (StatusConstant.CAMPAIGN_PAUSE.equals(status)) {//如果已经结束了，直接返回“否”就可以
//				return Flag;
//			}
			//判断当前时间是不是在活动的开始时间和结束时间之间
			if (campaign.getStartDate() == null || campaign.getEndDate() == null) {
				return Flag;
			}
			String[] days = DateUtils.getDaysBetween(campaign.getStartDate(), campaign.getEndDate());
			List<String> daysList = new ArrayList<String>(Arrays.asList(days));
			DateTime nowTime = new DateTime();
			String now = nowTime.toString("yyyyMMdd");
			if (!daysList.contains(now)) {
				return Flag;
			}
			//检查当前小时、星期是不是在活动时间定向内
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
	
	public void writeRedis(String campaignId) throws Exception {
		//写入活动下的创意基本信息   dsp_mapid_*
		redisService.writeCreativeInfoToRedis(campaignId);
		//写入活动下的创意ID  dsp_group_mapids_*
		redisService.writeMapidToRedis(campaignId);
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
		//写入项目预算
		redisService.writeProjectBudgetToRedis(campaignId);
		//写入活动预算
		redisService.writeCampaignBudgetToRedis(campaignId);
		//写入活动展现
		redisService.writeCampaignCounterToRedis(campaignId);
	}
	
	/**
	 * 暂停
	 * @param campaignId
	 * @throws Exception
	 */
	public void pause(String campaignId) throws Exception {
		//从redis中删除投放中的活动id
		redisService.deleteCampaignId(campaignId);
	}
	
	/**
	 * 根据时间定向投放活动，结束到期活动
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 */1 * * ?")
	public void launchByTime() throws Exception {
		String currentHour = DateUtils.getCurrentHour();//当前小时
		String currentDate = DateUtils.getCurrentDate();//当前日期
		LOGGER.info(currentDate + " " + currentHour + ":00:00 定时器开始执行—————In LaunchService");
		//查询投放中的项目
		ProjectModelExample projectExample = new ProjectModelExample();
		projectExample.createCriteria().andStatusNotEqualTo(StatusConstant.PROJECT_PAUSE);
		List<ProjectModel> projects = projectDao.selectByExample(projectExample);
		//查询非“已结束”的活动
		for (ProjectModel project : projects) {
			String projectId = project.getId();
			CampaignModelExample campaignExammple = new CampaignModelExample();
			campaignExammple.createCriteria().andProjectIdEqualTo(projectId)
					.andStatusNotEqualTo(StatusConstant.CAMPAIGN_PAUSE);
			List<CampaignModel> campaigns = campaignDao.selectByExample(campaignExammple);
			if (campaigns == null) {
				continue;
			}
			
			//查询活动的时间定向ID
			for (CampaignModel campaign : campaigns) {
				String id = campaign.getId();
//				String status = campaign.getStatus();
				//判断当前时间是不是在活动的开始时间和结束时间之间
//				DateTime startTime = new DateTime(campaign.getStartDate());
				DateTime endTime = new DateTime(campaign.getEndDate());
				DateTime nowTime = new DateTime();
				
//				String start = startTime.toString("yyyyMMdd");
				String end = endTime.toString("yyyyMMdd");
				String now = nowTime.toString("yyyyMMdd");
				
				if (Long.parseLong(now) > Long.parseLong(end)) {//当前时间大于结束时间，状态变成已结束
					campaign.setStatus(StatusConstant.CAMPAIGN_PAUSE);
					redisService.deleteCampaignId(id);
//				} else if (Long.parseLong(now) < Long.parseLong(start)) {//当前时间小于开始时间时无操作
				} else {//当前时间在开始时间和结束时间之间
					if (campaignIsInTimeTarget(id)) {
						writeRedis(id);
						campaign.setStatus(StatusConstant.CAMPAIGN_PROCEED);
					} else {
						redisService.deleteCampaignId(id);
					}
				}
				campaignDao.updateByPrimaryKeySelective(campaign);
			}
		}
		LOGGER.info(currentDate + " " + currentHour + ":00:00 定时器执行结束—————In LaunchService");
	}
	
	/**
	 * 删除redis中活动相关key
	 * @param campaignId
	 * @throws Exception
	 */
	public void deleteKeyFromRedis(String campaignId) throws Exception {
		redisService.deleteKeyFromRedis(campaignId);
	}
	
}
