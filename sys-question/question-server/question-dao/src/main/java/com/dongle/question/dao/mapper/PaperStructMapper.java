package com.dongle.question.dao.mapper;

import com.dongle.question.dao.entity.PaperStruct;
import com.dongle.question.dao.example.PaperStructExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PaperStructMapper {
    long countByExample(PaperStructExample example);

    int deleteByExample(PaperStructExample example);

    int deleteByPrimaryKey(String pid);

    int insert(PaperStruct record);

    int insertSelective(PaperStruct record);

    PaperStruct selectOneByExample(PaperStructExample example);

    List<PaperStruct> selectByExample(PaperStructExample example);

    PaperStruct selectByPrimaryKey(String pid);

    int updateByExampleSelective(@Param("record") PaperStruct record, @Param("example") PaperStructExample example);

    int updateByExample(@Param("record") PaperStruct record, @Param("example") PaperStructExample example);

    int updateByPrimaryKeySelective(PaperStruct record);

    int updateByPrimaryKey(PaperStruct record);

    int batchInsert(@Param("list") List<PaperStruct> list);

    int batchInsertSelective(@Param("list") List<PaperStruct> list, @Param("selective") PaperStruct.Column ... selective);
}