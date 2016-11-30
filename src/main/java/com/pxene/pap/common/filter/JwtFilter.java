package com.pxene.pap.common.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pxene.pap.common.beans.AccessToken;
import com.pxene.pap.common.beans.ResponseResult;
import com.pxene.pap.common.constant.HttpStatusCode;
import com.pxene.pap.common.utils.TokenUtils;
import com.pxene.pap.repository.TokenDao;

public class JwtFilter implements Filter
{
    private static final String BEARER = "Bearer";

    @Autowired
    private Environment env;
    
    @Autowired
    private TokenDao tokenDao;
    
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        String msg = HttpStatusCode.STR_UNAUTHORIZED;
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        String token = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.isEmpty(token) && token.startsWith(BEARER))
        {
            token = token.substring(token.indexOf(BEARER) + 6);
            if (!StringUtils.isEmpty(token))
            {
                String username = TokenUtils.parseUsernameInToken(env, token.trim());
                AccessToken accessToken = tokenDao.load(username);
                if (accessToken != null)  
                {
                    if (new Date(accessToken.getExpiresAt()).after(new Date()))
                    {
                        chain.doFilter(request, response);
                        return;
                    }
                    else
                    {
                        msg = "Token has expired.";
                    }
                }
                else
                {
                    msg = "Invalid token.";
                }
            }
        }
        
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        ObjectMapper mapper = new ObjectMapper();
        ResponseResult result = new ResponseResult();
        result.setCode(HttpServletResponse.SC_UNAUTHORIZED);
        result.setMessage(msg);
        httpResponse.getWriter().write(mapper.writeValueAsString(result));
        return;
    }
    
    @Override
    public void destroy()
    {
        
    }
}
