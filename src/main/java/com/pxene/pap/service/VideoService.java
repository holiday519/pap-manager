package com.pxene.pap.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.beans.VideoBean;
import com.pxene.pap.domain.model.basic.VideoModel;
import com.pxene.pap.repository.mapper.basic.VideoModelMapper;

@Service
public class VideoService {
	
	@Autowired
	private VideoModelMapper videoMapper;
	
	/**
	 * 修改视频信息
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public String updateVideo(VideoBean bean) throws Exception{
		String uuid = bean.getUuid();
    	String name = bean.getName();
    	String path = bean.getPath();
    	String imageId = bean.getImageId();
    	String type = bean.getType();
    	String size = bean.getSize();
    	Float volume = bean.getVolume();
    	Integer timeLength = bean.getTimelength();
    	VideoModel video = new VideoModel();
    	video.setId(uuid);
    	video.setName(name);
    	video.setPath(path);
    	video.setTypeId(type);
    	video.setSizeId(size);
    	video.setPath(path);
    	video.setVolume(volume);
    	video.setTimeLength(timeLength);
    	video.setImageId(imageId);
    	int num = videoMapper.updateByPrimaryKeySelective(video);
    	if (num > -1) {
    		return bean.getUuid();
    	}
    	return "操作失败";
	}
	 
}
