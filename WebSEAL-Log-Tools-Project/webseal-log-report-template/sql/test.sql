DROP FUNCTION func_convert2chars;
CREATE  FUNCTION func_convert2chars (dd INTEGER)
RETURNS VARCHAR(2)
------------------------------------------------------------------------
-- FUNCTION：func_convert2chars
-- Description：convert 0 to "00", 9 to "09"
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
 
-- Create Alias 
drop alias cars_t_event;
create alias cars_t_event for db2inst1.cars_t_event;
drop alias cars_t_res_access;
create alias cars_t_res_access for db2inst1.cars_t_res_access;

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

-- 每个季度各个应用登录次数统计
select
  year,
  quarter,
  RES_NAME_IN_PLCY,
  src_instance_id,
  access_dcn,
  count(*)
from 
   cars_t_event e inner join cars_t_res_access r on e.cars_seq_number=r.cars_seq_number
                  inner join v_cars_t_event_time t on e.cars_seq_number=t.cars_seq_number
where 
     e.eventtype='AUDIT_RESOURCE_ACCESS'
group by year, quarter, RES_NAME_IN_PLCY, src_instance_id, access_dcn
order by year asc, quarter asc;

-- 每月各个应用登录次数统计
select
  year,
  month,
  RES_NAME_IN_PLCY,
  src_instance_id,
  access_dcn,
  count(*)
from 
   cars_t_event e inner join cars_t_res_access r on e.cars_seq_number=r.cars_seq_number
                  inner join v_cars_t_event_time t on e.cars_seq_number=t.cars_seq_number
where 
     e.eventtype='AUDIT_RESOURCE_ACCESS'
group by year, month, RES_NAME_IN_PLCY, src_instance_id, access_dcn
order by year asc, month asc;

-- 每天各个应用登录次数统计
select
  year,
  month,
  day_of_month,
  RES_NAME_IN_PLCY,
  src_instance_id,
  access_dcn,
  count(*)
from 
   cars_t_event e inner join cars_t_res_access r on e.cars_seq_number=r.cars_seq_number
                  inner join v_cars_t_event_time t on e.cars_seq_number=t.cars_seq_number
where 
     e.eventtype='AUDIT_RESOURCE_ACCESS'
group by year, month, day_of_month, RES_NAME_IN_PLCY, src_instance_id, access_dcn
order by year asc, month asc, day_of_month asc;

-- 每小时各个应用登录次数统计
create view v_app_request_hour as
select
  year,
  month,
  day_of_month,
  hour,
  RES_NAME_IN_PLCY,
  src_instance_id,
  access_dcn,
  count(*) as total,
  select count(*) from 
from 
   cars_t_event e inner join cars_t_res_access r on e.cars_seq_number=r.cars_seq_number
                  inner join v_cars_t_event_time t on e.cars_seq_number=t.cars_seq_number
where 
     e.eventtype='AUDIT_RESOURCE_ACCESS'
group by year, month, day_of_month, hour, RES_NAME_IN_PLCY, src_instance_id, access_dcn;

--------------------------------------------------------------------------------------------------
-- 
create view v_app_request_hour_with_history as
select
  a.*,
  (select max(total) from v_app_request_hour v where a.hour=v.hour and a.RES_NAME_IN_PLCY=v.RES_NAME_IN_PLCY and a.src_instance_id=v.src_instance_id and a.access_dcn=v.access_dcn)
   as max_total,
  (select min(total) from v_app_request_hour v where a.hour=v.hour and a.RES_NAME_IN_PLCY=v.RES_NAME_IN_PLCY and a.src_instance_id=v.src_instance_id and a.access_dcn=v.access_dcn)
  as min_total,
  (select avg(total) from v_app_request_hour v where a.hour=v.hour and a.RES_NAME_IN_PLCY=v.RES_NAME_IN_PLCY and a.src_instance_id=v.src_instance_id and a.access_dcn=v.access_dcn)
  as avg_total  
from v_app_request_hour a


-- 每分钟各个应用登录次数统计
select
  year,
  month,
  day_of_month,
  hour,
  minute,
  RES_NAME_IN_PLCY,
  src_instance_id,
  access_dcn,
  count(*)
from 
   cars_t_event e inner join cars_t_res_access r on e.cars_seq_number=r.cars_seq_number
                  inner join v_cars_t_event_time t on e.cars_seq_number=t.cars_seq_number
where 
     e.eventtype='AUDIT_RESOURCE_ACCESS'
group by year, month, day_of_month, hour, minute, RES_NAME_IN_PLCY, src_instance_id, access_dcn
order by year asc, month asc, day_of_month asc, hour asc, minute asc;



---------------------------------------------------------------------------------------------------
-- View v_res_access_event_detail
create view v_res_access_event_detail as
select
  e.CARS_SEQ_NUMBER,
  e.SRC_INSTANCE_ID,
  e.APP_USR_NAME,
  e.TIME_STAMP,
  r.RES_NAME_IN_PLCY,
  r.access_dcn,
  r.RES_NAME_IN_APP
from 
   cars_t_event e inner join cars_t_res_access r on e.cars_seq_number=r.cars_seq_number
where 
     e.eventtype='AUDIT_RESOURCE_ACCESS';












select * from v_cars_t_event_time order by time_stamp DESC;
select * from cars_t_res_access;
select count(*) from cars_t_event;



select
 e.*
from 
 cars_t_event e inner join v_cars_t_event_time t on t.cars_seq_number=e.cars_seq_number







select month(time_stamp), count(*) from cars_t_event where year(time_stamp)=2011 group by month(time_stamp);
select month(time_stamp), count(*) from cars_t_event group by month(time_stamp);


select count(*) from cars_t_event where year(time_stamp)=2010 and month(time_stamp)=10;
delete from cars_t_event where year(time_stamp)=2010 and month(time_stamp)=10 and DAYOFWEEK(time_stamp)=7;

select * from cars_t_event order by time_stamp DESC;


