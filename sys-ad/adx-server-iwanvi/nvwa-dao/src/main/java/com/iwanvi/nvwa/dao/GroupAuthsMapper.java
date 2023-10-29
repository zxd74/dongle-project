package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.GroupAuths;
import com.iwanvi.nvwa.dao.model.GroupAuthsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GroupAuthsMapper {
    int countByExample(GroupAuthsExample example);

    int deleteByExample(GroupAuthsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GroupAuths record);

    int insertSelective(GroupAuths record);

    List<GroupAuths> selectByExample(GroupAuthsExample example);

    GroupAuths selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GroupAuths record, @Param("example") GroupAuthsExample example);

    int updateByExample(@Param("record") GroupAuths record, @Param("example") GroupAuthsExample example);

    int updateByPrimaryKeySelective(GroupAuths record);

    int updateByPrimaryKey(GroupAuths record);
}