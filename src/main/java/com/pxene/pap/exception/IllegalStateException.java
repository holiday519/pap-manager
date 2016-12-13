package com.pxene.pap.exception;

import org.springframework.http.HttpStatus;

public class IllegalStateException extends BaseException
{
    private static final long serialVersionUID = -4610285929051676052L;
    
    
    public IllegalStateException()
    {
        super(HttpStatus.BAD_REQUEST, BizExceptionEnum.IllegalStateException.getCode(), BizExceptionEnum.IllegalStateException.getMsg());
    }
    public IllegalStateException(String bizErrorMsg)
    {
        super(HttpStatus.BAD_REQUEST, BizExceptionEnum.IllegalStateException.getCode(), bizErrorMsg);
    }
    public IllegalStateException(int bizErrorCode, String bizErrorMsg)
    {
        super(HttpStatus.BAD_REQUEST, bizErrorCode, bizErrorMsg);
    }
}
