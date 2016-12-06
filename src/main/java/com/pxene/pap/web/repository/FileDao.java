package com.pxene.pap.web.repository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FileDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    public int saveFile(String path)
    {
        String sql = "INSERT INTO pap_t_vedio (id, path) VALUES(?, ?)";
        String uuid =  UUID.randomUUID().toString();
        return jdbcTemplate.update(sql, uuid, path);
    }
}
