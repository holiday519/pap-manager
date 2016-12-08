package com.pxene.pap.web.controller;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.common.RedisUtils;
import com.pxene.pap.domain.beans.SysRole;
import com.pxene.pap.domain.model.basic.AdvertiserModel;
import com.pxene.pap.repository.SysRoleRepository;
import com.pxene.pap.repository.mapper.basic.AdvertiserModelMapper;



@Controller
@RequestMapping(value = "/v1/test/")
public class HelloWorldController
{
    @Autowired
    private RedisUtils redisUtils;
    
    @Autowired
    private SysRoleRepository sysRoleRepository;
    
    
    @Autowired
    private AdvertiserModelMapper advertiserModelMapper;
    
    
    @RequestMapping(value = "helloworld")
    public String sayHello(HttpServletRequest request, HttpServletResponse response)
    {
        System.out.println(request.getSession().getMaxInactiveInterval());
        redisUtils.increment("tony", "age", 1);
        return "Hello World";
    }
    
    @RequestMapping(value = "mybatis", method = RequestMethod.GET)
    @ResponseBody
    public String testMyBatis()
    {
        String id = UUID.randomUUID().toString();
        AdvertiserModel advertiserModel = new AdvertiserModel();
        advertiserModel.setId(id);
        advertiserModel.setName("于伟超");
        
        int affectedRows = advertiserModelMapper.insert(advertiserModel);
        
        if (affectedRows > 0)
        {
            return advertiserModelMapper.selectByPrimaryKey(id).toString();
        }
        
        return null;
    }
    
    @RequestMapping(value = "jpa", method = RequestMethod.POST)
    @ResponseBody
    public String testJPA(@RequestBody SysRole sysRole) throws IllegalAccessException, InvocationTargetException
    {
        SysRole role = sysRoleRepository.findOne(sysRole.getId());
        
        BeanUtils.copyProperties(sysRole, role, getNullPropertyNames(sysRole));
        
        sysRoleRepository.save(role);
        
        return role.toString();
    }
    
    /**
     * 将源对象中属性的值为NULL的属性，全部筛选出来，构造成一个字符串数组
     * @param object    源对象
     * @return          值为NULL的属性名的数组
     */
    public static String[] getNullPropertyNames(Object object)
    {
        BeanWrapper beanWrapper = new BeanWrapperImpl(object);
        
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        
        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds)
        {
            Object srcValue = beanWrapper.getPropertyValue(pd.getName());
            if (srcValue == null)
            {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
