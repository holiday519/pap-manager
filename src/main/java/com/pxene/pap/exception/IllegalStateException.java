package com.pxene.pap.exception;

import org.springframework.http.HttpStatus;

public class IllegalStateException extends BaseException
{
    private static final long serialVersionUID = -4610285929051676052L;
    
    public static final int ERROR_CODE = 0x02;
    public static final String ERROR_MSG = "Illegal state";
    
    public IllegalStateException()
    {
        super(HttpStatus.BAD_REQUEST, ERROR_CODE, ERROR_MSG);
    }
    public IllegalStateException(String bizErrorMsg)
    {
        super(HttpStatus.BAD_REQUEST, ERROR_CODE, bizErrorMsg);
    }
    public IllegalStateException(int bizErrorCode, String bizErrorMsg)
    {
        super(HttpStatus.BAD_REQUEST, bizErrorCode, bizErrorMsg);
    }
}
