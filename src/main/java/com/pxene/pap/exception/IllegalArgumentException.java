package com.pxene.pap.exception;

import org.springframework.http.HttpStatus;

public class IllegalArgumentException extends BaseException
{
    private static final long serialVersionUID = -1389539463693690654L;
    
    
    public IllegalArgumentException()
    {
        super(HttpStatus.BAD_REQUEST, BizExceptionEnum.IllegalArgumentException.getCode(), BizExceptionEnum.IllegalArgumentException.getMsg());
    }
    public IllegalArgumentException(int bizErrorCode, String bizErrorMsg)
    {
        super(HttpStatus.BAD_REQUEST, bizErrorCode, bizErrorMsg);
    }
}
