package com.pxene.pap.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.beans.AppFlowHourBean;
import com.pxene.pap.domain.models.AppFlowHourModel;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.repository.basic.AppFlowHourDao;
import com.pxene.pap.repository.custom.AppFlowHourStatsDao;

@Service
public class AppFlowHourService extends BaseService
{
    @Autowired
    private AppFlowHourDao appFlowHourDao;
    
    @Autowired
    private AppFlowHourStatsDao appFlowHourStatsDao;
    
    
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
    public List<Map<String, Object>> listAppFlowHour(Date beginTime, Date endTime, int limitNum)
    {
        List<Map<String, Object>> beans = appFlowHourStatsDao.selectTopN(beginTime, endTime, limitNum);
        
        return beans;
    }
    
}
