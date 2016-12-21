package com.pxene.pap.web.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.constant.HttpStatusCode;
import com.pxene.pap.domain.beans.CreativeBean;
import com.pxene.pap.domain.beans.ImageBean;
import com.pxene.pap.service.FileService;

@RestController
public class FileController
{
    @Autowired
    private FileService fileService;
            
    
    @RequestMapping(value = "/tempfiles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String uploadFileToTempDir(@RequestPart(value = "file", required = true) MultipartFile file, HttpServletResponse response) throws Exception
    {
        ImageBean imageEntityBean = (ImageBean) fileService.saveFileToTempDir(file);
        if (imageEntityBean != null)
        {
            imageEntityBean.setId(UUID.randomUUID().toString());
            
            return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), "path", imageEntityBean.getPath(), response);
        }
        else
        {
            return ResponseUtils.sendHttp500(response);
        }
    }
    
}
