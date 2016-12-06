package com.pxene.pap.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pxene.pap.domain.beans.ImageEntity;

@Repository
public class ImageDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    public int saveFile(ImageEntity imageEntity)
    {
        // TODO 自动生成的方法存根
        return 0;
    }
}
