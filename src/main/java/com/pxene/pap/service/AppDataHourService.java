package com.pxene.pap.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.beans.AppDataHourBean;
import com.pxene.pap.domain.model.AppDataHourModel;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AppDataHourDao;
import com.pxene.pap.repository.custom.AppDataHourStatsDao;

@Service
public class AppDataHourService extends BaseService
{
    @Autowired
    private AppDataHourDao appDataHourDao;
    @Autowired
    private AppDataHourStatsDao appDataHourStatsDao;
    
    DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");    
    
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
    public List<Map<String, Object>> listAppDataHour(String campaignId, long beginTime, long endTime)
    {
    	List<Map<String, Object>> results = appDataHourStatsDao.selectByCampaignId(campaignId, new Date(beginTime), new Date(endTime));
    	if (results == null || results.size() <= 0) {
    		throw new ResourceNotFoundException();
    	} else {
    		return results;
    	}
    }
    
}
