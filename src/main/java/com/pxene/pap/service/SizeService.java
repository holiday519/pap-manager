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

import com.pxene.pap.domain.beans.SizeBean;
import com.pxene.pap.domain.models.SizeModel;
import com.pxene.pap.domain.models.SizeModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.SizeDao;

@Service
public class SizeService extends BaseService {

	@Autowired
	private SizeDao sizeDao;

	/**
	 * 添加尺寸
	 * @param bean
	 * @throws Exception
	 */
	@Transactional
	public void createsize(SizeBean bean) throws Exception {
		SizeModel model = modelMapper.map(bean, SizeModel.class);
		String id = UUID.randomUUID().toString();
		model.setId(id);
		try {
			sizeDao.insertSelective(model);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
		BeanUtils.copyProperties(model, bean);
	}

	/**
	 * 修改尺寸
	 * 
	 * @param id
	 * @param bean
	 */
	@Transactional
	public void updatesize(String id, SizeBean bean) throws Exception {
		SizeModel sizeInDB = sizeDao.selectByPrimaryKey(id);
		if (sizeInDB == null) {
			throw new ResourceNotFoundException();
		}

		SizeModel model = modelMapper.map(bean, SizeModel.class);
		model.setId(id);
		try {
			sizeDao.updateByPrimaryKeySelective(model);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
	}

	/**
	 * 删除尺寸
	 * 
	 * @param id
	 */
	@Transactional
	public void deletesize(String id) throws Exception {
		SizeModel sizeInDB = sizeDao.selectByPrimaryKey(id);
		if (sizeInDB == null) {
			throw new ResourceNotFoundException();
		}
		sizeDao.deleteByPrimaryKey(id);
	}

	/**
	 * 根据Id查询尺寸
	 * 
	 * @param id
	 * @return
	 */
	public SizeBean selectsize(String id) throws Exception {
		SizeModel sizeModel = sizeDao.selectByPrimaryKey(id);
		if (sizeModel == null) {
			throw new ResourceNotFoundException();
		}
		SizeBean bean = modelMapper.map(sizeModel, SizeBean.class);

		return bean;
	}
	/**
	 * 查询尺寸列表
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<SizeBean> selectsizes(String name) throws Exception {
		SizeModelExample example = new SizeModelExample();
		if (!StringUtils.isEmpty(name)) {
			example.createCriteria().andNameLike("%" + name + "%");
		}
		List<SizeBean> list = new ArrayList<SizeBean>();
		List<SizeModel> models = sizeDao.selectByExample(example);
		
		if (models == null || models.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		for (SizeModel model : models) {
			list.add(modelMapper.map(model, SizeBean.class));
		}
		
		return list;
	}

}
