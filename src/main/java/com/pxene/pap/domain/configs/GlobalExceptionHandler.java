package com.pxene.pap.domain.configs;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.domain.beans.ArgumentInvalidResult;
import com.pxene.pap.domain.beans.ResponseResult;
import com.pxene.pap.exception.BaseException;

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
    public ResponseResult handleException(Exception exception, HttpServletResponse response)
    {
        if (!(exception instanceof BaseException))
        {
            if (MethodArgumentNotValidException.class.isInstance(exception))
            {
                LOGGER.warn(exception.toString());
                List<ArgumentInvalidResult> invalidArguments = getBindResultErrors(exception);
                
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                return new ResponseResult(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), invalidArguments);
            }
            
            LOGGER.error(exception.toString());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name());
        }
        else
        {
            BaseException baseException = (BaseException) exception;
            LOGGER.debug(baseException.toString());
            
            int httpStatusCode = baseException.getHttpStatus().value();
            String httpStatusMsg = baseException.getHttpStatus().name();
            
            int bizStatusCode = baseException.getCode();
            String bizStatusMsg = baseException.getMessage();

            response.setStatus(httpStatusCode);
            return new ResponseResult(bizStatusCode == 0 ? httpStatusCode : bizStatusCode, StringUtils.isEmpty(bizStatusMsg) ? httpStatusMsg : bizStatusMsg);
        }
    }
    
    
    /**
     * 从原始的MethodArgumentNotValidException异常中解析出原始错误信息，按需重新封装后返回
     * @param exception     原始异常对象
     * @return              返回非法的字段名称，原始值，错误信息 
     */
    private List<ArgumentInvalidResult> getBindResultErrors(Exception exception)
    {
        MethodArgumentNotValidException notValidException = (MethodArgumentNotValidException) exception;
        BindingResult bindingResult = notValidException.getBindingResult();
        List<ArgumentInvalidResult> invalidArguments = new ArrayList<ArgumentInvalidResult>();
        
        for (FieldError error : bindingResult.getFieldErrors())
        {
            ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult(error.getField(), error.getRejectedValue(), error.getDefaultMessage());
            invalidArguments.add(invalidArgument);
        }
        return invalidArguments;
    }
}
