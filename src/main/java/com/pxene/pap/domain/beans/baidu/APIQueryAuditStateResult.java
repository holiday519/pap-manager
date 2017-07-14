package com.pxene.pap.domain.beans.baidu;

import java.util.Arrays;

public class APIQueryAuditStateResult extends BaiduResponse
{
    private CreativeAuditState[] response;

    
    public CreativeAuditState[] getResponse()
    {
        return response;
    }
    public void setResponse(CreativeAuditState[] response)
    {
        this.response = response;
    }
    
    
    public APIQueryAuditStateResult()
    {
        super();
    }
    public APIQueryAuditStateResult(int status, Error[] errors)
    {
        super(status, errors);
    }
    
    
    @Override
    public String toString()
    {
        return "APIQueryAuditStateResult [getResponse()=" + Arrays.toString(getResponse()) + ", getStatus()=" + getStatus() + ", getErrors()=" + Arrays.toString(getErrors()) + "]";
    }
}
