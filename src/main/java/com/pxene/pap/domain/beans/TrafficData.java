package com.pxene.pap.domain.beans;

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
     * 转化数(二跳数)
     */
    private Long jumpAmount;
    
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
     * 转化成本(二跳成本)
     */
    private Float jumpCost;
    
    
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
    public Long getJumpAmount()
    {
        return jumpAmount;
    }
    public void setJumpAmount(Long jumpAmount)
    {
        this.jumpAmount = jumpAmount;
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
    public Float getJumpCost()
    {
        return jumpCost;
    }
    public void setJumpCost(Float jumpCost)
    {
        this.jumpCost = jumpCost;
    }
    
    
    public TrafficData()
    {
        super();
    }
    public TrafficData(String date, String name, Long impressionAmount, Long clickAmount, Float clickRate, Long jumpAmount, Float totalCost, Float impressionCost, Float clickCost, Float jumpCost)
    {
        super();
        this.date = date;
        this.name = name;
        this.impressionAmount = impressionAmount;
        this.clickAmount = clickAmount;
        this.clickRate = clickRate;
        this.jumpAmount = jumpAmount;
        this.totalCost = totalCost;
        this.impressionCost = impressionCost;
        this.clickCost = clickCost;
        this.jumpCost = jumpCost;
    }
    
    
    @Override
    public String toString()
    {
        return "TrafficData [date=" + date + ", name=" + name + ", impressionAmount=" + impressionAmount + ", clickAmount=" + clickAmount + ", clickRate=" + clickRate + ", jumpAmount=" + jumpAmount
                + ", totalCost=" + totalCost + ", impressionCost=" + impressionCost + ", clickCost=" + clickCost + ", jumpCost=" + jumpCost + "]";
    }
}
