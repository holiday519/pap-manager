package com.pxene.pap.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException
{
    private static final long serialVersionUID = 8535205199241358936L;

    
    public BadRequestException()
    {
        super(HttpStatus.BAD_REQUEST, BizExceptionEnum.BadRequestException.getCode(), BizExceptionEnum.BadRequestException.getMsg());
    }
    public BadRequestException(int bizErrorCode, String bizErrorMsg)
    {
        super(HttpStatus.BAD_REQUEST, bizErrorCode, bizErrorMsg);
    }
}
