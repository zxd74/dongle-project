package com.dongle.question.exam.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paper")
public class PaperController {

    @GetMapping("/{paper-id}")
    public Object getPaper(@PathVariable("paper-id") String paperId){
        System.out.println("access the paper of ID:" + paperId);
        return null;
    }
}
