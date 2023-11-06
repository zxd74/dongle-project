"""
计算每股收益及总收入
"""


class IncomeCalc():
    def m(self, cost, price, num):
        pass


class PaCalc(IncomeCalc):
    """
    平安银行
    """
    stock_rage_a = 0.003
    stock_rage_min = 500
    stock_stamp = 0.001

    def m(self, cost, price, num):
        result, rate = self.__sm__(cost, price, num)
        print("股本价：%d,股价：%d,%d股收益：%.2f，收益率：%.3f" % (cost, price, num, result/100, rate))
        return result

    def __sm__(self, cost, price, num):
        rage = price * num * self.stock_rage_a
        rage = self.stock_rage_min if rage < self.stock_rage_min else rage
        stamp = price * num * self.stock_stamp
        result = ((price - cost) * num) - (rage + stamp)
        rate = result / (cost * num)
        return result, rate


stock_cost = 3714
stock_price = 3679
stock_num = 500
stock_day = 10

calc = PaCalc()
result = calc.m(stock_cost, stock_price, stock_num)
print("预估每日收入：%.3f" % ((float(result)/100)/stock_day))
