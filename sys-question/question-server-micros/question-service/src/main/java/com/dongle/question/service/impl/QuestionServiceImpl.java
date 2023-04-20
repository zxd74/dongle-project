package com.dongle.question.service.impl;

import com.dongle.question.core.service.QuestionService;
import com.dongle.question.core.model.QuestionModel;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

@DubboService(version = "1.0.0",group = "manage")
public class QuestionServiceImpl implements QuestionService {


    @Override
    public List<QuestionModel> getAllQuestions() {
        return null;
    }

    @Override
    public List<QuestionModel> findQuestions(QuestionModel question) {
        // TODO
        return null;
    }
}
