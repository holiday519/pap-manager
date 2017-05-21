package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.domain.beans.AppBean;
import com.pxene.pap.domain.models.AppModel;
import com.pxene.pap.domain.models.AppModelExample;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AppDao;

@Service
public class AppService extends BaseService {
	
	@Autowired
	private AppDao appDao;
	
	/**
	 * 查询app列表
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<AppBean> ListApps(String name) throws Exception {
		AppModelExample appExample = new AppModelExample();
		if (!StringUtils.isEmpty(name)) {
			appExample.createCriteria().andAppNameLike("%" + name + "%");
		}
		
		List<AppBean> result = new ArrayList<AppBean>();
		List<AppModel> apps = appDao.selectByExample(appExample);
		
//		if (apps == null || apps.isEmpty()) {
//			throw new ResourceNotFoundException();
//		}
		
		for (AppModel model : apps) {
			AppBean appBean = modelMapper.map(model, AppBean.class);
			result.add(appBean);
		}
		
		return result;
	}
}
