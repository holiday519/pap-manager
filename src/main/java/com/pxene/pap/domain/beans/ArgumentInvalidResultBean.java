package com.pxene.pap.domain.beans;

public class ArgumentInvalidResultBean
{
    private String field;
    private Object rejectedValue;
    private String defaultMessage;
    
    
    public String getField()
    {
        return field;
    }
    public void setField(String field)
    {
        this.field = field;
    }
    
    public Object getRejectedValue()
    {
        return rejectedValue;
    }
    public void setRejectedValue(Object rejectedValue)
    {
        this.rejectedValue = rejectedValue;
    }
    
    public String getDefaultMessage()
    {
        return defaultMessage;
    }
    public void setDefaultMessage(String defaultMessage)
    {
        this.defaultMessage = defaultMessage;
    }
    
    
    public ArgumentInvalidResultBean()
    {
        super();
    }
    public ArgumentInvalidResultBean(String field, Object rejectedValue, String defaultMessage)
    {
        super();
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.defaultMessage = defaultMessage;
    }
    
    
    @Override
    public String toString()
    {
        return "ArgumentInvalidResult [field=" + field + ", rejectedValue=" + rejectedValue + ", defaultMessage=" + defaultMessage + "]";
    }
}
