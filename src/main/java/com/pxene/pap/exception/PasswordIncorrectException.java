package com.pxene.pap.exception;

import org.springframework.http.HttpStatus;

public class PasswordIncorrectException extends BaseException
{
    private static final long serialVersionUID = -219805302367922274L;
    
    public static final int ERROR_CODE = 0x02;
    public static final String ERROR_MSG = "Password is incorrect";
    
    public PasswordIncorrectException()
    {
        super(HttpStatus.UNAUTHORIZED, ERROR_CODE, ERROR_MSG);
    }
    public PasswordIncorrectException(String bizErrorMsg)
    {
        super(HttpStatus.UNAUTHORIZED, ERROR_CODE, bizErrorMsg);
    }
    public PasswordIncorrectException(int bizErrorCode, String bizErrorMsg)
    {
        super(HttpStatus.UNAUTHORIZED, bizErrorCode, bizErrorMsg);
    }
}
