package com.pxene.pap.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundAuthException extends BaseException
{
    private static final long serialVersionUID = 6223257748356184009L;
    
    
    public UserNotFoundAuthException()
    {
        super(HttpStatus.UNAUTHORIZED, BizExceptionEnum.UserNotFoundException.getCode(), BizExceptionEnum.UserNotFoundException.getMsg());
    }
    public UserNotFoundAuthException(int bizErrorCode, String bizErrorMsg)
    {
        super(HttpStatus.UNAUTHORIZED, bizErrorCode, bizErrorMsg);
    }
}
