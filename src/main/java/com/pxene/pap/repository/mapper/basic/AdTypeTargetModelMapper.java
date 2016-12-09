package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.AdTypeTargetModel;
import com.pxene.pap.domain.model.basic.AdTypeTargetModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdTypeTargetModelMapper {
    int countByExample(AdTypeTargetModelExample example);

    int deleteByExample(AdTypeTargetModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(AdTypeTargetModel record);

    int insertSelective(AdTypeTargetModel record);

    List<AdTypeTargetModel> selectByExample(AdTypeTargetModelExample example);

    AdTypeTargetModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AdTypeTargetModel record, @Param("example") AdTypeTargetModelExample example);

    int updateByExample(@Param("record") AdTypeTargetModel record, @Param("example") AdTypeTargetModelExample example);

    int updateByPrimaryKeySelective(AdTypeTargetModel record);

    int updateByPrimaryKey(AdTypeTargetModel record);
}