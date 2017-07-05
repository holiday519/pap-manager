package com.pxene.pap.service;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.pxene.pap.domain.models.AdvertiserModel;
import com.pxene.pap.domain.models.AdxCostModel;
import com.pxene.pap.domain.models.AdxCostModelExample;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.CreativeModelExample;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.domain.models.ProjectModelExample;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.repository.basic.AdvertiserDao;
import com.pxene.pap.repository.basic.AdxCostDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.ProjectDao;


@Service
public class AdxCostService extends BaseService
{
    private static final String REDIS_KEY_PREFIX = "creativeDataDay_";

    private static final String REDIS_FIELD_PATTERN = "{0}_adx_{1}@{2}";
    
    private static final SimpleDateFormat DATATIME_FORMATTER = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    
    @Autowired
    private AdxCostDao adxCostDao;
    
    @Autowired
    private ProjectDao projectDao;
    
    @Autowired
    private CampaignDao campaignDao;
    
    @Autowired
    private CreativeDao creativeDao;

    @Autowired
    private AdvertiserDao advertiserDao;
    
    @Autowired
    private RedisHelper tertiaryRedis;
    
    @Autowired
    private LaunchService launchService;
    
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
                if (ratio < 0 || ratio > 100) {
                	continue;
                }
                
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
    public List<AdxCostData> listProjectsData(String date) throws ParseException
    {
        if (StringUtils.isEmpty(date))
        {
            throw new IllegalArgumentException();
        }
        
        Date startDate = DATATIME_FORMATTER.parse(date + " 00:00:00");
        Date endDate = DATATIME_FORMATTER.parse(date + " 23:59:59");
        
        // 查询出满足日期条件的全部活动
        CampaignModelExample campaignExample = new CampaignModelExample();
        campaignExample.createCriteria().andStartDateLessThanOrEqualTo(startDate).andEndDateGreaterThanOrEqualTo(endDate);
        List<CampaignModel> campaigns = campaignDao.selectByExample(campaignExample);
        
        CreativeModelExample creativeExample = new CreativeModelExample();
        
        Map<String, AdxCostData> tmpMap = new HashMap<String, AdxCostData>();
        String mode = "01";     // 固定值：01，RTB
        String status = "01";   // 固定值：01，投放中
        
        for (CampaignModel campaign : campaigns)
        {
            String projectId = campaign.getProjectId();
            String campaignId = campaign.getId();
            
            // 查询出属于这个活动的全部创意
            creativeExample.clear();
            creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
            List<CreativeModel> creatives = creativeDao.selectByExample(creativeExample);
            
            for (CreativeModel creative : creatives)
            {
                String creativeId = creative.getId();
                
                List<Map<String, String>> adxCreativeList = launchService.getAdxByCreative(creative);
                for (Map<String, String> entry : adxCreativeList)
                {
                    String adxId = entry.get("adxId");
                    String adxName = entry.get("adxName");
                    String idGroup = projectId + "_" + adxId;
                    
                    if (tmpMap.containsKey(idGroup))
                    {
                        AdxCostData oldAdxCostData = tmpMap.get(idGroup);
                        AdxCostData newAdxCostData = getCreativeStatics(date, creativeId, adxId);
                        
                        oldAdxCostData.setImpressionAmount(oldAdxCostData.getImpressionAmount() + newAdxCostData.getImpressionAmount());
                        oldAdxCostData.setClickAmount(oldAdxCostData.getClickAmount() + newAdxCostData.getClickAmount());
                        oldAdxCostData.setCost(oldAdxCostData.getCost() + newAdxCostData.getCost());
                        
//                        oldAdxCostData.setAdxName(adxName);
                    }
                    else
                    {
                        AdxCostData adxCostData = getCreativeStatics(date, creativeId, adxId);
                        adxCostData.setAdxName(adxName);
                        
                        tmpMap.put(idGroup, adxCostData);
                    }
                }
            }
        }
        
        List<AdxCostData> result = new ArrayList<AdxCostData>();
        
        for (Map.Entry<String, AdxCostData> entry : tmpMap.entrySet())
        {
            String key = entry.getKey();
            AdxCostData data = entry.getValue();
            
            String[] splitedKey = key.split("_");
            String projectId = splitedKey[0];
            String adxId = splitedKey[1];
            
            double cost = data.getCost();
            long impressionAmount = data.getImpressionAmount();
            long clickAmount = data.getClickAmount();
            double clickRate = 0.0D;
            if (impressionAmount != 0)
            {
                clickRate = (double) clickAmount / (double) impressionAmount;
            }
            
            if (cost > 0)
            {
                ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
                String projectCode = projectModel.getCode();
                String advertiserId = projectModel.getAdvertiserId();
                AdvertiserModel advertiserModel = advertiserDao.selectByPrimaryKey(advertiserId);
                
                data.setProjectCode(projectCode);
                data.setAdxId(adxId);
                data.setMode(mode);
                data.setStatus(status);
                data.setClickRate(clickRate);
                data.setAdvertiserName(advertiserModel.getName());
                
                result.add(data);
            }
        }
        
        return result;
    }

    /**
     * 构造一个Redis Key，Key的格式为“creativeDataDay_”加上创意ID，取出这个Key相应的Field（c：点击，m：展现，e：成本）
     * @param date  查询日期
     * @param creativeId    创意ID
     * @param adxId         ADX ID
     * @return 封装点击数、展现数、点击率等数据的对象
     */
    private AdxCostData getCreativeStatics(String date, String creativeId, String adxId)
    {
        AdxCostData adxCostData = new AdxCostData();
        
        String key = REDIS_KEY_PREFIX + creativeId;
        
        String clickField = MessageFormat.format(REDIS_FIELD_PATTERN, date, adxId, "c");
        String clickAmountStr = tertiaryRedis.hget(key, clickField);
        if (!StringUtils.isEmpty(clickAmountStr))
        {
            adxCostData.setClickAmount(Long.valueOf(clickAmountStr));
        }
        
        String impressionField = MessageFormat.format(REDIS_FIELD_PATTERN, date, adxId, "m");
        String impressionAmountStr = tertiaryRedis.hget(key, impressionField);
        if (!StringUtils.isEmpty(impressionAmountStr))
        {
            adxCostData.setImpressionAmount(Long.valueOf(impressionAmountStr));
        }
        
        String costField = MessageFormat.format(REDIS_FIELD_PATTERN, date, adxId, "e");
        String costAmountStr = tertiaryRedis.hget(key, costField);
        if (!StringUtils.isEmpty(costAmountStr))
        {
            adxCostData.setCost(Double.parseDouble(costAmountStr) / 100);
        }
        return adxCostData;
    }
    
    @Transactional
    public List<Map<String, String>> getProjectNames(String[] codes) {
    	if (codes.length == 0) {
    		return new ArrayList<Map<String, String>>();
    	}
    	List<Map<String, String>> results = new ArrayList<Map<String, String>>(); 
    	
    	ProjectModelExample ex = new ProjectModelExample();
    	ex.createCriteria().andCodeIn(Arrays.asList(codes));
    	List<ProjectModel> projects = projectDao.selectByExample(ex);
    	for (ProjectModel project : projects) {
    		Map<String, String> result = new HashMap<String, String>();
    		result.put("code", project.getCode());
    		result.put("name", project.getName());
    		results.add(result);
    	}
    	
    	return results;
    }
}
