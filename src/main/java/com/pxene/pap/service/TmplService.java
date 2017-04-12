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
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.CreativeModelExample;
import com.pxene.pap.domain.models.ImageTmplModel;
import com.pxene.pap.domain.models.InfoflowTmplModel;
import com.pxene.pap.domain.models.VideoTmplModel;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.repository.basic.AppDao;
import com.pxene.pap.repository.basic.AppTargetDao;
import com.pxene.pap.repository.basic.AppTmplDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.ImageTmplDao;
import com.pxene.pap.repository.basic.InfoflowTmplDao;
import com.pxene.pap.repository.basic.VideoTmplDao;

@Service
public class TmplService extends BaseService {

	@Autowired
	private ImageTmplDao imageTmplDao;
	
	@Autowired
	private VideoTmplDao videoTmplDao;
	
	@Autowired
	private AppTmplDao appTmplDao;
	
	@Autowired
	private AppTargetDao appTargetDao;

	@Autowired
	private InfoflowTmplDao infoflowTmplDao;
	
	@Autowired
	private AppDao appDao;
	
	@Autowired
	private CreativeDao creativeDao;
	
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
			/*id = UUID.randomUUID().toString();*/
			id = UUIDGenerator.getUUID();
		}
//		// 如果图片类型ID 不为NULL，添加关联关系
//		String imageTypeId = bean.getImageTypeId();
//		if (!StringUtils.isEmpty(imageTypeId)) {
//			ImageTmplTypeModel ittModel = new ImageTmplTypeModel();
//			ittModel.setId(UUID.randomUUID().toString());
//			ittModel.setId(UUIDGenerator.getUUID());
//			ittModel.setImageTmplId(id);
//			ittModel.setImageTypeId(imageTypeId);
//			imageTmplTypeDao.insertSelective(ittModel);
//		}
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
	public List<ImageTmpl> selectImageTmpls(String campaignId, String status) throws Exception {
		if (StringUtils.isEmpty(campaignId)) {
			throw new IllegalArgumentException();
		}
		//获取活动下的APPId
		List<String> appIdList = getAppidByCampaignId(campaignId);
		
		List<ImageTmpl> imageTmplList = new ArrayList<ImageTmpl>();
		List<ImageTmpl> imageTmplListNotUse = new ArrayList<ImageTmpl>();
		List<ImageTmpl> imageTmplListAll = new ArrayList<ImageTmpl>();

		// 查询app的模版
		AppTmplModelExample appTmplModelExample = new AppTmplModelExample();
		
		appTmplModelExample.createCriteria().andAppIdIn(appIdList).andAdTypeEqualTo(StatusConstant.CREATIVE_TYPE_IMAGE);
		List<AppTmplModel> appTmpls = appTmplDao.selectByExample(appTmplModelExample);
		
		if (appTmpls != null && !appTmpls.isEmpty()) {
			for (AppTmplModel appTmpl : appTmpls) {
				// 根据模版id查询信息
				String tmplId = appTmpl.getTmplId();
				if (!StringUtils.isEmpty(tmplId)) {
					ImageTmpl imageTmpl = getImageTmplDetail(tmplId);
					
					AppModel app = getAppById(appTmpl.getAppId());
	                if (app != null)
	                {
	                    String appName = app.getAppName();
	                    if (!StringUtils.isEmpty(appName))
	                    {
	                        imageTmpl.setAppName(appName);
	                    }
	                }
					
					if (StringUtils.isEmpty(status)) {
						imageTmplListAll.add(imageTmpl);
					} else {
						if (tmplIsUsed(campaignId, tmplId)) {
							imageTmplList.add(imageTmpl);
						} else {
							imageTmplListNotUse.add(imageTmpl);
						}
					}
				}
			}
		}
		
		if ("01".equals(status)) {//用着的
			return imageTmplList;
		} else if ("02".equals(status)) {
			return imageTmplListNotUse;
		} else {
			return imageTmplListAll;
		}
	}
	
	/**
	 * 查询视频模版详细信息
	 * @param paramId
	 * @return
	 * @throws Exception
	 */
	public List<VideoTmpl> selectVideoTmpls(String campaignId, String status) throws Exception {
		if (StringUtils.isEmpty(campaignId)) {
			throw new IllegalArgumentException();
		}
		//获取活动下的APPId
		List<String> appIdList = getAppidByCampaignId(campaignId);
				
		List<VideoTmpl> videpTmplList = new ArrayList<VideoTmpl>();
		List<VideoTmpl> videpTmplListNotUse = new ArrayList<VideoTmpl>();
		List<VideoTmpl> videpTmplListAll = new ArrayList<VideoTmpl>();
		// 查询app的模版
		AppTmplModelExample appTmplModelExample = new AppTmplModelExample();
		appTmplModelExample.createCriteria().andAppIdIn(appIdList).andAdTypeEqualTo(StatusConstant.CREATIVE_TYPE_VIDEO);
		List<AppTmplModel> appTmpls = appTmplDao.selectByExample(appTmplModelExample);
		
		if (appTmpls != null && !appTmpls.isEmpty()) {
			// 将id放入list中
			for (AppTmplModel appTmpl : appTmpls) {
				String tmplId = appTmpl.getTmplId();
				VideoTmplModel model = videoTmplDao.selectByPrimaryKey(tmplId);
				
				VideoTmpl videoTmpl = modelMapper.map(model, VideoTmpl.class);
				
				AppModel app = getAppById(appTmpl.getAppId());
				if (app != null)
				{
				    String appName = app.getAppName();
				    if (!StringUtils.isEmpty(appName))
				    {
				        videoTmpl.setAppName(appName);
				    }
				}
				
				if (!StringUtils.isEmpty(model.getImagelId())) {
					ImageTmpl image = getImageTmplDetail(model.getImagelId());
					if (image != null) {
						videoTmpl.setImageTmpl(image);
					}
				}
				if (StringUtils.isEmpty(status)) {
					videpTmplListAll.add(videoTmpl);
				} else {
					if (tmplIsUsed(campaignId, tmplId)) {
						videpTmplList.add(videoTmpl);
					} else {
						videpTmplListNotUse.add(videoTmpl);
					}
				}
			}
		}
		if ("01".equals(status)) {//用着的
			return videpTmplList;
		} else if ("02".equals(status)) {
			return videpTmplListNotUse;
		} else {
			return videpTmplListAll;
		}
	}

	/**
	 *  根据APPID获得APP信息
	 * @param appId appid
	 * @return
	 */
    private AppModel getAppById(String appId)
    {
        return appDao.selectByPrimaryKey(appId);
    }
	
	/**
	 * 查询信息流模版详细信息
	 * @param paramId
	 * @return
	 * @throws Exception
	 */
	public List<InfoTmpl> selectInfoflowTmpls(String campaignId, String status) throws Exception {
		if (StringUtils.isEmpty(campaignId)) {
			throw new IllegalArgumentException();
		}
		
		//获取活动下的APPId
		List<String> appIdList = getAppidByCampaignId(campaignId);
				
		List<InfoTmpl> infoTmplList = new ArrayList<InfoTmpl>();
		List<InfoTmpl> infoTmplListNotUse = new ArrayList<InfoTmpl>();
		List<InfoTmpl> infoTmplListAll = new ArrayList<InfoTmpl>();
		// 查询app的模版
		AppTmplModelExample appTmplModelExample = new AppTmplModelExample();
		appTmplModelExample.createCriteria().andAppIdIn(appIdList).andAdTypeEqualTo(StatusConstant.CREATIVE_TYPE_INFOFLOW);
		List<AppTmplModel> appTmpls = appTmplDao.selectByExample(appTmplModelExample);
		if (appTmpls != null && !appTmpls.isEmpty()) {
			List<String> tmplIds = new ArrayList<String>();
			// 将id放入list中
			for (AppTmplModel appTmpl : appTmpls) {
				String tmplId = appTmpl.getTmplId();
				tmplIds.add(tmplId);
				InfoflowTmplModel model = infoflowTmplDao.selectByPrimaryKey(tmplId);
				InfoTmpl infoTmpl = modelMapper.map(model, InfoTmpl.class);
				
				AppModel app = getAppById(appTmpl.getAppId());
                if (app != null)
                {
                    String appName = app.getAppName();
                    if (!StringUtils.isEmpty(appName))
                    {
                        infoTmpl.setAppName(appName);
                    }
                }
				
				// 小图信息
				if (!StringUtils.isEmpty(model.getIconId())) {
					ImageTmpl icon = getImageTmplDetail(model.getIconId());
					infoTmpl.setIcon(icon);
				}
				ImageTmpl image = null;
				// 大图信息（最多五个）
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
				if (StringUtils.isEmpty(status)) {
					infoTmplListAll.add(infoTmpl);
				} else {
					if (tmplIsUsed(campaignId, tmplId)) {
						infoTmplList.add(infoTmpl);
					} else {
						infoTmplListNotUse.add(infoTmpl);
					}
				}
			}
		}
		if ("01".equals(status)) {//用着的
			return infoTmplList;
		} else if ("02".equals(status)) {
			return infoTmplListNotUse;
		} else {
			return infoTmplListAll;
		}
	}
	
	/**
	 * 根据图片模版ID查询图片模版详细信息
	 * @param imageTmplId
	 * @return
	 */
	private ImageTmpl getImageTmplDetail(String imageTmplId) throws Exception {
		ImageTmpl imageTmpl = null;
		if (!StringUtils.isEmpty(imageTmplId)) {
			ImageTmplModel model = imageTmplDao.selectByPrimaryKey(imageTmplId);
			if (model != null) {
				imageTmpl = modelMapper.map(model, ImageTmpl.class);
			}
		}
		
		return imageTmpl;
	}
	
	/**
	 * 判断模版是否已近使用
	 * @param campaignId
	 * @param tmplId
	 * @return
	 * @throws Exception
	 */
	private boolean tmplIsUsed(String campaignId, String tmplId) throws Exception {
		CreativeModelExample example = new CreativeModelExample();
		example.createCriteria().andCampaignIdEqualTo(campaignId).andTmplIdEqualTo(tmplId);
		List<CreativeModel> list = creativeDao.selectByExample(example);
		if (list == null || list.isEmpty()) {
			return false;
		}
		return true;
	}	
	
//	/**
//	 * 根据模版Id查询App
//	 * @param tmplId
//	 * @return
//	 * @throws Exception
//	 */
//	private App[] getAppByTmplId(String tmplId) throws Exception {
//		AppTmplModelExample atExample = new AppTmplModelExample();
//		atExample.createCriteria().andTmplIdEqualTo(tmplId);
//		List<AppTmplModel> appTmpls = appTmplDao.selectByExample(atExample);
//		List<String> appIdList = new ArrayList<String>();
//		if (appTmpls != null && !appTmpls.isEmpty()) {
//			for (AppTmplModel appTmpl : appTmpls) {
//				String appId = appTmpl.getAppId();
//				if (!appIdList.contains(appId)) {
//					appIdList.add(appId);
//				}
//			}
//		}
//		List<AppModel> appModels = null;
//		if (!appIdList.isEmpty()) {
//			AppModelExample example = new AppModelExample();
//			example.createCriteria().andIdIn(appIdList);
//			appModels = appDao.selectByExample(example);
//		}
//		App[] apps = null;
//		if (appModels != null) {
//			apps = new App[appModels.size()];
//			for (int i = 0; i < appModels.size(); i++) {
//				AppModel appModel = appModels.get(i);
//				App app = modelMapper.map(appModel, App.class);
//				apps[i] = app;
//			}
//		}
//		
//		return apps;
//	}
	
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
