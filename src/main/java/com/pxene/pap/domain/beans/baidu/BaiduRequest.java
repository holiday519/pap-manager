package com.pxene.pap.domain.beans.baidu;

import java.util.Arrays;
import java.util.Map;

public class BaiduRequest extends BaseBean
{
    private Map<String, Object> authHeader;
    private Object[] request;
    
    
    public Map<String, Object> getAuthHeader()
    {
        return authHeader;
    }
    public void setAuthHeader(Map<String, Object> authHeader)
    {
        this.authHeader = authHeader;
    }
    public Object[] getRequest()
    {
        return request;
    }
    public void setRequest(Object[] request)
    {
        this.request = request;
    }
    
    
    public BaiduRequest()
    {
        super();
    }
    public BaiduRequest(Map<String, Object> authHeader, Object[] request)
    {
        super();
        this.authHeader = authHeader;
        this.request = request;
    }
    
    
    @Override
    public String toString()
    {
        return "BaiduRequest [authHeader=" + authHeader + ", request=" + Arrays.toString(request) + "]";
    }
}
