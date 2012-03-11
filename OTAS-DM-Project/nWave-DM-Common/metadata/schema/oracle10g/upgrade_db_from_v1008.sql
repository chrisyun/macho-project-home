/*==============================================================*/
/* Table: nW_DM_PROV_REQ                                        */
/*==============================================================*/
alter table nW_DM_PROV_REQ
      add(IS_PROMPT_FOR_BEGINNING              NUMBER(1),
          PROMPT_TYPE_FOR_BEGINNING            VARCHAR2(30),
          PROMPT_TEXT_FOR_BEGINNING            VARCHAR2(300),
          IS_PROMPT_FOR_FINISHED               NUMBER(1),
          PROMPT_TYPE_FOR_FINISHED            VARCHAR2(30),
          PROMPT_TEXT_FOR_FINISHED            VARCHAR2(300));


update  nW_DM_PROV_REQ set IS_PROMPT_FOR_BEGINNING=0 where IS_PROMPT_FOR_BEGINNING is null;
update  nW_DM_PROV_REQ set IS_PROMPT_FOR_FINISHED=0 where IS_PROMPT_FOR_FINISHED is null;
commit;

alter table nW_DM_PROV_REQ
      modify (IS_PROMPT_FOR_BEGINNING  not null,
               IS_PROMPT_FOR_FINISHED  not null);

 
 