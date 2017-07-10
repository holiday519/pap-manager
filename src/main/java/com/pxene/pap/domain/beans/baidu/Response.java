package com.pxene.pap.domain.beans.baidu;

import java.util.Arrays;

public class Response extends BaseBean
{
    /**
     * 返回状态码。
     * 0：成功，1：部分成功，2：全部失败
     */
    private int status;
    
    /**
     * 错误信息数组。
     */
    private Error[] errors;
    
    
    public int getStatus()
    {
        return status;
    }
    public void setStatus(int status)
    {
        this.status = status;
    }
    public Error[] getErrors()
    {
        return errors;
    }
    public void setErrors(Error[] errors)
    {
        this.errors = errors;
    }
    
    
    public Response()
    {
        super();
    }
    public Response(int status, Error[] errors)
    {
        super();
        this.status = status;
        this.errors = errors;
    }
    
    
    @Override
    public String toString()
    {
        return "Response [status=" + status + ", errors=" + Arrays.toString(errors) + "]";
    }
}
