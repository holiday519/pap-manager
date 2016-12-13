package com.pxene.pap.exception;

import org.springframework.http.HttpStatus;

public class PasswordIncorrectAuthException extends BaseException
{
    private static final long serialVersionUID = -219805302367922274L;
    
    
    public PasswordIncorrectAuthException()
    {
        super(HttpStatus.UNAUTHORIZED, BizExceptionEnum.PasswordIncorrectException.getCode(), BizExceptionEnum.PasswordIncorrectException.getMsg());
    }
    public PasswordIncorrectAuthException(int bizErrorCode, String bizErrorMsg)
    {
        super(HttpStatus.UNAUTHORIZED, bizErrorCode, bizErrorMsg);
    }
}
