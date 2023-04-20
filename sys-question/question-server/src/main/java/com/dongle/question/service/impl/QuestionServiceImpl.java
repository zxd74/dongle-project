package com.dongle.question.service.impl;

import com.dongle.question.manage.service.QuestionService;
import com.dongle.question.manage.model.QuestionModel;
import com.dongle.question.manage.service.QuestionService;
import com.dongle.question.model.QuestionModel;
import com.dongle.question.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
