package com.pxene.pap.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pxene.pap.common.FileUtils;
import com.pxene.pap.domain.beans.ImageBean;
import com.pxene.pap.domain.beans.MediaBean;
import com.pxene.pap.domain.beans.VideoBean;
import com.pxene.pap.domain.model.basic.AdvertiserModel;
import com.pxene.pap.exception.BadRequestException;

@Service
public class FileService
{
    private String tempDir;
    private String formalDir;
    
    
    @Autowired
    public FileService(Environment env)
    {
        tempDir = env.getProperty("pap.upload.tempDir");
        formalDir = env.getProperty("pap.upload.formalDir");
    }
    
    
    public List<MediaBean> uploadFiles(MultipartFile[] files)
    {
        List<MediaBean> medias = new ArrayList<>();
        
        for (MultipartFile file : files)
        {
            MediaBean uploadedFile = FileUtils.uploadFile(tempDir, UUID.randomUUID().toString(), file);
            if (uploadedFile != null)
            {
                if (uploadedFile instanceof VideoBean)
                {
                    VideoBean videoBean = (VideoBean) uploadedFile;
                    medias.add(videoBean);
                }
                if (uploadedFile instanceof ImageBean)
                {
                    ImageBean imageBean = (ImageBean) uploadedFile;
                    medias.add(imageBean);
                }
            }
        }
        return medias;
    }


    public boolean isFileExist(File file)
    {
        if (file != null && file.exists())
        {
            return true;
        }
        return false;
    }


    public MediaBean saveFileToTempDir(MultipartFile file)
    {
        MediaBean uploadedFile = FileUtils.uploadFile(tempDir, UUID.randomUUID().toString(), file);
        return uploadedFile;
    }


    public void copyTempToFormal(AdvertiserModel advertiserModel) throws Exception
    {
        String logoPath = advertiserModel.getLogoPath();
        String accountPath = advertiserModel.getAccountPath();
        String licensePath = advertiserModel.getLicensePath();
        String organizationPath = advertiserModel.getOrganizationPath();
        String icpPath = advertiserModel.getIcpPath();
        
        File destDir = new File(formalDir);
        
        try
        {
            if (!StringUtils.isEmpty(logoPath))
            {
                org.apache.commons.io.FileUtils.copyFileToDirectory(new File(logoPath), destDir);
            }
            if (!StringUtils.isEmpty(accountPath))
            {
                org.apache.commons.io.FileUtils.copyFileToDirectory(new File(accountPath), destDir);
            }
            if (!StringUtils.isEmpty(licensePath))
            {
                org.apache.commons.io.FileUtils.copyFileToDirectory(new File(licensePath), destDir);
            }
            if (!StringUtils.isEmpty(organizationPath))
            {
                org.apache.commons.io.FileUtils.copyFileToDirectory(new File(organizationPath), destDir);
            }
            if (!StringUtils.isEmpty(icpPath))
            {
                org.apache.commons.io.FileUtils.copyFileToDirectory(new File(icpPath), destDir);
            }
        }
        catch (FileNotFoundException exception)
        {
            throw new BadRequestException(exception.getMessage());
        }
        catch (Exception exception) 
        {
            throw exception;
        }
    }
}
