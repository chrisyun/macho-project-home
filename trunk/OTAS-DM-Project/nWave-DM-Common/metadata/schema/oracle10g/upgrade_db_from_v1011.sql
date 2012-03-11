/*==============================================================*/
/* Table: DM_FAVORITE                                           */
/*==============================================================*/
create table DM_FAVORITE  (
   FAVORITE_ID          NUMBER(20)                      not null,
   NAME                 VARCHAR2(1024)                  not null,
   DESCRIPTION          VARCHAR2(2000),
   ISSHARE              NUMBER(1)                       not null,
   OWNER                VARCHAR2(1024),
   constraint PK_DM_FAVORITE primary key (FAVORITE_ID)
);

/*==============================================================*/
/* Index: IDX_FK_FAVORITE                                       */
/*==============================================================*/
create unique index IDX_FK_FAVORITE on DM_FAVORITE (
   NAME ASC
);

/*==============================================================*/
/* Table: DM_FAVORITE_DEVICE                                    */
/*==============================================================*/
create table DM_FAVORITE_DEVICE  (
   FAVORITE_ID          NUMBER(20)                      not null,
   ID                   NUMBER(20)                      not null,
   DEVICE_ID            NUMBER(20)                      not null,
   constraint PK_DM_FAVORITE_DEVICE primary key (ID)
);

/*==============================================================*/
/* Index: IDX_FK_FAVORITE_DEVICE                                */
/*==============================================================*/
create index IDX_FK_FAVORITE_DEVICE on DM_FAVORITE_DEVICE (
   DEVICE_ID ASC
);

/*==============================================================*/
/* Index: IDX_FK_FAVORITE_ID                                    */
/*==============================================================*/
create index IDX_FK_FAVORITE_ID on DM_FAVORITE_DEVICE (
   FAVORITE_ID ASC
);

alter table DM_FAVORITE_DEVICE
   add constraint FK_FAVORITE foreign key (FAVORITE_ID)
      references DM_FAVORITE (FAVORITE_ID)
      on delete cascade;

alter table DM_FAVORITE_DEVICE
   add constraint FK_FAVORITE_DEVICE foreign key (DEVICE_ID)
      references nW_DM_DEVICE (DEVICE_ID)
      on delete cascade;
