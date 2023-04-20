package com.dongle.question.core.service;

import com.dongle.question.core.model.QuestionModel;

import java.util.List;

public interface QuestionService {

    /**
     * 获取所有试题信息
     * @return 返回试题集合
     */
    List<QuestionModel> getAllQuestions();

    /**
     * 根据试题信息查询试题
     * @param question 试题信息
     * @return 返回试题集合
     */
    List<QuestionModel>  findQuestions(QuestionModel question);

}
