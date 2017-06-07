package com.pxene.pap.repository.basic.view;

import com.pxene.pap.domain.models.CreativeBasicModel;
import com.pxene.pap.domain.models.CreativeBasicModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CreativeBasicDao {
    long countByExample(CreativeBasicModelExample example);

    int deleteByExample(CreativeBasicModelExample example);

    int insert(CreativeBasicModel record);

    int insertSelective(CreativeBasicModel record);

    List<CreativeBasicModel> selectByExampleWithBLOBs(CreativeBasicModelExample example);

    List<CreativeBasicModel> selectByExample(CreativeBasicModelExample example);

    int updateByExampleSelective(@Param("record") CreativeBasicModel record, @Param("example") CreativeBasicModelExample example);

    int updateByExampleWithBLOBs(@Param("record") CreativeBasicModel record, @Param("example") CreativeBasicModelExample example);

    int updateByExample(@Param("record") CreativeBasicModel record, @Param("example") CreativeBasicModelExample example);
}