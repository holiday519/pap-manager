package com.pxene.pap.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pxene.pap.domain.beans.PurposeBean;
import com.pxene.pap.domain.model.basic.DownLoadModel;
import com.pxene.pap.domain.model.basic.LandPageModel;
import com.pxene.pap.domain.model.basic.PurposeModel;
import com.pxene.pap.domain.model.basic.PurposeModelExample;
import com.pxene.pap.repository.mapper.basic.DownLoadModelMapper;
import com.pxene.pap.repository.mapper.basic.LandPageModelMapper;
import com.pxene.pap.repository.mapper.basic.PurposeModelMapper;

@Service
public class PurposeService {

	@Autowired
	private PurposeModelMapper purposeMapper;
	
	@Autowired
	private LandPageModelMapper landPageMapper;
	
	@Autowired
	private DownLoadModelMapper downLoadMapper;
	
	/**
	 * 新建活动目标
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public String  createPurpose(PurposeBean bean) throws Exception {
		String id = UUID.randomUUID().toString();
		String campaignId = bean.getCampaignId();
		String name = bean.getName();
		PurposeModelExample example = new PurposeModelExample();
		example.createCriteria().andNameEqualTo(name);
		List<PurposeModel> list = purposeMapper.selectByExample(example);
		if(list!=null && !list.isEmpty()){
			return "活动目标名称重复";
		}
		String landpagePath = bean.getLandpagePath();
		String downloadPath = bean.getDownloadPath();
		if ((landpagePath == null || "".equals(landpagePath))
				&& (downloadPath == null || "".equals(downloadPath))) {
			return "必须选择落地页或APP下载其中一个！";
		}
		PurposeModel purposeModel = new PurposeModel();
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
			landPageMapper.insertSelective(landPageModel);
			purposeModel.setLandpageId(landPageId);
		}
		//创建app下载信息
		if (downloadPath != null && !"".equals(downloadPath)) {
			String downLoadId = UUID.randomUUID().toString();
			String appOs = bean.getAppOs();
			String appName = bean.getAppName();
			String appId = bean.getAppId();
			String appPkgName = bean.getAppPkgName();
			DownLoadModel downLoadModel = new DownLoadModel();
			downLoadModel.setId(downLoadId);
			downLoadModel.setPath(downloadPath);
			downLoadModel.setAppOs(appOs);
			downLoadModel.setAppName(appName);
			downLoadModel.setAppId(appId);
			downLoadModel.setAppPkgName(appPkgName);
			downLoadMapper.insertSelective(downLoadModel);
			purposeModel.setDownloadId(downLoadId);
		}
		//创建目标地址
		purposeModel.setId(id);
		purposeModel.setCampaignId(campaignId);
		purposeModel.setName(name);
		int num = purposeMapper.insertSelective(purposeModel);
		if (num > 0) {
			return "目标地址创建成功";
		}
		return "目标地址创建失败";
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
		List<PurposeModel> list = purposeMapper.selectByExample(example);
		if(list!=null && !list.isEmpty()){
			return "活动目标名称重复";
		}
		PurposeModel oldModel = purposeMapper.selectByPrimaryKey(id);
		if (oldModel == null) {
			return "活动目标ID错误！";
		}
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
				DownLoadModel downLoad = new DownLoadModel();
				downLoad.setId(downLoadId);
				downLoad.setPath(path);
				downLoad.setAppOs(appOs);
				downLoad.setAppName(appName);
				downLoad.setAppId(appId);
				downLoad.setAppPkgName(appPkgName);
				downLoadMapper.updateByPrimaryKeySelective(downLoad);
				purposeModel.setDownloadId(downLoadId);
			}else{
				//删除APP下载；添加落地页
				String downLoadId = bean.getDownloadId();
				downLoadMapper.deleteByPrimaryKey(downLoadId);
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
				landPageMapper.insertSelective(landPageModel);
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
				landPageMapper.updateByPrimaryKeySelective(landPage);
				purposeModel.setLandpageId(landPageId);
			}else{
				//删除落地页；添加APP下载
				String landPageId = bean.getLandpageId();
				landPageMapper.deleteByPrimaryKey(landPageId);
				purposeModel.setLandpageId(null);
				
				String downLoadId = UUID.randomUUID().toString();
				String path = bean.getDownloadPath();
				String appOs = bean.getAppOs();
				String appName = bean.getAppName();
				String appId = bean.getAppId();
				String appPkgName = bean.getAppPkgName();
				DownLoadModel downLoadMoel = new DownLoadModel();
				downLoadMoel.setId(downLoadId);
				downLoadMoel.setPath(path);
				downLoadMoel.setAppOs(appOs);
				downLoadMoel.setAppName(appName);
				downLoadMoel.setAppId(appId);
				downLoadMoel.setAppPkgName(appPkgName);
				downLoadMapper.insertSelective(downLoadMoel);
				purposeModel.setDownloadId(downLoadId);
			}
		}
		purposeModel.setCampaignId(campaignId);
		purposeModel.setName(name);
		purposeModel.setId(id);
		//此处调用方法如果字段为NULL则更新成NULL（updateByPrimaryKeySelective方法会忽略掉NULL，导致数据更新成null失败）
		int num = purposeMapper.updateByPrimaryKey(purposeModel);
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
			landPageMapper.deleteByPrimaryKey(landpageId);
		}
		if(downloadId!=null){
			downLoadMapper.deleteByPrimaryKey(downloadId);
		}
		int num = purposeMapper.deleteByPrimaryKey(id);
		return num;
	}
	
}
