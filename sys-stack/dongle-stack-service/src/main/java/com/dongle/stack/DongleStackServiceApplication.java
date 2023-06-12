package com.dongle.stack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.dongle.stack.dao"})
public class DongleStackServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DongleStackServiceApplication.class, args);
    }

}
