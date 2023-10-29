import requests,threading,os
from bs4 import BeautifulSoup
from dongle.utils import Files as f

HOST = "https://www.z3680o.com"
BOOK_LIST_HOME = HOST + "/art/jiatingluanlun/"
BOOK_LIST_HOST = BOOK_LIST_HOME + "index_%d.html"
FILE_DIR = "F:\\Download\\Dongle\\b\\jtll\\"
if not os.path.exists(FILE_DIR) :
    os.makedirs(FILE_DIR)
FILE_NAME = FILE_DIR + "%s.txt"
MAX_PAGE_NUM = 10  # 最大抓取数，避免一直抓取
TOTAL = 0  # 网站总计数量
TOTAL_PAGE = 200  # 网站共共计数量


def resolveTotal(content):
    content = BeautifulSoup(content, "html.parser")
    data = content.find(class_="page_tip").text.split(",")
    TOTAL = data[0].replace("共", "").replace("条数据", "")
    TOTAL_PAGE = data[1].split("/")[1].replace("页", "")
    print("总计数量：%s,共计%s页" % (TOTAL, TOTAL_PAGE))


def getBookList(num):
    url = BOOK_LIST_HOST % num
    print("-------请求第%d页列表-------:%s" % (num,url))
    if num == 1:
        content = requests.get(BOOK_LIST_HOME).content.decode("utf-8")
        resolveTotal(content)
    else:
        content = requests.get(BOOK_LIST_HOST % num).content.decode("utf-8")
    return resolveBookList(content)


def resolveBookList(content):
    data = BeautifulSoup(content, "html.parser")
    result = []
    for a in data.find(class_="list-text-my").find("ul").find_all("a"):
        url = a['href']
        title = a['title']
        result.append({"url":url,"title":title})
    return result


def getBookListContent(bookList):
    for book in bookList:
        getBookContent(HOST + book['url'],book['title'])


def getBookContent(url,title):
    content = requests.get(url).content.decode("utf-8")
    content = resolveBookContent(content)
    f.write_content_by_lines(FILE_NAME % handler(title),content)
    print("已抓取",title)


def handler(title:str):
    return title.replace("[","").replace("]","")\
        .replace("<","").replace(">","")


def resolveBookContent(content):
    data = BeautifulSoup(content, "html.parser")
    return data.find(id="tpl-img-content").text.replace("<br/>","\\n")


def getBooksContent(num):
    try:
        getBookListContent(getBookList(num))
        print("-------请求第%d页抓取完毕-------" % num)
    except Exception as ex:
        print("第%d页抓取异常" % num,ex)


if __name__ == "__main__":
    maxPage = min(TOTAL_PAGE,MAX_PAGE_NUM)
    for i in range(1,maxPage,1):
        # getBookListContent(getBookList(i + 1))
        # 多线程模式, TODO 如何何理设置线程池多少
        curPage = i
        threading.Thread(target=getBooksContent,args=(curPage,),name="第%d页数据抓取" % curPage).start()