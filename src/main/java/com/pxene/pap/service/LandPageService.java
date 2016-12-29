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

import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.LandPageBean;
import com.pxene.pap.domain.model.basic.CampaignModel;
import com.pxene.pap.domain.model.basic.LandPageModel;
import com.pxene.pap.domain.model.basic.LandPageModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.LandPageDao;

@Service
public class LandPageService extends BaseService {

	@Autowired
	private LandPageDao landPageDao;

	@Autowired
	private CampaignDao campaignDao;

	/**
	 * 添加落地页
	 * 
	 * @param bean
	 * @throws Exception
	 */
	@Transactional
	public void createLandPage(LandPageBean bean) throws Exception {
		LandPageModel model = modelMapper.map(bean, LandPageModel.class);
		String id = UUID.randomUUID().toString();
		model.setId(id);
		try {
			// 添加落地页信息
			landPageDao.insertSelective(model);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
		BeanUtils.copyProperties(model, bean);
	}

	/**
	 * 修改落地页
	 * 
	 * @param id
	 * @param bean
	 */
	@Transactional
	public void updateLandPage(String id, LandPageBean bean) throws Exception {
		LandPageModel landPageInDB = landPageDao.selectByPrimaryKey(id);
		if (landPageInDB == null) {
			throw new ResourceNotFoundException();
		}

		LandPageModel model = modelMapper.map(bean, LandPageModel.class);
		model.setId(id);
		try {
			// 修改落地页信息
			landPageDao.updateByPrimaryKeySelective(model);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
	}

	/**
	 * 删除落地页
	 * 
	 * @param id
	 */
	@Transactional
	public void deleteLandPage(String id) throws Exception {
		LandPageModel landPageInDB = landPageDao.selectByPrimaryKey(id);
		if (landPageInDB == null) {
			throw new ResourceNotFoundException();
		}
		String campaignId = landPageInDB.getCampaignId();
		if (StringUtils.isEmpty(campaignId)) {
			// 如果落地页的campaignId是null，直接删除落地页
			landPageDao.deleteByPrimaryKey(id);
		}
		CampaignModel campaign = campaignDao.selectByPrimaryKey(campaignId);
		String status = campaign.getStatus();
		// 不是投放中才可以删除
		if (StatusConstant.CAMPAIGN_START.equals(status)) {
			throw new IllegalStatusException(PhrasesConstant.LANDPAGE_HAVE_CAMPAIGN_LAUNCH);
		}
		// 删除落地页
		landPageDao.deleteByPrimaryKey(id);
	}

	/**
	 * 根据Id查询落地页
	 * 
	 * @param id
	 * @return
	 */
	public LandPageBean selectLandPage(String id) throws Exception {
		LandPageModel landPageModel = landPageDao.selectByPrimaryKey(id);
		if (landPageModel == null) {
			throw new ResourceNotFoundException();
		}
		LandPageBean bean = modelMapper.map(landPageModel, LandPageBean.class);

		return bean;
	}
	/**
	 * 查询落地页列表
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<LandPageBean> selectLandPages(String name) throws Exception {
		LandPageModelExample example = new LandPageModelExample();
		if (!StringUtils.isEmpty(name)) {
			example.createCriteria().andNameLike("%" + name + "%");
		}
		List<LandPageBean> list = new ArrayList<LandPageBean>();
		List<LandPageModel> models = landPageDao.selectByExample(example);
		
		if (models == null || models.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		for (LandPageModel model : models) {
			list.add(modelMapper.map(model, LandPageBean.class));
		}
		
		return list;
	}

}
