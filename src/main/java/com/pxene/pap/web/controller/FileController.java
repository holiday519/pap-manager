package com.pxene.pap.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pxene.pap.constant.HttpStatusCode;
import com.pxene.pap.domain.beans.MediaBean;
import com.pxene.pap.web.service.FileService;

@Controller
public class FileController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);
    
    @Autowired
    private FileService fileService;
            
    
    @RequestMapping(value = "/files", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFiles(MultipartFile[] files, HttpServletResponse response) throws JsonProcessingException
    {
        List<MediaBean> uploadFiles = fileService.uploadFiles(files);
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatusCode.OK);
        return objectMapper.writeValueAsString(uploadFiles);
    }
    
}
