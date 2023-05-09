package com.dongle.question.controller;

import com.dongle.question.config.annotation.DongleResponse;
import com.dongle.question.service.ExamService;
import com.dongle.question.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exam")
@DongleResponse
public class ExamController {

    @Autowired
    public ExamService examService;

    @GetMapping("/get-all")
    public ResponseUtils.ResponseData getAll(){
        return ResponseUtils.success(examService.getAllExams());
    }

}
