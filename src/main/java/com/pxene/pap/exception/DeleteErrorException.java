package com.pxene.pap.exception;

import org.springframework.http.HttpStatus;

public class DeleteErrorException extends BaseException
{
    private static final long serialVersionUID = 6223257748356184009L;
    
    
    public DeleteErrorException()
    {
        super(HttpStatus.GONE, BizExceptionEnum.ResourceNotFoundException.getCode(), BizExceptionEnum.ResourceNotFoundException.getMsg());
    }
    public DeleteErrorException(String bizErrorMsg)
    {
        super(HttpStatus.GONE, BizExceptionEnum.ResourceNotFoundException.getCode(), bizErrorMsg);
    }
    public DeleteErrorException(int bizErrorCode, String bizErrorMsg)
    {
        super(HttpStatus.GONE, bizErrorCode, bizErrorMsg);
    }
}
