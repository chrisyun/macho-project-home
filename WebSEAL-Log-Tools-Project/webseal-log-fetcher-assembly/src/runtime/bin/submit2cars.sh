#!/bin/sh
source /opt/IBM/webseal-log-tool/bin/setenv.sh

$JAVA_HOME/bin/java -Dibm.stream.nio=true -Djava.io.tmpdir=$TOOLS_HOME/tmp -Dwebseal.tools.home=$TOOLS_HOME -cp $CLASS_PATH com.ibm.tivoli.cars.fetcher.Launcher -c $TOOLS_HOME/config/wblog.config.properties -d $WEBSEAL_REQUEST_LOG_DIR request.log.*
