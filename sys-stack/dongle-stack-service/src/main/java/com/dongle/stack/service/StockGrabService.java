package com.dongle.stack.service;

import java.util.Date;

public interface StockGrabService {

    /**
     * 抓取新股票数据
     * @param code
     * @return
     */
    boolean grabNewStock(String code,String from);

    /**
     * 抓取当天股票数据
     * @param day
     * @return
     */
    boolean grabByDay(String day);

}
