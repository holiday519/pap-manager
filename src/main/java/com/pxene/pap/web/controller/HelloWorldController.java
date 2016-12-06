package com.pxene.pap.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.common.RedisUtils;


@Controller
@RequestMapping(value = "/v1/test/")
@ResponseBody
public class HelloWorldController
{
    @Autowired
    private RedisUtils redisUtils;
    
    
    @RequestMapping(value = "helloworld")
    public String sayHello()
    {
        redisUtils.increment("tony", "age", 1);
        return "Hello World";
    }
}
