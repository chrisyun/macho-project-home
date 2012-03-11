/* alter table nW_DM_CONFIG_PROFILE                                         	*/
/*   drop constraint PROFILE_CARRIER_CARRIER_FK;															*/
/*																																						*/
/* alter table nW_DM_CONFIG_PROFILE																						*/
/*   add constraint PROFILE_CARRIER_CARRIER_FK foreign key (CARRIER_ID)				*/
/*      references nW_DM_CARRIER (CARRIER_ID)																	*/
/*      on delete cascade not deferrable;																			*/

/* Basic Attribute types                                                      */
insert into NW_DM_PROFILE_ATTRIB_TYPE(ATTRIBUTE_TYPE_ID, Name, DESCRIPTION, CHANGE_VERSION) values('10', 'String',  'Basic type', '0');
insert into NW_DM_PROFILE_ATTRIB_TYPE(ATTRIBUTE_TYPE_ID, Name, DESCRIPTION, CHANGE_VERSION) values('11', 'Binary',  'Basic type', '0');
insert into NW_DM_PROFILE_ATTRIB_TYPE(ATTRIBUTE_TYPE_ID, Name, DESCRIPTION, CHANGE_VERSION) values('12', 'Boolean', 'Basic type', '0');
insert into NW_DM_PROFILE_ATTRIB_TYPE(ATTRIBUTE_TYPE_ID, Name, DESCRIPTION, CHANGE_VERSION) values('13', 'Integer', 'Basic type', '0');
insert into NW_DM_PROFILE_ATTRIB_TYPE(ATTRIBUTE_TYPE_ID, Name, DESCRIPTION, CHANGE_VERSION) values('14', 'Double',  'Basic type', '0');
insert into NW_DM_PROFILE_ATTRIB_TYPE(ATTRIBUTE_TYPE_ID, Name, DESCRIPTION, CHANGE_VERSION) values('15', 'APLink',  'Basic type, Link to Access Point with AP Path', '0');
insert into NW_DM_PROFILE_ATTRIB_TYPE(ATTRIBUTE_TYPE_ID, Name, DESCRIPTION, CHANGE_VERSION) values('16', 'APName',  'Basic type, Link to Access Point with AP Name', '0');
insert into NW_DM_PROFILE_ATTRIB_TYPE(ATTRIBUTE_TYPE_ID, Name, DESCRIPTION, CHANGE_VERSION) values('17', 'Password','Basic type', '0');
insert into NW_DM_PROFILE_ATTRIB_TYPE(ATTRIBUTE_TYPE_ID, Name, DESCRIPTION, CHANGE_VERSION) values('18', 'Email',   'Basic type', '0');
insert into NW_DM_PROFILE_ATTRIB_TYPE(ATTRIBUTE_TYPE_ID, Name, DESCRIPTION, CHANGE_VERSION) values('19', 'SelfLink',  'Basic type, Link to Selft Profile Root Path', '0');
insert into NW_DM_PROFILE_ATTRIB_TYPE(ATTRIBUTE_TYPE_ID, Name, DESCRIPTION, CHANGE_VERSION) values('20', 'SelfName',  'Basic type, Link to Selft Profile Root Name', '0');
/* Boolean Attribute type values                                                      */
insert into NW_DM_ATTRIB_TYPE_VALUES(ATTRIBUTE_TYPE_VALUE_ID, ATTRIBUTE_TYPE_ID, VALUE) values('100', '12', 'true');
insert into NW_DM_ATTRIB_TYPE_VALUES(ATTRIBUTE_TYPE_VALUE_ID, ATTRIBUTE_TYPE_ID, VALUE) values('101', '12', 'false');

/**                                                                          */
/** Image status                                                             */
/**                                                                          */
insert into NW_DM_IMAGE_UPDATE_STATUS(STATUS_ID, NAME, CHANGE_VERSION) values('1000', 'Needs Creation', 	'0');
insert into NW_DM_IMAGE_UPDATE_STATUS(STATUS_ID, NAME, CHANGE_VERSION) values('1001', 'In Progress', 			'0');
insert into NW_DM_IMAGE_UPDATE_STATUS(STATUS_ID, NAME, CHANGE_VERSION) values('1002', 'Created', 					'0');
insert into NW_DM_IMAGE_UPDATE_STATUS(STATUS_ID, NAME, CHANGE_VERSION) values('1003', 'Failed Creation', 	'0');
insert into NW_DM_IMAGE_UPDATE_STATUS(STATUS_ID, NAME, CHANGE_VERSION) values('1004', 'Needs Testing', 		'0');
insert into NW_DM_IMAGE_UPDATE_STATUS(STATUS_ID, NAME, CHANGE_VERSION) values('1005', 'Failed Testing', 	'0');
insert into NW_DM_IMAGE_UPDATE_STATUS(STATUS_ID, NAME, CHANGE_VERSION) values('1006', 'Tested', 					'0');
insert into NW_DM_IMAGE_UPDATE_STATUS(STATUS_ID, NAME, CHANGE_VERSION) values('1007', 'Released', 				'0');
insert into NW_DM_IMAGE_UPDATE_STATUS(STATUS_ID, NAME, CHANGE_VERSION) values('1008', 'Broken', 					'0');
insert into NW_DM_IMAGE_UPDATE_STATUS(STATUS_ID, NAME, CHANGE_VERSION) values('1009', 'Placeholder', 			'0');
insert into NW_DM_IMAGE_UPDATE_STATUS(STATUS_ID, NAME, CHANGE_VERSION) values('1010', 'Unrecognized', 		'0');

/**                                                                         */
/** DeviceLogAction                                                         */  
/**                                                                         */
insert into nW_DM_DEVICE_LOG_ACTION values('CREATED','Device created');

insert into nW_DM_DEVICE_LOG_ACTION values('ACTIVATED','Device activated');
insert into nW_DM_DEVICE_LOG_ACTION values('DEACTIVATED','Device deactivated');
insert into nW_DM_DEVICE_LOG_ACTION values('DELETED','Device deleted');
insert into nW_DM_DEVICE_LOG_ACTION values('BOOTSTRAP_SENT','Bootstrap message sent');
insert into nW_DM_DEVICE_LOG_ACTION values('NOTIFICATION_SENT','Notification message sent');
insert into nW_DM_DEVICE_LOG_ACTION values('BOOTSTRAP_FAILED','Bootstrap message failed');

insert into nW_DM_DEVICE_LOG_ACTION values('UPDATE_FOUND','Update found');
insert into nW_DM_DEVICE_LOG_ACTION values('UPDATE_NOT_FOUND','Update not found');
insert into nW_DM_DEVICE_LOG_ACTION values('DOWNLOADING','Downloading update');
insert into nW_DM_DEVICE_LOG_ACTION values('DOWNLOAD_URL','Received download URL');
insert into nW_DM_DEVICE_LOG_ACTION values('RECEIVED','Update received');
insert into nW_DM_DEVICE_LOG_ACTION values('APPROVED','Update approved');
insert into nW_DM_DEVICE_LOG_ACTION values('INSTALLED','Update installed');
insert into nW_DM_DEVICE_LOG_ACTION values('FAILED','Update installation failed');
insert into nW_DM_DEVICE_LOG_ACTION values('BEGIN_JOB','Job started');
insert into nW_DM_DEVICE_LOG_ACTION values('FINISHED_JOB','Job succeeded');
insert into nW_DM_DEVICE_LOG_ACTION values('JOB_FAILED','Job failed');
insert into nW_DM_DEVICE_LOG_ACTION values('SUB_JOB_CREATION_FAILED','Sub job creation failed');
insert into nW_DM_DEVICE_LOG_ACTION values('SUB_JOB_CREATED','Sub job created');
insert into nW_DM_DEVICE_LOG_ACTION values('DISCOVER_NODE','Node discovered');
insert into nW_DM_DEVICE_LOG_ACTION values('DISCOVER_NODE_FAILED','Node discovery failed');
insert into nW_DM_DEVICE_LOG_ACTION values('COMMAND_SCRIPT_COMMAND','Command script executed');
insert into nW_DM_DEVICE_LOG_ACTION values('RULE_EXECUTED','RuleSet executed');
insert into nW_DM_DEVICE_LOG_ACTION values('RULE_EXEC_FAILED','RuleSet execution failed');
insert into nW_DM_DEVICE_LOG_ACTION values('RULE_SET_INITIALIZATION_FAILED','RuleSet initialization failed');
insert into nW_DM_DEVICE_LOG_ACTION values('SEND_PROFILE_BEGIN','Started sending profile');
insert into nW_DM_DEVICE_LOG_ACTION values('DELETE_PROFILE_BEGIN','Started deleting profile');
insert into nW_DM_DEVICE_LOG_ACTION values('SEND_PROFILE_END','Succeeded sending profile');
insert into nW_DM_DEVICE_LOG_ACTION values('DELETE_PROFILE_END','Succeeded deleting profile');
insert into nW_DM_DEVICE_LOG_ACTION values('SEND_PROFILE_FAILED','Failed sending profile');
insert into nW_DM_DEVICE_LOG_ACTION values('DELETE_PROFILE_FAILED','Failed deleting profile');
insert into nW_DM_DEVICE_LOG_ACTION values('RESEND_PROFILE_BEGIN','Started resending profile');
insert into nW_DM_DEVICE_LOG_ACTION values('RESEND_PROFILE_END','Succeeded resending profile');
insert into nW_DM_DEVICE_LOG_ACTION values('RESEND_PROFILE_FAILED','Failed resending profile');
insert into nW_DM_DEVICE_LOG_ACTION values('FAILURE_TO_SEND_LARGE_OBJECT','Failed sending large object');

/**                                                                         */
/** Audit Log Actions                                                       */  
/**   Column: optional == 1, means this audit action being supported        */
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('AttributeTypes', 'addAttributeType', 'Add Attribute Type', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('AttributeTypes', 'deleteAttributeTypes', 'Delete Attribute Types', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('AttributeTypes', 'viewAttributeTypes', 'View Attribute Types', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Carrier', 'createCarrier', 'Create Carrier', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Carrier', 'deleteCarrier', 'Delete Carrier', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Carrier', 'manageProfiles', 'Manage Profiles for a Carrier', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Carrier', 'searchCarriers', 'Search Carriers', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Carrier', 'updateCarrier', 'Update Carrier', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Carrier', 'viewCarrier', 'View Carrier', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Country', 'createCountry', 'Create Country', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Country', 'deleteCountry', 'Delete Country', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Country', 'exportCountries', 'Export Countries CSV File', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Country', 'loadCountries', 'Load Countries', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Country', 'searchCountries', 'Search Countries', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Country', 'updateCountry', 'Update Country', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Country', 'viewCountry', 'View Country', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('DDF', 'deleteDDFs', 'Delete DDFs', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('DDF', 'manageDDF', 'Manage DDFs', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('DDF', 'uploadDDF', 'Upload DDF', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Device', 'activateDevice', 'Activate Device', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Device', 'createWorkflowJob', 'Create Workflow for a Device', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Device', 'createWorkflowJobForDevices', 'Create Workflow Job for Devices', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Device', 'deactivateDevice', 'Deactivate Device', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Device', 'deleteDevice', 'Delete Device', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Device', 'discoverNode', 'Discover Node on a Device', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Device', 'discoverNodes', 'Discover Nodes on a Device', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Device', 'displayKnownDeviceTree', 'Display Known Device Tree', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Device', 'enterCommandScript', 'Enter Command Script for a Device', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Device', 'getAccessibleImages', 'Get Accessible Images For Device', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Device', 'getApplicableProfiles', 'Get Applicable Profiles', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Device', 'getAvailablePathsForAssignment', 'Get Available Paths To Image', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Device', 'resetDevice', 'Reset Device', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Device', 'searchDevices', 'Search Devices', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Device', 'updateDevice', 'Update Device', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Device', 'viewDevice', 'View Device', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('DeviceLog', 'searchDeviceLogs', 'Search Device Logs', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Manufacturer', 'createManufacturer', 'Create Manufacturer', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Manufacturer', 'deleteManufacturer', 'Delete Manufacturer', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Manufacturer', 'searchManufacturers', 'Search Manufacturers', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Manufacturer', 'updateManufacturer', 'Update Manufacturer', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Manufacturer', 'viewManufacturer', 'View Manufacturer', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Model', 'createModel', 'Create Model', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Model', 'deleteModel', 'Delete Model', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Model', 'searchModels', 'Search Models', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Model', 'updateModel', 'Update Model', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Model', 'viewModel', 'View Model', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Profile', 'createProfile', 'Create Profile for a Carrier', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Profile', 'deleteProfile', 'Delete Profile', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Profile', 'updateProfile', 'Update Profile', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Profile', 'viewProfile', 'View Profile', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ProfileMapping', 'deleteProfileMappings', 'Delete Profile Mappings', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ProfileMapping', 'manageProfileMappings', 'Manage Profile Mappings', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ProfileMapping', 'createProfileMapping', 'Create Profile Mapping', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ProfileTemplate', 'deleteProfileTemplate', 'Delete Profile Template', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ProfileTemplate', 'manageProfileTemplates', 'Manage Profile Templates', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ProfileTemplate', 'createProfileTemplate', 'Create Profile Template', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ProfileTemplate', 'uploadProfilesMetaData', 'Upload Profiles Meta Data', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ProfileAssignment', 'createProfileAssignments', 'Create Profile Assignments To a Device', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ProfileAssignment', 'resendProfileAssignments', 'Resend Profile Assignments', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ProfileAssignment', 'deleteProfileAssignments', 'Delete Profile Assignments of a Device', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ProfileAssignment', 'compareProfileAssignments', 'Compare Profile Assignments', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ProfileAssignment', 'editProfileAssignment', 'Edit Profile Assignment', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ProfileAssignment', 'uploadProfileAssignments', 'Upload Profile Assignments', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ProfileAssignment', 'viewProfileAssignment', 'View Profile Assignment', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ProfileAssignment', 'viewProfileAssignments', 'View Profile Assignments', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Provisioning', 'createJob', 'Create a job', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Provisioning', 'disableJob', 'Disable a job', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Provisioning', 'enableJob', 'Enable a job', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Provisioning', 'deleteJob', 'Delete a job', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Provisioning', 'cancelJob', 'Cancel a job', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Provisioning', 'assignImageToDevice', 'Assign Image To Device', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Provisioning', 'cancelProvisioningRequest', 'Cancel Provisioning Request', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Provisioning', 'installImageOnDevices', 'Install Image On Devices', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Provisioning', 'installNewVersionOnCarriers', 'Install New Version On Carriers', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Provisioning', 'searchProvisioningRequests', 'Search Provisioning Requests', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Provisioning', 'viewProvisioningRequest', 'View Provisioning Request', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Report', 'generateReport', 'Generate Report', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Security', 'login', 'Login', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Security', 'logout', 'Logout', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Subscriber', 'createSubscriber', 'Create Subscriber', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Subscriber', 'deactivateSubscriber', 'Deactivate Subscriber', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Subscriber', 'deleteSubscriber', 'delete Subscriber', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Subscriber', 'reactivateSubscriber', 'Reactivate Subscriber', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Subscriber', 'rebootstrapSubscriber', 'Rebootstrap Subscriber', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Subscriber', 'searchSubscribers', 'Search Subscribers', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Subscriber', 'viewSubscriber', 'View Subscriber', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Update', 'changeStatusOnUpdate', 'Change Status On Update', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Update', 'createUpdatePackages', 'Create Update Packages', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Update', 'deleteUpdate', 'Delete Update', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Update', 'editUpdatePackages', 'Edit Update Packages', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Update', 'searchUpdates', 'Search Updates', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Update', 'viewUpdate', 'View Update', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('User', 'createUser', 'create User', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('User', 'deleteUser', 'delete User', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('User', 'manageUsers', 'manage Users', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('User', 'viewUser', 'view User', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Workflow', 'getAvailableWorkflowJobTypes', 'Get Workflow Job Types', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Workflow', 'loadWorkflow', 'Load Workflow', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Workflow', 'modifyWorkflowJob', 'Modify Workflow Job', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Workflow', 'renameWorkflowJob', 'Rename Workflow Job', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Workflow', 'viewWorkflowJob', 'View Workflow Job', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Workflow', 'viewWorkflowJobs', 'View Workflow Jobs', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Software', 'viewSoftware', 'View Software', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Software', 'deleteSoftware', 'Delete Software', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Software', 'createSoftware', 'Create Software', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('Software', 'editSoftware', 'Edit Software', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ClientProvTemplate', 'deleteClientProvTemplate', 'Delete CP Profile Template', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ClientProvTemplate', 'updateClientProvTemplate', 'Update CP Profile Templates', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('ClientProvTemplate', 'createClientProvTemplate', 'Create CP Profile Template', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('SoftwareCategory', 'deleteSoftwareCategory', 'Delete SoftwareCategory', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('SoftwareCategory', 'updateSoftwareCategory', 'Update SoftwareCategory', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('SoftwareCategory', 'createSoftwareCategory', 'Create SoftwareCategory', '1');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('SoftwareVendor', 'viewVendor', 'View SoftwareVendor', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('SoftwareVendor', 'deleteVendor', 'Delete SoftwareVendor', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('SoftwareVendor', 'createVendor', 'Create SoftwareVendor', '0');
insert into NW_DM_AUDIT_LOG_ACTION(TYPE, VALUE, DESCRIPTION, OPTIONAL) values('SoftwareVendor', 'updateVendor', 'Update SoftwareVendor', '0');      

/**                                                                         */
/** Default Countries.                                                      */  
/**                                                                         */
insert into nW_DM_COUNTRY values('1086', 'CN', '86', 'China', '0', '0', '1', '1', '0');
insert into nW_DM_COUNTRY values('1001', 'US', '1', 'United State', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1852', 'HK', '852', 'Hong Kong', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1044', 'GB', '44', 'United Kingdom', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1033', 'FR', '33', 'France', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1039', 'IT', '39', 'Italy', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1049', 'DE', '49', 'Germany', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1065', 'SG', '65', 'Singapore', '0', '1', '0', '1', '0');
insert into nW_DM_COUNTRY values('1034', 'ES', '34', 'Spain', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1066', 'TH', '66', 'Thailand', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1067', 'CR', '661067', 'Costa Rica', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1068', 'PE', '661068', 'Peru', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1069', 'QA', '661069', 'Qatar', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1070', 'CH', '661070', 'Switzerland', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1071', 'SY', '661071', 'Syria', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1072', 'BO', '661072', 'Bolivia', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1073', 'RO', '661073', 'Romania', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1074', 'VE', '661074', 'Venezuela', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1075', 'IL', '661075', 'Israel', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1076', 'TR', '661076', 'Turkey', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1077', 'MA', '661077', 'Morocco', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1078', 'JP', '661078', 'Japan', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1079', 'SK', '661079', 'Slovakia', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1080', 'NL', '661080', 'Netherlands', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1081', 'DZ', '661081', 'Algeria', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1082', 'DK', '661082', 'Denmark', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1083', 'BY', '661083', 'Belarus', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1084', 'GR', '661084', 'Greece', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1085', 'YE', '661085', 'Yemen', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1086', 'HN', '661086', 'Honduras', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1087', 'UA', '661087', 'Ukraine', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1088', 'AE', '661088', 'United Arab Emirates', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1089', 'PL', '661089', 'Poland', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1090', 'EE', '661090', 'Estonia', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1091', 'CL', '661091', 'Chile', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1092', 'UY', '661092', 'Uruguay', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1093', 'AU', '661093', 'Australia', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1094', 'SI', '661094', 'Slovenia', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1095', 'SD', '661095', 'Sudan', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1096', 'BH', '661096', 'Bahrain', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1097', 'TN', '661097', 'Tunisia', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1098', 'AT', '661098', 'Austria', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1099', 'CZ', '661099', 'Czech Republic', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1100', 'DO', '661100', 'Dominican Republic', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1101', 'PY', '661101', 'Paraguay', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1102', 'KR', '661102', 'South Korea', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1103', 'CO', '661103', 'Colombia', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1104', 'HR', '661104', 'Croatia', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1105', 'SE', '661105', 'Sweden', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1106', 'IS', '661106', 'Iceland', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1107', 'LV', '661107', 'Latvia', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1108', 'LT', '661108', 'Lithuania', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1109', 'NO', '661109', 'Norway', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1110', 'FI', '661110', 'Finland', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1111', 'GT', '661111', 'Guatemala', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1112', 'HU', '661112', 'Hungary', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1113', 'BE', '661113', 'Belgium', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1114', 'LY', '661114', 'Libya', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1115', 'IE', '661115', 'Ireland', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1116', 'TW', '661116', 'Taiwan', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1117', 'JO', '661117', 'Jordan', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1118', 'PR', '661118', 'Puerto Rico', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1119', 'RU', '661119', 'Russia', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1120', 'SV', '661120', 'El Salvador', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1121', 'BG', '661121', 'Bulgaria', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1122', 'IN', '661122', 'India', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1123', 'KW', '661123', 'Kuwait', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1124', 'EC', '661124', 'Ecuador', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1125', 'AL', '661125', 'Albania', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1126', 'CA', '661126', 'Canada', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1127', 'MK', '661127', 'Macedonia', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1128', 'MX', '661128', 'Mexico', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1129', 'OM', '661129', 'Oman', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1130', 'ZA', '661130', 'South Africa', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1131', 'BR', '661131', 'Brazil', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1132', 'PA', '661132', 'Panama', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1133', 'PT', '661133', 'Portugal', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1134', 'LB', '661134', 'Lebanon', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1135', 'AR', '661135', 'Argentina', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1136', 'SA', '661136', 'Saudi Arabia', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1137', 'IQ', '661137', 'Iraq', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1138', 'LU', '661138', 'Luxembourg', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1139', 'NZ', '661139', 'New Zealand', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1140', 'NI', '661140', 'Nicaragua', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1141', 'EG', '661141', 'Egypt', '0', '1', '1', '1', '0');
insert into nW_DM_COUNTRY values('1142', 'VN', '661142', 'Vietnam', '0', '1', '1', '1', '0');

/**                                                                         */
/** Default Manufacturer                                */  
/**                                                                         */
insert into nW_DM_CARRIER(CARRIER_ID, CARRIER_EXTERNAL_ID, COUNTRY_ID, NAME, SERVER_AUTH_TYPE, NOTIFICATION_TYPE, PHONE_NUMBER_POLICY, NOTIFICATION_STATE_TIMEOUT, BOOTSTRAP_TIMEOUT, NOTIFICATION_MAX_NUM_RETRIES, CHANGE_VERSION) 
values ('1000', 'DefaultCarrier', '1086', 'Default Carrier', 'syncml:auth-md5', 'wapPush', '.*', '7200', '0', '3', '0');

/**                                                                         */
/** Default Manufacturer                                                    */  
/**                                                                         */
/** insert into nW_DM_MANUFACTURER (MANUFACTURER_ID, MANUFACTURER_EXTERNAL_ID, NAME, CHANGE_VERSION)
/** values ('2000', 'Default', 'Default', '0');

/**                                                                         */
/** Default Model, Please do not change the model_id: 3000                  */  
/**                                                                         */
/** insert into nW_DM_MODEL (MODEL_ID, NAME, MANUFACTURER_MODEL_ID, MANUFACTURER_ID, SUPPORTED_DOWNLOAD_METHODS, IS_PLAIN_PROFILE, IS_OMA_ENABLED, IS_USE_ENC_FOR_OMA_MSG, IS_USE_NEXT_NONCE_PER_PKG, CHANGE_VERSION)
/** values ('3000', 'Default', 'Default Model', '2000', '1', '1', '1', '1', '1', '0');

/**                                                                         */
/** Role meta data                                                          */  
/**                                                                         */
insert into NW_DM_ROLE (ID, External_ID, Name, Description) values('100', 'dm.admin', 'dm.admin', 'dm.admin');
insert into NW_DM_ROLE (ID, External_ID, Name, Description) values('110', 'dm.admin.jobs', 'dm.admin.jobs', 'dm.admin.jobs');
insert into NW_DM_ROLE (ID, External_ID, Name, Description) values('120', 'dm.admin.devices', 'dm.admin.devices', 'dm.admin.devices');
insert into NW_DM_ROLE (ID, External_ID, Name, Description) values('130', 'dm.admin.profiles', 'dm.admin.profiles', 'dm.admin.profiles');
insert into NW_DM_ROLE (ID, External_ID, Name, Description) values('140', 'dm.admin.manufacturers', 'dm.admin.manufacturers', 'dm.admin.manufacturers');
insert into NW_DM_ROLE (ID, External_ID, Name, Description) values('150', 'dm.operator.manufacturer', 'dm.operator.manufacturer', 'dm.operator.manufacturer');
insert into NW_DM_ROLE (ID, External_ID, Name, Description) values('160', 'dm.admin.models', 'dm.admin.models', 'dm.admin.models');
insert into NW_DM_ROLE (ID, External_ID, Name, Description) values('170', 'dm.admin.carriers', 'dm.admin.carriers', 'dm.admin.carriers');
insert into NW_DM_ROLE (ID, External_ID, Name, Description) values('180', 'dm.admin.security', 'dm.admin.security', 'dm.admin.security');
insert into NW_DM_ROLE (ID, External_ID, Name, Description) values('190', 'dm.admin.audit', 'dm.admin.audit', 'dm.admin.audit');

commit;
