/*==============================================================*/
/* Table: nW_DM_PROV_REQ                                      */
/*==============================================================*/
alter table nW_DM_PROV_REQ
      add(EXPIRED_TIME         DATE);


/*==============================================================*/
/* Table: nW_DM_CARRIER                                       */
/*==============================================================*/
alter table nW_DM_CARRIER
      add(JOB_EXPIRED_TIME     NUMBER(20));

update  nW_DM_CARRIER set JOB_EXPIRED_TIME='604800' where JOB_EXPIRED_TIME is null;
commit;

alter table nW_DM_CARRIER
      modify (JOB_EXPIRED_TIME  not null);

