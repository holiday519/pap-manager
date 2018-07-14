package com.pxene.pap.constant;
import static com.pxene.pap.constant.CodeTableConstant.*;

public enum RealTimeDataEnum
{
    B1("B1", DISPLAY_AMOUNT), B2("B2", CLICK_AMOUNT), B3("B3",JUMP_AMOUNT ), B4("B4", COST), B5("B5", ADX_COST);
    
    private String code;
    private String name;
    
    
    public String getCode()
    {
        return code;
    }
    public void setCode(String code)
    {
        this.code = code;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    
    private RealTimeDataEnum(String code, String name)
    {
        this.code = code;
        this.name = name;
    }
    
    
}
