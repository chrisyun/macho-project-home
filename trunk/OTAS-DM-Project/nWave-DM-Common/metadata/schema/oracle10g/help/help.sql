column index_name format a18;
column column_name format a18;
column table_name format a16;

select table_name, index_name,BLEVEL
from user_indexes;

select table_name, index_name, column_name, column_position
from user_ind_columns
order by table_name, index_name, column_name;


select to_char(sysdate, 'yyyy-mm-dd hh24:mi:ss' ),count(*) from nw_dm_device;


/*==============================================================*/

column SUBSCRIBER_EXTERNAL_ID format a18;
column MANUFACTURER_MODEL_ID format a18;
set pagesize 5000;

select SUBSCRIBER_EXTERNAL_ID,MANUFACTURER_MODEL_ID
from NW_DM_SUBSCRIBER,NW_DM_DEVICE, NW_DM_MODEL 
where NW_DM_SUBSCRIBER.SUBSCRIBER_ID=NW_DM_DEVICE.SUBSCRIBER_ID
  and NW_DM_DEVICE.MODEL_ID=NW_DM_MODEL.MODEL_ID
  and NW_DM_SUBSCRIBER.SERVICE_PROVIDER_ID='104229727'
  and NW_DM_MODEL.MANUFACTURER_MODEL_ID<>'6120c'
order by MANUFACTURER_MODEL_ID;


/*==============================================================*/
create index GLOB_IDX_DEVICE on nW_DM_DEVICE(DEVICE_ID) global 
partition by hash(DEVICE_ID) partitions 8;

/*==============================================================*/
/* 修改 NW_DM_DEVICE 的 DEVICE_ID 为 global hash partition      */
/*==============================================================*/

alter table nW_DM_DEVICE_GROUP_DEVICE
   drop constraint nW_DM_DEV_GRP_DEV_FK;

alter table nW_DM_DEVICE_LOG
   drop constraint nW_DM_DEVICE_LOG_DEVICE_FK;

alter table nW_DM_DEVICE_PROV_REQ
   drop constraint nW_DM_DPR_DEVICE_FK;

alter table nW_DM_DM_JOB_ADAPTER
   drop constraint DM_JOB_ADAPTER_DEVICE_FK;

alter table nW_DM_JOB_STATE
   drop constraint JOB_STATE_DEV_FK;

alter table nW_DM_PROFILE_ASSIGNMENT
   drop constraint PROFILE_ASSIGNMENT_DEVICE_FK;

alter table nW_DM_SESSION_AUTH
   drop constraint nW_DM_SESSION_AUTH_DEV_FK;
   
alter table nW_DM_DEVICE
   drop constraint nW_DM_DEVICE_PK;

alter table nW_DM_DEVICE
   add constraint nW_DM_DEVICE_PK primary key (DEVICE_ID)
         using index
       global partition by hash (DEVICE_ID)
       pctfree 10
       initrans 2;
       
alter table nW_DM_DEVICE_GROUP_DEVICE
   add constraint nW_DM_DEV_GRP_DEV_FK foreign key (DEVICE_ID)
      references nW_DM_DEVICE (DEVICE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DEVICE_LOG
   add constraint nW_DM_DEVICE_LOG_DEVICE_FK foreign key (DEVICE_ID)
      references nW_DM_DEVICE (DEVICE_ID)
      on delete set null
      deferrable initially deferred;

alter table nW_DM_DEVICE_PROV_REQ
   add constraint nW_DM_DPR_DEVICE_FK foreign key (DEVICE_ID)
      references nW_DM_DEVICE (DEVICE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DM_JOB_ADAPTER
   add constraint DM_JOB_ADAPTER_DEVICE_FK foreign key (DEVICE_ID)
      references nW_DM_DEVICE (DEVICE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_JOB_STATE
   add constraint JOB_STATE_DEV_FK foreign key (DEVICE_ID)
      references nW_DM_DEVICE (DEVICE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROFILE_ASSIGNMENT
   add constraint PROFILE_ASSIGNMENT_DEVICE_FK foreign key (DEVICE_ID)
      references nW_DM_DEVICE (DEVICE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_SESSION_AUTH
   add constraint nW_DM_SESSION_AUTH_DEV_FK foreign key (DEVICE_ID)
      references nW_DM_DEVICE (DEVICE_ID)
      on delete cascade
      not deferrable;

/*==============================================================*/
/*                                                              */
/* 修改 nW_DM_SUBSCRIBER 的 DEVICE_ID 为 global hash partition  */
/*                                                              */
/*==============================================================*/

alter table nW_DM_DEVICE
   drop constraint nW_DM_DEVICE_SUBS_FK;
   
alter table nW_DM_SUBSCRIBER
   drop constraint nW_DM_SUBSCRIBER_PK;

alter table nW_DM_SUBSCRIBER
   add constraint nW_DM_SUBSCRIBER_PK primary key (SUBSCRIBER_ID)
       using index
       global partition by hash (SUBSCRIBER_ID)
       pctfree 10
       initrans 2;

alter table nW_DM_DEVICE
   add constraint nW_DM_DEVICE_SUBS_FK foreign key (SUBSCRIBER_ID)
      references nW_DM_SUBSCRIBER (SUBSCRIBER_ID)
      on delete cascade
      not deferrable;







