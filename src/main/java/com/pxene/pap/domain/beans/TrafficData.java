package com.pxene.pap.domain.beans;

import java.util.Date;

public class TrafficData
{
    /**
     * 时间
     */
    private String date;
    
    /**
     * 客户名称
     */
    private String name;
    
    /**
     * 展现数
     */
    private Long impressionAmount;
    
    /**
     * 点击数
     */
    private Long clickAmount;
    
    /**
     * 点击率
     */
    private Float clickRate;
    
    /**
     * 转化数
     */
    private Long conversionAmount;
    
    /**
     * 总成本
     */
    private Float totalCost;
    
    /**
     * 展现成本
     */
    private Float impressionCost;
    
    /**
     * 点击成本
     */
    private Float clickCost;
    
    /**
     * 转化成本
     */
    private Float conversionCost;
    
    
    public String getDate()
    {
        return date;
    }
    public void setDate(String date)
    {
        this.date = date;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Long getImpressionAmount()
    {
        return impressionAmount;
    }
    public void setImpressionAmount(Long impressionAmount)
    {
        this.impressionAmount = impressionAmount;
    }
    public Long getClickAmount()
    {
        return clickAmount;
    }
    public void setClickAmount(Long clickAmount)
    {
        this.clickAmount = clickAmount;
    }
    public Float getClickRate()
    {
        return clickRate;
    }
    public void setClickRate(Float clickRate)
    {
        this.clickRate = clickRate;
    }
    public Long getConversionAmount()
    {
        return conversionAmount;
    }
    public void setConversionAmount(Long conversionAmount)
    {
        this.conversionAmount = conversionAmount;
    }
    public Float getTotalCost()
    {
        return totalCost;
    }
    public void setTotalCost(Float totalCost)
    {
        this.totalCost = totalCost;
    }
    public Float getImpressionCost()
    {
        return impressionCost;
    }
    public void setImpressionCost(Float impressionCost)
    {
        this.impressionCost = impressionCost;
    }
    public Float getClickCost()
    {
        return clickCost;
    }
    public void setClickCost(Float clickCost)
    {
        this.clickCost = clickCost;
    }
    public Float getConversionCost()
    {
        return conversionCost;
    }
    public void setConversionCost(Float conversionCost)
    {
        this.conversionCost = conversionCost;
    }
    
    
    public TrafficData()
    {
        super();
    }
    public TrafficData(String date, String name, Long impressionAmount, Long clickAmount, Float clickRate, Long conversionAmount, Float totalCost, Float impressionCost, Float clickCost,
            Float conversionCost)
    {
        super();
        this.date = date;
        this.name = name;
        this.impressionAmount = impressionAmount;
        this.clickAmount = clickAmount;
        this.clickRate = clickRate;
        this.conversionAmount = conversionAmount;
        this.totalCost = totalCost;
        this.impressionCost = impressionCost;
        this.clickCost = clickCost;
        this.conversionCost = conversionCost;
    }
    
    
    @Override
    public String toString()
    {
        return "TrafficData [date=" + date + ", name=" + name + ", impressionAmount=" + impressionAmount + ", clickAmount=" + clickAmount + ", clickRate=" + clickRate + ", conversionAmount="
                + conversionAmount + ", totalCost=" + totalCost + ", impressionCost=" + impressionCost + ", clickCost=" + clickCost + ", conversionCost=" + conversionCost + "]";
    }
}
