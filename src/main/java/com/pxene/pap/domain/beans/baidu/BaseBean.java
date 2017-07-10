package com.pxene.pap.domain.beans.baidu;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseBean
{
    public String toJsonString()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        String valueAsString;
        try
        {
            valueAsString = objectMapper.writeValueAsString(this);
        }
        catch (JsonProcessingException e)
        {
            return null;
        }
        return valueAsString;
    }
    
    public String toPrettyJsonString()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        String valueAsString;
        try
        {
            valueAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        }
        catch (JsonProcessingException e)
        {
            return null;
        }
        return valueAsString;
    }
}
