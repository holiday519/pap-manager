package com.pxene.pap.common;


import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;

public class TokenUtils
{
    private static final String BEARER = "Bearer";;

    // 从Token中解析出userid
    public static String parseUserIdInToken(Environment env, String token)
    {
        return parseFromToken(env, token, "userid");
    }
    
    // 从Token中解析出username
    public static String parseUsernameInToken(Environment env, String token)
    {
        return parseFromToken(env, token, "username");
    }

    /**
     * 从Token中抽取指定的值
     * @param env       Spring配置环境
     * @param token     JWT规范Token
     * @param key       JWT中保存的属性名
     * @return
     */
    private static String parseFromToken(Environment env, String token, String key)
    {
        if (!StringUtils.isEmpty(token) && token.startsWith(BEARER))
        {
            try
            {
                token = token.substring(token.indexOf(BEARER) + 6).trim();
            }
            catch (RuntimeException e)
            {
                e.printStackTrace();
            }
        }
        
        String tokenSecret = env.getProperty("dmp.token.secret");
        Claims parseJWT = JwtUtils.parseJWT(token, tokenSecret);
        if (parseJWT != null)
        {
            String userId = (String) parseJWT.get(key);
            return userId;
        }
        return null;
    }
    
    // 获得当前的方法名
    public static String getCurrentMethodName(Thread currentThread)
    {
        String methodName = currentThread.getStackTrace()[1].getMethodName();
        return methodName;
    }
}
