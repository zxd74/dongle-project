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

def formatFfmpeg(videos):
    for video in videos:
        print("nohup ffmpeg -i %s -vcodec copy resources/%s.mp4 > ff.log 2>&1 &" % (video['m3u8'],video['name']))

def videoFromXigua543(index):
    """
        https://www.xigua543.com/
    """
    host = "https://www.xigua543.com/"
    content = requestHtml(index)
    soup = BeautifulSoup(content, 'html.parser')
    videos  = resolveVideoList(soup,el=None,id='con_playlist_1')
    name = resolveVideoName(soup)
    for video in videos:
        content = requestHtml(host + video['url'])
        soup = BeautifulSoup(content, 'html.parser')
        url = resolveVideoContentFromScript(soup,r"var zanpiancms_player =(.*?)$")
        video['m3u8'] = getRealM3u8(url)
        video['name'] = name + video['name']
    formatFfmpeg(videos)


def videoFromHdmoli(index):
    """
        https://www.hdmoli.com/
    """
    def hdmoliDecode(content):
        import urllib,base64
        return urllib.parse.unquote(base64.b64decode(content).decode("utf-8"))
    
    host = "https://www.hdmoli.top/"
    content = requestHtml(index)
    soup = BeautifulSoup(content, 'html.parser')
    videos  = resolveVideoList(soup,el=None,id='panel1')
    name = resolveVideoName(soup)
    for video in videos:
        content = requestHtml(host + video['url'])
        soup = BeautifulSoup(content, 'html.parser')
        url = resolveVideoContentFromScript(soup,r"var player_aaaa=(.*?)$",defun=hdmoliDecode)
        video['m3u8'] = getRealM3u8(url)
        video['name'] = name + video['name']
    formatFfmpeg(videos)

def videoFrom(url):
    if url.find("www.xigua543.com") != -1:
        videoFromXigua543(url)
    elif url.find("www.hdmoli.top") != -1:
        videoFromHdmoli(url)

if __name__ == '__main__':
    videoFrom("https://www.xigua543.com/dongzuopian/jingqiduichang2/")
