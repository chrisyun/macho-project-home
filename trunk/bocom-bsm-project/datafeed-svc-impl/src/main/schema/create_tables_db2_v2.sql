--==============================================================
-- DBMS name:      IBM DB2 UDB 9.x Common Server
-- Created on:     2012;3;19 13:06:06
--==============================================================
drop trigger tsq_top_n
drop trigger tsq_top_n_data
drop trigger tsq_app_raw_data

alter table TOP_N_DATA drop foreign key F_Reference_1

drop table TOP_N

drop table TOP_N_DATA

drop table app_raw_data

drop sequence SEQ_APP_RAW_DID

create sequence SEQ_APP_RAW_DID increment by 1 start with 100

create table TOP_N(   TOP_N_ID             BIGINT                 not null generated by default as identity,   TOP_N_NAME           VARGRAPHIC(128)        not null,   TOP_N_DESCRIPTION    VARGRAPHIC(128),   UPLOAD_SERVER_IP     VARCHAR(128),   UPLOAD_TIME          TIMESTAMP              not null,   constraint P_Key_1 primary key (TOP_N_ID))

comment on table TOP_N is 'TOP N Data main data'

--==============================================================
-- Table: TOP_N_DATA
--==============================================================
create table TOP_N_DATA(   RANK_ID              BIGINT                 not null generated by default as identity,   TOP_N_ID             VARCHAR(64)            not null,   RANK_SEQ             INTEGER,   RANK_NAME            VARCHAR(128)           not null,   VALUE                DOUBLE,   RANK_CODE            VARCHAR(128),   constraint P_Key_1 primary key (RANK_ID))

comment on table TOP_N_DATA is 'TOP N data items'

--==============================================================
-- Table: app_raw_data
--==============================================================
create table app_raw_data(   id                 BIGINT                 not null,   dateslot           TIMESTAMP,   timeslot           TIMESTAMP,   ip                 VARCHAR(64),   metric_desc        VARCHAR(128),   value              DOUBLE,   type               VARCHAR(128),   montype            VARCHAR(128),   constraint P_Key_1 primary key (id))

alter table TOP_N_DATA   add constraint F_Reference_1 foreign key (TOP_N_ID)      references TOP_N (TOP_N_ID)      on delete restrict on update restrict


create trigger tsq_top_n no cascade before insert on TOP_N referencing new as new_ins for each row mode db2sql TOP_N_ID = next value for SEQ_APP_RAW_DID;

create trigger tsq_top_n_data no cascade before insert on TOP_N_DATA referencing new as new_ins for each row mode db2sql RANK_ID = next value for SEQ_APP_RAW_DID

create trigger tsq_app_raw_data no cascade before insert on app_raw_data referencing new as new_ins for each row mode db2sql id = next value for SEQ_APP_RAW_DID
