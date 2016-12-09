package com.pxene.pap.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.beans.InformationFlowBean;
import com.pxene.pap.domain.model.basic.InfoflowModel;
import com.pxene.pap.repository.mapper.basic.InfoflowModelMapper;

@Service
public class InfomationFlowService {
	
	@Autowired
	private InfoflowModelMapper infoMapper;
	
	/**
	 * 创建信息流
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public String createInformationFlow(InformationFlowBean bean) throws Exception{
		String id = UUID.randomUUID().toString();
		String name = bean.getName();
		String title = bean.getTitle();
		String description = bean.getDescription();
		String icon = bean.getIcon();
		String image1 = bean.getImage1();
		String image2 = bean.getImage2();
		String image3 = bean.getImage3();
		String image4 = bean.getImage4();
		String image5 = bean.getImage5();
		InfoflowModel info = new InfoflowModel();
		info.setId(id);
		info.setName(name);
		info.setTitle(title);
		info.setDescription(description);
		info.setIcon(icon);
		info.setImage1(image1);
		info.setImage2(image2);
		info.setImage3(image3);
		info.setImage4(image4);
		info.setImage5(image5);
		int num = infoMapper.insertSelective(info);
		if (num > 0) {
			return id;
		}
		return "操作失败";
	}
	
	/**
	 * 修改信息流创意
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public String updateInformationFlow(InformationFlowBean bean) throws Exception{
		String id = bean.getId();
		String name = bean.getName();
		String title = bean.getTitle();
		String description = bean.getDescription();
		String icon = bean.getIcon();
		String image1 = bean.getImage1();
		String image2 = bean.getImage2();
		String image3 = bean.getImage3();
		String image4 = bean.getImage4();
		String image5 = bean.getImage5();
		InfoflowModel info = new InfoflowModel();
		info.setId(id);
		info.setName(name);
		info.setTitle(title);
		info.setDescription(description);
		info.setIcon(icon);
		info.setImage1(image1);
		info.setImage2(image2);
		info.setImage3(image3);
		info.setImage4(image4);
		info.setImage5(image5);
		int num = infoMapper.updateByPrimaryKeySelective(info);
		if (num > 0) {
			return id;
		}
		return "操作失败";
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
