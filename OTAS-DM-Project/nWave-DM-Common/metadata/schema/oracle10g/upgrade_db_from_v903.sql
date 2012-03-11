/*==============================================================*/
/* Table: DM_ACCESS_LOG                                         */
/*==============================================================*/
create table DM_ACCESS_LOG  (
   ID                   NUMBER(20)                      not null,
   TIME_STAMP           DATE                            not null,
   URL                  VARCHAR2(4000)                  not null,
   QUERY                VARCHAR2(4000),
   CLIENT_IP            VARCHAR(64)                     not null,
   USER_AGENT           VARCHAR2(4000),
   SESSION_ID           VARCHAR(64),
   constraint PK_DM_ACCESS_LOG primary key (ID)
);

/*==============================================================*/
/* Table: DM_ACCESS_LOG_HEADER                                  */
/*==============================================================*/
create table DM_ACCESS_LOG_HEADER  (
   ID                   NUMBER(20)                      not null,
   LOG_ID               NUMBER(20),
   NAME                 VARCHAR(200)                    not null,
   VALUE                VARCHAR(4000),
   constraint PK_DM_ACCESS_LOG_HEADER primary key (ID)
);

/*==============================================================*/
/* Index: IDX_FK_ACCESS_LOG_HEAD                                */
/*==============================================================*/
create index IDX_FK_ACCESS_LOG_HEAD on DM_ACCESS_LOG_HEADER (
   LOG_ID ASC
);

/*==============================================================*/
/* Index: IDX_ACCESS_LOG_HEAD_NAME                              */
/*==============================================================*/
create index IDX_ACCESS_LOG_HEAD_NAME on DM_ACCESS_LOG_HEADER (
   NAME ASC
);

/*==============================================================*/
/* Table: DM_ACCESS_LOG_PARAMETER                               */
/*==============================================================*/
create table DM_ACCESS_LOG_PARAMETER  (
   ID                   NUMBER(20)                      not null,
   LOG_ID               NUMBER(20),
   NAME                 VARCHAR(200)                    not null,
   VALUE             VARCHAR(4000),
   constraint PK_DM_ACCESS_LOG_PARAMETER primary key (ID)
);

/*==============================================================*/
/* Index: IDX_FK_ACCESS_LOG_PARAM                               */
/*==============================================================*/
create index IDX_FK_ACCESS_LOG_PARAM on DM_ACCESS_LOG_PARAMETER (
   LOG_ID ASC
);

/*==============================================================*/
/* Index: IDX_ACCESS_LOG_HEAD_PARAM                             */
/*==============================================================*/
create index IDX_ACCESS_LOG_HEAD_PARAM on DM_ACCESS_LOG_PARAMETER (
   NAME ASC
);



alter table DM_ACCESS_LOG_HEADER
   add constraint FK_ACCESS_LOG_HEADER foreign key (LOG_ID)
      references DM_ACCESS_LOG (ID)
      on delete cascade;

alter table DM_ACCESS_LOG_PARAMETER
   add constraint FK_ACCESS_LOG_PARAM foreign key (LOG_ID)
      references DM_ACCESS_LOG (ID)
      on delete cascade;

/*==============================================================*/
/* Sequence: nwavedm_log_sequence                             */
/*==============================================================*/
CREATE SEQUENCE nwavedm_log_sequence
INCREMENT BY 1
START WITH 10
NOMAXVALUE
NOCYCLE;

select nwavedm_log_sequence.nextval from dual;

