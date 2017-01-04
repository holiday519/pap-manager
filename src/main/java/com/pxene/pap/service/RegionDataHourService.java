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

import com.pxene.pap.domain.beans.RegionDataHourBean;
import com.pxene.pap.domain.models.RegionDataHourModel;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.RegionDataHourDao;
import com.pxene.pap.repository.custom.RegionDataHourStatsDao;

@Service
public class RegionDataHourService extends BaseService
{
    @Autowired
    private RegionDataHourDao regionDataHourDao;
    
    @Autowired
    private RegionDataHourStatsDao regionDataHourStatsDao;
    
    DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");    
    
    @Transactional
    public void saveRegionDataHour(RegionDataHourBean bean)
    {
        RegionDataHourModel model = modelMapper.map(bean, RegionDataHourModel.class);
        
        try
        {
            regionDataHourDao.insertSelective(model);
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
    public void updateRegionDataHour(Integer id, RegionDataHourBean bean)
    {
        // 操作前先查询一次数据库，判断指定的资源是否存在
        RegionDataHourModel dataInDB = regionDataHourDao.selectByPrimaryKey(id);
        if (dataInDB == null)
        {
            throw new ResourceNotFoundException();
        }
        
        // 将传输对象映射成数据库Model
        RegionDataHourModel model = modelMapper.map(bean, RegionDataHourModel.class);
        model.setId(id);
        
        try
        {
            regionDataHourDao.updateByPrimaryKey(model);
        }
        catch (DuplicateKeyException exception)
        {
            // 违反数据库唯一约束时，向上抛出自定义异常，交给全局异常处理器处理
            throw new DuplicateEntityException();
        }
        
        // 将DAO编辑后的新对象复制回传输对象中
        BeanUtils.copyProperties(regionDataHourDao.selectByPrimaryKey(id), bean);
    }

    
    @Transactional
    public List<Map<String, Object>> listRegionDataHour(String campaignId, long beginTime, long endTime)
    {
    	List<Map<String,Object>> list = regionDataHourStatsDao.selectByCampaignId(campaignId, new Date(beginTime), new Date(endTime));
    	
    	if (list == null || list.isEmpty()) {
    		throw new ResourceNotFoundException();
    	}
        
        return list;
    }
    
}
