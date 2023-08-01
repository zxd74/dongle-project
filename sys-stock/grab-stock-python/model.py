class StockInfo:
    # code, `code_name`, `ipoDate`, `outDate`, `type`, `status`
    def __init__(self,code,name,ipo,out,type,status):
        self.code = code
        self.code_name = name
        self.ipoDate = ipo
        self.outDate = out
        self.type = type
        self.status = status

    def to_list(self):
        return [self.code,self.code_name,self.ipoDate,self.outDate,self.type,self.status]

class StockData:
    # `date`,`code`,`open`,`high`,`low`,`close`,`preclose`,`volume`,`amount`,`adjustflag`,`turn`,`tradestatus`,`pctChg`,`peTTM`,`pbMRQ`,`psTTM`,`pcfNcfTTM`,`isST`
    def __init__(self,date,code,open,high,low,close,preclose,volume,amount,adjustflag,turn,tradestatus,pctChg,peTTM,
                 pbMRQ,psTTM,pcfNcfTTM,isST):
        self.date = date
        self.code = code
        self.open = open
        self.high = high
        self.low = low
        self.close = close
        self.preclose = preclose
        self.volume = volume
        self.amount = amount
        self.adjustflag = adjustflag
        self.turn = turn
        self.tradestatus = tradestatus
        self.pctChg = pctChg
        self.peTTM = peTTM
        self.pbMRQ = pbMRQ
        self.psTTM = psTTM
        self.pcfNcfTTM = pcfNcfTTM
        self.isST = isST
    def to_list(self):
        return [self.date,self.code,self.open,self.high,self.low,self.close,self.preclose,self.volume,self.amount,self.adjustflag,
                self.turn,self.tradestatus,self.pctChg,self.peTTM,self.pbMRQ,self.psTTM,self.pcfNcfTTM,self.isST]