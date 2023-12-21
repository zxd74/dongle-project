"""
    各大普通资源网站通用解析
    1. 通过资源页获取资源信息：资源名，资源列表(需具体列表标签名或class)
    2. 通过资源播放页获取资源播放源信息(需具体播放源变量，根据需要解析m3u8地址)
    3. 获取实际资源源地址
"""
from bs4 import BeautifulSoup
import requests
import re,json

def requestHtml(url):
    return requests.get(url).content.decode('utf-8')

def resovleUrlRootPath(url):
    '''
        获取URL 根路径
    '''
    urls = urllib.parse.urlparse(url)
    return urls.scheme + "://" + urls.netloc

def resolveVideoName(soup):
    title = soup.title.string
    pattern=re.compile(r"《.*》")
    return pattern.search(title).group()

def resolveVideoList(soup:BeautifulSoup,el='div',cls=None,id=None):
    if cls:
        list = soup.find(el,class_=cls)
    else:
        list = soup.find(el,id=id)
    videos=[{'url':a.get('href'),'name':a.getText().replace('\n','').strip()} for a in list.find_all('a')]
    return videos

# 解析视频内容
def resolveVideoContentFromScript(soup,pattern,fieldname='url',defun=None):
    pattern=re.compile(pattern,re.MULTILINE|re.DOTALL)
    script=soup.find("script",string=pattern)
    data_str=pattern.search(script.string).group(1)
    if data_str[-1:] != "}":
        data_str = data_str[:-1]
    data_json= json.loads(data_str,strict=False)
    if defun:
        data_json[fieldname] = defun(data_json[fieldname])
    return data_json[fieldname]

def getRealM3u8(url):
    # 2000k/hls/mixed.m3u8 # 实际都是该结果，故直接输出
    prefix="/".join(url.split("/")[:-1])
    content=requests.get(url).content.decode("utf-8")
    if content.find("#EXTINF")==-1:  # 非真实m3u8链接
        for line in content.split("\n"):
            if not (line.startswith("#") or len(line)==0):
                return prefix + "/" + line
    else: # 真实m3u8链接
        return url

import urllib,base64
def urlDecode(url,type=0):
    """
        url内容解密
    """
    if type==0: return url
    if type==1: return urllib.parse.unquote(url)
    if type==2: return urllib.parse.unquote(base64.b64decode(url).decode("utf-8"))
    raise Exception("unknown type")


def formatFfmpeg(videos):
    print("----------------format m3u8 to ffmpeg---------------")
    for video in videos:
        print("nohup ffmpeg -i %s -vcodec copy resources/%s.mp4 > ff.log 2>&1 &" % (video['m3u8'],video['name']))

def formatUrlShow(videos):
    print("----------------show m3u8 url:")
    for video in videos:
        print("[%s] %s" % (video['name'],video['m3u8']))

def resolveVideo(home,id,cls,pattern,decodeType=0):
    """
    @home 视频介绍页
    @id 视频集目录id
    @cls 视频集目录class，与id二者取其一，id优先
    @pattern 视频播放内容解析脚本变量，从script
    @decode_type 视频路径解密方式，0-无需解密，1-url解密，2-url+base64解密
    """
    host = resovleUrlRootPath(home) 
    content = requestHtml(home)
    soup = BeautifulSoup(content, 'html.parser')
    videos  = resolveVideoList(soup,el=None,id=id,cls=cls)
    name = resolveVideoName(soup)
    print("film name:",name)
    for video in videos:
        # print(video['url'])
        content = requestHtml(host + video['url'])
        soup = BeautifulSoup(content, 'html.parser')
        url = resolveVideoContentFromScript(soup,pattern)
        url = urlDecode(url,decodeType)
        video['m3u8'] = getRealM3u8(url)
        video['name'] = name + video['name']
    
    formatUrlShow(videos)

def videoFrom(url):
    rootUrl = resovleUrlRootPath(url);
    print("rootUrl:",rootUrl)
    if rootUrl.find("www.xigua543.com") != -1:
        # videoFromXigua543(url)
        resolveVideo(url,'con_playlist_1',None,r"var zanpiancms_player =(.*?)$")
    elif rootUrl.find("www.hdmoli.top") != -1:
        # videoFromHdmoli(url)
        resolveVideo(url,'panel1',None,r"var player_aaaa=(.*?)$",decodeType=2)
    elif rootUrl.find("libvio.one") != -1:
        resolveVideo(url,None,"stui-content__playlist",r"var player_aaaa=(.*?)$",decodeType=2)

if __name__ == '__main__':
    videoFrom("https://libvio.one/detail/28519.html")
