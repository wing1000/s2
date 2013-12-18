#! /bin/bash
PRG="$BASH_SOURCE"
progname=`basename "$BASH_SOURCE"`
nohup_log="../server_nohup.log"

while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`"/$link"
    fi
done

bin=$(dirname "$PRG")
CONFIG_FILE=$rs_bin/../config/config.xml

case "$1" in
  'start')
    
    nohup $rs_bin/start-single.sh $rs_bin $CONFIG_FILE > $nohup_log 2>&1 &
    echo "start finish"
     ;;

  'stop')
    ps -ef| grep fengfei.ucm.server.ServerMain|grep java|awk '{print $2}' | xargs kill
    echo "rs-server stopped."
    ;;

  'info')
    ps -ef| grep fengfei.ucm.server.ServerMain|grep java
    ;;

  'restart')
    ps -ef| grep fengfei.ucm.server.ServerMain|grep java|awk '{print $2}' | xargs kill
    echo "rs-server stopped."

    nohup $bin/start-single.sh $bin $CONFIG_FILE > $nohup_log  2>&1 &
    echo "rs-server restart."
    ;;

  *)
    basename=`basename "$0"`
    echo "Usage: $basename  {start|stop|restart}  [  server options ]"
    exit 1
    ;;
esac

