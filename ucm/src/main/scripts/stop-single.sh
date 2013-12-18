#! /bin/sh

source /etc/profile
jps -l| grep fengfei.ucm.server.ServerMain | grep -v grep|cut -c 1-5 | xargs kill
echo server stoped.
