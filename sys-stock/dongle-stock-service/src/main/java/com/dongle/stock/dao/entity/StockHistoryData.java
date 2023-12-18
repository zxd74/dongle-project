package com.dongle.stock.dao.entity;

import com.dongle.stock.dao.StockHistoryKey;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "stock_day_data")
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
    private Double pctChg;
}
