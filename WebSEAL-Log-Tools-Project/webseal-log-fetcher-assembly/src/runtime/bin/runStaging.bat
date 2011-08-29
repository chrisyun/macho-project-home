set CARS_HOME=E:\IBM\tivoli\CommonAuditService
set DB2_HOME=E:\IBM\SQLLIB
set cars_classpath="%CARS_HOME%\server\etc;%CARS_HOME%\server\lib\ibmcars.jar;%DB2_HOME%\java\db2jcc.jar;%DB2_HOME%\java\db2jcc_license_cu.jar;%DB2_HOME%\java\Common.jar"
REM Staging XML data into Report DB
REM   java -cp $cars_classpath com.ibm.cars.staging.Staging -mode historical -starttime "Sep 1, 2010 00:00:00 AM GMT" -endtime "Jan 1, 2020 00:00:00 AM GMT"
java -cp %cars_classpath% com.ibm.cars.staging.Staging -mode incremental -dbpassword SGMsysadmin001
E:\IBM\SQLLIB\BIN\db2cmd db2 -tf E:\IBM\webseal-log-tool\bin\cleanEvtDB.sql
