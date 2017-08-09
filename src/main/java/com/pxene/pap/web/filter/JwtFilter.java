package com.pxene.pap.web.filter;

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
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pxene.pap.domain.beans.AccessTokenBean;
import com.pxene.pap.domain.beans.ResponseBean;
import com.pxene.pap.exception.BaseException;
import com.pxene.pap.exception.TokenInvalidException;
import com.pxene.pap.exception.TokenOverdueException;

public class JwtFilter implements Filter
{
    private static final String BEARER = "Bearer";
    
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        httpResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpResponse.addHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
        httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.addHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,PATCH,HEAD");
        httpResponse.addHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        httpResponse.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod()))
        {
            chain.doFilter(request, response);
            return;
        }
        
        BaseException exception = null;
        
        HttpSession httpSession = httpRequest.getSession();
        
        AccessTokenBean tokenInSession = (AccessTokenBean) httpSession.getAttribute("access-token");
        
        String token = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        
        if (!StringUtils.isEmpty(token) && token.startsWith(BEARER) && tokenInSession != null)
        {
            token = token.substring(token.indexOf(BEARER) + 6);
            
            if (!token.trim().equals(tokenInSession.getToken().trim()))
            {
                exception = new TokenInvalidException();
            }
            else
            {
                if (new Date(tokenInSession.getExpiresAt()).after(new Date()))
                {
                    chain.doFilter(request, response);
                    return;
                }
                else
                {
                    exception = new TokenOverdueException();
                }
            }
        }
        else 
        {
        	exception = new TokenInvalidException();
        	chain.doFilter(request, response);
            return;
        }
        
        httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        
        ObjectMapper mapper = new ObjectMapper();
        ResponseBean result = new ResponseBean();
    	result.setCode(exception.getCode());
        result.setMessage(exception.getMessage());
        httpResponse.getWriter().write(mapper.writeValueAsString(result));
    }
    
    @Override
    public void destroy()
    {
        
    }
}
