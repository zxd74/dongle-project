import pymysql
from urllib.parse import urlparse
# TODO 未完

class PoolDB(object):
    def __init__(self,creator,maxconnections,maxcached,maxshared,blocking,setsession,host,port,user,password,database,charset):
        self.creator = creator,
        self.maxconnections = maxconnections,  # 连接池的最大连接数
        self.maxcached = maxcached,
        self.maxshared = maxshared,
        self.blocking = blocking,
        self.setsession = setsession,
        self.host = host,
        self.port = port or 3306,
        self.user = user,
        self.password = password,
        self.database = database,
        self.charset = charset or 'utf8',

    def connection(self):
        return pymysql.connect(host=self.host,
                               port=self.port,
                               user=self.user,
                               password=self.password,
                               database=self.database,
                               charset=self.charset)

    def cursor(self,cursor):

        return

class MyPool(object):
    def __init__(self,url):
        self.url = url
        if not url:
            return
        url = urlparse(url)
        self.POOL = PoolDB(
            creator=pymysql,
            maxconnections=10,  # 连接池的最大连接数
            maxcached=10,
            maxshared=10,
            blocking=True,
            setsession=[],
            host=url.hostname,
            port=url.port or 3306,
            user=url.username,
            password=url.password,
            database=url.path.strip().strip('/'),
            charset='utf8',
        )

    def __new__(cls, *args, **kw):
        if not hasattr(cls, '_instance'):
            cls._instance = object.__new__(cls)
        return cls._instance

    def connect(self):
        conn = self.POOL.connection()
        cursor = conn.cursor(cursor=pymysql.cursors.DictCursor)
        return conn, cursor

    def connect_close(self, conn, cursor):
        cursor.close()
        conn.close()

    def fetch_all(self, sql, args):
        conn, cursor = self.connect()
        if args is None:
            cursor.execute(sql)
        else:
            cursor.execute(sql, args)
        record_list = cursor.fetchall()
        return record_list

    def fetch_one(self, sql, args):
        conn, cursor = self.connect()
        cursor.execute(sql, args)
        result = cursor.fetchone()
        return result

    def insert(self, sql, args):
        conn, cursor = self.connect()
        row = cursor.execute(sql, args)
        conn.commit()
        self.connect_close(conn, cursor)
        return row