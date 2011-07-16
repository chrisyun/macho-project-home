#!/bin/sh
export ICBC_PROBE_HOME=/home/zhao/icbc-probe

export JAVA_OPTS=-Dicbc.probe.home=$ICBC_PROBE_HOME
export CATALINA_HOME=$ICBC_PROBE_HOME
$ICBC_PROBE_HOME/bin/catalina.sh $*

