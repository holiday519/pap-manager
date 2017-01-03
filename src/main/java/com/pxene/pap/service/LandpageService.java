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
import com.pxene.pap.domain.beans.LandpageBean;
import com.pxene.pap.domain.model.CampaignModel;
import com.pxene.pap.domain.model.LandpageModel;
import com.pxene.pap.domain.model.LandpageModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.LandpageDao;

@Service
public class LandpageService extends BaseService {

	@Autowired
	private LandpageDao landpageDao;

	@Autowired
	private CampaignDao campaignDao;

	/**
	 * 添加落地页
	 * 
	 * @param bean
	 * @throws Exception
	 */
	@Transactional
	public void createLandpage(LandpageBean bean) throws Exception {
		LandpageModel model = modelMapper.map(bean, LandpageModel.class);
		String id = UUID.randomUUID().toString();
		model.setId(id);
		try {
			// 添加落地页信息
			landpageDao.insertSelective(model);
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
	public void updateLandpage(String id, LandpageBean bean) throws Exception {
		LandpageModel landpageInDB = landpageDao.selectByPrimaryKey(id);
		if (landpageInDB == null) {
			throw new ResourceNotFoundException();
		}

		LandpageModel model = modelMapper.map(bean, LandpageModel.class);
		model.setId(id);
		try {
			// 修改落地页信息
			landpageDao.updateByPrimaryKeySelective(model);
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
	public void deleteLandpage(String id) throws Exception {
		LandpageModel landpageInDB = landpageDao.selectByPrimaryKey(id);
		if (landpageInDB == null) {
			throw new ResourceNotFoundException();
		}
		String campaignId = landpageInDB.getCampaignId();
		if (StringUtils.isEmpty(campaignId)) {
			// 如果落地页的campaignId是null，直接删除落地页
			landpageDao.deleteByPrimaryKey(id);
		}
		CampaignModel campaign = campaignDao.selectByPrimaryKey(campaignId);
		String status = campaign.getStatus();
		// 不是投放中才可以删除
		if (StatusConstant.CAMPAIGN_START.equals(status)) {
			throw new IllegalStatusException(PhrasesConstant.LANDPAGE_HAVE_CAMPAIGN_LAUNCH);
		}
		// 删除落地页
		landpageDao.deleteByPrimaryKey(id);
	}

	/**
	 * 根据Id查询落地页
	 * 
	 * @param id
	 * @return
	 */
	public LandpageBean selectLandpage(String id) throws Exception {
		LandpageModel landpageModel = landpageDao.selectByPrimaryKey(id);
		if (landpageModel == null) {
			throw new ResourceNotFoundException();
		}
		LandpageBean bean = modelMapper.map(landpageModel, LandpageBean.class);

		return bean;
	}
	/**
	 * 查询落地页列表
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<LandpageBean> selectLandpages(String name) throws Exception {
		LandpageModelExample example = new LandpageModelExample();
		if (!StringUtils.isEmpty(name)) {
			example.createCriteria().andNameLike("%" + name + "%");
		}
		List<LandpageBean> list = new ArrayList<LandpageBean>();
		List<LandpageModel> models = landpageDao.selectByExample(example);
		
		if (models == null || models.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		for (LandpageModel model : models) {
			list.add(modelMapper.map(model, LandpageBean.class));
		}
		
		return list;
	}

}
