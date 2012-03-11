/*==============================================================*/
/* Table: nW_DM_PROFILE_MAPPING                                        */
/*==============================================================*/
alter table nW_DM_PROFILE_MAPPING
      add(NEED_TO_DISCOVERY    NUMBER(1),
          DISCOVERY_NODE_PATHS VARCHAR2(4000));


update  nW_DM_PROFILE_MAPPING set NEED_TO_DISCOVERY='1' where NEED_TO_DISCOVERY is null;
commit;

alter table nW_DM_PROFILE_MAPPING
      modify (NEED_TO_DISCOVERY  not null);

alter table nW_DM_PROFILE_NODE_MAPPING
      add VALUE_DEFAULT_MIME_TYPE VARCHAR(200);

alter table nW_DM_PROFILE_TEMPLATE
      add DESCRIPTION          VARCHAR2(2000);

/*==============================================================*/
/* Table: nW_DM_CARRIER                                        */
/*==============================================================*/
alter table nW_DM_CARRIER
      drop constraint DM_CAR_BOOT_DM_PROF;
      
alter table nW_DM_CARRIER
   add constraint DM_CAR_BOOT_DM_PROF foreign key (DM_FOR_BOOTSTRAP_ID)
      references nW_DM_CONFIG_PROFILE (PROFILE_ID)
      on delete set null;

alter table nW_DM_CARRIER
      drop constraint DM_CAR_NAP_PROF_FK;

alter table nW_DM_CARRIER
   add constraint DM_CAR_NAP_PROF_FK foreign key (NAP_FOR_BOOTSTRAP_ID)
      references nW_DM_CONFIG_PROFILE (PROFILE_ID)
      on delete set null
      not deferrable;

/*==============================================================*/
/* Table: DM_ENTERPRISE                                         */
/*==============================================================*/
create table DM_ENTERPRISE  (
   ENTERPRISE_ID        NUMBER(20)                      not null,
   NAME                 VARCHAR(200)                    not null,
   ACTIVED              NUMBER(1)                       not null,
   WEB_SITE             VARCHAR(200),
   DESCRIPTION          CLOB,
   CREATED_BY           VARCHAR2(255),
   CREATED_TIME         DATE                            not null,
   LAST_UPDATED_BY      VARCHAR2(255),
   LAST_UPDATED_TIME    DATE                            not null,
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint PK_DM_ENTERPRISE primary key (ENTERPRISE_ID)
);

/*==============================================================*/
/* Index: IDX_ENTERPRISE_NAME                                   */
/*==============================================================*/
create index IDX_ENTERPRISE_NAME on DM_ENTERPRISE (
   NAME ASC
);

/*==============================================================*/
/* Index: IDX_ENTERPRISE_ACTIVED                                */
/*==============================================================*/
create index IDX_ENTERPRISE_ACTIVED on DM_ENTERPRISE (
   ACTIVED ASC
);

/*==============================================================*/
/* Table: DM_ENTERPRISE_SOFTWRE                                 */
/*==============================================================*/
create table DM_ENTERPRISE_SOFTWRE  (
   ID                   NUMBER(20)                      not null,
   ENTERPRISE_ID        NUMBER(20),
   SOFTWARE_ID          NUMBER(20),
   constraint PK_DM_ENTERPRISE_SOFTWRE primary key (ID)
);

/*==============================================================*/
/* Index: IDX_FK_ENTER_SOFTW_1                                  */
/*==============================================================*/
create index IDX_FK_ENTER_SOFTW_1 on DM_ENTERPRISE_SOFTWRE (
   ENTERPRISE_ID ASC
);

/*==============================================================*/
/* Index: IDX_FK_ENTER_SOFTW_                                   */
/*==============================================================*/
create index IDX_FK_ENTER_SOFTW_ on DM_ENTERPRISE_SOFTWRE (
   SOFTWARE_ID ASC
);

/*==============================================================*/
/* Table: DM_ENTERPRISE_SUBSCRIBER                              */
/*==============================================================*/
create table DM_ENTERPRISE_SUBSCRIBER  (
   ID                   NUMBER(20)                      not null,
   ENTERPRISE_ID        NUMBER(20)                      not null,
   SUBSCRIBER_ID        NUMBER(20)                      not null,
   constraint PK_DM_ENTERPRISE_SUBSCRIBER primary key (ID)
);

/*==============================================================*/
/* Index: IDX_FK_ENTER_SUB_1                                    */
/*==============================================================*/
create index IDX_FK_ENTER_SUB_1 on DM_ENTERPRISE_SUBSCRIBER (
   ENTERPRISE_ID ASC
);

/*==============================================================*/
/* Index: IDX_FK_ENTER_SUB_2                                    */
/*==============================================================*/
create index IDX_FK_ENTER_SUB_2 on DM_ENTERPRISE_SUBSCRIBER (
   SUBSCRIBER_ID ASC
);

alter table DM_ENTERPRISE_SOFTWRE
   add constraint FK_ENTER_REF_ENTER_SOFTW foreign key (ENTERPRISE_ID)
      references DM_ENTERPRISE (ENTERPRISE_ID)
      on delete cascade;

alter table DM_ENTERPRISE_SOFTWRE
   add constraint FK_ENTER_REF_SOFTW foreign key (SOFTWARE_ID)
      references nW_DM_SOFTWARE (SOFTWARE_ID)
      on delete cascade;

alter table DM_ENTERPRISE_SUBSCRIBER
   add constraint FK_ENTER_REF_ENTER_SUBS foreign key (ENTERPRISE_ID)
      references DM_ENTERPRISE (ENTERPRISE_ID)
      on delete cascade;

alter table DM_ENTERPRISE_SUBSCRIBER
   add constraint FK_ENTER_RE_SUB foreign key (SUBSCRIBER_ID)
      references nW_DM_SUBSCRIBER (SUBSCRIBER_ID)
      on delete cascade;

/*==============================================================*/
/* Table: nW_DM_PROFILE_NODE_MAPPING                                 */
/*==============================================================*/
alter table nw_dm_profile_node_mapping add VV CLOB;

update nw_dm_profile_node_mapping set VV=VALUE;
commit;

alter table nw_dm_profile_node_mapping rename column value to value_deleted;

alter table nw_dm_profile_node_mapping rename column vv to value;