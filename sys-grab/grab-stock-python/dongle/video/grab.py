"""
1. 请求视频首页，解析视频列表
2. 循环视频列表
    2.1 根据视频查找m3u8，并解析实际m3u8地址
    2.2 解析实际m3u8内容，包含加密
    2.3 下载ts列表
        2.3.1 异步执行，解码ts内容，
            2.3.1.1 合并ts为MP4
    2.4 下载完成后，continue继续
"""
import threading,time,os
import requests
from bs4 import BeautifulSoup
from concurrent.futures import ThreadPoolExecutor,as_completed

TEMP_DIR = "/tmp/" + int(time.time()*1000)
if not os.path.exists(TEMP_DIR):
    os.mkdir(TEMP_DIR)

def generator(url):
    import hashlib
    return hashlib.md5(url.encode()).hexdigest()

def download(url):
    return requests.get(url).content

def download_file(url,path = TEMP_DIR,ext = ".html",read = True):
    file = path + generator(url) + ext
    if os.path.exists(file):
        if read:
            return read(file)
    
    print("downloading ",url)
    content = download(url)
    with open(file,"wb") as f:
        f.write(content)
    return content,file

def read(file):
    with open(file,"rb") as f:
        return f.read()

def resolve_root_path(url):
    return url.split("/")[-1]

def resolve_real_url(url,path):
    if path.startswith("http"):
        return path
    if path.startswith("/"):
        return url.split("/")[0] + path
    return url.split("/")[0] + "/" + path

def resolve_video_home(url,e,p):
    content = download_file(url)
    content = content.decode("utf-8")
    soup = BeautifulSoup(content,"html.parser")
    doc = soup.find(e,p)
    videos = []
    for a in doc.find_all("a"):
        video = {'name':a.text, 'url':resolve_real_url(url,a.get("href"))}
        videos.append(video)
        print(video)

def resolve_m3u8(videos,p,f):
    import re,json
    pattern=re.compile(pattern,re.MULTILINE|re.DOTALL)
    for video in videos:
        url = video['url']
        content = download(url).decode("utf-8")
        soup = BeautifulSoup(content,"html.parser")
        script=soup.find("script",string=pattern)
        data_str=pattern.search(script.string).group(1)
        if data_str[-1:] != "}":
            data_str = data_str[:-1]
        data_json= json.loads(data_str,strict=False)
        if f:
            data_json['url'] = f(data_json['url'])
        content = download(url).decode("utf-8")
        for line in content.split("\n"):
            if not line == "" and not line.startswith("#"):
                url = resolve_real_url(url,line)
                video['m3u8'] = url

def resolve_m3u8_content(videos):
    for video in videos:
        url = video['m3u8']
        if not url:
            raise Exception("warn missing must m3u8 address!",video['name'])
    
        content = download_file(url,ext = ".m3u8")
        content = content.decode("utf-8")
        ts = [],duration = 0
        for line in content.split("\n"):
            if line == "":
                continue

            if not line.startswith("#"):
                ts.append(resolve_real_url(url,line))
                continue

            if line.startswith("#EXT-X-KEY"):
                line = line.replace("#EXT-X-KEY:","")
                line = line.split(",")
                method = line[0].split("=")[1]
                if method != "NONE":
                    key = line[1].split("=")[1].replace('"',"")
                    key = resolve_real_url(url,key)
                    video['key']=key
                continue

            if line.startswith("#EXTINF"):
                line = line.replace("#EXTINF:","").replace(",","")
                duration += float(line)

        video['ts'] = ts
        video['duration'] = duration

def download_ts(videos):
    for video in videos:
        ts = video['ts']
        path = TEMP_DIR + generator(video['url']) + "/" 
        video['path'] = path
        fetures = []
        with ThreadPoolExecutor() as p:
            for t in ts:
                fetures.append(p.submit(download_file,t,path,".ts",False))
        as_completed(fetures)

from Crypto.Cipher import AES
def aes(key,data):
    aes = AES.new(key,AES.MODE_CBC,b"0000000000000000")
    return aes.decrypt(data)

def decode_ts(ts,path):
    filename = path + generator(ts)
    with open(filename + ".ts","rb") as f:
        data = f.read()
    
    key = video['key']
    key = download(key)
    data = aes(video['key'],data)
    with open(filename + ".dongle.ts","wb") as f:
        f.write(data)

def decode_ts(video):
    features = [] 
    with ThreadPoolExecutor() as p:
        for ts in video['ts']:
            features.append(p.submit(decode_ts,ts,video['path']))
    as_completed(features)

def generator_file_list(video):
    file = video['path'] + generator(video['url']) + ".txt"
    video['file'] = file
    if not os.path.exists(file):
        with open(file, 'rb') as f:
            for ts in video['ts']:
                f.write(f"file {path + generator(ts)}.dongle.ts\n")

def merge(video, path):
    import subprocess
    p = subprocess.Popen(f"ffmpeg -f concat -safe 0 -i {video['file']} -c copy {path}/{video['name']}.mp4", shell=True)
    code = p.wait()
    if code == 0:
        print(f"{video['name']} start at {start}")
    else:
        raise Exception(f"merge video {video['name']} failed")



def async_handler(video, path):
    start = time.time()
    key = video['key']
    if key:
        decode_ts(video)
    generator_file_list(video)
    merge(video, path)
    
    
if __name__ == '__main__':
    video_name = "东邻西舍第四季"
    url = 'https://www.ijujitv.cc/detail/30697.html'
    videos_list = resolve_video_home(url,"div",{"class":""}) # 获得视频列表
    path = "/data/temp/video/" + video_name + "/"
    for video in videos_list:
        start = time.time()
        video['start'] = start
        resolve_m3u8(video,"var player_aaaa=(.*?)$") # 获得真是m3u8地址
        resolve_m3u8_content(video) # 获得ts列表
        download_ts(video) # 下载ts内容
        print("download %s is complete,use time:%ds" % (video['name'],(time.time() - start)))
        threading.Thread(target=async_handler, args=(video,path)).start() # 异步执行后续操作，解密，合并