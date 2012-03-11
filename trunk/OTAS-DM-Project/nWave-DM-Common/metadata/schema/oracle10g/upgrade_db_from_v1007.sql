/*==============================================================*/
/* Table: nW_DM_PROFILE_NODE_MAPPING                             */
/*==============================================================*/
alter table nW_DM_PROFILE_NODE_MAPPING
      add COMMAND              VARCHAR(20);
 
alter table nW_DM_PROFILE_NODE_MAPPING
      add VALUE_FORMAT         VARCHAR(100);
 
alter table nW_DM_PROFILE_NODE_MAPPING
      modify RELATIVE_NODE_PATH   VARCHAR2(1000);
 
 