package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.domain.beans.PurposeBean;
import com.pxene.pap.domain.model.basic.DownLoadModel;
import com.pxene.pap.domain.model.basic.LandPageModel;
import com.pxene.pap.domain.model.basic.PurposeModel;
import com.pxene.pap.domain.model.basic.PurposeModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.DownLoadDao;
import com.pxene.pap.repository.basic.LandPageDao;
import com.pxene.pap.repository.basic.PurposeDao;

@Service
public class PurposeService extends BaseService {

	@Autowired
	private PurposeDao purposeDao;
	
	@Autowired
	private LandPageDao landPageDao;
	
	@Autowired
	private DownLoadDao downLoadDao;
	
	/**
	 * 新建活动目标
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void  createPurpose(PurposeBean bean) throws Exception {
		String id = UUID.randomUUID().toString();
		PurposeModel purposeModel = modelMapper.map(bean, PurposeModel.class);
		String landpagePath = bean.getLandpagePath();
		String downloadPath = bean.getDownloadPath();
		if ((landpagePath == null || "".equals(landpagePath))
				&& (downloadPath == null || "".equals(downloadPath))) {
			throw new IllegalStateException("必须选择落地页或APP下载其中一个！");
		}
		//创建落地页信息
		if (landpagePath != null && !"".equals(landpagePath)) {
			String landPageId = UUID.randomUUID().toString();
			LandPageModel landPageModel = modelMapper.map(bean, LandPageModel.class);
			landPageModel.setId(landPageId);
			landPageModel.setPath(landpagePath);
			landPageDao.insertSelective(landPageModel);
			purposeModel.setLandpageId(landPageId);
		}
		//创建app下载信息
		if (downloadPath != null && !"".equals(downloadPath)) {
			String downLoadId = UUID.randomUUID().toString();
			DownLoadModel downLoadModel = modelMapper.map(bean, DownLoadModel.class);
			downLoadModel.setId(downLoadId);
			downLoadModel.setPath(downloadPath);
			downLoadDao.insertSelective(downLoadModel);
			purposeModel.setDownloadId(downLoadId);
		}
		//创建目标地址
		try {
			purposeModel.setId(id);
			// 添加项目信息
			purposeDao.insertSelective(purposeModel);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
		BeanUtils.copyProperties(purposeModel, bean);
	}
	
	/**
	 * 编辑活动目标
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void updatePurpose(String id,PurposeBean bean) throws Exception {
		if (!StringUtils.isEmpty(bean.getId())) {
			throw new IllegalArgumentException();
		}
		
		PurposeModel purposeInDB = purposeDao.selectByPrimaryKey(id);
		if (purposeInDB == null) {
			throw new ResourceNotFoundException();
		}
		
		String campaignId = bean.getCampaignId();
		String name = bean.getName();
		PurposeModelExample example = new PurposeModelExample();
		example.createCriteria().andNameEqualTo(name).andIdNotEqualTo(id);
		List<PurposeModel> list = purposeDao.selectByExample(example);
		if(list!=null && !list.isEmpty()){
			throw new IllegalStateException("活动目标名称重复");
		}
		PurposeModel oldModel = purposeDao.selectByPrimaryKey(id);
		if (oldModel == null) {
			throw new IllegalStateException("活动目标ID错误！");
		}
		String escrowUrl = bean.getEscrowUrl();
		String oldLandpageId = oldModel.getLandpageId();
		String oldDownloadId = oldModel.getDownloadId();
		String landpagePath = bean.getLandpagePath();
		String downloadPath = bean.getDownloadPath();
		
		if ((landpagePath == null || "".equals(landpagePath))
				&& (downloadPath == null || "".equals(downloadPath))) {
			throw new IllegalStateException("必须选择落地页或APP下载其中一种！");
		}
		if ((landpagePath == null || "".equals(landpagePath))
				&& (downloadPath == null || "".equals(downloadPath))) {
			throw new IllegalStateException("只能选择落地页或APP下载其中一种！");
		}
		
		PurposeModel purposeModel = new PurposeModel();
		
		//如果“落地页”为NULL那么“APP下载”肯定不为NULL
		if (oldLandpageId == null || "".equals(oldLandpageId)) {
			if (landpagePath==null || "".equals(landpagePath)) {
				//编辑APP下载
				String downLoadId = bean.getDownloadId();
				DownLoadModel downLoad = modelMapper.map(bean, DownLoadModel.class);
				downLoad.setId(downLoadId);
				downLoadDao.updateByPrimaryKeySelective(downLoad);
				purposeModel.setDownloadId(downLoadId);
			}else{
				//删除APP下载；添加落地页
				String downLoadId = bean.getDownloadId();
				downLoadDao.deleteByPrimaryKey(downLoadId);
				purposeModel.setDownloadId(null);
				
				String landPageId = UUID.randomUUID().toString();
				String path = bean.getLandpagePath();
				LandPageModel landPageModel = modelMapper.map(bean, LandPageModel.class);
				landPageModel.setId(landPageId);
				landPageModel.setPath(path);
				landPageDao.insertSelective(landPageModel);
				purposeModel.setLandpageId(landPageId);
			}
		}
		if (oldDownloadId == null || "".equals(oldDownloadId)) {
			if (downloadPath==null || "".equals(downloadPath)) {
				//编辑落地页
				String landPageId = bean.getLandpageId();
				String path = bean.getLandpagePath();
				LandPageModel landPageModel = modelMapper.map(bean, LandPageModel.class);
				landPageModel.setId(landPageId);
				landPageModel.setPath(path);
				landPageDao.updateByPrimaryKeySelective(landPageModel);
				purposeModel.setLandpageId(landPageId);
			}else{
				//删除落地页；添加APP下载
				String landPageId = bean.getLandpageId();
				landPageDao.deleteByPrimaryKey(landPageId);
				purposeModel.setLandpageId(null);
				
				String downLoadId = UUID.randomUUID().toString();
				DownLoadModel downLoadMoel = modelMapper.map(bean, DownLoadModel.class);
				downLoadMoel.setId(downLoadId);
				downLoadDao.insertSelective(downLoadMoel);
				purposeModel.setDownloadId(downLoadId);
			}
		}
		purposeModel.setCampaignId(campaignId);
		purposeModel.setName(name);
		purposeModel.setEscrowUrl(escrowUrl);
		purposeModel.setId(id);
		//此处调用方法如果字段为NULL则更新成NULL（updateByPrimaryKeySelective方法会忽略掉NULL，导致数据更新成null失败）
		try {
			purposeDao.updateByPrimaryKey(purposeModel);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
	}
	
	/**
	 * 删除活动目标
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void deletePurpose(String id) throws Exception {
		
		PurposeModel purposeInDB = purposeDao.selectByPrimaryKey(id);
		if (purposeInDB == null) {
			throw new ResourceNotFoundException();
		}
		
		String landpageId = purposeInDB.getLandpageId();
		String downloadId = purposeInDB.getDownloadId();
		if (!StringUtils.isEmpty(landpageId)) {
			landPageDao.deleteByPrimaryKey(landpageId);
		}
		if (!StringUtils.isEmpty(downloadId)) {
			downLoadDao.deleteByPrimaryKey(downloadId);
		}
		purposeDao.deleteByPrimaryKey(id);
	}
	
	/**
	 * 根据ID查询活动目标
	 * @param id
	 * @return
	 */
	public PurposeBean selectPurpose(String id) {
		PurposeModel model = purposeDao.selectByPrimaryKey(id);
		if (model == null) {
        	throw new ResourceNotFoundException();
        }
        
		PurposeBean bean = modelMapper.map(model, PurposeBean.class);
		PurposeBean reslutBean = getPurposeParam(bean);
		return reslutBean;
	}
	
	/**
	 * 查询活动目标列表
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<PurposeBean> selectPurposes(String name) throws Exception {
		List<PurposeBean> reslut = new ArrayList<PurposeBean>();
		PurposeModelExample example = new PurposeModelExample();
		example.createCriteria().andNameEqualTo(name);
		List<PurposeModel> list = purposeDao.selectByExample(example);
		if (list == null || list.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		for (PurposeModel mol : list) {
			PurposeBean bean = modelMapper.map(mol, PurposeBean.class);
			reslut.add(getPurposeParam(bean));
		}
		
		return reslut;
	}
	
	
	/**
	 * 查询活动目标相关信息
	 * @param bean
	 * @return
	 */
	private PurposeBean getPurposeParam (PurposeBean bean) {
		String landpageId = bean.getLandpageId();
		String downloadId = bean.getDownloadId();
		if (!StringUtils.isEmpty(landpageId)) {
			LandPageModel landPageModel = landPageDao.selectByPrimaryKey(landpageId);
			bean.setLandpagePath(landPageModel.getPath());
			bean.setAnidDeepLink(landPageModel.getAnidDeepLink());
			bean.setIosDeepLink(landPageModel.getIosDeepLink());
		}
		if (!StringUtils.isEmpty(downloadId)) {
			DownLoadModel loadModel = downLoadDao.selectByPrimaryKey(downloadId);
			bean.setAppDescription(loadModel.getAppDescription());
			bean.setAppName(loadModel.getAppName());
			bean.setAppOs(loadModel.getAppOs());
			bean.setAppPkgName(loadModel.getAppPkgName());
			bean.setAppId(loadModel.getAppId());
			bean.setDownloadPath(loadModel.getPath());
		}
		return bean;
	}
}
