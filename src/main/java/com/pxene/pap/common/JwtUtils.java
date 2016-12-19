package com.pxene.pap.common;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.pxene.pap.domain.beans.AccessToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils
{
    public static AccessToken createJWT(String userid, long ttlMillis, String secret)
    {
        return createJWT(userid, null, null, null, ttlMillis, secret);
    }
    
    public static AccessToken createJWT(String userid, String issuer, String subject, String audience, long ttlMillis, String secret)
    {
        // 当前时间戳
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        
        // 1970年1月17日
        Date nbf = new Date(1357000000);
        
        // 签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        
        // 生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        
        // 添加构成JWT的参数
        JwtBuilder builder = Jwts.builder();
        builder.setHeaderParam("typ", "JWT");
        builder.claim("userid", userid);
        builder.setIssuer(issuer);                          // #-> 非必需。issuer: 请求实体，可以是发起请求的用户的信息，也可是jwt的签发者。
        builder.setSubject(subject);                        // #-> 非必需。subject: 该JWT所面向的用户.
        builder.setAudience(audience);                      // #-> 非必需。audience: 接收该JWT的一方。
        builder.setIssuedAt(now);                           // #-> 非必需。issued at： token创建时间，unix时间戳格式。
        builder.setNotBefore(nbf);                          // #-> 非必需。not before：如果当前时间在nbf里的时间之前，则Token不被接受；一般都会留一些余地，比如几分钟。    
        builder.signWith(signatureAlgorithm, signingKey);
        
        // 添加Token过期时间
        long expiresAt = 0;
        if (ttlMillis >= 0)
        {
            long expireMillis = nowMillis + ttlMillis;
            Date expire = new Date(expireMillis);
            expiresAt = expire.getTime();
            builder.setExpiration(expire).setNotBefore(now);// #-> 非必需。expire：指定token的生命周期。unix时间戳格式
        }
        
        // 生成JWT
        String compact = builder.compact();
        
        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(compact);
        accessToken.setExpiresAt(expiresAt);
        accessToken.setIssuedAt(nowMillis);
        accessToken.setTokenType("Bearer");
        accessToken.setUserid(userid);
        return accessToken;
    }

    public static Claims parseJWT(String jsonWebToken, String base64Security)
    {
        try
        {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security)).parseClaimsJws(jsonWebToken).getBody();
            return claims;
        }
        catch (Exception ex)
        {
            return null;
        }
    }
}
