package com.pxene.pap.domain.beans;

public class AdxCostData
{
    private String adxId;
    private String adxName;
    private String advertiserName;
    private String projectName;
    private String projectCode;
    private String mode;
    private int impressionAmount;
    private int clickAmount;
    private float clickRate;
    private float cost;
    private String status;
    
    
    public String getAdxId()
    {
        return adxId;
    }
    public void setAdxId(String adxId)
    {
        this.adxId = adxId;
    }
    public String getAdxName()
    {
        return adxName;
    }
    public void setAdxName(String adxName)
    {
        this.adxName = adxName;
    }
    public String getAdvertiserName()
    {
        return advertiserName;
    }
    public void setAdvertiserName(String advertiserName)
    {
        this.advertiserName = advertiserName;
    }
    public String getProjectName()
    {
        return projectName;
    }
    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }
    public String getProjectCode()
    {
        return projectCode;
    }
    public void setProjectCode(String projectCode)
    {
        this.projectCode = projectCode;
    }
    public String getMode()
    {
        return mode;
    }
    public void setMode(String mode)
    {
        this.mode = mode;
    }
    public int getImpressionAmount()
    {
        return impressionAmount;
    }
    public void setImpressionAmount(int impressionAmount)
    {
        this.impressionAmount = impressionAmount;
    }
    public int getClickAmount()
    {
        return clickAmount;
    }
    public void setClickAmount(int clickAmount)
    {
        this.clickAmount = clickAmount;
    }
    public float getClickRate()
    {
        return clickRate;
    }
    public void setClickRate(float clickRate)
    {
        this.clickRate = clickRate;
    }
    public float getCost()
    {
        return cost;
    }
    public void setCost(float cost)
    {
        this.cost = cost;
    }
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    
    @Override
    public String toString()
    {
        return "AdxCostData [adxId=" + adxId + ", adxName=" + adxName + ", advertiserName=" + advertiserName + ", projectName=" + projectName + ", projectCode=" + projectCode + ", mode=" + mode
                + ", impressionAmount=" + impressionAmount + ", clickAmount=" + clickAmount + ", clickRate=" + clickRate + ", cost=" + cost + ", status=" + status + "]";
    }
}
