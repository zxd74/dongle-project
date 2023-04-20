package com.dongle.question.manage.controller;

import com.dongle.question.core.service.QuestionService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @DubboReference
    public QuestionService questionService;

}
