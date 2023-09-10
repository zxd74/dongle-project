package com.dongle.stock.dao;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Dongle
 * @desc
 * @since 2023/9/1 13:44
 */
@Data
public class StockGroupKey implements Serializable {
    private int groupId;
    private String code;
}
