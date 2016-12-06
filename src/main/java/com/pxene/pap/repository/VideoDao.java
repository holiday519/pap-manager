package com.pxene.pap.repository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
    
    /*
    public int saveFile(VideoEntity videoEntity)
    {
        final String sql = "INSERT INTO pap_t_video (id, videotmplid, NAME, path, typeid, width, height, volume, timelength, imageid) VALUES (UUID_SHORT(), "111", "huluwa.mp4", "/usr/local/data/huluwa.mp4", "222", 110, 300, 500.0, 4, '', '')";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        
        jdbcTemplate.update(new PreparedStatementCreator()
        {
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException
            {
                PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                return ps;
            }
        }, keyHolder);
        
        return keyHolder.getKey().intValue();
    }
    */
}
