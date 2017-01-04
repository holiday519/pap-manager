package com.pxene.pap.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.common.DateUtils;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignModelExample;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.domain.models.ProjectModelExample;
import com.pxene.pap.domain.models.TimeModel;
import com.pxene.pap.domain.models.TimeModelExample;
import com.pxene.pap.domain.models.view.CampaignTargetModel;
import com.pxene.pap.domain.models.view.CampaignTargetModelExample;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.ProjectDao;
import com.pxene.pap.repository.basic.TimeDao;
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
	private TimeDao timeDao;
	
	@Autowired
	private CampaignTargetDao campaignTargetDao;
	
	/**
	 * 投放
	 * @param campaignIds
	 * @throws Exception
	 */
	public void launch(String campaignId) throws Exception {
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
//		redisService.writeCampaignFrequencyToRedis(campaignId);
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
	public void launchByTime() throws Exception {
		String currentWeek = DateUtils.getCurrentWeekInNumber();//当前星期
		String currentHour = DateUtils.getCurrentHour();//当前小时
		String currentDay = DateUtils.getCurrentDay();//当前时间
		String currentData = DateUtils.getCurrentData();//当前日期
		LOGGER.info(currentData + " " + currentHour + "点定时器开始执行—————In PutOnService");
		//查询当前时间对应的时间ID
		TimeModelExample timeExample = new TimeModelExample();
		timeExample.createCriteria().andWeekEqualTo(currentWeek).andClockEqualTo(currentHour);
		List<TimeModel> times = timeDao.selectByExample(timeExample);
		String cId = times.get(0).getId();
		//查询投放中和等待中的项目
		ProjectModelExample projectExample = new ProjectModelExample();
		projectExample.createCriteria()
				.andStatusNotEqualTo(StatusConstant.CAMPAIGN_CLOSE)
				.andStatusNotEqualTo(StatusConstant.PROJECT_PAUSE);
		List<ProjectModel> projects = projectDao.selectByExample(projectExample);
		//查询非“已结束”的活动
		for (ProjectModel project : projects) {
			String projectId = project.getId();
			CampaignModelExample campaignExammple = new CampaignModelExample();
			campaignExammple.createCriteria().andProjectIdEqualTo(projectId)
					.andStatusNotEqualTo(StatusConstant.CAMPAIGN_CLOSE);
			List<CampaignModel> campaigns = campaignDao.selectByExample(campaignExammple);
			if (campaigns == null) {
				continue;
			}
			//查询活动的时间定向ID
			for (CampaignModel campaign : campaigns) {
				String id = campaign.getId();
				String status = campaign.getStatus();
				String dayOfChange = DateUtils.getDayOfChange(campaign.getEndDate(), 1);//结束时间的后一天
				boolean stopFlag = false;//是否停止项目标识
				//活动除了已停止状态 都需要判断是否到达结束时间，到达结束时间的一律改成已停止 并且删除redis投放key
				if (currentDay.equals(dayOfChange)) {
					campaign.setStatus(StatusConstant.CAMPAIGN_CLOSE);
					campaignDao.updateByPrimaryKey(campaign);
					redisService.deleteCampaignId(id);
					stopFlag = true;
				}
				//非暂停状态才去判断是否需要投放 && 当前的项目没到停止时间
				if (!StatusConstant.CAMPAIGN_PAUSE.equals(status) && !stopFlag) {
					CampaignTargetModelExample targetModelExample = new CampaignTargetModelExample();
					targetModelExample.createCriteria().andIdEqualTo(id);
					List<CampaignTargetModel> ctModels = campaignTargetDao.selectByExampleWithBLOBs(targetModelExample);
					if (ctModels != null && ctModels.size() > 0) {
						for (CampaignTargetModel ctModel : ctModels) {
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
								//如果 时间定向中有当前小时，添加；没有则删除
								if (flag) {
									redisService.writeCampaignIds(id);
								} else {
									redisService.deleteCampaignId(id);
								}
							}
						}
					}
				}
			}
		}
		LOGGER.info(currentData + " " + currentHour + "点定时器执行结束—————In PutOnService");
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
