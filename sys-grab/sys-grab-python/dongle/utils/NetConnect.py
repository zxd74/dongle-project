# need install requests =》 pip3 install requests
import requests

# html 解析：beautifulsoup(4)、xpath、lxml(etree)、requests-html、正则、pyquery、ehp、justext
# xml 解析：(xml)sax\dom\elementtree

# 模拟请求header头信息
headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36',
    'Accept-Language': 'zh-CN,zh;q=0.9',
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
    'Cache-Control': 'max-age=0',
    'Connection': 'keep-alive',
    # 'X-FORWARDED-FOR':'',
}

# 代理设置
# proxies = {'http': "socks5://127.0.0.1:8080",
#            'https': "socks5://127.0.0.1:8080"}

# 保留session，保持长链接，缩短频繁请求时长
session = requests.session()


def request_get(url):
    return session.get(url, headers=headers)


def request_for_json_by_get(url, encoding="UTF-8") -> str:
    """
    请求json接口响应
    @param encoding:
    @param url:
    @return:
    """
    response = request_get(url)  # 请求连接
    if response.status_code == 200:
        response.encoding = encoding
        req_jason = response.json()  # 获取数据
        return req_jason
    return ""


def request_for_html_by_get(url, encoding="UTF-8") -> str:
    """
    请求html页面内容
    @param url:
    @param encoding:
    @return:
    """
    response = request_get(url)  # 请求连接
    if response.status_code == 200:
        return response.content.decode(encoding)
    return ""
