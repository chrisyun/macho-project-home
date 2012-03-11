create or replace view V_CP_CDR_LOG 
(LOG_ID,PHONENUMBER,WEB,MANUFACTURER,MODEL,IMEI,WORK_TYPE,STATUS,PROVINCE,COST_TYPE,FUNCTION_COST,START_TIME,END_TIME
            UNIQUE RELY DISABLE NOVALIDATE,CONSTRAINT LOG_ID_pk PRIMARY KEY (LOG_ID) RELY DISABLE NOVALIDATE)
as select   cp_audit_log_id as LOG_ID,
         device_phone_number as PHONENUMBER,
         concat('','') as WEB,
         device_manufacturer as MANUFACTURER,
         device_model as MODEL,
         concat('','') as IMEI,
         concat(profile_name, ' [ '||message_encoder||' ] ') as WORK_TYPE,
         status as STATUS,
         concat('','') as PROVINCE,
         concat('','') as COST_TYPE,
         concat('','') as FUNCTION_COST,
         to_char(time_stamp, 'YYYY-mm-dd') as START_TIME,
         to_char(time_stamp, 'YYYY-MM-DD') as END_TIME
  from nw_dm_client_prov_audit_log