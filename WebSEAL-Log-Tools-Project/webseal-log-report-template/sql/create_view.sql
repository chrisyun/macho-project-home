DROP FUNCTION func_convert2chars;
CREATE  FUNCTION func_convert2chars (dd INTEGER)
RETURNS VARCHAR(2)
------------------------------------------------------------------------
-- FUNCTION£ºfunc_convert2chars
-- Description£ºconvert 0 to "00", 9 to "09"
------------------------------------------------------------------------
 NO EXTERNAL ACTION
 DETERMINISTIC
 LANGUAGE SQL
 CONTAINS SQL
 NULL CALL
 return case when (dd <= 9) then
   '0'||char(dd)
 else
   char(dd)
end;


-- Create VIEW v_cars_t_event_time 
DROP VIEW v_cars_t_event_time;
create view v_cars_t_event_time as
select
  e.cars_seq_number as cars_seq_number,
  year(time_stamp) as year, 
  month(time_stamp) as month,
  QUARTER(time_stamp) as quarter,
  day(time_stamp) as day_of_month,
  DAYOFWEEK(time_stamp) as day_of_week,
  WEEK(time_stamp) as week_of_year,
  hour(time_stamp) as hour,
  minute(time_stamp) as minute,
  second(time_stamp) as second,
  microsecond(time_stamp) as microsecond
from 
   cars_t_event e;
   
drop view    v_app_request_year;
create view v_app_request_year as
select
  year,
  RES_NAME_IN_PLCY as Application,
  src_instance_id as WebSEAL,
  access_dcn,
  action,
  count(*) as total
from 
   cars_t_event e inner join cars_t_res_access r on e.cars_seq_number=r.cars_seq_number
                  inner join v_cars_t_event_time t on e.cars_seq_number=t.cars_seq_number
where 
     e.eventtype='AUDIT_RESOURCE_ACCESS' and e.APP_USR_NAME<>'Unauth'
group by year, RES_NAME_IN_PLCY, src_instance_id, access_dcn, action;

drop view v_app_request_month;
create view v_app_request_month as
select
  year,
  month,
  RES_NAME_IN_PLCY as Application,
  src_instance_id as WebSEAL,
  access_dcn,
  action,
  count(*) as total
from 
   cars_t_event e inner join cars_t_res_access r on e.cars_seq_number=r.cars_seq_number
                  inner join v_cars_t_event_time t on e.cars_seq_number=t.cars_seq_number
where 
     e.eventtype='AUDIT_RESOURCE_ACCESS' and e.APP_USR_NAME<>'Unauth'
group by year, month, RES_NAME_IN_PLCY, src_instance_id, access_dcn, action;

drop view v_app_request_day_of_month;
create view v_app_request_day_of_month as
select
  year,
  month,
  day_of_month,
  RES_NAME_IN_PLCY as Application,
  src_instance_id as WebSEAL,
  access_dcn,
  action,
  count(*) as total
from 
   cars_t_event e inner join cars_t_res_access r on e.cars_seq_number=r.cars_seq_number
                  inner join v_cars_t_event_time t on e.cars_seq_number=t.cars_seq_number
where 
     e.eventtype='AUDIT_RESOURCE_ACCESS' and e.APP_USR_NAME<>'Unauth'
group by year, month, day_of_month, RES_NAME_IN_PLCY, src_instance_id, access_dcn, action;

drop view v_app_request_hour;
create view v_app_request_hour as
select
  year,
  month,
  day_of_month,
  hour,
  RES_NAME_IN_PLCY as Application,
  src_instance_id as WebSEAL,
  access_dcn,
  action,
  count(*) as total
from 
   cars_t_event e inner join cars_t_res_access r on e.cars_seq_number=r.cars_seq_number
                  inner join v_cars_t_event_time t on e.cars_seq_number=t.cars_seq_number
where 
     e.eventtype='AUDIT_RESOURCE_ACCESS' and e.APP_USR_NAME<>'Unauth'
group by year, month, day_of_month, hour, RES_NAME_IN_PLCY, src_instance_id, access_dcn, action;

--------------------------------------------------------------------------------------------------
-- 
drop view v_app_request_hour_with_history;
create view v_app_request_hour_with_history as
select
  a.*,
  (select max(total) from v_app_request_hour v where a.hour=v.hour and a.Application=v.Application and a.WebSEAL=v.WebSEAL and a.access_dcn=v.access_dcn)
   as max_total,
  (select min(total) from v_app_request_hour v where a.hour=v.hour and a.Application=v.Application and a.WebSEAL=v.WebSEAL and a.access_dcn=v.access_dcn)
  as min_total,
  (select avg(total) from v_app_request_hour v where a.hour=v.hour and a.Application=v.Application and a.WebSEAL=v.WebSEAL and a.access_dcn=v.access_dcn)
  as avg_total  
from v_app_request_hour a;

--------------------------------------------------------------------------------------------------
-- 
drop view v_res_access_event_detail;
create view v_res_access_event_detail as
select
  e.CARS_SEQ_NUMBER,
  e.SRC_INSTANCE_ID,
  e.APP_USR_NAME,
  e.TIME_STAMP,
  r.RES_NAME_IN_PLCY,
  r.access_dcn,
  action,
  r.RES_NAME_IN_APP
from 
   cars_t_event e inner join cars_t_res_access r on e.cars_seq_number=r.cars_seq_number
where 
     e.eventtype='AUDIT_RESOURCE_ACCESS' and e.APP_USR_NAME<>'Unauth';
     
---------------------------------------------------------------------------------------------
Testing SQL
---------------------------------------------------------------------------------------------
-- select count(*) from cars_t_event

-- select count(*) from v_app_request_hour_with_history

-- select * from v_app_request_hour;

-- select * from v_app_request_year order by year asc, Application asc, WebSEAL asc;

-- update cars_t_res_access set action='Internal' where src_instance_id='webseal177'
-- update cars_t_res_access set action='External' where src_instance_id='default-webseald-lmwebseal1'
-- update cars_t_event set time_stamp=time_stamp + 8 hours