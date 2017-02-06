package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.beans.LandpageDataBean;

@Service
public class LandpageDataService extends BaseService
{
    DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");    
    
    @Transactional
    public List<LandpageDataBean> listLandpageDatas(String campaignId, long beginTime, long endTime)
    {
        
        return new ArrayList<LandpageDataBean>();
    }
    
}
