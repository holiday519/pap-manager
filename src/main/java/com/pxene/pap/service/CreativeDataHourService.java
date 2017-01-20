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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.JedisUtils;
import com.pxene.pap.domain.beans.CreativeDataHourBean;
import com.pxene.pap.domain.beans.DayAndHourDataBean;
import com.pxene.pap.domain.models.CreativeDataHourModel;
import com.pxene.pap.domain.models.CreativeMaterialModel;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.CreativeDataHourDao;
import com.pxene.pap.repository.basic.CreativeMaterialDao;
import com.pxene.pap.repository.custom.CreativeDataHourStatsDao;

@Service
public class CreativeDataHourService extends BaseService
{
    @Autowired
    private CreativeDataHourDao creativeDataHourDao;
    
    @Autowired
    private CreativeDataHourStatsDao creativeDataHourStatsDao;
    
    @Autowired
    private CreativeDao creativeDao;
    
    @Autowired
    private CreativeMaterialDao creativeMaterialDao;
    
    DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");    
    
    @Transactional
    public void saveCreativeDataHour(CreativeDataHourBean bean)
    {
        CreativeDataHourModel model = modelMapper.map(bean, CreativeDataHourModel.class);
        
        try
        {
            creativeDataHourDao.insertSelective(model);
        }
        catch (DuplicateKeyException exception)
        {
            // 违反数据库唯一约束时，向上抛出自定义异常，交给全局异常处理器处理
            throw new DuplicateEntityException();
        }
        
        // 将DAO创建的新对象复制回传输对象中
        BeanUtils.copyProperties(model, bean);
        
    }

    @Transactional
    public void updateCreativeDataHour(Integer id, CreativeDataHourBean bean)
    {
        // 操作前先查询一次数据库，判断指定的资源是否存在
        CreativeDataHourModel dataInDB = creativeDataHourDao.selectByPrimaryKey(id);
        if (dataInDB == null)
        {
            throw new ResourceNotFoundException();
        }
        
        // 将传输对象映射成数据库Model
        CreativeDataHourModel model = modelMapper.map(bean, CreativeDataHourModel.class);
        model.setId(id);
        
        try
        {
            creativeDataHourDao.updateByPrimaryKey(model);
        }
        catch (DuplicateKeyException exception)
        {
            // 违反数据库唯一约束时，向上抛出自定义异常，交给全局异常处理器处理
            throw new DuplicateEntityException();
        }
        
        // 将DAO编辑后的新对象复制回传输对象中
        BeanUtils.copyProperties(creativeDataHourDao.selectByPrimaryKey(id), bean);
    }

    
    @Transactional
    public List<DayAndHourDataBean> listCreativeDataHour(String campaignId, long beginTime, long endTime) throws Exception
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
			sourceMap = margeDayTables(sourceMap, daysList.toArray(new String[daysList.size()]));
		}
    	
    	List<DayAndHourDataBean> beans = getListFromSource(sourceMap);
    	formatLastList(beans);
    	for (DayAndHourDataBean bean : beans) {
    		bean.setCampaignId(campaignId);
    	}
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
    	String creativeId = keyArray[0];
    	
    	if (beans.isEmpty()) {
    		DayAndHourDataBean bean = new DayAndHourDataBean();
    		if (key.indexOf("@m") > 0) {// 展现
    			bean.setImpressionAmount(Long.parseLong(value));
        	} else if (key.indexOf("@c") > 0) {// 点击
        		bean.setClickAmount(Long.parseLong(value));
        	} else if (key.indexOf("@a@") > 0) {// 到达
				bean.setArrivalAmount(Long.parseLong(value));
			} else if (key.indexOf("@w@") > 0) {// 中标
				bean.setWinAmount(Long.parseLong(value));
			} else if (key.indexOf("@s@") > 0) {// 平均访问时间
				bean.setResidentTime(Integer.parseInt(value));
			} else if (key.indexOf("@u@") > 0) {// 独立访客数
				bean.setUniqueAmount(Long.parseLong(value));
			} else if (key.indexOf("@j@") > 0) {// 二跳数
				bean.setJumpAmount(Long.parseLong(value));
			} else if (key.indexOf("@b@") > 0) {// 参与竞价量
				bean.setBidAmount(Long.parseLong(value));
			}
    		bean.setCreativeId(creativeId);
    		beans.add(bean);
    	} else {
    		boolean flag = false;
    		int index = 0;
    		for (int i=0;i < beans.size();i++) {
    			DayAndHourDataBean bean = beans.get(i);
    			if (bean.getCreativeId().equals(creativeId)) {
    				index = i;
    				flag = true;
    				break;
    			}
    		}
    		if (flag) {
    			DayAndHourDataBean bean = beans.get(index);
    			if (key.indexOf("@m") > 0) {// 展现
        			bean.setImpressionAmount(Long.parseLong(value) + (bean.getImpressionAmount()==null?0:bean.getImpressionAmount()));
            	} else if (key.indexOf("@c") > 0) {// 点击
            		bean.setClickAmount(Long.parseLong(value) + (bean.getClickAmount()==null?0:bean.getClickAmount()));
            	} else if (key.indexOf("@a@") > 0) {// 到达
    				bean.setArrivalAmount(Long.parseLong(value) + (bean.getArrivalAmount()==null?0:bean.getArrivalAmount()));
    			} else if (key.indexOf("@w@") > 0) {// 中标
    				bean.setWinAmount(Long.parseLong(value) + (bean.getWinAmount()==null?0:bean.getWinAmount()));
    			} else if (key.indexOf("@s@") > 0) {// 平均访问时间
    				bean.setResidentTime(Integer.parseInt(value) + (bean.getResidentTime()==null?0:bean.getResidentTime()));
    			} else if (key.indexOf("@u@") > 0) {// 独立访客数
    				bean.setUniqueAmount(Long.parseLong(value) + (bean.getUniqueAmount()==null?0:bean.getUniqueAmount()));
    			} else if (key.indexOf("@j@") > 0) {// 二跳数
    				bean.setJumpAmount(Long.parseLong(value) + (bean.getJumpAmount()==null?0:bean.getJumpAmount()));
    			} else if (key.indexOf("@b@") > 0) {// 参与竞价量
    				bean.setBidAmount(Long.parseLong(value) + (bean.getBidAmount()==null?0:bean.getBidAmount()));
    			}
    			bean.setCreativeId(creativeId);
    		} else {
    			DayAndHourDataBean bean = new DayAndHourDataBean();
        		if (key.indexOf("@m") > 0) {// 展现
        			bean.setImpressionAmount(Long.parseLong(value) + (bean.getImpressionAmount()==null?0:bean.getImpressionAmount()));
            	} else if (key.indexOf("@c") > 0) {// 点击
            		bean.setClickAmount(Long.parseLong(value) + (bean.getClickAmount()==null?0:bean.getClickAmount()));
            	} else if (key.indexOf("@a@") > 0) {// 到达
    				bean.setArrivalAmount(Long.parseLong(value) + (bean.getArrivalAmount()==null?0:bean.getArrivalAmount()));
    			} else if (key.indexOf("@w@") > 0) {// 中标
    				bean.setWinAmount(Long.parseLong(value) + (bean.getWinAmount()==null?0:bean.getWinAmount()));
    			} else if (key.indexOf("@s@") > 0) {// 平均访问时间
    				bean.setResidentTime(Integer.parseInt(value) + (bean.getResidentTime()==null?0:bean.getResidentTime()));
    			} else if (key.indexOf("@u@") > 0) {// 独立访客数
    				bean.setUniqueAmount(Long.parseLong(value) + (bean.getUniqueAmount()==null?0:bean.getUniqueAmount()));
    			} else if (key.indexOf("@j@") > 0) {// 二跳数
    				bean.setJumpAmount(Long.parseLong(value) + (bean.getJumpAmount()==null?0:bean.getJumpAmount()));
    			} else if (key.indexOf("@b@") > 0) {// 参与竞价量
    				bean.setBidAmount(Long.parseLong(value) + (bean.getBidAmount()==null?0:bean.getBidAmount()));
    			}
        		bean.setCreativeId(creativeId);
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
    	DecimalFormat format = new DecimalFormat("0.00000");
    	if (beans != null && beans.size() > 0) {
    		for (DayAndHourDataBean bean : beans) {
    			bean.setBidAmount(null);
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
				
				if (!StringUtils.isEmpty(bean.getCreativeId())) {
					CreativeMaterialModel model = creativeMaterialDao.selectByPrimaryKey(bean.getCreativeId());
					if (model != null) {
						//相同creativeId的素材对象creativeId都变成一个
						String creativeId = model.getCreativeId();
						if (!StringUtils.isEmpty(creativeId)) {
							CreativeModel creativeModel = creativeDao.selectByPrimaryKey(creativeId);
							bean.setCreativeId(creativeModel.getId());
							bean.setCreativeName(creativeModel.getName());
						}
					}
				}
    		}
    	}
    	//将所有不重复的creaticeId放入List————根据List就知道最后条数
    	List<String> creativeIds = new ArrayList<String>();
    	for (DayAndHourDataBean bean : beans) {
    		if (creativeIds.contains(bean.getCreativeId())) {
    			creativeIds.add(bean.getCreativeId());	
    		}
    	}
    	//创建一个新的List存放结果用
    	List<DayAndHourDataBean> newBeans = new ArrayList<DayAndHourDataBean>();
    	if (beans !=null && !beans.isEmpty()) {
			for (DayAndHourDataBean bean : beans) {
				//如果list里没有CreativeId，先添加进去
				if(indexOfBean(newBeans, bean.getCreativeId()) == null){
					newBeans.add(bean);
				} else {
					//如果有，就将相同creativeId的数据合并成一条
					int index = indexOfBean(newBeans, bean.getCreativeId());
					DayAndHourDataBean dataBean = newBeans.get(index);
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
    public Integer indexOfBean(List<DayAndHourDataBean> newBeans, String id) {
    	Integer index = null;
    	for (int i=0;i<newBeans.size();i++) {
    		if (id.equals(newBeans.get(i).getCreativeId())) {
    			index = i;
    			break;
    		}
    	}
    	return index;
    }
    
}
