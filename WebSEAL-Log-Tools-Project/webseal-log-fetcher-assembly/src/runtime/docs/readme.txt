1. Login as root, create directory for installtion:
   mkdir /opt/IBM/webseal-log-tool
   chown -R ivmgr.ivmgr /opt/IBM/webseal-log-tool/
   
2. Login as ivmgr, unzip software package:
   su - ivmgr
   cd /opt/IBM
   unzip /tmp/webseal-log-tool-1.0.0-SNAPSHOT-bin.zip 

3. Modify config, set CARS server parameters and WebSEAL instance parameters:
   /opt/IBM/webseal-log-tool/config/wblog.config.properties  
4. Modify WebSEAL request log path:
   /opt/IBM/webseal-log-tool/bin/setenv.sh  
