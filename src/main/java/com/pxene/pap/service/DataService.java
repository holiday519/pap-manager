package com.pxene.pap.service;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.transaction.Transactional;

import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.ExcelOperateUtil;
import com.pxene.pap.common.ExcelUtil;
import com.pxene.pap.common.RedisHelper;
import com.pxene.pap.common.UUIDGenerator;

import com.pxene.pap.domain.beans.*;
import com.pxene.pap.domain.models.*;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.*;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pxene.pap.constant.CodeTableConstant;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.RedisKeyConstant;
import com.pxene.pap.domain.models.view.CampaignTargetModel;
import com.pxene.pap.domain.models.view.CampaignTargetModelExample;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.repository.basic.view.CampaignTargetDao;
import com.pxene.pap.repository.basic.view.CreativeBasicDao;
import com.pxene.pap.repository.custom.CustomRegionDao;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

@Service
public class DataService extends BaseService {
	@Autowired
	private CreativeService creativeService;
	
	@Autowired
	private AdvertiserDao advertiserDao;
	
	@Autowired
	private CreativeDao creativeDao;

	@Autowired
	private CampaignDao campaignDao;

	@Autowired
	private RegionDao regionDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private EffectDao effectDao;
	
	@Autowired
	private EffectFileDao effectFileDao;
	
	@Autowired
	private CreativeBasicDao creativeBasicDao;
	
	@Autowired
	private AdxCostDao adxCostDao;
	
	@Autowired
	private CampaignTargetDao campaignTargetDao;
	
	@Autowired
	private CustomRegionDao customRegionDao;
	
	@Autowired
	private LandpageCodeHistoryDao landpageCodeHistoryDao;
	
	private static Map<String, Set<String>> table = new HashMap<String, Set<String>>();
	
	@Autowired
	private RedisHelper redisHelper3;

	@Autowired
	private AppService appService;
	
	private static class CREATIVE_DATA_SUFFIX {
		public static String WIN = "@w";
		public static String IMPRESSION = "@m";
		public static String CLICK = "@c";
		public static String EXPENSE = "@e";
		public static String JUMP = "@j";
	}
	private static class CREATIVE_DATA_TYPE {
		public static String ADX = "_adx_";
		public static String REGION = "_region_";
		public static String OS = "_os_";
		public static String NETWORK = "_network_";
		public static String OPERATOR = "_operator_";
	}
	
	static {
		Set<String> network = new HashSet<String>();
		network.add("1");
		network.add("3");
		network.add("4");
		network.add("5");
		table.put("network", network);
		Set<String> operator = new HashSet<String>();
		operator.add("1");
		operator.add("2");
		operator.add("3");
		table.put("operator", operator);
		Set<String> os = new HashSet<String>();
		os.add("1");
		os.add("2");
		os.add("3");
		table.put("os", os);
	}
	
	
	@PostConstruct
    public void selectRedis()
    {
        redisHelper3.select("redis.tertiary.");
    }
	
	
	/**
	 * 查询小时数据
	 * @param startDate
	 * @param endDate
	 * @param advertiserId
	 * @param projectId
	 * @param campaignId
	 * @param creativeId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<AnalysisBean> listTimes(Long startDate, Long endDate, String advertiserId, 
			String projectId, String campaignId, String creativeId) throws Exception {
		List<AnalysisBean> result = null;
		if (creativeId != null && !creativeId.isEmpty()) {
			result = getTimeData4Creative(creativeId, startDate, endDate);
		} else if (campaignId != null && !campaignId.isEmpty()) {
			result = getTimeData4Campaign(campaignId, startDate, endDate);
		} else if (projectId != null && !projectId.isEmpty()) {
			result = getTimeData4Project(projectId, startDate, endDate);
		} else if (advertiserId != null && !advertiserId.isEmpty()) {
			result = getTimeData4Advertiser(advertiserId, startDate, endDate);
		} else {
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		
		return result == null ? new ArrayList<AnalysisBean>() : result;
	}
	
	/**
	 * 查询地域数据
	 * @param startDate
	 * @param endDate
	 * @param advertiserId
	 * @param projectId
	 * @param campaignId
	 * @param creativeId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<AnalysisBean> listRegions(Long startDate, Long endDate, String advertiserId, 
			String projectId, String campaignId, String creativeId) throws Exception {
		List<AnalysisBean> result = null;
		if (creativeId != null && !creativeId.isEmpty()) {
			result = getRegionData4Creative(creativeId, startDate, endDate);
		} else if (campaignId != null && !campaignId.isEmpty()) {
			result = getRegionData4Campaign(campaignId, startDate, endDate);
		} else if (projectId != null && !projectId.isEmpty()) {
			result = getRegionData4Project(projectId, startDate, endDate);
		} else if (advertiserId != null && !advertiserId.isEmpty()) {
			result = getRegionData4Advertiser(advertiserId, startDate, endDate);
		} else {
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		
		return result == null ? new ArrayList<AnalysisBean>() : result;
	}
	
	/**
	 * 查询运营商数据
	 * @param startDate
	 * @param endDate
	 * @param advertiserId
	 * @param projectId
	 * @param campaignId
	 * @param creativeId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<AnalysisBean> listOperators(Long startDate, Long endDate, String advertiserId, 
		String projectId, String campaignId, String creativeId) throws Exception {
		List<AnalysisBean> result = null;
		if (creativeId != null && !creativeId.isEmpty()) {
			result = getOperatorData4Creative(creativeId, startDate, endDate);
		} else if (campaignId != null && !campaignId.isEmpty()) {
			result = getOperatorData4Campaign(campaignId, startDate, endDate);
		} else if (projectId != null && !projectId.isEmpty()) {
			result = getOperatorData4Project(projectId, startDate, endDate);
		} else if (advertiserId != null && !advertiserId.isEmpty()) {
			result = getOperatorData4Advertiser(advertiserId, startDate, endDate);
		} else {
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		
		return result == null ? new ArrayList<AnalysisBean>() : result;
	}
	/**
	 * 查询网络数据
	 * @param startDate
	 * @param endDate
	 * @param advertiserId
	 * @param projectId
	 * @param campaignId
	 * @param creativeId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<AnalysisBean> listNetworks(Long startDate, Long endDate, String advertiserId, 
			String projectId, String campaignId, String creativeId) throws Exception {
		List<AnalysisBean> result = null;
		if (creativeId != null && !creativeId.isEmpty()) {
			result = getNetworkData4Creative(creativeId, startDate, endDate);
		} else if (campaignId != null && !campaignId.isEmpty()) {
			result = getNetworkData4Campaign(campaignId, startDate, endDate);
		} else if (projectId != null && !projectId.isEmpty()) {
			result = getNetworkData4Project(projectId, startDate, endDate);
		} else if (advertiserId != null && !advertiserId.isEmpty()) {
			result = getNetworkData4Advertiser(advertiserId, startDate, endDate);
		} else {
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		
		return result == null ? new ArrayList<AnalysisBean>() : result;
	}
	/**
	 * 查询系统数据
	 * @param startDate
	 * @param endDate
	 * @param advertiserId
	 * @param projectId
	 * @param campaignId
	 * @param creativeId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<AnalysisBean> listSystems(Long startDate, Long endDate, String advertiserId, 
			String projectId, String campaignId, String creativeId) throws Exception {
		List<AnalysisBean> result = null;
		if (creativeId != null && !creativeId.isEmpty()) {
			result = getSystemData4Creative(creativeId, startDate, endDate);
		} else if (campaignId != null && !campaignId.isEmpty()) {
			result = getSystemData4Campaign(campaignId, startDate, endDate);
		} else if (projectId != null && !projectId.isEmpty()) {
			result = getSystemData4Project(projectId, startDate, endDate);
		} else if (advertiserId != null && !advertiserId.isEmpty()) {
			result = getSystemData4Advertiser(advertiserId, startDate, endDate);
		} else {
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		
		return result == null ? new ArrayList<AnalysisBean>() : result;
	}
	

	/**
	 * 列出广告主数据
	 * @param advertiserId
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
     * @throws Exception
     */
	@Transactional
	public List<AdvertiserBean> listAdvertisers(String advertiserId, String type, Long startDate, Long endDate) throws Exception {
		List<AdvertiserBean> result = new ArrayList<AdvertiserBean>();
		String[] advertiserIds = null;
		// 获取要查询的客户ID
		if (advertiserId != null && !advertiserId.isEmpty()) {
			advertiserIds = new String[1];
			advertiserIds[0] = advertiserId;
		} else {
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		// 获取每日数据
		if (CodeTableConstant.SUMMARYWAY_TOTAL.equals(type)) {
			for (String id : advertiserIds) {
				AdvertiserBean bean = getAdvertiserData(id, startDate, endDate);
				if (!isEmptyData(bean)) {
					bean.setName(getAdvertiserName(id));
					result.add(bean);
				}
			}
		} else if (CodeTableConstant.SUMMARYWAY_DAY.equals(type)) {
			String[] days = DateUtils.getDaysBetween(new Date(startDate), new Date(endDate));
			for (String day : days) {
				Map<String, Long> time = DateUtils.getStartAndEndTime(day);
				for (String id : advertiserIds) {
					AdvertiserBean bean = getAdvertiserData(id, time.get("startTime"), time.get("endTime"));
					if (!isEmptyData(bean)) {
						bean.setDate(day);
						bean.setName(getAdvertiserName(id));
						result.add(bean);
					}
				}
			}
		} else {
			throw new IllegalArgumentException(PhrasesConstant.PARAM_ERROR);
		}
		return result;
	}
	
	/**
	 * 列出项目数据
	 * @param advertiserId
	 * @param projectId
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
     * @throws Exception
     */
	@Transactional
	public List<ProjectBean> listProjects(String advertiserId, String projectId, String type, Long startDate, Long endDate) throws Exception {
		List<ProjectBean> result = new ArrayList<ProjectBean>();
		String[] projectIds = null;
		// 获取要查询的项目ID
		if (projectId != null && !projectId.isEmpty()) {
			projectIds = new String[1];
			projectIds[0] = projectId;
		} else if (advertiserId != null && !advertiserId.isEmpty()) {
			projectIds = getProjectIdsByAdvertiserId(advertiserId);
		} else {
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		// 获取每日数据
		if (CodeTableConstant.SUMMARYWAY_TOTAL.equals(type)) {
			for (String id : projectIds) {
				ProjectBean bean = getProjectData(id, startDate, endDate);
				if (!isEmptyData(bean)) {
					bean.setName(getProjectName(id));
					result.add(bean);
				}
			}
		} else if (CodeTableConstant.SUMMARYWAY_DAY.equals(type)) {
			String[] days = DateUtils.getDaysBetween(new Date(startDate), new Date(endDate));
			for (String day : days) {
				Map<String, Long> time = DateUtils.getStartAndEndTime(day);
				for (String id : projectIds) {
					ProjectBean bean = getProjectData(id, time.get("startTime"), time.get("endTime"));
					if (!isEmptyData(bean)) {
						bean.setDate(day);
						bean.setName(getProjectName(id));
						result.add(bean);
					}
				}
			}
		} else {
			throw new IllegalArgumentException(PhrasesConstant.PARAM_ERROR);
		}
		return result;
	}

	/**
	 * 列出活动数据
	 * @param advertiserId
	 * @param projectId
	 * @param campaignId
	 * @param type
	 * @param startDate
	 * @param endDate
     * @return
     * @throws Exception
     */
	@Transactional
	public List<CampaignBean> listCampaigns(String advertiserId, String projectId, String campaignId, 
			String type, Long startDate, Long endDate) throws Exception {
		List<CampaignBean> result = new ArrayList<CampaignBean>();
		String[] campaignIds = null;
		// 获取要查询的活动ID
		if (campaignId != null && !campaignId.isEmpty()) {
			campaignIds = new String[1];
			campaignIds[0] = campaignId;
		} else if (projectId != null && !projectId.isEmpty()) {
			campaignIds = getCampaignIdsByProjectId(projectId);
		} else if (advertiserId != null && !advertiserId.isEmpty()) {
			campaignIds = getCampaignIdsByAdvertiserId(advertiserId);
		} else {
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		// 获取每日数据
		if (CodeTableConstant.SUMMARYWAY_TOTAL.equals(type)) {
			for (String id : campaignIds) {
				CampaignBean bean = getCampaignData(id, startDate, endDate);
				if (!isEmptyData(bean)) {
					bean.setName(getCampaignName(id));
					result.add(bean);
				}
			}
		} else if (CodeTableConstant.SUMMARYWAY_DAY.equals(type)) {
			String[] days = DateUtils.getDaysBetween(new Date(startDate), new Date(endDate));
			for (String day : days) {
				Map<String, Long> time = DateUtils.getStartAndEndTime(day);
				for (String id : campaignIds) {
					CampaignBean bean = getCampaignData(id, time.get("startTime"), time.get("endTime"));
					if (!isEmptyData(bean)) {
						bean.setDate(day);
						bean.setName(getCampaignName(id));
						result.add(bean);
					}
				}
			}
		} else {
			throw new IllegalArgumentException(PhrasesConstant.PARAM_ERROR);
		}
		return result;
	}

	/**
	 * 列出创意数据
	 * @param advertiserId
	 * @param projectId
	 * @param campaignId
	 * @param type
	 * @param startDate
	 * @param endDate
     * @return
     * @throws Exception
     */
	@Transactional
	public List<CreativeBean> listCreatives(String advertiserId, String projectId, String campaignId, 
			String creativeId, String type, Long startDate, Long endDate) throws Exception{
		List<CreativeBean> result = new ArrayList<CreativeBean>();
		String[] creativeIds = null;
		if (creativeId != null && !creativeId.isEmpty()) {
			creativeIds = new String[1];
			creativeIds[0] = creativeId;
		} else if (campaignId != null && !campaignId.isEmpty()) {
			creativeIds = getCreativeIdsByCampaignId(campaignId);
		} else if (projectId != null && !projectId.isEmpty()) {
			creativeIds = getCreativeIdsByPorjectId(projectId);
		} else if (advertiserId != null && !advertiserId.isEmpty()) {
			creativeIds = getCreativeIdsByAdvertiserId(advertiserId);
		} else {
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		
		if (CodeTableConstant.SUMMARYWAY_TOTAL.equals(type)) {
			for (String id : creativeIds) {
				CreativeBean bean = getCreativeData(id, startDate, endDate);
				if (!isEmptyData(bean)) {
					result.add(bean);
				}
			}
		} else if (CodeTableConstant.SUMMARYWAY_DAY.equals(type)) {
			String[] days = DateUtils.getDaysBetween(new Date(startDate), new Date(endDate));
			for (String day : days) {
				Map<String, Long> time = DateUtils.getStartAndEndTime(day);
				for (String id : creativeIds) {
					CreativeBean bean = getCreativeData(id, time.get("startTime"), time.get("endTime"));
					if (!isEmptyData(bean)) {
						bean.setDate(day);
						result.add(bean);
					}
				}
			}
		} else {
			throw new IllegalArgumentException(PhrasesConstant.PARAM_ERROR);
		}
		return result;
	}


    /**
     * 转化数据导入
     * @param file      模版文件
     * @param projectId 项目ID
     */
    @Transactional
    public void importEffect(MultipartFile file, String projectId) throws IOException, EncryptedDocumentException, IllegalArgumentException, InvalidFormatException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException
    {
        InputStream inputStream = file.getInputStream();
       
        List<EffectModel> modelList = null;
        try
        {
            modelList = readTemplateFile(inputStream);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(PhrasesConstant.EFFECT_TEMPLATE_FORMAT_ERROR);
        }
        
        if (modelList == null || modelList.isEmpty())
        {
            throw new IllegalArgumentException(PhrasesConstant.EFFECT_TEMPLATE_FORMAT_ERROR);
        }
        
        // 匹配的监测码
        List<String>  marryCodes = new ArrayList<String>();
        
        // 遍历Excel文件中的每个数据行
        for (EffectModel model : modelList)
        {
        	Date date = model.getDate();
			if (date.before(new Date())) {
				// 导入今天及之前的数据
				model.setId(UUIDGenerator.getUUID());
	            model.setProjectId(projectId);
	            
	            EffectModelExample example = new EffectModelExample();
	            
	            // 使用日期、监测码作为联合主键，查询该条记录是否已存在
	            example.createCriteria().andCodeEqualTo(model.getCode()).andDateEqualTo(model.getDate());

	            List<EffectModel> effectsInDB = effectDao.selectByExample(example);
	            
	            // 如果记录已存在，则更新，否则直接插入
	            if (effectsInDB != null && !effectsInDB.isEmpty())
	            {
	                effectDao.updateByExampleSelective(model, example);
	            }
	            else
	            {
	                effectDao.insert(model);
	            }
	            
	            // 查询匹配数量
	            String code = model.getCode();           
	            // 查询项目下活动使用的监测码：根据项目id查询活动信息
	            CampaignModelExample campaignEx = new CampaignModelExample();
	            campaignEx.createCriteria().andProjectIdEqualTo(projectId);
	            List<CampaignModel> campaigns = campaignDao.selectByExample(campaignEx);
	            // Excel文件中的每个数据行对应的监测码日期date有没有用到这个code监测码
	            if (campaigns != null && !campaigns.isEmpty()) {
	            	Set<String> usedCodes = new HashSet<String>();
	            	for (CampaignModel campaign : campaigns) {
	            		// 查询活动在execl文件时间里使用的监测码的情况
	            		LandpageCodeHistoryModelExample exampleHistory = new LandpageCodeHistoryModelExample();
	            		exampleHistory.createCriteria().andCampaignIdEqualTo(campaign.getId());
	            		List<LandpageCodeHistoryModel> historys = landpageCodeHistoryDao.selectByExample(exampleHistory);
	            		if (historys != null && !historys.isEmpty()) {
	            			// 如果不为空，将监测码放到一个集合中            			
	            			for (LandpageCodeHistoryModel history : historys) {
	            				Date start = history.getStartTime();
	            				Date end = history.getEndTime();
	            				// 转换格式：如果date与startDate同一天，date可能小于startDate
	            				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	            				String strDate = dateFormat.format(start); 
	            				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	            				Date startDate = null;
								try {
									startDate = format.parse(strDate);
								} catch (ParseException e) {
									e.printStackTrace();
								}
	            				// 判断是否在时间范围内：date在开始时间和结束时间之间，即date大于等于startDate并且小于等于end
	            				if ((date.after(startDate) || date.equals(startDate)) && date.before(end)) {
	            					String[] strCodes = history.getCodes().split(",");
	            					if (strCodes != null && strCodes.length > 0) {
	            						for (String strCode : strCodes) {
	            							usedCodes.add(strCode);
	            						}
	            					}	            					
	            				}            				
	            			}
	            		}
	            	}
	            	if (usedCodes.contains(code)) {
	            		// 如果监测码历史记录表在Excel文件该行的日期使用的监测码历史记录中包含上传的监测码，则将其放到list集合中
	            		marryCodes.add(code);
	            	}            	
	            }
			}           
        }
        
        // 向上传文件插入数据
        // 1.获取项目名称
        ProjectModel project = projectDao.selectByPrimaryKey(projectId);
        String projectName = project.getName();
        // 2. 插入属性
        EffectFileModel effectFile = new EffectFileModel();
        effectFile.setId(UUIDGenerator.getUUID());             // 文件id
        effectFile.setName(file.getOriginalFilename());        // 文件名称
        effectFile.setProjectId(projectId);                    // 项目id
        effectFile.setProjectName(projectName);                // 项目名称
        effectFile.setAmount(marryCodes.size());               // 匹配数量
        // 3.插入
        effectFileDao.insert(effectFile);
    }
    
    @Transactional
    public List<EffectFileBean> listEffectFiles() throws Exception {
    	// 查询上传文件信息
    	EffectFileModelExample example = new EffectFileModelExample();
    	List<EffectFileModel> models = effectFileDao.selectByExample(example);
    	// 定义返回的list
    	List<EffectFileBean> beans = new ArrayList<EffectFileBean>();
    	// 遍历数据库中查询到的全部结果，逐个将DAO创建的新对象复制回传输对象中
    	for (EffectFileModel model : models) {
    		EffectFileBean bean = modelMapper.map(model, EffectFileBean.class);
    		beans.add(bean);
    	}
		return beans;   	
    }
    
    /**
     * 读取转化数据模板文件，转换成一个实体列表。
     * @param inp   上传文件的字节输入流
     * @return
     * @throws EncryptedDocumentException
     * @throws InvalidFormatException
     * @throws FileNotFoundException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private List<EffectModel> readTemplateFile(InputStream inp) throws EncryptedDocumentException, InvalidFormatException, FileNotFoundException, IOException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException
    {
        Workbook wb = WorkbookFactory.create(inp);
       
        Sheet sheet = wb.getSheetAt(0);
        
        Row firstRow = sheet.getRow(0);
        
        // 模板文件中数据行的行号（base 0）：除去指标行（A1，A2....A10)、除去列头（日期、监测码、注册数...）
        int beginDataLine = 2;
        
        // 获得当前Sheet的总行数（不为空的行数）
        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
        
        // 获得第一行中共有多少个非空列
        int phyNumOfCells = firstRow.getPhysicalNumberOfCells() - 2;
        
        // 获得第一行中第一个不为空的逻辑单元格（base 0），即，“指标”的第一列（最小为A1，最大为A10）
        int firstCellIndex = 2;//firstRow.getFirstCellNum();
        
        // 获得第一行中最后一个不为空的逻辑单元格（base 1），即，“指标”的最后一列（最小为A1，最大为A10）
        int lastCellIndex = firstRow.getLastCellNum();
        
        // 构建第一行中的物理列号与单元格内容的映射
        Map<Integer, String> titleMap = buildColumnCellMap(firstRow, firstCellIndex, lastCellIndex);
        
        // --> 检查列边界：保证“指标”列左侧有日期和监测码两列，保证“指标”列最多为10列，保证“指标列”不间断
        if (firstCellIndex < 2 || lastCellIndex > 12)
        {
            throw new IllegalArgumentException(PhrasesConstant.EFFECT_TEMPLATE_FORMAT_ERROR);
        }
        if (lastCellIndex != firstCellIndex + phyNumOfCells)
        {
            throw new IllegalArgumentException(PhrasesConstant.EFFECT_TEMPLATE_FORMAT_ERROR);
        }
        
        // --> 检查行边界
        if (physicalNumberOfRows < beginDataLine)
        {
            throw new IllegalArgumentException(PhrasesConstant.EFFECT_TEMPLATE_FORMAT_ERROR);
        }
        
        // --> 检查指标列映射
        if (titleMap == null || titleMap.isEmpty())
        {
            throw new IllegalArgumentException(PhrasesConstant.EFFECT_TEMPLATE_FORMAT_ERROR);
        }
        
        Row tmpRow = null;
        Cell tmpCell = null;
        List<EffectModel> list = new ArrayList<EffectModel>();
        
        // 遍历全部数据行
        for (int i = beginDataLine; i < physicalNumberOfRows; i++)
        {
            tmpRow = sheet.getRow(i);
            
            Class<EffectModel> effectClass = EffectModel.class;
            EffectModel td = effectClass.newInstance();
            
            Cell dateCell = tmpRow.getCell(0);
            Cell codeCell = tmpRow.getCell(1);
            
            // 如果第一列日期设置不正确
            if (dateCell == null || codeCell == null)
            {
                throw new IllegalArgumentException(PhrasesConstant.EFFECT_TEMPLATE_FORMAT_ERROR);
            }
            
            Date firstColumnValue = dateCell.getDateCellValue();
            if (firstColumnValue == null)
            {
                throw new InvalidFormatException(PhrasesConstant.EFFECT_TEMPLATE_FORMAT_ERROR);
            }
            else
            {
                td.setDate(firstColumnValue);
            }
            
            // 如果第二列监测码设置不正确
            String secondColumnValue = null;
            CellType cellTypeEnum = codeCell.getCellTypeEnum();
            if (cellTypeEnum == CellType.NUMERIC)
            {
                secondColumnValue = String.valueOf(codeCell.getNumericCellValue());
            }
            else if (cellTypeEnum == CellType.STRING)
            {
                secondColumnValue = codeCell.getStringCellValue();
            }
            
            if (!StringUtils.isEmpty(secondColumnValue))
            {
                td.setCode(secondColumnValue);
            }
            else 
            {
                throw new InvalidFormatException(PhrasesConstant.EFFECT_TEMPLATE_FORMAT_ERROR);
            }
            
            
            // 遍历全部的“指标”列（不包含第一列日期，第二列监测码以外的全部列，形如A1, A2, ... , A10）
            for (int j = firstCellIndex; j < lastCellIndex; j++)
            {
                tmpCell = tmpRow.getCell(j);
                
                String columnVal = titleMap.get(j);// 根据指定的物理列号，获得它是属于哪个指标，如：第2列，对应的是A3，则调用setA3()
                
                String methodName = "set" + columnVal;// 拼接成反射需要调用的方法名
                
                Method method = effectClass.getDeclaredMethod(methodName, Double.class);
               
                method.invoke(td, tmpCell.getNumericCellValue());
            }
            
            list.add(td);
        }
        
        wb.close();
        return list;
    }


    /**
     * 构建第一行中的自定义列名与物理列值的映射。
     * 如物理单元格：C1,D1,E1中分别保存的值为A3,A2,A1，则构造一个Map，Key为物理列列号，Value为单元格中的值：2-A3, 3-A2, 4-A1
     * @param firstRow          第一行对象
     * @param firstCellIndex    获取第一个不为空的单元格是第几列（物理列号）
     * @param lastCellIndex     获取最后一个不为空的单元格是第几列（物理列号）
     * @return
     */
    private static Map<Integer, String> buildColumnCellMap(Row firstRow, int firstCellIndex, int lastCellIndex)
    {
        Map<Integer, String> titleMap = new HashMap<Integer, String>();
        for (int i = firstCellIndex; i < lastCellIndex; i++)
        {
            titleMap.put(i, firstRow.getCell(i).getStringCellValue());
        }
        return titleMap;
    }

	/**
	 * 把数据导入到07版Excel中，并下载
	 * @param type
	 * @param datas
	 * @param fileName
	 * @param response
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws UnsupportedEncodingException
     */
    public void exportDataToExcel(String type, List<? extends BasicDataBean> datas, String fileName, HttpServletResponse response) 
    		throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, UnsupportedEncodingException {
    	if (datas == null || datas.isEmpty()) {
    		return;
		}
    	
		// 定义列名称和列宽度
		String[] recoresColumns;
		// 定义需要显示在Excel行中的实体Bean中的属性名称
		String[] recoresFields;
		// REVIEW ME: 字符串操作尽量避免中文
		if (type.equals(CodeTableConstant.SUMMARYWAY_TOTAL)) {//汇总
			if (fileName.startsWith("creatives")) {
				recoresColumns = new String[]{"ID_#_3000", "展现数_#_3000", "点击数_#_3000", "CTR_#_3000", "二跳数_#_3000", "成本_#_3000","修正成本_#_3000", "千次展现成本_#_4000", "点击成本_#_3000", "二跳成本_#_3000"};
				recoresFields = new String[]{"id", "impressionAmount", "clickAmount", "clickRate", "jumpAmount", "totalCost","adxCost", "impressionCost", "clickCost", "jumpCost"};
			} else {
				recoresColumns = new String[]{"名称_#_3000", "展现数_#_3000", "点击数_#_3000", "CTR_#_3000", "二跳数_#_3000", "成本_#_3000", "修正成本_#_3000","千次展现成本_#_4000", "点击成本_#_3000", "二跳成本_#_3000"};
				recoresFields = new String[]{"name", "impressionAmount", "clickAmount", "clickRate", "jumpAmount", "totalCost","adxCost", "impressionCost", "clickCost", "jumpCost"};
			}
		} else {
			if (fileName.startsWith("creatives")) {
				recoresColumns = new String[]{"日期_#_3000", "ID_#_3000", "展现数_#_3000", "点击数_#_3000", "CTR_#_3000", "二跳数_#_3000", "成本_#_3000","修正成本_#_3000", "千次展现成本_#_4000", "点击成本_#_3000", "二跳成本_#_3000"};
				recoresFields = new String[]{"date", "id", "impressionAmount", "clickAmount", "clickRate", "jumpAmount", "totalCost","adxCost", "impressionCost", "clickCost", "jumpCost"};
			} else {
				recoresColumns = new String[]{"日期_#_3000", "名称_#_3000", "展现数_#_3000", "点击数_#_3000", "CTR_#_3000", "二跳数_#_3000", "成本_#_3000", "修正成本_#_3000","千次展现成本_#_4000", "点击成本_#_3000", "二跳成本_#_3000"};
				recoresFields = new String[]{"date", "name", "impressionAmount", "clickAmount", "clickRate", "jumpAmount", "totalCost","adxCost", "impressionCost", "clickCost", "jumpCost"};
			}
		}

		XSSFWorkbook workBook = new XSSFWorkbook();
		ExcelUtil<BasicDataBean> excelUtil = new ExcelUtil<BasicDataBean>();
		String sheetName ="sheet0";
		//把数据添加到excel中
		setDataToExcel(excelUtil, workBook, sheetName, datas, recoresColumns, recoresFields);
		//把文件写入到流中
		ByteArrayInputStream inputStream = (ByteArrayInputStream) ExcelUtil.writeExcelToStream(workBook);
		//下载07Excelw文件
		ExcelOperateUtil.downloadExcel07(inputStream, response, fileName);
    }

	/**
	 * 把数据添加到07版exel中
	 * @param excelUtil
	 * @param workBook
	 * @param sheetName
	 * @param datas
	 * @param headerColumns
	 * @param fieldColumns
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws java.lang.IllegalArgumentException
     * @throws InvocationTargetException
     */
	public void setDataToExcel(ExcelUtil excelUtil, XSSFWorkbook workBook, String sheetName, List<? extends BasicDataBean> datas, String[] headerColumns, String[] fieldColumns)
			throws NoSuchMethodException, IllegalAccessException, java.lang.IllegalArgumentException, InvocationTargetException
	{

		XSSFSheet sheet = workBook.createSheet(sheetName);

		excelUtil.setGenerateHeader(workBook, sheet, headerColumns);
		//默认单元格格式
		XSSFCellStyle style = excelUtil.getCellStyle(workBook, false);

		//货币格式保留两位小数
		XSSFCellStyle currencyStyle = excelUtil.getCellStyle(workBook, false);
		XSSFDataFormat dataFormat = workBook.createDataFormat();
		currencyStyle.setDataFormat(dataFormat.getFormat("￥###0.00"));
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");

		//千分位格式化
		DecimalFormat decimalFormat = new DecimalFormat(".00\u2030");

		int rowNum = 0;
		for (BasicDataBean data : datas)
		{
			rowNum++;
			Row row = sheet.createRow(rowNum);
			row.setHeightInPoints(25);
			for (int i = 0; i < fieldColumns.length; i++)
			{
				String fieldName = fieldColumns[i];
				Cell cell = row.createCell(i);
				cell.setCellStyle(style);
				if ("id".equals(fieldName)) {
					// 如果是ID字段，肯定是创意数据
					CreativeBean bean = (CreativeBean)data;
					cell.setCellValue(bean.getId());
				}
				if ("name".equals(fieldName)) {
					String name = "";
					if (data instanceof CampaignBean) {
						CampaignBean bean = (CampaignBean)data;
						name = bean.getName();
					}
					if (data instanceof ProjectBean) {
						ProjectBean bean = (ProjectBean)data;
						name = bean.getName();
					}
					if (data instanceof AdvertiserBean) {
						AdvertiserBean bean = (AdvertiserBean)data;
						name = bean.getName();
					}
					cell.setCellValue(name);
				}
				if ("date".equals(fieldName)) {
					cell.setCellValue(data.getDate());
				}
				if ("impressionAmount".equals(fieldName)) {
					cell.setCellValue(data.getImpressionAmount());
				}
				if ("clickAmount".equals(fieldName)) {
					cell.setCellValue(data.getClickAmount());
				}
				if ("clickRate".equals(fieldName)) {
					cell.setCellValue(decimalFormat.format(data.getClickRate()));
				}
				if ("jumpAmount".equals(fieldName)) {
					cell.setCellValue(data.getJumpAmount());
				}
				if ("totalCost".equals(fieldName)) {
					cell.setCellStyle(currencyStyle);
					cell.setCellValue(data.getTotalCost());
				}
				if ("adxCost".equals(fieldName)) {
					cell.setCellStyle(currencyStyle);
					cell.setCellValue(data.getAdxCost());
				}
				if ("impressionCost".equals(fieldName)) {
					cell.setCellStyle(currencyStyle);
					cell.setCellValue(data.getImpressionCost());
				}
				if ("clickCost".equals(fieldName)) {
					cell.setCellStyle(currencyStyle);
					cell.setCellValue(data.getClickCost());
				}
				if ("jumpCost".equals(fieldName)) {
					cell.setCellStyle(currencyStyle);
					cell.setCellValue(data.getJumpAmount());
				}
				
			}
		}
	}

	/**
	 * 给数据excel命名
	 * @param type
	 * @param startDate
	 * @param endDate
     * @return
     */
	@Transactional
	public String renameDatasExcel(String type, Long startDate, Long endDate) {
		String typeName = "";
		if(type.equals(CodeTableConstant.SUMMARYWAY_TOTAL)){
			typeName = "total";
		}else if(type.equals(CodeTableConstant.SUMMARYWAY_DAY)){
			typeName = "day";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return typeName + "-" + sdf.format(new Date(startDate)) + "to" + sdf.format(new Date(endDate));
	}
	
	/**
	 * 获取单个创意一个时间段内的数据
	 * @param creativeId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public CreativeBean getCreativeData(String creativeId, long startDate, long endDate) {
		// 先从redis中查询是否有该创意的数据
		CreativeBean creativeData = new CreativeBean();
		String dayKey = RedisKeyConstant.CREATIVE_DATA_DAY + creativeId;
		creativeData.setId(creativeId);
		if (redisHelper3.exists(dayKey)) {
			String[] days = DateUtils.getDaysBetween(new Date(startDate), new Date(endDate));
			// 修正花费要查出的相关信息
			// 1.创意投放的adxID
			CreativeBasicModelExample creativeBasicExample = new CreativeBasicModelExample();
			creativeBasicExample.createCriteria().andCreativeIdsLike("%" + creativeId + "%");
			List<CreativeBasicModel> creativeBasics = creativeBasicDao.selectByExampleWithBLOBs(creativeBasicExample);
			if (creativeBasics.isEmpty()) {
				return creativeData;
			}
			// 获取日数据对象
			Map<String, String> dayData = redisHelper3.hget(dayKey);
			long impression = 0L, click = 0L, jump = 0L;
			double expense = 0D, adxExpense = 0D;
			for (String day : days) {
				// 基础数据累加
				String impressionKey = day + CREATIVE_DATA_SUFFIX.IMPRESSION;
				if (dayData.containsKey(impressionKey)) {
					impression += Long.parseLong(dayData.get(impressionKey));
				}
				String clickKey = day + CREATIVE_DATA_SUFFIX.CLICK;
				if (dayData.containsKey(clickKey)) {
					click += Long.parseLong(dayData.get(clickKey));
				}
				String jumpKey = day + CREATIVE_DATA_SUFFIX.JUMP;
				if (dayData.containsKey(jumpKey)) {
					jump += Long.parseLong(dayData.get(jumpKey));
				}
				String expenseKey = day + CREATIVE_DATA_SUFFIX.EXPENSE;
				if (dayData.containsKey(expenseKey)) {
					expense += Double.parseDouble(dayData.get(expenseKey)) / 100;
				}
				
				// 修正花费累加
				for (CreativeBasicModel basic : creativeBasics) {
					String adxId = basic.getAdxId();
					String adxExpenseKey = day + CREATIVE_DATA_TYPE.ADX + adxId + CREATIVE_DATA_SUFFIX.EXPENSE;
					// 判断该adx是否有数据
					if (dayData.containsKey(adxExpenseKey)) {
						// 获取当天的花费
						double adxExpenseVal = Double.parseDouble(dayData.get(adxExpenseKey)) / 100;
						// 获取当天的修正比
						AdxCostModelExample adxCostExample = new AdxCostModelExample();
						Date dayDate = DateUtils.strToDate(day, "yyyyMMdd");
						adxCostExample.createCriteria().andAdxIdEqualTo(adxId).andStartDateLessThanOrEqualTo(dayDate).andEndDateGreaterThanOrEqualTo(dayDate);
						List<AdxCostModel> adxCosts = adxCostDao.selectByExample(adxCostExample);
						if (adxCosts.isEmpty()) {
							continue;
						}
						float ratio = adxCosts.get(0).getRatio();
						adxExpense += adxExpenseVal * ratio;
					}
				}
			}
//			creativeData.setId(creativeId);
			creativeData.setImpressionAmount(impression);
			creativeData.setClickAmount(click);
			creativeData.setJumpAmount(jump);
			creativeData.setTotalCost(expense);
			creativeData.setAdxCost(adxExpense);
			creativeData.setClickRate(impression == 0 ? 0f : (float)(click/(double)impression));
			creativeData.setImpressionCost(impression == 0 ? 0f : (float)(expense/impression*1000));
			creativeData.setClickCost(click == 0 ? 0f : (float)(expense/click));
			creativeData.setJumpCost(jump == 0 ? 0f : (float)(expense/jump));
			//添加素材路径和活动名称
			addOtherInfoToCreativeBean(creativeData);

		}
		
		return creativeData;
	}

	/**
	 * 把其他信息添加到CreativeBean
	 * 素材路径，活动名称
	 * @param creativeBean
     */
	public void addOtherInfoToCreativeBean(CreativeBean creativeBean){
		String[] materialPaths = null;
		if(creativeBean !=null){
			String creativeId = creativeBean.getId();
			if(creativeId !=null && !creativeId.isEmpty()){
				CreativeModel creativeModel = creativeDao.selectByPrimaryKey(creativeId);
				if(creativeModel != null){
					//添加素材
					creativeService.getMaterialInfoByCreativeModel(creativeModel,creativeBean);
					//获取活动名称
					CampaignModel campaignModel = campaignDao.selectByPrimaryKey(creativeModel.getCampaignId());
					if(campaignModel !=null){
						creativeBean.setCampaignName(campaignModel.getName());
					}else{
						creativeBean.setCampaignName("");
					}
				}
			}
		}

	}

	
	/**
	 * 获取单个活动一个时间段内的数据
	 * @param campaignId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public CampaignBean getCampaignData(String campaignId, long startDate, long endDate) {
		CampaignBean campaignData = new CampaignBean();
		String[] creativeIds = getCreativeIdsByCampaignId(campaignId);
		long impression = 0L, click = 0L, jump = 0L;
		double expense = 0D, adxExpense = 0D;
		for (String creativeId : creativeIds) {
			CreativeBean bean = getCreativeData(creativeId, startDate, endDate);
			impression += bean.getImpressionAmount();
			click += bean.getClickAmount();
			jump += bean.getJumpAmount();
			expense += bean.getTotalCost();
			adxExpense += bean.getAdxCost();
		}
		campaignData.setId(campaignId);
		campaignData.setImpressionAmount(impression);
		campaignData.setClickAmount(click);
		campaignData.setClickRate(impression == 0 ? 0f : (float)(click/(double)impression));
		campaignData.setJumpAmount(jump);
		campaignData.setTotalCost(expense);
		campaignData.setAdxCost(adxExpense);
		campaignData.setImpressionCost(impression == 0 ? 0f : (float)(expense/impression*1000));
		campaignData.setClickCost(click == 0 ? 0f : (float)(expense/click));
		campaignData.setJumpCost(jump == 0 ? 0f : (float)(expense/jump));
		//将app信息添加到活动
		addAppToCampaign(campaignData);

		return campaignData;
	}

	/**
	 * 将app信息添加到活动
	 * @param campaignBean
     */
	public void addAppToCampaign(CampaignBean campaignBean){

		if(campaignBean != null && campaignBean.getId()!=null) {
			String campaignId = campaignBean.getId();
			List<AppModel> appModels = appService.getAppByCampaignId(campaignId);
			if(appModels != null && !appModels.isEmpty()){
				CampaignBean.Target target = new CampaignBean.Target();
				CampaignBean.Target.App[] apps = new CampaignBean.Target.App[appModels.size()];
				int i=0;
				for(AppModel appModel : appModels){
					CampaignBean.Target.App app = new CampaignBean.Target.App();
					app.setName(appModel.getAppName());
					apps[i++]=app;
				}
				target.setApps(apps);
				campaignBean.setTarget(target);
			}
		}
	}
	
	/**
	 * 获取单个项目一个时间段内的数据
	 * @param projectId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public ProjectBean getProjectData(String projectId, long startDate, long endDate) {
		ProjectBean projectData = new ProjectBean();
		String[] campaignIds = getCampaignIdsByProjectId(projectId);
		long impression = 0L, click = 0L, jump = 0L;
		double expense = 0D, adxExpense = 0D;
		for (String campaignId : campaignIds) {
			CampaignBean bean = getCampaignData(campaignId, startDate, endDate);
			impression += bean.getImpressionAmount();
			click += bean.getClickAmount();
			jump += bean.getJumpAmount();
			expense += bean.getTotalCost();
			adxExpense += bean.getAdxCost();
		}
		projectData.setId(projectId);
		projectData.setImpressionAmount(impression);
		projectData.setClickAmount(click);
		projectData.setClickRate(impression == 0 ? 0f : (float)(click/(double)impression));
		projectData.setJumpAmount(jump);
		projectData.setTotalCost(expense);
		projectData.setAdxCost(adxExpense);
		projectData.setImpressionCost(impression == 0 ? 0f : (float)(expense/impression*1000));
		projectData.setClickCost(click == 0 ? 0f : (float)(expense/click));
		projectData.setJumpCost(jump == 0 ? 0f : (float)(expense/jump));
		
		return projectData;
	}
	
	/**
	 * 获取单个客户一个时间段内的数据
	 * @param creativeId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public AdvertiserBean getAdvertiserData(String advertiserId, long startDate, long endDate) {
		AdvertiserBean advertiserData = new AdvertiserBean();
		String[] projectIds = getProjectIdsByAdvertiserId(advertiserId);
		long impression = 0L, click = 0L, jump = 0L;
		double expense = 0D, adxExpense = 0D;
		for (String projectId : projectIds) {
			ProjectBean bean = getProjectData(projectId, startDate, endDate);
			impression += bean.getImpressionAmount();
			click += bean.getClickAmount();
			jump += bean.getJumpAmount();
			expense += bean.getTotalCost();
			adxExpense += bean.getAdxCost();
		}
		advertiserData.setId(advertiserId);
		advertiserData.setImpressionAmount(impression);
		advertiserData.setClickAmount(click);
		advertiserData.setClickRate(impression == 0 ? 0f : (float)(click/(double)impression));
		advertiserData.setJumpAmount(jump);
		advertiserData.setTotalCost(expense);
		advertiserData.setAdxCost(adxExpense);
		advertiserData.setImpressionCost(impression == 0 ? 0f : (float)(expense/impression*1000));
		advertiserData.setClickCost(click == 0 ? 0f : (float)(expense/click));
		advertiserData.setJumpCost(jump == 0 ? 0f : (float)(expense/jump));
		
		return advertiserData;
	}
	
	/**
	 * 为创意查询时段数据
	 * @param creativeId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private List<AnalysisBean> getTimeData4Creative(String creativeId, long startDate, long endDate) {
		List<AnalysisBean> creativeDatas = new ArrayList<AnalysisBean>();
		String hourKey = RedisKeyConstant.CREATIVE_DATA_HOUR + creativeId;
		
		if (redisHelper3.exists(hourKey)) {
			String[] hours = new String[]{"00","01","02","03","04","05","06","07","08","09","10",
					"11","12","13","14","15","16","17","18","19","20","21","22","23"};
			String[] days = DateUtils.getDaysBetween(new Date(startDate), new Date(endDate));
			// 获取小时数据对象
			Map<String, String> hourData = redisHelper3.hget(hourKey);
			for (String hour : hours) {
				AnalysisBean creativeData = new AnalysisBean();
				long impression = 0L, click = 0L, jump = 0L;
				double expense = 0D;
				for (String day : days) {
					String impressionKey = day + hour + CREATIVE_DATA_SUFFIX.IMPRESSION;
					if (hourData.containsKey(impressionKey)) {
						impression += Long.parseLong(hourData.get(impressionKey));
					}
					String clickKey = day + hour + CREATIVE_DATA_SUFFIX.CLICK;
					if (hourData.containsKey(clickKey)) {
						click += Long.parseLong(hourData.get(clickKey));
					}
					String jumpKey = day + hour + CREATIVE_DATA_SUFFIX.JUMP;
					if (hourData.containsKey(jumpKey)) {
						jump += Long.parseLong(hourData.get(jumpKey));
					}
					String expenseKey = day + hour + CREATIVE_DATA_SUFFIX.EXPENSE;
					if (hourData.containsKey(expenseKey)) {
						expense += Double.parseDouble(hourData.get(expenseKey)) / 100;
					}
				}
				creativeData.setTime(hour);
				creativeData.setImpressionAmount(impression);
				creativeData.setClickAmount(click);
				creativeData.setClickRate(impression == 0 ? 0f : (float)(click/(double)impression));
				creativeData.setJumpAmount(jump);
				creativeData.setTotalCost(expense);
				creativeData.setImpressionCost(impression == 0 ? 0f : (float)(expense/impression*1000));
				creativeData.setClickCost(click == 0 ? 0f : (float)(expense/click));
				creativeData.setJumpCost(jump == 0 ? 0f : (float)(expense/jump));
				creativeDatas.add(creativeData);
			}
		}
		return creativeDatas;
	}
	
	/**
	 * 为活动查询时段数据
	 * @param campaignId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private List<AnalysisBean> getTimeData4Campaign(String campaignId, long startDate, long endDate) {
		List<AnalysisBean> campaignDatas = new ArrayList<AnalysisBean>();
		String[] creativeIds = getCreativeIdsByCampaignId(campaignId);
		Map<String, AnalysisBean> cache = new HashMap<String, AnalysisBean>();
		for (String creativeId : creativeIds) {
			List<AnalysisBean> creativeDatas = getTimeData4Creative(creativeId, startDate, endDate);
			for (AnalysisBean creativeData : creativeDatas) {
				String hour = creativeData.getTime();
				if (cache.containsKey(hour)) {
					AnalysisBean campaignData = cache.get(hour);
					campaignData.setImpressionAmount(campaignData.getImpressionAmount() + creativeData.getImpressionAmount());
					campaignData.setClickAmount(campaignData.getClickAmount() + creativeData.getClickAmount());
					campaignData.setJumpAmount(campaignData.getJumpAmount() + creativeData.getJumpAmount());
					campaignData.setTotalCost(campaignData.getTotalCost() + creativeData.getTotalCost());
				} else {
					AnalysisBean campaignData = new AnalysisBean();
					campaignData.setTime(hour);
					campaignData.setImpressionAmount(creativeData.getImpressionAmount());
					campaignData.setClickAmount(creativeData.getClickAmount());
					campaignData.setJumpAmount(creativeData.getJumpAmount());
					campaignData.setTotalCost(creativeData.getTotalCost());
					cache.put(hour, campaignData);
				}
			}
		}
		for (Entry<String, AnalysisBean> entry : cache.entrySet()) {
			AnalysisBean campaignData = entry.getValue();
			long impression = campaignData.getImpressionAmount();
			long click = campaignData.getClickAmount();
			long jump = campaignData.getJumpAmount();
			double expense = campaignData.getTotalCost();
			campaignData.setClickRate(impression == 0 ? 0f : (float)(click/(double)impression));
			campaignData.setImpressionCost(impression == 0 ? 0f : (float)(expense/impression*1000));
			campaignData.setClickCost(click == 0 ? 0f : (float)(expense/click));
			campaignData.setJumpCost(jump == 0 ? 0f : (float)(expense/jump));
			
			campaignDatas.add(campaignData);
		}
		return campaignDatas;
	}
	
	/**
	 * 为项目查询时段数据
	 * @param projectId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private List<AnalysisBean> getTimeData4Project(String projectId, long startDate, long endDate) {
		List<AnalysisBean> projectDatas = new ArrayList<AnalysisBean>();
		String[] campaignIds = getCampaignIdsByProjectId(projectId);
		Map<String, AnalysisBean> cache = new HashMap<String, AnalysisBean>();
		for (String campaignId : campaignIds) {
			List<AnalysisBean> campaignDatas = getTimeData4Campaign(campaignId, startDate, endDate);
			for (AnalysisBean campaignData : campaignDatas) {
				String hour = campaignData.getTime();
				if (cache.containsKey(hour)) {
					AnalysisBean projectData = cache.get(hour);
					projectData.setImpressionAmount(projectData.getImpressionAmount() + campaignData.getImpressionAmount());
					projectData.setClickAmount(projectData.getClickAmount() + campaignData.getClickAmount());
					projectData.setJumpAmount(projectData.getJumpAmount() + campaignData.getJumpAmount());
					projectData.setTotalCost(projectData.getTotalCost() + campaignData.getTotalCost());
				} else {
					AnalysisBean projectData = new AnalysisBean();
					projectData.setTime(hour);
					projectData.setImpressionAmount(campaignData.getImpressionAmount());
					projectData.setClickAmount(campaignData.getClickAmount());
					projectData.setJumpAmount(campaignData.getJumpAmount());
					projectData.setTotalCost(campaignData.getTotalCost());
					cache.put(hour, projectData);
				}
			}
		}
		for (Entry<String, AnalysisBean> entry : cache.entrySet()) {
			AnalysisBean projectData = entry.getValue();
			long impression = projectData.getImpressionAmount();
			long click = projectData.getClickAmount();
			long jump = projectData.getJumpAmount();
			double expense = projectData.getTotalCost();
			projectData.setClickRate(impression == 0 ? 0f : (float)(click/(double)impression));
			projectData.setImpressionCost(impression == 0 ? 0f : (float)(expense/impression*1000));
			projectData.setClickCost(click == 0 ? 0f : (float)(expense/click));
			projectData.setJumpCost(jump == 0 ? 0f : (float)(expense/jump));
			
			projectDatas.add(projectData);
		}
		
		return projectDatas;
	}
	
	/**
	 * 为客户查询时段数据
	 * @param advertiserId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private List<AnalysisBean> getTimeData4Advertiser(String advertiserId, long startDate, long endDate) {
		List<AnalysisBean> advertiserDatas = new ArrayList<AnalysisBean>();
		String[] projectIds = getCampaignIdsByAdvertiserId(advertiserId);
		Map<String, AnalysisBean> cache = new HashMap<String, AnalysisBean>();
		for (String projectId : projectIds) {
			List<AnalysisBean> projectDatas = getTimeData4Campaign(projectId, startDate, endDate);
			for (AnalysisBean projectData : projectDatas) {
				String hour = projectData.getTime();
				if (cache.containsKey(hour)) {
					AnalysisBean advertiserData = cache.get(hour);
					advertiserData.setImpressionAmount(advertiserData.getImpressionAmount() + projectData.getImpressionAmount());
					advertiserData.setClickAmount(advertiserData.getClickAmount() + projectData.getClickAmount());
					advertiserData.setJumpAmount(advertiserData.getJumpAmount() + projectData.getJumpAmount());
					advertiserData.setTotalCost(advertiserData.getTotalCost() + projectData.getTotalCost());
				} else {
					AnalysisBean advertiserData = new AnalysisBean();
					advertiserData.setTime(hour);
					advertiserData.setImpressionAmount(projectData.getImpressionAmount());
					advertiserData.setClickAmount(projectData.getClickAmount());
					advertiserData.setJumpAmount(projectData.getJumpAmount());
					advertiserData.setTotalCost(projectData.getTotalCost());
					cache.put(hour, advertiserData);
				}
			}
		}
		for (Entry<String, AnalysisBean> entry : cache.entrySet()) {
			AnalysisBean advertiserData = entry.getValue();
			long impression = advertiserData.getImpressionAmount();
			long click = advertiserData.getClickAmount();
			long jump = advertiserData.getJumpAmount();
			double expense = advertiserData.getTotalCost();
			advertiserData.setClickRate(impression == 0 ? 0f : (float)(click/(double)impression));
			advertiserData.setImpressionCost(impression == 0 ? 0f : (float)(expense/impression*1000));
			advertiserData.setClickCost(click == 0 ? 0f : (float)(expense/click));
			advertiserData.setJumpCost(jump == 0 ? 0f : (float)(expense/jump));
			
			advertiserDatas.add(advertiserData);
		}
		
		return advertiserDatas;
	}
	
	/**
	 * 为创意查询地域数据
	 * @param creativeId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private List<AnalysisBean> getRegionData4Creative(String creativeId, long startDate, long endDate) {
		List<AnalysisBean> creativeDatas = new ArrayList<AnalysisBean>();
		String dayKey = RedisKeyConstant.CREATIVE_DATA_DAY + creativeId;
		
		if (redisHelper3.exists(dayKey)) {
			// 查该创意所属的活动
			CreativeModel creativeModel = creativeDao.selectByPrimaryKey(creativeId);
			String campaignId = creativeModel.getCampaignId();
			// 查询该创意定向的地域
			CampaignTargetModelExample campaignTargetExample = new CampaignTargetModelExample();
			campaignTargetExample.createCriteria().andIdEqualTo(campaignId);
			List<CampaignTargetModel> campaignTargetModels = campaignTargetDao.selectByExampleWithBLOBs(campaignTargetExample);
			if (!campaignTargetModels.isEmpty()) {
				String[] days = DateUtils.getDaysBetween(new Date(startDate), new Date(endDate));
				Map<String, String> dayData = redisHelper3.hget(dayKey);
				// 地域数组
				String regionIds = campaignTargetModels.get(0).getRegionId();
				if (regionIds == null || regionIds.isEmpty()) {
					// 查地域表获取所有地域ID
					regionIds = customRegionDao.selectRegionIds();
				}
				for (String regionId : regionIds.split(",")) {
					// 查询地域名称
					RegionModel regionModel = regionDao.selectByPrimaryKey(regionId);
					if (regionModel == null) {
						continue;
					}
					String regionName = regionModel.getName();
					AnalysisBean creativeData = new AnalysisBean();
					long impression = 0L, click = 0L, jump = 0L;
					double expense = 0D;
					for (String day : days) {
						String impressionKey = day + CREATIVE_DATA_TYPE.REGION + regionId + CREATIVE_DATA_SUFFIX.IMPRESSION;
						if (dayData.containsKey(impressionKey)) {
							impression += Long.parseLong(dayData.get(impressionKey));
						}
						String clickKey = day + CREATIVE_DATA_TYPE.REGION + regionId + CREATIVE_DATA_SUFFIX.CLICK;
						if (dayData.containsKey(clickKey)) {
							click += Long.parseLong(dayData.get(clickKey));
						}
						String jumpKey = day + CREATIVE_DATA_TYPE.REGION + regionId + CREATIVE_DATA_SUFFIX.JUMP;
						if (dayData.containsKey(jumpKey)) {
							jump += Long.parseLong(dayData.get(jumpKey));
						}
						String expenseKey = day + CREATIVE_DATA_TYPE.REGION + regionId + CREATIVE_DATA_SUFFIX.EXPENSE;
						if (dayData.containsKey(expenseKey)) {
							expense += Double.parseDouble(dayData.get(expenseKey)) / 100;
						} 
					}
					creativeData.setId(regionId);
					creativeData.setName(regionName);
					creativeData.setImpressionAmount(impression);
					creativeData.setClickAmount(click);
					creativeData.setClickRate(impression == 0 ? 0f : (float)(click/(double)impression));
					creativeData.setJumpAmount(jump);
					creativeData.setTotalCost(expense);
					creativeData.setClickRate(impression == 0 ? 0f : (float)(expense/impression));
					creativeData.setClickRate(click == 0 ? 0f : (float)(expense/click));
					creativeData.setClickRate(jump == 0 ? 0f : (float)(expense/jump));
					creativeDatas.add(creativeData);
				}
			}
			
		}
		return creativeDatas;
	}
	
	/**
	 * 为活动查询地域数据
	 * @param creativeId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private List<AnalysisBean> getRegionData4Campaign(String campaignId, long startDate, long endDate) {
		List<AnalysisBean> campaignDatas = new ArrayList<AnalysisBean>();
		String[] creativeIds = getCreativeIdsByCampaignId(campaignId);
		Map<String, AnalysisBean> cache = new HashMap<String, AnalysisBean>();
		for (String creativeId : creativeIds) {
			List<AnalysisBean> creativeDatas = getRegionData4Creative(creativeId, startDate, endDate);
			for (AnalysisBean creativeData : creativeDatas) {
				String id = creativeData.getId();
				if (cache.containsKey(id)) {
					AnalysisBean campaignData = cache.get(id);
					campaignData.setImpressionAmount(campaignData.getImpressionAmount() + creativeData.getImpressionAmount());
					campaignData.setClickAmount(campaignData.getClickAmount() + creativeData.getClickAmount());
					campaignData.setJumpAmount(campaignData.getJumpAmount() + creativeData.getJumpAmount());
					campaignData.setTotalCost(campaignData.getTotalCost() + creativeData.getTotalCost());
				} else {
					AnalysisBean campaignData = new AnalysisBean();
					campaignData.setId(id);
					campaignData.setName(creativeData.getName());
					campaignData.setImpressionAmount(creativeData.getImpressionAmount());
					campaignData.setClickAmount(creativeData.getClickAmount());
					campaignData.setJumpAmount(creativeData.getJumpAmount());
					campaignData.setTotalCost(creativeData.getTotalCost());
					cache.put(id, campaignData);
				}
			}
		}
		for (Entry<String, AnalysisBean> entry : cache.entrySet()) {
			AnalysisBean campaignData = entry.getValue();
			long impression = campaignData.getImpressionAmount();
			long click = campaignData.getClickAmount();
			long jump = campaignData.getJumpAmount();
			double expense = campaignData.getTotalCost();
			campaignData.setClickRate(impression == 0 ? 0f : (float)(click/(double)impression));
			campaignData.setImpressionCost(impression == 0 ? 0f : (float)(expense/impression*1000));
			campaignData.setClickCost(click == 0 ? 0f : (float)(expense/click));
			campaignData.setJumpCost(jump == 0 ? 0f : (float)(expense/jump));
			
			campaignDatas.add(campaignData);
		}
		return campaignDatas;
	}
	
	/**
	 * 为项目查询地域数据
	 * @param creativeId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private List<AnalysisBean> getRegionData4Project(String projectId, long startDate, long endDate) {
		List<AnalysisBean> projectDatas = new ArrayList<AnalysisBean>();
		String[] campaignIds = getCampaignIdsByProjectId(projectId);
		Map<String, AnalysisBean> cache = new HashMap<String, AnalysisBean>();
		for (String campaignId : campaignIds) {
			List<AnalysisBean> campaignDatas = getRegionData4Campaign(campaignId, startDate, endDate);
			for (AnalysisBean campaignData : campaignDatas) {
				String id = campaignData.getId();
				if (cache.containsKey(id)) {
					AnalysisBean projectData = cache.get(id);
					projectData.setImpressionAmount(projectData.getImpressionAmount() + campaignData.getImpressionAmount());
					projectData.setClickAmount(projectData.getClickAmount() + campaignData.getClickAmount());
					projectData.setJumpAmount(projectData.getJumpAmount() + campaignData.getJumpAmount());
					projectData.setTotalCost(projectData.getTotalCost() + campaignData.getTotalCost());
				} else {
					AnalysisBean projectData = new AnalysisBean();
					projectData.setId(id);
					projectData.setName(campaignData.getName());
					projectData.setImpressionAmount(campaignData.getImpressionAmount());
					projectData.setClickAmount(campaignData.getClickAmount());
					projectData.setJumpAmount(campaignData.getJumpAmount());
					projectData.setTotalCost(campaignData.getTotalCost());
					cache.put(id, projectData);
				}
			}
		}
		for (Entry<String, AnalysisBean> entry : cache.entrySet()) {
			AnalysisBean projectData = entry.getValue();
			long impression = projectData.getImpressionAmount();
			long click = projectData.getClickAmount();
			long jump = projectData.getJumpAmount();
			double expense = projectData.getTotalCost();
			projectData.setClickRate(impression == 0 ? 0f : (float)(click/(double)impression));
			projectData.setImpressionCost(impression == 0 ? 0f : (float)(expense/impression*1000));
			projectData.setClickCost(click == 0 ? 0f : (float)(expense/click));
			projectData.setJumpCost(jump == 0 ? 0f : (float)(expense/jump));
			
			projectDatas.add(projectData);
		}
		return projectDatas;
	}
	
	/**
	 * 为客户查询地域数据
	 * @param creativeId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private List<AnalysisBean> getRegionData4Advertiser(String advertiserId, long startDate, long endDate) {
		List<AnalysisBean> advertiserDatas = new ArrayList<AnalysisBean>();
		String[] projectIds = getProjectIdsByAdvertiserId(advertiserId);
		Map<String, AnalysisBean> cache = new HashMap<String, AnalysisBean>();
		for (String projectId : projectIds) {
			List<AnalysisBean> projectDatas = getRegionData4Project(projectId, startDate, endDate);
			for (AnalysisBean projectData : projectDatas) {
				String id = projectData.getId();
				if (cache.containsKey(id)) {
					AnalysisBean advertiserData = cache.get(id);
					advertiserData.setImpressionAmount(advertiserData.getImpressionAmount() + projectData.getImpressionAmount());
					advertiserData.setClickAmount(advertiserData.getClickAmount() + projectData.getClickAmount());
					advertiserData.setJumpAmount(advertiserData.getJumpAmount() + projectData.getJumpAmount());
					advertiserData.setTotalCost(advertiserData.getTotalCost() + projectData.getTotalCost());
				} else {
					AnalysisBean advertiserData = new AnalysisBean();
					advertiserData.setId(id);
					advertiserData.setName(projectData.getName());
					advertiserData.setImpressionAmount(projectData.getImpressionAmount());
					advertiserData.setClickAmount(projectData.getClickAmount());
					advertiserData.setJumpAmount(projectData.getJumpAmount());
					advertiserData.setTotalCost(projectData.getTotalCost());
					cache.put(id, advertiserData);
				}
			}
		}
		for (Entry<String, AnalysisBean> entry : cache.entrySet()) {
			AnalysisBean advertiserData = entry.getValue();
			long impression = advertiserData.getImpressionAmount();
			long click = advertiserData.getClickAmount();
			long jump = advertiserData.getJumpAmount();
			double expense = advertiserData.getTotalCost();
			advertiserData.setClickRate(impression == 0 ? 0f : (float)(click/(double)impression));
			advertiserData.setImpressionCost(impression == 0 ? 0f : (float)(expense/impression*1000));
			advertiserData.setClickCost(click == 0 ? 0f : (float)(expense/click));
			advertiserData.setJumpCost(jump == 0 ? 0f : (float)(expense/jump));
			
			advertiserDatas.add(advertiserData);
		}
		return advertiserDatas;
	}
	
	private List<AnalysisBean> getOperatorData4Creative(String creativeId, long startDate, long endDate) {
		List<AnalysisBean> creativeDatas = new ArrayList<AnalysisBean>();
		String dayKey = RedisKeyConstant.CREATIVE_DATA_DAY + creativeId;
		
		if (redisHelper3.exists(dayKey)) {
			String[] days = DateUtils.getDaysBetween(new Date(startDate), new Date(endDate));
			Map<String, String> dayData = redisHelper3.hget(dayKey);
			AnalysisBean mobileData = new AnalysisBean();
			AnalysisBean unicomData = new AnalysisBean();
			AnalysisBean telecomData = new AnalysisBean();
			for (String day : days) {
				// 移动
				putDataInBean(mobileData, dayData, day + CREATIVE_DATA_TYPE.OPERATOR + CodeTableConstant.OPERATOR_CODE_MOBILE);
				// 联通
				putDataInBean(unicomData, dayData, day + CREATIVE_DATA_TYPE.OPERATOR + CodeTableConstant.OPERATOR_CODE_UNICOM);
				// 电信
				putDataInBean(telecomData, dayData, day + CREATIVE_DATA_TYPE.OPERATOR + CodeTableConstant.OPERATOR_CODE_TELECOM);
			}
			calDataInBean(mobileData, CodeTableConstant.OPERATOR_CODE_MOBILE, CodeTableConstant.OPERATOR_NAME_MOBILE);
			calDataInBean(unicomData, CodeTableConstant.OPERATOR_CODE_UNICOM, CodeTableConstant.OPERATOR_NAME_UNICOM);
			calDataInBean(telecomData, CodeTableConstant.OPERATOR_CODE_TELECOM, CodeTableConstant.OPERATOR_NAME_TELECOM);
			creativeDatas.add(mobileData);
			creativeDatas.add(unicomData);
			creativeDatas.add(telecomData);
		}
		return creativeDatas;
	}
	
	private List<AnalysisBean> getOperatorData4Campaign(String campaignId, long startDate, long endDate) {
		List<AnalysisBean> campaignDatas = new ArrayList<AnalysisBean>();
		String[] creativeIds = getCreativeIdsByCampaignId(campaignId);
		AnalysisBean mobileData = new AnalysisBean();
		AnalysisBean unicomData = new AnalysisBean();
		AnalysisBean telecomData = new AnalysisBean();
		for (String creativeId : creativeIds) {
			// 遍历所有创意的数据
			List<AnalysisBean> creativeDatas = getOperatorData4Creative(creativeId, startDate, endDate);
			for (AnalysisBean creativeData : creativeDatas) {
				String id = creativeData.getId();
				if (CodeTableConstant.OPERATOR_CODE_MOBILE.equals(id)) {
					putDataInBean(mobileData, creativeData.getImpressionAmount(), creativeData.getClickAmount(), 
							creativeData.getJumpAmount(), creativeData.getTotalCost());
				}
				if (CodeTableConstant.OPERATOR_CODE_UNICOM.equals(id)) {
					putDataInBean(unicomData, creativeData.getImpressionAmount(), creativeData.getClickAmount(), 
							creativeData.getJumpAmount(), creativeData.getTotalCost());
				}
				if (CodeTableConstant.OPERATOR_CODE_TELECOM.equals(id)) {
					putDataInBean(telecomData, creativeData.getImpressionAmount(), creativeData.getClickAmount(), 
							creativeData.getJumpAmount(), creativeData.getTotalCost());
				}
			}
		}
		calDataInBean(mobileData, CodeTableConstant.OPERATOR_CODE_MOBILE, CodeTableConstant.OPERATOR_NAME_MOBILE);
		calDataInBean(unicomData, CodeTableConstant.OPERATOR_CODE_UNICOM, CodeTableConstant.OPERATOR_NAME_UNICOM);
		calDataInBean(telecomData, CodeTableConstant.OPERATOR_CODE_TELECOM, CodeTableConstant.OPERATOR_NAME_TELECOM);
		campaignDatas.add(mobileData);
		campaignDatas.add(unicomData);
		campaignDatas.add(telecomData);
		
		return campaignDatas;
	}
	
	private List<AnalysisBean> getOperatorData4Project(String projectId, long startDate, long endDate) {
		List<AnalysisBean> projectDatas = new ArrayList<AnalysisBean>();
		String[] campaignIds = getCampaignIdsByProjectId(projectId);
		AnalysisBean mobileData = new AnalysisBean();
		AnalysisBean unicomData = new AnalysisBean();
		AnalysisBean telecomData = new AnalysisBean();
		for (String campaignId : campaignIds) {
			// 遍历所有创意的数据
			List<AnalysisBean> campaignDatas = getOperatorData4Campaign(campaignId, startDate, endDate);
			for (AnalysisBean campaignData : campaignDatas) {
				String id = campaignData.getId();
				if (CodeTableConstant.OPERATOR_CODE_MOBILE.equals(id)) {
					putDataInBean(mobileData, campaignData.getImpressionAmount(), campaignData.getClickAmount(), 
							campaignData.getJumpAmount(), campaignData.getTotalCost());
				}
				if (CodeTableConstant.OPERATOR_CODE_UNICOM.equals(id)) {
					putDataInBean(unicomData, campaignData.getImpressionAmount(), campaignData.getClickAmount(), 
							campaignData.getJumpAmount(), campaignData.getTotalCost());
				}
				if (CodeTableConstant.OPERATOR_CODE_TELECOM.equals(id)) {
					putDataInBean(telecomData, campaignData.getImpressionAmount(), campaignData.getClickAmount(), 
							campaignData.getJumpAmount(), campaignData.getTotalCost());
				}
			}
		}
		calDataInBean(mobileData, CodeTableConstant.OPERATOR_CODE_MOBILE, CodeTableConstant.OPERATOR_NAME_MOBILE);
		calDataInBean(unicomData, CodeTableConstant.OPERATOR_CODE_UNICOM, CodeTableConstant.OPERATOR_NAME_UNICOM);
		calDataInBean(telecomData, CodeTableConstant.OPERATOR_CODE_TELECOM, CodeTableConstant.OPERATOR_NAME_TELECOM);
		projectDatas.add(mobileData);
		projectDatas.add(unicomData);
		projectDatas.add(telecomData);
		
		return projectDatas;
	}
	
	private List<AnalysisBean> getOperatorData4Advertiser(String advertiserId, long startDate, long endDate) {
		List<AnalysisBean> advertiserDatas = new ArrayList<AnalysisBean>();
		String[] projectIds = getProjectIdsByAdvertiserId(advertiserId);
		AnalysisBean mobileData = new AnalysisBean();
		AnalysisBean unicomData = new AnalysisBean();
		AnalysisBean telecomData = new AnalysisBean();
		for (String projectId : projectIds) {
			List<AnalysisBean> projectDatas = getOperatorData4Project(projectId, startDate, endDate);
			for (AnalysisBean projectData : projectDatas) {
				String id = projectData.getId();
				if (CodeTableConstant.OPERATOR_CODE_MOBILE.equals(id)) {
					putDataInBean(mobileData, projectData.getImpressionAmount(), projectData.getClickAmount(), 
							projectData.getJumpAmount(), projectData.getTotalCost());
				}
				if (CodeTableConstant.OPERATOR_CODE_UNICOM.equals(id)) {
					putDataInBean(unicomData, projectData.getImpressionAmount(), projectData.getClickAmount(), 
							projectData.getJumpAmount(), projectData.getTotalCost());
				}
				if (CodeTableConstant.OPERATOR_CODE_TELECOM.equals(id)) {
					putDataInBean(telecomData, projectData.getImpressionAmount(), projectData.getClickAmount(), 
							projectData.getJumpAmount(), projectData.getTotalCost());
				}
			}
		}
		calDataInBean(mobileData, CodeTableConstant.OPERATOR_CODE_MOBILE, CodeTableConstant.OPERATOR_NAME_MOBILE);
		calDataInBean(unicomData, CodeTableConstant.OPERATOR_CODE_UNICOM, CodeTableConstant.OPERATOR_NAME_UNICOM);
		calDataInBean(telecomData, CodeTableConstant.OPERATOR_CODE_TELECOM, CodeTableConstant.OPERATOR_NAME_TELECOM);
		advertiserDatas.add(mobileData);
		advertiserDatas.add(unicomData);
		advertiserDatas.add(telecomData);
		
		return advertiserDatas;
	}
	
	private List<AnalysisBean> getNetworkData4Creative(String creativeId, long startDate, long endDate) {
		List<AnalysisBean> creativeDatas = new ArrayList<AnalysisBean>();
		String dayKey = RedisKeyConstant.CREATIVE_DATA_DAY + creativeId;
		
		if (redisHelper3.exists(dayKey)) {
			String[] days = DateUtils.getDaysBetween(new Date(startDate), new Date(endDate));
			Map<String, String> dayData = redisHelper3.hget(dayKey);
			AnalysisBean n2gData = new AnalysisBean();
			AnalysisBean n3gData = new AnalysisBean();
			AnalysisBean n4gData = new AnalysisBean();
			AnalysisBean wifiData = new AnalysisBean();
			for (String day : days) {
				// 2g
				putDataInBean(n2gData, dayData, day + CREATIVE_DATA_TYPE.NETWORK + CodeTableConstant.NETWORK_CODE_2G);
				// 3g
				putDataInBean(n3gData, dayData, day + CREATIVE_DATA_TYPE.NETWORK + CodeTableConstant.NETWORK_CODE_3G);
				// 4g
				putDataInBean(n4gData, dayData, day + CREATIVE_DATA_TYPE.NETWORK + CodeTableConstant.NETWORK_CODE_4G);
				// wifi
				putDataInBean(wifiData, dayData, day + CREATIVE_DATA_TYPE.NETWORK + CodeTableConstant.NETWORK_CODE_WIFI);
			}
			calDataInBean(n2gData, CodeTableConstant.NETWORK_CODE_2G, CodeTableConstant.NETWORK_NAME_2G);
			calDataInBean(n3gData, CodeTableConstant.NETWORK_CODE_3G, CodeTableConstant.NETWORK_NAME_3G);
			calDataInBean(n4gData, CodeTableConstant.NETWORK_CODE_4G, CodeTableConstant.NETWORK_NAME_4G);
			calDataInBean(wifiData, CodeTableConstant.NETWORK_CODE_WIFI, CodeTableConstant.NETWORK_NAME_WIFI);
			creativeDatas.add(n2gData);
			creativeDatas.add(n3gData);
			creativeDatas.add(n4gData);
			creativeDatas.add(wifiData);
		}
		return creativeDatas;
	}
	
	private List<AnalysisBean> getNetworkData4Campaign(String campaignId, long startDate, long endDate) {
		List<AnalysisBean> campaignDatas = new ArrayList<AnalysisBean>();
		String[] creativeIds = getCreativeIdsByCampaignId(campaignId);
		AnalysisBean n2gData = new AnalysisBean();
		AnalysisBean n3gData = new AnalysisBean();
		AnalysisBean n4gData = new AnalysisBean();
		AnalysisBean wifiData = new AnalysisBean();
		for (String creativeId : creativeIds) {
			// 遍历所有创意的数据
			List<AnalysisBean> creativeDatas = getNetworkData4Creative(creativeId, startDate, endDate);
			for (AnalysisBean creativeData : creativeDatas) {
				String id = creativeData.getId();
				if (CodeTableConstant.NETWORK_CODE_2G.equals(id)) {
					putDataInBean(n2gData, creativeData.getImpressionAmount(), creativeData.getClickAmount(), 
							creativeData.getJumpAmount(), creativeData.getTotalCost());
				}
				if (CodeTableConstant.NETWORK_CODE_3G.equals(id)) {
					putDataInBean(n3gData, creativeData.getImpressionAmount(), creativeData.getClickAmount(), 
							creativeData.getJumpAmount(), creativeData.getTotalCost());
				}
				if (CodeTableConstant.NETWORK_CODE_4G.equals(id)) {
					putDataInBean(n4gData, creativeData.getImpressionAmount(), creativeData.getClickAmount(), 
							creativeData.getJumpAmount(), creativeData.getTotalCost());
				}
				if (CodeTableConstant.NETWORK_CODE_WIFI.equals(id)) {
					putDataInBean(wifiData, creativeData.getImpressionAmount(), creativeData.getClickAmount(), 
							creativeData.getJumpAmount(), creativeData.getTotalCost());
				}
			}
		}
		calDataInBean(n2gData, CodeTableConstant.NETWORK_CODE_2G, CodeTableConstant.NETWORK_NAME_2G);
		calDataInBean(n3gData, CodeTableConstant.NETWORK_CODE_3G, CodeTableConstant.NETWORK_NAME_3G);
		calDataInBean(n4gData, CodeTableConstant.NETWORK_CODE_4G, CodeTableConstant.NETWORK_NAME_4G);
		calDataInBean(wifiData, CodeTableConstant.NETWORK_CODE_WIFI, CodeTableConstant.NETWORK_NAME_WIFI);
		campaignDatas.add(n2gData);
		campaignDatas.add(n3gData);
		campaignDatas.add(n4gData);
		campaignDatas.add(wifiData);
		
		return campaignDatas;
	}
	
	private List<AnalysisBean> getNetworkData4Project(String projectId, long startDate, long endDate) {
		List<AnalysisBean> projectDatas = new ArrayList<AnalysisBean>();
		String[] campaignIds = getCampaignIdsByProjectId(projectId);
		AnalysisBean n2gData = new AnalysisBean();
		AnalysisBean n3gData = new AnalysisBean();
		AnalysisBean n4gData = new AnalysisBean();
		AnalysisBean wifiData = new AnalysisBean();
		for (String campaignId : campaignIds) {
			// 遍历所有创意的数据
			List<AnalysisBean> campaignDatas = getNetworkData4Campaign(campaignId, startDate, endDate);
			for (AnalysisBean campaignData : campaignDatas) {
				String id = campaignData.getId();
				if (CodeTableConstant.NETWORK_CODE_2G.equals(id)) {
					putDataInBean(n2gData, campaignData.getImpressionAmount(), campaignData.getClickAmount(), 
							campaignData.getJumpAmount(), campaignData.getTotalCost());
				}
				if (CodeTableConstant.NETWORK_CODE_3G.equals(id)) {
					putDataInBean(n3gData, campaignData.getImpressionAmount(), campaignData.getClickAmount(), 
							campaignData.getJumpAmount(), campaignData.getTotalCost());
				}
				if (CodeTableConstant.NETWORK_CODE_4G.equals(id)) {
					putDataInBean(n4gData, campaignData.getImpressionAmount(), campaignData.getClickAmount(), 
							campaignData.getJumpAmount(), campaignData.getTotalCost());
				}
				if (CodeTableConstant.NETWORK_CODE_WIFI.equals(id)) {
					putDataInBean(wifiData, campaignData.getImpressionAmount(), campaignData.getClickAmount(), 
							campaignData.getJumpAmount(), campaignData.getTotalCost());
				}
			}
		}
		calDataInBean(n2gData, CodeTableConstant.NETWORK_CODE_2G, CodeTableConstant.NETWORK_NAME_2G);
		calDataInBean(n3gData, CodeTableConstant.NETWORK_CODE_3G, CodeTableConstant.NETWORK_NAME_3G);
		calDataInBean(n4gData, CodeTableConstant.NETWORK_CODE_4G, CodeTableConstant.NETWORK_NAME_4G);
		calDataInBean(wifiData, CodeTableConstant.NETWORK_CODE_WIFI, CodeTableConstant.NETWORK_NAME_WIFI);
		projectDatas.add(n2gData);
		projectDatas.add(n3gData);
		projectDatas.add(n4gData);
		projectDatas.add(wifiData);
		
		return projectDatas;
	}
	
	private List<AnalysisBean> getNetworkData4Advertiser(String advertiserId, long startDate, long endDate) {
		List<AnalysisBean> advertiserDatas = new ArrayList<AnalysisBean>();
		String[] projectIds = getProjectIdsByAdvertiserId(advertiserId);
		AnalysisBean n2gData = new AnalysisBean();
		AnalysisBean n3gData = new AnalysisBean();
		AnalysisBean n4gData = new AnalysisBean();
		AnalysisBean wifiData = new AnalysisBean();
		for (String projectId : projectIds) {
			// 遍历所有创意的数据
			List<AnalysisBean> projectDatas = getNetworkData4Project(projectId, startDate, endDate);
			for (AnalysisBean projectData : projectDatas) {
				String id = projectData.getId();
				if (CodeTableConstant.NETWORK_CODE_2G.equals(id)) {
					putDataInBean(n2gData, projectData.getImpressionAmount(), projectData.getClickAmount(), 
							projectData.getJumpAmount(), projectData.getTotalCost());
				}
				if (CodeTableConstant.NETWORK_CODE_3G.equals(id)) {
					putDataInBean(n3gData, projectData.getImpressionAmount(), projectData.getClickAmount(), 
							projectData.getJumpAmount(), projectData.getTotalCost());
				}
				if (CodeTableConstant.NETWORK_CODE_4G.equals(id)) {
					putDataInBean(n4gData, projectData.getImpressionAmount(), projectData.getClickAmount(), 
							projectData.getJumpAmount(), projectData.getTotalCost());
				}
				if (CodeTableConstant.NETWORK_CODE_WIFI.equals(id)) {
					putDataInBean(wifiData, projectData.getImpressionAmount(), projectData.getClickAmount(), 
							projectData.getJumpAmount(), projectData.getTotalCost());
				}
			}
		}
		calDataInBean(n2gData, CodeTableConstant.NETWORK_CODE_2G, CodeTableConstant.NETWORK_NAME_2G);
		calDataInBean(n3gData, CodeTableConstant.NETWORK_CODE_3G, CodeTableConstant.NETWORK_NAME_3G);
		calDataInBean(n4gData, CodeTableConstant.NETWORK_CODE_4G, CodeTableConstant.NETWORK_NAME_4G);
		calDataInBean(wifiData, CodeTableConstant.NETWORK_CODE_WIFI, CodeTableConstant.NETWORK_NAME_WIFI);
		advertiserDatas.add(n2gData);
		advertiserDatas.add(n3gData);
		advertiserDatas.add(n4gData);
		advertiserDatas.add(wifiData);
		
		return advertiserDatas;
	}
	
	private List<AnalysisBean> getSystemData4Creative(String creativeId, long startDate, long endDate) {
		List<AnalysisBean> creativeDatas = new ArrayList<AnalysisBean>();
		String dayKey = RedisKeyConstant.CREATIVE_DATA_DAY + creativeId;
		
		if (redisHelper3.exists(dayKey)) {
			String[] days = DateUtils.getDaysBetween(new Date(startDate), new Date(endDate));
			Map<String, String> dayData = redisHelper3.hget(dayKey);
			AnalysisBean iosData = new AnalysisBean();
			AnalysisBean androidData = new AnalysisBean();
			AnalysisBean windowsData = new AnalysisBean();
			for (String day : days) {
				// ios
				putDataInBean(iosData, dayData, day + CREATIVE_DATA_TYPE.OS + CodeTableConstant.SYSTEM_CODE_IOS);
				// android
				putDataInBean(androidData, dayData, day + CREATIVE_DATA_TYPE.OS + CodeTableConstant.SYSTEM_CODE_ANDROID);
				// windows
				putDataInBean(windowsData, dayData, day + CREATIVE_DATA_TYPE.OS + CodeTableConstant.SYSTEM_CODE_WINDOWS);
			}
			calDataInBean(iosData, CodeTableConstant.SYSTEM_CODE_IOS, CodeTableConstant.SYSTEM_NAME_IOS);
			calDataInBean(androidData, CodeTableConstant.SYSTEM_CODE_ANDROID, CodeTableConstant.SYSTEM_NAME_ANDROID);
			calDataInBean(windowsData, CodeTableConstant.SYSTEM_CODE_WINDOWS, CodeTableConstant.SYSTEM_NAME_WINDOWS);
			creativeDatas.add(iosData);
			creativeDatas.add(androidData);
			creativeDatas.add(windowsData);
		}
		return creativeDatas;
	}
	
	private List<AnalysisBean> getSystemData4Campaign(String campaignId, long startDate, long endDate) {
		List<AnalysisBean> campaignDatas = new ArrayList<AnalysisBean>();
		String[] creativeIds = getCreativeIdsByCampaignId(campaignId);
		AnalysisBean iosData = new AnalysisBean();
		AnalysisBean androidData = new AnalysisBean();
		AnalysisBean windowsData = new AnalysisBean();
		for (String creativeId : creativeIds) {
			// 遍历所有创意的数据
			List<AnalysisBean> creativeDatas = getSystemData4Creative(creativeId, startDate, endDate);
			for (AnalysisBean creativeData : creativeDatas) {
				String id = creativeData.getId();
				if (CodeTableConstant.SYSTEM_CODE_IOS.equals(id)) {
					putDataInBean(iosData, creativeData.getImpressionAmount(), creativeData.getClickAmount(), 
							creativeData.getJumpAmount(), creativeData.getTotalCost());
				}
				if (CodeTableConstant.SYSTEM_CODE_ANDROID.equals(id)) {
					putDataInBean(androidData, creativeData.getImpressionAmount(), creativeData.getClickAmount(), 
							creativeData.getJumpAmount(), creativeData.getTotalCost());
				}
				if (CodeTableConstant.SYSTEM_CODE_WINDOWS.equals(id)) {
					putDataInBean(windowsData, creativeData.getImpressionAmount(), creativeData.getClickAmount(), 
							creativeData.getJumpAmount(), creativeData.getTotalCost());
				}
			}
		}
		calDataInBean(iosData, CodeTableConstant.SYSTEM_CODE_IOS, CodeTableConstant.SYSTEM_NAME_IOS);
		calDataInBean(androidData, CodeTableConstant.SYSTEM_CODE_ANDROID, CodeTableConstant.SYSTEM_NAME_ANDROID);
		calDataInBean(windowsData, CodeTableConstant.SYSTEM_CODE_WINDOWS, CodeTableConstant.SYSTEM_NAME_WINDOWS);
		campaignDatas.add(iosData);
		campaignDatas.add(androidData);
		campaignDatas.add(windowsData);
		
		return campaignDatas;
	}
	
	private List<AnalysisBean> getSystemData4Project(String projectId, long startDate, long endDate) {
		List<AnalysisBean> projectDatas = new ArrayList<AnalysisBean>();
		String[] campaignIds = getCampaignIdsByProjectId(projectId);
		AnalysisBean iosData = new AnalysisBean();
		AnalysisBean androidData = new AnalysisBean();
		AnalysisBean windowsData = new AnalysisBean();
		for (String campaignId : campaignIds) {
			// 遍历所有创意的数据
			List<AnalysisBean> campaignDatas = getSystemData4Campaign(campaignId, startDate, endDate);
			for (AnalysisBean campaignData : campaignDatas) {
				String id = campaignData.getId();
				if (CodeTableConstant.SYSTEM_CODE_IOS.equals(id)) {
					putDataInBean(iosData, campaignData.getImpressionAmount(), campaignData.getClickAmount(), 
							campaignData.getJumpAmount(), campaignData.getTotalCost());
				}
				if (CodeTableConstant.SYSTEM_CODE_ANDROID.equals(id)) {
					putDataInBean(androidData, campaignData.getImpressionAmount(), campaignData.getClickAmount(), 
							campaignData.getJumpAmount(), campaignData.getTotalCost());
				}
				if (CodeTableConstant.SYSTEM_CODE_WINDOWS.equals(id)) {
					putDataInBean(windowsData, campaignData.getImpressionAmount(), campaignData.getClickAmount(), 
							campaignData.getJumpAmount(), campaignData.getTotalCost());
				}
			}
		}
		calDataInBean(iosData, CodeTableConstant.SYSTEM_CODE_IOS, CodeTableConstant.SYSTEM_NAME_IOS);
		calDataInBean(androidData, CodeTableConstant.SYSTEM_CODE_ANDROID, CodeTableConstant.SYSTEM_NAME_ANDROID);
		calDataInBean(windowsData, CodeTableConstant.SYSTEM_CODE_WINDOWS, CodeTableConstant.SYSTEM_NAME_WINDOWS);
		projectDatas.add(iosData);
		projectDatas.add(androidData);
		projectDatas.add(windowsData);
		
		return projectDatas;
	}
	
	private List<AnalysisBean> getSystemData4Advertiser(String advertiserId, long startDate, long endDate) {
		List<AnalysisBean> advertiserDatas = new ArrayList<AnalysisBean>();
		String[] projectIds = getProjectIdsByAdvertiserId(advertiserId);
		AnalysisBean iosData = new AnalysisBean();
		AnalysisBean androidData = new AnalysisBean();
		AnalysisBean windowsData = new AnalysisBean();
		for (String projectId : projectIds) {
			// 遍历所有创意的数据
			List<AnalysisBean> projectDatas = getSystemData4Project(projectId, startDate, endDate);
			for (AnalysisBean projectData : projectDatas) {
				String id = projectData.getId();
				if (CodeTableConstant.SYSTEM_CODE_IOS.equals(id)) {
					putDataInBean(iosData, projectData.getImpressionAmount(), projectData.getClickAmount(), 
							projectData.getJumpAmount(), projectData.getTotalCost());
				}
				if (CodeTableConstant.SYSTEM_CODE_ANDROID.equals(id)) {
					putDataInBean(androidData, projectData.getImpressionAmount(), projectData.getClickAmount(), 
							projectData.getJumpAmount(), projectData.getTotalCost());
				}
				if (CodeTableConstant.SYSTEM_CODE_WINDOWS.equals(id)) {
					putDataInBean(windowsData, projectData.getImpressionAmount(), projectData.getClickAmount(), 
							projectData.getJumpAmount(), projectData.getTotalCost());
				}
			}
		}
		calDataInBean(iosData, CodeTableConstant.SYSTEM_CODE_IOS, CodeTableConstant.SYSTEM_NAME_IOS);
		calDataInBean(androidData, CodeTableConstant.SYSTEM_CODE_ANDROID, CodeTableConstant.SYSTEM_NAME_ANDROID);
		calDataInBean(windowsData, CodeTableConstant.SYSTEM_CODE_WINDOWS, CodeTableConstant.SYSTEM_NAME_WINDOWS);
		advertiserDatas.add(iosData);
		advertiserDatas.add(androidData);
		advertiserDatas.add(windowsData);
		
		return advertiserDatas;
	}
	
	private String[] getProjectIdsByAdvertiserId(String advertiserId) {
		ProjectModelExample example = new ProjectModelExample();
		example.createCriteria().andAdvertiserIdEqualTo(advertiserId);
		List<ProjectModel> models = projectDao.selectByExample(example);
		Set<String> idSet = new HashSet<String>();
		for (ProjectModel model : models) {
			idSet.add(model.getId());
		}
		return idSet.toArray(new String[0]);
	}
	private String[] getCampaignIdsByAdvertiserId(String advertiserId) {
		String[] projectIds = getProjectIdsByAdvertiserId(advertiserId);
		Set<String> idSet = new HashSet<String>();
		for (String porjectId : projectIds) {
			CampaignModelExample example = new CampaignModelExample();
			example.createCriteria().andProjectIdEqualTo(porjectId);
			List<CampaignModel> models = campaignDao.selectByExample(example);
			for (CampaignModel model : models) {
				idSet.add(model.getId());
			}
		}
		return idSet.toArray(new String[0]);
	}
	private String[] getCreativeIdsByAdvertiserId(String advertiserId) {
		String[] campaignIds = getCampaignIdsByAdvertiserId(advertiserId);
		Set<String> idSet = new HashSet<String>();
		for (String campaignId : campaignIds) {
			CreativeModelExample example = new CreativeModelExample();
			example.createCriteria().andCampaignIdEqualTo(campaignId);
			List<CreativeModel> models = creativeDao.selectByExample(example);
			for (CreativeModel model : models) {
				idSet.add(model.getId());
			}
		}
		return idSet.toArray(new String[0]);
	}
	private String[] getCampaignIdsByProjectId(String projectId) {
		CampaignModelExample example = new CampaignModelExample();
		example.createCriteria().andProjectIdEqualTo(projectId);
		List<CampaignModel> models = campaignDao.selectByExample(example);
		Set<String> idSet = new HashSet<String>();
		for (CampaignModel model : models) {
			idSet.add(model.getId());
		}
		return idSet.toArray(new String[0]);
	}
	private String[] getCreativeIdsByPorjectId(String projectId) {
		String[] campaignIds = getCampaignIdsByProjectId(projectId);
		Set<String> idSet = new HashSet<String>();
		for (String campaignId : campaignIds) {
			CreativeModelExample example = new CreativeModelExample();
			example.createCriteria().andCampaignIdEqualTo(campaignId);
			List<CreativeModel> models = creativeDao.selectByExample(example);
			for (CreativeModel model : models) {
				idSet.add(model.getId());
			}
		}
		return idSet.toArray(new String[0]);
	}
	private String[] getCreativeIdsByCampaignId(String campaignId) {
		CreativeModelExample example = new CreativeModelExample();
		example.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> models = creativeDao.selectByExample(example);
		Set<String> idSet = new HashSet<String>();
		for (CreativeModel model : models) {
			idSet.add(model.getId());
		}
		return idSet.toArray(new String[0]);
	}
	
	private String getAdvertiserName(String advertiserId) {
		AdvertiserModel model = advertiserDao.selectByPrimaryKey(advertiserId);
		return model.getName();
	}
	private String getProjectName(String projectId) {
		ProjectModel model = projectDao.selectByPrimaryKey(projectId);
		return model.getName();
	}
	private String getCampaignName(String campaignId) {
		CampaignModel model = campaignDao.selectByPrimaryKey(campaignId);
		return model.getName();
	}
	
	/**
	 * 根据前缀读取redis，并存入相应的bean中
	 * @param bean
	 * @param dataMap
	 * @param prefix
	 */
	private void putDataInBean(AnalysisBean bean, Map<String, String> dataMap, String prefix) {
		String key;
		key = prefix + CREATIVE_DATA_SUFFIX.IMPRESSION;
		if (dataMap.containsKey(key)) {
			bean.setImpressionAmount(bean.getImpressionAmount() + Long.parseLong(dataMap.get(key)));
		}
		key = prefix + CREATIVE_DATA_SUFFIX.CLICK;
		if (dataMap.containsKey(key)) {
			bean.setClickAmount(bean.getClickAmount() + Long.parseLong(dataMap.get(key)));
		}
		key = prefix + CREATIVE_DATA_SUFFIX.JUMP;
		if (dataMap.containsKey(key)) {
			bean.setJumpAmount(bean.getJumpAmount() + Long.parseLong(dataMap.get(key)));
		}
		key = prefix + CREATIVE_DATA_SUFFIX.EXPENSE;
		if (dataMap.containsKey(key)) {
			bean.setTotalCost(bean.getTotalCost() + Double.parseDouble(dataMap.get(key)) / 100);
		}
	}
	
	/**
	 * 计算bean中的数据
	 * @param bean
	 * @param id
	 * @param name
	 */
	private void calDataInBean(AnalysisBean bean, String id, String name) {
		bean.setId(id);
		bean.setName(name);
		long impression = bean.getImpressionAmount();
		long click = bean.getClickAmount();
		long jump = bean.getJumpAmount();
		double expense = bean.getTotalCost();
		bean.setClickRate(impression == 0 ? 0f : (float)(click / (double)impression));
		bean.setImpressionCost(impression == 0 ? 0f : (float)(expense / impression * 1000));
		bean.setClickCost(click == 0 ? 0f : (float)(expense / click));
		bean.setJumpCost(jump == 0 ? 0f : (float)(expense / jump));
	}
	
	private void putDataInBean(AnalysisBean bean, long impression, long click, long jump, double expense) {
		bean.setImpressionAmount(bean.getImpressionAmount() + impression);
		bean.setClickAmount(bean.getClickAmount() + click);
		bean.setJumpAmount(bean.getJumpAmount() + jump);
		bean.setTotalCost(bean.getTotalCost() + expense);
	}
	
	private boolean isEmptyData(BasicDataBean bean) {
		return bean.getImpressionAmount() == 0 && bean.getClickAmount() == 0 
				&& bean.getJumpAmount() == 0 && bean.getTotalCost() == 0;
	}
	
}
