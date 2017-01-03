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

import com.pxene.pap.domain.beans.CreativeDataHourBean;
import com.pxene.pap.domain.model.basic.CreativeDataHourModel;
import com.pxene.pap.domain.model.basic.CreativeDataHourModelExample;
import com.pxene.pap.domain.model.basic.CreativeDataHourModelExample.Criteria;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.CreativeDataHourDao;

@Service
public class CreativeDataHourService extends BaseService
{
    @Autowired
    private CreativeDataHourDao creativeDataHourDao;
    
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
    public List<CreativeDataHourBean> listCreativeDataHour(String campaignId, long beginTime, long endTime)
    {
        CreativeDataHourModelExample example = new CreativeDataHourModelExample();
        Criteria criteria = example.createCriteria();
        criteria.andCampaignIdEqualTo(campaignId);
        criteria.andDatetimeBetween(new Date(beginTime), new Date(endTime));
        
        List<CreativeDataHourModel> models = creativeDataHourDao.selectByExample(example);
        List<CreativeDataHourBean> list = new ArrayList<CreativeDataHourBean>();
        
        if (models == null || models.size() <= 0)
        {
            throw new ResourceNotFoundException();
        }
        else
        {
            // 遍历数据库中查询到的全部结果，逐个将DAO创建的新对象复制回传输对象中
            for (CreativeDataHourModel model : models)
            {
                list.add(modelMapper.map(model, CreativeDataHourBean.class));
            }
        }
        
        return list;
    }
    
}
