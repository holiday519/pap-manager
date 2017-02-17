package com.pxene.pap.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.JedisUtils;
import com.pxene.pap.domain.beans.LandpageDataBean;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.LandpageModel;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.LandpageDao;

@Service
public class LandpageDataService extends BaseService
{
	@Autowired
	private LandpageDao landpageDao;
	
	@Autowired
	private CampaignDao campaignDao;
	
    DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");    
    
    @Transactional
    public List<LandpageDataBean> listLandpageDatas(String campaignId, long beginTime, long endTime) throws Exception
    {
    	Map<String, String> sourceMap = new HashMap<String, String>();
    	DateTime begin = new DateTime(beginTime);
    	DateTime end = new DateTime(endTime);
    	if (end.toString("yyyy-MM-dd").equals(begin.toString("yyyy-MM-dd"))) {
    		//查看是不是全天(如果是全天，查询天表；但是时间不能是今天，当天数据还未生成天文件)
			if (begin.toString("HH").equals("00") && end.toString("HH").equals("23")
					&& !begin.toString("yyyy-MM-dd").equals(new DateTime().toString("yyyy-MM-dd"))) {
				String [] days = {begin.toString("yyyyMMdd")};
				sourceMap = margeDayTables(sourceMap, days);
			} else {
				sourceMap = makeDayTableUseHour(sourceMap, begin.toDate(), end.toDate());
			}
    	} else {
			String[] days = DateUtils.getDaysBetween(begin.toDate(), end.toDate());
			List<String> daysList = new ArrayList<String>(Arrays.asList(days));
			if (!begin.toString("HH").equals("00")) {
				Date bigHourOfDay = DateUtils.getBigHourOfDay(begin.toDate());
				sourceMap = makeDayTableUseHour(sourceMap, begin.toDate(), bigHourOfDay);
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
				sourceMap = makeDayTableUseHour(sourceMap, smallHourOfDay, begin.toDate());
				if (daysList != null && daysList.size() > 0) {
					for (int i = 0; i < daysList.size(); i++) {
						if (daysList.get(i).equals(end.toString("yyyyMMdd"))) {
							daysList.remove(i);
						}
					}
				}
			}
			sourceMap = margeDayTables(sourceMap, daysList.toArray(new String[daysList.size()]));
		}
    	
    	//查询活动对应的落地页id;不是这个id的数据不显示
    	CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
    	
    	if (campaignModel == null) {
    		return new ArrayList<LandpageDataBean>();
    	}
    	String landpageId = campaignModel.getLandpageId();
    	List<LandpageDataBean> beans = getListFromSource(landpageId, sourceMap);
    	formatLastList(beans);
    	return beans;
    }
    
    /**
     * 将小时文件拼成一个（类似天文件）合并至源Map中
     * @param sourceMap 源Map
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return
     */
	private Map<String, String> makeDayTableUseHour(Map<String, String> sourceMap, Date beginTime, Date endTime)	throws Exception {
		String[] hours = DateUtils.getHoursBetween(beginTime, endTime);
		for (int i = 0; i < hours.length; i++) {
			String hour = hours[i];
			Map<String, String> map = JedisUtils.hget("landpageDataHour_" + new DateTime(beginTime).toString("yyyyMMdd") + hour);
			Set<String> hkeys = JedisUtils.hkeys("landpageDataHour_" + new DateTime(beginTime).toString("yyyyMMdd") + hour);
			if (hkeys != null && !hkeys.isEmpty()) {
				for (String hkey : hkeys) {
					String hourStr = map.get(hkey);
					if (sourceMap.containsKey(hkey)) {
						String str = sourceMap.get(hkey);
						if (StringUtils.isEmpty(str)) {
							// 根据类型判断求和类型int？double？
							if (hkey.indexOf("@m") > 0) {// 展现
								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
							} else if (hkey.indexOf("@c") > 0) {// 点击
								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
							} else if (hkey.indexOf("@a") > 0) {// 到达
								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
							} else if (hkey.indexOf("@w") > 0) {// 中标
								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
							} else if (hkey.indexOf("@u") > 0) {// 独立访客数
								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
							}
						} else {
							sourceMap.put(hkey, hourStr);
						}
					} else {
						sourceMap.put(hkey, hourStr);
					}
				}
			}
		}
		return sourceMap;
	}
    
    /**
     * 整合天文件为一个文件
     * @param sourceMap
     * @param days
     * @return
     * @throws Exception
     */
	private Map<String, String> margeDayTables(Map<String, String> sourceMap, String [] days) throws Exception {
    	if (days!=null && days.length > 0) {
    		for (int i = 0; i < days.length; i++) {
    			String day = days[i];
    			Map<String, String> map = JedisUtils.hget("landpageDataDay_" + day);
    			Set<String> hkeys = JedisUtils.hkeys("landpageDataDay_" + day);
    			if (hkeys != null && !hkeys.isEmpty()) {
    				for (String hkey : hkeys) {
    					String hourStr = map.get(hkey);
    					if (sourceMap.containsKey(hkey)) {
    						String str = sourceMap.get(hkey);
    						if (StringUtils.isEmpty(str)) {
    							// 根据类型判断求和类型int？double？
    							if (hkey.indexOf("@m") > 0) {// 展现
    								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
    							} else if (hkey.indexOf("@c") > 0) {// 点击
    								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
    							} else if (hkey.indexOf("@a") > 0) {// 到达
    								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
    							} else if (hkey.indexOf("@w") > 0) {// 中标
    								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
    							} else if (hkey.indexOf("@u") > 0) {// 独立访客数
    								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
    							}
    						} else {
    							sourceMap.put(hkey, hourStr);
    						}
    					} else {
    						sourceMap.put(hkey, hourStr);
    					}
    				}
    			}
    		}
    	}
		return sourceMap;
    }
    
    /**
     * 将合并后的Map数据整理成List数据
     * @param sourceMap
     * @return
     * @throws Exception
     */
	private List<LandpageDataBean> getListFromSource(String landpageId, Map<String, String> sourceMap) throws Exception {
    	List<LandpageDataBean> beans = new ArrayList<LandpageDataBean>();
    	for (String key : sourceMap.keySet()) {
    		if (!StringUtils.isEmpty(key)) {
    			String value = sourceMap.get(key);
    			beans = takeDataToList(landpageId, beans, key, value);
    		}
    	}
    	return beans;
    }
    
    /**
     * 将数据整合到list中
     * @param beans
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
	private List<LandpageDataBean> takeDataToList(String landpageId, List<LandpageDataBean> beans, String key, String value) throws Exception {
    	String[] keyArray = key.split("@");
    	String ldpgId = keyArray[0];
    	boolean isFlag = false;//当前landpageid是否属于当前活动
    	
    	if (landpageId.equals(ldpgId)) {
    		isFlag = true;
    	}
    	
    	//如果当前landpageid属于当前活动才整合数据
    	if (isFlag) {
    		if (beans.isEmpty()) {
    			LandpageDataBean bean = new LandpageDataBean();
    			if (key.indexOf("@s") > 0) {// 平均停留时间
    				bean.setResidentTime(Long.parseLong(value));
    			} else if (key.indexOf("@a") > 0) {// 到达
    				bean.setArrivalAmount(Long.parseLong(value));
    			} else if (key.indexOf("@j") > 0) {// 二跳量
    				bean.setJumpAmount(Long.parseLong(value));
    			} else if (key.indexOf("@u") > 0) {// 独立访客数
    				bean.setUniqueAmount(Long.parseLong(value));
    			}
    			bean.setId(ldpgId);
    			beans.add(bean);
    		} else {
    			boolean flag = false;
    			int index = 0;
    			for (int i=0;i < beans.size();i++) {
    				LandpageDataBean bean = beans.get(i);
    				if (bean.getId().equals(ldpgId)) {
    					index = i;
    					flag = true;
    					break;
    				}
    			}
    			if (flag) {
    				LandpageDataBean bean = beans.get(index);
    				if (key.indexOf("@s") > 0) {// 平均停留时间
    					bean.setResidentTime(Long.parseLong(value) + (bean.getResidentTime()==null?0:bean.getResidentTime()));
    				} else if (key.indexOf("@a") > 0) {// 到达
    					bean.setArrivalAmount(Long.parseLong(value) + (bean.getArrivalAmount()==null?0:bean.getArrivalAmount()));
    				} else if (key.indexOf("@j") > 0) {// 二跳量
    					bean.setJumpAmount(Long.parseLong(value) + (bean.getJumpAmount()==null?0:bean.getJumpAmount()));
    				} else if (key.indexOf("@u") > 0) {// 独立访客数
    					bean.setUniqueAmount(Long.parseLong(value) + (bean.getUniqueAmount()==null?0:bean.getUniqueAmount()));
    				}
    				bean.setId(ldpgId);
    			} else {
    				LandpageDataBean bean = new LandpageDataBean();
    				if (key.indexOf("@s") > 0) {// 平均停留时间
    					bean.setResidentTime(Long.parseLong(value) + (bean.getResidentTime()==null?0:bean.getResidentTime()));
    				} else if (key.indexOf("@a") > 0) {// 到达
    					bean.setArrivalAmount(Long.parseLong(value) + (bean.getArrivalAmount()==null?0:bean.getArrivalAmount()));
    				} else if (key.indexOf("@j") > 0) {// 二跳量
    					bean.setJumpAmount(Long.parseLong(value) + (bean.getJumpAmount()==null?0:bean.getJumpAmount()));
    				} else if (key.indexOf("@u") > 0) {// 独立访客数
    					bean.setUniqueAmount(Long.parseLong(value) + (bean.getUniqueAmount()==null?0:bean.getUniqueAmount()));
    				}
    				bean.setId(ldpgId);
    				beans.add(bean);
    			}
    		}
    	}
		return beans;
    }
	
    /**
     * 格式化list中各个返回参数
     * @param beans
     * @return
     */
	private List<LandpageDataBean> formatLastList(List<LandpageDataBean> beans) {
    	DecimalFormat format = new DecimalFormat("0.00000");
    	if (beans != null && beans.size() > 0) {
    		for (LandpageDataBean bean : beans) {
				if (bean.getArrivalAmount() == null) {
					bean.setArrivalAmount(0L);
				}
				if (bean.getUniqueAmount() == null) {
					bean.setUniqueAmount(0L);
				}
				if (bean.getJumpAmount() == null) {
					bean.setJumpAmount(0L);
				}
				if (bean.getResidentTime() == null) {
					bean.setResidentTime(0L);
				}
				if (bean.getArrivalAmount() == 0) {
					bean.setJumpRate(0F);
				} else {
			        double percent = (double)bean.getJumpAmount() / bean.getArrivalAmount();
			        Float result = Float.parseFloat(format.format(percent));
			        bean.setJumpRate(result);
				}
				if (!StringUtils.isEmpty(bean.getId())) {
					LandpageModel model = landpageDao.selectByPrimaryKey(bean.getId());
					bean.setId(model.getId());
					bean.setName(model.getName());
				}
    		}
    	}
    	//将所有不重复的landpageId放入List————根据List就知道最后条数
    	List<String> landpageIds = new ArrayList<String>();
    	for (LandpageDataBean bean : beans) {
    		if (landpageIds.contains(bean.getId())) {
    			landpageIds.add(bean.getId());	
    		}
    	}
    	//创建一个新的List存放结果用
    	List<LandpageDataBean> newBeans = new ArrayList<LandpageDataBean>();
    	if (beans !=null && !beans.isEmpty()) {
			for (LandpageDataBean bean : beans) {
				//如果list里没有CreativeId，先添加进去
				if(indexOfBean(newBeans, bean.getId()) == null){
					newBeans.add(bean);
				} else {
					//如果有，就将相同landpageId的数据合并成一条
					int index = indexOfBean(newBeans, bean.getId());
					LandpageDataBean dataBean = newBeans.get(index);
					dataBean.setArrivalAmount(dataBean.getArrivalAmount() + bean.getArrivalAmount());
					dataBean.setUniqueAmount(dataBean.getUniqueAmount() + bean.getUniqueAmount());
					
					if (dataBean.getArrivalAmount() == 0) {
						dataBean.setJumpRate(0F);
					} else {
				        double percent = (double)dataBean.getJumpAmount() / dataBean.getArrivalAmount();
				        Float result = Float.parseFloat(format.format(percent));
				        dataBean.setJumpRate(result);
					}
				}
			}
    	}
    	//返回新的结果集
    	return newBeans;
    }
    
    /**
     * 查询list中是否含有bean
     * @param newBeans
     * @param id
     * @return
     */
	private Integer indexOfBean(List<LandpageDataBean> newBeans, String id) {
    	Integer index = null;
    	for (int i=0;i<newBeans.size();i++) {
    		if (id.equals(newBeans.get(i).getId())) {
    			index = i;
    			break;
    		}
    	}
    	return index;
    }
    
}
