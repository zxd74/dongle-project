#!/bin/bash -ilex

PARAM=$1
INDEX=0
if [[ -z "$1" ]]; then
    PARAM=13321
fi

SCRIPT_HOME=$(dirname $(readlink -f $0))
for i in $(seq 0 $INDEX)
do
    PORT=`expr $PARAM + $i`
    bash -ilex $SCRIPT_HOME/startup.sh $PORT
done
