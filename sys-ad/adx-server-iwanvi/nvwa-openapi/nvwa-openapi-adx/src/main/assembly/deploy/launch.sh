#!/bin/bash -ilex

app=nvwa-openapi-adx
cd /data/apps/nvwa/$app

ARTIFACT_FILE="$app-%ENV%.tar.gz"

if [ ! -f $ARTIFACT_FILE ];then
    echo "$ARTIFACT_FILE not exist"
    exit 1     
fi

tar -xzf $ARTIFACT_FILE

cd $app-%ENV%/bin

bash -ex run.sh