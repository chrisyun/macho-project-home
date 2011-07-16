set ICBC_PROBE_HOME=C:\icbc-probe
set JAVA_HOME=%ICBC_PROBE_HOME%\jdk1.6.0_12

set JAVA_OPTS=-Dicbc.probe.home=%ICBC_PROBE_HOME%
set CATALINA_HOME=%ICBC_PROBE_HOME%
%ICBC_PROBE_HOME%\bin\catalina %*

