package com.dongle.question.service.impl;

import com.dongle.question.core.service.ExamService;
import com.dongle.question.core.model.ExamModel;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

@DubboService(version = "1.0.0",group = "manage")
public class ExamServiceImpl implements ExamService {

    @Override
    public List<ExamModel> getAllExams() {
        return null;
    }

    @Override
    public List<ExamModel> findExams(ExamModel model) {
        // TODO
        return null;
    }
}
