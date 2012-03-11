/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/processor/TestJobProcessSelector.java,v 1.28 2008/09/10 09:59:42 zhao Exp $
  * $Revision: 1.28 $
  * $Date: 2008/09/10 09:59:42 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
  *
  * This SOURCE CODE FILE, which has been provided by NPower as part
  * of a NPower product for use ONLY by licensed users of the product,
  * includes CONFIDENTIAL and PROPRIETARY information of NPower.
  *
  * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
  * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
  * THE PRODUCT.
  *
  * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
  * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
  * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
  * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
  * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
  * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
  * CODE FILE.
  * ===============================================================================================
  */
package com.npower.dm.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementProcessor;
import sync4j.framework.engine.dm.SessionContext;
import sync4j.framework.security.Sync4jPrincipal;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DDFTree;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceGroup;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelCharacter;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.core.ProfileTemplate;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.ProvisionJobStatus;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.core.SoftwareLicenseType;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.core.SoftwareVendor;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.BaseTestSoftwareBean;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.DDFTreeBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProfileAssignmentBean;
import com.npower.dm.management.ProfileConfigBean;
import com.npower.dm.management.ProfileMappingBean;
import com.npower.dm.management.ProfileTemplateBean;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.management.SubscriberBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.28 $ $Date: 2008/09/10 09:59:42 $
 */
public class TestJobProcessSelector extends TestCase {
  
  /**
   * 
   */
  private static final String ATTRIBUTE_DEFAULT_VALUE = "default value " + System.currentTimeMillis();

  private static final String BASE_DIR = AllTests.BASE_DIR;

  private String FILENAME_GET_SCRIPT = "/metadata/command/test/test.get.xml";
  private static final String MANUFACTURER_External_ID = "TEST.MANUFACTUER";
  private static final String MODEL_External_ID = "TEST.MODEL";

  /**
   * 
   */
  private static final String CLIENT_PASSWORD = "sync4j";
  /**
   * 
   */
  private static final String CLIENT_USERNAME = "sync4j";

  private String serverPassword = "srvpwd";
  /**
   * CarrierEntity External ID
   */
  private String Carrier_External_ID = "Test.Carrier";

  /**
   * DeviceEntity IMEI, ExternalID
   */
  private String Device_External_ID = "IMEI:353755000569915";


  private static final String MAPPING_TEST_FILE = BASE_DIR + "/metadata/profile/mapping/test/mapping.test.1.xml";
  /**
   * DDF files
   */
  private static final String FILENAME_TEST_NOKIA_DDF_1 = BASE_DIR + "/metadata/ddf/test/test.nokia.1.xml";

  private static final String FILENAME_TEST_NOKIA_DDF_2 = BASE_DIR + "/metadata/ddf/test/test.nokia.2.xml";

  private static final String FILENAME_TEST_NOKIA_DDF_3 = BASE_DIR + "/metadata/ddf/test/test.nokia.3.xml";

  private static final String FILENAME_TEST_NOKIA_DDF_4 = BASE_DIR + "/metadata/ddf/test/test.nokia.4.xml";

  /**
   * Profile Template XML File for Testing
   */
  private static final String FILENAME_PROFILE_METADATA = BASE_DIR + "/metadata/profile/metadata/profile.test.xml";

  /**
   * ProfileTemplate name for testcase
   */
  private static final String PROFILE_TEMPLATE_NAME_4_NAP = "TEST.Profile Template#1";

  private static final String PROFILE_TEMPLATE_NAME_4_PROXY = "Test.Proxy Template";

  private static final String PROFILE_TEMPLATE_NAME_4_EMAIL_WITH_NAP = "TEST.Email Template";

  private static final String PROFILE_TEMPLATE_NAME_4_MMS = "TEST.MMS Template";
  
  private long jobID4DiscoveryJob = 0;
  private long jobID4CommandJob = 0;
  private long jobID4ProfileAssignment = 0;

  private long jobID4ProfileReAssignment = 0;

  private long jobID4SoftwareManagement = 0;

  private long softwareID;

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    jobID4DiscoveryJob = 0;
    jobID4CommandJob = 0;
    jobID4ProfileAssignment = 0;
    jobID4ProfileReAssignment = 0;
    jobID4SoftwareManagement = 0;
    
    try {
        // clear all
        this.tearDownEntities();
        this.removeAllOfSoftwares();
        
        // Create carrier, manufacturer, model, device, subscriber
        setupEntities();
        
        // Instance a Selector
        this.setupProcessorSelector();
     } catch (Exception e) {
      throw e;
    }
  }

 
  private void setupProcessorSelector() throws Exception {
  }

  /**
   * @throws DMException
   */
  private void setupEntities() throws Exception {
    this.setupManufacturer(MANUFACTURER_External_ID);
    this.setupModel(MANUFACTURER_External_ID, MODEL_External_ID);
    
    this.setupDDFTree(MANUFACTURER_External_ID, MODEL_External_ID, FILENAME_TEST_NOKIA_DDF_1);
    this.setupDDFTree(MANUFACTURER_External_ID, MODEL_External_ID, FILENAME_TEST_NOKIA_DDF_2);
    this.setupDDFTree(MANUFACTURER_External_ID, MODEL_External_ID, FILENAME_TEST_NOKIA_DDF_3);
    this.setupDDFTree(MANUFACTURER_External_ID, MODEL_External_ID, FILENAME_TEST_NOKIA_DDF_4);
    
    this.setupProfileCategories(FILENAME_PROFILE_METADATA);
    this.setupProfileTemplates(FILENAME_PROFILE_METADATA);

    this.setUpCarrier(Carrier_External_ID);
    
    this.setUpProfileConfigs(Carrier_External_ID);
    
    this.setupDevice(Carrier_External_ID, MANUFACTURER_External_ID, MODEL_External_ID, Device_External_ID);
    
    this.setupProfileMapping(MANUFACTURER_External_ID, MODEL_External_ID, MAPPING_TEST_FILE);
    
    this.setupSoftwares(MANUFACTURER_External_ID, MODEL_External_ID);
  }
  

  private void setupManufacturer(String manufacturerExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        if (manufacturer == null) {
           manufacturer = modelBean.newManufacturerInstance(manufacturerExternalID, manufacturerExternalID, manufacturerExternalID);
           factory.beginTransaction();
           modelBean.update(manufacturer);
           factory.commit();
        }
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
    
  }

  private void setupModel(String manufacturerExternalID, String modelExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        assertNotNull(manufacturer);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExternalID);
        if (model == null) {
           model = modelBean.newModelInstance(manufacturer, modelExternalID, modelExternalID, true, true, true, true, true);
           factory.beginTransaction();
           modelBean.update(model);
           factory.commit();
        }
        // Set up model spec: Nokia S60 3rd
        factory.beginTransaction();
        ModelCharacter character = modelBean.newModelCharacterInstance(model, "general", "developer.platform");
        character.setValue("S60 3rd Edition");
        modelBean.update(character);
        factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
    
  }
  
  private void setupDDFTree(String manufacturerExternalID, String modelExternalID, String DDFFilename) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExternalID);
        DDFTreeBean treeBean = factory.createDDFTreeBean();

        InputStream in = new FileInputStream(new File(DDFFilename));
        
        factory.beginTransaction();
        DDFTree tree = treeBean.parseDDFTree(in);
        Set<DDFNode> nodes = tree.getRootDDFNodes();
        assertEquals(nodes.size(), 1);
        in.close();

        treeBean.addDDFTree(tree);
        modelBean.attachDDFTree(model, tree.getID());
        
        factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }
  
  private void setupProfileCategories(String templateFilename) throws Exception {
    InputStream in = null;
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      in = new FileInputStream(templateFilename);

      String clause4AllTestCategories = "from ProfileCategoryEntity where name like 'Test.%' or name like 'TEST.%'";

      factory.beginTransaction();
      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      List<ProfileCategory> categories = templateBean.findProfileCategories(clause4AllTestCategories);
      for (ProfileCategory category: categories) {
          templateBean.deleteCategory(category);
      }
      factory.commit();

      categories = templateBean.findProfileCategories(clause4AllTestCategories);
      assertEquals(categories.size(), 0);

      int total = templateBean.importCategory(in);
      in.close();
      assertTrue(total > 0);

      categories = templateBean.findProfileCategories(clause4AllTestCategories);
      assertTrue(categories.size() > 0);

    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  private void setupProfileTemplates(String templateFilename) throws Exception {
    InputStream in = null;
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      in = new FileInputStream(templateFilename);

      String clause4AllTestTemplates = "from ProfileTemplateEntity where name like 'Test.%' or name like 'TEST.%'";

      factory.beginTransaction();
      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      List<ProfileTemplate> templates = templateBean.findTemplates(clause4AllTestTemplates);
      for (int i = 0; i < templates.size(); i++) {
          ProfileTemplate template = (ProfileTemplate) templates.get(i);
          templateBean.delete(template);
      }
      factory.commit();

      templates = templateBean.findTemplates(clause4AllTestTemplates);
      assertEquals(templates.size(), 0);

      int total = templateBean.importProfileTemplates(in);
      in.close();
      assertTrue(total > 0);

      templates = templateBean.findTemplates(clause4AllTestTemplates);
      assertTrue(templates.size() > 0);

    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  public void setUpCarrier(String carrierExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        CarrierBean carrierBean = factory.createCarrierBean();
        Country country = factory.createCountryBean().getCountryByISOCode("CN");
        assertNotNull(country);
        assertEquals(country.getCountryCode(), "86");
        Carrier carrier = carrierBean.getCarrierByExternalID(carrierExternalID);
        if (carrier == null) {
           carrier = carrierBean.newCarrierInstance(country, carrierExternalID, carrierExternalID);
           factory.beginTransaction();
           carrierBean.update(carrier);
           factory.commit();
        }
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  private void setUpProfileConfigs(String carrierExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ProfileConfigBean profileBean = factory.createProfileConfigBean();
    try {
        factory.beginTransaction();
        {
          String templateName = PROFILE_TEMPLATE_NAME_4_NAP;
          String profileName = templateName;
          String typeOfProfile = ProfileConfig.PROFILE_TYPE_NAP;
          ProfileConfig profile = this.createProfileConfig(factory, carrierExternalID, templateName, profileName, typeOfProfile);
          profileBean.setAttributeValue(profile, "TEST Display Name",            ATTRIBUTE_DEFAULT_VALUE);
          profileBean.setAttributeValue(profile, "TEST Bearer Direction",        ATTRIBUTE_DEFAULT_VALUE);
          profileBean.setAttributeValue(profile, "TEST NAP Address",             ATTRIBUTE_DEFAULT_VALUE);
          profileBean.setAttributeValue(profile, "TEST NAP Address Type",        ATTRIBUTE_DEFAULT_VALUE);
          profileBean.setAttributeValue(profile, "TEST DNS Address",             ATTRIBUTE_DEFAULT_VALUE);
          profileBean.setAttributeValue(profile, "TEST Authentication Username", ATTRIBUTE_DEFAULT_VALUE);
          profileBean.setAttributeValue(profile, "TEST Authentication Password", ATTRIBUTE_DEFAULT_VALUE);
        }
        {
          String templateName = PROFILE_TEMPLATE_NAME_4_PROXY;
          String profileName = templateName;
          String typeOfProfile = ProfileConfig.PROFILE_TYPE_PROXY;
          ProfileConfig profile = this.createProfileConfig(factory, carrierExternalID, templateName, profileName, typeOfProfile);
          assertNotNull(profile);
          
        }
        {
          String templateName = PROFILE_TEMPLATE_NAME_4_EMAIL_WITH_NAP;
          String profileName = templateName;
          String typeOfProfile = ProfileConfig.PROFILE_TYPE_SERVICE;
          ProfileConfig profile = this.createProfileConfig(factory, carrierExternalID, templateName, profileName, typeOfProfile);
          profileBean.setAttributeValue(profile, "Display Name",                 "OTAS Email");
          profileBean.setAttributeValue(profile, "Username",                     "zhaodonglu");
          profileBean.setAttributeValue(profile, "Password",                     "zhaodonglu");
          profileBean.setAttributeValue(profile, "Email Address",                "zhaodonglu@otas.cn");
          profileBean.setAttributeValue(profile, "Reply To Address",             "zhaodonglu@otas.cn");
          profileBean.setAttributeValue(profile, "User Display Name",            "Zhao DongLu");
          profileBean.setAttributeValue(profile, "Receiving Server Address",     "pop.otas.cn");
          profileBean.setAttributeValue(profile, "Receiving Server Port",        "110");
          profileBean.setAttributeValue(profile, "Sending Server Address",       "smtp.otas.cn");
          profileBean.setAttributeValue(profile, "Sending Server Port",          "25");
          profileBean.setAttributeValue(profile, "Mailbox Protocol",             "POP");
          profileBean.setAttributeValue(profile, "APLink",                       "AP/INSG1");
        }
        {
          String templateName = PROFILE_TEMPLATE_NAME_4_MMS;
          String profileName = templateName;
          String typeOfProfile = ProfileConfig.PROFILE_TYPE_SERVICE;
          ProfileConfig profile = this.createProfileConfig(factory, carrierExternalID, templateName, profileName, typeOfProfile);
          assertNotNull(profile);
        }
        factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
    
  }

  /**
   * Create a profile config.
   * @param carrierExternalID
   * @param templateName
   * @param profileName
   * @param profileType
   * 
   * @return     Return a ProfileConfig.
   * @throws Exception
   */
  private ProfileConfig createProfileConfig(ManagementBeanFactory factory, String carrierExternalID, 
                                   String templateName, String profileName, 
                                   String profileType) throws Exception {
      ProfileTemplateBean templateBean = factory.createProfileTemplateBean();
      CarrierBean carrierBean = factory.createCarrierBean();
      ProfileConfigBean profileBean = factory.createProfileConfigBean();
      
      ProfileTemplate template = templateBean.getTemplateByName(templateName);

      Carrier carrier = carrierBean.getCarrierByExternalID(carrierExternalID);
      assertNotNull(carrier);

      ProfileConfig configProfile = profileBean.getProfileConfigByName(carrier.getExternalID(), template.getProfileCategory().getName(), profileName);
      if (configProfile != null) {
         profileBean.deleteProfileConfig(configProfile);
      }

      configProfile = profileBean.newProfileConfigInstance(profileName, 
                                carrier, template,
                                ProfileConfig.PROFILE_TYPE_NAP);
      // Add
      profileBean.update(configProfile);

      assertTrue(configProfile.getID() > 0);
      return configProfile;
  }
  
  public void setupDevice(String carrierExternalID, String manufacturerExternalID, String modelExternalID, String deviceExternalID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        CarrierBean carrierBean = factory.createCarrierBean();
        ModelBean modelBean = factory.createModelBean();
        SubscriberBean subscriberBean = factory.createSubcriberBean();
        
        Carrier carrier = carrierBean.getCarrierByExternalID(carrierExternalID);
        assertNotNull(carrier);
  
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        assertNotNull(manufacturer);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExternalID);
        assertNotNull(model);
  
        String externalID = "Zhao DongLu";
        String phoneNumber = "13801356729";
        String password = "123456!@#";
  
        List<Subscriber> list = subscriberBean.findSubscriber("from SubscriberEntity where externalId='" + externalID + "'");
        Subscriber subscriber = null;
        if (list.size() == 0) {
           subscriber = subscriberBean.newSubscriberInstance(carrier, externalID, phoneNumber, password);
  
           factory.beginTransaction();
           subscriberBean.update(subscriber);
           factory.commit();
        } else {
          subscriber = (Subscriber) list.get(0);
        }
        assertNotNull(subscriber);
  
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        if (device == null) {
          // Create a DeviceEntity
          device = deviceBean.newDeviceInstance(subscriber, model, deviceExternalID);
          device.setOMAClientUsername(CLIENT_USERNAME);
          device.setOMAClientPassword(CLIENT_PASSWORD);
          device.setOMAServerPassword(serverPassword);
          factory.beginTransaction();
          deviceBean.update(device);
          factory.commit();
        }
  
        // Test found
        device = deviceBean.getDeviceByID("" + device.getID());
        assertNotNull(device);
  
        assertEquals(deviceExternalID, device.getExternalId());
  
    } catch (DMException e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  // Test case: import profile mapping
  public void setupProfileMapping(String manufacturerExternalID, String modelExternalID, String mappingFilename) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        InputStream in = new FileInputStream(new File(mappingFilename));
        ProfileMappingBean pmBean = factory.createProfileMappingBean();
        List<ProfileMapping> mappings = pmBean.importProfileMapping(in);
        in.close();
  
        factory.beginTransaction();
        
        assertEquals(mappings.size(), 5);
        // Attach the profileMapping to Model
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExternalID);
        for (int i = 0; i < mappings.size(); i++) {
            ProfileMapping map = (ProfileMapping) mappings.get(i);
            
            modelBean.attachProfileMapping(model, map.getID());
            
            ProfileTemplate template = map.getProfileTemplate();
            ProfileMapping map2 = model.getProfileMap(template);
            assertEquals(map, map2);
            
        }
        
        factory.commit();
        
        assertEquals(5, model.getProfileMappings().size());
      
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  private ProfileAssignment createAssignment(ManagementBeanFactory factory, String profileName, String deviceExternalID) throws Exception {
    try {
        ProfileConfigBean profileBean = factory.createProfileConfigBean();
        DeviceBean deviceBean = factory.createDeviceBean();
        ProfileAssignmentBean assignmentBean = factory.createProfileAssignmentBean();
        
        ProfileConfig napProfile = profileBean.getProfileConfigByName(Carrier_External_ID, "TEST.NAP", profileName);
        assertNotNull(napProfile);
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        assertNotNull(device);
  
        // Assignment # 1
        ProfileAssignment assignment1 = assignmentBean.newProfileAssignmentInstance(napProfile, device);
        assignmentBean.update(assignment1);
  
        return assignment1;
    } catch (Exception e) {
      throw e;
    } finally {
    }
  }
  
  private ProfileAssignment setupProfileAssignment(ManagementBeanFactory factory, String profileName, String deviceExternalID) throws Exception {
    factory.beginTransaction();
  
    ProfileAssignmentBean assignmentBean = factory.createProfileAssignmentBean();
    ProfileAssignment assignment = this.createAssignment(factory, profileName, deviceExternalID);
    assignmentBean.update(assignment);
    
    factory.commit();
    return assignment;
  }

  private void setupSoftwares(String manExtID, String modelExtID) throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
      ModelBean modelBean = factory.createModelBean();

      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manExtID);
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExtID);

      SoftwareVendor vendor = BaseTestSoftwareBean.setupSoftwareVendor("software vendor#1", "Mobile software vendor#1");

      SoftwareCategory category = BaseTestSoftwareBean.setupSoftwareCategory(null, "Category#1", "Mobile software category#1");
      
      // Create software
      SoftwareBean bean = factory.createSoftwareBean();
      factory.beginTransaction();
      Software s1 = bean.newSoftwareInstance(vendor, category, "MSN", "Windows Mobile Messager", "1.0",
                                             SoftwareLicenseType.FREE);
      s1.setDescription("Microsoft Winodws Mobile Messager");
      bean.update(s1);
      this.softwareID = s1.getId();
      factory.commit();

      // Create Software package
      factory.beginTransaction();
      Set<Model> setOfModel = new HashSet<Model>();
      setOfModel.add(model);
      SoftwarePackage pkg = bean.newPackageInstance(s1, null, null, setOfModel, "http://www.software.com/msn.3.1.s60.3rd.sisx");
      String installationOpts = "<InstOpts>" + 
      "<StdOpt name=\"drive\" value=\"!\"/>" + 
      "<StdOpt name=\"lang\" value=\"*\" />" + 
      "<StdOpt name=\"upgrade\" value=\"yes\"/>" + 
      "<StdOpt name=\"kill\" value=\"yes\"/>" + 
      "<StdSymOpt name=\"pkginfo\" value=\"yes\"/>" + 
      "<StdSymOpt name=\"optionals\" value=\"yes\"/>" + 
      "<StdSymOpt name=\"ocsp\" value=\"yes\"/>" + 
      "<StdSymOpt name=\"capabilities\" value=\"yes\"/>" + 
      "<StdSymOpt name=\"untrusted\" value=\"yes\"/>" + 
      "<StdSymOpt name=\"ignoreocspwarn\" value=\"yes\"/>" + 
      "<StdSymOpt name=\"ignorewarn\" value=\"yes\"/>" + 
      "<StdSymOpt name=\"fileoverwrite\" value=\"yes\"/>" + 
      "</InstOpts>";
      pkg.setInstallationOptions(installationOpts);
      bean.update(pkg);
      long softwarePackageID = pkg.getId();
      assertTrue(softwarePackageID > 0);
      factory.commit();
      
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }

  }

  private void setupDiscoveryJob() throws Exception {
    // Testcase: add a Job
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        /*
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.getCarrierByExternalID(carrierExternalID);
    
        SubscriberBean subscriberBean = factory.createSubcriberBean();
        Subscriber subscriber = subscriberBean.getSubscriberByExternalID(deviceExternalID);
    
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExternalID);
        */
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(Device_External_ID);
        assertNotNull(device);
        
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        
        // Create devicegroup and job ...
        factory.beginTransaction();
        // Create a device group
        DeviceGroup group = deviceBean.newDeviceGroup();
        deviceBean.add(group, device);
        deviceBean.update(group);
        
        // Create a discovery job
        String[] nodePath = {"DevDetail", "DevInfo", "SyncML"};
        ProvisionJob job = jobBean.newJob4Discovery(group, nodePath);
        jobBean.update(job);
        this.jobID4DiscoveryJob = job.getID();
        assertTrue(this.jobID4DiscoveryJob > 0);
        
        factory.commit();
        
        // Testcase: load a Job
        job = jobBean.loadJobByID(this.jobID4DiscoveryJob);
        assertNotNull(job);
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job.getState());
        assertEquals(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE, job.getTargetType());
        assertEquals(ProvisionJob.JOB_TYPE_DISCOVERY, job.getJobType());
        String[] nodes = job.getNodes4Discovery();
        assertEquals(nodes.length, nodePath.length);
        // result will be sorted by rootNode
        assertEquals(nodes[0], nodePath[0]);
        assertEquals(nodes[1], nodePath[1]);
        assertEquals(nodes[2], nodePath[2]);
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
    
  }
  
  
  private void setupCommandJob() throws Exception {
    // Testcase: add a Job
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(Device_External_ID);
        assertNotNull(device);
        
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        
        // Create devicegroup and job ...
        factory.beginTransaction();
        // Create a device group
        DeviceGroup group = deviceBean.newDeviceGroup();
        deviceBean.add(group, device);
        deviceBean.update(group);
        
        // Create a command job
        String script = readScript(new File(AllTests.BASE_DIR, FILENAME_GET_SCRIPT));
        ProvisionJob job = jobBean.newJob4Command(group, script);
        jobBean.update(job);
        this.jobID4CommandJob = job.getID();
        assertTrue(this.jobID4CommandJob > 0);
        
        factory.commit();
        
        // Testcase: load a Job
        job = jobBean.loadJobByID(this.jobID4CommandJob);
        assertNotNull(job);
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job.getState());
        assertEquals(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE, job.getTargetType());
        assertEquals(ProvisionJob.JOB_TYPE_SCRIPT, job.getJobType());
        assertEquals(script, job.getScriptString());
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }  
  }

  /**
   * 
   */
  private void setupProfileReAssignmentJob() throws Exception {
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        ProfileAssignment assignment = this.setupProfileAssignment(factory, PROFILE_TEMPLATE_NAME_4_NAP, Device_External_ID);
  
        factory.beginTransaction();
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        ProvisionJob job = jobBean.newJob4Assignment(assignment);
        this.jobID4ProfileAssignment = job.getID();
        factory.commit();
        
        Device device = factory.createDeviceBean().getDeviceByExternalID(this.Device_External_ID);
        ProvisionJobStatus status = jobBean.getProvisionJobStatus(job, device);
        status.setState(ProvisionJobStatus.DEVICE_JOB_STATE_DONE);
        assignment.setProfileRootNodePath("./Email/OTAS1");
        factory.beginTransaction();
        jobBean.update(status);
        factory.createProfileAssignmentBean().update(assignment);
        factory.commit();
  
        // Re-assign it
        factory.beginTransaction();
        job = jobBean.newJob4Assignment(assignment);
        this.jobID4ProfileReAssignment = job.getID();
        factory.commit();
  
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
    
  }

  /**
   * 
   */
  private void setupProfileAssignmentJob() throws Exception {
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        ProfileAssignment assignment = this.setupProfileAssignment(factory, PROFILE_TEMPLATE_NAME_4_NAP, Device_External_ID);
  
        factory.beginTransaction();
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        ProvisionJob job = jobBean.newJob4Assignment(assignment);
        this.jobID4ProfileAssignment = job.getID();
        factory.commit();
        
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
    
  }

  private void setupSoftwareManagementJob() throws Exception {
    // Testcase: add a Job
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(Device_External_ID);
        assertNotNull(device);
        
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        
        // Create devicegroup and job ...
        factory.beginTransaction();
        // Create a device group
        DeviceGroup group = deviceBean.newDeviceGroup();
        deviceBean.add(group, device);
        deviceBean.update(group);
        
        // Create a job
        SoftwareBean softwareBean = factory.createSoftwareBean();
        Software software = softwareBean.getSoftwareByID(this.softwareID);
        assertNotNull(software);
        ProvisionJob job = jobBean.newJob4SoftwareInstall(device, software);
        jobBean.update(job);
        this.jobID4SoftwareManagement = job.getID();
        assertTrue(this.jobID4SoftwareManagement > 0);
        
        factory.commit();
        
        // Testcase: load a Job
        job = jobBean.loadJobByID(this.jobID4SoftwareManagement);
        assertNotNull(job);
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job.getState());
        assertEquals(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE, job.getTargetType());
        assertEquals(ProvisionJob.JOB_TYPE_SOFTWARE_INSTALL, job.getJobType());
        assertEquals(software.getId(), job.getTargetSoftware().getId());
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }  
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    this.tearDownEntities();
    this.removeAllOfSoftwares();
  }
  
  private void tearDownEntities() throws Exception {
    ManagementBeanFactory factory = null;
    try {
      
        factory = AllTests.getManagementBeanFactory();
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(Device_External_ID);
        if (device != null) {
          factory.beginTransaction();
          deviceBean.delete(device);
          factory.commit();
        }
        
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.getCarrierByExternalID(Carrier_External_ID);
        if (carrier != null) {
           factory.beginTransaction();
           carrierBean.delete(carrier);
           factory.commit();
        }
        
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(MANUFACTURER_External_ID);
        if (manufacturer != null) {
           factory.beginTransaction();
           modelBean.delete(manufacturer);
           factory.commit();
        }
    } catch (DMException e) {
      factory.rollback();
      e.printStackTrace();
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
    
  }
  
  /**
   * @throws DMException
   * @throws Exception
   */
  private void removeAllOfSoftwares() throws DMException, Exception {
    // Remove all of softwares
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        factory.beginTransaction();
        SoftwareBean bean = factory.createSoftwareBean();
        for (SoftwareVendor vendor: bean.getAllOfVendors()) {
            bean.delete(vendor);
        }
        factory.commit();
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  /**
   * Delete the job created for TreeDiscovery.
   * @throws Exception
   */
  public void tearDownTreeDiscoveryJob() throws Exception {
    long jobID = this.jobID4DiscoveryJob;
    deleteJob(jobID);
  }


  /**
   * Delete the job created for Command.
   * @throws Exception
   */
  public void tearDownCommandJob() throws Exception {
    long jobID = this.jobID4CommandJob;
    deleteJob(jobID);
  }

  /**
   * 
   */
  private void tearDownProfileAssignmentJob() throws Exception {
    long jobID = this.jobID4ProfileAssignment;
    deleteJob(jobID);
  }


  /**
   * 
   */
  private void tearDownProfileReAssignmentJob() throws Exception {
    long jobID = this.jobID4ProfileAssignment;
    deleteJob(jobID);
    jobID = this.jobID4ProfileReAssignment;
    deleteJob(jobID);
  }

  /**
   * 
   */
  private void tearDownSoftwareManagementJob() throws Exception {
    long jobID = this.jobID4SoftwareManagement;
    deleteJob(jobID);
  }


  /**
   * Delete the job from DM inventory.
   * @param jobID
   */
  private void deleteJob(long jobID) throws Exception {
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        factory.beginTransaction();
        jobBean.delete(jobID);
        factory.commit();
        
    } catch (DMException e) {
      factory.rollback();
      throw e;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }
  
  /**
   * Read script from a script file.
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   */
  private String readScript(File file) throws FileNotFoundException, IOException {
    BufferedReader scriptFile = new BufferedReader(new FileReader(file));
    String line = scriptFile.readLine();
    String script = "";
    while (line != null) {
          script += line;
          line = scriptFile.readLine();
    }
    return script;
  }
  
  // TestCases *********************************************************************************
  public void testErrorSelector() throws Exception {
    
    JobProcessSelector selector = new JobProcessSelector();
    selector.setDefaultProcessor(GenericProcessor.class.getName());
    selector.setErrorProcessor(ScriptManagementProcessor.class.getName());

    // Test ErrorProcessor
    String deviceExternalID = "nothing device extID";
    DevInfo devInfo = new DevInfo("nothing device extID", "nothing manufacturerID", "nothing modelExternalID", "dmV", "lang");
    DeviceDMState dmState = new DeviceDMState(deviceExternalID);
    ManagementProcessor processor = selector.getProcessor(dmState, devInfo, null);
    assertTrue(processor instanceof ScriptManagementProcessor);
    
  }
  
  public void testDefautlSelector() throws Exception {
    JobProcessSelector selector = new JobProcessSelector();
    selector.setDefaultProcessor(GenericProcessor.class.getName());
    selector.setErrorProcessor(ScriptManagementProcessor.class.getName());

    // Test DefaultProcessor
    // Make sure the queue is empty for this device in this testcase.
    DevInfo devInfo = new DevInfo(Device_External_ID, MANUFACTURER_External_ID, MODEL_External_ID, "dmV", "lang");
    DeviceDMState dmState = new DeviceDMState(Device_External_ID);
    ManagementProcessor processor = selector.getProcessor(dmState, devInfo, null);
    // Always JobQueueProcessor
    assertTrue(processor instanceof JobQueueProcessor);
  }
  
  public void testTreeDiscoverySelector() throws Exception {
    // Create a discovery job for this device.
    this.setupDiscoveryJob();
    
    JobProcessSelector selector = new JobProcessSelector();
    selector.setAlwaysQueueProcessor(false);
    selector.setDefaultProcessor(GenericProcessor.class.getName());
    selector.setErrorProcessor(ScriptManagementProcessor.class.getName());

    // Test TreeDiscoveryProcessor
    DevInfo devInfo = new DevInfo(Device_External_ID, MANUFACTURER_External_ID, MODEL_External_ID, "dmV", "lang");
    DeviceDMState dmState = new DeviceDMState(Device_External_ID);
    ManagementProcessor processor = selector.getProcessor(dmState, devInfo, null);
    assertTrue(processor instanceof TreeDiscoveryProcessor);
    assertEquals(this.jobID4DiscoveryJob, Long.parseLong(dmState.mssid));
    // Remove the discovery job
    this.tearDownTreeDiscoveryJob();
  }

  public void testCommandSelector() throws Exception {
    // Create a command job for this device.
    this.setupCommandJob();
    
    JobProcessSelector selector = new JobProcessSelector();
    selector.setAlwaysQueueProcessor(false);
    selector.setDefaultProcessor(GenericProcessor.class.getName());
    selector.setErrorProcessor(ScriptManagementProcessor.class.getName());

    // Test TreeDiscoveryProcessor
    DevInfo devInfo = new DevInfo(Device_External_ID, MANUFACTURER_External_ID, MODEL_External_ID, "dmV", "lang");
    DeviceDMState dmState = new DeviceDMState(Device_External_ID);
    ManagementProcessor processor = selector.getProcessor(dmState, devInfo, null);
    assertTrue(processor instanceof CommandProcessor);
    assertEquals(this.jobID4CommandJob, Long.parseLong(dmState.mssid));
    
    String sessionID = "00001";
    Principal principal = new Sync4jPrincipal(CLIENT_USERNAME, CLIENT_PASSWORD, Device_External_ID);
    int sessionType = 0;
    SessionContext session = new SessionContext(sessionID, sessionID, (Sync4jPrincipal)principal, sessionType, devInfo, dmState, null, null);
    processor.beginSession(session);
    ManagementOperation[] operations = processor.getNextOperations();
    assertTrue(operations.length > 1);
    int DM_SESSION_SUCCESS = DeviceDMState.STATE_COMPLETED;
    processor.endSession(DM_SESSION_SUCCESS);
    
    // Remove the command job
    this.tearDownCommandJob();
  }

  public void testProfileAssignmentSelector() throws Exception {
    // Create a ProfileAssignment job for this device.
    this.setupProfileAssignmentJob();
    
    JobProcessSelector selector = new JobProcessSelector();
    selector.setAlwaysQueueProcessor(false);
    selector.setDefaultProcessor(GenericProcessor.class.getName());
    selector.setErrorProcessor(ScriptManagementProcessor.class.getName());

    // Test TreeDiscoveryProcessor
    DevInfo devInfo = new DevInfo(Device_External_ID, MANUFACTURER_External_ID, MODEL_External_ID, "dmV", "lang");
    DeviceDMState dmState = new DeviceDMState(Device_External_ID);
    ManagementProcessor processor = selector.getProcessor(dmState, devInfo, null);

    assertTrue(processor instanceof SmartProfileAssignmentProcessor);
    assertEquals(this.jobID4ProfileAssignment, Long.parseLong(dmState.mssid));
    
    String sessionID = "00001";
    Principal principal = new Sync4jPrincipal(CLIENT_USERNAME, CLIENT_PASSWORD, Device_External_ID);
    int sessionType = 0;
    SessionContext session = new SessionContext(sessionID, sessionID, (Sync4jPrincipal)principal, sessionType, devInfo, dmState, null, null);
    processor.beginSession(session);
    ManagementOperation[] operations = processor.getNextOperations();
    assertTrue(operations.length > 1);
    int DM_SESSION_SUCCESS = DeviceDMState.STATE_COMPLETED;
    processor.endSession(DM_SESSION_SUCCESS);

    // Remove the ProfileAssignment job
    this.tearDownProfileAssignmentJob();
  }

  public void testProfileReAssignmentSelector() throws Exception {
    // Create a ProfileReAssignment job for this device.
    this.setupProfileReAssignmentJob();
    
    JobProcessSelector selector = new JobProcessSelector();
    selector.setAlwaysQueueProcessor(false);
    selector.setDefaultProcessor(GenericProcessor.class.getName());
    selector.setErrorProcessor(ScriptManagementProcessor.class.getName());

    // Test TreeDiscoveryProcessor
    DevInfo devInfo = new DevInfo(Device_External_ID, MANUFACTURER_External_ID, MODEL_External_ID, "dmV", "lang");
    DeviceDMState dmState = new DeviceDMState(Device_External_ID);
    ManagementProcessor processor = selector.getProcessor(dmState, devInfo, null);

    assertTrue(processor instanceof SmartProfileAssignmentProcessor);
    assertEquals(this.jobID4ProfileReAssignment, Long.parseLong(dmState.mssid));
    
    String sessionID = "00001";
    Principal principal = new Sync4jPrincipal(CLIENT_USERNAME, CLIENT_PASSWORD, Device_External_ID);
    int sessionType = 0;
    SessionContext session = new SessionContext(sessionID, sessionID, (Sync4jPrincipal)principal, sessionType, devInfo, dmState, null, null);
    processor.beginSession(session);
    ManagementOperation[] operations = processor.getNextOperations();
    assertTrue(operations.length > 0);
    int DM_SESSION_SUCCESS = DeviceDMState.STATE_COMPLETED;
    processor.endSession(DM_SESSION_SUCCESS);

    // Remove the ProfileReAssignment job
    this.tearDownProfileReAssignmentJob();
  }
  
  public void testSoftwareManagementSelector() throws Exception {
    // Create a SoftwareManagement job for this device.
    this.setupSoftwareManagementJob();
    
    JobProcessSelector selector = new JobProcessSelector();
    selector.setAlwaysQueueProcessor(false);
    selector.setDefaultProcessor(GenericProcessor.class.getName());
    selector.setErrorProcessor(ScriptManagementProcessor.class.getName());

    // Test TreeDiscoveryProcessor
    DevInfo devInfo = new DevInfo(Device_External_ID, MANUFACTURER_External_ID, MODEL_External_ID, "dmV", "lang");
    DeviceDMState dmState = new DeviceDMState(Device_External_ID);
    ManagementProcessor processor = selector.getProcessor(dmState, devInfo, null);
    assertTrue(processor instanceof SoftwareManagementProcessor);
    assertEquals(this.jobID4SoftwareManagement, Long.parseLong(dmState.mssid));
    
    String sessionID = "00001";
    Principal principal = new Sync4jPrincipal(CLIENT_USERNAME, CLIENT_PASSWORD, Device_External_ID);
    int sessionType = 0;
    SessionContext session = new SessionContext(sessionID, sessionID, (Sync4jPrincipal)principal, sessionType, devInfo, dmState, null, null);
    processor.beginSession(session);
    ManagementOperation[] operations = processor.getNextOperations();
    assertTrue(operations.length > 0);
    int DM_SESSION_SUCCESS = DeviceDMState.STATE_COMPLETED;
    processor.endSession(DM_SESSION_SUCCESS);
    
    // Remove the SoftwareManagement job
    this.tearDownSoftwareManagementJob();
  }

  public void testJobQueueSelector() throws Exception {
    // Create a discovery job for this device.
    this.setupDiscoveryJob();
    // Create a command job for this device.
    this.setupCommandJob();
    // Create a ProfileAssignment job for this device.
    this.setupProfileAssignmentJob();
    // Create a ProfileAssignment job for this device.
    this.setupProfileReAssignmentJob();
    
    JobProcessSelector selector = new JobProcessSelector();
    selector.setDefaultProcessor(GenericProcessor.class.getName());
    selector.setErrorProcessor(ScriptManagementProcessor.class.getName());

    // Test TreeDiscoveryProcessor
    DevInfo devInfo = new DevInfo(Device_External_ID, MANUFACTURER_External_ID, MODEL_External_ID, "dmV", "lang");
    DeviceDMState dmState = new DeviceDMState(Device_External_ID);
    ManagementProcessor processor = selector.getProcessor(dmState, devInfo, null);

    assertTrue(processor instanceof JobQueueProcessor);
    assertEquals(0, Long.parseLong(dmState.mssid));
    
    String sessionID = "00001";
    Principal principal = new Sync4jPrincipal(CLIENT_USERNAME, CLIENT_PASSWORD, Device_External_ID);
    int sessionType = 0;
    SessionContext session = new SessionContext(sessionID, sessionID, (Sync4jPrincipal)principal, sessionType, devInfo, dmState, null, null);
    processor.beginSession(session);
    ManagementOperation[] operations = processor.getNextOperations();
    assertTrue(operations.length > 0);
    int DM_SESSION_SUCCESS = DeviceDMState.STATE_COMPLETED;
    processor.endSession(DM_SESSION_SUCCESS);

    // Remove the discovery job
    this.tearDownTreeDiscoveryJob();

    // Remove the command job
    this.tearDownCommandJob();

    // Remove the ProfileAssignment job
    this.tearDownProfileAssignmentJob();

    // Remove the ProfileReAssignment job
    this.tearDownProfileReAssignmentJob();
  }
  
  public static void main(String[] args) {
    for (int i = 0; i < 5; i++) {
        junit.textui.TestRunner.run(TestJobProcessSelector.class);
    }
  }

}
