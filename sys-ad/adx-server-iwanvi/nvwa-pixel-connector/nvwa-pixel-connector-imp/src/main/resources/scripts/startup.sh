#!/bin/bash

JMX_PORT=`expr $1 + 500`

#JAVA_OPTS="-server -Xms512m -Xmx512m -Xmn192m -XX:MaxPermSize=128m \
JAVA_OPTS="-server -Xms1024m -Xmx1024m -Xmn384m -XX:MaxPermSize=128m \
-Xss256k -XX:+UseConcMarkSweepGC \
-XX:+UseParNewGC -XX:CMSFullGCsBeforeCompaction=5 \
-XX:+UseCMSCompactAtFullCollection \
-XX:+PrintGC -Xloggc:/data/logs/apps/nvwa/iwanvi-nvwa-pixel-connector-imp/gc_$1.log \
-Dfile.encoding=UTF8 -Duser.timezone=GMT+08"

#-Djava.rmi.server.hostname= \
#-Dcom.sun.management.jmxremote \
#-Dcom.sun.management.jmxremote.port=$JMX_PORT \
#-Dcom.sun.management.jmxremote.authenticate=false \
#-Dcom.sun.management.jmxremote.ssl=false"

#JAVA_OPTS="-server -Xms512m -Xmx512m -Xmn192m -XX:MaxPermSize=128m \

PHOME=$(dirname `readlink -f "$0"`)
PHOME=$(dirname $PHOME)

pid=`ps -eo pid,args | grep nvwa-pixel-connector-imp | grep $1 | grep java | grep -v grep | awk '{print $1}'`

if [ -n "$pid" ]
then
    kill -3 ${pid}
    kill ${pid} && sleep 3
    if [  -n "`ps -eo pid | grep $pid`" ]
    then
        kill -9 ${pid}
    fi
    echo "kill pid: ${pid}"
fi

java -Dmodule=$1 $JAVA_OPTS -cp $PHOME/conf:$PHOME/lib/* com.iwanvi.nvwa.pixel.connector.imp.bootstrap.HttpServer $1 > /dev/null 2>&1 &
