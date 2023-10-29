package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.UserGrand;
import com.iwanvi.nvwa.dao.model.UserGrandExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserGrandMapper {
    int countByExample(UserGrandExample example);

    int deleteByExample(UserGrandExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserGrand record);

    int insertSelective(UserGrand record);

    List<UserGrand> selectByExample(UserGrandExample example);

    UserGrand selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserGrand record, @Param("example") UserGrandExample example);

    int updateByExample(@Param("record") UserGrand record, @Param("example") UserGrandExample example);

    int updateByPrimaryKeySelective(UserGrand record);

    int updateByPrimaryKey(UserGrand record);

    List<UserGrand> select(UserGrandExample example);
}