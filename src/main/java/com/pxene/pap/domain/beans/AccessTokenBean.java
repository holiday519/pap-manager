package com.pxene.pap.domain.beans;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenBean implements Serializable
{
    private static final long serialVersionUID = 1L;

    @JsonProperty("token")
    private String accessToken;
    
    @JsonProperty("token_type")
    private String tokenType;
    
    @JsonProperty("expires_at")
    private long expiresAt;
    
    @JsonProperty("issued_at")
    private long issuedAt;
    
    @JsonProperty("userid")
    private String userid;
    
    
    public String getAccessToken()
    {
        return accessToken;
    }
    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
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
        return "AccessToken [accessToken=" + accessToken + ", tokenType=" + tokenType + ", expiresAt=" + expiresAt + ", issuedAt=" + issuedAt + ", userid=" + userid + "]";
    }
}
