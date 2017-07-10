package com.pxene.pap.domain.beans.baidu;

public class Error extends BaseBean
{
    /**
     * 如果传入单个元素，则 index 为 0；如果传入的是数组，则 index 为出错的数组下标，从 0 开始。
     */
    private int index;
    
    /**
     * 错误码。
     */
    private int code;
    
    /**
     * 错误信息。
     */
    private String message;
    
    /**
     * 错误字段：发生错误的字段说明。
     */
    private String field;
    
    
    public int getIndex()
    {
        return index;
    }
    public void setIndex(int index)
    {
        this.index = index;
    }
    public int getCode()
    {
        return code;
    }
    public void setCode(int code)
    {
        this.code = code;
    }
    public String getMessage()
    {
        return message;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }
    public String getField()
    {
        return field;
    }
    public void setField(String field)
    {
        this.field = field;
    }
    
    
    public Error()
    {
        super();
    }
    public Error(int index, int code, String message, String field)
    {
        super();
        this.index = index;
        this.code = code;
        this.message = message;
        this.field = field;
    }
    
    
    @Override
    public String toString()
    {
        return "Error [index=" + index + ", code=" + code + ", message=" + message + ", field=" + field + "]";
    }
}
