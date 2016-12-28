package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.beans.AppFlowHourBean;
import com.pxene.pap.domain.model.basic.AppFlowHourModel;
import com.pxene.pap.domain.model.basic.AppFlowHourModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AppFlowHourDao;

@Service
public class AppFlowHourService extends BaseService
{
    @Autowired
    private AppFlowHourDao appFlowHourDao;
    
    
    @Transactional
    public void saveAppFlowHour(AppFlowHourBean appDataDayBean)
    {
        AppFlowHourModel appDataDayModel = modelMapper.map(appDataDayBean, AppFlowHourModel.class);
        
        try
        {
            appFlowHourDao.insertSelective(appDataDayModel);
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
    public void updateAppFlowHour(Integer id, AppFlowHourBean appDataDayBean)
    {
        // 操作前先查询一次数据库，判断指定的资源是否存在
        AppFlowHourModel appDataDayInDB = appFlowHourDao.selectByPrimaryKey(id);
        if (appDataDayInDB == null)
        {
            throw new ResourceNotFoundException();
        }
        
        // 将传输对象映射成数据库Model
        AppFlowHourModel appDataDayModel = modelMapper.map(appDataDayBean, AppFlowHourModel.class);
        appDataDayModel.setId(id);
        
        try
        {
            appFlowHourDao.updateByPrimaryKey(appDataDayModel);
        }
        catch (DuplicateKeyException exception)
        {
            // 违反数据库唯一约束时，向上抛出自定义异常，交给全局异常处理器处理
            throw new DuplicateEntityException();
        }
        
        // 将DAO编辑后的新对象复制回传输对象中
        BeanUtils.copyProperties(appFlowHourDao.selectByPrimaryKey(id), appDataDayBean);
    }

    
    @Transactional
    public AppFlowHourBean findAppFlowHourById(Integer id)
    {
        AppFlowHourModel appDataDayModel = appFlowHourDao.selectByPrimaryKey(id);
        
        if (appDataDayModel == null)
        {
            throw new ResourceNotFoundException();
        }
        
        // 将DAO创建的新对象复制回传输对象中
        return modelMapper.map(appDataDayModel, AppFlowHourBean.class);
    }

    
    @Transactional
    public List<AppFlowHourBean> listAppFlowHour()
    {
        AppFlowHourModelExample example = new AppFlowHourModelExample();
        
        List<AppFlowHourModel> appDataDayModels = appFlowHourDao.selectByExample(example);
        List<AppFlowHourBean> appDataDayList = new ArrayList<AppFlowHourBean>();
        
        if (appDataDayModels == null || appDataDayModels.size() <= 0)
        {
            throw new ResourceNotFoundException();
        }
        else
        {
            // 遍历数据库中查询到的全部结果，逐个将DAO创建的新对象复制回传输对象中
            for (AppFlowHourModel appDataDayModel : appDataDayModels)
            {
                appDataDayList.add(modelMapper.map(appDataDayModel, AppFlowHourBean.class));
            }
        }
        
        return appDataDayList;
    }
    
}
