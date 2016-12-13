package com.pxene.pap.exception;

import org.springframework.http.HttpStatus;

public class UpdateErrorException extends BaseException
{
    private static final long serialVersionUID = -6188245293314338042L;
    
    
    public UpdateErrorException()
    {
        super(HttpStatus.INTERNAL_SERVER_ERROR, BizExceptionEnum.ServerAbnormalException.getCode(), BizExceptionEnum.ServerAbnormalException.getMsg());
    }
    public UpdateErrorException(String bizErrorMsg)
    {
        super(HttpStatus.INTERNAL_SERVER_ERROR, BizExceptionEnum.ServerAbnormalException.getCode(), bizErrorMsg);
    }
    public UpdateErrorException(int bizErrorCode, String bizErrorMsg)
    {
        super(HttpStatus.INTERNAL_SERVER_ERROR, bizErrorCode, bizErrorMsg);
    }
}
