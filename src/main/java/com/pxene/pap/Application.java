/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pxene.pap;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.pxene.pap.web.filter.JwtFilter;
import com.pxene.pap.web.filter.RateLimiterFilter;


@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application
{
    private static Log logger = LogFactory.getLog(Application.class);
    public static String secret;

    
    public static void main(String[] args) throws Exception
    {
        // # 等效于：SpringApplication.run(Application.class, args);
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Mode.CONSOLE);
        app.run(args);
    }
    
    
    @Bean
    protected ServletContextListener listener()
    {
        return new ServletContextListener()
        {
            @Override
            public void contextInitialized(ServletContextEvent sce)
            {
                logger.info("ServletContext initialized");
            }
            
            @Override
            public void contextDestroyed(ServletContextEvent sce)
            {
                logger.info("ServletContext destroyed");
            }
        };
    }
    
    /**
     * 注册Filter：接口调用频率过滤器.
     * @return
     */
    @Bean
    protected FilterRegistrationBean rateLimiterFilterRegistration()
    {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        
        registrationBean.setFilter(new RateLimiterFilter());
        registrationBean.addUrlPatterns("/v1/*");
        
        return registrationBean;
    }
    
    /**
     * 注册Filter：自定义的Token认证过滤器
     * @return
     */
    @Bean
    protected FilterRegistrationBean customTokenFilterRegistrationBean()
    {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        
        Filter jwtFilter = new JwtFilter();
        registrationBean.setFilter(jwtFilter);
        
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/v1/test/*");
        registrationBean.setUrlPatterns(urlPatterns);
        
        return registrationBean;
    }
    
}
