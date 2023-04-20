package com.dongle.question.manage.controller;

import com.dongle.question.core.service.ExamService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exam")
public class ExamController {

    @DubboReference
    public ExamService examService;


}
