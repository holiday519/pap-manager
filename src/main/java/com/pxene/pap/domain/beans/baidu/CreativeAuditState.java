package com.pxene.pap.domain.beans.baidu;

import java.util.Arrays;

public class CreativeAuditState extends BaseBean
{
    /**
     * 创意 id，
     * N/A
     */
    private long creativeId;
    
    /**
     * 检查状态，
     * N/A，
     * 0：检查通过，1：待检查（默认），2：检查未通过，
     * 原因通过 refuseReason 字段说明。
     */
    private int state;
    
    /**
     * 未通过理由，
     * N/A，
     * 可包含一到多个理由。
     */
    private String[] refuseReason;
    
    /**
     * 关联的广告主状态，
     * N/A，
     * 0：检查通过，1：待检查（默认），2：检查未通过，3：--，
     * 因为资质或信息不全，广告主未发起状态检查。
     */
    private int advertiserState;
    
    /**
     * 低俗标签，
     * N/A，
     * 0：未关联低俗标签，1：低俗度较高，2：低俗度适中，3：不低俗
     */
    private int is_vulgar;
    
    /**
     * 流量渠道审核信息列表，
     * N/A
     */
    private SellerAuditInfo sellerAuditInfo;
    
    
    public long getCreativeId()
    {
        return creativeId;
    }
    public void setCreativeId(long creativeId)
    {
        this.creativeId = creativeId;
    }
    public int getState()
    {
        return state;
    }
    public void setState(int state)
    {
        this.state = state;
    }
    public String[] getRefuseReason()
    {
        return refuseReason;
    }
    public void setRefuseReason(String[] refuseReason)
    {
        this.refuseReason = refuseReason;
    }
    public int getAdvertiserState()
    {
        return advertiserState;
    }
    public void setAdvertiserState(int advertiserState)
    {
        this.advertiserState = advertiserState;
    }
    public int getIs_vulgar()
    {
        return is_vulgar;
    }
    public void setIs_vulgar(int is_vulgar)
    {
        this.is_vulgar = is_vulgar;
    }
    public SellerAuditInfo getSellerAuditInfo()
    {
        return sellerAuditInfo;
    }
    public void setSellerAuditInfo(SellerAuditInfo sellerAuditInfo)
    {
        this.sellerAuditInfo = sellerAuditInfo;
    }
    
    
    public CreativeAuditState()
    {
        super();
    }
    public CreativeAuditState(long creativeId, int state, String[] refuseReason, int advertiserState, int is_vulgar, SellerAuditInfo sellerAuditInfo)
    {
        super();
        this.creativeId = creativeId;
        this.state = state;
        this.refuseReason = refuseReason;
        this.advertiserState = advertiserState;
        this.is_vulgar = is_vulgar;
        this.sellerAuditInfo = sellerAuditInfo;
    }
    
    
    @Override
    public String toString()
    {
        return "CreativeAuditState [creativeId=" + creativeId + ", state=" + state + ", refuseReason=" + Arrays.toString(refuseReason) + ", advertiserState=" + advertiserState + ", is_vulgar="
                + is_vulgar + ", sellerAuditInfo=" + sellerAuditInfo + "]";
    }
}
