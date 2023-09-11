import requests
from bs4 import BeautifulSoup as soup

# https://www.biquxsw.net/38_38339/
BOOK_INFO_URL = "https://www.biquxsw.net/38_38339/"
# https://www.biquxsw.net/153_153178/60603190.html


def getBookChaptList(url):
    """
    获取图书章节列表
    @param content:
    @return:
    """
    if not url.endswith("/"):
        url = url + "/"
    content = requests.get(url).content
    html = soup(content, "html.parser")
    dt = html.find(id="list").find_all("dt")[1]

    book_chapts = []
    while dt.next_sibling:
        dt = dt.next_sibling
        a = dt.find("a")
        if a:
            book_chapts.append({'url': url + a['href'], "chapt": a.text})
    return book_chapts


def getBookChaptContent(url):
    """
    抓取图书章节内容
    @param url:
    @return:
    """
    content = requests.get(url).content
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
    getBookChaptList("https://www.biquxsw.net/38_38339")