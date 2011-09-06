---------------------------------------------------------------------------------------------------------------
-- 创建一个应用和Objectclass的字典
---------------------------------------------------------------------------------------------------------------
create table app_acct_objectclass_map (
  objectclass varchar(100) not null,
  app_name varchar(100) not null,
  constraint P_PK_app_acct_oc primary key (objectclass)
);
create unique index P_PKEY_app_acct_oc on app_acct_objectclass_map(objectclass);
create unique index P_PKEY_app_acct_app on app_acct_objectclass_map(app_name);

---------------------------------------------------------------------------------------------------------------
-- 插入字典数据
---------------------------------------------------------------------------------------------------------------
insert into app_acct_objectclass_map(objectclass, app_name) values('ERTKOAAPPACCOUNT', 'OA');
insert into app_acct_objectclass_map(objectclass, app_name) values('ERSYSTEMUSER', 'TIM');
insert into app_acct_objectclass_map(objectclass, app_name) values('ERITAMACCOUNT', 'TAM');
insert into app_acct_objectclass_map(objectclass, app_name) values('ERTKITSMACCOUNT', 'ITSM');
insert into app_acct_objectclass_map(objectclass, app_name) values('ERTKBPMAPPACCOUNT', 'BPM');
insert into app_acct_objectclass_map(objectclass, app_name) values('ERTKEBAACCOUNT', 'EBA');
insert into app_acct_objectclass_map(objectclass, app_name) values('ERTKBASACCOUNT', 'BAS');
insert into app_acct_objectclass_map(objectclass, app_name) values('ERTKFOLAPPACCOUNT', 'FOL');
insert into app_acct_objectclass_map(objectclass, app_name) values('ERTKFEACCOUNT', 'FE');
insert into app_acct_objectclass_map(objectclass, app_name) values('ERSAPNWACCOUNT', 'SAP');
insert into app_acct_objectclass_map(objectclass, app_name) values('ERIDIOS400ACCOUNT', 'CSC');
insert into app_acct_objectclass_map(objectclass, app_name) values('ERTKTMSACCOUNT', 'TMS');
insert into app_acct_objectclass_map(objectclass, app_name) values('ERTKKMAPPACCOUNT', 'KM');

---------------------------------------------------------------------------------------------------------------
-- 所有系统所有帐号的清单视图
---------------------------------------------------------------------------------------------------------------
drop view v_account;
create view v_account as
select
    oc.EID,
    eu.eruid uid, -- 帐号的UID
    cn.cn as cn, -- 帐号的姓名
    default_status(status.eraccountstatus) as status, -- 帐号的状态1表示禁用, 0表示启用
    erlaststatuschangedate.ERLASTSTATUSCHAN, -- 表示帐号状态的变更日期
    entry.create_timestamp as createTimestamp, -- 帐号创建时间
    owner.OWNER as person_dn, -- 帐号所属的人员的DN
    app_map.app_name as app_name
from
    ERUID eu inner join OBJECTCLASS oc on eu.eid=oc.eid
                     inner join app_acct_objectclass_map app_map on app_map.objectclass=oc.objectclass
                     left join OWNER owner on eu.eid=owner.eid
                     left join ERACCOUNTSTATUS status on eu.eid=status.eid
                     left join cn cn on eu.eid=cn.eid
                     left join erlaststatuschangedate on eu.eid=erlaststatuschangedate.eid
                     left join ldap_entry entry on eu.eid=entry.eid
;


-- ---------------------------------------------------
-- ---------------------------------------------------
-- TKPerson View
-- ---------------------------------------------------
-- ---------------------------------------------------
drop view v_tkperson;
create view v_tkperson as
select
    oc.EID as eid,
    uid.uid as uid,
    empnum.employeenumber as employeenumber,
    cn.cn as cn,
    default_status(status.ERPERSONSTATUS) as status,
    ou.ou as ou,
    o.o as o,
    cdate.ERCREATEDATE as CREATEDATE,
    gid.erglobalid as erglobalid,
    oaorgcodepath.TKOAORGCODEPATH as TKOAORGCODEPATH,
    oaorgcode.TKOAORGCODE as TKOAORGCODE,
    orgcodepath.TKORGCODEPATH as TKORGCODEPATH,
    departmentnumber.DEPARTMENTNUMBER as DEPARTMENTNUMBER,
    le.dn as dn
from
    TIMLDAP.LDAP_ENTRY le inner join TIMLDAP.OBJECTCLASS oc on le.eid=oc.eid 
                     left join TIMLDAP.ERGLOBALID gid on le.eid=gid.eid 
                     left join TIMLDAP.EMPLOYEENUMBER empnum on le.eid=empnum.eid
                     left join TIMLDAP.UID uid on le.eid=uid.eid
                     left join TIMLDAP.CN cn on le.eid=cn.eid
                     left join TIMLDAP.ERPERSONSTATUS status on le.eid=status.eid
					 left join TIMLDAP.O o on le.eid=o.eid
                     left join TIMLDAP.OU ou on le.eid=ou.eid
                     left join TIMLDAP.ERCREATEDATE cdate on le.eid=cdate.eid
                     left join TIMLDAP.TKOAORGCODEPATH oaorgcodepath on le.eid=oaorgcodepath.eid
                     left join TIMLDAP.TKOAORGCODE oaorgcode on le.eid=oaorgcode.eid
                     left join TIMLDAP.TKORGCODEPATH orgcodepath on le.eid=orgcodepath.eid
                     left join TIMLDAP.DEPARTMENTNUMBER departmentnumber on le.eid=departmentnumber.eid
where 
 oc.OBJECTCLASS='TKPERSON';

-- select count(*) from v_tkperson;
-- select status, count(*) from v_tkperson group by status;

-- ---------------------------------------------------
-- Org View
-- ---------------------------------------------------
drop view V_ORG_VIEW;
create view V_ORG_VIEW as 
select
    TKORGANIZATIONCODE.EID,
    TKORGANIZATIONCODE.TKORGANIZATIONCO as HR_CODE, -- 
    OU.OU as HR_NAME, -- 
    TKORGCODEPATH.TKORGCODEPATH as HR_CODE_PATH,
    TKORGNAMEPATH.TKORGNAMEPATH as HR_NAME_PATH,
    TKORGANIZATIONIDOA.TKORGANIZATIONID as OA_CODE,
    TKORGANIZATIONIDSAP.TKORGANIZATIONID as SAP_CODE,
    TKORGANIZATIONIDBAS.TKORGANIZATIONID as BAS_CODE,
    TKORGANIZATIONIDBPM.TKORGANIZATIONID as BPM_CODE,
    TKORGANIZATIONIDCSC.TKORGANIZATIONID as CSC_CODE,
    TKORGANIZATIONIDEBA.TKORGANIZATIONID as EBA_CODE,
    TKORGANIZATIONIDCSCFE.TKORGANIZATIONID as CSCFE_CODE,
    TKORGANIZATIONIDFAP.TKORGANIZATIONID as FAP_CODE,
    TKORGANIZATIONIDFOL.TKORGANIZATIONID as FOL_CODE,
    TKORGANIZATIONIDITSM.TKORGANIZATIONID as ITSM_CODE,
    -- 0-公司, 1-机构, 2-部门, 3-虚拟组织
    TKORGANIZATIONCATEGORY.TKORGANIZATIONCA as HR_ORG_CATEGORY,
    -- 0 - 运营, 1 - 在筹, 2-撤销, 3-休眠
    TKORGSTATUS.TKORGSTATUS as HR_ORG_STATUS, 
    TKORGLOCATION.TKORGLOCATION as HR_ORG_LOCATION,
    -- 创建时间
    entry.create_timestamp as createTimestamp,
    parent_entry.dn as parent_dn,
    parent_ORG_CODE.TKORGANIZATIONCO as PARENT_ORG_CODE
from
    TKORGANIZATIONCODE
    inner join TKORGNAMEPATH on TKORGANIZATIONCODE.EID=TKORGNAMEPATH.EID
    inner join TKORGCODEPATH on TKORGANIZATIONCODE.EID=TKORGCODEPATH.EID
    inner join ldap_entry entry on TKORGANIZATIONCODE.EID=entry.eid
    left join OU on TKORGANIZATIONCODE.EID=OU.EID
    left join TKORGANIZATIONIDOA on TKORGANIZATIONCODE.EID=TKORGANIZATIONIDOA.EID
    left join TKORGANIZATIONIDBAS on TKORGANIZATIONCODE.EID=TKORGANIZATIONIDBAS.EID
    left join TKORGANIZATIONIDBPM on TKORGANIZATIONCODE.EID=TKORGANIZATIONIDBPM.EID
    left join TKORGANIZATIONIDCSC on TKORGANIZATIONCODE.EID=TKORGANIZATIONIDCSC.EID
    left join TKORGANIZATIONIDCSCFE on TKORGANIZATIONCODE.EID=TKORGANIZATIONIDCSCFE.EID
    left join TKORGANIZATIONIDEBA on TKORGANIZATIONCODE.EID=TKORGANIZATIONIDEBA.EID
    left join TKORGANIZATIONIDFAP on TKORGANIZATIONCODE.EID=TKORGANIZATIONIDFAP.EID
    left join TKORGANIZATIONIDFOL on TKORGANIZATIONCODE.EID=TKORGANIZATIONIDFOL.EID
    left join TKORGANIZATIONIDITSM on TKORGANIZATIONCODE.EID=TKORGANIZATIONIDITSM.EID
    left join TKORGANIZATIONIDSAP on TKORGANIZATIONCODE.EID=TKORGANIZATIONIDSAP.EID
    left join TKORGANIZATIONCATEGORY on TKORGANIZATIONCODE.EID=TKORGANIZATIONCATEGORY.EID
    left join TKORGSTATUS on TKORGANIZATIONCODE.EID=TKORGSTATUS.EID
    left join TKORGLOCATION on TKORGANIZATIONCODE.EID=TKORGLOCATION.EID
    left join ERPARENT parentdn on TKORGANIZATIONCODE.EID=parentdn.EID
    left join ldap_entry parent_entry on parent_entry.DN=parentdn.ERPARENT
    left join TKORGANIZATIONCODE parent_ORG_CODE on parent_ORG_CODE.EID=parent_entry.EID
where TKORGANIZATIONCODE.TKORGANIZATIONCO like 'T%';



-- ---------------------------------------------------
-- 1. OA Account View, owser有值的表示为非孤儿帐号
-- ---------------------------------------------------
drop view v_oa_account;
create view v_oa_account as
select
    oc.EID as eid,
    eu.eruid as uid, -- 帐号的UID
    cn.cn as cn, -- 帐号的姓名
    default_status(status.eraccountstatus) as status, -- 帐号的状态1表示禁用, 0表示启用,
    erlaststatuschangedate.ERLASTSTATUSCHAN, -- 表示帐号状态的变更日期
    owner.OWNER as person_dn -- 帐号所属的人员的DN
from
    TIMLDAP.ERUID eu inner join TIMLDAP.OBJECTCLASS oc on eu.eid=oc.eid  
                     left join TIMLDAP.OWNER owner on eu.eid=owner.eid
                     left join TIMLDAP.ERACCOUNTSTATUS status on eu.eid=status.eid
                     left join TIMLDAP.cn cn on eu.eid=cn.eid
                     left join TIMLDAP.erlaststatuschangedate on eu.eid=TIMLDAP.erlaststatuschangedate.eid
where 
 OBJECTCLASS='ERTKOAAPPACCOUNT'
;

-- select erlaststatuschangedate.ERLASTSTATUSCHAN from TIMLDAP.erlaststatuschangedate statustime
-- select count(*) from v_oa_account;
-- select * from v_oa_account;
-- select UID,CN,STATUS,ERLASTSTATUSCHAN from v_oa_account where status=1 and person_dn is not null;

-- ---------------------------------------------------
-- ---------------------------------------------------
-- 2. TIM Account View, owser有值的表示为非孤儿帐号
-- ---------------------------------------------------
-- ---------------------------------------------------
drop view v_tim_account;
create view v_tim_account as
select
    oc.EID,
    eu.eruid uid, -- 帐号的UID
    cn.cn as cn, -- 帐号的姓名
    default_status(status.eraccountstatus) as status, -- 帐号的状态1表示禁用, 0表示启用
    erlaststatuschangedate.ERLASTSTATUSCHAN, -- 表示帐号状态的变更日期
    owner.OWNER as person_dn -- 帐号所属的人员的DN
from
    TIMLDAP.ERUID eu inner join TIMLDAP.OBJECTCLASS oc on eu.eid=oc.eid  
                     left join TIMLDAP.OWNER owner on eu.eid=owner.eid
                     left join TIMLDAP.ERACCOUNTSTATUS status on eu.eid=status.eid
                     left join TIMLDAP.cn cn on eu.eid=cn.eid
                     left join TIMLDAP.erlaststatuschangedate on eu.eid=TIMLDAP.erlaststatuschangedate.eid
where 
 OBJECTCLASS='ERSYSTEMUSER'
;

-- select count(*) from v_tim_account;

-- ---------------------------------------------------
-- ---------------------------------------------------
-- 3. TAM Account View, owser有值的表示为非孤儿帐号
-- ---------------------------------------------------
-- ---------------------------------------------------
drop view v_tam_account;
create view v_tam_account as
select
    oc.EID,
    eu.eruid uid, -- 帐号的UID
    cn.cn as cn, -- 帐号的姓名
    default_status(status.eraccountstatus) as status, -- 帐号的状态1表示禁用, 0表示启用
    erlaststatuschangedate.ERLASTSTATUSCHAN, -- 表示帐号状态的变更日期
    owner.OWNER as person_dn -- 帐号所属的人员的DN
from
    TIMLDAP.ERUID eu inner join TIMLDAP.OBJECTCLASS oc on eu.eid=oc.eid  
                     left join TIMLDAP.OWNER owner on eu.eid=owner.eid
                     left join TIMLDAP.ERACCOUNTSTATUS status on eu.eid=status.eid
                     left join TIMLDAP.CN cn on eu.eid=cn.eid
                     left join TIMLDAP.erlaststatuschangedate on eu.eid=TIMLDAP.erlaststatuschangedate.eid
where 
 OBJECTCLASS='ERITAMACCOUNT'
;

-- select count(*) from v_tam_account;

-- ---------------------------------------------------
-- ---------------------------------------------------
-- 4. ITSM Account View, owser有值的表示为非孤儿帐号
-- ---------------------------------------------------
-- ---------------------------------------------------
drop view v_itsm_account;
create view v_itsm_account as
select
    oc.EID,
    eu.eruid uid, -- 帐号的UID
    cn.cn as cn, -- 帐号的姓名
    default_status(status.eraccountstatus) as status, -- 帐号的状态1表示禁用, 0表示启用
    erlaststatuschangedate.ERLASTSTATUSCHAN, -- 表示帐号状态的变更日期
    owner.OWNER as person_dn -- 帐号所属的人员的DN
from
    TIMLDAP.ERUID eu inner join TIMLDAP.OBJECTCLASS oc on eu.eid=oc.eid  
                     left join TIMLDAP.OWNER owner on eu.eid=owner.eid
                     left join TIMLDAP.ERACCOUNTSTATUS status on eu.eid=status.eid
                     left join TIMLDAP.CN cn on eu.eid=cn.eid
                     left join TIMLDAP.erlaststatuschangedate on eu.eid=TIMLDAP.erlaststatuschangedate.eid
where 
 OBJECTCLASS='ERTKITSMACCOUNT'
;

-- select count(*) from v_itsm_account;

-- ---------------------------------------------------
-- ---------------------------------------------------
-- 5. BPM Account View, owser有值的表示为非孤儿帐号
-- ---------------------------------------------------
-- ---------------------------------------------------
drop view v_bpm_account;
create view v_bpm_account as
select
    oc.EID,
    eu.eruid uid, -- 帐号的UID
    cn.cn as cn, -- 帐号的姓名
    default_status(status.eraccountstatus) as status, -- 帐号的状态1表示禁用, 0表示启用
    erlaststatuschangedate.ERLASTSTATUSCHAN, -- 表示帐号状态的变更日期
    owner.OWNER as person_dn -- 帐号所属的人员的DN
from
    TIMLDAP.ERUID eu inner join TIMLDAP.OBJECTCLASS oc on eu.eid=oc.eid  
                     left join TIMLDAP.OWNER owner on eu.eid=owner.eid
                     left join TIMLDAP.ERACCOUNTSTATUS status on eu.eid=status.eid
                     left join TIMLDAP.CN cn on eu.eid=cn.eid
                     left join TIMLDAP.erlaststatuschangedate on eu.eid=TIMLDAP.erlaststatuschangedate.eid
where 
 OBJECTCLASS='ERTKBPMAPPACCOUNT'
;

-- select count(*) from v_bpm_account;

-- ---------------------------------------------------
-- ---------------------------------------------------
-- 6. EBA Account View, owser有值的表示为非孤儿帐号
-- ---------------------------------------------------
-- ---------------------------------------------------
drop view v_eba_account;
create view v_eba_account as
select
    oc.EID,
    eu.eruid uid, -- 帐号的UID
    cn.cn as cn, -- 帐号的姓名
    default_status(status.eraccountstatus) as status, -- 帐号的状态1表示禁用, 0表示启用
    erlaststatuschangedate.ERLASTSTATUSCHAN, -- 表示帐号状态的变更日期
    owner.OWNER as person_dn -- 帐号所属的人员的DN
from
    TIMLDAP.ERUID eu inner join TIMLDAP.OBJECTCLASS oc on eu.eid=oc.eid  
                     left join TIMLDAP.OWNER owner on eu.eid=owner.eid
                     left join TIMLDAP.ERACCOUNTSTATUS status on eu.eid=status.eid
                     left join TIMLDAP.CN cn on eu.eid=cn.eid
                     left join TIMLDAP.erlaststatuschangedate on eu.eid=TIMLDAP.erlaststatuschangedate.eid
where 
 OBJECTCLASS='ERTKEBAACCOUNT'
;

-- select count(*) from v_eba_account;


-- ---------------------------------------------------
-- ---------------------------------------------------
-- 7. BAS Account View, owser有值的表示为非孤儿帐号
-- ---------------------------------------------------
-- ---------------------------------------------------
drop view v_bas_account;
create view v_bas_account as
select
    oc.EID,
    eu.eruid uid, -- 帐号的UID
    cn.cn as cn, -- 帐号的姓名
    default_status(status.eraccountstatus) as status, -- 帐号的状态1表示禁用, 0表示启用
    erlaststatuschangedate.ERLASTSTATUSCHAN, -- 表示帐号状态的变更日期
    owner.OWNER as person_dn -- 帐号所属的人员的DN
from
    TIMLDAP.ERUID eu inner join TIMLDAP.OBJECTCLASS oc on eu.eid=oc.eid  
                     left join TIMLDAP.OWNER owner on eu.eid=owner.eid
                     left join TIMLDAP.ERACCOUNTSTATUS status on eu.eid=status.eid
                     left join TIMLDAP.CN cn on eu.eid=cn.eid
                     left join TIMLDAP.erlaststatuschangedate on eu.eid=TIMLDAP.erlaststatuschangedate.eid
where 
 OBJECTCLASS='ERTKBASACCOUNT'
;

-- select count(*) from v_bas_account;


-- ---------------------------------------------------
-- ---------------------------------------------------
-- 8. FOL Account View, owser有值的表示为非孤儿帐号
-- ---------------------------------------------------
-- ---------------------------------------------------
drop view v_fol_account;
create view v_fol_account as
select
    oc.EID,
    eu.eruid uid, -- 帐号的UID
    cn.cn as cn, -- 帐号的姓名
    default_status(status.eraccountstatus) as status, -- 帐号的状态1表示禁用, 0表示启用
    erlaststatuschangedate.ERLASTSTATUSCHAN, -- 表示帐号状态的变更日期
    owner.OWNER as person_dn -- 帐号所属的人员的DN
from
    TIMLDAP.ERUID eu inner join TIMLDAP.OBJECTCLASS oc on eu.eid=oc.eid  
                     left join TIMLDAP.OWNER owner on eu.eid=owner.eid
                     left join TIMLDAP.ERACCOUNTSTATUS status on eu.eid=status.eid
                     left join TIMLDAP.CN cn on eu.eid=cn.eid
                     left join TIMLDAP.erlaststatuschangedate on eu.eid=TIMLDAP.erlaststatuschangedate.eid
where 
 OBJECTCLASS='ERTKFOLAPPACCOUNT'
;

-- select count(*) from v_fol_account;


-- ---------------------------------------------------
-- ---------------------------------------------------
-- 9. FE Account View, owser有值的表示为非孤儿帐号
-- ---------------------------------------------------
-- ---------------------------------------------------
drop view v_fe_account;
create view v_fe_account as
select
    oc.EID,
    eu.eruid uid, -- 帐号的UID
    cn.cn as cn, -- 帐号的姓名
    default_status(status.eraccountstatus) as status, -- 帐号的状态1表示禁用, 0表示启用
    erlaststatuschangedate.ERLASTSTATUSCHAN, -- 表示帐号状态的变更日期
    owner.OWNER as person_dn -- 帐号所属的人员的DN
from
    TIMLDAP.ERUID eu inner join TIMLDAP.OBJECTCLASS oc on eu.eid=oc.eid  
                     left join TIMLDAP.OWNER owner on eu.eid=owner.eid
                     left join TIMLDAP.ERACCOUNTSTATUS status on eu.eid=status.eid
                     left join TIMLDAP.CN cn on eu.eid=cn.eid
                     left join TIMLDAP.erlaststatuschangedate on eu.eid=TIMLDAP.erlaststatuschangedate.eid
where 
 OBJECTCLASS='ERTKFEACCOUNT'
;

-- select count(*) from v_fe_account;


-- ---------------------------------------------------
-- ---------------------------------------------------
-- 10. SAP Account View, owser有值的表示为非孤儿帐号
-- ---------------------------------------------------
-- ---------------------------------------------------
drop view v_sap_account;
create view v_sap_account as
select
    oc.EID,
    eu.eruid uid, -- 帐号的UID
    cn.ERSAPNWSURNAME as cn, -- 帐号的姓名
    default_status(status.eraccountstatus) as status, -- 帐号的状态1表示禁用, 0表示启用
    erlaststatuschangedate.ERLASTSTATUSCHAN, -- 表示帐号状态的变更日期
    owner.OWNER as person_dn -- 帐号所属的人员的DN
from
    TIMLDAP.ERUID eu inner join TIMLDAP.OBJECTCLASS oc on eu.eid=oc.eid  
                     left join TIMLDAP.OWNER owner on eu.eid=owner.eid
                     left join TIMLDAP.ERACCOUNTSTATUS status on eu.eid=status.eid
                     left join TIMLDAP.ERSAPNWSURNAME cn on eu.eid=cn.eid
                     left join TIMLDAP.erlaststatuschangedate on eu.eid=TIMLDAP.erlaststatuschangedate.eid
where 
 OBJECTCLASS='ERSAPNWACCOUNT'
;

-- select count(*) from v_sap_account;

-- ---------------------------------------------------
-- ---------------------------------------------------
-- 11. CSC Account View, owser有值的表示为非孤儿帐号
-- ---------------------------------------------------
-- ---------------------------------------------------
drop view v_csc_account;
create view v_csc_account as
select
    oc.EID,
    eu.eruid uid, -- 帐号的UID
    ertext.EROS400TEXT as cn, -- 帐号的姓名
    default_status(status.eraccountstatus) as status, -- 帐号的状态1表示禁用, 0表示启用
    erlaststatuschangedate.ERLASTSTATUSCHAN, -- 表示帐号状态的变更日期
    owner.OWNER as person_dn -- 帐号所属的人员的DN
from
    TIMLDAP.ERUID eu inner join TIMLDAP.OBJECTCLASS oc on eu.eid=oc.eid  
                     left join TIMLDAP.OWNER owner on eu.eid=owner.eid
                     left join TIMLDAP.ERACCOUNTSTATUS status on eu.eid=status.eid
                     left join TIMLDAP.EROS400TEXT ertext on eu.eid=ertext.eid
                     left join TIMLDAP.erlaststatuschangedate on eu.eid=TIMLDAP.erlaststatuschangedate.eid
where 
 OBJECTCLASS='ERIDIOS400ACCOUNT'
;

-- select count(*) from v_csc_account;

-- ---------------------------------------------------
-- ---------------------------------------------------
-- 12. TMS Account View, owser有值的表示为非孤儿帐号
-- ---------------------------------------------------
-- ---------------------------------------------------
drop view v_tms_account;
create view v_tms_account as
select
    oc.EID,
    eu.eruid uid, -- 帐号的UID
    cn.cn as cn, -- 帐号的姓名
    default_status(status.eraccountstatus) as status, -- 帐号的状态1表示禁用, 0表示启用
    erlaststatuschangedate.ERLASTSTATUSCHAN, -- 表示帐号状态的变更日期
    owner.OWNER as person_dn -- 帐号所属的人员的DN
from
    TIMLDAP.ERUID eu inner join TIMLDAP.OBJECTCLASS oc on eu.eid=oc.eid  
                     left join TIMLDAP.OWNER owner on eu.eid=owner.eid
                     left join TIMLDAP.ERACCOUNTSTATUS status on eu.eid=status.eid
                     left join TIMLDAP.CN cn on eu.eid=cn.eid
                     left join TIMLDAP.erlaststatuschangedate on eu.eid=TIMLDAP.erlaststatuschangedate.eid
where 
 OBJECTCLASS='ERTKTMSACCOUNT'
;

-- select count(*) from v_tms_account;
-- select status,count(*) from v_tms_account group by status;

-- ---------------------------------------------------
-- ---------------------------------------------------
-- 13. KM Account View, owser有值的表示为非孤儿帐号
-- ---------------------------------------------------
-- ---------------------------------------------------
drop view v_km_account;
create view v_km_account as
select
    oc.EID,
    eu.eruid uid, -- 帐号的UID
    cn.cn as cn, -- 帐号的姓名
    default_status(status.eraccountstatus) as status, -- 帐号的状态1表示禁用, 0表示启用
    erlaststatuschangedate.ERLASTSTATUSCHAN, -- 表示帐号状态的变更日期
    owner.OWNER as person_dn -- 帐号所属的人员的DN
from
    TIMLDAP.ERUID eu inner join TIMLDAP.OBJECTCLASS oc on eu.eid=oc.eid  
                     left join TIMLDAP.OWNER owner on eu.eid=owner.eid
                     left join TIMLDAP.ERACCOUNTSTATUS status on eu.eid=status.eid
                     left join TIMLDAP.CN cn on eu.eid=cn.eid
                     left join TIMLDAP.erlaststatuschangedate on eu.eid=TIMLDAP.erlaststatuschangedate.eid
where 
 OBJECTCLASS='ERTKKMAPPACCOUNT'
;

-- select count(*) from v_km_account;
-- select status,count(*) from v_km_account group by status;


-- ---------------------------------------------------
--
-- 各个人员拥有的帐号明细
--
-- ---------------------------------------------------
drop view v_person_accounts;
create view v_person_accounts as
select 
 p.uid as person_uid,
 p.employeenumber,
 p.cn as person_cn,
 p.status as person_status,
 p.ou as person_ou,
 oa.uid as oa_uid,
 oa.cn as oa_cn,
 oa.status as oa_status,
 bas.uid as bas_uid,
 bas.cn as bas_cn,
 bas.status as bas_status,
 eba.uid as eba_uid,
 eba.cn as eba_cn,
 eba.status as eba_status,
 sap.uid as sap_uid,
 sap.cn as sap_cn,
 sap.status as sap_status,
 csc.uid as csc_uid,
 csc.cn as csc_cn,
 csc.status as csc_status,
 fe.uid as fe_uid,
 fe.cn as fe_cn,
 fe.status as fe_status,
 fol.uid as fol_uid,
 fol.cn as fol_cn,
 fol.status as fol_status,
 bpm.uid as bpm_uid,
 bpm.cn as bpm_cn,
 bpm.status as bpm_status,
 itsm.uid as itsm_uid,
 itsm.cn as itsm_cn,
 itsm.status as itsm_status,
 tms.uid as tms_uid,
 tms.cn as tms_cn,
 tms.status as tms_status,
 km.uid as km_uid,
 km.cn as km_cn,
 km.status as km_status
from 
  TIMLDAP.v_tkperson p left join TIMLDAP.v_oa_account oa on p.dn=oa.person_dn
                       left join TIMLDAP.V_BAS_account bas on p.dn=bas.person_dn
                       left join TIMLDAP.V_EBA_account eba on p.dn=eba.person_dn
                       left join TIMLDAP.V_SAP_account sap on p.dn=sap.person_dn
                       left join TIMLDAP.V_CSC_account csc on p.dn=csc.person_dn
                       left join TIMLDAP.V_FE_account fe on p.dn=fe.person_dn
                       left join TIMLDAP.V_FOL_account fol on p.dn=fol.person_dn
                       left join TIMLDAP.V_BPM_account bpm on p.dn=bpm.person_dn
                       left join TIMLDAP.V_ITSM_account itsm on p.dn=itsm.person_dn
                       left join TIMLDAP.V_TMS_account tms on p.dn=tms.person_dn
                       left join TIMLDAP.V_KM_account km on p.dn=km.person_dn
;


-- select * from v_person_accounts order by person_uid;
-- select * from v_person_accounts where employeenumber<>'0000043' and employeenumber<>'0000137' order by employeenumber ;
-- select * from v_person_accounts order by employeenumber ;
-- select * from v_person_accounts where person_uid='YANGLE01';


-- ---------------------------------------------------
--
-- 员工号对应多个虚拟人的清单
--
-- ---------------------------------------------------
drop view v_multiple_person;
create view v_multiple_person as
select employeenumber, uid
from TIMLDAP.v_tkperson
where employeenumber in (
  select employeenumber
  from TIMLDAP.v_tkperson
  group by employeenumber having count(*) > 1
);


7.2.  查询举例
-- 查询OA组织机构与所在HR组织的OA组织机构不一致的人员数据
select 
 p.uid,
 p.cn,
 p.TKOAORGCODE as oa_org_code_from_person,
 o.oa_code as oa_org_code_from_containter,
 o.hr_name_path as hr_org
from 
 v_tkperson p left join v_tk_org_view o on o.hr_code=p.DEPARTMENTNUMBER
where 
 p.TKOAORGCODE<>o.oa_code
order by o.hr_name_path;

-- --------------------------------------------------------------
-- 报表相关数据计算
-- --------------------------------------------------------------

-- 人员总数及禁用和启用用户的数量
select count(*) from v_tkperson;
select p.status, count(*) from v_tkperson p group by p.status;

-- 各个系统孤儿帐号和绑定帐号分布
-- OA帐号总量
select count(*) from v_oa_account;
-- OA绑定帐号总量及启用、禁用的数量
select count(*) from v_oa_account where v_oa_account.person_dn is not null;
select status, count(*) from v_oa_account where v_oa_account.person_dn is not null group by status;
-- OA孤儿帐号总量及启用、禁用的数量
select count(*) from v_oa_account where v_oa_account.person_dn is null;
select status, count(*) from v_oa_account where v_oa_account.person_dn is null group by status;
select count(*) from v_oa_account where status=0;
select count(*) from v_oa_account where status=1;

-- sap帐号总量
select count(*) from v_sap_account;
-- sap绑定帐号总量及启用、禁用的数量
select count(*) from v_sap_account where v_sap_account.person_dn is not null;
select status, count(*) from v_sap_account where v_sap_account.person_dn is not null group by status;
-- sap孤儿帐号总量及启用、禁用的数量
select count(*) from v_sap_account where v_sap_account.person_dn is null;
select status, count(*) from v_sap_account where v_sap_account.person_dn is null group by status;
select count(*) from v_sap_account where status=0;
select count(*) from v_sap_account where status=1;

-- bas帐号总量
select count(*) from v_bas_account;
-- bas绑定帐号总量及启用、禁用的数量
select count(*) from v_bas_account where v_bas_account.person_dn is not null;
select status, count(*) from v_bas_account where v_bas_account.person_dn is not null group by status;
-- bas孤儿帐号总量及启用、禁用的数量
select count(*) from v_bas_account where v_bas_account.person_dn is null;
select status, count(*) from v_bas_account where v_bas_account.person_dn is null group by status;
select count(*) from v_bas_account where status=0;
select count(*) from v_bas_account where status=1;

-- eba帐号总量
select count(*) from v_eba_account;
-- eba绑定帐号总量及启用、禁用的数量
select count(*) from v_eba_account where v_eba_account.person_dn is not null;
select status, count(*) from v_eba_account where v_eba_account.person_dn is not null group by status;
-- eba孤儿帐号总量及启用、禁用的数量
select count(*) from v_eba_account where v_eba_account.person_dn is null;
select status, count(*) from v_eba_account where v_eba_account.person_dn is null group by status;
select count(*) from v_eba_account where status=0;
select count(*) from v_eba_account where status=1;

-- csc帐号总量
select count(*) from v_csc_account;
-- csc绑定帐号总量及启用、禁用的数量
select count(*) from v_csc_account where v_csc_account.person_dn is not null;
select status, count(*) from v_csc_account where v_csc_account.person_dn is not null group by status;
-- csc孤儿帐号总量及启用、禁用的数量
select count(*) from v_csc_account where v_csc_account.person_dn is null;
select status, count(*) from v_csc_account where v_csc_account.person_dn is null group by status;
select count(*) from v_csc_account where status=0;
select count(*) from v_csc_account where status=1;

-- fe帐号总量
select count(*) from v_fe_account;
-- fe绑定帐号总量及启用、禁用的数量
select count(*) from v_fe_account where v_fe_account.person_dn is not null;
select status, count(*) from v_fe_account where v_fe_account.person_dn is not null group by status;
-- fe孤儿帐号总量及启用、禁用的数量
select count(*) from v_fe_account where v_fe_account.person_dn is null;
select status, count(*) from v_fe_account where v_fe_account.person_dn is null group by status;
select count(*) from v_fe_account where status=0;
select count(*) from v_fe_account where status=1;

-- fol帐号总量
select count(*) from v_fol_account;
-- fol绑定帐号总量及启用、禁用的数量
select count(*) from v_fol_account where v_fol_account.person_dn is not null;
select status, count(*) from v_fol_account where v_fol_account.person_dn is not null group by status;
-- fol孤儿帐号总量及启用、禁用的数量
select count(*) from v_fol_account where v_fol_account.person_dn is null;
select status, count(*) from v_fol_account where v_fol_account.person_dn is null group by status;
select count(*) from v_fol_account where status=0 or status is null;
select count(*) from v_fol_account where status=1;

-- bpm帐号总量
select count(*) from v_bpm_account;
-- bpm绑定帐号总量及启用、禁用的数量
select count(*) from v_bpm_account where v_bpm_account.person_dn is not null;
select status, count(*) from v_bpm_account where v_bpm_account.person_dn is not null group by status;
-- bpm孤儿帐号总量及启用、禁用的数量
select count(*) from v_bpm_account where v_bpm_account.person_dn is null;
select status, count(*) from v_bpm_account where v_bpm_account.person_dn is null group by status;
select count(*) from v_bpm_account where status=0;
select count(*) from v_bpm_account where status=1;

-- itsm帐号总量
select count(*) from v_itsm_account;
-- itsm绑定帐号总量及启用、禁用的数量
select count(*) from v_itsm_account where v_itsm_account.person_dn is not null;
select status, count(*) from v_itsm_account where v_itsm_account.person_dn is not null group by status;
-- itsm孤儿帐号总量及启用、禁用的数量
select count(*) from v_itsm_account where v_itsm_account.person_dn is null;
select status, count(*) from v_itsm_account where v_itsm_account.person_dn is null group by status;
select count(*) from v_itsm_account where status=0;
select count(*) from v_itsm_account where status=1;

-- tms帐号总量
select count(*) from v_tms_account;
-- tms绑定帐号总量及启用、禁用的数量
select count(*) from v_tms_account where v_tms_account.person_dn is not null;
select status, count(*) from v_tms_account where v_tms_account.person_dn is not null group by status;
-- tms孤儿帐号总量及启用、禁用的数量
select count(*) from v_tms_account where v_tms_account.person_dn is null;
select status, count(*) from v_tms_account where v_tms_account.person_dn is null group by status;
select count(*) from v_tms_account where status=0;
select count(*) from v_tms_account where status=1;

-- km帐号总量
select count(*) from v_km_account;
-- km绑定帐号总量及启用、禁用的数量
select count(*) from v_km_account where v_km_account.person_dn is not null;
select status, count(*) from v_km_account where v_km_account.person_dn is not null group by status;
-- km孤儿帐号总量及启用、禁用的数量
select count(*) from v_km_account where v_km_account.person_dn is null;
select status, count(*) from v_km_account where v_km_account.person_dn is null group by status;
select count(*) from v_km_account where status=0;
select count(*) from v_km_account where status=1;


-- 查询各个禁用帐号的禁用时间，禁用时间为空的表示初始回收时状态即为禁用
select 'OA', UID,CN,STATUS,ERLASTSTATUSCHAN from v_oa_account where status=1 and person_dn is not null
union
select 'CSC', UID,CN,STATUS,ERLASTSTATUSCHAN from v_csc_account where status=1 and person_dn is not null
union
select 'BAS', UID,CN,STATUS,ERLASTSTATUSCHAN from v_bas_account where status=1 and person_dn is not null
union
select 'BPM', UID,CN,STATUS,ERLASTSTATUSCHAN from v_bpm_account where status=1 and person_dn is not null
union
select 'EBA', UID,CN,STATUS,ERLASTSTATUSCHAN from v_eba_account where status=1 and person_dn is not null
union
select 'FE', UID,CN,STATUS,ERLASTSTATUSCHAN from v_fe_account where status=1 and person_dn is not null
union
select 'FOL', UID,CN,STATUS,ERLASTSTATUSCHAN from v_fol_account where status=1 and person_dn is not null
union
select 'ITSM', UID,CN,STATUS,ERLASTSTATUSCHAN from v_itsm_account where status=1 and person_dn is not null
union
select 'KM', UID,CN,STATUS,ERLASTSTATUSCHAN from v_km_account where status=1 and person_dn is not null
union
select 'SAP', UID,CN,STATUS,ERLASTSTATUSCHAN from v_sap_account where status=1 and person_dn is not null

