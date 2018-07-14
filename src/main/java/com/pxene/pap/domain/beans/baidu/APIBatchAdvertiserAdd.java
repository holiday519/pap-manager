package com.pxene.pap.domain.beans.baidu;

import java.util.Arrays;

/**
 * 批量新增广告主。
 * 限制：一次最多新增5个广告主
 * @author ningyu
 *
 */
public class APIBatchAdvertiserAdd extends BaseBean
{
    /**
     * 广告主列表
     */
    private Advertiser[] request;

    
    public Advertiser[] getRequest()
    {
        return request;
    }
    public void setRequest(Advertiser[] request)
    {
        this.request = request;
    }
    
    
    @Override
    public String toString()
    {
        return "APIBatchAdvertiserAdd [request=" + Arrays.toString(request) + "]";
    }
}
