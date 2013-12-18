#! /bin/sh

source /etc/profile

SERVER_CLASSPATH=$CLASSPATH
CD=$1
CONFIG_FILE=$2

#echo $CD "," $CONFIG_FILE "\n"

if [ -z $CD ]
 then
 CD=`pwd`
fi

if [ -z $CONFIG_FILE ]
 then
 CONFIG_FILE=$CD/../config/config.xml
fi

echo "Reading config " $CD "," $CONFIG_FILE "\n"
SERVER_CLASSPATH=.:$CD/../config:$CD/../lib/*


#for fn in `ls $CD/../config/*`
#do
#	SERVER_CLASSPATH=$SERVER_CLASSPATH:$fn
#done


SERVER_CLASSPATH=$CD/../config:$SERVER_CLASSPATH
#echo $SERVER_CLASSPATH
echo Starting server...


#:<<BLOCK'
#-server
#-Xmx4000M
#-Xms4000M
#-Xmn600M
#-XX:PermSize= 512M
#-XX:MaxPermSize= 512M
#-Xss256K
#-XX:+DisableExplicitGC
#-XX:SurvivorRatio=1
#-XX:+UseConcMarkSweepGC
#-XX:+UseParNewGC
#-XX:+CMSParallelRemarkEnabled
#-XX:+UseCMSCompactAtFullCollection
#-XX:CMSFullGCsBeforeCompaction=0
#-XX:+CMSClassUnloadingEnabled
#-XX:LargePageSizeInBytes=128M
#-XX:+UseFastAccessorMethods
#-XX:+UseCMSInitiatingOccupancyOnly
#-XX:CMSInitiatingOccupancyFraction=70
#-XX:SoftRefLRUPolicyMSPerMB=0
#
#(Xmx-Xmn)*(100-CMSInitiatingOccupancyFraction)/100>=Xmn
#
#'BLOCK

JAVA_OPTS="-Xmx4000M -Xms4000M -Xmn600M -XX:PermSize=500M -XX:MaxPermSize=500M -Xss256K -XX:+DisableExplicitGC -XX:SurvivorRatio=1 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=0 -XX:+CMSClassUnloadingEnabled -XX:LargePageSizeInBytes=128M -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:SoftRefLRUPolicyMSPerMB=0 "  
# for log:JAVA_OPTS="-Xmx4000M -Xms4000M -Xmn600M -XX:PermSize=500M -XX:MaxPermSize=500M -Xss256K -XX:+DisableExplicitGC -XX:SurvivorRatio=1 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=0 -XX:+CMSClassUnloadingEnabled -XX:LargePageSizeInBytes=128M -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:SoftRefLRUPolicyMSPerMB=0 -XX:+PrintClassHistogram -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC -Xloggc:log/gc.log"  
#JAVA_OPTS="-Xms2048m -Xmx2048m -XX:NewSize=256m -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -server"
#java $JAVA_OPTS -classpath $SERVER_CLASSPATH -Dname=server fengfei.ucm.server.ServerMain
java $JAVA_OPTS -cp $SERVER_CLASSPATH fengfei.ucm.server.ServerMain $CONFIG_FILE
#JAVA_JMX_OPTS="-Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.port=4901 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false $JAVA_OPTS"
#java $JAVA_JMX_OPTS -classpath $RSCLASSPATH -Dname=server engfei.ucm.server.ServerMain $CONFIG_FILE

#echo server started.
