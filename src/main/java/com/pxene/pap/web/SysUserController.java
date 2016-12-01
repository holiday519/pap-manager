package com.pxene.pap.web;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.common.beans.user.SysUser;
import com.pxene.pap.common.constant.HttpStatusCode;
import com.pxene.pap.common.utils.ResponseUtils;
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
    
    
    @RequestMapping(value = "auth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String authenticate(@RequestBody SysUser user, HttpServletResponse response)
    {
        LOGGER.debug("Received body params User {}.", user);
        
        String username = user.getUsername();
        String password = user.getPassword();
        
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            return ResponseUtils.sendReponse(LOGGER, HttpStatusCode.BAD_REQUEST, "请求参数不正确", response);
        }
        
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        SysUser userInDB = (SysUser) userService.loadUserByUsername(username);
        
        if (userInDB == null)
        {
            return ResponseUtils.sendReponse(LOGGER, HttpStatusCode.UNAUTHORIZED, "用户名或密码错误", response);
        }
        
        String passwordInDB = userInDB.getPassword();
        if (!StringUtils.isEmpty(passwordInDB) && md5Password.equals(passwordInDB))
        {
            return ResponseUtils.sendReponse(LOGGER, HttpStatusCode.OK, "登陆成功", response);
        }
        
        return ResponseUtils.sendHttp500(LOGGER, response);
    }
}
