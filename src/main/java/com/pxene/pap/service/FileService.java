package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pxene.pap.common.FileUtils;
import com.pxene.pap.domain.beans.ImageBean;
import com.pxene.pap.domain.beans.MediaBean;
import com.pxene.pap.domain.beans.VideoBean;

@Service
public class FileService
{
    @Autowired
    private Environment env;
    
    
    public List<MediaBean> uploadFiles(MultipartFile[] files)
    {
        String uploadDir = env.getProperty("pap.upload.dir");
        
        List<MediaBean> medias = new ArrayList<>();
        
        for (MultipartFile file : files)
        {
            MediaBean uploadedFile = FileUtils.uploadFile(uploadDir, file);
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
    
    
    
}
