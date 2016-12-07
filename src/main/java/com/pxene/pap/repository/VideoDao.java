package com.pxene.pap.repository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ch.qos.logback.core.db.dialect.DBUtil;

import com.pxene.pap.common.DBUtils;
import com.pxene.pap.domain.beans.VideoBean;
import com.pxene.pap.domain.beans.VideoEntity;


@Repository
public class VideoDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    public int saveFile(VideoEntity entity)
    {
        String sql = "INSERT INTO pap_t_video (id, name, path, typeid, sizeid, volume, timelength, imageid, remark) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int affectedRows = jdbcTemplate.update(sql, UUID.randomUUID().toString(), entity.getName(), entity.getPath(), entity.getTypeid(), entity.getWidth() + entity.getHeight(), entity.getVolume(), entity.getTimelength(), entity.getImageid(), entity.getRemark());
        return affectedRows;
    }
   
    public int updateVideo(VideoBean bean) throws Exception{
    	String uuid = bean.getUuid();
    	String name = bean.getName();
    	String path = bean.getPath();
    	String imageId = bean.getImageId();
    	String type = bean.getType();
    	String size = bean.getSize();
    	Float volume = bean.getVolume();
    	Integer timelength = bean.getTimelength();
    	String sql = "update pap_t_video set ";
    	if(name != null){
    		sql = sql + "name = '" + name + "'";
    	}
    	if(path != null){
    		sql = sql + ", path = '" + path + "'";
    	}
    	if(imageId != null){
    		sql = sql + ", imageid = '" + imageId + "'";
    	}
    	if(type != null){
    		sql = sql + ", typeid = '" + type + "'";
    	}
    	if(size != null){
    		sql = sql + ", sizeid = '" + size + "'";
    	}
    	if(volume != null){
    		sql = sql + ", volume = " + volume + "";
    	}
    	if(timelength != null){
    		sql = sql + ", timelength = " + timelength + "";
    	}
    	sql = sql.replace("set ,", "set");
    	sql = sql + " where id = '" + uuid + "'";
//    	String sql = DBUtils.buildUpdateSQLByObject("", "id='"+ uuid +"'", bean);
    	System.out.println(sql);
    	int num = jdbcTemplate.update(sql);
    	return num;
    }
}
