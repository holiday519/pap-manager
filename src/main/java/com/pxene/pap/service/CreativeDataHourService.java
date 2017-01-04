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

import com.pxene.pap.domain.beans.CreativeDataHourBean;
import com.pxene.pap.domain.models.CreativeDataHourModel;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.CreativeDataHourDao;
import com.pxene.pap.repository.custom.CreativeDataHourStatsDao;

@Service
public class CreativeDataHourService extends BaseService
{
    @Autowired
    private CreativeDataHourDao creativeDataHourDao;
    
    @Autowired
    private CreativeDataHourStatsDao creativeDataHourStatsDao;
    
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
    public List<Map<String, Object>> listCreativeDataHour(String campaignId, long beginTime, long endTime)
    {
		List<Map<String,Object>> list = creativeDataHourStatsDao.selectByCampaignId(campaignId, new Date(beginTime), new Date(endTime));
		    	
    	if (list == null || list.isEmpty()) {
    		throw new ResourceNotFoundException();
    	}
        
        return list;
    }
    
}
