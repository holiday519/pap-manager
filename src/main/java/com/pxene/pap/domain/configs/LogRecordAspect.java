package com.pxene.pap.domain.configs;

import java.lang.reflect.Method;
import java.text.MessageFormat;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LogRecordAspect
{
    private static final LocalVariableTableParameterNameDiscoverer DISCOVERER = new LocalVariableTableParameterNameDiscoverer();
    private static final String PARAM_TEMPLATE = "{0}({1}) : {2}";
    private static final Logger LOGGER = LoggerFactory.getLogger(LogRecordAspect.class);
    
    
    @Pointcut("execution(* com.pxene.pap.web.*.*Controller.*(..))")
    public void executeController()
    {
    }
    
    @Pointcut("execution(* com.pxene.pap.service.*Service.*(..))")
    public void executeService()
    {
    }
    
    @Pointcut("executeController() || executeService()")
    public void executeControllerOrService()
    {
    }
    
    @Around(value = "executeControllerOrService()")
    public Object recordRequestParamsAndResult(ProceedingJoinPoint joinPoint) throws Throwable
    {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getStaticPart().getSignature();
        Method method = methodSignature.getMethod();
        
        String methodName = methodSignature.toShortString();
        String[] paramsNames = DISCOVERER.getParameterNames(method);
        Object[] paramsValues = joinPoint.getArgs();
        
        StringBuilder paramsBuilder = new StringBuilder("[Details for method '" + methodName + "' received request params] -> ");
        StringBuilder resultBuilder = new StringBuilder("[Details for method '" + methodName + "' return result] -> ");
        for (int i = 0; i < paramsNames.length; i++)
        {
            String paramName = paramsNames[i];
            Object paramValue = paramsValues[i];
            String paramType = (paramValue == null) ? null : paramValue.getClass().getSimpleName();
            String paramStr = MessageFormat.format(PARAM_TEMPLATE, paramName, paramType, paramValue);
            paramsBuilder.append(paramStr);
            
            if (i < paramsNames.length - 1)
            {
                paramsBuilder.append(", ");
            }
        }
        
        // 打印方法入参
        LOGGER.debug(paramsBuilder.toString());

        Object result = joinPoint.proceed();
        resultBuilder.append(result);
        
        // 打印方法返回值
        LOGGER.debug(resultBuilder.toString());
        
        return result;
    }
}
