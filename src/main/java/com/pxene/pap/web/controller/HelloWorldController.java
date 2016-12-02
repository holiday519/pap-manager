package com.pxene.pap.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/v1/test/")
@ResponseBody
public class HelloWorldController
{
    @RequestMapping(value = "helloworld")
    public String sayHello()
    {
        return "Hello World";
    }
}
