import requests
import queue,datetime,threading,os

url = ""
DIR = r""
source_file = DIR + "list.txt"
target_file = DIR + "video.mp4"
if os.path.exists(DIR) is False:
    print("Please create a download directory first!")
    exit(-1)
isAes = False
def handlerPrefix(url): # 解析前缀
    return "".join(url[:url.rfind("/")+1])

def resolveM3u8(url,suffix=None,prefix=None): # 解析m3u8 获取ts列表
    if prefix is None:
        prefix = handlerPrefix(url)
    
    content = requests.get(url).text
    list=[]
    for line in content.split("\n"):
        if line.startswith("#EXT-X-KEY"):
            # 加密处理
            isAes = True
            print("视频已加密，暂时无法处理！")
            return None
        if line.startswith("#") or line == '':
            continue
        if suffix:
            if suffix.startswith("?"): # 输入参数时代表已了解url结构
                line = line + suffix
            elif line.__contains__("?"): # 未避免参数值内？
                line = line + "&" + suffix
        list.append({'url':prefix+line})
    return list

def generatorListFile(list,filename): # 格式化内容
    with open(filename, "w") as f:
        for i in range(len(list)):
            file = "%d.ts" % i
            list[i]['filename'] = file
            f.write("file " + DIR + file + "\n")

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
            downTs(url,DIR + filename)
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
        os.system(command)
        print("video merge is complate!")
    except Exception as ex:
        print("video merge is error!")
        raise ex

def delTsFile(): # 删除无用文件
    for root, dirs, files in os.walk(DIR):
        for file in files:
            if file.endswith('.ts'):
                os.remove(os.path.join(root, file))

def repair(list): # 修复使用，下载缺失片段
    for item in list:
        url = item['url']
        filename = item['filename']
        if os.path.exists(DIR + filename):
            continue
        try:
            downTs(url,DIR + filename)
            print("download %s is success[%s]" % (url,filename))
        except Exception as e:
            print("[ERROR][DOWNLOAD]%s download is error!" % filename)


if __name__ == "__main__":
    start = datetime.datetime.now()
    list = resolveM3u8(url)
    if list:
        print("ready down ts count[%d]" % len(list))
        generatorListFile(list,source_file)
        # repair(list)
        qe = convertQueue(list)
        downByThread(down,qe)
        merge(source_file,target_file)
        delTsFile()
    print("use time[%s]!" % str(datetime.datetime.now() - start))