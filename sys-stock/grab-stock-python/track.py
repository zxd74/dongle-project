import sys
import datetime
import baostack_data as bs
import utils as u

PARAM_CODE = 'code'
PARAM_METHOD = 'method'
PARAM_DAY = 'day'
METHOD_NEW = "new"
METHOD_DATA = "data"
DATE_FORMAT = "%Y-%m-%d"

params = {'method': 'data', 'code': 'sz.002236', 'day': datetime.datetime.now().strftime(DATE_FORMAT)}

sys.argv.pop(0)
for p in sys.argv:
    ps = p.split("=")
    params[ps[0]] = ps[1]
print(params)


# 抓取新股票，抓取近一个月的股票数据
def grab_new_stock():
    if not params.__contains__(PARAM_CODE):
        print("缺少必要code")
        sys.exit(-1)
    code = params[PARAM_CODE]
    start_day = params[PARAM_DAY]
    # end_day = (datetime.datetime.now() - datetime.timedelta(days=1)).strftime(DATE_FORMAT)
    end_day = params[PARAM_DAY]
    if u.exsit_stock_data(code):
        print("stock is exist!")
        return
    bs.login()
    data = bs.track_stock_info(code)
    if data:
        u.save_stock_data([data.to_list().__str__()])
        save_stock_data([code], start_day, end_day)
    bs.logout()


#  抓取指定日期的所有股票数据，先校验是否存在，不存在再抓取，存在则不抓取
def grab_stock_data():
    day = params[PARAM_DAY]
    if u.exsit_stock_day_data(day):
        print("%s stock day already exist!")
        return
    bs.login()
    codes = u.query_stock_code_list()
    save_stock_data(codes,day,day)
    bs.logout()


# 保存股票交易数据
def save_stock_data(codes,sday,eday):
    result = bs.track_stock_data(codes, sday, eday)
    data = []
    for r in result:
        data.append(r.to_list().__str__())
    if len(data)>0:
        u.save_history_data(data)


if __name__ == "__main__":
    if params[PARAM_METHOD] == METHOD_NEW:
        grab_new_stock()
    else:
        grab_stock_data()
