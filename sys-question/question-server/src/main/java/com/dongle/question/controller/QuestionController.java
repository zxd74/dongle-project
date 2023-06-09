package com.dongle.question.controller;

import com.dongle.question.config.annotation.DongleResponse;
import com.dongle.question.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
@DongleResponse
public class QuestionController {

    @Autowired
    public QuestionService questionService;

}
