package com.pxene.pap.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pxene.pap.common.FileUtils;
import com.pxene.pap.common.UUIDGenerator;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.domain.beans.PopulationBean;
import com.pxene.pap.domain.models.PopulationModel;
import com.pxene.pap.domain.models.PopulationModelExample;
import com.pxene.pap.domain.models.PopulationTargetModel;
import com.pxene.pap.domain.models.PopulationTargetModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.PopulationDao;
import com.pxene.pap.repository.basic.PopulationTargetDao;

@Service
public class PopulationService extends BaseService {
 
	@Autowired
	private PopulationDao populationDao;
	
	@Autowired
	private PopulationTargetDao populationTargetDao;
	
	private static String UPLOAD_DIR;
	
	
	@Autowired
	public PopulationService(Environment env)
	{
	    UPLOAD_DIR = env.getProperty("pap.population.path");
	}
	
	@Transactional
	public List<PopulationBean> listPopulations(String name) throws Exception {
		PopulationModelExample example = new PopulationModelExample();
		if (!StringUtils.isEmpty(name)) {
			example.createCriteria().andNameLike("%" + name + "%");
		}
		// 按更新时间进行倒序排序
        example.setOrderByClause("update_time DESC");
        List<PopulationModel> models = populationDao.selectByExample(example);
//        if (models == null || models.size() <= 0) {
//            throw new ResourceNotFoundException();
//        }
        List<PopulationBean> beans = new ArrayList<PopulationBean>();
        for (PopulationModel model : models) {
        	PopulationBean bean = modelMapper.map(model, PopulationBean.class);
        	beans.add(bean);
        }
		
		return beans;
	}

	/**
	 * 添加人群定向：上传文件至本地服务器并插入到数据库中。
	 * @param file 人群文件
	 * @param name 人群名称
	 * @return
	 */
	@Transactional
    public String createPopulation(MultipartFile file, String name) throws Exception
    {
		PopulationModelExample example = new PopulationModelExample();
		example.createCriteria().andNameEqualTo(name);
		List<PopulationModel> populationInDBs = populationDao.selectByExample(example);
		if (!populationInDBs.isEmpty()) {
			throw new DuplicateEntityException(PhrasesConstant.NAME_NOT_REPEAT);
		}
		
        String id = UUIDGenerator.getUUID();
        Integer amount;
        try
        {
            amount = getAmount(file);
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException(PhrasesConstant.POPULATION_FILE_ERROR);
        }
        
        String fileName = file.getOriginalFilename();
        FileUtils.uploadFileToLocal(UPLOAD_DIR, id, file);
        
        PopulationModel record = new PopulationModel();
        record.setId(id);
        record.setName(name);
        record.setAmount(amount);
        record.setFileName(fileName);
//        record.setPath(uploadFilePath);
        
        populationDao.insertSelective(record);
        
        return id;
    }
    
    /**
     * 修改人群定向：上传新文件、修改人群名称。
     * @param id    人群定向ID
     * @param file  人群文件
     * @param name  人群名称
     */
	@Transactional
    public void updatePopulation(String id, MultipartFile file, String name) throws Exception
    {
		// 验证id是否存在
		PopulationModel model = populationDao.selectByPrimaryKey(id);
		if (model == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		} else {
			String nameInDB = model.getName();
			if (!nameInDB.equals(name)) {
				PopulationModelExample example = new PopulationModelExample();
				example.createCriteria().andNameEqualTo(name);
				List<PopulationModel> models = populationDao.selectByExample(example);
				if (models != null && !models.isEmpty()) {
					throw new DuplicateEntityException(PhrasesConstant.NAME_NOT_REPEAT);
				}
			}
		}
		
		try
        {
            Integer amount = getAmount(file);
            
            if (amount != null && amount > 0)
            {
                // 从DB中查询出原有的人群定向文件保存路径，删除掉旧的文件。
                PopulationModel population = populationDao.selectByPrimaryKey(id);
                FileUtils.deleteLocalFile(new File(UPLOAD_DIR + population.getId() + ".txt"));
                
                // 上传新的文件到本地服务器
                String fileName = file.getOriginalFilename();
                FileUtils.uploadFileToLocal(UPLOAD_DIR, id, file);
                
                // 将新的名称和文件路径保存回DB
                PopulationModel newPopulcation = new PopulationModel();
                newPopulcation.setId(id);
                newPopulcation.setName(name);
                newPopulcation.setAmount(amount);
                newPopulcation.setFileName(fileName);
//                newPopulcation.setPath(uploadFilePath);

                populationDao.updateByPrimaryKey(newPopulcation);
            }
            else
            {
                throw new IllegalArgumentException(PhrasesConstant.POPULATION_FILE_ERROR);
            }
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException(PhrasesConstant.POPULATION_FILE_ERROR);
        }
		
    }
	
	@Transactional
    public void updatePopulation4Name(String id, String name) throws Exception
    {
		if (StringUtils.isEmpty(name)) {
			throw new IllegalArgumentException(PhrasesConstant.NAME_NOT_NULL);
		}
		// 验证id是否存在
		PopulationModel model = populationDao.selectByPrimaryKey(id);
		if (model == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		
		PopulationModelExample example = new PopulationModelExample();
		example.createCriteria().andNameEqualTo(name);
		List<PopulationModel> models = populationDao.selectByExample(example);
		if (models != null && !models.isEmpty()) {
			throw new DuplicateEntityException(PhrasesConstant.NAME_NOT_REPEAT);
		}
		
		PopulationModel newPopulcation = new PopulationModel();
		newPopulcation.setId(id);
		newPopulcation.setName(name);
      
		populationDao.updateByPrimaryKeySelective(newPopulcation);
    }

    /**
     * 批量删除人群定向：定向文件、DB中人群信息。
     * @param ids
     */
	@Transactional(dontRollbackOn = IOException.class)
    public void deletePopulations(String[] ids) throws Exception
    {
        if(ids.length ==0)
        {
            throw new IllegalArgumentException();
        }
        
        // 操作前先查询一次数据库，判断指定的资源是否存在
        PopulationModelExample populationExample = new PopulationModelExample();
        populationExample.createCriteria().andIdIn(Arrays.asList(ids));
        
        List<PopulationModel> populationsInDB = populationDao.selectByExample(populationExample);
        if (populationsInDB == null || (populationsInDB.size() != ids.length))
        {
            throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
        }
        
		for (PopulationModel population : populationsInDB) {
			String populationId = population.getId();
			// 判断该名单是否已经被使用，被使用则不能删除
			PopulationTargetModelExample populationTargetEx = new PopulationTargetModelExample();
			populationTargetEx.createCriteria().andPopulationIdEqualTo(populationId);
			List<PopulationTargetModel> populationTargets = populationTargetDao.selectByExample(populationTargetEx);
			if (populationTargets == null || populationTargets.isEmpty()) {
				// 删除DB中的人群定向
				int affectedRows = populationDao.deleteByPrimaryKey(populationId);

				if (affectedRows > 0) {
					// 删除本地服务器上的文件
					// String filePath = population.getPath();
					String filePath = UPLOAD_DIR + population.getId() + ".txt";
					if (!StringUtils.isEmpty(filePath)) {
						org.apache.commons.io.FileUtils.forceDelete(new File(filePath));
					}
				}
			} else {
				throw new IllegalStatusException(PhrasesConstant.POPULATIONS_FILE_USED);
			}
		}
    }

	@Transactional
    public PopulationBean getPopulation(String id) throws Exception {
    	PopulationModel model = populationDao.selectByPrimaryKey(id);
    	
    	return modelMapper.map(model, PopulationBean.class);
    }
    
    /**
     * 读取文件中除了中括号（[）以外的行数
     * @param file
     * @return
     * @throws IOException
     */
    private static Integer getAmount(MultipartFile file) throws IOException
    {
        int amount = 0;
        
        List<String> readedLines = IOUtils.readLines(file.getInputStream());
        
        if (readedLines != null && readedLines.size() > 0)
        {
            for (String line : readedLines)
            {
                if (!StringUtils.isEmpty(line) && !line.contains("["))
                {
                    amount++;
                }
            }
        }
        return amount;
    }
}
