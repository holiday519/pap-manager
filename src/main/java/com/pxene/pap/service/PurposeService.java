package com.pxene.pap.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.beans.PurposeBean;
import com.pxene.pap.domain.model.basic.DownLoadModel;
import com.pxene.pap.domain.model.basic.LandPageModel;
import com.pxene.pap.domain.model.basic.PurposeModel;
import com.pxene.pap.domain.model.basic.PurposeModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
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
			String anidDeepLink = bean.getAnidDeepLink();
			String iosDeepLink = bean.getIosDeepLink();
			String landPageId = UUID.randomUUID().toString();
			LandPageModel landPageModel = new LandPageModel();
			landPageModel.setId(landPageId);
			landPageModel.setPath(landpagePath);
			landPageModel.setAnidDeepLink(anidDeepLink);
			landPageModel.setIosDeepLink(iosDeepLink);
			landPageDao.insertSelective(landPageModel);
			purposeModel.setLandpageId(landPageId);
		}
		//创建app下载信息
		if (downloadPath != null && !"".equals(downloadPath)) {
			String downLoadId = UUID.randomUUID().toString();
			String appOs = bean.getAppOs();
			String appName = bean.getAppName();
			String appId = bean.getAppId();
			String appPkgName = bean.getAppPkgName();
			String appDescription = bean.getAppDescription();
			DownLoadModel downLoadModel = new DownLoadModel();
			downLoadModel.setId(downLoadId);
			downLoadModel.setPath(downloadPath);
			downLoadModel.setAppOs(appOs);
			downLoadModel.setAppName(appName);
			downLoadModel.setAppId(appId);
			downLoadModel.setAppDescription(appDescription);
			downLoadModel.setAppPkgName(appPkgName);
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
	public String  updatePurpose(PurposeBean bean) throws Exception {
		String id = bean.getId();
		String campaignId = bean.getCampaignId();
		String name = bean.getName();
		if (id == null || "".equals(id)) {
			return "活动目标ID不能为空！";
		}
		PurposeModelExample example = new PurposeModelExample();
		example.createCriteria().andNameEqualTo(name).andIdNotEqualTo(id);
		List<PurposeModel> list = purposeDao.selectByExample(example);
		if(list!=null && !list.isEmpty()){
			return "活动目标名称重复";
		}
		PurposeModel oldModel = purposeDao.selectByPrimaryKey(id);
		if (oldModel == null) {
			return "活动目标ID错误！";
		}
		String escrowUrl = bean.getEscrowUrl();
		String oldLandpageId = oldModel.getLandpageId();
		String oldDownloadId = oldModel.getDownloadId();
		String landpagePath = bean.getLandpagePath();
		String downloadPath = bean.getDownloadPath();
		
		if ((landpagePath == null || "".equals(landpagePath))
				&& (downloadPath == null || "".equals(downloadPath))) {
			return "必须选择落地页或APP下载其中一种！";
		}
		if ((landpagePath == null || "".equals(landpagePath))
				&& (downloadPath == null || "".equals(downloadPath))) {
			return "只能选择落地页或APP下载其中一种！";
		}
		
		PurposeModel purposeModel = new PurposeModel();
		
		//如果“落地页”为NULL那么“APP下载”肯定不为NULL
		if (oldLandpageId == null || "".equals(oldLandpageId)) {
			if (landpagePath==null || "".equals(landpagePath)) {
				//编辑APP下载
				String downLoadId = bean.getDownloadId();
				String path = bean.getDownloadPath();
				String appOs = bean.getAppOs();
				String appName = bean.getAppName();
				String appId = bean.getAppId();
				String appPkgName = bean.getAppPkgName();
				String appDescription = bean.getAppDescription();
				DownLoadModel downLoad = new DownLoadModel();
				downLoad.setId(downLoadId);
				downLoad.setPath(path);
				downLoad.setAppOs(appOs);
				downLoad.setAppName(appName);
				downLoad.setAppId(appId);
				downLoad.setAppPkgName(appPkgName);
				downLoad.setAppDescription(appDescription);
				downLoadDao.updateByPrimaryKeySelective(downLoad);
				purposeModel.setDownloadId(downLoadId);
			}else{
				//删除APP下载；添加落地页
				String downLoadId = bean.getDownloadId();
				downLoadDao.deleteByPrimaryKey(downLoadId);
				purposeModel.setDownloadId(null);
				
				String landPageId = UUID.randomUUID().toString();
				String path = bean.getLandpagePath();
				String anidDeepLink = bean.getAnidDeepLink();
				String iosDeepLink = bean.getIosDeepLink();
				LandPageModel landPageModel = new LandPageModel();
				landPageModel.setId(landPageId);
				landPageModel.setPath(path);
				landPageModel.setAnidDeepLink(anidDeepLink);
				landPageModel.setIosDeepLink(iosDeepLink);
				landPageDao.insertSelective(landPageModel);
				purposeModel.setLandpageId(landPageId);
			}
		}
		if (oldDownloadId == null || "".equals(oldDownloadId)) {
			if (downloadPath==null || "".equals(downloadPath)) {
				//编辑落地页
				String landPageId = bean.getLandpageId();
				String anidDeepLink = bean.getAnidDeepLink();
				String iosDeepLink = bean.getIosDeepLink();
				String path = bean.getLandpagePath();
				LandPageModel landPage = new LandPageModel();
				landPage.setId(landPageId);
				landPage.setPath(path);
				landPage.setAnidDeepLink(anidDeepLink);
				landPage.setIosDeepLink(iosDeepLink);
				landPageDao.updateByPrimaryKeySelective(landPage);
				purposeModel.setLandpageId(landPageId);
			}else{
				//删除落地页；添加APP下载
				String landPageId = bean.getLandpageId();
				landPageDao.deleteByPrimaryKey(landPageId);
				purposeModel.setLandpageId(null);
				
				String downLoadId = UUID.randomUUID().toString();
				String path = bean.getDownloadPath();
				String appOs = bean.getAppOs();
				String appName = bean.getAppName();
				String appId = bean.getAppId();
				String appPkgName = bean.getAppPkgName();
				String appDescription = bean.getAppDescription();
				DownLoadModel downLoadMoel = new DownLoadModel();
				downLoadMoel.setId(downLoadId);
				downLoadMoel.setPath(path);
				downLoadMoel.setAppOs(appOs);
				downLoadMoel.setAppName(appName);
				downLoadMoel.setAppId(appId);
				downLoadMoel.setAppPkgName(appPkgName);
				downLoadMoel.setAppDescription(appDescription);
				downLoadDao.insertSelective(downLoadMoel);
				purposeModel.setDownloadId(downLoadId);
			}
		}
		purposeModel.setCampaignId(campaignId);
		purposeModel.setName(name);
		purposeModel.setEscrowUrl(escrowUrl);
		purposeModel.setId(id);
		//此处调用方法如果字段为NULL则更新成NULL（updateByPrimaryKeySelective方法会忽略掉NULL，导致数据更新成null失败）
		int num = purposeDao.updateByPrimaryKey(purposeModel);
		if (num > -1) {
			return "目标地址编辑成功";
		}
		return "目标地址编辑失败";
	}
	
	/**
	 * 删除活动目标
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int  deletePurpose(PurposeBean bean) throws Exception {
		String id = bean.getId();
		String landpageId = bean.getLandpageId();
		String downloadId = bean.getDownloadId();
		if (landpageId != null) {
			landPageDao.deleteByPrimaryKey(landpageId);
		}
		if (downloadId != null) {
			downLoadDao.deleteByPrimaryKey(downloadId);
		}
		int num = purposeDao.deleteByPrimaryKey(id);
		return num;
	}
	
}
