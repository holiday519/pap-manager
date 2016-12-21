package com.pxene.pap.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;

public class BaseService {

	protected static ModelMapper modelMapper = new ModelMapper();
	
	static {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
}
