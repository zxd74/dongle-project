class StockJyseType:
    """
    股票交易所类型
    """
    SHENZHEN = "sz"
    SHANGHAI = "sh"
    BEIJING = "bj"
    HUNGKONG = "hk"


class Stock:
    """
    股票信息类
    """

    def __init__(self, src: str, code: str, name: str):
        """
        @param src: 交易所类型
        @param code: 股票代码
        @param name: 股票名称
        """
        self.src = src
        self.code = code
        self.name = name

    def __str__(self):
        return "src=%s,code=%s,name=%s" % (self.src, self.code, self.name)

    def __repr__(self):
        return self.__str__()

    # def sql(self):
    #     return "['%s','%s','%s']" % (self.src,self.code,self.name)


class StockData:
    """
    股票数据类
    """

    def __init__(self, date: str, code, open=0, low=0, high=0, close=0, volume=0, amount=0):
        """
        @param date: 数据日期 str
        @param code: 股票代码 str
        @param open: 开盘价 float
        @param low: 最低价 float
        @param high: 最高价 float
        @param close: 最高价 float
        @param volume: 交易量 股数
        @param amount: 交易额 金额 元，float
        """
        self.code = code
        self.date = date
        self.open = open
        self.low = low
        self.high = high
        self.close = close
        self.volume = volume
        self.amount = amount

    def __str__(self):
        return "[date=%s,code=%s,open=%s,high=%s,low=%s,close=%s,volume=%s,amount=%s]" % (
            self.date, self.code, self.open, self.high, self.low, self.close, self.volume, self.amount)

    def __repr__(self):
        return self.__str__()

    # def sql(self):
    #     return "('%s','%s','%s','%s','%s','%s','%s','%s')," % (self.date,self.code,self.open,self.high,self.low,self.close,self.volume,self.amount)
