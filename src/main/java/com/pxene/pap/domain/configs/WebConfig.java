package com.pxene.pap.domain.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter 
{
    @Autowired
    private Environment env;
    

    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        String excelMapURL = env.getProperty("pap.excel.download.mapUrl");
        String excelSavePath = env.getProperty("pap.excel.savePath");
        
        //添加Excel文件下载映射
        registry.addResourceHandler(excelMapURL).addResourceLocations("file:" + excelSavePath);
        super.addResourceHandlers(registry);
    }
    
}
