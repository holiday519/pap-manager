package com.pxene.pap.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.RedisHelper;
import com.pxene.pap.constant.CodeTableConstant;
import com.pxene.pap.domain.beans.BasicDataBean;
import com.pxene.pap.domain.models.AdvertiserModel;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.CreativeModelExample;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.domain.models.ProjectModelExample;
import com.pxene.pap.domain.models.RegionModel;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AdvertiserDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.ProjectDao;
import com.pxene.pap.repository.basic.RegionDao;

@Service
public class DataService extends BaseService {
	@Autowired
	private CreativeService creativeService;
	
	@Autowired
	private AdvertiserDao advertiserDao;
	
	@Autowired
	private CreativeDao creativeDao;

	@Autowired
	private CampaignDao campaignDao;

	@Autowired
	private RegionDao regionDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	private static Map<String, Set<String>> table = new HashMap<String, Set<String>>();
	
	private RedisHelper redisHelper;
	
	static {
		Set<String> network = new HashSet<String>();
		network.add("1");
		network.add("3");
		network.add("4");
		network.add("5");
		table.put("network", network);
		Set<String> operator = new HashSet<String>();
		operator.add("1");
		operator.add("2");
		operator.add("3");
		table.put("operator", operator);
		Set<String> os = new HashSet<String>();
		os.add("1");
		os.add("2");
		os.add("3");
		table.put("os", os);
	}
	
	
	public DataService()
    {
	    // 指定使用配置文件中的哪个具体的Redis配置
        redisHelper = RedisHelper.open("redis.primary.");
    }
	

	/**
	 * 查询小时数据
	 * @param startDate
	 * @param endDate
	 * @param advertiserId
	 * @param projectId
	 * @param campaignId
	 * @param creativeId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getDataForTime(Long startDate, Long endDate, String advertiserId, 
			String projectId, String campaignId, String creativeId) throws Exception {
		
		List<String> creativeIds = getCreativeIdListByParam(advertiserId, projectId, campaignId, creativeId);
		
		List<Map<String, Object>> list = getDatafromHourTableForTime(creativeIds, new Date(startDate), new Date(endDate));
		
		return list;
	}
	/**
	 * 查询地域数据
	 * @param startDate
	 * @param endDate
	 * @param advertiserId
	 * @param projectId
	 * @param campaignId
	 * @param creativeId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getDataForRegion(Long startDate, Long endDate, String advertiserId, 
			String projectId, String campaignId, String creativeId) throws Exception {
		
		List<String> creativeIds = getCreativeIdListByParam(advertiserId, projectId, campaignId, creativeId);
		
		List<Map<String, Object>> list = getDatafromDayTable(creativeIds, new Date(startDate), new Date(endDate), "region");
		
		return list;
	}
	/**
	 * 查询运营商数据
	 * @param startDate
	 * @param endDate
	 * @param advertiserId
	 * @param projectId
	 * @param campaignId
	 * @param creativeId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getDataForOperator(Long startDate, Long endDate, String advertiserId, 
			String projectId, String campaignId, String creativeId) throws Exception {
		
		List<String> creativeIds = getCreativeIdListByParam(advertiserId, projectId, campaignId, creativeId);
		
		List<Map<String, Object>> list = getDatafromDayTable(creativeIds, new Date(startDate), new Date(endDate), "operator");
		
		return list;
	}
	/**
	 * 查询网络数据
	 * @param startDate
	 * @param endDate
	 * @param advertiserId
	 * @param projectId
	 * @param campaignId
	 * @param creativeId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getDataForNetwork(Long startDate, Long endDate, String advertiserId, 
			String projectId, String campaignId, String creativeId) throws Exception {
		
		List<String> creativeIds = getCreativeIdListByParam(advertiserId, projectId, campaignId, creativeId);

		List<Map<String, Object>> list = getDatafromDayTable(creativeIds, new Date(startDate), new Date(endDate), "network");
		
		return list;
	}
	/**
	 * 查询系统数据
	 * @param startDate
	 * @param endDate
	 * @param advertiserId
	 * @param projectId
	 * @param campaignId
	 * @param creativeId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getDataForSystem(Long startDate, Long endDate, String advertiserId, 
			String projectId, String campaignId, String creativeId) throws Exception {
		
		List<String> creativeIds = getCreativeIdListByParam(advertiserId, projectId, campaignId, creativeId);
		
		List<Map<String, Object>> list = getDatafromDayTable(creativeIds, new Date(startDate), new Date(endDate), "os");
		
		return list;
	}
	
	/**
	 * 取天数据(其他数据查询用)
	 * @param creativeIds
	 * @param daysList
	 * @param bean
	 * @return 
	 * @throws Exception
	 */
	private List<Map<String, Object>> getDatafromDayTable(List<String> creativeIds, Date startDate, Date endDate, String type) throws Exception {
		String[] days = DateUtils.getDaysBetween(startDate, endDate);
		
		Set<String> codes = new HashSet<String>();//存放所有的code（例如：type为“region”时查询regioncode）
		
		Map<String, Long> mMap = new HashMap<String, Long>();//装展现
		Map<String, Long> cMap = new HashMap<String, Long>();//装点击
		Map<String, Long> jMap = new HashMap<String, Long>();//装二跳
		Map<String, Float> eMap = new HashMap<String, Float>();//装花费
		
		Set<String> set = table.get(type);
		
		for (String creativeId : creativeIds) {
			Map<String, String> map = redisHelper.hget("creativeDataDay_" + creativeId);//获取map集合
			Set<String> hkeys = redisHelper.hkeys("creativeDataDay_" + creativeId);//获取所有key
			if (hkeys != null && !hkeys.isEmpty()) {
				//拿出所有的key中属于当前type的定向的值（比如：type为“region时”拿出所有的regioncode）
				for (String hkey : hkeys) {
					if (hkey.indexOf(type) > -1 && daysContainKey(days, hkey.substring(0, 8))) {
						//循环遍历所有创意文件，找出符合日期、type的key，不重复的放到map中
						int befor = hkey.lastIndexOf("_") + 1;//此处想要的并不是“_”的索引，是他的后一位,即code的开始位
						int after = hkey.indexOf("@");
						String substring = hkey.substring(befor, after);//类别code（例如：type为“region”时，查询regioncode）
						if (set == null || set.isEmpty()) {
							codes.add(substring);
						} else if (set.contains(substring)) {
							codes.add(substring);
						} else {
							substring = "0";
							codes.add(substring);
						}
						
						String newValue = map.get(hkey);
						if (!StringUtils.isEmpty(newValue)) {
							if (hkey.indexOf("@m") > 0) {// 展现								
								//如果map中已经有值，加起来，不然直接存入map
								Long value = mMap.get(substring);
								if (value != null) {
									if (set == null || set.isEmpty()) {
										mMap.put(substring, value + Long.parseLong(newValue));
									} else if (set.contains(substring)) {
										mMap.put(substring, value + Long.parseLong(newValue));
									} else {
										mMap.put("0", value + Long.parseLong(newValue));
									}
								} else {
									if (set == null || set.isEmpty()) {
										mMap.put(substring, Long.parseLong(newValue));
									} else if (set.contains(substring)) {
										mMap.put(substring, Long.parseLong(newValue));
									} else {
										mMap.put("0", Long.parseLong(newValue));
									}
								}								
							} else if (hkey.indexOf("@c") > 0) {// 点击
								//如果map中已经有值，加起来，不然直接存入map
								Long value = cMap.get(substring);
								if (value != null) {
									if (set == null || set.isEmpty()) {
										cMap.put(substring, value + Long.parseLong(newValue));
									} else if (set.contains(substring)) {
										cMap.put(substring, value + Long.parseLong(newValue));
									} else {
										cMap.put("0", value + Long.parseLong(newValue));
									}
								} else {
									if (set == null || set.isEmpty()) {
										cMap.put(substring, Long.parseLong(newValue));
									} else if (set.contains(substring)) {
										cMap.put(substring, Long.parseLong(newValue));
									} else {
										cMap.put("0", Long.parseLong(newValue));
									}
								}
							} else if (hkey.indexOf("@j") > 0) {// 二跳
								//如果map中已经有值，加起来，不然直接存入map
								Long value = mMap.get(substring);
								if (value != null) {
									if (set == null || set.isEmpty()) {
										jMap.put(substring, value + Long.parseLong(newValue));
									} else if (set.contains(substring)) {
										jMap.put(substring, value + Long.parseLong(newValue));
									} else {
										jMap.put("0", value + Long.parseLong(newValue));
									}
								} else {
									if (set == null || set.isEmpty()) {
										jMap.put(substring, Long.parseLong(newValue));
									} else if (set.contains(substring)) {
										jMap.put(substring, Long.parseLong(newValue));
									} else {
										jMap.put("0", Long.parseLong(newValue));
									}
								}
							} else if (hkey.indexOf("@e") > 0) {// 花费
								//如果map中已经有值，加起来，不然直接存入map
								Long value = mMap.get(substring);
								if (value != null) {
									if (set == null || set.isEmpty()) {
										eMap.put(substring, value + Float.parseFloat(newValue));
									} else if (set.contains(substring)) {
										eMap.put(substring, value + Float.parseFloat(newValue));
									} else {
										eMap.put("0", value + Float.parseFloat(newValue));
									}
								} else {
									if (set == null || set.isEmpty()) {
										eMap.put(substring, Float.parseFloat(newValue));
									} else if (set.contains(substring)) {
										eMap.put(substring, Float.parseFloat(newValue));
									} else {
										eMap.put("0", Float.parseFloat(newValue));
									}
								}
							}
						}
					}
				}				
			}
		}
		//整理结果集
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		if (!codes.isEmpty()) {
			Map<String, Object> map = null;
			BasicDataBean bean = null;
			for (String code : codes) {
				bean = new BasicDataBean();
				map = new HashMap<String, Object>();
				formatBeanParams(bean);
				if (mMap.get(code) != null) {
					bean.setImpressionAmount(mMap.get(code));
				}
				if (cMap.get(code) != null) {
					bean.setClickAmount(cMap.get(code));
				}
				if (jMap.get(code) != null) {
					bean.setJumpAmount(jMap.get(code));
				}
				if (eMap.get(code) != null) {
					bean.setTotalCost(eMap.get(code));
				}
				formatBeanRate(bean);
				
				String name = getNameByType(type, code);
				map.put("name", name);
				map.put("id", code);
				map.put("impressionAmount", bean.getImpressionAmount());
				map.put("clickAmount", bean.getClickAmount());
				map.put("jumpAmount", bean.getJumpAmount());
				map.put("clickRate", bean.getClickRate());
				map.put("totalCost", bean.getTotalCost());
				map.put("impressionCost", bean.getImpressionCost());
				map.put("clickCost", bean.getClickCost());
				map.put("jumpCost", bean.getJumpCost());
				result.add(map);
			}
		}
		return result;
	}
	
	/**
	 * 检查日期是否在制定日期数组中
	 * @param days
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private boolean daysContainKey(String[] days, String key) throws Exception {
		List<String> list = Arrays.asList(days);
		if (list.contains(key)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据类型和code获取名称
	 * @param type
	 * @param code
	 * @return
	 */
	private String getNameByType(String type, String code) {
		if ("region".equals(type)) {
			RegionModel model = regionDao.selectByPrimaryKey(code.substring(4, code.length()));
			if (model != null) {
				return model.getName();
			} else {
				return CodeTableConstant.NAME_UNKNOW;
			}
		} else if ("operator".equals(type)) {//运营商
			if (CodeTableConstant.OPERATOR_CODE_YIDONG.equals(code)) {
				return CodeTableConstant.OPERATOR_NAME_YIDONG;
			} else if (CodeTableConstant.OPERATOR_CODE_LIANTONG.equals(code)) {
				return CodeTableConstant.OPERATOR_NAME_LIANTONG;
			} else if (CodeTableConstant.OPERATOR_CODE_DIANXIN.equals(code)) {
				return CodeTableConstant.OPERATOR_NAME_DIANXIN;
			} else {
				return CodeTableConstant.NAME_UNKNOW;
			}
		} else if ("network".equals(type)) {
			if (CodeTableConstant.NETWORK_CODE_2G.equals(code)) {
				return CodeTableConstant.NETWORK_NAME_2G;
			} else if (CodeTableConstant.NETWORK_CODE_3G.equals(code)) {
				return CodeTableConstant.NETWORK_NAME_3G;
			} else if (CodeTableConstant.NETWORK_CODE_4G.equals(code)) {
				return CodeTableConstant.NETWORK_NAME_4G;
			} else if (CodeTableConstant.NETWORK_CODE_WIFI.equals(code)) {
				return CodeTableConstant.NETWORK_NAME_WIFI;
			} else {
				return CodeTableConstant.NAME_UNKNOW;
			}
		} else if ("os".equals(type)) {
			if (CodeTableConstant.SYSTEM_CODE_IOS.equals(code)) {
				return CodeTableConstant.SYSTEM_NAME_IOS;
			} else if (CodeTableConstant.SYSTEM_CODE_ANDROID.equals(code)) {
				return CodeTableConstant.SYSTEM_NAME_ANDROID;
			} else if (CodeTableConstant.SYSTEM_CODE_WINDOWS.equals(code)) {
				return CodeTableConstant.SYSTEM_NAME_WINDOWS;
			} else {
				return CodeTableConstant.NAME_UNKNOW;
			}
		}
		
		return null;
	}
	
	/**
	 * 取小时数据(查询时间数据用)
	 * @param creativeIds
	 * @param startDate
	 * @param endDate
	 * @param bean
	 * @throws Exception
	 */
	private List<Map<String, Object>> getDatafromHourTableForTime(List<String> creativeIds, Date startDate, Date endDate) throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String timeHours[] = new String[]{"00","01","02","03","04","05","06","07","08","09","10",
				"11","12","13","14","15","16","17","18","19","20","21","22","23"};
		String[] days = DateUtils.getDaysBetween(startDate, endDate);
		BasicDataBean bean = null;
		Map<String, Object> resultMap = null;
		for (String hour : timeHours) {
			bean = new BasicDataBean();
			resultMap = new HashMap<String, Object>();
			formatBeanParams(bean);//将属性值变成0
			for (String creativeId : creativeIds) {
				Map<String, String> map = redisHelper.hget("creativeDataHour_" + creativeId);//获取map集合
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
					    float totalCost = bean.getTotalCost() + (Float.parseFloat(exp) / 100); //将Redis中取出的价格（分）转换成价格（元）
						bean.setTotalCost(totalCost);
						//bean.setTotalCost(bean.getTotalCost() + Float.parseFloat(exp));
					}
				}
			}
			formatBeanRate(bean);
			
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
	public void formatBeanParams(BasicDataBean bean) throws Exception {
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
	 * 注：此方法传入bean中不能有NULL值，可以是0；
	 */
	public void formatBeanRate(BasicDataBean bean) throws Exception {
		DecimalFormat format = new DecimalFormat("0.0000");
		if (bean.getClickAmount() > 0) {
	        double percent = (double)bean.getTotalCost() / bean.getClickAmount();
			Float result = Float.parseFloat(format.format(percent));
	        bean.setClickCost(result);
		}
		if (bean.getJumpAmount() > 0) {
			double percent = (double)bean.getTotalCost() / bean.getJumpAmount();
			Float result = Float.parseFloat(format.format(percent));
			bean.setJumpCost(result);
		}
		if (bean.getImpressionAmount() > 0) {
			double percent = (double)bean.getClickAmount() / bean.getImpressionAmount();
	        Float result = Float.parseFloat(format.format(percent));
	        bean.setClickRate(result);
	        
	        // bean.getTotalCost()表示每次展现的价格（分），乘以1000，表示千次展现转化每次展现（分），再除以100，表示每次展现（元）
	        percent = (double) (bean.getTotalCost() * 1000 / bean.getImpressionAmount() / 100);
	        result = Float.parseFloat(format.format(percent));
	        bean.setImpressionCost(result);
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
						creativeIds = getCreativeIdListByAdvertiserId(advertiserId);
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
	public List<String> getCreativeIdListByAdvertiserId(String advertiserId)
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
	
	public List<Map<String, Object>> getAdvertiserData(Long startDate, Long endDate, String id) throws Exception {
		// 获取所有天
		String[] days = DateUtils.getDaysBetween(new Date(startDate), new Date(endDate));
		List<String> creativeIds = getCreativeIdListByAdvertiserId(id);
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		
		//查询名称
		String name = null;
		AdvertiserModel model = advertiserDao.selectByPrimaryKey(id);
		if (model == null) {
			throw new ResourceNotFoundException();
		}
		name = model.getName();
		
		for (String day : days) {
			Date time = DateUtils.strToDate(day, "yyyyMMdd");
			Date smallHourOfDay = DateUtils.getSmallHourOfDay(time);
			Date bigHourOfDay = DateUtils.getBigHourOfDay(time);
			BasicDataBean bean = creativeService.getCreativeDatas(creativeIds, smallHourOfDay.getTime(), bigHourOfDay.getTime());
			
			Map<String, Object> result = new HashMap<String, Object>();
			if (bean.getImpressionAmount() == 0 && bean.getClickAmount() == 0
					&& bean.getJumpCost() == 0 && bean.getTotalCost() == 0) {
				continue;
			} else {
				result.put("impressionAmount", bean.getImpressionAmount());
				result.put("clickAmount", bean.getClickAmount());
				result.put("jumpAmount", bean.getJumpAmount());
				result.put("clickRate", bean.getClickRate());
				result.put("totalCost", bean.getTotalCost());
				result.put("impressionCost", bean.getImpressionCost());
				result.put("clickCost", bean.getClickCost());
				result.put("jumpCost", bean.getJumpCost());
				
				result.put("date", day);
				result.put("name", name);
				
				results.add(result);
			}
		}
		
		return results;
	}
	
	public List<Map<String, Object>> getProjectData(Long startDate, Long endDate, String id) throws Exception {
		// 获取所有天
		String[] days = DateUtils.getDaysBetween(new Date(startDate), new Date(endDate));
		List<String> creativeIds = getCreativeIdListByProjectId(id);
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		
		//查询名称
		String name = null;
		ProjectModel model = projectDao.selectByPrimaryKey(id);
		if (model == null) {
			throw new ResourceNotFoundException();
		}
		name = model.getName();
		
		for (String day : days) {
			Date time = DateUtils.strToDate(day, "yyyyMMdd");
			Date smallHourOfDay = DateUtils.getSmallHourOfDay(time);
			Date bigHourOfDay = DateUtils.getBigHourOfDay(time);
			BasicDataBean bean = creativeService.getCreativeDatas(creativeIds, smallHourOfDay.getTime(), bigHourOfDay.getTime());;
			Map<String, Object> result = new HashMap<String, Object>();
			
			if (bean.getImpressionAmount() == 0 && bean.getClickAmount() == 0
					&& bean.getJumpCost() == 0 && bean.getTotalCost() == 0) {
				continue;
			} else {
				result.put("impressionAmount", bean.getImpressionAmount());
				result.put("clickAmount", bean.getClickAmount());
				result.put("jumpAmount", bean.getJumpAmount());
				result.put("clickRate", bean.getClickRate());
				result.put("totalCost", bean.getTotalCost());
				result.put("impressionCost", bean.getImpressionCost());
				result.put("clickCost", bean.getClickCost());
				result.put("jumpCost", bean.getJumpCost());
				
				result.put("date", day);
				result.put("name", name);
				results.add(result);
			}
			
		}
		
		return results;
	}
	
	public List<Map<String, Object>> getCampaignData(Long startDate, Long endDate, String id) throws Exception {
		// 获取所有天
		String[] days = DateUtils.getDaysBetween(new Date(startDate), new Date(endDate));
		List<String> creativeIds = getCreativeIdListByCampaignId(id);
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		//查询名称
		String name = null;
		CampaignModel model = campaignDao.selectByPrimaryKey(id);
		if (model == null) {
			throw new ResourceNotFoundException();
		}
		name = model.getName();
		
		for (String day : days) {
			Date time = DateUtils.strToDate(day, "yyyyMMdd");
			Date smallHourOfDay = DateUtils.getSmallHourOfDay(time);
			Date bigHourOfDay = DateUtils.getBigHourOfDay(time);
			BasicDataBean bean = creativeService.getCreativeDatas(creativeIds, smallHourOfDay.getTime(), bigHourOfDay.getTime());
			Map<String, Object> result = new HashMap<String, Object>();
			
			if (bean.getImpressionAmount() == 0 && bean.getClickAmount() == 0
					&& bean.getJumpCost() == 0 && bean.getTotalCost() == 0) {
				continue;
			} else {
				result.put("impressionAmount", bean.getImpressionAmount());
				result.put("clickAmount", bean.getClickAmount());
				result.put("jumpAmount", bean.getJumpAmount());
				result.put("clickRate", bean.getClickRate());
				result.put("totalCost", bean.getTotalCost());
				result.put("impressionCost", bean.getImpressionCost());
				result.put("clickCost", bean.getClickCost());
				result.put("jumpCost", bean.getJumpCost());
				
				result.put("date", day);
				result.put("name", name);
				
				results.add(result);
			}
		}
		
		return results;
	}
	
	public List<Map<String, Object>> getCreativeData(Long startDate, Long endDate, String id) throws Exception {
		// 获取所有天
		String[] days = DateUtils.getDaysBetween(new Date(startDate), new Date(endDate));
		List<String> creativeIds = new ArrayList<String>();
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		creativeIds.add(id);
		
		//查询名称
		String name = null;
		CreativeModel model = creativeDao.selectByPrimaryKey(id);
		if (model == null) {
			throw new ResourceNotFoundException();
		}
		name = model.getName();
		
		for (String day : days) {
			Date time = DateUtils.strToDate(day, "yyyyMMdd");
			Date smallHourOfDay = DateUtils.getSmallHourOfDay(time);
			Date bigHourOfDay = DateUtils.getBigHourOfDay(time);
			BasicDataBean bean = creativeService.getCreativeDatas(creativeIds, smallHourOfDay.getTime(), bigHourOfDay.getTime());
			Map<String, Object> result = new HashMap<String, Object>();
			
			if (bean.getImpressionAmount() == 0 && bean.getClickAmount() == 0
					&& bean.getJumpCost() == 0 && bean.getTotalCost() == 0) {
				continue;
			} else {
				result.put("impressionAmount", bean.getImpressionAmount());
				result.put("clickAmount", bean.getClickAmount());
				result.put("jumpAmount", bean.getJumpAmount());
				result.put("clickRate", bean.getClickRate());
				result.put("totalCost", bean.getTotalCost());
				result.put("impressionCost", bean.getImpressionCost());
				result.put("clickCost", bean.getClickCost());
				result.put("jumpCost", bean.getJumpCost());
				
				result.put("date", day);
				result.put("name", name);
				
				results.add(result);
			}
		}
		
		return results;
	}
	
	/**
	 * 查询属于指定广告主的全部项目ID
	 * @param advertiserId 广告主ID
	 * @return
	 */
    public List<String> getProjectIdListByAdvertiserId(String advertiserId)
    {
        List<String> result = new ArrayList<String>();
        
        ProjectModelExample example = new ProjectModelExample();
        example.createCriteria().andAdvertiserIdEqualTo(advertiserId);
        
        List<ProjectModel> projects = projectDao.selectByExample(example);
        
        if (projects != null && !projects.isEmpty())
        {
            for (ProjectModel project : projects)
            {
                result.add(project.getId());
            }
        }

        return result;
    }
    
    /**
     * 查询属于指定广告主的全部活动ID
     * @param advertiserId  广告主ID
     * @return
     */
    public List<String> getCampaignIdListByAdvertiserId(String advertiserId)
    {
        List<String> result = new ArrayList<String>();
        
        List<String> projectIdList = getProjectIdListByAdvertiserId(advertiserId);
        
        for (String projectId : projectIdList)
        {
            CampaignModelExample example = new CampaignModelExample();
            example.createCriteria().andProjectIdEqualTo(projectId);
            
            List<CampaignModel> campaigns = campaignDao.selectByExample(example);
            
            for (CampaignModel campaign : campaigns)
            {
                result.add(campaign.getId());
            }
        }
        
        return result;
    }
	
}
