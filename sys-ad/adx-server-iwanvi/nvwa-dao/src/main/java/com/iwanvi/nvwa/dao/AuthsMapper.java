package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.Auths;
import com.iwanvi.nvwa.dao.model.AuthsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthsMapper {
    int countByExample(AuthsExample example);

    int deleteByExample(AuthsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auths record);

    int insertSelective(Auths record);

    List<Auths> selectByExample(AuthsExample example);

    Auths selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auths record, @Param("example") AuthsExample example);

    int updateByExample(@Param("record") Auths record, @Param("example") AuthsExample example);

    int updateByPrimaryKeySelective(Auths record);

    int updateByPrimaryKey(Auths record);

    List<Auths> selectByUserIdAndPlatform(@Param("uid") Integer userId, @Param("platform") Integer platform);

    List<Auths> selectByGroupIdAndPlatform(@Param("gid") Integer gId, @Param("platform") Integer platform);
}