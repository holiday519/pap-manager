package com.pxene.pap.common.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pxene.pap.common.constant.HttpStatusCode;


public class ResponseUtils
{
    public static String toNormalResponse(String msg)
    {
        return toNormalResponse("msg", msg);
    }
    public static String toNormalResponse(String key, String val)
    {
        JsonObject obj = new JsonObject();
        obj.addProperty(key, val);
        
        return obj.toString();
    }
    public static String toNormalResponse(String key, double val)
    {
        JsonObject obj = new JsonObject();
        obj.addProperty(key, val);
        
        return obj.toString();
    }
    public static String toNormalResponse(String key, List<String> vals)
    {
        JsonObject obj = new JsonObject();
        JsonArray jsonarray = new JsonArray();
        for (String val : vals)
        {
            jsonarray.add(val);
        }
        obj.add(key, jsonarray );
        
        return obj.toString();
    }
    
    /**
     * 从BindingResult对象中取出由JSR-303校验后的错误，并构建成一个JSON对象的字符串表示。
     * @param result    hibernate-valid校验结果对象
     * @param response
     * @return
     */
    public static String getValidateErrors(BindingResult result, HttpServletResponse response)
    {
        if (result != null && result.hasErrors())
        {
            response.setStatus(HttpStatusCode.BAD_REQUEST);
            
            List<ObjectError> allErrors = result.getAllErrors();
            List<String> allErrorMsgs = new ArrayList<>(allErrors.size());
            
            for (ObjectError objectError : allErrors)
            {
                allErrorMsgs.add(objectError.getDefaultMessage());
            }
            
            return ResponseUtils.toNormalResponse("errors", allErrorMsgs);
        }
        return null;
    }
    
    public static String sendReponse(Logger logger, int code, Object o, HttpServletResponse resonse)
    {
        logger.debug(o.toString());
        try
        {
            return new ObjectMapper().writeValueAsString(o);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public static String sendReponse(Logger logger, int code, String val, HttpServletResponse response)
    {
        logger.debug(val);
        response.setStatus(code);
        return ResponseUtils.toNormalResponse(val);
    }
    public static String sendReponse(Logger logger, int code, String key, String val, HttpServletResponse response)
    {
        logger.debug(key + ":" + val);
        response.setStatus(code);
        return ResponseUtils.toNormalResponse(key, val);
    }
    public static String sendResponse(Logger logger, String key, String val, int statusCode, String statusDesc, HttpServletResponse response)
    {
        response.setStatus(statusCode);
        if (StringUtils.isEmpty(key) && StringUtils.isEmpty(val))
        {
            return ResponseUtils.toNormalResponse(statusDesc);
        }
        return ResponseUtils.toNormalResponse(key, val);
    }
    
    public static String sendHttp400(Logger logger, HttpServletResponse response)
    {
        return sendResponse(logger, null, null, HttpStatusCode.BAD_REQUEST, HttpStatusCode.STR_BAD_REQUEST, response);
    }
    
    public static String sendHttp500(Logger logger, HttpServletResponse response)
    {
        return sendResponse(logger, null, null, HttpStatusCode.INTERNAL_SERVER_ERROR, HttpStatusCode.STR_INTERNAL_SERVER_ERROR, response);
    }
}
