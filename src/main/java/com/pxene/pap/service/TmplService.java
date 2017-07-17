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
import com.pxene.pap.domain.models.AdxModel;
import com.pxene.pap.domain.models.AdxTmplModel;
import com.pxene.pap.domain.models.AdxTmplModelExample;
import com.pxene.pap.domain.models.AppModel;
import com.pxene.pap.domain.models.AppTargetModel;
import com.pxene.pap.domain.models.AppTargetModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.CreativeModelExample;
import com.pxene.pap.domain.models.ImageTmplModel;
import com.pxene.pap.domain.models.InfoflowTmplModel;
import com.pxene.pap.domain.models.VideoTmplModel;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AdxDao;
import com.pxene.pap.repository.basic.AdxTmplDao;
import com.pxene.pap.repository.basic.AppDao;
import com.pxene.pap.repository.basic.AppTargetDao;
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
	private AppTargetDao appTargetDao;

	@Autowired
	private InfoflowTmplDao infoflowTmplDao;
	
	@Autowired
	private AppDao appDao;
	
	@Autowired
	private CreativeDao creativeDao;
	
	@Autowired
	private AdxTmplDao adxTmplDao;
	
	@Autowired
	private AdxDao adxDao;
	
	@Autowired
	private CreativeService creativeService;
	 
	
	/**
	 * 查询图片模版详细信息
	 * @param paramId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<ImageTmpl> listImageTmpls(String adxId) throws Exception {
		
		List<ImageTmpl> imageTmplList = new ArrayList<ImageTmpl>();;
		// 查询图片模板id
		AdxTmplModelExample  adxTmpleEx = new AdxTmplModelExample();
		adxTmpleEx.createCriteria().andAdxIdEqualTo(adxId).andAdTypeEqualTo(CodeTableConstant.CREATIVE_TYPE_IMAGE);
		List<AdxTmplModel> adxTmpls = adxTmplDao.selectByExample(adxTmpleEx);
		
		if (adxTmpls != null && !adxTmpls.isEmpty()) {
			ImageTmpl imageTmpl = null;
			for (AdxTmplModel adxTmpl : adxTmpls) {
				// 根据图片模板id查询图片模板详情
				String tmplId = adxTmpl.getTmplId();
				if (!StringUtils.isEmpty(tmplId)) {
					imageTmpl = getImageTmplDetail(tmplId);
					// 根据AdxId查询ADX信息
					AdxModel adx = getAdxById(adxId);
					if (imageTmpl != null && adx != null) {
						// 图片模板信息和ADX信息不为空，将图片模板信息放到list中
						imageTmpl.setAdxId(adxId);
						imageTmpl.setAdxName(adx.getName());
						imageTmplList.add(imageTmpl);
					}
				}				
			}
		}
		return imageTmplList;						
	}
	
	/**
	 * 查询视频模版详细信息
	 * @param paramId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<VideoTmpl> listVideoTmpls(String adxId) throws Exception {
		
		List<VideoTmpl> videpTmplList = new ArrayList<VideoTmpl>();
		
		// 查询视频模板id
		AdxTmplModelExample  adxTmpleEx = new AdxTmplModelExample();
		adxTmpleEx.createCriteria().andAdxIdEqualTo(adxId).andAdTypeEqualTo(CodeTableConstant.CREATIVE_TYPE_VIDEO);
		List<AdxTmplModel> adxTmpls = adxTmplDao.selectByExample(adxTmpleEx);
		
		if (adxTmpls != null && !adxTmpls.isEmpty()) {
			VideoTmpl videoTmpl = null;
			for (AdxTmplModel adxTmpl : adxTmpls) {
				// 根据视频模板id查询视频模板详情
				String tmplId = adxTmpl.getTmplId();
				if (!StringUtils.isEmpty(tmplId)) {
					VideoTmplModel model = videoTmplDao.selectByPrimaryKey(tmplId);
					videoTmpl = modelMapper.map(model, VideoTmpl.class);
					// 根据AdxId查询ADX信息
					AdxModel adx = getAdxById(adxId);
					if (adx != null) {
						 videoTmpl.setAdxId(adxId);
						 videoTmpl.setAdxName(adx.getName());
	                }
					// 根据图片id查询图片模板信息
					String imageTmplId = model.getImageId();
					if (!StringUtils.isEmpty(imageTmplId)) {
						ImageTmpl imageTmpl = getImageTmplDetail(imageTmplId);
						if (imageTmpl != null) {
							videoTmpl.setImageTmpl(imageTmpl);
						}
					}
					videpTmplList.add(videoTmpl);
				}				
			}
		}
		return videpTmplList;
	}

	/**
	 *  根据APPID获得APP信息
	 * @param appId appid
	 * @return
	 */
//    private AppModel getAppById(String appId)
//    {
//        return appDao.selectByPrimaryKey(appId);
//    }
	
	/**
	 * 查询信息流模版详细信息
	 * @param paramId
	 * @return
	 * @throws Exception
	 */
    @Transactional
	public List<InfoflowTmpl> listInfoflowTmpls(String adxId) throws Exception {				
				
		List<InfoflowTmpl> infoTmplList = new ArrayList<InfoflowTmpl>();

		// 查询信息流模板id
		AdxTmplModelExample  adxTmpleEx = new AdxTmplModelExample();
		adxTmpleEx.createCriteria().andAdxIdEqualTo(adxId).andAdTypeEqualTo(CodeTableConstant.CREATIVE_TYPE_INFOFLOW);
		List<AdxTmplModel> adxTmpls = adxTmplDao.selectByExample(adxTmpleEx);
				
		if (adxTmpls != null && !adxTmpls.isEmpty()) {
			InfoflowTmpl infoflowTmpl = null;
			for (AdxTmplModel adxTmpl : adxTmpls) {
				String tmplId = adxTmpl.getTmplId();
				if (!StringUtils.isEmpty(tmplId)) {
					// 根据信息流模板id查询信息流模板信息
					InfoflowTmplModel model = infoflowTmplDao.selectByPrimaryKey(tmplId);
					infoflowTmpl = modelMapper.map(model, InfoflowTmpl.class);
					// 查询ADX信息
					AdxModel adx = getAdxById(adxId);
	                if (adx != null) {
	                	infoflowTmpl.setAdxId(adxId);;
	                	infoflowTmpl.setAdxName(adx.getName());;
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
					infoTmplList.add(infoflowTmpl);
				}
			}
		}
		return infoTmplList;		
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
//	public List<String> getAppidByCampaignId(String campaignId) throws Exception {
//		AppTargetModelExample example = new AppTargetModelExample();
//		example.createCriteria().andCampaignIdEqualTo(campaignId);
//		List<AppTargetModel> list = appTargetDao.selectByExample(example);
//		List<String> appIds = new ArrayList<String>();
//		if (list != null && !list.isEmpty()) {
//			for (AppTargetModel model : list) {
//				String appId = model.getAppId();
//				if (!StringUtils.isEmpty(appId)) {
//					appIds.add(appId);
//				}
//			}
//		}
//		return appIds;
//	}
	
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
	
	/**
	 * 通过ADXId找ADX名称
	 * @param adxId
	 * @return
	 * @throws Exception
	 */
	public AdxModel getAdxById(String adxId) throws Exception {
		AdxModel adx = adxDao.selectByPrimaryKey(adxId);
		return adx;
	}
}
