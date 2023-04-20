package com.dongle.question.dao.mapper;

import com.dongle.question.dao.entity.Exam;
import com.dongle.question.dao.example.ExamExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExamMapper {
    long countByExample(ExamExample example);

    int deleteByExample(ExamExample example);

    int deleteByPrimaryKey(String id);

    int insert(Exam record);

    int insertSelective(Exam record);

    Exam selectOneByExample(ExamExample example);

    List<Exam> selectByExample(ExamExample example);

    Exam selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Exam record, @Param("example") ExamExample example);

    int updateByExample(@Param("record") Exam record, @Param("example") ExamExample example);

    int updateByPrimaryKeySelective(Exam record);

    int updateByPrimaryKey(Exam record);

    int batchInsert(@Param("list") List<Exam> list);

    int batchInsertSelective(@Param("list") List<Exam> list, @Param("selective") Exam.Column ... selective);
}