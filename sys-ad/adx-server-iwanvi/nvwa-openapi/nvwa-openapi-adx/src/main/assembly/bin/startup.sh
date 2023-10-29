#!/bin/bash

export MALLOC_ARENA_MAX=1
JMX_PORT=`expr $1 + 500`

PHOME=$(dirname `readlink -f "$0"`)
PHOME=$(dirname $PHOME)

JAVA_OPTS="-server -Xms1024m -Xmx1024m -Xmn384m -Dmodule=$1 -Dsun.rmi.dgc.server.gcInterval=3600000 \
-Dsun.rmi.dgc.client.gcInterval=3600000 \
-Xss256k -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:$PHOME/gc.log \
-XX:+UseParNewGC -XX:CMSFullGCsBeforeCompaction=5 -XX:CMSInitiatingOccupancyFraction=75 \
-XX:+UseCMSCompactAtFullCollection -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$PHOME/"

pid=`ps -eo pid,args | grep $1 | grep java | grep -v grep | awk '{print $1}'`

if [ -n "$pid" ]
then
    kill -3 ${pid}
    kill ${pid} && sleep 5
    if [  -n "`ps -eo pid | grep $pid`" ]
    then
    	echo "force killed application"
        kill -9 ${pid}
    fi
    echo "kill pid: ${pid}"
fi

java $JAVA_OPTS -cp $PHOME/conf:$PHOME/lib/* com.iwanvi.nvwa.openapi.adx.Application $1 > /dev/null 2>&1 &