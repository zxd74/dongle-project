package com.dongle.question;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dongle.question.dao")
public class QuestionManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuestionManageApplication.class, args);
    }

}
