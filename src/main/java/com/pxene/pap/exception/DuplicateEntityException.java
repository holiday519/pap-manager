package com.pxene.pap.exception;

import org.springframework.http.HttpStatus;

public class DuplicateEntityException extends BaseException
{
    private static final long serialVersionUID = -1078308774980784735L;
    
    
    public DuplicateEntityException()
    {
        super(HttpStatus.BAD_REQUEST, BizExceptionEnum.EntityAlreadyExistsException.getCode(), BizExceptionEnum.EntityAlreadyExistsException.getMsg());
    }
    public DuplicateEntityException(String bizErrorMsg)
    {
        super(HttpStatus.BAD_REQUEST, BizExceptionEnum.EntityAlreadyExistsException.getCode(), bizErrorMsg);
    }
    public DuplicateEntityException(int bizErrorCode, String bizErrorMsg)
    {
        super(HttpStatus.BAD_REQUEST, bizErrorCode, bizErrorMsg);
    }
}
