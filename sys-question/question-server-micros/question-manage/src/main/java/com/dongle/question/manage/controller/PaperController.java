package com.dongle.question.manage.controller;

import com.dongle.question.core.service.PaperService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paper")
public class PaperController {

    @DubboReference
    private PaperService paperService;

}
