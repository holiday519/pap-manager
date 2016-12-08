package com.pxene.pap.repository.mapper.basic;

import com.pxene.pap.domain.model.basic.UserModel;
import com.pxene.pap.domain.model.basic.UserModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface UserModelMapper {
    int countByExample(UserModelExample example);

    int deleteByExample(UserModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserModel record);

    int insertSelective(UserModel record);

    List<UserModel> selectByExample(UserModelExample example);

    UserModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserModel record, @Param("example") UserModelExample example);

    int updateByExample(@Param("record") UserModel record, @Param("example") UserModelExample example);

    int updateByPrimaryKeySelective(UserModel record);

    int updateByPrimaryKey(UserModel record);
}