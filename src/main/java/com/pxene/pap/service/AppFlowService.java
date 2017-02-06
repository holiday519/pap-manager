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
import com.pxene.pap.domain.beans.AppFlowBean;
import com.pxene.pap.domain.models.AppModel;
import com.pxene.pap.domain.models.AppModelExample;
import com.pxene.pap.repository.basic.AppDao;

@Service
public class AppFlowService extends BaseService
{
	@Autowired
	private AppDao appDao;
	
	private static final String HOUR_PREFIX = "appFlowHour_";
	private static final String DAY_PREFIX = "appFlowDay_";
	
    @Transactional
    public List<AppFlowBean> listAppFlows(Date beginTime, Date endTime, int limitNum)
    {
    	List<AppFlowBean> beans = new ArrayList<AppFlowBean>();
    	// 查出所有的app
    	List<AppModel> models = appDao.selectByExample(new AppModelExample());
    	String[] dayHours = DateUtils.getDayHoursBetween(beginTime, endTime);
    	for (AppModel model : models) {
    		String key = model.getAdxId() + "_" + model.getAppId();
    		long total = 0;
    		for (String dayHour : dayHours) {
    			Map<String, String> apps = JedisUtils.hget(HOUR_PREFIX + dayHour);
    			if (apps.containsKey(key)) {
    				total += Long.parseLong(apps.get(key));
    			}
    		}
    		AppFlowBean bean = modelMapper.map(model, AppFlowBean.class);
    		bean.setRequestAmount(total);
    		beans.add(bean);
    	}
    	
    	Collections.sort(beans, new Comparator<AppFlowBean>() {  
            //升序排序  
            public int compare(AppFlowBean bean1, AppFlowBean bean2) {  
                return bean2.getRequestAmount().compareTo(bean1.getRequestAmount());  
            }  
        });
    	
        return beans.size() > limitNum ? beans.subList(0, limitNum) : beans;
    }
    
    /*public static void main(String[] args) {
    	
		String[] hours = DateUtils.getDayHoursBetween(new Date(1484020800000L), new Date(1484038800000L));
		for (String hour : hours) {
			System.out.println(hour);
		}
	}*/
    
}
