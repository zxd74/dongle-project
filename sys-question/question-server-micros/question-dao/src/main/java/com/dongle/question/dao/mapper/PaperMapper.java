package com.dongle.question.dao.mapper;

import com.dongle.question.dao.entity.Paper;
import com.dongle.question.dao.example.PaperExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface PaperMapper {
    long countByExample(PaperExample example);

    int deleteByExample(PaperExample example);

    int deleteByPrimaryKey(String id);

    int insert(Paper record);

    int insertSelective(Paper record);

    Paper selectOneByExample(PaperExample example);

    List<Paper> selectByExample(PaperExample example);

    Paper selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Paper record, @Param("example") PaperExample example);

    int updateByExample(@Param("record") Paper record, @Param("example") PaperExample example);

    int updateByPrimaryKeySelective(Paper record);

    int updateByPrimaryKey(Paper record);

    int batchInsert(@Param("list") List<Paper> list);

    int batchInsertSelective(@Param("list") List<Paper> list, @Param("selective") Paper.Column ... selective);
}