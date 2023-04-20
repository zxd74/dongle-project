package com.dongle.question.service;

import com.dongle.question.model.ExamModel;

import java.util.List;

public interface ExamService {
    /**
     * 获取全部考试计划
     * @return 返回考试计划集
     */
    List<ExamModel> getAllExams();

    /**
     * 根据考试信息查询考试计划
     * @param model 考试信息
     * @return 返回考试计划集
     */
    List<ExamModel> findExams(ExamModel model);

}
