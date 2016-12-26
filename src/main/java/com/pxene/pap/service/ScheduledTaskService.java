package com.pxene.pap.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTaskService
{
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    
    @Scheduled(cron = "0 */1 * * * *")
    public void reportCurrentTime()
    {
        System.out.println("在指定时间 " + dateFormat.format(new Date()) + "执行");
    }
}
