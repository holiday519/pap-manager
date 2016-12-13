package com.pxene.pap.domain.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY) 
public class ResponseResult
{
    private int code;
    private String message;
    private Object data;
    
    
    public int getCode()
    {
        return code;
    }
    public void setCode(int code)
    {
        this.code = code;
    }
    public String getMessage()
    {
        return message;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }
    public Object getData()
    {
        return data;
    }
    public void setData(Object data)
    {
        this.data = data;
    }
    
    
    public ResponseResult()
    {
        super();
    }
    public ResponseResult(int code)
    {
        super();
        this.code = code;
    }
    public ResponseResult(String message)
    {
        super();
        this.message = message;
    }
    public ResponseResult(int code, String message)
    {
        super();
        this.code = code;
        this.message = message;
    }
    public ResponseResult(int code, String message, Object data)
    {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    
    @Override
    public String toString()
    {
        return "ResultMsg [code=" + code + ", message=" + message + ", data=" + data + "]";
    }
}
