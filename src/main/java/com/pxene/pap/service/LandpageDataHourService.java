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

import com.pxene.pap.domain.beans.LandpageDataHourBean;
import com.pxene.pap.domain.model.basic.LandpageDataHourModel;
import com.pxene.pap.domain.model.basic.LandpageDataHourModelExample;
import com.pxene.pap.domain.model.basic.LandpageDataHourModelExample.Criteria;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.LandpageDataHourDao;

@Service
public class LandpageDataHourService extends BaseService
{
    @Autowired
    private LandpageDataHourDao landpageDataHourDao;
    
    DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");    
    
    @Transactional
    public void saveLandpageDataHour(LandpageDataHourBean landpageDataHourBean)
    {
        LandpageDataHourModel landpageDataHourModel = modelMapper.map(landpageDataHourBean, LandpageDataHourModel.class);
        
        try
        {
            landpageDataHourDao.insertSelective(landpageDataHourModel);
        }
        catch (DuplicateKeyException exception)
        {
            // 违反数据库唯一约束时，向上抛出自定义异常，交给全局异常处理器处理
            throw new DuplicateEntityException();
        }
        
        // 将DAO创建的新对象复制回传输对象中
        BeanUtils.copyProperties(landpageDataHourModel, landpageDataHourBean);
        
    }

    @Transactional
    public void updateLandpageDataHour(Integer id, LandpageDataHourBean landpageDataDayBean)
    {
        // 操作前先查询一次数据库，判断指定的资源是否存在
        LandpageDataHourModel dataInDB = landpageDataHourDao.selectByPrimaryKey(id);
        if (dataInDB == null)
        {
            throw new ResourceNotFoundException();
        }
        
        // 将传输对象映射成数据库Model
        LandpageDataHourModel landpageDataHourModel = modelMapper.map(landpageDataDayBean, LandpageDataHourModel.class);
        landpageDataHourModel.setId(id);
        
        try
        {
            landpageDataHourDao.updateByPrimaryKey(landpageDataHourModel);
        }
        catch (DuplicateKeyException exception)
        {
            // 违反数据库唯一约束时，向上抛出自定义异常，交给全局异常处理器处理
            throw new DuplicateEntityException();
        }
        
        // 将DAO编辑后的新对象复制回传输对象中
        BeanUtils.copyProperties(landpageDataHourDao.selectByPrimaryKey(id), landpageDataDayBean);
    }

    
    @Transactional
    public List<LandpageDataHourBean> listLandpageDataHour(String campaignId, long beginTime, long endTime)
    {
        LandpageDataHourModelExample example = new LandpageDataHourModelExample();
        Criteria criteria = example.createCriteria();
        criteria.andCampaignIdEqualTo(campaignId);
        criteria.andDatetimeBetween(new Date(beginTime), new Date(endTime));
        
        List<LandpageDataHourModel> models = landpageDataHourDao.selectByExample(example);
        List<LandpageDataHourBean> landpageDataHourList = new ArrayList<LandpageDataHourBean>();
        
        if (models == null || models.size() <= 0)
        {
            throw new ResourceNotFoundException();
        }
        else
        {
            // 遍历数据库中查询到的全部结果，逐个将DAO创建的新对象复制回传输对象中
            for (LandpageDataHourModel model : models)
            {
                landpageDataHourList.add(modelMapper.map(model, LandpageDataHourBean.class));
            }
        }
        
        return landpageDataHourList;
    }
    
}
