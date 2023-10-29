package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.DmpTags;
import com.iwanvi.nvwa.dao.model.DmpTagsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DmpTagsMapper {
    int countByExample(DmpTagsExample example);

    int deleteByExample(DmpTagsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DmpTags record);

    int insertSelective(DmpTags record);

    List<DmpTags> selectByExample(DmpTagsExample example);

    DmpTags selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DmpTags record, @Param("example") DmpTagsExample example);

    int updateByExample(@Param("record") DmpTags record, @Param("example") DmpTagsExample example);

    int updateByPrimaryKeySelective(DmpTags record);

    int updateByPrimaryKey(DmpTags record);

    List<Integer> selcetTagIdUsedByTagsId(@Param("tid") Integer tid);
}