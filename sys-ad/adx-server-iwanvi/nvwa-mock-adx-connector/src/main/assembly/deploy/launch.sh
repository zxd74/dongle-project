#!/bin/bash

cd /data/apps/nvwa-mock-adx-connector

ARTIFACT_FILE="nvwa-mock-adx-connector-%ENV%.tar.gz"

if [ ! -f $ARTIFACT_FILE ];then
    echo "$ARTIFACT_FILE not exist"
    exit 1     
fi

tar -xzf $ARTIFACT_FILE

cd nvwa-mock-adx-connector-%ENV%/bin

bash -ex run.sh