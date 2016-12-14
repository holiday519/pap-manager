package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.model.basic.CreativeMaterialModel;
import com.pxene.pap.domain.model.basic.CreativeMaterialModelExample;
import com.pxene.pap.domain.model.basic.CreativeModel;
import com.pxene.pap.domain.model.basic.CreativeModelExample;
import com.pxene.pap.repository.mapper.basic.CampaignModelMapper;
import com.pxene.pap.repository.mapper.basic.CreativeMaterialModelMapper;
import com.pxene.pap.repository.mapper.basic.CreativeModelMapper;
import com.pxene.pap.repository.mapper.basic.ProjectModelMapper;

@Service
public class RedisService {

	@Autowired
	private CampaignModelMapper campaignMapper;
	
	@Autowired
	private ProjectModelMapper projectMapper;
	
	@Autowired
	private CreativeModelMapper creativeMapper;
	
	@Autowired
	private CreativeMaterialModelMapper creativeMaterialMapper;
	
	@Transactional
	public void writeCreativeInfoToRedis(String campaignId) throws Exception {
		// 查询推广组下创意
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> creatives = creativeMapper.selectByExample(creativeExample);
		// 创意id数组
		List<String> creativeIds = new ArrayList<String>();
		//如果推广组下无创意，
		if(creatives == null || creatives.isEmpty()){
			throw new Exception();
		}
		// 将查询出来的创意id放入创意id数组
		for (CreativeModel creative : creatives) {
			String creativeId = creative.getId();
			if (creativeId != null && !creativeId.isEmpty()) {
				creativeIds.add(creativeId);
			}
		}
		// 根据创意id数组查询创意所对应的关联关系表数据
		if (!creativeIds.isEmpty()) {
			CreativeMaterialModelExample mapExample = new CreativeMaterialModelExample();
			mapExample.createCriteria().andCreativeIdIn(creativeIds);
			List<CreativeMaterialModel> mapModels = creativeMaterialMapper.selectByExample(mapExample);
			if (mapModels != null && !mapModels.isEmpty()) {
				for(CreativeMaterialModel mapModel : mapModels){
					String mapId = mapModel.getId();
					String creativeType = mapModel.getCreativeType();
					// 图片创意
					if ("1".equals(creativeType)) {

					// 视频创意
					} else if ("2".equals(creativeType)) {
					
					// 信息流创意
					} else if ("3".equals(creativeType)) {

					}
				}
			}
		}
	}
	
	public void writeImgCreativeInfoToRedis(String mapId) throws Exception {
		CreativeMaterialModel materialModel = creativeMaterialMapper.selectByPrimaryKey(mapId);
		
	}
	
}
