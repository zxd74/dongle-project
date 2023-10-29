package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.UserAuths;
import com.iwanvi.nvwa.dao.model.UserAuthsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAuthsMapper {
    int countByExample(UserAuthsExample example);

    int deleteByExample(UserAuthsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserAuths record);

    int insertSelective(UserAuths record);

    List<UserAuths> selectByExample(UserAuthsExample example);

    UserAuths selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserAuths record, @Param("example") UserAuthsExample example);

    int updateByExample(@Param("record") UserAuths record, @Param("example") UserAuthsExample example);

    int updateByPrimaryKeySelective(UserAuths record);

    int updateByPrimaryKey(UserAuths record);
}