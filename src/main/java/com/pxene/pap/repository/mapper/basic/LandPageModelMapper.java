package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.LandPageModel;
import com.pxene.pap.domain.model.basic.LandPageModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LandPageModelMapper {
    long countByExample(LandPageModelExample example);

    int deleteByExample(LandPageModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(LandPageModel record);

    int insertSelective(LandPageModel record);

    List<LandPageModel> selectByExample(LandPageModelExample example);

    LandPageModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") LandPageModel record, @Param("example") LandPageModelExample example);

    int updateByExample(@Param("record") LandPageModel record, @Param("example") LandPageModelExample example);

    int updateByPrimaryKeySelective(LandPageModel record);

    int updateByPrimaryKey(LandPageModel record);
}