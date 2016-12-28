package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.beans.RegionDataHourBean;
import com.pxene.pap.domain.beans.RegionDataHourViewBean;
import com.pxene.pap.domain.model.basic.RegionDataHourModel;
import com.pxene.pap.domain.model.basic.view.RegionDataHourViewModel;
import com.pxene.pap.domain.model.basic.view.RegionDataHourViewModelExample;
import com.pxene.pap.domain.model.basic.view.RegionDataHourViewModelExample.Criteria;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.RegionDataHourDao;
import com.pxene.pap.repository.basic.view.RegionDataHourViewDao;

@Service
public class RegionDataHourService extends BaseService
{
    @Autowired
    private RegionDataHourDao regionDataHourDao;
    
    @Autowired
    private RegionDataHourViewDao regionDataHourViewDao;
    
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
    public List<RegionDataHourViewBean> listRegionDataHour(String campaignId, long beginTime, long endTime)
    {
        RegionDataHourViewModelExample example = new RegionDataHourViewModelExample();
        Criteria criteria = example.createCriteria();
        criteria.andCampaignIdEqualTo(campaignId);
        criteria.andDatetimeBetween(new Date(beginTime), new Date(endTime));
        
        List<RegionDataHourViewModel> models = regionDataHourViewDao.selectByExample(example);
        List<RegionDataHourViewBean> list = new ArrayList<RegionDataHourViewBean>();
        
        if (models == null || models.size() <= 0)
        {
            throw new ResourceNotFoundException();
        }
        else
        {
            // 遍历数据库中查询到的全部结果，逐个将DAO创建的新对象复制回传输对象中
            for (RegionDataHourViewModel model : models)
            {
                list.add(modelMapper.map(model, RegionDataHourViewBean.class));
            }
        }
        
        return list;
    }
    
}