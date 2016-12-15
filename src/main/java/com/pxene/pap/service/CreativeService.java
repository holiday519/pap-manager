package com.pxene.pap.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.beans.CreativeBean;
import com.pxene.pap.domain.model.basic.CreativeMaterialModel;
import com.pxene.pap.domain.model.basic.CreativeMaterialModelExample;
import com.pxene.pap.domain.model.basic.CreativeModel;
import com.pxene.pap.repository.mapper.basic.CreativeMaterialModelMapper;
import com.pxene.pap.repository.mapper.basic.CreativeModelMapper;
import com.pxene.pap.repository.mapper.basic.InfoFlowModelMapper;

@Service
public class CreativeService {
	
	@Autowired
	private CreativeModelMapper creativeMapper;
	
	@Autowired
	private InfoFlowModelMapper infoFlowModelMapper;
	
	@Autowired
	private CreativeMaterialModelMapper creativeMaterialMapper;
	
	/**
	 * 创建创意
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public String createCreative(CreativeBean bean) throws Exception {
		String creativeId = UUID.randomUUID().toString();
		bean.setId(creativeId);
		String campaignId = bean.getCampaignId();
		String name = bean.getName();
		//添加创意——素材关联关系
		addCreativeMaterial(bean);
		//创意表数据添加
		CreativeModel creative = new CreativeModel();
		creative.setId(creativeId);
		creative.setCampaignId(campaignId);
		creative.setName(name);
		int num = creativeMapper.insertSelective(creative);
		if (num > 0) {
			return "创意创建成功";
		}else{
			return "创意创建失败";
		}
	}
	
	/**
	 * 编辑创意
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public String updateCreative(CreativeBean bean) throws Exception {
		String creativeId = bean.getId();
		String campaignId = bean.getCampaignId();
		String name = bean.getName();
		//先删除关联关系
		deleteCreativeMaterial(creativeId);
		//添加创意——素材关联关系
		addCreativeMaterial(bean);
		//创意表数据添加
		CreativeModel creative = new CreativeModel();
		creative.setId(creativeId);
		creative.setCampaignId(campaignId);
		creative.setName(name);
		int num = creativeMapper.updateByPrimaryKeySelective(creative);
		if (num > -1) {
			return creativeId;
		}else{
			return "创意编辑失败";
		}
	}
	
	/**
	 * 删除创意——素材关联关系
	 * @param creativeId
	 */
	@Transactional
	public void  deleteCreativeMaterial(String creativeId) throws Exception {
		CreativeMaterialModelExample example = new CreativeMaterialModelExample();
		example.createCriteria().andCreativeIdEqualTo(creativeId);
		creativeMaterialMapper.deleteByExample(example);
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
				creativeMaterialMapper.insertSelective(cmModel);
			}
		}
	}
	
	@Transactional
	public int deleteCreative(String creativeId) throws Exception {
		CreativeMaterialModelExample cmExample = new CreativeMaterialModelExample();
		cmExample.createCriteria().andCreativeIdEqualTo(creativeId);
		// 删除信息流创意；图片和视频不用删除
		List<CreativeMaterialModel> cmList = creativeMaterialMapper.selectByExample(cmExample);
		if (cmList != null && !cmList.isEmpty()) {
			for (CreativeMaterialModel cm : cmList) {
				String cmId = cm.getMaterialId();
				infoFlowModelMapper.deleteByPrimaryKey(cmId);
			}
		}
		// 删除关联关系表中数据
		creativeMaterialMapper.deleteByExample(cmExample);
		// 删除创意数据
		int num = creativeMapper.deleteByPrimaryKey(creativeId);
		return num;
	}
}
