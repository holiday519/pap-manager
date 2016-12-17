package com.pxene.pap.web.filter;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import com.google.common.util.concurrent.RateLimiter;
import com.google.gson.JsonObject;


/**
 * 自定义过滤器，限制接口调用频率.
 * @author ningyu
 */
public class RateLimiterFilter implements Filter
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RateLimiterFilter.class);
    
    private RateLimiter limiter = null;
    
    private int rateLimitPerSec = 1;
    
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        Resource resource = new ClassPathResource("application.properties");
        try
        {
            Properties loadedProperties = PropertiesLoaderUtils.loadProperties(resource);
            if (loadedProperties != null)
            {
                String property = loadedProperties.getProperty("dmp.web.rateLimitPerSec");
                if (!StringUtils.isEmpty(property))
                {
                    rateLimitPerSec = Integer.parseInt(property);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        limiter = RateLimiter.create(rateLimitPerSec); // RateLimiter.create(100) -> 100 request per second
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        if (limiter.tryAcquire())
        {
            String host = getRemoteAddress(req);
            LOGGER.debug("{} get access.", host);
            chain.doFilter(request, response);
        }
        else
        {
            
            String msg = "System limitation reached!";
            LOGGER.debug(msg);
            
            resp.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            resp.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
            resp.setHeader(HttpHeaders.RETRY_AFTER, "1");
            
            JsonObject object = new JsonObject();
            object.addProperty("msg", msg);
            object.addProperty("desc", rateLimitPerSec + " request per second");
            response.getWriter().write(object.toString());
            return;
        }
    }
    
    @Override
    public void destroy()
    {
        limiter = null;
        rateLimitPerSec = 1;
    }
    
    /**
     * Servlet获取客户端ip地址
     * @param request
     * @return
     */
    private String getRemoteAddress(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
        {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
