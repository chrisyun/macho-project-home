---------------------------------------------------------------------------------------------------
drop view v_app_unauth_year;
create view v_app_unauth_year as
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
     e.eventtype='AUDIT_RESOURCE_ACCESS' and e.APP_USR_NAME='Unauth'
group by year, RES_NAME_IN_PLCY, src_instance_id, access_dcn, action;

---------------------------------------------------------------------------------------------------
drop view v_app_unauth_month;
create view v_app_unauth_month as
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
     e.eventtype='AUDIT_RESOURCE_ACCESS' and e.APP_USR_NAME='Unauth'
group by year, month, RES_NAME_IN_PLCY, src_instance_id, access_dcn, action;

---------------------------------------------------------------------------------------------------
drop view v_app_unauth_day_of_month;
create view v_app_unauth_day_of_month as
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
     e.eventtype='AUDIT_RESOURCE_ACCESS' and e.APP_USR_NAME='Unauth'
group by year, month, day_of_month, RES_NAME_IN_PLCY, src_instance_id, access_dcn, action;

---------------------------------------------------------------------------------------------------
drop view v_app_unauth_hour;
create view v_app_unauth_hour as
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
     e.eventtype='AUDIT_RESOURCE_ACCESS' and e.APP_USR_NAME='Unauth'
group by year, month, day_of_month, hour, RES_NAME_IN_PLCY, src_instance_id, access_dcn, action;

-------------------------------------------------------------------------------------------------- 
drop view v_res_unauth_event_detail;
create view v_res_unauth_event_detail as
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
     e.eventtype='AUDIT_RESOURCE_ACCESS' and e.APP_USR_NAME='Unauth';

