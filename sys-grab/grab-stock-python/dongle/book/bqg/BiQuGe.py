import requests
from bs4 import BeautifulSoup as soup
from selenium import webdriver
from selenium.webdriver.chrome.options import Options

## https://www.beqege.com 需要模拟实际浏览器请求，否则无法请求实际内容
BOOK_INFO_URL = "https://www.beqege.com/61467/"
OPTIONS = Options()
OPTIONS.add_argument("--headless")  # 静默模式
OPTIONS.add_argument("--disable-gpu")  # 禁止加载图片
OPTIONS.add_argument("log-level=3")  # 禁止打印你日志


def getHtmlContent(url):
    """
    利用selenium模拟实际浏览器访问行为
    @param url:
    @return:
    """
    chrome = webdriver.Chrome(OPTIONS)
    chrome.get(url)
    content = chrome.page_source
    chrome.close()
    return content

def getBookChaptList(url):
    """
    获取图书章节列表
    @param content:
    @return:
    """
    if not url.endswith("/"):
        url = url + "/"
    content = getHtmlContent(url)
    print(len(content),content)
    html = soup(content, "html.parser")
    book_chapts = []

    print(html.find(id="list"))
    # dd = html.find(id="list")
    # if dd:
    #     print(dd.children)
    # else:
    #     print("no content")
    # for dd in html.find(id="list").find_all("dd"):
    #     a = dd.find("a")
    #     if a:
    #         book_chapts.append({'url': url + a['href'], "chapt": a.text})
    return book_chapts


def getBookChaptContent(url):
    """
    抓取图书章节内容
    @param url:
    @return:
    """
    content = getHtmlContent(url)
    html = soup(content,"html.parser")
    return html.find(id="content").text


def getBookChaptContents(list):
    """
    抓取图书所有章节内容
    @param list:
    @return:
    """
    for b in list:
        b['content'] = getBookChaptContent(b['url'])


if __name__ == "__main__":
    getBookChaptList("https://www.beqege.com/309/")