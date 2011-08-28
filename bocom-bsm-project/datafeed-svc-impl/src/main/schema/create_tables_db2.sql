--==============================================================
-- DBMS name:      IBM DB2 UDB 9.x Common Server
-- Created on:     2011/8/28 19:28:35
--==============================================================


drop trigger tib_app_raw_data
/

drop trigger tsq_app_raw_data
/

drop table app_raw_data
/

drop sequence SEQ_APP_RAW_DID
/

create sequence SEQ_APP_RAW_DID
increment by 1
start with 100000
/

--==============================================================
-- Table: app_raw_data
--==============================================================
create table app_raw_data
(
   id                 BIGINT                 not null,
   dateslot           TIMESTAMP,
   timeslot           TIMESTAMP,
   ip                 VARCHAR(64),
   metric_desc        VARCHAR(128),
   value              DOUBLE,
   type               VARCHAR(128),
   montype            VARCHAR(128),
   constraint P_APP_R_D_KEY primary key (id)
)
/


create trigger tib_app_raw_data no cascade before insert
on app_raw_data referencing new as new_ins for each row mode db2sql
when (
     (0=1)
     )
begin atomic
   signal sqlstate '70001' ('Cannot create child in app_raw_data.');
end
/


create trigger tsq_app_raw_data no cascade before insert
on app_raw_data referencing new as new_ins for each row mode db2sql
id = next value for SEQ_APP_RAW_DID
/

