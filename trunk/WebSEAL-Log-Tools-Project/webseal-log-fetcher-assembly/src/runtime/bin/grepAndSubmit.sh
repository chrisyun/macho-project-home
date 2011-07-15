#!/bin/sh
source /opt/IBM/webseal-log-tool/bin/setenv.sh

tail --follow=name $WEBSEAL_REQUEST_LOG_DIR/request.log | $TOOLS_HOME/bin/submit2cars.sh
