select count(*) from cei_t_xml00

select
    e.*,
    r.* 
from
    DB2INST1.CARS_T_RES_ACCESS r, DB2INST1.CARS_T_EVENT e
where r.event_id=e.event_id
 and r.res_type='url'
order by e.time_stamp desc

delete from DB2INST1.CARS_T_EVENT where event_id in (select * from CARS_T_RES_ACCESS where res_type='url' and res_name_in_plcy<>'Not Available')

select event_id from CARS_T_RES_ACCESS where res_type='url' order by event_id

delete from DB2INST1.CARS_T_EVENT where event_id in ('CBA6011DFC4BA8485D6297908E41F131FC3BC000051A200000001');
commit;

select
    count(*) 
from
    DB2INST1.CARS_T_RES_ACCESS r, DB2INST1.CARS_T_EVENT e
where r.event_id=e.event_id
 and r.res_type='url'
 
 
 
select
    e.cars_seq_number, e.time_stamp 
from
    DB2INST1.CARS_T_RES_ACCESS r, DB2INST1.CARS_T_EVENT e
where r.event_id=e.event_id
 and r.res_type='url' and date(e.time_stamp) between '2010-09-01' and '2010-09-27'
order by e.time_stamp asc


select
    r.res_name_in_app, count(*)
from
    DB2INST1.CARS_T_RES_ACCESS r, DB2INST1.CARS_T_EVENT e
where r.event_id=e.event_id
 and r.res_type='url' and date(e.time_stamp) between '2010-10-01' and '2010-10-30'
group by r.res_name_in_app
having count(*) desc

# 查看各个应用的访问次数
select 
  r.res_name_in_plcy, count(*)
from
    DB2INST1.CARS_T_RES_ACCESS r, DB2INST1.CARS_T_EVENT e
where r.event_id=e.event_id
 and date(e.time_stamp) between '2010-10-01' and '2010-10-30'
 and r.res_name_in_plcy<>'Not Available' and r.res_name_in_plcy<>'OA'
group by r.res_name_in_plcy
order by count(*) desc

# 用户访问应用的清单
select 
  r.res_name_in_plcy, e.time_stamp, e.app_usr_name, r.res_name_in_app
from
    DB2INST1.CARS_T_RES_ACCESS r, DB2INST1.CARS_T_EVENT e
where r.event_id=e.event_id
 and date(e.time_stamp) between '2010-10-01' and '2010-10-30'
 and r.res_name_in_plcy<>'Not Available' and r.res_name_in_plcy<>'OA'
order by r.res_name_in_plcy asc, e.time_stamp

# 那些用户访问过各个应用及访问次数
select 
  distinct r.res_name_in_plcy, e.app_usr_name, count(*)
from
    DB2INST1.CARS_T_RES_ACCESS r, DB2INST1.CARS_T_EVENT e
where r.event_id=e.event_id
 and date(e.time_stamp) between '2010-10-01' and '2010-10-30'
 and r.res_name_in_plcy<>'Not Available' and r.res_name_in_plcy<>'OA'
group by r.res_name_in_plcy, e.app_usr_name
order by r.res_name_in_plcy asc, count(*) desc

# 查看某个用户的访问详情
select 
  r.res_name_in_plcy, e.time_stamp ,e.app_usr_name
from
    DB2INST1.CARS_T_RES_ACCESS r, DB2INST1.CARS_T_EVENT e
where r.event_id=e.event_id
 and date(e.time_stamp) between '2010-10-01' and '2010-10-30'
 and r.res_name_in_plcy<>'Not Available' and r.res_name_in_plcy<>'OA'
 and e.app_usr_name='pengpxz' and r.res_name_in_plcy='BAS'
order by e.time_stamp asc

