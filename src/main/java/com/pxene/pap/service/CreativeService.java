package com.pxene.pap.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.domain.beans.CreativeBean;
import com.pxene.pap.domain.model.basic.CreativeMaterialModel;
import com.pxene.pap.domain.model.basic.CreativeMaterialModelExample;
import com.pxene.pap.domain.model.basic.CreativeModel;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.NotFoundException;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.CreativeMaterialDao;
import com.pxene.pap.repository.basic.InfoFlowDao;

@Service
public class CreativeService extends BaseService {
	
	@Autowired
	private CreativeDao creativeDao;
	
	@Autowired
	private InfoFlowDao infoFlowDao;
	
	@Autowired
	private CreativeMaterialDao creativeMaterialDao;
	
	/**
	 * 创建创意
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void createCreative(CreativeBean bean) throws Exception {
		
		CreativeModel model = modelMapper.map(bean, CreativeModel.class);
		String creativeId = UUID.randomUUID().toString();
		bean.setId(creativeId);
		//添加创意——素材关联关系
		addCreativeMaterial(bean);
		//创意表数据添加
		creativeDao.insertSelective(model);
		BeanUtils.copyProperties(model, bean);
	}
	
	/**
	 * 编辑创意
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void updateCreative(String creativeId, CreativeBean bean) throws Exception {
		if (!StringUtils.isEmpty(bean.getId())) {
			throw new IllegalArgumentException();
		}
		
		CreativeModel creativeInDB = creativeDao.selectByPrimaryKey(creativeId);
		if (creativeInDB == null || StringUtils.isEmpty(creativeInDB.getId())) {
			throw new NotFoundException();
		}
		
		bean.setId(creativeId);
		CreativeModel creative = modelMapper.map(bean, CreativeModel.class);
		//先删除关联关系
		deleteCreativeMaterial(creativeId);
		//添加创意——素材关联关系
		addCreativeMaterial(bean);
		//创意表数据添加
		creativeDao.updateByPrimaryKeySelective(creative);
	}
	
	/**
	 * 删除创意——素材关联关系
	 * @param creativeId
	 */
	@Transactional
	public void  deleteCreativeMaterial(String creativeId) throws Exception {
		CreativeMaterialModelExample example = new CreativeMaterialModelExample();
		example.createCriteria().andCreativeIdEqualTo(creativeId);
		creativeMaterialDao.deleteByExample(example);
	}
	
	/**
	 * 添加创意——素材关联关系
	 * @param bean
	 */
	@Transactional
	public void addCreativeMaterial(CreativeBean bean) throws Exception {
		String creativeId = bean.getId();
		List<Float> prices = bean.getPrice();
		List<String> materialIds = bean.getMaterialIds();
		List<String> types = bean.getCreativeType();
		if (materialIds != null && !materialIds.isEmpty()) {
			for (int i = 0; i < materialIds.size(); i++) {
				String mapid = UUID.randomUUID().toString();
				String materialId = materialIds.get(i);
				String type = types.get(i);
				Float price = prices.get(i);
				CreativeMaterialModel cmModel = new CreativeMaterialModel();
				cmModel.setId(mapid);
				cmModel.setCreativeId(creativeId);
				cmModel.setMaterialId(materialId);
				cmModel.setCreativeType(type);
				cmModel.setPrice(new BigDecimal(price));
				creativeMaterialDao.insertSelective(cmModel);
			}
		}
	}
	
	@Transactional
	public void deleteCreative(String creativeId) throws Exception {
		CreativeModel creativeInDB = creativeDao.selectByPrimaryKey(creativeId);
		if (creativeInDB == null || StringUtils.isEmpty(creativeInDB.getId())) {
			throw new NotFoundException();
		}
		
		CreativeMaterialModelExample cmExample = new CreativeMaterialModelExample();
		cmExample.createCriteria().andCreativeIdEqualTo(creativeId);
		// 删除信息流创意；图片和视频不用删除
		List<CreativeMaterialModel> cmList = creativeMaterialDao.selectByExample(cmExample);
		if (cmList != null && !cmList.isEmpty()) {
			for (CreativeMaterialModel cm : cmList) {
				String cmId = cm.getMaterialId();
				infoFlowDao.deleteByPrimaryKey(cmId);
			}
		}
		// 删除关联关系表中数据
		creativeMaterialDao.deleteByExample(cmExample);
		// 删除创意数据
		creativeDao.deleteByPrimaryKey(creativeId);
	}
}
