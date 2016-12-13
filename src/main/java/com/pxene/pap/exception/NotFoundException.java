package com.pxene.pap.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException
{
    private static final long serialVersionUID = 6223257748356184009L;
    
    
    public NotFoundException()
    {
        super(HttpStatus.NOT_FOUND, BizExceptionEnum.NotFoundException.getCode(), BizExceptionEnum.NotFoundException.getMsg());
    }
    public NotFoundException(String bizErrorMsg)
    {
        super(HttpStatus.NOT_FOUND, BizExceptionEnum.NotFoundException.getCode(), bizErrorMsg);
    }
    public NotFoundException(int bizErrorCode, String bizErrorMsg)
    {
        super(HttpStatus.NOT_FOUND, bizErrorCode, bizErrorMsg);
    }
}
