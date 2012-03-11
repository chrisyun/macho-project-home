/*==============================================================*/
/* View: DM_TRACKING_LOG_DETAIL                                 */
/*==============================================================*/
create or replace view DM_TRACKING_LOG_DETAIL(DID, JOB_ID, DEVICE_ID, SESSION_ID, BEGIN_TIME_STAMP, END_TIME_STAMP, REQUEST_SUM, RESPONSE_SUM, CLIENT_IP, USER_AGENT) as
select http.id as did, sums.job_id,sums.device_external_id,sums.jid,sums.begin_time_stamp,sums.end_time_stamp, http.dm_request_size,http.dm_response_size,http.client_ip,http.user_agent 
           from  dm_tracking_log_http  http,(select distinct  job.dm_session_id as jid,
                             job.job_id as job_id,
                             job.device_external_id as device_external_id,
                             min(begin_time_stamp) as begin_time_stamp, 
                             max(end_time_stamp) as end_time_stamp
                       from dm_tracking_log_job job 
                       group by job.job_id,job.device_external_id,job.dm_session_id)  sums
    where http.dm_session_id=sums.jid
with read only;

 comment on table DM_TRACKING_LOG_DETAIL is
'create or replace view dm_tracking_log_detail  (did,job_id,device_id,session_id,begin_time_stamp,end_time_stamp,request_sum,response_sum,client_ip,user_agent  UNIQUE RELY DISABLE NOVALIDATE,
 CONSTRAINT newid_pk PRIMARY KEY (did) RELY DISABLE NOVALIDATE) as 
   select http.id as did, sums.job_id,sums.device_external_id,sums.jid,sums.begin_time_stamp,sums.end_time_stamp, http.dm_request_size,http.dm_response_size,http.client_ip,http.user_agent 
           from  dm_tracking_log_http  http,(select distinct  job.dm_session_id as jid,
                             job.job_id as job_id,
                             job.device_external_id as device_external_id,
                             min(begin_time_stamp) as begin_time_stamp, 
                             max(end_time_stamp) as end_time_stamp
                       from dm_tracking_log_job job 
                       group by job.job_id,job.device_external_id,job.dm_session_id)  sums
    where http.dm_session_id=sums.jid ';

/*==============================================================*/
/* View: DM_TRACKING_LOG_SUM                                    */
/*==============================================================*/
create or replace view DM_TRACKING_LOG_SUM(JOB_ID, DEVICE_ID, SESSION_ID, BEGIN_TIME_STAMP, END_TIME_STAMP, REQUEST_SUM, RESPONSE_SUM) as
select sums.job_id,sums.device_external_id,sums.jid,min(sums.begin_time_stamp),max(sums.end_time_stamp), sum(http.dm_request_size),sum(http.dm_response_size)
           from  dm_tracking_log_http  http,(select distinct  job.dm_session_id as jid,
                             job.job_id as job_id,
                             job.device_external_id as device_external_id,
                             min(begin_time_stamp) as begin_time_stamp,
                             max(end_time_stamp) as end_time_stamp
                       from dm_tracking_log_job job
                       group by job.job_id,job.device_external_id,job.dm_session_id)  sums
    where http.dm_session_id=sums.jid
    group by sums.jid,sums.job_id,sums.device_external_id
with read only;

 comment on table DM_TRACKING_LOG_SUM is
'create or replace view dm_tracking_log_sum  (job_id,device_id,session_id,begin_time_stamp,end_time_stamp,request_sum,response_sum UNIQUE RELY DISABLE NOVALIDATE,
 CONSTRAINT id_pk PRIMARY KEY (session_id) RELY DISABLE NOVALIDATE) as 
   select sums.job_id,sums.device_external_id,sums.jid,min(sums.begin_time_stamp),max(sums.end_time_stamp), sum(http.dm_request_size),sum(http.dm_response_size) 
           from  dm_tracking_log_http  http,(select distinct  job.dm_session_id as jid,
                             job.job_id as job_id,
                             job.device_external_id as device_external_id,
                             min(begin_time_stamp) as begin_time_stamp, 
                             max(end_time_stamp) as end_time_stamp
                       from dm_tracking_log_job job 
                       group by job.job_id,job.device_external_id,job.dm_session_id)  sums
    where http.dm_session_id=sums.jid  
    group by sums.jid,sums.job_id,sums.device_external_id';


/*==============================================================*/
/* Table: nW_DM_SOFTWARE_TRACKING_LOG                         */
/*==============================================================*/
create table nW_DM_SOFTWARE_TRACKING_LOG  (
   TRACKING_LOG_ID      NUMBER(20)                      not null,
   SOFTWARE_ID          NUMBER(20),
   PACKAGE_ID           NUMBER(20),
   TRACKING_TYPE        VARCHAR(32)                     not null,
   TIME_STAMP           DATE                            not null,
   CLIENT_IP            VARCHAR2(32),
   PHONE_NUMBER         VARCHAR2(32),
   constraint PK_NW_DM_SOFTWARE_TRACKING_LOG primary key (TRACKING_LOG_ID)
         using index global partition by hash (TRACKING_LOG_ID)
);

/*==============================================================*/
/* Index: IDX_SW_TRCK_LOG_FK_SW                                 */
/*==============================================================*/
create index IDX_SW_TRCK_LOG_FK_SW on nW_DM_SOFTWARE_TRACKING_LOG (
   SOFTWARE_ID ASC
);

/*==============================================================*/
/* Index: IDX_SW_TRCK_LOG_FK_SW_PKG                             */
/*==============================================================*/
create index IDX_SW_TRCK_LOG_FK_SW_PKG on nW_DM_SOFTWARE_TRACKING_LOG (
   PACKAGE_ID ASC
);

/*==============================================================*/
/* Index: IDX_SW_TRK_LOG_TIME                                   */
/*==============================================================*/
create index IDX_SW_TRK_LOG_TIME on nW_DM_SOFTWARE_TRACKING_LOG (
   TIME_STAMP ASC
);

/*==============================================================*/
/* Index: IDX_SW_TRK_LOG_TYPE                                   */
/*==============================================================*/
create index IDX_SW_TRK_LOG_TYPE on nW_DM_SOFTWARE_TRACKING_LOG (
   TRACKING_TYPE ASC
);

/*==============================================================*/
/* Index: IDX_SW_TRK_TRK_TIME_DAY                               */
/*==============================================================*/
create index IDX_SW_TRK_TRK_TIME_DAY on nW_DM_SOFTWARE_TRACKING_LOG (
   to_char(TIME_STAMP, 'YYYY-DDD') ASC
);

/*==============================================================*/
/* Index: IDX_SW_TRK_TRK_TIME_WEEK                              */
/*==============================================================*/
create index IDX_SW_TRK_TRK_TIME_WEEK on nW_DM_SOFTWARE_TRACKING_LOG (
   to_char(TIME_STAMP, 'YYYY-IW') ASC
);

/*==============================================================*/
/* Index: IDX_SW_TRK_TRK_TIME_MONTH                             */
/*==============================================================*/
create index IDX_SW_TRK_TRK_TIME_MONTH on nW_DM_SOFTWARE_TRACKING_LOG (
   to_char(TIME_STAMP, 'YYYY-MM') ASC
);

/*==============================================================*/
/* Index: IDX_SW_TRK_TRK_TIME_YEAR                              */
/*==============================================================*/
create index IDX_SW_TRK_TRK_TIME_YEAR on nW_DM_SOFTWARE_TRACKING_LOG (
   to_char(TIME_STAMP, 'yyyy') ASC
);


alter table nW_DM_SOFTWARE_TRACKING_LOG
   add constraint FK_TRCK_TARGET_SOFT foreign key (SOFTWARE_ID)
      references nW_DM_SOFTWARE (SOFTWARE_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_TRACKING_LOG
   add constraint FK_REF_TRCK_TARGET_SOFT_PKG foreign key (PACKAGE_ID)
      references nW_DM_SOFTWARE_PACKAGE (PACKAGE_ID)
      on delete cascade;

/*==============================================================*/
/* View: V_SOFTWARE_TRACK_LOG_ALL                               */
/*==============================================================*/
create or replace view V_SOFTWARE_TRACK_LOG_ALL as
select min(TRACKING_LOG_ID) as id, software_id, package_id, tracking_type, count(*) as count from NW_DM_SOFTWARE_TRACKING_LOG
group by software_id, package_id, tracking_type
with read only;

/*==============================================================*/
/* View: V_SOFTWARE_TRACK_LOG_DAILY                             */
/*==============================================================*/
create or replace view V_SOFTWARE_TRACK_LOG_DAILY as
select  min(TRACKING_LOG_ID) as id, software_id, package_id, tracking_type, to_char(TIME_STAMP, 'YYYY-DDD') as day_of_year, count(*) as count from NW_DM_SOFTWARE_TRACKING_LOG
group by software_id, package_id, tracking_type, to_char(TIME_STAMP, 'YYYY-DDD')
with read only;

comment on column V_SOFTWARE_TRACK_LOG_DAILY.DAY_OF_YEAR is
'2008-08';

/*==============================================================*/
/* View: V_SOFTWARE_TRACK_LOG_MONTHLY                           */
/*==============================================================*/
create or replace view V_SOFTWARE_TRACK_LOG_MONTHLY as
select min(TRACKING_LOG_ID) as id, software_id, package_id, tracking_type, to_char(TIME_STAMP, 'YYYY-MM') as month_of_year, count(*) as count from NW_DM_SOFTWARE_TRACKING_LOG
group by software_id, package_id, tracking_type, to_char(TIME_STAMP, 'YYYY-MM')
with read only;

/*==============================================================*/
/* View: V_SOFTWARE_TRACK_LOG_WEEKLY                            */
/*==============================================================*/
create or replace view V_SOFTWARE_TRACK_LOG_WEEKLY as
select min(TRACKING_LOG_ID) as id, software_id, package_id, tracking_type, to_char(TIME_STAMP, 'YYYY-IW') as week_of_year, count(*) as count from NW_DM_SOFTWARE_TRACKING_LOG
group by software_id, package_id, tracking_type, to_char(TIME_STAMP, 'YYYY-IW')
with read only;

/*==============================================================*/
/* View: V_SOFTWARE_TRACK_LOG_YEARLY                            */
/*==============================================================*/
create or replace view V_SOFTWARE_TRACK_LOG_YEARLY as
select min(TRACKING_LOG_ID) as id, software_id, package_id, tracking_type, to_char(TIME_STAMP, 'YYYY') as year, count(*) as count from NW_DM_SOFTWARE_TRACKING_LOG
group by software_id, package_id, tracking_type, to_char(TIME_STAMP, 'YYYY')
with read only;
