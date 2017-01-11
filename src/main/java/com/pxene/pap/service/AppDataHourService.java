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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.JedisUtils;
import com.pxene.pap.domain.beans.AppDataHourBean;
import com.pxene.pap.domain.beans.DayAndHourDataBean;
import com.pxene.pap.domain.models.AppDataHourModel;
import com.pxene.pap.domain.models.AppModel;
import com.pxene.pap.domain.models.AppModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AppDao;
import com.pxene.pap.repository.basic.AppDataHourDao;
import com.pxene.pap.repository.custom.AppDataHourStatsDao;

@Service
public class AppDataHourService extends BaseService
{
    @Autowired
    private AppDataHourDao appDataHourDao;
    
    @Autowired
    private AppDao appDao;
    
    @Autowired
    private AppDataHourStatsDao appDataHourStatsDao;
    
    @Transactional
    public void saveAppDataHour(AppDataHourBean appDataDayBean)
    {
        AppDataHourModel appDataDayModel = modelMapper.map(appDataDayBean, AppDataHourModel.class);
        
        try
        {
            appDataHourDao.insertSelective(appDataDayModel);
        }
        catch (DuplicateKeyException exception)
        {
            // 违反数据库唯一约束时，向上抛出自定义异常，交给全局异常处理器处理
            throw new DuplicateEntityException();
        }
        
        // 将DAO创建的新对象复制回传输对象中
        BeanUtils.copyProperties(appDataDayModel, appDataDayBean);
        
    }

    @Transactional
    public void updateAppDataHour(Integer id, AppDataHourBean appDataDayBean)
    {
        // 操作前先查询一次数据库，判断指定的资源是否存在
        AppDataHourModel appDataDayInDB = appDataHourDao.selectByPrimaryKey(id);
        if (appDataDayInDB == null)
        {
            throw new ResourceNotFoundException();
        }
        
        // 将传输对象映射成数据库Model
        AppDataHourModel appDataDayModel = modelMapper.map(appDataDayBean, AppDataHourModel.class);
        appDataDayModel.setId(id);
        
        try
        {
            appDataHourDao.updateByPrimaryKey(appDataDayModel);
        }
        catch (DuplicateKeyException exception)
        {
            // 违反数据库唯一约束时，向上抛出自定义异常，交给全局异常处理器处理
            throw new DuplicateEntityException();
        }
        
        // 将DAO编辑后的新对象复制回传输对象中
        BeanUtils.copyProperties(appDataHourDao.selectByPrimaryKey(id), appDataDayBean);
    }

    
    @Transactional
    public List<DayAndHourDataBean> listAppDataHour(String campaignId, long beginTime, long endTime) throws Exception
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
				String[] days = DateUtils.getDaysArrayBetweenTwoDate(begin.toDate(), end.toDate());
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
				sourceMap = margeDayTables(sourceMap, days);
			}
    	}
    	
    	List<DayAndHourDataBean> beans = getListFromSource(sourceMap);
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
	public Map<String, String> makeDayTableUseHour(Map<String, String> sourceMap, Date beginTime, Date endTime)	throws Exception {
		String[] hours = DateUtils.getHourArrayBetweenTwoDate(beginTime, endTime);
		for (int i = 0; i < hours.length; i++) {
			String hour = hours[i];
			Map<String, String> map = JedisUtils.hget("appDataHour_" + new DateTime(beginTime).toString("yyyyMMdd") + hour);
			Set<String> hkeys = JedisUtils.hkeys("appDataHour_" + new DateTime(beginTime).toString("yyyyMMdd") + hour);
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
    			Map<String, String> map = JedisUtils.hget("appDataDay_" + day);
    			Set<String> hkeys = JedisUtils.hkeys("appDataDay_" + day);
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
    public List<DayAndHourDataBean> getListFromSource(Map<String, String> sourceMap) throws Exception {
    	List<DayAndHourDataBean> beans = new ArrayList<DayAndHourDataBean>();
    	for (String key : sourceMap.keySet()) {
    		if (!StringUtils.isEmpty(key)) {
    			String value = sourceMap.get(key);
    			beans = takeDataToList(beans, key, value);
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
    public List<DayAndHourDataBean> takeDataToList(List<DayAndHourDataBean> beans, String key, String value) throws Exception {
    	String[] keyArray = key.split("@");
    	String appId = keyArray[2];
    	String adxId = keyArray[3];
    	
    	if (beans.isEmpty()) {
    		DayAndHourDataBean bean = new DayAndHourDataBean();
    		if (key.indexOf("@m@") > 0) {// 展现
    			bean.setImpressionAmount(Long.parseLong(value));
        	} else if (key.indexOf("@c@") > 0) {// 点击
        		bean.setClickAmount(Long.parseLong(value));
        	}
    		bean.setAppId(appId);
    		bean.setAdxId(adxId);
    		beans.add(bean);
    	} else {
    		boolean flag = false;
    		int index = 0;
    		for (int i=0;i < beans.size();i++) {
    			DayAndHourDataBean bean = beans.get(i);
    			if (bean.getAppId().equals(appId) && bean.getAdxId().endsWith(adxId)) {
    				index = i;
    				flag = true;
    				break;
    			}
    		}
    		if (flag) {
    			DayAndHourDataBean bean = beans.get(index);
    			if (key.indexOf("@m@") > 0) {// 展现
        			bean.setImpressionAmount(Long.parseLong(value) + (bean.getImpressionAmount()==null?0:bean.getImpressionAmount()));
            	} else if (key.indexOf("@c@") > 0) {// 点击
            		bean.setClickAmount(Long.parseLong(value) + (bean.getClickAmount()==null?0:bean.getClickAmount()));
            	}
        		bean.setAppId(appId);
        		bean.setAdxId(adxId);
    		} else {
    			DayAndHourDataBean bean = new DayAndHourDataBean();
        		if (key.indexOf("@m@") > 0) {// 展现
        			bean.setImpressionAmount(Long.parseLong(value) + (bean.getImpressionAmount()==null?0:bean.getImpressionAmount()));
            	} else if (key.indexOf("@c@") > 0) {// 点击
            		bean.setClickAmount(Long.parseLong(value) + (bean.getClickAmount()==null?0:bean.getClickAmount()));
            	}
        		bean.setAppId(appId);
        		bean.setAdxId(adxId);
        		beans.add(bean);
    		}
    	}
		return beans;
    }
	
    /**
     * 格式化list中各个返回参数
     * @param beans
     * @return
     */
    public List<DayAndHourDataBean> formatLastList(List<DayAndHourDataBean> beans) {
    	if (beans != null && beans.size() > 0) {
    		for (DayAndHourDataBean bean : beans) {
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
				bean.setJumpAmount(null);
				
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
				
				if (!StringUtils.isEmpty(bean.getAdxId()) && !StringUtils.isEmpty(bean.getAppId())) {
					AppModelExample example = new AppModelExample();
					example.createCriteria().andAdxIdEqualTo(bean.getAdxId()).andAppIdEqualTo(bean.getAppId());
					List<AppModel> apps = appDao.selectByExample(example);
					if (apps != null && apps.size() > 0) {
						for (AppModel app : apps) {
							bean.setRealAppId(bean.getAppId());
							bean.setAppId(app.getId());
							bean.setAppName(app.getAppName());
							bean.setAppType(app.getAppType());
							bean.setPkgName(app.getPkgName());
							bean.setAdxId(app.getAdxId());
						}
					}
				}
    		}
    	}
    	return beans;
    }
}
