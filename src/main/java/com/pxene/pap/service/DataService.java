package com.pxene.pap.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.JedisUtils;
import com.pxene.pap.domain.beans.BasicDataBean;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.CreativeModelExample;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.domain.models.ProjectModelExample;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.ProjectDao;

@Service
public class DataService {
	@Autowired
	private CreativeDao creativeDao;

	@Autowired
	private CampaignDao campaignDao;

	@Autowired
	private ProjectDao projectDao;

	public List<BasicDataBean> get(String type, List<String> creativeIds) throws Exception {
		return null;
	}

	public List<String> findCreativeIdsByCampaignId(String campaignId) throws Exception {
		// TODO 自动生成的方法存根
		return null;
	}
	
	/**
	 * 查询小时数据
	 * @param beginTime
	 * @param endTime
	 * @param advertiserId
	 * @param projectId
	 * @param campaignId
	 * @param creativeId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getDataForTime(Long beginTime, Long endTime, String advertiserId, 
			String projectId, String campaignId, String creativeId) throws Exception {
		
		List<String> creativeIds = getCreativeIdListByParam(advertiserId, projectId, campaignId, creativeId);
		
		List<Map<String, Object>> list = getDatafromHourTableForTime(creativeIds, new Date(beginTime), new Date(endTime));
		
		return list;
	}
	
	/**
	 * 查询创意投放数据
	 * @param creativeIds
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public BasicDataBean getCreativeDatas(List<String> creativeIds, Long beginTime, Long endTime) throws Exception {
		BasicDataBean basicData = new BasicDataBean();
		FormatBeanParams(basicData);//将属性值变成0
		DateTime begin = new DateTime(beginTime);
    	DateTime end = new DateTime(endTime);
    	if (end.toString("yyyy-MM-dd").equals(begin.toString("yyyy-MM-dd"))) {
    		//查看是不是全天(如果是全天，查询天文件；但是时间不可以是今天，因为当天数据还未生成天文件)
    		if (begin.toString("HH").equals("00") && end.toString("HH").equals("23")
					&& !begin.toString("yyyy-MM-dd").equals(new DateTime().toString("yyyy-MM-dd"))) {
				List<String> days = new ArrayList<String>();
				days.add(begin.toString("yyyyMMdd"));
				getDatafromDayTable(creativeIds, days, basicData);
			} else {//如果是今天就要查询小时数据
				getDatafromHourTable(creativeIds, begin.toDate(), end.toDate(), basicData);
			}
    	} else {
    		String[] days = DateUtils.getDaysBetween(begin.toDate(), end.toDate());
			List<String> daysList = new ArrayList<String>(Arrays.asList(days));
			if (!begin.toString("HH").equals("00")) {
				Date bigHourOfDay = DateUtils.getBigHourOfDay(begin.toDate());
				getDatafromHourTable(creativeIds, begin.toDate(), bigHourOfDay, basicData);
				if (daysList != null && daysList.size() > 0) {
					for (int i = 0; i < daysList.size(); i++) {
						if (daysList.get(i).equals(begin.toString("yyyyMMdd"))) {
							daysList.remove(i);
						}
					}
				}
			}
			if (!end.toString("HH").equals("23")) {
				Date smallHourOfDay = DateUtils.getSmallHourOfDay(begin.toDate());
				getDatafromHourTable(creativeIds, smallHourOfDay, begin.toDate(), basicData);
				if (daysList != null && daysList.size() > 0) {
					for (int i = 0; i < daysList.size(); i++) {
						if (daysList.get(i).equals(end.toString("yyyyMMdd"))) {
							daysList.remove(i);
						}
					}
				}
			}
			getDatafromDayTable(creativeIds, daysList, basicData);
    	}
    	
    	FormatBeanRate(basicData);
		return basicData;
	}
	
	/**
	 * 取天数据
	 * @param creativeIds
	 * @param daysList
	 * @param bean
	 * @throws Exception
	 */
	private void getDatafromDayTable(List<String> creativeIds, List<String> daysList, BasicDataBean bean) throws Exception {
		for (String creativeId : creativeIds) {
			Map<String, String> map = JedisUtils.hget("creativeDataDay_" + creativeId);//获取map集合
			Set<String> hkeys = JedisUtils.hkeys("creativeDataDay_" + creativeId);//获取所有key
			if (hkeys != null && !hkeys.isEmpty()) {
				for (String hkey : hkeys) {
					//必须要符合“日期”+“@”才是创意的数据
					for (String day : daysList) {
						if (hkey.indexOf(day + "@") > -1) {
							String value = map.get(hkey);
							//根据不同的值来整合不同属性值
							if (!StringUtils.isEmpty(value)) {
								if (hkey.indexOf("@m") > 0) {// 展现
									bean.setImpressionAmount(bean.getImpressionAmount() + Long.parseLong(value));
								} else if (hkey.indexOf("@c") > 0) {// 点击
									bean.setClickAmount(bean.getClickAmount() + Long.parseLong(value));
								} else if (hkey.indexOf("@j") > 0) {// 二跳
									bean.setJumpAmount(bean.getJumpAmount() + Long.parseLong(value));
								} else if (hkey.indexOf("@e") > 0) {// 花费
									bean.setTotalCost(bean.getTotalCost() + Float.parseFloat(value));
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 取小时数据
	 * @param creativeIds
	 * @param beginTime
	 * @param endTime
	 * @param bean
	 * @throws Exception
	 */
	private void getDatafromHourTable(List<String> creativeIds, Date beginTime, Date endTime, BasicDataBean bean) throws Exception {
		String[] hours = DateUtils.getHoursBetween(beginTime, endTime);
		String day = new DateTime(beginTime).toString("yyyyMMdd");
		for (String creativeId : creativeIds) {
			Map<String, String> map = JedisUtils.hget("creativeDataHour_" + creativeId);//获取map集合
			Set<String> hkeys = JedisUtils.hkeys("creativeDataHour_" + creativeId);//获取所有key
			if (hkeys != null && !hkeys.isEmpty()) {
				for (String hkey : hkeys) {
					//必须要符合“日期”+“小时”+“@”才是创意的数据
					for (String hour : hours) {
						if (hkey.indexOf(day + hour + "@") > -1) {
							String value = map.get(hkey);
							//根据不同的值来整合不同属性值
							if (!StringUtils.isEmpty(value)) {
								if (hkey.indexOf("@m") > 0) {// 展现
									bean.setImpressionAmount(bean.getImpressionAmount() + Long.parseLong(value));
								} else if (hkey.indexOf("@c") > 0) {// 点击
									bean.setClickAmount(bean.getClickAmount() + Long.parseLong(value));
								} else if (hkey.indexOf("@j") > 0) {// 二跳
									bean.setJumpAmount(bean.getJumpAmount() + Long.parseLong(value));
								} else if (hkey.indexOf("@e") > 0) {// 花费
									bean.setTotalCost(bean.getTotalCost() + Float.parseFloat(value));
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * 取小时数据(查询时间数据用)
	 * @param creativeIds
	 * @param beginTime
	 * @param endTime
	 * @param bean
	 * @throws Exception
	 */
	private List<Map<String, Object>> getDatafromHourTableForTime(List<String> creativeIds, Date beginTime, Date endTime) throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String timeHours[] = new String[]{"00","01","02","03","04","05","06","07","07","08","09","10",
				"11","12","13","14","15","16","17","18","19","20","21","22","23"};
		String[] days = DateUtils.getDaysBetween(beginTime, endTime);
		BasicDataBean bean = null;
		Map<String, Object> resultMap = null;
		for (String hour : timeHours) {
			bean = new BasicDataBean();
			resultMap = new HashMap<String, Object>();
			FormatBeanParams(bean);//将属性值变成0
			for (String creativeId : creativeIds) {
				Map<String, String> map = JedisUtils.hget("creativeDataHour_" + creativeId);//获取map集合
				for (String day : days) {
					String hkey = day + hour + "@";
					
					//根据不同的值来整合不同属性值
					String imp = map.get(hkey + "m");
					String clk = map.get(hkey + "c");
					String jump = map.get(hkey + "j");
					String exp = map.get(hkey + "e");
					
					if (!StringUtils.isEmpty(imp)) {// 展现
						bean.setImpressionAmount(bean.getImpressionAmount() + Long.parseLong(imp));
					}
					if (!StringUtils.isEmpty(clk)) {// 点击
						bean.setClickAmount(bean.getClickAmount() + Long.parseLong(clk));
					}
					if (!StringUtils.isEmpty(jump)) {// 二跳
						bean.setJumpAmount(bean.getJumpAmount() + Long.parseLong(jump));
					}
					if (!StringUtils.isEmpty(exp)) {// 花费--expense
						bean.setTotalCost(bean.getTotalCost() + Float.parseFloat(exp));
					}
				}
			}
			FormatBeanRate(bean);
			
			resultMap.put("time", hour);
			resultMap.put("impressionAmount", bean.getImpressionAmount());
			resultMap.put("clickAmount", bean.getClickAmount());
			resultMap.put("jumpAmount", bean.getJumpAmount());
			resultMap.put("clickRate", bean.getClickRate());
			resultMap.put("totalCost", bean.getTotalCost());
			resultMap.put("impressionCost", bean.getImpressionCost());
			resultMap.put("clickCost", bean.getClickCost());
			resultMap.put("jumpCost", bean.getJumpCost());

			result.add(resultMap);
		}
		return result;
	}
	
	/**
	 * 将实例化的bean种属性值变成0
	 * @param bean
	 * @throws Exception
	 */
	private void FormatBeanParams(BasicDataBean bean) throws Exception {
		bean.setImpressionAmount(0L);
		bean.setClickAmount(0L);
		bean.setJumpAmount(0L);
		bean.setTotalCost(0F);
		
		bean.setImpressionCost(0F);
		bean.setClickRate(0F);
		bean.setClickCost(0F);
		bean.setJumpCost(0F);
	}
	
	/**
	 * 格式话“率”
	 * @param bean
	 * @throws Exception
	 */
	private void FormatBeanRate(BasicDataBean bean) throws Exception {
		DecimalFormat format = new DecimalFormat("0.00000");
		if (bean.getTotalCost() > 0) {
			double percent = (double)bean.getImpressionAmount() / bean.getTotalCost();
	        Float result = Float.parseFloat(format.format(percent));
	        bean.setImpressionCost(result);
	        
	        percent = (double)bean.getClickAmount() / bean.getTotalCost();
	        result = Float.parseFloat(format.format(percent));
	        bean.setClickCost(result);
	        
	        percent = (double)bean.getJumpAmount() / bean.getTotalCost();
	        result = Float.parseFloat(format.format(percent));
	        bean.setJumpCost(result);
		}
		if (bean.getImpressionAmount() > 0) {
			double percent = (double)bean.getClickAmount() / bean.getImpressionAmount();
	        Float result = Float.parseFloat(format.format(percent));
	        bean.setClickRate(result);
		}
	}
	
	/**
	 * 获取创意ID数组
	 * @param advertiserId
	 * @param projectId
	 * @param campaignId
	 * @param creativeId
	 * @return
	 * @throws Exception
	 */
	public List<String> getCreativeIdListByParam(String advertiserId, String projectId, String campaignId, String creativeId) throws Exception {
		List<String> creativeIds = new ArrayList<String>();
		if (!StringUtils.isEmpty(creativeId)) {
			creativeIds.add(creativeId);
		} else {
			if (!StringUtils.isEmpty(campaignId)) {
				creativeIds = getCreativeIdListByCampaignId(campaignId);
			} else {
				if (!StringUtils.isEmpty(projectId)) {
					creativeIds = getCreativeIdListByProjectId(projectId);
				} else {
					if (!StringUtils.isEmpty(advertiserId)) {
						creativeIds = getCreativeIdListByCampaignId(campaignId);
					} else {
						throw new IllegalArgumentException();
					}
				}
			}
		}
		return creativeIds;
	}

	/**
	 * 根据项目ID，查询该项目下的全部活动ID。
	 * 
	 * @param projectId
	 *            项目ID
	 * @return
	 */
	public List<String> findCampaignIdListByProjectId(String projectId)
			throws Exception {
		List<String> result = new ArrayList<String>();

		CampaignModelExample example = new CampaignModelExample();
		example.createCriteria().andProjectIdEqualTo(projectId);
		List<CampaignModel> campaigns = campaignDao.selectByExample(example);

		if (campaigns != null && !campaigns.isEmpty()) {
			for (CampaignModel campaign : campaigns) {
				result.add(campaign.getId());
			}
		}

		return result;
	}

	/**
	 * 根据活动ID，查询出这个活动下的全部创意ID。
	 * 
	 * @param campaignId
	 *            活动ID
	 * @return
	 */
	public List<String> getCreativeIdListByCampaignId(String campaignId)
			throws Exception {
		List<String> result = new ArrayList<String>();

		CreativeModelExample example = new CreativeModelExample();
		example.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> creatives = creativeDao.selectByExample(example);

		if (creatives != null && !creatives.isEmpty()) {
			for (CreativeModel creative : creatives) {
				result.add(creative.getId());
			}
		}

		return result;
	}

	/**
	 * 根据项目ID，查询出这个项目下的全部创意ID。
	 * 
	 * @param projectId
	 *            项目ID
	 * @return
	 */
	public List<String> getCreativeIdListByProjectId(String projectId)
			throws Exception {
		List<String> result = new ArrayList<String>();
		List<String> campaignIds = findCampaignIdListByProjectId(projectId);

		for (String campaignId : campaignIds) {
			result.addAll(getCreativeIdListByCampaignId(campaignId));
		}

		return result;
	}

	/**
	 * 根据广告主ID查询其名下的全部创意ID。
	 * 
	 * @param advertiserId
	 *            广告主ID
	 * @return
	 */
	public List<String> getCreAtiveIdListByAdvertiserId(String advertiserId)
			throws Exception {
		List<String> result = new ArrayList<String>();

		ProjectModelExample example = new ProjectModelExample();
		example.createCriteria().andAdvertiserIdEqualTo(advertiserId);
		List<ProjectModel> projects = projectDao.selectByExample(example);

		if (projects != null && !projects.isEmpty()) {
			for (ProjectModel project : projects) {
				result.addAll(getCreativeIdListByProjectId(project.getId()));
			}
		}

		return result;
	}

}
