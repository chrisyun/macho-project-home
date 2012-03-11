/*==============================================================*/
/* Table: nW_DM_SOFTWARE                                         */
/*==============================================================*/
alter table nW_DM_SOFTWARE
      add STATUS               VARCHAR2(32);

comment on column nW_DM_SOFTWARE.STATUS is
'release,test';

update nW_DM_SOFTWARE set STATUS='release';
commit;

alter table nW_DM_SOFTWARE
      modify STATUS               not null;

/*==============================================================*/
/* Index: IDX_SOFTWARE_STATUS                                   */
/*==============================================================*/
create index IDX_SOFTWARE_STATUS on nW_DM_SOFTWARE (
   STATUS ASC
);

/*==============================================================*/
/* Table: nW_DM_SOFTWARE_PACKAGE                                */
/*==============================================================*/
alter table nW_DM_SOFTWARE_PACKAGE
      add STATUS               VARCHAR2(32);

alter table nW_DM_SOFTWARE_PACKAGE
      add DESCRIPTION          CLOB;

update nW_DM_SOFTWARE_PACKAGE set STATUS='release';
commit;

alter table nW_DM_SOFTWARE_PACKAGE
      modify STATUS               not null;

/*==============================================================*/
/* Index: IDX_SOFT_PKG_STATUS                                   */
/*==============================================================*/
create index IDX_SOFT_PKG_STATUS on nW_DM_SOFTWARE_PACKAGE (
   STATUS ASC
);

/*================================================================================================================================*/


/*==============================================================*/
/* Table: nW_DM_SOFTWARE_CATEGORIES                           */
/*==============================================================*/
create table nW_DM_SOFTWARE_CATEGORIES  (
   SOFTWARE_ID          NUMBER(20)                      not null,
   CATEGORY_ID          NUMBER(20)                      not null,
   PRIORITY              NUMBER(3)                       not null,
   constraint PK_NW_DM_SOFTWARE_CATEGORIES primary key (SOFTWARE_ID, CATEGORY_ID)
);

/*==============================================================*/
/* Index: IDX_CATEGORY_SOFTS_FK                                 */
/*==============================================================*/
create index IDX_CATEGORY_SOFTS_FK on nW_DM_SOFTWARE_CATEGORIES (
   SOFTWARE_ID ASC
);

/*==============================================================*/
/* Index: IDX_SOFT_CATEGORIES_FK                                */
/*==============================================================*/
create index IDX_SOFT_CATEGORIES_FK on nW_DM_SOFTWARE_CATEGORIES (
   CATEGORY_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_SOFTWARE_RECOMMEND                            */
/*==============================================================*/
create table nW_DM_SOFTWARE_RECOMMEND  (
   CATEGORY_ID          NUMBER(20)                      not null,
   SOFTWARE_ID          NUMBER(20)                      not null,
   PRIORITY              NUMBER(5)                       not null,
   constraint PK_NW_DM_SOFTWARE_RECOMMEND primary key (CATEGORY_ID, SOFTWARE_ID)
);


alter table nW_DM_SOFTWARE_CATEGORIES
   add constraint FK_CATEGORY_SOFTS foreign key (SOFTWARE_ID)
      references nW_DM_SOFTWARE (SOFTWARE_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_CATEGORIES
   add constraint FK_SOFT_CATEGORIES foreign key (CATEGORY_ID)
      references nW_DM_SOFTWARE_CATEGORY (CATEGORY_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_RECOMMEND
   add constraint FK_RECOMM_CATEGORY foreign key (CATEGORY_ID)
      references nW_DM_SOFTWARE_CATEGORY (CATEGORY_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_RECOMMEND
   add constraint FK_RECOMM_SOFTWARE foreign key (SOFTWARE_ID)
      references nW_DM_SOFTWARE (SOFTWARE_ID)
      on delete cascade;



/* ===================================================*/
alter table nW_DM_SOFTWARE
      add CREATED_TIME         DATE;

update nW_DM_SOFTWARE set CREATED_TIME=sysdate where CREATED_TIME IS NULL;

alter table nW_DM_SOFTWARE
      modify CREATED_TIME               not null;

/* ===================================================*/
alter table nW_DM_SOFTWARE
      add CREATED_BY           VARCHAR2(255);

/* ===================================================*/
alter table nW_DM_SOFTWARE
      add LAST_UPDATED_TIME    DATE;
      
update nW_DM_SOFTWARE set LAST_UPDATED_TIME=CREATED_TIME where LAST_UPDATED_TIME IS NULL;

alter table nW_DM_SOFTWARE
      modify LAST_UPDATED_TIME               not null;

/* ===================================================*/
alter table nW_DM_SOFTWARE
      add LAST_UPDATED_BY      VARCHAR2(255);
      
/* ===================================================*/
alter table nW_DM_SOFTWARE
      add CHANGE_VERSION       NUMBER(20);
      
update nW_DM_SOFTWARE set CHANGE_VERSION='0' where CHANGE_VERSION IS NULL;

alter table nW_DM_SOFTWARE
      modify CHANGE_VERSION               not null;
/* ===================================================*/


/* ===================================================*/
alter table nW_DM_SOFTWARE_PACKAGE
      add CREATED_TIME         DATE;

update nW_DM_SOFTWARE_PACKAGE set CREATED_TIME=sysdate where CREATED_TIME IS NULL;

alter table nW_DM_SOFTWARE_PACKAGE
      modify CREATED_TIME               not null;

/* ===================================================*/
alter table nW_DM_SOFTWARE_PACKAGE
      add CREATED_BY           VARCHAR2(255);

/* ===================================================*/
alter table nW_DM_SOFTWARE_PACKAGE
      add LAST_UPDATED_TIME    DATE;
      
update nW_DM_SOFTWARE_PACKAGE set LAST_UPDATED_TIME=CREATED_TIME where LAST_UPDATED_TIME IS NULL;

alter table nW_DM_SOFTWARE_PACKAGE
      modify LAST_UPDATED_TIME               not null;

/* ===================================================*/
alter table nW_DM_SOFTWARE_PACKAGE
      add LAST_UPDATED_BY      VARCHAR2(255);
      
/* ===================================================*/
alter table nW_DM_SOFTWARE_PACKAGE
      add CHANGE_VERSION       NUMBER(20);
      
update nW_DM_SOFTWARE_PACKAGE set CHANGE_VERSION='0' where CHANGE_VERSION IS NULL;

alter table nW_DM_SOFTWARE_PACKAGE
      modify CHANGE_VERSION               not null;
/* ===================================================*/


/* ===================================================*/
insert into nW_DM_SOFTWARE_CATEGORIES(SOFTWARE_ID, CATEGORY_ID, PRIORITY)
       select SOFTWARE_ID,CATEGORY_ID,0 from nW_DM_SOFTWARE;


/* ===================================================*/

alter table nW_DM_SOFTWARE
   drop constraint FK_SOFT_CATEGORY;

drop index IDX_SOFTWARE_CATEGORY_FK;

alter table nW_DM_SOFTWARE
   drop column CATEGORY_ID;
   

/*================================================================================================================================*/


/*==============================================================*/
/* Improve select count(*) performance                          */
/*==============================================================*/

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

