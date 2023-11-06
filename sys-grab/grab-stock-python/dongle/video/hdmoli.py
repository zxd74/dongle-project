"""
    首页：https://www.hdmoli.top/
    视频详情页：https://www.hdmoli.top/detail/23043.html
    视频播放页：https://www.hdmoli.top/play/23043-1-1.html
"""
import requests
import re,json,base64,urllib
from bs4 import BeautifulSoup

# 1. 从第一集开始解析
        # 1.1 获取视频列表
    # 2. 读取视频配置源，并解析具体视频url
    # 3. 请求具体的m3u8地址
HOST="https://www.hdmoli.top"
FIRST="https://www.hdmoli.top/play/23043-1-1.html"

def getM3u8(soup):
    pattern=re.compile(r"var player_aaaa=(.*?)$",re.MULTILINE|re.DOTALL)
    script=soup.find("script",string=pattern)
    data_str=pattern.search(script.string).group(1)
    data_json=json.loads(data_str,strict=False)
    if data_json['url_next']:
        data_json['url_next']=decode(data_json['url_next'])
    if data_json['url']:
        data_json['url']=decode(data_json['url'])
    return data_json

def decode(content):
    return urllib.parse.unquote(base64.b64decode(content).decode("utf-8"))

def getList(soup):
    list = soup.find("div",class_="module-play-list")
    videos=[]
    for a in list.find_all("a"):
        videos.append({'url':a.get("href"),'title':a.get("title")})
    return videos

def getRealM3u8(url):
    # https://vip.ffzy-play6.com/20221210/14056_112a16b4/index.m3u8
    # 2000k/hls/mixed.m3u8 # 实际都是该结果，故直接输出
    prefix="/".join(url.split("/")[:-1])
    # content=requests.get(url).content.decode("utf-8")
    # for line in content.split("\n"):
    #     if not (line.startswith("#") or len(line)==0):
    #         return prefix + "/" + line
    return prefix + "/2000k/hls/mixed.m3u8"

def getHtml(url):
    content = requests.get(url).content.decode("utf-8")
    return BeautifulSoup(content, "html.parser")


videos=[{'url': '/play/23043-1-1.html', 'title': '播放疯味英雄第01集', 'm3u8': 'https://vip.ffzy-play6.com/20221210/14056_112a16b4/index.m3u8', 'real-m3u8': 'https://vip.ffzy-play6.com/20221210/14056_112a16b4/2000k/hls/mixed.m3u8'}, {'url': '/play/23043-1-2.html', 'title': '播放疯味英雄第02集', 'm3u8': 'https://vip.ffzy-play6.com/20221210/14058_2fb44556/index.m3u8', 'real-m3u8': 'https://vip.ffzy-play6.com/20221210/14058_2fb44556/2000k/hls/mixed.m3u8'}, {'url': '/play/23043-1-3.html', 'title': '播放疯味英雄第03集', 'm3u8': 'https://vip.ffzy-play6.com/20221210/14057_39f7731d/index.m3u8', 'real-m3u8': 'https://vip.ffzy-play6.com/20221210/14057_39f7731d/2000k/hls/mixed.m3u8'}, {'url': '/play/23043-1-4.html', 'title': '播放疯味英雄第04集', 'm3u8': 'https://vip.ffzy-play6.com/20221210/14059_074fa9da/index.m3u8', 'real-m3u8': 'https://vip.ffzy-play6.com/20221210/14059_074fa9da/2000k/hls/mixed.m3u8'}, {'url': '/play/23043-1-5.html', 'title': '播放疯味英雄第05集', 'm3u8': 'https://vip.ffzy-play6.com/20221210/14060_571a75f4/index.m3u8', 'real-m3u8': 'https://vip.ffzy-play6.com/20221210/14060_571a75f4/2000k/hls/mixed.m3u8'}, {'url': '/play/23043-1-6.html', 'title': '播放疯味英雄第06集', 'm3u8': 'https://vip.ffzy-play6.com/20221210/14061_19360147/index.m3u8', 'real-m3u8': 'https://vip.ffzy-play6.com/20221210/14061_19360147/2000k/hls/mixed.m3u8'}, {'url': '/play/23043-1-7.html', 'title': '播放疯味英雄第07集', 'm3u8': 'https://vip.ffzy-play6.com/20221210/14062_d0d11ee6/index.m3u8', 'real-m3u8': 'https://vip.ffzy-play6.com/20221210/14062_d0d11ee6/2000k/hls/mixed.m3u8'}, {'url': '/play/23043-1-8.html', 'title': '播放疯味英雄第08集', 'm3u8': 'https://vip.ffzy-play6.com/20221210/14063_07f67f80/index.m3u8', 'real-m3u8': 'https://vip.ffzy-play6.com/20221210/14063_07f67f80/2000k/hls/mixed.m3u8'}, {'url': '/play/23043-1-9.html', 'title': '播放疯味英雄第09集', 'm3u8': 'https://vip.ffzy-play6.com/20221210/14064_ae8f8fbc/index.m3u8', 'real-m3u8': 'https://vip.ffzy-play6.com/20221210/14064_ae8f8fbc/2000k/hls/mixed.m3u8'}, {'url': '/play/23043-1-10.html', 'title': '播放疯味英雄第10集', 'm3u8': 'https://vip.ffzy-play6.com/20221210/14065_0183e7a1/index.m3u8', 'real-m3u8': 'https://vip.ffzy-play6.com/20221210/14065_0183e7a1/2000k/hls/mixed.m3u8'}, {'url': '/play/23043-1-11.html', 'title': '播放疯味英雄第11集', 'm3u8': 'https://vip.ffzy-play6.com/20221210/14066_92970722/index.m3u8', 'real-m3u8': 'https://vip.ffzy-play6.com/20221210/14066_92970722/2000k/hls/mixed.m3u8'}, {'url': '/play/23043-1-12.html', 'title': '播放疯味英雄第12集', 'm3u8': 'https://vip.ffzy-play6.com/20221210/14067_460ba91d/index.m3u8', 'real-m3u8': 'https://vip.ffzy-play6.com/20221210/14067_460ba91d/2000k/hls/mixed.m3u8'}, {'url': '/play/23043-1-13.html', 'title': '播放疯味英雄第13集', 'm3u8': 'https://vip.ffzy-play6.com/20221210/14068_b5b68bbc/index.m3u8', 'real-m3u8': 'https://vip.ffzy-play6.com/20221210/14068_b5b68bbc/2000k/hls/mixed.m3u8'}]
def formatM3u8(videos):
    for video in videos:
        print("nohup ffmpeg -i %s -vcode copy resources/%s.mp4 > ff.log 2>&1 &" % (video['real-m3u8'],video['title']))

formatM3u8(videos)


if __name__ == "__main__":
    soup=getHtml(FIRST)
    videos=getList(soup)
    for video in videos:
        url = HOST + video['url']
        if url == FIRST:
            data = getM3u8(soup)
        else:
            soup = getHtml(url)
            data = getM3u8(soup)
        video['m3u8']=data['url']
        video['real-m3u8'] = getRealM3u8(data['url'])
    formatM3u8(videos)
