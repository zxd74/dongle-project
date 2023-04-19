package com.dongle.question.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.dongle.question.dao"})
public class QuestionManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuestionManageApplication.class, args);
    }

}
