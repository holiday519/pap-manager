package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.APPTmplBean;
import com.pxene.pap.domain.beans.APPTmplBean.ImageTmpl;
import com.pxene.pap.domain.beans.APPTmplBean.InfoTmpl;
import com.pxene.pap.domain.beans.APPTmplBean.VideoTmpl;
import com.pxene.pap.domain.beans.ImageTmplBean;
import com.pxene.pap.domain.beans.VideoTmplBean;
import com.pxene.pap.domain.model.basic.AppTmplModel;
import com.pxene.pap.domain.model.basic.AppTmplModelExample;
import com.pxene.pap.domain.model.basic.ImageTmplModel;
import com.pxene.pap.domain.model.basic.ImageTmplTypeModel;
import com.pxene.pap.domain.model.basic.InfoFlowTmplModel;
import com.pxene.pap.domain.model.basic.InfoFlowTmplModelExample;
import com.pxene.pap.domain.model.basic.view.TmplImageDetailModelExample;
import com.pxene.pap.domain.model.basic.view.TmplImageDetailModelWithBLOBs;
import com.pxene.pap.domain.model.basic.view.TmplVideoDetailModelExample;
import com.pxene.pap.domain.model.basic.view.TmplVideoDetailModelWithBLOBs;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AppTmplDao;
import com.pxene.pap.repository.basic.ImageTmplDao;
import com.pxene.pap.repository.basic.ImageTmplTypeDao;
import com.pxene.pap.repository.basic.InfoFlowTmplDao;
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
	private TmplImageDetailDao tmplImageDetailDao;

	@Autowired
	private TmplVideoDetailDao tmplVideoDetailDao;

	@Autowired
	private InfoFlowTmplDao infoFlowTmplDao;

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
	public APPTmplBean selectAppTmplImage(String paramId) throws Exception {
		if (StringUtils.isEmpty(paramId)) {
			throw new IllegalArgumentException();
		}
		//appid数组转成list
		String[] appids = paramId.split(",");
		List<String> appIdList = Arrays.asList(appids);
		
		APPTmplBean bean = new APPTmplBean();
		List<ImageTmpl> imageTmplList = new ArrayList<ImageTmpl>();

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
					if (imageTmpl != null) {
						imageTmplList.add(imageTmpl);
					}
				}
			}
		}
		
		if (!imageTmplList.isEmpty()) {
			ImageTmpl[] images = new ImageTmpl[imageTmplList.size()];
			for (int i = 0;i<imageTmplList.size();i++) {
				images[i] = imageTmplList.get(i);
			}
			bean.setImageTmpl(images);
		} else {
			throw new ResourceNotFoundException();
		}
		
		return bean;
	}
	
	/**
	 * 查询视频模版详细信息
	 * @param paramId
	 * @return
	 * @throws Exception
	 */
	public APPTmplBean selectAppTmplVideo(String paramId) throws Exception {
		if (StringUtils.isEmpty(paramId)) {
			throw new IllegalArgumentException();
		}
		//appid数组转成list
		String[] appids = paramId.split(",");
		List<String> appIdList = Arrays.asList(appids);
		
		APPTmplBean bean = new APPTmplBean();
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
			example.createCriteria().andIdIn(tmplIds);
			List<TmplVideoDetailModelWithBLOBs> videos = tmplVideoDetailDao.selectByExampleWithBLOBs(example);
			if (videos != null && !videos.isEmpty()) {
				for (TmplVideoDetailModelWithBLOBs video : videos) {
					VideoTmpl videoTmpl = modelMapper.map(video, VideoTmpl.class);
					if(!StringUtils.isEmpty(video.getImageTmplId())){
						ImageTmpl image = getImageTmplDetail(video.getImageTmplId());
						if (image != null) {
							videoTmpl.setImageTmpl(image);
						}
					}
					videpTmplList.add(videoTmpl);
				}
			}
		}
		if (!videpTmplList.isEmpty()) {
			VideoTmpl[] videos = new VideoTmpl[videpTmplList.size()];
			for (int i = 0;i<videpTmplList.size();i++) {
				videos[i] = videpTmplList.get(i);
			}
			bean.setVideoTmpl(videos);
		} else {
			throw new ResourceNotFoundException();
		}
		return bean;
	}
	
	/**
	 * 查询信息流模版详细信息
	 * @param paramId
	 * @return
	 * @throws Exception
	 */
	public APPTmplBean selectAppTmplInfo(String paramId) throws Exception {
		if (StringUtils.isEmpty(paramId)) {
			throw new IllegalArgumentException();
		}
		
		//appid数组转成list
		String[] appids = paramId.split(",");
		List<String> appIdList = Arrays.asList(appids);
		
		APPTmplBean bean = new APPTmplBean();
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
			InfoFlowTmplModelExample example = new InfoFlowTmplModelExample();
			example.createCriteria().andIdIn(tmplIds);
			List<InfoFlowTmplModel> list = infoFlowTmplDao.selectByExample(example);
			if (list != null && !list.isEmpty()) {
				for (InfoFlowTmplModel model : list) {
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
					infoTmplList.add(infoTmpl);
				}
			}
		}
		
		if (!infoTmplList.isEmpty()) {
			InfoTmpl[] infos = new InfoTmpl[infoTmplList.size()];
			for (int i = 0;i<infoTmplList.size();i++) {
				infos[i] = infoTmplList.get(i);
			}
			bean.setInfoTmpl(infos);
		} else {
			throw new ResourceNotFoundException();
		}
		return bean;
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
}
