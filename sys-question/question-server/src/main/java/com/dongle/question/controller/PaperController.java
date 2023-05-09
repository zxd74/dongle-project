package com.dongle.question.controller;

import com.dongle.question.config.annotation.DongleResponse;
import com.dongle.question.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paper")
@DongleResponse
public class PaperController {

    @Autowired
    private PaperService paperService;

}
