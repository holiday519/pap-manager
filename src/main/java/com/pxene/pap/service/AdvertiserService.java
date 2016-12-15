package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.domain.beans.AdvertiserBean;
import com.pxene.pap.domain.model.basic.AdvertiserModel;
import com.pxene.pap.domain.model.basic.AdvertiserModelExample;
import com.pxene.pap.domain.model.basic.AdvertiserModelExample.Criteria;
import com.pxene.pap.domain.model.basic.ProjectModel;
import com.pxene.pap.domain.model.basic.ProjectModelExample;
import com.pxene.pap.exception.BadRequestException;
import com.pxene.pap.exception.DeleteErrorException;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.IllegalStateException;
import com.pxene.pap.exception.NotFoundException;
import com.pxene.pap.exception.UpdateErrorException;
import com.pxene.pap.repository.mapper.basic.AdvertiserModelMapper;
import com.pxene.pap.repository.mapper.basic.ProjectModelMapper;

@Service
public class AdvertiserService
{
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();
    
    @Autowired
    private AdvertiserModelMapper advertiserMapper;

    @Autowired
    private ProjectModelMapper projectMapper;
    
    
    public void saveAdvertiser(AdvertiserBean advertiserBean)
    {
        AdvertiserModel advertiserModel = MODEL_MAPPER.map(advertiserBean, AdvertiserModel.class);
        advertiserModel.setId(UUID.randomUUID().toString());
        
        try
        {
            int affectedRows = advertiserMapper.insertSelective(advertiserModel);
            if (affectedRows <= 0)
            {
                throw new BadRequestException();
            }
        }
        catch (Exception exception)
        {
            if (exception instanceof DuplicateKeyException)
            {
                throw new DuplicateEntityException();
            }
        }
        
        BeanUtils.copyProperties(advertiserModel, advertiserBean);
    }


    @Transactional
    public void deleteAdvertiser(String id) throws Exception
    {
        ProjectModelExample example = new ProjectModelExample();
        example.createCriteria().andAdvertiserIdEqualTo(id);
        
        List<ProjectModel> projects = projectMapper.selectByExample(example);
        
        if (projects != null && !projects.isEmpty())
        {
            throw new IllegalStateException("广告主还有创建成功的项目，不能删除。");
        }
        else
        {
            int affectedRows = advertiserMapper.deleteByPrimaryKey(id);
            if (affectedRows <= 0)
            {
                throw new DeleteErrorException();
            }
        }
    }

    public AdvertiserBean findAdvertiserById(String id) throws Exception
    {
        AdvertiserModel advertiserModel = advertiserMapper.selectByPrimaryKey(id);
        
        if (advertiserModel == null)
        {
            throw new NotFoundException();
        }
        
        return MODEL_MAPPER.map(advertiserModel, AdvertiserBean.class);
    }


    public List<AdvertiserBean> listAdvertisers(String name) throws Exception
    {
        AdvertiserModelExample example = new AdvertiserModelExample();
        Criteria criteria = example.createCriteria();
        
        if (!StringUtils.isEmpty(name))
        {
            criteria.andNameLike(name);
        }
        
        List<AdvertiserModel> advertiserModels = advertiserMapper.selectByExample(example);
        List<AdvertiserBean> advertiserList = new ArrayList<AdvertiserBean>();
        
        if (advertiserModels == null || advertiserModels.size() <= 0)
        {
            throw new NotFoundException();
        }
        else
        {
            for (AdvertiserModel advertiserModel : advertiserModels)
            {
                advertiserList.add(MODEL_MAPPER.map(advertiserModel, AdvertiserBean.class));
            }
        }
        
        return advertiserList;
    }


    /**
     * @param id
     * @param advertiserBean
     * @throws Exception
     */
    public void patchUpdateAdvertiser(String id, AdvertiserBean advertiserBean) throws Exception
    {
        if (StringUtils.isEmpty(id) || !StringUtils.isEmpty(advertiserBean.getId()))
        {
            throw new IllegalArgumentException();
        }
        
        AdvertiserModel advertiserModel = MODEL_MAPPER.map(advertiserBean, AdvertiserModel.class);
        
        AdvertiserModelExample example = new AdvertiserModelExample();
        Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        
        try
        {
            int affectedRows = advertiserMapper.updateByExampleSelective(advertiserModel, example);
            if (affectedRows > 0)
            {
                BeanUtils.copyProperties(advertiserMapper.selectByPrimaryKey(id), advertiserBean);
            }
            else
            {
                throw new UpdateErrorException();
            }
        }
        catch (Exception exception)
        {
            if (exception instanceof DuplicateKeyException)
            {
                throw new DuplicateEntityException();
            }
        }
    }
    
    public void updateAdvertiser(String id, AdvertiserBean advertiserBean) throws Exception
    {
        if (StringUtils.isEmpty(id))
        {
            throw new IllegalArgumentException();
        }
        
        AdvertiserModel advertiserModel = MODEL_MAPPER.map(advertiserBean, AdvertiserModel.class);
        advertiserModel.setId(id);
        
        try
        {
            int affectedRows = advertiserMapper.updateByPrimaryKey(advertiserModel);
            if (affectedRows > 0)
            {
                BeanUtils.copyProperties(advertiserMapper.selectByPrimaryKey(id), advertiserBean);
            }
            else
            {
                throw new UpdateErrorException();
            }
        }
        catch (Exception exception)
        {
            if (exception instanceof DuplicateKeyException)
            {
                throw new DuplicateEntityException();
            }
        }
    }
    
    public static class AdvertiserModelList
    {
        List<AdvertiserModel> advertiserModels;
        
        
        public List<AdvertiserModel> getAdvertiserModels()
        {
            return advertiserModels;
        }
        public void setAdvertiserModels(List<AdvertiserModel> advertiserModels)
        {
            this.advertiserModels = advertiserModels;
        }
    }
    public static class AdvertiserBeanList
    {
        List<AdvertiserBean> advertiserBeans;
        
        
        public List<AdvertiserBean> getAdvertiserBeans()
        {
            return advertiserBeans;
        }
        public void setAdvertiserBeans(List<AdvertiserBean> advertiserBeans)
        {
            this.advertiserBeans = advertiserBeans;
        }
    }
}
