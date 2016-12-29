package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.domain.beans.ImageTmplBean;
import com.pxene.pap.domain.beans.SizeBean;
import com.pxene.pap.domain.beans.VideoTmplBean;
import com.pxene.pap.domain.model.basic.ImageTmplModel;
import com.pxene.pap.domain.model.basic.ImageTmplTypeModel;
import com.pxene.pap.domain.model.basic.SizeModel;
import com.pxene.pap.domain.model.basic.SizeModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.ImageTmplDao;
import com.pxene.pap.repository.basic.ImageTmplTypeDao;
import com.pxene.pap.repository.basic.SizeDao;

@Service
public class CreativeTmplService extends BaseService {

	@Autowired
	private ImageTmplDao imageTmplDao;
	
	@Autowired
	private ImageTmplTypeDao imageTmplTypeDao;
	
	/**
	 * 添加图片模版
	 * @param bean
	 * @throws Exception
	 */
	@Transactional
	public String addImageTmpl(ImageTmplBean bean) throws Exception {
		if (bean == null) {
			throw new IllegalArgumentException();
		}
		//如果id为null，则添加一个UUID
		String id = bean.getId();
		if (StringUtils.isEmpty(id)) {
			id = UUID.randomUUID().toString();
		}
		//如果图片类型ID 不为NULL，添加关联关系
		String imageTypeId = bean.getImageTypeId();
		if (!StringUtils.isEmpty(imageTypeId)) {
			ImageTmplTypeModel ittModel = new ImageTmplTypeModel();
			ittModel.setId(UUID.randomUUID().toString());
			ittModel.setImageTmplId(id);
			ittModel.setImageTypeId(imageTypeId);
			imageTmplTypeDao.insertSelective(ittModel);
		}
		//转换参数
		ImageTmplModel model = modelMapper.map(bean, ImageTmplModel.class);
		
		try {
			//添加图片模版
			imageTmplDao.insertSelective(model);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
		return id;
	}

	public void addVideoTmpl(VideoTmplBean bean) throws Exception {
//		if (bean == null) {
//			throw new IllegalArgumentException();
//		}
//		//如果id为null，则添加一个UUID
//		String id = bean.getId();
//		if (StringUtils.isEmpty(id)) {
//			id = UUID.randomUUID().toString();
//		}
//		String imageTypeId = bean.getImageTypeId();
//		if (!StringUtils.isEmpty(imageTypeId)) {
//			ImageTmplTypeModel ittModel = new ImageTmplTypeModel();
//			ittModel.setId(UUID.randomUUID().toString());
//			ittModel.setImageTmplId(id);
//			ittModel.setImageTypeId(imageTypeId);
//			imageTmplTypeDao.insertSelective(ittModel);
//		}
		
	}
	
}
