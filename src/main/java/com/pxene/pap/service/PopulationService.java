package com.pxene.pap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.pxene.pap.domain.beans.PopulationBean;
import com.pxene.pap.repository.basic.PopulationDao;

public class PopulationService extends BaseService {

	@Autowired
	private PopulationDao populationDao;
	
	public List<PopulationBean> listPopulations(String name) throws Exception {
		if (!StringUtils.isEmpty(name)) {
		}
		
		return null;
	}
}
