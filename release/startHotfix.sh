#!/bin/sh

cd /home/iogame/server/hotfix/
ps -ef|grep hotswap-update.jar|grep -v grep|awk '{print $2}'|xargs kill -9
nohup java -jar hotswap-update.jar > server.out 2>server.error.out &
tail -100f server.out
