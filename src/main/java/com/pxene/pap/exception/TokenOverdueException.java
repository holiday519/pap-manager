package com.pxene.pap.exception;

import org.springframework.http.HttpStatus;

public class TokenOverdueException extends BaseException {

	private static final long serialVersionUID = 4701567857012002992L;
	
	public static final int ERROR_CODE = 0x03;
	public static final String ERROR_MSG = "token is overdue";
    
    public TokenOverdueException()
    {
        super(HttpStatus.UNAUTHORIZED, ERROR_CODE, ERROR_MSG);
    }
    public TokenOverdueException(String bizErrorMsg)
    {
        super(HttpStatus.UNAUTHORIZED, ERROR_CODE, bizErrorMsg);
    }
    public TokenOverdueException(int bizErrorCode, String bizErrorMsg)
    {
        super(HttpStatus.UNAUTHORIZED, bizErrorCode, bizErrorMsg);
    }

}
