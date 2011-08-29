set JAVA_HOME=E:\IBM\WebSphere\AppServer\java
set TOOLS_HOME=E:\IBM\webseal-log-tool
set WEBSEAL_REQUEST_LOG_DIR=E:\IBM\Tivoli\PDWeb\www-default\log

# CLASSPATH
set CLASS_PATH=%TOOLS_HOME%\config;%TOOLS_HOME%\lib\commons-beanutils-1.8.3.jar;%TOOLS_HOME%\lib\commons-httpclient-3.1.jar;%TOOLS_HOME%\lib\commons-logging-1.1.1.jar;%TOOLS_HOME%\lib\webseal-log-fetcher-assembly-1.0.0-SNAPSHOT.jar;%TOOLS_HOME%\lib\xpp3_min-1.1.4c.jar;%TOOLS_HOME%\lib\commons-codec-1.2.jar;%TOOLS_HOME%\lib\commons-lang-2.5.jar;%TOOLS_HOME%\lib\log4j-1.2.16.jar;%TOOLS_HOME%\lib\webseal.log.fetcher.common-1.0.0-SNAPSHOT.jar;%TOOLS_HOME%\lib\xstream-1.3.1.jar

%JAVA_HOME%\bin\java -Dibm.stream.nio=true -Djava.io.tmpdir=%TOOLS_HOME%\tmp -Dwebseal.tools.home=%TOOLS_HOME% -cp %CLASS_PATH% com.ibm.tivoli.cars.fetcher.Launcher -c %TOOLS_HOME%\config\wblog.config.properties -d %WEBSEAL_REQUEST_LOG_DIR% request.log.*
