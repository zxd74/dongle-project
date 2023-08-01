import json,requests
import time,random

UserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36"


class JysApi:
    def __init__(self):
        pass

    def grab_stock_data_by_day(self,code,day):
        """
        查询一天股票数据
        @param code:
        @param day:
        @return:
        """
        pass

    def grab_stock_data_by_days(self,code,start,end):
        """
        查询一段时间股票数据
        @param code:
        @param start:
        @param end:
        @return:
        """
        pass


class SzJysApi(JysApi):
    """
    深圳交易所API查询
    """
    # https://www.szse.cn/api/report/ShowReport/data?SHOWTYPE=JSON&CATALOGID=1815_stock_snapshot&TABKEY=tab1&txtDMorJC=002415&txtBeginDate=2023-07-31&txtEndDate=2023-07-31&archiveDate=2021-07-01&random=0.27880532547100145"
    stock_data_request_uri = "https://www.szse.cn/api/report/ShowReport/data?SHOWTYPE=JSON&CATALOGID=1815_stock_snapshot&TABKEY=tab1&txtDMorJC=%s&txtBeginDate=%s&txtEndDate=%s&archiveDate=&random=0.27880532547100145"
    cols = {"jyrq": "交易日期", "zqdm": "证券代码", "zqjc": "证券简称", "qss": "前收", "ks": "开盘", "zg": "最高", "zd": "最低", "ss": "今收",
            "sdf": "涨跌幅<br>（%）", "cjgs": "成交量<br>(万股)", "cjje": "成交金额<br>(万元)", "syl1": "市盈率"}
    def __init__(self):
        super().__init__()

    def grab_stock_data_by_day(self,code,day):
        url = self.stock_data_request_uri % (code, day, day)
        content = requests.get(url)
        data = content.json()
        data = data[0]['data']
        if len(data) == 0:
            print("未查询道数据")
            return

        for d in data:
            for col in self.cols:
                print("%s : %s" % (self.cols[col], d[col]))
        pass


class ShJysApi(JysApi):
    """
    上海交易所API查询
    """
    # http://query.sse.com.cn/commonQuery.do?jsonCallBack=jsonpCallback11398773&sqlId=COMMON_SSE_CP_GPJCTPZ_GPLB_CJGK_MRGK_C&SEC_CODE=603069&TX_DATE=2023-07-26&TX_DATE_MON=&TX_DATE_YEAR=&_=1690808485545
    # http://query.sse.com.cn/commonQuery.do?jsonCallBack=jsonpCallback%s&sqlId=COMMON_SSE_CP_GPJCTPZ_GPLB_CJGK_MRGK_C&SEC_CODE=%s&TX_DATE=%s&TX_DATE_MON=&TX_DATE_YEAR=&_=%d
    stock_request_day_uri = "http://query.sse.com.cn/commonQuery.do?"
    def __init__(self):
        super().__init__()

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
    def grab_stock_data_by_day(self,code,day):
        t = int(time.time() * 1000)
        r = int(random.random() * (100000000  + 1))
        headers  = {
            "User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36",
            "Cookie":"ba17301551dcbaf9_gdp_user_key=; gdp_user_id=gioenc-342gae68%2C078g%2C5d97%2Cacg0%2Cgg9812bb3526; ba17301551dcbaf9_gdp_session_id_b4388a90-37b4-4870-a840-ba2fd116da65=true; ba17301551dcbaf9_gdp_session_id_e752cd56-677c-4c58-8907-e0f12c2f08d8=true; ba17301551dcbaf9_gdp_session_id_a8288453-f138-4dae-9543-37f6a7401ac8=true; VISITED_MENU=%5B%229061%22%2C%229060%22%5D; VISITED_COMPANY_CODE=%5B%22600000%22%2C%22688111%22%2C%22603069%22%5D; VISITED_STOCK_CODE=%5B%22600000%22%2C%22688111%22%2C%22603069%22%5D; JSESSIONID=AB2D0D283994F48072E17109768C6BA3; ba17301551dcbaf9_gdp_session_id=757befbd-70c1-4978-88c7-6952898e7084; ba17301551dcbaf9_gdp_session_id_757befbd-70c1-4978-88c7-6952898e7084=true; ba17301551dcbaf9_gdp_sequence_ids={%22globalKey%22:252%2C%22VISIT%22:5%2C%22PAGE%22:36%2C%22VIEW_CLICK%22:185%2C%22VIEW_CHANGE%22:15%2C%22CUSTOM%22:15}",
            "Host":"query.sse.com.cn",
            "Referer":"http://www.sse.com.cn/",  # TODO 此条件必需
        }
        jsonCallBack = "jsonpCallback" + str(r)
        params={
            "jsonCallBack":jsonCallBack,
            "sqlId":"COMMON_SSE_CP_GPJCTPZ_GPLB_CJGK_MRGK_C",
            "SEC_CODE" :code,
            "TX_DATE_MON":day,
            "TX_DATE_YEAR":"",
            "_":t
        }
        content = requests.post(self.stock_request_day_uri,data=params,headers=headers).content.decode("utf-8")
        content = content[22:-1:]
        data = json.loads(content)
        if 'result' not in data:
            # {"jsonCallBack":"jsonpCallback14576570","success":"false","error":"System Error, Please try again later...","errorType":"ExceptionInterceptor"}
            print("查询错误",content)
            return
        data = data['result']
        if len(data) == 0 :
            print("未查询道数据")
            return
        for d in data:
            print(d)


class BjJysApi(JysApi):
    """
    北京交易所API
    """
    # 最新信息 https://www.bse.cn/nqhqController/nqhq.do?callback=jQuery331_1690817441090&page=0&zqdm=833751&xxfcbj=2&_=1690817441091
    # 日K线，https://www.bse.cn/companyEchartsController/getKLine/list/833751.do?%7B%22begin%22%3A0%2C%22end%22%3A-1%2C%22type%22%3A%22dayKline%22%2C%22xxfcbj%22%3A2%7D&callback=jQuery331_1690818072978&begin=0&end=-1&type=dayKline&xxfcbj=2&_=1690818072980
    stock_request_uri = "https://www.bse.cn/nqhqController/nqhq.do"

    def grab_stock_data_by_day(self, code, day):
        t = int(time.time() * 1000)
        headers = {
            # "Host":"www.bse.cn",
            # "Referer":"https://www.bse.cn/products/neeq_listed_companies/company_time_sharing.html?companyCode=%s&typename=G" % code,
            # "Cookie":"HMF_CI=5c15cfbf2c84f6421545690ddcff8fa920310faf2fe71df7c63bf43708086934216e64a9fd0a8b1d5711672cd4ab69bc72d462e4246aa6734b15c85581de0a5ff9; Hm_lvt_ef6193a308904a92936b38108b93bd7f=1690790903,1690861389; Hm_lpvt_ef6193a308904a92936b38108b93bd7f=%s" % int(time.time()),
            # "Content-Type":"Application/json;charset=utf-8", # TODO 必需
            # "X-Requested-With":"XMLHttpRequest",
            "User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36",
        }
        url = "https://www.bse.cn/nqhqController/nqhq.do?callback=jQuery331_%s&page=0&zqdm=%s&xxfcbj=2&_=%s" % (t,code,t+1)
        content = requests.get(url,headers=headers).content.decode("UTF-8")
        content = content[24:-1:]
        data = json.loads(content)
        # "content":[{"hqbjw1":10.1,"hqbjw2":10.09,"hqbjw3":10.08,"hqbjw4":10.07,"hqbjw5":10.06,"hqbsl1":200,"hqbsl2":3327,"hqbsl3":26337,"hqbsl4":15982,"hqbsl5":12058,"hqcjbs":0,"hqcjje":3565705.23,"hqcjsl":352939,"hqckj":0,"hqgxsj":"115158","hqhycc":0,"hqjrkp":10.09,"hqjsd1":0,"hqjsd2":0,"hqjsrq":"20230801","hqppl":0,"hqsjw1":10.11,"hqsjw2":10.12,"hqsjw3":10.13,"hqsjw4":10.14,"hqsjw5":10.15,"hqssl1":4162,"hqssl2":21580,"hqssl3":45524,"hqssl4":39274,"hqssl5":28500,"hqsyl1":15.1729,"hqsyl2":0,"hqzd":0,"hqzdcj":10.09,"hqzdf":0,"hqzgcj":10.13,"hqzjcj":10.09,"hqzqdm":"430017","hqzqjc":"星昊医药","hqzrsp":10.09,"hqzybj":10.1,"hqzybl":200,"hqzysj":10.11,"hqzysl":4162,"xxcyhbjq":"","xxfcbj":"2","xxhbzl":"人民币","xxzrlx":"连续竞价"}]
        content = data[0]['content']
        data = content[0]
        rq = data['hqjsrq']
        if rq != day.replace("-",""):
            print("查询日期不相符 查询日期:%s,结果日期:%s" % (day,rq))
            return
        print(data)


class XgJysApi(JysApi):

    stock_request_uri = "https://www1.hkex.com.hk/hkexwidget/data/getderivativesfutures?lang=chn&token=evLtsLsBNAUVTPxtGqVeG73F%2bd6E7hIUdgfBG8sX%2ftEE5bq41sSkWWLCcgGzv6ak"
    params_url = "&ats=%s&type=0&qid=%s&callback=jQuery%s_%s&_=%s"

    def grab_stock_data_by_day(self, code, day):
        t = int(time.time() * 1000)
        headers = {
            "User-Agent": UserAgent,
        }
        url = self.stock_request_uri + (self.params_url % (code,t + 100,"35108083434263050362",t,t + 4))
        content = requests.get(url,headers=headers).content.decode("UTF-8")
        content = content[41:-1:]
        data = json.loads(content)
        data = data['data']['futureslist'][0]
        print(data)
        pass


# jys = SzJysApi()
# jys.grab_stock_data_by_day("002415","2023-07-31")

# jys = ShJysApi()
# jys.grab_stock_data_by_day("603069","2023-07-31")


# jys = BjJysApi()
# jys.grab_stock_data_by_day("430017","2023-07-31")


# jys = XgJysApi()
# jys.grab_stock_data_by_day("ALH",None)
