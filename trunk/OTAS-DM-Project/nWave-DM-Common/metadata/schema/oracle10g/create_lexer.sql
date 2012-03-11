/*==============================================================*/
-- 删除"otas_dm_portal_lexer"词法分析器
/*==============================================================*/
exec ctx_ddl.drop_preference ('otas_dm_portal_lexer');
/*==============================================================*/
--删除如下索引
/*==============================================================*/
drop index IDX_FT_SOFT_NAME;

drop index IDX_FT_SOFT_DESC;

drop index IDX_FT_VENDOR_NAME;

drop index IDX_FT_CATEGORY_NAME;

/*==============================================================*/
--建立"BASIC_LEXER"模式的名为"otas_dm_portal_lexer"词法分析器
/*==============================================================*/

exec ctx_ddl.create_preference ('otas_dm_portal_lexer', 'BASIC_LEXER');

/*==============================================================*/
-- 在"NW_DM_SOFTWARE(NAME)"、"NW_DM_SOFTWARE(DESCRIPTION)"、
-- "NW_DM_SOFTWARE_VENDOR(NAME)"、"NW_DM_SOFTWARE_CATEGORY(NAME)"
-- 表上建立如果词法分析索引
/*==============================================================*/

create index IDX_FT_SOFT_NAME on NW_DM_SOFTWARE(NAME) 
       indextype is ctxsys.context parameters('lexer otas_dm_portal_lexer');

create index IDX_FT_SOFT_DESC on NW_DM_SOFTWARE(DESCRIPTION) 
       indextype is ctxsys.context parameters('lexer otas_dm_portal_lexer');

create index IDX_FT_VENDOR_NAME on NW_DM_SOFTWARE_VENDOR(NAME) 
       indextype is ctxsys.context parameters('lexer otas_dm_portal_lexer');

create index IDX_FT_CATEGORY_NAME on NW_DM_SOFTWARE_CATEGORY(NAME) 
       indextype is ctxsys.context parameters('lexer otas_dm_portal_lexer');

/*==============================================================*/
-- 上述索引建立后，如果对上述几张表进行insert,update,delete时
-- 全文检索是检索不到新产生的记录的，所以要做如下索引：
/*==============================================================*/
-- 同步索引
EXEC CTX_DDL.SYNC_INDEX('IDX_FT_SOFT_NAME', '2M');

EXEC CTX_DDL.SYNC_INDEX('IDX_FT_SOFT_DESC', '2M');

EXEC CTX_DDL.SYNC_INDEX('IDX_FT_VENDOR_NAME', '2M');

EXEC CTX_DDL.SYNC_INDEX('IDX_FT_CATEGORY_NAME', '2M');

-- 优化索引
exec ctx_ddl.optimize_index ('IDX_FT_SOFT_NAME', 'full');

exec ctx_ddl.optimize_index ('IDX_FT_SOFT_DESC', 'full');

exec ctx_ddl.optimize_index ('IDX_FT_VENDOR_NAME', 'full');

exec ctx_ddl.optimize_index ('IDX_FT_CATEGORY_NAME', 'full');

/*==============================================================*/
-- 但是上述两种索引并不能自动执行，要通过oracle的jobs来自动每隔多长时间运行，
-- 代码如下：
-- 通过jobs自动执行同步索引: 
/*==============================================================*/

-- 建立同步job
DECLARE 
job1 number; 

--提交同步job1每隔15分钟同步

BEGIN 

DBMS_JOB.SUBMIT(:job1,'CTX_DDL.SYNC_INDEX(''IDX_FT_SOFT_NAME'', ''2M'');', SYSDATE, 'SYSDATE + (1/24/4)'); 
DBMS_JOB.run(:job1);
DBMS_JOB.SUBMIT(:job1,'CTX_DDL.SYNC_INDEX(''IDX_FT_SOFT_DESC'', ''2M'');', SYSDATE, 'SYSDATE + (1/24/4)'); 
DBMS_JOB.run(:job1);
DBMS_JOB.SUBMIT(:job1,'CTX_DDL.SYNC_INDEX(''IDX_FT_VENDOR_NAME'', ''2M'');', SYSDATE, 'SYSDATE + (1/24/4)'); 
DBMS_JOB.run(:job1);
DBMS_JOB.SUBMIT(:job1,'CTX_DDL.SYNC_INDEX(''IDX_FT_CATEGORY_NAME'', ''2M'');', SYSDATE, 'SYSDATE + (1/24/4)'); 
DBMS_JOB.run(:job1);

commit; 

END; 
/

-- 通过jobs自动执行优化索引:  

DECLARE
job2 number; --建立优化job

--提交同步job，每隔1天优化

BEGIN 

DBMS_JOB.SUBMIT(:job2,'ctx_ddl.optimize_index (''IDX_FT_SOFT_NAME'', ''full'');', SYSDATE, 'SYSDATE + 1'); 
DBMS_JOB.run(:job2);
DBMS_JOB.SUBMIT(:job2,'ctx_ddl.optimize_index (''IDX_FT_SOFT_DESC'', ''full'');', SYSDATE, 'SYSDATE + 1'); 
DBMS_JOB.run(:job2);
DBMS_JOB.SUBMIT(:job2,'ctx_ddl.optimize_index (''IDX_FT_VENDOR_NAME'', ''full'');', SYSDATE, 'SYSDATE + 1'); 
DBMS_JOB.run(:job2);
DBMS_JOB.SUBMIT(:job2,'ctx_ddl.optimize_index (''IDX_FT_CATEGORY_NAME'', ''full'');', SYSDATE, 'SYSDATE + 1'); 
DBMS_JOB.run(:job2);

commit; 

END;
/


