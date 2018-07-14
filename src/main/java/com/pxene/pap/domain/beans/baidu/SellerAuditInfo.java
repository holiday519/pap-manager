package com.pxene.pap.domain.beans.baidu;

import java.util.Arrays;

public class SellerAuditInfo
{
    /**
     * 流量渠道 id,
     * N/A,
     * 此 id 目前只在百度内部使用。
     */
    private int sellerId;
    
    /**
     * 检查状态,
     * N/A,
     * 0：检查通过，1：待检查（默认），2：检查未通过，
     * 原因通过 refuseReason 字段说明。
     */
    private int state;
    
    /**
     * 未通过理由,
     * N/A,
     * 可包含一到多个理由。
     */
    private String[] refuseReason;
    
    
    public int getSellerId()
    {
        return sellerId;
    }
    public void setSellerId(int sellerId)
    {
        this.sellerId = sellerId;
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
    
    
    public SellerAuditInfo()
    {
        super();
    }
    public SellerAuditInfo(int sellerId, int state, String[] refuseReason)
    {
        super();
        this.sellerId = sellerId;
        this.state = state;
        this.refuseReason = refuseReason;
    }
    
    
    @Override
    public String toString()
    {
        return "SellerAuditInfo [sellerId=" + sellerId + ", state=" + state + ", refuseReason=" + Arrays.toString(refuseReason) + "]";
    }
}
