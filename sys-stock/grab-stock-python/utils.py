import pymysql
import csv

db = pymysql.connect(host='db.dongle.com', user='root', passwd='Dongle@123', port=3306, db="dongle-data")
cursor = db.cursor()


def dis_conn():
    # db.close()
    # cursor.close()
    pass


# 保存股票交易数据
def save_history_data(result):
    try:
        sql = "insert into stock_history_data(`date`,`code`,`open`,`high`,`low`,`close`,`preclose`,`volume`,`amount`,`adjustflag`,`turn`,`tradestatus`,`pctChg`,`peTTM`,`pbMRQ`,`psTTM`,`pcfNcfTTM`,`isST`) values "
        for row in result:
            sql += "(" + str(row).removeprefix("[").removesuffix("]") + "),"
        sql = sql.removesuffix(",")
        cursor.execute(sql)
        db.commit()
    except Exception as ex:
        print("save history data is error")
        print(ex)
        db.rollback()

    # 关闭数据库连接
    dis_conn()


# 保存股票信息
def save_stock_data(result):
    try:
        sql = "insert into stock_info(`code`,  `code_name`,  `ipoDate`,  `outDate`,  `type`,  `status`) values "
        for row in result:
            sql += "(" + str(row).removeprefix("[").removesuffix("]") + "),"
        sql = sql.removesuffix(",")
        cursor.execute(sql)
        db.commit()
    except Exception as ex:
        print("save history data is error")
        print(ex)
        db.rollback()

    # 关闭数据库连接
    dis_conn()


# 查询股票代码列表
def query_stock_code_list():
    codes = []
    try:
        sql = "select code from stock_info"
        cursor.execute(sql)
        result = cursor.fetchall()
        for r in result:
            codes.append(r[0])
    except Exception as ex:
        print("save history data is error")
        print(ex)
    dis_conn()
    return codes


# 检查股票是否存在
def exsit_stock_data(code):
    sql = "select count(1) from stock_info where code = '" + code + "'"
    cursor.execute(sql)
    result = cursor.fetchone()
    for r in result:
        return r != 0
    return False


# 检查交易日数据是否存在
def exsit_stock_day_data(day):
    sql = "select count(1) from stack_history_data where date = '" + day + "'"
    cursor.execute(sql)
    result = cursor.fetchone()
    for r in result:
        return r != 0
    return False


def readExcel(file):
    data = []
    with open(file, 'r', encoding='utf-8') as csv_file:
        reader = csv.reader(csv_file)
        next(reader)
        for row in reader:
            data.append(row)
    return data
