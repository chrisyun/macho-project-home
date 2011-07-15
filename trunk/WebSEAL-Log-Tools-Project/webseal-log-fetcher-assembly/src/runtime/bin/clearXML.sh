#!/bin/sh
DB_ALIAS=eventxml
DB_USER=db2inst1
DB_PASSWORD=P@ssw0rd

. /home/$DB_USER/sqllib/db2profile
db2 "connect to $DB_ALIAS user $DB_USER USING $DB_PASSWORD"
db2 "select count(*) from DB2INST1.CEI_T_XML00 where CREATION_TIME_UTC<(select max(CREATION_TIME_UTC) from DB2INST1.CEI_T_XML00) - 7 * 24 * 3600 * 1000"
# Delete old than 1 week 
db2 "delete from DB2INST1.CEI_T_XML00 where CREATION_TIME_UTC<(select max(CREATION_TIME_UTC) from DB2INST1.CEI_T_XML00) - 7 * 24 * 3600 * 1000"
db2 "commit"
db2 "select count(*) from DB2INST1.CARS_T_EVENT where month(current date) - month(TIME_STAMP)>6"
echo "Delete old than 6 months"
db2 "delete from DB2INST1.CARS_T_EVENT where month(current date) - month(TIME_STAMP)>6"
db2 "commit"