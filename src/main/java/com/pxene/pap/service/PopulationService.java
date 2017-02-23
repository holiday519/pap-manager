package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.domain.beans.PopulationBean;
import com.pxene.pap.domain.models.PopulationModel;
import com.pxene.pap.domain.models.PopulationModelExample;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.PopulationDao;

@Service
public class PopulationService extends BaseService {
 
	@Autowired
	private PopulationDao populationDao;
	
	public List<PopulationBean> selectPopulations(String name) throws Exception {
		PopulationModelExample example = new PopulationModelExample();
		if (!StringUtils.isEmpty(name)) {
			example.createCriteria().andNameLike("%" + name + "%");
		}
		// 按更新时间进行倒序排序
        example.setOrderByClause("update_time DESC");
        List<PopulationModel> models = populationDao.selectByExample(example);
        if (models == null || models.size() <= 0) {
            throw new ResourceNotFoundException();
        }
        List<PopulationBean> beans = new ArrayList<PopulationBean>();
        for (PopulationModel model : models) {
        	PopulationBean bean = modelMapper.map(model, PopulationBean.class);
        	beans.add(bean);
        }
		
		return beans;
	}
}
