package com.iwanvi.nvwa.web.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolConfig {

    /**
     * 机审检查队列线程池
     * @return
     */
    @Bean(value = "checkThreadPool")
    public ExecutorService buildCheckThreadPool(){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("check-queue-thread-%d").build();

        ExecutorService pool = new ThreadPoolExecutor(10, 50, 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(5),namedThreadFactory,new ThreadPoolExecutor.AbortPolicy());
        return pool ;
    }
}
