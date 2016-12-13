package com.pxene.pap.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    private HttpStatus httpStatus;
    private int code;
    private String message;
    
    
    public HttpStatus getHttpStatus()
    {
        return httpStatus;
    }
    public void setHttpStatus(HttpStatus httpStatus)
    {
        this.httpStatus = httpStatus;
    }
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
    
    
    public BaseException()
    {
        super();
    }
    public BaseException(int code, String message)
    {
        super(message);
        this.code = code;
    }
    public BaseException(HttpStatus httpStatus, int code, String message)
    {
        super();
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
    
    
    @Override
    public String toString()
    {
        return "BaseException [httpStatus=" + httpStatus + ", code=" + code + ", message=" + message + "]";
    }
}
