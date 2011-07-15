#!/bin/sh
JAVA_HOME=/opt/ibm/java2-i386-50
TOOLS_HOME=/opt/IBM/webseal-log-tool
WEBSEAL_REQUEST_LOG_DIR=/var/ibm/tivoli/common/DPW/logs/www-tkws1/log

# CLASSPATH
CLASS_PATH=$TOOLS_HOME/config:$TOOLS_HOME/lib/commons-beanutils-1.8.3.jar:$TOOLS_HOME/lib/commons-httpclient-3.1.jar:$TOOLS_HOME/lib/commons-logging-1.1.1.jar:$TOOLS_HOME/lib/webseal-log-fetcher-assembly-1.0.0-SNAPSHOT.jar:$TOOLS_HOME/lib/xpp3_min-1.1.4c.jar:$TOOLS_HOME/lib/commons-codec-1.2.jar:$TOOLS_HOME/lib/commons-lang-2.5.jar:$TOOLS_HOME/lib/log4j-1.2.16.jar:$TOOLS_HOME/lib/webseal.log.fetcher.common-1.0.0-SNAPSHOT.jar:$TOOLS_HOME/lib/xstream-1.3.1.jar
