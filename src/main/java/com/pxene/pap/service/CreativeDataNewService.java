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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.JedisUtils;
import com.pxene.pap.constant.RedisKeyConstant;
import com.pxene.pap.domain.beans.CreativeDataBean;

@Service
public class CreativeDataNewService extends BaseService
{
    
    DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");    
    
    @Transactional
    public List<CreativeDataBean> listCreativeDatas(String campaignId, long beginTime, long endTime) throws Exception
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
    	
    	List<CreativeDataBean> beans = getListFromSource(campaignId, sourceMap);
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
	private List<CreativeDataBean> getListFromSource(String campaignId, Map<String, String> sourceMap) throws Exception {
    	List<CreativeDataBean> beans = new ArrayList<CreativeDataBean>();
    	for (String key : sourceMap.keySet()) {
    		if (!StringUtils.isEmpty(key)) {
    			String value = sourceMap.get(key);
    			beans = takeDataToList(campaignId, beans, key, value);
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
	private List<CreativeDataBean> takeDataToList(String campaignId, List<CreativeDataBean> beans, String key, String value) throws Exception {
    	String[] keyArray = key.split("@");
    	String creativeId = keyArray[0];
    	//只返回当前活动下创意数据
    	String mapId = keyArray[0];
    	boolean isFlag = false;//当前mapid是否属于当前活动
    	//判断两次：判断mapId是不是属于mapids；判断mapId是不是数据分key中mapId；属于其中一个则证明该数据属于当前活动
    	if (!isFlag) {
    		//redis中活动下mpaid
    		String str = JedisUtils.getStr(RedisKeyConstant.CAMPAIGN_MAPIDS + campaignId);
    		if (!StringUtils.isEmpty(str)) {
    			JsonObject idObj = new JsonObject();
    			JsonArray idArray = new JsonArray();
    			if (!StringUtils.isEmpty(str)) {
    				Gson gson = new Gson();
    				idObj = gson.fromJson(str, new JsonObject().getClass());
    				idArray = idObj.get("mapids").getAsJsonArray();
    				
    				for (int i = 0; i < idArray.size(); i++) {
    					if (mapId.equals(idArray.get(i).getAsString())) {
    						isFlag = true;
    						break;
    					}
    				}
    			}
    		}
    	}
    	//------------分key逻辑
//    	if (!isFlag) {
//    		//redis中活动分key分出的mapId
//    		String str = JedisUtils.getStr("part_parent_campaignId_" + campaignId);
//    		if (!StringUtils.isEmpty(str)) {
//    			String[] mapChilds = str.split(",");
//    			if (mapChilds != null && mapChilds.length > 0) {
//    				for (String mId : mapChilds) {
//    					if (mapId.equals(mId)) {
//    						isFlag = true;
//    						break;
//    					}
//    				}
//    			}
//    		}
//    	}
    	
    	//如果当前mapid属于当前活动才整合数据
    	if (isFlag) {
    		if (beans.isEmpty()) {
    			CreativeDataBean bean = new CreativeDataBean();
    			if (key.indexOf("@m") > 0) {// 展现
    				bean.setImpressionAmount(Long.parseLong(value));
    			} else if (key.indexOf("@c") > 0) {// 点击
    				bean.setClickAmount(Long.parseLong(value));
    			} else if (key.indexOf("@a") > 0) {// 到达
    				bean.setArrivalAmount(Long.parseLong(value));
    			} else if (key.indexOf("@w") > 0) {// 中标
    				bean.setWinAmount(Long.parseLong(value));
    			} else if (key.indexOf("@u") > 0) {// 独立访客数
    				bean.setUniqueAmount(Long.parseLong(value));
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
    				} else if (key.indexOf("@a") > 0) {// 到达
    					bean.setArrivalAmount(Long.parseLong(value) + (bean.getArrivalAmount()==null?0:bean.getArrivalAmount()));
    				} else if (key.indexOf("@w") > 0) {// 中标
    					bean.setWinAmount(Long.parseLong(value) + (bean.getWinAmount()==null?0:bean.getWinAmount()));
    				} else if (key.indexOf("@u") > 0) {// 独立访客数
    					bean.setUniqueAmount(Long.parseLong(value) + (bean.getUniqueAmount()==null?0:bean.getUniqueAmount()));
    				}
    				bean.setId(creativeId);
    			} else {
    				CreativeDataBean bean = new CreativeDataBean();
    				if (key.indexOf("@m") > 0) {// 展现
    					bean.setImpressionAmount(Long.parseLong(value) + (bean.getImpressionAmount()==null?0:bean.getImpressionAmount()));
    				} else if (key.indexOf("@c") > 0) {// 点击
    					bean.setClickAmount(Long.parseLong(value) + (bean.getClickAmount()==null?0:bean.getClickAmount()));
    				} else if (key.indexOf("@a") > 0) {// 到达
    					bean.setArrivalAmount(Long.parseLong(value) + (bean.getArrivalAmount()==null?0:bean.getArrivalAmount()));
    				} else if (key.indexOf("@w") > 0) {// 中标
    					bean.setWinAmount(Long.parseLong(value) + (bean.getWinAmount()==null?0:bean.getWinAmount()));
    				} else if (key.indexOf("@u") > 0) {// 独立访客数
    					bean.setUniqueAmount(Long.parseLong(value) + (bean.getUniqueAmount()==null?0:bean.getUniqueAmount()));
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
    	DecimalFormat format = new DecimalFormat("0.00000");
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
				
				
				if (bean.getWinAmount() == 0) {
					bean.setImpressionRate(0F);
				} else {
			        double percent = (double)bean.getImpressionAmount() / bean.getWinAmount();
			        Float result = Float.parseFloat(format.format(percent));
			        bean.setImpressionRate(result);
				}
				
				if (bean.getImpressionAmount() == 0) {
					bean.setClickRate(0F);
				} else {
					double percent = (double)bean.getClickAmount() / bean.getImpressionAmount();
					Float result = Float.parseFloat(format.format(percent));
					bean.setClickRate(result);
				}
				
				if (bean.getClickAmount() == 0) {
					bean.setArrivalRate(0F);
				} else {
					double percent = (double)bean.getArrivalAmount() / bean.getClickAmount();
					Float result = Float.parseFloat(format.format(percent));
					bean.setArrivalRate(result);
				}
				
//				if (!StringUtils.isEmpty(bean.getId())) {
//					CreativeMaterialModel model = creativeMaterialDao.selectByPrimaryKey(bean.getId());
//					if (model != null) {
//						//相同creativeId的素材对象creativeId都变成一个
//						String creativeId = model.getCreativeId();
//						if (!StringUtils.isEmpty(creativeId)) {
//							CreativeModel creativeModel = creativeDao.selectByPrimaryKey(creativeId);
//							bean.setId(creativeModel.getId());
//							bean.setName(creativeModel.getName());
//						}
//					}
//				}
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
					
					if (dataBean.getWinAmount() == 0) {
						dataBean.setImpressionRate(0F);
					} else {
				        double percent = (double)dataBean.getImpressionAmount() / dataBean.getWinAmount();
				        Float result = Float.parseFloat(format.format(percent));
				        dataBean.setImpressionRate(result);
					}
					
					if (dataBean.getImpressionAmount() == 0) {
						dataBean.setClickRate(0F);
					} else {
						double percent = (double)dataBean.getClickAmount() / dataBean.getImpressionAmount();
						Float result = Float.parseFloat(format.format(percent));
						dataBean.setClickRate(result);
					}
					
					if (dataBean.getClickAmount() == 0) {
						dataBean.setArrivalRate(0F);
					} else {
						double percent = (double)dataBean.getArrivalAmount() / dataBean.getClickAmount();
						Float result = Float.parseFloat(format.format(percent));
						dataBean.setArrivalRate(result);
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
