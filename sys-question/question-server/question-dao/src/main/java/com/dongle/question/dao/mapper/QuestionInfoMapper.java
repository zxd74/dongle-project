package com.dongle.question.dao.mapper;

import com.dongle.question.dao.entity.QuestionInfo;
import com.dongle.question.dao.example.QuestionInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuestionInfoMapper {
    long countByExample(QuestionInfoExample example);

    int deleteByExample(QuestionInfoExample example);

    int deleteByPrimaryKey(String qid);

    int insert(QuestionInfo record);

    int insertSelective(QuestionInfo record);

    QuestionInfo selectOneByExample(QuestionInfoExample example);

    QuestionInfo selectOneByExampleWithBLOBs(QuestionInfoExample example);

    List<QuestionInfo> selectByExampleWithBLOBs(QuestionInfoExample example);

    List<QuestionInfo> selectByExample(QuestionInfoExample example);

    QuestionInfo selectByPrimaryKey(String qid);

    int updateByExampleSelective(@Param("record") QuestionInfo record, @Param("example") QuestionInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") QuestionInfo record, @Param("example") QuestionInfoExample example);

    int updateByExample(@Param("record") QuestionInfo record, @Param("example") QuestionInfoExample example);

    int updateByPrimaryKeySelective(QuestionInfo record);

    int updateByPrimaryKeyWithBLOBs(QuestionInfo record);

    int batchInsert(@Param("list") List<QuestionInfo> list);

    int batchInsertSelective(@Param("list") List<QuestionInfo> list, @Param("selective") QuestionInfo.Column ... selective);
}