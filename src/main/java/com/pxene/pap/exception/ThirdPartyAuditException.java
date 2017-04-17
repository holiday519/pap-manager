package com.pxene.pap.exception;

import org.springframework.http.HttpStatus;

public class ThirdPartyAuditException extends BaseException {

	private static final long serialVersionUID = 1315817257677433229L;
	public static final int ERROR_CODE = 0x02;
	public static final String ERROR_MSG = "third-party audit failure";
	
	public ThirdPartyAuditException()
    {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_CODE, ERROR_MSG);
    }
    public ThirdPartyAuditException(String bizErrorMsg)
    {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_CODE, bizErrorMsg);
    }
    public ThirdPartyAuditException(int bizErrorCode, String bizErrorMsg)
    {
        super(HttpStatus.INTERNAL_SERVER_ERROR, bizErrorCode, bizErrorMsg);
    }

}
