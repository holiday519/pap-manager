package com.pxene.pap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.beans.AdvertiserBean;
import com.pxene.pap.repository.AdvertiserDao;

@Service
public class AdvertiserService
{
    @Autowired
    private AdvertiserDao advertiserDao;

    
    public int saveAdvertiser(AdvertiserBean advertiser)
    {
        return advertiserDao.save(advertiser);
    } 
}
