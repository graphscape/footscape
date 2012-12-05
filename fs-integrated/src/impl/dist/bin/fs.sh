#!/bin/sh
##
#
#
#
#
#
#Resolve FS HOME
if [ -z "$FS_HOME" ] ; then

    ## resolve links - $0 may be a link to activemq's home
    PRG="$0"
    progname=`basename "$0"`
    saveddir=`pwd`

    # need this for relative symlinks
    dirname_prg=`dirname "$PRG"`
    cd "$dirname_prg"

    while [ -h "$PRG" ] ; do
      ls=`ls -ld "$PRG"`
      link=`expr "$ls" : '.*-> \(.*\)$'`
      if expr "$link" : '.*/.*' > /dev/null; then
      PRG="$link"
      else
      PRG=`dirname "$PRG"`"/$link"
      fi
    done

    FS_HOME=`dirname "$PRG"`/..

    cd "$saveddir"

    # make it fully qualified
FS_HOME=`cd "$FS_HOME" && pwd`
fi

echo 'FS_HOME:' $FS_HOME

FS_LOGS=$FS_HOME/logs
#Version
FS_VERSION=1.0.0
FS_LOG_LEVEL=INFO
#JAVA_HOME
JAVA_HOME=$JAVA_HOME
#Daemon class
CLASS=com.fs.integrated.daemon.Main
#
#CMD 
#
EXEC=/usr/bin/jsvc
#Class path
CLASS_PATH="$FS_HOME/bin/*":"$FS_HOME/lib/*":"$FS_HOME/conf"
#user
CUSER=`whoami 2>/dev/null`
USER=$CUSER
USER=wu
#Pid file
PID=${FS_LOGS}/fs.pid
#out
LOG_OUT=${FS_LOGS}/fs.out
#err
LOG_ERR=${FS_LOGS}/fs.err

OPTS_1="-home "$JAVA_HOME" -cp $CLASS_PATH -user $USER -outfile $LOG_OUT -errfile $LOG_ERR -pidfile $PID"
OPTS_2="-Dfs.home=$FS_HOME -Dfs.log.level=$FS_LOG_LEVEL"
OPTS_3="-debug -keepstdin"
OPTS_ALL="$OPTS_1 $OPTS_2 $OPTS_3"
##
#
##
##
#Start,stop
##
do_exec()
{
  
$EXEC $OPTS_ALL $1 $CLASS

}

case "$1" in
    start)
        do_exec
            ;;
    stop)
        do_exec "-stop"
            ;;
    restart)
        if [ -f "$PID" ]; then
            do_exec "-stop"
            do_exec
        else
            echo "service not running, will do nothing"
            exit 1
        fi
            ;;
    *)
            echo "usage: daemon {start|stop|restart}" >&2
            exit 3
            ;;
esac
