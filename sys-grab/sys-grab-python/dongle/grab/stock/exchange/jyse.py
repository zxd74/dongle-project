import datetime
import json, requests
import time, random
import model, utils

UserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36"


class JysApi:
    def __init__(self, name):
        self.name = name

    def grab_stock_data_by_day(self, code, day):
        """
        查询一天股票数据
        @param code:
        @param day:
        @return:
        """
        pass

    def grab_stock_data_by_days(self, code, start, end):
        """
        查询一段时间股票数据
        @param code:
        @param start:
        @param end:
        @return:
        """
        sdt = utils.DateUtil.date_format(start, utils.DateUtil.DATE_FORMAT)
        edt = utils.DateUtil.date_format(end, utils.DateUtil.DATE_FORMAT)
        # TODO 分析子类是否能直接范围查询接口
        if utils.DateUtil.compareGreater(sdt, edt):
            return None
        stockDatas = []
        while utils.DateUtil.compareGreater(sdt, edt):
            sdt = utils.DateUtil.date(sdt, 1)
            tdt = utils.DateUtil.format_date(sdt)
            stockData = self.grab_stock_data_by_day(code, tdt)
            if stockData:
                stockDatas.append(stockData)
        return stockDatas


class SzJysApi(JysApi):
    """
    深圳交易所API查询
    """
    # https://www.szse.cn/api/report/ShowReport/data?SHOWTYPE=JSON&CATALOGID=1815_stock_snapshot&TABKEY=tab1&txtDMorJC=002415&txtBeginDate=2023-07-31&txtEndDate=2023-07-31&archiveDate=2021-07-01&random=0.27880532547100145"
    __stock_data_request_uri = "https://www.szse.cn/api/report/ShowReport/data?SHOWTYPE=JSON&CATALOGID=1815_stock_snapshot&TABKEY=tab1&txtDMorJC=%s&txtBeginDate=%s&txtEndDate=%s&archiveDate=&random=0.27880532547100145"
    # http://www.szse.cn/api/market/ssjjhq/getHistoryData?random=0.8563216570328807&cycleType=32&marketId=1&code=002415
    __stock_history_request_uri = "http://www.szse.cn/api/market/ssjjhq/getHistoryData?random=%f&cycleType=32&marketId=1&code=%s"
    """
    {"jyrq": "交易日期", "zqdm": "证券代码", "zqjc": "证券简称", "qss": "前收", "ks": "开盘", "zg": "最高", "zd": "最低", "ss": "今收",
            "sdf": "涨跌幅<br>（%）", "cjgs": "成交量<br>(万股)", "cjje": "成交金额<br>(万元)", "syl1": "市盈率"}
    """

    def __init__(self):
        super().__init__(model.StockJyseType.SHENZHEN)

    def grab_stock_data_by_day(self, code, day):
        # 基本时试试数据，如果需要整天数据，不要在交易时间内查询
        url = self.__stock_data_request_uri % (code, day, day)
        content = requests.get(url)
        data = content.json()
        data = data[0]['data']
        if len(data) == 0:
            print("深圳交易所未查询到股票%s %s交易数据" % (code, day))
            return None
        d = data[0]
        print(d)
        stockData = model.StockData(day, code, d['ks'], d['zg'], d['zd'], d['ss'])
        return stockData

    def grab_stock_data_by_days(self, code, start, end):
        """
        {"code": "0", "data": {"code": "002415", "name": "海康威视", "picupdata": [["2022-11-15", "30.55", "31.78", "30.22", "31.80", "1.19", "3.89", 899182, 2803483713.0]],"picdowndata": [["2022-11-15", 899182, "plus"], ["2022-11-16", 497212, "minus"]]}}
        """
        url = self.__stock_history_request_uri % (random.random(), code)
        content = requests.get(url)
        data = content.json()
        data = data['data']
        if not data:
            return None
        data = data['picupdata']
        if not data or len(data) == 0:
            return None
        stockDatas = []
        start = utils.DateUtil.date_format(start, utils.DateUtil.DATE_FORMAT)
        end = utils.DateUtil.date_format(end, utils.DateUtil.DATE_FORMAT)
        for d in data:
            dt = utils.DateUtil.date_format(d[0])
            if utils.DateUtil.compareLess(dt, start) == 1 or \
                    utils.DateUtil.compareGreater(dt, end) == 1:
                # 小于日期或超出结束日期时无效
                continue
            # stockDatas.append(model.StockData(d[0], code, d[1], d[2], d[3], d[4],d[7],d[8]))
            stockDatas.append(model.StockData(d[0], code, d[1], d[2], d[3], d[4]))
        return stockDatas


class ShJysApi(JysApi):
    """
    上海交易所API查询
    """
    # http://query.sse.com.cn/commonQuery.do?jsonCallBack=jsonpCallback11398773&sqlId=COMMON_SSE_CP_GPJCTPZ_GPLB_CJGK_MRGK_C&SEC_CODE=603069&TX_DATE=2023-07-26&TX_DATE_MON=&TX_DATE_YEAR=&_=1690808485545
    # http://query.sse.com.cn/commonQuery.do?jsonCallBack=jsonpCallback%s&sqlId=COMMON_SSE_CP_GPJCTPZ_GPLB_CJGK_MRGK_C&SEC_CODE=%s&TX_DATE=%s&TX_DATE_MON=&TX_DATE_YEAR=&_=%d
    __stock_request_day_uri = "http://query.sse.com.cn/commonQuery.do?"
    __headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36",
        "Cookie": "ba17301551dcbaf9_gdp_user_key=; gdp_user_id=gioenc-342gae68%2C078g%2C5d97%2Cacg0%2Cgg9812bb3526; ba17301551dcbaf9_gdp_session_id_b4388a90-37b4-4870-a840-ba2fd116da65=true; ba17301551dcbaf9_gdp_session_id_e752cd56-677c-4c58-8907-e0f12c2f08d8=true; ba17301551dcbaf9_gdp_session_id_a8288453-f138-4dae-9543-37f6a7401ac8=true; VISITED_MENU=%5B%229061%22%2C%229060%22%5D; VISITED_COMPANY_CODE=%5B%22600000%22%2C%22688111%22%2C%22603069%22%5D; VISITED_STOCK_CODE=%5B%22600000%22%2C%22688111%22%2C%22603069%22%5D; JSESSIONID=AB2D0D283994F48072E17109768C6BA3; ba17301551dcbaf9_gdp_session_id=757befbd-70c1-4978-88c7-6952898e7084; ba17301551dcbaf9_gdp_session_id_757befbd-70c1-4978-88c7-6952898e7084=true; ba17301551dcbaf9_gdp_sequence_ids={%22globalKey%22:252%2C%22VISIT%22:5%2C%22PAGE%22:36%2C%22VIEW_CLICK%22:185%2C%22VIEW_CHANGE%22:15%2C%22CUSTOM%22:15}",
        "Host": "query.sse.com.cn",
        "Referer": "http://www.sse.com.cn/",  # TODO 此条件必需
    }

    def __init__(self):
        super().__init__(model.StockJyseType.SHANGHAI)

    '''
    "CLOSE_PRICE":"18.61",
    "LOW_VOL_DATE":"20230727",
    "LOW_AMT":"8163.39",
    "SEC_CODE":"603069",
    "HIGH_PRICE":"18.91",
    "HIGH_AMT":"8163.39",
    "HIGH_PRICE_DATE":"20230727",
    "HIGH_VOL_DATE":"20230727",
    "HIGH_VOL":"435.73",
    "TOTAL_VALUE":"588076.0",
    "SWING_RATE":"2.4377",
    "LOW_AMT_DATE":"20230727",
    "TX_DATE":"20230727",
    "SEC_NAME":"海汽集团",
    "LOW_PRICE_DATE":"20230727",
    "TO_RATE":"1.38",
    "CHANGE_PRICE":"0.11",
    "ACCU_TRADE_DAYS":"0",
    "TRADE_AMT":"8163.39",
    "LOW_PRICE":"18.46",
    "DATE_TYPE":"D",
    "TRADE_VOL":"435.73",
    "LOW_VOL":"435.73",
    "CHANGE_RATE":"0.59459",
    "NEGO_VALUE":"588076.0",
    "HIGH_AMT_DATE":"20230727",
    "OPEN_PRICE":"18.5",
    "PE_RATE":"149.87072"
    '''

    def grab_stock_data_by_day(self, code, day):
        t = int(time.time() * 1000)
        r = int(random.random() * (100000000 + 1))

        jsonCallBack = "jsonpCallback" + str(r)
        params = {
            "jsonCallBack": jsonCallBack,
            "sqlId": "COMMON_SSE_CP_GPJCTPZ_GPLB_CJGK_MRGK_C",
            "SEC_CODE": code,
            "TX_DATE_MON": day,
            "TX_DATE_YEAR": "",
            "_": t
        }
        content = requests.post(self.__stock_request_day_uri, data=params, headers=self.__headers).content.decode(
            "utf-8")
        content = utils.JsonUtil.convertJsonFromJsonCallBack(content, jsonCallBack)
        data = json.loads(content)
        if 'result' not in data:
            # {"jsonCallBack":"jsonpCallback14576570","success":"false","error":"System Error, Please try again later...","errorType":"ExceptionInterceptor"}
            print("上海交易所查询到股票%s %s交易数据失败：%s" % (code, day, content))
            return None
        data = data['result']
        if len(data) == 0:
            print("上海交易所未查询到股票%s %s交易数据" % (code, day))
            return None
        data: {} = data[0]
        stockData = model.StockData(day,code, data['OPEN_PRICE'], data['HIGH_PRICE'], data['LOW_PRICE'],
                                    data['CLOSE_PRICE'])
        return stockData


class BjJysApi(JysApi):
    """
    北京交易所API
    """
    # 最新信息 https://www.bse.cn/nqhqController/nqhq.do?callback=jQuery331_1690817441090&page=0&zqdm=833751&xxfcbj=2&_=1690817441091
    # 日K线，https://www.bse.cn/companyEchartsController/getKLine/list/833751.do?%7B%22begin%22%3A0%2C%22end%22%3A-1%2C%22type%22%3A%22dayKline%22%2C%22xxfcbj%22%3A2%7D&callback=jQuery331_1690818072978&begin=0&end=-1&type=dayKline&xxfcbj=2&_=1690818072980
    __stock_new_request_uri = "https://www.bse.cn/nqhqController/nqhq.do?callback=%s&page=0&zqdm=%s&xxfcbj=2&_=%s"
    __stock_day_request_uri = "https://www.bse.cn/companyEchartsController/getKLine/list/%s.do?callback=%s&begin=%d&end=%d&type=dayKline&xxfcbj=2&_=%d"
    __stock_json_call_back = "jQuery331_%d"
    __headers = {
        # "Host":"www.bse.cn",
        # "Referer":"https://www.bse.cn/products/neeq_listed_companies/company_time_sharing.html?companyCode=%s&typename=G" % code,
        # "Cookie":"HMF_CI=5c15cfbf2c84f6421545690ddcff8fa920310faf2fe71df7c63bf43708086934216e64a9fd0a8b1d5711672cd4ab69bc72d462e4246aa6734b15c85581de0a5ff9; Hm_lvt_ef6193a308904a92936b38108b93bd7f=1690790903,1690861389; Hm_lpvt_ef6193a308904a92936b38108b93bd7f=%s" % int(time.time()),
        # "Content-Type":"Application/json;charset=utf-8", # TODO 必需
        # "X-Requested-With":"XMLHttpRequest",
        "User-Agent": UserAgent,
    }

    def __init__(self):
        super().__init__(model.StockJyseType.BEIJING)

    def grab_stock_data_by_new(self, code):
        return self.grab_stock_data_by_day(code, utils.DateUtil.date())

    def grab_stock_data_by_day(self, code, day):
        stocks = self.grab_stock_data_by_days(code, day, day)
        return stocks[0]

    def grab_stock_data_by_days(self, code, start, end):
        t = int(time.time() * 1000)
        jsonCallBack = self.__stock_json_call_back % t
        # TODO 带分析begin和end有效参数
        url = self.__stock_day_request_uri % (code, jsonCallBack, 0, -1, (t + 1))
        print(url)
        content = requests.get(url, headers=self.__headers).content.decode("UTF-8")
        content = utils.JsonUtil.convertJsonFromJsonCallBack(content, jsonCallBack)
        """
            content = '[{"cjje":2492459.030,"cjl":222711,"jrsp":11.150,"jrkp":11.420,"jsrq":"20230906","drzd":11.420,"drzx":11.100,"zrsp":11.360}],"status":0,"msg":""}]'
            cjje: 成交额
            cjl: 成交量
            jrsp: 收盘价
            jrkp: 开盘价
            jsrq:交易日期
            drzd: 最高价
            drzx: 最低价
            zrsp: 均价？未确定
        """
        data = json.loads(content)
        data = data['data']
        stockDatas = []
        start = utils.DateUtil.date_format(start, utils.DateUtil.DATE_FORMAT)
        end = utils.DateUtil.date_format(end, utils.DateUtil.DATE_FORMAT)
        for d in data:
            dt = d['jsrq']
            if utils.DateUtil.compareLess(utils.DateUtil.date_format(dt, utils.DateUtil.SHORT_DATA_FORMAT),
                                          start) == 1 or \
                    utils.DateUtil.compareGreater(utils.DateUtil.date_format(dt, utils.DateUtil.SHORT_DATA_FORMAT),
                                                  end) == 1:
                # 小于日期或超出结束日期时无效
                continue
            stockDatas.append(
                model.StockData(dt, code, str(d['jrkp']), str(d['drzd']), str(d['drzx']), str(d['jrsp'])))
        return stockDatas


class HkJysApi(JysApi):
    """
    香港交易所：Token必需
    # TODO 香港交易所如何查询当天数据
    """
    stock_request_uri = "https://www1.hkex.com.hk/hkexwidget/data/getderivativesfutures?lang=chn&token=evLtsLsBNAUVTPxtGqVeG73F%2bd6E7hIUdgfBG8sX%2ftEE5bq41sSkWWLCcgGzv6ak"
    params_url = "&ats=%s&type=0&qid=%s&callback=%s&_=%s"
    # https://www1.hkex.com.hk/hkexwidget/data/getchartdata2?hchart=1&span=6&int=2&ric=9988.HK&token=evLtsLsBNAUVTPxtGqVeG%2bCZDfd6q7Z5ljeC8EZ%2bAJZnb8xf1uAKElHhTxnwf31l&qid=1694057547045&callback=jQuery3510021416680100266205_1694057523853&_=1694057523867
    stock_hisotry_request_uri = "https://www1.hkex.com.hk/hkexwidget/data/getchartdata2?hchart=1&span=6&int=%d&ric=%s.HK&token=%s&qid=%d&callback=%s&_=%d"
    jsonCallBack = "jQuery3510021416680100266205_%d"
    headers = {
        "User-Agent": UserAgent,
    }
    token = "evLtsLsBNAUVTPxtGqVeG%2bCZDfd6q7Z5ljeC8EZ%2bAJZnb8xf1uAKElHhTxnwf31l"

    #
    def __init__(self):
        super().__init__(model.StockJyseType.HUNGKONG)

    def grab_stock_data_by_day(self, code, day):
        tp = self.__tp__(utils.DateUtil.date_format(day))
        if tp == -1:
            print("香港交易所无法根据日期匹配到查询类型:%s", day)
            return None
        content = self.__grab_stock_data__(code, tp)
        stockDatas = self.__resove_stock_data__(content, code, day, day)
        if not stockDatas or len(stockDatas) == 0:
            return None
        return stockDatas[0]

    def grab_stock_data_by_days(self, code, start, end):
        tp = max(self.__tp__(utils.DateUtil.date_format(start)), self.__tp__(utils.DateUtil.date_format(end)))
        if tp == -1:
            print("香港交易所无法根据日期匹配到查询类型:%s,%s", start, end)
            return None
        content = self.__grab_stock_data__(code, tp)
        stockDatas = self.__resove_stock_data__(content, code, start, end)
        if not stockDatas:
            return None
        return stockDatas[0]

    def __grab_stock_data__(self, code, tp: int):
        t = utils.DateUtil.timestamp(tp=1)
        jsonCallBack = self.jsonCallBack % (t + 10)
        url = self.stock_hisotry_request_uri % (tp, code, self.token, t, jsonCallBack, (t + 20))
        content = requests.get(url, headers=self.headers).content.decode("utf-8")
        return utils.JsonUtil.convertJsonFromJsonCallBack(content, jsonCallBack)

    def __resove_stock_data__(self, content, code, start, end):
        print(content)
        data = json.loads(content)
        data = data['data']['datalist']
        if not data or len(data) == 0:
            return None
        stockDatas = []
        start = utils.DateUtil.date_format(start, utils.DateUtil.DATE_FORMAT)
        end = utils.DateUtil.date_format(end, utils.DateUtil.DATE_FORMAT)
        for d in data:
            # [1691337600000, 94.350, 96.050, 93.600, 95.600, 22359341, 2129470000]
            if d[1]:
                dt = utils.DateUtil.localdate(d[0], tp=1)
                if utils.DateUtil.compareLess(dt, start) == 1 or \
                        utils.DateUtil.compareGreater(dt, end) == 1:
                    # 小于日期或超出结束日期时无效
                    continue
                dt = utils.DateUtil.format_date(dt)
                stockDatas.append(model.StockData(dt, code, d[1], d[2], d[3], d[4]))
        return stockDatas

    def __tp__(self, day: datetime.datetime):
        """
            @return
                0 当日，分时数据
                1 半年，官网使用4
                2 1个月
                3 3个月
                4 6个月(半年)
                5 一年
                6 两年，TODO 两年以上支提供周数据
                7 五年
                8 十年
                9 本年至今
        """
        num = utils.DateUtil.dif_days(datetime.datetime.now(), day)
        # else 1 if 90 < num <= 180 \
        return 0 if num == 0 \
            else 2 if 1 <= num <= 30 \
            else 3 if 30 < num <= 90 \
            else 4 if 90 < num <= 180 \
            else 5 if 180 < num <= 365 \
            else 6 if 365 < num <= (365 * 2) \
            else 7 if (365 * 2) < num <= (365 * 5) \
            else 8 if (365 * 5) < num <= (365 * 10) \
            else -1

jys = SzJysApi()
print(jys.grab_stock_data_by_day('000988','2023-09-11'))