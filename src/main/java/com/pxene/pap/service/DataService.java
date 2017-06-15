package com.pxene.pap.service;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.ExcelOperateUtil;
import com.pxene.pap.common.ExcelUtil;
import com.pxene.pap.common.RedisHelper;
import com.pxene.pap.common.UUIDGenerator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pxene.pap.constant.CodeTableConstant;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.RedisKeyConstant;
import com.pxene.pap.domain.beans.BasicDataBean;
import com.pxene.pap.domain.beans.CampaignBean;
import com.pxene.pap.domain.beans.CreativeBean;
import com.pxene.pap.domain.beans.EffectFileBean;
import com.pxene.pap.domain.beans.ProjectBean;
import com.pxene.pap.domain.models.AdvertiserModel;
import com.pxene.pap.domain.models.AdxCostModel;
import com.pxene.pap.domain.models.AdxCostModelExample;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignModelExample;
import com.pxene.pap.domain.models.CreativeBasicModel;
import com.pxene.pap.domain.models.CreativeBasicModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.CreativeModelExample;
import com.pxene.pap.domain.models.EffectFileModel;
import com.pxene.pap.domain.models.EffectFileModelExample;
import com.pxene.pap.domain.models.EffectModel;
import com.pxene.pap.domain.models.EffectModelExample;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.domain.models.ProjectModelExample;
import com.pxene.pap.domain.models.RegionModel;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AdvertiserDao;
import com.pxene.pap.repository.basic.AdxCostDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.EffectDao;
import com.pxene.pap.repository.basic.EffectFileDao;
import com.pxene.pap.repository.basic.ProjectDao;
import com.pxene.pap.repository.basic.RegionDao;
import com.pxene.pap.repository.basic.view.CreativeBasicDao;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

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
	
	@Autowired
	private EffectDao effectDao;
	
	@Autowired
	private EffectFileDao effectFileDao;
	
	@Autowired
	private CreativeBasicDao creativeBasicDao;
	
	@Autowired
	private AdxCostDao adxCostDao;
	
	private static Map<String, Set<String>> table = new HashMap<String, Set<String>>();
	
	@Autowired
	private RedisHelper redisHelper3;
	
	private static class CREATIVE_DATA_SUFFIX {
		public static String WIN = "@w";
		public static String IMPRESSION = "@m";
		public static String CLICK = "@c";
		public static String EXPENSE = "@e";
		public static String JUMP = "@j";
	}
	private static class CREATIVE_DATA_TYPE {
		public static String ADX = "_adx_";
		public static String REGION = "_region_";
		public static String OS = "_os_";
		public static String NETWORK = "_network_";
		public static String OPERATOR = "_operator_";
	}
	
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
	
	
	@PostConstruct
    public void selectRedis()
    {
        redisHelper3.select("redis.tertiary.");
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
	@Transactional
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
	@Transactional
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
	@Transactional
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
	@Transactional
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
	@Transactional
	public List<Map<String, Object>> getDataForSystem(Long startDate, Long endDate, String advertiserId, 
			String projectId, String campaignId, String creativeId) throws Exception {
		
		List<String> creativeIds = getCreativeIdListByParam(advertiserId, projectId, campaignId, creativeId);
		
		List<Map<String, Object>> list = getDatafromDayTable(creativeIds, new Date(startDate), new Date(endDate), "os");
		
		return list;
	}
	
	/**
	 * 取天数据(其他数据查询用)
	 * @param creativeIds
	 * @param startDate
	 * @param endDate
	 * @return 
	 * @throws Exception
	 */
	private List<Map<String, Object>> getDatafromDayTable(List<String> creativeIds, Date startDate, Date endDate, String type) throws Exception {
		String[] days = DateUtils.getDaysBetween(startDate, endDate);
		
		Set<String> codes = new HashSet<String>();//存放所有的code（例如：type为“region”时查询regioncode）
		
		Map<String, Long> mMap = new HashMap<String, Long>();//装展现
		Map<String, Long> cMap = new HashMap<String, Long>();//装点击
		Map<String, Long> jMap = new HashMap<String, Long>();//装二跳
		Map<String, Double> eMap = new HashMap<String, Double>();//装花费
		
		Set<String> set = table.get(type);
		
		for (String creativeId : creativeIds) {
			Map<String, String> map = redisHelper3.hget("creativeDataDay_" + creativeId);//获取map集合
			Set<String> hkeys = redisHelper3.hkeys("creativeDataDay_" + creativeId);//获取所有key
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
										eMap.put(substring, value + Double.parseDouble(newValue));
									} else if (set.contains(substring)) {
										eMap.put(substring, value + Double.parseDouble(newValue));
									} else {
										eMap.put("0", value + Double.parseDouble(newValue));
									}
								} else {
									if (set == null || set.isEmpty()) {
										eMap.put(substring, Double.parseDouble(newValue));
									} else if (set.contains(substring)) {
										eMap.put(substring, Double.parseDouble(newValue));
									} else {
										eMap.put("0", Double.parseDouble(newValue));
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
				Map<String, String> map = redisHelper3.hget("creativeDataHour_" + creativeId);//获取map集合
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
						double totalCost = bean.getTotalCost() + (Double.parseDouble(exp) / 100); //将Redis中取出的价格（分）转换成价格（元）
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
		bean.setTotalCost(0D);
		
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

	/**
	 * 列出广告主数据
	 * @param advertiserId
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
     * @throws Exception
     */
	@Transactional
	public List<Map<String, Object>> listAdvertisers(String advertiserId, String type, Long startDate, Long endDate) throws Exception {
		if(!type.equals(CodeTableConstant.SUMMARYWAY_TOTAL) && !type.equals(CodeTableConstant.SUMMARYWAY_DAY)){
			throw new IllegalArgumentException(PhrasesConstant.PARAM_ERROR);
		}

		List<Map<String, Object>> datas;
		if(advertiserId!=null) {
			datas = getAdvertiserDataByAdvertiserId(startDate, endDate, advertiserId, type);
		}else{
			//查询全部客户
			datas = getAllAdvertiserData(startDate, endDate, type);
		}
		return datas;
	}
	/**
	 * 列出广告主数据--根据客户查
	 * @param startDate
	 * @param endDate
	 * @param advertiserId
	 * @param type
	 * @return
     * @throws Exception
     */
	public List<Map<String, Object>> getAdvertiserDataByAdvertiserId(Long startDate, Long endDate, String advertiserId,String type) throws Exception {
		// 获取所有天
		String[] days = DateUtils.getDaysBetween(new Date(startDate), new Date(endDate));
		List<String> creativeIds = getCreativeIdListByAdvertiserId(advertiserId);
		if(creativeIds == null){
			return null;
		}

		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		
		//查询客户名称
		AdvertiserModel model = advertiserDao.selectByPrimaryKey(advertiserId);
		if (model == null) {
			throw new ResourceNotFoundException();
		}

		String name = model.getName();
		Map<String,Object> otherValue = new HashMap<>();
		otherValue.put("name",name);

		if(type.equals(CodeTableConstant.SUMMARYWAY_TOTAL)) {//汇总
			List<Map<String,Object>> result = getFlowData_total(startDate,endDate,creativeIds,otherValue);
			results.addAll(result);
		}else if(type.equals(CodeTableConstant.SUMMARYWAY_DAY)){
//			for (String day : days) {
//				Date time = DateUtils.strToDate(day, "yyyyMMdd");
//				Date smallHourOfDay = DateUtils.getSmallHourOfDay(time);
//				Date bigHourOfDay = DateUtils.getBigHourOfDay(time);
//				BasicDataBean bean = creativeService.getCreativeDatas(creativeIds, smallHourOfDay.getTime(), bigHourOfDay.getTime());
//
//				Map<String, Object> result = new HashMap<String, Object>();
//				if (bean.getImpressionAmount() == 0 && bean.getClickAmount() == 0
//						&& bean.getJumpCost() == 0 && bean.getTotalCost() == 0) {
//					continue;
//				} else {
//					result.put("impressionAmount", bean.getImpressionAmount());
//					result.put("clickAmount", bean.getClickAmount());
//					result.put("jumpAmount", bean.getJumpAmount());
//					result.put("clickRate", bean.getClickRate());
//					result.put("totalCost", bean.getTotalCost());
//					result.put("impressionCost", bean.getImpressionCost());
//					result.put("clickCost", bean.getClickCost());
//					result.put("jumpCost", bean.getJumpCost());
//
//					result.put("date", day);
//					result.put("name", name);
//
//					results.add(result);
//				}
//			}
			List<Map<String,Object>>result = getFlowData_Day(days, creativeIds, otherValue);
			results.addAll(result);
		}

		
		return results;
	}



	/**
	 * 获取流量数据--汇总方式：合计
	 * @param startDate 开始日期
	 * @param endDate	结束日期
	 * @param creativeIds 创意ID集合
	 * @param otherValue  其他值
	 * @return
     * @throws Exception
     */
	public List<Map<String, Object>> getFlowData_total(Long startDate,Long endDate,List<String> creativeIds,Map<String,Object> otherValue) throws Exception{

		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date startTime = sdf.parse(sdf.format(startDate));
		Date endTime = sdf.parse(sdf.format(endDate));
		Date smallHourOfDay = DateUtils.getSmallHourOfDay(startTime);
		Date bigHourOfDay = DateUtils.getBigHourOfDay(endTime);
		BasicDataBean bean = creativeService.getCreativeDatas(creativeIds, smallHourOfDay.getTime(), bigHourOfDay.getTime());
		Map<String, Object> result = new HashMap<String, Object>();
		if (bean!=null) {
			if (bean.getImpressionAmount() == 0 && bean.getClickAmount() == 0
					&& bean.getJumpCost() == 0 && bean.getTotalCost() == 0) {
				return results;
			} else {
				result.put("impressionAmount", bean.getImpressionAmount());
				result.put("clickAmount", bean.getClickAmount());
				result.put("jumpAmount", bean.getJumpAmount());
				result.put("clickRate", bean.getClickRate());
				result.put("totalCost", bean.getTotalCost());
				result.put("impressionCost", bean.getImpressionCost());
				result.put("clickCost", bean.getClickCost());
				result.put("jumpCost", bean.getJumpCost());
				//修正成本
				result.put("adxCost",bean.getAdxCost());
				result.putAll(otherValue);

				results.add(result);
			}
		}

		return results;
	}

	/**
	 * 按天获取客户数据（点击展现等）
	 * @param days 日期
	 * @param creativeIds 创意id集合
	 * @param otherValue 其他值
	 * @return
     * @throws Exception
     */
	public List<Map<String, Object>> getFlowData_Day(String[] days,List<String> creativeIds,Map<String,Object> otherValue) throws Exception{
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

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
				//修正成本
				result.put("adxCost",bean.getAdxCost());

				result.put("date", day);
				result.putAll(otherValue);

				results.add(result);
			}
		}
		return results;
	}

	/**
	 * 列出广告主数据--获取所有客户的数据
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param type 汇总方式
	 * @return
     * @throws Exception
     */
	public List<Map<String, Object>> getAllAdvertiserData(Long startDate, Long endDate,String type) throws Exception {
		//获取所有客户信息
		List<AdvertiserModel> advertiserList =advertiserDao.selectByExample(null);
		if(advertiserList == null){
			return null;
		}
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		Map<AdvertiserModel,List<String>> advertiser_creative = new HashMap<>();
		//循环获取每个用户的活动ID
		for(AdvertiserModel advertiser: advertiserList){
			List<String> creativeIds = getCreativeIdListByAdvertiserId(advertiser.getId());
			if(creativeIds != null && creativeIds.size()>0) {
				advertiser_creative.put(advertiser, creativeIds);
			}
		}

		if(type.equals(CodeTableConstant.SUMMARYWAY_TOTAL)) {//汇总方式
			//按代理人汇总
			for(AdvertiserModel ad: advertiser_creative.keySet()) {
				Map<String,Object> otherValue = new HashMap<>();
				otherValue.put("name",ad.getName());

				List<String> creativeIds = advertiser_creative.get(ad);
				List<Map<String,Object>> result = getFlowData_total(startDate,endDate,creativeIds,otherValue);
				if(result != null) {
					results.addAll(result);
				}
			}
		}else if(type.equals(CodeTableConstant.SUMMARYWAY_DAY)){
			// 获取所有天
			String[] days = DateUtils.getDaysBetween(new Date(startDate), new Date(endDate));
			//按代理人,按天
			for(AdvertiserModel ad: advertiser_creative.keySet()) {
				Map<String,Object> otherValue = new HashMap<>();
				otherValue.put("name",ad.getName());

				List<String> creativeIds = advertiser_creative.get(ad);
				List<Map<String,Object>>result = getFlowData_Day(days, creativeIds, otherValue);
				if(result != null) {
					results.addAll(result);
				}
			}
		}

		return results;
	}

	/**
	 * 列出项目数据
	 * @param advertiserId
	 * @param projectId
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
     * @throws Exception
     */
	@Transactional
	public List<Map<String, Object>> listProjects(String advertiserId, String projectId, String type,Long startDate, Long endDate) throws Exception{
		if(!type.equals(CodeTableConstant.SUMMARYWAY_TOTAL) && !type.equals(CodeTableConstant.SUMMARYWAY_DAY)){
			throw new IllegalArgumentException(PhrasesConstant.PARAM_ERROR);
		}

		List<Map<String, Object>> datas;
		if(projectId!=null){
			//如果projectId不为null，根据projectId查询
			datas =getProjectDataByProjectId(startDate, endDate, type, projectId);
		}else if(projectId == null && advertiserId!=null){
			//如果projectId为null,根据客户查询
			datas = getProjectDataByAdvertiserId(startDate, endDate, type, advertiserId);
		}else {
			//查询全部客户的项目数据
			datas = getAllProjectData(startDate, endDate, type);
		}
		return datas;
	}
	/**
	 * 列出项目数据--通过项目id获取项目数据
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @param projectId
	 * @return
     * @throws Exception
     */
	public List<Map<String, Object>> getProjectDataByProjectId(Long startDate, Long endDate,String type, String projectId) throws Exception {
		// 获取所有天
		String[] days = DateUtils.getDaysBetween(new Date(startDate), new Date(endDate));
		List<String> creativeIds = getCreativeIdListByProjectId(projectId);
		List<Map<String, Object>> results = null;
		if(creativeIds == null){
			return null;
		}
		//查询项目名称
		ProjectModel model = projectDao.selectByPrimaryKey(projectId);
		if (model == null) {
			throw new ResourceNotFoundException();
		}
		String name = model.getName();
		Map<String,Object> otherValue = new HashMap<>();
		otherValue.put("name",name);

		if(type.equals(CodeTableConstant.SUMMARYWAY_TOTAL)) {//汇总
			results = getFlowData_total(startDate,endDate,creativeIds,otherValue);

		}else if(type.equals(CodeTableConstant.SUMMARYWAY_DAY)){
			results = getFlowData_Day(days,creativeIds,otherValue);
		}

		return results;
	}

	/**
	 * 列出项目数据--根据客户汇总
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @param advertiserId
	 * @return
     * @throws Exception
     */
	public List<Map<String, Object>> getProjectDataByAdvertiserId(Long startDate, Long endDate,String type, String advertiserId) throws Exception {
		//根据客户id获取下面的projectid
		ProjectModelExample example = new ProjectModelExample();
		example.createCriteria().andAdvertiserIdEqualTo(advertiserId);
		List<ProjectModel> projectList = projectDao.selectByExample(example);
		if(projectList == null){
			return  null;
		}
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		//循环项目id,按项目id汇总
		for(ProjectModel projectModel: projectList){
			List<Map<String, Object>> result = getProjectDataByProjectId(startDate,endDate,type,projectModel.getId());
			if(result !=null) {
				results.addAll(result);
			}
		}

		return results;
	}


	/**
	 * 列出项目数据--获取所有用户的所有项目数据
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @return
     * @throws Exception
     */
	public List<Map<String, Object>> getAllProjectData(Long startDate, Long endDate,String type) throws Exception {
		//获取所有客户信息
		List<AdvertiserModel> advertiserList =advertiserDao.selectByExample(null);
		if(advertiserList == null){
			return null;
		}

		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		//循环客户信息，按项目汇总数据
		for(AdvertiserModel advertiserModel: advertiserList){
			List<Map<String, Object>> result = getProjectDataByAdvertiserId(startDate,endDate,type,advertiserModel.getId());
			if(result != null) {
				results.addAll(result);
			}
		}

		return results;
	}

	/**
	 * 列出活动数据
	 * @param advertiserId
	 * @param projectId
	 * @param campaignId
	 * @param type
	 * @param startDate
	 * @param endDate
     * @return
     * @throws Exception
     */
	@Transactional
	public List<Map<String, Object>> listCampaigns(String advertiserId, String projectId, String campaignId,String type,Long startDate, Long endDate) throws Exception{
		if(!type.equals(CodeTableConstant.SUMMARYWAY_TOTAL) && !type.equals(CodeTableConstant.SUMMARYWAY_DAY)){
			throw new IllegalArgumentException(PhrasesConstant.PARAM_ERROR);
		}

		List<Map<String, Object>> datas;
		if(campaignId!=null) {
			//查询指定活动的数据
			datas = getCampaignDataByCampaignId(startDate, endDate, type, campaignId);
		}else if(campaignId == null && projectId != null){
			//查询指定项目的数据
			datas = getCampaignDataByProjectId(startDate, endDate, type, projectId);
		}else if(campaignId == null && projectId == null && advertiserId != null){
			//查询客户的数据
			datas = getCampaignDataByAdvertiserId(startDate, endDate, type,advertiserId);
		}else{
			//查询所有客户的数据
			datas = getAllCampaignData(startDate, endDate, type);
		}
		return datas;
	}

	/**
	 * 列出活动数据--根据活动id
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @param campaignId
	 * @return
     * @throws Exception
     */
	public List<Map<String, Object>> getCampaignDataByCampaignId(Long startDate, Long endDate, String type, String campaignId) throws Exception {
		// 获取所有天
		String[] days = DateUtils.getDaysBetween(new Date(startDate), new Date(endDate));
		//获取活动下的创意ID
		List<String> creativeIds = getCreativeIdListByCampaignId(campaignId);
		if(creativeIds == null){
			return null;
		}
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		//查询活动名称
		CampaignModel model = campaignDao.selectByPrimaryKey(campaignId);
		if (model == null) {
			throw new ResourceNotFoundException();
		}
		String name = model.getName();
		Map<String,Object> otherValue = new HashMap<>();
		otherValue.put("name",name);
		if(type.equals(CodeTableConstant.SUMMARYWAY_TOTAL)) {//汇总
			results = getFlowData_total(startDate,endDate,creativeIds,otherValue);

		}else if(type.equals(CodeTableConstant.SUMMARYWAY_DAY)){
			results = getFlowData_Day(days,creativeIds,otherValue);
		}

		return results;
	}

	/**
	 * 列出活动数据--根据项目id查询
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @param projectId
	 * @return
     * @throws Exception
     */
	public List<Map<String, Object>> getCampaignDataByProjectId(Long startDate, Long endDate,String type, String projectId) throws Exception {
		List<String> campaignIds = findCampaignIdListByProjectId(projectId);
		if(campaignIds == null) {
			return null;
		}
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		//循环活动id,根据活动汇总数据
		for(String campaignId : campaignIds){
			List<Map<String, Object>> result = getCampaignDataByCampaignId(startDate,endDate,type,campaignId);
			if(result != null){
				results.addAll(result);
			}

		}

		return results;
	}

	/**
	 * 列出活动数据--根据客户id查询
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @param advertiserId
	 * @return
     * @throws Exception
     */
	public List<Map<String, Object>> getCampaignDataByAdvertiserId(Long startDate, Long endDate,String type, String advertiserId) throws Exception {
		//根据客户id获取下面的projectid
		ProjectModelExample example = new ProjectModelExample();
		example.createCriteria().andAdvertiserIdEqualTo(advertiserId);
		List<ProjectModel> projectList = projectDao.selectByExample(example);
		if(projectList == null){
			return null;
		}
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		//循环项目信息，根据项目查
		for(ProjectModel projectModel: projectList){
			List<Map<String, Object>> result = getCampaignDataByProjectId(startDate,endDate,type,projectModel.getId());
			if(result != null) {
				results.addAll(result);
			}
		}

		return results;
	}

	/**
	 * 列出活动数据--查询所有用户的所有活动数据
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @return
     * @throws Exception
     */
	public List<Map<String, Object>> getAllCampaignData (Long startDate, Long endDate,String type) throws Exception {
		//获取所有客户信息
		List<AdvertiserModel> advertiserList =advertiserDao.selectByExample(null);
		if(advertiserList == null){
			return null;
		}
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		//循环客户信息，按活动汇总数据
		for(AdvertiserModel advertiserModel: advertiserList){
			List<Map<String, Object>> result = getCampaignDataByAdvertiserId(startDate,endDate,type,advertiserModel.getId());
			if(result !=null) {
				results.addAll(result);
			}
		}

		return results;
	}

	/**
	 * 列出创意数据
	 * @param advertiserId
	 * @param projectId
	 * @param campaignId
	 * @param type
	 * @param startDate
	 * @param endDate
     * @return
     * @throws Exception
     */
	@Transactional
	public List<Map<String, Object>> listCreatives(String advertiserId, String projectId, String campaignId, String type,Long startDate, Long endDate) throws Exception{
		if(!type.equals(CodeTableConstant.SUMMARYWAY_TOTAL) && !type.equals(CodeTableConstant.SUMMARYWAY_DAY)){
			throw new IllegalArgumentException(PhrasesConstant.PARAM_ERROR);
		}

		List<Map<String, Object>> datas;
		if(campaignId != null){
			//查询指定活动的数据
			datas = getCreativeDataByCampaignId(startDate, endDate, type, campaignId);
		}else if(campaignId == null && projectId != null){
			//查询指定项目的数据
			datas = getCreativeDataByProjectId(startDate, endDate, type, projectId);
		}else if(campaignId == null && projectId == null && advertiserId != null){
			//查询指定用户的数据
			datas = getCreativeDataByAdvertiserId(startDate, endDate, type, advertiserId);
		}else{
			datas = getAllCreativeData(startDate, endDate, type);
		}
		return datas;
	}

	/**
	 * 列出创意数据--根据创意ID查
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @param creativeId
	 * @return
     * @throws Exception
     */
	public List<Map<String, Object>> getCreativeDataByCreativeId(Long startDate, Long endDate,String type, String creativeId) throws Exception {
		// 获取所有天
		String[] days = DateUtils.getDaysBetween(new Date(startDate), new Date(endDate));
		List<String> creativeIds = new ArrayList<String>();
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		creativeIds.add(creativeId);

		//查询创意名称
		CreativeModel model = creativeDao.selectByPrimaryKey(creativeId);
		if (model == null) {
			throw new ResourceNotFoundException();
		}
		//创意没有name,改为取id
		String id = model.getId();
		Map<String,Object> otherValue = new HashMap<>();
		otherValue.put("id",id);
		if(type.equals(CodeTableConstant.SUMMARYWAY_TOTAL)) {//汇总
			results = getFlowData_total(startDate,endDate,creativeIds,otherValue);

		}else if(type.equals(CodeTableConstant.SUMMARYWAY_DAY)){
			results = getFlowData_Day(days,creativeIds,otherValue);
		}

		return results;
	}

	/**
	 * 列出创意数据--根据活动查
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @param campaignId
	 * @return
     * @throws Exception
     */
	public List<Map<String, Object>> getCreativeDataByCampaignId(Long startDate, Long endDate,String type, String campaignId) throws Exception {
		//获取活动下的创意ID
		List<String> creativeIds = getCreativeIdListByCampaignId(campaignId);
		if(creativeIds == null){
			return null;
		}
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

		for(String creativeId : creativeIds){
			List<Map<String, Object>> result = getCreativeDataByCreativeId(startDate,endDate,type,creativeId);
			if(result != null){
				results.addAll(result);
			}
		}
		return results;
	}

	/**
	 * 列出创意数据--根据项目查
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @param projectId
	 * @return
     * @throws Exception
     */
	public List<Map<String, Object>> getCreativeDataByProjectId(Long startDate, Long endDate,String type, String projectId) throws Exception {
		List<String> campaignIds = findCampaignIdListByProjectId(projectId);
		if(campaignIds == null) {
			return null;
		}
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		//循环活动id,根据活动汇总数据
		for(String campaignId : campaignIds){
			List<Map<String, Object>> result = getCreativeDataByCampaignId(startDate,endDate,type,campaignId);
			if(result != null){
				results.addAll(result);
			}

		}
		return results;
	}

	/**
	 * 列出创意数据--根据客户查找
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @param advertiserId
	 * @return
     * @throws Exception
     */
	public List<Map<String, Object>> getCreativeDataByAdvertiserId(Long startDate, Long endDate,String type, String advertiserId) throws Exception {
		//根据客户id获取下面的projectid
		ProjectModelExample example = new ProjectModelExample();
		example.createCriteria().andAdvertiserIdEqualTo(advertiserId);
		List<ProjectModel> projectList = projectDao.selectByExample(example);
		if(projectList == null){
			return null;
		}
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		//循环项目信息，根据项目查
		for(ProjectModel projectModel: projectList){
			List<Map<String, Object>> result = getCreativeDataByProjectId(startDate,endDate,type,projectModel.getId());
			if(result != null) {
				results.addAll(result);
			}
		}

		return results;
	}

	/**
	 * 列出创意数据--查询全部用户的创意数据
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @return
     * @throws Exception
     */
	public List<Map<String, Object>> getAllCreativeData (Long startDate, Long endDate,String type) throws Exception {
		//获取所有客户信息
		List<AdvertiserModel> advertiserList =advertiserDao.selectByExample(null);
		if(advertiserList == null){
			return null;
		}
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		//循环客户信息，按活动汇总数据
		for(AdvertiserModel advertiserModel: advertiserList){
			List<Map<String, Object>> result = getCreativeDataByAdvertiserId(startDate,endDate,type,advertiserModel.getId());
			if(result !=null) {
				results.addAll(result);
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

    /**
     * 转化数据导入
     * @param file      模版文件
     * @param projectId 项目ID
     */
    @Transactional
    public void importEffect(MultipartFile file, String projectId) throws IOException, EncryptedDocumentException, IllegalArgumentException, InvalidFormatException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException
    {
        InputStream inputStream = file.getInputStream();
       
        List<EffectModel> modelList = null;
        try
        {
            modelList = readTemplateFile(inputStream);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(PhrasesConstant.EFFECT_TEMPLATE_FORMAT_ERROR);
        }
        
        if (modelList == null || modelList.isEmpty())
        {
            throw new IllegalArgumentException(PhrasesConstant.EFFECT_TEMPLATE_FORMAT_ERROR);
        }
        
        // 遍历Excel文件中的每个数据行
        for (EffectModel model : modelList)
        {
            model.setId(UUIDGenerator.getUUID());
            model.setProjectId(projectId);
            
            EffectModelExample example = new EffectModelExample();
            
            // 使用日期、监测码作为联合主键，查询该条记录是否已存在
            example.createCriteria().andCodeEqualTo(model.getCode()).andDateEqualTo(model.getDate());

            List<EffectModel> effectsInDB = effectDao.selectByExample(example);
            
            // 如果记录已存在，则更新，否则直接插入
            if (effectsInDB != null && !effectsInDB.isEmpty())
            {
                effectDao.updateByExampleSelective(model, example);
            }
            else
            {
                effectDao.insert(model);
            }
        }
        
        // 向上传文件插入数据
        // 1.获取项目名称
        ProjectModel project = projectDao.selectByPrimaryKey(projectId);
        String projectName = project.getName();
        // 2. 插入属性
        EffectFileModel effectFile = new EffectFileModel();
        effectFile.setId(UUIDGenerator.getUUID());             // 文件id
        effectFile.setName(file.getOriginalFilename());        // 文件名称
        effectFile.setProjectId(projectId);                    // 项目id
        effectFile.setProjectName(projectName);                // 项目名称
        effectFile.setAmount(modelList.size());                // 匹配数量
        // 3.插入
        effectFileDao.insert(effectFile);
    }
    
    @Transactional
    public List<EffectFileBean> listEffectFiles() throws Exception {
    	// 查询上传文件信息
    	EffectFileModelExample example = new EffectFileModelExample();
    	List<EffectFileModel> models = effectFileDao.selectByExample(example);
    	// 定义返回的list
    	List<EffectFileBean> beans = new ArrayList<EffectFileBean>();
    	// 遍历数据库中查询到的全部结果，逐个将DAO创建的新对象复制回传输对象中
    	for (EffectFileModel model : models) {
    		EffectFileBean bean = modelMapper.map(model, EffectFileBean.class);
    		beans.add(bean);
    	}
		return beans;   	
    }
    
    /**
     * 读取转化数据模板文件，转换成一个实体列表。
     * @param inp   上传文件的字节输入流
     * @return
     * @throws EncryptedDocumentException
     * @throws InvalidFormatException
     * @throws FileNotFoundException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private List<EffectModel> readTemplateFile(InputStream inp) throws EncryptedDocumentException, InvalidFormatException, FileNotFoundException, IOException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException
    {
        Workbook wb = WorkbookFactory.create(inp);
       
        Sheet sheet = wb.getSheetAt(0);
        
        Row firstRow = sheet.getRow(0);
        
        // 模板文件中数据行的行号（base 0）：除去指标行（A1，A2....A10)、除去列头（日期、监测码、注册数...）
        int beginDataLine = 2;
        
        // 获得当前Sheet的总行数（不为空的行数）
        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
        
        // 获得第一行中共有多少个非空列
        int phyNumOfCells = firstRow.getPhysicalNumberOfCells() - 2;
        
        // 获得第一行中第一个不为空的逻辑单元格（base 0），即，“指标”的第一列（最小为A1，最大为A10）
        int firstCellIndex = 2;//firstRow.getFirstCellNum();
        
        // 获得第一行中最后一个不为空的逻辑单元格（base 1），即，“指标”的最后一列（最小为A1，最大为A10）
        int lastCellIndex = firstRow.getLastCellNum();
        
        // 构建第一行中的物理列号与单元格内容的映射
        Map<Integer, String> titleMap = buildColumnCellMap(firstRow, firstCellIndex, lastCellIndex);
        
        // --> 检查列边界：保证“指标”列左侧有日期和监测码两列，保证“指标”列最多为10列，保证“指标列”不间断
        if (firstCellIndex < 2 || lastCellIndex > 12)
        {
            throw new IllegalArgumentException(PhrasesConstant.EFFECT_TEMPLATE_FORMAT_ERROR);
        }
        if (lastCellIndex != firstCellIndex + phyNumOfCells)
        {
            throw new IllegalArgumentException(PhrasesConstant.EFFECT_TEMPLATE_FORMAT_ERROR);
        }
        
        // --> 检查行边界
        if (physicalNumberOfRows < beginDataLine)
        {
            throw new IllegalArgumentException(PhrasesConstant.EFFECT_TEMPLATE_FORMAT_ERROR);
        }
        
        // --> 检查指标列映射
        if (titleMap == null || titleMap.isEmpty())
        {
            throw new IllegalArgumentException(PhrasesConstant.EFFECT_TEMPLATE_FORMAT_ERROR);
        }
        
        Row tmpRow = null;
        Cell tmpCell = null;
        List<EffectModel> list = new ArrayList<EffectModel>();
        
        // 遍历全部数据行
        for (int i = beginDataLine; i < physicalNumberOfRows; i++)
        {
            tmpRow = sheet.getRow(i);
            
            Class<EffectModel> effectClass = EffectModel.class;
            EffectModel td = effectClass.newInstance();
            
            Cell dateCell = tmpRow.getCell(0);
            Cell codeCell = tmpRow.getCell(1);
            
            // 如果第一列日期设置不正确
            if (dateCell == null || codeCell == null)
            {
                throw new IllegalArgumentException(PhrasesConstant.EFFECT_TEMPLATE_FORMAT_ERROR);
            }
            
            Date firstColumnValue = dateCell.getDateCellValue();
            if (firstColumnValue == null)
            {
                throw new InvalidFormatException(PhrasesConstant.EFFECT_TEMPLATE_FORMAT_ERROR);
            }
            else
            {
                td.setDate(firstColumnValue);
            }
            
            // 如果第二列监测码设置不正确
            String secondColumnValue = null;
            CellType cellTypeEnum = codeCell.getCellTypeEnum();
            if (cellTypeEnum == CellType.NUMERIC)
            {
                secondColumnValue = String.valueOf(codeCell.getNumericCellValue());
            }
            else if (cellTypeEnum == CellType.STRING)
            {
                secondColumnValue = codeCell.getStringCellValue();
            }
            
            if (!StringUtils.isEmpty(secondColumnValue))
            {
                td.setCode(secondColumnValue);
            }
            else 
            {
                throw new InvalidFormatException(PhrasesConstant.EFFECT_TEMPLATE_FORMAT_ERROR);
            }
            
            
            // 遍历全部的“指标”列（不包含第一列日期，第二列监测码以外的全部列，形如A1, A2, ... , A10）
            for (int j = firstCellIndex; j < lastCellIndex; j++)
            {
                tmpCell = tmpRow.getCell(j);
                
                String columnVal = titleMap.get(j);// 根据指定的物理列号，获得它是属于哪个指标，如：第2列，对应的是A3，则调用setA3()
                
                String methodName = "set" + columnVal;// 拼接成反射需要调用的方法名
                
                Method method = effectClass.getDeclaredMethod(methodName, Double.class);
               
                method.invoke(td, tmpCell.getNumericCellValue());
            }
            
            list.add(td);
        }
        
        wb.close();
        return list;
    }


    /**
     * 构建第一行中的自定义列名与物理列值的映射。
     * 如物理单元格：C1,D1,E1中分别保存的值为A3,A2,A1，则构造一个Map，Key为物理列列号，Value为单元格中的值：2-A3, 3-A2, 4-A1
     * @param firstRow          第一行对象
     * @param firstCellIndex    获取第一个不为空的单元格是第几列（物理列号）
     * @param lastCellIndex     获取最后一个不为空的单元格是第几列（物理列号）
     * @return
     */
    private static Map<Integer, String> buildColumnCellMap(Row firstRow, int firstCellIndex, int lastCellIndex)
    {
        Map<Integer, String> titleMap = new HashMap<Integer, String>();
        for (int i = firstCellIndex; i < lastCellIndex; i++)
        {
            titleMap.put(i, firstRow.getCell(i).getStringCellValue());
        }
        return titleMap;
    }

	/**
	 * 把数据导入到07版Excel中，并下载
	 * @param type
	 * @param datas
	 * @param fileName
	 * @param response
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws UnsupportedEncodingException
     */
    public void exportDataToExcel(String type,List<Map<String, Object>> datas, String fileName, HttpServletResponse response) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, UnsupportedEncodingException {
    	if(datas == null){
    		return ;
		}

		// 定义列名称和列宽度
		String[] recoresColumns;
		// 定义需要显示在Excel行中的实体Bean中的属性名称
		String[] recoresFields;
		// REVIEW ME: 字符串操作尽量避免中文
		if(type.equals(CodeTableConstant.SUMMARYWAY_TOTAL)) {//汇总
			if(fileName.startsWith("creatives")) {
				recoresColumns = new String[]{"ID_#_3000", "展现数_#_3000", "点击数_#_3000", "CTR_#_3000", "二跳数_#_3000", "成本_#_3000","修正成本_#_3000", "千次展现成本_#_4000", "点击成本_#_3000", "二跳成本_#_3000"};
				recoresFields = new String[]{"id", "impressionAmount", "clickAmount", "clickRate", "jumpAmount", "totalCost","adxCost", "impressionCost", "clickCost", "jumpCost"};
			}else{
				recoresColumns = new String[]{"名称_#_3000", "展现数_#_3000", "点击数_#_3000", "CTR_#_3000", "二跳数_#_3000", "成本_#_3000", "修正成本_#_3000","千次展现成本_#_4000", "点击成本_#_3000", "二跳成本_#_3000"};
				recoresFields = new String[]{"name", "impressionAmount", "clickAmount", "clickRate", "jumpAmount", "totalCost","adxCost", "impressionCost", "clickCost", "jumpCost"};
			}
		}else{
			if(fileName.startsWith("creatives")) {
				recoresColumns = new String[]{"日期_#_3000", "ID_#_3000", "展现数_#_3000", "点击数_#_3000", "CTR_#_3000", "二跳数_#_3000", "成本_#_3000","修正成本_#_3000", "千次展现成本_#_4000", "点击成本_#_3000", "二跳成本_#_3000"};
				recoresFields = new String[]{"date", "id", "impressionAmount", "clickAmount", "clickRate", "jumpAmount", "totalCost","adxCost", "impressionCost", "clickCost", "jumpCost"};
			}else{
				recoresColumns = new String[]{"日期_#_3000", "名称_#_3000", "展现数_#_3000", "点击数_#_3000", "CTR_#_3000", "二跳数_#_3000", "成本_#_3000", "修正成本_#_3000","千次展现成本_#_4000", "点击成本_#_3000", "二跳成本_#_3000"};
				recoresFields = new String[]{"date", "name", "impressionAmount", "clickAmount", "clickRate", "jumpAmount", "totalCost","adxCost", "impressionCost", "clickCost", "jumpCost"};
			}
		}

		XSSFWorkbook workBook = new XSSFWorkbook();
		ExcelUtil<BasicDataBean> excelUtil = new ExcelUtil<BasicDataBean>();
		String sheetName ="sheet0";
		//把数据添加到excel中
		setDataToExcel(excelUtil,workBook, sheetName, datas, recoresColumns, recoresFields);
		//把文件写入到流中
		ByteArrayInputStream inputStream = (ByteArrayInputStream) ExcelUtil.writeExcelToStream(workBook);
		//下载07Excelw文件
		ExcelOperateUtil.downloadExcel07(inputStream, response, fileName);
    }

	/**
	 * 把数据添加到07版exel中
	 * @param excelUtil
	 * @param workBook
	 * @param sheetName
	 * @param dataList
	 * @param headerColumns
	 * @param fieldColumns
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws java.lang.IllegalArgumentException
     * @throws InvocationTargetException
     */
	public XSSFSheet setDataToExcel(ExcelUtil excelUtil,XSSFWorkbook workBook, String sheetName, List<Map<String,Object>> dataList, String[] headerColumns, String[] fieldColumns)
			throws NoSuchMethodException, IllegalAccessException, java.lang.IllegalArgumentException, InvocationTargetException
	{

		XSSFSheet sheet = workBook.createSheet(sheetName);

		excelUtil.setGenerateHeader(workBook, sheet, headerColumns);
		//默认单元格格式
		XSSFCellStyle style = excelUtil.getCellStyle(workBook, false);

		//货币格式保留两位小数
		XSSFCellStyle currencyStyle = excelUtil.getCellStyle(workBook, false);
		XSSFDataFormat dataFormat = workBook.createDataFormat();
		currencyStyle.setDataFormat(dataFormat.getFormat("￥###0.00"));

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");

		//千分位格式化
		DecimalFormat decimalFormat=new DecimalFormat(".00\u2030");

		int rowNum = 0;
		for (Map<String,Object> dataMap : dataList)
		{
			rowNum++;
			Row row = sheet.createRow(rowNum);
			row.setHeightInPoints(25);
			for (int i = 0; i < fieldColumns.length; i++)
			{
				String fieldName = fieldColumns[i];
				try
				{
					Object cellValue = dataMap.get(fieldName);
					Cell cell = row.createCell(i);
					cell.setCellStyle(style);
					if (cellValue instanceof String) {
						String stringValue = (String) cellValue;
						cell.setCellValue(stringValue);
					} else if (cellValue instanceof Double) {
						double doubleValue = ((Double) cellValue).doubleValue();
						cell.setCellValue(doubleValue);
					} else if (cellValue instanceof Float) {
						float floatValue = ((Float) cellValue).floatValue();
						if(fieldName.equals("clickRate")){
							cell.setCellValue(decimalFormat.format(floatValue));
						}else {
							cell.setCellStyle(currencyStyle);
							cell.setCellValue(floatValue);
						}
					} else if (cellValue instanceof Long) {
						long longValue = ((Long) cellValue).longValue();
						cell.setCellValue(longValue);
					}else  if (cellValue instanceof Integer) {
						int intValue = ((Integer) cellValue).intValue();
						cell.setCellValue(intValue);
					}

				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		return sheet;
	}

	/**
	 * 给数据excel命名
	 * @param type
	 * @param startDate
	 * @param endDate
     * @return
     */
	@Transactional
	public String renameDatasExcel(String type, Long startDate, Long endDate) {
		// REVIEW　ME: 导出文件名中尽量避免出现中文
		String typeName ="";
		if(type.equals(CodeTableConstant.SUMMARYWAY_TOTAL)){
			typeName="total";
		}else if(type.equals(CodeTableConstant.SUMMARYWAY_DAY)){
			typeName="day";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fileName = typeName+"-"+sdf.format(new Date(startDate))+"to"+sdf.format(new Date(endDate));
		return fileName;
	}
	
	/**
	 * 获取单个创意一个时间段内的数据
	 * @param creativeId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public BasicDataBean getCreativeData(String creativeId, long startDate, long endDate) {
		// 先从redis中查询是否有该创意的数据
		CreativeBean creativeData = new CreativeBean();
		if (redisHelper3.exists(RedisKeyConstant.CREATIVE_DATA_DAY + creativeId)) {
			String[] days = DateUtils.getDaysBetween(new Date(startDate), new Date(endDate));
			// 修正花费要查出的相关信息
			// 1.创意投放的adxID
			CreativeBasicModelExample creativeBasicExample = new CreativeBasicModelExample();
			creativeBasicExample.createCriteria().andCreativeIdsLike("%" + creativeId + "%");
			List<CreativeBasicModel> creativeBasics = creativeBasicDao.selectByExampleWithBLOBs(creativeBasicExample);
			if (creativeBasics.isEmpty()) {
				return creativeData;
			}
			// 获取日数据对象
			Map<String, String> dayData = redisHelper3.hget(RedisKeyConstant.CREATIVE_DATA_DAY + creativeId);
			long impression = 0L, click = 0L, jump = 0L;
			double expense = 0D, adxExpense = 0D;
			for (String day : days) {
				// 基础数据累加
				String impressionKey = day + CREATIVE_DATA_SUFFIX.IMPRESSION;
				if (dayData.containsKey(impressionKey)) {
					impression += Long.parseLong(dayData.get(impressionKey));
				}
				String clickKey = day + CREATIVE_DATA_SUFFIX.CLICK;
				if (dayData.containsKey(clickKey)) {
					click += Long.parseLong(dayData.get(clickKey));
				}
				String jumpKey = day + CREATIVE_DATA_SUFFIX.JUMP;
				if (dayData.containsKey(jumpKey)) {
					jump += Long.parseLong(dayData.get(jumpKey));
				}
				String expenseKey = day + CREATIVE_DATA_SUFFIX.EXPENSE;
				if (dayData.containsKey(expenseKey)) {
					expense += Double.parseDouble(dayData.get(expenseKey)) / 100;
				}
				
				// 修正花费累加
				for (CreativeBasicModel basic : creativeBasics) {
					String adxId = basic.getAdxId();
					String adxExpenseKey = day + CREATIVE_DATA_TYPE.ADX + adxId + CREATIVE_DATA_SUFFIX.EXPENSE;
					// 判断该adx是否有数据
					if (dayData.containsKey(adxExpenseKey)) {
						// 获取当天的花费
						double adxExpenseVal = Double.parseDouble(dayData.get(adxExpenseKey)) / 100;
						// 获取当天的修正比
						AdxCostModelExample adxCostExample = new AdxCostModelExample();
						Date dayDate = DateUtils.strToDate(day, "yyyyMMdd");
						adxCostExample.createCriteria().andAdxIdEqualTo(adxId).andStartDateLessThanOrEqualTo(dayDate).andEndDateGreaterThanOrEqualTo(dayDate);
						List<AdxCostModel> adxCosts = adxCostDao.selectByExample(adxCostExample);
						if (adxCosts.isEmpty()) {
							continue;
						}
						float ratio = adxCosts.get(0).getRatio();
						adxExpense += adxExpenseVal * ratio;
					}
				}
			}
			creativeData.setId(creativeId);
			creativeData.setImpressionAmount(impression);
			creativeData.setClickAmount(click);
			creativeData.setClickRate(impression == 0 ? 0f : (float)click/impression);
			creativeData.setJumpAmount(jump);
			creativeData.setTotalCost(expense);
			creativeData.setAdxCost(adxExpense);
			creativeData.setImpressionCost(impression == 0 ? 0f : (float)expense/impression);
			creativeData.setClickCost(click == 0 ? 0f : (float)expense/click);
			creativeData.setJumpCost(jump == 0 ? 0f : (float)expense/jump);
		}
		
		return creativeData;
	}
	
	/**
	 * 
	 * @param campaignId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public BasicDataBean getCampaignData(String campaignId, long startDate, long endDate) {
		CampaignBean campaignData = new CampaignBean();
		CreativeModelExample example = new CreativeModelExample();
		example.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> models = creativeDao.selectByExample(example);
		long impression = 0L, click = 0L, jump = 0L;
		double expense = 0D, adxExpense = 0D;
		for (CreativeModel model : models) {
			CreativeBean bean = (CreativeBean)getCreativeData(model.getId(), startDate, endDate);
			impression += bean.getImpressionAmount();
			click += bean.getClickAmount();
			jump += bean.getJumpAmount();
			expense += bean.getTotalCost();
			adxExpense += bean.getAdxCost();
		}
		campaignData.setId(campaignId);
		campaignData.setImpressionAmount(impression);
		campaignData.setClickAmount(click);
		campaignData.setClickRate(impression == 0 ? 0f : (float)click/impression);
		campaignData.setJumpAmount(jump);
		campaignData.setTotalCost(expense);
		campaignData.setAdxCost(adxExpense);
		campaignData.setImpressionCost(impression == 0 ? 0f : (float)expense/impression);
		campaignData.setClickCost(click == 0 ? 0f : (float)expense/click);
		campaignData.setJumpCost(jump == 0 ? 0f : (float)expense/jump);
		
		return campaignData;
	}
	
	public BasicDataBean getProjectData(String projectId, long startDate, long endDate) {
		ProjectBean projectData = new ProjectBean();
		CampaignModelExample example = new CampaignModelExample();
		example.createCriteria().andProjectIdEqualTo(projectId);
		List<CampaignModel> models = campaignDao.selectByExample(example);
		long impression = 0L, click = 0L, jump = 0L;
		double expense = 0D, adxExpense = 0D;
		for (CampaignModel model : models) {
			CampaignBean bean = (CampaignBean)getCampaignData(model.getId(), startDate, endDate);
			impression += bean.getImpressionAmount();
			click += bean.getClickAmount();
			jump += bean.getJumpAmount();
			expense += bean.getTotalCost();
			adxExpense += bean.getAdxCost();
		}
		projectData.setId(projectId);
		projectData.setImpressionAmount(impression);
		projectData.setClickAmount(click);
		projectData.setClickRate(impression == 0 ? 0f : (float)click/impression);
		projectData.setJumpAmount(jump);
		projectData.setTotalCost(expense);
		projectData.setAdxCost(adxExpense);
		projectData.setImpressionCost(impression == 0 ? 0f : (float)expense/impression);
		projectData.setClickCost(click == 0 ? 0f : (float)expense/click);
		projectData.setJumpCost(jump == 0 ? 0f : (float)expense/jump);
		
		return projectData;
	}
	
}
