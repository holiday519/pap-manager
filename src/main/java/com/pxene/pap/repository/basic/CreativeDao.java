package com.pxene.pap.repository.basic;

import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.CreativeModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CreativeDao {
    long countByExample(CreativeModelExample example);

    int deleteByExample(CreativeModelExample example);

    int insert(CreativeModel record);

    int insertSelective(CreativeModel record);

    List<CreativeModel> selectByExample(CreativeModelExample example);

    int updateByExampleSelective(@Param("record") CreativeModel record, @Param("example") CreativeModelExample example);

    int updateByExample(@Param("record") CreativeModel record, @Param("example") CreativeModelExample example);
}