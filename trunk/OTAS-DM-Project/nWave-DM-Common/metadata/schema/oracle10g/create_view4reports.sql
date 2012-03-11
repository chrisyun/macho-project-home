/*==============================================================*/
/* View: dm_rpt_device_counter                                  */
/*==============================================================*/
create or replace view dm_rpt_device_counter as
select to_char(d.CREATED_TIME, 'YYYY') as CREATED_YEAR,
       to_char(d.CREATED_TIME, 'MM') as CREATED_MONTH,
       to_char(d.CREATED_TIME, 'DD') as CREATED_DAY,
       c.NAME as CARRIER,
       v.NAME as Manufacturer,
       m.NAME as Model,
       count(d.DEVICE_ID) as TOTAL
from NW_DM_DEVICE d,NW_DM_MODEL m, NW_DM_MANUFACTURER v, NW_DM_SUBSCRIBER s, NW_DM_CARRIER c
where
     d.MODEL_ID=m.MODEL_ID
     and m.MANUFACTURER_ID=v.MANUFACTURER_ID
     and d.SUBSCRIBER_ID=s.SUBSCRIBER_ID
     and c.carrier_id=s.carrier_id
group by to_char(d.CREATED_TIME, 'YYYY'),
         to_char(d.CREATED_TIME, 'MM'),
         to_char(d.CREATED_TIME, 'DD'),
         c.name,
         v.name, 
         m.name
order by CREATED_YEAR, CREATED_MONTH, CREATED_DAY;


/*==============================================================*/
/* View: dm_rpt_job_device_status                               */
/*==============================================================*/
create or replace view dm_rpt_job_device_status as
(select j.prov_req_id    as job_id, 
       upper(j.job_mode) as job_mode, 
       j.state           as job_state, 
       j.job_type        as job_type,
       dev.device_external_id as imei,
       sub.phone_number  as msisdn,
       upper(s.state)    as device_job_status, 
       s.scheduled_time  as scheduled_time,
       carr.name         as carrier,
       v.name            as vendor,
       m.name            as model,
       pcat.name         as profile_category,
       prof.external_id  as profile,
       sv.name || ' ' || soft.name || ' ' || soft.version         as software,
       img.image_external_id as target_image,
       1                 as total
from nw_dm_device_prov_req s left join nw_dm_pr_element e on e.pr_element_id=s.pr_element_id
                             left join nw_dm_prov_req j on j.prov_req_id=e.prov_req_id
                             left join nw_dm_device dev on s.device_id=dev.device_id
                             left join nw_dm_subscriber sub on dev.subscriber_id=sub.subscriber_id
                             left join nw_dm_carrier carr on sub.carrier_id=carr.carrier_id
                             left join nw_dm_model m on dev.model_id=m.model_id
                             left join nw_dm_manufacturer v on m.manufacturer_id=v.manufacturer_id
                             left join nw_dm_job_state js on js.job_id=j.prov_req_id
                             left join nw_dm_job_assignments jassign on js.job_state_id=jassign.job_state_id
                             left join nw_dm_profile_assignment passign on jassign.profile_assignment_id=passign.profile_assignment_id
                             left join nw_dm_config_profile prof on passign.profile_id=prof.profile_id
                             left join nw_dm_profile_template prt on prt.template_id=prof.template_id
                             left join nw_dm_profile_category pcat on pcat.category_id=prt.profile_category_id 
                             left join nw_dm_software soft on soft.software_id=j.target_software_id
                             left join nw_dm_software_vendor sv on soft.vendor_id=sv.vendor_id
                             left join nw_dm_image img on j.target_image_id=img.image_id
)
union
(select j.prov_req_id    as job_id, 
       upper(j.job_mode) as job_mode, 
       j.state           as job_state, 
       j.job_type        as job_type,
       dev.device_external_id as imei,
       sub.phone_number  as msisdn,
       upper(s.status)   as device_job_status, 
       j.scheduled_time  as scheduled_time,
       carr.name         as carrier,
       v.name            as vendor,
       m.name            as model,
       pcat.name         as profile_category,
       s.profile_external_id  as profile, 
       null              as software,
       null              as target_image,
       1                 as total
from nW_CP_JOB_TARGET_DEVICE s left join nw_dm_prov_req j on j.prov_req_id=s.prov_req_id
                               left join nw_dm_device dev on s.device_id=dev.device_id
                               left join nw_dm_subscriber sub on dev.subscriber_id=sub.subscriber_id
                               left join nw_dm_carrier carr on sub.carrier_id=carr.carrier_id
                               left join nw_dm_model m on dev.model_id=m.model_id
                               left join nw_dm_manufacturer v on m.manufacturer_id=v.manufacturer_id
                               left join nw_dm_config_profile prof on prof.external_id=s.profile_external_id
                               left join nw_dm_profile_template prt on prt.template_id=prof.template_id
                               left join nw_dm_profile_category pcat on pcat.category_id=prt.profile_category_id 
);


/*==============================================================*/
/* View: dm_rpt_model_capabilities                              */
/*==============================================================*/
create or replace view dm_rpt_model_capabilities as
(
select 'dm' as type,
       v.name as vendor,
       md.name as model,
       t.name as template,
       c.name as category,
       1 as support
from nw_dm_model_profile_map m inner join nw_dm_profile_mapping mp on m.profile_mapping_id=mp.profile_mapping_id
                               inner join nw_dm_profile_template t on t.template_id=mp.template_id
                               inner join nw_dm_profile_category c on c.category_id=t.profile_category_id
                               inner join nw_dm_model md on md.model_id=m.device_model_id
                               inner join nw_dm_manufacturer v on v.manufacturer_id=md.manufacturer_id
) union (                               
select encoder as type,
       v.name as vendor,
       md.name as model,
       t.name as template,
       c.name as category,
       1 as support
from nw_dm_model_client_prov_map cpm inner join nw_dm_client_prov_template t on t.template_id=cpm.template_id
                               inner join nw_dm_profile_category c on c.category_id=t.category_id
                               inner join nw_dm_model md on md.model_id=cpm.model_id
                               inner join nw_dm_manufacturer v on v.manufacturer_id=md.manufacturer_id
)
order by type, vendor, model, category, template;


/*==============================================================*/
/* View: dm_rpt_model                                           */
/*==============================================================*/
create or replace view dm_rpt_model as
select v.name manufacturer, m.name model 
from nw_dm_model m inner join nw_dm_manufacturer v on m.manufacturer_id=v.manufacturer_id
order by manufacturer, model;

