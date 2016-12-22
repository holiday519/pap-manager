package com.pxene.pap.web.controller;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.AccessTokenBean;
import com.pxene.pap.domain.beans.AuthBean;
import com.pxene.pap.domain.model.basic.UserModel;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.PasswordIncorrectException;
import com.pxene.pap.exception.UserNotExistException;
import com.pxene.pap.service.TokenService;


@Controller
public class TokenController
{
    @Autowired
    private TokenService tokenService;
    
    /**
     * 根据用户名、密码获取AccessToken。
     * @param user      包含用户名和用户密码的请求载荷，必填
     * @param request
     * @param response
     * @return
     * @throws AuthException 
     */
    @RequestMapping(value = "/auth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String login(@RequestBody AuthBean authBean, HttpServletResponse response) throws Exception
    {
        String userName = authBean.getUserName();
        String password = authBean.getPassword();
        
        // 校验请求参数
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password))
        {
            throw new IllegalArgumentException();
        }
        
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        UserModel userModel = (UserModel) tokenService.loadUserByUsername(userName);
        
        // 校验用户是否存在
        if (userModel == null)
        {
            throw new UserNotExistException();
        }
        
        String passwordInDB = userModel.getPassword();
        if (!StringUtils.isEmpty(passwordInDB) && md5Password.equals(passwordInDB))
        {
            // 生成accessToken
            AccessTokenBean token = tokenService.generateToken(userModel);
            
            // 将新生成的Token保存至Redis中（同时设定TTL）
            tokenService.saveToken(token);
            
            return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), token, response);
        }
        else
        {
            // 密码不正确
            throw new PasswordIncorrectException();
        }
    }
    
    @RequestMapping(value = "/auth/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void logout(@PathVariable(required = true) String userId, HttpServletResponse response) throws Exception
    {
        tokenService.deleteToken(userId);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
