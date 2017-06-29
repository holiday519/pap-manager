package com.pxene.pap.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.RedisHelper;
import com.pxene.pap.common.UUIDGenerator;
import com.pxene.pap.domain.beans.AdxCostBean;
import com.pxene.pap.domain.beans.AdxCostBean.Adxes;
import com.pxene.pap.domain.beans.AdxCostData;
import com.pxene.pap.domain.models.AdxCostModel;
import com.pxene.pap.domain.models.AdxCostModelExample;
import com.pxene.pap.domain.models.CreativeBasicModel;
import com.pxene.pap.domain.models.CreativeBasicModelExample;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.repository.basic.AdxCostDao;
import com.pxene.pap.repository.basic.view.CreativeBasicDao;


@Service
public class AdxCostService extends BaseService
{
    private static final String REDIS_KEY_PREFIX = "creativeDataDay_";

    private static final String REDIS_FIELD_PATTERN = "{0}_adx_{1}@{2}";
    
    @Autowired
    private AdxCostDao adxCostDao;
    
    @Autowired
    private RedisHelper tertiaryRedis;
    
    @Autowired
    private CreativeBasicDao creativeBasicDao;
    
    
    @PostConstruct
    public void selectRedis()
    {
        tertiaryRedis.select("redis.tertiary.");
    }
    
    
    @Transactional
    public void create(AdxCostBean adxCost)
    {
        if (adxCost == null || adxCost.getAdxes() == null || adxCost.getAdxes().length < 1)
        {
            throw new IllegalArgumentException();
        }
        
        Date startDate = adxCost.getStartDate();
        Date endDate = adxCost.getEndDate();
        List<Date> days = DateUtils.listDatesBetweenTwoDates(new LocalDate(startDate), new LocalDate(endDate), true);
        
        AdxCostModelExample example = new AdxCostModelExample();
        
        for (Date day : days)
        {
            Adxes[] adxItems = adxCost.getAdxes();
            
            for (Adxes adxItem : adxItems)
            {
                String uuid = UUIDGenerator.getUUID();
                String adxId = adxItem.getAdxId();
                Float ratio = adxItem.getRatio();
                
                AdxCostModel record = new AdxCostModel();
                record.setId(uuid);
                record.setFixDate(day);
                record.setAdxId(adxId);
                record.setRatio(ratio);
                
                example.clear();
                example.createCriteria().andAdxIdEqualTo(adxId).andFixDateEqualTo(day);
                
                List<AdxCostModel> adxCostsInDB = adxCostDao.selectByExample(example);
                if (adxCostsInDB != null && !adxCostsInDB.isEmpty())
                {
                    record.setId(adxCostsInDB.get(0).getId());
                    adxCostDao.updateByPrimaryKey(record);
                }
                else
                {
                    adxCostDao.insert(record);
                }
            }
        }
    }

    @Transactional
    public List<AdxCostData> listProjectsData(String date)
    {
        if (StringUtils.isEmpty(date))
        {
            throw new IllegalArgumentException();
        }
        
        List<AdxCostData> result = new ArrayList<AdxCostData>();
        
        Date currentDate = new Date();
        
        // 根据活动时间，从视图“pap_v_creative_basic”中查询出所有的创意
        CreativeBasicModelExample example = new CreativeBasicModelExample();
        example.createCriteria().andCampaignStartDateLessThanOrEqualTo(currentDate).andCampaignEndDateGreaterThanOrEqualTo(currentDate);
        List<CreativeBasicModel> rows = creativeBasicDao.selectByExampleWithBLOBs(example);
        
        for (CreativeBasicModel row : rows)
        {
            AdxCostData data = new AdxCostData();
            
            String adxId = row.getAdxId();
            String adxName = row.getAdxName();
            String advertiserName = row.getAdvertiserName();
            String projectCode = row.getProjectId();
            String projectName = row.getProjectName();
            String mode = "01"; // 固定值：01，RTB
            int impressionAmount = 0;
            int clickAmount = 0;
            float clickRate = 0.0f;
            float cost = 0.0f;
            String status = "01"; // 固定值：01，投放中
            
            String creativeIdsStr = row.getCreativeIds();
            if (!StringUtils.isEmpty(creativeIdsStr))
            {
                String[] creativeIds = creativeIdsStr.split(",");
                for (String creativeId : creativeIds)
                {
                    String key = REDIS_KEY_PREFIX + creativeId;
                    
                    String clickField = MessageFormat.format(REDIS_FIELD_PATTERN, date, adxId, "c");
                    String clickAmountStr = tertiaryRedis.hget(key, clickField);
                    if (!StringUtils.isEmpty(clickAmountStr))
                    {
                        clickAmount = clickAmount + Integer.valueOf(clickAmountStr);
                    }
                    
                    String impressionField = MessageFormat.format(REDIS_FIELD_PATTERN, date, adxId, "m");
                    String impressionAmountStr = tertiaryRedis.hget(key, impressionField);
                    if (!StringUtils.isEmpty(impressionAmountStr))
                    {
                        impressionAmount = impressionAmount + Integer.valueOf(impressionAmountStr);
                    }
                    
                    String costField = MessageFormat.format(REDIS_FIELD_PATTERN, date, adxId, "e");
                    String costAmountStr = tertiaryRedis.hget(key, costField);
                    if (!StringUtils.isEmpty(costAmountStr))
                    {
                        cost = cost + Float.parseFloat(costAmountStr);
                    }
                }
                
                if (impressionAmount != 0)
                {
                    clickRate = clickAmount / impressionAmount;
                }
                
                data.setAdxId(adxId);
                data.setAdxName(adxName);
                data.setAdvertiserName(advertiserName);
                data.setProjectCode(projectCode);
                data.setProjectName(projectName);
                data.setMode(mode);
                data.setImpressionAmount(impressionAmount);
                data.setClickAmount(clickAmount);
                data.setClickRate(clickRate);
                data.setCost(cost / 100);
                data.setStatus(status);
                
                if (cost > 0)
                {
                    result.add(data);
                }
            }
        }
        
        return result;
    }
}
