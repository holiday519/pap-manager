package com.pxene.pap.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.domain.beans.InformationFlowBean;
import com.pxene.pap.domain.model.basic.InfoFlowModel;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.NotFoundException;
import com.pxene.pap.repository.mapper.basic.InfoFlowModelMapper;

@Service
public class InfomationFlowService extends BaseService{
	
	@Autowired
	private InfoFlowModelMapper infoMapper;
	
	/**
	 * 创建信息流
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void createInformationFlow(InformationFlowBean bean) throws Exception{
		bean.setId(UUID.randomUUID().toString());
		InfoFlowModel info = modelMapper.map(bean, InfoFlowModel.class);
		infoMapper.insertSelective(info);
	}
	
	/**
	 * 修改信息流创意
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void updateInformationFlow(String id, InformationFlowBean bean) throws Exception{
		if (!StringUtils.isEmpty(bean.getId())) {
			throw new IllegalArgumentException();
		}

		InfoFlowModel infoFlowInDB = infoMapper.selectByPrimaryKey(id);
		if (infoFlowInDB ==null || StringUtils.isEmpty(infoFlowInDB.getId())) {
			throw new NotFoundException();
		}
		
		bean.setId(id);
		InfoFlowModel info = modelMapper.map(bean, InfoFlowModel.class);
		infoMapper.updateByPrimaryKeySelective(info);
	}
	
	/**
	 * 删除信息流创意
	 * @param id
	 * @return
	 */
	@Transactional
	public int deleteInfoFlow(String id)  throws Exception{
		int num = infoMapper.deleteByPrimaryKey(id);
		return num;
	}
	
}
