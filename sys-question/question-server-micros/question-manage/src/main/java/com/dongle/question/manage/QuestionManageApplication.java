package com.dongle.question.manage;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableDubbo
public class QuestionManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuestionManageApplication.class, args);
    }

}
