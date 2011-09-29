drop view v_user_pwd_policy;
DROP FUNCTION func_eval_password_max_age;
DROP FUNCTION func_eval_pwd_expire_time;


CREATE  FUNCTION func_eval_password_max_age (hasPolicy INTEGER, userPwdAge INTEGER, globalPwdAge INTEGER)
RETURNS INTEGER
------------------------------------------------------------------------
-- FUNCTION£ºfunc_eval_password_max_age
-- Description£ºEvaluate password max age policy
------------------------------------------------------------------------
 NO EXTERNAL ACTION
 DETERMINISTIC
 LANGUAGE SQL
 CONTAINS SQL
 NULL CALL
 return case when (hasPolicy = 1) then
   userPwdAge
 else
   globalPwdAge
end;

CREATE  FUNCTION func_eval_pwd_expire_time (pwdAge INTEGER, PwdLastChanged TIMESTAMP)
RETURNS TIMESTAMP
------------------------------------------------------------------------
-- FUNCTION£ºfunc_eval_password_max_age
-- Description£ºEvaluate password expire time
------------------------------------------------------------------------
 NO EXTERNAL ACTION
 DETERMINISTIC
 LANGUAGE SQL
 CONTAINS SQL
 NULL CALL
 return case when (pwdAge > 0) then
   (PwdLastChanged + pwdAge SECONDS)
end;


-- Global Policy
create or replace view v_global_pwd_policy as 
select   
    oc.EID as eid,  
    passwordMaxAge.passwordMaxAge as passwordMaxAge,  
    cn.cn as cn 
from 
     tamldap.cn cn inner join tamldap.OBJECTCLASS oc on cn.eid=oc.eid  
                     left join tamldap.passwordMaxAge passwordMaxAge on oc.eid=passwordMaxAge.eid 
where 
 oc.OBJECTCLASS='EPASSWORDPOLICY' and cn.cn='DEFAULT'
;
----Global Policy----update by zhanghongxing 
create view v_global_pwd_policy as 
select   
    oc.EID as eid,  
    passwordMaxAge.passwordMaxAge as passwordMaxAge,  
    cn.cn as cn 
from 
     cn cn inner join OBJECTCLASS oc on cn.eid=oc.eid  
                     left join passwordMaxAge passwordMaxAge on oc.eid=passwordMaxAge.eid 
where 
 oc.OBJECTCLASS='EPASSWORDPOLICY' and cn.cn='DEFAULT'
;

-- User password policy
create or replace view v_user_pwd_policy as 
select   
    oc.EID as eid,  
    lower(principalName.principalName) as username,
    user_cn.cn as cn,
    secHasPolicy.secHasPolicy as HasPolicy,  -- 1 means has policy 
    secPwdLastChanged.secPwdLastChanged as PwdLastChanged,  
    passwordMaxAge.passwordMaxAge as userPasswordMaxAge,  
    secAcctValid.secAcctValid as AcctValid,  
    secDN.secDN as secDN,  
    (select passwordMaxAge from v_global_pwd_policy) as globalPasswordMaxAge ,
    func_eval_password_max_age(secHasPolicy.secHasPolicy, passwordMaxAge.passwordMaxAge, (select passwordMaxAge from v_global_pwd_policy)) as appled_pwd_max_age,
    func_eval_pwd_expire_time(func_eval_password_max_age(secHasPolicy.secHasPolicy, passwordMaxAge.passwordMaxAge, (select passwordMaxAge from v_global_pwd_policy)), secPwdLastChanged.secPwdLastChanged) as pwd_expire_time
from 
     tamldap.principalName principalName inner join tamldap.OBJECTCLASS oc on principalName.eid=oc.eid  
                     left join tamldap.secDN secDN on oc.eid=secDN.eid 
                     left join tamldap.secHasPolicy secHasPolicy on oc.eid=secHasPolicy.eid 
                     left join tamldap.secPwdLastChanged secPwdLastChanged on oc.eid=secPwdLastChanged.eid 
                     left join tamldap.secAcctValid secAcctValid on oc.eid=secAcctValid.eid 
                     left join tamldap.ldap_entry entry on oc.eid=entry.eid 
                     left join tamldap.ldap_entry policieslEntry on policieslEntry.peid=entry.eid 
                     left join tamldap.ldap_entry policyEntry on policyEntry.peid=policieslEntry.eid 
                     left join tamldap.passwordMaxAge passwordMaxAge on policyEntry.eid=passwordMaxAge.eid 
                     inner join tamldap.ldap_entry user on user.dn=secDN.secDN
                     inner join tamldap.cn user_cn on user_cn.eid=user.eid
where 
     oc.OBJECTCLASS='SECUSER'
;

-----User password policy----update by zhanghongxing-----
create view v_user_pwd_policy as 
select   
    oc.EID as eid,  
    lower(principalName.principalName) as username,
    user_cn.cn as cn,
    secHasPolicy.secHasPolicy as HasPolicy,  -- 1 means has policy 
    secPwdLastChanged.secPwdLastChanged as PwdLastChanged,  
    passwordMaxAge.passwordMaxAge as userPasswordMaxAge,  
    secAcctValid.secAcctValid as AcctValid,  
    secDN.secDN as secDN,  
    (select passwordMaxAge from v_global_pwd_policy) as globalPasswordMaxAge ,
    func_eval_password_max_age(secHasPolicy.secHasPolicy, passwordMaxAge.passwordMaxAge, (select passwordMaxAge from v_global_pwd_policy)) as appled_pwd_max_age,
    func_eval_pwd_expire_time(func_eval_password_max_age(secHasPolicy.secHasPolicy, passwordMaxAge.passwordMaxAge, (select passwordMaxAge from v_global_pwd_policy)), secPwdLastChanged.secPwdLastChanged) as pwd_expire_time
from 
     principalName principalName inner join OBJECTCLASS oc on principalName.eid=oc.eid  
                     left join secDN secDN on oc.eid=secDN.eid 
                     left join secHasPolicy secHasPolicy on oc.eid=secHasPolicy.eid 
                     left join secPwdLastChanged secPwdLastChanged on oc.eid=secPwdLastChanged.eid 
                     left join secAcctValid secAcctValid on oc.eid=secAcctValid.eid 
                     left join ldap_entry entry on oc.eid=entry.eid 
                     left join ldap_entry policieslEntry on policieslEntry.peid=entry.eid 
                     left join ldap_entry policyEntry on policyEntry.peid=policieslEntry.eid 
                     left join passwordMaxAge passwordMaxAge on policyEntry.eid=passwordMaxAge.eid 
                     inner join ldap_entry user on user.dn=secDN.secDN
                     inner join cn user_cn on user_cn.eid=user.eid
where 
     oc.OBJECTCLASS='SECUSER'
;

----------------------------------------------------------------------------------------------------------------
-- create or replace view v_all_tam_node as
-- WITH  RPL_ALL(peid, eid, dn) AS
-- (
--   SELECT ROOT.peid, ROOT.eid, ROOT.dn FROM LDAP_ENTRY ROOT WHERE ROOT.dn=upper('dc=tam,dc=sgmam,dc=com')
--   UNION  ALL
--   SELECT CHILD.peid, CHILD.eid, CHILD.dn FROM RPL_ALL PARENT, LDAP_ENTRY CHILD WHERE PARENT.eid=CHILD.peid
-- )
-- SELECT 
--   DISTINCT peid, eid, dn
-- FROM RPL_ALL
-- ;
-- 
-- create or replace view v_all_tam_users_node as
-- WITH  RPL_EXCL(peid, eid, dn) AS
-- (
--   SELECT ROOT.peid, ROOT.eid, ROOT.dn FROM LDAP_ENTRY ROOT WHERE ROOT.dn=upper('cn=users,dc=tam,dc=sgmam,dc=com')
--   UNION  ALL
--   SELECT CHILD.peid, CHILD.eid, CHILD.dn FROM RPL_EXCL PARENT, LDAP_ENTRY CHILD WHERE PARENT.eid=CHILD.peid
-- )
-- SELECT 
--   DISTINCT peid, eid, dn
-- FROM RPL_EXCL
-- ;

------------------------------------------------------------------------------------------------------------------
drop view v_tamldap_user;
create view v_tamldap_user as
select   
    eid as eid,  
    lower(username) as uid,
    lower(cn) as cn
from 
    v_user_pwd_policy;
--      tamldap.uid uid inner join tamldap.OBJECTCLASS oc on uid.eid=oc.eid  
--                      inner join tamldap.cn user_cn on user_cn.eid=uid.eid
-- where 
--      oc.OBJECTCLASS='INETORGPERSON'
--      and oc.eid in (
--        select eid from v_all_tam_node
--        except
--        select eid from v_all_tam_users_node)
-- ;
