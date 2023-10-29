import base64
import threading

import pymysql
from dongle.utils import Files as f

# db = pymysql.connect(host='db.dongle.com', user='root', passwd='Dongle@123', port=3306, db="dongle-data")
# cursor = db.cursor()
CAT = "jtll"
DIR = "F:\\Download\\Dongle\\b\\" + CAT + "\\"
files = f.read_dir_files_name(DIR)
DATE = "2023-10-23"
THREAD_NUM = 20


def putData(list):
    db = pymysql.connect(host='db.dongle.com', user='root', passwd='Dongle@123', port=3306, db="dongle-data")
    cursor = db.cursor()
    filename = ''
    try:
        for file in list:
            filename = file
            content = f.read_file(DIR + file).strip()
            sql = "insert into books(`title`,`cat`,`content`,`cdt`) value('%s','%s','%s','%s')" % (file, CAT, pymysql.converters.escape_string(content), DATE)
            cursor.execute(sql)
            db.commit()
    except Exception as ex:
        print("save book content is error",filename)
        db.rollback()
        raise
    cursor.close()
    db.close()


print("start sync %s files" % DIR)
size = int(len(files) / THREAD_NUM)
for i in range(0, len(files), size):
    start = i
    stop = start + min(size, len(files) - start)
    list = files[start:stop:]
    threading.Thread(target=putData, args=(list,)).start()