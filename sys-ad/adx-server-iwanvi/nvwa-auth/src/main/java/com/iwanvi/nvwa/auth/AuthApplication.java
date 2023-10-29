package com.iwanvi.nvwa.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@ComponentScan("com.iwanvi.nvwa")
@MapperScan("com.iwanvi.nvwa.dao")
public class AuthApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(AuthApplication.class, args);
    }
}
