package com.pxene.pap.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.beans.RegionFlowHourBean;
import com.pxene.pap.domain.model.RegionFlowHourModel;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.repository.basic.RegionFlowHourDao;
import com.pxene.pap.repository.custom.RegionFlowHourStatsDao;

@Service
public class RegionFlowHourService extends BaseService
{
    @Autowired
    private RegionFlowHourDao regionFlowHourDao;
    
    @Autowired
    private RegionFlowHourStatsDao regionFlowHourStatsDao;
    
    
    @Transactional
    public void saveRegionFlowHour(RegionFlowHourBean bean)
    {
        RegionFlowHourModel model = modelMapper.map(bean, RegionFlowHourModel.class);
        
        try
        {
            regionFlowHourDao.insertSelective(model);
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
    public List<Map<String, Object>> listRegionFlowHour(Date beginTime, Date endTime, int limitNum)
    {
        List<Map<String, Object>> beans = regionFlowHourStatsDao.selectTopN(beginTime, endTime, limitNum);
        
        return beans;
    }
    
}
