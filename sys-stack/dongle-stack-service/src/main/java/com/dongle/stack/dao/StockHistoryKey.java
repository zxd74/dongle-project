package com.dongle.stack.dao;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockHistoryKey implements Serializable {
    private String date;
    private String code;
}
