# 依赖
* [baostock](baostock.com):`pip install baostock`
* pymysql:`pip install pymysql`

# 功能
1. 获取股票信息
2. 获取股票交易日k线数据

# 部署
## 服务端
* 仅初次配置内容
  * 配置Dockerfile(后续无需处理)
```shell
# dongle-stock-web.Dockerfile
FROM dongle/java:1.8
WORKDIR /usr/local/src/
ADD stock/dongle-stock-service-1.0.0.jar ./
CMD ["java","-jar","dongle-stock-service-1.0.0.jar"]
EXPOSE 8888
```
  * nginx配置
```txt
upstream stock-service.dongle.com{
    server localhost:8888
}
server {
    listen       80;
    server_name  stock-service.dongle.com;

    charset utf-8;
    gzip on;

    access_log  /data/nginx/logs/stock-service.dongle.com.access.log  main;
    error_log  /data/nginx/logs/stock-service.dongle.com.error.log;

    # cros处理
    add_header Access-Control-Allow-Origin *; 
    add_header Access-Control-Allow-Credentials true;
    add_header Access-Control-Allow-Headers X-Requested-With;
    add_header Access-Control-Allow-Methods GET,POST,OPTIONS;

    location / {
        proxy_pass   http://stock-service;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }

    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   html;
    }
}
```
* 打包java项目
```shell
mvn clean package & mvn install
```
* 将打包内容复制到dockerfile可操作位置，进行镜像生成
```shell
docker build . -t dongle/stock-service -f dongle-stock-service.Dockerfile
```
* 启动服务镜像
```shell
docker run -d --add-host=db.dongle.com:192.168.74.3 --name stock-service -p 8888:8888 dongle/stock-service
```
## Web
* 仅初次配置内容
  * 配置Dockerfile
```shell
# dongle-stock-web.Dockerfile
FROM nginx
RUN echo "Asia/Shanghai" > /etc/timezone
COPY stock/dist /usr/share/nginx/html/
COPY nginx/default.conf /etc/nginx/conf.d/default.conf
```
  * nginx配置
```txt
upstream stock-web{
    server localhost:8080
}
server {
    listen       80;
    server_name  stock-web.dongle.com;

    charset utf-8;
    gzip on;

    access_log  /data/nginx/logs/stock-web.dongle.com.access.log  main;
    error_log  /data/nginx/logs/stock-web.dongle.com.error.log;

    # cros处理
    add_header Access-Control-Allow-Origin *; 
    add_header Access-Control-Allow-Credentials true;
    add_header Access-Control-Allow-Headers X-Requested-With;
    add_header Access-Control-Allow-Methods GET,POST,OPTIONS;

    location / {
        proxy_pass   http://stock-web;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }

    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   html;
    }
}
```
* vue项目打包：`dist/`
```shell
npm install 
npm run build
```
* 将打包内容复制到dockerfile可操作位置，进行镜像生成
```shell
docker build . -t dongle/stock-web -f dongle-stock-web.Dockerfile
```
* 启动web镜像
```shell
docker stop stock-web & docker rm stock-web
docker run -d --name stock-web -p 8080:80 dongle/stock-web
```
## Compose
* 定制`docker-compose.yml`
```txt
services:
 dongle-stock-service:
  build:
   context: ../
   dockerfile: docker-df/dongle-stock-service.Dockerfile
  image: dongle/stock-service-demo
  container_name: stock-service-demo
  volumes:
   - /data/logs/:/data/logs
  ports:
   - 8888:8888
  extra_hosts:
   - "db.dongle.com:192.168.74.3"

 dongle-stock-web:
  build:
   context: ../
   dockerfile: docker-df/dongle-stock-web.Dockerfile
  image: dongle/stock-web-demo
  container_name: stock-web-demo
  volumes:
   - /data/docker/nginx/logs:/var/logs/nginx
  ports:
   - "8080:80"
```
* 构建镜像`docker compose build`
* 顺序创建并启动容器`docker compose up -d`