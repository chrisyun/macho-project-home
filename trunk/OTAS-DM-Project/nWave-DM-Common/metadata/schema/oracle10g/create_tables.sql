/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     2009-2-27 14:44:25                           */
/*==============================================================*/


alter table DM_ACCESS_LOG_HEADER
   drop constraint FK_ACCESS_LOG_HEADER;

alter table DM_ACCESS_LOG_PARAMETER
   drop constraint FK_ACCESS_LOG_PARAM;

alter table DM_ENTERPRISE_SOFTWRE
   drop constraint FK_ENTER_REF_ENTER_SOFTW;

alter table DM_ENTERPRISE_SOFTWRE
   drop constraint FK_ENTER_REF_SOFTW;

alter table DM_ENTERPRISE_SUBSCRIBER
   drop constraint FK_ENTER_REF_ENTER_SUBS;

alter table DM_ENTERPRISE_SUBSCRIBER
   drop constraint FK_ENTER_RE_SUB;

alter table DM_FAVORITE_DEVICE
   drop constraint FK_FAVORITE;

alter table DM_FAVORITE_DEVICE
   drop constraint FK_FAVORITE_DEVICE;

alter table OS_CURRENTSTEP
   drop constraint nW_CURR_WFENTRY;

alter table OS_HISTORYSTEP
   drop constraint nW_HIST_WFENTRY;

alter table OS_WFENTRY
   drop constraint nW_WFENTRY_WORKFLOW;

alter table PREDEFINED_MODEL_SELECTOR
   drop constraint FK_PRED_MODE_SELTOR;

alter table PREDEFINED_MODEL_SELECTOR
   drop constraint FK_PRE_MS_SELECTED_MODELS;

alter table nW_CP_JOB_PROFILE
   drop constraint FK_CP_JOB_REF_TAR_PROF;

alter table nW_CP_JOB_PROFILE_PARAMETER
   drop constraint FK_CP_PROF_REF_PARAM;

alter table nW_CP_JOB_TARGET_DEVICE
   drop constraint FK_JOB_TARGET_DEV;

alter table nW_DM_ATTRIB_TRANSLATIONS
   drop constraint PROFILE_ATTRIB_MAP_FK;

alter table nW_DM_ATTRIB_TYPE_VALUES
   drop constraint PROFILE_ATTRIB_TYPE_VALUE_FK;

alter table nW_DM_AUDIT_LOG
   drop constraint nW_DM_LOG_ACTION_FK;

alter table nW_DM_AUDIT_LOG_TARGET
   drop constraint FK_AUDIT_LOG_TARGET;

alter table nW_DM_AUTO_JOB_NODES_DISCOVER
   drop constraint FK_AUTO_JOB_DISCOVERY_NODES;

alter table nW_DM_AUTO_JOB_PROFILE_CONFIG
   drop constraint FK_NW_DM_AU_AUTO_JOB__NW_DM_CO;

alter table nW_DM_AUTO_JOB_PROFILE_CONFIG
   drop constraint FK_AUTO_JOB_PROFILE_CONFGS;

alter table nW_DM_AUTO_PROV_JOB
   drop constraint FK_NW_DM_AU_FK_AUTO_P_NW_DM_CA;

alter table nW_DM_CARRIER
   drop constraint DM_CAR_BOOT_DM_PROF;

alter table nW_DM_CARRIER
   drop constraint DM_CAR_NAP_PROF_FK;

alter table nW_DM_CARRIER
   drop constraint FK_DM_CARRIER_PARENT;

alter table nW_DM_CARRIER
   drop constraint nW_DM_CAR_COUN_CODE_FK;

alter table nW_DM_CLIENT_PROV_TEMPLATE
   drop constraint FK_CP_TEMPLATE_CATEGORY;

alter table nW_DM_CONFIG_PROFILE
   drop constraint CONFIG_PROFILE_NAP_FK;

alter table nW_DM_CONFIG_PROFILE
   drop constraint CONFIG_PROFILE_PROXY_FK;

alter table nW_DM_CONFIG_PROFILE
   drop constraint CONFIG_PROFILE_TEMPLATE_FK;

alter table nW_DM_CONFIG_PROFILE
   drop constraint PROFILE_CARRIER_CARRIER_FK;

alter table nW_DM_DDF_NODE
   drop constraint DDF_NODE_DDF_TREE_FK;

alter table nW_DM_DDF_NODE
   drop constraint DDF_NODE_PARENT_NODE_FK;

alter table nW_DM_DDF_NODE_ACCESS_TYPE
   drop constraint DDF_NODE_ACC_TYPE_DDF_NODE_FK;

alter table nW_DM_DDF_NODE_MIME_TYPE
   drop constraint DDF_NODE_MIME_TYPE_DDF_NODE_FK;

alter table nW_DM_DEVICE
   drop constraint nW_DM_DEVICE_CURRENT_UPDATE;

alter table nW_DM_DEVICE
   drop constraint nW_DM_DEVICE_DTREE_FK;

alter table nW_DM_DEVICE
   drop constraint nW_DM_DEVICE_IMAGE;

alter table nW_DM_DEVICE
   drop constraint nW_DM_DEVICE_INP_DPR;

alter table nW_DM_DEVICE
   drop constraint nW_DM_DEVICE_MODEL_FK;

alter table nW_DM_DEVICE
   drop constraint nW_DM_DEVICE_PR_ELEM_ID;

alter table nW_DM_DEVICE
   drop constraint nW_DM_DEVICE_SUBS_FK;

alter table nW_DM_DEVICE_GROUP_DEVICE
   drop constraint nW_DM_DEV_GRP_DEV_FK;

alter table nW_DM_DEVICE_GROUP_DEVICE
   drop constraint nW_DM_DEV_GRP_GRP_FK;

alter table nW_DM_DEVICE_GROUP_DEVICE
   drop constraint nW_DM_DEV_GRP_INIT_GRP_FK;

alter table nW_DM_DEVICE_LOG
   drop constraint nW_DM_DEVICE_LOG_ACTION_FK;

alter table nW_DM_DEVICE_LOG
   drop constraint nW_DM_DEVICE_LOG_DEVICE_FK;

alter table nW_DM_DEVICE_LOG
   drop constraint nW_DM_DEVICE_LOG_JOB_FK;

alter table nW_DM_DEVICE_PROV_REQ
   drop constraint DEVICE_PROV_REQ_JOB_STATE_FK;

alter table nW_DM_DEVICE_PROV_REQ
   drop constraint FK_DEV_STAT_TARG_SOFT;

alter table nW_DM_DEVICE_PROV_REQ
   drop constraint nW_DM_DPR_DEVICE_FK;

alter table nW_DM_DEVICE_PROV_REQ
   drop constraint nW_DM_DPR_INST_IMAGE_FK;

alter table nW_DM_DEVICE_PROV_REQ
   drop constraint nW_DM_DPR_OLD_IMG_FK;

alter table nW_DM_DEVICE_PROV_REQ
   drop constraint nW_DM_DPR_PR_ELEM_FK;

alter table nW_DM_DEVICE_PROV_REQ
   drop constraint nW_DM_DPR_TO_IMG_FK;

alter table nW_DM_DISCOVERY_JOB_NODE
   drop constraint nW_DM_DISC_JOB_ROOT_NODE_FK;

alter table nW_DM_DISC_JOB_ST_NODE
   drop constraint nW_DM_DISC_JOB_ST_RT_ND_FK;

alter table nW_DM_DM_CMD
   drop constraint DM_CMD_DM_CMD_PKG_FK;

alter table nW_DM_DM_CMD
   drop constraint nW_DM_DM_COMP_FK;

alter table nW_DM_DM_CMD_RESPONSE
   drop constraint DM_CMD_RESP_PKG_RESP_PROC_FK;

alter table nW_DM_DM_JOB_ADAPTER
   drop constraint DM_JOB_ADAPTER_DEVICE_FK;

alter table nW_DM_DM_JOB_ADAPTER
   drop constraint DM_JOB_ADAPTER_DEVICE_JOB_FK;

alter table nW_DM_DM_JOB_ADAPTER
   drop constraint DM_JOB_ADAPTER_JOB_STATE_FK;

alter table nW_DM_DM_JOB_EXEC_CLIENT
   drop constraint DM_JOB_EXEC_CLI_DEV_JOB_FK;

alter table nW_DM_DM_JOB_EXEC_CLIENT
   drop constraint DM_JOB_EXEC_CLI_WFLW_JOB_ST_FK;

alter table nW_DM_DM_PKG_HANDLER
   drop constraint DM_PKG_HANDLER_CMD_PACKAGE_FK;

alter table nW_DM_DM_PKG_HANDLER
   drop constraint DM_PKG_HANDLER_CMD_RSP_BLDR_FK;

alter table nW_DM_DM_PKG_HANDLER
   drop constraint DM_PKG_HANDLER_DM_SESSION_FK;

alter table nW_DM_DM_PKG_HANDLER
   drop constraint DM_PKG_HANDLER_PKG_SENDER_FK;

alter table nW_DM_DM_PKG_RESP_PROC
   drop constraint DM_PKG_RESP_PROC_PKG_SNDR_FK;

alter table nW_DM_DM_PKG_SENDER
   drop constraint DM_PKG_SENDER_CMD_PKG_FK;

alter table nW_DM_DM_PKG_SENDER_CMD_IDS
   drop constraint DM_PKG_SNDR_CMDIDS_PKG_SNDR_FK;

alter table nW_DM_DM_RESULTS_MAP
   drop constraint DM_RESULTS_MAP_DM_CMD_RESP_FK;

alter table nW_DM_DM_SESSION
   drop constraint DM_SESSION_CMD_SESS_HANDLER_FK;

alter table nW_DM_DM_SESSION
   drop constraint DM_SESSION_CUR_PKG_HANDLER_FK;

alter table nW_DM_DM_SESSION
   drop constraint nW_DM_SESSION_SES_AUTH_FK;

alter table nW_DM_DTREE
   drop constraint nW_DM_DTREE_NODE_FK;

alter table nW_DM_DTREE_NODE
   drop constraint FK_DEVICE_PARENT_NODE;

alter table nW_DM_FW_JOB_UPDATE_PATH
   drop constraint nW_DM_JOB_PATH_ID_FK;

alter table nW_DM_FW_JOB_UPDATE_PATH
   drop constraint nW_DM_JOB_PATH_UPDATE_FK;

alter table nW_DM_GET_CMD_ITEM
   drop constraint GET_CMD_ITEM_GET_CMD_FK;

alter table nW_DM_IMAGE
   drop constraint nW_DM_IMAGE_MODEL_FK;

alter table nW_DM_IMAGE
   drop constraint nW_DM_IMG_STATUS_FK;

alter table nW_DM_IMAGE_CARRIERS
   drop constraint nW_DM_IMG_CARR_CARR_FK;

alter table nW_DM_IMAGE_CARRIERS
   drop constraint nW_DM_IMG_CARR_IMG_FK;

alter table nW_DM_IMAGE_CARRIERS
   drop constraint nW_DM_IMG_CARR_STATU_FK;

alter table nW_DM_JOB_ASSIGNMENTS
   drop constraint nW_DM_JOB_ASSGN_ID_FK;

alter table nW_DM_JOB_ASSIGNMENTS
   drop constraint nW_DM_JOB_ASSGN_STATE_ID_FK;

alter table nW_DM_JOB_STATE
   drop constraint JOB_STATE_DEV_FK;

alter table nW_DM_JOB_STATE
   drop constraint JOB_STATE_INSTALLING_IMAGE_FK;

alter table nW_DM_JOB_STATE
   drop constraint JOB_STATE_JOB_ADAPTER_FK;

alter table nW_DM_JOB_STATE
   drop constraint JOB_STATE_OLD_CURRENT_IMAGE_FK;

alter table nW_DM_JOB_STATE
   drop constraint JOB_STATE_TO_IMAGE_FK;

alter table nW_DM_JOB_STATE
   drop constraint nW_DM_JOB_CMD_PKG_FK;

alter table nW_DM_JOB_STATE
   drop constraint nW_DM_JOB_ST_PROV_FK;

alter table nW_DM_MAPPING_NODE_NAME
   drop constraint NODE_NAME_DDF_NODE_FK;

alter table nW_DM_MAPPING_NODE_NAME
   drop constraint NODE_NAME_PROFILE_MAPPING_FK;

alter table nW_DM_MODEL
   drop constraint FK_MODEL_FAMILY;

alter table nW_DM_MODEL
   drop constraint nW_DM_MODEL_MANUFACTURER_FK;

alter table nW_DM_MODEL_CHARACTER
   drop constraint FK_MODEL_CHARACTER;

alter table nW_DM_MODEL_CLIENT_PROV_MAP
   drop constraint FK_CP_TEMPL_REF_MODEL;

alter table nW_DM_MODEL_CLIENT_PROV_MAP
   drop constraint FK_MODEL_REF_CP_TEMPL;

alter table nW_DM_MODEL_DDF_TREE
   drop constraint MODEL_DDF_TREE_DDF_TREE_FK;

alter table nW_DM_MODEL_DDF_TREE
   drop constraint MODEL_DDF_TREE_DEVICE_MODEL_FK;

alter table nW_DM_MODEL_DM_BOOT_PROP
   drop constraint nW_DM_MOD_DM_PROP_BT_FK;

alter table nW_DM_MODEL_DM_PROP
   drop constraint nW_DM_MODEL_DM_PROP_FK;

alter table nW_DM_MODEL_PROFILE_MAP
   drop constraint MODEL_PROF_MAP_DEVICE_MODEL_FK;

alter table nW_DM_MODEL_PROFILE_MAP
   drop constraint MODEL_PROF_MAP_PROF_MAPPING_FK;

alter table nW_DM_MODEL_TAC
   drop constraint nW_DM_MODEL_TAC_FK;

alter table nW_DM_NODES_TO_DISCOVER
   drop constraint NODES_TO_DISC_DEV_PROV_REQ_FK;

alter table nW_DM_PATH_ELEMENT
   drop constraint nW_DM_PATH_ELEM_PR_ELEM_FK;

alter table nW_DM_PATH_ELEMENT
   drop constraint nW_DM_UPDATE_FK;

alter table nW_DM_PREV_PKG_RESP
   drop constraint nW_DM_PREV_PKG_SESS_FK;

alter table nW_DM_PROFILE_ASSIGNMENT
   drop constraint PROFILE_ASSIGNMENT_DEVICE_FK;

alter table nW_DM_PROFILE_ASSIGNMENT
   drop constraint PROFILE_ASSIGNMENT_PROFILE_FK;

alter table nW_DM_PROFILE_ASSIGN_VALUE
   drop constraint PROF_ASSIGN_VAL_ATTR_VALUE_FK;

alter table nW_DM_PROFILE_ASSIGN_VALUE
   drop constraint PROF_ASSIGN_VAL_PROF_ASSIGN_FK;

alter table nW_DM_PROFILE_ATTRIBUTE
   drop constraint PROFILE_ATTRIBUTE_FK;

alter table nW_DM_PROFILE_ATTRIBUTE
   drop constraint PROFILE_ATTRIBUTE_TYPE_FK;

alter table nW_DM_PROFILE_ATTRIB_VALUE
   drop constraint PROFILE_ATTRIB_VALUE_ATTRIB_FK;

alter table nW_DM_PROFILE_MAPPING
   drop constraint PROF_MAPPING_PROF_ROOT_NODE_FK;

alter table nW_DM_PROFILE_MAPPING
   drop constraint PROF_MAPPING_TEMPLATE_FK;

alter table nW_DM_PROFILE_NODE_MAPPING
   drop constraint PROF_NODE_MAPPING_ATTRIBUTE_FK;

alter table nW_DM_PROFILE_NODE_MAPPING
   drop constraint PROF_NODE_MAPPING_DDF_NODE_FK;

alter table nW_DM_PROFILE_NODE_MAPPING
   drop constraint PROF_NODE_MAPPING_PROF_MAP_FK;

alter table nW_DM_PROFILE_PARAMETER
   drop constraint PROFILE_PARAM_ASSIGNEMENT_FK;

alter table nW_DM_PROFILE_PARAMETER
   drop constraint PROFILE_PARAM_ATTRIBUTE_FK;

alter table nW_DM_PROFILE_PARAMETER
   drop constraint PROFILE_PARAM_LEAF_NODE_FK;

alter table nW_DM_PROFILE_TEMPLATE
   drop constraint PROFILE_TEMPLATE_PROF_CAT_FK;

alter table nW_DM_PROFILE_VALUE_ITEM
   drop constraint PROF_VAL_ITEM_PROF_ATTR_VAL_FK;

alter table nW_DM_PROFILE_VALUE_MAP
   drop constraint PROF_VALUE_MAP_ATTRIB_VALUE_FK;

alter table nW_DM_PROFILE_VALUE_MAP
   drop constraint PROF_VALUE_MAP_PROFILE_FK;

alter table nW_DM_PROV_REQ
   drop constraint FK_JOB_TARGET_SOFT;

alter table nW_DM_PROV_REQ
   drop constraint FK_PROV_PARENT_JOB;

alter table nW_DM_PROV_REQ
   drop constraint nW_DM_PR_TI_FK;

alter table nW_DM_PROV_REQ
   drop constraint nW_DM_PR_WF_FK;

alter table nW_DM_PR_ELEMENT
   drop constraint nW_DM_PR_ELEM_CAR_FK;

alter table nW_DM_PR_ELEMENT
   drop constraint nW_DM_PR_ELEM_DEV_GRP_FK;

alter table nW_DM_PR_ELEMENT
   drop constraint nW_DM_PR_ELEM_PR_FK;

alter table nW_DM_PR_ELEMENT
   drop constraint nW_DM_PR_ELEM_SI_FK;

alter table nW_DM_PR_PHONE_NUMBER
   drop constraint nW_DM_PR_PHONE_NUMBER_PR_PK;

alter table nW_DM_REPL_CMD_ITEM
   drop constraint REPL_CMD_ITEM_REPL_CMD_FK;

alter table nW_DM_SESSION_AUTH
   drop constraint nW_DM_SESSION_AUTH_DEV_FK;

alter table nW_DM_SESSION_AUTH
   drop constraint nW_DM_SESSION_AUTH_SES_FK;

alter table nW_DM_SOFTWARE
   drop constraint FK_SOFT_VENDOR;

alter table nW_DM_SOFTWARE_CATEGORIES
   drop constraint FK_CATEGORY_SOFTS;

alter table nW_DM_SOFTWARE_CATEGORIES
   drop constraint FK_SOFT_CATEGORIES;

alter table nW_DM_SOFTWARE_CATEGORY
   drop constraint FK_PARENT_SOFT_CATEGOREY;

alter table nW_DM_SOFTWARE_EVALUATE
   drop constraint FK_SOFT_EVAL;

alter table nW_DM_SOFTWARE_PACKAGE
   drop constraint FK_REFERENCE_SOFT_PKG;

alter table nW_DM_SOFTWARE_PACKAGE
   drop constraint FK_SOFT_BLOB;

alter table nW_DM_SOFTWARE_PACKAGE_MODEL
   drop constraint FK_SOFT_PKG_MOD_REF_MODEL;

alter table nW_DM_SOFTWARE_PACKAGE_MODEL
   drop constraint FK_SOFT_PKG_REF_MODEL;

alter table nW_DM_SOFTWARE_PACKAGE_MODEL
   drop constraint FK_SPKG_REF_MODEL_CLASS;

alter table nW_DM_SOFTWARE_RATING
   drop constraint FK_SOFTWARE_RATE;

alter table nW_DM_SOFTWARE_RECOMMEND
   drop constraint FK_RECOMM_CATEGORY;

alter table nW_DM_SOFTWARE_RECOMMEND
   drop constraint FK_RECOMM_SOFTWARE;

alter table nW_DM_SOFTWARE_SCREEN_SHOOT
   drop constraint FK_SCREEN_GRAPHICS;

alter table nW_DM_SOFTWARE_SCREEN_SHOOT
   drop constraint FK_SOFT_SCREEN_SHOOT;

alter table nW_DM_SOFTWARE_TRACKING_LOG
   drop constraint FK_TRCK_TARGET_SOFT;

alter table nW_DM_SOFTWARE_TRACKING_LOG
   drop constraint FK_REF_TRCK_TARGET_SOFT_PKG;

alter table nW_DM_STEPS
   drop constraint nW_DM_STEPS_WORKFLOW;

alter table nW_DM_SUBSCRIBER
   drop constraint FK_SUBSCR_SP;

alter table nW_DM_SUBSCRIBER
   drop constraint nW_DM_CARRIER_FK;

alter table nW_DM_SUBSCRIBER
   drop constraint nW_DM_NEW_CARRIER_FK;

alter table nW_DM_UPDATE
   drop constraint nW_DM_UPDATE_BLOB_FK;

alter table nW_DM_UPDATE
   drop constraint nW_DM_UPDATE_IMAGE_FROM_FK;

alter table nW_DM_UPDATE
   drop constraint nW_DM_UPDATE_IMAGE_TO_FK;

alter table nW_DM_UPDATE
   drop constraint nW_DM_UPD_STATUS_FK;

alter table nW_DM_UPDATE_CARRIERS
   drop constraint nW_DM_UPD_CARR_CARR_FK;

alter table nW_DM_UPDATE_CARRIERS
   drop constraint nW_DM_UPD_CARR_STATU_FK;

alter table nW_DM_UPDATE_CARRIERS
   drop constraint nW_DM_UPD_CARR_UPD_FK;

alter table nW_DM_USER_CARRIER
   drop constraint FK_USER_CARRIER;

alter table nW_DM_USER_MANUFACTURER
   drop constraint FK_USERS_MANUFACT;

alter table nW_DM_USER_ROLE
   drop constraint FK_US_USER_ROL;

alter table nW_DM_USER_ROLE
   drop constraint FK_US_U_R_RO;

drop view DM_TRACKING_LOG_DETAIL;

drop view DM_TRACKING_LOG_SUM;

drop view V_SOFTWARE_TRACK_LOG_ALL;

drop view V_SOFTWARE_TRACK_LOG_DAILY;

drop view V_SOFTWARE_TRACK_LOG_MONTHLY;

drop view V_SOFTWARE_TRACK_LOG_WEEKLY;

drop view V_SOFTWARE_TRACK_LOG_YEARLY;

drop view nW_DM_MODEL_FAMILY;

drop table DM_ACCESS_LOG cascade constraints;

drop index IDX_ACCESS_LOG_HEAD_NAME;

drop index IDX_FK_ACCESS_LOG_HEAD;

drop table DM_ACCESS_LOG_HEADER cascade constraints;

drop index IDX_ACCESS_LOG_HEAD_PARAM;

drop index IDX_FK_ACCESS_LOG_PARAM;

drop table DM_ACCESS_LOG_PARAMETER cascade constraints;

drop index IDX_DEV_CHANGE_LOG_IMSI;

drop index IDX_DEV_CHANGE_LOG_MSISDN;

drop index IDX_DEV_CHANGE_LOG_IMEI;

drop table DM_DEVICE_CHANGE_LOG cascade constraints;

drop index IDX_ENTERPRISE_ACTIVED;

drop index IDX_ENTERPRISE_NAME;

drop table DM_ENTERPRISE cascade constraints;

drop index IDX_FK_ENTER_SOFTW_;

drop index IDX_FK_ENTER_SOFTW_1;

drop table DM_ENTERPRISE_SOFTWRE cascade constraints;

drop index IDX_FK_ENTER_SUB_2;

drop index IDX_FK_ENTER_SUB_1;

drop table DM_ENTERPRISE_SUBSCRIBER cascade constraints;

drop index IDX_ISSHARE_FAVORITE;

drop index IDX_OWNER_FAVORITE;

drop index IDX_FK_FAVORITE;

drop table DM_FAVORITE cascade constraints;

drop index IDX_FK_FAVORITE_ID;

drop index IDX_FK_FAVORITE_DEVICE;

drop table DM_FAVORITE_DEVICE cascade constraints;

drop index IDX_TRK_HT_DM_SESSION_ID;

drop table DM_TRACKING_LOG_HTTP cascade constraints;

drop index IDX_TRK_LOG_JOB_JOB_ID;

drop index IDX_TRK_LOG_JOB_DEV_EXT_ID;

drop index IDX_TRK_LOG_JOB_SESS_ID;

drop table DM_TRACKING_LOG_JOB cascade constraints;

drop index IDX_MODEL_CLASS_NAME;

drop table MODEL_CLASSIFICATION cascade constraints;

drop table OS_CURRENTSTEP cascade constraints;

drop index Index_HISTORYSTEP2;

drop table OS_HISTORYSTEP cascade constraints;

drop table OS_PROPERTYSET cascade constraints;

drop table OS_WFENTRY cascade constraints;

drop index IDX_PRED_MOD_SLTOR_FK2;

drop index IDX_PRED_MOD_SLTOR_FK1;

drop table PREDEFINED_MODEL_SELECTOR cascade constraints;

drop index IDX_CP_STATE;

drop table nW_CP_JOB cascade constraints;

drop table nW_CP_JOB_PROFILE cascade constraints;

drop index IDX_FK_CP_PROF_PARAM;

drop table nW_CP_JOB_PROFILE_PARAMETER cascade constraints;

drop index IDX_CP_TAR_DEV_STATUS;

drop index IDX_FK_CP_JOB;

drop table nW_CP_JOB_TARGET_DEVICE cascade constraints;

drop table nW_DM_ATTRIB_TRANSLATIONS cascade constraints;

drop index Index_ATTRIB_TYPE_VALUE_3;

drop index Index_ATTRIB_TYPE_VALUES_val;

drop table nW_DM_ATTRIB_TYPE_VALUES cascade constraints;

drop index Index_AUDIT_LOG_Action;

drop table nW_DM_AUDIT_LOG cascade constraints;

drop table nW_DM_AUDIT_LOG_ACTION cascade constraints;

drop index FK_AUDIT_LOG;

drop table nW_DM_AUDIT_LOG_TARGET cascade constraints;

drop table nW_DM_AUTO_JOB_NODES_DISCOVER cascade constraints;

drop table nW_DM_AUTO_JOB_PROFILE_CONFIG cascade constraints;

drop index IDX_AUTO_JOB_ID;

drop index IDX_AUTO_JOB_STATE;

drop index IDX_AUTO_JOB_SCH_TIME;

drop table nW_DM_AUTO_PROV_JOB cascade constraints;

drop table nW_DM_BLOB cascade constraints;

drop index DM_CARRIER_IDX_BOOT_DM;

drop index DM_CARRIER_IDX_BOOT_NAP;

drop table nW_DM_CARRIER cascade constraints;

drop index CP_AUDIT_LOG_IDX_MSG_ID;

drop index CP_AUDIT_LOG_IDX_PRO_N;

drop index CP_AUDIT_LOG_IDX_STATUS;

drop index CP_AUDIT_LOG_IDX_CARRIER;

drop index CP_AUDIT_LOG_IDX_MODEL;

drop index CP_AUDIT_LOG_IDX_MANU;

drop index CP_AUDIT_LOG_IDX_PHONE;

drop index CP_AUDIT_LOG_IDX_TIME;

drop table nW_DM_CLIENT_PROV_AUDIT_LOG cascade constraints;

drop table nW_DM_CLIENT_PROV_TEMPLATE cascade constraints;

drop table nW_DM_CONFIG_PROFILE cascade constraints;

drop table nW_DM_COUNTRY cascade constraints;

drop index Idx_DDF_Tree_ID;

drop index Idx_DDF_Parent_ID;

drop table nW_DM_DDF_NODE cascade constraints;

drop table nW_DM_DDF_NODE_ACCESS_TYPE cascade constraints;

drop table nW_DM_DDF_NODE_MIME_TYPE cascade constraints;

drop table nW_DM_DDF_TREE cascade constraints;

drop index IDX_DEVICE_EXT_ID;

drop index nW_DM_DEV_IDX_IMG_PR_ELEM;

drop index nW_DM_DEVICE_IDX_UPDATE;

drop index nW_DM_DEVICE_IDX_SUB;

drop index nW_DM_DEVICE_IDX_MODEL;

drop index nW_DM_DEVICE_IDX_DPR;

drop index nW_DM_DEVICE_IDX_CUR_IMG;

drop index nW_DM_DEVICE_IDX_CARRIER;

drop table nW_DM_DEVICE cascade constraints;

drop table nW_DM_DEVICE_GROUP cascade constraints;

drop index nW_DM_DEV_GRPDEV_IDX_DEVICE;

drop table nW_DM_DEVICE_GROUP_DEVICE cascade constraints;

drop index nW_DM_DEV_LOG_IDX_JOB;

drop index nW_DM_DEV_LOG_IDX_DEVICE;

drop table nW_DM_DEVICE_LOG cascade constraints;

drop table nW_DM_DEVICE_LOG_ACTION cascade constraints;

drop index IDX_DEC_PRV_REQ_SOFT;

drop index nW_DM_DEV_REQ_IDX_JOB_STATE;

drop index nW_DM_DEV_PROV_REQ_IDX_PDEV;

drop index nW_DM_DEV_PROV_IDX_PR_ELEM;

drop index nW_DM_DEV_PROV_IDX_DEVICE;

drop table nW_DM_DEVICE_PROV_REQ cascade constraints;

drop table nW_DM_DISCOVERY_JOB_NODE cascade constraints;

drop table nW_DM_DISCOVER_RESULTS cascade constraints;

drop table nW_DM_DISC_JOB_ST_NODE cascade constraints;

drop index nW_DM_DM_CMD_IDX_CMD_PKG;

drop table nW_DM_DM_CMD cascade constraints;

drop table nW_DM_DM_CMD_PKG cascade constraints;

drop index nW_DM_CMD_RESP_IDX_PROC_ID;

drop table nW_DM_DM_CMD_RESPONSE cascade constraints;

drop index nW_DM_CMD_SESS_IDX_JOB_ADAP;

drop table nW_DM_DM_CMD_SESSION cascade constraints;

drop index nW_DM_JOB_ADAP_IDX_JOBSTATE;

drop index nW_DM_JOB_ADAP_IDX_DEV_JOB;

drop index nW_DM_JOB_ADAP_IDX_DEVICE;

drop table nW_DM_DM_JOB_ADAPTER cascade constraints;

drop index nW_DM_JOB_EXEC_IDX_JOBSTATE;

drop index nW_DM_JOB_EXEC_IDX_DEV_JOB;

drop table nW_DM_DM_JOB_EXEC_CLIENT cascade constraints;

drop index nW_DM_DM_PKG_HAND_IDX_SESS;

drop table nW_DM_DM_PKG_HANDLER cascade constraints;

drop index Index_PKG_RESP_PROC_2;

drop table nW_DM_DM_PKG_RESP_PROC cascade constraints;

drop index Index_PKG_SENDER_2;

drop table nW_DM_DM_PKG_SENDER cascade constraints;

drop table nW_DM_DM_PKG_SENDER_CMD_IDS cascade constraints;

drop table nW_DM_DM_RESULTS_MAP cascade constraints;

drop index nW_DM_DM_SESS_IDX_SESS_HAND;

drop index nW_DM_DM_SESS_IDX_SESS_AUTH;

drop index nW_DM_DM_SESS_IDX_PKG_HAND;

drop index nW_DM_DM_SESS_IDX_DEVICE;

drop table nW_DM_DM_SESSION cascade constraints;

drop index Index_DTREE2;

drop table nW_DM_DTREE cascade constraints;

drop index nW_DM_DTREE_NODE_IDX_UPDATE;

drop index nW_DM_DTREE_NODE_IDX_PARENT;

drop index nW_DM_DTREE_NODE_IDX_NAME;

drop table nW_DM_DTREE_NODE cascade constraints;

drop index nW_DM_UPD_PATH_IDX_JOBSTATE;

drop table nW_DM_FW_JOB_UPDATE_PATH cascade constraints;

drop table nW_DM_GET_CMD_ITEM cascade constraints;

drop index nW_DM_IMAGE_IDX_MODEL;

drop index nW_DM_IMAGE_IDX_EXT;

drop table nW_DM_IMAGE cascade constraints;

drop index Index_IMAGE_CARRIERS2;

drop table nW_DM_IMAGE_CARRIERS cascade constraints;

drop table nW_DM_IMAGE_UPDATE_STATUS cascade constraints;

drop index nW_DM_JMSMSG_X;

drop table nW_DM_JMSSTATE cascade constraints;

drop index nW_DM_JMSMSGQ_X;

drop table nW_DM_JMSSTORE cascade constraints;

drop table nW_DM_JOB_ASSIGNMENTS cascade constraints;

drop index nW_DM_JOB_STATE_IDX_JOB_ID;

drop index nW_DM_JOB_STATE_IDX_JOBADAP;

drop index nW_DM_JOB_STATE_IDX_DEVICE;

drop table nW_DM_JOB_STATE cascade constraints;

drop table nW_DM_MANUFACTURER cascade constraints;

drop table nW_DM_MAPPING_NODE_NAME cascade constraints;

drop index Index_MODEL2;

drop table nW_DM_MODEL cascade constraints;

drop index IDX_MODEL_CHARA_VAL;

drop index IDX_MODEL_CHARA_NAME;

drop index IDX_MODEL_CHARA_CATEG;

drop index IDX_FK_MOD_CHARACTER;

drop table nW_DM_MODEL_CHARACTER cascade constraints;

drop table nW_DM_MODEL_CLIENT_PROV_MAP cascade constraints;

drop table nW_DM_MODEL_DDF_TREE cascade constraints;

drop table nW_DM_MODEL_DM_BOOT_PROP cascade constraints;

drop table nW_DM_MODEL_DM_PROP cascade constraints;

drop table nW_DM_MODEL_PROFILE_MAP cascade constraints;

drop table nW_DM_MODEL_TAC cascade constraints;

drop table nW_DM_NODES_TO_DISCOVER cascade constraints;

drop table nW_DM_PATH_ELEMENT cascade constraints;

drop index nW_DM_PREV_RESP_IDX_SESSION;

drop table nW_DM_PREV_PKG_RESP cascade constraints;

drop index nW_DM_PRF_ASSGN_IDX_PROFILE;

drop index nW_DM_PRF_ASSGN_IDX_DEVICE;

drop table nW_DM_PROFILE_ASSIGNMENT cascade constraints;

drop table nW_DM_PROFILE_ASSIGN_VALUE cascade constraints;

drop index IDX_PROFILE_ATTRIBUTE_FK_TYPE;

drop index IDX_PROFILE_ATTR_FK_TEMPLATE;

drop table nW_DM_PROFILE_ATTRIBUTE cascade constraints;

drop table nW_DM_PROFILE_ATTRIB_TYPE cascade constraints;

drop index IDX_PROFILE_ATTRIB_VALUE_FK;

drop table nW_DM_PROFILE_ATTRIB_VALUE cascade constraints;

drop table nW_DM_PROFILE_CATEGORY cascade constraints;

drop index IDX_PROFILE_MAPPING_FK_2;

drop index IDX_PROFILE_MAPPING_FK_1;

drop table nW_DM_PROFILE_MAPPING cascade constraints;

drop index IDX_PK_NODE_MAPPING_FK_MAPPING;

drop index IDX_PK_NODE_MAPPING_FK_DDF;

drop index IDX_PK_NODE_MAPPING_FK_ATTR;

drop table nW_DM_PROFILE_NODE_MAPPING cascade constraints;

drop index IDX_PROFILE_PARAMETER_FK_3;

drop index IDX_PROFILE_PARAMETER_FK_2;

drop index IDX_PROFILE_PARAMETER_FK_1;

drop table nW_DM_PROFILE_PARAMETER cascade constraints;

drop index IDX_PROF_TEMP_FK_CATE;

drop table nW_DM_PROFILE_TEMPLATE cascade constraints;

drop index Idx_profile_value_item;

drop table nW_DM_PROFILE_VALUE_ITEM cascade constraints;

drop table nW_DM_PROFILE_VALUE_MAP cascade constraints;

drop index IDX_PARENT_PROV_REQ;

drop index IDX_PROV_REQ_SOFT_FK;

drop index IDX_PROV_REQ_NAME;

drop index IDX_PROV_REQ_MODE;

drop index nW_DM_PROV_REQ_IDX_TO_IMG;

drop index nW_DM_PROV_REQ_IDX_STATE;

drop index nW_DM_PROV_REQ_IDX_SCH_TIME;

drop table nW_DM_PROV_REQ cascade constraints;

drop index nW_DM_PR_ELEM_IDX_SRC_IMG;

drop index nW_DM_PR_ELEM_IDX_PROV_REQ;

drop index nW_DM_PR_ELEM_IDX_DEV_GRP;

drop index nW_DM_PR_ELEM_IDX_CARRIER;

drop table nW_DM_PR_ELEMENT cascade constraints;

drop index IDX_PR_PHONE_NUMBER_FK;

drop table nW_DM_PR_PHONE_NUMBER cascade constraints;

drop table nW_DM_PR_RATE cascade constraints;

drop table nW_DM_REPL_CMD_ITEM cascade constraints;

drop table nW_DM_REPORT_TEMPLATE cascade constraints;

drop index IDX_ROLE_EXT_ID;

drop table nW_DM_ROLE cascade constraints;

drop table nW_DM_SERVICE_LOCK cascade constraints;

drop index IDX_SP_EXT_ID;

drop table nW_DM_SERVICE_PROVIDER cascade constraints;

drop index nW_DM_SESS_AUTH_IDX_SESSION;

drop index nW_DM_SESS_AUTH_IDX_DEVICE;

drop table nW_DM_SESSION_AUTH cascade constraints;

drop index IDX_SOFTWARE_STATUS;

drop index IDX_SOFTWRE_EXT_ID;

drop index IDX_SOFTWARE_VENDOR_FK;

drop table nW_DM_SOFTWARE cascade constraints;

drop index IDX_SOFT_CATEGORIES_FK;

drop index IDX_CATEGORY_SOFTS_FK;

drop table nW_DM_SOFTWARE_CATEGORIES cascade constraints;

drop index IDX_SOFT_PARENT_CATEGORY_FK;

drop table nW_DM_SOFTWARE_CATEGORY cascade constraints;

drop index IDX_SOFT_EVAL_CRET_TIME;

drop index IDX_SOFT_EVAL_USERID;

drop index IDX_FK_EVAL_TO_SOFT;

drop table nW_DM_SOFTWARE_EVALUATE cascade constraints;

drop index IDX_SOFT_PKG_STATUS;

drop index IDX_SOFTWARE_PKG_FK_BLOG;

drop index IDX_SOFTWARE_PKG_FK_SOFT;

drop table nW_DM_SOFTWARE_PACKAGE cascade constraints;

drop index IDX_FK_SOFT_PKG_REF_MOD_CLAS;

drop index IDX_SOFT_PKG_FAMILY;

drop index IDX_FK_SOFT_PKG_MODEL;

drop index IDX_FK_SOFT_PKG_REF_MODEL;

drop table nW_DM_SOFTWARE_PACKAGE_MODEL cascade constraints;

drop table nW_DM_SOFTWARE_RATING cascade constraints;

drop index IDX_SOFT_RECOMM_UNIQ;

drop table nW_DM_SOFTWARE_RECOMMEND cascade constraints;

drop index IDX_SOFT_SCREEN_FK_SOFTWARE;

drop index IDX_SOFT_SCREEN_FK_GRAPHICS;

drop table nW_DM_SOFTWARE_SCREEN_SHOOT cascade constraints;

drop index IDX_SW_TRK_TRK_TIME_YEAR;

drop index IDX_SW_TRK_TRK_TIME_MONTH;

drop index IDX_SW_TRK_TRK_TIME_WEEK;

drop index IDX_SW_TRK_TRK_TIME_DAY;

drop index IDX_SW_TRK_LOG_TYPE;

drop index IDX_SW_TRK_LOG_TIME;

drop index IDX_SW_TRCK_LOG_FK_SW_PKG;

drop index IDX_SW_TRCK_LOG_FK_SW;

drop table nW_DM_SOFTWARE_TRACKING_LOG cascade constraints;

drop table nW_DM_SOFTWARE_VENDOR cascade constraints;

drop index IDX_STEPS_FK;

drop table nW_DM_STEPS cascade constraints;

drop index IDX_SUBSCRIBER_EXT_ID;

drop index nW_DM_SUB_IDX_UPDATE;

drop index nW_DM_SUB_IDX_CARRIER;

drop table nW_DM_SUBSCRIBER cascade constraints;

drop index nW_DM_UPDATE_IDX_TO;

drop index nW_DM_UPDATE_IDX_FROM;

drop table nW_DM_UPDATE cascade constraints;

drop index IDX_UPD_CARR_FK_3;

drop index IDX_UPD_CARR_FK_2;

drop index IDX_UPD_CARR_FK_1;

drop table nW_DM_UPDATE_CARRIERS cascade constraints;

drop table nW_DM_UPDATE_WORKFLOW cascade constraints;

drop index IDX_USERNAME;

drop table nW_DM_USER cascade constraints;

drop table nW_DM_USER_CARRIER cascade constraints;

drop index IDX_USER_MANU_EXT_ID;

drop table nW_DM_USER_MANUFACTURER cascade constraints;

drop index IDX_USER_ROLE_ID;

drop table nW_DM_USER_ROLE cascade constraints;

/*==============================================================*/
/* Table: DM_ACCESS_LOG                                         */
/*==============================================================*/
create table DM_ACCESS_LOG  (
   ID                   NUMBER(20)                      not null,
   TIME_STAMP           DATE                            not null,
   URL                  VARCHAR2(4000)                  not null,
   QUERY                VARCHAR2(4000),
   CLIENT_IP            VARCHAR(64)                     not null,
   USER_AGENT           VARCHAR2(4000),
   SESSION_ID           VARCHAR(64),
   constraint PK_DM_ACCESS_LOG primary key (ID)
);

/*==============================================================*/
/* Table: DM_ACCESS_LOG_HEADER                                  */
/*==============================================================*/
create table DM_ACCESS_LOG_HEADER  (
   ID                   NUMBER(20)                      not null,
   LOG_ID               NUMBER(20),
   NAME                 VARCHAR(200)                    not null,
   VALUE                VARCHAR(4000),
   constraint PK_DM_ACCESS_LOG_HEADER primary key (ID)
);

/*==============================================================*/
/* Index: IDX_FK_ACCESS_LOG_HEAD                                */
/*==============================================================*/
create index IDX_FK_ACCESS_LOG_HEAD on DM_ACCESS_LOG_HEADER (
   LOG_ID ASC
);

/*==============================================================*/
/* Index: IDX_ACCESS_LOG_HEAD_NAME                              */
/*==============================================================*/
create index IDX_ACCESS_LOG_HEAD_NAME on DM_ACCESS_LOG_HEADER (
   NAME ASC
);

/*==============================================================*/
/* Table: DM_ACCESS_LOG_PARAMETER                               */
/*==============================================================*/
create table DM_ACCESS_LOG_PARAMETER  (
   ID                   NUMBER(20)                      not null,
   LOG_ID               NUMBER(20),
   NAME                 VARCHAR(200)                    not null,
   VALUE                VARCHAR(4000),
   constraint PK_DM_ACCESS_LOG_PARAMETER primary key (ID)
);

/*==============================================================*/
/* Index: IDX_FK_ACCESS_LOG_PARAM                               */
/*==============================================================*/
create index IDX_FK_ACCESS_LOG_PARAM on DM_ACCESS_LOG_PARAMETER (
   LOG_ID ASC
);

/*==============================================================*/
/* Index: IDX_ACCESS_LOG_HEAD_PARAM                             */
/*==============================================================*/
create index IDX_ACCESS_LOG_HEAD_PARAM on DM_ACCESS_LOG_PARAMETER (
   NAME ASC
);

/*==============================================================*/
/* Table: DM_DEVICE_CHANGE_LOG                                  */
/*==============================================================*/
create table DM_DEVICE_CHANGE_LOG  (
   ID                   NUMBER(20)                      not null,
   IMEI                 VARCHAR(32)                     not null,
   PHONE_NUMBER         VARCHAR(32)                     not null,
   IMSI                 VARCHAR(32),
   BRAND                VARCHAR(64),
   MODEL                VARCHAR(64),
   SOFTWARE_VERSION     VARCHAR(255),
   LAST_UPDATE          DATE                            not null,
   constraint PK_DM_DEVICE_CHANGE_LOG primary key (ID)
);

/*==============================================================*/
/* Index: IDX_DEV_CHANGE_LOG_IMEI                               */
/*==============================================================*/
create index IDX_DEV_CHANGE_LOG_IMEI on DM_DEVICE_CHANGE_LOG (
   IMEI ASC
);

/*==============================================================*/
/* Index: IDX_DEV_CHANGE_LOG_MSISDN                             */
/*==============================================================*/
create index IDX_DEV_CHANGE_LOG_MSISDN on DM_DEVICE_CHANGE_LOG (
   PHONE_NUMBER ASC
);

/*==============================================================*/
/* Index: IDX_DEV_CHANGE_LOG_IMSI                               */
/*==============================================================*/
create index IDX_DEV_CHANGE_LOG_IMSI on DM_DEVICE_CHANGE_LOG (
   IMSI ASC
);

/*==============================================================*/
/* Table: DM_ENTERPRISE                                         */
/*==============================================================*/
create table DM_ENTERPRISE  (
   ENTERPRISE_ID        NUMBER(20)                      not null,
   NAME                 VARCHAR(200)                    not null,
   ACTIVED              NUMBER(1)                       not null,
   WEB_SITE             VARCHAR(200),
   DESCRIPTION          CLOB,
   CREATED_BY           VARCHAR2(255),
   CREATED_TIME         DATE                            not null,
   LAST_UPDATED_BY      VARCHAR2(255),
   LAST_UPDATED_TIME    DATE                            not null,
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint PK_DM_ENTERPRISE primary key (ENTERPRISE_ID)
);

/*==============================================================*/
/* Index: IDX_ENTERPRISE_NAME                                   */
/*==============================================================*/
create index IDX_ENTERPRISE_NAME on DM_ENTERPRISE (
   NAME ASC
);

/*==============================================================*/
/* Index: IDX_ENTERPRISE_ACTIVED                                */
/*==============================================================*/
create index IDX_ENTERPRISE_ACTIVED on DM_ENTERPRISE (
   ACTIVED ASC
);

/*==============================================================*/
/* Table: DM_ENTERPRISE_SOFTWRE                                 */
/*==============================================================*/
create table DM_ENTERPRISE_SOFTWRE  (
   ID                   NUMBER(20)                      not null,
   ENTERPRISE_ID        NUMBER(20),
   SOFTWARE_ID          NUMBER(20),
   constraint PK_DM_ENTERPRISE_SOFTWRE primary key (ID)
);

/*==============================================================*/
/* Index: IDX_FK_ENTER_SOFTW_1                                  */
/*==============================================================*/
create index IDX_FK_ENTER_SOFTW_1 on DM_ENTERPRISE_SOFTWRE (
   ENTERPRISE_ID ASC
);

/*==============================================================*/
/* Index: IDX_FK_ENTER_SOFTW_                                   */
/*==============================================================*/
create index IDX_FK_ENTER_SOFTW_ on DM_ENTERPRISE_SOFTWRE (
   SOFTWARE_ID ASC
);

/*==============================================================*/
/* Table: DM_ENTERPRISE_SUBSCRIBER                              */
/*==============================================================*/
create table DM_ENTERPRISE_SUBSCRIBER  (
   ID                   NUMBER(20)                      not null,
   ENTERPRISE_ID        NUMBER(20)                      not null,
   SUBSCRIBER_ID        NUMBER(20)                      not null,
   constraint PK_DM_ENTERPRISE_SUBSCRIBER primary key (ID)
);

/*==============================================================*/
/* Index: IDX_FK_ENTER_SUB_1                                    */
/*==============================================================*/
create index IDX_FK_ENTER_SUB_1 on DM_ENTERPRISE_SUBSCRIBER (
   ENTERPRISE_ID ASC
);

/*==============================================================*/
/* Index: IDX_FK_ENTER_SUB_2                                    */
/*==============================================================*/
create index IDX_FK_ENTER_SUB_2 on DM_ENTERPRISE_SUBSCRIBER (
   SUBSCRIBER_ID ASC
);

/*==============================================================*/
/* Table: DM_FAVORITE                                           */
/*==============================================================*/
create table DM_FAVORITE  (
   FAVORITE_ID          NUMBER(20)                      not null,
   NAME                 VARCHAR2(1024)                  not null,
   DESCRIPTION          VARCHAR2(2000),
   ISSHARE              NUMBER(1)                       not null,
   OWNER                VARCHAR2(1024),
   constraint PK_DM_FAVORITE primary key (FAVORITE_ID)
);

/*==============================================================*/
/* Index: IDX_FK_FAVORITE                                       */
/*==============================================================*/
create unique index IDX_FK_FAVORITE on DM_FAVORITE (
   NAME ASC
);

/*==============================================================*/
/* Index: IDX_OWNER_FAVORITE                                    */
/*==============================================================*/
create unique index IDX_OWNER_FAVORITE on DM_FAVORITE (
   OWNER ASC
);

/*==============================================================*/
/* Index: IDX_ISSHARE_FAVORITE                                  */
/*==============================================================*/
create unique index IDX_ISSHARE_FAVORITE on DM_FAVORITE (
   ISSHARE ASC
);

/*==============================================================*/
/* Table: DM_FAVORITE_DEVICE                                    */
/*==============================================================*/
create table DM_FAVORITE_DEVICE  (
   FAVORITE_ID          NUMBER(20)                      not null,
   ID                   NUMBER(20)                      not null,
   DEVICE_ID            NUMBER(20)                      not null,
   constraint PK_DM_FAVORITE_DEVICE primary key (ID)
);

/*==============================================================*/
/* Index: IDX_FK_FAVORITE_DEVICE                                */
/*==============================================================*/
create index IDX_FK_FAVORITE_DEVICE on DM_FAVORITE_DEVICE (
   DEVICE_ID ASC
);

/*==============================================================*/
/* Index: IDX_FK_FAVORITE_ID                                    */
/*==============================================================*/
create index IDX_FK_FAVORITE_ID on DM_FAVORITE_DEVICE (
   FAVORITE_ID ASC
);

/*==============================================================*/
/* Table: DM_TRACKING_LOG_HTTP                                  */
/*==============================================================*/
create table DM_TRACKING_LOG_HTTP  (
   ID                   NUMBER(20)                      not null,
   TIME_STAMP           DATE                            not null,
   DM_SESSION_ID        VARCHAR2(255),
   DM_REQUEST_SIZE      NUMBER(10)                      not null,
   DM_RESPONSE_SIZE     NUMBER(10)                      not null,
   CLIENT_IP            VARCHAR2(64)                    not null,
   USER_AGENT           VARCHAR2(255),
   constraint PK_DM_TRACKING_LOG_HTTP primary key (ID)
);

/*==============================================================*/
/* Index: IDX_TRK_HT_DM_SESSION_ID                              */
/*==============================================================*/
create index IDX_TRK_HT_DM_SESSION_ID on DM_TRACKING_LOG_HTTP (
   DM_SESSION_ID ASC
);

/*==============================================================*/
/* Table: DM_TRACKING_LOG_JOB                                   */
/*==============================================================*/
create table DM_TRACKING_LOG_JOB  (
   ID                   NUMBER(20)                      not null,
   DM_SESSION_ID        VARCHAR2(255)                   not null,
   DEVICE_EXTERNAL_ID   VARCHAR2(32)                    not null,
   PROCESSOR            VARCHAR2(255)                   not null,
   STATUS_CODE          VARCHAR2(64),
   JOB_ID               NUMBER(20),
   BEGIN_TIME_STAMP     DATE,
   END_TIME_STAMP       DATE,
   UPDATED_TIME         DATE                            not null,
   constraint PK_DM_TRACKING_LOG_JOB primary key (ID)
);

/*==============================================================*/
/* Index: IDX_TRK_LOG_JOB_SESS_ID                               */
/*==============================================================*/
create index IDX_TRK_LOG_JOB_SESS_ID on DM_TRACKING_LOG_JOB (
   DM_SESSION_ID ASC
);

/*==============================================================*/
/* Index: IDX_TRK_LOG_JOB_DEV_EXT_ID                            */
/*==============================================================*/
create index IDX_TRK_LOG_JOB_DEV_EXT_ID on DM_TRACKING_LOG_JOB (
   DEVICE_EXTERNAL_ID ASC
);

/*==============================================================*/
/* Index: IDX_TRK_LOG_JOB_JOB_ID                                */
/*==============================================================*/
create index IDX_TRK_LOG_JOB_JOB_ID on DM_TRACKING_LOG_JOB (
   JOB_ID ASC
);

/*==============================================================*/
/* Table: MODEL_CLASSIFICATION                                  */
/*==============================================================*/
create table MODEL_CLASSIFICATION  (
   MODEL_CLASSIFICATION_ID NUMBER(20)                      not null,
   EXTERNAL_ID          VARCHAR(255)                    not null,
   NAME                 VARCHAR(255)                    not null,
   DESCRIPTION          VARCHAR2(1024),
   MODEL_SELECTOR       BLOB,
   constraint PK_MODEL_CLASSIFICATION primary key (MODEL_CLASSIFICATION_ID),
   constraint AK_KEY_EXT_ID_MODEL_C_MODEL_CL unique (EXTERNAL_ID)
);

/*==============================================================*/
/* Index: IDX_MODEL_CLASS_NAME                                  */
/*==============================================================*/
create index IDX_MODEL_CLASS_NAME on MODEL_CLASSIFICATION (
   NAME ASC
);

/*==============================================================*/
/* Table: OS_CURRENTSTEP                                        */
/*==============================================================*/
create table OS_CURRENTSTEP  (
   ID                   NUMBER                          not null,
   ENTRY_ID             NUMBER,
   STEP_ID              NUMBER,
   ACTION_ID            NUMBER,
   START_DATE           DATE,
   FINISH_DATE          DATE,
   DUE_DATE             DATE,
   STATUS               VARCHAR2(20),
   constraint SYS_C003162 primary key (ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: OS_HISTORYSTEP                                        */
/*==============================================================*/
create table OS_HISTORYSTEP  (
   ID                   NUMBER                          not null,
   ENTRY_ID             NUMBER,
   STEP_ID              NUMBER,
   ACTION_ID            NUMBER,
   START_DATE           DATE,
   FINISH_DATE          DATE,
   DUE_DATE             DATE,
   STATUS               VARCHAR2(20),
   constraint SYS_C003164 primary key (ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: Index_HISTORYSTEP2                                  */
/*==============================================================*/
create index Index_HISTORYSTEP2 on OS_HISTORYSTEP (
   ENTRY_ID ASC
);

/*==============================================================*/
/* Table: OS_PROPERTYSET                                        */
/*==============================================================*/
create table OS_PROPERTYSET  (
   ENTITY_NAME          VARCHAR2(125)                   not null,
   ENTITY_ID            NUMBER(20)                      not null,
   ENTITY_KEY           VARCHAR2(125)                   not null,
   KEY_TYPE             NUMBER,
   BOOLEAN_VAL          NUMBER(1)                       not null,
   DOUBLE_VAL           FLOAT,
   STRING_VAL           VARCHAR2(255),
   LONG_VAL             NUMBER,
   INT_VAL              NUMBER,
   DATE_VAL             DATE,
   constraint SYS_C003167 primary key (ENTITY_NAME, ENTITY_ID, ENTITY_KEY)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: OS_WFENTRY                                            */
/*==============================================================*/
create table OS_WFENTRY  (
   ID                   NUMBER                          not null,
   NAME                 VARCHAR2(100),
   STATE                NUMBER,
   constraint SYS_C003160 primary key (ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: PREDEFINED_MODEL_SELECTOR                             */
/*==============================================================*/
create table PREDEFINED_MODEL_SELECTOR  (
   ID                   NUMBER(20)                      not null,
   MODEL_ID             NUMBER(20),
   MODEL_CLASSIFICATION_ID NUMBER(20),
   constraint PK_PREDEFINED_MODEL_SELECTOR primary key (ID)
);

/*==============================================================*/
/* Index: IDX_PRED_MOD_SLTOR_FK1                                */
/*==============================================================*/
create index IDX_PRED_MOD_SLTOR_FK1 on PREDEFINED_MODEL_SELECTOR (
   MODEL_CLASSIFICATION_ID ASC
);

/*==============================================================*/
/* Index: IDX_PRED_MOD_SLTOR_FK2                                */
/*==============================================================*/
create index IDX_PRED_MOD_SLTOR_FK2 on PREDEFINED_MODEL_SELECTOR (
   MODEL_ID ASC
);

/*==============================================================*/
/* Table: nW_CP_JOB                                           */
/*==============================================================*/
create table nW_CP_JOB  (
   JOB_ID               NUMBER(20)                      not null,
   NAME                 VARCHAR2(255),
   TYPE                 VARCHAR2(255),
   DESCRIPTION          VARCHAR2(2048),
   STATE                VARCHAR2(255)                   not null,
   SCHEDULE_TIME        DATE,
   MAX_RETRIES          NUMBER(2),
   MAX_DURATION         NUMBER(6),
   CREATED_TIME         DATE                            not null,
   CREATED_BY           VARCHAR2(255),
   LAST_UPDATED_TIME    DATE,
   LAST_UPDATED_BY      VARCHAR2(255),
   constraint PK_NW_CP_JOB primary key (JOB_ID)
);

comment on column nW_CP_JOB.MAX_DURATION is
'max duration in seconds';

/*==============================================================*/
/* Index: IDX_CP_STATE                                          */
/*==============================================================*/
create index IDX_CP_STATE on nW_CP_JOB (
   STATE ASC
);

/*==============================================================*/
/* Table: nW_CP_JOB_PROFILE                                   */
/*==============================================================*/
create table nW_CP_JOB_PROFILE  (
   ID                   NUMBER(20)                      not null,
   PROFILE_EXTERNAL_ID  VARCHAR2(255)                   not null,
   TARGET_DEVICE_ID     NUMBER(20),
   constraint PK_NW_CP_JOB_PROFILE primary key (ID)
);

/*==============================================================*/
/* Table: nW_CP_JOB_PROFILE_PARAMETER                         */
/*==============================================================*/
create table nW_CP_JOB_PROFILE_PARAMETER  (
   NAME                 VARCHAR2(255)                   not null,
   ID                   NUMBER(20),
   VALUE                BLOB,
   constraint PK_NW_CP_JOB_PROFILE_PARAMETER primary key (NAME)
);

/*==============================================================*/
/* Index: IDX_FK_CP_PROF_PARAM                                  */
/*==============================================================*/
create index IDX_FK_CP_PROF_PARAM on nW_CP_JOB_PROFILE_PARAMETER (
   ID ASC
);

/*==============================================================*/
/* Table: nW_CP_JOB_TARGET_DEVICE                             */
/*==============================================================*/
create table nW_CP_JOB_TARGET_DEVICE  (
   ID                   NUMBER(20)                      not null,
   PROV_REQ_ID          NUMBER(20),
   DEVICE_ID            VARCHAR2(255),
   PHONE_NUMBER         VARCHAR2(255),
   MANUFACTURER_EXTERNAL_ID VARCHAR2(255),
   MODEL_EXTERNAL_ID    VARCHAR2(255),
   CARRIER_EXTERNAL_ID  VARCHAR2(255),
   STATUS               VARCHAR2(64)                    not null,
   NUMBER_OF_ENQUEUE_RETRIES NUMBER(6)                       not null,
   LAST_ENQUEUE_RETRIED_TIME DATE,
   MESSAGE_ID           VARCHAR2(255),
   MESSAGE_TYPE         VARCHAR2(255),
   MESSAGE_CONTENT      CLOB,
   MESSAGE_RAW          BLOB,
   SECURITY_METHOD      VARCHAR2(255),
   SECURITY_PIN         VARCHAR2(255),
   PROFILE_EXTERNAL_ID  VARCHAR2(255),
   PARAMETERS           BLOB,
   FINISHED_TIME        DATE,
   CREATED_TIME         DATE                            not null,
   CREATED_BY           VARCHAR2(255),
   LAST_UPDATED_TIME    DATE,
   LAST_UPDATED_BY      VARCHAR2(255),
   constraint PK_NW_CP_JOB_TARGET_DEVICE primary key (ID)
);

comment on column nW_CP_JOB_TARGET_DEVICE.NUMBER_OF_ENQUEUE_RETRIES is
'尝试送入SMS Queue的次数';

comment on column nW_CP_JOB_TARGET_DEVICE.LAST_ENQUEUE_RETRIED_TIME is
'最后一次尝试进入SMS Queue的时间';

/*==============================================================*/
/* Index: IDX_FK_CP_JOB                                         */
/*==============================================================*/
create index IDX_FK_CP_JOB on nW_CP_JOB_TARGET_DEVICE (
   PROV_REQ_ID ASC
);

/*==============================================================*/
/* Index: IDX_CP_TAR_DEV_STATUS                                 */
/*==============================================================*/
create index IDX_CP_TAR_DEV_STATUS on nW_CP_JOB_TARGET_DEVICE (
   STATUS ASC
);

/*==============================================================*/
/* Table: nW_DM_ATTRIB_TRANSLATIONS                           */
/*==============================================================*/
create table nW_DM_ATTRIB_TRANSLATIONS  (
   NODE_MAPPING_ID      NUMBER(20)                      not null,
   VALUE                VARCHAR2(50)                    not null,
   TRANSLATION          VARCHAR2(50)                    not null,
   constraint PK_NW_DM_ATTRIB_TRANSLATIONS primary key (NODE_MAPPING_ID, VALUE, TRANSLATION)
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_ATTRIB_TYPE_VALUES                            */
/*==============================================================*/
create table nW_DM_ATTRIB_TYPE_VALUES  (
   ATTRIBUTE_TYPE_VALUE_ID NUMBER(20)                      not null,
   ATTRIBUTE_TYPE_ID    NUMBER(20)                      not null,
   VALUE                VARCHAR2(4000)                  not null,
   constraint PK_NW_DM_ATTRIB_TYPE_VALUES primary key (ATTRIBUTE_TYPE_VALUE_ID)
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: Index_ATTRIB_TYPE_VALUES_val                        */
/*==============================================================*/
create index Index_ATTRIB_TYPE_VALUES_val on nW_DM_ATTRIB_TYPE_VALUES (
   ATTRIBUTE_TYPE_ID ASC
);

/*==============================================================*/
/* Index: Index_ATTRIB_TYPE_VALUE_3                           */
/*==============================================================*/
create index Index_ATTRIB_TYPE_VALUE_3 on nW_DM_ATTRIB_TYPE_VALUES (
   VALUE ASC
);

/*==============================================================*/
/* Table: nW_DM_AUDIT_LOG                                     */
/*==============================================================*/
create table nW_DM_AUDIT_LOG  (
   LOG_ID               NUMBER(20)                      not null,
   USER_NAME            VARCHAR2(100)                   not null,
   ACTION               VARCHAR2(50)                    not null,
   CREATION_DATE        DATE                            not null,
   IP_ADDRESS           VARCHAR2(100),
   DESCRIPTION          CLOB,
   constraint nW_DM_AUDIT_LOG_PK primary key (LOG_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: Index_AUDIT_LOG_Action                              */
/*==============================================================*/
create index Index_AUDIT_LOG_Action on nW_DM_AUDIT_LOG (
   ACTION ASC
);

/*==============================================================*/
/* Table: nW_DM_AUDIT_LOG_ACTION                              */
/*==============================================================*/
create table nW_DM_AUDIT_LOG_ACTION  (
   VALUE                VARCHAR2(50)                    not null,
   DESCRIPTION          VARCHAR2(50)                    not null,
   TYPE                 VARCHAR2(50)                    not null,
   OPTIONAL             NUMBER(1)                       not null,
   constraint nW_DM_AUDIT_LOG_ACTION_PK primary key (VALUE)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_AUDIT_LOG_TARGET                              */
/*==============================================================*/
create table nW_DM_AUDIT_LOG_TARGET  (
   TARGET_ID            NUMBER(20)                      not null,
   LOG_ID               NUMBER(20),
   ROLE_NAME            VARCHAR2(40),
   DESCRIPTION          VARCHAR2(1000),
   TYPE                 VARCHAR2(200),
   DEVICE_ID            VARCHAR2(20),
   DEVICE_EXTERNAL_ID   VARCHAR2(200),
   IMAGE_ID             VARCHAR2(20),
   IMAGE_EXTERNAL_ID    VARCHAR2(1000),
   SUBSCRIBER_ID        VARCHAR2(20),
   SUBSCRIBER_EXTERNAL_ID VARCHAR2(1000),
   SUBSCRIBER_PHONE_NUMBER VARCHAR2(32),
   CARRIER_EXTERNAL_ID  VARCHAR2(100),
   CARRIER_ID           VARCHAR2(20),
   COUNTRY_EXTERNAL_ID  VARCHAR2(1000),
   COUNTRY_ID           VARCHAR2(20),
   MODEL_ID             VARCHAR2(20),
   MODEL_NAME           VARCHAR2(1000),
   UPDATE_ID            VARCHAR2(20),
   WORKFLOW_NAME        VARCHAR2(200),
   MANUFACTURER_ID      VARCHAR2(20),
   MANUFACTURER_EXTERNAL_ID VARCHAR2(1000),
   PROV_REQ_ID          VARCHAR2(20),
   TEMPLATE_ID          VARCHAR2(20),
   PROFILE_ID           VARCHAR2(20),
   PROFILE_NAME         VARCHAR2(1000),
   PROFILE_TEMPLATE_ID  VARCHAR2(20),
   PROFILE_TEMPLATE_NAME VARCHAR2(1000),
   USER_ID              VARCHAR2(20),
   USER_NAME            VARCHAR2(255),
   PROFILE_ASSIGNMENT_ID VARCHAR2(20),
   ATTRIBUTE_TYPE_NAME  VARCHAR2(1000),
   SOFTWARE_NAME        VARCHAR2(80),
   SOFTWARE_VENDOR      VARCHAR2(80),
   SOFTWARE_CATEGORY    VARCHAR2(80),
   SOFTWARE_VENDOR_ID   VARCHAR2(80),
   SOFTWARE_VENDOR_EXTERNAL_ID VARCHAR2(80),
   constraint PK_NW_DM_AUDIT_LOG_TARGET primary key (TARGET_ID)
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: FK_AUDIT_LOG                                          */
/*==============================================================*/
create index FK_AUDIT_LOG on nW_DM_AUDIT_LOG_TARGET (
   LOG_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_AUTO_JOB_NODES_DISCOVER                       */
/*==============================================================*/
create table nW_DM_AUTO_JOB_NODES_DISCOVER  (
   PROV_REQ_ID          NUMBER(20)                      not null,
   NODE_INDEX           NUMBER(20)                      not null,
   NODE_NAME            VARCHAR2(1024)                  not null,
   constraint PK_AUTO_JOB_NODES_DISCOVER primary key (NODE_INDEX, NODE_NAME, PROV_REQ_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_AUTO_JOB_PROFILE_CONFIG                       */
/*==============================================================*/
create table nW_DM_AUTO_JOB_PROFILE_CONFIG  (
   PROV_REQ_ID          NUMBER(20)                      not null,
   PROFILE_INDEX        NUMBER(20)                      not null,
   PROFILE_ID           NUMBER(20)                      not null,
   constraint PK_AUTO_JOB_PROF_CFG primary key (PROV_REQ_ID, PROFILE_INDEX, PROFILE_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_AUTO_PROV_JOB                                 */
/*==============================================================*/
create table nW_DM_AUTO_PROV_JOB  (
   PROV_REQ_ID          NUMBER(20)                      not null,
   CARRIER_ID           NUMBER(20),
   TYPE                 VARCHAR2(200)                   not null,
   JOB_TYPE             VARCHAR2(15)                    not null,
   JOB_TYPE_FOR_DISPLAY VARCHAR2(100)                   not null,
   STATE                VARCHAR2(20)                    not null,
   RUNNING              NUMBER(1)                       not null,
   NAME                 VARCHAR2(2000),
   DESCRIPTION          VARCHAR2(2000),
   BEGIN_TIME           DATE                            not null,
   END_TIME             DATE,
   CRITERIA             CLOB,
   SCRIPT               CLOB,
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint SYS_C003502 primary key (PROV_REQ_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  lob
 ( SCRIPT )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
  noparallel;

/*==============================================================*/
/* Index: IDX_AUTO_JOB_SCH_TIME                                 */
/*==============================================================*/
create index IDX_AUTO_JOB_SCH_TIME on nW_DM_AUTO_PROV_JOB (
   BEGIN_TIME ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: IDX_AUTO_JOB_STATE                                    */
/*==============================================================*/
create index IDX_AUTO_JOB_STATE on nW_DM_AUTO_PROV_JOB (
   STATE ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: IDX_AUTO_JOB_ID                                       */
/*==============================================================*/
create index IDX_AUTO_JOB_ID on nW_DM_AUTO_PROV_JOB (
   PROV_REQ_ID ASC,
   CARRIER_ID ASC,
   TYPE ASC,
   JOB_TYPE ASC,
   STATE ASC
);

/*==============================================================*/
/* Table: nW_DM_BLOB                                          */
/*==============================================================*/
create table nW_DM_BLOB  (
   BLOB_ID              NUMBER(20)                      not null,
   BITS                 BLOB                            not null,
   FILENAME             VARCHAR2(255),
   MIME_TYPE            VARCHAR2(255),
   CREATION_DATE        DATE,
   STATUS               VARCHAR2(10),
   LAST_UPDATED_BY      VARCHAR2(200),
   LAST_UPDATED_TIME    DATE,
   constraint nW_DM_BLOB_PK primary key (BLOB_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  lob
 ( BITS )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
  noparallel;

/*==============================================================*/
/* Table: nW_DM_CARRIER                                       */
/*==============================================================*/
create table nW_DM_CARRIER  (
   CARRIER_ID           NUMBER(20)                      not null,
   CARRIER_EXTERNAL_ID  VARCHAR2(32)                    not null,
   NAME                 VARCHAR2(255)                   not null,
   PARENT_CARRIER_ID    NUMBER(20),
   SERVER_AUTH_TYPE     VARCHAR2(100)                   not null,
   PHONE_NUMBER_POLICY  VARCHAR2(4000),
   NOTIFICATION_TYPE    VARCHAR2(10)                    not null,
   NOTIFICATION_PROPERTIES VARCHAR2(1000),
   NOTIFICATION_STATE_TIMEOUT NUMBER(20)                      not null,
   NOTIFICATION_MAX_NUM_RETRIES NUMBER(5)                       not null,
   BOOTSTRAP_TIMEOUT    NUMBER(20)                      not null,
   BOOTSTRAP_MAX_RETRIES NUMBER(5)                       not null,
   DEFAULT_BOOTSTRAP_PIN_TYPE VARCHAR2(36)                    not null,
   DEFAULT_BOOTSTRAP_USER_PIN VARCHAR2(48)                    not null,
   JOB_EXPIRED_TIME     NUMBER(20)                      not null,
   COUNTRY_ID           NUMBER(20)                      not null,
   LAST_UPDATED_BY      VARCHAR2(200),
   LAST_UPDATED_TIME    DATE,
   DESCRIPTION          VARCHAR2(4000),
   CHANGE_VERSION       NUMBER(20)                      not null,
   NAP_FOR_BOOTSTRAP_ID NUMBER(20),
   DM_FOR_BOOTSTRAP_ID  NUMBER(20),
   constraint nW_DM_CARRIER_PK primary key (CARRIER_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_DM_CAR_EXT_ID_UNIQE unique (CARRIER_EXTERNAL_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_DM_CAR_NAM_CO_UNQ unique (NAME, COUNTRY_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: DM_CARRIER_IDX_BOOT_NAP                               */
/*==============================================================*/
create index DM_CARRIER_IDX_BOOT_NAP on nW_DM_CARRIER (
   NAP_FOR_BOOTSTRAP_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: DM_CARRIER_IDX_BOOT_DM                                */
/*==============================================================*/
create index DM_CARRIER_IDX_BOOT_DM on nW_DM_CARRIER (
   DM_FOR_BOOTSTRAP_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_CLIENT_PROV_AUDIT_LOG                         */
/*==============================================================*/
create table nW_DM_CLIENT_PROV_AUDIT_LOG  (
   CP_AUDIT_LOG_ID      NUMBER(20)                      not null,
   JOB_ID               NUMBER(20),
   TIME_STAMP           DATE                            not null,
   DEVICE_PHONE_NUMBER  VARCHAR2(32)                    not null,
   DEVICE_MANUFACTURER  VARCHAR2(200),
   DEVICE_MODEL         VARCHAR2(200),
   CARRIER              VARCHAR2(32),
   STATUS               VARCHAR2(20)                    not null,
   PROFILE_CATEGORY     VARCHAR2(255),
   PROFILE_NAME         VARCHAR2(255),
   MESSAGE_ID           VARCHAR2(200),
   MESSAGE_ENCODER      VARCHAR2(255),
   PROFILE_CONTENT      CLOB,
   MEMO                 VARCHAR2(2048),
   constraint PK_NW_DM_CLIENT_PROV_AUDIT_LOG primary key (CP_AUDIT_LOG_ID)
         using index
       global partition by hash (CP_AUDIT_LOG_ID)
       pctfree 10
       initrans 2
);

/*==============================================================*/
/* Index: CP_AUDIT_LOG_IDX_TIME                                 */
/*==============================================================*/
create index CP_AUDIT_LOG_IDX_TIME on nW_DM_CLIENT_PROV_AUDIT_LOG (
   TIME_STAMP ASC
);

/*==============================================================*/
/* Index: CP_AUDIT_LOG_IDX_PHONE                                */
/*==============================================================*/
create index CP_AUDIT_LOG_IDX_PHONE on nW_DM_CLIENT_PROV_AUDIT_LOG (
   DEVICE_PHONE_NUMBER ASC
);

/*==============================================================*/
/* Index: CP_AUDIT_LOG_IDX_MANU                                 */
/*==============================================================*/
create index CP_AUDIT_LOG_IDX_MANU on nW_DM_CLIENT_PROV_AUDIT_LOG (
   DEVICE_MANUFACTURER ASC
);

/*==============================================================*/
/* Index: CP_AUDIT_LOG_IDX_MODEL                                */
/*==============================================================*/
create index CP_AUDIT_LOG_IDX_MODEL on nW_DM_CLIENT_PROV_AUDIT_LOG (
   DEVICE_MODEL ASC
);

/*==============================================================*/
/* Index: CP_AUDIT_LOG_IDX_CARRIER                              */
/*==============================================================*/
create index CP_AUDIT_LOG_IDX_CARRIER on nW_DM_CLIENT_PROV_AUDIT_LOG (
   CARRIER ASC
);

/*==============================================================*/
/* Index: CP_AUDIT_LOG_IDX_STATUS                               */
/*==============================================================*/
create index CP_AUDIT_LOG_IDX_STATUS on nW_DM_CLIENT_PROV_AUDIT_LOG (
   STATUS ASC
);

/*==============================================================*/
/* Index: CP_AUDIT_LOG_IDX_PRO_N                                */
/*==============================================================*/
create index CP_AUDIT_LOG_IDX_PRO_N on nW_DM_CLIENT_PROV_AUDIT_LOG (
   PROFILE_NAME ASC
);

/*==============================================================*/
/* Index: CP_AUDIT_LOG_IDX_MSG_ID                               */
/*==============================================================*/
create index CP_AUDIT_LOG_IDX_MSG_ID on nW_DM_CLIENT_PROV_AUDIT_LOG (
   MESSAGE_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_CLIENT_PROV_TEMPLATE                          */
/*==============================================================*/
create table nW_DM_CLIENT_PROV_TEMPLATE  (
   TEMPLATE_ID          NUMBER(20)                      not null,
   EXTERNAL_ID          VARCHAR2(255)                   not null,
   CATEGORY_ID          NUMBER(20),
   NAME                 VARCHAR2(255),
   DESCRIPTION          VARCHAR2(4000),
   ENCODER              VARCHAR2(255),
   CONTENT              CLOB                            not null,
   LAST_UPDATED_BY      VARCHAR2(255),
   LAST_UPDATED_TIME    DATE,
   CHANGE_VERSION       NUMBER(20),
   constraint PK_NW_DM_CLIENT_PROV_TEMPLATE primary key (TEMPLATE_ID)
);

/*==============================================================*/
/* Table: nW_DM_CONFIG_PROFILE                                */
/*==============================================================*/
create table nW_DM_CONFIG_PROFILE  (
   PROFILE_ID           NUMBER(20)                      not null,
   EXTERNAL_ID          VARCHAR2(255)                   not null,
   PROFILE_TYPE         VARCHAR2(255)                   not null,
   NAME                 VARCHAR2(255)                   not null,
   CARRIER_ID           NUMBER(20),
   TEMPLATE_ID          NUMBER(20)                      not null,
   NAP_ID               NUMBER(20),
   PROXY_ID             NUMBER(20),
   IS_USER_PROFILE      NUMBER(1),
   DESCRIPTION          VARCHAR2(2000),
   LAST_UPDATED_BY      VARCHAR2(200),
   LAST_UPDATED_TIME    DATE,
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint SYS_C003362 primary key (PROFILE_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_PRF_NAME_UNIQE unique (NAME, CARRIER_ID, TEMPLATE_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint AK_PROF_EXT_ID_UNIQE_NW_DM_CO unique (EXTERNAL_ID)
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_COUNTRY                                       */
/*==============================================================*/
create table nW_DM_COUNTRY  (
   COUNTRY_ID           NUMBER(20)                      not null,
   ISO_CODE             VARCHAR2(2)                     not null,
   COUNTRY_CODE         VARCHAR2(20)                    not null,
   COUNTRY_NAME         VARCHAR2(100)                   not null,
   TRUNK_CODE           VARCHAR2(5),
   DISPLAY_COUNTRY_CODE NUMBER(1)                       not null,
   DISPLAY_TRUNK_CODE   NUMBER(1)                       not null,
   DISPLAY_PREFIX       NUMBER(1)                       not null,
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint SYS_C003022 primary key (COUNTRY_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_DM_COU_ISO_UNIQE unique (ISO_CODE)
         deferrable
   initially deferred
   using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_DM_COU_NAME_UNIQE unique (COUNTRY_NAME)
         deferrable
   initially deferred
   using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_DDF_NODE                                      */
/*==============================================================*/
create table nW_DM_DDF_NODE  (
   DDF_NODE_ID          NUMBER(20)                      not null,
   IS_LEAF_NODE         NUMBER(1)                       not null,
   NAME                 VARCHAR2(255),
   DESCRIPTION          VARCHAR2(4000),
   TITLE                VARCHAR2(255),
   FORMAT               VARCHAR2(255),
   OCCURRENCE           VARCHAR2(255),
   MAX_OCCURRENCE       NUMBER(20),
   IS_DYNAMIC           NUMBER(1),
   PARENT_NODE_ID       NUMBER(20),
   DDF_DOCUMENT_NAME    VARCHAR2(255),
   VALUE                VARCHAR2(255),
   DEFAULT_VALUE        VARCHAR2(255),
   DDF_TREE_ID          NUMBER(20),
   constraint SYS_C003342 primary key (DDF_NODE_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: Idx_DDF_Parent_ID                                   */
/*==============================================================*/
create index Idx_DDF_Parent_ID on nW_DM_DDF_NODE (
   PARENT_NODE_ID ASC
);

/*==============================================================*/
/* Index: Idx_DDF_Tree_ID                                     */
/*==============================================================*/
create index Idx_DDF_Tree_ID on nW_DM_DDF_NODE (
   DDF_TREE_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_DDF_NODE_ACCESS_TYPE                          */
/*==============================================================*/
create table nW_DM_DDF_NODE_ACCESS_TYPE  (
   DDF_NODE_ID          NUMBER(20)                      not null,
   ACCESS_TYPE          VARCHAR2(255)                   not null,
   constraint PK_NW_DM_DDF_NODE_ACCESS_TYPE primary key (DDF_NODE_ID, ACCESS_TYPE)
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_DDF_NODE_MIME_TYPE                            */
/*==============================================================*/
create table nW_DM_DDF_NODE_MIME_TYPE  (
   DDF_NODE_ID          NUMBER(20)                      not null,
   MIME_TYPE            VARCHAR2(255)                   not null,
   constraint PK_NW_DM_DDF_NODE_MIME_TYPE primary key (DDF_NODE_ID, MIME_TYPE)
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_DDF_TREE                                      */
/*==============================================================*/
create table nW_DM_DDF_TREE  (
   DDF_TREE_ID          NUMBER(20)                      not null,
   SPEC_VERSION         VARCHAR2(255),
   MANUFACTURER         VARCHAR2(255),
   MODEL                VARCHAR2(255),
   constraint SYS_C003350 primary key (DDF_TREE_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_DEVICE                                        */
/*==============================================================*/
create table nW_DM_DEVICE  (
   DEVICE_ID            NUMBER(20)                      not null,
   DEVICE_EXTERNAL_ID   VARCHAR2(200)                   not null,
   DESCRIPTION          VARCHAR2(4000),
   MODEL_ID             NUMBER(20)                      not null,
   CURRENT_IMAGE_ID     NUMBER(20),
   SMS_STATE            VARCHAR2(50)                    not null,
   IS_ACTIVATED         NUMBER(1)                       not null,
   IS_BOOTED            NUMBER(1)                       not null,
   BOOTSTRAP_PIN_TYPE   VARCHAR2(36),
   BOOTSTRAP_USER_PIN   VARCHAR2(48),
   BOOTSTAP_ASK_COUNTER NUMBER(8)                       not null,
   LAST_BOOTSTRAP_TIME  DATE,
   NO_SMS_RETRIES       NUMBER(20)                      not null,
   SMS_MESSAGE_ID       VARCHAR2(32),
   TIME_SMS_STATE_LAST_UPDATED DATE,
   IN_PROGRESS_DPR      NUMBER(20),
   CURRENT_UPDATE       NUMBER(20),
   SUBSCRIBER_ID        NUMBER(20)                      not null,
   PR_ELEMENT_ID        NUMBER(20),
   DA_VERSION           VARCHAR2(6)                     not null,
   UA_VERSION           VARCHAR2(6)                     not null,
   PEDIGREE_ID          VARCHAR2(15),
   LAST_UPDATED_BY      VARCHAR2(200),
   LAST_UPDATED_TIME    DATE,
   CREATED_TIME         DATE                            not null,
   OMA_SERVER_NONCE     VARCHAR2(255),
   OMA_CLIENT_NONCE     VARCHAR2(255),
   OMA_SERVER_PASSWORD  VARCHAR2(255),
   OMA_CLIENT_PASSWORD  VARCHAR2(255),
   OMA_CLIENT_USERNAME  VARCHAR2(255),
   OMA_CLIENT_AUTH_SCHEME VARCHAR2(255),
   GUID_COUNTER         NUMBER(5)                       not null,
   CHANGE_VERSION       NUMBER(20)                      not null,
   MANUFACTURER_NAME    VARCHAR2(255)                   not null,
   MODEL_NAME           VARCHAR2(255)                   not null,
   MANUFACTURER_ID      NUMBER(20)                      not null,
   SUBSCRIBER_PHONE_NUMBER VARCHAR2(32)                    not null,
   SUBSCRIBER_PASSWORD  VARCHAR2(32)                    not null,
   SUBSCRIBER_STATE     VARCHAR2(32)                    not null,
   SUBSCRIBER_ASK_PERM_TRIG NUMBER(1)                       not null,
   SUBSCRIBER_CARRIER_ID NUMBER(20)                      not null,
   SUBSCRIBER_IS_ACTIVATED NUMBER(1)                       not null,
   SUBSCRIBER_CARRIER_NAME VARCHAR2(32)                    not null,
   DEVICE_TREE_ID       NUMBER(20),
   constraint nW_DM_DEVICE_PK primary key (DEVICE_ID)
         using index
       global partition by hash (DEVICE_ID)
       pctfree 10
       initrans 2,
   constraint nW_DM_DEVICE_EXT_ID_UNIQE unique (DEVICE_EXTERNAL_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_DM_DEVICE_SMS_ID_UNQ unique (SMS_MESSAGE_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: nW_DM_DEVICE_IDX_CARRIER                            */
/*==============================================================*/
create index nW_DM_DEVICE_IDX_CARRIER on nW_DM_DEVICE (
   SUBSCRIBER_CARRIER_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_DEVICE_IDX_CUR_IMG                            */
/*==============================================================*/
create index nW_DM_DEVICE_IDX_CUR_IMG on nW_DM_DEVICE (
   CURRENT_IMAGE_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_DEVICE_IDX_DPR                                */
/*==============================================================*/
create index nW_DM_DEVICE_IDX_DPR on nW_DM_DEVICE (
   IN_PROGRESS_DPR ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_DEVICE_IDX_MODEL                              */
/*==============================================================*/
create index nW_DM_DEVICE_IDX_MODEL on nW_DM_DEVICE (
   MODEL_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_DEVICE_IDX_SUB                                */
/*==============================================================*/
create index nW_DM_DEVICE_IDX_SUB on nW_DM_DEVICE (
   SUBSCRIBER_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_DEVICE_IDX_UPDATE                             */
/*==============================================================*/
create index nW_DM_DEVICE_IDX_UPDATE on nW_DM_DEVICE (
   DEVICE_ID ASC,
   CHANGE_VERSION ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_DEV_IDX_IMG_PR_ELEM                           */
/*==============================================================*/
create index nW_DM_DEV_IDX_IMG_PR_ELEM on nW_DM_DEVICE (
   PR_ELEMENT_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: IDX_DEVICE_EXT_ID                                     */
/*==============================================================*/
create index IDX_DEVICE_EXT_ID on nW_DM_DEVICE (
   DEVICE_EXTERNAL_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_DEVICE_GROUP                                  */
/*==============================================================*/
create table nW_DM_DEVICE_GROUP  (
   DEVICE_GROUP_ID      NUMBER(20)                      not null,
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint nW_DM_DEVICE_GRP_ID_PK primary key (DEVICE_GROUP_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_DEVICE_GROUP_DEVICE                           */
/*==============================================================*/
create table nW_DM_DEVICE_GROUP_DEVICE  (
   DEVICE_GROUP_ID      NUMBER(20)                      not null,
   DEVICE_ID            NUMBER(20)                      not null,
   INITIAL_GROUP_ID     NUMBER(20),
   MARKED               NUMBER(1)                       not null,
   constraint nW_DM_DEV_GRP_DEV_PK primary key (DEVICE_GROUP_ID, DEVICE_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: nW_DM_DEV_GRPDEV_IDX_DEVICE                         */
/*==============================================================*/
create index nW_DM_DEV_GRPDEV_IDX_DEVICE on nW_DM_DEVICE_GROUP_DEVICE (
   DEVICE_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Table: nW_DM_DEVICE_LOG                                    */
/*==============================================================*/
create table nW_DM_DEVICE_LOG  (
   DEVICE_LOG_ID        NUMBER(20)                      not null,
   DEVICE_ID            NUMBER(20),
   ACTION               VARCHAR2(40)                    not null,
   CREATION_DATE        DATE                            not null,
   DEVICE_EXTERNAL_ID   VARCHAR2(200)                   not null,
   SUBSCRIBER_PHONE_NUMBER VARCHAR2(32),
   JOB_ID               NUMBER(20),
   JOB_TYPE             VARCHAR2(25),
   MESSAGE              VARCHAR2(200),
   constraint nW_DM_DEVICE_LOG_PK primary key (DEVICE_LOG_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: nW_DM_DEV_LOG_IDX_DEVICE                            */
/*==============================================================*/
create index nW_DM_DEV_LOG_IDX_DEVICE on nW_DM_DEVICE_LOG (
   DEVICE_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_DEV_LOG_IDX_JOB                               */
/*==============================================================*/
create index nW_DM_DEV_LOG_IDX_JOB on nW_DM_DEVICE_LOG (
   JOB_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Table: nW_DM_DEVICE_LOG_ACTION                             */
/*==============================================================*/
create table nW_DM_DEVICE_LOG_ACTION  (
   VALUE                VARCHAR2(40)                    not null,
   DESCRIPTION          VARCHAR2(100)                   not null,
   constraint nW_DM_DEVICE_LOG_ACTION_PK primary key (VALUE)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_DEVICE_PROV_REQ                               */
/*==============================================================*/
create table nW_DM_DEVICE_PROV_REQ  (
   DEV_PROV_REQ_ID      NUMBER(20)                      not null,
   DEVICE_ID            NUMBER(20),
   PR_ELEMENT_ID        NUMBER(20)                      not null,
   OLD_CURRENT_IMAGE_ID NUMBER(20),
   TO_IMAGE_ID          NUMBER(20),
   TARGET_SOFTWARE_ID   NUMBER(20),
   STATE                VARCHAR2(20)                    not null,
   CAUSE                VARCHAR2(2000),
   READY_TO_NOTIFY      NUMBER(1)                       not null,
   PATH_INDEX           NUMBER(4),
   ASK_PERMISSION_ON_TRIGGER NUMBER(4)                       not null,
   SCHEDULED_TIME       DATE                            not null,
   NOTIFICATION_START_TIME NUMBER(4,2)                     not null,
   NOTIFICATION_END_TIME NUMBER(4,2)                     not null,
   RATE                 NUMBER(20)                      not null,
   UI_MODE              VARCHAR2(15)                    not null,
   JOB_STATE_ID         NUMBER(20),
   LAST_NOTIFICATION_TIME DATE,
   ASK_COUNT            NUMBER(5),
   ASK_INTERVAL         NUMBER(7),
   ASK_BEFORE_DOWN      NUMBER(1),
   ASK_BEFORE_APPLY     NUMBER(1),
   LAST_UPDATED_TIME    DATE,
   CHANGE_VERSION       NUMBER(20)                      not null,
   PENDING_DEVICE_JOB_INDEX NUMBER(20),
   PENDING_DEVICE_ID    NUMBER(20),
   WORKFLOW_ENTRY_ID    NUMBER(20),
   CURRENT_JOB_ADAPTER_ID NUMBER(20),
   INSTALLATION_STATE   VARCHAR2(200),
   INSTALLING_IMAGE     NUMBER(20),
   constraint nW_DM_DEVICE_PROV_REQ_PK primary key (DEV_PROV_REQ_ID)
         using index
       global partition by hash (DEV_PROV_REQ_ID)
       pctfree 10
       initrans 2,
   constraint nW_DM_DPR_DEV_PR_ELEM_UNIQ unique (PR_ELEMENT_ID, DEVICE_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: nW_DM_DEV_PROV_IDX_DEVICE                           */
/*==============================================================*/
create index nW_DM_DEV_PROV_IDX_DEVICE on nW_DM_DEVICE_PROV_REQ (
   DEVICE_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_DEV_PROV_IDX_PR_ELEM                          */
/*==============================================================*/
create index nW_DM_DEV_PROV_IDX_PR_ELEM on nW_DM_DEVICE_PROV_REQ (
   PR_ELEMENT_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_DEV_PROV_REQ_IDX_PDEV                         */
/*==============================================================*/
create index nW_DM_DEV_PROV_REQ_IDX_PDEV on nW_DM_DEVICE_PROV_REQ (
   PENDING_DEVICE_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_DEV_REQ_IDX_JOB_STATE                         */
/*==============================================================*/
create index nW_DM_DEV_REQ_IDX_JOB_STATE on nW_DM_DEVICE_PROV_REQ (
   JOB_STATE_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: IDX_DEC_PRV_REQ_SOFT                                  */
/*==============================================================*/
create index IDX_DEC_PRV_REQ_SOFT on nW_DM_DEVICE_PROV_REQ (
   TARGET_SOFTWARE_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_DISCOVERY_JOB_NODE                            */
/*==============================================================*/
create table nW_DM_DISCOVERY_JOB_NODE  (
   DISCOVERY_JOB_ID     NUMBER(20)                      not null,
   ROOT_NODE            VARCHAR2(500)                   not null,
   constraint PK_NW_DM_DISCOVERY_JOB_NODE primary key (DISCOVERY_JOB_ID, ROOT_NODE)
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_DISCOVER_RESULTS                              */
/*==============================================================*/
create table nW_DM_DISCOVER_RESULTS  (
   DEV_PROV_REQ_ID      NUMBER(20)                      not null,
   RESULT_NAME          VARCHAR2(300)                   not null,
   ITEM_DATA_KIND       VARCHAR2(20),
   UPDATE_ID            VARCHAR2(255),
   RAW_DATA             CLOB,
   BINARY_DATA          BLOB,
   M_FORMAT             VARCHAR2(255),
   M_TYPE               VARCHAR2(255),
   constraint nW_DM_DISCOVER_RESULTS_PK primary key (DEV_PROV_REQ_ID, RESULT_NAME)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  lob
 ( RAW_DATA )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( BINARY_DATA )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
  noparallel;

/*==============================================================*/
/* Table: nW_DM_DISC_JOB_ST_NODE                              */
/*==============================================================*/
create table nW_DM_DISC_JOB_ST_NODE  (
   DISCOVERY_JOB_STATE_ID NUMBER(20)                      not null,
   ROOT_NODE            VARCHAR2(500)                   not null,
   constraint PK_NW_DM_DISC_JOB_ST_NODE primary key (DISCOVERY_JOB_STATE_ID, ROOT_NODE)
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_DM_CMD                                        */
/*==============================================================*/
create table nW_DM_DM_CMD  (
   DM_CMD_ID            NUMBER(20)                      not null,
   CMD_TYPE             VARCHAR2(255)                   not null,
   NAME                 VARCHAR2(255),
   TARGET               VARCHAR2(255),
   M_FORMAT             VARCHAR2(255),
   M_TYPE               VARCHAR2(255),
   ITEM_DATA_KIND       VARCHAR2(20),
   UPDATE_ID            VARCHAR2(255),
   RAW_DATA             VARCHAR2(255),
   BINARY_DATA          BLOB,
   ALERT_CODE           VARCHAR2(255),
   DM_CMD_PKG_ID        NUMBER(20),
   CMD_INDEX            NUMBER(20),
   COMPOSITE_COMMAND_ID NUMBER(20),
   constraint SYS_C003462 primary key (DM_CMD_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  lob
 ( BINARY_DATA )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
  noparallel;

/*==============================================================*/
/* Index: nW_DM_DM_CMD_IDX_CMD_PKG                            */
/*==============================================================*/
create index nW_DM_DM_CMD_IDX_CMD_PKG on nW_DM_DM_CMD (
   DM_CMD_PKG_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Table: nW_DM_DM_CMD_PKG                                    */
/*==============================================================*/
create table nW_DM_DM_CMD_PKG  (
   DM_CMD_PKG_ID        NUMBER(20)                      not null,
   STATE                VARCHAR2(255),
   constraint SYS_C003441 primary key (DM_CMD_PKG_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_DM_CMD_RESPONSE                               */
/*==============================================================*/
create table nW_DM_DM_CMD_RESPONSE  (
   DM_CMD_RESPONSE_ID   NUMBER(20)                      not null,
   RESPONSE_TYPE        VARCHAR2(255),
   PARENT_COMMAND_RESPONSE_ID NUMBER(20),
   CHILD_INDEX          NUMBER(20),
   URL_REF              VARCHAR2(255),
   STATUSCODE           NUMBER(20),
   PKG_RESPONSE_PROC_ID NUMBER(20),
   RESPONSE_INDEX       NUMBER(20),
   constraint SYS_C003446 primary key (DM_CMD_RESPONSE_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: nW_DM_CMD_RESP_IDX_PROC_ID                          */
/*==============================================================*/
create index nW_DM_CMD_RESP_IDX_PROC_ID on nW_DM_DM_CMD_RESPONSE (
   PKG_RESPONSE_PROC_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Table: nW_DM_DM_CMD_SESSION                                */
/*==============================================================*/
create table nW_DM_DM_CMD_SESSION  (
   DM_CMD_SESSION_ID    NUMBER(20)                      not null,
   CMD_SESSION_TYPE     VARCHAR2(255)                   not null,
   MAN_EXT_ID           VARCHAR2(255),
   MAN_MOD_ID           VARCHAR2(255),
   DEVICE_ID            VARCHAR2(255),
   JOB_ADAPTER_ID       NUMBER(20),
   constraint SYS_C003459 primary key (DM_CMD_SESSION_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: nW_DM_CMD_SESS_IDX_JOB_ADAP                         */
/*==============================================================*/
create index nW_DM_CMD_SESS_IDX_JOB_ADAP on nW_DM_DM_CMD_SESSION (
   JOB_ADAPTER_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Table: nW_DM_DM_JOB_ADAPTER                                */
/*==============================================================*/
create table nW_DM_DM_JOB_ADAPTER  (
   CHANGE_VERSION       NUMBER(20),
   DEVICE_ID            NUMBER(20),
   DEVICE_JOB_ID        NUMBER(20),
   NEXT_PROFILE         NUMBER(20),
   JOB_ADAPTER_TYPE     VARCHAR2(200),
   JOB_STATE_ID         NUMBER(20),
   JOB_ADAPTER_ID       NUMBER(20)                      not null,
   constraint nW_DM_DM_JOB_ADAPTER_PK primary key (JOB_ADAPTER_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: nW_DM_JOB_ADAP_IDX_DEVICE                           */
/*==============================================================*/
create index nW_DM_JOB_ADAP_IDX_DEVICE on nW_DM_DM_JOB_ADAPTER (
   DEVICE_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_JOB_ADAP_IDX_DEV_JOB                          */
/*==============================================================*/
create index nW_DM_JOB_ADAP_IDX_DEV_JOB on nW_DM_DM_JOB_ADAPTER (
   DEVICE_JOB_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_JOB_ADAP_IDX_JOBSTATE                         */
/*==============================================================*/
create index nW_DM_JOB_ADAP_IDX_JOBSTATE on nW_DM_DM_JOB_ADAPTER (
   JOB_STATE_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Table: nW_DM_DM_JOB_EXEC_CLIENT                            */
/*==============================================================*/
create table nW_DM_DM_JOB_EXEC_CLIENT  (
   JOB_EXEC_CLIENT_ID   NUMBER(20)                      not null,
   JOB_CLIENT_TYPE      VARCHAR2(255)                   not null,
   DEVICE_JOB_ID        NUMBER(20),
   WORKFLOW_JOB_STATE_ID NUMBER(20),
   ACTION_ID            VARCHAR2(255),
   STATE                VARCHAR2(20),
   constraint SYS_C003483 primary key (JOB_EXEC_CLIENT_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: nW_DM_JOB_EXEC_IDX_DEV_JOB                          */
/*==============================================================*/
create index nW_DM_JOB_EXEC_IDX_DEV_JOB on nW_DM_DM_JOB_EXEC_CLIENT (
   DEVICE_JOB_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_JOB_EXEC_IDX_JOBSTATE                         */
/*==============================================================*/
create index nW_DM_JOB_EXEC_IDX_JOBSTATE on nW_DM_DM_JOB_EXEC_CLIENT (
   WORKFLOW_JOB_STATE_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Table: nW_DM_DM_PKG_HANDLER                                */
/*==============================================================*/
create table nW_DM_DM_PKG_HANDLER  (
   DM_PKG_HANDLER_ID    NUMBER(20)                      not null,
   HANDLER_TYPE         VARCHAR2(255)                   not null,
   PACKAGE_SENDER_ID    NUMBER(20),
   COMMAND_PACKAGE_ID   NUMBER(20),
   COMMAND_RESPONSE_BUILDER_ID NUMBER(20),
   DM_SESSION_ID        NUMBER(20),
   constraint SYS_C003478 primary key (DM_PKG_HANDLER_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: nW_DM_DM_PKG_HAND_IDX_SESS                          */
/*==============================================================*/
create index nW_DM_DM_PKG_HAND_IDX_SESS on nW_DM_DM_PKG_HANDLER (
   DM_SESSION_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Table: nW_DM_DM_PKG_RESP_PROC                              */
/*==============================================================*/
create table nW_DM_DM_PKG_RESP_PROC  (
   DM_PKG_RESP_PROC_ID  NUMBER(20)                      not null,
   PACKAGE_SENDER_ID    NUMBER(20),
   constraint SYS_C003439 primary key (DM_PKG_RESP_PROC_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: Index_PKG_RESP_PROC_2                               */
/*==============================================================*/
create index Index_PKG_RESP_PROC_2 on nW_DM_DM_PKG_RESP_PROC (
   PACKAGE_SENDER_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_DM_PKG_SENDER                                 */
/*==============================================================*/
create table nW_DM_DM_PKG_SENDER  (
   DM_PKG_SENDER_ID     NUMBER(20)                      not null,
   COMMAND_PACKAGE_ID   NUMBER(20),
   NEXT_COMMAND_INDEX   NUMBER(20),
   NEXT_ITEM_INDEX      NUMBER(20),
   CHUNK_INDEX          NUMBER(20),
   constraint SYS_C003468 primary key (DM_PKG_SENDER_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: Index_PKG_SENDER_2                                  */
/*==============================================================*/
create index Index_PKG_SENDER_2 on nW_DM_DM_PKG_SENDER (
   COMMAND_PACKAGE_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_DM_PKG_SENDER_CMD_IDS                         */
/*==============================================================*/
create table nW_DM_DM_PKG_SENDER_CMD_IDS  (
   DM_PKG_SENDER_ID     NUMBER(20)                      not null,
   MSG_ID               VARCHAR2(255)                   not null,
   CMD_INDEX            NUMBER(20)                      not null,
   CMD_ID               NUMBER(20)                      not null,
   constraint SYS_C003475 primary key (DM_PKG_SENDER_ID, MSG_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_DM_RESULTS_MAP                                */
/*==============================================================*/
create table nW_DM_DM_RESULTS_MAP  (
   DM_CMD_RESPONSE_ID   NUMBER(20)                      not null,
   ITEM_DATA_KIND       VARCHAR2(20),
   UPDATE_ID            VARCHAR2(255),
   RAW_DATA             CLOB,
   BINARY_DATA          BLOB,
   M_FORMAT             VARCHAR2(255),
   M_TYPE               VARCHAR2(255),
   MAP_KEY              VARCHAR2(255)                   not null,
   constraint SYS_C003471 primary key (DM_CMD_RESPONSE_ID, MAP_KEY)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  lob
 ( RAW_DATA )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( BINARY_DATA )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
  noparallel;

/*==============================================================*/
/* Table: nW_DM_DM_SESSION                                    */
/*==============================================================*/
create table nW_DM_DM_SESSION  (
   DM_SESSION_ID        NUMBER(20)                      not null,
   EXTERNAL_ID          VARCHAR2(255),
   IS_CLIENT_AUTHENTICATED NUMBER(1),
   IS_SERVER_AUTHENTICATED NUMBER(1),
   IS_SERVER_USING_HMAC NUMBER(1),
   SERVER_AUTH_TYPE     VARCHAR2(100),
   MAX_MSG_SIZE         NUMBER(20),
   MAX_OBJ_SIZE         NUMBER(20),
   NUM_SERVER_AUTH_ATTEMPTS NUMBER(20),
   DEVICE_EXTERNAL_ID   VARCHAR2(255),
   CUR_SERVER_NONCE     VARCHAR2(255),
   CUR_CLIENT_NONCE     VARCHAR2(255),
   MSG_ID_COUNTER       NUMBER(20),
   CUR_PKG_HANDLER_ID   NUMBER(20),
   CMD_SESSION_HANDLER_ID NUMBER(20),
   SESSION_AUTH_ID      NUMBER(20),
   CUR_MSG_ID_PROC      VARCHAR2(40)                    not null,
   constraint SYS_C003449 primary key (DM_SESSION_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: nW_DM_DM_SESS_IDX_DEVICE                            */
/*==============================================================*/
create index nW_DM_DM_SESS_IDX_DEVICE on nW_DM_DM_SESSION (
   EXTERNAL_ID ASC,
   DEVICE_EXTERNAL_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_DM_SESS_IDX_PKG_HAND                          */
/*==============================================================*/
create index nW_DM_DM_SESS_IDX_PKG_HAND on nW_DM_DM_SESSION (
   CUR_PKG_HANDLER_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_DM_SESS_IDX_SESS_AUTH                         */
/*==============================================================*/
create index nW_DM_DM_SESS_IDX_SESS_AUTH on nW_DM_DM_SESSION (
   SESSION_AUTH_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_DM_SESS_IDX_SESS_HAND                         */
/*==============================================================*/
create index nW_DM_DM_SESS_IDX_SESS_HAND on nW_DM_DM_SESSION (
   CMD_SESSION_HANDLER_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Table: nW_DM_DTREE                                         */
/*==============================================================*/
create table nW_DM_DTREE  (
   DEVICE_TREE_ID       NUMBER(20)                      not null,
   ROOT_NODE_ID         NUMBER(20)                      not null,
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint nW_DM_DTREE_TEST_PK primary key (DEVICE_TREE_ID)
         using index
       global partition by hash (DEVICE_TREE_ID)
       pctfree 10
       initrans 2
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: Index_DTREE2                                        */
/*==============================================================*/
create index Index_DTREE2 on nW_DM_DTREE (
   ROOT_NODE_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_DTREE_NODE                                    */
/*==============================================================*/
create table nW_DM_DTREE_NODE  (
   DEVICE_TREE_NODE_ID  NUMBER(20)                      not null,
   IS_LEAF_NODE         NUMBER(1)                       not null,
   NODE_NAME            VARCHAR2(255),
   PARENT_NODE_ID       NUMBER(20),
   M_FORMAT             VARCHAR2(255),
   M_TYPE               VARCHAR2(255),
   M_SIZE               VARCHAR2(20),
   M_VERSION            NUMBER(6),
   M_TITLE              VARCHAR2(255),
   M_TSTAMP             VARCHAR2(20),
   M_ACL                VARCHAR2(255),
   LAST_READ            DATE,
   ITEM_DATA_KIND       VARCHAR2(20),
   UPDATE_ID            VARCHAR2(255),
   RAW_DATA             CLOB,
   BINARY_DATA          BLOB,
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint nW_DM_DTREE_NODE_PK primary key (DEVICE_TREE_NODE_ID)
         using index
       global partition by hash (DEVICE_TREE_NODE_ID)
       pctfree 10
       initrans 2
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  lob
 ( RAW_DATA )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( BINARY_DATA )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
  noparallel;

/*==============================================================*/
/* Index: nW_DM_DTREE_NODE_IDX_NAME                           */
/*==============================================================*/
create index nW_DM_DTREE_NODE_IDX_NAME on nW_DM_DTREE_NODE (
   NODE_NAME ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_DTREE_NODE_IDX_PARENT                         */
/*==============================================================*/
create index nW_DM_DTREE_NODE_IDX_PARENT on nW_DM_DTREE_NODE (
   PARENT_NODE_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_DTREE_NODE_IDX_UPDATE                         */
/*==============================================================*/
create index nW_DM_DTREE_NODE_IDX_UPDATE on nW_DM_DTREE_NODE (
   DEVICE_TREE_NODE_ID ASC,
   CHANGE_VERSION ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Table: nW_DM_FW_JOB_UPDATE_PATH                            */
/*==============================================================*/
create table nW_DM_FW_JOB_UPDATE_PATH  (
   FW_JOB_STATE_ID      NUMBER(20),
   UPDATE_ID            NUMBER(20),
   PATH_INDEX           NUMBER(20)
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: nW_DM_UPD_PATH_IDX_JOBSTATE                         */
/*==============================================================*/
create index nW_DM_UPD_PATH_IDX_JOBSTATE on nW_DM_FW_JOB_UPDATE_PATH (
   FW_JOB_STATE_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Table: nW_DM_GET_CMD_ITEM                                  */
/*==============================================================*/
create table nW_DM_GET_CMD_ITEM  (
   GET_CMD_ID           NUMBER(20)                      not null,
   ELT                  VARCHAR2(255),
   ITEM_INDEX           NUMBER(20)                      not null,
   constraint SYS_C003466 primary key (GET_CMD_ID, ITEM_INDEX)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_IMAGE                                         */
/*==============================================================*/
create table nW_DM_IMAGE  (
   IMAGE_ID             NUMBER(20)                      not null,
   IMAGE_EXTERNAL_ID    VARCHAR2(255)                   not null,
   MODEL_ID             NUMBER(20)                      not null,
   STATUS_ID            NUMBER(20)                      not null,
   APPLICABLE_TO_ALL_CARRIERS NUMBER(1)                       not null,
   DESCRIPTION          VARCHAR2(4000),
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint nW_DM_IMAGE_PK primary key (IMAGE_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_DM_EXT_ID_MODEL_UNQ unique (IMAGE_EXTERNAL_ID, MODEL_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: nW_DM_IMAGE_IDX_EXT                                 */
/*==============================================================*/
create index nW_DM_IMAGE_IDX_EXT on nW_DM_IMAGE (
   IMAGE_EXTERNAL_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_IMAGE_IDX_MODEL                               */
/*==============================================================*/
create index nW_DM_IMAGE_IDX_MODEL on nW_DM_IMAGE (
   MODEL_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Table: nW_DM_IMAGE_CARRIERS                                */
/*==============================================================*/
create table nW_DM_IMAGE_CARRIERS  (
   IMAGE_CARRIERS_ID    NUMBER(20)                      not null,
   IMAGE_ID             NUMBER(20),
   CARRIER_ID           NUMBER(20)                      not null,
   STATUS_ID            NUMBER(20)                      not null,
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint nW_DM_IMG_CARR_PK primary key (IMAGE_CARRIERS_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_DM_IMG_CARR_UNQ unique (IMAGE_ID, CARRIER_ID)
         deferrable
   initially deferred
   using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: Index_IMAGE_CARRIERS2                               */
/*==============================================================*/
create index Index_IMAGE_CARRIERS2 on nW_DM_IMAGE_CARRIERS (
   STATUS_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_IMAGE_UPDATE_STATUS                           */
/*==============================================================*/
create table nW_DM_IMAGE_UPDATE_STATUS  (
   STATUS_ID            NUMBER(20)                      not null,
   NAME                 VARCHAR2(20)                    not null,
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint nW_DM_STATUS_PK primary key (STATUS_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_JMSSTATE                                      */
/*==============================================================*/
create table nW_DM_JMSSTATE  (
   RECORDHANDLE         NUMBER,
   RECORDSTATE          NUMBER,
   RECORDGENERATION     NUMBER
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: nW_DM_JMSMSG_X                                      */
/*==============================================================*/
create index nW_DM_JMSMSG_X on nW_DM_JMSSTATE (
   RECORDHANDLE ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Table: nW_DM_JMSSTORE                                      */
/*==============================================================*/
create table nW_DM_JMSSTORE  (
   RECORDHANDLE         NUMBER,
   RECORDSTATE          NUMBER,
   RECORD               LONG RAW
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: nW_DM_JMSMSGQ_X                                     */
/*==============================================================*/
create index nW_DM_JMSMSGQ_X on nW_DM_JMSSTORE (
   RECORDHANDLE ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Table: nW_DM_JOB_ASSIGNMENTS                               */
/*==============================================================*/
create table nW_DM_JOB_ASSIGNMENTS  (
   JOB_STATE_ID         NUMBER(20)                      not null,
   ASSIGNMENT_INDEX     NUMBER(20)                      not null,
   PROFILE_ASSIGNMENT_ID NUMBER(20)                      not null,
   constraint PK_NW_DM_JOB_ASSIGNMENTS primary key (JOB_STATE_ID, ASSIGNMENT_INDEX, PROFILE_ASSIGNMENT_ID)
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_JOB_STATE                                     */
/*==============================================================*/
create table nW_DM_JOB_STATE  (
   JOB_STATE_ID         NUMBER(20)                      not null,
   JOB_STATE_TYPE       VARCHAR2(255)                   not null,
   JOB_ADAPTER_ID       NUMBER(20),
   JOB_ID               NUMBER(20),
   DEVICE_ID            NUMBER(20),
   WORKFLOW_ENTRY_ID    NUMBER(20),
   TO_IMAGE_ID          NUMBER(20),
   OLD_CURRENT_IMAGE_ID NUMBER(20),
   ASK_INTERVAL         NUMBER(20),
   ASK_COUNT            NUMBER(20),
   ASK_BEFORE_APPLY     NUMBER(1),
   ASK_BEFORE_DOWN      NUMBER(1),
   PATH_INDEX           NUMBER(20),
   INSTALLATION_STATE   VARCHAR2(255),
   WORKFLOW_STATE       VARCHAR2(10),
   UI_MODE              VARCHAR2(255),
   ASK_USER             NUMBER(1),
   INSTALLING_IMAGE     NUMBER(20),
   COMMAND_PACKAGE_ID   NUMBER(20),
   constraint SYS_C003600 primary key (JOB_STATE_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: nW_DM_JOB_STATE_IDX_DEVICE                          */
/*==============================================================*/
create index nW_DM_JOB_STATE_IDX_DEVICE on nW_DM_JOB_STATE (
   DEVICE_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_JOB_STATE_IDX_JOBADAP                         */
/*==============================================================*/
create index nW_DM_JOB_STATE_IDX_JOBADAP on nW_DM_JOB_STATE (
   JOB_ADAPTER_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_JOB_STATE_IDX_JOB_ID                          */
/*==============================================================*/
create index nW_DM_JOB_STATE_IDX_JOB_ID on nW_DM_JOB_STATE (
   JOB_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Table: nW_DM_MANUFACTURER                                  */
/*==============================================================*/
create table nW_DM_MANUFACTURER  (
   MANUFACTURER_ID      NUMBER(20)                      not null,
   NAME                 VARCHAR2(255),
   DESCRIPTION          VARCHAR2(4000),
   MANUFACTURER_EXTERNAL_ID VARCHAR2(200)                   not null,
   LAST_UPDATED_BY      VARCHAR2(200),
   LAST_UPDATED_TIME    DATE,
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint nW_DM_MANUFACTURER_PK primary key (MANUFACTURER_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_DM_MAN_EXT_ID_UNIQE unique (MANUFACTURER_EXTERNAL_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_DM_MAN_NAME_UNIQE unique (NAME)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_MAPPING_NODE_NAME                             */
/*==============================================================*/
create table nW_DM_MAPPING_NODE_NAME  (
   PROFILE_MAPPING_ID   NUMBER(20)                      not null,
   DDF_NODE_ID          NUMBER(20)                      not null,
   NODE_NAME            VARCHAR2(255)                   not null,
   constraint PK_NW_DM_MAPPING_NODE_NAME primary key (PROFILE_MAPPING_ID, DDF_NODE_ID, NODE_NAME)
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_MODEL                                         */
/*==============================================================*/
create table nW_DM_MODEL  (
   MODEL_ID             NUMBER(20)                      not null,
   NAME                 VARCHAR2(255)                   not null,
   DESCRIPTION          VARCHAR2(4000),
   MANUFACTURER_MODEL_ID VARCHAR2(200)                   not null,
   MANUFACTURER_ID      NUMBER(20)                      not null,
   MODEL_FAMILY_ID      NUMBER(20),
   SUPPORTED_DOWNLOAD_METHODS NUMBER(2)                       not null,
   LAST_UPDATED_BY      VARCHAR2(200),
   LAST_UPDATED_TIME    DATE,
   IS_OMA_DM_ENABLED    NUMBER(1)                       not null,
   OMA_DM_VERSION       VARCHAR(16),
   IS_OMA_CP_ENABLED    NUMBER(1)                       not null,
   OMA_CP_VERSION       VARCHAR(16),
   IS_NOKIA_OTA_ENABLED NUMBER(1)                       not null,
   NOKIA_OTA_VERSION    VARCHAR(16),
   SERVER_AUTH_TYPE     VARCHAR2(100),
   IS_PLAIN_PROFILE     NUMBER(1)                       not null,
   IS_USE_ENC_FOR_OMA_MSG NUMBER(1)                       not null,
   IS_USE_NEXT_NONCE_PER_PKG NUMBER(1)                       not null,
   FIRMWARE_NODE        VARCHAR2(255),
   FIRMWARE_UPDATE_NODE VARCHAR2(255),
   FIRMWARE_UPDATE_REPLACE_NODE VARCHAR2(255),
   FIRMWARE_UPDATE_EXEC_NODE VARCHAR2(255),
   FIRMWARE_UPDATE_STATUS_NODE VARCHAR2(255),
   CHANGE_VERSION       NUMBER(20)                      not null,
   FAMILY_EXT_ID        VARCHAR2(255),
   OPERATING_SYSTEM     VARCHAR2(255),
   ANNOUNCED_DATE       DATE,
   ICON                 BLOB,
   CREATED_TIME         DATE                            not null,
   constraint nW_DM_MODEL_PK primary key (MODEL_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_DM_MODEL_NAME_UNQ unique (NAME, MANUFACTURER_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: Index_MODEL2                                        */
/*==============================================================*/
create index Index_MODEL2 on nW_DM_MODEL (
   MANUFACTURER_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_MODEL_CHARACTER                               */
/*==============================================================*/
create table nW_DM_MODEL_CHARACTER  (
   MODEL_CHARACTER_ID   NUMBER(20)                      not null,
   MODEL_ID             NUMBER(20),
   CATEGORY             VARCHAR(255),
   NAME                 VARCHAR(255)                    not null,
   VALUE                VARCHAR2(1024),
   constraint PK_NW_DM_MODEL_CHARACTER primary key (MODEL_CHARACTER_ID)
);

/*==============================================================*/
/* Index: IDX_FK_MOD_CHARACTER                                  */
/*==============================================================*/
create index IDX_FK_MOD_CHARACTER on nW_DM_MODEL_CHARACTER (
   MODEL_ID ASC
);

/*==============================================================*/
/* Index: IDX_MODEL_CHARA_CATEG                                 */
/*==============================================================*/
create index IDX_MODEL_CHARA_CATEG on nW_DM_MODEL_CHARACTER (
   CATEGORY ASC
);

/*==============================================================*/
/* Index: IDX_MODEL_CHARA_NAME                                  */
/*==============================================================*/
create index IDX_MODEL_CHARA_NAME on nW_DM_MODEL_CHARACTER (
   NAME ASC
);

/*==============================================================*/
/* Index: IDX_MODEL_CHARA_VAL                                   */
/*==============================================================*/
create index IDX_MODEL_CHARA_VAL on nW_DM_MODEL_CHARACTER (
   VALUE ASC
);

/*==============================================================*/
/* Table: nW_DM_MODEL_CLIENT_PROV_MAP                         */
/*==============================================================*/
create table nW_DM_MODEL_CLIENT_PROV_MAP  (
   MODEL_ID             NUMBER(20)                      not null,
   TEMPLATE_ID          NUMBER(20)                      not null,
   constraint PK_NW_DM_MODEL_CLIENT_PROV_MAP primary key (MODEL_ID, TEMPLATE_ID)
);

/*==============================================================*/
/* Table: nW_DM_MODEL_DDF_TREE                                */
/*==============================================================*/
create table nW_DM_MODEL_DDF_TREE  (
   DEVICE_MODEL_ID      NUMBER(20)                      not null,
   DDF_TREE_ID          NUMBER(20)                      not null,
   constraint PK_NW_DM_MODEL_DDF_TREE primary key (DEVICE_MODEL_ID, DDF_TREE_ID)
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_MODEL_DM_BOOT_PROP                            */
/*==============================================================*/
create table nW_DM_MODEL_DM_BOOT_PROP  (
   MODEL_ID             NUMBER(20)                      not null,
   PROP_NAME            VARCHAR2(255)                   not null,
   PROP_VALUE           VARCHAR2(255)                   not null,
   constraint nW_DM_MOD_DM_PROP_BT_PK primary key (MODEL_ID, PROP_NAME)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_MODEL_DM_PROP                                 */
/*==============================================================*/
create table nW_DM_MODEL_DM_PROP  (
   MODEL_ID             NUMBER(20)                      not null,
   PROP_NAME            VARCHAR2(255)                   not null,
   PROP_VALUE           VARCHAR2(255)                   not null,
   constraint nW_DM_MODEL_DM_PROPS_PK primary key (MODEL_ID, PROP_NAME)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_MODEL_PROFILE_MAP                             */
/*==============================================================*/
create table nW_DM_MODEL_PROFILE_MAP  (
   DEVICE_MODEL_ID      NUMBER(20)                      not null,
   PROFILE_MAPPING_ID   NUMBER(20)                      not null,
   constraint PK_NW_DM_MODEL_PROFILE_MAP primary key (DEVICE_MODEL_ID, PROFILE_MAPPING_ID)
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_MODEL_TAC                                     */
/*==============================================================*/
create table nW_DM_MODEL_TAC  (
   MODEL_ID             NUMBER(20)                      not null,
   TAC                  VARCHAR2(255)                   not null,
   constraint PK_NW_DM_MODEL_TAC primary key (MODEL_ID, TAC)
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_NODES_TO_DISCOVER                             */
/*==============================================================*/
create table nW_DM_NODES_TO_DISCOVER  (
   DEV_PROV_REQ_ID      NUMBER(20)                      not null,
   NODE_INDEX           NUMBER(20)                      not null,
   NODE_NAME            VARCHAR2(300),
   constraint PK_JOB_NODES_TO_DISCOVER primary key (DEV_PROV_REQ_ID, NODE_INDEX)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_PATH_ELEMENT                                  */
/*==============================================================*/
create table nW_DM_PATH_ELEMENT  (
   PATH_ELEMENT_ID      NUMBER(20)                      not null,
   PR_ELEMENT_ID        NUMBER(20),
   UPDATE_ID            NUMBER(20),
   SEQUENCE_NUMBER      NUMBER(4)                       not null,
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint nW_DM_PATH_ELEM_PK primary key (PATH_ELEMENT_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_PREV_PKG_RESP                                 */
/*==============================================================*/
create table nW_DM_PREV_PKG_RESP  (
   PREV_PKG_RESP_ID     NUMBER(20)                      not null,
   DM_SESSION_ID        NUMBER(20)                      not null,
   RESPONSE_DATA        BLOB                            not null,
   HMAC_HEADER          VARCHAR2(255),
   constraint SYS_C003453 primary key (PREV_PKG_RESP_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  lob
 ( RESPONSE_DATA )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
  noparallel;

/*==============================================================*/
/* Index: nW_DM_PREV_RESP_IDX_SESSION                         */
/*==============================================================*/
create index nW_DM_PREV_RESP_IDX_SESSION on nW_DM_PREV_PKG_RESP (
   DM_SESSION_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Table: nW_DM_PROFILE_ASSIGNMENT                            */
/*==============================================================*/
create table nW_DM_PROFILE_ASSIGNMENT  (
   PROFILE_ASSIGNMENT_ID NUMBER(20)                      not null,
   PROFILE_ROOT_NODE_PATH VARCHAR2(255),
   STATUS               VARCHAR2(200)                   not null,
   LAST_SENT_TO_DEVICE  DATE,
   PROFILE_ID           NUMBER(20)                      not null,
   DEVICE_ID            NUMBER(20),
   LAST_UPDATED_BY      VARCHAR2(200),
   LAST_UPDATED_TIME    DATE,
   ASSIGNMENT_INDEX     NUMBER(20),
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint SYS_C003387 primary key (PROFILE_ASSIGNMENT_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: nW_DM_PRF_ASSGN_IDX_DEVICE                          */
/*==============================================================*/
create index nW_DM_PRF_ASSGN_IDX_DEVICE on nW_DM_PROFILE_ASSIGNMENT (
   DEVICE_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_PRF_ASSGN_IDX_PROFILE                         */
/*==============================================================*/
create index nW_DM_PRF_ASSGN_IDX_PROFILE on nW_DM_PROFILE_ASSIGNMENT (
   PROFILE_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Table: nW_DM_PROFILE_ASSIGN_VALUE                          */
/*==============================================================*/
create table nW_DM_PROFILE_ASSIGN_VALUE  (
   PROFILE_ASSIGNMENT_ID NUMBER(20)                      not null,
   ATTRIBUTE_VALUE_ID   NUMBER(20)                      not null,
   constraint PK_NW_DM_PROFILE_ASSIGN_VALUE primary key (PROFILE_ASSIGNMENT_ID, ATTRIBUTE_VALUE_ID)
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_PROFILE_ATTRIBUTE                             */
/*==============================================================*/
create table nW_DM_PROFILE_ATTRIBUTE  (
   ATTRIBUTE_ID         NUMBER(20)                      not null,
   NAME                 VARCHAR2(255)                   not null,
   DISPLAY_NAME         VARCHAR2(255),
   DESCRIPTION          VARCHAR2(4000),
   IS_REQUIRED          NUMBER(1)                       not null,
   DEFAULT_VALUE        VARCHAR2(255),
   IS_USER_ATTRIBUTE    NUMBER(1)                       not null,
   IS_MULTIVALUED       NUMBER(1)                       not null,
   DISPLAY_VALUE        NUMBER(1)                       not null,
   IS_VALUE_UNIQUE      NUMBER(1)                       not null,
   ATTRIBUTE_INDEX      NUMBER(20),
   ATTRIBUTE_TYPE_ID    NUMBER(20),
   TEMPLATE_ID          NUMBER(20),
   constraint SYS_C003338 primary key (ATTRIBUTE_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_PRF_ATTR_NAME_UNIQE unique (NAME, TEMPLATE_ID)
         deferrable
   initially deferred
   using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: IDX_PROFILE_ATTR_FK_TEMPLATE                          */
/*==============================================================*/
create index IDX_PROFILE_ATTR_FK_TEMPLATE on nW_DM_PROFILE_ATTRIBUTE (
   TEMPLATE_ID ASC
);

/*==============================================================*/
/* Index: IDX_PROFILE_ATTRIBUTE_FK_TYPE                         */
/*==============================================================*/
create index IDX_PROFILE_ATTRIBUTE_FK_TYPE on nW_DM_PROFILE_ATTRIBUTE (
   ATTRIBUTE_TYPE_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_PROFILE_ATTRIB_TYPE                           */
/*==============================================================*/
create table nW_DM_PROFILE_ATTRIB_TYPE  (
   ATTRIBUTE_TYPE_ID    NUMBER(20)                      not null,
   NAME                 VARCHAR2(255)                   not null,
   DESCRIPTION          VARCHAR2(4000),
   LAST_UPDATED_BY      VARCHAR2(200),
   LAST_UPDATED_TIME    DATE,
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint SYS_C003318 primary key (ATTRIBUTE_TYPE_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_ATTRIB_TYPE_NAME_UNIQE unique (NAME)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_PROFILE_ATTRIB_VALUE                          */
/*==============================================================*/
create table nW_DM_PROFILE_ATTRIB_VALUE  (
   ATTRIBUTE_VALUE_ID   NUMBER(20)                      not null,
   IS_MULTI_VALUED      NUMBER(1)                       not null,
   ATTRIBUTE_ID         NUMBER(20)                      not null,
   ITEM_DATA_KIND       VARCHAR2(20),
   UPDATE_ID            VARCHAR2(255),
   RAW_DATA             CLOB,
   BINARY_DATA          BLOB,
   M_FORMAT             VARCHAR2(255),
   M_TYPE               VARCHAR2(255),
   constraint SYS_C003325 primary key (ATTRIBUTE_VALUE_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  lob
 ( RAW_DATA )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( BINARY_DATA )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
  noparallel;

/*==============================================================*/
/* Index: IDX_PROFILE_ATTRIB_VALUE_FK                           */
/*==============================================================*/
create index IDX_PROFILE_ATTRIB_VALUE_FK on nW_DM_PROFILE_ATTRIB_VALUE (
   ATTRIBUTE_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_PROFILE_CATEGORY                              */
/*==============================================================*/
create table nW_DM_PROFILE_CATEGORY  (
   CATEGORY_ID          NUMBER(20)                      not null,
   NAME                 VARCHAR2(255)                   not null,
   DESCRIPTION          VARCHAR2(255),
   LAST_UPDATED_BY      VARCHAR2(200),
   LAST_UPDATED_TIME    DATE,
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint SYS_C003313 primary key (CATEGORY_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_PRF_CAT_NAME_UNIQE unique (NAME)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_PROFILE_MAPPING                               */
/*==============================================================*/
create table nW_DM_PROFILE_MAPPING  (
   PROFILE_MAPPING_ID   NUMBER(20)                      not null,
   TEMPLATE_ID          NUMBER(20)                      not null,
   PROFILE_ROOT_NODE_ID NUMBER(20)                      not null,
   ROOT_NODE_PATH       VARCHAR2(400),
   SHARE_ROOT_NODE      NUMBER(1),
   ASSIGN_TO_DEVICE     NUMBER(1),
   LINKED_PROFILE_TYPE  VARCHAR2(200),
   NEED_TO_DISCOVERY    NUMBER(1)                      default 1 not null,
   DISCOVERY_NODE_PATHS VARCHAR2(4000),
   LAST_UPDATED_BY      VARCHAR2(200),
   LAST_UPDATED_TIME    DATE,
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint SYS_C003355 primary key (PROFILE_MAPPING_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

comment on column nW_DM_PROFILE_MAPPING.NEED_TO_DISCOVERY is
'表示当与此Mapping对应Assignment任务运行结束时,是否还需要自动采集一些节点. 缺省为true, 1';

comment on column nW_DM_PROFILE_MAPPING.DISCOVERY_NODE_PATHS is
'当NEED_TO_DISCOVERY为1时, 表示任务结束后需要Discovery节点, 此字段指定需要discovery的节点路径, 路径可以是多个, 但需要以,分隔, 另外路径必须为绝对路径，并不包含dynamic节点。如果need_to_discovery为1, 但本字段为null, 则自动计算需要discovery的路径。
';

/*==============================================================*/
/* Index: IDX_PROFILE_MAPPING_FK_1                              */
/*==============================================================*/
create index IDX_PROFILE_MAPPING_FK_1 on nW_DM_PROFILE_MAPPING (
   PROFILE_ROOT_NODE_ID ASC
);

/*==============================================================*/
/* Index: IDX_PROFILE_MAPPING_FK_2                              */
/*==============================================================*/
create index IDX_PROFILE_MAPPING_FK_2 on nW_DM_PROFILE_MAPPING (
   TEMPLATE_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_PROFILE_NODE_MAPPING                          */
/*==============================================================*/
create table nW_DM_PROFILE_NODE_MAPPING  (
   NODE_MAPPING_ID      NUMBER(20)                      not null,
   ATTRIBUTE_ID         NUMBER(20),
   DDF_NODE_ID          NUMBER(20)                      not null,
   PROFILE_MAPPING_ID   NUMBER(20),
   MAPPING_TYPE         VARCHAR2(10)                    not null,
   COMMAND              VARCHAR(20),
   RELATIVE_NODE_PATH   VARCHAR2(1000),
   VALUE                CLOB,
   VALUE_FORMAT         VARCHAR(100),
   VALUE_DEFAULT_MIME_TYPE VARCHAR(200),
   DISPLAY_NAME         VARCHAR2(200),
   CATEGORY_NAME        VARCHAR2(10),
   PARAM_INDEX          NUMBER(20),
   constraint SYS_C003372 primary key (NODE_MAPPING_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: IDX_PK_NODE_MAPPING_FK_ATTR                           */
/*==============================================================*/
create index IDX_PK_NODE_MAPPING_FK_ATTR on nW_DM_PROFILE_NODE_MAPPING (
   ATTRIBUTE_ID ASC
);

/*==============================================================*/
/* Index: IDX_PK_NODE_MAPPING_FK_DDF                            */
/*==============================================================*/
create index IDX_PK_NODE_MAPPING_FK_DDF on nW_DM_PROFILE_NODE_MAPPING (
   DDF_NODE_ID ASC
);

/*==============================================================*/
/* Index: IDX_PK_NODE_MAPPING_FK_MAPPING                        */
/*==============================================================*/
create index IDX_PK_NODE_MAPPING_FK_MAPPING on nW_DM_PROFILE_NODE_MAPPING (
   PROFILE_MAPPING_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_PROFILE_PARAMETER                             */
/*==============================================================*/
create table nW_DM_PROFILE_PARAMETER  (
   PROFILE_PARAMETER_ID NUMBER(20)                      not null,
   PARAMETER_TYPE       VARCHAR2(10)                    not null,
   DM_TREE_PATH         VARCHAR2(255)                   not null,
   ATTRIBUTE_ID         NUMBER(20),
   LEAF_DDF_NODE_ID     NUMBER(20)                      not null,
   PROFILE_ASSIGNMENT_ID NUMBER(20),
   PARAM_INDEX          NUMBER(20),
   ITEM_DATA_KIND       VARCHAR2(20),
   UPDATE_ID            VARCHAR2(255),
   RAW_DATA             CLOB,
   BINARY_DATA          BLOB,
   M_FORMAT             VARCHAR2(255),
   M_TYPE               VARCHAR2(255),
   PARAM_NAME           VARCHAR2(200),
   constraint SYS_C003394 primary key (PROFILE_PARAMETER_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  lob
 ( RAW_DATA )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( BINARY_DATA )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
  noparallel;

/*==============================================================*/
/* Index: IDX_PROFILE_PARAMETER_FK_1                            */
/*==============================================================*/
create index IDX_PROFILE_PARAMETER_FK_1 on nW_DM_PROFILE_PARAMETER (
   PROFILE_ASSIGNMENT_ID ASC
);

/*==============================================================*/
/* Index: IDX_PROFILE_PARAMETER_FK_2                            */
/*==============================================================*/
create index IDX_PROFILE_PARAMETER_FK_2 on nW_DM_PROFILE_PARAMETER (
   ATTRIBUTE_ID ASC
);

/*==============================================================*/
/* Index: IDX_PROFILE_PARAMETER_FK_3                            */
/*==============================================================*/
create index IDX_PROFILE_PARAMETER_FK_3 on nW_DM_PROFILE_PARAMETER (
   LEAF_DDF_NODE_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_PROFILE_TEMPLATE                              */
/*==============================================================*/
create table nW_DM_PROFILE_TEMPLATE  (
   TEMPLATE_ID          NUMBER(20)                      not null,
   NAME                 VARCHAR2(255)                   not null,
   NEEDS_NAP            NUMBER(1),
   NEEDS_PROXY          NUMBER(1),
   PROFILE_CATEGORY_ID  NUMBER(20)                      not null,
   DESCRIPTION          VARCHAR2(2000),
   LAST_UPDATED_BY      VARCHAR2(200),
   LAST_UPDATED_TIME    DATE,
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint SYS_C003347 primary key (TEMPLATE_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_PRF_TEMPL_NAME_UNIQE unique (NAME)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: IDX_PROF_TEMP_FK_CATE                                 */
/*==============================================================*/
create index IDX_PROF_TEMP_FK_CATE on nW_DM_PROFILE_TEMPLATE (
   PROFILE_CATEGORY_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_PROFILE_VALUE_ITEM                            */
/*==============================================================*/
create table nW_DM_PROFILE_VALUE_ITEM  (
   PROFILE_VALUE_ITEM_ID NUMBER(20)                      not null,
   PROFILE_ATTRIBUTE_VALUE_ID NUMBER(20)                      not null,
   ITEM_DATA_KIND       VARCHAR2(20),
   UPDATE_ID            VARCHAR2(255),
   RAW_DATA             CLOB,
   BINARY_DATA          BLOB,
   M_FORMAT             VARCHAR2(255),
   M_TYPE               VARCHAR2(255),
   constraint PK_NW_DM_PROFILE_VALUE_ITEM primary key (PROFILE_VALUE_ITEM_ID)
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  lob
 ( RAW_DATA )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( BINARY_DATA )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
  noparallel;

/*==============================================================*/
/* Index: Idx_profile_value_item                              */
/*==============================================================*/
create index Idx_profile_value_item on nW_DM_PROFILE_VALUE_ITEM (
   PROFILE_ATTRIBUTE_VALUE_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_PROFILE_VALUE_MAP                             */
/*==============================================================*/
create table nW_DM_PROFILE_VALUE_MAP  (
   PROFILE_ID           NUMBER(20)                      not null,
   ATTRIBUTE_VALUE_ID   NUMBER(20)                      not null,
   VALUE_INDEX          NUMBER(20),
   constraint PK_NW_DM_PROFILE_VALUE_MAP primary key (PROFILE_ID, ATTRIBUTE_VALUE_ID)
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_PROV_REQ                                      */
/*==============================================================*/
create table nW_DM_PROV_REQ  (
   PROV_REQ_ID          NUMBER(20)                      not null,
   PARENT_PROV_REQ_ID   NUMBER(20),
   JOB_MODE             VARCHAR(20)                     not null,
   TYPE                 VARCHAR2(200)                   not null,
   NAME                 VARCHAR2(2000),
   DESCRIPTION          VARCHAR2(2000),
   RUNNING              NUMBER(1)                       not null,
   TARGET_IMAGE_ID      NUMBER(20),
   TARGET_IMAGE_DESCRIPTION VARCHAR2(300),
   TARGET_SOFTWARE_ID   NUMBER(20),
   TARGET_SOFTWARE_DESCRIPTION VARCHAR2(300),
   STATE                VARCHAR2(20)                    not null,
   SCHEDULED_TIME       DATE                            not null,
   EXPIRED_TIME         DATE,
   CONCURRENT_SIZE      NUMBER(10)                      not null,
   CONCURRENT_INTERVAL  NUMBER(12)                      not null,
   CRITERIA             VARCHAR2(100),
   RATE                 NUMBER(20)                      not null,
   ASK_PERMISSION_ON_TRIGGER NUMBER(1)                       not null,
   REQUIRED_NOTIFICATION NUMBER(1)                       not null,
   NOTIFICATION_START_TIME NUMBER(4,2)                     not null,
   NOTIFICATION_END_TIME NUMBER(4,2)                     not null,
   UI_MODE              VARCHAR2(15)                    not null,
   ASK_COUNT            NUMBER(5)                       not null,
   ASK_INTERVAL         NUMBER(7)                       not null,
   ASK_BEFORE_DOWN      NUMBER(1)                       not null,
   ASK_BEFORE_APPLY     NUMBER(1)                       not null,
   JOB_TYPE             VARCHAR2(32)                    not null,
   JOB_TYPE_FOR_DISPLAY VARCHAR2(100)                   not null,
   COMMAND_PACKAGE_ID   NUMBER(20),
   SCRIPT               CLOB,
   APP_LANGUAGE         VARCHAR2(50),
   WORKFLOW_ID          NUMBER(20),
   WORKFLOW_NAME        VARCHAR2(100),
   MAX_RETRIES          NUMBER(2)                       not null,
   MAX_DURATION         NUMBER(6)                       not null,
   CREATED_TIME         DATE                            not null,
   CREATED_BY           VARCHAR2(255),
   LAST_UPDATED_TIME    DATE,
   LAST_UPDATED_BY      VARCHAR2(255),
   CHANGE_VERSION       NUMBER(20)                      not null,
   IS_PROMPT_FOR_BEGINNING NUMBER(1)                       not null,
   PROMPT_TYPE_FOR_BEGINNING VARCHAR(30),
   PROMPT_TEXT_FOR_BEGINNING VARCHAR(300),
   IS_PROMPT_FOR_FINISHED NUMBER(1)                       not null,
   PROMPT_TYPE_FOR_FINISHED VARCHAR(30),
   PROMPT_TEXT_FOR_FINISHED VARCHAR(300),
   constraint SYS_C003299 primary key (PROV_REQ_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  lob
 ( SCRIPT )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
  noparallel;

comment on column nW_DM_PROV_REQ.JOB_MODE is
'Job mode, indicate CP or DM processing mode.';

comment on column nW_DM_PROV_REQ.TYPE is
'指示目标设备是单个设备还是一组设备';

comment on column nW_DM_PROV_REQ.MAX_RETRIES is
'任务最大尝试的次数';

comment on column nW_DM_PROV_REQ.MAX_DURATION is
'max duration in seconds';

/*==============================================================*/
/* Index: nW_DM_PROV_REQ_IDX_SCH_TIME                         */
/*==============================================================*/
create index nW_DM_PROV_REQ_IDX_SCH_TIME on nW_DM_PROV_REQ (
   SCHEDULED_TIME ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_PROV_REQ_IDX_STATE                            */
/*==============================================================*/
create index nW_DM_PROV_REQ_IDX_STATE on nW_DM_PROV_REQ (
   STATE ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_PROV_REQ_IDX_TO_IMG                           */
/*==============================================================*/
create index nW_DM_PROV_REQ_IDX_TO_IMG on nW_DM_PROV_REQ (
   TARGET_IMAGE_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: IDX_PROV_REQ_MODE                                     */
/*==============================================================*/
create index IDX_PROV_REQ_MODE on nW_DM_PROV_REQ (
   JOB_MODE ASC
);

/*==============================================================*/
/* Index: IDX_PROV_REQ_NAME                                     */
/*==============================================================*/
create index IDX_PROV_REQ_NAME on nW_DM_PROV_REQ (
   TYPE ASC
);

/*==============================================================*/
/* Index: IDX_PROV_REQ_SOFT_FK                                  */
/*==============================================================*/
create index IDX_PROV_REQ_SOFT_FK on nW_DM_PROV_REQ (
   TARGET_SOFTWARE_ID ASC
);

/*==============================================================*/
/* Index: IDX_PARENT_PROV_REQ                                   */
/*==============================================================*/
create index IDX_PARENT_PROV_REQ on nW_DM_PROV_REQ (
   PARENT_PROV_REQ_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_PR_ELEMENT                                    */
/*==============================================================*/
create table nW_DM_PR_ELEMENT  (
   PR_ELEMENT_ID        NUMBER(20)                      not null,
   DEVICE_GROUP_ID      NUMBER(20),
   PROV_REQ_ID          NUMBER(20),
   SOURCE_IMAGE_ID      NUMBER(20),
   CARRIER_ID           NUMBER(20),
   STATE                VARCHAR2(20)                    not null,
   CHANGE_VERSION       NUMBER(20)                      not null,
   JOB_ELEMENT_TYPE     VARCHAR2(20)                    not null,
   constraint nW_DM_PR_ELEMENT_PK primary key (PR_ELEMENT_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: nW_DM_PR_ELEM_IDX_CARRIER                           */
/*==============================================================*/
create index nW_DM_PR_ELEM_IDX_CARRIER on nW_DM_PR_ELEMENT (
   CARRIER_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_PR_ELEM_IDX_DEV_GRP                           */
/*==============================================================*/
create index nW_DM_PR_ELEM_IDX_DEV_GRP on nW_DM_PR_ELEMENT (
   DEVICE_GROUP_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_PR_ELEM_IDX_PROV_REQ                          */
/*==============================================================*/
create index nW_DM_PR_ELEM_IDX_PROV_REQ on nW_DM_PR_ELEMENT (
   PROV_REQ_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_PR_ELEM_IDX_SRC_IMG                           */
/*==============================================================*/
create index nW_DM_PR_ELEM_IDX_SRC_IMG on nW_DM_PR_ELEMENT (
   SOURCE_IMAGE_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Table: nW_DM_PR_PHONE_NUMBER                               */
/*==============================================================*/
create table nW_DM_PR_PHONE_NUMBER  (
   PR_PN_ID             NUMBER(20)                      not null,
   PR_ID                NUMBER(20),
   PHONE_NUMBER         VARCHAR2(200)                   not null,
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint nW_DM_PR_PHONE_NUMBER_PK primary key (PR_PN_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: IDX_PR_PHONE_NUMBER_FK                                */
/*==============================================================*/
create index IDX_PR_PHONE_NUMBER_FK on nW_DM_PR_PHONE_NUMBER (
   PR_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_PR_RATE                                       */
/*==============================================================*/
create table nW_DM_PR_RATE  (
   RATE                 NUMBER(20)                      not null,
   LABEL                VARCHAR2(200)                   not null,
   IS_DEFAULT           NUMBER(20)                      not null,
   constraint nW_DM_PR_RATE_PK primary key (RATE)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_REPL_CMD_ITEM                                 */
/*==============================================================*/
create table nW_DM_REPL_CMD_ITEM  (
   REPL_CMD_ID          NUMBER(20)                      not null,
   TARGET               VARCHAR2(255),
   M_FORMAT             VARCHAR2(255),
   M_TYPE               VARCHAR2(255),
   ITEM_DATA_KIND       VARCHAR2(20),
   UPDATE_ID            VARCHAR2(255),
   RAW_DATA             CLOB,
   BINARY_DATA          BLOB,
   ITEM_INDEX           NUMBER(20)                      not null,
   constraint SYS_C003444 primary key (REPL_CMD_ID, ITEM_INDEX)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  lob
 ( RAW_DATA )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
lob
 ( BINARY_DATA )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
  noparallel;

/*==============================================================*/
/* Table: nW_DM_REPORT_TEMPLATE                               */
/*==============================================================*/
create table nW_DM_REPORT_TEMPLATE  (
   TEMPLATE_ID          NUMBER(20)                      not null,
   NAME                 VARCHAR2(100)                   not null,
   DATA                 CLOB                            not null,
   DESCRIPTION          VARCHAR2(255),
   constraint nW_DM_TEMPLATE_PK primary key (TEMPLATE_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_DM_TEMPLATE_NAME_UNIQUE unique (NAME)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  lob
 ( DATA )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
  noparallel;

/*==============================================================*/
/* Table: nW_DM_ROLE                                          */
/*==============================================================*/
create table nW_DM_ROLE  (
   ID                   NUMBER(20)                      not null,
   EXTERNAL_ID          VARCHAR2(200)                   not null,
   NAME                 VARCHAR2(30)                    not null,
   DESCRIPTION          VARCHAR2(200),
   constraint PK_NW_DM_ROLES primary key (ID)
);

/*==============================================================*/
/* Index: IDX_ROLE_EXT_ID                                       */
/*==============================================================*/
create unique index IDX_ROLE_EXT_ID on nW_DM_ROLE (
   EXTERNAL_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_SERVICE_LOCK                                  */
/*==============================================================*/
create table nW_DM_SERVICE_LOCK  (
   SERVICE_NAME         VARCHAR2(256)                   not null,
   SERVICE_OWNER        VARCHAR2(200),
   LAST_UPDATED_TIME    DATE                            not null,
   constraint nW_DM_SERVICE_LOCK_FK primary key (SERVICE_NAME)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Table: nW_DM_SERVICE_PROVIDER                              */
/*==============================================================*/
create table nW_DM_SERVICE_PROVIDER  (
   SERVICE_PROVIDER_ID  NUMBER(20)                      not null,
   EXTERNAL_ID          VARCHAR2(200)                   not null,
   NAME                 VARCHAR2(200)                   not null,
   DESCRIPTION          VARCHAR2(2000),
   constraint PK_NW_DM_SERVICE_PROVIDER primary key (SERVICE_PROVIDER_ID)
);

/*==============================================================*/
/* Index: IDX_SP_EXT_ID                                         */
/*==============================================================*/
create unique index IDX_SP_EXT_ID on nW_DM_SERVICE_PROVIDER (
   EXTERNAL_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_SESSION_AUTH                                  */
/*==============================================================*/
create table nW_DM_SESSION_AUTH  (
   SESSION_AUTH_ID      NUMBER(20)                      not null,
   SESSION_AUTH_TYPE    VARCHAR2(20),
   DEVICE_ID            NUMBER(20),
   CLIENT_NONCE         VARCHAR2(50),
   SESSION_ID           NUMBER(20),
   constraint SYS_C003454 primary key (SESSION_AUTH_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: nW_DM_SESS_AUTH_IDX_DEVICE                          */
/*==============================================================*/
create index nW_DM_SESS_AUTH_IDX_DEVICE on nW_DM_SESSION_AUTH (
   DEVICE_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_SESS_AUTH_IDX_SESSION                         */
/*==============================================================*/
create index nW_DM_SESS_AUTH_IDX_SESSION on nW_DM_SESSION_AUTH (
   SESSION_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Table: nW_DM_SOFTWARE                                      */
/*==============================================================*/
create table nW_DM_SOFTWARE  (
   SOFTWARE_ID          NUMBER(20)                      not null,
   VENDOR_ID            NUMBER(20)                      not null,
   EXTERNAL_ID          VARCHAR2(255)                   not null,
   NAME                 VARCHAR2(255)                   not null,
   VERSION              VARCHAR2(255),
   STATUS               VARCHAR2(32)                    not null,
   LICENSE_TYPE         VARCHAR2(255),
   DESCRIPTION          CLOB,
   CREATED_TIME         DATE                            not null,
   CREATED_BY           VARCHAR2(255),
   LAST_UPDATED_TIME    DATE                            not null,
   LAST_UPDATED_BY      VARCHAR2(255),
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint PK_NW_DM_SOFTWARE primary key (SOFTWARE_ID),
   constraint AK_SFT_EXT_ID_UNQ_NW_DM_SO unique (EXTERNAL_ID)
);

comment on column nW_DM_SOFTWARE.STATUS is
'release,test';

comment on column nW_DM_SOFTWARE.LICENSE_TYPE is
'free,trial';

/*==============================================================*/
/* Index: IDX_SOFTWARE_VENDOR_FK                                */
/*==============================================================*/
create index IDX_SOFTWARE_VENDOR_FK on nW_DM_SOFTWARE (
   VENDOR_ID ASC
);

/*==============================================================*/
/* Index: IDX_SOFTWRE_EXT_ID                                    */
/*==============================================================*/
create unique index IDX_SOFTWRE_EXT_ID on nW_DM_SOFTWARE (
   EXTERNAL_ID ASC
);

/*==============================================================*/
/* Index: IDX_SOFTWARE_STATUS                                   */
/*==============================================================*/
create index IDX_SOFTWARE_STATUS on nW_DM_SOFTWARE (
   STATUS ASC
);

/*==============================================================*/
/* Table: nW_DM_SOFTWARE_CATEGORIES                           */
/*==============================================================*/
create table nW_DM_SOFTWARE_CATEGORIES  (
   SOFTWARE_ID          NUMBER(20)                      not null,
   CATEGORY_ID          NUMBER(20)                      not null,
   PRIORITY             NUMBER(3)                       not null,
   constraint PK_NW_DM_SOFTWARE_CATEGORIES primary key (SOFTWARE_ID, CATEGORY_ID)
);

/*==============================================================*/
/* Index: IDX_CATEGORY_SOFTS_FK                                 */
/*==============================================================*/
create index IDX_CATEGORY_SOFTS_FK on nW_DM_SOFTWARE_CATEGORIES (
   SOFTWARE_ID ASC
);

/*==============================================================*/
/* Index: IDX_SOFT_CATEGORIES_FK                                */
/*==============================================================*/
create index IDX_SOFT_CATEGORIES_FK on nW_DM_SOFTWARE_CATEGORIES (
   CATEGORY_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_SOFTWARE_CATEGORY                             */
/*==============================================================*/
create table nW_DM_SOFTWARE_CATEGORY  (
   CATEGORY_ID          NUMBER(20)                      not null,
   PARENT_CATEGORY_ID   NUMBER(20),
   NAME                 VARCHAR2(255)                   not null,
   DESCRIPTION          CLOB,
   constraint PK_NW_DM_SOFTWARE_CATEGORY primary key (CATEGORY_ID)
);

/*==============================================================*/
/* Index: IDX_SOFT_PARENT_CATEGORY_FK                           */
/*==============================================================*/
create index IDX_SOFT_PARENT_CATEGORY_FK on nW_DM_SOFTWARE_CATEGORY (
   PARENT_CATEGORY_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_SOFTWARE_EVALUATE                             */
/*==============================================================*/
create table nW_DM_SOFTWARE_EVALUATE  (
   EVALUATE_ID          NUMBER(20)                      not null,
   SOFTWARE_ID          NUMBER(20)                      not null,
   REMARK               CLOB,
   GRADE                NUMBER(2)                       not null,
   USER_NAME            VARCHAR(20),
   USER_IP              VARCHAR(30),
   CREATED_TIME         DATE                            not null,
   constraint PK_NW_DM_SOFTWARE_EVALUATE primary key (EVALUATE_ID)
);

/*==============================================================*/
/* Index: IDX_FK_EVAL_TO_SOFT                                   */
/*==============================================================*/
create index IDX_FK_EVAL_TO_SOFT on nW_DM_SOFTWARE_EVALUATE (
   SOFTWARE_ID ASC
);

/*==============================================================*/
/* Index: IDX_SOFT_EVAL_USERID                                  */
/*==============================================================*/
create index IDX_SOFT_EVAL_USERID on nW_DM_SOFTWARE_EVALUATE (
   USER_NAME ASC
);

/*==============================================================*/
/* Index: IDX_SOFT_EVAL_CRET_TIME                               */
/*==============================================================*/
create index IDX_SOFT_EVAL_CRET_TIME on nW_DM_SOFTWARE_EVALUATE (
   CREATED_TIME ASC
);

/*==============================================================*/
/* Table: nW_DM_SOFTWARE_PACKAGE                              */
/*==============================================================*/
create table nW_DM_SOFTWARE_PACKAGE  (
   PACKAGE_ID           NUMBER(20)                      not null,
   SOFTWARE_ID          NUMBER(20)                      not null,
   LANGUAGE             VARCHAR2(64),
   MIME_TYPE            VARCHAR2(255),
   STATUS               VARCHAR2(32)                    not null,
   INSTALLATION_OPTIONS CLOB,
   URL                  VARCHAR2(1024),
   BLOB_FILENAME        VARCHAR2(255),
   BLOB_ID              NUMBER(20),
   NAME                 VARCHAR2(1024),
   DESCRIPTION          CLOB,
   PUBLICHED_TIME       DATE                            not null,
   CREATED_TIME         DATE                            not null,
   CREATED_BY           VARCHAR2(255),
   LAST_UPDATED_TIME    DATE                            not null,
   LAST_UPDATED_BY      VARCHAR2(255),
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint PK_NW_DM_SOFTWARE_PACKAGE primary key (PACKAGE_ID)
);

comment on column nW_DM_SOFTWARE_PACKAGE.MIME_TYPE is
'Software MIME Type String';

comment on column nW_DM_SOFTWARE_PACKAGE.URL is
'URL to download software';

comment on column nW_DM_SOFTWARE_PACKAGE.BLOB_FILENAME is
'Filename of this software package';

comment on column nW_DM_SOFTWARE_PACKAGE.BLOB_ID is
'Blob id which reference a blob record from table of nw_dm_blob, and this blob record hold the software installation image.';

/*==============================================================*/
/* Index: IDX_SOFTWARE_PKG_FK_SOFT                              */
/*==============================================================*/
create index IDX_SOFTWARE_PKG_FK_SOFT on nW_DM_SOFTWARE_PACKAGE (
   SOFTWARE_ID ASC
);

/*==============================================================*/
/* Index: IDX_SOFTWARE_PKG_FK_BLOG                              */
/*==============================================================*/
create index IDX_SOFTWARE_PKG_FK_BLOG on nW_DM_SOFTWARE_PACKAGE (
   BLOB_ID ASC
);

/*==============================================================*/
/* Index: IDX_SOFT_PKG_STATUS                                   */
/*==============================================================*/
create index IDX_SOFT_PKG_STATUS on nW_DM_SOFTWARE_PACKAGE (
   STATUS ASC
);

/*==============================================================*/
/* Table: nW_DM_SOFTWARE_PACKAGE_MODEL                        */
/*==============================================================*/
create table nW_DM_SOFTWARE_PACKAGE_MODEL  (
   PACKAGE_MODEL_ID     NUMBER(20)                      not null,
   MODEL_ID             NUMBER(20),
   PACKAGE_ID           NUMBER(20),
   MODEL_CLASSIFICATION_ID NUMBER(20),
   MODEL_FAMILY         VARCHAR2(255),
   DYNAMIC_MODEL_SELECTOR CLOB,
   constraint PK_NW_DM_SOFTWARE_PACKAGE_MODE primary key (PACKAGE_MODEL_ID)
);

/*==============================================================*/
/* Index: IDX_FK_SOFT_PKG_REF_MODEL                             */
/*==============================================================*/
create index IDX_FK_SOFT_PKG_REF_MODEL on nW_DM_SOFTWARE_PACKAGE_MODEL (
   MODEL_ID ASC
);

/*==============================================================*/
/* Index: IDX_FK_SOFT_PKG_MODEL                                 */
/*==============================================================*/
create index IDX_FK_SOFT_PKG_MODEL on nW_DM_SOFTWARE_PACKAGE_MODEL (
   PACKAGE_ID ASC
);

/*==============================================================*/
/* Index: IDX_SOFT_PKG_FAMILY                                   */
/*==============================================================*/
create index IDX_SOFT_PKG_FAMILY on nW_DM_SOFTWARE_PACKAGE_MODEL (
   MODEL_FAMILY ASC
);

/*==============================================================*/
/* Index: IDX_FK_SOFT_PKG_REF_MOD_CLAS                          */
/*==============================================================*/
create index IDX_FK_SOFT_PKG_REF_MOD_CLAS on nW_DM_SOFTWARE_PACKAGE_MODEL (
   MODEL_CLASSIFICATION_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_SOFTWARE_RATING                               */
/*==============================================================*/
create table nW_DM_SOFTWARE_RATING  (
   RATING_ID            NUMBER(20)                      not null,
   PACKAGE_ID           NUMBER(20),
   NAME                 VARCHAR(255)                    not null,
   RATE                 NUMBER(16),
   constraint PK_NW_DM_SOFTWARE_RATING primary key (RATING_ID),
   constraint AK_SOFT_RATING_NAME_U_NW_DM_SO unique (NAME, PACKAGE_ID)
);

/*==============================================================*/
/* Table: nW_DM_SOFTWARE_RECOMMEND                            */
/*==============================================================*/
create table nW_DM_SOFTWARE_RECOMMEND  (
   RECOMMEND_ID         NUMBER(20)                      not null,
   CATEGORY_ID          NUMBER(20),
   SOFTWARE_ID          NUMBER(20)                      not null,
   PRIORITY             NUMBER(10)                      not null,
   DESCRIPTION          CLOB,
   constraint PK_NW_DM_SOFTWARE_RECOMMEND primary key (RECOMMEND_ID)
);

/*==============================================================*/
/* Index: IDX_SOFT_RECOMM_UNIQ                                  */
/*==============================================================*/
create unique index IDX_SOFT_RECOMM_UNIQ on nW_DM_SOFTWARE_RECOMMEND (
   CATEGORY_ID ASC,
   SOFTWARE_ID ASC,
   PRIORITY ASC
);

/*==============================================================*/
/* Table: nW_DM_SOFTWARE_SCREEN_SHOOT                         */
/*==============================================================*/
create table nW_DM_SOFTWARE_SCREEN_SHOOT  (
   SCREEN_SHOOT_ID      NUMBER(20)                      not null,
   BLOB_ID              NUMBER(20)                      not null,
   SOFTWARE_ID          NUMBER(20)                      not null,
   DESCRIPTION          CLOB,
   constraint PK_NW_DM_SOFTWARE_SCREEN_SHOOT primary key (SCREEN_SHOOT_ID)
);

/*==============================================================*/
/* Index: IDX_SOFT_SCREEN_FK_GRAPHICS                           */
/*==============================================================*/
create index IDX_SOFT_SCREEN_FK_GRAPHICS on nW_DM_SOFTWARE_SCREEN_SHOOT (
   BLOB_ID ASC
);

/*==============================================================*/
/* Index: IDX_SOFT_SCREEN_FK_SOFTWARE                           */
/*==============================================================*/
create index IDX_SOFT_SCREEN_FK_SOFTWARE on nW_DM_SOFTWARE_SCREEN_SHOOT (
   SOFTWARE_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_SOFTWARE_TRACKING_LOG                         */
/*==============================================================*/
create table nW_DM_SOFTWARE_TRACKING_LOG  (
   TRACKING_LOG_ID      NUMBER(20)                      not null,
   SOFTWARE_ID          NUMBER(20),
   PACKAGE_ID           NUMBER(20),
   TRACKING_TYPE        VARCHAR(32)                     not null,
   TIME_STAMP           DATE                            not null,
   CLIENT_IP            VARCHAR2(32),
   PHONE_NUMBER         VARCHAR2(32),
   constraint PK_NW_DM_SOFTWARE_TRACKING_LOG primary key (TRACKING_LOG_ID)
         using index global partition by hash (TRACKING_LOG_ID)
);

/*==============================================================*/
/* Index: IDX_SW_TRCK_LOG_FK_SW                                 */
/*==============================================================*/
create index IDX_SW_TRCK_LOG_FK_SW on nW_DM_SOFTWARE_TRACKING_LOG (
   SOFTWARE_ID ASC
);

/*==============================================================*/
/* Index: IDX_SW_TRCK_LOG_FK_SW_PKG                             */
/*==============================================================*/
create index IDX_SW_TRCK_LOG_FK_SW_PKG on nW_DM_SOFTWARE_TRACKING_LOG (
   PACKAGE_ID ASC
);

/*==============================================================*/
/* Index: IDX_SW_TRK_LOG_TIME                                   */
/*==============================================================*/
create index IDX_SW_TRK_LOG_TIME on nW_DM_SOFTWARE_TRACKING_LOG (
   TIME_STAMP ASC
);

/*==============================================================*/
/* Index: IDX_SW_TRK_LOG_TYPE                                   */
/*==============================================================*/
create index IDX_SW_TRK_LOG_TYPE on nW_DM_SOFTWARE_TRACKING_LOG (
   TRACKING_TYPE ASC
);

/*==============================================================*/
/* Index: IDX_SW_TRK_TRK_TIME_DAY                               */
/*==============================================================*/
create index IDX_SW_TRK_TRK_TIME_DAY on nW_DM_SOFTWARE_TRACKING_LOG (
   to_char(TIME_STAMP, 'YYYY-DDD') ASC
);

/*==============================================================*/
/* Index: IDX_SW_TRK_TRK_TIME_WEEK                              */
/*==============================================================*/
create index IDX_SW_TRK_TRK_TIME_WEEK on nW_DM_SOFTWARE_TRACKING_LOG (
   to_char(TIME_STAMP, 'YYYY-IW') ASC
);

/*==============================================================*/
/* Index: IDX_SW_TRK_TRK_TIME_MONTH                             */
/*==============================================================*/
create index IDX_SW_TRK_TRK_TIME_MONTH on nW_DM_SOFTWARE_TRACKING_LOG (
   to_char(TIME_STAMP, 'YYYY-MM') ASC
);

/*==============================================================*/
/* Index: IDX_SW_TRK_TRK_TIME_YEAR                              */
/*==============================================================*/
create index IDX_SW_TRK_TRK_TIME_YEAR on nW_DM_SOFTWARE_TRACKING_LOG (
   to_char(TIME_STAMP, 'yyyy') ASC
);

/*==============================================================*/
/* Table: nW_DM_SOFTWARE_VENDOR                               */
/*==============================================================*/
create table nW_DM_SOFTWARE_VENDOR  (
   VENDOR_ID            NUMBER(20)                      not null,
   NAME                 VARCHAR2(255)                   not null,
   DESCRIPTION          CLOB,
   WEB_SITE             VARCHAR2(255),
   constraint PK_NW_DM_SOFTWARE_VENDOR primary key (VENDOR_ID)
);

/*==============================================================*/
/* Table: nW_DM_STEPS                                         */
/*==============================================================*/
create table nW_DM_STEPS  (
   STEPS_ID             NUMBER(20)                      not null,
   NAME                 VARCHAR2(100)                   not null,
   STEP_ID              NUMBER(20)                      not null,
   WORKFLOW_ID          NUMBER(20)                      not null,
   constraint nW_DM_STEPS_PK primary key (STEPS_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_DM_ST_WFID_NAME_UNIQUE unique (WORKFLOW_ID, NAME)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_DM_ST_WFID_STID_UNIQUE unique (WORKFLOW_ID, STEP_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 10
initrans 1
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: IDX_STEPS_FK                                          */
/*==============================================================*/
create index IDX_STEPS_FK on nW_DM_STEPS (
   WORKFLOW_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_SUBSCRIBER                                    */
/*==============================================================*/
create table nW_DM_SUBSCRIBER  (
   SUBSCRIBER_ID        NUMBER(20)                      not null,
   FIRSTNAME            VARCHAR2(32),
   LASTNAME             VARCHAR2(32),
   SUBSCRIBER_EXTERNAL_ID VARCHAR2(32)                    not null,
   CARRIER_ID           NUMBER(20)                      not null,
   PHONE_NUMBER         VARCHAR2(32)                    not null,
   IMSI                 VARCHAR2(32),
   PASSWORD             VARCHAR2(32)                    not null,
   NEW_PASSWORD         VARCHAR2(32),
   NEW_CARRIER_ID       NUMBER(20),
   SERVICE_PROVIDER_ID  NUMBER(20),
   STATE                VARCHAR2(32),
   SMS_STATE            VARCHAR2(32)                    not null,
   BOOTSTRAP_PIN_TYPE   VARCHAR2(36),
   PIN                  VARCHAR2(32),
   ASK_PERMISSION_ON_TRIGGER NUMBER(1)                       not null,
   NOTIFICATION_TIME    DATE,
   NO_SMS_RETRIES       NUMBER(20)                      not null,
   SMS_MESSAGE_ID       VARCHAR2(32),
   MSG_SEQ_NO           NUMBER(20)                      not null,
   IS_ACTIVATED         NUMBER(1)                       not null,
   EMAIL                VARCHAR2(200),
   CREATED_TIME         DATE                            not null,
   CREATED_BY           VARCHAR2(200),
   LAST_UPDATED_BY      VARCHAR2(200),
   LAST_UPDATED_TIME    DATE,
   LAST_REGISTRATION_TIME DATE,
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint nW_DM_SUBSCRIBER_PK primary key (SUBSCRIBER_ID)
         using index
       global partition by hash (SUBSCRIBER_ID)
       pctfree 10
       initrans 2,
   constraint nW_DM_SUBS_EXT_ID_UNIQE unique (SUBSCRIBER_EXTERNAL_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_DM_SUBS_PHONE_UNIQE unique (PHONE_NUMBER)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_DM_SUBS_SMS_ID_UNQ unique (SMS_MESSAGE_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

comment on column nW_DM_SUBSCRIBER.PIN is
'PIN for UserPIN Type';

comment on column nW_DM_SUBSCRIBER.CREATED_TIME is
'创建时间';

comment on column nW_DM_SUBSCRIBER.CREATED_BY is
'创建者';

/*==============================================================*/
/* Index: nW_DM_SUB_IDX_CARRIER                               */
/*==============================================================*/
create index nW_DM_SUB_IDX_CARRIER on nW_DM_SUBSCRIBER (
   NEW_CARRIER_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_SUB_IDX_UPDATE                                */
/*==============================================================*/
create index nW_DM_SUB_IDX_UPDATE on nW_DM_SUBSCRIBER (
   SUBSCRIBER_ID ASC,
   CHANGE_VERSION ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: IDX_SUBSCRIBER_EXT_ID                                 */
/*==============================================================*/
create index IDX_SUBSCRIBER_EXT_ID on nW_DM_SUBSCRIBER (
   SUBSCRIBER_EXTERNAL_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_UPDATE                                        */
/*==============================================================*/
create table nW_DM_UPDATE  (
   UPDATE_ID            NUMBER(20)                      not null,
   FROM_IMAGE_ID        NUMBER(20)                      not null,
   TO_IMAGE_ID          NUMBER(20)                      not null,
   STATUS_ID            NUMBER(20)                      not null,
   BLOB_ID              NUMBER(20),
   LAST_UPDATED_BY      VARCHAR2(200),
   LAST_UPDATED_TIME    DATE                            not null,
   DESCRIPTION          VARCHAR2(4000),
   WORKFLOW_ENTRY_ID    NUMBER(20)                      not null,
   WORKFLOW_ID          NUMBER(20)                      not null,
   WORKFLOW_STEPS       VARCHAR2(400),
   CHANGE_VERSION       NUMBER(20)                      not null,
   CREATED_TIME         DATE                            not null,
   constraint nW_DM_UPDATE_PK primary key (UPDATE_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_DM_UPDATE_UQ unique (BLOB_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: nW_DM_UPDATE_IDX_FROM                               */
/*==============================================================*/
create index nW_DM_UPDATE_IDX_FROM on nW_DM_UPDATE (
   FROM_IMAGE_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Index: nW_DM_UPDATE_IDX_TO                                 */
/*==============================================================*/
create index nW_DM_UPDATE_IDX_TO on nW_DM_UPDATE (
   TO_IMAGE_ID ASC
)
pctfree 10
initrans 2
maxtrans 255
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
);

/*==============================================================*/
/* Table: nW_DM_UPDATE_CARRIERS                               */
/*==============================================================*/
create table nW_DM_UPDATE_CARRIERS  (
   UPDATE_CARRIERS_ID   NUMBER(20)                      not null,
   UPDATE_ID            NUMBER(20),
   CARRIER_ID           NUMBER(20)                      not null,
   STATUS_ID            NUMBER(20)                      not null,
   WORKFLOW_ENTRY_ID    NUMBER(20)                      not null,
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint nW_DM_UPD_CARR_PK primary key (UPDATE_CARRIERS_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_DM_UPD_CARR_UNQ unique (UPDATE_ID, CARRIER_ID)
         deferrable
   initially deferred
   using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  noparallel;

/*==============================================================*/
/* Index: IDX_UPD_CARR_FK_1                                     */
/*==============================================================*/
create index IDX_UPD_CARR_FK_1 on nW_DM_UPDATE_CARRIERS (
   CARRIER_ID ASC
);

/*==============================================================*/
/* Index: IDX_UPD_CARR_FK_2                                     */
/*==============================================================*/
create index IDX_UPD_CARR_FK_2 on nW_DM_UPDATE_CARRIERS (
   STATUS_ID ASC
);

/*==============================================================*/
/* Index: IDX_UPD_CARR_FK_3                                     */
/*==============================================================*/
create index IDX_UPD_CARR_FK_3 on nW_DM_UPDATE_CARRIERS (
   UPDATE_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_UPDATE_WORKFLOW                               */
/*==============================================================*/
create table nW_DM_UPDATE_WORKFLOW  (
   WORKFLOW_ID          NUMBER(20)                      not null,
   NAME                 VARCHAR2(100)                   not null,
   INTERNAL_NAME        VARCHAR2(100)                   not null,
   IS_CURRENT           NUMBER(1)                       not null,
   DATA                 CLOB                            not null,
   TYPE                 VARCHAR2(20)                    not null,
   CHANGE_VERSION       NUMBER(20)                      not null,
   constraint nW_DM_UP_WF_PK primary key (WORKFLOW_ID)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_DM_WF_INT_NAME_UNQ unique (INTERNAL_NAME)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging,
   constraint nW_DM_WF_NAME_UNIQUE unique (NAME, TYPE)
         using index
       pctfree 10
       initrans 2
       maxtrans 255
       storage
       (
           initial 64K
           minextents 1
           maxextents unlimited
       )
        logging
)
  pctfree 5
initrans 1
maxtrans 255
storage
(
    initial 5120K
    minextents 1
    maxextents unlimited
)
logging
  lob
 ( DATA )
    store as  (
        chunk 8192
        pctversion 10
         nocache
         logging
         enable storage in row
    )
  noparallel;

/*==============================================================*/
/* Table: nW_DM_USER                                          */
/*==============================================================*/
create table nW_DM_USER  (
   ID                   NUMBER(20)                      not null,
   USERNAME             VARCHAR2(30)                    not null,
   PASSWORD             VARCHAR2(30),
   ACTIVE               NUMBER(1)                       not null,
   FIRSTNAME            VARCHAR2(30),
   LASTNAME             VARCHAR2(30),
   CREATE_TIME          DATE                            not null,
   LAST_LOGIN_TIME      DATE,
   LAST_LOGIN_IP        VARCHAR2(15),
   TELEPHONE            VARCHAR2(20),
   EMAIL                VARCHAR2(50),
   constraint  nW_DM_USER_ID_PK primary key (ID)
);

/*==============================================================*/
/* Index: IDX_USERNAME                                          */
/*==============================================================*/
create unique index IDX_USERNAME on nW_DM_USER (
   USERNAME ASC
);

/*==============================================================*/
/* Table: nW_DM_USER_CARRIER                                  */
/*==============================================================*/
create table nW_DM_USER_CARRIER  (
   USER_ID              NUMBER(20)                      not null,
   EXTERNAL_ID          VARCHAR2(200)                   not null,
   constraint nW_DM_USERS_CAR_USER_ID_PK primary key (USER_ID, EXTERNAL_ID)
);

/*==============================================================*/
/* Table: nW_DM_USER_MANUFACTURER                             */
/*==============================================================*/
create table nW_DM_USER_MANUFACTURER  (
   USER_ID              NUMBER(20)                      not null,
   EXTERNAL_ID          VARCHAR2(200)                   not null,
   constraint PK_NW_DM_USER_MANUFACTURER primary key (USER_ID, EXTERNAL_ID)
);

/*==============================================================*/
/* Index: IDX_USER_MANU_EXT_ID                                  */
/*==============================================================*/
create unique index IDX_USER_MANU_EXT_ID on nW_DM_USER_MANUFACTURER (
   EXTERNAL_ID ASC
);

/*==============================================================*/
/* Table: nW_DM_USER_ROLE                                     */
/*==============================================================*/
create table nW_DM_USER_ROLE  (
   USER_ID              NUMBER(20)                      not null,
   ROLE_ID              NUMBER(20)                      not null,
   constraint PK_NW_DM_USER_ROLES primary key (USER_ID, ROLE_ID)
);

/*==============================================================*/
/* Index: IDX_USER_ROLE_ID                                      */
/*==============================================================*/
create unique index IDX_USER_ROLE_ID on nW_DM_USER_ROLE (
   ROLE_ID ASC
);

/*==============================================================*/
/* View: DM_TRACKING_LOG_DETAIL                                 */
/*==============================================================*/
create or replace view DM_TRACKING_LOG_DETAIL(DID, JOB_ID, DEVICE_ID, SESSION_ID, BEGIN_TIME_STAMP, END_TIME_STAMP, REQUEST_SUM, RESPONSE_SUM, CLIENT_IP, USER_AGENT) as
select http.id as did, sums.job_id,sums.device_external_id,sums.jid,sums.begin_time_stamp,sums.end_time_stamp, http.dm_request_size,http.dm_response_size,http.client_ip,http.user_agent 
           from  dm_tracking_log_http  http,(select distinct  job.dm_session_id as jid,
                             job.job_id as job_id,
                             job.device_external_id as device_external_id,
                             min(begin_time_stamp) as begin_time_stamp, 
                             max(end_time_stamp) as end_time_stamp
                       from dm_tracking_log_job job 
                       group by job.job_id,job.device_external_id,job.dm_session_id)  sums
    where http.dm_session_id=sums.jid;

 comment on table DM_TRACKING_LOG_DETAIL is
'create or replace view dm_tracking_log_detail  (did,job_id,device_id,session_id,begin_time_stamp,end_time_stamp,request_sum,response_sum,client_ip,user_agent  UNIQUE RELY DISABLE NOVALIDATE,
 CONSTRAINT newid_pk PRIMARY KEY (did) RELY DISABLE NOVALIDATE) as 
   select http.id as did, sums.job_id,sums.device_external_id,sums.jid,sums.begin_time_stamp,sums.end_time_stamp, http.dm_request_size,http.dm_response_size,http.client_ip,http.user_agent 
           from  dm_tracking_log_http  http,(select distinct  job.dm_session_id as jid,
                             job.job_id as job_id,
                             job.device_external_id as device_external_id,
                             min(begin_time_stamp) as begin_time_stamp, 
                             max(end_time_stamp) as end_time_stamp
                       from dm_tracking_log_job job 
                       group by job.job_id,job.device_external_id,job.dm_session_id)  sums
    where http.dm_session_id=sums.jid ';

/*==============================================================*/
/* View: DM_TRACKING_LOG_SUM                                    */
/*==============================================================*/
create or replace view DM_TRACKING_LOG_SUM(JOB_ID, DEVICE_ID, SESSION_ID, BEGIN_TIME_STAMP, END_TIME_STAMP, REQUEST_SUM, RESPONSE_SUM) as
select sums.job_id,sums.device_external_id,sums.jid,min(sums.begin_time_stamp),max(sums.end_time_stamp), sum(http.dm_request_size),sum(http.dm_response_size)
           from  dm_tracking_log_http  http,(select distinct  job.dm_session_id as jid,
                             job.job_id as job_id,
                             job.device_external_id as device_external_id,
                             min(begin_time_stamp) as begin_time_stamp,
                             max(end_time_stamp) as end_time_stamp
                       from dm_tracking_log_job job
                       group by job.job_id,job.device_external_id,job.dm_session_id)  sums
    where http.dm_session_id=sums.jid
    group by sums.jid,sums.job_id,sums.device_external_id;

 comment on table DM_TRACKING_LOG_SUM is
'create or replace view dm_tracking_log_sum  (job_id,device_id,session_id,begin_time_stamp,end_time_stamp,request_sum,response_sum UNIQUE RELY DISABLE NOVALIDATE,
 CONSTRAINT id_pk PRIMARY KEY (session_id) RELY DISABLE NOVALIDATE) as 
   select sums.job_id,sums.device_external_id,sums.jid,min(sums.begin_time_stamp),max(sums.end_time_stamp), sum(http.dm_request_size),sum(http.dm_response_size) 
           from  dm_tracking_log_http  http,(select distinct  job.dm_session_id as jid,
                             job.job_id as job_id,
                             job.device_external_id as device_external_id,
                             min(begin_time_stamp) as begin_time_stamp, 
                             max(end_time_stamp) as end_time_stamp
                       from dm_tracking_log_job job 
                       group by job.job_id,job.device_external_id,job.dm_session_id)  sums
    where http.dm_session_id=sums.jid  
    group by sums.jid,sums.job_id,sums.device_external_id';

/*==============================================================*/
/* View: V_SOFTWARE_TRACK_LOG_ALL                               */
/*==============================================================*/
create or replace view V_SOFTWARE_TRACK_LOG_ALL as
select min(TRACKING_LOG_ID) as id, software_id, package_id, tracking_type, count(*) as count from NW_DM_SOFTWARE_TRACKING_LOG
group by software_id, package_id, tracking_type;

/*==============================================================*/
/* View: V_SOFTWARE_TRACK_LOG_DAILY                             */
/*==============================================================*/
create or replace view V_SOFTWARE_TRACK_LOG_DAILY as
select  min(TRACKING_LOG_ID) as id, software_id, package_id, tracking_type, to_char(TIME_STAMP, 'YYYY-DDD') as day_of_year, count(*) as count from NW_DM_SOFTWARE_TRACKING_LOG
group by software_id, package_id, tracking_type, to_char(TIME_STAMP, 'YYYY-DDD');

comment on column V_SOFTWARE_TRACK_LOG_DAILY.DAY_OF_YEAR is
'2008-08';

/*==============================================================*/
/* View: V_SOFTWARE_TRACK_LOG_MONTHLY                           */
/*==============================================================*/
create or replace view V_SOFTWARE_TRACK_LOG_MONTHLY as
select min(TRACKING_LOG_ID) as id, software_id, package_id, tracking_type, to_char(TIME_STAMP, 'YYYY-MM') as month_of_year, count(*) as count from NW_DM_SOFTWARE_TRACKING_LOG
group by software_id, package_id, tracking_type, to_char(TIME_STAMP, 'YYYY-MM');

/*==============================================================*/
/* View: V_SOFTWARE_TRACK_LOG_WEEKLY                            */
/*==============================================================*/
create or replace view V_SOFTWARE_TRACK_LOG_WEEKLY as
select min(TRACKING_LOG_ID) as id, software_id, package_id, tracking_type, to_char(TIME_STAMP, 'YYYY-IW') as week_of_year, count(*) as count from NW_DM_SOFTWARE_TRACKING_LOG
group by software_id, package_id, tracking_type, to_char(TIME_STAMP, 'YYYY-IW');

/*==============================================================*/
/* View: V_SOFTWARE_TRACK_LOG_YEARLY                            */
/*==============================================================*/
create or replace view V_SOFTWARE_TRACK_LOG_YEARLY as
select min(TRACKING_LOG_ID) as id, software_id, package_id, tracking_type, to_char(TIME_STAMP, 'YYYY') as year, count(*) as count from NW_DM_SOFTWARE_TRACKING_LOG
group by software_id, package_id, tracking_type, to_char(TIME_STAMP, 'YYYY');

/*==============================================================*/
/* View: nW_DM_MODEL_FAMILY                                   */
/*==============================================================*/
create or replace view nW_DM_MODEL_FAMILY as
select
   distinct FAMILY_EXT_ID
from
   nW_DM_MODEL
order by
   FAMILY_EXT_ID ASC;

alter table DM_ACCESS_LOG_HEADER
   add constraint FK_ACCESS_LOG_HEADER foreign key (LOG_ID)
      references DM_ACCESS_LOG (ID)
      on delete cascade;

alter table DM_ACCESS_LOG_PARAMETER
   add constraint FK_ACCESS_LOG_PARAM foreign key (LOG_ID)
      references DM_ACCESS_LOG (ID)
      on delete cascade;

alter table DM_ENTERPRISE_SOFTWRE
   add constraint FK_ENTER_REF_ENTER_SOFTW foreign key (ENTERPRISE_ID)
      references DM_ENTERPRISE (ENTERPRISE_ID)
      on delete cascade;

alter table DM_ENTERPRISE_SOFTWRE
   add constraint FK_ENTER_REF_SOFTW foreign key (SOFTWARE_ID)
      references nW_DM_SOFTWARE (SOFTWARE_ID)
      on delete cascade;

alter table DM_ENTERPRISE_SUBSCRIBER
   add constraint FK_ENTER_REF_ENTER_SUBS foreign key (ENTERPRISE_ID)
      references DM_ENTERPRISE (ENTERPRISE_ID)
      on delete cascade;

alter table DM_ENTERPRISE_SUBSCRIBER
   add constraint FK_ENTER_RE_SUB foreign key (SUBSCRIBER_ID)
      references nW_DM_SUBSCRIBER (SUBSCRIBER_ID)
      on delete cascade;

alter table DM_FAVORITE_DEVICE
   add constraint FK_FAVORITE foreign key (FAVORITE_ID)
      references DM_FAVORITE (FAVORITE_ID)
      on delete cascade;

alter table DM_FAVORITE_DEVICE
   add constraint FK_FAVORITE_DEVICE foreign key (DEVICE_ID)
      references nW_DM_DEVICE (DEVICE_ID)
      on delete cascade;

alter table OS_CURRENTSTEP
   add constraint nW_CURR_WFENTRY foreign key (ENTRY_ID)
      references OS_WFENTRY (ID)
      on delete cascade
      not deferrable;

alter table OS_HISTORYSTEP
   add constraint nW_HIST_WFENTRY foreign key (ENTRY_ID)
      references OS_WFENTRY (ID)
      on delete cascade
      not deferrable;

alter table OS_WFENTRY
   add constraint nW_WFENTRY_WORKFLOW foreign key (NAME)
      references nW_DM_UPDATE_WORKFLOW (INTERNAL_NAME)
      on delete cascade
      not deferrable;

alter table PREDEFINED_MODEL_SELECTOR
   add constraint FK_PRED_MODE_SELTOR foreign key (MODEL_CLASSIFICATION_ID)
      references MODEL_CLASSIFICATION (MODEL_CLASSIFICATION_ID)
      on delete cascade;

alter table PREDEFINED_MODEL_SELECTOR
   add constraint FK_PRE_MS_SELECTED_MODELS foreign key (MODEL_ID)
      references nW_DM_MODEL (MODEL_ID)
      on delete cascade;

alter table nW_CP_JOB_PROFILE
   add constraint FK_CP_JOB_REF_TAR_PROF foreign key (TARGET_DEVICE_ID)
      references nW_CP_JOB_TARGET_DEVICE (ID)
      on delete cascade;

alter table nW_CP_JOB_PROFILE_PARAMETER
   add constraint FK_CP_PROF_REF_PARAM foreign key (ID)
      references nW_CP_JOB_PROFILE (ID)
      on delete cascade;

alter table nW_CP_JOB_TARGET_DEVICE
   add constraint FK_JOB_TARGET_DEV foreign key (PROV_REQ_ID)
      references nW_DM_PROV_REQ (PROV_REQ_ID)
      on delete cascade;

alter table nW_DM_ATTRIB_TRANSLATIONS
   add constraint PROFILE_ATTRIB_MAP_FK foreign key (NODE_MAPPING_ID)
      references nW_DM_PROFILE_NODE_MAPPING (NODE_MAPPING_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_ATTRIB_TYPE_VALUES
   add constraint PROFILE_ATTRIB_TYPE_VALUE_FK foreign key (ATTRIBUTE_TYPE_ID)
      references nW_DM_PROFILE_ATTRIB_TYPE (ATTRIBUTE_TYPE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_AUDIT_LOG
   add constraint nW_DM_LOG_ACTION_FK foreign key (ACTION)
      references nW_DM_AUDIT_LOG_ACTION (VALUE)
      not deferrable;

alter table nW_DM_AUDIT_LOG_TARGET
   add constraint FK_AUDIT_LOG_TARGET foreign key (LOG_ID)
      references nW_DM_AUDIT_LOG (LOG_ID)
      on delete cascade;

alter table nW_DM_AUTO_JOB_NODES_DISCOVER
   add constraint FK_AUTO_JOB_DISCOVERY_NODES foreign key (PROV_REQ_ID)
      references nW_DM_AUTO_PROV_JOB (PROV_REQ_ID)
      on delete cascade;

alter table nW_DM_AUTO_JOB_PROFILE_CONFIG
   add constraint FK_NW_DM_AU_AUTO_JOB__NW_DM_CO foreign key (PROFILE_ID)
      references nW_DM_CONFIG_PROFILE (PROFILE_ID)
      on delete cascade;

alter table nW_DM_AUTO_JOB_PROFILE_CONFIG
   add constraint FK_AUTO_JOB_PROFILE_CONFGS foreign key (PROV_REQ_ID)
      references nW_DM_AUTO_PROV_JOB (PROV_REQ_ID)
      on delete cascade;

alter table nW_DM_AUTO_PROV_JOB
   add constraint FK_NW_DM_AU_FK_AUTO_P_NW_DM_CA foreign key (CARRIER_ID)
      references nW_DM_CARRIER (CARRIER_ID)
      on delete cascade;

alter table nW_DM_CARRIER
   add constraint DM_CAR_BOOT_DM_PROF foreign key (DM_FOR_BOOTSTRAP_ID)
      references nW_DM_CONFIG_PROFILE (PROFILE_ID)
      on delete set null;

alter table nW_DM_CARRIER
   add constraint DM_CAR_NAP_PROF_FK foreign key (NAP_FOR_BOOTSTRAP_ID)
      references nW_DM_CONFIG_PROFILE (PROFILE_ID)
      on delete set null
      not deferrable;

alter table nW_DM_CARRIER
   add constraint FK_DM_CARRIER_PARENT foreign key (PARENT_CARRIER_ID)
      references nW_DM_CARRIER (CARRIER_ID)
      on delete cascade;

alter table nW_DM_CARRIER
   add constraint nW_DM_CAR_COUN_CODE_FK foreign key (COUNTRY_ID)
      references nW_DM_COUNTRY (COUNTRY_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_CLIENT_PROV_TEMPLATE
   add constraint FK_CP_TEMPLATE_CATEGORY foreign key (CATEGORY_ID)
      references nW_DM_PROFILE_CATEGORY (CATEGORY_ID)
      on delete cascade;

alter table nW_DM_CONFIG_PROFILE
   add constraint CONFIG_PROFILE_NAP_FK foreign key (NAP_ID)
      references nW_DM_CONFIG_PROFILE (PROFILE_ID)
      on delete set null
      not deferrable;

alter table nW_DM_CONFIG_PROFILE
   add constraint CONFIG_PROFILE_PROXY_FK foreign key (PROXY_ID)
      references nW_DM_CONFIG_PROFILE (PROFILE_ID)
      on delete set null
      not deferrable;

alter table nW_DM_CONFIG_PROFILE
   add constraint CONFIG_PROFILE_TEMPLATE_FK foreign key (TEMPLATE_ID)
      references nW_DM_PROFILE_TEMPLATE (TEMPLATE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_CONFIG_PROFILE
   add constraint PROFILE_CARRIER_CARRIER_FK foreign key (CARRIER_ID)
      references nW_DM_CARRIER (CARRIER_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DDF_NODE
   add constraint DDF_NODE_DDF_TREE_FK foreign key (DDF_TREE_ID)
      references nW_DM_DDF_TREE (DDF_TREE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DDF_NODE
   add constraint DDF_NODE_PARENT_NODE_FK foreign key (PARENT_NODE_ID)
      references nW_DM_DDF_NODE (DDF_NODE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DDF_NODE_ACCESS_TYPE
   add constraint DDF_NODE_ACC_TYPE_DDF_NODE_FK foreign key (DDF_NODE_ID)
      references nW_DM_DDF_NODE (DDF_NODE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DDF_NODE_MIME_TYPE
   add constraint DDF_NODE_MIME_TYPE_DDF_NODE_FK foreign key (DDF_NODE_ID)
      references nW_DM_DDF_NODE (DDF_NODE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DEVICE
   add constraint nW_DM_DEVICE_CURRENT_UPDATE foreign key (CURRENT_UPDATE)
      references nW_DM_UPDATE (UPDATE_ID)
      on delete set null
      deferrable initially deferred;

alter table nW_DM_DEVICE
   add constraint nW_DM_DEVICE_DTREE_FK foreign key (DEVICE_TREE_ID)
      references nW_DM_DTREE (DEVICE_TREE_ID)
      on delete set null
      not deferrable;

alter table nW_DM_DEVICE
   add constraint nW_DM_DEVICE_IMAGE foreign key (CURRENT_IMAGE_ID)
      references nW_DM_IMAGE (IMAGE_ID)
      on delete set null
      not deferrable;

alter table nW_DM_DEVICE
   add constraint nW_DM_DEVICE_INP_DPR foreign key (IN_PROGRESS_DPR)
      references nW_DM_DEVICE_PROV_REQ (DEV_PROV_REQ_ID)
      on delete set null
      deferrable initially deferred;

alter table nW_DM_DEVICE
   add constraint nW_DM_DEVICE_MODEL_FK foreign key (MODEL_ID)
      references nW_DM_MODEL (MODEL_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DEVICE
   add constraint nW_DM_DEVICE_PR_ELEM_ID foreign key (PR_ELEMENT_ID)
      references nW_DM_PR_ELEMENT (PR_ELEMENT_ID)
      on delete set null
      not deferrable;

alter table nW_DM_DEVICE
   add constraint nW_DM_DEVICE_SUBS_FK foreign key (SUBSCRIBER_ID)
      references nW_DM_SUBSCRIBER (SUBSCRIBER_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DEVICE_GROUP_DEVICE
   add constraint nW_DM_DEV_GRP_DEV_FK foreign key (DEVICE_ID)
      references nW_DM_DEVICE (DEVICE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DEVICE_GROUP_DEVICE
   add constraint nW_DM_DEV_GRP_GRP_FK foreign key (DEVICE_GROUP_ID)
      references nW_DM_DEVICE_GROUP (DEVICE_GROUP_ID)
      on delete cascade
      deferrable initially deferred;

alter table nW_DM_DEVICE_GROUP_DEVICE
   add constraint nW_DM_DEV_GRP_INIT_GRP_FK foreign key (INITIAL_GROUP_ID)
      references nW_DM_DEVICE_GROUP (DEVICE_GROUP_ID)
      on delete cascade
      deferrable initially deferred;

alter table nW_DM_DEVICE_LOG
   add constraint nW_DM_DEVICE_LOG_ACTION_FK foreign key (ACTION)
      references nW_DM_DEVICE_LOG_ACTION (VALUE)
      not deferrable;

alter table nW_DM_DEVICE_LOG
   add constraint nW_DM_DEVICE_LOG_DEVICE_FK foreign key (DEVICE_ID)
      references nW_DM_DEVICE (DEVICE_ID)
      on delete set null
      deferrable initially deferred;

alter table nW_DM_DEVICE_LOG
   add constraint nW_DM_DEVICE_LOG_JOB_FK foreign key (JOB_ID)
      references nW_DM_PROV_REQ (PROV_REQ_ID)
      on delete set null
      deferrable initially deferred;

alter table nW_DM_DEVICE_PROV_REQ
   add constraint DEVICE_PROV_REQ_JOB_STATE_FK foreign key (JOB_STATE_ID)
      references nW_DM_JOB_STATE (JOB_STATE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DEVICE_PROV_REQ
   add constraint FK_DEV_STAT_TARG_SOFT foreign key (TARGET_SOFTWARE_ID)
      references nW_DM_SOFTWARE (SOFTWARE_ID)
      on delete cascade;

alter table nW_DM_DEVICE_PROV_REQ
   add constraint nW_DM_DPR_DEVICE_FK foreign key (DEVICE_ID)
      references nW_DM_DEVICE (DEVICE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DEVICE_PROV_REQ
   add constraint nW_DM_DPR_INST_IMAGE_FK foreign key (INSTALLING_IMAGE)
      references nW_DM_IMAGE (IMAGE_ID)
      on delete set null
      not deferrable;

alter table nW_DM_DEVICE_PROV_REQ
   add constraint nW_DM_DPR_OLD_IMG_FK foreign key (OLD_CURRENT_IMAGE_ID)
      references nW_DM_IMAGE (IMAGE_ID)
      on delete set null
      not deferrable;

alter table nW_DM_DEVICE_PROV_REQ
   add constraint nW_DM_DPR_PR_ELEM_FK foreign key (PR_ELEMENT_ID)
      references nW_DM_PR_ELEMENT (PR_ELEMENT_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DEVICE_PROV_REQ
   add constraint nW_DM_DPR_TO_IMG_FK foreign key (TO_IMAGE_ID)
      references nW_DM_IMAGE (IMAGE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DISCOVERY_JOB_NODE
   add constraint nW_DM_DISC_JOB_ROOT_NODE_FK foreign key (DISCOVERY_JOB_ID)
      references nW_DM_PROV_REQ (PROV_REQ_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DISC_JOB_ST_NODE
   add constraint nW_DM_DISC_JOB_ST_RT_ND_FK foreign key (DISCOVERY_JOB_STATE_ID)
      references nW_DM_JOB_STATE (JOB_STATE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DM_CMD
   add constraint DM_CMD_DM_CMD_PKG_FK foreign key (DM_CMD_PKG_ID)
      references nW_DM_DM_CMD_PKG (DM_CMD_PKG_ID)
      not deferrable;

alter table nW_DM_DM_CMD
   add constraint nW_DM_DM_COMP_FK foreign key (COMPOSITE_COMMAND_ID)
      references nW_DM_DM_CMD (DM_CMD_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DM_CMD_RESPONSE
   add constraint DM_CMD_RESP_PKG_RESP_PROC_FK foreign key (PKG_RESPONSE_PROC_ID)
      references nW_DM_DM_PKG_RESP_PROC (DM_PKG_RESP_PROC_ID)
      not deferrable;

alter table nW_DM_DM_JOB_ADAPTER
   add constraint DM_JOB_ADAPTER_DEVICE_FK foreign key (DEVICE_ID)
      references nW_DM_DEVICE (DEVICE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DM_JOB_ADAPTER
   add constraint DM_JOB_ADAPTER_DEVICE_JOB_FK foreign key (DEVICE_JOB_ID)
      references nW_DM_DM_JOB_EXEC_CLIENT (JOB_EXEC_CLIENT_ID)
      not deferrable;

alter table nW_DM_DM_JOB_ADAPTER
   add constraint DM_JOB_ADAPTER_JOB_STATE_FK foreign key (JOB_STATE_ID)
      references nW_DM_JOB_STATE (JOB_STATE_ID)
      not deferrable;

alter table nW_DM_DM_JOB_EXEC_CLIENT
   add constraint DM_JOB_EXEC_CLI_DEV_JOB_FK foreign key (DEVICE_JOB_ID)
      references nW_DM_DEVICE_PROV_REQ (DEV_PROV_REQ_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DM_JOB_EXEC_CLIENT
   add constraint DM_JOB_EXEC_CLI_WFLW_JOB_ST_FK foreign key (WORKFLOW_JOB_STATE_ID)
      references nW_DM_JOB_STATE (JOB_STATE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DM_PKG_HANDLER
   add constraint DM_PKG_HANDLER_CMD_PACKAGE_FK foreign key (COMMAND_PACKAGE_ID)
      references nW_DM_DM_CMD_PKG (DM_CMD_PKG_ID)
      not deferrable;

alter table nW_DM_DM_PKG_HANDLER
   add constraint DM_PKG_HANDLER_CMD_RSP_BLDR_FK foreign key (COMMAND_RESPONSE_BUILDER_ID)
      references nW_DM_DM_PKG_RESP_PROC (DM_PKG_RESP_PROC_ID)
      not deferrable;

alter table nW_DM_DM_PKG_HANDLER
   add constraint DM_PKG_HANDLER_DM_SESSION_FK foreign key (DM_SESSION_ID)
      references nW_DM_DM_SESSION (DM_SESSION_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DM_PKG_HANDLER
   add constraint DM_PKG_HANDLER_PKG_SENDER_FK foreign key (PACKAGE_SENDER_ID)
      references nW_DM_DM_PKG_SENDER (DM_PKG_SENDER_ID)
      not deferrable;

alter table nW_DM_DM_PKG_RESP_PROC
   add constraint DM_PKG_RESP_PROC_PKG_SNDR_FK foreign key (PACKAGE_SENDER_ID)
      references nW_DM_DM_PKG_SENDER (DM_PKG_SENDER_ID)
      not deferrable;

alter table nW_DM_DM_PKG_SENDER
   add constraint DM_PKG_SENDER_CMD_PKG_FK foreign key (COMMAND_PACKAGE_ID)
      references nW_DM_DM_CMD_PKG (DM_CMD_PKG_ID)
      not deferrable;

alter table nW_DM_DM_PKG_SENDER_CMD_IDS
   add constraint DM_PKG_SNDR_CMDIDS_PKG_SNDR_FK foreign key (DM_PKG_SENDER_ID)
      references nW_DM_DM_PKG_SENDER (DM_PKG_SENDER_ID)
      not deferrable;

alter table nW_DM_DM_RESULTS_MAP
   add constraint DM_RESULTS_MAP_DM_CMD_RESP_FK foreign key (DM_CMD_RESPONSE_ID)
      references nW_DM_DM_CMD_RESPONSE (DM_CMD_RESPONSE_ID)
      not deferrable;

alter table nW_DM_DM_SESSION
   add constraint DM_SESSION_CMD_SESS_HANDLER_FK foreign key (CMD_SESSION_HANDLER_ID)
      references nW_DM_DM_CMD_SESSION (DM_CMD_SESSION_ID)
      not deferrable;

alter table nW_DM_DM_SESSION
   add constraint DM_SESSION_CUR_PKG_HANDLER_FK foreign key (CUR_PKG_HANDLER_ID)
      references nW_DM_DM_PKG_HANDLER (DM_PKG_HANDLER_ID)
      not deferrable;

alter table nW_DM_DM_SESSION
   add constraint nW_DM_SESSION_SES_AUTH_FK foreign key (SESSION_AUTH_ID)
      references nW_DM_SESSION_AUTH (SESSION_AUTH_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DTREE
   add constraint nW_DM_DTREE_NODE_FK foreign key (ROOT_NODE_ID)
      references nW_DM_DTREE_NODE (DEVICE_TREE_NODE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_DTREE_NODE
   add constraint FK_DEVICE_PARENT_NODE foreign key (PARENT_NODE_ID)
      references nW_DM_DTREE_NODE (DEVICE_TREE_NODE_ID)
      on delete cascade;

alter table nW_DM_FW_JOB_UPDATE_PATH
   add constraint nW_DM_JOB_PATH_ID_FK foreign key (FW_JOB_STATE_ID)
      references nW_DM_JOB_STATE (JOB_STATE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_FW_JOB_UPDATE_PATH
   add constraint nW_DM_JOB_PATH_UPDATE_FK foreign key (UPDATE_ID)
      references nW_DM_UPDATE (UPDATE_ID)
      on delete set null
      not deferrable;

alter table nW_DM_GET_CMD_ITEM
   add constraint GET_CMD_ITEM_GET_CMD_FK foreign key (GET_CMD_ID)
      references nW_DM_DM_CMD (DM_CMD_ID)
      not deferrable;

alter table nW_DM_IMAGE
   add constraint nW_DM_IMAGE_MODEL_FK foreign key (MODEL_ID)
      references nW_DM_MODEL (MODEL_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_IMAGE
   add constraint nW_DM_IMG_STATUS_FK foreign key (STATUS_ID)
      references nW_DM_IMAGE_UPDATE_STATUS (STATUS_ID)
      not deferrable;

alter table nW_DM_IMAGE_CARRIERS
   add constraint nW_DM_IMG_CARR_CARR_FK foreign key (CARRIER_ID)
      references nW_DM_CARRIER (CARRIER_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_IMAGE_CARRIERS
   add constraint nW_DM_IMG_CARR_IMG_FK foreign key (IMAGE_ID)
      references nW_DM_IMAGE (IMAGE_ID)
      on delete cascade
      deferrable initially deferred;

alter table nW_DM_IMAGE_CARRIERS
   add constraint nW_DM_IMG_CARR_STATU_FK foreign key (STATUS_ID)
      references nW_DM_IMAGE_UPDATE_STATUS (STATUS_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_JOB_ASSIGNMENTS
   add constraint nW_DM_JOB_ASSGN_ID_FK foreign key (PROFILE_ASSIGNMENT_ID)
      references nW_DM_PROFILE_ASSIGNMENT (PROFILE_ASSIGNMENT_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_JOB_ASSIGNMENTS
   add constraint nW_DM_JOB_ASSGN_STATE_ID_FK foreign key (JOB_STATE_ID)
      references nW_DM_JOB_STATE (JOB_STATE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_JOB_STATE
   add constraint JOB_STATE_DEV_FK foreign key (DEVICE_ID)
      references nW_DM_DEVICE (DEVICE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_JOB_STATE
   add constraint JOB_STATE_INSTALLING_IMAGE_FK foreign key (INSTALLING_IMAGE)
      references nW_DM_IMAGE (IMAGE_ID)
      not deferrable;

alter table nW_DM_JOB_STATE
   add constraint JOB_STATE_JOB_ADAPTER_FK foreign key (JOB_ADAPTER_ID)
      references nW_DM_DM_JOB_ADAPTER (JOB_ADAPTER_ID)
      not deferrable;

alter table nW_DM_JOB_STATE
   add constraint JOB_STATE_OLD_CURRENT_IMAGE_FK foreign key (OLD_CURRENT_IMAGE_ID)
      references nW_DM_IMAGE (IMAGE_ID)
      not deferrable;

alter table nW_DM_JOB_STATE
   add constraint JOB_STATE_TO_IMAGE_FK foreign key (TO_IMAGE_ID)
      references nW_DM_IMAGE (IMAGE_ID)
      not deferrable;

alter table nW_DM_JOB_STATE
   add constraint nW_DM_JOB_CMD_PKG_FK foreign key (COMMAND_PACKAGE_ID)
      references nW_DM_DM_CMD_PKG (DM_CMD_PKG_ID)
      not deferrable;

alter table nW_DM_JOB_STATE
   add constraint nW_DM_JOB_ST_PROV_FK foreign key (JOB_ID)
      references nW_DM_PROV_REQ (PROV_REQ_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_MAPPING_NODE_NAME
   add constraint NODE_NAME_DDF_NODE_FK foreign key (DDF_NODE_ID)
      references nW_DM_DDF_NODE (DDF_NODE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_MAPPING_NODE_NAME
   add constraint NODE_NAME_PROFILE_MAPPING_FK foreign key (PROFILE_MAPPING_ID)
      references nW_DM_PROFILE_MAPPING (PROFILE_MAPPING_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_MODEL
   add constraint FK_MODEL_FAMILY foreign key (MODEL_FAMILY_ID)
      references nW_DM_MODEL (MODEL_ID)
      on delete cascade;

alter table nW_DM_MODEL
   add constraint nW_DM_MODEL_MANUFACTURER_FK foreign key (MANUFACTURER_ID)
      references nW_DM_MANUFACTURER (MANUFACTURER_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_MODEL_CHARACTER
   add constraint FK_MODEL_CHARACTER foreign key (MODEL_ID)
      references nW_DM_MODEL (MODEL_ID)
      on delete cascade;

alter table nW_DM_MODEL_CLIENT_PROV_MAP
   add constraint FK_CP_TEMPL_REF_MODEL foreign key (MODEL_ID)
      references nW_DM_MODEL (MODEL_ID)
      on delete cascade;

alter table nW_DM_MODEL_CLIENT_PROV_MAP
   add constraint FK_MODEL_REF_CP_TEMPL foreign key (TEMPLATE_ID)
      references nW_DM_CLIENT_PROV_TEMPLATE (TEMPLATE_ID)
      on delete cascade;

alter table nW_DM_MODEL_DDF_TREE
   add constraint MODEL_DDF_TREE_DDF_TREE_FK foreign key (DDF_TREE_ID)
      references nW_DM_DDF_TREE (DDF_TREE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_MODEL_DDF_TREE
   add constraint MODEL_DDF_TREE_DEVICE_MODEL_FK foreign key (DEVICE_MODEL_ID)
      references nW_DM_MODEL (MODEL_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_MODEL_DM_BOOT_PROP
   add constraint nW_DM_MOD_DM_PROP_BT_FK foreign key (MODEL_ID)
      references nW_DM_MODEL (MODEL_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_MODEL_DM_PROP
   add constraint nW_DM_MODEL_DM_PROP_FK foreign key (MODEL_ID)
      references nW_DM_MODEL (MODEL_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_MODEL_PROFILE_MAP
   add constraint MODEL_PROF_MAP_DEVICE_MODEL_FK foreign key (DEVICE_MODEL_ID)
      references nW_DM_MODEL (MODEL_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_MODEL_PROFILE_MAP
   add constraint MODEL_PROF_MAP_PROF_MAPPING_FK foreign key (PROFILE_MAPPING_ID)
      references nW_DM_PROFILE_MAPPING (PROFILE_MAPPING_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_MODEL_TAC
   add constraint nW_DM_MODEL_TAC_FK foreign key (MODEL_ID)
      references nW_DM_MODEL (MODEL_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_NODES_TO_DISCOVER
   add constraint NODES_TO_DISC_DEV_PROV_REQ_FK foreign key (DEV_PROV_REQ_ID)
      references nW_DM_JOB_STATE (JOB_STATE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PATH_ELEMENT
   add constraint nW_DM_PATH_ELEM_PR_ELEM_FK foreign key (PR_ELEMENT_ID)
      references nW_DM_PR_ELEMENT (PR_ELEMENT_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PATH_ELEMENT
   add constraint nW_DM_UPDATE_FK foreign key (UPDATE_ID)
      references nW_DM_UPDATE (UPDATE_ID)
      on delete set null
      not deferrable;

alter table nW_DM_PREV_PKG_RESP
   add constraint nW_DM_PREV_PKG_SESS_FK foreign key (DM_SESSION_ID)
      references nW_DM_DM_SESSION (DM_SESSION_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROFILE_ASSIGNMENT
   add constraint PROFILE_ASSIGNMENT_DEVICE_FK foreign key (DEVICE_ID)
      references nW_DM_DEVICE (DEVICE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROFILE_ASSIGNMENT
   add constraint PROFILE_ASSIGNMENT_PROFILE_FK foreign key (PROFILE_ID)
      references nW_DM_CONFIG_PROFILE (PROFILE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROFILE_ASSIGN_VALUE
   add constraint PROF_ASSIGN_VAL_ATTR_VALUE_FK foreign key (ATTRIBUTE_VALUE_ID)
      references nW_DM_PROFILE_ATTRIB_VALUE (ATTRIBUTE_VALUE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROFILE_ASSIGN_VALUE
   add constraint PROF_ASSIGN_VAL_PROF_ASSIGN_FK foreign key (PROFILE_ASSIGNMENT_ID)
      references nW_DM_PROFILE_ASSIGNMENT (PROFILE_ASSIGNMENT_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROFILE_ATTRIBUTE
   add constraint PROFILE_ATTRIBUTE_FK foreign key (TEMPLATE_ID)
      references nW_DM_PROFILE_TEMPLATE (TEMPLATE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROFILE_ATTRIBUTE
   add constraint PROFILE_ATTRIBUTE_TYPE_FK foreign key (ATTRIBUTE_TYPE_ID)
      references nW_DM_PROFILE_ATTRIB_TYPE (ATTRIBUTE_TYPE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROFILE_ATTRIB_VALUE
   add constraint PROFILE_ATTRIB_VALUE_ATTRIB_FK foreign key (ATTRIBUTE_ID)
      references nW_DM_PROFILE_ATTRIBUTE (ATTRIBUTE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROFILE_MAPPING
   add constraint PROF_MAPPING_PROF_ROOT_NODE_FK foreign key (PROFILE_ROOT_NODE_ID)
      references nW_DM_DDF_NODE (DDF_NODE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROFILE_MAPPING
   add constraint PROF_MAPPING_TEMPLATE_FK foreign key (TEMPLATE_ID)
      references nW_DM_PROFILE_TEMPLATE (TEMPLATE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROFILE_NODE_MAPPING
   add constraint PROF_NODE_MAPPING_ATTRIBUTE_FK foreign key (ATTRIBUTE_ID)
      references nW_DM_PROFILE_ATTRIBUTE (ATTRIBUTE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROFILE_NODE_MAPPING
   add constraint PROF_NODE_MAPPING_DDF_NODE_FK foreign key (DDF_NODE_ID)
      references nW_DM_DDF_NODE (DDF_NODE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROFILE_NODE_MAPPING
   add constraint PROF_NODE_MAPPING_PROF_MAP_FK foreign key (PROFILE_MAPPING_ID)
      references nW_DM_PROFILE_MAPPING (PROFILE_MAPPING_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROFILE_PARAMETER
   add constraint PROFILE_PARAM_ASSIGNEMENT_FK foreign key (PROFILE_ASSIGNMENT_ID)
      references nW_DM_PROFILE_ASSIGNMENT (PROFILE_ASSIGNMENT_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROFILE_PARAMETER
   add constraint PROFILE_PARAM_ATTRIBUTE_FK foreign key (ATTRIBUTE_ID)
      references nW_DM_PROFILE_ATTRIBUTE (ATTRIBUTE_ID)
      not deferrable;

alter table nW_DM_PROFILE_PARAMETER
   add constraint PROFILE_PARAM_LEAF_NODE_FK foreign key (LEAF_DDF_NODE_ID)
      references nW_DM_DDF_NODE (DDF_NODE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROFILE_TEMPLATE
   add constraint PROFILE_TEMPLATE_PROF_CAT_FK foreign key (PROFILE_CATEGORY_ID)
      references nW_DM_PROFILE_CATEGORY (CATEGORY_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROFILE_VALUE_ITEM
   add constraint PROF_VAL_ITEM_PROF_ATTR_VAL_FK foreign key (PROFILE_ATTRIBUTE_VALUE_ID)
      references nW_DM_PROFILE_ATTRIB_VALUE (ATTRIBUTE_VALUE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROFILE_VALUE_MAP
   add constraint PROF_VALUE_MAP_ATTRIB_VALUE_FK foreign key (ATTRIBUTE_VALUE_ID)
      references nW_DM_PROFILE_ATTRIB_VALUE (ATTRIBUTE_VALUE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROFILE_VALUE_MAP
   add constraint PROF_VALUE_MAP_PROFILE_FK foreign key (PROFILE_ID)
      references nW_DM_CONFIG_PROFILE (PROFILE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROV_REQ
   add constraint FK_JOB_TARGET_SOFT foreign key (TARGET_SOFTWARE_ID)
      references nW_DM_SOFTWARE (SOFTWARE_ID)
      on delete cascade;

alter table nW_DM_PROV_REQ
   add constraint FK_PROV_PARENT_JOB foreign key (PARENT_PROV_REQ_ID)
      references nW_DM_PROV_REQ (PROV_REQ_ID)
      on delete cascade;

alter table nW_DM_PROV_REQ
   add constraint nW_DM_PR_TI_FK foreign key (TARGET_IMAGE_ID)
      references nW_DM_IMAGE (IMAGE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PROV_REQ
   add constraint nW_DM_PR_WF_FK foreign key (WORKFLOW_ID)
      references nW_DM_UPDATE_WORKFLOW (WORKFLOW_ID)
      on delete set null
      not deferrable;

alter table nW_DM_PR_ELEMENT
   add constraint nW_DM_PR_ELEM_CAR_FK foreign key (CARRIER_ID)
      references nW_DM_CARRIER (CARRIER_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PR_ELEMENT
   add constraint nW_DM_PR_ELEM_DEV_GRP_FK foreign key (DEVICE_GROUP_ID)
      references nW_DM_DEVICE_GROUP (DEVICE_GROUP_ID)
      on delete cascade
      deferrable initially deferred;

alter table nW_DM_PR_ELEMENT
   add constraint nW_DM_PR_ELEM_PR_FK foreign key (PROV_REQ_ID)
      references nW_DM_PROV_REQ (PROV_REQ_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_PR_ELEMENT
   add constraint nW_DM_PR_ELEM_SI_FK foreign key (SOURCE_IMAGE_ID)
      references nW_DM_IMAGE (IMAGE_ID)
      on delete cascade
      deferrable initially deferred;

alter table nW_DM_PR_PHONE_NUMBER
   add constraint nW_DM_PR_PHONE_NUMBER_PR_PK foreign key (PR_ID)
      references nW_DM_PROV_REQ (PROV_REQ_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_REPL_CMD_ITEM
   add constraint REPL_CMD_ITEM_REPL_CMD_FK foreign key (REPL_CMD_ID)
      references nW_DM_DM_CMD (DM_CMD_ID)
      not deferrable;

alter table nW_DM_SESSION_AUTH
   add constraint nW_DM_SESSION_AUTH_DEV_FK foreign key (DEVICE_ID)
      references nW_DM_DEVICE (DEVICE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_SESSION_AUTH
   add constraint nW_DM_SESSION_AUTH_SES_FK foreign key (SESSION_ID)
      references nW_DM_DM_SESSION (DM_SESSION_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_SOFTWARE
   add constraint FK_SOFT_VENDOR foreign key (VENDOR_ID)
      references nW_DM_SOFTWARE_VENDOR (VENDOR_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_CATEGORIES
   add constraint FK_CATEGORY_SOFTS foreign key (SOFTWARE_ID)
      references nW_DM_SOFTWARE (SOFTWARE_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_CATEGORIES
   add constraint FK_SOFT_CATEGORIES foreign key (CATEGORY_ID)
      references nW_DM_SOFTWARE_CATEGORY (CATEGORY_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_CATEGORY
   add constraint FK_PARENT_SOFT_CATEGOREY foreign key (PARENT_CATEGORY_ID)
      references nW_DM_SOFTWARE_CATEGORY (CATEGORY_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_EVALUATE
   add constraint FK_SOFT_EVAL foreign key (SOFTWARE_ID)
      references nW_DM_SOFTWARE (SOFTWARE_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_PACKAGE
   add constraint FK_REFERENCE_SOFT_PKG foreign key (SOFTWARE_ID)
      references nW_DM_SOFTWARE (SOFTWARE_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_PACKAGE
   add constraint FK_SOFT_BLOB foreign key (BLOB_ID)
      references nW_DM_BLOB (BLOB_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_PACKAGE_MODEL
   add constraint FK_SOFT_PKG_MOD_REF_MODEL foreign key (MODEL_ID)
      references nW_DM_MODEL (MODEL_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_PACKAGE_MODEL
   add constraint FK_SOFT_PKG_REF_MODEL foreign key (PACKAGE_ID)
      references nW_DM_SOFTWARE_PACKAGE (PACKAGE_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_PACKAGE_MODEL
   add constraint FK_SPKG_REF_MODEL_CLASS foreign key (MODEL_CLASSIFICATION_ID)
      references MODEL_CLASSIFICATION (MODEL_CLASSIFICATION_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_RATING
   add constraint FK_SOFTWARE_RATE foreign key (PACKAGE_ID)
      references nW_DM_SOFTWARE_PACKAGE (PACKAGE_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_RECOMMEND
   add constraint FK_RECOMM_CATEGORY foreign key (CATEGORY_ID)
      references nW_DM_SOFTWARE_CATEGORY (CATEGORY_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_RECOMMEND
   add constraint FK_RECOMM_SOFTWARE foreign key (SOFTWARE_ID)
      references nW_DM_SOFTWARE (SOFTWARE_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_SCREEN_SHOOT
   add constraint FK_SCREEN_GRAPHICS foreign key (BLOB_ID)
      references nW_DM_BLOB (BLOB_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_SCREEN_SHOOT
   add constraint FK_SOFT_SCREEN_SHOOT foreign key (SOFTWARE_ID)
      references nW_DM_SOFTWARE (SOFTWARE_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_TRACKING_LOG
   add constraint FK_TRCK_TARGET_SOFT foreign key (SOFTWARE_ID)
      references nW_DM_SOFTWARE (SOFTWARE_ID)
      on delete cascade;

alter table nW_DM_SOFTWARE_TRACKING_LOG
   add constraint FK_REF_TRCK_TARGET_SOFT_PKG foreign key (PACKAGE_ID)
      references nW_DM_SOFTWARE_PACKAGE (PACKAGE_ID)
      on delete cascade;

alter table nW_DM_STEPS
   add constraint nW_DM_STEPS_WORKFLOW foreign key (WORKFLOW_ID)
      references nW_DM_UPDATE_WORKFLOW (WORKFLOW_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_SUBSCRIBER
   add constraint FK_SUBSCR_SP foreign key (SERVICE_PROVIDER_ID)
      references nW_DM_SERVICE_PROVIDER (SERVICE_PROVIDER_ID)
      on delete set null;

alter table nW_DM_SUBSCRIBER
   add constraint nW_DM_CARRIER_FK foreign key (CARRIER_ID)
      references nW_DM_CARRIER (CARRIER_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_SUBSCRIBER
   add constraint nW_DM_NEW_CARRIER_FK foreign key (NEW_CARRIER_ID)
      references nW_DM_CARRIER (CARRIER_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_UPDATE
   add constraint nW_DM_UPDATE_BLOB_FK foreign key (BLOB_ID)
      references nW_DM_BLOB (BLOB_ID)
      not deferrable;

alter table nW_DM_UPDATE
   add constraint nW_DM_UPDATE_IMAGE_FROM_FK foreign key (FROM_IMAGE_ID)
      references nW_DM_IMAGE (IMAGE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_UPDATE
   add constraint nW_DM_UPDATE_IMAGE_TO_FK foreign key (TO_IMAGE_ID)
      references nW_DM_IMAGE (IMAGE_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_UPDATE
   add constraint nW_DM_UPD_STATUS_FK foreign key (STATUS_ID)
      references nW_DM_IMAGE_UPDATE_STATUS (STATUS_ID)
      not deferrable;

alter table nW_DM_UPDATE_CARRIERS
   add constraint nW_DM_UPD_CARR_CARR_FK foreign key (CARRIER_ID)
      references nW_DM_CARRIER (CARRIER_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_UPDATE_CARRIERS
   add constraint nW_DM_UPD_CARR_STATU_FK foreign key (STATUS_ID)
      references nW_DM_IMAGE_UPDATE_STATUS (STATUS_ID)
      on delete cascade
      not deferrable;

alter table nW_DM_UPDATE_CARRIERS
   add constraint nW_DM_UPD_CARR_UPD_FK foreign key (UPDATE_ID)
      references nW_DM_UPDATE (UPDATE_ID)
      on delete cascade
      deferrable initially deferred;

alter table nW_DM_USER_CARRIER
   add constraint FK_USER_CARRIER foreign key (USER_ID)
      references nW_DM_USER (ID)
      on delete cascade;

alter table nW_DM_USER_MANUFACTURER
   add constraint FK_USERS_MANUFACT foreign key (USER_ID)
      references nW_DM_USER (ID)
      on delete cascade;

alter table nW_DM_USER_ROLE
   add constraint FK_US_USER_ROL foreign key (USER_ID)
      references nW_DM_USER (ID)
      on delete cascade;

alter table nW_DM_USER_ROLE
   add constraint FK_US_U_R_RO foreign key (ROLE_ID)
      references nW_DM_ROLE (ID)
      on delete cascade;

