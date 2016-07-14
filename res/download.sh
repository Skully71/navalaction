#!/bin/sh
set -e -x
URL=http://storage.googleapis.com/nacleanopenworldprodshards/
SHARD=cleanopenworldprodeu1
DATE=`date +"%Y%m%d"`
echo $DATE
mkdir $DATE || true
for i in ItemTemplates Nations Ports Shops
do
	echo $i
	curl $URL$i\_$SHARD.json -o $DATE/$i\_$SHARD.json
done
