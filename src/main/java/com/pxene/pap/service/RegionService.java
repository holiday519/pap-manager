package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.domain.beans.RegionBean;
import com.pxene.pap.domain.model.RegionModel;
import com.pxene.pap.domain.model.RegionModelExample;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.RegionDao;

@Service
public class RegionService extends BaseService {
	
	@Autowired
	private RegionDao regionDao;
	
	/**
	 * 查询地域列表
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<RegionBean> ListRegions(String name) throws Exception {
		RegionModelExample regionExample = new RegionModelExample();
		if (!StringUtils.isEmpty(name)) {
			regionExample.createCriteria().andNameLike("%" + name + "%");
		}
		
		List<RegionBean> result = new ArrayList<RegionBean>();
		List<RegionModel> regions = regionDao.selectByExample(regionExample);
		
		if (regions == null || regions.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		for (RegionModel model : regions) {
			RegionBean regionBean = modelMapper.map(model, RegionBean.class);
			result.add(regionBean);
		}
		
		return result;
	}
}
