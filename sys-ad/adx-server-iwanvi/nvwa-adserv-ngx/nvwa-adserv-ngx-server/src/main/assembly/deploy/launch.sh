#!/bin/bash -ilex

cd /data/apps/nvwa/nvwa-adserv-ngx-server

ARTIFACT_FILE="nvwa-adserv-ngx-server-%ENV%.tar.gz"

if [ ! -f $ARTIFACT_FILE ];then
    echo "$ARTIFACT_FILE not exist"
    exit 1     
fi

tar -xzf $ARTIFACT_FILE

cd nvwa-adserv-ngx-server-%ENV%/bin

bash -ex run.sh