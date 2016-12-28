package com.pxene.pap.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

import javax.imageio.ImageIO;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pxene.pap.domain.beans.ImageBean;
import com.pxene.pap.domain.beans.MediaBean;
import com.pxene.pap.domain.beans.VideoBean;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import it.sauronsoftware.jave.VideoSize;

public class FileUtils
{
    public static File convert(MultipartFile file) throws IOException
    {    
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile(); 
        FileOutputStream fos = new FileOutputStream(convFile); 
        fos.write(file.getBytes());
        fos.close(); 
        return convFile;
    }
    
    /**
     * 上传文件
     * @param uploadDir
     * @param fileName
     * @param file
     * @return path
     */
    public static String uploadFile(String uploadDir, String fileName, MultipartFile file)
    {
        String path = null;
        try
        {
            String name = file.getOriginalFilename();
            String fileExtension = getFileExtensionByDot(name);
            path = uploadDir + fileName + "." + fileExtension;
            
            // 上传至本地
            org.apache.commons.io.FileUtils.writeByteArrayToFile(new File(path), file.getBytes(), false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return path;
    }
    
    /**
     * 检查上传素材属性
     * @param file
     * @return
     */
    public static MediaBean checkFile(MultipartFile file)
    {
    	MediaBean result = null;
    	
    	try
    	{
    		String name = file.getOriginalFilename();
    		String contentType = file.getContentType();
    		String fileExtension = getFileExtensionByDot(name);
    		float volume = file.getSize() / 1024.0f;
    		int width = 0;
    		int height = 0;
    		
    		if (contentType.startsWith("image"))
    		{
    			BufferedImage sourceImg = ImageIO.read(file.getInputStream());
    			width = sourceImg.getWidth();
    			height = sourceImg.getHeight();
    			
    			result = new ImageBean(width, height);
    		}
    		else if (contentType.startsWith("video"))
    		{
    			Encoder encoder = new Encoder();
    			
    			File convFile = com.pxene.pap.common.FileUtils.convert(file);
    			
    			MultimediaInfo multimediaInfo = encoder.getInfo(convFile);
    			int timeLength = new BigDecimal((double) multimediaInfo.getDuration()/1000).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();// 视频时长
    			VideoSize videoSize = multimediaInfo.getVideo().getSize();
    			width = videoSize.getWidth();
    			height = videoSize.getHeight();
    			
    			result = new VideoBean(width, height, timeLength);
    		}
    		
    		// 设置基本属性
    		result.setName(name);
    		result.setType(fileExtension);
    		result.setVolume(volume);
    		
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		return null;
    	}
    	return result;
    }
    
    
    
    public static String getFileExtensionByContentType(String contentType)
    {
        return getFileExtension("/", contentType);
    }
    public static String getFileExtensionByDot(String contentType)
    {
        return getFileExtension(".", contentType);
    }
    public static String getFileExtension(String seperator, String source)
    {
        String[] tokenizeToStringArray = StringUtils.tokenizeToStringArray(source, seperator, true, true);
        if (tokenizeToStringArray.length >= 2)
        {
            return tokenizeToStringArray[tokenizeToStringArray.length-1];
        }
        return null;
    }
}
