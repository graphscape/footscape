#!/bin/sh
##
#
#
#
#
#
#Resolve FS HOME
if [ -z "$FS_HOME" ] ; then

    ## resolve links - $0 
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
#Daemon class
CLASS=com.fs.integrated.Server
#
#CMD 
#

#Class path
CLASS_PATH="$FS_HOME/bin/*":"$FS_HOME/lib/*":"$FS_HOME/conf"

OPTS="-cp $CLASS_PATH -Dfs.home=$FS_HOME -Dfs.log.level=$FS_LOG_LEVEL"
##
#
##
##
##
  
$JAVA_HOME/bin/java $OPTS $CLASS
