#!/bin/sh
source /opt/IBM/webseal-log-tool/bin/setenv.sh

$JAVA_HOME/bin/java -Djava.io.tmpdir=$TOOLS_HOME/tmp -Dwebseal.tools.home=$TOOLS_HOME -cp $CLASS_PATH com.ibm.tivoli.cars.fetcher.Launcher -c $TOOLS_HOME/config/wblog.config.properties
