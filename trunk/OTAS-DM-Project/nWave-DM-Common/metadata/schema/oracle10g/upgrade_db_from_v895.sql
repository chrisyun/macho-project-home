/*==============================================================*/
/* Table: nW_DM_SERVICE_PROVIDER                              */
/*==============================================================*/
create table nW_DM_SERVICE_PROVIDER  (
   SERVICE_PROVIDER_ID  NUMBER(20)                      not null,
   EXTERNAL_ID          VARCHAR2(200)                   not null,
   NAME                 VARCHAR2(200)                   not null,
   DESCRIPTION          VARCHAR2(2000),
   constraint PK_NW_DM_SERVICE_PROVIDER primary key (SERVICE_PROVIDER_ID)
);

/*==============================================================*/
/* Index: IDX_SP_EXT_ID                                         */
/*==============================================================*/
create unique index IDX_SP_EXT_ID on nW_DM_SERVICE_PROVIDER (
   EXTERNAL_ID ASC
);

/*==============================================================*/
/* Index: IDX_SOFTWRE_EXT_ID                                    */
/*==============================================================*/
create unique index IDX_SOFTWRE_EXT_ID on nW_DM_SOFTWARE (
   EXTERNAL_ID ASC
);

/*==============================================================*/
/* Modify Table: nW_DM_SUBSCRIBER                                    */
/*==============================================================*/

alter table nW_DM_SUBSCRIBER
      add SERVICE_PROVIDER_ID  NUMBER(20);
       
alter table nW_DM_SUBSCRIBER
      add CREATED_TIME         DATE;
       
alter table nW_DM_SUBSCRIBER
      add CREATED_BY           VARCHAR2(200);
       


comment on column nW_DM_SUBSCRIBER.CREATED_TIME is
'创建时间';

comment on column nW_DM_SUBSCRIBER.CREATED_BY is
'创建者';


alter table nW_DM_SUBSCRIBER
   add constraint FK_SUBSCR_SP foreign key (SERVICE_PROVIDER_ID)
      references nW_DM_SERVICE_PROVIDER (SERVICE_PROVIDER_ID)
      on delete set null;

