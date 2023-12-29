import requests
import queue,datetime,threading,os
import uuid

import sys

url = sys.argv[1] or ""
file_name = sys.argv[2] or "video"
file_num = sys.argv[3] or "01"

if url == "":
    raise Exception("Please input url!")

print("receive param: %s %s %s" % (url,file_name,file_num))

DIR = "/data/temp/video/" # 根目录
TARGET_DIR = DIR + file_name + "/" # 目标目录，mp4存放目录
if os.path.exists(TARGET_DIR) is False:
    os.makedirs(TARGET_DIR)
target_file = "%s%s-%s.mp4" % (TARGET_DIR,file_name,file_num)

import hashlib
identy = hashlib.md5(url.encode('utf-8')).hexdigest()
TMP_DIR = DIR  + identy +"/" # 临时目录
if os.path.exists(TMP_DIR) is False:
    os.makedirs(TMP_DIR)
source_file = TMP_DIR + identy + "-tmp.txt"
ts_file = TMP_DIR + identy + "-ts.txt"

def handlerPrefix(url): # 解析前缀
    return "".join(url[:url.rfind("/")+1])

def resovleUrlRootPath(url):
    import urllib
    '''
        获取URL 根路径
    '''
    urls = urllib.parse.urlparse(url)
    return urls.scheme + "://" + urls.netloc

def resolveM3u8(url,suffix=None,prefix=None): # 解析m3u8 获取ts列表
    list=[]
    if os.path.exists(ts_file):
        with open(ts_file,'r',encoding='utf-8') as f:
            content = f.read()
            for line in content.split("\n"):
                list.append({'url':line})
            return list
    if prefix is None:
        prefix = handlerPrefix(url)
    root = resovleUrlRootPath(url)
    content = requests.get(url).text
    isSkip = False
    for line in content.split("\n"):
        if line.startswith('#EXT-X-DISCONTINUITY'):
            isSkip = not isSkip
            continue

        if isSkip:
            continue

        if line.startswith("#EXT-X-KEY"):
            # 加密处理
            if not line.startswith("#EXT-X-KEY:METHOD=NONE"):
                print("视频已被加密，暂时无法处理！",line)
                return None
        if line.startswith("#") or line == '':
            continue
        
        if line.startswith('/'):
            line = root + line
        else:
            line = prefix + line
        if suffix:
            if suffix.startswith("?"): # 输入参数时代表已了解url结构
                line = line + suffix
            elif line.__contains__("?"): # 未避免参数值内？
                line = line + "&" + suffix
        list.append({'url':line})
    with open(ts_file,'w',encoding='utf-8') as f:
        for i in list:
            f.write(i['url'] + "\n")
    return list

def generatorListFile(list,filename): # 格式化内容
    if os.path.exists(file_name):
        os.remove(file_name)
    with open(filename, "w") as f:
        for i in range(len(list)):
            file = "%d.ts" % i
            list[i]['filename'] = file
            f.write("file " + TMP_DIR + file + "\n")

def convertQueue(list): # 绑定queue
    qe = queue.Queue()
    for item in list:
        qe.put(item)
    return qe

def downByThread(func,qe): # 多线程下载
    import threading
    from multiprocessing import cpu_count
    cpu = min(qe.qsize(),cpu_count())
    print("start thread count:" + str(cpu))

    threads=[] 
    for i in range(cpu):
        threads.append(threading.Thread(target=func,name="th-" + str(i),args=[qe]))  # kwargs={'qe':qe}

    for t in threads:
        t.start()
    for t in threads:
        t.join()

def downTs(url,filename): # 下载ts
    if os.path.exists(filename):
        print("file exists：%s" % filename)
        return
    try:
        r = requests.get(url,stream=True)
        with open(filename, 'wb') as f:
            for chunk in r.iter_content(chunk_size=1024):
                if chunk:
                    f.write(chunk)
    except Exception as e:
        raise e

def down(qe): # 执行下载逻辑
    thn = threading.current_thread().name
    while not qe.empty():
        start = datetime.datetime.now()
        ts = qe.get()
        url= ts['url']
        filename = ts['filename']
        try:
            downTs(url,TMP_DIR + filename)
            print("Thread[%s] download %s is success[%s],use time[%s]!" % (thn,url,filename,datetime.datetime.now() - start))
        except Exception as e:
            print("[ERROR][DOWNLOAD]%s download is error!" % filename)

def merge(list_file,filename): # 合并ts为MP4
    if os.path.exists(filename):
        os.remove(filename)
    #  -safe 0  防止Operation not permitted
    command = "ffmpeg -y -f concat -safe 0 -i %s -vcodec copy %s" % (list_file,filename)
    print("exec video merge system command:",command)
    try:
        # os.system(command)
        import subprocess
        p = subprocess.Popen(command, shell=True)
        print("video merge is complate!",p.wait())
    except Exception as ex:
        print("video merge is error!")
        raise ex

def delTsFile(): # 删除无用文件
    # os.delete(TMP_DIR)
    import shutil
    shutil.rmtree(TMP_DIR)
    # os.remove(TMP_DIR) # 无权删除，拒绝访问

def repair(list): # 修复使用，下载缺失片段
    for item in list:
        url = item['url']
        filename = item['filename']
        if os.path.exists(TMP_DIR + filename):
            continue
        try:
            downTs(url,TMP_DIR + filename)
            print("download %s is success[%s]" % (url,filename))
        except Exception as e:
            print("[ERROR][DOWNLOAD]%s download is error!" % filename)


if __name__ == "__main__":
    start = datetime.datetime.now()
    list = resolveM3u8(url)
    if list:
        print("ready down ts count[%d]" % len(list))
        generatorListFile(list,source_file)
        qe = convertQueue(list)
        downByThread(down,qe)
        print("--- download complete,ready merge ts file ---")
        merge(source_file,target_file)
        delTsFile()
    print("use time[%s]!" % str(datetime.datetime.now() - start))