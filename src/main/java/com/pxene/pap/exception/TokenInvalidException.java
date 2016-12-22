package com.pxene.pap.exception;

import org.springframework.http.HttpStatus;

public class TokenInvalidException extends BaseException {

	private static final long serialVersionUID = 8842371814057242577L;
	
	public static final int ERROR_CODE = 0x04;
	public static final String ERROR_MSG = "token is invalid";
    
    public TokenInvalidException()
    {
        super(HttpStatus.UNAUTHORIZED, ERROR_CODE, ERROR_MSG);
    }
    public TokenInvalidException(String bizErrorMsg)
    {
        super(HttpStatus.UNAUTHORIZED, ERROR_CODE, bizErrorMsg);
    }
    public TokenInvalidException(int bizErrorCode, String bizErrorMsg)
    {
        super(HttpStatus.UNAUTHORIZED, bizErrorCode, bizErrorMsg);
    }

}
