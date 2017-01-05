package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.ImageTmplBean;
import com.pxene.pap.domain.beans.TmplBean;
import com.pxene.pap.domain.beans.TmplBean.App;
import com.pxene.pap.domain.beans.TmplBean.ImageTmpl;
import com.pxene.pap.domain.beans.TmplBean.InfoTmpl;
import com.pxene.pap.domain.beans.TmplBean.VideoTmpl;
import com.pxene.pap.domain.beans.VideoTmplBean;
import com.pxene.pap.domain.models.AppModel;
import com.pxene.pap.domain.models.AppModelExample;
import com.pxene.pap.domain.models.AppTargetModel;
import com.pxene.pap.domain.models.AppTargetModelExample;
import com.pxene.pap.domain.models.AppTmplModel;
import com.pxene.pap.domain.models.AppTmplModelExample;
import com.pxene.pap.domain.models.CreativeMaterialModel;
import com.pxene.pap.domain.models.CreativeMaterialModelExample;
import com.pxene.pap.domain.models.ImageTmplModel;
import com.pxene.pap.domain.models.ImageTmplTypeModel;
import com.pxene.pap.domain.models.InfoflowTmplModel;
import com.pxene.pap.domain.models.InfoflowTmplModelExample;
import com.pxene.pap.domain.models.InfoflowTmplModelExample.Criteria;
import com.pxene.pap.domain.models.view.TmplImageDetailModelExample;
import com.pxene.pap.domain.models.view.TmplImageDetailModelWithBLOBs;
import com.pxene.pap.domain.models.view.TmplVideoDetailModelExample;
import com.pxene.pap.domain.models.view.TmplVideoDetailModelWithBLOBs;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AppDao;
import com.pxene.pap.repository.basic.AppTargetDao;
import com.pxene.pap.repository.basic.AppTmplDao;
import com.pxene.pap.repository.basic.CreativeMaterialDao;
import com.pxene.pap.repository.basic.ImageTmplDao;
import com.pxene.pap.repository.basic.ImageTmplTypeDao;
import com.pxene.pap.repository.basic.InfoflowTmplDao;
import com.pxene.pap.repository.basic.view.TmplImageDetailDao;
import com.pxene.pap.repository.basic.view.TmplVideoDetailDao;

@Service
public class TmplService extends BaseService {

	@Autowired
	private ImageTmplDao imageTmplDao;

	@Autowired
	private ImageTmplTypeDao imageTmplTypeDao;

	@Autowired
	private AppTmplDao appTmplDao;
	
	@Autowired
	private AppTargetDao appTargetDao;

	@Autowired
	private TmplImageDetailDao tmplImageDetailDao;

	@Autowired
	private TmplVideoDetailDao tmplVideoDetailDao;

	@Autowired
	private InfoflowTmplDao infoflowTmplDao;
	
	@Autowired
	private AppDao appDao;
	
	@Autowired
	private CreativeMaterialDao creativeMaterialDao;
	
	@Autowired
	private CreativeService creativeService;
	
	/**
	 * 添加图片模版
	 * 
	 * @param bean
	 * @throws Exception
	 */
	@Transactional
	public String addImageTmpl(ImageTmplBean bean) throws Exception {
		if (bean == null) {
			throw new IllegalArgumentException();
		}
		// 如果id为null，则添加一个UUID
		String id = bean.getId();
		if (StringUtils.isEmpty(id)) {
			id = UUID.randomUUID().toString();
		}
		// 如果图片类型ID 不为NULL，添加关联关系
		String imageTypeId = bean.getImageTypeId();
		if (!StringUtils.isEmpty(imageTypeId)) {
			ImageTmplTypeModel ittModel = new ImageTmplTypeModel();
			ittModel.setId(UUID.randomUUID().toString());
			ittModel.setImageTmplId(id);
			ittModel.setImageTypeId(imageTypeId);
			imageTmplTypeDao.insertSelective(ittModel);
		}
		// 转换参数
		ImageTmplModel model = modelMapper.map(bean, ImageTmplModel.class);

		try {
			// 添加图片模版
			imageTmplDao.insertSelective(model);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
		return id;
	}

	public void addVideoTmpl(VideoTmplBean bean) throws Exception {

	}

	/**
	 * 查询图片模版详细信息
	 * @param paramId
	 * @return
	 * @throws Exception
	 */
	public List<ImageTmpl> selectImageTmpls(String campaignId, String creativeId) throws Exception {
		if (StringUtils.isEmpty(campaignId)) {
			throw new IllegalArgumentException();
		}
		//获取活动下的APPId
		List<String> appIdList = getAppidByCampaignId(campaignId);
		
		List<ImageTmpl> imageTmplList = new ArrayList<ImageTmpl>();

		// 查询app的模版
		AppTmplModelExample appTmplModelExample = new AppTmplModelExample();
		//如果creativeId不为空,在查询模版时，排除掉该创意已经绑定的模版
		if(!StringUtils.isEmpty(creativeId)) {
			CreativeMaterialModelExample exa = new CreativeMaterialModelExample();
			exa.createCriteria().andCreativeIdEqualTo(creativeId);
			List<CreativeMaterialModel> models = creativeMaterialDao.selectByExample(exa);
			List<String> list = new ArrayList<String>();
			if (models != null && !models.isEmpty()) {
				for (CreativeMaterialModel model : models) {
					String tmplId = model.getTmplId();
					list.add(tmplId);
				}
			}
			if (!list.isEmpty()) {
				appTmplModelExample.createCriteria().andAppIdIn(appIdList).andAdTypeEqualTo(StatusConstant.CREATIVE_TYPE_IMAGE).andTmplIdNotIn(list);
			}
		} else {
			appTmplModelExample.createCriteria().andAppIdIn(appIdList).andAdTypeEqualTo(StatusConstant.CREATIVE_TYPE_IMAGE);
		}
		List<AppTmplModel> appTmpls = appTmplDao.selectByExample(appTmplModelExample);
		
		if (appTmpls != null && !appTmpls.isEmpty()) {
			for (AppTmplModel appTmpl : appTmpls) {
				// 根据模版id查询信息
				String tmplId = appTmpl.getTmplId();
				if (!StringUtils.isEmpty(tmplId)) {
					ImageTmpl imageTmpl = getImageTmplDetail(tmplId);
					if (imageTmpl != null) {
						//查询app信息
						imageTmpl.setApps(getAppByTmplId(appTmpl.getTmplId()));
						imageTmplList.add(imageTmpl);
					}
				}
			}
		}
		
		if (imageTmplList.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		return imageTmplList;
	}
	
	/**
	 * 查询视频模版详细信息
	 * @param paramId
	 * @return
	 * @throws Exception
	 */
	public List<VideoTmpl> selectVideoTmpls(String campaignId, String creativeId) throws Exception {
		if (StringUtils.isEmpty(campaignId)) {
			throw new IllegalArgumentException();
		}
		//获取活动下的APPId
		List<String> appIdList = getAppidByCampaignId(campaignId);
				
		List<VideoTmpl> videpTmplList = new ArrayList<VideoTmpl>();
		// 查询app的模版
		AppTmplModelExample appTmplModelExample = new AppTmplModelExample();
		appTmplModelExample.createCriteria().andAppIdIn(appIdList).andAdTypeEqualTo(StatusConstant.CREATIVE_TYPE_VIDEO);
		List<AppTmplModel> appTmpls = appTmplDao.selectByExample(appTmplModelExample);
		
		if (appTmpls != null && !appTmpls.isEmpty()) {
			List<String> tmplIds = new ArrayList<String>();
			//将id放入list中
			for (AppTmplModel appTmpl : appTmpls) {
				String tmplId = appTmpl.getTmplId();
				tmplIds.add(tmplId);
			}
			//根据模版id查询出所有视频模版信息
			TmplVideoDetailModelExample example = new TmplVideoDetailModelExample();
			//如果creativeId不为空,在查询模版时，排除掉该创意已经绑定的模版
			if(!StringUtils.isEmpty(creativeId)) {
				CreativeMaterialModelExample exa = new CreativeMaterialModelExample();
				exa.createCriteria().andCreativeIdEqualTo(creativeId);
				List<CreativeMaterialModel> models = creativeMaterialDao.selectByExample(exa);
				List<String> list = new ArrayList<String>();
				if (models != null && !models.isEmpty()) {
					for (CreativeMaterialModel model : models) {
						String tmplId = model.getTmplId();
						list.add(tmplId);
					}
				}
				if (!list.isEmpty()) {
					example.createCriteria().andIdIn(tmplIds).andIdNotIn(list);
				}
			} else {
				example.createCriteria().andIdIn(tmplIds);
			}
			List<TmplVideoDetailModelWithBLOBs> videos = tmplVideoDetailDao.selectByExampleWithBLOBs(example);
			if (videos != null && !videos.isEmpty()) {
				VideoTmpl videoTmpl = null;
				for (TmplVideoDetailModelWithBLOBs video : videos) {
					videoTmpl = modelMapper.map(video, VideoTmpl.class);
					if(!StringUtils.isEmpty(video.getImageTmplId())){
						ImageTmpl image = getImageTmplDetail(video.getImageTmplId());
						if (image != null) {
							//查询App信息
							videoTmpl.setApps(getAppByTmplId(video.getId()));
							videoTmpl.setImageTmpl(image);
						}
					}
					videpTmplList.add(videoTmpl);
				}
			}
		}
		if (videpTmplList.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		return videpTmplList;
	}
	
	/**
	 * 查询信息流模版详细信息
	 * @param paramId
	 * @return
	 * @throws Exception
	 */
	public List<InfoTmpl> selectInfoflowTmpls(String campaignId, String creativeId) throws Exception {
		if (StringUtils.isEmpty(campaignId)) {
			throw new IllegalArgumentException();
		}
		
		//获取活动下的APPId
		List<String> appIdList = getAppidByCampaignId(campaignId);
				
		List<InfoTmpl> infoTmplList = new ArrayList<InfoTmpl>();
		// 查询app的模版
		AppTmplModelExample appTmplModelExample = new AppTmplModelExample();
		appTmplModelExample.createCriteria().andAppIdIn(appIdList).andAdTypeEqualTo(StatusConstant.CREATIVE_TYPE_INFOFLOW);
		List<AppTmplModel> appTmpls = appTmplDao.selectByExample(appTmplModelExample);
		if (appTmpls != null && !appTmpls.isEmpty()) {
			List<String> tmplIds = new ArrayList<String>();
			//将id放入list中
			for (AppTmplModel appTmpl : appTmpls) {
				String tmplId = appTmpl.getTmplId();
				tmplIds.add(tmplId);
			}
			//查询信息流模版
			InfoflowTmplModelExample example = new InfoflowTmplModelExample();
			Criteria createCriteria = example.createCriteria();
			createCriteria.andIdIn(tmplIds);
			//如果creativeId不为空,在查询模版时，排除掉该创意已经绑定的模版
			if(!StringUtils.isEmpty(creativeId)) {
				CreativeMaterialModelExample exa = new CreativeMaterialModelExample();
				exa.createCriteria().andCreativeIdEqualTo(creativeId);
				List<CreativeMaterialModel> models = creativeMaterialDao.selectByExample(exa);
				List<String> list = new ArrayList<String>();
				if (models != null && !models.isEmpty()) {
					for (CreativeMaterialModel model : models) {
						String tmplId = model.getTmplId();
						list.add(tmplId);
					}
				}
				if (!list.isEmpty()) {
					createCriteria.andIdNotIn(list);
				}
			}
			//查询模版
			List<InfoflowTmplModel> list = infoflowTmplDao.selectByExample(example);
			if (list != null && !list.isEmpty()) {
				for (InfoflowTmplModel model : list) {
					InfoTmpl infoTmpl = modelMapper.map(model, InfoTmpl.class);
					// 小图信息
					if (!StringUtils.isEmpty(model.getIconId())) {
						ImageTmpl icon = getImageTmplDetail(model.getIconId());
						infoTmpl.setIcon(icon);
					}
					ImageTmpl image = null;
					//大图信息（最多五个）
					if (!StringUtils.isEmpty(model.getImage1Id())) {
						image = getImageTmplDetail(model.getImage1Id());
						infoTmpl.setImage1(image);
					}
					if (!StringUtils.isEmpty(model.getImage2Id())) {
						image = getImageTmplDetail(model.getImage2Id());
						infoTmpl.setImage2(image);
					}
					if (!StringUtils.isEmpty(model.getImage3Id())) {
						image = getImageTmplDetail(model.getImage3Id());
						infoTmpl.setImage3(image);
					}
					if (!StringUtils.isEmpty(model.getImage4Id())) {
						image = getImageTmplDetail(model.getImage4Id());
						infoTmpl.setImage4(image);
					}
					if (!StringUtils.isEmpty(model.getImage5Id())) {
						image = getImageTmplDetail(model.getImage5Id());
						infoTmpl.setImage5(image);
					}
					//查询app信息
					infoTmpl.setApps(getAppByTmplId(model.getId()));
					infoTmplList.add(infoTmpl);
				}
			}
		}
		
		if (infoTmplList.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		return infoTmplList;
	}
	
	/**
	 * 根据图片模版ID查询图片模版详细信息
	 * @param imageTmplId
	 * @return
	 */
	private ImageTmpl getImageTmplDetail(String imageTmplId) {
		ImageTmpl imageTmpl = null;
		if (!StringUtils.isEmpty(imageTmplId)) {
			//查询图片模版信息
			TmplImageDetailModelExample example = new TmplImageDetailModelExample();
			example.createCriteria().andIdEqualTo(imageTmplId);
			List<TmplImageDetailModelWithBLOBs> list = tmplImageDetailDao.selectByExampleWithBLOBs(example);
			if (list != null && !list.isEmpty()) {
				for (TmplImageDetailModelWithBLOBs imol : list) {
					imageTmpl = modelMapper.map(imol, ImageTmpl.class);
				}
			}
		}
		
		return imageTmpl;
	}
	
	/**
	 * 根据模版Id查询App
	 * @param tmplId
	 * @return
	 * @throws Exception
	 */
	private App[] getAppByTmplId(String tmplId) throws Exception {
		AppTmplModelExample atExample = new AppTmplModelExample();
		atExample.createCriteria().andTmplIdEqualTo(tmplId);
		List<AppTmplModel> appTmpls = appTmplDao.selectByExample(atExample);
		List<String> appIdList = new ArrayList<String>();
		if (appTmpls != null && !appTmpls.isEmpty()) {
			for (AppTmplModel appTmpl : appTmpls) {
				String appId = appTmpl.getAppId();
				if (!appIdList.contains(appId)) {
					appIdList.add(appId);
				}
			}
		}
		List<AppModel> appModels = null;
		if (!appIdList.isEmpty()) {
			AppModelExample example = new AppModelExample();
			example.createCriteria().andIdIn(appIdList);
			appModels = appDao.selectByExample(example);
		}
		App[] apps = null;
		if (appModels != null) {
			apps = new App[appModels.size()];
			for (int i = 0; i < appModels.size(); i++) {
				AppModel appModel = appModels.get(i);
				App app = modelMapper.map(appModel, App.class);
				apps[i] = app;
			}
		}
		
		return apps;
	}
	
	/**
	 * 根据活动ID查询appid
	 * @param campaignId
	 */
	public List<String> getAppidByCampaignId(String campaignId) throws Exception {
		AppTargetModelExample example = new AppTargetModelExample();
		example.createCriteria().andCampaignIdEqualTo(campaignId);
		List<AppTargetModel> list = appTargetDao.selectByExample(example);
		List<String> appIds = new ArrayList<String>();
		if (list != null && !list.isEmpty()) {
			for (AppTargetModel model : list) {
				String appId = model.getAppId();
				if (!StringUtils.isEmpty(appId)) {
					appIds.add(appId);
				}
			}
		}
		return appIds;
	}
}
