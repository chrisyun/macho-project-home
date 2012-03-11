/*==============================================================*/
/* Table: nW_DM_AUDIT_LOG_TARGET                                       */
/*==============================================================*/
alter table nW_DM_AUDIT_LOG_TARGET
      add  CLIENT_PROV_TEMPLATE_ID     VARCHAR2(20);
      
alter table nW_DM_AUDIT_LOG_TARGET
       add  CLIENT_PROV_TEMPLATE_NAME  VARCHAR2(1000);
       
alter table nW_DM_AUDIT_LOG_TARGET
      add  SOFTWARE_CATEGORY_ID     VARCHAR2(20);
      
alter table nW_DM_AUDIT_LOG_TARGET
       add  SOFTWARE_CATEGORY_PARENT  VARCHAR2(1000);       
       
alter table nW_DM_AUDIT_LOG_TARGET
      add(SOFTWARE_NAME          VARCHAR2(80));
      
alter table nW_DM_AUDIT_LOG_TARGET
      add(SOFTWARE_VENDOR        VARCHAR2(80));
      
alter table nW_DM_AUDIT_LOG_TARGET
      add(SOFTWARE_CATEGORY      VARCHAR2(80));  

alter table nW_DM_AUDIT_LOG_TARGET
      add SOFTWARE_VENDOR_ID        VARCHAR2(80);
      
alter table nW_DM_AUDIT_LOG_TARGET
      add SOFTWARE_VENDOR_EXTERNAL_ID      VARCHAR2(80);   

/*==============================================================*/
/* Insert: NW_DM_AUDIT_LOG_ACTION  date                         */
/*==============================================================*/      
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Software', 'viewSoftware', 'View Software', '0');

insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Software', 'deleteSoftware', 'Delete Software', '0');

insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Software', 'createSoftware', 'Create Software', '0');

insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Software', 'editSoftware', 'Edit Software', '0');      

insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ClientProvTemplate', 'deleteClientProvTemplate', 'Delete CP Profile Template', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ClientProvTemplate', 'updateClientProvTemplate', 'Update CP Profile Templates', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ClientProvTemplate', 'createClientProvTemplate', 'Create CP Profile Template', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('SoftwareCategory', 'deleteSoftwareCategory', 'Delete SoftwareCategory', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('SoftwareCategory', 'updateSoftwareCategory', 'Update SoftwareCategory', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('SoftwareCategory', 'createSoftwareCategory', 'Create SoftwareCategory', '1');

/*==============================================================*/
/* Insert: NW_DM_AUDIT_LOG_ACTION  date                         */
/*==============================================================*/      
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('SoftwareVendor', 'viewVendor', 'View SoftwareVendor', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('SoftwareVendor', 'deleteVendor', 'Delete SoftwareVendor', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('SoftwareVendor', 'createVendor', 'Create SoftwareVendor', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('SoftwareVendor', 'updateVendor', 'Update SoftwareVendor', '0');      

/*==============================================================*/
/* commit;                                                      */
/*==============================================================*/

commit;