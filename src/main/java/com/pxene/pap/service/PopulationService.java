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
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.PopulationDao;

@Service
public class PopulationService extends BaseService {
 
	@Autowired
	private PopulationDao populationDao;
	
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
        if (models == null || models.size() <= 0) {
            throw new ResourceNotFoundException();
        }
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
        if (file != null) {
        	try
            {
                Integer amount = getAmount(file);
                
                if (amount != null && amount > 0)
                {
                    // 从DB中查询出原有的人群定向文件保存路径，删除掉旧的文件。
                    PopulationModel population = populationDao.selectByPrimaryKey(id);
                    FileUtils.deleteLocalFile(new File(population.getPath()));
                    
                    // 上传新的文件到本地服务器
                    String fileName = file.getOriginalFilename();
                    FileUtils.uploadFileToLocal(UPLOAD_DIR, id, file);
                    
                    // 将新的名称和文件路径保存回DB
                    PopulationModel newPopulcation = new PopulationModel();
                    newPopulcation.setId(id);
                    newPopulcation.setName(name);
                    newPopulcation.setAmount(amount);
                    newPopulcation.setFileName(fileName);
                    
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
        } else {
        	PopulationModel population = new PopulationModel();
        	population.setName(name);
        	populationDao.updateByPrimaryKey(population);
        }
		
    }

    /**
     * 批量删除人群定向：定向文件、DB中人群信息。
     * @param ids
     */
	@Transactional
    public void deletePopulations(String[] ids) throws Exception
    {
        // 操作前先查询一次数据库，判断指定的资源是否存在
        PopulationModelExample populationExample = new PopulationModelExample();
        populationExample.createCriteria().andIdIn(Arrays.asList(ids));
        
        List<PopulationModel> populationsInDB = populationDao.selectByExample(populationExample);
        if (populationsInDB == null || (populationsInDB.size() != ids.length))
        {
            throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
        }
        
        for (PopulationModel population : populationsInDB)
        {
            // 删除DB中的人群定向
            int affectedRows = populationDao.deleteByPrimaryKey(population.getId());
            
            if (affectedRows > 0)
            {
                // 删除本地服务器上的文件
                try
                {
                    org.apache.commons.io.FileUtils.forceDelete(new File(population.getPath()));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
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
