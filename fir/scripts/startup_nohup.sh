#! /bin/sh

server_dir=`pwd`
mkdir -p $server_dir/logs

CONFIG_FILE=$1

if [ -z $CONFIG_FILE ]
 then
  CONFIG_FILE=$server_dir/../config/config.xml
fi

nohup $server_dir/startup.sh $server_dir $CONFIG_FILE  &