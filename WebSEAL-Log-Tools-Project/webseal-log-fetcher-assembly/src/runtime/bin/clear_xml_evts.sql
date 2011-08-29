connect to eventxml user db2admin using SGMsysadmin001;
select count(*) from cei_t_xml00;
delete from cei_t_xml00 where (TIMESTAMP('1970-01-01','00.00.00') + (creation_time_utc/1000) seconds) + 3 days < current_timestamp;
select count(*) from cei_t_xml00;
