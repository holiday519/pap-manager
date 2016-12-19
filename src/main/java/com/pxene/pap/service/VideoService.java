package com.pxene.pap.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.domain.beans.VideoBean;
import com.pxene.pap.domain.model.basic.VideoModel;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.NotFoundException;
import com.pxene.pap.repository.basic.VideoDao;

@Service
public class VideoService extends BaseService {
	
	@Autowired
	private VideoDao videoDao;
	
	/**
	 * 修改视频信息
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void updateVideo(String id, VideoBean bean) throws Exception {
		if (!StringUtils.isEmpty(bean.getUuid())) {
			throw new IllegalArgumentException();
		}

		VideoModel creativeInDB = videoDao.selectByPrimaryKey(id);
		if (creativeInDB == null || StringUtils.isEmpty(creativeInDB.getId())) {
			throw new NotFoundException();
		}

		String size = bean.getSize();
		VideoModel video = modelMapper.map(bean, VideoModel.class);
		video.setId(id);
		video.setSizeId(size);
		videoDao.updateByPrimaryKeySelective(video);
	}
	 
}
