package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.JedisUtils;
import com.pxene.pap.domain.beans.RegionFlowBean;
import com.pxene.pap.domain.models.RegionModel;
import com.pxene.pap.domain.models.RegionModelExample;
import com.pxene.pap.repository.basic.RegionDao;

@Service
public class RegionFlowService extends BaseService
{
	@Autowired
	private RegionDao regionDao;
	
	private static final String HOUR_PREFIX = "regionFlowHour_";
	private static final String DAY_PREFIX = "regionFlowDay_";
    
    @Transactional
    public List<RegionFlowBean> listRegionFlows(Date beginTime, Date endTime, int limitNum)
    {
    	List<RegionFlowBean> beans = new ArrayList<RegionFlowBean>();
        
    	List<RegionModel> models = regionDao.selectByExample(new RegionModelExample());
    	String[] dayHours = DateUtils.getDayHoursBetween(beginTime, endTime);
    	for (RegionModel model : models) {
    		String key = model.getId();
    		long total = 0;
    		for (String dayHour : dayHours) {
    			Map<String, String> apps = JedisUtils.hget(HOUR_PREFIX + dayHour);
    			if (apps.containsKey(key)) {
    				total += Long.parseLong(apps.get(key));
    			}
    		}
    		RegionFlowBean bean = modelMapper.map(model, RegionFlowBean.class);
    		bean.setRequestAmount(total);
    		beans.add(bean);
    	}
    	
    	Collections.sort(beans, new Comparator<RegionFlowBean>() {  
            //升序排序  
            public int compare(RegionFlowBean bean1, RegionFlowBean bean2) {  
                return bean2.getRequestAmount().compareTo(bean1.getRequestAmount());  
            }  
        });
    	
        return beans.size() > limitNum ? beans.subList(0, limitNum) : beans;
    }
    
}
