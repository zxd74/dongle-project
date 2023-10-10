"""
功能一：根据指定股票code到指定交易所抓取股票信息
功能二：根据指定股票code到指定交易所抓取股票交易日数据
"""
import sys

import jyse
import model, utils

DB_OPTION = utils.StockDao()
JYS_MAP: dict = {}
DATA_DIR = "D:\\"


def getStocks(day):
    stocks = DB_OPTION.queryStockTmp(day)
    if stocks and len(stocks) > 0:
        return stocks
    """从库中读取所需抓取的股票集"""
    return DB_OPTION.queryStocks()


def getJyse(src):
    if src in JYS_MAP:
        return JYS_MAP[src]
    if src == model.StockJyseType.SHENZHEN:
        jys = jyse.SzJysApi()
    elif src == model.StockJyseType.SHANGHAI:
        jys = jyse.ShJysApi()
    elif src == model.StockJyseType.BEIJING:
        jys = jyse.BjJysApi()
    elif src == model.StockJyseType.HUNGKONG:
        jys = jyse.HkJysApi()
    else:
        jys = None
    JYS_MAP[src] = jys
    return jys


def grabStockData(stocks: [model.Stock], day: str):
    result = []
    for stock in stocks:
        stockJyse = getJyse(stock.src)
        if stockJyse:
            stockData: model.StockData = stockJyse.grab_stock_data_by_day(stock.code, day)
            if not stockData:
                print("无法抓取%s，%s数据" % (stock.code, day))
                continue
            result.append(stockData)
    return result


def saveStockInfo(stock: model.Stock):
    DB_OPTION.saveStock(stock)


def saveStockData(stockDatas: [model.StockData]):
    DB_OPTION.saveStockDatas(stockDatas)
    print("save stock data to db successful![%d]" % len(stockDatas))


def saveToFile(data, file):
    utils.JsonUtil.obj_2_json_to_file(data, DATA_DIR + file)
    pass


def saveStockTmp(codes,day):
    DB_OPTION.saveStockTmp(codes, day)


def grabStockDatas(day):
    print("start grab stocks data [%s]" % day)
    # 1. 从库中查询股票集
    stocks: [model.Stock] = getStocks(day)
    if not stocks:
        print("未获取到股票集！")
        exit(-1)
    print("ready grab %d stocks data" % len(stocks))
    # 2. 从交易所抓取股票数据
    stockDatas: [model.StockData] = grabStockData(stocks, day)
    # 3. 保存股票数据入库
    if not stockDatas or len(stockDatas) == 0:
        print("未抓取到股票数据")
        exit(-2)
    print(
        "应抓数据：%d条,实抓数据:%d条，抓取结果：%s" % (len(stocks), len(stockDatas), "完成" if len(stocks) == len(stockDatas) else "不完全"))
    try:
        saveStockData(stockDatas)
        if len(stockDatas) < len(stocks):
            codes = [stock.code for stock in stocks]
            for code in [data.code for data in stockDatas]:
                if code in codes:
                    codes.remove(code)
            saveStockTmp(codes,day)

    except Exception as ex:
        print("股票数据入库异常！", ex)
        # 4. 将股票数据备份入文件
        saveToFile(stockDatas, "data_" + day + ".json")
    print(day, "股票数据抓取任务结束！")
    print(stockDatas)
    DB_OPTION.close()


def grabNewStockDatas(stock, days=30):
    start = utils.DateUtil.format_date()
    end = utils.DateUtil.format_date(utils.DateUtil.date(dif=-days))
    jys = getJyse(stock.src)
    stockDatas = jys.grab_stock_data_by_days(stock.code, start, end)
    saveStockData(stockDatas)


def handlerStockData(data):
    return model.StockData(data['code'], data['date'], data['open'], data['high'], data['low'], data['close'])


if __name__ == "__main__":
    params = utils.EnvUtil.params_sys(sys.argv)
    day = params['day'] if 'day' in params else utils.DateUtil.format_date()
    grabStockDatas(day)
    # TODO 考虑根据交易所做多线程采集处理
    # TODO 为什么上海还能查到周日数据
