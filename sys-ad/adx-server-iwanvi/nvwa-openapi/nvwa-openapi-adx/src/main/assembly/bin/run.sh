#!/bin/bash

PARAM=$1

INDEX=0
if [[ -z "$1" ]]; then
    PARAM=adx-openapi-serv
fi

SCRIPT_HOME=$(dirname $(readlink -f $0))
for i in $(seq 0 $INDEX)
do
    MODULE="$PARAM-$i"
    sh $SCRIPT_HOME/startup.sh $MODULE
done
