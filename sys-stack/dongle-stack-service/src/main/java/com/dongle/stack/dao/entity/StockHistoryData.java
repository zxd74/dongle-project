package com.dongle.stack.dao.entity;

import com.dongle.stack.dao.StockHistoryKey;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "stack_history_data")
@IdClass(StockHistoryKey.class)
@Entity
public class StockHistoryData {
    @Id
    private String date;
    @Id
    private String code;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double preclose;
    private Long volume;
    private Double amount;
    private int adjustflag;
    private Double turn;
    private int tradestatus;
    private Double pctChg;
    private Double peTTM;
    private Double pbMRQ;
    private Double psTTM;
    private Double pcfNcfTTM;
    private int isST;
}
