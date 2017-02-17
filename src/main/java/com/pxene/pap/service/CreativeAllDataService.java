package com.pxene.pap.service;

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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.JedisUtils;
import com.pxene.pap.domain.beans.CreativeDataBean;
/**
 * 查询创意所有数据属性（用于广告主、项目、活动、创意列表时数据查询）
 * 查询 点击、展现、二跳、花费
 *
 */
@Service
public class CreativeAllDataService extends BaseService
{
    
    DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");    
    
    @Transactional
    public CreativeDataBean listCreativeData(List<String> creativeIds, long beginTime, long endTime) throws Exception
    {
    	List<CreativeDataBean> beans = listCreativeDatas(creativeIds, beginTime, endTime);
    	CreativeDataBean resultBean = new CreativeDataBean();
    	Float cost = 0F;
		Long jumpAmount = 0L;
		Long impressionAmount = 0L;
		Long clickAmount = 0L;
		if (beans != null && !beans.isEmpty()) {
			for (CreativeDataBean bean : beans) {
				resultBean.setJumpAmount(bean.getJumpAmount() + jumpAmount);
				resultBean.setCost(cost + bean.getCost());
				resultBean.setImpressionAmount(impressionAmount + bean.getImpressionAmount());
				resultBean.setClickAmount(clickAmount + bean.getClickAmount());
			}
		}
    	return resultBean;
    }
    
    @Transactional
    public List<CreativeDataBean> listCreativeDatas(List<String> creativeIds, long beginTime, long endTime) throws Exception
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
    	
    	List<CreativeDataBean> beans = getListFromSource(creativeIds, sourceMap);
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
			Map<String, String> map = JedisUtils.hget("creativeDataHour_" + new DateTime(beginTime).toString("yyyyMMdd") + hour);
			Set<String> hkeys = JedisUtils.hkeys("creativeDataHour_" + new DateTime(beginTime).toString("yyyyMMdd") + hour);
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
							} else if (hkey.indexOf("@j") > 0) {// 二跳数
								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
							} else if (hkey.indexOf("@e") > 0) {// 花费
								sourceMap.put(hkey, String.valueOf(Float.parseFloat(hourStr) + Float.parseFloat(str)));
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
    			Map<String, String> map = JedisUtils.hget("creativeDataDay_" + day);
    			Set<String> hkeys = JedisUtils.hkeys("creativeDataDay_" + day);
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
    							} else if (hkey.indexOf("@j") > 0) {// 二跳数
    								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
    							} else if (hkey.indexOf("@e") > 0) {// 花费
    								sourceMap.put(hkey, String.valueOf(Float.parseFloat(hourStr) + Float.parseFloat(str)));
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
	private List<CreativeDataBean> getListFromSource(List<String> creativeIds, Map<String, String> sourceMap) throws Exception {
    	List<CreativeDataBean> beans = new ArrayList<CreativeDataBean>();
    	for (String key : sourceMap.keySet()) {
    		if (!StringUtils.isEmpty(key)) {
    			String value = sourceMap.get(key);
    			beans = takeDataToList(creativeIds, beans, key, value);
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
	private List<CreativeDataBean> takeDataToList(List<String> creativeIds, List<CreativeDataBean> beans, String key, String value) throws Exception {
    	String[] keyArray = key.split("@");
    	String creativeId = keyArray[0];
    	//只返回当前创意数组中创意id的数据
    	String mapId = keyArray[0];
    	boolean isFlag = false;//当前mapid是否属于当前活动
    	
    	if (creativeIds.contains(mapId)) {
    		isFlag = true;
    	}
    	
    	//如果当前mapid属于当前活动才整合数据
    	if (isFlag) {
    		if (beans.isEmpty()) {
    			CreativeDataBean bean = new CreativeDataBean();
    			if (key.indexOf("@m") > 0) {// 展现
    				bean.setImpressionAmount(Long.parseLong(value));
    			} else if (key.indexOf("@c") > 0) {// 点击
    				bean.setClickAmount(Long.parseLong(value));
    			} else if (key.indexOf("@j") > 0) {// 跳出
    				bean.setUniqueAmount(Long.parseLong(value));
    			} else if (key.indexOf("@e") > 0) {// 花费
    				bean.setCost(Float.parseFloat(value));
    			}
    			bean.setId(creativeId);
    			beans.add(bean);
    		} else {
    			boolean flag = false;
    			int index = 0;
    			for (int i=0;i < beans.size();i++) {
    				CreativeDataBean bean = beans.get(i);
    				if (bean.getId().equals(creativeId)) {
    					index = i;
    					flag = true;
    					break;
    				}
    			}
    			if (flag) {
    				CreativeDataBean bean = beans.get(index);
    				if (key.indexOf("@m") > 0) {// 展现
    					bean.setImpressionAmount(Long.parseLong(value) + (bean.getImpressionAmount()==null?0:bean.getImpressionAmount()));
    				} else if (key.indexOf("@c") > 0) {// 点击
    					bean.setClickAmount(Long.parseLong(value) + (bean.getClickAmount()==null?0:bean.getClickAmount()));
    				} else if (key.indexOf("@j") > 0) {// 二跳量
    					bean.setJumpAmount(Long.parseLong(value) + (bean.getJumpAmount()==null?0:bean.getJumpAmount()));
    				} else if (key.indexOf("@e") > 0) {// 花费
    					bean.setCost(Float.parseFloat(value) + (bean.getCost()==null?0:bean.getCost()));
    				}
    				bean.setId(creativeId);
    			} else {
    				CreativeDataBean bean = new CreativeDataBean();
    				if (key.indexOf("@m") > 0) {// 展现
    					bean.setImpressionAmount(Long.parseLong(value) + (bean.getImpressionAmount()==null?0:bean.getImpressionAmount()));
    				} else if (key.indexOf("@c") > 0) {// 点击
    					bean.setClickAmount(Long.parseLong(value) + (bean.getClickAmount()==null?0:bean.getClickAmount()));
    				} else if (key.indexOf("@j") > 0) {// 二跳量
    					bean.setJumpAmount(Long.parseLong(value) + (bean.getJumpAmount()==null?0:bean.getJumpAmount()));
    				} else if (key.indexOf("@e") > 0) {// 花费
    					bean.setCost(Float.parseFloat(value) + (bean.getCost()==null?0:bean.getCost()));
    				}
    				bean.setId(creativeId);
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
	private List<CreativeDataBean> formatLastList(List<CreativeDataBean> beans) {
    	if (beans != null && beans.size() > 0) {
    		for (CreativeDataBean bean : beans) {
				if (bean.getWinAmount() == null) {
					bean.setWinAmount(0L);
				}
				if (bean.getImpressionAmount() == null) {
					bean.setImpressionAmount(0L);
				}
				if (bean.getClickAmount() == null) {
					bean.setClickAmount(0L);
				}
				if (bean.getArrivalAmount() == null) {
					bean.setArrivalAmount(0L);
				}
				if (bean.getUniqueAmount() == null) {
					bean.setUniqueAmount(0L);
				}
				if (bean.getJumpAmount() == null) {
					bean.setJumpAmount(0L);
				}
				if (bean.getCost() == null) {
					bean.setCost(0F);
				}
    		}
    	}
    	//将所有不重复的creaticeId放入List————根据List就知道最后条数
    	List<String> creativeIds = new ArrayList<String>();
    	for (CreativeDataBean bean : beans) {
    		if (creativeIds.contains(bean.getId())) {
    			creativeIds.add(bean.getId());	
    		}
    	}
    	//创建一个新的List存放结果用
    	List<CreativeDataBean> newBeans = new ArrayList<CreativeDataBean>();
    	if (beans !=null && !beans.isEmpty()) {
			for (CreativeDataBean bean : beans) {
				//如果list里没有CreativeId，先添加进去
				if(indexOfBean(newBeans, bean.getId()) == null){
					newBeans.add(bean);
				} else {
					//如果有，就将相同creativeId的数据合并成一条
					int index = indexOfBean(newBeans, bean.getId());
					CreativeDataBean dataBean = newBeans.get(index);
					dataBean.setWinAmount(dataBean.getWinAmount() + bean.getWinAmount());
					dataBean.setImpressionAmount(dataBean.getImpressionAmount() + bean.getImpressionAmount());
					dataBean.setClickAmount(dataBean.getClickAmount() + bean.getClickAmount());
					dataBean.setArrivalAmount(dataBean.getArrivalAmount() + bean.getArrivalAmount());
					dataBean.setUniqueAmount(dataBean.getUniqueAmount() + bean.getUniqueAmount());
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
	private Integer indexOfBean(List<CreativeDataBean> newBeans, String id) {
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
