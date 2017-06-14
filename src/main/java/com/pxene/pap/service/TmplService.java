package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.constant.CodeTableConstant;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.TmplBean.ImageTmpl;
import com.pxene.pap.domain.beans.TmplBean.InfoflowTmpl;
import com.pxene.pap.domain.beans.TmplBean.VideoTmpl;
import com.pxene.pap.domain.models.AppModel;
import com.pxene.pap.domain.models.AppTargetModel;
import com.pxene.pap.domain.models.AppTargetModelExample;
import com.pxene.pap.domain.models.AppTmplModel;
import com.pxene.pap.domain.models.AppTmplModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.CreativeModelExample;
import com.pxene.pap.domain.models.ImageTmplModel;
import com.pxene.pap.domain.models.InfoflowTmplModel;
import com.pxene.pap.domain.models.VideoTmplModel;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
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
	 * 查询图片模版详细信息
	 * @param paramId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<ImageTmpl> listImageTmpls(String campaignId, String status) throws Exception {
		if (StringUtils.isEmpty(campaignId)) {
			throw new IllegalArgumentException();
		}
		//获取活动下的APPId
		List<String> appIdList = getAppidByCampaignId(campaignId);
		
		if (appIdList.size() == 0) {
			throw new IllegalStatusException(PhrasesConstant.APP_NOT_FOUND);
		}
		
		List<ImageTmpl> imageTmplList = new ArrayList<ImageTmpl>();
		List<ImageTmpl> imageTmplListNotUse = new ArrayList<ImageTmpl>();
		List<ImageTmpl> imageTmplListAll = new ArrayList<ImageTmpl>();

		// 查询app的模版
		AppTmplModelExample appTmplModelExample = new AppTmplModelExample();
		
		appTmplModelExample.createCriteria().andAppIdIn(appIdList).andAdTypeEqualTo(CodeTableConstant.CREATIVE_TYPE_IMAGE);
		List<AppTmplModel> appTmpls = appTmplDao.selectByExample(appTmplModelExample);
		
		if (appTmpls != null && !appTmpls.isEmpty()) {
			// 缓存模板信息
			Map<String, ImageTmpl> tmplMap = new HashMap<String, ImageTmpl>();
			for (AppTmplModel appTmpl : appTmpls) {
				// 根据模版id查询信息
				String tmplId = appTmpl.getTmplId();
				if (!StringUtils.isEmpty(tmplId)) {
					// 查询该tmplId是否出现过
					ImageTmpl imageTmpl = null;
					if (tmplMap.containsKey(tmplId)) {
						imageTmpl = tmplMap.get(tmplId);
						AppModel app = getAppById(appTmpl.getAppId());
						if (app != null) {
							imageTmpl.setAppName(imageTmpl.getAppName() + "," + app.getAppName());
						}
					} else {
						imageTmpl = getImageTmplDetail(tmplId);
						AppModel app = getAppById(appTmpl.getAppId());
						 if (app != null) {
		                	 imageTmpl.setAppId(app.getId());
		                	 imageTmpl.setAppName(app.getAppName());
		                }
						tmplMap.put(tmplId, imageTmpl);
					}
				}
			}
			for (Entry<String, ImageTmpl> entry : tmplMap.entrySet()) {
				String tmplId = entry.getKey();
				ImageTmpl imageTmpl = entry.getValue();
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
	@Transactional
	public List<VideoTmpl> listVideoTmpls(String campaignId, String status) throws Exception {
		if (StringUtils.isEmpty(campaignId)) {
			throw new IllegalArgumentException();
		}
		//获取活动下的APPId
		List<String> appIdList = getAppidByCampaignId(campaignId);
		if (appIdList.size() == 0) {
			throw new IllegalStatusException(PhrasesConstant.APP_NOT_FOUND);
		}
				
		List<VideoTmpl> videpTmplList = new ArrayList<VideoTmpl>();
		List<VideoTmpl> videpTmplListNotUse = new ArrayList<VideoTmpl>();
		List<VideoTmpl> videpTmplListAll = new ArrayList<VideoTmpl>();
		// 查询app的模版
		AppTmplModelExample appTmplModelExample = new AppTmplModelExample();
		appTmplModelExample.createCriteria().andAppIdIn(appIdList).andAdTypeEqualTo(CodeTableConstant.CREATIVE_TYPE_VIDEO);
		List<AppTmplModel> appTmpls = appTmplDao.selectByExample(appTmplModelExample);
		
		if (appTmpls != null && !appTmpls.isEmpty()) {
			// 缓存模板信息
			Map<String, VideoTmpl> tmplMap = new HashMap<String, VideoTmpl>();
			for (AppTmplModel appTmpl : appTmpls) {
				String tmplId = appTmpl.getTmplId();
				if (!StringUtils.isEmpty(tmplId)) {
					VideoTmpl videoTmpl = null;
					if (tmplMap.containsKey(tmplId)) {
						videoTmpl = tmplMap.get(tmplId);
						AppModel app = getAppById(appTmpl.getAppId());
						if (app != null) {
							videoTmpl.setAppName(videoTmpl.getAppName() + "," + app.getAppName());
						}
					} else {
						VideoTmplModel model = videoTmplDao.selectByPrimaryKey(tmplId);
						videoTmpl = modelMapper.map(model, VideoTmpl.class);
						AppModel app = getAppById(appTmpl.getAppId());
						if (app != null) {
							 videoTmpl.setAppId(app.getId());
							 videoTmpl.setAppName(app.getAppName());
		                }
						String imageTmplId = model.getImageId();
						if (!StringUtils.isEmpty(imageTmplId)) {
							ImageTmpl imageTmpl = getImageTmplDetail(imageTmplId);
							if (imageTmpl != null) {
								videoTmpl.setImageTmpl(imageTmpl);
							}
						}
						tmplMap.put(tmplId, videoTmpl);
					}
				}
			}
			for (Entry<String, VideoTmpl> entry : tmplMap.entrySet()) {
				String tmplId = entry.getKey();
				VideoTmpl videoTmpl = entry.getValue();
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
    @Transactional
	public List<InfoflowTmpl> listInfoflowTmpls(String campaignId, String status) throws Exception {
		if (StringUtils.isEmpty(campaignId)) {
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		
		//获取活动下的APPId
		List<String> appIdList = getAppidByCampaignId(campaignId);
		if (appIdList.size() == 0) {
			throw new IllegalStatusException(PhrasesConstant.APP_NOT_FOUND);
		}
				
		List<InfoflowTmpl> infoTmplList = new ArrayList<InfoflowTmpl>();
		List<InfoflowTmpl> infoTmplListNotUse = new ArrayList<InfoflowTmpl>();
		List<InfoflowTmpl> infoTmplListAll = new ArrayList<InfoflowTmpl>();
		// 查询app的模版
		AppTmplModelExample appTmplModelExample = new AppTmplModelExample();
		appTmplModelExample.createCriteria().andAppIdIn(appIdList).andAdTypeEqualTo(CodeTableConstant.CREATIVE_TYPE_INFOFLOW);
		List<AppTmplModel> appTmpls = appTmplDao.selectByExample(appTmplModelExample);
		if (appTmpls != null && !appTmpls.isEmpty()) {
			Map<String, InfoflowTmpl> tmplMap = new HashMap<String, InfoflowTmpl>();
			for (AppTmplModel appTmpl : appTmpls) {
				String tmplId = appTmpl.getTmplId();
				if (!StringUtils.isEmpty(tmplId)) {
					InfoflowTmpl infoflowTmpl = null;
					if (appTmpls.contains(tmplId)) {
						infoflowTmpl = tmplMap.get(tmplId);
						AppModel app = getAppById(appTmpl.getAppId());
		                if (app != null) {
		                	infoflowTmpl.setAppName(infoflowTmpl.getAppName() + "," + app.getAppName());
		                }
					} else {
						InfoflowTmplModel model = infoflowTmplDao.selectByPrimaryKey(tmplId);
						infoflowTmpl = modelMapper.map(model, InfoflowTmpl.class);
						AppModel app = getAppById(appTmpl.getAppId());
		                if (app != null) {
		                	infoflowTmpl.setAppId(app.getId());
		                	infoflowTmpl.setAppName(app.getAppName());
		                }
		                // 小图信息
						if (!StringUtils.isEmpty(model.getIconId())) {
							ImageTmpl icon = getImageTmplDetail(model.getIconId());
							infoflowTmpl.setIcon(icon);
						}
						ImageTmpl image = null;
						// 大图信息（最多五个）
						if (!StringUtils.isEmpty(model.getImage1Id())) {
							image = getImageTmplDetail(model.getImage1Id());
							infoflowTmpl.setImage1(image);
						}
						if (!StringUtils.isEmpty(model.getImage2Id())) {
							image = getImageTmplDetail(model.getImage2Id());
							infoflowTmpl.setImage2(image);
						}
						if (!StringUtils.isEmpty(model.getImage3Id())) {
							image = getImageTmplDetail(model.getImage3Id());
							infoflowTmpl.setImage3(image);
						}
						if (!StringUtils.isEmpty(model.getImage4Id())) {
							image = getImageTmplDetail(model.getImage4Id());
							infoflowTmpl.setImage4(image);
						}
						if (!StringUtils.isEmpty(model.getImage5Id())) {
							image = getImageTmplDetail(model.getImage5Id());
							infoflowTmpl.setImage5(image);
						}
						tmplMap.put(tmplId, infoflowTmpl);
					}
				}
			}
			
			for (Entry<String, InfoflowTmpl> entry : tmplMap.entrySet()) {
				String tmplId = entry.getKey();
				InfoflowTmpl infoflowTmpl = entry.getValue();
				if (StringUtils.isEmpty(status)) {
					infoTmplListAll.add(infoflowTmpl);
				} else {
					if (tmplIsUsed(campaignId, tmplId)) {
						infoTmplList.add(infoflowTmpl);
					} else {
						infoTmplListNotUse.add(infoflowTmpl);
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
	
	@Transactional
	public ImageTmpl getImageTmpl(String id) throws Exception {
		ImageTmplModel imageTmplModel = imageTmplDao.selectByPrimaryKey(id);
		if (imageTmplModel == null) {
			throw new ResourceNotFoundException();
		}
		return modelMapper.map(imageTmplModel, ImageTmpl.class);
	}
	
	@Transactional
	public VideoTmpl getVideoTmpl(String id) throws Exception {
		VideoTmplModel videoTmplModel = videoTmplDao.selectByPrimaryKey(id);
		if (videoTmplModel == null) {
			throw new ResourceNotFoundException();
		}
		VideoTmpl videoTmpl = modelMapper.map(videoTmplModel, VideoTmpl.class);
		String imageTmplId = videoTmplModel.getImageId();
		if (!StringUtils.isEmpty(imageTmplId)) {
			ImageTmpl imageTmpl = getImageTmplDetail(imageTmplId);
			videoTmpl.setImageTmpl(imageTmpl);
		}
		return videoTmpl;
	}
	
	@Transactional
	public InfoflowTmpl getInfoflowTmpl(String id) throws Exception {
		InfoflowTmplModel infoflowTmplModel = infoflowTmplDao.selectByPrimaryKey(id);
		if (infoflowTmplModel == null) {
			throw new ResourceNotFoundException();
		}
		InfoflowTmpl infoflowTmpl = modelMapper.map(infoflowTmplModel, InfoflowTmpl.class);
		String iconTmplId = infoflowTmplModel.getIconId();
		if (!StringUtils.isEmpty(iconTmplId)) {
			ImageTmpl iconTmpl = getImageTmplDetail(iconTmplId);
			infoflowTmpl.setIcon(iconTmpl);
		}
		String image1TmplId = infoflowTmplModel.getImage1Id();
		if (!StringUtils.isEmpty(image1TmplId)) {
			ImageTmpl image1Tmpl = getImageTmplDetail(image1TmplId);
			infoflowTmpl.setImage1(image1Tmpl);
		}
		String image2TmplId = infoflowTmplModel.getImage2Id();
		if (!StringUtils.isEmpty(image2TmplId)) {
			ImageTmpl image2Tmpl = getImageTmplDetail(image2TmplId);
			infoflowTmpl.setImage2(image2Tmpl);
		}
		String image3TmplId = infoflowTmplModel.getImage3Id();
		if (!StringUtils.isEmpty(image3TmplId)) {
			ImageTmpl image3Tmpl = getImageTmplDetail(image3TmplId);
			infoflowTmpl.setImage3(image3Tmpl);
		}
		String image4TmplId = infoflowTmplModel.getImage4Id();
		if (!StringUtils.isEmpty(image4TmplId)) {
			ImageTmpl image4Tmpl = getImageTmplDetail(image4TmplId);
			infoflowTmpl.setImage4(image4Tmpl);
		}
		String image5TmplId = infoflowTmplModel.getImage5Id();
		if (!StringUtils.isEmpty(image5TmplId)) {
			ImageTmpl image5Tmpl = getImageTmplDetail(image5TmplId);
			infoflowTmpl.setImage5(image5Tmpl);
		}
		
		return infoflowTmpl;
	}
}
