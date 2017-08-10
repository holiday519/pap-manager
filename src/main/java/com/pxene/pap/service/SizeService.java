package com.pxene.pap.service;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pxene.pap.repository.custom.CustomCreativeDao;

@Service
public class SizeService extends BaseService {
	
    @Autowired
    private CustomCreativeDao customCreativeDao;
	
    /**
     * 列出所有图片大小，去重
     *
     * @return
     */
    public List<Map<String, String>> listSizes() {
        List<Map<String, String>> result = customCreativeDao.selectImageSizes();
        return result;
    }
	
}
