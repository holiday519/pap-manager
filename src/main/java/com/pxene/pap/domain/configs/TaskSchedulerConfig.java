package com.pxene.pap.domain.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("com.pxene.pap")
@EnableScheduling
public class TaskSchedulerConfig
{
    
}
