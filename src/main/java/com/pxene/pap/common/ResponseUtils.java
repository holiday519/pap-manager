package com.pxene.pap.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


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
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            
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
    
    public static String sendReponse(int code, Object o, HttpServletResponse response) throws JsonProcessingException
    {
        if (o != null)
        {
            response.setStatus(code);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(Include.NON_NULL);
            return objectMapper.writeValueAsString(o);
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }
    public static String sendReponse(int code, String val, HttpServletResponse response)
    {
        response.setStatus(code);
        return ResponseUtils.toNormalResponse(val);
    }
    public static String sendReponse(int code, String key, String val, HttpServletResponse response)
    {
        response.setStatus(code);
        return ResponseUtils.toNormalResponse(key, val);
    }
    public static String sendResponse(String key, String val, int statusCode, String statusDesc, HttpServletResponse response)
    {
        response.setStatus(statusCode);
        if (StringUtils.isEmpty(key) && StringUtils.isEmpty(val))
        {
            return ResponseUtils.toNormalResponse(statusDesc);
        }
        return ResponseUtils.toNormalResponse(key, val);
    }
    
    public static String sendHttp400(HttpServletResponse response)
    {
        return sendResponse(null, null, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), response);
    }
    
    public static String sendHttp500(HttpServletResponse response)
    {
        return sendResponse(null, null, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(), response);
    }
}
