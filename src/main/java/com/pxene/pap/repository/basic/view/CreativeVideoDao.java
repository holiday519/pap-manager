package com.pxene.pap.repository.basic.view;

import com.pxene.pap.domain.models.view.CreativeVideoModel;
import com.pxene.pap.domain.models.view.CreativeVideoModelExample;
import com.pxene.pap.domain.models.view.CreativeVideoModelWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CreativeVideoDao {
    long countByExample(CreativeVideoModelExample example);

    int deleteByExample(CreativeVideoModelExample example);

    int insert(CreativeVideoModelWithBLOBs record);

    int insertSelective(CreativeVideoModelWithBLOBs record);

    List<CreativeVideoModelWithBLOBs> selectByExampleWithBLOBs(CreativeVideoModelExample example);

    List<CreativeVideoModel> selectByExample(CreativeVideoModelExample example);

    int updateByExampleSelective(@Param("record") CreativeVideoModelWithBLOBs record, @Param("example") CreativeVideoModelExample example);

    int updateByExampleWithBLOBs(@Param("record") CreativeVideoModelWithBLOBs record, @Param("example") CreativeVideoModelExample example);

    int updateByExample(@Param("record") CreativeVideoModel record, @Param("example") CreativeVideoModelExample example);
}