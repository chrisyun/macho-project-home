#!/bin/sh
source /opt/IBM/webseal-log-tool/bin/setenv.sh

nohup $TOOLS_HOME/bin/grepAndSubmit.sh >> $TOOLS_HOME/logs/start.log 2>&1 &

