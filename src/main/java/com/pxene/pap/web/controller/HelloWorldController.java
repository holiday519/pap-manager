package com.pxene.pap.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public String sayHello(HttpServletRequest request, HttpServletResponse response)
    {
        System.out.println(request.getSession().getMaxInactiveInterval());
        redisUtils.increment("tony", "age", 1);
        return "Hello World";
    }
}
