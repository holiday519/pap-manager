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
     * @throws IOException 
     */
    public static String uploadFileToLocal(String uploadDir, String fileName, MultipartFile file) throws IOException
    {
        String path = null;
        String name = file.getOriginalFilename();
        String fileExtension = getFileExtensionByDot(name);
        path = uploadDir + fileName + "." + fileExtension;
        
        // 上传至本地
        org.apache.commons.io.FileUtils.writeByteArrayToFile(new File(path), file.getBytes(), false);
        
        return path;
    }
    
    public static String uploadFileToRemote(String host, int port, String username, String password, String uploadDir, String fileName, MultipartFile file)
    {
        String path = null;
        
        try
        {
            String name = file.getOriginalFilename();
            String fileExtension = getFileExtensionByDot(name);
            String fullName = fileName + "." + fileExtension;
            path = uploadDir + fullName;
            
            // 上传至远程
            ScpHelper scp = new ScpHelper(host, port, username, password);
            scp.putFile(file.getBytes(), fullName, uploadDir);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
        
        return path;
    }
    
    public static void copyRemoteFile(String host, int port, String username, String password, String from, String to) throws Exception
    {
        ScpHelper helper = new ScpHelper(host, port, username, password);
        helper.copy(from, to);
    }
    
    public static void deleteLocalFile(File file) throws IOException
    {
        if (file.isFile())
        {
            org.apache.commons.io.FileUtils.forceDelete(file);
        }
    }

    public static void deleteRemoteFile(String host, int port, String username, String password, String path) throws Exception
    {
        ScpHelper helper = new ScpHelper(host, port, username, password);
        helper.delete(path);
    }

    /**
     * 检查上传素材属性
     * @param file
     * @return
     */
    public static MediaBean checkFile(MultipartFile file)
    {
		try
    	{
    		String name = file.getOriginalFilename();
    		String contentType = file.getContentType();
    		String fileExtension = getFileExtensionByDotCode(name);
    		float volume = file.getSize() / 1024.0f;
    		int width = 0;
    		int height = 0;
    		
    		if (contentType.startsWith("image"))
    		{
    			BufferedImage sourceImg = ImageIO.read(file.getInputStream());
    			width = sourceImg.getWidth();
    			height = sourceImg.getHeight();
    			
    			ImageBean result = new ImageBean();
    			result.setHeight(height);
    			result.setWidth(width);
    			// 设置基本属性
        		result.setFormat(fileExtension);
        		result.setVolume(volume);
        		return result;
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
    			
    			VideoBean result = new VideoBean();
    			result.setHeight(height);
    			result.setWidth(width);
    			result.setTimelength(timeLength);
    			// 设置基本属性
        		result.setFormat(fileExtension);
        		result.setVolume(volume);
        		return result;
    		}
    		
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		return null;
    	}
    	return null;
    }
    
    
    
    public static String getFileExtensionByContentType(String contentType)
    {
        return getFileExtension("/", contentType);
    }
    public static String getFileExtensionByDot(String contentType)
    {
        return getFileExtension(".", contentType);
    }
    public static String getFileExtensionByDotCode(String contentType)
    {
    	String string = getFileExtension(".", contentType).toLowerCase();
    	if ("jpg".equals(string)) {
    		return "18";
    	} else if ("png".equals(string)) {
    		return "17";
    	} else if ("jpeg".equals(string)) {
    		return "18";
    	} else if ("gif".equals(string)) {
    		return "19";
    	} else if ("gif".equals(string)) {
    		return "19";
    	} else if ("flv".equals(string)) {
    		return "33";
    	} else if ("mp4".equals(string)) {
    		return "34";
    	}
        return "0";
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
