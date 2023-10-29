package com.iwanvi.nvwa.crontab;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableScheduling
@ComponentScan("com.iwanvi.nvwa")
@MapperScan("com.iwanvi.nvwa.dao")
@ImportResource(locations = {"classpath:/spring/applicationContext-redis.xml"})
public class CrontabApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(CrontabApplication.class, args);
    }
}
