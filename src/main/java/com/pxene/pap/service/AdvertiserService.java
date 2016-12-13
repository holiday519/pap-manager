package com.pxene.pap.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    @Autowired
    private AdvertiserModelMapper advertiserMapper;

    @Autowired
    private ProjectModelMapper projectMapper;
    
    
    public AdvertiserModel saveAdvertiser(AdvertiserModel advertiser)
    {
        advertiser.setId(UUID.randomUUID().toString());
        try
        {
            int affectedRows = advertiserMapper.insertSelective(advertiser);
            if (affectedRows <= 0)
            {
                throw new BadRequestException();
            }
        }
        catch (Exception exception)
        {
            if (exception instanceof DuplicateKeyException)
            {
                exception.printStackTrace();
                if (exception.getCause().getMessage().contains("Unique_Name"))
                {
                    throw new DuplicateEntityException();
                }
            }
        }
        return advertiser;
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


    public AdvertiserModel findAdvertiserById(String id) throws Exception
    {
        AdvertiserModel advertiser = advertiserMapper.selectByPrimaryKey(id);
        
        if (advertiser == null)
        {
            throw new NotFoundException();
        }
        
        return advertiser;
    }


    public List<AdvertiserModel> listAdvertisers(String name) throws Exception
    {
        AdvertiserModelExample example = new AdvertiserModelExample();
        Criteria criteria = example.createCriteria();
        
        if (!StringUtils.isEmpty(name))
        {
            criteria.andNameLike(name);
        }
        
        List<AdvertiserModel> advertisers = advertiserMapper.selectByExample(example);
        
        if (advertisers == null || advertisers.size() <= 0)
        {
            throw new NotFoundException();
        }
        
        return advertisers;
    }


    public AdvertiserModel pathUpdateAdvertiser(String id, AdvertiserModel advertiser) throws Exception
    {
        AdvertiserModelExample example = new AdvertiserModelExample();
        Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        
        if (!StringUtils.isEmpty(advertiser.getId()))
        {
            throw new IllegalArgumentException();
        }
        
        int affectedRows = advertiserMapper.updateByExampleSelective(advertiser, example);
        if (affectedRows > 0)
        {
            return advertiserMapper.selectByPrimaryKey(id);
        }
        else
        {
            throw new UpdateErrorException();
        }
    }
    
    public AdvertiserModel updateAdvertiser(String id, AdvertiserModel advertiser) throws Exception
    {
        advertiser.setId(id);
        
        int affectedRows = advertiserMapper.updateByPrimaryKey(advertiser);
        if (affectedRows > 0)
        {
            return advertiserMapper.selectByPrimaryKey(id);
        }
        else
        {
            throw new UpdateErrorException();
        }
    } 
}
