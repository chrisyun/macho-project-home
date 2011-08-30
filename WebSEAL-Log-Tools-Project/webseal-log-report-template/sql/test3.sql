--select * from LDAP_ENTRY
--where dn=upper('cn=users,dc=tam,dc=sgmam,dc=com')

create or replace view v_all_tam_node as
WITH  RPL_ALL(peid, eid, dn) AS
(
  SELECT ROOT.peid, ROOT.eid, ROOT.dn FROM LDAP_ENTRY ROOT WHERE ROOT.dn=upper('dc=tam,dc=sgmam,dc=com')
  UNION  ALL
  SELECT CHILD.peid, CHILD.eid, CHILD.dn FROM RPL_ALL PARENT, LDAP_ENTRY CHILD WHERE PARENT.eid=CHILD.peid
)
SELECT 
  DISTINCT peid, eid, dn
FROM RPL_ALL
;

create or replace view v_all_tam_users_node as
WITH  RPL_EXCL(peid, eid, dn) AS
(
  SELECT ROOT.peid, ROOT.eid, ROOT.dn FROM LDAP_ENTRY ROOT WHERE ROOT.dn=upper('cn=users,dc=tam,dc=sgmam,dc=com')
  UNION  ALL
  SELECT CHILD.peid, CHILD.eid, CHILD.dn FROM RPL_EXCL PARENT, LDAP_ENTRY CHILD WHERE PARENT.eid=CHILD.peid
)
SELECT 
  DISTINCT peid, eid, dn
FROM RPL_EXCL
;

select eid from v_all_tam_node
except
select eid from v_all_tam_users_node
;

select   
    oc.EID as eid,  
    uid.uid as uid,
    user_cn.cn as cn
from 
     tamldap.uid uid inner join tamldap.OBJECTCLASS oc on uid.eid=oc.eid  
                     inner join tamldap.cn user_cn on user_cn.eid=uid.eid
where 
     oc.OBJECTCLASS='INETORGPERSON' 
     and oc.eid in (
       select eid from v_all_tam_node
       except
       select eid from v_all_tam_users_node)
     
     
;


