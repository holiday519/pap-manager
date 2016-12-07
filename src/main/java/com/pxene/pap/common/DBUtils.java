package com.pxene.pap.common;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;

public class DBUtils
{
    private static final String SQL_TEMP_UPDATE = "UPDATE {0} SET {1} WHERE {2}";
    

    public static String buildUpdateSQLByObject(String tableName, String whereStr, Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        Map<String, String> describe = BeanUtils.describe(object);
        
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : describe.entrySet())
        {
            if (!"class".equals(entry.getKey()) && !StringUtils.isEmpty(entry.getValue()))
            {
                String tmpStr = entry.getKey() + " = " + StringUtils.quoteIfString(entry.getValue());
                stringBuilder.append(tmpStr);
                stringBuilder.append(", ");
            }
        }
        
        String setStr = null;
        String originStr = stringBuilder.toString();
        if (!StringUtils.isEmpty(originStr))
        {
            originStr = originStr.trim();
            if (originStr.endsWith(","))
            {
                setStr = originStr.substring(0, originStr.lastIndexOf(","));
                
                if (!StringUtils.isEmpty(setStr))
                {
                    return MessageFormat.format(SQL_TEMP_UPDATE, tableName, setStr, whereStr);
                }
            }
        }
        
        return null;
    }
}
