package com.pxene.pap.web;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pxene.pap.common.beans.user.SysUser;
import com.pxene.pap.service.SysUserService;


@Controller
@RequestMapping(value = "/v1/")
public class SysUserController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserController.class);
    
    @Autowired
    private Environment env;
    
    @Autowired
    private SysUserService userService;
    
    
    @RequestMapping(value = "auth", method = RequestMethod.POST)
    public String authenticate(@RequestBody SysUser user, HttpServletResponse response)
    {
        LOGGER.debug("Received body params User {}.", user);
        String tokenSecret = env.getProperty("dmp.token.secret");
        
        String username = user.getUsername();
        String password = user.getPassword();
        
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        SysUser userInDB = (SysUser) userService.loadUserByUsername(username);
        
        
        if (userInDB != null)
        {
        }
        
        
        return tokenSecret;
    }
}
