package com.dongle.question.service.impl;

import com.dongle.question.core.service.PaperService;
import com.dongle.question.core.model.PaperModel;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

@DubboService(version = "1.0.0",group = "manage")
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
