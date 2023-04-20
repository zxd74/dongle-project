package com.dongle.question.dao.mapper;

import com.dongle.question.dao.entity.PaperQuestion;
import com.dongle.question.dao.example.PaperQuestionExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface PaperQuestionMapper {
    long countByExample(PaperQuestionExample example);

    int deleteByExample(PaperQuestionExample example);

    int deleteByPrimaryKey(String pid);

    int insert(PaperQuestion record);

    int insertSelective(PaperQuestion record);

    PaperQuestion selectOneByExample(PaperQuestionExample example);

    List<PaperQuestion> selectByExample(PaperQuestionExample example);

    PaperQuestion selectByPrimaryKey(String pid);

    int updateByExampleSelective(@Param("record") PaperQuestion record, @Param("example") PaperQuestionExample example);

    int updateByExample(@Param("record") PaperQuestion record, @Param("example") PaperQuestionExample example);

    int updateByPrimaryKeySelective(PaperQuestion record);

    int updateByPrimaryKey(PaperQuestion record);

    int batchInsert(@Param("list") List<PaperQuestion> list);

    int batchInsertSelective(@Param("list") List<PaperQuestion> list, @Param("selective") PaperQuestion.Column ... selective);
}