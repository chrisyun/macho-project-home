/*==============================================================*/
/* Table: nW_DM_DEVICE                             */
/*==============================================================*/
update nW_DM_DEVICE set CREATED_TIME=LAST_UPDATED_TIME;
commit;
alter table nW_DM_DEVICE
      modify CREATED_TIME         DATE                            not null;
 
/*==============================================================*/
/* Table: nW_DM_MODEL                             */
/*==============================================================*/
alter table nW_DM_MODEL
      add CREATED_TIME         DATE;

update nW_DM_MODEL set LAST_UPDATED_TIME=to_date('2006-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS') where LAST_UPDATED_TIME is NULL;
update nW_DM_MODEL set CREATED_TIME=LAST_UPDATED_TIME;
commit;

alter table nW_DM_MODEL
      modify CREATED_TIME         DATE                            not null;

/*==============================================================*/
/* Table: nW_DM_UPDATE                             */
/*==============================================================*/
alter table nW_DM_UPDATE
      add CREATED_TIME         DATE;

update nW_DM_UPDATE set LAST_UPDATED_TIME=to_date('2006-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS') where LAST_UPDATED_TIME is NULL;
update nW_DM_UPDATE set CREATED_TIME=to_date('2006-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS');
commit;

alter table nW_DM_UPDATE
      modify CREATED_TIME         DATE                            not null;
