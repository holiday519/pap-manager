package com.pxene.pap.repository.basic.view;

import com.pxene.pap.domain.models.view.CreativeImageModel;
import com.pxene.pap.domain.models.view.CreativeImageModelExample;
import com.pxene.pap.domain.models.view.CreativeImageModelWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CreativeImageDao {
    long countByExample(CreativeImageModelExample example);

    int deleteByExample(CreativeImageModelExample example);

    int insert(CreativeImageModelWithBLOBs record);

    int insertSelective(CreativeImageModelWithBLOBs record);

    List<CreativeImageModelWithBLOBs> selectByExampleWithBLOBs(CreativeImageModelExample example);

    List<CreativeImageModel> selectByExample(CreativeImageModelExample example);

    int updateByExampleSelective(@Param("record") CreativeImageModelWithBLOBs record, @Param("example") CreativeImageModelExample example);

    int updateByExampleWithBLOBs(@Param("record") CreativeImageModelWithBLOBs record, @Param("example") CreativeImageModelExample example);

    int updateByExample(@Param("record") CreativeImageModel record, @Param("example") CreativeImageModelExample example);
}