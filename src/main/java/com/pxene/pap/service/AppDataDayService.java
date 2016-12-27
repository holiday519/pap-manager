package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.beans.AppDataDayBean;
import com.pxene.pap.domain.model.basic.AppDataDayModel;
import com.pxene.pap.domain.model.basic.AppDataDayModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AppDataDayDao;

@Service
public class AppDataDayService extends BaseService
{
    @Autowired
    private AppDataDayDao appDataDayDao;
    
    
    @Transactional
    public void saveAppDataDay(AppDataDayBean appDataDayBean)
    {
        AppDataDayModel appDataDayModel = modelMapper.map(appDataDayBean, AppDataDayModel.class);
        
        try
        {
            appDataDayDao.insertSelective(appDataDayModel);
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
    public void updateAppDataDay(Integer id, AppDataDayBean appDataDayBean)
    {
        // 操作前先查询一次数据库，判断指定的资源是否存在
        AppDataDayModel appDataDayInDB = appDataDayDao.selectByPrimaryKey(id);
        if (appDataDayInDB == null)
        {
            throw new ResourceNotFoundException();
        }
        
        // 将传输对象映射成数据库Model
        AppDataDayModel appDataDayModel = modelMapper.map(appDataDayBean, AppDataDayModel.class);
        appDataDayModel.setId(id);
        
        try
        {
            appDataDayDao.updateByPrimaryKey(appDataDayModel);
        }
        catch (DuplicateKeyException exception)
        {
            // 违反数据库唯一约束时，向上抛出自定义异常，交给全局异常处理器处理
            throw new DuplicateEntityException();
        }
        
        // 将DAO编辑后的新对象复制回传输对象中
        BeanUtils.copyProperties(appDataDayDao.selectByPrimaryKey(id), appDataDayBean);
    }

    
    @Transactional
    public AppDataDayBean findAppDataDayById(Integer id)
    {
        AppDataDayModel appDataDayModel = appDataDayDao.selectByPrimaryKey(id);
        
        if (appDataDayModel == null)
        {
            throw new ResourceNotFoundException();
        }
        
        // 将DAO创建的新对象复制回传输对象中
        return modelMapper.map(appDataDayModel, AppDataDayBean.class);
    }

    
    @Transactional
    public List<AppDataDayBean> listAppDataDay()
    {
        AppDataDayModelExample example = new AppDataDayModelExample();
        
        List<AppDataDayModel> appDataDayModels = appDataDayDao.selectByExample(example);
        List<AppDataDayBean> appDataDayList = new ArrayList<AppDataDayBean>();
        
        if (appDataDayModels == null || appDataDayModels.size() <= 0)
        {
            throw new ResourceNotFoundException();
        }
        else
        {
            // 遍历数据库中查询到的全部结果，逐个将DAO创建的新对象复制回传输对象中
            for (AppDataDayModel appDataDayModel : appDataDayModels)
            {
                appDataDayList.add(modelMapper.map(appDataDayModel, AppDataDayBean.class));
            }
        }
        
        return appDataDayList;
    }
    
}
