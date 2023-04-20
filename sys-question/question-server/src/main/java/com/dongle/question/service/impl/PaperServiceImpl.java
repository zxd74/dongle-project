package com.dongle.question.service.impl;

import com.dongle.question.manage.service.PaperService;
import com.dongle.question.manage.model.PaperModel;
import com.dongle.question.manage.service.PaperService;
import com.dongle.question.model.PaperModel;
import com.dongle.question.service.PaperService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {


    @Override
    public List<PaperModel> getAllExams() {
        return null;
    }

    @Override
    public List<PaperModel> findExams(PaperModel model) {
        // TODO
        return null;
    }
}
