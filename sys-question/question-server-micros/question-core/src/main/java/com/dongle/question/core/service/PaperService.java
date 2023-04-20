package com.dongle.question.core.service;

import com.dongle.question.core.model.PaperModel;

import java.util.List;

public interface PaperService {

    /**
     * 获取全部试卷
     * @return 试卷集
     */
    List<PaperModel> getAllExams();

    /**
     * 根据试卷信息查询试卷
     * @param model 试卷信息
     * @return 试卷集
     */
    List<PaperModel> findExams(PaperModel model);
}
