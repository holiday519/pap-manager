package com.pxene.pap.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.domain.model.basic.AdvertiserModel;
import com.pxene.pap.domain.model.basic.AdvertiserModelExample;
import com.pxene.pap.domain.model.basic.AdvertiserModelExample.Criteria;
import com.pxene.pap.repository.mapper.basic.AdvertiserModelMapper;

@Service
public class AdvertiserService
{
    @Autowired
    private AdvertiserModelMapper advertiserMapper;
    
    
    public int saveAdvertiser(AdvertiserModel advertiser)
    {
        advertiser.setId(UUID.randomUUID().toString());
        return advertiserMapper.insertSelective(advertiser);
    }


    @Transactional
    public int deleteAdvertiser(String id)
    {
        return advertiserMapper.deleteByPrimaryKey(id);
    }


    public AdvertiserModel findAdvertiserById(String id)
    {
        return advertiserMapper.selectByPrimaryKey(id);
    }


    public List<AdvertiserModel> listAdvertisers(String name)
    {
        AdvertiserModelExample example = new AdvertiserModelExample();
        Criteria criteria = example.createCriteria();
        
        if (!StringUtils.isEmpty(name))
        {
            criteria.andNameLike(name);
        }
        
        return advertiserMapper.selectByExample(example);
    } 
}
