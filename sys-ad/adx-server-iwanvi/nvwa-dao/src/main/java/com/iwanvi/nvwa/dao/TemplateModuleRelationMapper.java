package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.TemplateModuleRelation;
import com.iwanvi.nvwa.dao.model.TemplateModuleRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TemplateModuleRelationMapper {
    int countByExample(TemplateModuleRelationExample example);

    int deleteByExample(TemplateModuleRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TemplateModuleRelation record);

    int insertSelective(TemplateModuleRelation record);

    List<TemplateModuleRelation> selectByExample(TemplateModuleRelationExample example);

    TemplateModuleRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TemplateModuleRelation record, @Param("example") TemplateModuleRelationExample example);

    int updateByExample(@Param("record") TemplateModuleRelation record, @Param("example") TemplateModuleRelationExample example);

    int updateByPrimaryKeySelective(TemplateModuleRelation record);

    int updateByPrimaryKey(TemplateModuleRelation record);
}