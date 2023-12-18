package com.dongle.stock.config.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Dongle
 * @desc
 * @since 2023/9/10 010 8:19
 */
@Component("stockDataTask")
public class StockDataTask {

    @Scheduled(cron = "0 0 0 * * *")
    public void calc(){

    }
}
