#!/bin/sh
export CARS_HOME=/opt/IBM/Tivoli/CommonAuditService
export DB2_HOME=/opt/ibm/db2/V9.1
export DB2INSTANCE_OWNER=/home/db2inst1
export cars_classpath=$CARS_HOME/server/etc:$CARS_HOME/server/lib/ibmcars.jar:$DB2_HOME/java/db2jcc.jar:$DB2_HOME/java/db2jcc_license_cu.jar:$DB2INSTANCE_OWNER/sqllib/java/db2java.zip:$DB2INSTANCE_OWNER/sqllib/java/db2jcc.jar:$DB2INSTANCE_OWNER/sqllib/function:$DB2INSTANCE_OWNER/sqllib/java/db2jcc_license_cu.jar
# Staging XML data into Report DB
if [ "X$1" = "Xhistorical" ]; then
   banner "Historical Mode"
   banner `date +"%Y%m%d.%H%M%S"`
   java -cp $cars_classpath com.ibm.cars.staging.Staging -mode historical -starttime "Sep 1, 2010 00:00:00 AM GMT" -endtime "Jan 1, 2020 00:00:00 AM GMT"
   exit $?
fi
if [ "X$1" = "Xincremental" ]; then
   banner "Incremental Mode"
   banner `date +"%Y%m%d.%H%M%S"`
   java -cp $cars_classpath com.ibm.cars.staging.Staging -mode incremental
   exit $?
fi
if [ "X$1" = "Xprune" ]; then
   banner "Prune Mode"
   banner `date +"%Y%m%d.%H%M%S"`
   java -cp $cars_classpath com.ibm.cars.staging.Staging -mode prune -prunetime "Sep 1, 2000 00:00:00 AM GMT"
   exit $?
fi
echo "Usage runstaging.sh historical|incremental|prune"