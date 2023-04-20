package com.dongle.question.dao.mapper;

import com.dongle.question.dao.entity.User;
import com.dongle.question.dao.example.UserExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectOneByExample(UserExample example);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int batchInsert(@Param("list") List<User> list);

    int batchInsertSelective(@Param("list") List<User> list, @Param("selective") User.Column ... selective);
}