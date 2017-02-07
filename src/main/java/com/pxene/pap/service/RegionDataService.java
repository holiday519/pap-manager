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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.JedisUtils;
import com.pxene.pap.constant.RedisKeyConstant;
import com.pxene.pap.domain.beans.RegionDataBean;
import com.pxene.pap.domain.models.RegionModel;
import com.pxene.pap.repository.basic.RegionDao;

@Service
public class RegionDataService extends BaseService
{
    @Autowired
    private RegionDao regionDao;
    
    DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");    
    
    /**
     * 查询地域小时数据（包括分key）
     * @param campaignId
     * @param beginTime
     * @param endTime
     * @return
     * @throws Exception
     */
    @Transactional
    public List<RegionDataBean> listRegionDataHours(String campaignId, long beginTime, long endTime) throws Exception {
    	List<RegionDataBean> result = new ArrayList<RegionDataBean>();
    	
    	String str = JedisUtils.getStr("part_parent_campaignId_" + campaignId);
    	long ms = endTime - beginTime;
    	int days =  (int) (ms /3600 /1000) + 1;
    	//如果redis中没有分key；说明活动没有绑定过规则，直接查询即可
    	if (!StringUtils.isEmpty(str)) {
    		String[] ids = str.split(",");
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				//查询出的数据整合时，需要判断未分key之前是否与分开之后数据的ID相同，如果相同加起来，不相同直接放到结果集中
				List<RegionDataBean> list = listRegionDataHour(id, beginTime, endTime);
				if (list == null || list.isEmpty()) {
					continue;
				}
				if (result.isEmpty()) {
					result.addAll(list);
				} else {
					List<RegionDataBean> newList = new ArrayList<RegionDataBean>();//用于存放result中、不包含的、 新id的bean 
					RegionDataBean reslutBean = null;
					RegionDataBean bean = null;
					//循环遍历分key返回结果，如果reslut中已有某条ID数据，就把两条数据整合到一起放到结果集中
					//如果没有，先放到临时list中，循环检查结束后，将新list拼接到结果集中
					for (int b = 0; b < list.size(); b++) {
						boolean flag = false;//reslut所含id是否与分key中id相同
						bean = list.get(b);
						//将各个“量”都格式化一下：空则变成0
						formatBeanAmount(bean);
						for (int r = 0; r < result.size(); r++) {
							reslutBean = result.get(r);
							//将各个“量”都格式化一下：空则变成0
							formatBeanAmount(reslutBean);
							if (reslutBean.getId().equals(bean.getId())) {
								reslutBean.setBidAmount(bean.getBidAmount() + reslutBean.getBidAmount());
								reslutBean.setWinAmount(bean.getWinAmount() + reslutBean.getWinAmount());
								reslutBean.setImpressionAmount(bean.getImpressionAmount() + reslutBean.getImpressionAmount());
								reslutBean.setClickAmount(bean.getClickAmount() + reslutBean.getClickAmount());
								reslutBean.setArrivalAmount(bean.getArrivalAmount() + reslutBean.getArrivalAmount());
								reslutBean.setUniqueAmount(bean.getUniqueAmount() + reslutBean.getUniqueAmount());
								flag = true;
								break;
							}
						}
						if (flag) {
							formatBeanRate(reslutBean, days);//格式化一下“率”
						}else{
							newList.add(bean);
						}
					}
					if (!newList.isEmpty()) {
						result.addAll(newList);
					}
				}
			}
    	}else {
    		result = listRegionDataHour(campaignId, beginTime, endTime);
    	}
    	return result;
    }
    
    /**
     * 查询活动地域小时数据
     * @param campaignId
     * @param beginTime
     * @param endTime
     * @return
     * @throws Exception
     */
    @Transactional
    public List<RegionDataBean> listRegionDataHour(String campaignId, long beginTime, long endTime) throws Exception
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
    	
    	long ms = endTime - beginTime;
    	int days =  (int) (ms /3600 /1000) + 1;
    	
    	List<RegionDataBean> beans = getListFromSource(campaignId, sourceMap);
    	formatLastList(beans , days);
    	return beans;
    }
    
    /**
     * 将小时文件拼成一个（类似天文件）合并至源Map中
     * @param sourceMap 源Map
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return
     */
	public Map<String, String> makeDayTableUseHour(Map<String, String> sourceMap, Date beginTime, Date endTime)	throws Exception {
		String[] hours = DateUtils.getHoursBetween(beginTime, endTime);
		for (int i = 0; i < hours.length; i++) {
			String hour = hours[i];
			Map<String, String> map = JedisUtils.hget("regionDataHour_" + new DateTime(beginTime).toString("yyyyMMdd") + hour);
			Set<String> hkeys = JedisUtils.hkeys("regionDataHour_" + new DateTime(beginTime).toString("yyyyMMdd") + hour);
			if (hkeys != null && !hkeys.isEmpty()) {
				for (String hkey : hkeys) {
					String hourStr = map.get(hkey);
					if (sourceMap.containsKey(hkey)) {
						String str = sourceMap.get(hkey);
						if (StringUtils.isEmpty(str)) {
							// 根据类型判断求和类型int？double？
							if (hkey.indexOf("@m@") > 0) {// 展现
								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
							} else if (hkey.indexOf("@c@") > 0) {// 点击
								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
							} else if (hkey.indexOf("@a@") > 0) {// 到达
								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
							} else if (hkey.indexOf("@w@") > 0) {// 中标
								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
							} else if (hkey.indexOf("@s@") > 0) {// 平均访问时间
								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
							} else if (hkey.indexOf("@u@") > 0) {// 独立访客数
								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
							} else if (hkey.indexOf("@j@") > 0) {// 二跳数
								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
							} else if (hkey.indexOf("@b@") > 0) {// 参与竞价量
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
    public Map<String, String> margeDayTables(Map<String, String> sourceMap, String [] days) throws Exception {
    	if (days!=null && days.length > 0) {
    		for (int i = 0; i < days.length; i++) {
    			String day = days[i];
    			Map<String, String> map = JedisUtils.hget("regionDataDay_" + day);
    			Set<String> hkeys = JedisUtils.hkeys("regionDataDay_" + day);
    			if (hkeys != null && !hkeys.isEmpty()) {
    				for (String hkey : hkeys) {
    					String hourStr = map.get(hkey);
    					if (sourceMap.containsKey(hkey)) {
    						String str = sourceMap.get(hkey);
    						if (StringUtils.isEmpty(str)) {
    							// 根据类型判断求和类型int？double？
    							if (hkey.indexOf("@m@") > 0) {// 展现
    								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
    							} else if (hkey.indexOf("@c@") > 0) {// 点击
    								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
    							} else if (hkey.indexOf("@a@") > 0) {// 到达
    								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
    							} else if (hkey.indexOf("@w@") > 0) {// 中标
    								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
    							} else if (hkey.indexOf("@s@") > 0) {// 平均访问时间
    								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
    							} else if (hkey.indexOf("@u@") > 0) {// 独立访客数
    								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
    							} else if (hkey.indexOf("@j@") > 0) {// 二跳数
    								sourceMap.put(hkey, String.valueOf(Integer.parseInt(hourStr) + Integer.parseInt(str)));
    							} else if (hkey.indexOf("@b@") > 0) {// 参与竞价量
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
    public List<RegionDataBean> getListFromSource(String campaignId, Map<String, String> sourceMap) throws Exception {
    	List<RegionDataBean> beans = new ArrayList<RegionDataBean>();
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
    public List<RegionDataBean> takeDataToList(String campaignId, List<RegionDataBean> beans, String key, String value) throws Exception {
    	String[] keyArray = key.split("@");
    	String regionId = keyArray[2];
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
    	if (!isFlag) {
    		//redis中活动分key分出的mapId
    		String str = JedisUtils.getStr("part_parent_campaignId_" + campaignId);
    		if (!StringUtils.isEmpty(str)) {
    			String[] mapChilds = str.split(",");
    			if (mapChilds != null && mapChilds.length > 0) {
    				for (String mId : mapChilds) {
    					if (mapId.equals(mId)) {
    						isFlag = true;
    						break;
    					}
    				}
    			}
    		}
    	}
		
    	//如果当前mapid属于当前活动才整合数据
    	if (isFlag) {
    		if (beans.isEmpty()) {
    			RegionDataBean bean = new RegionDataBean();
    			if (key.indexOf("@m@") > 0) {// 展现
    				bean.setImpressionAmount(Long.parseLong(value));
    			} else if (key.indexOf("@c@") > 0) {// 点击
    				bean.setClickAmount(Long.parseLong(value));
    			} else if (key.indexOf("@a@") > 0) {// 到达
    				bean.setArrivalAmount(Long.parseLong(value));
    			} else if (key.indexOf("@w@") > 0) {// 中标
    				bean.setWinAmount(Long.parseLong(value));
    			} else if (key.indexOf("@u@") > 0) {// 独立访客数
    				bean.setUniqueAmount(Long.parseLong(value));
    			} else if (key.indexOf("@j@") > 0) {// 二跳数
    				bean.setJumpAmount(Long.parseLong(value));
    			} else if (key.indexOf("@b@") > 0) {// 参与竞价量
    				bean.setBidAmount(Long.parseLong(value));
    			}
    			bean.setId(regionId);
    			beans.add(bean);
    		} else {
    			boolean flag = false;
    			int index = 0;
    			for (int i=0;i < beans.size();i++) {
    				RegionDataBean bean = beans.get(i);
    				if (bean.getId().equals(regionId)) {
    					index = i;
    					flag = true;
    					break;
    				}
    			}
    			if (flag) {
    				RegionDataBean bean = beans.get(index);
    				if (key.indexOf("@m@") > 0) {// 展现
    					bean.setImpressionAmount(Long.parseLong(value) + (bean.getImpressionAmount()==null?0:bean.getImpressionAmount()));
    				} else if (key.indexOf("@c@") > 0) {// 点击
    					bean.setClickAmount(Long.parseLong(value) + (bean.getClickAmount()==null?0:bean.getClickAmount()));
    				} else if (key.indexOf("@a@") > 0) {// 到达
    					bean.setArrivalAmount(Long.parseLong(value) + (bean.getArrivalAmount()==null?0:bean.getArrivalAmount()));
    				} else if (key.indexOf("@w@") > 0) {// 中标
    					bean.setWinAmount(Long.parseLong(value) + (bean.getWinAmount()==null?0:bean.getWinAmount()));
    				} else if (key.indexOf("@u@") > 0) {// 独立访客数
    					bean.setUniqueAmount(Long.parseLong(value) + (bean.getUniqueAmount()==null?0:bean.getUniqueAmount()));
    				} else if (key.indexOf("@j@") > 0) {// 二跳数
    					bean.setJumpAmount(Long.parseLong(value) + (bean.getJumpAmount()==null?0:bean.getJumpAmount()));
    				} else if (key.indexOf("@b@") > 0) {// 参与竞价量
    					bean.setBidAmount(Long.parseLong(value) + (bean.getBidAmount()==null?0:bean.getBidAmount()));
    				}
    				bean.setId(regionId);
    			} else {
    				RegionDataBean bean = new RegionDataBean();
    				if (key.indexOf("@m@") > 0) {// 展现
    					bean.setImpressionAmount(Long.parseLong(value) + (bean.getImpressionAmount()==null?0:bean.getImpressionAmount()));
    				} else if (key.indexOf("@c@") > 0) {// 点击
    					bean.setClickAmount(Long.parseLong(value) + (bean.getClickAmount()==null?0:bean.getClickAmount()));
    				} else if (key.indexOf("@a@") > 0) {// 到达
    					bean.setArrivalAmount(Long.parseLong(value) + (bean.getArrivalAmount()==null?0:bean.getArrivalAmount()));
    				} else if (key.indexOf("@w@") > 0) {// 中标
    					bean.setWinAmount(Long.parseLong(value) + (bean.getWinAmount()==null?0:bean.getWinAmount()));
    				} else if (key.indexOf("@u@") > 0) {// 独立访客数
    					bean.setUniqueAmount(Long.parseLong(value) + (bean.getUniqueAmount()==null?0:bean.getUniqueAmount()));
    				} else if (key.indexOf("@j@") > 0) {// 二跳数
    					bean.setJumpAmount(Long.parseLong(value) + (bean.getJumpAmount()==null?0:bean.getJumpAmount()));
    				} else if (key.indexOf("@b@") > 0) {// 参与竞价量
    					bean.setBidAmount(Long.parseLong(value) + (bean.getBidAmount()==null?0:bean.getBidAmount()));
    				}
    				bean.setId(regionId);
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
     * @throws Exception 
     */
    public List<RegionDataBean> formatLastList(List<RegionDataBean> beans, int days) throws Exception {
    	if (beans != null && beans.size() > 0) {
    		for (RegionDataBean bean : beans) {
				
    			formatBeanAmount(bean);//计算“量”
				formatBeanRate(bean, days);//计算“率”
				
				if (!StringUtils.isEmpty(bean.getId())) {
					String regionId = bean.getId().substring(4,bean.getId().length());
					RegionModel model = regionDao.selectByPrimaryKey(regionId);
					if (model != null) {
						bean.setName(model.getName());
					}
				}
    		}
    	}
    	return beans;
    }
    
    /**
     * 格式化各种“量”
     * @param bean
     */
    public void formatBeanAmount(RegionDataBean bean) throws Exception {
    	if (bean == null) {
    		return;
    	}
    	if (bean.getBidAmount() == null) {
			bean.setBidAmount(0L);
		}
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
    }
    /**
     * 格式化各种“率”
     * @param bean
     */
    public void formatBeanRate(RegionDataBean bean, int days) throws Exception {
    	if (bean == null) {
    		return;
    	}
		DecimalFormat format = new DecimalFormat("0.00000");
		if (bean.getBidAmount() == 0) {
			bean.setWinRate(0F);
		} else {
	        double percent = (double)bean.getWinAmount() / bean.getBidAmount();
	        Float result = Float.parseFloat(format.format(percent));
	        bean.setWinRate(result);
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
		if (bean.getJumpAmount() == 0) {
			bean.setJumpRate(0F);
		} else {
			double percent = (double)bean.getJumpAmount() / bean.getArrivalAmount();
			Float result = Float.parseFloat(format.format(percent));
			bean.setJumpRate(result);
		}
    }
}
