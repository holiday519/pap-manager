package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.models.IndustryModel;
import com.pxene.pap.domain.models.IndustryModelExample;
import com.pxene.pap.repository.basic.IndustryDao;

@Service
public class IndustryService {

	@Autowired
	private IndustryDao industryDao;
	
	public List<Map<String, String>> listIndustries() throws Exception {
		IndustryModelExample example = new IndustryModelExample();
		List<IndustryModel> models = industryDao.selectByExample(example);
		
		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
		for (IndustryModel model : models) {
			Map<String, String> result = new HashMap<String, String>();
			result.put("id", String.valueOf(model.getId()));
			result.put("name", model.getName());
			results.add(result);
		}
		
		return results;
	}
}
