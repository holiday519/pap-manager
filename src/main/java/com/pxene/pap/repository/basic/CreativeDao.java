package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.model.basic.CreativeModel;
import com.pxene.pap.domain.model.basic.CreativeModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CreativeDao {
    long countByExample(CreativeModelExample example);

    int deleteByExample(CreativeModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(CreativeModel record);

    int insertSelective(CreativeModel record);

    List<CreativeModel> selectByExample(CreativeModelExample example);

    CreativeModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CreativeModel record, @Param("example") CreativeModelExample example);

    int updateByExample(@Param("record") CreativeModel record, @Param("example") CreativeModelExample example);

    int updateByPrimaryKeySelective(CreativeModel record);

    int updateByPrimaryKey(CreativeModel record);
}