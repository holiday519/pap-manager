package com.pxene.pap.domain.beans;

import java.util.Arrays;
import java.util.Date;

public class AdxCostBean
{
    private Date startDate;
    private Date endDate;
    private Adxes[] adxes;
    
    
    public Date getStartDate()
    {
        return startDate;
    }
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }
    public Date getEndDate()
    {
        return endDate;
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }
    public Adxes[] getAdxes()
    {
        return adxes;
    }
    public void setAdxes(Adxes[] adxes)
    {
        this.adxes = adxes;
    }
    
    
    @Override
    public String toString()
    {
        return "AdxCostBean [startDate=" + startDate + ", endDate=" + endDate + ", adxes=" + Arrays.toString(adxes) + "]";
    }


    public static class Adxes
    {
        private String adxId;
        private float ratio;
        
        
        public String getAdxId()
        {
            return adxId;
        }
        public void setAdxId(String adxId)
        {
            this.adxId = adxId;
        }
        public float getRatio()
        {
            return ratio;
        }
        public void setRatio(float ratio)
        {
            this.ratio = ratio;
        }
        
        
        @Override
        public String toString()
        {
            return "Adxes [adxId=" + adxId + ", ratio=" + ratio + "]";
        }
    }
}