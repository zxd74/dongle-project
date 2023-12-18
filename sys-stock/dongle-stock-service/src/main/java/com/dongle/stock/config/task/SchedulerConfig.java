package com.dongle.stock.config.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * @author Dongle
 * @desc
 * @since 2023/9/10 010 8:44
 */
@Configuration
@EnableScheduling
public class SchedulerConfig {

    // @Bean(destroyMethod = "shutdown")
    // public Executor taskScheduler(){
    //     return Executors.newScheduledThreadPool(10);
    // }
}
