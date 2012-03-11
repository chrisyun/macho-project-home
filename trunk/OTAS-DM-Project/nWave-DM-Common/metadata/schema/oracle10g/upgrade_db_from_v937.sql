/*==============================================================*/
/* Table: DM_TRACKING_LOG_HTTP                                  */
/*==============================================================*/
create table DM_TRACKING_LOG_HTTP  (
   ID                   NUMBER(20)                      not null,
   TIME_STAMP           DATE                            not null,
   DM_SESSION_ID        VARCHAR2(255),
   DM_REQUEST_SIZE      NUMBER(10)                      not null,
   DM_RESPONSE_SIZE     NUMBER(10)                      not null,
   CLIENT_IP            VARCHAR2(64)                    not null,
   USER_AGENT           VARCHAR2(255),
   constraint PK_DM_TRACKING_LOG_HTTP primary key (ID)
);

/*==============================================================*/
/* Index: IDX_TRK_HT_DM_SESSION_ID                              */
/*==============================================================*/
create index IDX_TRK_HT_DM_SESSION_ID on DM_TRACKING_LOG_HTTP (
   DM_SESSION_ID ASC
);

/*==============================================================*/
/* Table: DM_TRACKING_LOG_JOB                                   */
/*==============================================================*/
create table DM_TRACKING_LOG_JOB  (
   ID                   NUMBER(20)                      not null,
   DM_SESSION_ID        VARCHAR2(255)                   not null,
   DEVICE_EXTERNAL_ID   VARCHAR2(32)                    not null,
   PROCESSOR            VARCHAR2(255)                   not null,
   STATUS_CODE          VARCHAR2(64),
   JOB_ID               NUMBER(20),
   BEGIN_TIME_STAMP     DATE,
   END_TIME_STAMP       DATE,
   UPDATED_TIME         DATE                            not null,
   constraint PK_DM_TRACKING_LOG_JOB primary key (ID)
);

/*==============================================================*/
/* Index: IDX_TRK_LOG_JOB_SESS_ID                               */
/*==============================================================*/
create index IDX_TRK_LOG_JOB_SESS_ID on DM_TRACKING_LOG_JOB (
   DM_SESSION_ID ASC
);

/*==============================================================*/
/* Index: IDX_TRK_LOG_JOB_DEV_EXT_ID                            */
/*==============================================================*/
create index IDX_TRK_LOG_JOB_DEV_EXT_ID on DM_TRACKING_LOG_JOB (
   DEVICE_EXTERNAL_ID ASC
);

/*==============================================================*/
/* Index: IDX_TRK_LOG_JOB_JOB_ID                                */
/*==============================================================*/
create index IDX_TRK_LOG_JOB_JOB_ID on DM_TRACKING_LOG_JOB (
   JOB_ID ASC
);
