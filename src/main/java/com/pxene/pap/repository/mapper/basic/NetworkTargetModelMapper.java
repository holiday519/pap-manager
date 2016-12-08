package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.NetworkTargetModel;
import com.pxene.pap.domain.model.basic.NetworkTargetModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NetworkTargetModelMapper {
    long countByExample(NetworkTargetModelExample example);

    int deleteByExample(NetworkTargetModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(NetworkTargetModel record);

    int insertSelective(NetworkTargetModel record);

    List<NetworkTargetModel> selectByExample(NetworkTargetModelExample example);

    NetworkTargetModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") NetworkTargetModel record, @Param("example") NetworkTargetModelExample example);

    int updateByExample(@Param("record") NetworkTargetModel record, @Param("example") NetworkTargetModelExample example);

    int updateByPrimaryKeySelective(NetworkTargetModel record);

    int updateByPrimaryKey(NetworkTargetModel record);
}