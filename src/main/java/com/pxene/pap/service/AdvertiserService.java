package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.domain.beans.AdvertiserBean;
import com.pxene.pap.domain.model.basic.AdvertiserModel;
import com.pxene.pap.domain.model.basic.AdvertiserModelExample;
import com.pxene.pap.domain.model.basic.AdvertiserModelExample.Criteria;
import com.pxene.pap.domain.model.basic.ProjectModel;
import com.pxene.pap.domain.model.basic.ProjectModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.IllegalStateException;
import com.pxene.pap.exception.NotFoundException;
import com.pxene.pap.repository.basic.AdvertiserDao;
import com.pxene.pap.repository.basic.ProjectDao;

@Service
public class AdvertiserService extends BaseService
{
    @Autowired
    private AdvertiserDao advertiserDao;

    @Autowired
    private ProjectDao projectDao;
    
    
    public void saveAdvertiser(AdvertiserBean advertiserBean) throws Exception
    {
        // 将传输对象映射成数据库Model，并设置UUID
        AdvertiserModel advertiserModel = modelMapper.map(advertiserBean, AdvertiserModel.class);
        advertiserModel.setId(UUID.randomUUID().toString());
        
        try
        {
            advertiserDao.insertSelective(advertiserModel);
        }
        catch (DuplicateKeyException exception)
        {
            // 违反数据库唯一约束时，向上抛出自定义异常，交给全局异常处理器处理
            throw new DuplicateEntityException();
        }
        
        // 将DAO创建的新对象复制回传输对象中
        BeanUtils.copyProperties(advertiserModel, advertiserBean);
    }


    @Transactional
    public void deleteAdvertiser(String id) throws Exception
    {
        // 操作前先查询一次数据库，判断指定的资源是否存在
        AdvertiserModel advertiserInDB = advertiserDao.selectByPrimaryKey(id);
        if (advertiserInDB == null)
        {
            throw new NotFoundException();
        }
        
        ProjectModelExample example = new ProjectModelExample();
        example.createCriteria().andAdvertiserIdEqualTo(id);
        
        List<ProjectModel> projects = projectDao.selectByExample(example);
        
        // 查看欲操作的广告主名下是否还有创建的项目，如果有，则不可以删除
        if (projects != null && !projects.isEmpty())
        {
            throw new IllegalStateException(PhrasesConstant.ADVERVISER_HAS_PROJECTS);
        }
        else
        {
            advertiserDao.deleteByPrimaryKey(id);
        }
    }

    @Transactional
    public void patchUpdateAdvertiser(String id, AdvertiserBean advertiserBean) throws Exception
    {
        // 更新操作要求绑定到RequestBody中的资源ID必须为空
        if (!StringUtils.isEmpty(advertiserBean.getId()))
        {
            throw new IllegalArgumentException();
        }
        
        // 操作前先查询一次数据库，判断指定的资源是否存在
        AdvertiserModel advertiserInDB = advertiserDao.selectByPrimaryKey(id);
        if (advertiserInDB == null)
        {
            throw new NotFoundException();
        }
        
        // 将传输对象映射成数据库Model
        AdvertiserModel advertiserModel = modelMapper.map(advertiserBean, AdvertiserModel.class);
        
        AdvertiserModelExample example = new AdvertiserModelExample();
        Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        
        try
        {
            advertiserDao.updateByExampleSelective(advertiserModel, example);
            // 将DAO编辑后的新对象复制回传输对象中
            BeanUtils.copyProperties(advertiserDao.selectByPrimaryKey(id), advertiserBean);
        }
        catch (DuplicateKeyException exception)
        {
            // 违反数据库唯一约束时，向上抛出自定义异常，交给全局异常处理器处理
            throw new DuplicateEntityException();
        }
    }


    @Transactional
    public void updateAdvertiser(String id, AdvertiserBean advertiserBean) throws Exception
    {
        // 更新操作要求绑定到RequestBody中的资源ID必须为空
        if (!StringUtils.isEmpty(advertiserBean.getId()))
        {
            throw new IllegalArgumentException();
        }
        
        // 操作前先查询一次数据库，判断指定的资源是否存在
        AdvertiserModel advertiserInDB = advertiserDao.selectByPrimaryKey(id);
        if (advertiserInDB == null)
        {
            throw new NotFoundException();
        }
        
        // 将传输对象映射成数据库Model
        AdvertiserModel advertiserModel = modelMapper.map(advertiserBean, AdvertiserModel.class);
        advertiserModel.setId(id);
        
        try
        {
            advertiserDao.updateByPrimaryKey(advertiserModel);
            
            // 将DAO编辑后的新对象复制回传输对象中
            BeanUtils.copyProperties(advertiserDao.selectByPrimaryKey(id), advertiserBean);
        }
        catch (DuplicateKeyException exception)
        {
            // 违反数据库唯一约束时，向上抛出自定义异常，交给全局异常处理器处理
            throw new DuplicateEntityException();
        }
    }


    public AdvertiserBean findAdvertiserById(String id) throws Exception
    {
        AdvertiserModel advertiserModel = advertiserDao.selectByPrimaryKey(id);
        
        if (advertiserModel == null)
        {
            throw new NotFoundException();
        }
        
        // 将DAO创建的新对象复制回传输对象中
        return modelMapper.map(advertiserModel, AdvertiserBean.class);
    }


    public List<AdvertiserBean> listAdvertisers(String name) throws Exception
    {
        AdvertiserModelExample example = new AdvertiserModelExample();
        Criteria criteria = example.createCriteria();
        
        // 根据用户名进行过滤（可选）
        if (!StringUtils.isEmpty(name))
        {
            criteria.andNameLike("%" + name + "%");
        }
        
        List<AdvertiserModel> advertiserModels = advertiserDao.selectByExample(example);
        List<AdvertiserBean> advertiserList = new ArrayList<AdvertiserBean>();
        
        if (advertiserModels == null || advertiserModels.size() <= 0)
        {
            throw new NotFoundException();
        }
        else
        {
            // 遍历数据库中查询到的全部结果，逐个将DAO创建的新对象复制回传输对象中
            for (AdvertiserModel advertiserModel : advertiserModels)
            {
                advertiserList.add(modelMapper.map(advertiserModel, AdvertiserBean.class));
            }
        }
        
        return advertiserList;
    }
}
