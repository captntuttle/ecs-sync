#!/bin/sh
################
# This is a sample shell script to run the EcsSync tool as a stand-alone
# instance to run a single job configured via XML
################

# specify any external jars here
#EXT_JARS=/some_other_dir/sqljdbc4.jar

CLASSPATH=.:./*
if [ -n "${EXT_JARS}" ]
then
  CLASSPATH=${CLASSPATH}:${EXT_JARS}
fi

if [ -z "$1" ]
then
  echo "This script is for running a stand-alone ecs-sync job outside of a service"
  echo "To submit a job to the running service, use ecs-sync-ctl"
  echo usage:
  echo "    $0 <config-xml-file>"
  exit 1
fi

java -classpath "${CLASSPATH}" com.emc.ecs.sync.EcsSync --xml-config "$1"
exit $?
