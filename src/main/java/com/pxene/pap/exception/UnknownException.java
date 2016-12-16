package com.pxene.pap.exception;

import org.springframework.http.HttpStatus;

public class UnknownException extends BaseException
{
    private static final long serialVersionUID = 8535205199241358936L;

    
    public UnknownException()
    {
        super(HttpStatus.INTERNAL_SERVER_ERROR, BizExceptionEnum.UnknowException.getCode(), BizExceptionEnum.UnknowException.getMsg());
    }
    public UnknownException(String bizErrorMsg)
    {
        super(HttpStatus.INTERNAL_SERVER_ERROR, BizExceptionEnum.UnknowException.getCode(), bizErrorMsg);
    }
    public UnknownException(int bizErrorCode, String bizErrorMsg)
    {
        super(HttpStatus.INTERNAL_SERVER_ERROR, bizErrorCode, bizErrorMsg);
    }
}
