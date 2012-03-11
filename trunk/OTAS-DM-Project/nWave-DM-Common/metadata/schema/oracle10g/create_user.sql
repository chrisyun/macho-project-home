CREATE USER otasdm
    IDENTIFIED BY otasdm DEFAULT TABLESPACE OTASDM
    TEMPORARY TABLESPACE OTASDM_TMP
;

GRANT RESOURCE, CONNECT, DBA, CTXAPP TO otasdm;

--在做全文检索时，需要给otasdm用户受予以下两个权限

grant execute on sys.dbms_job to otasdm;

grant execute on ctxsys.ctx_ddl to otasdm;


