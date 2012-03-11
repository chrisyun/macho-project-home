/*==============================================================*/
/* Table: nW_DM_SOFTWARE_RATING                                         */
/*==============================================================*/
drop table nW_DM_SOFTWARE_RATING cascade constraints;

create table nW_DM_SOFTWARE_RATING  (
   RATING_ID            NUMBER(20)                      not null,
   PACKAGE_ID           NUMBER(20),
   NAME                 VARCHAR(255)                    not null,
   RATE                 NUMBER(16),
   constraint PK_NW_DM_SOFTWARE_RATING primary key (RATING_ID),
   constraint AK_SOFT_RATING_NAME_UNIQ unique (NAME, PACKAGE_ID)
);

alter table nW_DM_SOFTWARE_RATING
   add constraint FK_SOFTWARE_RATE foreign key (PACKAGE_ID)
      references nW_DM_SOFTWARE_PACKAGE (PACKAGE_ID)
      on delete cascade;

      

/*==============================================================*/
/* Table: nW_DM_SOFTWARE_RECOMMEND                            */
/*==============================================================*/
drop table nW_DM_SOFTWARE_RECOMMEND;

create table nW_DM_SOFTWARE_RECOMMEND  (
   RECOMMEND_ID         NUMBER(20)                      not null,
   CATEGORY_ID          NUMBER(20),
   SOFTWARE_ID          NUMBER(20)                      not null,
   PRIORITY             NUMBER(10)                      not null,
   DESCRIPTION          CLOB,
   constraint PK_NW_DM_SOFTWARE_RECOMMEND primary key (RECOMMEND_ID)
);

/*==============================================================*/
/* Index: IDX_SOFT_RECOMM_UNIQ                                  */
/*==============================================================*/
create unique index IDX_SOFT_RECOMM_UNIQ on nW_DM_SOFTWARE_RECOMMEND (
   CATEGORY_ID ASC,
   SOFTWARE_ID ASC,
   PRIORITY ASC
);

alter table nW_DM_SOFTWARE_RECOMMEND
   add constraint FK_RECOMM_CATEGORY foreign key (CATEGORY_ID)
      references nW_DM_SOFTWARE_CATEGORY (CATEGORY_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_RECOMMEND
   add constraint FK_RECOMM_SOFTWARE foreign key (SOFTWARE_ID)
      references nW_DM_SOFTWARE (SOFTWARE_ID)
      on delete cascade;


/*==============================================================*/
/* Table: DM_DEVICE_CHANGE_LOG                                  */
/*==============================================================*/
create table DM_DEVICE_CHANGE_LOG  (
   ID                   NUMBER(20)                      not null,
   IMEI                 VARCHAR(32)                     not null,
   PHONE_NUMBER         VARCHAR(32)                     not null,
   IMSI                 VARCHAR(32),
   BRAND                VARCHAR(64),
   MODEL                VARCHAR(64),
   LAST_UPDATE          DATE                            not null,
   constraint PK_DM_DEVICE_CHANGE_LOG primary key (ID)
);

/*==============================================================*/
/* Index: IDX_DEV_CHANGE_LOG_IMEI                               */
/*==============================================================*/
create index IDX_DEV_CHANGE_LOG_IMEI on DM_DEVICE_CHANGE_LOG (
   IMEI ASC
);

/*==============================================================*/
/* Index: IDX_DEV_CHANGE_LOG_MSISDN                             */
/*==============================================================*/
create index IDX_DEV_CHANGE_LOG_MSISDN on DM_DEVICE_CHANGE_LOG (
   PHONE_NUMBER ASC
);

/*==============================================================*/
/* Index: IDX_DEV_CHANGE_LOG_IMSI                               */
/*==============================================================*/
create index IDX_DEV_CHANGE_LOG_IMSI on DM_DEVICE_CHANGE_LOG (
   IMSI ASC
);


/*==============================================================*/
/* Table: nW_DM_PROV_REQ                                         */
/*==============================================================*/
alter table nW_DM_PROV_REQ
      add CONCURRENT_SIZE      NUMBER(10);

update nW_DM_PROV_REQ set CONCURRENT_SIZE='10';
commit;

alter table nW_DM_PROV_REQ
      modify CONCURRENT_SIZE               not null;

alter table nW_DM_PROV_REQ
      add CONCURRENT_INTERVAL  NUMBER(12);

update nW_DM_PROV_REQ set CONCURRENT_INTERVAL='100';
commit;

alter table nW_DM_PROV_REQ
      modify CONCURRENT_INTERVAL               not null;

alter table nW_DM_PROV_REQ
   add PARENT_PROV_REQ_ID   NUMBER(20);

/*==============================================================*/
/* Index: IDX_PARENT_PROV_REQ                                   */
/*==============================================================*/
create index IDX_PARENT_PROV_REQ on nW_DM_PROV_REQ (
   PARENT_PROV_REQ_ID ASC
);

alter table nW_DM_PROV_REQ
   add constraint FK_PROV_PARENT_JOB foreign key (PARENT_PROV_REQ_ID)
      references nW_DM_PROV_REQ (PROV_REQ_ID)
      on delete cascade;

/*==============================================================*/
/* Table: nW_CP_JOB_TARGET_DEVICE                                         */
/*==============================================================*/
alter table nW_CP_JOB_TARGET_DEVICE
   add NUMBER_OF_ENQUEUE_RETRIES NUMBER(6);
update nW_CP_JOB_TARGET_DEVICE set NUMBER_OF_ENQUEUE_RETRIES='1';
commit;
alter table nW_CP_JOB_TARGET_DEVICE
      modify NUMBER_OF_ENQUEUE_RETRIES               not null;

alter table nW_CP_JOB_TARGET_DEVICE
   add LAST_ENQUEUE_RETRIED_TIME DATE;
   
alter table nW_CP_JOB_TARGET_DEVICE
   add MESSAGE_RAW          BLOB;
   