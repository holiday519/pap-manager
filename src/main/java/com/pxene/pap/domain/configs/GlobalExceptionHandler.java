package com.pxene.pap.domain.configs;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.domain.beans.ArgumentInvalidBean;
import com.pxene.pap.domain.beans.ResponseBean;
import com.pxene.pap.exception.BaseException;
import com.pxene.pap.exception.ServerFailureException;
import com.pxene.pap.exception.IllegalArgumentException;

/**
 * 全局异常处理类：针对所有的Controller抛出的异常。
 * <p>
 * 如果抛出的是自定义异常，则使用自定义异常类中的属性，包装一个通用错误对象（包含一个整型的code和一个字符串类型的msg）；
 * 如果抛出的是除自定义异常以外的异常，则统一使用HttpStatus Code 500作为响应码。
 * </p>
 * @author ningyu
 */
@ControllerAdvice
public class GlobalExceptionHandler
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseBean handleException(Exception exception, HttpServletResponse response)
    {
        if (!(exception instanceof BaseException))
        {
        	// bean中使用@Valid校验参数发生异常
            if (MethodArgumentNotValidException.class.isInstance(exception))
            {
            	LOGGER.warn("", exception);
                List<ArgumentInvalidBean> invalidArguments = getBindResultErrors(exception);
                
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                return new ResponseBean(IllegalArgumentException.ERROR_CODE, IllegalArgumentException.ERROR_MSG, invalidArguments);
            }
            
            LOGGER.error("", exception);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseBean(ServerFailureException.ERROR_CODE, ServerFailureException.ERROR_MSG);
        }
        else
        {
            BaseException baseException = (BaseException) exception;
            LOGGER.debug(baseException.toString());
            
            int httpStatusCode = baseException.getHttpStatus().value();
//            String httpStatusMsg = baseException.getHttpStatus().name();
            
            int bizStatusCode = baseException.getCode();
            String bizStatusMsg = baseException.getMessage();

            response.setStatus(httpStatusCode);
            return new ResponseBean(bizStatusCode, bizStatusMsg);
        }
    }
    
    
    /**
     * 从原始的MethodArgumentNotValidException异常中解析出原始错误信息，按需重新封装后返回
     * @param exception     原始异常对象
     * @return              返回非法的字段名称，原始值，错误信息 
     */
    private List<ArgumentInvalidBean> getBindResultErrors(Exception exception)
    {
        MethodArgumentNotValidException notValidException = (MethodArgumentNotValidException) exception;
        BindingResult bindingResult = notValidException.getBindingResult();
        List<ArgumentInvalidBean> invalidArguments = new ArrayList<ArgumentInvalidBean>();
        
        for (FieldError error : bindingResult.getFieldErrors())
        {
            ArgumentInvalidBean invalidArgument = new ArgumentInvalidBean(error.getField(), error.getRejectedValue(), error.getDefaultMessage());
            invalidArguments.add(invalidArgument);
        }
        return invalidArguments;
    }
}
