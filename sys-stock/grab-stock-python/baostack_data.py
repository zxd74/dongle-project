import baostock as bs
import model

history_filed = "date,code,open,high,low,close,preclose,volume,amount,adjustflag,turn,tradestatus,pctChg,peTTM,pbMRQ," \
                "psTTM,pcfNcfTTM,isST "


def login():
    lg = bs.login()
    if lg is None: return False
    if lg.error_code != '0':
        print('login respond code:%s,msg:%s' % (lg.error_code, lg.error_msg))
        return False
    return True


def logout():
    bs.logout()


def track_stock_info(code):
    rs = bs.query_stock_basic(code)
    # rs = bs.query_stock_basic(code_name="浦发银行")  # 支持模糊查询
    if rs.error_code != '0':
        print('query_stock_basic respond code:%s,msg:%s' % (rs.error_code, rs.error_msg))
        return False
    while (rs.error_code == '0') & rs.next():
        data = rs.get_row_data()
        return model.StockInfo(data[0], data[1], data[2], data[3], data[4], data[5])
    return None


def track_stock_data(codes,sday,eday):
    """

    @rtype: object
    """
    result = []
    for code in codes:
        # 打印结果集
        rs = bs.query_history_k_data_plus(code,
                                          history_filed,
                                          start_date=sday, end_date=eday,
                                          frequency="d", adjustflag="3")
        if rs.error_code != '0':
            print('query_history_k_data_plus respond code:%s,msg:%s' % (rs.error_code, rs.error_msg))
            return
        while (rs.error_code == '0') & rs.next():
            data = rs.get_row_data()
            result.append(model.StockData(data[0], data[1], data[2], data[3], data[4], data[5],
                                   data[6], data[7], data[8], data[9], data[10], data[11],
                                   data[12], data[13], data[14], data[15], data[16], data[17]))
    if len(result) == 0:
        return None
    return result