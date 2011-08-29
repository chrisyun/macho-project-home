create view v_tamldap_user as
select   
    oc.EID as eid,  
    uid.uid as uid,
    user_cn.cn as cn
from 
     tamldap.uid uid inner join tamldap.OBJECTCLASS oc on uid.eid=oc.eid  
                     inner join tamldap.cn user_cn on user_cn.eid=uid.eid
where 
     oc.OBJECTCLASS='INETORGPERSON'
;

select * from tamldap_user


CREATE SERVER tamldap TYPE DB2/UDB VERSION '9.7' WRAPPER DRDA AUTHORIZATION "db2admin"  PASSWORD "passw0rd" OPTIONS(DBNAME 'tamldap')


create nickname eventxml.v_res_access_event_detail for evtxmldb.db2admin.v_res_access_event_detail
select count(*) from evtxmldb.db2admin.v_res_access_event_detail


