package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.domain.beans.RegionBean;
import com.pxene.pap.domain.beans.RegionBean.City;
import com.pxene.pap.domain.models.RegionModel;
import com.pxene.pap.domain.models.RegionModelExample;
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
		
//		if (regions == null || regions.isEmpty()) {
//			throw new ResourceNotFoundException();
//		}
		
		for (RegionModel model : regions) {
			RegionBean regionBean = modelMapper.map(model, RegionBean.class);
			result.add(regionBean);
		}
		
		List<RegionBean> province = new ArrayList<RegionBean>();
		List<RegionBean> city = new ArrayList<RegionBean>();
		for (RegionBean model : result) {
			if (!"000000".equals(model.getId())) {
				// 以“0000”结尾的是省
				if (model.getId().endsWith("0000")) {
					province.add(model);
				} else {
					// 除了省份就是城市
					city.add(model);
				}
			}
		}
		for (RegionBean pro : province) {
			List<RegionBean> cityList = new ArrayList<RegionBean>();
			for (RegionBean ci : city) {
				String pStr = pro.getId().substring(0, 4) + "00";
				String cStr = ci.getId().substring(0, 2) + "0000";
				if (pStr.equals(cStr)) {
					cityList.add(ci);
				}
			}
			City[] citys = new City[cityList.size()];
			for (int i = 0; i < cityList.size(); i++) {
				citys[i] = modelMapper.map(cityList.get(i), City.class);
			}
			if (citys.length > 0) {
				pro.setCitys(citys);
			}
		}
		
		return province;
	}
	
}
