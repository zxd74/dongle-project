#!/bin/bash

JAVA_OPTS="-server -Xms512m -Xmx512m -Xmn128m -XX:MaxPermSize=256m \
-Xss300k -XX:+UseConcMarkSweepGC \
-XX:+UseParNewGC -XX:CMSFullGCsBeforeCompaction=5 \
-XX:+UseCMSCompactAtFullCollection \
-XX:+PrintGC -Xloggc:/data/logs/apps/nvwa-zwww/nvwa-auth/gc_$1.log"

PHOME=$(dirname `readlink -f "$0"`)
PHOME=$(dirname $PHOME)

pid=`ps -ef | grep java | grep nvwa-auth | awk '{print $2}'`

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

java -Dmodule=$1 $JAVA_OPTS -cp $PHOME/conf:$PHOME/lib/* com.iwanvi.nvwa.auth.AuthApplication > /dev/null 2>&1 &
