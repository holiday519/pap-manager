package com.pxene.pap.web.controller;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.constant.HttpStatusCode;
import com.pxene.pap.domain.beans.AccessToken;
import com.pxene.pap.domain.beans.SysUser;
import com.pxene.pap.domain.model.basic.UserModel;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.PasswordIncorrectAuthException;
import com.pxene.pap.exception.UserNotFoundAuthException;
import com.pxene.pap.service.TokenService;


@Controller
public class TokenController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenController.class);
    
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
    @RequestMapping(value = "/auth/tokens", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String authenticate(@RequestBody UserModel user, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOGGER.debug("Received body params User {}.", user);
        
        String username = user.getName();
        String password = user.getPassword();
        
        // 校验请求参数
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            throw new IllegalArgumentException();
        }
        
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        UserModel userInDB = (UserModel) tokenService.loadUserByUsername(username);
        
        // 校验用户是否存在
        if (userInDB == null)
        {
            throw new UserNotFoundAuthException();
        }
        
        String passwordInDB = userInDB.getPassword();
        if (!StringUtils.isEmpty(passwordInDB) && md5Password.equals(passwordInDB))
        {
            // 生成accessToken
            AccessToken token = tokenService.generateToken(userInDB);
            
            // 将Token保存在Session中
            HttpSession session = request.getSession();
            session.setAttribute(userInDB.getId(), token);
            
            return ResponseUtils.sendReponse(LOGGER, HttpStatusCode.CREATED, token, response);
        }
        else
        {
            // 密码不正确
            throw new PasswordIncorrectAuthException();
        }
    }
}
