package com.pxene.pap.exception;

import org.springframework.http.HttpStatus;

public class UserNotExistException extends BaseException
{
    private static final long serialVersionUID = 6223257748356184009L;
    
    public static final int ERROR_CODE = 0x01;
    public static final String ERROR_MSG = "User do not exist";
    
    public UserNotExistException()
    {
        super(HttpStatus.UNAUTHORIZED, ERROR_CODE, ERROR_MSG);
    }
    public UserNotExistException(String bizErrorMsg)
    {
        super(HttpStatus.UNAUTHORIZED, ERROR_CODE, bizErrorMsg);
    }
    public UserNotExistException(int bizErrorCode, String bizErrorMsg)
    {
        super(HttpStatus.UNAUTHORIZED, bizErrorCode, bizErrorMsg);
    }
}
