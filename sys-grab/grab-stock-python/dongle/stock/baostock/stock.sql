-- 操作SQL
---------新表初始化旧数据-----------
--      股票交易日数同步
INSERT INTO stock_day_data(date,`code`,`open`,high,low,`close`,volume,amount)
    SELECT date,SUBSTRING(`code`,4),`open`,high,low,`close`,volume,amount FROM stock_history_data; -- 不带条件同步
    -- WHERE date = '2023-09-07';   -- 带条件同步

--      股票信息同步
INSERT INTO stocks(`source`,`code`,`name`,ipoDate)
    SELECT SUBSTRING(`code`,1,2),SUBSTR(`code`,4),code_name,ipoDate FROM stock_info;


--  表结构
----------旧表--------
CREATE TABLE `stock_info` (
  `code` varchar(10) NOT NULL COMMENT '证券代码',
  `code_name` varchar(20) DEFAULT NULL COMMENT '证券名称',
  `ipoDate` char(10) DEFAULT NULL COMMENT '上市日期',
  `outDate` char(10) DEFAULT NULL COMMENT '退市日期',
  `type` int(1) DEFAULT NULL COMMENT '证券类型，其中1：股票，2：指数，3：其它，4：可转债，5：ETF',
  `status` int(1) DEFAULT NULL COMMENT '上市状态，其中1：上市，0：退市',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '旧股票信息表';
CREATE TABLE `stock_history_data` (
  `date` char(20) NOT NULL COMMENT '交易所行情日期',
  `code` char(10) NOT NULL COMMENT '证券代码',
  `open` decimal(10,4) DEFAULT NULL COMMENT '开盘价',
  `high` decimal(10,4) DEFAULT NULL COMMENT '最高价',
  `low` decimal(10,4) DEFAULT NULL COMMENT '最低价',
  `close` decimal(10,4) DEFAULT NULL COMMENT '收盘价',
  `preclose` decimal(10,4) DEFAULT NULL COMMENT '前收盘价见表格下方详细说明',
  `volume` bigint(20) DEFAULT NULL COMMENT ' 成交量（累计 单位：股）',
  `amount` decimal(20,4) DEFAULT NULL COMMENT ' 成交额（单位：人民币元）',
  `adjustflag` int(1) DEFAULT NULL COMMENT ' 复权状态(1：后复权， 2：前复权，3：不复权）',
  `turn` decimal(20,6) DEFAULT NULL COMMENT '  换手率[指定交易日的成交量(股)/指定交易日的股票的流通股总股数(股)]*100%',
  `tradestatus` int(1) DEFAULT NULL COMMENT '交易状态(1：正常交易 0：停牌）',
  `pctChg` decimal(20,6) DEFAULT NULL COMMENT ' 涨跌幅（百分比）日涨跌幅=[(指定交易日的收盘价-指定交易日前收盘价)/指定交易日前收盘价]*100%',
  `peTTM` decimal(20,6) DEFAULT NULL COMMENT ' 滚动市盈率(指定交易日的股票收盘价/指定交易日的每股盈余TTM)=(指定交易日的股票收盘价*截至当日公司总股本)/归属母公司股东净利润TTM',
  `pbMRQ` decimal(20,6) DEFAULT NULL COMMENT ' 市净率(指定交易日的股票收盘价/指定交易日的每股净资产)=总市值/(最近披露的归属母公司股东的权益-其他权益工具)',
  `psTTM` decimal(20,6) DEFAULT NULL COMMENT ' 滚动市销率(指定交易日的股票收盘价/指定交易日的每股销售额)=(指定交易日的股票收盘价*截至当日公司总股本)/营业总收入TTM',
  `pcfNcfTTM` decimal(20,6) DEFAULT NULL COMMENT ' 滚动市现率(指定交易日的股票收盘价/指定交易日的每股现金流TTM)=(指定交易日的股票收盘价*截至当日公司总股本)/现金以及现金等价物净增加额TTM',
  `isST` int(1) DEFAULT NULL COMMENT '是否ST股，1是，0否',
  PRIMARY KEY (`date`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '旧股票交易数据表';

--------------新表 结构----------------
CREATE TABLE `stocks` (
  `code` varchar(10) NOT NULL COMMENT '证券代码',
  `source` varchar(10) NOT NULL COMMENT '交易所简称',
  `code_name` varchar(20) DEFAULT NULL COMMENT '证券名称',
  `ipoDate` char(10) DEFAULT NULL COMMENT '上市日期',
  `outDate` char(10) DEFAULT NULL COMMENT '退市日期',
  `type` int(1) DEFAULT NULL COMMENT '证券类型，其中1：股票，2：指数，3：其它，4：可转债，5：ETF',
  `status` int(1) DEFAULT NULL COMMENT '上市状态，其中1：上市，0：退市',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '股票信息表';
CREATE TABLE `stock_day_data` (
  `date` char(20) NOT NULL COMMENT '交易所行情日期',
  `code` char(10) NOT NULL COMMENT '证券代码',
  `open` decimal(10,4) DEFAULT NULL COMMENT '开盘价',
  `high` decimal(10,4) DEFAULT NULL COMMENT '最高价',
  `low` decimal(10,4) DEFAULT NULL COMMENT '最低价',
  `close` decimal(10,4) DEFAULT NULL COMMENT '收盘价',
  `preclose` decimal(10,4) DEFAULT NULL COMMENT '前一日收盘价',
  `volume` bigint(20) DEFAULT NULL COMMENT ' 成交量（累计 单位：股）',
  `amount` decimal(20,4) DEFAULT NULL COMMENT ' 成交额（单位：人民币元）',
  `pctChg` decimal(20,6) DEFAULT NULL COMMENT '涨跌幅,日涨跌幅=[(指定交易日的收盘价-指定交易日前收盘价)/指定交易日前收盘价]*100%',
  PRIMARY KEY (`date`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '股票交易日数据表';
CREATE TABLE `stock_jys` (
  `id` varchar(10) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易所表';

CREATE TABLE `stock_group` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='股票分组表';
CREATE TABLE `stock_group_info` (
  `group_id` int(11) NOT NULL,
  `code` varchar(255) NOT NULL,
  PRIMARY KEY (`group_id`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='股票分组详情表';