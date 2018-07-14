package com.pxene.pap.domain.beans;

import java.io.Serializable;

public class AccessTokenBean implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String token;
    
    private String tokenType;
    
    private long expiresAt;
    
    private long issuedAt;
    
    private String userid;
    
    
    public String getToken()
    {
        return token;
    }
    public void setToken(String token)
    {
        this.token = token;
    }
    public String getTokenType()
    {
        return tokenType;
    }
    public void setTokenType(String tokenType)
    {
        this.tokenType = tokenType;
    }
    public long getExpiresAt()
    {
        return expiresAt;
    }
    public void setExpiresAt(long expiresAt)
    {
        this.expiresAt = expiresAt;
    }
    public long getIssuedAt()
    {
        return issuedAt;
    }
    public void setIssuedAt(long issuedAt)
    {
        this.issuedAt = issuedAt;
    }
    public String getUserid()
    {
        return userid;
    }
    public void setUserid(String userid)
    {
        this.userid = userid;
    }
    
    
    @Override
    public String toString()
    {
        return "AccessToken [accessToken=" + token + ", tokenType=" + tokenType + ", expiresAt=" + expiresAt + ", issuedAt=" + issuedAt + ", userid=" + userid + "]";
    }
}
